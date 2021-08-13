package server;

import client.inventory.IItem;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import database.DatabaseConnection;
import handling.MaplePacket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import tools.Pair;
import tools.packet.MTSCSPacket;

public class MTSStorage
{
    private static final long serialVersionUID = 231541893513228L;
    private static MTSStorage instance;
    private long lastUpdate;
    private final Map<Integer, MTSCart> idToCart;
    private final AtomicInteger packageId;
    private final Map<Integer, MTSItemInfo> buyNow;
    private boolean end;
    private final ReentrantReadWriteLock mutex;
    private final ReentrantReadWriteLock cart_mutex;
    
    public static MTSStorage getInstance() {
        return MTSStorage.instance;
    }
    
    public static void load() {
        if (MTSStorage.instance == null) {
            (MTSStorage.instance = new MTSStorage()).loadBuyNow();
        }
    }
    
    public MTSStorage() {
        this.lastUpdate = System.currentTimeMillis();
        this.end = false;
        System.out.println("Loading MTSStorage :::");
        this.idToCart = new LinkedHashMap<Integer, MTSCart>();
        this.buyNow = new LinkedHashMap<Integer, MTSItemInfo>();
        this.packageId = new AtomicInteger(1);
        this.mutex = new ReentrantReadWriteLock();
        this.cart_mutex = new ReentrantReadWriteLock();
    }
    
    public boolean check(final int packageid) {
        return this.getSingleItem(packageid) != null;
    }
    
    public boolean checkCart(final int packageid, final int charID) {
        final MTSItemInfo item = this.getSingleItem(packageid);
        return item != null && item.getCharacterId() != charID;
    }
    
    public MTSItemInfo getSingleItem(final int packageid) {
        this.mutex.readLock().lock();
        try {
            return this.buyNow.get(packageid);
        }
        finally {
            this.mutex.readLock().unlock();
        }
    }
    
    public void addToBuyNow(final MTSCart cart, final IItem item, final int price, final int cid, final String seller, final long expiration) {
        this.mutex.writeLock().lock();
        int id;
        try {
            id = this.packageId.incrementAndGet();
            this.buyNow.put(id, new MTSItemInfo(price, item, seller, id, cid, expiration));
        }
        finally {
            this.mutex.writeLock().unlock();
        }
        cart.addToNotYetSold(id);
    }
    
    public boolean removeFromBuyNow(final int id, final int cidBought, final boolean check) {
        IItem item = null;
        this.mutex.writeLock().lock();
        try {
            if (this.buyNow.containsKey(id)) {
                final MTSItemInfo r = this.buyNow.get(id);
                if (!check || r.getCharacterId() == cidBought) {
                    item = r.getItem();
                    this.buyNow.remove(id);
                }
            }
        }
        finally {
            this.mutex.writeLock().unlock();
        }
        if (item != null) {
            this.cart_mutex.readLock().lock();
            try {
                for (final Map.Entry<Integer, MTSCart> c : this.idToCart.entrySet()) {
                    c.getValue().removeFromCart(id);
                    c.getValue().removeFromNotYetSold(id);
                    if (c.getKey() == cidBought) {
                        c.getValue().addToInventory(item);
                    }
                }
            }
            finally {
                this.cart_mutex.readLock().unlock();
            }
        }
        return item != null;
    }
    
    private void loadBuyNow() {
        int lastPackage = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM mts_items WHERE tab = 1");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lastPackage = rs.getInt("id");
                final int cId = rs.getInt("characterid");
                if (!this.idToCart.containsKey(cId)) {
                    this.idToCart.put(cId, new MTSCart(cId));
                }
                final Map<Integer, Pair<IItem, MapleInventoryType>> items = ItemLoader.MTS.loadItems(false, lastPackage);
                if (items != null && items.size() > 0) {
                    for (final Pair<IItem, MapleInventoryType> i : items.values()) {
                        this.buyNow.put(lastPackage, new MTSItemInfo(rs.getInt("price"), i.getLeft(), rs.getString("seller"), lastPackage, cId, rs.getLong("expiration")));
                    }
                }
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        this.packageId.set(lastPackage);
    }
    
