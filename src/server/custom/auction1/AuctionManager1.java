package server.custom.auction1;

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

public class AuctionManager1
{
    public static AuctionManager1 getInstance() {
        return InstanceHolder1.instance;
    }
    
    private AuctionManager1() {
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
        if (ItemFlag.LOCK.check(flag) || (quantity != 1 && itemtype == MapleInventoryType.EQUIP)) {
            return -7;
        }
        final OtherSettings item_id = new OtherSettings();
        final String[] itemgy_id = item_id.getItempb_id();
        for (int i = 0; i < itemgy_id.length; ++i) {
            if (source.getItemId() == Integer.parseInt(itemgy_id[i])) {
                return -8;
            }
        }
        final AuctionItem1 AuctionItem1 = new AuctionItem1();
        AuctionItem1.setAuctionState(AuctionState1.下架);
        AuctionItem1.setCharacterid(player.getId());
        AuctionItem1.setCharacterName(player.getName());
        AuctionItem1.setQuantity(quantity);
        AuctionItem1.setItem(source.copy());
        final int id = this.add(AuctionItem1);
        if (id < 1) {
            return id;
        }
        AuctionItem1.setId(id);
        if (GameConstants.isThrowingStar(source.getItemId()) || GameConstants.isBullet(source.getItemId())) {
            quantity = source.getQuantity();
        }
        MapleInventoryManipulator.removeFromSlot(player.getClient(), itemtype, source.getPosition(), quantity, false);
        return ret;
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final long id) {
        final AuctionItem1 AuctionItem1 = this.findById(id);
        if (AuctionItem1 == null) {
            return -5;
        }
        return this.takeOutAuctionItem(player, id, (short)AuctionItem1.getQuantity());
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final long id, final short count) {
        final AuctionItem1 AuctionItem1 = this.findById(id);
        if (AuctionItem1 == null) {
            return -5;
        }
        return this.takeOutAuctionItem(player, AuctionItem1, count);
    }
    
    public int takeOutAuctionItem(final MapleCharacter player, final AuctionItem1 AuctionItem1, final short count) {
        if (AuctionItem1 == null) {
            return -5;
        }
        if (AuctionItem1.getCharacterid() != player.getId()) {
            return -6;
        }
        if (AuctionState1.下架 != AuctionItem1.getAuctionState()) {
            return -7;
        }
        if (count > AuctionItem1.getQuantity() || count < 1) {
            return -8;
        }
        if (!MapleInventoryManipulator.checkSpace(player.getClient(), AuctionItem1.getItem().getItemId(), count, "")) {
            return -9;
        }
        int ret = 1;
        if (count < AuctionItem1.getQuantity() && !GameConstants.isThrowingStar(AuctionItem1.getItem().getItemId()) && !GameConstants.isBullet(AuctionItem1.getItem().getItemId())) {
            AuctionItem1.setQuantity(AuctionItem1.getQuantity() - count);
            ret = getInstance().update(AuctionItem1);
        }
        else {
            ret = getInstance().deleteById(AuctionItem1.getId());
        }
        if (ret > 0) {
            this.gainItem(AuctionItem1.getItem(), count, player.getClient());
        }
        return ret;
    }
    
    public int buy(final MapleCharacter player, final long id) {
        final AuctionItem1 AuctionItem1 = this.findById(id);
        if (AuctionItem1 == null) {
            return -5;
        }
        return this.buy(player, AuctionItem1);
    }
    
    public int buy(final MapleCharacter player, final AuctionItem1 AuctionItem1) {
        int ret = -1;
        if (AuctionItem1 == null) {
            return -5;
        }
        if (AuctionState1.上架 != AuctionItem1.getAuctionState()) {
            return -6;
        }
        final AuctionPoint1 AuctionPoint1 = this.getAuctionPoint(player.getId());
        if (AuctionPoint1 == null) {
            return -7;
        }
        if (AuctionPoint1.getPoint1() < AuctionItem1.getPrice()) {
            return -8;
        }
        if (!MapleInventoryManipulator.checkSpace(player.getClient(), AuctionItem1.getItem().getItemId(), AuctionItem1.getQuantity(), "")) {
            return -9;
        }
        AuctionItem1.setAuctionState(AuctionState1.已售);
        AuctionItem1.setBuyer(player.getId());
        AuctionItem1.setBuyerName(player.getName());
        ret = this.update(AuctionItem1);
        if (ret > 0) {
            final int addpc = this.addPoint(player.getId(), -AuctionItem1.getPrice());
            if (addpc > 0) {
                final int addp = this.addPoint(AuctionItem1.getCharacterid(), AuctionItem1.getPrice());
                if (addp > 0) {
                    if (AuctionItem1.getCharacterid() != player.getId()) {
                        this.addPointSell(AuctionItem1.getCharacterid(), AuctionItem1.getPrice());
                        this.addPointBuy(player.getId(), AuctionItem1.getPrice());
                    }
                    this.gainItem(AuctionItem1.getItem(), (short)AuctionItem1.getQuantity(), player.getClient());
                }
            }
        }
        return ret;
    }
    
