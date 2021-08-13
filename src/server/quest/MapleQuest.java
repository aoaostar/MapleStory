package server.quest;

import client.MapleCharacter;
import client.MapleQuestStatus;
import constants.GameConstants;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import scripting.NPCScriptManager;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;

public class MapleQuest implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private static final Map<Integer, MapleQuest> quests;
    private static MapleDataProvider questData;
    private static MapleData actions;
    private static MapleData requirements;
    private static MapleData info;
    private static MapleData pinfo;
    protected int id;
    protected List<MapleQuestRequirement> startReqs;
    protected List<MapleQuestRequirement> completeReqs;
    protected List<MapleQuestAction> startActs;
    protected List<MapleQuestAction> completeActs;
    protected Map<String, List<Pair<String, Pair<String, Integer>>>> partyQuestInfo;
    protected Map<Integer, Integer> relevantMobs;
    private boolean autoStart;
    private boolean autoPreComplete;
    private boolean repeatable;
    private boolean customend;
    private int viewMedalItem;
    private int selectedSkillID;
    protected String name;
    
    private static boolean loadQuest(final MapleQuest ret, final int id) throws NullPointerException {
        final MapleData basedata1 = MapleQuest.requirements.getChildByPath(String.valueOf(id));
        final MapleData basedata2 = MapleQuest.actions.getChildByPath(String.valueOf(id));
        if (basedata1 == null || basedata2 == null) {
            return false;
        }
        final MapleData startReqData = basedata1.getChildByPath("0");
        if (startReqData != null) {
            final List<MapleData> startC = startReqData.getChildren();
            if (startC != null && startC.size() > 0) {
                for (final MapleData startReq : startC) {
                    final MapleQuestRequirementType type = MapleQuestRequirementType.getByWZName(startReq.getName());
                    if (type.equals(MapleQuestRequirementType.interval)) {
                        ret.repeatable = true;
                    }
                    final MapleQuestRequirement req = new MapleQuestRequirement(ret, type, startReq);
                    if (req.getType().equals(MapleQuestRequirementType.mob)) {
                        for (final MapleData mob : startReq.getChildren()) {
                            ret.relevantMobs.put(MapleDataTool.getInt(mob.getChildByPath("id")), MapleDataTool.getInt(mob.getChildByPath("count"), 0));
                        }
                    }
                    ret.startReqs.add(req);
                }
            }
        }
        final MapleData completeReqData = basedata1.getChildByPath("1");
        if (completeReqData != null) {
            final List<MapleData> completeC = completeReqData.getChildren();
            if (completeC != null && completeC.size() > 0) {
                for (final MapleData completeReq : completeC) {
                    final MapleQuestRequirement req = new MapleQuestRequirement(ret, MapleQuestRequirementType.getByWZName(completeReq.getName()), completeReq);
                    if (req.getType().equals(MapleQuestRequirementType.mob)) {
                        for (final MapleData mob : completeReq.getChildren()) {
                            ret.relevantMobs.put(MapleDataTool.getInt(mob.getChildByPath("id")), MapleDataTool.getInt(mob.getChildByPath("count"), 0));
                        }
                    }
                    else if (req.getType().equals(MapleQuestRequirementType.endscript)) {
                        ret.customend = true;
                    }
                    ret.completeReqs.add(req);
                }
            }
        }
        final MapleData startActData = basedata2.getChildByPath("0");
        if (startActData != null) {
            final List<MapleData> startC2 = startActData.getChildren();
            for (final MapleData startAct : startC2) {
                ret.startActs.add(new MapleQuestAction(MapleQuestActionType.getByWZName(startAct.getName()), startAct, ret));
            }
        }
        final MapleData completeActData = basedata2.getChildByPath("1");
        if (completeActData != null) {
            final List<MapleData> completeC2 = completeActData.getChildren();
            for (final MapleData completeAct : completeC2) {
                ret.completeActs.add(new MapleQuestAction(MapleQuestActionType.getByWZName(completeAct.getName()), completeAct, ret));
            }
        }
        final MapleData questInfo = MapleQuest.info.getChildByPath(String.valueOf(id));
        if (questInfo != null) {
            ret.name = MapleDataTool.getString("name", questInfo, "");
            ret.autoStart = (MapleDataTool.getInt("autoStart", questInfo, 0) == 1);
            ret.autoPreComplete = (MapleDataTool.getInt("autoPreComplete", questInfo, 0) == 1);
            ret.viewMedalItem = MapleDataTool.getInt("viewMedalItem", questInfo, 0);
            ret.selectedSkillID = MapleDataTool.getInt("selectedSkillID", questInfo, 0);
        }
        final MapleData pquestInfo = MapleQuest.pinfo.getChildByPath(String.valueOf(id));
        if (pquestInfo != null) {
            for (final MapleData d : pquestInfo.getChildByPath("rank")) {
                final List<Pair<String, Pair<String, Integer>>> pInfo = new ArrayList<Pair<String, Pair<String, Integer>>>();
                for (final MapleData c : d) {
                    for (final MapleData b : c) {
                        pInfo.add(new Pair<String, Pair<String, Integer>>(c.getName(), new Pair<String, Integer>(b.getName(), MapleDataTool.getInt(b, 0))));
                    }
                }
                ret.partyQuestInfo.put(d.getName(), pInfo);
            }
        }
        return true;
    }
    
    public static void initQuests() {
        MapleQuest.questData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Quest.wz"));
        MapleQuest.actions = MapleQuest.questData.getData("Act.img");
        MapleQuest.requirements = MapleQuest.questData.getData("Check.img");
        MapleQuest.info = MapleQuest.questData.getData("QuestInfo.img");
        MapleQuest.pinfo = MapleQuest.questData.getData("PQuest.img");
    }
    
    public static void clearQuests() {
        MapleQuest.quests.clear();
        initQuests();
    }
    
    public static MapleQuest getInstance(final int id) {
        MapleQuest ret = MapleQuest.quests.get(id);
        if (ret == null) {
            ret = new MapleQuest(id);
            try {
                if (GameConstants.isCustomQuest(id) || !loadQuest(ret, id)) {
                    ret = new MapleCustomQuest(id);
                }
                MapleQuest.quests.put(id, ret);
            }
            catch (NullPointerException ex) {
                ex.printStackTrace();
                FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
                FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Caused by questID " + id);
                System.out.println("Caused by questID " + id);
                return new MapleCustomQuest(id);
            }
        }
        return ret;
    }
    
    protected MapleQuest(final int id) {
        this.autoStart = false;
        this.autoPreComplete = false;
        this.repeatable = false;
        this.customend = false;
        this.viewMedalItem = 0;
        this.selectedSkillID = 0;
        this.name = "";
        this.relevantMobs = new LinkedHashMap<Integer, Integer>();
        this.startReqs = new LinkedList<MapleQuestRequirement>();
        this.completeReqs = new LinkedList<MapleQuestRequirement>();
        this.startActs = new LinkedList<MapleQuestAction>();
        this.completeActs = new LinkedList<MapleQuestAction>();
        this.partyQuestInfo = new LinkedHashMap<String, List<Pair<String, Pair<String, Integer>>>>();
        this.id = id;
    }
    
    public List<Pair<String, Pair<String, Integer>>> getInfoByRank(final String rank) {
        return this.partyQuestInfo.get(rank);
    }
    
    public int getSkillID() {
        return this.selectedSkillID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean canStart(final MapleCharacter c, final Integer npcid) {
        if (c.getQuest(this).getStatus() != 0 && (c.getQuest(this).getStatus() != 2 || !this.repeatable)) {
            return false;
        }
        for (final MapleQuestRequirement r : this.startReqs) {
            if (!r.check(c, npcid)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canComplete(final MapleCharacter c, final Integer npcid) {
        if (c.getQuest(this).getStatus() != 1) {
            return false;
        }
        for (final MapleQuestRequirement r : this.completeReqs) {
            if (!r.check(c, npcid)) {
                return false;
            }
        }
        return true;
    }
    
    public void RestoreLostItem(final MapleCharacter c, final int itemid) {
        for (final MapleQuestAction a : this.startActs) {
            if (a.RestoreLostItem(c, itemid)) {
                break;
            }
        }
    }
    
    public void start(final MapleCharacter c, final int npc) {
        if ((this.autoStart || this.checkNPCOnMap(c, npc)) && this.canStart(c, npc)) {
            for (final MapleQuestAction a : this.startActs) {
                if (!a.checkEnd(c, null)) {
                    return;
                }
            }
            for (final MapleQuestAction a : this.startActs) {
                a.runStart(c, null);
            }
            if (!this.customend) {
                this.forceStart(c, npc, null);
            }
            else {
                NPCScriptManager.getInstance().endQuest(c.getClient(), npc, this.getId(), true);
            }
        }
    }
    
    public void complete(final MapleCharacter c, final int npc) {
        this.complete(c, npc, null);
    }
    
    public void complete(final MapleCharacter c, final int npc, final Integer selection) {
        if ((this.autoPreComplete || this.checkNPCOnMap(c, npc)) && this.canComplete(c, npc)) {
            if (npc != 9010000) {
                for (final MapleQuestAction a : this.completeActs) {
                    if (!a.checkEnd(c, selection)) {
                        return;
                    }
                }
                this.forceComplete(c, npc);
                for (final MapleQuestAction a : this.completeActs) {
                    a.runEnd(c, selection);
                }
            }
            c.getClient().getSession().write(MaplePacketCreator.showSpecialEffect(10));
            c.getMap().broadcastMessage(c, MaplePacketCreator.showSpecialEffect(c.getId(), 10), false);
        }
        else {
            if (npc != 9010000) {
                for (final MapleQuestAction a : this.completeActs) {
                    if (!a.checkEnd(c, selection)) {
                        return;
                    }
                }
                this.forceComplete(c, npc);
                for (final MapleQuestAction a : this.completeActs) {
                    a.runEnd(c, selection);
                }
            }
            c.getClient().getSession().write(MaplePacketCreator.showSpecialEffect(10));
            c.getMap().broadcastMessage(c, MaplePacketCreator.showSpecialEffect(c.getId(), 10), false);
        }
    }
    
    public void forfeit(final MapleCharacter c) {
        if (c.getQuest(this).getStatus() != 1) {
            return;
        }
        final MapleQuestStatus oldStatus = c.getQuest(this);
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte)0);
        newStatus.setForfeited(oldStatus.getForfeited() + 1);
        newStatus.setCompletionTime(oldStatus.getCompletionTime());
        c.updateQuest(newStatus);
    }
    
    public void forceStart(final MapleCharacter c, final int npc, final String customData) {
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte)1, npc);
        newStatus.setForfeited(c.getQuest(this).getForfeited());
        newStatus.setCompletionTime(c.getQuest(this).getCompletionTime());
        newStatus.setCustomData(customData);
        c.updateQuest(newStatus);
    }
    
    public void forceComplete(final MapleCharacter c, final int npc) {
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte)2, npc);
        newStatus.setForfeited(c.getQuest(this).getForfeited());
        c.updateQuest(newStatus);
        c.getClient().getSession().write(MaplePacketCreator.showSpecialEffect(10));
        c.getMap().broadcastMessage(c, MaplePacketCreator.showSpecialEffect(c.getId(), 10), false);
    }
    
    public int getId() {
        return this.id;
    }
    
    public Map<Integer, Integer> getRelevantMobs() {
        return this.relevantMobs;
    }
    
    private boolean checkNPCOnMap(final MapleCharacter player, final int npcid) {
        return (GameConstants.isEvan(player.getJob()) && npcid == 1013000) || (player.getMap() != null && player.getMap().containsNPC(npcid));
    }
    
    public int getMedalItem() {
        return this.viewMedalItem;
    }
    
    static {
        quests = new LinkedHashMap<Integer, MapleQuest>();
    }
    
    public enum MedalQuest
    {
        新手冒险家(29005, 29015, 15, new int[] { 104000000, 104010001, 100000006, 104020000, 100000000, 100010000, 100040000, 100040100, 101010103, 101020000, 101000000, 102000000, 101030104, 101030406, 102020300, 103000000, 102050000, 103010001, 103030200, 110000000 }), 
        ElNath(29006, 29012, 50, new int[] { 200000000, 200010100, 200010300, 200080000, 200080100, 211000000, 211030000, 211040300, 211040400, 211040401 }), 
        LudusLake(29007, 29012, 40, new int[] { 222000000, 222010400, 222020000, 220000000, 220020300, 220040200, 221020701, 221000000, 221030600, 221040400 }), 
        Underwater(29008, 29012, 40, new int[] { 230000000, 230010400, 230010200, 230010201, 230020000, 230020201, 230030100, 230040000, 230040200, 230040400 }), 
        MuLung(29009, 29012, 50, new int[] { 251000000, 251010200, 251010402, 251010500, 250010500, 250010504, 250000000, 250010300, 250010304, 250020300 }), 
        NihalDesert(29010, 29012, 70, new int[] { 261030000, 261020401, 261020000, 261010100, 261000000, 260020700, 260020300, 260000000, 260010600, 260010300 }), 
        MinarForest(29011, 29012, 70, new int[] { 240000000, 240010200, 240010800, 240020401, 240020101, 240030000, 240040400, 240040511, 240040521, 240050000 }), 
        Sleepywood(29014, 29015, 50, new int[] { 105040300, 105070001, 105040305, 105090200, 105090300, 105090301, 105090312, 105090500, 105090900, 105080000 });
        
        public int questid;
        public int level;
        public int lquestid;
        public int[] maps;
        
        MedalQuest(final int questid, final int lquestid, final int level, final int[] maps) {
            this.questid = questid;
            this.level = level;
            this.lquestid = lquestid;
            this.maps = maps;
        }
    }
}
