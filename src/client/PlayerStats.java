package client;

import client.inventory.Equip;
import client.inventory.IEquip;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import client.inventory.MapleWeaponType;
import constants.GameConstants;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleStatEffect;
import server.StructSetItem;
import tools.MaplePacketCreator;
import tools.data.output.MaplePacketLittleEndianWriter;


public class PlayerStats implements Serializable
{
    private static final long serialVersionUID = -679541993413738569L;
    private final transient WeakReference<MapleCharacter> chr;
    private final Map<Integer, Integer> setHandling;
    private final List<Equip> durabilityHandling;
    private final List<Equip> equipLevelHandling;
    private transient float shouldHealHP;
    private transient float shouldHealMP;
    public short str;
    public short dex;
    public short luk;
    public short int_;
    public short hp;
    public short maxhp;
    public short mp;
    public short maxmp;
    private transient short passive_sharpeye_percent;
    private transient short localmaxhp;
    private transient short localmaxmp;
    private transient byte passive_mastery;
    private transient byte passive_sharpeye_rate;
    private transient int localstr;
    private transient int localdex;
    private transient int localluk;
    private transient int localint_;
    private transient int magic;
    private transient int watk;
    private transient int hands;
    private transient int accuracy;
    public transient boolean equippedWelcomeBackRing;
    public transient boolean equippedFairy;
    public transient boolean hasMeso;
    public transient boolean hasItem;
    public transient boolean hasVac;
    public transient boolean hasClone;
    public transient boolean hasPartyBonus;
    public transient boolean Berserk;
    public transient boolean isRecalc;
    public transient int equipmentBonusExp;
    public transient int expMod;
    public transient int dropMod;
    public transient int cashMod;
    public transient int levelBonus;
    public transient double expBuff;
    public transient double dropBuff;
    public transient double mesoBuff;
    public transient double cashBuff;
    public transient double dam_r;
    public transient double bossdam_r;
    public transient int recoverHP;
    public transient int recoverMP;
    public transient int mpconReduce;
    public transient int incMesoProp;
    public transient int incRewardProp;
    public transient int DAMreflect;
    public transient int DAMreflect_rate;
    public transient int mpRestore;
    public transient int hpRecover;
    public transient int hpRecoverProp;
    public transient int mpRecover;
    public transient int mpRecoverProp;
    public transient int RecoveryUP;
    public transient int incAllskill;
    private transient float speedMod;
    private transient float jumpMod;
    private transient float localmaxbasedamage;
    public transient int def;
    public transient int element_ice;
    public transient int element_fire;
    public transient int element_light;
    public transient int element_psn;
    public ReentrantLock lock;
    public transient boolean canFish;
    public transient boolean canFishVIP;
    public transient int decreaseDebuff;
    
    public PlayerStats(final MapleCharacter chr) {
        this.setHandling = new HashMap<Integer, Integer>();
        this.durabilityHandling = new ArrayList<Equip>();
        this.equipLevelHandling = new ArrayList<Equip>();
        this.Berserk = false;
        this.isRecalc = false;
        this.lock = new ReentrantLock();
        this.chr = new WeakReference<MapleCharacter>(chr);
    }
    
    public void init() {
        this.recalcLocalStats();
        this.relocHeal();
    }
    
    public short getStr() {
        return this.str;
    }
    
    public short getDex() {
        return this.dex;
    }
    
    public short getLuk() {
        return this.luk;
    }
    
    public short getInt() {
        return this.int_;
    }
    
    public void setStr(final short str) {
        this.str = str;
        this.recalcLocalStats();
    }
    
    public void setDex(final short dex) {
        this.dex = dex;
        this.recalcLocalStats();
    }
    
    public void setLuk(final short luk) {
        this.luk = luk;
        this.recalcLocalStats();
    }
    
    public void setInt(final short int_) {
        this.int_ = int_;
        this.recalcLocalStats();
    }
    
    public boolean setHp(final int newhp) {
        return this.setHp(newhp, false);
    }
    
    public boolean setHp(final int newhp, final boolean silent) {
        final short oldHp = this.hp;
        int thp = newhp;
        if (thp < 0) {
            thp = 0;
        }
        if (thp > this.localmaxhp) {
            thp = this.localmaxhp;
        }
        this.hp = (short)thp;
        final MapleCharacter chra = this.chr.get();
        if (chra != null) {
            if (!silent) {
                chra.updatePartyMemberHP();
            }
            if (oldHp > this.hp && !chra.isAlive()) {
                chra.playerDead();
            }
        }
        return this.hp != oldHp;
    }
    
    public boolean setMp(final int newmp) {
        final short oldMp = this.mp;
        int tmp = newmp;
        if (tmp < 0) {
            tmp = 0;
        }
        if (tmp > this.localmaxmp) {
            tmp = this.localmaxmp;
        }
        this.mp = (short)tmp;
        return this.mp != oldMp;
    }
    
    public void setMaxHp(final short hp) {
        this.maxhp = hp;
        this.recalcLocalStats();
    }
    
    public void setMaxMp(final short mp) {
        this.maxmp = mp;
        this.recalcLocalStats();
    }
    
    public short getHp() {
        return this.hp;
    }
    
    public short getMaxHp() {
        return this.maxhp;
    }
    
    public short getMp() {
        return this.mp;
    }
    
    public short getMaxMp() {
        return this.maxmp;
    }
    
