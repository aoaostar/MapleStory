package client.inventory;

import constants.GameConstants;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tools.Pair;

public enum ItemLoader {
    装备道具("inventoryitems", "inventoryequipment", 0, new String[] { "characterid" }),
    STORAGE("inventoryitems", "inventoryequipment", 1, new String[] { "accountid" }),
    CASHSHOP_EXPLORER("csitems", "csequipment", 2, new String[] { "accountid" }),
    CASHSHOP_CYGNUS("csitems", "csequipment", 3, new String[] { "accountid" }),
    CASHSHOP_ARAN("csitems", "csequipment", 4, new String[] { "accountid" }),
    HIRED_MERCHANT("hiredmerchitems", "hiredmerchequipment", 5, new String[] { "packageid", "accountid", "characterid" }),
    DUEY("dueyitems", "dueyequipment", 6, new String[] { "packageid" }),
    CASHSHOP_EVAN("csitems", "csequipment", 7, new String[] { "accountid" }),
    MTS("mtsitems", "mtsequipment", 8, new String[] { "packageid" }),
    MTS_TRANSFER("mtstransfer", "mtstransferequipment", 9, new String[] { "characterid" }),
    CASHSHOP_DB("csitems", "csequipment", 10, new String[] { "accountid" }),
    CASHSHOP_RESIST("csitems", "csequipment", 11, new String[] { "accountid" });

    private final int value;

    private final String table;

    private final String table_equip;

    private final List<String> arg;

    ItemLoader(String table, String table_equip, int value, String... arg) {
        this.table = table;
        this.table_equip = table_equip;
        this.value = value;
        this.arg = Arrays.asList(arg);
    }

    public int getValue() {
        return this.value;
    }

