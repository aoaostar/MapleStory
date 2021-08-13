package client;

import java.io.*;

public enum MapleBuffStat implements Serializable
{
    物理攻击(4294967296L), 
    物理防御(8589934592L), 
    魔法攻击(17179869184L), 
    魔法防御(34359738368L), 
    命中率(68719476736L), 
    回避率(137438953472L), 
    手技(274877906944L), 
    移动速度(549755813888L), 
    跳跃力(1099511627776L), 
    魔法盾(2199023255552L), 
    隐身术(4398046511104L), 
    攻击加速(8796093022208L), 
    伤害反击(17592186044416L), 
    最大HP(35184372088832L), 
    最大MP(70368744177664L), 
    神之保护(140737488355328L), 
    无形箭弩(281474976710656L), 
    斗气集中(9007199254740992L), 
    召唤兽(9007199254740992L), 
    属性攻击(18014398509481984L), 
    龙之力(36028797018963968L), 
    神圣祈祷(72057594037927936L), 
    聚财术(144115188075855872L), 
    影分身(288230376151711744L), 
    敛财术(576460752303423488L), 
    替身术(576460752303423488L), 
    金钱护盾(1152921504606846976L), 
    PERFECT_ARMOR(4L, true), 
    SATELLITESAFE_PROC(8L, true), 
    SATELLITESAFE_ABSORB(16L, true), 
    CRITICAL_RATE_BUFF(64L, true), 
    MP_BUFF(128L, true), 
    DAMAGE_TAKEN_BUFF(256L, true), 
    DODGE_CHANGE_BUFF(512L, true), 
    CONVERSION(1024L, true), 
    REAPER(2048L, true), 
    金属机甲(8192L, true), 
    黑暗灵气(32768L, true), 
    蓝色灵气(65536L, true), 
    黄色灵气(131072L, true), 
    灵魂助力(8388608L, 4L), 
    能量获得(137438953472L, true), 
    疾驰移动(549755813888L), 
    疾驰跳跃(1099511627776L), 
    骑兽技能(1099511627776L, true), 
    导航辅助(1073741824L, 8L), 
    自然力重置(144115188075855872L, true), 
    PYRAMID_PQ(2L, false), 
    LIGHTNING_CHARGE(4L, false), 
    灵魂之石(2199023255552L, true), 
    MAGIC_SHIELD(140737488355328L, true), 
    MAGIC_RESISTANCE(281474976710656L, true), 
    SOARING(1125899906842624L, true), 
    MIRROR_IMAGE(9007199254740992L, true), 
    OWL_SPIRIT(18014398509481984L, true), 
    FINAL_CUT(72057594037927936L, true), 
    THORNS(144115188075855872L, true), 
    提高队员攻击力_BUFF(288230376151711744L, true), 
    RAINING_MINES(1152921504606846976L, true), 
    增强_最大HP(2305843009213693952L, true), 
    增强_最大MP(4611686018427387904L, true), 
    增强_物理攻击(Long.MIN_VALUE, true), 
    增强_物理防御力(1L, true), 
    增强_魔法防御力(2L, true), 
    变身(2L), 
    团队治疗(4L), 
    冒险岛勇士(8L), 
    稳如泰山(16L), 
    火眼晶晶(32L), 
    魔法反击(64L), 
    龙咆哮(128L), 
    暗器伤人(256L), 
    终极无限(512L), 
    圣灵之盾(1024L), 
    击退箭(2048L), 
    刺眼箭(4096L), 
    集中精力(8192L), 
    英雄之回声(32768L), 
    UNKNOWN3(65536L), 
    GHOST_MORPH(131072L), 
    ARIANT_COSS_IMU(262144L), 
    掉落_率(1048576L), 
    金币_率(2097152L), 
    经验_率(4194304L), 
    现金_率(8388608L), 
    GM_隐藏术(16777216L), 
    UNKNOWN7(33554432L), 
    ILLUSION(67108864L), 
    狂暴战魂(134217728L), 
    金刚霸体(268435456L), 
    闪光击(536870912L), 
    ARIANT_COSS_IMU2(1073741824L), 
    终极弓剑(2147483648L), 
    骑宠技能(1099511627776L), 
    连环吸血(8L, 3L), 
    冰雪矛(16384L, 2L), 
    战神之盾(16L, 3L), 
    战神抗压(32L, 3L), 
    矛连击强化(4294967296L, 3L), 
    灵巧击退(140737488355328L), 
    WEAKEN(4611686018427387904L), 
    子弹数量(1048575L, 1L);
    
    private static long serialVersionUID;
    private long buffstat;
    private long maskPos;
    private boolean first;
    
    private MapleBuffStat(final long buffstat) {
        this.buffstat = buffstat;
        this.maskPos = 4L;
        this.first = false;
    }
    
    private MapleBuffStat(final long buffstat, final long maskPos) {
        this.buffstat = buffstat;
        this.maskPos = maskPos;
        this.first = false;
    }
    
    private MapleBuffStat(final long buffstat, final boolean first) {
        this.buffstat = buffstat;
        this.maskPos = 4L;
        this.first = first;
    }
    
    public long getMaskPos() {
        return this.maskPos;
    }
    
    public boolean isFirst() {
        return this.first;
    }
    
    public long getValue() {
        return this.buffstat;
    }
    
    static {
        MapleBuffStat.serialVersionUID = 0L;
    }
}
