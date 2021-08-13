package server;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import constants.GameConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;
import tools.StringUtil;

public class MapleItemInformationProvider {

    protected Map<Integer, Boolean> onEquipUntradableCache;
    protected MapleDataProvider etcData;
    protected MapleDataProvider itemData;
    protected MapleDataProvider equipData;
    protected MapleDataProvider stringData;
    protected MapleData cashStringData;
    protected MapleData consumeStringData;
    protected MapleData eqpStringData;
    protected MapleData etcStringData;
    protected MapleData insStringData;
    protected MapleData petStringData;
    protected Map<Integer, List<Integer>> scrollReqCache;
    protected Map<Integer, Short> slotMaxCache;
    protected Map<Integer, Integer> getExpCache;
    protected Map<Integer, String> faceList;
    protected Map<Integer, String> hairList;
    protected MapleDataProvider chrData;
    protected Map<Integer, List<StructPotentialItem>> potentialCache;
    protected Map<Integer, MapleStatEffect> itemEffects;
    protected Map<Integer, Map<String, Integer>> equipStatsCache;
    protected Map<Integer, Map<String, Byte>> itemMakeStatsCache;
    protected Map<Integer, Short> itemMakeLevel;
    protected Map<Integer, Equip> equipCache;
    protected Map<Integer, Double> priceCache;
    protected Map<Integer, Integer> wholePriceCache;
    protected Map<Integer, Integer> projectileWatkCache;
    protected Map<Integer, Integer> monsterBookID;
    protected Map<Integer, String> nameCache;
    protected Map<Integer, String> descCache;
    protected Map<Integer, String> msgCache;
    protected Map<Integer, Map<String, Integer>> SkillStatsCache;
    protected Map<Integer, Byte> consumeOnPickupCache;
    protected Map<Integer, Boolean> dropRestrictionCache;
    protected Map<Integer, Boolean> accCache;
    protected Map<Integer, Boolean> pickupRestrictionCache;
    protected Map<Integer, Integer> stateChangeCache;
    protected Map<Integer, Integer> mesoCache;
    protected Map<Integer, Boolean> notSaleCache;
    protected Map<Integer, Integer> karmaEnabledCache;
    protected Map<Integer, Boolean> karmaCache;
    protected Map<Integer, Boolean> isQuestItemCache;
    protected Map<Integer, Boolean> blockPickupCache;
    protected Map<Integer, List<Integer>> petsCanConsumeCache;
    protected Map<Integer, Boolean> logoutExpireCache;
    protected Map<Integer, List<Pair<Integer, Integer>>> summonMobCache;
    protected List<Pair<Integer, String>> itemNameCache;
    protected Map<Integer, Map<Integer, Map<String, Integer>>> equipIncsCache;
    protected Map<Integer, Map<Integer, List<Integer>>> equipSkillsCache;
    protected Map<Integer, Pair<Integer, List<StructRewardItem>>> RewardItem;
    protected Map<Byte, StructSetItem> setItems;
    protected Map<Integer, Pair<Integer, List<Integer>>> questItems;
    protected Map<Integer, MapleInventoryType> inventoryTypeCache;

    private static final MapleItemInformationProvider instance;
    private static final Map<Integer, Short> whiteItemList;
    private static final Random rand;

    public static MapleItemInformationProvider getInstance() {
        return MapleItemInformationProvider.instance;
    }

    protected MapleItemInformationProvider() {
        this.onEquipUntradableCache = new HashMap<Integer, Boolean>();
        this.etcData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Etc.wz"));
        this.itemData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Item.wz"));
        this.equipData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Character.wz"));
        this.stringData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/String.wz"));
        this.cashStringData = this.stringData.getData("Cash.img");
        this.consumeStringData = this.stringData.getData("Consume.img");
        this.eqpStringData = this.stringData.getData("Eqp.img");
        this.etcStringData = this.stringData.getData("Etc.img");
        this.insStringData = this.stringData.getData("Ins.img");
        this.petStringData = this.stringData.getData("Pet.img");
        this.scrollReqCache = new HashMap<Integer, List<Integer>>();
        this.slotMaxCache = new HashMap<Integer, Short>();
        this.getExpCache = new HashMap<Integer, Integer>();
        this.potentialCache = new HashMap<Integer, List<StructPotentialItem>>();
        this.itemEffects = new HashMap<Integer, MapleStatEffect>();
        this.equipStatsCache = new HashMap<Integer, Map<String, Integer>>();
        this.itemMakeStatsCache = new HashMap<Integer, Map<String, Byte>>();
        this.itemMakeLevel = new HashMap<Integer, Short>();
        this.equipCache = new HashMap<Integer, Equip>();
        this.priceCache = new HashMap<Integer, Double>();
        this.wholePriceCache = new HashMap<Integer, Integer>();
        this.projectileWatkCache = new HashMap<Integer, Integer>();
        this.monsterBookID = new HashMap<Integer, Integer>();
        this.nameCache = new HashMap<Integer, String>();
        this.descCache = new HashMap<Integer, String>();
        this.msgCache = new HashMap<Integer, String>();
        this.SkillStatsCache = new HashMap<Integer, Map<String, Integer>>();
        this.consumeOnPickupCache = new HashMap<Integer, Byte>();
        this.dropRestrictionCache = new HashMap<Integer, Boolean>();
        this.accCache = new HashMap<Integer, Boolean>();
        this.pickupRestrictionCache = new HashMap<Integer, Boolean>();
        this.stateChangeCache = new HashMap<Integer, Integer>();
        this.mesoCache = new HashMap<Integer, Integer>();
        this.notSaleCache = new HashMap<Integer, Boolean>();
        this.karmaEnabledCache = new HashMap<Integer, Integer>();
        this.karmaCache = new HashMap<Integer, Boolean>();
        this.isQuestItemCache = new HashMap<Integer, Boolean>();
        this.blockPickupCache = new HashMap<Integer, Boolean>();
        this.petsCanConsumeCache = new HashMap<Integer, List<Integer>>();
        this.logoutExpireCache = new HashMap<Integer, Boolean>();
        this.summonMobCache = new HashMap<Integer, List<Pair<Integer, Integer>>>();
        this.itemNameCache = new ArrayList<Pair<Integer, String>>();
        this.equipIncsCache = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();
        this.equipSkillsCache = new HashMap<Integer, Map<Integer, List<Integer>>>();
        this.RewardItem = new HashMap<Integer, Pair<Integer, List<StructRewardItem>>>();
        this.setItems = new HashMap<Byte, StructSetItem>();
        this.questItems = new HashMap<Integer, Pair<Integer, List<Integer>>>();
        this.inventoryTypeCache = new HashMap<Integer, MapleInventoryType>();
    }

    public void load() {
        if (this.setItems.size() != 0 || this.potentialCache.size() != 0) {
            return;
        }
        this.getAllItems();
    }

    public List<StructPotentialItem> getPotentialInfo(final int potId) {
        return this.potentialCache.get(potId);
    }

    public Map<Integer, List<StructPotentialItem>> getAllPotentialInfo() {
        return this.potentialCache;
    }

