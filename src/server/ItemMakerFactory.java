package server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;

public class ItemMakerFactory
{
    private static final ItemMakerFactory instance;
    protected Map<Integer, ItemMakerCreateEntry> createCache;
    protected Map<Integer, GemCreateEntry> gemCache;
    
    public static ItemMakerFactory getInstance() {
        return ItemMakerFactory.instance;
    }
    
    protected ItemMakerFactory() {
        this.createCache = new HashMap<Integer, ItemMakerCreateEntry>();
        this.gemCache = new HashMap<Integer, GemCreateEntry>();
        final MapleData info = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Etc.wz")).getData("ItemMake.img");
        for (final MapleData dataType : info.getChildren()) {
            final int type = Integer.parseInt(dataType.getName());
            switch (type) {
                case 0: {
                    for (final MapleData itemFolder : dataType.getChildren()) {
                        final int reqLevel = MapleDataTool.getInt("reqLevel", itemFolder, 0);
                        final byte reqMakerLevel = (byte)MapleDataTool.getInt("reqSkillLevel", itemFolder, 0);
                        final int cost = MapleDataTool.getInt("meso", itemFolder, 0);
                        final int quantity = MapleDataTool.getInt("itemNum", itemFolder, 0);
                        final GemCreateEntry ret = new GemCreateEntry(cost, reqLevel, reqMakerLevel, quantity);
                        for (final MapleData rewardNRecipe : itemFolder.getChildren()) {
                            for (final MapleData ind : rewardNRecipe.getChildren()) {
                                if (rewardNRecipe.getName().equals("randomReward")) {
                                    ret.addRandomReward(MapleDataTool.getInt("item", ind, 0), MapleDataTool.getInt("prob", ind, 0));
                                }
                                else {
                                    if (!rewardNRecipe.getName().equals("recipe")) {
                                        continue;
                                    }
                                    ret.addReqRecipe(MapleDataTool.getInt("item", ind, 0), MapleDataTool.getInt("count", ind, 0));
                                }
                            }
                        }
                        this.gemCache.put(Integer.parseInt(itemFolder.getName()), ret);
                    }
                    continue;
                }
                case 1:
                case 2:
                case 4:
                case 8:
                case 16: {
                    for (final MapleData itemFolder : dataType.getChildren()) {
                        final int reqLevel = MapleDataTool.getInt("reqLevel", itemFolder, 0);
                        final byte reqMakerLevel = (byte)MapleDataTool.getInt("reqSkillLevel", itemFolder, 0);
                        final int cost = MapleDataTool.getInt("meso", itemFolder, 0);
                        final int quantity = MapleDataTool.getInt("itemNum", itemFolder, 0);
                        final byte totalupgrades = (byte)MapleDataTool.getInt("tuc", itemFolder, 0);
                        final int stimulator = MapleDataTool.getInt("catalyst", itemFolder, 0);
                        final ItemMakerCreateEntry imt = new ItemMakerCreateEntry(cost, reqLevel, reqMakerLevel, quantity, totalupgrades, stimulator);
                        for (final MapleData Recipe : itemFolder.getChildren()) {
                            for (final MapleData ind : Recipe.getChildren()) {
                                if (Recipe.getName().equals("recipe")) {
                                    imt.addReqItem(MapleDataTool.getInt("item", ind, 0), MapleDataTool.getInt("count", ind, 0));
                                }
                            }
                        }
                        this.createCache.put(Integer.parseInt(itemFolder.getName()), imt);
                    }
                    continue;
                }
            }
        }
    }
    
    public GemCreateEntry getGemInfo(final int itemid) {
        return this.gemCache.get(itemid);
    }
    
    public ItemMakerCreateEntry getCreateInfo(final int itemid) {
        return this.createCache.get(itemid);
    }
    
    static {
        instance = new ItemMakerFactory();
    }
    
    public static class GemCreateEntry
    {
        private final int reqLevel;
        private final int reqMakerLevel;
        private final int cost;
        private final int quantity;
        private final List<Pair<Integer, Integer>> randomReward;
        private final List<Pair<Integer, Integer>> reqRecipe;
        
        public GemCreateEntry(final int cost, final int reqLevel, final int reqMakerLevel, final int quantity) {
            this.randomReward = new ArrayList<Pair<Integer, Integer>>();
            this.reqRecipe = new ArrayList<Pair<Integer, Integer>>();
            this.cost = cost;
            this.reqLevel = reqLevel;
            this.reqMakerLevel = reqMakerLevel;
            this.quantity = quantity;
        }
        
        public int getRewardAmount() {
            return this.quantity;
        }
        
        public List<Pair<Integer, Integer>> getRandomReward() {
            return this.randomReward;
        }
        
        public List<Pair<Integer, Integer>> getReqRecipes() {
            return this.reqRecipe;
        }
        
        public int getReqLevel() {
            return this.reqLevel;
        }
        
        public int getReqSkillLevel() {
            return this.reqMakerLevel;
        }
        
        public int getCost() {
            return this.cost;
        }
        
        protected void addRandomReward(final int itemId, final int prob) {
            this.randomReward.add(new Pair<Integer, Integer>(itemId, prob));
        }
        
        protected void addReqRecipe(final int itemId, final int count) {
            this.reqRecipe.add(new Pair<Integer, Integer>(itemId, count));
        }
    }
    
    public static class ItemMakerCreateEntry
    {
        private final int reqLevel;
        private final int cost;
        private final int quantity;
        private final int stimulator;
        private final byte tuc;
        private final byte reqMakerLevel;
        private final List<Pair<Integer, Integer>> reqItems;
        private final List<Integer> reqEquips;
        
        public ItemMakerCreateEntry(final int cost, final int reqLevel, final byte reqMakerLevel, final int quantity, final byte tuc, final int stimulator) {
            this.reqItems = new ArrayList<Pair<Integer, Integer>>();
            this.reqEquips = new ArrayList<Integer>();
            this.cost = cost;
            this.tuc = tuc;
            this.reqLevel = reqLevel;
            this.reqMakerLevel = reqMakerLevel;
            this.quantity = quantity;
            this.stimulator = stimulator;
        }
        
        public byte getTUC() {
            return this.tuc;
        }
        
        public int getRewardAmount() {
            return this.quantity;
        }
        
        public List<Pair<Integer, Integer>> getReqItems() {
            return this.reqItems;
        }
        
        public List<Integer> getReqEquips() {
            return this.reqEquips;
        }
        
        public int getReqLevel() {
            return this.reqLevel;
        }
        
        public byte getReqSkillLevel() {
            return this.reqMakerLevel;
        }
        
        public int getCost() {
            return this.cost;
        }
        
        public int getStimulator() {
            return this.stimulator;
        }
        
        protected void addReqItem(final int itemId, final int amount) {
            this.reqItems.add(new Pair<Integer, Integer>(itemId, amount));
        }
    }
}
