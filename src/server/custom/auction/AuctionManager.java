package server.custom.auction;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.Equip;
import client.inventory.IEquip;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import constants.GameConstants;
import constants.OtherSettings;
import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import tools.MaplePacketCreator;

public class AuctionManager
{
    public static AuctionManager getInstance() {
        return InstanceHolder.instance;
    }
    
    private AuctionManager() {
    }
    
    public final void gainItem(final IItem item, final short quantity, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
            if (!MapleInventoryManipulator.checkSpace(cg, item.getItemId(), quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(item.getItemId()) && !GameConstants.isBullet(item.getItemId())) {
                final Equip equip = (Equip)item;
                final String name = ii.getName(item.getItemId());
                if (item.getItemId() / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "你已获得称号 <" + name + ">";
                    cg.getPlayer().dropMessage(5, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, equip.copy());
            }
            else {
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            }
        }
        else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(item.getItemId()), item.getItemId(), -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(item.getItemId(), quantity, true));
    }
    
    public int putInt(final MapleCharacter player, final IItem source, short quantity) {
        final int ret = 1;
        if (player == null || source == null) {
            return -4;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (source.getExpiration() > 0L) {
            return -5;
        }
        final byte flag = source.getFlag();
        if (quantity > source.getQuantity() || quantity < 1) {
            return -6;
        }
        final MapleInventoryType itemtype = this.getItemTypeByItemId(source.getItemId());
        if (ItemFlag.LOCK.check(flag) || ItemFlag.UNTRADEABLE.check(flag) || (quantity != 1 && itemtype == MapleInventoryType.EQUIP)) {
            return -7;
        }
        final OtherSettings item_id = new OtherSettings();
        final String[] itemgy_id = item_id.getItempb_id();
        for (int i = 0; i < itemgy_id.length; ++i) {
            if (source.getItemId() == Integer.parseInt(itemgy_id[i])) {
                return -8;
            }
        }
        final AuctionItem auctionItem = new AuctionItem();
        auctionItem.setAuctionState(AuctionState.下架);
        auctionItem.setCharacterid(player.getId());
        auctionItem.setCharacterName(player.getName());
        auctionItem.setQuantity(quantity);
        auctionItem.setItem(source.copy());
        final int id = this.add(auctionItem);
        if (id < 1) {
            return id;
        }
        auctionItem.setId(id);
        if (GameConstants.isThrowingStar(source.getItemId()) || GameConstants.isBullet(source.getItemId())) {
            quantity = source.getQuantity();
        }
        MapleInventoryManipulator.removeFromSlot(player.getClient(), itemtype, source.getPosition(), quantity, false);
        return ret;
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final long id) {
        final AuctionItem auctionItem = this.findById(id);
        if (auctionItem == null) {
            return -5;
        }
        return this.takeOutAuctionItem(player, id, (short)auctionItem.getQuantity());
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final long id, final short count) {
        final AuctionItem auctionItem = this.findById(id);
        if (auctionItem == null) {
            return -5;
        }
        return this.takeOutAuctionItem(player, auctionItem, count);
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final AuctionItem auctionItem, final short count) {
        if (auctionItem == null) {
            return -5;
        }
        if (auctionItem.getCharacterid() != player.getId()) {
            return -6;
        }
        if (AuctionState.下架 != auctionItem.getAuctionState()) {
            return -7;
        }
        if (count > auctionItem.getQuantity() || count < 1) {
            return -8;
        }
        if (!MapleInventoryManipulator.checkSpace(player.getClient(), auctionItem.getItem().getItemId(), count, "")) {
            return -9;
        }
        int ret = 1;
        if (count < auctionItem.getQuantity() && !GameConstants.isThrowingStar(auctionItem.getItem().getItemId()) && !GameConstants.isBullet(auctionItem.getItem().getItemId())) {
            auctionItem.setQuantity(auctionItem.getQuantity() - count);
            ret = getInstance().update(auctionItem);
        }
        else {
            ret = getInstance().deleteById(auctionItem.getId());
        }
        if (ret > 0) {
            this.gainItem(auctionItem.getItem(), count, player.getClient());
        }
        return ret;
    }
    
    public int buy(final MapleCharacter player, final long id) {
        final AuctionItem auctionItem = this.findById(id);
        if (auctionItem == null) {
            return -5;
        }
        return this.buy(player, auctionItem);
    }
    
    public int buy(final MapleCharacter player, final AuctionItem auctionItem) {
        int ret = -1;
        if (auctionItem == null) {
            return -5;
        }
        if (AuctionState.上架 != auctionItem.getAuctionState()) {
            return -6;
        }
        final AuctionPoint auctionPoint = this.getAuctionPoint(player.getId());
        if (auctionPoint == null) {
            return -7;
        }
        if (auctionPoint.getPoint() < auctionItem.getPrice()) {
            return -8;
        }
        if (!MapleInventoryManipulator.checkSpace(player.getClient(), auctionItem.getItem().getItemId(), auctionItem.getQuantity(), "")) {
            return -9;
        }
        auctionItem.setAuctionState(AuctionState.已售);
        auctionItem.setBuyer(player.getId());
        auctionItem.setBuyerName(player.getName());
        ret = this.update(auctionItem);
        if (ret > 0) {
            final int addpc = this.addPoint(player.getId(), -auctionItem.getPrice());
            if (addpc > 0) {
                final int addp = this.addPoint(auctionItem.getCharacterid(), auctionItem.getPrice());
                if (addp > 0) {
                    if (auctionItem.getCharacterid() != player.getId()) {
                        this.addPointSell(auctionItem.getCharacterid(), auctionItem.getPrice());
                        this.addPointBuy(player.getId(), auctionItem.getPrice());
                    }
                    this.gainItem(auctionItem.getItem(), (short)auctionItem.getQuantity(), player.getClient());
                }
            }
        }
        return ret;
    }
    
    public int setPutaway(final long id, final int price) {
        final AuctionItem auctionItem = this.findById(id);
        if (auctionItem == null) {
            return -5;
        }
        auctionItem.setPrice(price);
        return this.setPutaway(auctionItem);
    }
    
    public int setPutaway(final AuctionItem auctionItem) {
        if (AuctionState.下架 != auctionItem.getAuctionState()) {
            return -6;
        }
        if (auctionItem.getPrice() < 1) {
            return -7;
        }
        auctionItem.setAuctionState(AuctionState.上架);
        return this.update(auctionItem);
    }
    
    public int soldOut(final long id) {
        final AuctionItem auctionItem = this.findById(id);
        if (auctionItem == null) {
            return -5;
        }
        return this.soldOut(auctionItem);
    }
    
    public int soldOut(final AuctionItem auctionItem) {
        if (AuctionState.上架 != auctionItem.getAuctionState()) {
            return -6;
        }
        auctionItem.setAuctionState(AuctionState.下架);
        auctionItem.setPrice(0);
        return this.update(auctionItem);
    }
    
    public AuctionPoint getAuctionPoint(final int characterid) {
        AuctionPoint auctionPoint = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionPoint where characterid = ?");
            ps.setInt(1, characterid);
            rs = ps.executeQuery();
            if (rs.next()) {
                auctionPoint = new AuctionPoint();
                auctionPoint.setCharacterid(rs.getInt("characterid"));
                auctionPoint.setPoint(rs.getInt("point"));
                auctionPoint.setPoint_sell(rs.getInt("point_sell"));
                auctionPoint.setPoint_buy(rs.getInt("point_buy"));
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return null;
            }
        }
        return auctionPoint;
    }
    