    public void saveBuyNow(final boolean isShutDown) {
        if (this.end) {
            return;
        }
        this.end = isShutDown;
        if (isShutDown) {
            System.out.println("Saving MTS...");
        }
        final Map<Integer, ArrayList<IItem>> expire = new HashMap<Integer, ArrayList<IItem>>();
        final List<Integer> toRemove = new ArrayList<Integer>();
        final long now = System.currentTimeMillis();
        final Map<Integer, ArrayList<Pair<IItem, MapleInventoryType>>> items = new HashMap<Integer, ArrayList<Pair<IItem, MapleInventoryType>>>();
        final Connection con = DatabaseConnection.getConnection();
        this.mutex.writeLock().lock();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM mts_items WHERE tab = 1");
            ps.execute();
            ps.close();
            ps = con.prepareStatement("INSERT INTO mts_items VALUES (?, ?, ?, ?, ?, ?)");
            for (final MTSItemInfo m : this.buyNow.values()) {
                if (now > m.getEndingDate()) {
                    if (!expire.containsKey(m.getCharacterId())) {
                        expire.put(m.getCharacterId(), new ArrayList<IItem>());
                    }
                    expire.get(m.getCharacterId()).add(m.getItem());
                    toRemove.add(m.getId());
                    items.put(m.getId(), null);
                }
                else {
                    ps.setInt(1, m.getId());
                    ps.setByte(2, (byte)1);
                    ps.setInt(3, m.getPrice());
                    ps.setInt(4, m.getCharacterId());
                    ps.setString(5, m.getSeller());
                    ps.setLong(6, m.getEndingDate());
                    ps.executeUpdate();
                    if (!items.containsKey(m.getId())) {
                        items.put(m.getId(), new ArrayList<Pair<IItem, MapleInventoryType>>());
                    }
                    items.get(m.getId()).add(new Pair<IItem, MapleInventoryType>(m.getItem(), GameConstants.getInventoryType(m.getItem().getItemId())));
                }
            }
            for (final int i : toRemove) {
                this.buyNow.remove(i);
            }
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.mutex.writeLock().unlock();
        }
        if (isShutDown) {
            System.out.println("Saving MTS items...");
        }
        try {
            for (final Map.Entry<Integer, ArrayList<Pair<IItem, MapleInventoryType>>> ite : items.entrySet()) {
                ItemLoader.MTS.saveItems(ite.getValue(), ite.getKey());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (isShutDown) {
            System.out.println("Saving MTS carts...");
        }
        this.cart_mutex.writeLock().lock();
        try {
            for (final Map.Entry<Integer, MTSCart> c : this.idToCart.entrySet()) {
                for (final int j : toRemove) {
                    c.getValue().removeFromCart(j);
                    c.getValue().removeFromNotYetSold(j);
                }
                if (expire.containsKey(c.getKey())) {
                    for (final IItem item : expire.get(c.getKey())) {
                        c.getValue().addToInventory(item);
                    }
                }
                c.getValue().save();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.cart_mutex.writeLock().unlock();
        }
        this.lastUpdate = System.currentTimeMillis();
    }
    
    public void checkExpirations() {
        if (System.currentTimeMillis() - this.lastUpdate > 3600000L) {
            this.saveBuyNow(false);
        }
    }
    
    public MTSCart getCart(final int characterId) {
        this.cart_mutex.readLock().lock();
        MTSCart ret;
        try {
            ret = this.idToCart.get(characterId);
        }
        finally {
            this.cart_mutex.readLock().unlock();
        }
        if (ret == null) {
            this.cart_mutex.writeLock().lock();
            try {
                ret = new MTSCart(characterId);
                this.idToCart.put(characterId, ret);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                this.cart_mutex.writeLock().unlock();
            }
        }
        return ret;
    }
    
    public MaplePacket getCurrentMTS(final MTSCart cart) {
        this.mutex.readLock().lock();
        try {
            switch (cart.getTab()) {
                case 1: {
                    return MTSCSPacket.sendMTS(this.getBuyNow(cart.getType(), cart.getPage()), cart.getTab(), cart.getType(), cart.getPage(), this.buyNow.size() / 16 + ((this.buyNow.size() % 16 > 0) ? 1 : 0));
                }
                case 4: {
                    return MTSCSPacket.sendMTS(this.getCartItems(cart), cart.getTab(), cart.getType(), cart.getPage(), 0);
                }
                default: {
                    return MTSCSPacket.sendMTS(new ArrayList<MTSItemInfo>(), cart.getTab(), cart.getType(), cart.getPage(), 0);
                }
            }
        }
        finally {
            this.mutex.readLock().unlock();
        }
    }
    
    public MaplePacket getCurrentNotYetSold(final MTSCart cart) {
        this.mutex.readLock().lock();
        try {
            final List<MTSItemInfo> nys = new ArrayList<MTSItemInfo>();
            final List<Integer> nyss = new ArrayList<Integer>(cart.getNotYetSold());
            for (final int i : nyss) {
                final MTSItemInfo r = this.buyNow.get(i);
                if (r == null) {
                    cart.removeFromNotYetSold(i);
                }
                else {
                    nys.add(r);
                }
            }
            return MTSCSPacket.getNotYetSoldInv(nys);
        }
        finally {
            this.mutex.readLock().unlock();
        }
    }
    
    public MaplePacket getCurrentTransfer(final MTSCart cart, final boolean changed) {
        return MTSCSPacket.getTransferInventory(cart.getInventory(), changed);
    }
    
    private List<MTSItemInfo> getBuyNow(final int type, int page) {
        final int size = this.buyNow.size() / 16 + ((this.buyNow.size() % 16 > 0) ? 1 : 0);
        final List<MTSItemInfo> ret = new ArrayList<MTSItemInfo>();
        final List<MTSItemInfo> rett = new ArrayList<MTSItemInfo>(this.buyNow.values());
        if (page > size) {
            page = 0;
        }
        for (int i = page * 16; i < page * 16 + 16 && this.buyNow.size() >= i + 1; ++i) {
            final MTSItemInfo r = rett.get(i);
            if (r != null && (type == 0 || GameConstants.getInventoryType(r.getItem().getItemId()).getType() == type)) {
                ret.add(r);
            }
        }
        return ret;
    }
    
    private List<MTSItemInfo> getCartItems(final MTSCart cart) {
        final List<MTSItemInfo> ret = new ArrayList<MTSItemInfo>();
        final List<Integer> cartt = new ArrayList<Integer>(cart.getCart());
        for (final int i : cartt) {
            final MTSItemInfo r = this.buyNow.get(i);
            if (r == null) {
                cart.removeFromCart(i);
            }
            else {
                if (cart.getType() != 0 && GameConstants.getInventoryType(r.getItem().getItemId()).getType() != cart.getType()) {
                    continue;
                }
                ret.add(r);
            }
        }
        return ret;
    }
    
    public static class MTSItemInfo
    {
        private final int price;
        private final IItem item;
        private final String seller;
        private final int id;
        private final int cid;
        private final long date;
        
        public MTSItemInfo(final int price, final IItem item, final String seller, final int id, final int cid, final long date) {
            this.item = item;
            this.price = price;
            this.seller = seller;
            this.id = id;
            this.cid = cid;
            this.date = date;
        }
        
        public IItem getItem() {
            return this.item;
        }
        
        public int getPrice() {
            return this.price;
        }
        
        public int getRealPrice() {
            return this.price + this.getTaxes();
        }
        
        public int getTaxes() {
            return ServerConstants.MTS_BASE + this.price * ServerConstants.MTS_TAX / 100;
        }
        
        public int getId() {
            return this.id;
        }
        
        public int getCharacterId() {
            return this.cid;
        }
        
        public long getEndingDate() {
            return this.date;
        }
        
        public String getSeller() {
            return this.seller;
        }
    }
}