    public List<Pair<Integer, String>> getAllItems() {
        if (this.itemNameCache.size() != 0) {
            return this.itemNameCache;
        }
        final List<Pair<Integer, String>> itemPairs = new ArrayList<Pair<Integer, String>>();
        MapleData itemsData = this.stringData.getData("Cash.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Consume.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder2 : eqpType.getChildren()) {
                itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder2.getName()), MapleDataTool.getString("name", itemFolder2, "NO-NAME")));
            }
        }
        itemsData = this.stringData.getData("Etc.img").getChildByPath("Etc");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Ins.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Pet.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        return itemPairs;
    }

    protected MapleData getStringData(final int itemId) {
        String cat = null;
        MapleData data;
        if (itemId >= 5010000) {
            data = this.cashStringData;
        } else if (itemId >= 2000000 && itemId < 3000000) {
            data = this.consumeStringData;
        } else if ((itemId >= 1132000 && itemId < 1183000) || (itemId >= 1010000 && itemId < 1040000) || (itemId >= 1122000 && itemId < 1123000)) {
            data = this.eqpStringData;
            cat = "Eqp/Accessory";
        } else if (itemId >= 1662000 && itemId < 1680000) {
            data = this.eqpStringData;
            cat = "Eqp/Android";
        } else if (itemId >= 1000000 && itemId < 1010000) {
            data = this.eqpStringData;
            cat = "Eqp/Cap";
        } else if (itemId >= 1102000 && itemId < 1103000) {
            data = this.eqpStringData;
            cat = "Eqp/Cape";
        } else if (itemId >= 1040000 && itemId < 1050000) {
            data = this.eqpStringData;
            cat = "Eqp/Coat";
        } else if (itemId >= 20000 && itemId < 22000) {
            data = this.eqpStringData;
            cat = "Eqp/Face";
        } else if (itemId >= 1080000 && itemId < 1090000) {
            data = this.eqpStringData;
            cat = "Eqp/Glove";
        } else if (itemId >= 30000 && itemId < 35000) {
            data = this.eqpStringData;
            cat = "Eqp/Hair";
        } else if (itemId >= 1050000 && itemId < 1060000) {
            data = this.eqpStringData;
            cat = "Eqp/Longcoat";
        } else if (itemId >= 1060000 && itemId < 1070000) {
            data = this.eqpStringData;
            cat = "Eqp/Pants";
        } else if (itemId >= 1610000 && itemId < 1660000) {
            data = this.eqpStringData;
            cat = "Eqp/Mechanic";
        } else if (itemId >= 1802000 && itemId < 1820000) {
            data = this.eqpStringData;
            cat = "Eqp/PetEquip";
        } else if (itemId >= 1920000 && itemId < 2000000) {
            data = this.eqpStringData;
            cat = "Eqp/Dragon";
        } else if (itemId >= 1112000 && itemId < 1120000) {
            data = this.eqpStringData;
            cat = "Eqp/Ring";
        } else if (itemId >= 1092000 && itemId < 1100000) {
            data = this.eqpStringData;
            cat = "Eqp/Shield";
        } else if (itemId >= 1070000 && itemId < 1080000) {
            data = this.eqpStringData;
            cat = "Eqp/Shoes";
        } else if (itemId >= 1900000 && itemId < 1920000) {
            data = this.eqpStringData;
            cat = "Eqp/Taming";
        } else if (itemId >= 1200000 && itemId < 1210000) {
            data = this.eqpStringData;
            cat = "Eqp/Totem";
        } else if (itemId >= 1210000 && itemId < 1800000) {
            data = this.eqpStringData;
            cat = "Eqp/Weapon";
        } else if (itemId >= 4000000 && itemId < 5000000) {
            data = this.etcStringData;
            cat = "Etc";
        } else if (itemId >= 3000000 && itemId < 4000000) {
            data = this.insStringData;
        } else {
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

    protected MapleData getItemData(final int itemId) {
        MapleData ret = null;
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = this.itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = this.itemData.getData(topDir.getName() + "/" + iFile.getName());
                    if (ret == null) {
                        return null;
                    }
                    ret = ret.getChildByPath(idStr);
                    return ret;
                } else {
                    if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                        return this.itemData.getData(topDir.getName() + "/" + iFile.getName());
                    }
                    continue;
                }
            }
        }
        root = this.equipData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    return this.equipData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        return ret;
    }


    //物品上限
    public short getSlotMax(final MapleClient c, final int itemId) {
        if (this.slotMaxCache.containsKey(itemId)) {
            return this.slotMaxCache.get(itemId);
        }
        short ret = 0;
        final MapleData item = this.getItemData(itemId);
        if (item != null) {
            Short tmp = whiteItemList.get(itemId);
            if (null == tmp) {
                final MapleData smEntry = item.getChildByPath("info/slotMax");
                if (smEntry == null) {
                    if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                        ret = 1;
                    } else {
                        ret = 100;
                    }
                } else {
                    ret = (short) MapleDataTool.getInt(smEntry);
                }
            } else {
                ret = tmp;
            }
        }
        this.slotMaxCache.put(itemId, ret);
        return ret;
    }

    public int getWholePrice(final int itemId) {
        if (this.wholePriceCache.containsKey(itemId)) {
            return this.wholePriceCache.get(itemId);
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return -1;
        }
        int pEntry = 0;
        final MapleData pData = item.getChildByPath("info/price");
        if (pData == null) {
            return -1;
        }
        pEntry = MapleDataTool.getInt(pData);
        this.wholePriceCache.put(itemId, pEntry);
        return pEntry;
    }

    public double getPrice(final int itemId) {
        if (this.priceCache.containsKey(itemId)) {
            return this.priceCache.get(itemId);
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return -1.0;
        }
        double pEntry = 0.0;
        MapleData pData = item.getChildByPath("info/unitPrice");
        if (pData != null) {
            try {
                pEntry = MapleDataTool.getDouble(pData);
            } catch (Exception e) {
                pEntry = MapleDataTool.getIntConvert(pData);
            }
        } else {
            pData = item.getChildByPath("info/price");
            if (pData == null) {
                return -1.0;
            }
            pEntry = MapleDataTool.getIntConvert(pData);
        }
        if (itemId == 2070019 || itemId == 2330007) {
            pEntry = 1.0;
        }
        this.priceCache.put(itemId, pEntry);
        return pEntry;
    }

    public Map<String, Byte> getItemMakeStats(final int itemId) {
        if (this.itemMakeStatsCache.containsKey(itemId)) {
            return this.itemMakeStatsCache.get(itemId);
        }
        if (itemId / 10000 != 425) {
            return null;
        }
        final Map<String, Byte> ret = new LinkedHashMap<String, Byte>();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        ret.put("incPAD", (byte) MapleDataTool.getInt("incPAD", info, 0));
        ret.put("incMAD", (byte) MapleDataTool.getInt("incMAD", info, 0));
        ret.put("incACC", (byte) MapleDataTool.getInt("incACC", info, 0));
        ret.put("incEVA", (byte) MapleDataTool.getInt("incEVA", info, 0));
        ret.put("incSpeed", (byte) MapleDataTool.getInt("incSpeed", info, 0));
        ret.put("incJump", (byte) MapleDataTool.getInt("incJump", info, 0));
        ret.put("incMaxHP", (byte) MapleDataTool.getInt("incMaxHP", info, 0));
        ret.put("incMaxMP", (byte) MapleDataTool.getInt("incMaxMP", info, 0));
        ret.put("incSTR", (byte) MapleDataTool.getInt("incSTR", info, 0));
        ret.put("incINT", (byte) MapleDataTool.getInt("incINT", info, 0));
        ret.put("incLUK", (byte) MapleDataTool.getInt("incLUK", info, 0));
        ret.put("incDEX", (byte) MapleDataTool.getInt("incDEX", info, 0));
        ret.put("randOption", (byte) MapleDataTool.getInt("randOption", info, 0));
        ret.put("randStat", (byte) MapleDataTool.getInt("randStat", info, 0));
        this.itemMakeStatsCache.put(itemId, ret);
        return ret;
    }

    private int rand(final int min, final int max) {
        return Math.abs(Randomizer.rand(min, max));
    }

    public Equip levelUpEquip(final Equip equip, final Map<String, Integer> sta) {
        final Equip nEquip = (Equip) equip.copy();
        try {
            for (final Map.Entry<String, Integer> stat : sta.entrySet()) {
                final String s = stat.getKey();
                switch (s) {
                    case "STRMin": {
                        nEquip.setStr((short) (nEquip.getStr() + this.rand(stat.getValue(), sta.get("STRMax"))));
                        continue;
                    }
                    case "DEXMin": {
                        nEquip.setDex((short) (nEquip.getDex() + this.rand(stat.getValue(), sta.get("DEXMax"))));
                        continue;
                    }
                    case "INTMin": {
                        nEquip.setInt((short) (nEquip.getInt() + this.rand(stat.getValue(), sta.get("INTMax"))));
                        continue;
                    }
                    case "LUKMin": {
                        nEquip.setLuk((short) (nEquip.getLuk() + this.rand(stat.getValue(), sta.get("LUKMax"))));
                        continue;
                    }
                    case "PADMin": {
                        nEquip.setWatk((short) (nEquip.getWatk() + this.rand(stat.getValue(), sta.get("PADMax"))));
                        continue;
                    }
                    case "PDDMin": {
                        nEquip.setWdef((short) (nEquip.getWdef() + this.rand(stat.getValue(), sta.get("PDDMax"))));
                        continue;
                    }
                    case "MADMin": {
                        nEquip.setMatk((short) (nEquip.getMatk() + this.rand(stat.getValue(), sta.get("MADMax"))));
                        continue;
                    }
                    case "MDDMin": {
                        nEquip.setMdef((short) (nEquip.getMdef() + this.rand(stat.getValue(), sta.get("MDDMax"))));
                        continue;
                    }
                    case "ACCMin": {
                        nEquip.setAcc((short) (nEquip.getAcc() + this.rand(stat.getValue(), sta.get("ACCMax"))));
                        continue;
                    }
                    case "EVAMin": {
                        nEquip.setAvoid((short) (nEquip.getAvoid() + this.rand(stat.getValue(), sta.get("EVAMax"))));
                        continue;
                    }
                    case "SpeedMin": {
                        nEquip.setSpeed((short) (nEquip.getSpeed() + this.rand(stat.getValue(), sta.get("SpeedMax"))));
                        continue;
                    }
                    case "JumpMin": {
                        nEquip.setJump((short) (nEquip.getJump() + this.rand(stat.getValue(), sta.get("JumpMax"))));
                        continue;
                    }
                    case "MHPMin": {
                        nEquip.setHp((short) (nEquip.getHp() + this.rand(stat.getValue(), sta.get("MHPMax"))));
                        continue;
                    }
                    case "MMPMin": {
                        nEquip.setMp((short) (nEquip.getMp() + this.rand(stat.getValue(), sta.get("MMPMax"))));
                        continue;
                    }
                    case "MaxHPMin": {
                        nEquip.setHp((short) (nEquip.getHp() + this.rand(stat.getValue(), sta.get("MaxHPMax"))));
                        continue;
                    }
                    case "MaxMPMin": {
                        nEquip.setMp((short) (nEquip.getMp() + this.rand(stat.getValue(), sta.get("MaxMPMax"))));
                        continue;
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return nEquip;
    }

    public Map<Integer, Map<String, Integer>> getEquipIncrements(final int itemId) {
        if (this.equipIncsCache.containsKey(itemId)) {
            return this.equipIncsCache.get(itemId);
        }
        final Map<Integer, Map<String, Integer>> ret = new LinkedHashMap<Integer, Map<String, Integer>>();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info/level/info");
        if (info == null) {
            return null;
        }
        for (final MapleData dat : info.getChildren()) {
            final Map<String, Integer> incs = new HashMap<String, Integer>();
            for (final MapleData data : dat.getChildren()) {
                if (data.getName().length() > 3) {
                    incs.put(data.getName().substring(3), MapleDataTool.getIntConvert(data.getName(), dat, 0));
                }
            }
            ret.put(Integer.parseInt(dat.getName()), incs);
        }
        this.equipIncsCache.put(itemId, ret);
        return ret;
    }

    public Map<Integer, List<Integer>> getEquipSkills(final int itemId) {
        if (this.equipSkillsCache.containsKey(itemId)) {
            return this.equipSkillsCache.get(itemId);
        }
        final Map<Integer, List<Integer>> ret = new LinkedHashMap<Integer, List<Integer>>();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info/level/case");
        if (info == null) {
            return null;
        }
        for (final MapleData dat : info.getChildren()) {
            for (final MapleData data : dat.getChildren()) {
                if (data.getName().length() == 1) {
                    final List<Integer> adds = new ArrayList<Integer>();
                    for (final MapleData skil : data.getChildByPath("Skill").getChildren()) {
                        adds.add(MapleDataTool.getIntConvert("id", skil, 0));
                    }
                    ret.put(Integer.parseInt(data.getName()), adds);
                }
            }
        }
        this.equipSkillsCache.put(itemId, ret);
        return ret;
    }

    public Map<String, Integer> getEquipStats(final int itemId) {
        if (this.equipStatsCache.containsKey(itemId)) {
            return this.equipStatsCache.get(itemId);
        }
        final Map<String, Integer> ret = new LinkedHashMap<String, Integer>();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), MapleDataTool.getIntConvert(data));
            }
        }
        ret.put("tuc", MapleDataTool.getInt("tuc", info, 0));
        ret.put("reqLevel", MapleDataTool.getInt("reqLevel", info, 0));
        ret.put("reqJob", MapleDataTool.getInt("reqJob", info, 0));
        ret.put("reqSTR", MapleDataTool.getInt("reqSTR", info, 0));
        ret.put("reqDEX", MapleDataTool.getInt("reqDEX", info, 0));
        ret.put("reqINT", MapleDataTool.getInt("reqINT", info, 0));
        ret.put("reqLUK", MapleDataTool.getInt("reqLUK", info, 0));
        ret.put("reqPOP", MapleDataTool.getInt("reqPOP", info, 0));
        ret.put("cash", MapleDataTool.getInt("cash", info, 0));
        ret.put("canLevel", (info.getChildByPath("level") != null) ? 1 : 0);
        ret.put("cursed", MapleDataTool.getInt("cursed", info, 0));
        ret.put("success", MapleDataTool.getInt("success", info, 0));
        ret.put("setItemID", MapleDataTool.getInt("setItemID", info, 0));
        ret.put("equipTradeBlock", MapleDataTool.getInt("equipTradeBlock", info, 0));
        ret.put("durability", MapleDataTool.getInt("durability", info, -1));
        if (GameConstants.isMagicWeapon(itemId)) {
            ret.put("elemDefault", MapleDataTool.getInt("elemDefault", info, 100));
            ret.put("incRMAS", MapleDataTool.getInt("incRMAS", info, 100));
            ret.put("incRMAF", MapleDataTool.getInt("incRMAF", info, 100));
            ret.put("incRMAL", MapleDataTool.getInt("incRMAL", info, 100));
            ret.put("incRMAI", MapleDataTool.getInt("incRMAI", info, 100));
        }
        this.equipStatsCache.put(itemId, ret);
        return ret;
    }

    public boolean canEquip(final Map<String, Integer> stats, final int itemid, final int level, final int job, final int fame, final int str, final int dex, final int luk, final int int_, final int supremacy) {
        if (level + supremacy >= stats.get("reqLevel") && str >= stats.get("reqSTR") && dex >= stats.get("reqDEX") && luk >= stats.get("reqLUK") && int_ >= stats.get("reqINT")) {
            final int fameReq = stats.get("reqPOP");
            return fameReq == 0 || fame >= fameReq;
        }
        return false;
    }

    public int getReqLevel(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return this.getEquipStats(itemId).get("reqLevel");
    }

    public boolean isCashItem(final int itemId) {
        return this.getEquipStats(itemId) != null && this.getEquipStats(itemId).get("cash") == 1;
    }

    public int getSlots(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return this.getEquipStats(itemId).get("tuc");
    }

    public int getSetItemID(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return this.getEquipStats(itemId).get("setItemID");
    }

    public StructSetItem getSetItem(final int setItemId) {
        return this.setItems.get((byte) setItemId);
    }

    public List<Integer> getScrollReqs(final int itemId) {
        if (this.scrollReqCache.containsKey(itemId)) {
            return this.scrollReqCache.get(itemId);
        }
        final List<Integer> ret = new ArrayList<Integer>();
        final MapleData data = this.getItemData(itemId).getChildByPath("req");
        if (data == null) {
            return ret;
        }
        for (final MapleData req : data.getChildren()) {
            ret.add(MapleDataTool.getInt(req));
        }
        this.scrollReqCache.put(itemId, ret);
        return ret;
    }

    public IItem scrollEquipWithId(final IItem equip, final IItem scrollId, final boolean ws, final MapleCharacter chr, final int vegas, final boolean checkIfGM) {
        if (equip.getType() == 1) {
            final Equip nEquip = (Equip) equip;
            final Map<String, Integer> stats = this.getEquipStats(scrollId.getItemId());
            final Map<String, Integer> eqstats = this.getEquipStats(equip.getItemId());
            final int succ = GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getSuccessTablet(scrollId.getItemId(), nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId())) ? 0 : stats.get("success"));
            final int curse = GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getCurseTablet(scrollId.getItemId(), nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId())) ? 0 : stats.get("cursed"));
            final int success = succ + ((vegas == 5610000 && succ == 10) ? 20 : ((vegas == 5610001 && succ == 60) ? 30 : 0));
            if (GameConstants.isPotentialScroll(scrollId.getItemId()) || GameConstants.isEquipScroll(scrollId.getItemId()) || Randomizer.nextInt(100) <= success || checkIfGM) {
                switch (scrollId.getItemId()) {
                    case 2049000:
                    case 2049001:
                    case 2049002:
                    case 2049003:
                    case 2049004:
                    case 2049005: {
                        if (nEquip.getLevel() + nEquip.getUpgradeSlots() < eqstats.get("tuc")) {
                            nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 1));
                            break;
                        }
                        break;
                    }
                    case 2049006:
                    case 2049007:
                    case 2049008: {
                        if (nEquip.getLevel() + nEquip.getUpgradeSlots() < eqstats.get("tuc")) {
                            nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 2));
                            break;
                        }
                        break;
                    }
                    case 2040727: {
                        byte flag = nEquip.getFlag();
                        flag |= (byte) ItemFlag.SPIKES.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    case 2041058: {
                        byte flag = nEquip.getFlag();
                        flag |= (byte) ItemFlag.COLD.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    default: {
                        if (GameConstants.isChaosScroll(scrollId.getItemId())) {
                            final int z = GameConstants.getChaosNumber(scrollId.getItemId());
                            if (scrollId.getItemId() == 2049122 || scrollId.getItemId() == 2049124) {
                                if (nEquip.getStr() > 0) {
                                    nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getDex() > 0) {
                                    nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getInt() > 0) {
                                    nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getLuk() > 0) {
                                    nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getWatk() > 0) {
                                    nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getWdef() > 0) {
                                    nEquip.setWdef((short) (nEquip.getWdef() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getMatk() > 0) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getMdef() > 0) {
                                    nEquip.setMdef((short) (nEquip.getMdef() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getAcc() > 0) {
                                    nEquip.setAcc((short) (nEquip.getAcc() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getAvoid() > 0) {
                                    nEquip.setAvoid((short) (nEquip.getAvoid() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getSpeed() > 0) {
                                    nEquip.setSpeed((short) (nEquip.getSpeed() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getJump() > 0) {
                                    nEquip.setJump((short) (nEquip.getJump() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getHp() > 0) {
                                    nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(z) + 1));
                                }
                                if (nEquip.getMp() > 0) {
                                    nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(z) + 1));
                                    break;
                                }
                                break;
                            } else {
                                if (nEquip.getStr() > 0) {
                                    nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getDex() > 0) {
                                    nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getInt() > 0) {
                                    nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getLuk() > 0) {
                                    nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getWatk() > 0) {
                                    nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getWdef() > 0) {
                                    nEquip.setWdef((short) (nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getMatk() > 0) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getMdef() > 0) {
                                    nEquip.setMdef((short) (nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getAcc() > 0) {
                                    nEquip.setAcc((short) (nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getAvoid() > 0) {
                                    nEquip.setAvoid((short) (nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getSpeed() > 0) {
                                    nEquip.setSpeed((short) (nEquip.getSpeed() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getJump() > 0) {
                                    nEquip.setJump((short) (nEquip.getJump() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getHp() > 0) {
                                    nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                }
                                if (nEquip.getMp() > 0) {
                                    nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                    break;
                                }
                                break;
                            }
                        } else {
                            if (GameConstants.isEquipScroll(scrollId.getItemId())) {
                                break;
                            }
                            if (GameConstants.isPotentialScroll(scrollId.getItemId())) {
                                break;
                            }
                            for (final Map.Entry<String, Integer> stat : stats.entrySet()) {
                                final String s;
                                final String key = s = stat.getKey();
                                switch (s) {
                                    case "STR": {
                                        nEquip.setStr((short) (nEquip.getStr() + stat.getValue()));
                                        continue;
                                    }
                                    case "DEX": {
                                        nEquip.setDex((short) (nEquip.getDex() + stat.getValue()));
                                        continue;
                                    }
                                    case "INT": {
                                        nEquip.setInt((short) (nEquip.getInt() + stat.getValue()));
                                        continue;
                                    }
                                    case "LUK": {
                                        nEquip.setLuk((short) (nEquip.getLuk() + stat.getValue()));
                                        continue;
                                    }
                                    case "PAD": {
                                        nEquip.setWatk((short) (nEquip.getWatk() + stat.getValue()));
                                        continue;
                                    }
                                    case "PDD": {
                                        nEquip.setWdef((short) (nEquip.getWdef() + stat.getValue()));
                                        continue;
                                    }
                                    case "MAD": {
                                        nEquip.setMatk((short) (nEquip.getMatk() + stat.getValue()));
                                        continue;
                                    }
                                    case "MDD": {
                                        nEquip.setMdef((short) (nEquip.getMdef() + stat.getValue()));
                                        continue;
                                    }
                                    case "ACC": {
                                        nEquip.setAcc((short) (nEquip.getAcc() + stat.getValue()));
                                        continue;
                                    }
                                    case "EVA": {
                                        nEquip.setAvoid((short) (nEquip.getAvoid() + stat.getValue()));
                                        continue;
                                    }
                                    case "Speed": {
                                        nEquip.setSpeed((short) (nEquip.getSpeed() + stat.getValue()));
                                        continue;
                                    }
                                    case "Jump": {
                                        nEquip.setJump((short) (nEquip.getJump() + stat.getValue()));
                                        continue;
                                    }
                                    case "MHP": {
                                        nEquip.setHp((short) (nEquip.getHp() + stat.getValue()));
                                        continue;
                                    }
                                    case "MMP": {
                                        nEquip.setMp((short) (nEquip.getMp() + stat.getValue()));
                                        continue;
                                    }
                                    case "MHPr": {
                                        nEquip.setHpR((short) (nEquip.getHpR() + stat.getValue()));
                                        continue;
                                    }
                                    case "MMPr": {
                                        nEquip.setMpR((short) (nEquip.getMpR() + stat.getValue()));
                                        continue;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (!GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - 1));
                    nEquip.setLevel((byte) (nEquip.getLevel() + 1));
                }
            } else {
                if (!ws && !GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - 1));
                }
                if (Randomizer.nextInt(99) < curse) {
                    return null;
                }
            }
        }
        return equip;
    }

    public IItem getEquipById(final int equipId) {
        return this.getEquipById(equipId, -1);
    }

    public IItem getEquipById(final int equipId, final int ringId) {
        final Equip nEquip = new Equip(equipId, (short) 0, ringId, (byte) 0);
        nEquip.setQuantity((short) 1);
        final Map<String, Integer> stats = this.getEquipStats(equipId);
        if (stats != null) {
            for (final Map.Entry<String, Integer> stat : stats.entrySet()) {
                final String s;
                final String key = s = stat.getKey();
                switch (s) {
                    case "STR": {
                        nEquip.setStr((short) (int) stat.getValue());
                        continue;
                    }
                    case "DEX": {
                        nEquip.setDex((short) (int) stat.getValue());
                        continue;
                    }
                    case "INT": {
                        nEquip.setInt((short) (int) stat.getValue());
                        continue;
                    }
                    case "LUK": {
                        nEquip.setLuk((short) (int) stat.getValue());
                        continue;
                    }
                    case "PAD": {
                        nEquip.setWatk((short) (int) stat.getValue());
                        continue;
                    }
                    case "PDD": {
                        nEquip.setWdef((short) (int) stat.getValue());
                        continue;
                    }
                    case "MAD": {
                        nEquip.setMatk((short) (int) stat.getValue());
                        continue;
                    }
                    case "MDD": {
                        nEquip.setMdef((short) (int) stat.getValue());
                        continue;
                    }
                    case "ACC": {
                        nEquip.setAcc((short) (int) stat.getValue());
                        continue;
                    }
                    case "EVA": {
                        nEquip.setAvoid((short) (int) stat.getValue());
                        continue;
                    }
                    case "Speed": {
                        nEquip.setSpeed((short) (int) stat.getValue());
                        continue;
                    }
                    case "Jump": {
                        nEquip.setJump((short) (int) stat.getValue());
                        continue;
                    }
                    case "MHP": {
                        nEquip.setHp((short) (int) stat.getValue());
                        continue;
                    }
                    case "MMP": {
                        nEquip.setMp((short) (int) stat.getValue());
                        continue;
                    }
                    case "MHPr": {
                        nEquip.setHpR((short) (int) stat.getValue());
                        continue;
                    }
                    case "MMPr": {
                        nEquip.setMpR((short) (int) stat.getValue());
                        continue;
                    }
                    case "tuc": {
                        nEquip.setUpgradeSlots(stat.getValue().byteValue());
                        continue;
                    }
                    case "Craft": {
                        nEquip.setHands(stat.getValue().shortValue());
                        continue;
                    }
                    case "durability": {
                        nEquip.setDurability(stat.getValue());
                        continue;
                    }
                }
            }
        }
        this.equipCache.put(equipId, nEquip);
        return nEquip.copy();
    }

    private short getRandStat(final int defaultValue) {
        if (defaultValue == 0) {
            return 0;
        }
        int maxRange = 5;
        double defaultMaxRange = 0.1;
        final int tmp = MapleItemInformationProvider.rand.nextInt(1200);
        if (tmp >= 1199) {
            maxRange = 100;
            defaultMaxRange = 2.0;
        } else if (tmp >= 1099) {
            maxRange = 80;
            defaultMaxRange = 1.7;
        } else if (tmp >= 999) {
            maxRange = 65;
            defaultMaxRange = 1.5;
        } else if (tmp >= 960) {
            maxRange = 55;
            defaultMaxRange = 1.3;
        } else if (tmp >= 930) {
            maxRange = 45;
            defaultMaxRange = 1.0;
        } else if (tmp >= 850) {
            maxRange = 35;
            defaultMaxRange = 0.8;
        } else if (tmp >= 706) {
            maxRange = 25;
            defaultMaxRange = 0.6;
        } else if (tmp >= 386) {
            maxRange = 15;
            defaultMaxRange = 0.4;
        } else {
            maxRange = 10;
            defaultMaxRange = 0.2;
        }
        final int lMaxRange = (int) Math.min(Math.ceil(defaultValue * defaultMaxRange), maxRange);
        return (short) (defaultValue - lMaxRange + Math.floor(Math.random() * (lMaxRange * 2 + 1)));
    }

    public Equip randomizeStats(final Equip equip) {
        equip.setStr(this.getRandStat(equip.getStr()));
        equip.setDex(this.getRandStat(equip.getDex()));
        equip.setInt(this.getRandStat(equip.getInt()));
        equip.setLuk(this.getRandStat(equip.getLuk()));
        equip.setMatk(this.getRandStat(equip.getMatk()));
        equip.setWatk(this.getRandStat(equip.getWatk()));
        equip.setAcc(this.getRandStat(equip.getAcc()));
        equip.setAvoid(this.getRandStat(equip.getAvoid()));
        equip.setJump(this.getRandStat(equip.getJump()));
        equip.setHands(this.getRandStat(equip.getHands()));
        equip.setSpeed(this.getRandStat(equip.getSpeed()));
        equip.setWdef(this.getRandStat(equip.getWdef()));
        equip.setMdef(this.getRandStat(equip.getMdef()));
        equip.setHp(this.getRandStat(equip.getHp()));
        equip.setMp(this.getRandStat(equip.getMp()));
        return equip;
    }

    public MapleStatEffect getItemEffect(final int itemId) {
        MapleStatEffect ret = this.itemEffects.get(itemId);
        if (ret == null) {
            final MapleData item = this.getItemData(itemId);
            if (item == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData(item.getChildByPath("spec"), itemId);
            this.itemEffects.put(itemId, ret);
        }
        return ret;
    }

    public List<Pair<Integer, Integer>> getSummonMobs(final int itemId) {
        if (this.summonMobCache.containsKey(itemId)) {
            return this.summonMobCache.get(itemId);
        }
        if (!GameConstants.isSummonSack(itemId)) {
            return null;
        }
        final MapleData data = this.getItemData(itemId).getChildByPath("mob");
        if (data == null) {
            return null;
        }
        final List<Pair<Integer, Integer>> mobPairs = new ArrayList<Pair<Integer, Integer>>();
        for (final MapleData child : data.getChildren()) {
            mobPairs.add(new Pair<Integer, Integer>(MapleDataTool.getIntConvert("id", child), MapleDataTool.getIntConvert("prob", child)));
        }
        this.summonMobCache.put(itemId, mobPairs);
        return mobPairs;
    }

    public int getCardMobId(final int id) {
        if (id == 0) {
            return 0;
        }
        if (this.monsterBookID.containsKey(id)) {
            return this.monsterBookID.get(id);
        }
        final MapleData data = this.getItemData(id);
        final int monsterid = MapleDataTool.getIntConvert("info/mob", data, 0);
        if (monsterid == 0) {
            return 0;
        }
        this.monsterBookID.put(id, monsterid);
        return this.monsterBookID.get(id);
    }

    public int getWatkForProjectile(final int itemId) {
        Integer atk = this.projectileWatkCache.get(itemId);
        if (atk != null) {
            return atk;
        }
        final MapleData data = this.getItemData(itemId);
        atk = MapleDataTool.getInt("info/incPAD", data, 0);
        this.projectileWatkCache.put(itemId, atk);
        return atk;
    }

    public boolean canScroll(final int scrollid, final int itemid) {
        return scrollid / 100 % 100 == itemid / 10000 % 100;
    }

    public String getName(final int itemId) {
        if (this.nameCache.containsKey(itemId)) {
            return this.nameCache.get(itemId);
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("name", strings, null);
        this.nameCache.put(itemId, ret);
        return ret;
    }

    public String getDesc(final int itemId) {
        if (this.descCache.containsKey(itemId)) {
            return this.descCache.get(itemId);
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("desc", strings, null);
        this.descCache.put(itemId, ret);
        return ret;
    }

    public String getMsg(final int itemId) {
        if (this.msgCache.containsKey(itemId)) {
            return this.msgCache.get(itemId);
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("msg", strings, null);
        this.msgCache.put(itemId, ret);
        return ret;
    }

    public short getItemMakeLevel(final int itemId) {
        if (this.itemMakeLevel.containsKey(itemId)) {
            return this.itemMakeLevel.get(itemId);
        }
        if (itemId / 10000 != 400) {
            return 0;
        }
        final short lvl = (short) MapleDataTool.getIntConvert("info/lv", this.getItemData(itemId), 0);
        this.itemMakeLevel.put(itemId, lvl);
        return lvl;
    }

    public byte isConsumeOnPickup(final int itemId) {
        if (this.consumeOnPickupCache.containsKey(itemId)) {
            return this.consumeOnPickupCache.get(itemId);
        }
        final MapleData data = this.getItemData(itemId);
        byte consume = (byte) MapleDataTool.getIntConvert("spec/consumeOnPickup", data, 0);
        if (consume == 0) {
            consume = (byte) MapleDataTool.getIntConvert("specEx/consumeOnPickup", data, 0);
        }
        if (consume == 1 && MapleDataTool.getIntConvert("spec/party", this.getItemData(itemId), 0) > 0) {
            consume = 2;
        }
        this.consumeOnPickupCache.put(itemId, consume);
        return consume;
    }

    public boolean isDropRestricted(final int itemId) {
        if (this.dropRestrictionCache.containsKey(itemId)) {
            return this.dropRestrictionCache.get(itemId);
        }
        final MapleData data = this.getItemData(itemId);
        boolean trade = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1 || MapleDataTool.getIntConvert("info/quest", data, 0) == 1) {
            trade = true;
        }
        this.dropRestrictionCache.put(itemId, trade);
        return trade;
    }

    public boolean isPickupRestricted(final int itemId) {
        if (this.pickupRestrictionCache.containsKey(itemId)) {
            return this.pickupRestrictionCache.get(itemId);
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/only", this.getItemData(itemId), 0) == 1;
        this.pickupRestrictionCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public boolean isAccountShared(final int itemId) {
        if (this.accCache.containsKey(itemId)) {
            return this.accCache.get(itemId);
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/accountSharable", this.getItemData(itemId), 0) == 1;
        this.accCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public int getStateChangeItem(final int itemId) {
        if (this.stateChangeCache.containsKey(itemId)) {
            return this.stateChangeCache.get(itemId);
        }
        final int triggerItem = MapleDataTool.getIntConvert("info/stateChangeItem", this.getItemData(itemId), 0);
        this.stateChangeCache.put(itemId, triggerItem);
        return triggerItem;
    }

    public int getMeso(final int itemId) {
        if (this.mesoCache.containsKey(itemId)) {
            return this.mesoCache.get(itemId);
        }
        final int triggerItem = MapleDataTool.getIntConvert("info/meso", this.getItemData(itemId), 0);
        this.mesoCache.put(itemId, triggerItem);
        return triggerItem;
    }

    public boolean isKarmaEnabled(final int itemId) {
        if (this.karmaEnabledCache.containsKey(itemId)) {
            return this.karmaEnabledCache.get(itemId) == 1;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", this.getItemData(itemId), 0);
        this.karmaEnabledCache.put(itemId, iRestricted);
        return iRestricted == 1;
    }

    public boolean isPKarmaEnabled(final int itemId) {
        if (this.karmaEnabledCache.containsKey(itemId)) {
            return this.karmaEnabledCache.get(itemId) == 2;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", this.getItemData(itemId), 0);
        this.karmaEnabledCache.put(itemId, iRestricted);
        return iRestricted == 2;
    }

    public boolean isPickupBlocked(final int itemId) {
        if (this.blockPickupCache.containsKey(itemId)) {
            return this.blockPickupCache.get(itemId);
        }
        final boolean iRestricted = MapleDataTool.getIntConvert("info/pickUpBlock", this.getItemData(itemId), 0) == 1;
        this.blockPickupCache.put(itemId, iRestricted);
        return iRestricted;
    }

    public boolean isLogoutExpire(final int itemId) {
        if (this.logoutExpireCache.containsKey(itemId)) {
            return this.logoutExpireCache.get(itemId);
        }
        final boolean iRestricted = MapleDataTool.getIntConvert("info/expireOnLogout", this.getItemData(itemId), 0) == 1;
        this.logoutExpireCache.put(itemId, iRestricted);
        return iRestricted;
    }

    public boolean cantSell(final int itemId) {
        if (this.notSaleCache.containsKey(itemId)) {
            return this.notSaleCache.get(itemId);
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/notSale", this.getItemData(itemId), 0) == 1;
        this.notSaleCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public Pair<Integer, List<StructRewardItem>> getRewardItem(final int itemid) {
        if (this.RewardItem.containsKey(itemid)) {
            return this.RewardItem.get(itemid);
        }
        final MapleData data = this.getItemData(itemid);
        if (data == null) {
            return null;
        }
        final MapleData rewards = data.getChildByPath("reward");
        if (rewards == null) {
            return null;
        }
        int totalprob = 0;
        final List<StructRewardItem> all = new ArrayList<StructRewardItem>();
        for (final MapleData reward : rewards) {
            final StructRewardItem struct = new StructRewardItem();
            struct.itemid = MapleDataTool.getInt("item", reward, 0);
            struct.prob = (byte) MapleDataTool.getInt("prob", reward, 0);
            struct.quantity = (short) MapleDataTool.getInt("count", reward, 0);
            struct.effect = MapleDataTool.getString("Effect", reward, "");
            struct.worldmsg = MapleDataTool.getString("worldMsg", reward, null);
            struct.period = MapleDataTool.getInt("period", reward, -1);
            totalprob += struct.prob;
            all.add(struct);
        }
        final Pair<Integer, List<StructRewardItem>> toreturn = new Pair<Integer, List<StructRewardItem>>(totalprob, all);
        this.RewardItem.put(itemid, toreturn);
        return toreturn;
    }

    public Map<String, Integer> getSkillStats(final int itemId) {
        if (this.SkillStatsCache.containsKey(itemId)) {
            return this.SkillStatsCache.get(itemId);
        }
        if (itemId / 10000 != 228 && itemId / 10000 != 229 && itemId / 10000 != 562) {
            return null;
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        final Map<String, Integer> ret = new LinkedHashMap<String, Integer>();
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), MapleDataTool.getIntConvert(data));
            }
        }
        ret.put("masterLevel", MapleDataTool.getInt("masterLevel", info, 0));
        ret.put("reqSkillLevel", MapleDataTool.getInt("reqSkillLevel", info, 0));
        ret.put("success", MapleDataTool.getInt("success", info, 0));
        final MapleData skill = info.getChildByPath("skill");
        for (int i = 0; i < skill.getChildren().size(); ++i) {
            ret.put("skillid" + i, MapleDataTool.getInt(Integer.toString(i), skill, 0));
        }
        this.SkillStatsCache.put(itemId, ret);
        return ret;
    }

    public List<Integer> petsCanConsume(final int itemId) {
        if (this.petsCanConsumeCache.get(itemId) != null) {
            return this.petsCanConsumeCache.get(itemId);
        }
        final List<Integer> ret = new ArrayList<Integer>();
        final MapleData data = this.getItemData(itemId);
        if (data == null || data.getChildByPath("spec") == null) {
            return ret;
        }
        int curPetId = 0;
        for (final MapleData c : data.getChildByPath("spec")) {
            try {
                Integer.parseInt(c.getName());
            } catch (NumberFormatException e) {
                continue;
            }
            curPetId = MapleDataTool.getInt(c, 0);
            if (curPetId == 0) {
                break;
            }
            ret.add(curPetId);
        }
        this.petsCanConsumeCache.put(itemId, ret);
        return ret;
    }

    public boolean isQuestItem(final int itemId) {
        if (this.isQuestItemCache.containsKey(itemId)) {
            return this.isQuestItemCache.get(itemId);
        }
        final boolean questItem = MapleDataTool.getIntConvert("info/quest", this.getItemData(itemId), 0) == 1;
        this.isQuestItemCache.put(itemId, questItem);
        return questItem;
    }

    public Pair<Integer, List<Integer>> questItemInfo(final int itemId) {
        if (this.questItems.containsKey(itemId)) {
            return this.questItems.get(itemId);
        }
        if (itemId / 10000 != 422 || this.getItemData(itemId) == null) {
            return null;
        }
        final MapleData itemD = this.getItemData(itemId).getChildByPath("info");
        if (itemD == null || itemD.getChildByPath("consumeItem") == null) {
            return null;
        }
        final List<Integer> consumeItems = new ArrayList<Integer>();
        for (final MapleData consume : itemD.getChildByPath("consumeItem")) {
            consumeItems.add(MapleDataTool.getInt(consume, 0));
        }
        final Pair<Integer, List<Integer>> questItem = new Pair<Integer, List<Integer>>(MapleDataTool.getIntConvert("questId", itemD, 0), consumeItems);
        this.questItems.put(itemId, questItem);
        return questItem;
    }

    public boolean itemExists(final int itemId) {
        return GameConstants.getInventoryType(itemId) != MapleInventoryType.UNDEFINED && this.getItemData(itemId) != null;
    }

    public boolean isCash(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH;
        }
        return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || this.getEquipStats(itemId).get("cash") > 0;
    }

    public MapleInventoryType getInventoryType(final int itemId) {
        if (this.inventoryTypeCache.containsKey(itemId)) {
            return this.inventoryTypeCache.get(itemId);
        }
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = this.itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.getByWZName(topDir.getName());
                    this.inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
                if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.getByWZName(topDir.getName());
                    this.inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        root = this.equipData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.EQUIP;
                    this.inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        final MapleInventoryType ret = MapleInventoryType.UNDEFINED;
        this.inventoryTypeCache.put(itemId, ret);
        return ret;
    }

    public short getPetFlagInfo(final int itemId) {
        short flag = 0;
        if (itemId / 10000 != 500) {
            return flag;
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return flag;
        }
        if (MapleDataTool.getIntConvert("info/pickupItem", item, 0) > 0) {
            flag |= 0x1;
        }
        if (MapleDataTool.getIntConvert("info/longRange", item, 0) > 0) {
            flag |= 0x2;
        }
        if (MapleDataTool.getIntConvert("info/pickupAll", item, 0) > 0) {
            flag |= 0x4;
        }
        if (MapleDataTool.getIntConvert("info/sweepForDrop", item, 0) > 0) {
            flag |= 0x10;
        }
        if (MapleDataTool.getIntConvert("info/consumeHP", item, 0) > 0) {
            flag |= 0x20;
        }
        if (MapleDataTool.getIntConvert("info/consumeMP", item, 0) > 0) {
            flag |= 0x40;
        }
        return flag;
    }

    public boolean isKarmaAble(final int itemId) {
        if (this.karmaCache.containsKey(itemId)) {
            return this.karmaCache.get(itemId);
        }
        final MapleData data = this.getItemData(itemId);
        final boolean bRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", data, 0) > 0;
        this.karmaCache.put(itemId, bRestricted);
        return bRestricted;
    }

    public List<Pair<String, Integer>> getItemLevelupStats(final int itemId, final int level, final boolean timeless) {
        final List<Pair<String, Integer>> list = new LinkedList<Pair<String, Integer>>();
        final MapleData data = this.getItemData(itemId);
        final MapleData data2 = data.getChildByPath("info").getChildByPath("level");
        if (data2 != null) {
            final MapleData data3 = data2.getChildByPath("info").getChildByPath(Integer.toString(level));
            if (data3 != null) {
                for (final MapleData da : data3.getChildren()) {
                    if (Math.random() < 0.9) {
                        if (da.getName().startsWith("incDEXMin")) {
                            list.add(new Pair<String, Integer>("incDEX", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incDEXMax")))));
                        } else if (da.getName().startsWith("incSTRMin")) {
                            list.add(new Pair<String, Integer>("incSTR", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incSTRMax")))));
                        } else if (da.getName().startsWith("incINTMin")) {
                            list.add(new Pair<String, Integer>("incINT", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incINTMax")))));
                        } else if (da.getName().startsWith("incLUKMin")) {
                            list.add(new Pair<String, Integer>("incLUK", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incLUKMax")))));
                        } else if (da.getName().startsWith("incMHPMin")) {
                            list.add(new Pair<String, Integer>("incMHP", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incMHPMax")))));
                        } else if (da.getName().startsWith("incMMPMin")) {
                            list.add(new Pair<String, Integer>("incMMP", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incMMPMax")))));
                        } else if (da.getName().startsWith("incPADMin")) {
                            list.add(new Pair<String, Integer>("incPAD", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incPADMax")))));
                        } else if (da.getName().startsWith("incMADMin")) {
                            list.add(new Pair<String, Integer>("incMAD", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incMADMax")))));
                        } else if (da.getName().startsWith("incPDDMin")) {
                            list.add(new Pair<String, Integer>("incPDD", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incPDDMax")))));
                        } else if (da.getName().startsWith("incMDDMin")) {
                            list.add(new Pair<String, Integer>("incMDD", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incMDDMax")))));
                        } else if (da.getName().startsWith("incACCMin")) {
                            list.add(new Pair<String, Integer>("incACC", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incACCMax")))));
                        } else if (da.getName().startsWith("incEVAMin")) {
                            list.add(new Pair<String, Integer>("incEVA", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incEVAMax")))));
                        } else if (da.getName().startsWith("incSpeedMin")) {
                            list.add(new Pair<String, Integer>("incSpeed", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incSpeedMax")))));
                        } else {
                            if (!da.getName().startsWith("incJumpMin")) {
                                continue;
                            }
                            list.add(new Pair<String, Integer>("incJump", this.rand(MapleDataTool.getInt(da), MapleDataTool.getInt(data3.getChildByPath("incJumpMax")))));
                        }
                    }
                }
            }
        }
        return list;
    }

    public boolean isUntradeableOnEquip(final int itemId) {
        if (this.onEquipUntradableCache.containsKey(itemId)) {
            return this.onEquipUntradableCache.get(itemId);
        }
        final boolean untradableOnEquip = MapleDataTool.getIntConvert("info/equipTradeBlock", this.getItemData(itemId), 0) > 0;
        this.onEquipUntradableCache.put(itemId, untradableOnEquip);
        return untradableOnEquip;
    }

    public int getExpCache(final int itemId) {
        if (this.getExpCache.containsKey(itemId)) {
            return this.getExpCache.get(itemId);
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return 0;
        }
        int pEntry = 0;
        final MapleData pData = item.getChildByPath("spec/exp");
        if (pData == null) {
            return 0;
        }
        pEntry = MapleDataTool.getInt(pData);
        this.getExpCache.put(itemId, pEntry);
        return pEntry;
    }

    public boolean hairExists(final int hair) {
        return this.hairList.containsKey(hair);
    }

    public boolean faceExists(final int face) {
        return this.faceList.containsKey(face);
    }

    public int getLimitBreak(final int itemId) {
        if (this.getEquipStats(itemId) == null || !this.getEquipStats(itemId).containsKey("limitBreak")) {
            return 999999;
        }
        return this.getEquipStats(itemId).get("limitBreak");
    }

    public List<Pair<Integer, String>> getAllItems2() {
        final List<Pair<Integer, String>> itemPairs = new ArrayList<Pair<Integer, String>>();
        MapleData itemsData = this.stringData.getData("Cash.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Consume.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder2 : eqpType.getChildren()) {
                itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder2.getName()), MapleDataTool.getString("name", itemFolder2, "NO-NAME")));
            }
        }
        itemsData = this.stringData.getData("Etc.img").getChildByPath("Etc");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Ins.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Pet.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair<Integer, String>(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        return itemPairs;
    }

    public final void loadHairFace(final boolean reload) {
        if (reload) {
            this.hairList.clear();
            this.faceList.clear();
        }
        if (!this.hairList.isEmpty() || !this.faceList.isEmpty()) {
            return;
        }
        final String[] array;
        final String[] types = array = new String[]{"Hair", "Face"};
        for (final String type : array) {
            MapleDataDirectoryEntry data = null;
            for (final MapleDataDirectoryEntry d : this.chrData.getRoot().getSubdirectories()) {
                if (d.getName().equals(type)) {
                    data = d;
                    break;
                }
            }
            if (data != null) {
                for (final MapleData c : this.stringData.getData("Item.img").getChildByPath("Eqp/" + type)) {
                    if (data.getEntry(StringUtil.getLeftPaddedStr(c.getName() + ".img", '0', 12)) != null) {
                        final int dataid = Integer.parseInt(c.getName());
                        final String name = MapleDataTool.getString("name", c, "无名字");
                        if (type.equals("Hair")) {
                            this.hairList.put(dataid, name);
                        } else {
                            this.faceList.put(dataid, name);
                        }
                    }
                }
            }
        }
    }

    public int getTotalStat(final Equip equip) {
        return equip.getStr() + equip.getDex() + equip.getInt() + equip.getLuk() + equip.getMatk() * 5 + equip.getWatk() * 5;
    }

    static {
        instance = new MapleItemInformationProvider();
        rand = new Random();
        whiteItemList = new HashMap<Integer, Short>();//物品上限白名单
        whiteItemList.put(4001126, Short.valueOf("30000"));//枫叶
        whiteItemList.put(46354, Short.valueOf("234"));//心跳箱子
    }
}
