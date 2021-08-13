package server.maps;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import handling.MaplePacket;
import java.awt.Point;
import java.awt.Rectangle;
import server.MapleStatEffect;
import server.life.MapleMonster;
import server.life.MobSkill;
import tools.MaplePacketCreator;

public class MapleMist extends AbstractMapleMapObject
{
    private Rectangle mistPosition;
    private MapleStatEffect source;
    private MobSkill skill;
    private boolean isMobMist;
    private int skillDelay;
    private int skilllevel;
    private int isPoisonMist;
    private int ownerId;
    
    public MapleMist(final Rectangle mistPosition, final MapleMonster mob, final MobSkill skill) {
        this.mistPosition = mistPosition;
        this.ownerId = mob.getId();
        this.skill = skill;
        this.skilllevel = skill.getSkillLevel();
        this.isMobMist = true;
        this.isPoisonMist = 0;
        this.skillDelay = 0;
    }
    
    public MapleMist(final Rectangle mistPosition, final MapleCharacter owner, final MapleStatEffect source) {
        this.mistPosition = mistPosition;
        this.ownerId = owner.getId();
        this.source = source;
        this.skillDelay = 8;
        this.isMobMist = false;
        this.skilllevel = owner.getSkillLevel(SkillFactory.getSkill(source.getSourceId()));
        switch (source.getSourceId()) {
            case 4221006: {
                this.isPoisonMist = 0;
                break;
            }
            case 2111003:
            case 12111005:
            case 14111006: {
                this.isPoisonMist = 1;
                break;
            }
            case 22161003: {
                this.isPoisonMist = 2;
                break;
            }
        }
    }
    
    public MapleMist(final Rectangle mistPosition, final MapleCharacter owner) {
        this.mistPosition = mistPosition;
        this.ownerId = owner.getId();
        (this.source = new MapleStatEffect()).setSourceId(2111003);
        this.skilllevel = 30;
        this.isMobMist = false;
        this.isPoisonMist = 0;
        this.skillDelay = 8;
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.MIST;
    }
    
    @Override
    public Point getPosition() {
        return this.mistPosition.getLocation();
    }
    
    public ISkill getSourceSkill() {
        return SkillFactory.getSkill(this.source.getSourceId());
    }
    
    public boolean isMobMist() {
        return this.isMobMist;
    }
    
    public int isPoisonMist() {
        return this.isPoisonMist;
    }
    
    public int getSkillDelay() {
        return this.skillDelay;
    }
    
    public int getSkillLevel() {
        return this.skilllevel;
    }
    
    public int getOwnerId() {
        return this.ownerId;
    }
    
    public MobSkill getMobSkill() {
        return this.skill;
    }
    
    public Rectangle getBox() {
        return this.mistPosition;
    }
    
    public MapleStatEffect getSource() {
        return this.source;
    }
    
    @Override
    public void setPosition(final Point position) {
    }
    
    public MaplePacket fakeSpawnData(final int level) {
        return MaplePacketCreator.spawnMist(this);
    }
    
    @Override
    public void sendSpawnData(final MapleClient c) {
        c.getSession().write((Object)MaplePacketCreator.spawnMist(this));
    }
    
    @Override
    public void sendDestroyData(final MapleClient c) {
        c.getSession().write((Object)MaplePacketCreator.removeMist(this.getObjectId(), false));
    }
    
    public boolean makeChanceResult() {
        return this.source.makeChanceResult();
    }
}
