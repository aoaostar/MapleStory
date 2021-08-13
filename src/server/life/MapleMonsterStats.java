package server.life;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tools.Pair;

public class MapleMonsterStats
{
    private byte cp;
    private byte selfDestruction_action;
    private byte tagColor;
    private byte tagBgColor;
    private byte rareItemDropLevel;
    private byte HPDisplayType;
    private short level;
    private short PhysicalDefense;
    private short MagicDefense;
    private short eva;
    private long hp;
    private int exp;
    private int mp;
    private int removeAfter;
    private int buffToGive;
    private int fixedDamage;
    private int selfDestruction_hp;
    private int dropItemPeriod;
    private int point;
    private boolean boss;
    private boolean undead;
    private boolean ffaLoot;
    private boolean firstAttack;
    private boolean isExplosiveReward;
    private boolean mobile;
    private boolean fly;
    private boolean onlyNormalAttack;
    private boolean friendly;
    private boolean noDoom;
    private String name;
    private final Map<Element, ElementalEffectiveness> resistance;
    private List<Integer> revives;
    private final List<Pair<Integer, Integer>> skills;
    private BanishInfo banish;
    private boolean changeable;
    private int acc;
    
    public MapleMonsterStats() {
        this.resistance = new HashMap<Element, ElementalEffectiveness>();
        this.revives = new ArrayList<Integer>();
        this.skills = new ArrayList<Pair<Integer, Integer>>();
    }
    
    public int getExp() {
        return this.exp;
    }
    
    public void setExp(final int exp) {
        this.exp = exp;
    }
    
    public long getHp() {
        return this.hp;
    }
    
    public void setHp(final long hp) {
        this.hp = hp;
    }
    
    public int getMp() {
        return this.mp;
    }
    
    public void setMp(final int mp) {
        this.mp = mp;
    }
    
    public short getLevel() {
        return this.level;
    }
    
    public void setLevel(final short level) {
        this.level = level;
    }
    
    public void setSelfD(final byte selfDestruction_action) {
        this.selfDestruction_action = selfDestruction_action;
    }
    
    public byte getSelfD() {
        return this.selfDestruction_action;
    }
    
    public void setSelfDHP(final int selfDestruction_hp) {
        this.selfDestruction_hp = selfDestruction_hp;
    }
    
    public int getSelfDHp() {
        return this.selfDestruction_hp;
    }
    
    public void setFixedDamage(final int damage) {
        this.fixedDamage = damage;
    }
    
    public int getFixedDamage() {
        return this.fixedDamage;
    }
    
    public void setPhysicalDefense(final short PhysicalDefense) {
        this.PhysicalDefense = PhysicalDefense;
    }
    
    public short getPhysicalDefense() {
        return this.PhysicalDefense;
    }
    
    public void setMagicDefense(final short MagicDefense) {
        this.MagicDefense = MagicDefense;
    }
    
    public short getMagicDefense() {
        return this.MagicDefense;
    }
    
    public void setEva(final short eva) {
        this.eva = eva;
    }
    
    public short getEva() {
        return this.eva;
    }
    
    public void setOnlyNormalAttack(final boolean onlyNormalAttack) {
        this.onlyNormalAttack = onlyNormalAttack;
    }
    
    public boolean getOnlyNoramlAttack() {
        return this.onlyNormalAttack;
    }
    
    public BanishInfo getBanishInfo() {
        return this.banish;
    }
    
    public void setBanishInfo(final BanishInfo banish) {
        this.banish = banish;
    }
    
    public int getRemoveAfter() {
        return this.removeAfter;
    }
    
    public void setRemoveAfter(final int removeAfter) {
        this.removeAfter = removeAfter;
    }
    
    public byte getrareItemDropLevel() {
        return this.rareItemDropLevel;
    }
    
    public void setrareItemDropLevel(final byte rareItemDropLevel) {
        this.rareItemDropLevel = rareItemDropLevel;
    }
    
    public void setBoss(final boolean boss) {
        this.boss = boss;
    }
    
    public boolean isBoss() {
        return this.boss;
    }
    
