package handling.channel.handler;

import client.ISkill;
import client.MapleCharacter;
import client.SkillFactory;
import constants.GameConstants;
import java.awt.Point;
import java.util.List;
import server.MapleStatEffect;
import tools.AttackPair;

public class AttackInfo
{
    public int skill;
    public int charge;
    public int lastAttackTickCount;
    public List<AttackPair> allDamage;
    public Point position;
    public byte hits;
    public byte targets;
    public byte tbyte;
    public byte display;
    public byte animation;
    public byte speed;
    public byte AOE;
    public short starSlot;
    public short cashSlot;
    public byte unk;
    public boolean real;
    public boolean isCloseRangeAttack;
    
    public AttackInfo() {
        this.real = true;
        this.isCloseRangeAttack = false;
    }
    
    public MapleStatEffect getAttackEffect(final MapleCharacter chr, int skillLevel, final ISkill skill_) {
        if (GameConstants.isMulungSkill(this.skill) || GameConstants.isPyramidSkill(this.skill)) {
            skillLevel = 1;
        } else if (skillLevel <= 0) {
            return null;
        }
        if (GameConstants.isLinkedAranSkill(this.skill)) {
            final ISkill skillLink = SkillFactory.getSkill(this.skill);
            if (this.display > 80 && !skillLink.getAction()) {
                return null;
            }
            return skillLink.getEffect(skillLevel);
        }
        else {
            if (this.display > 80 && !skill_.getAction()) {
                return null;
            }
            return skill_.getEffect(skillLevel);
        }
    }
}