    public int getTotalDex() {
        return this.localdex;
    }
    
    public int getTotalInt() {
        return this.localint_;
    }
    
    public int getTotalStr() {
        return this.localstr;
    }
    
    public int getTotalLuk() {
        return this.localluk;
    }
    
    public int getTotalMagic() {
        return this.magic;
    }
    
    public double getSpeedMod() {
        return this.speedMod;
    }
    
    public double getJumpMod() {
        return this.jumpMod;
    }
    
    public int getTotalWatk() {
        return this.watk;
    }
    
    public short getCurrentMaxHp() {
        return this.localmaxhp;
    }
    
    public short getCurrentMaxMp() {
        return this.localmaxmp;
    }
    
    public int getHands() {
        return this.hands;
    }
    
    public float getCurrentMaxBaseDamage() {
        return this.localmaxbasedamage;
    }
    
    public void recalcLocalStats() {
        this.recalcLocalStats(false);
    }
    
    public void recalcLocalStats(final boolean first_login) {
        final MapleCharacter chra = this.chr.get();
        if (chra == null) {
            return;
        }
        this.lock.lock();
        try {
            if (this.isRecalc) {
                return;
            }
            this.isRecalc = true;
        }
        finally {
            this.lock.unlock();
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final int oldmaxhp = this.localmaxhp;
        int localmaxhp_ = this.getMaxHp();
        int localmaxmp_ = this.getMaxMp();
        this.localdex = this.getDex();
        this.localint_ = this.getInt();
        this.localstr = this.getStr();
        this.localluk = this.getLuk();
        int speed = 100;
        int jump = 100;
        int percent_hp = 0;
        int percent_mp = 0;
        final int percent_str = 0;
        final int percent_dex = 0;
        final int percent_int = 0;
        final int percent_luk = 0;
        final int percent_acc = 0;
        final int percent_atk = 0;
        final int percent_matk = 0;
        int added_sharpeye_rate = 0;
        int added_sharpeye_dmg = 0;
        this.magic = this.localint_;
        this.watk = 0;
        if (chra.getJob() == 500 || (chra.getJob() >= 520 && chra.getJob() <= 522)) {
            this.watk = 20;
        }
        else if (chra.getJob() == 400 || (chra.getJob() >= 410 && chra.getJob() <= 412) || (chra.getJob() >= 1400 && chra.getJob() <= 1412)) {
            this.watk = 30;
        }
        this.dam_r = 1.0;
        this.bossdam_r = 1.0;
        this.expBuff = 100.0;
        this.cashBuff = 100.0;
        this.dropBuff = 100.0;
        this.mesoBuff = 100.0;
        this.recoverHP = 0;
        this.recoverMP = 0;
        this.mpconReduce = 0;
        this.incMesoProp = 0;
        this.incRewardProp = 0;
        this.DAMreflect = 0;
        this.DAMreflect_rate = 0;
        this.hpRecover = 0;
        this.hpRecoverProp = 0;
        this.mpRecover = 0;
        this.mpRecoverProp = 0;
        this.mpRestore = 0;
        this.equippedWelcomeBackRing = false;
        this.equippedFairy = false;
        this.hasMeso = false;
        this.hasItem = false;
        this.hasPartyBonus = false;
        this.hasVac = false;
        this.hasClone = false;
        final boolean canEquipLevel = chra.getLevel() >= 120 && !GameConstants.isKOC(chra.getJob());
        this.equipmentBonusExp = 0;
        this.RecoveryUP = 0;
        this.dropMod = 1;
        this.expMod = 1;
        this.cashMod = 1;
        this.levelBonus = 0;
        this.incAllskill = 0;
        this.durabilityHandling.clear();
        this.equipLevelHandling.clear();
        this.setHandling.clear();
        this.element_fire = 100;
        this.element_ice = 100;
        this.element_light = 100;
        this.element_psn = 100;
        this.def = 100;
        this.canFish = false;
        this.canFishVIP = false;
        for (final IItem item : chra.getInventory(MapleInventoryType.EQUIPPED)) {
            final IEquip equip = (IEquip)item;
            if (equip.getPosition() == -11 && GameConstants.isMagicWeapon(equip.getItemId())) {
                final Map<String, Integer> eqstat = MapleItemInformationProvider.getInstance().getEquipStats(equip.getItemId());
                this.element_fire = eqstat.get("incRMAF");
                this.element_ice = eqstat.get("incRMAI");
                this.element_light = eqstat.get("incRMAL");
                this.element_psn = eqstat.get("incRMAS");
                this.def = eqstat.get("elemDefault");
            }
            this.accuracy += equip.getAcc();
            localmaxhp_ += equip.getHp();
            localmaxmp_ += equip.getMp();
            this.localdex += equip.getDex();
            this.localint_ += equip.getInt();
            this.localstr += equip.getStr();
            this.localluk += equip.getLuk();
            this.magic += equip.getMatk() + equip.getInt();
            this.watk += equip.getWatk();
            speed += equip.getSpeed();
            jump += equip.getJump();
            switch (equip.getItemId()) {
                case 1112127: {
                    this.equippedWelcomeBackRing = true;
                    break;
                }
                case 1122017: {
                    this.equippedFairy = true;
                    break;
                }
                case 1812000: {
                    this.hasMeso = true;
                    break;
                }
                case 1812001: {
                    this.hasItem = true;
                    break;
                }
                default: {
                    for (final int eb_bonus : GameConstants.Equipments_Bonus) {
                        if (equip.getItemId() == eb_bonus) {
                            this.equipmentBonusExp += GameConstants.道具佩戴附加经验值(eb_bonus);
                            break;
                        }
                    }
                    break;
                }
            }
            percent_hp += equip.getHpR();
            percent_mp += equip.getMpR();
            final int set = ii.getSetItemID(equip.getItemId());
            if (set > 0) {
                int value = 1;
                if (this.setHandling.get(set) != null) {
                    value += this.setHandling.get(set);
                }
                this.setHandling.put(set, value);
            }
        }
        for (final Map.Entry<Integer, Integer> entry : this.setHandling.entrySet()) {
            final StructSetItem set2 = ii.getSetItem(entry.getKey());
            if (set2 != null) {
                final Map<Integer, StructSetItem.SetItem> itemz = set2.getItems();
                for (final Map.Entry<Integer, StructSetItem.SetItem> ent : itemz.entrySet()) {
                    if (ent.getKey() <= entry.getValue()) {
                        final StructSetItem.SetItem se = ent.getValue();
                        this.localstr += se.incSTR;
                        this.localdex += se.incDEX;
                        this.localint_ += se.incINT;
                        this.localluk += se.incLUK;
                        this.watk += se.incPAD;
                        this.magic += se.incINT + se.incMAD;
                        speed += se.incSpeed;
                        this.accuracy += se.incACC;
                        localmaxhp_ += se.incMHP;
                        localmaxmp_ += se.incMMP;
                    }
                }
            }
        }
        final int day = Calendar.getInstance().get(7);
        final int hour = Calendar.getInstance().get(11);
        for (final IItem item2 : chra.getInventory(MapleInventoryType.CASH)) {
            if (this.expMod < 3 && (item2.getItemId() == 5211060 || item2.getItemId() == 5211050 || item2.getItemId() == 5211051 || item2.getItemId() == 5211052 || item2.getItemId() == 5211053 || item2.getItemId() == 5211054)) {
                this.expMod = 3;
            }
            else if (this.expMod == 1 && (item2.getItemId() == 5210001 || item2.getItemId() == 5210004 || item2.getItemId() == 5211061 || item2.getItemId() == 5211000 || item2.getItemId() == 5211001 || item2.getItemId() == 5211002 || item2.getItemId() == 5211003 || item2.getItemId() == 5211046 || item2.getItemId() == 5211047 || item2.getItemId() == 5211048 || item2.getItemId() == 5211049)) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && (item2.getItemId() == 5210005 || item2.getItemId() == 5210000)) {
                if (day >= 2 && day <= 6) {
                    if (hour >= 18 || hour < 6) {
                        this.expMod = 2;
                    }
                }
                else if (day == 1 || day == 7) {
                    this.expMod = 2;
                }
            }
            else if (this.expMod == 1 && (item2.getItemId() == 5210003 || item2.getItemId() == 5210002)) {
                if (day >= 2 && day <= 6) {
                    if (hour < 18 && hour >= 6) {
                        this.expMod = 2;
                    }
                }
                else if (day == 1 || day == 7) {
                    this.expMod = 2;
                }
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210006 && (hour >= 22 || hour <= 2)) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210007 && hour >= 2 && hour <= 6) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210008 && hour >= 6 && hour <= 10) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210009 && hour >= 10 && hour <= 14) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210010 && hour >= 14 && hour <= 18) {
                this.expMod = 2;
            }
            else if (this.expMod == 1 && item2.getItemId() == 5210011 && hour >= 18 && hour <= 22) {
                this.expMod = 2;
            }
            if (this.dropMod == 1) {
                if (item2.getItemId() == 5360009 || item2.getItemId() == 5360010 || item2.getItemId() == 5360011 || item2.getItemId() == 5360012 || item2.getItemId() == 5360013 || item2.getItemId() == 5360014 || item2.getItemId() == 5360017 || item2.getItemId() == 5360050 || item2.getItemId() == 5360053 || item2.getItemId() == 5360042 || item2.getItemId() == 5360052 || item2.getItemId() == 5360016 || item2.getItemId() == 5360015) {
                    this.dropMod = 2;
                }
                else if (item2.getItemId() == 5360000 && hour >= 0 && hour <= 6) {
                    this.dropMod = 2;
                }
                else if (item2.getItemId() == 5360001 && hour >= 6 && hour <= 12) {
                    this.dropMod = 2;
                }
                else if (item2.getItemId() == 5360002 && hour >= 12 && hour <= 18) {
                    this.dropMod = 2;
                }
                else if (item2.getItemId() == 5360003 && hour >= 18 && hour <= 24) {
                    this.dropMod = 2;
                }
            }
            if (item2.getItemId() == 5650000) {
                this.hasPartyBonus = true;
            }
            else if (item2.getItemId() == 5590001) {
                this.levelBonus = 10;
            }
            else if (this.levelBonus == 0 && item2.getItemId() == 5590000) {
                this.levelBonus = 5;
            }
            else {
                if (item2.getItemId() != 5340001) {
                    continue;
                }
                this.canFish = true;
                this.canFishVIP = true;
            }
        }
        for (final IItem item2 : chra.getInventory(MapleInventoryType.ETC)) {
            switch (item2.getItemId()) {
                case 4030003: {
                    this.hasVac = true;
                    continue;
                }
                case 4030004: {
                    this.hasClone = true;
                    continue;
                }
                case 4030005: {
                    this.cashMod = 2;
                    continue;
                }
            }
        }
        this.magic += chra.getSkillLevel(SkillFactory.getSkill(22000000));
        this.localstr += (int)(percent_str * this.localstr / 100.0f);
        this.localdex += (int)(percent_dex * this.localdex / 100.0f);
        final int before_ = this.localint_;
        this.localint_ += (int)(percent_int * this.localint_ / 100.0f);
        this.magic += this.localint_ - before_;
        this.localluk += (int)(percent_luk * this.localluk / 100.0f);
        this.accuracy += (int)(percent_acc * this.accuracy / 100.0f);
        this.watk += (int)(percent_atk * this.watk / 100.0f);
        this.magic += (int)(percent_matk * this.magic / 100.0f);
        localmaxhp_ += (int)(percent_hp * localmaxhp_ / 100.0f);
        localmaxmp_ += (int)(percent_mp * localmaxmp_ / 100.0f);
        Integer buff = chra.getBuffedValue(MapleBuffStat.冒险岛勇士);
        if (buff != null) {
            final double d = buff / 100.0;
            this.localstr += (int)(d * this.str);
            this.localdex += (int)(d * this.dex);
            this.localluk += (int)(d * this.luk);
            final int before = this.localint_;
            this.localint_ += (int)(d * this.int_);
            this.magic += this.localint_ - before;
        }
        buff = chra.getBuffedValue(MapleBuffStat.英雄之回声);
        if (buff != null) {
            final double d = buff / 100.0;
            this.watk += (int)(this.watk * d);
            this.magic += (int)(this.magic * d);
        }
        buff = chra.getBuffedValue(MapleBuffStat.矛连击强化);
        if (buff != null) {
            this.watk += buff / 10;
        }
        buff = chra.getBuffedValue(MapleBuffStat.最大HP);
        if (buff != null) {
            localmaxhp_ += (int)(buff / 100.0 * localmaxhp_);
        }
        buff = chra.getBuffedValue(MapleBuffStat.CONVERSION);
        if (buff != null) {
            localmaxhp_ += (int)(buff / 100.0 * localmaxhp_);
        }
        buff = chra.getBuffedValue(MapleBuffStat.最大MP);
        if (buff != null) {
            localmaxmp_ += (int)(buff / 100.0 * localmaxmp_);
        }
        buff = chra.getBuffedValue(MapleBuffStat.MP_BUFF);
        if (buff != null) {
            localmaxmp_ += (int)(buff / 100.0 * localmaxmp_);
        }
        buff = chra.getBuffedValue(MapleBuffStat.增强_最大HP);
        if (buff != null) {
            localmaxhp_ += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.增强_最大MP);
        if (buff != null) {
            localmaxmp_ += buff;
        }
        switch (chra.getJob()) {
            case 322: {
                final ISkill expert = SkillFactory.getSkill(3220004);
                final int boostLevel = chra.getSkillLevel(expert);
                if (boostLevel > 0) {
                    this.watk += expert.getEffect(boostLevel).getX();
                    break;
                }
                break;
            }
            case 312: {
                final ISkill expert = SkillFactory.getSkill(3120005);
                final int boostLevel = chra.getSkillLevel(expert);
                if (boostLevel > 0) {
                    this.watk += expert.getEffect(boostLevel).getX();
                    break;
                }
                break;
            }
            case 211:
            case 212: {
                final ISkill amp = SkillFactory.getSkill(2110001);
                final int level = chra.getSkillLevel(amp);
                if (level > 0) {
                    this.dam_r *= amp.getEffect(level).getY() / 100.0;
                    this.bossdam_r *= amp.getEffect(level).getY() / 100.0;
                    break;
                }
                break;
            }
            case 221:
            case 222: {
                final ISkill amp = SkillFactory.getSkill(2210001);
                final int level = chra.getSkillLevel(amp);
                if (level > 0) {
                    this.dam_r *= amp.getEffect(level).getY() / 100.0;
                    this.bossdam_r *= amp.getEffect(level).getY() / 100.0;
                    break;
                }
                break;
            }
            case 1211:
            case 1212: {
                final ISkill amp = SkillFactory.getSkill(12110001);
                final int level = chra.getSkillLevel(amp);
                if (level > 0) {
                    this.dam_r *= amp.getEffect(level).getY() / 100.0;
                    this.bossdam_r *= amp.getEffect(level).getY() / 100.0;
                    break;
                }
                break;
            }
            case 2215:
            case 2216:
            case 2217:
            case 2218: {
                final ISkill amp = SkillFactory.getSkill(22150000);
                final int level = chra.getSkillLevel(amp);
                if (level > 0) {
                    this.dam_r *= amp.getEffect(level).getY() / 100.0;
                    this.bossdam_r *= amp.getEffect(level).getY() / 100.0;
                    break;
                }
                break;
            }
            case 2112: {
                final ISkill expert = SkillFactory.getSkill(21120001);
                final int boostLevel = chra.getSkillLevel(expert);
                if (boostLevel > 0) {
                    this.watk += expert.getEffect(boostLevel).getX();
                    break;
                }
                break;
            }
        }
        final ISkill blessoffairy = SkillFactory.getSkill(GameConstants.getBOF_ForJob(chra.getJob()));
        final int boflevel = chra.getSkillLevel(blessoffairy);
        if (boflevel > 0) {
            this.watk += blessoffairy.getEffect(boflevel).getX();
            this.magic += blessoffairy.getEffect(boflevel).getY();
            this.accuracy += blessoffairy.getEffect(boflevel).getX();
        }
        buff = chra.getBuffedValue(MapleBuffStat.经验_率);
        if (buff != null) {
            if (chra.getBuffSource(MapleBuffStat.经验_率) == 0) {
                chra.getMapId();
            }
            else {
                this.expBuff *= buff / 100.0;
            }
        }
        buff = chra.getBuffedValue(MapleBuffStat.掉落_率);
        if (buff != null) {
            if (chra.getBuffSource(MapleBuffStat.掉落_率) == 0) {
                chra.getMapId();
            }
            else {
                this.dropBuff *= buff / 100.0;
            }
        }
        buff = chra.getBuffedValue(MapleBuffStat.现金_率);
        if (buff != null) {
            this.cashBuff *= buff / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.金币_率);
        if (buff != null) {
            this.mesoBuff *= buff / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.聚财术);
        if (buff != null) {
            this.mesoBuff *= buff / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.命中率);
        if (buff != null) {
            this.accuracy += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.物理攻击);
        if (buff != null) {
            this.watk += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.增强_物理攻击);
        if (buff != null) {
            this.watk += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.魔法攻击);
        if (buff != null) {
            this.magic += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.移动速度);
        if (buff != null) {
            speed += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.跳跃力);
        if (buff != null) {
            jump += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.疾驰跳跃);
        if (buff != null) {
            speed += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.疾驰跳跃);
        if (buff != null) {
            jump += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.提高队员攻击力_BUFF);
        if (buff != null) {
            this.dam_r += buff;
            this.bossdam_r += buff;
        }
        buff = chra.getBuffedSkill_Y(MapleBuffStat.FINAL_CUT);
        if (buff != null) {
            this.dam_r *= buff / 100.0;
            this.bossdam_r *= buff / 100.0;
        }
        buff = chra.getBuffedSkill_Y(MapleBuffStat.OWL_SPIRIT);
        if (buff != null) {
            this.dam_r *= buff / 100.0;
            this.bossdam_r *= buff / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.狂暴战魂);
        if (buff != null) {
            this.dam_r *= 2.0;
            this.bossdam_r *= 2.0;
        }
        final ISkill bx = SkillFactory.getSkill(1320006);
        if (chra.getSkillLevel(bx) > 0) {
            this.dam_r *= bx.getEffect(chra.getSkillLevel(bx)).getDamage() / 100.0;
            this.bossdam_r *= bx.getEffect(chra.getSkillLevel(bx)).getDamage() / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.PYRAMID_PQ);
        if (buff != null) {
            final MapleStatEffect eff = chra.getStatForBuff(MapleBuffStat.PYRAMID_PQ);
            this.dam_r *= eff.getBerserk() / 100.0;
            this.bossdam_r *= eff.getBerserk() / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.属性攻击);
        if (buff != null) {
            final MapleStatEffect eff = chra.getStatForBuff(MapleBuffStat.属性攻击);
            this.dam_r *= eff.getDamage() / 100.0;
            this.bossdam_r *= eff.getDamage() / 100.0;
        }
        buff = chra.getBuffedValue(MapleBuffStat.LIGHTNING_CHARGE);
        if (buff != null) {
            final MapleStatEffect eff = chra.getStatForBuff(MapleBuffStat.LIGHTNING_CHARGE);
            this.dam_r *= eff.getDamage() / 100.0;
            this.bossdam_r *= eff.getDamage() / 100.0;
        }
        buff = chra.getBuffedSkill_X(MapleBuffStat.THORNS);
        if (buff != null) {
            added_sharpeye_rate += buff;
        }
        buff = chra.getBuffedSkill_Y(MapleBuffStat.THORNS);
        if (buff != null) {
            added_sharpeye_dmg += buff - 100;
        }
        buff = chra.getBuffedSkill_X(MapleBuffStat.火眼晶晶);
        if (buff != null) {
            added_sharpeye_rate += buff;
        }
        buff = chra.getBuffedSkill_Y(MapleBuffStat.火眼晶晶);
        if (buff != null) {
            added_sharpeye_dmg += buff;
        }
        buff = chra.getBuffedValue(MapleBuffStat.CRITICAL_RATE_BUFF);
        if (buff != null) {
            added_sharpeye_rate += buff;
        }
        if (speed > 140) {
            speed = 140;
        }
        if (jump > 123) {
            jump = 123;
        }
        this.speedMod = speed / 100.0f;
        this.jumpMod = jump / 100.0f;
        final Integer mount = chra.getBuffedValue(MapleBuffStat.骑兽技能);
        if (mount != null) {
            this.jumpMod = 1.23f;
            switch (mount) {
                case 1: {
                    this.speedMod = 1.5f;
                    break;
                }
                case 2: {
                    this.speedMod = 1.7f;
                    break;
                }
                case 3: {
                    this.speedMod = 1.8f;
                    break;
                }
                default: {
                    System.err.println("Unhandeled monster riding level, Speedmod = " + this.speedMod + "");
                    break;
                }
            }
        }
        this.hands = this.localdex + this.localint_ + this.localluk;
        this.localmaxhp = (short)Math.min(30000, Math.abs(Math.max(-30000, localmaxhp_)));
        this.localmaxmp = (short)Math.min(30000, Math.abs(Math.max(-30000, localmaxmp_)));
        this.CalcPassive_SharpEye(chra, added_sharpeye_rate, added_sharpeye_dmg);
        this.CalcPassive_Mastery(chra);
        if (first_login) {
            chra.silentEnforceMaxHpMp();
        }
        else {
            chra.enforceMaxHpMp();
        }
        this.localmaxbasedamage = this.calculateMaxBaseDamage(this.watk, this.magic - this.localint_);
        if (oldmaxhp != 0 && oldmaxhp != this.localmaxhp) {
            chra.updatePartyMemberHP();
        }
        this.lock.lock();
        try {
            this.isRecalc = false;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public boolean checkEquipLevels(final MapleCharacter chr, final int gain) {
        boolean changed = false;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<Equip> all = new ArrayList<Equip>(this.equipLevelHandling);
        for (Equip eq : all) {
            final int lvlz = eq.getEquipLevels();
            eq.setItemEXP(eq.getItemEXP() + gain);
            if (eq.getEquipLevels() > lvlz) {
                for (int i = eq.getEquipLevels() - lvlz; i > 0; --i) {
                    final Map<Integer, Map<String, Integer>> inc = ii.getEquipIncrements(eq.getItemId());
                    if (inc != null && inc.containsKey(lvlz + i)) {
                        eq = ii.levelUpEquip(eq, inc.get(lvlz + i));
                    }
                    if (GameConstants.getStatFromWeapon(eq.getItemId()) == null) {
                        final Map<Integer, List<Integer>> ins = ii.getEquipSkills(eq.getItemId());
                        if (ins != null && ins.containsKey(lvlz + i)) {
                            for (final Integer z : ins.get(lvlz + i)) {
                                if (Math.random() < 0.1) {
                                    final ISkill skil = SkillFactory.getSkill(z);
                                    if (skil == null || !skil.canBeLearnedBy(chr.getJob()) || chr.getSkillLevel(skil) >= chr.getMasterLevel(skil)) {
                                        continue;
                                    }
                                    chr.changeSkillLevel(skil, (byte)(chr.getSkillLevel(skil) + 1), chr.getMasterLevel(skil));
                                }
                            }
                        }
                    }
                }
                changed = true;
            }
            chr.forceReAddItem(eq.copy(), MapleInventoryType.EQUIPPED);
        }
        if (changed) {
            chr.equipChanged();
            chr.getClient().getSession().write(MaplePacketCreator.showItemLevelupEffect());
            chr.getMap().broadcastMessage(chr, MaplePacketCreator.showForeignItemLevelupEffect(chr.getId()), false);
        }
        return changed;
    }
    
    public boolean checkEquipDurabilitys(final MapleCharacter chr, final int gain) {
        for (final Equip item : this.durabilityHandling) {
            item.setDurability(item.getDurability() + gain);
            if (item.getDurability() < 0) {
                item.setDurability(0);
            }
        }
        final List<Equip> all = new ArrayList<Equip>(this.durabilityHandling);
        for (final Equip eqq : all) {
            if (eqq.getDurability() == 0) {
                if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                    chr.getClient().getSession().write(MaplePacketCreator.getInventoryFull());
                    chr.getClient().getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return false;
                }
                this.durabilityHandling.remove(eqq);
                final short pos = chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot();
                MapleInventoryManipulator.unequip(chr.getClient(), eqq.getPosition(), pos);
                chr.getClient().getSession().write(MaplePacketCreator.updateSpecialItemUse(eqq, (byte)1, pos));
            }
            else {
                chr.forceReAddItem(eqq.copy(), MapleInventoryType.EQUIPPED);
            }
        }
        return true;
    }
    
    private void CalcPassive_Mastery(final MapleCharacter player) {
        if (player.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11)) == null) {
            this.passive_mastery = 0;
            return;
        }
        int skil = 0;
        switch (GameConstants.getWeaponType(player.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11)).getItemId())) {
            case BOW: {
                skil = (GameConstants.isKOC(player.getJob()) ? 13100000 : (GameConstants.isResist(player.getJob()) ? 33100000 : 3100000));
                break;
            }
            case CLAW: {
                skil = 4100000;
                break;
            }
            case KATARA:
            case DAGGER: {
                skil = ((player.getJob() >= 430 && player.getJob() <= 434) ? 4300000 : 4200000);
                break;
            }
            case CROSSBOW: {
                skil = 3200000;
                break;
            }
            case AXE1H:
            case AXE2H: {
                skil = 1100001;
                break;
            }
            case SWORD1H:
            case SWORD2H: {
                skil = (GameConstants.isKOC(player.getJob()) ? 11100000 : ((player.getJob() > 112) ? 1200000 : 1100000));
                break;
            }
            case BLUNT1H:
            case BLUNT2H: {
                skil = 1200001;
                break;
            }
            case POLE_ARM: {
                skil = (GameConstants.isAran(player.getJob()) ? 21100000 : 1300001);
                break;
            }
            case SPEAR: {
                skil = 1300000;
                break;
            }
            case KNUCKLE: {
                skil = (GameConstants.isKOC(player.getJob()) ? 15100001 : 5100001);
                break;
            }
            case GUN: {
                skil = (GameConstants.isResist(player.getJob()) ? 35100000 : 5200000);
                break;
            }
            case STAFF: {
                skil = 32100006;
                break;
            }
            default: {
                this.passive_mastery = 0;
                return;
            }
        }
        if (player.getSkillLevel(skil) <= 0) {
            this.passive_mastery = 0;
            return;
        }
        this.passive_mastery = (byte)(player.getSkillLevel(skil) / 2 + player.getSkillLevel(skil) % 2);
    }
    
    private void CalcPassive_SharpEye(final MapleCharacter player, final int added_sharpeye_rate, final int added_sharpeye_dmg) {
        switch (player.getJob()) {
            case 410:
            case 411:
            case 412: {
                final ISkill critSkill = SkillFactory.getSkill(4100001);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 1410:
            case 1411:
            case 1412: {
                final ISkill critSkill = SkillFactory.getSkill(14100001);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 511:
            case 512: {
                final ISkill critSkill = SkillFactory.getSkill(5110000);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate += (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 1511:
            case 1512: {
                final ISkill critSkill = SkillFactory.getSkill(15110000);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 2111:
            case 2112: {
                final ISkill critSkill = SkillFactory.getSkill(21110000);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getX() * critSkill.getEffect(critlevel).getDamage() + added_sharpeye_dmg + 100);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getX() * critSkill.getEffect(critlevel).getY() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 300:
            case 310:
            case 311:
            case 312:
            case 320:
            case 321:
            case 322: {
                final ISkill critSkill = SkillFactory.getSkill(3000001);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 1300:
            case 1310:
            case 1311:
            case 1312: {
                final ISkill critSkill = SkillFactory.getSkill(13000000);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
            case 2214:
            case 2215:
            case 2216:
            case 2217:
            case 2218: {
                final ISkill critSkill = SkillFactory.getSkill(22140000);
                final int critlevel = player.getSkillLevel(critSkill);
                if (critlevel > 0) {
                    this.passive_sharpeye_percent = (short)(critSkill.getEffect(critlevel).getDamage() - 100 + added_sharpeye_dmg);
                    this.passive_sharpeye_rate = (byte)(critSkill.getEffect(critlevel).getProb() + added_sharpeye_rate);
                    return;
                }
                break;
            }
        }
        this.passive_sharpeye_percent = (short)added_sharpeye_dmg;
        this.passive_sharpeye_rate = (byte)added_sharpeye_rate;
    }
    
    public short passive_sharpeye_percent() {
        return this.passive_sharpeye_percent;
    }
    
    public byte passive_sharpeye_rate() {
        return this.passive_sharpeye_rate;
    }
    
    public byte passive_mastery() {
        return this.passive_mastery;
    }
    
    public float calculateMaxBaseDamage(int watk, final int matk) {
        final MapleCharacter chra = this.chr.get();
        if (chra == null) {
            return 0.0f;
        }
        float maxbasedamage;
        if (watk == 0) {
            maxbasedamage = 1.0f;
        }
        else {
            final IItem weapon_item = chra.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
            final int job = chra.getJob();
            final MapleWeaponType weapon = (weapon_item == null) ? MapleWeaponType.NOT_A_WEAPON : GameConstants.getWeaponType(weapon_item.getItemId());
            int mainstat = 0;
            int secondarystat = 0;
            switch (weapon) {
                case BOW:
                case CROSSBOW: {
                    mainstat = this.localdex;
                    secondarystat = this.localstr;
                    break;
                }
                case CLAW:
                case KATARA:
                case DAGGER: {
                    if ((job >= 400 && job <= 434) || (job >= 1400 && job <= 1412)) {
                        mainstat = this.localluk;
                        secondarystat = this.localdex + this.localstr;
                        break;
                    }
                    mainstat = this.localstr;
                    secondarystat = this.localdex;
                    break;
                }
                case KNUCKLE: {
                    mainstat = this.localstr;
                    secondarystat = this.localdex;
                    break;
                }
                case GUN: {
                    mainstat = this.localdex;
                    secondarystat = this.localstr;
                    break;
                }
                case NOT_A_WEAPON: {
                    if ((job >= 500 && job <= 522) || (job >= 1500 && job <= 1512) || (job >= 3500 && job <= 3512)) {
                        mainstat = this.localstr;
                        secondarystat = this.localdex;
                        break;
                    }
                    mainstat = 0;
                    secondarystat = 0;
                    break;
                }
                default: {
                    if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1211 && (weapon == MapleWeaponType.WAND || weapon == MapleWeaponType.STAFF))) {
                        mainstat = this.localint_;
                        secondarystat = this.localluk;
                        watk = matk;
                        break;
                    }
                    mainstat = this.localstr;
                    secondarystat = this.localdex;
                    break;
                }
            }
            maxbasedamage = (weapon.getMaxDamageMultiplier() * mainstat + secondarystat) * watk / 100.0f;
        }
        return maxbasedamage;
    }
    
    public float getHealHP() {
        return this.shouldHealHP;
    }
    
    public float getHealMP() {
        return this.shouldHealMP;
    }
    
    public void relocHeal() {
        final MapleCharacter chra = this.chr.get();
        if (chra == null) {
            return;
        }
        final int playerjob = chra.getJob();
        this.shouldHealHP = (float)(10 + this.recoverHP);
        this.shouldHealMP = (float)(3 + this.mpRestore + this.recoverMP);
        if (GameConstants.isJobFamily(200, playerjob)) {
            this.shouldHealMP += chra.getSkillLevel(SkillFactory.getSkill(2000000)) / 10.0f * chra.getLevel();
        }
        else if (GameConstants.isJobFamily(111, playerjob)) {
            final ISkill effect = SkillFactory.getSkill(1110000);
            final int lvl = chra.getSkillLevel(effect);
            if (lvl > 0) {
                this.shouldHealMP += effect.getEffect(lvl).getMp();
            }
        }
        else if (GameConstants.isJobFamily(121, playerjob)) {
            final ISkill effect = SkillFactory.getSkill(1210000);
            final int lvl = chra.getSkillLevel(effect);
            if (lvl > 0) {
                this.shouldHealMP += effect.getEffect(lvl).getMp();
            }
        }
        else if (GameConstants.isJobFamily(1111, playerjob)) {
            final ISkill effect = SkillFactory.getSkill(11110000);
            final int lvl = chra.getSkillLevel(effect);
            if (lvl > 0) {
                this.shouldHealMP += effect.getEffect(lvl).getMp();
            }
        }
        else if (GameConstants.isJobFamily(410, playerjob)) {
            final ISkill effect = SkillFactory.getSkill(4100002);
            final int lvl = chra.getSkillLevel(effect);
            if (lvl > 0) {
                this.shouldHealHP += effect.getEffect(lvl).getHp();
                this.shouldHealMP += effect.getEffect(lvl).getMp();
            }
        }
        else if (GameConstants.isJobFamily(420, playerjob)) {
            final ISkill effect = SkillFactory.getSkill(4200001);
            final int lvl = chra.getSkillLevel(effect);
            if (lvl > 0) {
                this.shouldHealHP += effect.getEffect(lvl).getHp();
                this.shouldHealMP += effect.getEffect(lvl).getMp();
            }
        }
        if (chra.isGM()) {
            this.shouldHealHP += 1000.0f;
            this.shouldHealMP += 1000.0f;
        }
        if (chra.getChair() != 0) {
            this.shouldHealHP += 99.0f;
            this.shouldHealMP += 99.0f;
        }
        else {
            final float recvRate = chra.getMap().getRecoveryRate();
            this.shouldHealHP *= recvRate;
            this.shouldHealMP *= recvRate;
        }
        this.shouldHealHP *= 2.0f;
        this.shouldHealMP *= 2.0f;
    }
    
    public void connectData(final MaplePacketLittleEndianWriter mplew) {
        mplew.writeShort(this.str);
        mplew.writeShort(this.dex);
        mplew.writeShort(this.int_);
        mplew.writeShort(this.luk);
        mplew.writeShort(this.hp);
        mplew.writeShort(this.maxhp);
        mplew.writeShort(this.mp);
        mplew.writeShort(this.maxmp);
    }
    
    public int getSkillByJob(final int skillID, final int job) {
        if (GameConstants.isKOC(job)) {
            return skillID + 10000000;
        }
        if (GameConstants.isAran(job)) {
            return skillID + 20000000;
        }
        if (GameConstants.isEvan(job)) {
            return skillID + 20010000;
        }
        if (GameConstants.isResist(job)) {
            return skillID + 30000000;
        }
        return skillID;
    }
    
    public int getLimitBreak(final MapleCharacter chra) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        int limitBreak = 999999;
        final Equip weapon = (Equip)chra.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
        if (weapon != null) {
            limitBreak = ii.getLimitBreak(weapon.getItemId());
            final Equip subweapon = (Equip)chra.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-10));
            if (subweapon != null && GameConstants.is物理武器(subweapon.getItemId())) {
                final int subWeaponLB = ii.getLimitBreak(subweapon.getItemId());
                if (subWeaponLB > limitBreak) {
                    limitBreak = subWeaponLB;
                }
            }
        }
        return limitBreak;
    }
    
    private void resetLocalStats(final int job) {
        this.accuracy = 0;
        this.localdex = this.getDex();
        this.localint_ = this.getInt();
        this.localstr = this.getStr();
        this.localluk = this.getLuk();
        this.decreaseDebuff = 0;
        this.passive_sharpeye_rate = 5;
        this.magic = 0;
        this.watk = 0;
        this.expBuff = 100.0;
        this.cashBuff = 100.0;
        this.dropBuff = 100.0;
        this.mesoBuff = 100.0;
        this.recoverHP = 0;
        this.recoverMP = 0;
        this.mpconReduce = 0;
        this.incMesoProp = 0;
        this.DAMreflect = 0;
        this.DAMreflect_rate = 0;
        this.hpRecover = 0;
        this.hpRecoverProp = 0;
        this.mpRecover = 0;
        this.mpRecoverProp = 0;
        this.mpRestore = 0;
        this.equippedWelcomeBackRing = false;
        this.hasPartyBonus = false;
        this.hasClone = false;
        this.Berserk = false;
        this.canFish = false;
        this.canFishVIP = false;
        this.equipmentBonusExp = 0;
        this.RecoveryUP = 100;
        this.dropMod = 1;
        this.cashMod = 1;
        this.levelBonus = 0;
        this.incAllskill = 0;
        this.durabilityHandling.clear();
        this.equipLevelHandling.clear();
        this.setHandling.clear();
        this.element_fire = 100;
        this.element_ice = 100;
        this.element_light = 100;
        this.element_psn = 100;
        this.def = 100;
    }
}
