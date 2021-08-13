package server;

import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import database.DatabaseException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tools.MaplePacketCreator;
import tools.Pair;

public class MapleStorage implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private final int id;
    private final int accountId;
    private final List<IItem> items;
    private int meso;
    private byte slots;
    private boolean changed;
    private final Map<MapleInventoryType, List<IItem>> typeItems;
    
    public static int create(final int id) throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("INSERT INTO storages (accountid, slots, meso) VALUES (?, ?, ?)", 1);
        ps.setInt(1, id);
        ps.setInt(2, 4);
        ps.setInt(3, 0);
        ps.executeUpdate();
        final ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            final int storageid = rs.getInt(1);
            ps.close();
            rs.close();
            return storageid;
        }
        ps.close();
        rs.close();
        throw new DatabaseException("Inserting char failed.");
    }
    
    public static MapleStorage loadStorage(final int id) {
        MapleStorage ret = null;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM storages WHERE accountid = ?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int storeId = rs.getInt("storageid");
                ret = new MapleStorage(storeId, rs.getByte("slots"), rs.getInt("meso"), id);
                rs.close();
                ps.close();
                for (final Pair<IItem, MapleInventoryType> mit : ItemLoader.STORAGE.loadItems(false, id).values()) {
                    ret.items.add(mit.getLeft());
                }
            }
            else {
                final int storeId = create(id);
                ret = new MapleStorage(storeId, (byte)4, 0, id);
                rs.close();
                ps.close();
            }
        }
        catch (SQLException ex) {
            System.err.println("Error loading storage" + ex);
        }
        return ret;
    }
    
    private MapleStorage(final int id, final byte slots, final int meso, final int accountId) {
        this.changed = false;
        this.typeItems = new EnumMap<MapleInventoryType, List<IItem>>(MapleInventoryType.class);
        this.id = id;
        this.slots = slots;
        this.items = new LinkedList<IItem>();
        this.meso = meso;
        this.accountId = accountId;
    }
    
    public void saveToDB() {
        if (!this.changed) {
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE storages SET slots = ?, meso = ? WHERE storageid = ?");
            ps.setInt(1, this.slots);
            ps.setInt(2, this.meso);
            ps.setInt(3, this.id);
            ps.executeUpdate();
            ps.close();
            final List<Pair<IItem, MapleInventoryType>> listing = new ArrayList<Pair<IItem, MapleInventoryType>>();
            for (final IItem item : this.items) {
                listing.add(new Pair<IItem, MapleInventoryType>(item, GameConstants.getInventoryType(item.getItemId())));
            }
            ItemLoader.STORAGE.saveItems(listing, this.accountId);
        }
        catch (SQLException ex) {
            System.err.println("Error saving storage" + ex);
        }
    }
    
    public IItem takeOut(final byte slot) {
        if (slot >= this.items.size() || slot < 0) {
            return null;
        }
        this.changed = true;
        final IItem ret = this.items.remove(slot);
        final MapleInventoryType type = GameConstants.getInventoryType(ret.getItemId());
        this.typeItems.put(type, new ArrayList<IItem>(this.filterItems(type)));
        return ret;
    }
    
    public void store(final IItem item) {
        this.changed = true;
        this.items.add(item);
        final MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        this.typeItems.put(type, new ArrayList<IItem>(this.filterItems(type)));
    }
    
    public List<IItem> getItems() {
        return Collections.unmodifiableList((List<? extends IItem>)this.items);
    }
    
    private List<IItem> filterItems(final MapleInventoryType type) {
        final List<IItem> ret = new LinkedList<IItem>();
        for (final IItem item : this.items) {
            if (GameConstants.getInventoryType(item.getItemId()) == type) {
                ret.add(item);
            }
        }
        return ret;
    }
    
    public byte getSlot(final MapleInventoryType type, final byte slot) {
        byte ret = 0;
        final List<IItem> it = this.typeItems.get(type);
        if (slot >= it.size() || slot < 0) {
            return -1;
        }
        for (final IItem item : this.items) {
            if (item == it.get(slot)) {
                return ret;
            }
            ++ret;
        }
        return -1;
    }
    
    public void sendStorage(final MapleClient c, final int npcId) {
        Collections.sort(this.items, new Comparator<IItem>() {
            @Override
            public int compare(final IItem o1, final IItem o2) {
                if (GameConstants.getInventoryType(o1.getItemId()).getType() < GameConstants.getInventoryType(o2.getItemId()).getType()) {
                    return -1;
                }
                if (GameConstants.getInventoryType(o1.getItemId()) == GameConstants.getInventoryType(o2.getItemId())) {
                    return 0;
                }
                return 1;
            }
        });
        for (final MapleInventoryType type : MapleInventoryType.values()) {
            this.typeItems.put(type, new ArrayList<IItem>(this.items));
        }
        c.getSession().write((Object)MaplePacketCreator.getStorage(npcId, this.slots, this.items, this.meso));
    }
    
    public void sendStored(final MapleClient c, final MapleInventoryType type) {
        c.getSession().write((Object)MaplePacketCreator.storeStorage(this.slots, type, this.typeItems.get(type)));
    }
    
    public void sendTakenOut(final MapleClient c, final MapleInventoryType type) {
        c.getSession().write((Object)MaplePacketCreator.takeOutStorage(this.slots, type, this.typeItems.get(type)));
    }
    
    public int getMeso() {
        return this.meso;
    }
    
    public IItem findById(final int itemId) {
        for (final IItem item : this.items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }
    
    public void setMeso(final int meso) {
        if (meso < 0) {
            return;
        }
        this.changed = true;
        this.meso = meso;
    }
    
    public void sendMeso(final MapleClient c) {
        c.getSession().write((Object)MaplePacketCreator.mesoStorage(this.slots, this.meso));
    }
    
    public boolean isFull() {
        return this.items.size() >= this.slots;
    }
    
    public int getSlots() {
        return this.slots;
    }
    
    public void increaseSlots(final byte gain) {
        this.changed = true;
        this.slots += gain;
    }
    
    public void setSlots(final byte set) {
        this.changed = true;
        this.slots = set;
    }
    
    public void close() {
        this.typeItems.clear();
    }
    
    public List<IItem> listByEquipOnlyId(final int equipOnlyId) {
        final List<IItem> ret = new ArrayList<IItem>();
        for (final IItem item : this.items) {
            if (item.getEquipOnlyId() > 0 && item.getEquipOnlyId() == equipOnlyId) {
                ret.add(item);
            }
        }
        if (ret.size() > 1) {
            Collections.sort(ret);
        }
        return ret;
    }
}