    public int setPutaway(final long id, final int price) {
        final AuctionItem1 AuctionItem1 = this.findById(id);
        if (AuctionItem1 == null) {
            return -5;
        }
        AuctionItem1.setPrice(price);
        return this.setPutaway(AuctionItem1);
    }
    
    public int setPutaway(final AuctionItem1 AuctionItem1) {
        if (AuctionState1.下架 != AuctionItem1.getAuctionState()) {
            return -6;
        }
        if (AuctionItem1.getPrice() < 1) {
            return -7;
        }
        AuctionItem1.setAuctionState(AuctionState1.上架);
        return this.update(AuctionItem1);
    }
    
    public int soldOut(final long id) {
        final AuctionItem1 AuctionItem1 = this.findById(id);
        if (AuctionItem1 == null) {
            return -5;
        }
        return this.soldOut(AuctionItem1);
    }
    
    public int soldOut(final AuctionItem1 AuctionItem1) {
        if (AuctionState1.上架 != AuctionItem1.getAuctionState()) {
            return -6;
        }
        AuctionItem1.setAuctionState(AuctionState1.下架);
        AuctionItem1.setPrice(0);
        return this.update(AuctionItem1);
    }
    
    public AuctionPoint1 getAuctionPoint(final int characterid) {
        AuctionPoint1 AuctionPoint1 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from AuctionPoint1 where characterid = ?");
            ps.setInt(1, characterid);
            rs = ps.executeQuery();
            if (rs.next()) {
                AuctionPoint1 = new AuctionPoint1();
                AuctionPoint1.setCharacterid(rs.getInt("characterid"));
                AuctionPoint1.setPoint(rs.getInt("point"));
                AuctionPoint1.setPoint1_sell(rs.getInt("point_sell"));
                AuctionPoint1.setPoint1_buy(rs.getInt("point_buy"));
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
                return null;
            }
        }
        return AuctionPoint1;
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
        AuctionPoint1 dbPoint = this.getAuctionPoint(characterid);
        if (dbPoint == null) {
            dbPoint = new AuctionPoint1();
            dbPoint.setCharacterid(characterid);
        }
        else {
            update = true;
        }
        switch (type) {
            case 1: {
                dbPoint.setPoint(dbPoint.getPoint1() + point);
                break;
            }
            case 2: {
                dbPoint.setPoint1_sell(dbPoint.getPoint1_sell() + point);
                break;
            }
            case 3: {
                dbPoint.setPoint1_buy(dbPoint.getPoint1_buy() + point);
                break;
            }
        }
        PreparedStatement ps = null;
        try {
            if (update) {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `AuctionPoint1` SET point = ? , point_sell = ? , point_buy = ? WHERE characterid = ? ");
                ps.setLong(1, dbPoint.getPoint1());
                ps.setLong(2, dbPoint.getPoint1_sell());
                ps.setLong(3, dbPoint.getPoint1_buy());
                ps.setInt(4, dbPoint.getCharacterid());
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `AuctionPoint1` VALUES (?, ?, ?, ?)");
                ps.setInt(1, dbPoint.getCharacterid());
                ps.setLong(2, dbPoint.getPoint1());
                ps.setLong(3, dbPoint.getPoint1_sell());
                ps.setLong(4, dbPoint.getPoint1_buy());
            }
            ret = ps.executeUpdate();
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int add(final AuctionItem1 AuctionItem1) {
        int ret = -1;
        final MapleInventoryType itemtype = this.getItemTypeByItemId(AuctionItem1.getItem().getItemId());
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `auctionitems` VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `auctionitems` (characterid,characterName,AuctionState1,buyer,buyerName,price,itemid,inventorytype,quantity,owner,GM_Log,uniqueid,flag,expiredate,sender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
            }
            this.mapSavePs(ps, AuctionItem1);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int update(final AuctionItem1 AuctionItem1) {
        int ret = -1;
        final MapleInventoryType itemtype = this.getItemTypeByItemId(AuctionItem1.getItem().getItemId());
        PreparedStatement ps = null;
        final ResultSet rs = null;
        try {
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `auctionitems` SET characterid = ? ,characterName = ? ,AuctionState1 = ?,buyer = ?,buyerName = ? ,price = ?,itemid = ?,inventorytype = ?,quantity = ?,owner = ?,GM_Log = ?,uniqueid = ?,flag = ?,expiredate = ?,sender = ?,upgradeslots = ?,level = ?,str = ?,dex = ?,_int = ?,luk = ?,hp = ?,mp = ?,watk = ?,matk = ?,wdef = ?,mdef = ?,acc = ?,avoid = ?,hands = ?,speed = ?,jump = ?,ViciousHammer = ?,itemEXP = ?,durability = ?,enhance = ?,potential1 = ?,potential2 = ?,potential3 = ?,hpR = ?,mpR = ?,itemlevel = ? where id = ?");
            }
            else {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE `auctionitems` SET characterid = ?, characterName = ? ,AuctionState1 = ?,buyer = ?,buyerName = ? ,price = ?,itemid = ?,inventorytype = ?,quantity = ?,owner = ?,GM_Log = ?,uniqueid = ?,flag = ?,expiredate = ?,sender = ? where id = ?");
            }
            this.mapSavePs(ps, AuctionItem1);
            if (itemtype.equals(MapleInventoryType.EQUIP) || itemtype.equals(MapleInventoryType.EQUIPPED)) {
                ps.setLong(43, AuctionItem1.getId());
            }
            else {
                ps.setLong(16, AuctionItem1.getId());
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public int deletePlayerSold(final int characterid) {
        int ret = -1;
        PreparedStatement ps_del = null;
        try {
            ps_del = DatabaseConnection.getConnection().prepareStatement("DELETE  FROM auctionItems where characterid = ? and AuctionState1 = ?");
            ps_del.setInt(1, characterid);
            ps_del.setInt(2, AuctionState1.已售.getState1());
            ret = ps_del.executeUpdate();
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
                return -2;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
        finally {
            try {
                if (ps_del != null) {
                    ps_del.close();
                }
            }
            catch (SQLException ex2) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
                return -2;
            }
        }
        return ret;
    }
    
    public AuctionItem1 findById(final long id) {
        AuctionItem1 AuctionItem1 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                AuctionItem1 = this.mapLoadRs(rs);
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return AuctionItem1;
    }
    
    public List<AuctionItem1> findByCharacterId(final int characterid) {
        final List<AuctionItem1> ret = new ArrayList<AuctionItem1>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where characterid = ?");
            ps.setInt(1, characterid);
            rs = ps.executeQuery();
            while (rs.next()) {
                final AuctionItem1 AuctionItem1 = this.mapLoadRs(rs);
                if (AuctionItem1 != null) {
                    ret.add(AuctionItem1);
                }
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return ret;
    }
    
    public List<AuctionItem1> findByItemType(final int inventorytype) {
        final List<AuctionItem1> ret = new ArrayList<AuctionItem1>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("select * from auctionitems where inventorytype = ? and AuctionState1 = 1 order by itemid desc");
            ps.setInt(1, inventorytype);
            rs = ps.executeQuery();
            while (rs.next()) {
                final AuctionItem1 AuctionItem1 = this.mapLoadRs(rs);
                if (AuctionItem1 != null) {
                    ret.add(AuctionItem1);
                }
            }
        }
        catch (SQLException e1) {
            Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, e1);
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AuctionManager1.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return ret;
    }
    
    public MapleInventoryType getItemTypeByItemId(final int itemid) {
        return MapleInventoryType.getByType((byte)(itemid / 1000000));
    }
    
    public void mapSavePs(final PreparedStatement ps, final AuctionItem1 auctionItem1) throws SQLException {
        final MapleInventoryType itemtype = this.getItemTypeByItemId(auctionItem1.getItem().getItemId());
        final IItem item = auctionItem1.getItem();
        ps.setInt(1, auctionItem1.getCharacterid());
        ps.setString(2, auctionItem1.getCharacterName());
        ps.setInt(3, auctionItem1.getAuctionState().getState1());
        ps.setInt(4, auctionItem1.getBuyer());
        ps.setString(5, auctionItem1.getBuyerName());
        ps.setInt(6, auctionItem1.getPrice());
        ps.setInt(7, item.getItemId());
        ps.setInt(8, itemtype.getType());
        ps.setInt(9, auctionItem1.getQuantity());
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
    
    public AuctionItem1 mapLoadRs(final ResultSet rs) throws SQLException {
        final long id = rs.getLong("id");
        final int characterid = rs.getInt("characterid");
        final String characterName = rs.getString("characterName");
        final Byte AuctionState1 = rs.getByte("AuctionState1");
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
        final AuctionItem1 auctionItem1 = new AuctionItem1();
        auctionItem1.setPrice(price);
        auctionItem1.setBuyer(buyer);
        auctionItem1.setBuyerName(buyerName);
        auctionItem1.setCharacterid(characterid);
        auctionItem1.setCharacterName(characterName);
        auctionItem1.setQuantity(quantity);
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
            auctionItem1.setItem(equip.copy());
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
            auctionItem1.setItem(item.copy());
        }
        return auctionItem1;
    }
    
    private static class InstanceHolder1
    {
        public static final AuctionManager1 instance;
        
        static {
            instance = new AuctionManager1();
        }
    }
}
