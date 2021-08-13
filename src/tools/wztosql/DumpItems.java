package tools.wztosql;

import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;

public class DumpItems
{
    private final MapleDataProvider item;
    private final MapleDataProvider string;
    private final MapleDataProvider character;
    protected final MapleData cashStringData;
    protected final MapleData consumeStringData;
    protected final MapleData eqpStringData;
    protected final MapleData etcStringData;
    protected final MapleData insStringData;
    protected final MapleData petStringData;
    protected final Set<Integer> doneIds;
    protected boolean hadError;
    protected boolean update;
    protected int id;
    private final Connection con;
    private final List<String> subCon;
    private final List<String> subMain;
    
    public DumpItems(final boolean update) throws Exception {
        this.string = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/String.wz"));
        this.cashStringData = this.string.getData("Cash.img");
        this.consumeStringData = this.string.getData("Consume.img");
        this.eqpStringData = this.string.getData("Eqp.img");
        this.etcStringData = this.string.getData("Etc.img");
        this.insStringData = this.string.getData("Ins.img");
        this.petStringData = this.string.getData("Pet.img");
        this.doneIds = new LinkedHashSet<Integer>();
        this.hadError = false;
        this.update = false;
        this.id = 0;
        this.con = DatabaseConnection.getConnection();
        this.subCon = new LinkedList<String>();
        this.subMain = new LinkedList<String>();
        this.update = update;
        this.item = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Item.wz"));
        this.character = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Character.wz"));
        if (this.item == null || this.string == null || this.character == null) {
            this.hadError = true;
        }
    }
    
    public boolean isHadError() {
        return this.hadError;
    }
    
