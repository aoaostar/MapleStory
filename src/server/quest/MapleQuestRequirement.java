package server.quest;

import client.ISkill;
import client.MapleCharacter;
import client.MapleQuestStatus;
import client.SkillFactory;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import constants.GameConstants;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataTool;
import tools.Pair;

public class MapleQuestRequirement implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private MapleQuest quest;
    private MapleQuestRequirementType type;
    private int intStore;
    private String stringStore;
    private List<Pair<Integer, Integer>> dataStore;
    
    public MapleQuestRequirement(final MapleQuest quest, final MapleQuestRequirementType type, final MapleData data) {
        this.type = type;
        this.quest = quest;
        switch (type) {
            case job: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    this.dataStore.add(new Pair<Integer, Integer>(i, MapleDataTool.getInt(child.get(i), -1)));
                }
                break;
            }
            case skill: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    final MapleData childdata = child.get(i);
                    this.dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("acquire"), 0)));
                }
                break;
            }
            case quest: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    final MapleData childdata = child.get(i);
                    this.dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id")), MapleDataTool.getInt(childdata.getChildByPath("state"), 0)));
                }
                break;
            }
            case item: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    final MapleData childdata = child.get(i);
                    this.dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id")), MapleDataTool.getInt(childdata.getChildByPath("count"), 0)));
                }
                break;
            }
            case pettamenessmin:
            case npc:
            case questComplete:
            case pop:
            case interval:
            case mbmin:
            case lvmax:
            case lvmin: {
                this.intStore = MapleDataTool.getInt(data, -1);
                break;
            }
            case end: {
                this.stringStore = MapleDataTool.getString(data, null);
                break;
            }
            case mob: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    final MapleData childdata = child.get(i);
                    this.dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("count"), 0)));
                }
                break;
            }
            case fieldEnter: {
                final MapleData zeroField = data.getChildByPath("0");
                if (zeroField != null) {
                    this.intStore = MapleDataTool.getInt(zeroField);
                    break;
                }
                this.intStore = -1;
                break;
            }
            case mbcard: {
                final List<MapleData> child = data.getChildren();
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (int i = 0; i < child.size(); ++i) {
                    final MapleData childdata = child.get(i);
                    this.dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("min"), 0)));
                }
                break;
            }
            case pet: {
                this.dataStore = new LinkedList<Pair<Integer, Integer>>();
                for (final MapleData child2 : data) {
                    this.dataStore.add(new Pair<Integer, Integer>(-1, MapleDataTool.getInt("id", child2, 0)));
                }
                break;
            }
        }
    }

    public boolean check(MapleCharacter c, Integer npcid) {
        String timeStr;
        Calendar cal;
        switch (this.type) {
            case job:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    if (((Integer)a.getRight()).intValue() == c.getJob() || c.isGM())
                        return true;
                }
                return false;
            case skill:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    boolean acquire = (((Integer)a.getRight()).intValue() > 0);
                    int skill = ((Integer)a.getLeft()).intValue();
                    ISkill skil = SkillFactory.getSkill(skill);
                    if (acquire) {
                        if (skil.isFourthJob()) {
                            if (c.getMasterLevel(skil) == 0)
                                return false;
                            continue;
                        }
                        if (c.getSkillLevel(skil) == 0)
                            return false;
                        continue;
                    }
                    if (c.getSkillLevel(skil) > 0 || c.getMasterLevel(skil) > 0)
                        return false;
                }
                return true;
            case quest:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    MapleQuestStatus q = c.getQuest(MapleQuest.getInstance(((Integer)a.getLeft()).intValue()));
                    int state = ((Integer)a.getRight()).intValue();
                    if (state == 0 || (
                            q == null && state == 0))
                        continue;
                    if (q == null || q.getStatus() != state)
                        return false;
                }
                return true;
            case item:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    int itemId = ((Integer)a.getLeft()).intValue();
                    short quantity = 0;
                    MapleInventoryType iType = GameConstants.getInventoryType(itemId);
                    for (IItem item : c.getInventory(iType).listById(itemId))
                        quantity = (short)(quantity + item.getQuantity());
                    int count = ((Integer)a.getRight()).intValue();
                    if (quantity < count || (count <= 0 && quantity > 0))
                        return false;
                }
                return true;
            case lvmin:
                return (c.getLevel() >= this.intStore);
            case lvmax:
                return (c.getLevel() <= this.intStore);
            case end:
                timeStr = this.stringStore;
                cal = Calendar.getInstance();
                cal.set(Integer.parseInt(timeStr.substring(0, 4)), Integer.parseInt(timeStr.substring(4, 6)), Integer.parseInt(timeStr.substring(6, 8)), Integer.parseInt(timeStr.substring(8, 10)), 0);
                return (cal.getTimeInMillis() >= System.currentTimeMillis());
            case mob:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    int mobId = ((Integer)a.getLeft()).intValue();
                    int killReq = ((Integer)a.getRight()).intValue();
                    if (c.getQuest(this.quest).getMobKills(mobId) < killReq)
                        return false;
                }
                return true;
            case npc:
                return (npcid == null || npcid.intValue() == this.intStore);
            case fieldEnter:
                if (this.intStore != -1)
                    return (this.intStore == c.getMapId());
                return false;
            case mbmin:
                if (c.getMonsterBook().getTotalCards() >= this.intStore)
                    return true;
                return false;
            case mbcard:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    int cardId = ((Integer)a.getLeft()).intValue();
                    int killReq = ((Integer)a.getRight()).intValue();
                    if (c.getMonsterBook().getLevelByCard(cardId) < killReq)
                        return false;
                }
                return true;
            case pop:
                return (c.getFame() <= this.intStore);
            case questComplete:
                return (c.getNumQuest() >= this.intStore);
            case interval:
                return (c.getQuest(this.quest).getStatus() != 2 || c.getQuest(this.quest).getCompletionTime() <= System.currentTimeMillis() - (this.intStore * 60) * 1000L);
            case pet:
                for (Pair<Integer, Integer> a : this.dataStore) {
                    if (c.getPetIndexById(((Integer)a.getRight()).intValue()) == -1)
                        return false;
                }
                return true;
            case pettamenessmin:
                for (MaplePet pet : c.getPets()) {
                    if (pet.getSummoned() && pet.getCloseness() >= this.intStore)
                        return true;
                }
                return false;
        }
        return true;
    }

    public MapleQuestRequirementType getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.type.toString();
    }
}
