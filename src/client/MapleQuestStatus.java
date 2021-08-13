package client;


import constants.GameConstants;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import server.life.MapleLifeFactory;
import server.quest.MapleQuest;

public class MapleQuestStatus implements Serializable
{
    private static long serialVersionUID;
    private transient MapleQuest quest;
    private byte status;
    private Map<Integer, Integer> killedMobs;
    private int npc;
    private long completionTime;
    private int forfeited;
    private String customData;
    
    public MapleQuestStatus(final MapleQuest quest, final byte status) {
        this.killedMobs = null;
        this.forfeited = 0;
        this.quest = quest;
        this.setStatus(status);
        this.completionTime = System.currentTimeMillis();
        if (status == 1 && !quest.getRelevantMobs().isEmpty()) {
            this.registerMobs();
        }
    }
    
    public MapleQuestStatus(final MapleQuest quest, final byte status, final int npc) {
        this.killedMobs = null;
        this.forfeited = 0;
        this.quest = quest;
        this.setStatus(status);
        this.setNpc(npc);
        this.completionTime = System.currentTimeMillis();
        if (status == 1 && !quest.getRelevantMobs().isEmpty()) {
            this.registerMobs();
        }
    }
    
    public MapleQuest getQuest() {
        return this.quest;
    }
    
    public byte getStatus() {
        return this.status;
    }
    
    public void setStatus(final byte status) {
        this.status = status;
    }
    
    public int getNpc() {
        return this.npc;
    }
    
    public void setNpc(final int npc) {
        this.npc = npc;
    }
    
    public boolean isCustom() {
        return GameConstants.isCustomQuest(this.quest.getId());
    }
    
    private void registerMobs() {
        this.killedMobs = new LinkedHashMap<Integer, Integer>();
        for (final int i : this.quest.getRelevantMobs().keySet()) {
            this.killedMobs.put(i, 0);
        }
    }
    
    private int maxMob(final int mobid) {
        for (final Map.Entry<Integer, Integer> qs : this.quest.getRelevantMobs().entrySet()) {
            if (qs.getKey() == mobid) {
                return qs.getValue();
            }
        }
        return 0;
    }
    
    public boolean mobKilled(final int id, final int skillID) {
        if (this.quest != null && this.quest.getSkillID() > 0 && this.quest.getSkillID() != skillID) {
            return false;
        }
        final Integer mob = this.killedMobs.get(id);
        if (mob == null) {
            for (final Map.Entry<Integer, Integer> mo : this.killedMobs.entrySet()) {
                if (this.questCount(mo.getKey(), id)) {
                    final int mobb = this.maxMob(mo.getKey());
                    if (mo.getValue() >= mobb) {
                        return false;
                    }
                    this.killedMobs.put(mo.getKey(), Math.min(mo.getValue() + 1, mobb));
                    return true;
                }
            }
            return false;
        }
        final int mo2 = this.maxMob(id);
        if (mob >= mo2) {
            return false;
        }
        this.killedMobs.put(id, Math.min(mob + 1, mo2));
        return true;
    }
    
    private boolean questCount(final int mo, final int id) {
        if (MapleLifeFactory.getQuestCount(mo) != null) {
            for (final int i : MapleLifeFactory.getQuestCount(mo)) {
                if (i == id) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setMobKills(final int id, final int count) {
        if (this.killedMobs == null) {
            this.registerMobs();
        }
        this.killedMobs.put(id, count);
    }
    
    public boolean hasMobKills() {
        return this.killedMobs != null && this.killedMobs.size() > 0;
    }
    
    public int getMobKills(final int id) {
        final Integer mob = this.killedMobs.get(id);
        if (mob == null) {
            return 0;
        }
        return mob;
    }
    
    public Map<Integer, Integer> getMobKills() {
        return this.killedMobs;
    }
    
    public long getCompletionTime() {
        return this.completionTime;
    }
    
    public void setCompletionTime(final long completionTime) {
        this.completionTime = completionTime;
    }
    
    public int getForfeited() {
        return this.forfeited;
    }
    
    public void setForfeited(final int forfeited) {
        if (forfeited >= this.forfeited) {
            this.forfeited = forfeited;
            return;
        }
        throw new IllegalArgumentException("Can't set forfeits to something lower than before.");
    }
    
    public void setCustomData(final String customData) {
        this.customData = customData;
    }
    
    public String getCustomData() {
        return this.customData;
    }
    
    static {
        MapleQuestStatus.serialVersionUID = 91795419934134L;
    }
}