    public void dumpItems() throws Exception {
        if (!this.hadError) {
            final PreparedStatement psa = this.con.prepareStatement("INSERT INTO wz_itemadddata(itemid, `key`, `subKey`, `value`) VALUES (?, ?, ?, ?)");
            final PreparedStatement psr = this.con.prepareStatement("INSERT INTO wz_itemrewarddata(itemid, item, prob, quantity, period, worldMsg, effect) VALUES (?, ?, ?, ?, ?, ?, ?)");
            final PreparedStatement ps = this.con.prepareStatement("INSERT INTO wz_itemdata(itemid, name, msg, `desc`, slotMax, price, wholePrice, stateChange, flags, karma, meso, monsterBook, itemMakeLevel, questId, scrollReqs, consumeItem, totalprob, incSkill, replaceId, replaceMsg, `create`, afterImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            final PreparedStatement pse = this.con.prepareStatement("INSERT INTO wz_itemequipdata(itemid, itemLevel, `key`, `value`) VALUES (?, ?, ?, ?)");
            try {
                this.dumpItems(psa, psr, ps, pse);
            }
            catch (Exception e) {
                System.out.println(this.id + " quest.");
                this.hadError = true;
            }
            finally {
                psr.executeBatch();
                psr.close();
                psa.executeBatch();
                psa.close();
                pse.executeBatch();
                pse.close();
                ps.executeBatch();
                ps.close();
            }
        }
    }
    
    public void delete(final String sql) throws Exception {
        try (final PreparedStatement ps = this.con.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
    
    public boolean doesExist(final String sql) throws Exception {
        boolean ret;
        try (final PreparedStatement ps = this.con.prepareStatement(sql);
             final ResultSet rs = ps.executeQuery()) {
            ret = rs.next();
        }
        return ret;
    }
    
    public void dumpItems(final MapleDataProvider d, final PreparedStatement psa, final PreparedStatement psr, final PreparedStatement ps, final PreparedStatement pse, final boolean charz) throws Exception {
        for (final MapleDataDirectoryEntry topDir : d.getRoot().getSubdirectories()) {
            if (!topDir.getName().equalsIgnoreCase("Special") && !topDir.getName().equalsIgnoreCase("Hair") && !topDir.getName().equalsIgnoreCase("Face") && !topDir.getName().equalsIgnoreCase("Afterimage")) {
                for (final MapleDataFileEntry ifile : topDir.getFiles()) {
                    final MapleData iz = d.getData(topDir.getName() + "/" + ifile.getName());
                    if (charz || topDir.getName().equalsIgnoreCase("Pet")) {
                        this.dumpItem(psa, psr, ps, pse, iz);
                    }
                    else {
                        for (final MapleData itemData : iz) {
                            this.dumpItem(psa, psr, ps, pse, itemData);
                        }
                    }
                }
            }
        }
    }
    
    public void dumpItem(final PreparedStatement psa, final PreparedStatement psr, final PreparedStatement ps, final PreparedStatement pse, final MapleData iz) throws Exception {
        try {
            if (iz.getName().endsWith(".img")) {
                this.id = Integer.parseInt(iz.getName().substring(0, iz.getName().length() - 4));
            }
            else {
                this.id = Integer.parseInt(iz.getName());
            }
        }
        catch (NumberFormatException nfe) {
            return;
        }
        if (this.doneIds.contains(this.id) || GameConstants.getInventoryType(this.id) == MapleInventoryType.UNDEFINED) {
            return;
        }
        this.doneIds.add(this.id);
        if (this.update && this.doesExist("SELECT * FROM wz_itemdata WHERE itemid = " + this.id)) {
            return;
        }
        ps.setInt(1, this.id);
        final MapleData stringData = this.getStringData(this.id);
        if (stringData == null) {
            ps.setString(2, "");
            ps.setString(3, "");
            ps.setString(4, "");
        }
        else {
            ps.setString(2, MapleDataTool.getString("name", stringData, ""));
            ps.setString(3, MapleDataTool.getString("msg", stringData, ""));
            ps.setString(4, MapleDataTool.getString("desc", stringData, ""));
        }
        final MapleData smEntry = iz.getChildByPath("info/slotMax");
        short ret;
        if (smEntry == null) {
            if (GameConstants.getInventoryType(this.id) == MapleInventoryType.EQUIP) {
                ret = 1;
            }
            else {
                ret = 100;
            }
        }
        else {
            ret = (short)MapleDataTool.getIntConvert(smEntry);
        }
        ps.setInt(5, ret);
        MapleData pData = iz.getChildByPath("info/unitPrice");
        double pEntry;
        if (pData != null) {
            try {
                pEntry = MapleDataTool.getDouble(pData);
            }
            catch (Exception e) {
                pEntry = MapleDataTool.getIntConvert(pData);
            }
        }
        else {
            pData = iz.getChildByPath("info/price");
            if (pData == null) {
                pEntry = -1.0;
            }
            else {
                pEntry = MapleDataTool.getIntConvert(pData);
            }
        }
        if (this.id == 2070019 || this.id == 2330007) {
            pEntry = 1.0;
        }
        ps.setString(6, String.valueOf(pEntry));
        ps.setInt(7, MapleDataTool.getIntConvert("info/price", iz, -1));
        ps.setInt(8, MapleDataTool.getIntConvert("info/stateChangeItem", iz, 0));
        int flags = MapleDataTool.getIntConvert("info/bagType", iz, 0);
        if (MapleDataTool.getIntConvert("info/notSale", iz, 0) > 0) {
            flags |= 0x10;
        }
        if (MapleDataTool.getIntConvert("info/expireOnLogout", iz, 0) > 0) {
            flags |= 0x20;
        }
        if (MapleDataTool.getIntConvert("info/pickUpBlock", iz, 0) > 0) {
            flags |= 0x40;
        }
        if (MapleDataTool.getIntConvert("info/only", iz, 0) > 0) {
            flags |= 0x80;
        }
        if (MapleDataTool.getIntConvert("info/accountSharable", iz, 0) > 0) {
            flags |= 0x100;
        }
        if (MapleDataTool.getIntConvert("info/quest", iz, 0) > 0) {
            flags |= 0x200;
        }
        if (this.id != 4310008 && MapleDataTool.getIntConvert("info/tradeBlock", iz, 0) > 0) {
            flags |= 0x400;
        }
        if (MapleDataTool.getIntConvert("info/accountShareTag", iz, 0) > 0) {
            flags |= 0x800;
        }
        if (MapleDataTool.getIntConvert("info/mobHP", iz, 0) > 0 && MapleDataTool.getIntConvert("info/mobHP", iz, 0) < 100) {
            flags |= 0x1000;
        }
        ps.setInt(9, flags);
        ps.setInt(10, MapleDataTool.getIntConvert("info/tradeAvailable", iz, 0));
        ps.setInt(11, MapleDataTool.getIntConvert("info/meso", iz, 0));
        ps.setInt(12, MapleDataTool.getIntConvert("info/mob", iz, 0));
        ps.setInt(13, MapleDataTool.getIntConvert("info/lv", iz, 0));
        ps.setInt(14, MapleDataTool.getIntConvert("info/questId", iz, 0));
        int totalprob = 0;
        final StringBuilder scrollReqs = new StringBuilder();
        final StringBuilder consumeItem = new StringBuilder();
        final StringBuilder incSkill = new StringBuilder();
        MapleData dat = iz.getChildByPath("req");
        if (dat != null) {
            for (final MapleData req : dat) {
                if (scrollReqs.length() > 0) {
                    scrollReqs.append(",");
                }
                scrollReqs.append(MapleDataTool.getIntConvert(req));
            }
        }
        dat = iz.getChildByPath("consumeItem");
        if (dat != null) {
            for (final MapleData req : dat) {
                if (consumeItem.length() > 0) {
                    consumeItem.append(",");
                }
                consumeItem.append(MapleDataTool.getIntConvert(req));
            }
        }
        ps.setString(15, scrollReqs.toString());
        ps.setString(16, consumeItem.toString());
        final Map<Integer, Map<String, Integer>> equipStats = new HashMap<Integer, Map<String, Integer>>();
        equipStats.put(-1, new HashMap<String, Integer>());
        dat = iz.getChildByPath("mob");
        if (dat != null) {
            for (final MapleData child : dat) {
                equipStats.get(-1).put("mob" + MapleDataTool.getIntConvert("id", child, 0), MapleDataTool.getIntConvert("prob", child, 0));
            }
        }
        dat = iz.getChildByPath("info/level/case");
        if (dat != null) {
            for (final MapleData info : dat) {
                for (final MapleData data : info) {
                    if (data.getName().length() == 1 && data.getChildByPath("Skill") != null) {
                        for (final MapleData skil : data.getChildByPath("Skill")) {
                            final int incSkillz = MapleDataTool.getIntConvert("id", skil, 0);
                            if (incSkillz != 0) {
                                if (incSkill.length() > 0) {
                                    incSkill.append(",");
                                }
                                incSkill.append(incSkillz);
                            }
                        }
                    }
                }
            }
        }
        dat = iz.getChildByPath("info/level/info");
        if (dat != null) {
            for (final MapleData info : dat) {
                if (MapleDataTool.getIntConvert("exp", info, 0) == 0) {
                    continue;
                }
                final int lv = Integer.parseInt(info.getName());
                if (equipStats.get(lv) == null) {
                    equipStats.put(lv, new HashMap<String, Integer>());
                }
                for (final MapleData data2 : info) {
                    if (data2.getName().length() > 3) {
                        equipStats.get(lv).put(data2.getName().substring(3), MapleDataTool.getIntConvert(data2));
                    }
                }
            }
        }
        dat = iz.getChildByPath("info");
        if (dat != null) {
            ps.setString(22, MapleDataTool.getString("afterImage", dat, ""));
            final Map<String, Integer> rett = equipStats.get(-1);
            for (final MapleData data3 : dat.getChildren()) {
                if (data3.getName().startsWith("inc")) {
                    final int gg = MapleDataTool.getIntConvert(data3);
                    if (gg == 0) {
                        continue;
                    }
                    rett.put(data3.getName().substring(3), gg);
                }
            }
            for (final String stat : GameConstants.stats) {
                final MapleData d = dat.getChildByPath(stat);
                if (stat.equals("canLevel")) {
                    if (dat.getChildByPath("level") != null) {
                        rett.put(stat, 1);
                    }
                }
                else if (d != null) {
                    if (stat.equals("skill")) {
                        for (int i = 0; i < d.getChildren().size(); ++i) {
                            rett.put("skillid" + i, MapleDataTool.getIntConvert(Integer.toString(i), d, 0));
                        }
                    }
                    else {
                        final int dd = MapleDataTool.getIntConvert(d);
                        if (dd != 0) {
                            rett.put(stat, dd);
                        }
                    }
                }
            }
        }
        else {
            ps.setString(22, "");
        }
        pse.setInt(1, this.id);
        for (final Map.Entry<Integer, Map<String, Integer>> stats : equipStats.entrySet()) {
            pse.setInt(2, stats.getKey());
            for (final Map.Entry<String, Integer> stat2 : stats.getValue().entrySet()) {
                pse.setString(3, stat2.getKey());
                pse.setInt(4, stat2.getValue());
                pse.addBatch();
            }
        }
        dat = iz.getChildByPath("info/addition");
        if (dat != null) {
            psa.setInt(1, this.id);
            for (final MapleData d2 : dat.getChildren()) {
                final Pair<String, Integer> incs = null;
                final String name = d2.getName();
                switch (name) {
                    case "statinc":
                    case "critical":
                    case "skill":
                    case "mobdie":
                    case "hpmpchange":
                    case "elemboost":
                    case "elemBoost":
                    case "mobcategory":
                    case "boss": {
                        for (final MapleData subKey : d2.getChildren()) {
                            if (subKey.getName().equals("con")) {
                                for (final MapleData conK : subKey.getChildren()) {
                                    final String name2 = conK.getName();
                                    switch (name2) {
                                        case "job": {
                                            final StringBuilder sbbb = new StringBuilder();
                                            if (conK.getData() == null) {
                                                for (final MapleData ids : conK.getChildren()) {
                                                    sbbb.append(ids.getData().toString());
                                                    sbbb.append(",");
                                                }
                                                sbbb.deleteCharAt(sbbb.length() - 1);
                                            }
                                            else {
                                                sbbb.append(conK.getData().toString());
                                            }
                                            psa.setString(2, d2.getName().equals("elemBoost") ? "elemboost" : d2.getName());
                                            psa.setString(3, "con:job");
                                            psa.setString(4, sbbb.toString());
                                            psa.addBatch();
                                            continue;
                                        }
                                        case "weekDay": {
                                            continue;
                                        }
                                        default: {
                                            psa.setString(2, d2.getName().equals("elemBoost") ? "elemboost" : d2.getName());
                                            psa.setString(3, "con:" + conK.getName());
                                            psa.setString(4, conK.getData().toString());
                                            psa.addBatch();
                                            continue;
                                        }
                                    }
                                }
                            }
                            else {
                                psa.setString(2, d2.getName().equals("elemBoost") ? "elemboost" : d2.getName());
                                psa.setString(3, subKey.getName());
                                psa.setString(4, subKey.getData().toString());
                                psa.addBatch();
                            }
                        }
                        continue;
                    }
                    default: {
                        System.out.println("UNKNOWN EQ ADDITION : " + d2.getName() + " from " + this.id);
                        continue;
                    }
                }
            }
        }
        dat = iz.getChildByPath("reward");
        if (dat != null) {
            psr.setInt(1, this.id);
            for (final MapleData reward : dat) {
                psr.setInt(2, MapleDataTool.getIntConvert("item", reward, 0));
                psr.setInt(3, MapleDataTool.getIntConvert("prob", reward, 0));
                psr.setInt(4, MapleDataTool.getIntConvert("count", reward, 0));
                psr.setInt(5, MapleDataTool.getIntConvert("period", reward, 0));
                psr.setString(6, MapleDataTool.getString("worldMsg", reward, ""));
                psr.setString(7, MapleDataTool.getString("Effect", reward, ""));
                psr.addBatch();
                totalprob += MapleDataTool.getIntConvert("prob", reward, 0);
            }
        }
        ps.setInt(17, totalprob);
        ps.setString(18, incSkill.toString());
        dat = iz.getChildByPath("replace");
        if (dat != null) {
            ps.setInt(19, MapleDataTool.getInt("itemid", dat, 0));
            ps.setString(20, MapleDataTool.getString("msg", dat, ""));
        }
        else {
            ps.setInt(19, 0);
            ps.setString(20, "");
        }
        ps.setInt(21, MapleDataTool.getInt("info/create", iz, 0));
        ps.addBatch();
    }
    
    public void dumpItems(final PreparedStatement psa, final PreparedStatement psr, final PreparedStatement ps, final PreparedStatement pse) throws Exception {
        if (!this.update) {
            this.delete("DELETE FROM wz_itemdata");
            this.delete("DELETE FROM wz_itemequipdata");
            this.delete("DELETE FROM wz_itemadddata");
            this.delete("DELETE FROM wz_itemrewarddata");
            System.out.println("Deleted wz_itemdata successfully.");
        }
        System.out.println("Adding into wz_itemdata.....");
        this.dumpItems(this.item, psa, psr, ps, pse, false);
        this.dumpItems(this.character, psa, psr, ps, pse, true);
        System.out.println("Done wz_itemdata...");
        if (!this.subMain.isEmpty()) {
            System.out.println(this.subMain.toString());
        }
        if (!this.subCon.isEmpty()) {
            System.out.println(this.subCon.toString());
        }
    }
    
    public int currentId() {
        return this.id;
    }
    
    public static void main(final String[] args) {
        boolean hadError = false;
        boolean update = false;
        final long startTime = System.currentTimeMillis();
        for (final String file : args) {
            if (file.equalsIgnoreCase("-update")) {
                update = true;
            }
        }
        int currentQuest = 0;
        try {
            final DumpItems dq = new DumpItems(update);
            System.out.println("Dumping Items");
            dq.dumpItems();
            hadError |= dq.isHadError();
            currentQuest = dq.currentId();
        }
        catch (Exception e) {
            hadError = true;
            System.out.println(currentQuest + " quest.");
        }
        final long endTime = System.currentTimeMillis();
        final double elapsedSeconds = (endTime - startTime) / 1000.0;
        final int elapsedSecs = (int)elapsedSeconds % 60;
        final int elapsedMinutes = (int)(elapsedSeconds / 60.0);
        String withErrors = "";
        if (hadError) {
            withErrors = " with errors";
        }
        System.out.println("Finished" + withErrors + " in " + elapsedMinutes + " minutes " + elapsedSecs + " seconds");
    }
    
    protected final MapleData getStringData(final int itemId) {
        String cat = null;
        MapleData data;
        if (itemId >= 5010000) {
            data = this.cashStringData;
        }
        else if (itemId >= 2000000 && itemId < 3000000) {
            data = this.consumeStringData;
        }
        else if ((itemId >= 1132000 && itemId < 1183000) || (itemId >= 1010000 && itemId < 1040000) || (itemId >= 1122000 && itemId < 1123000)) {
            data = this.eqpStringData;
            cat = "Eqp/Accessory";
        }
        else if (itemId >= 1172000 && itemId < 1180000) {
            data = this.eqpStringData;
            cat = "Eqp/MonsterBook";
        }
        else if (itemId >= 1662000 && itemId < 1680000) {
            data = this.eqpStringData;
            cat = "Eqp/Android";
        }
        else if (itemId >= 1000000 && itemId < 1010000) {
            data = this.eqpStringData;
            cat = "Eqp/Cap";
        }
        else if (itemId >= 1102000 && itemId < 1103000) {
            data = this.eqpStringData;
            cat = "Eqp/Cape";
        }
        else if (itemId >= 1040000 && itemId < 1050000) {
            data = this.eqpStringData;
            cat = "Eqp/Coat";
        }
        else if (itemId >= 20000 && itemId < 22000) {
            data = this.eqpStringData;
            cat = "Eqp/Face";
        }
        else if (itemId >= 1080000 && itemId < 1090000) {
            data = this.eqpStringData;
            cat = "Eqp/Glove";
        }
        else if (itemId >= 30000 && itemId < 35000) {
            data = this.eqpStringData;
            cat = "Eqp/Hair";
        }
        else if (itemId >= 1050000 && itemId < 1060000) {
            data = this.eqpStringData;
            cat = "Eqp/Longcoat";
        }
        else if (itemId >= 1060000 && itemId < 1070000) {
            data = this.eqpStringData;
            cat = "Eqp/Pants";
        }
        else if (itemId >= 1610000 && itemId < 1660000) {
            data = this.eqpStringData;
            cat = "Eqp/Mechanic";
        }
        else if (itemId >= 1802000 && itemId < 1820000) {
            data = this.eqpStringData;
            cat = "Eqp/PetEquip";
        }
        else if (itemId >= 1920000 && itemId < 2000000) {
            data = this.eqpStringData;
            cat = "Eqp/Dragon";
        }
        else if (itemId >= 1112000 && itemId < 1120000) {
            data = this.eqpStringData;
            cat = "Eqp/Ring";
        }
        else if (itemId >= 1092000 && itemId < 1100000) {
            data = this.eqpStringData;
            cat = "Eqp/Shield";
        }
        else if (itemId >= 1070000 && itemId < 1080000) {
            data = this.eqpStringData;
            cat = "Eqp/Shoes";
        }
        else if (itemId >= 1900000 && itemId < 1920000) {
            data = this.eqpStringData;
            cat = "Eqp/Taming";
        }
        else if (itemId >= 1200000 && itemId < 1210000) {
            data = this.eqpStringData;
            cat = "Eqp/Totem";
        }
        else if (itemId >= 1210000 && itemId < 1800000) {
            data = this.eqpStringData;
            cat = "Eqp/Weapon";
        }
        else if (itemId >= 4000000 && itemId < 5000000) {
            data = this.etcStringData;
            cat = "Etc";
        }
        else if (itemId >= 3000000 && itemId < 4000000) {
            data = this.insStringData;
        }
        else {
            if (itemId < 5000000 || itemId >= 5010000) {
                return null;
            }
            data = this.petStringData;
        }
        if (cat == null) {
            return data.getChildByPath(String.valueOf(itemId));
        }
        return data.getChildByPath(cat + "/" + itemId);
    }
}