    public Map<Integer, Pair<IItem, MapleInventoryType>> loadItems_hm(int packageid, int accountid) throws SQLException {
        Map<Integer, Pair<IItem, MapleInventoryType>> items = new LinkedHashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `hiredmerchitems` LEFT JOIN `hiredmerchequipment` USING(`inventoryitemid`) WHERE `type` = ? AND `accountid` = ? ");
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query.toString());
        ps.setInt(1, this.value);
        ps.setInt(2, accountid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MapleInventoryType mit = MapleInventoryType.getByType(rs.getByte("inventorytype"));
            if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                Equip equip = new Equip(rs.getInt("itemid"), rs.getShort("position"), rs.getInt("uniqueid"), rs.getByte("flag"));
                equip.setQuantity((short)1);
                equip.setOwner(rs.getString("owner"));
                equip.setExpiration(rs.getLong("expiredate"));
                equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                equip.setLevel(rs.getByte("level"));
                equip.setStr(rs.getShort("str"));
                equip.setDex(rs.getShort("dex"));
                equip.setInt(rs.getShort("int"));
                equip.setLuk(rs.getShort("luk"));
                equip.setHp(rs.getShort("hp"));
                equip.setMp(rs.getShort("mp"));
                equip.setWatk(rs.getShort("watk"));
                equip.setMatk(rs.getShort("matk"));
                equip.setWdef(rs.getShort("wdef"));
                equip.setMdef(rs.getShort("mdef"));
                equip.setAcc(rs.getShort("acc"));
                equip.setAvoid(rs.getShort("avoid"));
                equip.setHands(rs.getShort("hands"));
                equip.setSpeed(rs.getShort("speed"));
                equip.setJump(rs.getShort("jump"));
                equip.setViciousHammer(rs.getByte("ViciousHammer"));
                equip.setItemEXP(rs.getInt("itemEXP"));
                equip.setGMLog(rs.getString("GM_Log"));
                equip.setDurability(rs.getInt("durability"));
                equip.setEnhance(rs.getByte("enhance"));
                equip.setPotential1(rs.getShort("potential1"));
                equip.setPotential2(rs.getShort("potential2"));
                equip.setPotential3(rs.getShort("potential3"));
                equip.setHpR(rs.getShort("hpR"));
                equip.setMpR(rs.getShort("mpR"));
                equip.setGiftFrom(rs.getString("sender"));
                equip.setEquipLevel(rs.getByte("itemlevel"));
                equip.setEquipOnlyId(rs.getInt("equipOnlyId"));
                if (equip.getUniqueId() > -1 &&
                        GameConstants.isEffectRing(rs.getInt("itemid"))) {
                    MapleRing ring = MapleRing.loadFromDb(equip.getUniqueId(), mit.equals(MapleInventoryType.EQUIPPED));
                    if (ring != null)
                        equip.setRing(ring);
                }
                items.put(Integer.valueOf(rs.getInt("inventoryitemid")), new Pair(equip.copy(), mit));
                continue;
            }
            Item item = new Item(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("quantity"), rs.getByte("flag"));
            item.setUniqueId(rs.getInt("uniqueid"));
            item.setOwner(rs.getString("owner"));
            item.setExpiration(rs.getLong("expiredate"));
            item.setEquipOnlyId(rs.getInt("equipOnlyId"));
            item.setGMLog(rs.getString("GM_Log"));
            item.setGiftFrom(rs.getString("sender"));
            if (GameConstants.isPet(item.getItemId()))
                if (item.getUniqueId() > -1) {
                    MaplePet pet = MaplePet.loadFromDb(item.getItemId(), item.getUniqueId(), item.getPosition());
                    if (pet != null)
                        item.setPet(pet);
                } else {
                    int new_unique = MapleInventoryIdentifier.getInstance();
                    item.setUniqueId(new_unique);
                    item.setPet(MaplePet.createPet(item.getItemId(), new_unique));
                }
            items.put(Integer.valueOf(rs.getInt("inventoryitemid")), new Pair(item.copy(), mit));
        }
        rs.close();
        ps.close();
        return items;
    }

    public Map<Integer, Pair<IItem, MapleInventoryType>> loadItems(boolean login, Integer... id) throws SQLException {
        List<Integer> lulz = Arrays.asList(id);
        Map<Integer, Pair<IItem, MapleInventoryType>> items = new LinkedHashMap<>();
        if (lulz.size() != this.arg.size())
            return items;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `");
        query.append(this.table);
        query.append("` LEFT JOIN `");
        query.append(this.table_equip);
        query.append("` USING(`inventoryitemid`) WHERE `type` = ?");
        for (String g : this.arg) {
            query.append(" AND `");
            query.append(g);
            query.append("` = ?");
        }
        if (login) {
            query.append(" AND `inventorytype` = ");
            query.append(MapleInventoryType.EQUIPPED.getType());
        }
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query.toString());
        ps.setInt(1, this.value);
        for (int i = 0; i < lulz.size(); i++)
            ps.setInt(i + 2, ((Integer)lulz.get(i)).intValue());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MapleInventoryType mit = MapleInventoryType.getByType(rs.getByte("inventorytype"));
            if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                Equip equip = new Equip(rs.getInt("itemid"), rs.getShort("position"), rs.getInt("uniqueid"), rs.getByte("flag"));
                if (!login) {
                    equip.setQuantity((short)1);
                    equip.setOwner(rs.getString("owner"));
                    equip.setExpiration(rs.getLong("expiredate"));
                    equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                    equip.setLevel(rs.getByte("level"));
                    equip.setStr(rs.getShort("str"));
                    equip.setDex(rs.getShort("dex"));
                    equip.setInt(rs.getShort("int"));
                    equip.setLuk(rs.getShort("luk"));
                    equip.setHp(rs.getShort("hp"));
                    equip.setMp(rs.getShort("mp"));
                    equip.setWatk(rs.getShort("watk"));
                    equip.setMatk(rs.getShort("matk"));
                    equip.setWdef(rs.getShort("wdef"));
                    equip.setMdef(rs.getShort("mdef"));
                    equip.setAcc(rs.getShort("acc"));
                    equip.setAvoid(rs.getShort("avoid"));
                    equip.setHands(rs.getShort("hands"));
                    equip.setSpeed(rs.getShort("speed"));
                    equip.setJump(rs.getShort("jump"));
                    equip.setViciousHammer(rs.getByte("ViciousHammer"));
                    equip.setItemEXP(rs.getInt("itemEXP"));
                    equip.setGMLog(rs.getString("GM_Log"));
                    equip.setDurability(rs.getInt("durability"));
                    equip.setEnhance(rs.getByte("enhance"));
                    equip.setPotential1(rs.getShort("potential1"));
                    equip.setPotential2(rs.getShort("potential2"));
                    equip.setPotential3(rs.getShort("potential3"));
                    equip.setHpR(rs.getShort("hpR"));
                    equip.setMpR(rs.getShort("mpR"));
                    equip.setGiftFrom(rs.getString("sender"));
                    equip.setEquipLevel(rs.getByte("itemlevel"));
                    equip.setEquipOnlyId(rs.getInt("equipOnlyId"));
                    if (equip.getUniqueId() > -1 &&
                            GameConstants.isEffectRing(rs.getInt("itemid"))) {
                        MapleRing ring = MapleRing.loadFromDb(equip.getUniqueId(), mit.equals(MapleInventoryType.EQUIPPED));
                        if (ring != null)
                            equip.setRing(ring);
                    }
                }
                items.put(Integer.valueOf(rs.getInt("inventoryitemid")), new Pair(equip.copy(), mit));
                continue;
            }
            Item item = new Item(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("quantity"), rs.getByte("flag"));
            item.setUniqueId(rs.getInt("uniqueid"));
            item.setOwner(rs.getString("owner"));
            item.setExpiration(rs.getLong("expiredate"));
            item.setEquipOnlyId(rs.getInt("equipOnlyId"));
            item.setGMLog(rs.getString("GM_Log"));
            item.setGiftFrom(rs.getString("sender"));
            if (GameConstants.isPet(item.getItemId()))
                if (item.getUniqueId() > -1) {
                    MaplePet pet = MaplePet.loadFromDb(item.getItemId(), item.getUniqueId(), item.getPosition());
                    if (pet != null)
                        item.setPet(pet);
                } else {
                    int new_unique = MapleInventoryIdentifier.getInstance();
                    item.setUniqueId(new_unique);
                    item.setPet(MaplePet.createPet(item.getItemId(), new_unique));
                }
            items.put(Integer.valueOf(rs.getInt("inventoryitemid")), new Pair(item.copy(), mit));
        }
        rs.close();
        ps.close();
        return items;
    }

    public void saveItems(List<Pair<IItem, MapleInventoryType>> items, Integer... id) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        saveItems(items, con, id);
    }

    public void saveItems(List<Pair<IItem, MapleInventoryType>> items, Connection con, Integer... id) throws SQLException {
        List<Integer> lulz = Arrays.asList(id);
        if (lulz.size() != this.arg.size())
            return;
        if (items == null)
            return;
        StringBuilder querySelectNeedDelete = new StringBuilder();
        querySelectNeedDelete.append("SELECT * FROM `");
        querySelectNeedDelete.append(this.table);
        querySelectNeedDelete.append("` WHERE `type` = ? AND (`");
        querySelectNeedDelete.append(this.arg.get(0));
        querySelectNeedDelete.append("` = ?");
        if (this.arg.size() > 1)
            for (int j = 1; j < this.arg.size(); j++) {
                querySelectNeedDelete.append(" OR `");
                querySelectNeedDelete.append(this.arg.get(j));
                querySelectNeedDelete.append("` = ?");
            }
        querySelectNeedDelete.append(")");
        PreparedStatement ps = con.prepareStatement(querySelectNeedDelete.toString());
        ps.setInt(1, this.value);
        for (int i = 0; i < lulz.size(); i++)
            ps.setInt(i + 2, ((Integer)lulz.get(i)).intValue());
        ResultSet rs = ps.executeQuery();
        List<Integer> equipOnlyIds = new ArrayList<>();
        Map<Integer, Integer> checkItems = new HashMap<>();
        while (rs.next()) {
            int itemId = rs.getInt("itemId");
            int equipOnlyId = rs.getInt("equipOnlyId");
            if (equipOnlyId > 0)
                if (checkItems.containsKey(Integer.valueOf(equipOnlyId))) {
                    if (((Integer)checkItems.get(Integer.valueOf(equipOnlyId))).intValue() == itemId)
                        equipOnlyIds.add(Integer.valueOf(equipOnlyId));
                } else {
                    checkItems.put(Integer.valueOf(equipOnlyId), Integer.valueOf(itemId));
                }
            boolean find = false;
            for (Pair<IItem, MapleInventoryType> item : items) {
                if (((IItem)item.getLeft()).getEquipOnlyId() == equipOnlyId && ((IItem)item.getLeft()).getItemId() == itemId) {
                    find = true;
                    break;
                }
            }
            if (!find || equipOnlyIds.contains(Integer.valueOf(equipOnlyId))) {
                StringBuilder queryDelete = new StringBuilder();
                queryDelete.append("DELETE FROM `");
                queryDelete.append(this.table);
                queryDelete.append("` WHERE `type` = ? AND `itemId` = " + itemId + " AND `equipOnlyId` = " + equipOnlyId + " AND (`");
                queryDelete.append(this.arg.get(0));
                queryDelete.append("` = ?");
                if (this.arg.size() > 1)
                    for (int j = 1; j < this.arg.size(); j++) {
                        queryDelete.append(" OR `");
                        queryDelete.append(this.arg.get(j));
                        queryDelete.append("` = ?");
                    }
                queryDelete.append(")");
                PreparedStatement ps2 = con.prepareStatement(queryDelete.toString());
                try {
                    ps2.setInt(1, this.value);
                    for (int j = 0; j < lulz.size(); j++)
                        ps2.setInt(j + 2, ((Integer)lulz.get(j)).intValue());
                    ps2.executeUpdate();
                } catch (SQLException ex) {
                    System.err.println("Delete Item Error: " + itemId + " equipOnlyId : " + equipOnlyId + " " + ex);
                }
                ps2.close();
            }
        }
        checkItems.clear();
        equipOnlyIds.clear();
        rs.close();
        ps.close();
        StringBuilder querySelectNeedInsert = new StringBuilder();
        querySelectNeedInsert.append("SELECT * FROM `");
        querySelectNeedInsert.append(this.table);
        querySelectNeedInsert.append("` WHERE `type` = ? AND `itemId` = ? AND `equipOnlyId` = ? AND (`");
        querySelectNeedInsert.append(this.arg.get(0));
        querySelectNeedInsert.append("` = ?");
        if (this.arg.size() > 1)
            for (int j = 1; j < this.arg.size(); j++) {
                querySelectNeedInsert.append(" OR `");
                querySelectNeedInsert.append(this.arg.get(j));
                querySelectNeedInsert.append("` = ?");
            }
        querySelectNeedInsert.append(") LIMIT 1");
        ps = con.prepareStatement(querySelectNeedInsert.toString());
        for (Pair<IItem, MapleInventoryType> item : items) {
            int itemId = ((IItem)item.getLeft()).getItemId();
            int equipOnlyId = ((IItem)item.getLeft()).getEquipOnlyId();
            ps.setInt(1, this.value);
            ps.setInt(2, itemId);
            ps.setInt(3, equipOnlyId);
            for (int j = 0; j < lulz.size(); j++)
                ps.setInt(j + 4, ((Integer)lulz.get(j)).intValue());
            rs = ps.executeQuery();
            if (rs.next()) {
                StringBuilder queryItemUpdate = new StringBuilder("UPDATE `");
                queryItemUpdate.append(this.table);
                queryItemUpdate.append("` SET ");
                queryItemUpdate.append("`itemid` = ?, `inventorytype` = ?, `position` = ?, `quantity` = ?, `owner` = ?, `GM_Log` = ?, `uniqueid` = ?, `expiredate` = ?, `flag` = ?, `type` = ?, `sender` = ?  WHERE `equipOnlyId` = ? and (`");
                queryItemUpdate.append(this.arg.get(0));
                queryItemUpdate.append("` = ?");
                if (this.arg.size() > 1)
                    for (int k = 1; k < this.arg.size(); k++) {
                        queryItemUpdate.append(" OR `");
                        queryItemUpdate.append(this.arg.get(k));
                        queryItemUpdate.append("` = ?");
                    }
                queryItemUpdate.append(")");
                PreparedStatement ps2 = con.prepareStatement(queryItemUpdate.toString());
                try {
                    IItem itemUpdate = (IItem)item.getLeft();
                    MapleInventoryType mit = (MapleInventoryType)item.getRight();
                    try {
                        ps2.setInt(1, itemUpdate.getItemId());
                        ps2.setInt(2, mit.getType());
                        ps2.setInt(3, itemUpdate.getPosition());
                        ps2.setInt(4, itemUpdate.getQuantity());
                        ps2.setString(5, itemUpdate.getOwner());
                        ps2.setString(6, itemUpdate.getGMLog());
                        ps2.setInt(7, itemUpdate.getUniqueId());
                        ps2.setLong(8, itemUpdate.getExpiration());
                        ps2.setByte(9, itemUpdate.getFlag());
                        ps2.setByte(10, (byte)this.value);
                        ps2.setString(11, itemUpdate.getGiftFrom());
                        ps2.setInt(12, itemUpdate.getEquipOnlyId());
                        for (int k = 0; k < lulz.size(); k++)
                            ps2.setInt(13 + k, ((Integer)lulz.get(k)).intValue());
                        ps2.executeUpdate();
                        ps2.close();
                    } catch (SQLException ex) {
                        System.err.println("GMLOG : " + itemUpdate.getGMLog() + "1 Table_equip : " + this.table + " " + ex);
                    }
                    if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                        PreparedStatement pse = con.prepareStatement("UPDATE `" + this.table_equip + "` SET `upgradeslots` = ?, `level` = ?, `str` = ?, `dex` = ?, `int` = ?, `luk` = ?, `hp` = ?, `mp` = ?, `watk` = ?, `matk` = ?, `wdef` = ?, `mdef` = ?, `acc` = ?, `avoid` = ?, `hands` = ?, `speed` = ?, `jump` = ?, `ViciousHammer` = ?, `itemEXP` = ?, `durability` = ?, `enhance` = ?, `potential1` = ?, `potential2` = ?, `potential3` = ?, `hpR` = ?, `mpR` = ?, `itemlevel` = ? WHERE `equipOnlyId` = ?");
                        IEquip equip = (IEquip)itemUpdate;
                        pse.setInt(1, equip.getUpgradeSlots());
                        pse.setInt(2, equip.getLevel());
                        pse.setInt(3, equip.getStr());
                        pse.setInt(4, equip.getDex());
                        pse.setInt(5, equip.getInt());
                        pse.setInt(6, equip.getLuk());
                        pse.setInt(7, equip.getHp());
                        pse.setInt(8, equip.getMp());
                        pse.setInt(9, equip.getWatk());
                        pse.setInt(10, equip.getMatk());
                        pse.setInt(11, equip.getWdef());
                        pse.setInt(12, equip.getMdef());
                        pse.setInt(13, equip.getAcc());
                        pse.setInt(14, equip.getAvoid());
                        pse.setInt(15, equip.getHands());
                        pse.setInt(16, equip.getSpeed());
                        pse.setInt(17, equip.getJump());
                        pse.setInt(18, equip.getViciousHammer());
                        pse.setInt(19, equip.getItemEXP());
                        pse.setInt(20, equip.getDurability());
                        pse.setByte(21, equip.getEnhance());
                        pse.setInt(22, equip.getPotential1());
                        pse.setInt(23, equip.getPotential2());
                        pse.setInt(24, equip.getPotential3());
                        pse.setInt(25, equip.getHpR());
                        pse.setInt(26, equip.getMpR());
                        pse.setByte(27, equip.getEquipLevel());
                        pse.setInt(28, equip.getEquipOnlyId());
                        pse.executeUpdate();
                        pse.close();
                    }
                } catch (RuntimeException|SQLException ex) {
                    System.err.println("2 table_equip: " + this.table_equip + " " + ex);
                }
            } else {
                StringBuilder queryItemInsert = new StringBuilder("INSERT INTO `");
                queryItemInsert.append(this.table);
                queryItemInsert.append("` (");
                for (String g : this.arg) {
                    queryItemInsert.append(g);
                    queryItemInsert.append(", ");
                }
                queryItemInsert.append("itemid, inventorytype, position, quantity, owner, GM_Log, uniqueid, expiredate, flag, `type`, sender, `equipOnlyId` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
                for (String g : this.arg)
                    queryItemInsert.append(", ?");
                queryItemInsert.append(")");
                PreparedStatement ps3 = con.prepareStatement(queryItemInsert.toString());
                IItem itemTmp = (IItem)item.getLeft();
                MapleInventoryType mit = (MapleInventoryType)item.getRight();
                try {
                    int k = 1;
                    for (k = 0; k < lulz.size(); k++)
                        ps3.setInt(k + 1, ((Integer)lulz.get(k)).intValue());
                    ps3.setInt(k + 1, itemTmp.getItemId());
                    ps3.setInt(k + 2, mit.getType());
                    ps3.setInt(k + 3, itemTmp.getPosition());
                    ps3.setInt(k + 4, itemTmp.getQuantity());
                    ps3.setString(k + 5, itemTmp.getOwner());
                    ps3.setString(k + 6, itemTmp.getGMLog());
                    ps3.setInt(k + 7, itemTmp.getUniqueId());
                    ps3.setLong(k + 8, itemTmp.getExpiration());
                    ps3.setByte(k + 9, itemTmp.getFlag());
                    ps3.setByte(k + 10, (byte)this.value);
                    ps3.setString(k + 11, itemTmp.getGiftFrom());
                    ps3.setInt(k + 12, itemTmp.getEquipOnlyId());
                    ps3.executeUpdate();
                    ps3.close();
                    ps3 = con.prepareStatement("select @@identity as id");
                    ResultSet rs2 = ps3.executeQuery();
                    int newIndex = 0;
                    if (rs2.next())
                        newIndex = rs2.getInt(1);
                    rs2.close();
                    int onlyID = 0;
                    if (itemTmp.getEquipOnlyId() == -1) {
                        onlyID = newIndex;
                        StringBuilder queryItemUpdateOnlyId = new StringBuilder("Update `");
                        queryItemUpdateOnlyId.append(this.table);
                        queryItemUpdateOnlyId.append("` set `equipOnlyId` = ? WHERE `inventoryitemid` = ?");
                        PreparedStatement ps2 = con.prepareStatement(queryItemUpdateOnlyId.toString());
                        ps2.setInt(1, onlyID);
                        ps2.setInt(2, onlyID);
                        ps2.executeUpdate();
                        ps2.close();
                        itemTmp.setEquipOnlyId(onlyID);
                    } else {
                        onlyID = itemTmp.getEquipOnlyId();
                    }
                    ps3.close();
                    if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                        if (onlyID == 0)
                            throw new RuntimeException("Inserting item failed.");
                        try {
                            PreparedStatement pse = con.prepareStatement("INSERT INTO " + this.table_equip + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            pse.setInt(1, newIndex);
                            IEquip equip = (IEquip)itemTmp;
                            pse.setInt(2, equip.getUpgradeSlots());
                            pse.setInt(3, equip.getLevel());
                            pse.setInt(4, equip.getStr());
                            pse.setInt(5, equip.getDex());
                            pse.setInt(6, equip.getInt());
                            pse.setInt(7, equip.getLuk());
                            pse.setInt(8, equip.getHp());
                            pse.setInt(9, equip.getMp());
                            pse.setInt(10, equip.getWatk());
                            pse.setInt(11, equip.getMatk());
                            pse.setInt(12, equip.getWdef());
                            pse.setInt(13, equip.getMdef());
                            pse.setInt(14, equip.getAcc());
                            pse.setInt(15, equip.getAvoid());
                            pse.setInt(16, equip.getHands());
                            pse.setInt(17, equip.getSpeed());
                            pse.setInt(18, equip.getJump());
                            pse.setInt(19, equip.getViciousHammer());
                            pse.setInt(20, equip.getItemEXP());
                            pse.setInt(21, equip.getDurability());
                            pse.setByte(22, equip.getEnhance());
                            pse.setInt(23, equip.getPotential1());
                            pse.setInt(24, equip.getPotential2());
                            pse.setInt(25, equip.getPotential3());
                            pse.setInt(26, equip.getHpR());
                            pse.setInt(27, equip.getMpR());
                            pse.setByte(28, equip.getEquipLevel());
                            pse.setInt(29, onlyID);
                            pse.executeUpdate();
                            pse.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            System.err.println("INSERT NEW E ERROR : " + itemTmp.getItemId() + " EquipOnlyId : " + itemTmp.getEquipOnlyId() + " " + ex);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.err.println("INSERT NEW ITEM ERROR : " + itemTmp.getItemId() + " EquipOnlyId : " + itemTmp.getEquipOnlyId() + " " + ex);
                }
            }
            rs.close();
        }
        ps.close();
    }
}
