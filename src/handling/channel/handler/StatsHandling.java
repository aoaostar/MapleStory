package handling.channel.handler;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.PlayerStats;
import client.SkillFactory;
import constants.GameConstants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Randomizer;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;

public class StatsHandling
{
    private static final Logger log;
    
    public static void DistributeAP(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final List<Pair<MapleStat, Integer>> statupdate = new ArrayList<Pair<MapleStat, Integer>>(2);
        c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, chr.getJob()));
        chr.updateTick(slea.readInt());
        final PlayerStats stat = chr.getStat();
        final int job = chr.getJob();
        if (chr.getRemainingAp() > 0) {
            switch (slea.readInt()) {
                case 256: {
                    if (stat.getStr() >= 999) {
                        return;
                    }
                    stat.setStr((short)(stat.getStr() + 1));
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.STR, (int)stat.getStr()));
                    break;
                }
                case 512: {
                    if (stat.getDex() >= 999) {
                        return;
                    }
                    stat.setDex((short)(stat.getDex() + 1));
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.DEX, (int)stat.getDex()));
                    break;
                }
                case 1024: {
                    if (stat.getInt() >= 999) {
                        return;
                    }
                    stat.setInt((short)(stat.getInt() + 1));
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.INT, (int)stat.getInt()));
                    break;
                }
                case 2048: {
                    if (stat.getLuk() >= 999) {
                        return;
                    }
                    stat.setLuk((short)(stat.getLuk() + 1));
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.LUK, (int)stat.getLuk()));
                    break;
                }
                case 8192: {
                    short maxhp = stat.getMaxHp();
                    if (chr.getHpApUsed() >= 10000 || maxhp >= 30000) {
                        return;
                    }
                    if (job == 0) {
                        maxhp += (short)Randomizer.rand(8, 12);
                    }
                    else if ((job >= 100 && job <= 132) || (job >= 3200 && job <= 3212)) {
                        final ISkill improvingMaxHP = SkillFactory.getSkill(1000001);
                        final int improvingMaxHPLevel = c.getPlayer().getSkillLevel(improvingMaxHP);
                        maxhp += (short)Randomizer.rand(20, 25);
                        if (improvingMaxHPLevel >= 1) {
                            maxhp += (short)improvingMaxHP.getEffect(improvingMaxHPLevel).getX();
                        }
                    }
                    else if ((job >= 200 && job <= 232) || GameConstants.isEvan(job)) {
                        maxhp += (short)Randomizer.rand(10, 20);
                    }
                    else if ((job >= 300 && job <= 322) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3300 && job <= 3312)) {
                        maxhp += (short)Randomizer.rand(16, 20);
                    }
                    else if ((job >= 500 && job <= 522) || (job >= 3500 && job <= 3512)) {
                        final ISkill improvingMaxHP = SkillFactory.getSkill(5100000);
                        final int improvingMaxHPLevel = c.getPlayer().getSkillLevel(improvingMaxHP);
                        maxhp += (short)Randomizer.rand(18, 22);
                        if (improvingMaxHPLevel >= 1) {
                            maxhp += (short)improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    }
                    else if (job >= 1500 && job <= 1512) {
                        final ISkill improvingMaxHP = SkillFactory.getSkill(15100000);
                        final int improvingMaxHPLevel = c.getPlayer().getSkillLevel(improvingMaxHP);
                        maxhp += (short)Randomizer.rand(18, 22);
                        if (improvingMaxHPLevel >= 1) {
                            maxhp += (short)improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    }
                    else if (job >= 1100 && job <= 1112) {
                        final ISkill improvingMaxHP = SkillFactory.getSkill(11000000);
                        final int improvingMaxHPLevel = c.getPlayer().getSkillLevel(improvingMaxHP);
                        maxhp += (short)Randomizer.rand(36, 42);
                        if (improvingMaxHPLevel >= 1) {
                            maxhp += (short)improvingMaxHP.getEffect(improvingMaxHPLevel).getY();
                        }
                    }
                    else if (job >= 1200 && job <= 1212) {
                        maxhp += (short)Randomizer.rand(15, 21);
                    }
                    else if (job >= 2000 && job <= 2112) {
                        maxhp += (short)Randomizer.rand(40, 50);
                    }
                    else {
                        maxhp += (short)Randomizer.rand(50, 100);
                    }
                    maxhp = (short)Math.min(30000, Math.abs(maxhp));
                    chr.setHpApUsed((short)(chr.getHpApUsed() + 1));
                    stat.setMaxHp(maxhp);
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.MAXHP, (int)maxhp));
                    break;
                }
                case 32768: {
                    short maxmp = stat.getMaxMp();
                    short Int = (short)((short)(stat.getInt() / 10) - 10);
                    if (Int < 0) {
                        Int = 0;
                    }
                    if (chr.getHpApUsed() >= 10000 || stat.getMaxMp() >= 30000) {
                        return;
                    }
                    if (job == 0) {
                        maxmp += (short)Randomizer.rand(6, 8);
                    }
                    else if (job >= 100 && job <= 132) {
                        maxmp += (short)Randomizer.rand(2, 4);
                    }
                    else if ((job >= 200 && job <= 232) || GameConstants.isEvan(job) || (job >= 3200 && job <= 3212)) {
                        final ISkill improvingMaxMP = SkillFactory.getSkill(2000001);
                        final int improvingMaxMPLevel = c.getPlayer().getSkillLevel(improvingMaxMP);
                        maxmp += (short)Randomizer.rand(18, 20);
                        if (improvingMaxMPLevel >= 1) {
                            maxmp += (short)(improvingMaxMP.getEffect(improvingMaxMPLevel).getY() * 2);
                        }
                    }
                    else if ((job >= 300 && job <= 322) || (job >= 400 && job <= 434) || (job >= 500 && job <= 522) || (job >= 3200 && job <= 3212) || (job >= 3500 && job <= 3512) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 1500 && job <= 1512)) {
                        maxmp += (short)Randomizer.rand(10, 12);
                    }
                    else if (job >= 1100 && job <= 1112) {
                        maxmp += (short)Randomizer.rand(6, 9);
                    }
                    else if (job >= 1200 && job <= 1212) {
                        final ISkill improvingMaxMP = SkillFactory.getSkill(12000000);
                        final int improvingMaxMPLevel = c.getPlayer().getSkillLevel(improvingMaxMP);
                        maxmp += (short)Randomizer.rand(18, 20);
                        if (improvingMaxMPLevel >= 1) {
                            maxmp += (short)(improvingMaxMP.getEffect(improvingMaxMPLevel).getY() * 2);
                        }
                    }
                    else if (job >= 2000 && job <= 2112) {
                        maxmp += (short)Randomizer.rand(6, 9);
                    }
                    else {
                        maxmp += (short)Randomizer.rand(50, 100);
                    }
                    maxmp += Int;
                    maxmp = (short)Math.min(30000, Math.abs(maxmp));
                    chr.setHpApUsed((short)(chr.getHpApUsed() + 1));
                    stat.setMaxMp(maxmp);
                    statupdate.add(new Pair<MapleStat, Integer>(MapleStat.MAXMP, (int)maxmp));
                    break;
                }
                default: {
                    c.getSession().write(MaplePacketCreator.updatePlayerStats(MaplePacketCreator.EMPTY_STATUPDATE, true, chr.getJob()));
                    return;
                }
            }
            chr.setRemainingAp((short)(chr.getRemainingAp() - 1));
            statupdate.add(new Pair<MapleStat, Integer>(MapleStat.AVAILABLEAP, (int)chr.getRemainingAp()));
            c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, chr.getJob()));
        }
    }
    
    public static void DistributeSP(final int skillid, final MapleClient c, final MapleCharacter chr) {
        boolean isBeginnerSkill = false;
        int remainingSp = 0;
        switch (skillid) {
            case 1000:
            case 1001:
            case 1002: {
                final int snailsLevel = chr.getSkillLevel(SkillFactory.getSkill(1000));
                final int recoveryLevel = chr.getSkillLevel(SkillFactory.getSkill(1001));
                final int nimbleFeetLevel = chr.getSkillLevel(SkillFactory.getSkill(1002));
                remainingSp = Math.min(chr.getLevel() - 1, 6) - snailsLevel - recoveryLevel - nimbleFeetLevel;
                isBeginnerSkill = true;
                break;
            }
            case 10001000:
            case 10001001:
            case 10001002: {
                final int snailsLevel = chr.getSkillLevel(SkillFactory.getSkill(10001000));
                final int recoveryLevel = chr.getSkillLevel(SkillFactory.getSkill(10001001));
                final int nimbleFeetLevel = chr.getSkillLevel(SkillFactory.getSkill(10001002));
                remainingSp = Math.min(chr.getLevel() - 1, 6) - snailsLevel - recoveryLevel - nimbleFeetLevel;
                isBeginnerSkill = true;
                break;
            }
            case 20001000:
            case 20001001:
            case 20001002: {
                final int snailsLevel = chr.getSkillLevel(SkillFactory.getSkill(20001000));
                final int recoveryLevel = chr.getSkillLevel(SkillFactory.getSkill(20001001));
                final int nimbleFeetLevel = chr.getSkillLevel(SkillFactory.getSkill(20001002));
                remainingSp = Math.min(chr.getLevel() - 1, 6) - snailsLevel - recoveryLevel - nimbleFeetLevel;
                isBeginnerSkill = true;
                break;
            }
            case 20011000:
            case 20011001:
            case 20011002: {
                final int snailsLevel = chr.getSkillLevel(SkillFactory.getSkill(20011000));
                final int recoveryLevel = chr.getSkillLevel(SkillFactory.getSkill(20011001));
                final int nimbleFeetLevel = chr.getSkillLevel(SkillFactory.getSkill(20011002));
                remainingSp = Math.min(chr.getLevel() - 1, 6) - snailsLevel - recoveryLevel - nimbleFeetLevel;
                isBeginnerSkill = true;
                break;
            }
            case 30000002:
            case 30001000:
            case 30001001: {
                final int snailsLevel = chr.getSkillLevel(SkillFactory.getSkill(30001000));
                final int recoveryLevel = chr.getSkillLevel(SkillFactory.getSkill(30001001));
                final int nimbleFeetLevel = chr.getSkillLevel(SkillFactory.getSkill(30000002));
                remainingSp = Math.min(chr.getLevel() - 1, 9) - snailsLevel - recoveryLevel - nimbleFeetLevel;
                isBeginnerSkill = true;
                break;
            }
            default: {
                remainingSp = chr.getRemainingSp(GameConstants.getSkillBookForSkill(skillid));
                break;
            }
        }
        final ISkill skill = SkillFactory.getSkill(skillid);
        if (skill.hasRequiredSkill() && chr.getSkillLevel(SkillFactory.getSkill(skill.getRequiredSkillId())) < skill.getRequiredSkillLevel()) {
            return;
        }
        final int maxlevel = skill.isFourthJob() ? chr.getMasterLevel(skill) : skill.getMaxLevel();
        final int curLevel = chr.getSkillLevel(skill);
        if (skill.isInvisible() && chr.getSkillLevel(skill) == 0 && ((skill.isFourthJob() && chr.getMasterLevel(skill) == 0) || (!skill.isFourthJob() && maxlevel < 10 && !isBeginnerSkill))) {
            return;
        }
        for (final int i : GameConstants.blockedSkills) {
            if (skill.getId() == i) {
                chr.dropMessage(1, "你可能不会增加这个技能.");
                return;
            }
        }
        if (remainingSp > 0 && curLevel + 1 <= maxlevel && (skill.canBeLearnedBy(chr.getJob()) || isBeginnerSkill)) {
            if (!isBeginnerSkill) {
                final int skillbook = GameConstants.getSkillBookForSkill(skillid);
                chr.setRemainingSp(chr.getRemainingSp(skillbook) - 1, skillbook);
            }
            chr.updateSingleStat(MapleStat.AVAILABLESP, chr.getRemainingSp());
            chr.changeSkillLevel(skill, (byte)(curLevel + 1), chr.getMasterLevel(skill));
        }
        else if (!skill.canBeLearnedBy(chr.getJob())) {}
    }
    
    public static void AutoAssignAP(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final List statupdate = new ArrayList(2);
        c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, chr.getJob()));
        final PlayerStats playerst = chr.getStat();
        slea.readInt();
        for (int count = slea.readInt(), i = 0; i < count; ++i) {
            final int update = slea.readInt();
            final int updatenumber = slea.readInt();
            if (chr.getRemainingAp() >= updatenumber) {
                switch (update) {
                    case 256: {
                        if (playerst.getStr() + updatenumber >= 30000) {
                            return;
                        }
                        playerst.setStr((short)(playerst.getStr() + updatenumber));
                        statupdate.add(new Pair<MapleStat, Integer>(MapleStat.STR, (int)playerst.getStr()));
                        break;
                    }
                    case 512: {
                        if (playerst.getDex() + updatenumber >= 30000) {
                            return;
                        }
                        playerst.setDex((short)(playerst.getDex() + updatenumber));
                        statupdate.add(new Pair<MapleStat, Integer>(MapleStat.DEX, (int)playerst.getDex()));
                        break;
                    }
                    case 1024: {
                        if (playerst.getInt() + updatenumber >= 30000) {
                            return;
                        }
                        playerst.setInt((short)(playerst.getInt() + updatenumber));
                        statupdate.add(new Pair<MapleStat, Integer>(MapleStat.INT, (int)playerst.getInt()));
                        break;
                    }
                    case 2048: {
                        if (playerst.getLuk() + updatenumber >= 30000) {
                            return;
                        }
                        playerst.setLuk((short)(playerst.getLuk() + updatenumber));
                        statupdate.add(new Pair<MapleStat, Integer>(MapleStat.LUK, (int)playerst.getLuk()));
                        break;
                    }
                    default: {
                        c.getSession().write(MaplePacketCreator.updatePlayerStats(MaplePacketCreator.EMPTY_STATUPDATE, true, chr.getJob()));
                        return;
                    }
                }
                chr.setRemainingAp((short)(chr.getRemainingAp() - updatenumber));
            }
            else {
                StatsHandling.log.info("[h4x] Player {} is distributing AP to {} without having any", chr.getName(), update);
            }
        }
        statupdate.add(new Pair<MapleStat, Integer>(MapleStat.AVAILABLEAP, (int)chr.getRemainingAp()));
        c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, chr.getJob()));
    }
    
    static {
        log = LoggerFactory.getLogger((Class)StatsHandling.class);
    }
}