    public int addPoint(final int characterid, final long point) {
        return this.addPoint(characterid, point, 1);
    }
    
    public int addPointSell(final int characterid, final long point) {
        return this.addPoint(characterid, point, 2);
    }
    
    public int addPointBuy(final int characterid, final long point) {
        return this.addPoint(characterid, point, 3);
    }
    
    public int addPoint(final int characterid, final long point, final int type) {
        int ret = -1;
        boolean update = false;
        AuctionPoint dbPoint = this.getAuctionPoint(characterid);
        if (dbPoint == null) {
            dbPoint = new AuctionPoint();
            dbPoint.setCharacterid(characterid);
        }
        else {
            update = true;
        }
        switch (type) {
            case 1: {
                dbPoint.setPoint(dbPoint.getPoint() + point);
                break;
            }
            case 2: {
                dbPoint.setPoint_sell(dbPoint.getPoint_sell() + point);
                break;
            }
            case 3: {
                dbPoint.setPoint_buy(dbPoint.getPoint_buy() + point);
                break;
            }
        }
        PreparedStatement ps = null;
        try {
            if (update) {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `auctionPoint` SET point = ? , point_sell = ? , point_buy = ? WHERE characterid = ? ");
                ps.setLong(1, dbPoint.getPoint());
                ps.setLong(2, dbPoint.getPoint_sell());
                ps.setLong(3, dbPoint.getPoint_buy());
                ps.setInt(4, dbPoint.getCharacterid());
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `auctionPoint` VALUES (?, ?, ?, ?)");
                ps.setInt(1, dbPoint.getCharacterid());
                ps.setLong(2, dbPoint.getPoint());
                ps.setLong(3, dbPoint.getPoint_sell());
                ps.setLong(4, dbPoint.getPoint_buy());
            }
            ret = ps.executeUpdate();
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int add(final AuctionItem auctionItem) {
        int ret = -1;
        final MapleInventoryType itemtype = this.getItemTypeByItemId(auctionItem.getItem().getItemId());
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `auctionitems` VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `auctionitems` (characterid,characterName,auctionState,buyer,buyerName,price,itemid,inventorytype,quantity,owner,GM_Log,uniqueid,flag,expiredate,sender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
            }
            this.mapSavePs(ps, auctionItem);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                ret = rs.getInt(1);
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int update(final AuctionItem auctionItem) {
        int ret = -1;
        final MapleInventoryType itemtype = this.getItemTypeByItemId(auctionItem.getItem().getItemId());
        PreparedStatement ps = null;
        final ResultSet rs = null;
        try {
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `auctionitems` SET characterid = ? ,characterName = ? ,auctionState = ?,buyer = ?,buyerName = ? ,price = ?,itemid = ?,inventorytype = ?,quantity = ?,owner = ?,GM_Log = ?,uniqueid = ?,flag = ?,expiredate = ?,sender = ?,upgradeslots = ?,level = ?,str = ?,dex = ?,_int = ?,luk = ?,hp = ?,mp = ?,watk = ?,matk = ?,wdef = ?,mdef = ?,acc = ?,avoid = ?,hands = ?,speed = ?,jump = ?,ViciousHammer = ?,itemEXP = ?,durability = ?,enhance = ?,potential1 = ?,potential2 = ?,potential3 = ?,hpR = ?,mpR = ?,itemlevel = ? where id = ?");
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `auctionitems` SET characterid = ?, characterName = ? ,auctionState = ?,buyer = ?,buyerName = ? ,price = ?,itemid = ?,inventorytype = ?,quantity = ?,owner = ?,GM_Log = ?,uniqueid = ?,flag = ?,expiredate = ?,sender = ? where id = ?");
            }
            this.mapSavePs(ps, auctionItem);
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps.setLong(43, auctionItem.getId());
            }
            else {
                ps.setLong(16, auctionItem.getId());
            }
            ret = ps.executeUpdate();
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int deleteById(final long id) {
        int ret = -1;
        PreparedStatement ps_del = null;
        try {
            ps_del = DatabaseConnection.getConnection().prepareStatement("DELETE  FROM auctionItems where id = ?");
            ps_del.setLong(1, id);
            ret = ps_del.executeUpdate();
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int deletePlayerSold(final int characterid) {
        int ret = -1;
        PreparedStatement ps_del = null;
        try {
            ps_del = DatabaseConnection.getConnection().prepareStatement("DELETE  FROM auctionItems where characterid = ? and auctionState = ?");
            ps_del.setInt(1, characterid);
            ps_del.setInt(2, AuctionState.已售.getState());
            ret = ps_del.executeUpdate();
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public AuctionItem findById(final long id) {
        AuctionItem auctionItem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                auctionItem = this.mapLoadRs(rs);
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return auctionItem;
    }
    
    public List<AuctionItem> findByCharacterId(final int characterid) {
        final List<AuctionItem> ret = new ArrayList<AuctionItem>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where characterid = ?");
            ps.setInt(1, characterid);
            rs = ps.executeQuery();
            while (rs.next()) {
                final AuctionItem auctionItem = this.mapLoadRs(rs);
                if (auctionItem != null) {
                    ret.add(auctionItem);
                }
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return ret;
    }
    
    public List<AuctionItem> findByItemType(final int inventorytype) {
        final List<AuctionItem> ret = new ArrayList<AuctionItem>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where inventorytype = ? and auctionState = 1 order by itemid desc");
            ps.setInt(1, inventorytype);
            rs = ps.executeQuery();
            while (rs.next()) {
                final AuctionItem auctionItem = this.mapLoadRs(rs);
                if (auctionItem != null) {
                    ret.add(auctionItem);
                }
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return ret;
    }
    
    public MapleInventoryType getItemTypeByItemId(final int itemid) {
        return MapleInventoryType.getByType((byte)(itemid / 1000000));
    }
    
    public void mapSavePs(final PreparedStatement ps, final AuctionItem auctionItem) throws SQLException {
        final MapleInventoryType itemtype = this.getItemTypeByItemId(auctionItem.getItem().getItemId());
        final IItem item = auctionItem.getItem();
        ps.setInt(1, auctionItem.getCharacterid());
        ps.setString(2, auctionItem.getCharacterName());
        ps.setInt(3, auctionItem.getAuctionState().getState());
        ps.setInt(4, auctionItem.getBuyer());
        ps.setString(5, auctionItem.getBuyerName());
        ps.setInt(6, auctionItem.getPrice());
        ps.setInt(7, item.getItemId());
        ps.setInt(8, itemtype.getType());
        ps.setInt(9, auctionItem.getQuantity());
        ps.setString(10, item.getOwner());
        ps.setString(11, item.getGMLog());
        ps.setInt(12, item.getUniqueId());
        ps.setByte(13, item.getFlag());
        ps.setLong(14, item.getExpiration());
        ps.setString(15, item.getGiftFrom());
        if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
            final IEquip equip = (IEquip)item;
            ps.setInt(16, equip.getUpgradeSlots());
            ps.setInt(17, equip.getLevel());
            ps.setInt(18, equip.getStr());
            ps.setInt(19, equip.getDex());
            ps.setInt(20, equip.getInt());
            ps.setInt(21, equip.getLuk());
            ps.setInt(22, equip.getHp());
            ps.setInt(23, equip.getMp());
            ps.setInt(24, equip.getWatk());
            ps.setInt(25, equip.getMatk());
            ps.setInt(26, equip.getWdef());
            ps.setInt(27, equip.getMdef());
            ps.setInt(28, equip.getAcc());
            ps.setInt(29, equip.getAvoid());
            ps.setInt(30, equip.getHands());
            ps.setInt(31, equip.getSpeed());
            ps.setInt(32, equip.getJump());
            ps.setInt(33, equip.getViciousHammer());
            ps.setInt(34, equip.getItemEXP());
            ps.setInt(35, equip.getDurability());
            ps.setByte(36, equip.getEnhance());
            ps.setInt(37, equip.getPotential1());
            ps.setInt(38, equip.getPotential2());
            ps.setInt(39, equip.getPotential3());
            ps.setInt(40, equip.getHpR());
            ps.setInt(41, equip.getMpR());
            ps.setByte(42, equip.getEquipLevel());
        }
    }
    
    public AuctionItem mapLoadRs(final ResultSet rs) throws SQLException {
        final long id = rs.getLong("id");
        final int characterid = rs.getInt("characterid");
        final String characterName = rs.getString("characterName");
        final Byte auctionState = rs.getByte("auctionState");
        final int buyer = rs.getInt("buyer");
        final String buyerName = rs.getString("buyerName");
        final int price = rs.getInt("price");
        final int itemid = rs.getInt("itemid");
        final int inventorytype = rs.getInt("inventorytype");
        final short quantity = rs.getShort("quantity");
        final String owner = rs.getString("owner");
        final String GM_Log = rs.getString("GM_Log");
        final int uniqueid = rs.getInt("uniqueid");
        final byte flag = rs.getByte("flag");
        final long expiredate = rs.getLong("expiredate");
        final String sender = rs.getString("sender");
        final byte upgradeslots = rs.getByte("upgradeslots");
        final byte level = rs.getByte("level");
        final short str = rs.getShort("str");
        final short dex = rs.getShort("dex");
        final short _int = rs.getShort("_int");
        final short luk = rs.getShort("luk");
        final short hp = rs.getShort("hp");
        final short mp = rs.getShort("mp");
        final short watk = rs.getShort("watk");
        final short matk = rs.getShort("matk");
        final short wdef = rs.getShort("wdef");
        final short mdef = rs.getShort("mdef");
        final short acc = rs.getShort("acc");
        final short avoid = rs.getShort("avoid");
        final short hands = rs.getShort("hands");
        final short speed = rs.getShort("speed");
        final short jump = rs.getShort("jump");
        final byte ViciousHammer = rs.getByte("ViciousHammer");
        final int itemEXP = rs.getInt("itemEXP");
        final int durability = rs.getInt("durability");
        final byte enhance = rs.getByte("enhance");
        final short potential1 = rs.getShort("potential1");
        final short potential2 = rs.getShort("potential2");
        final short potential3 = rs.getShort("potential3");
        final short hpR = rs.getShort("hpR");
        final short mpR = rs.getShort("mpR");
        final byte itemlevel = rs.getByte("itemlevel");
        final MapleInventoryType mit = MapleInventoryType.getByType((byte)inventorytype);
        if (characterid < 1 || mit == null) {
            return null;
        }
        final AuctionItem auctionItem = new AuctionItem();
        auctionItem.setId(id);
        auctionItem.setAuctionState(AuctionState.getState(auctionState));
        auctionItem.setPrice(price);
        auctionItem.setBuyer(buyer);
        auctionItem.setBuyerName(buyerName);
        auctionItem.setCharacterid(characterid);
        auctionItem.setCharacterName(characterName);
        auctionItem.setQuantity(quantity);
        if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
            final Equip equip = new Equip(itemid, (short)0, uniqueid, flag);
            equip.setQuantity((short)1);
            equip.setOwner(owner);
            equip.setExpiration(expiredate);
            equip.setUpgradeSlots(upgradeslots);
            equip.setLevel(level);
            equip.setStr(str);
            equip.setDex(dex);
            equip.setInt(_int);
            equip.setLuk(luk);
            equip.setHp(hp);
            equip.setMp(mp);
            equip.setWatk(watk);
            equip.setMatk(matk);
            equip.setWdef(wdef);
            equip.setMdef(mdef);
            equip.setAcc(acc);
            equip.setAvoid(avoid);
            equip.setHands(hands);
            equip.setSpeed(speed);
            equip.setJump(jump);
            equip.setViciousHammer(ViciousHammer);
            equip.setItemEXP(itemEXP);
            equip.setGMLog(GM_Log);
            equip.setDurability(durability);
            equip.setEnhance(enhance);
            equip.setPotential1(potential1);
            equip.setPotential2(potential2);
            equip.setPotential3(potential3);
            equip.setHpR(hpR);
            equip.setMpR(mpR);
            equip.setGiftFrom(sender);
            equip.setEquipLevel(itemlevel);
            if (equip.getUniqueId() > -1 && GameConstants.isEffectRing(itemid)) {
                final MapleRing ring = MapleRing.loadFromDb(equip.getUniqueId(), mit.equals(MapleInventoryType.EQUIPPED));
                if (ring != null) {
                    equip.setRing(ring);
                }
            }
            auctionItem.setItem(equip.copy());
        }
        else {
            final Item item = new Item(itemid, (short)0, quantity, flag);
            item.setUniqueId(uniqueid);
            item.setOwner(owner);
            item.setExpiration(expiredate);
            item.setGMLog(GM_Log);
            item.setGiftFrom(sender);
            if (GameConstants.isPet(item.getItemId())) {
                if (item.getUniqueId() > -1) {
                    final MaplePet pet = MaplePet.loadFromDb(item.getItemId(), item.getUniqueId(), item.getPosition());
                    if (pet != null) {
                        item.setPet(pet);
                    }
                }
                else {
                    final int new_unique = MapleInventoryIdentifier.getInstance();
                    item.setUniqueId(new_unique);
                    item.setPet(MaplePet.createPet(item.getItemId(), new_unique));
                }
            }
            auctionItem.setItem(item.copy());
        }
        return auctionItem;
    }
    
    private static class InstanceHolder
    {
        public static final AuctionManager instance;
        
        static {
            instance = new AuctionManager();
        }
    }
}