    public void setFfaLoot(final boolean ffaLoot) {
        this.ffaLoot = ffaLoot;
    }
    
    public boolean isFfaLoot() {
        return this.ffaLoot;
    }
    
    public void setExplosiveReward(final boolean isExplosiveReward) {
        this.isExplosiveReward = isExplosiveReward;
    }
    
    public boolean isExplosiveReward() {
        return this.isExplosiveReward;
    }
    
    public void setMobile(final boolean mobile) {
        this.mobile = mobile;
    }
    
    public boolean getMobile() {
        return this.mobile;
    }
    
    public void setFly(final boolean fly) {
        this.fly = fly;
    }
    
    public boolean getFly() {
        return this.fly;
    }
    
    public List<Integer> getRevives() {
        return this.revives;
    }
    
    public void setRevives(final List<Integer> revives) {
        this.revives = revives;
    }
    
    public void setUndead(final boolean undead) {
        this.undead = undead;
    }
    
    public boolean getUndead() {
        return this.undead;
    }
    
    public void setEffectiveness(final Element e, final ElementalEffectiveness ee) {
        this.resistance.put(e, ee);
    }
    
    public void removeEffectiveness(final Element e) {
        this.resistance.remove(e);
    }
    
    public ElementalEffectiveness getEffectiveness(final Element e) {
        final ElementalEffectiveness elementalEffectiveness = this.resistance.get(e);
        if (elementalEffectiveness == null) {
            return ElementalEffectiveness.正常;
        }
        return elementalEffectiveness;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public byte getTagColor() {
        return this.tagColor;
    }
    
    public void setTagColor(final int tagColor) {
        this.tagColor = (byte)tagColor;
    }
    
    public byte getTagBgColor() {
        return this.tagBgColor;
    }
    
    public void setTagBgColor(final int tagBgColor) {
        this.tagBgColor = (byte)tagBgColor;
    }
    
    public void setSkills(final List<Pair<Integer, Integer>> skill_) {
        for (final Pair<Integer, Integer> skill : skill_) {
            this.skills.add(skill);
        }
    }
    
    public List<Pair<Integer, Integer>> getSkills() {
        return Collections.unmodifiableList(this.skills);
    }
    
    public byte getNoSkills() {
        return (byte)this.skills.size();
    }
    
    public boolean hasSkill(final int skillId, final int level) {
        for (final Pair<Integer, Integer> skill : this.skills) {
            if (skill.getLeft() == skillId && skill.getRight() == level) {
                return true;
            }
        }
        return false;
    }
    
    public void setFirstAttack(final boolean firstAttack) {
        this.firstAttack = firstAttack;
    }
    
    public boolean isFirstAttack() {
        return this.firstAttack;
    }
    
    public void setCP(final byte cp) {
        this.cp = cp;
    }
    
    public byte getCP() {
        return this.cp;
    }
    
    public void setPoint(final int cp) {
        this.point = cp;
    }
    
    public int getPoint() {
        return this.point;
    }
    
    public void setFriendly(final boolean friendly) {
        this.friendly = friendly;
    }
    
    public boolean isFriendly() {
        return this.friendly;
    }
    
    public void setNoDoom(final boolean doom) {
        this.noDoom = doom;
    }
    
    public boolean isNoDoom() {
        return this.noDoom;
    }
    
    public void setBuffToGive(final int buff) {
        this.buffToGive = buff;
    }
    
    public int getBuffToGive() {
        return this.buffToGive;
    }
    
    public byte getHPDisplayType() {
        return this.HPDisplayType;
    }
    
    public void setHPDisplayType(final byte HPDisplayType) {
        this.HPDisplayType = HPDisplayType;
    }
    
    public int getDropItemPeriod() {
        return this.dropItemPeriod;
    }
    
    public void setDropItemPeriod(final int d) {
        this.dropItemPeriod = d;
    }
    
    public void setChange(final boolean invin) {
        this.changeable = invin;
    }
    
    public boolean isChangeable() {
        return this.changeable;
    }
    
    public int getAcc() {
        return this.acc;
    }
    
    public final void setAcc(final int acc) {
        this.acc = acc;
    }
}
