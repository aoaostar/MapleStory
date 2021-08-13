package server.maps;

import client.MapleCharacter;
import client.MapleClient;
import client.anticheat.CheatingOffense;
import constants.GameConstants;
import java.awt.Point;
import server.MapleStatEffect;
import tools.MaplePacketCreator;

public class MapleSummon extends AbstractAnimatedMapleMapObject
{
    private int ownerid;
    private int skillLevel;
    private int ownerLevel;
    private int skill;
    private int fh;
    private MapleMap map;
    private short hp;
    private boolean changedMap;
    private SummonMovementType movementType;
    private int lastSummonTickCount;
    private byte Summon_tickResetCount;
    private long Server_ClientSummonTickDiff;
    
    public MapleSummon(final MapleCharacter owner, final MapleStatEffect skill, final Point pos, final SummonMovementType movementType) {
        this.changedMap = false;
        this.ownerid = owner.getId();
        this.ownerLevel = owner.getLevel();
        this.skill = skill.getSourceId();
        this.map = owner.getMap();
        this.skillLevel = skill.getLevel();
        this.movementType = movementType;
        this.setPosition(pos);
        try {
            this.fh = owner.getMap().getFootholds().findBelow(pos).getId();
        }
        catch (NullPointerException e) {
            this.fh = 0;
        }
        if (!this.is替身术()) {
            this.lastSummonTickCount = 0;
            this.Summon_tickResetCount = 0;
            this.Server_ClientSummonTickDiff = 0L;
        }
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().write((Object)MaplePacketCreator.removeSummon(this, false));
    }
    
    public void updateMap(final MapleMap map) {
        this.map = map;
    }
    
    public MapleCharacter getOwner() {
        return this.map.getCharacterById(this.ownerid);
    }
    
    public int getFh() {
        return this.fh;
    }
    
    public void setFh(final int fh) {
        this.fh = fh;
    }
    
    public int getOwnerId() {
        return this.ownerid;
    }
    
    public int getOwnerLevel() {
        return this.ownerLevel;
    }
    
    public int getSkill() {
        return this.skill;
    }
    
    public short getHP() {
        return this.hp;
    }
    
    public void addHP(final short delta) {
        this.hp += delta;
    }
    
    public SummonMovementType getMovementType() {
        return this.movementType;
    }
    
    public boolean is替身术() {
        switch (this.skill) {
            case 3111002:
            case 3211002:
            case 4341006:
            case 13111004:
            case 33111003: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isGaviota() {
        return this.skill == 5211002;
    }
    
    public boolean isBeholder() {
        return this.skill == 1321007;
    }
    
    public boolean isMultiSummon() {
        return this.skill == 5211002 || this.skill == 5211001 || this.skill == 5220002 || this.skill == 32111006;
    }
    
    public boolean isSummon() {
        switch (this.skill) {
            case 1321007:
            case 2121005:
            case 2221005:
            case 2311006:
            case 2321003:
            case 5211001:
            case 5211002:
            case 5220002:
            case 11001004:
            case 12001004:
            case 12111004:
            case 13001004:
            case 13111004:
            case 14001005:
            case 15001004: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public int getSkillLevel() {
        return this.skillLevel;
    }
    
    public int getSummonType() {
        if (this.is替身术()) {
            return 0;
        }
        switch (this.skill) {
            case 1321007: {
                return 2;
            }
            case 35111001:
            case 35111009:
            case 35111010: {
                return 3;
            }
            case 35121009: {
                return 4;
            }
            default: {
                return 1;
            }
        }
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.SUMMON;
    }
    
    public void CheckSummonAttackFrequency(final MapleCharacter chr, final int tickcount) {
        final int tickdifference = tickcount - this.lastSummonTickCount;
        if (tickdifference < GameConstants.getSummonAttackDelay(this.skill)) {
            chr.getCheatTracker().registerOffense(CheatingOffense.召唤兽快速攻击);
        }
        final long STime_TC = System.currentTimeMillis() - tickcount;
        final long S_C_Difference = this.Server_ClientSummonTickDiff - STime_TC;
        if (S_C_Difference > 200L) {
            chr.getCheatTracker().registerOffense(CheatingOffense.召唤兽快速攻击);
        }
        ++this.Summon_tickResetCount;
        if (this.Summon_tickResetCount > 4) {
            this.Summon_tickResetCount = 0;
            this.Server_ClientSummonTickDiff = STime_TC;
        }
        this.lastSummonTickCount = tickcount;
    }
    
    public boolean isChangedMap() {
        return this.changedMap;
    }
    
    public void setChangedMap(final boolean cm) {
        this.changedMap = cm;
    }
}
