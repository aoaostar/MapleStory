package handling.channel.handler;

import KinMS.PvP.MaplePvp;
import client.ISkill;
import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleStat;
import client.PlayerStats;
import client.SkillFactory;
import client.anticheat.CheatTracker;
import client.anticheat.CheatingOffense;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import constants.GameConstants;
import handling.world.World;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import server.MapleItemInformationProvider;
import server.MapleStatEffect;
import server.Randomizer;
import server.life.Element;
import server.life.MapleMonster;
import server.life.MapleMonsterStats;
import server.maps.MapleMap;
import server.maps.MapleMapItem;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.AttackPair;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.LittleEndianAccessor;

public class    DamageParse
{
    private static final int[] charges;

    //普通攻击
    public static void applyAttack(AttackInfo attack, ISkill theSkill, MapleCharacter player, int attackCount, double maxDamagePerMonster, MapleStatEffect effect, AttackType attack_type) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.人物死亡攻击);
            return;
        }
        if (attack.real);
        if (attack.skill != 0) {
            boolean ban = false;
            String lastReason = "";
            String reason = "";
            if (effect == null) {
                player.getClient().getSession().write(MaplePacketCreator.enableActions());
                return;
            }
            if (GameConstants.isMulungSkill(attack.skill)) {
                if (player.getMapId() / 10000 != 92502)
                    return;
                player.mulung_EnergyModify(false);
            }
            if (GameConstants.isPyramidSkill(attack.skill)) {
                if (player.getMapId() / 1000000 != 926)
                    return;
                if (player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player))
                    return;
            }
        }
        int totDamage = 0;
        MapleMap map = player.getMap();
        if (map.isPvpMap()) {
            MaplePvp.doPvP(player, map, attack, effect);
        } else if (map.isPartyPvpMap()) {
            MaplePvp.doPartyPvP(player, map, attack, effect);
        } else if (map.isGuildPvpMap()) {
            MaplePvp.doGuildPvP(player, map, attack, effect);
        }
        if (attack.skill == 4211006)
            for (AttackPair oned : attack.allDamage) {
                if (oned.attack != null)
                    continue;
                MapleMapObject mapobject = map.getMapObject(oned.objectid, MapleMapObjectType.ITEM);
                if (mapobject != null) {
                    MapleMapItem mapitem = (MapleMapItem)mapobject;
                    mapitem.getLock().lock();
                    try {
                        if (mapitem.getMeso() > 0) {
                            if (mapitem.isPickedUp())
                                return;
                            map.removeMapObject((MapleMapObject)mapitem);
                            map.broadcastMessage(MaplePacketCreator.explodeDrop(mapitem.getObjectId()));
                            mapitem.setPickedUp(true);
                        } else {
                            player.getCheatTracker().registerOffense(CheatingOffense.其他异常);
                            return;
                        }
                    } finally {
                        mapitem.getLock().unlock();
                    }
                    continue;
                }
                player.getCheatTracker().registerOffense(CheatingOffense.金钱炸弹_不存在道具);
                return;
            }
        int totDamageToOneMonster = 0;
        long hpMob = 0L;
        PlayerStats stats = player.getStat();
        byte ShdowPartnerAttackPercentage = 0;
        if (attack_type == AttackType.RANGED_WITH_SHADOWPARTNER || attack_type == AttackType.NON_RANGED_WITH_MIRROR) {
            MapleStatEffect shadowPartnerEffect;
            if (attack_type == AttackType.NON_RANGED_WITH_MIRROR) {
                shadowPartnerEffect = player.getStatForBuff(MapleBuffStat.MIRROR_IMAGE);
            } else {
                shadowPartnerEffect = player.getStatForBuff(MapleBuffStat.影分身);
            }
            if (shadowPartnerEffect != null)
                if (attack.skill != 0 && attack_type != AttackType.NON_RANGED_WITH_MIRROR) {
                    ShdowPartnerAttackPercentage = (byte)shadowPartnerEffect.getY();
                } else {
                    ShdowPartnerAttackPercentage = (byte)shadowPartnerEffect.getX();
                }
            attackCount /= 2;
        }
        for (AttackPair oned : attack.allDamage) {
            MapleMonster monster = map.getMonsterByOid(oned.objectid);
            if (monster != null) {
                totDamageToOneMonster = 0;
                hpMob = monster.getHp();
                MapleMonsterStats monsterstats = monster.getStats();
                int fixeddmg = monsterstats.getFixedDamage();
                boolean Tempest = (monster.getStatusSourceID(MonsterStatus.冻结) == 21120006);
                byte overallAttackCount = 0;
                for (Pair<Integer, Boolean> eachde : (Iterable<Pair<Integer, Boolean>>)oned.attack) {
                    Integer eachd = (Integer)eachde.left;
                    overallAttackCount = (byte)(overallAttackCount + 1);
                    if (fixeddmg != -1) {
                        if (monsterstats.getOnlyNoramlAttack()) {
                            eachd = Integer.valueOf((attack.skill != 0) ? 0 : fixeddmg);
                        } else {
                            eachd = Integer.valueOf(fixeddmg);
                        }
                    } else if (!monsterstats.getOnlyNoramlAttack()) {
                        if (!player.isGM());
                    }
                    if (player == null)
                        return;
                    totDamageToOneMonster += eachd.intValue();
                    if (monster.getId() == 9300021 && player.getPyramidSubway() != null)
                        player.getPyramidSubway().onMiss(player);
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);
                if (attack.skill == 2301002 && !monsterstats.getUndead()) {
                    player.ban("修改WZ", true, true, false);
                    FileoutputUtil.logToFile_chr(player, FileoutputUtil.ban_log, "使用群体治愈伤害怪物 " + monster.getId());
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[封号系统] " + player.getName() + " 该玩家攻击异常被系统自动封号处理。").getBytes());
                    return;
                }
                double Position_range = player.getPosition().distanceSq(monster.getPosition());
                double Count_range = 7000000.0D;
                if (Position_range > Count_range && (player.getJob() != 2000 || player.getJob() != 2100 || player.getJob() != 2111 || player.getJob() != 2112)) {
                    player.getCheatTracker().registerOffense(CheatingOffense.攻击范围过大, " 技能 " + attack.skill + " 范围 : " + (long)Position_range + "正常范围 " + (long)Count_range);
                    return;
                }
                if (player.getBuffedValue(MapleBuffStat.敛财术) != null)
                    switch (attack.skill) {
                        case 0:
                        case 4001334:
                        case 4201005:
                        case 4211002:
                        case 4211004:
                        case 4221003:
                        case 4221007:
                            handlePickPocket(player, monster, oned);
                            break;
                    }
                MapleStatEffect ds = player.getStatForBuff(MapleBuffStat.隐身术);
                if (ds != null && !player.isGM() && (
                        ds.getSourceId() == 4221007 || !ds.makeChanceResult() || ds.getSourceId() == 0))
                    player.cancelEffectFromBuffStat(MapleBuffStat.隐身术);
                if (totDamageToOneMonster > 0) {
                    int remainingHP;
                    int[] skills;
                    if (attack.skill != 1221011 && attack.skill != 3221007) {
                        monster.damage(player, totDamageToOneMonster, true, attack.skill);
                    } else {
                        monster.damage(player, monster.getStats().isBoss() ? totDamageToOneMonster : monster.getHp(), true, attack.skill);
                    }
                    if (monster.isBuffed(MonsterStatus.反射物理伤害))
                        player.addHP(-(3000 + Randomizer.nextInt(1500)));
                    if (stats.hpRecoverProp > 0 &&
                            Randomizer.nextInt(100) <= stats.hpRecoverProp)
                        player.healHP(stats.hpRecover);
                    if (stats.mpRecoverProp > 0 &&
                            Randomizer.nextInt(100) <= stats.mpRecoverProp)
                        player.healMP(stats.mpRecover);
                    if (player.getBuffedValue(MapleBuffStat.连环吸血) != null) {
                        stats.setHp(stats.getHp() + (int)Math.min(monster.getMobMaxHp(), Math.min((int)(totDamage * player.getStatForBuff(MapleBuffStat.连环吸血).getX() / 10.0D), stats.getMaxHp() / 2)), true);
                        player.updateSingleStat(MapleStat.HP, player.getHp());
                    }
                    switch (attack.skill) {
                        case 4101005:
                        case 5111004:
                        case 14101006:
                        case 15111001:
                            stats.setHp(stats.getHp() + (int)Math.min(monster.getMobMaxHp(), Math.min((int)(totDamage * theSkill.getEffect(player.getSkillLevel(theSkill)).getX() / 100.0D), stats.getMaxHp() / 2)), true);
                            player.updateSingleStat(MapleStat.HP, player.getHp());
                            break;
                        case 5211006:
                        case 5220011:
                        case 22151002:
                            player.setLinkMid(monster.getObjectId());
                            break;
                        case 1311005:
                            remainingHP = stats.getHp() - totDamage * effect.getX() / 100;
                            stats.setHp((remainingHP < 1) ? 1 : remainingHP);
                            player.updateSingleStat(MapleStat.HP, player.getHp());
                            break;
                        case 4001002:
                        case 4001334:
                        case 4001344:
                        case 4111005:
                        case 4121007:
                        case 4201005:
                        case 4211002:
                        case 4221001:
                        case 4221007:
                        case 4301001:
                        case 4311002:
                        case 4311003:
                        case 4331000:
                        case 4331004:
                        case 4331005:
                        case 4341005:
                            skills = new int[] { 4120005, 4220005, 14110004 };
                            for (int i : skills) {
                                ISkill skill = SkillFactory.getSkill(i);
                                if (player.getSkillLevel(skill) > 0) {
                                    MapleStatEffect venomEffect = skill.getEffect(player.getSkillLevel(skill));
                                    if (!venomEffect.makeChanceResult())
                                        break;
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.中毒, Integer.valueOf(1), i, null, false), true, venomEffect.getDuration(), true);
                                    break;
                                }
                            }
                            break;
                        case 4201004:
                            monster.handleSteal(player);
                            break;
                        case 21000002:
                        case 21100001:
                        case 21100002:
                        case 21100004:
                        case 21110002:
                        case 21110003:
                        case 21110004:
                        case 21110006:
                        case 21110007:
                        case 21110008:
                        case 21120002:
                        case 21120005:
                        case 21120006:
                        case 21120009:
                        case 21120010:
                            if (player.getBuffedValue(MapleBuffStat.属性攻击) != null && !monster.getStats().isBoss()) {
                                MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.属性攻击);
                                if (eff != null && eff.getSourceId() == 21111005)
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.速度, Integer.valueOf(eff.getX()), eff.getSourceId(), null, false), false, (eff.getY() * 1000), false);
                            }
                            if (player.getBuffedValue(MapleBuffStat.战神抗压) != null && !monster.getStats().isBoss()) {
                                MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.战神抗压);
                                if (eff != null && eff.makeChanceResult() && !monster.isBuffed(MonsterStatus.NEUTRALISE))
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.NEUTRALISE, Integer.valueOf(1), eff.getSourceId(), null, false), false, (eff.getX() * 1000), false);
                            }
                            break;
                    }
                    if (totDamageToOneMonster > 0) {
                        IItem weapon_ = player.getInventory(MapleInventoryType.EQUIPPED).getItem((short)-11);
                        if (weapon_ != null) {
                            MonsterStatus stat = GameConstants.getStatFromWeapon(weapon_.getItemId());
                            if (stat != null && Randomizer.nextInt(100) < GameConstants.getStatChance()) {
                                MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(stat, Integer.valueOf(GameConstants.getXForStat(stat)), GameConstants.getSkillForStat(stat), null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, 10000L, false, false);
                            }
                        }
                        if (player.getBuffedValue(MapleBuffStat.刺眼箭) != null) {
                            MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.刺眼箭);
                            if (eff.makeChanceResult()) {
                                MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.命中, Integer.valueOf(eff.getX()), eff.getSourceId(), null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, (eff.getY() * 1000), false);
                            }
                        }
                        if (player.getBuffedValue(MapleBuffStat.击退箭) != null) {
                            ISkill skill = SkillFactory.getSkill(3121007);
                            MapleStatEffect eff = skill.getEffect(player.getSkillLevel(skill));
                            if (eff.makeChanceResult()) {
                                MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.速度, Integer.valueOf(eff.getX()), 3121007, null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, (eff.getY() * 1000), false);
                            }
                        }
                        if (player.getJob() == 121)
                            for (int charge : charges) {
                                ISkill skill = SkillFactory.getSkill(charge);
                                if (player.isBuffFrom(MapleBuffStat.属性攻击, skill)) {
                                    MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.冻结, Integer.valueOf(1), charge, null, false);
                                    monster.applyStatus(player, monsterStatusEffect, false, (skill.getEffect(player.getSkillLevel(skill)).getY() * 2000), false);
                                    break;
                                }
                            }
                        if (player.getJob() == 221) {
                            int[] 冰咆哮 = { 2211002 };
                            int arrayOfInt1[] = 冰咆哮, i = arrayOfInt1.length;
                            byte b = 0;
                            if (b < i) {
                                int bing = arrayOfInt1[b];
                                ISkill skill = SkillFactory.getSkill(bing);
                                MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.冻结, Integer.valueOf(1), bing, null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, (skill.getEffect(player.getSkillLevel(skill)).getY() * 2000), false);
                            }
                        }
                    }
                    if (effect != null && effect.getMonsterStati().size() > 0 &&
                            effect.makeChanceResult())
                        for (Map.Entry<MonsterStatus, Integer> z : (Iterable<Map.Entry<MonsterStatus, Integer>>)effect.getMonsterStati().entrySet())
                            monster.applyStatus(player, new MonsterStatusEffect(z.getKey(), z.getValue(), theSkill.getId(), null, false), effect.isPoison(), effect.getDuration(), false);
                }
            }
        }
        (player.getStat()).mesoBuff = 100.0D;
        Integer buff = player.getBuffedValue(MapleBuffStat.聚财术);
        if (buff != null)
            (player.getStat()).mesoBuff *= buff.doubleValue() / 100.0D;
        buff = player.getBuffedValue(MapleBuffStat.金币_率);
        if (buff != null)
            (player.getStat()).mesoBuff *= buff.doubleValue() / 100.0D;
        if (attack.skill == 4331003 && totDamageToOneMonster < hpMob)
            return;
        if (attack.skill != 0 && (attack.targets > 0 || (attack.skill != 4331003 && attack.skill != 4341002)) && attack.skill != 21101003 && attack.skill != 5110001 && attack.skill != 15100004 && attack.skill != 11101002 && attack.skill != 13101002)
            effect.applyTo(player, attack.position);
        if (totDamage > 1) {
            CheatTracker tracker = player.getCheatTracker();
            tracker.setAttacksWithoutHit(true);
            if (tracker.getAttacksWithoutHit() > 1000)
                tracker.registerOffense(CheatingOffense.人物无敌, Integer.toString(tracker.getAttacksWithoutHit()));
        }
    }

    //魔法伤害
    public static void applyAttackMagic(final AttackInfo attack, final ISkill theSkill, final MapleCharacter player, final MapleStatEffect effect) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.人物死亡攻击);
            return;
        }
        if (effect == null) {
            player.getClient().getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (attack.real) {}
        if (GameConstants.isMulungSkill(attack.skill)) {
            if (player.getMapId() / 10000 != 92502) {
                return;
            }
            player.mulung_EnergyModify(false);
        }
        if (GameConstants.isPyramidSkill(attack.skill)) {
            if (player.getMapId() / 1000000 != 926) {
                return;
            }
            if (player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player)) {
                return;
            }
        }
        final PlayerStats stats = player.getStat();
        final Element element = (player.getBuffedValue(MapleBuffStat.自然力重置) != null) ? Element.NEUTRAL : theSkill.getElement();

        double maxDamagePerHit;
        if (attack.skill == 1000 || attack.skill == 10001000 || attack.skill == 20001000 || attack.skill == 20011000 || attack.skill == 30001000) {
            maxDamagePerHit = 40;
        } else if (GameConstants.isPyramidSkill(attack.skill)) {
            maxDamagePerHit = 1;
        } else {
            final double v75 = (effect.getMatk() * 0.058);
//	    minDamagePerHit = stats.getTotalMagic() * (stats.getInt() * 0.5 + (v75 * v75) + (effect.getMastery() * 0.9 * effect.getMatk()) * 3.3) / 100;
            maxDamagePerHit = stats.getTotalMagic() * (stats.getInt() * 0.5 + (v75 * v75) + effect.getMatk() * 3.3) / 100;
        }
        maxDamagePerHit *= 1.04; // Avoid any errors for now

        double MaxDamagePerHit = 0;
        int totDamage = 0;
        final ISkill eaterSkill = SkillFactory.getSkill(GameConstants.getMPEaterForJob(player.getJob()));
        final int eaterLevel = player.getSkillLevel(eaterSkill);
        final MapleMap map = player.getMap();
        if (map.isPvpMap()) {
            MaplePvp.doPvP(player, map, attack, effect);
        }
        else if (map.isPartyPvpMap()) {
            MaplePvp.doPartyPvP(player, map, attack, effect);
        }
        else if (map.isGuildPvpMap()) {
            MaplePvp.doGuildPvP(player, map, attack, effect);
        }
        for (final AttackPair oned : attack.allDamage) {
            final MapleMonster monster = map.getMonsterByOid(oned.objectid);
            if (monster != null) {
                final boolean Tempest = monster.getStatusSourceID(MonsterStatus.冻结) == 21120006 && !monster.getStats().isBoss();
                int totDamageToOneMonster = 0;
                final MapleMonsterStats monsterstats = monster.getStats();
                final int fixeddmg = monsterstats.getFixedDamage();
                byte overallAttackCount = 0;
                for (final Pair<Integer, Boolean> eachde : oned.attack) {
                    Integer eachd = eachde.left;
                    ++overallAttackCount;
                    if (fixeddmg != -1) {
                        eachd = (monsterstats.getOnlyNoramlAttack() ? 0 : fixeddmg);
                    }
                    else if (monsterstats.getOnlyNoramlAttack()) {
                        eachd = 0;
                    } else if (!player.isGM()) {
                        if (Tempest) { // Buffed with Tempest
                            // In special case such as Chain lightning, the damage will be reduced from the maxMP.
                            if (eachd > monster.getMobMaxHp()) {
                                eachd = (int) Math.min(monster.getMobMaxHp(), Integer.MAX_VALUE);
                                player.getCheatTracker().registerOffense(CheatingOffense.魔法伤害过高);
                            }
                        } else if (!monster.isBuffed(MonsterStatus.免疫伤害) && !monster.isBuffed(MonsterStatus.免疫魔法攻击) && !monster.isBuffed(MonsterStatus.反射物理伤害)) {
                            if (eachd > maxDamagePerHit) {
                                player.getCheatTracker().registerOffense(CheatingOffense.魔法伤害过高);
                                if (eachd > MaxDamagePerHit * 2) {
//				    System.out.println("EXCEED!!! Client damage : " + eachd + " Server : " + MaxDamagePerHit);
                                    eachd = (int) (MaxDamagePerHit * 2); // Convert to server calculated damage
                                    FileoutputUtil.logToFile_chr(player, FileoutputUtil.fixdam_ph, " 技能 " + attack.skill + " 怪物 " + monster.getId() + " 预计伤害:" + (long) MaxDamagePerHit + "  实际" + eachd);
                                    player.getCheatTracker().registerOffense(CheatingOffense.魔法伤害过高2);
                                }
                            }
                        } else if (eachd > maxDamagePerHit * 2) {
                            FileoutputUtil.logToFile_chr(player, FileoutputUtil.fixdam_ph, " 技能 " + attack.skill + " 怪物 " + monster.getId() + " 预计伤害:" + (long) MaxDamagePerHit + "  实际" + eachd);
                            eachd = (int) (maxDamagePerHit);
                        }
                    }
                    totDamageToOneMonster += eachd;
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);
                final double Position_range = player.getPosition().distanceSq(monster.getPosition());
                final double Count_range = 7000000.0;
                if (Position_range > Count_range && (player.getJob() != 2000 || player.getJob() != 2100 || player.getJob() != 2111 || player.getJob() != 2112)) {
                    player.getCheatTracker().registerOffense(CheatingOffense.攻击范围过大, " 技能 " + attack.skill + " 范围 : " + (long)Position_range + "正常范围 " + (long)Count_range);
                    return;
                }
                if (attack.skill == 2301002 && !monsterstats.getUndead()) {
                    player.getCheatTracker().registerOffense(CheatingOffense.治愈术攻击非不死系怪物);
                    return;
                }
                if (totDamageToOneMonster <= 0) {
                    continue;
                }
                monster.damage(player, totDamageToOneMonster, true, attack.skill);
                if (monster.isBuffed(MonsterStatus.反射魔法伤害)) {
                    player.addHP(-(3000 + Randomizer.nextInt(1500)));
                }
                switch (attack.skill) {
                    case 2221003: {
                        monster.setTempEffectiveness(Element.FIRE, theSkill.getEffect(player.getSkillLevel(theSkill)).getDuration());
                        break;
                    }
                    case 2121003: {
                        monster.setTempEffectiveness(Element.ICE, theSkill.getEffect(player.getSkillLevel(theSkill)).getDuration());
                        break;
                    }
                }
                if (effect != null && effect.getMonsterStati().size() > 0 && effect.makeChanceResult()) {
                    for (final Map.Entry<MonsterStatus, Integer> z : effect.getMonsterStati().entrySet()) {
                        monster.applyStatus(player, new MonsterStatusEffect(z.getKey(), z.getValue(), theSkill.getId(), null, false), effect.isPoison(), effect.getDuration(), false);
                    }
                }
                if (eaterLevel <= 0) {
                    continue;
                }
                eaterSkill.getEffect(eaterLevel).applyPassive(player, monster);
            }
        }
        if (attack.skill != 2301002) {
            effect.applyTo(player);
        }
        player.getStat().mesoBuff = 100.0;
        Integer buff = player.getBuffedValue(MapleBuffStat.聚财术);
        if (buff != null) {
            final PlayerStats stat = player.getStat();
            stat.mesoBuff *= buff / 100.0;
        }
        buff = player.getBuffedValue(MapleBuffStat.金币_率);
        if (buff != null) {
            final PlayerStats stat2 = player.getStat();
            stat2.mesoBuff *= buff / 100.0;
        }
        if (totDamage > 1) {
            final CheatTracker tracker = player.getCheatTracker();
            tracker.setAttacksWithoutHit(true);
            if (tracker.getAttacksWithoutHit() > 1000) {
                tracker.registerOffense(CheatingOffense.人物无敌, Integer.toString(tracker.getAttacksWithoutHit()));
            }
        }
    }
    
    private static double CalculateMaxMagicDamagePerHit(final MapleCharacter chr, final ISkill skill, final MapleMonster monster, final MapleMonsterStats mobstats, final PlayerStats stats, final Element elem, final Integer sharpEye, final double maxDamagePerMonster) {
        final int dLevel = Math.max(mobstats.getLevel() - chr.getLevel(), 0);
        final int Accuracy = (int)(Math.floor(stats.getTotalInt() / 10.0) + Math.floor(stats.getTotalLuk() / 10.0));
        final int MinAccuracy = mobstats.getEva() * (dLevel * 2 + 51) / 120;
        if (MinAccuracy > Accuracy && skill.getId() != 1000 && skill.getId() != 10001000 && skill.getId() != 20001000 && skill.getId() != 20011000 && skill.getId() != 30001000 && !GameConstants.isPyramidSkill(skill.getId())) {
            return 0.0;
        }
        double elemMaxDamagePerMob = 0.0;
        switch (monster.getEffectiveness(elem)) {
            case 免疫: {
                elemMaxDamagePerMob = 1.0;
                break;
            }
            case 正常: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster, stats);
                break;
            }
            case 虚弱: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster * 1.5, stats);
                break;
            }
            case 增强: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster * 0.5, stats);
                break;
            }
            default: {
                throw new RuntimeException("Unknown enum constant");
            }
        }
        elemMaxDamagePerMob -= mobstats.getMagicDefense() * 0.5;
        elemMaxDamagePerMob += elemMaxDamagePerMob / 100.0 * sharpEye;
        elemMaxDamagePerMob += elemMaxDamagePerMob * (mobstats.isBoss() ? stats.bossdam_r : stats.dam_r) / 100.0;
        switch (skill.getId()) {
            case 1000:
            case 10001000:
            case 20001000:
            case 20011000:
            case 30001000: {
                elemMaxDamagePerMob = 40.0;
                break;
            }
            case 1020:
            case 10001020:
            case 20001020:
            case 20011020:
            case 30001020: {
                elemMaxDamagePerMob = 1.0;
                break;
            }
        }
        if (skill.getId() == 2301002) {
            elemMaxDamagePerMob *= 2.0;
        }
        if (elemMaxDamagePerMob > 199999.0) {
            elemMaxDamagePerMob = 199999.0;
        }
        else if (elemMaxDamagePerMob < 0.0) {
            elemMaxDamagePerMob = 1.0;
        }
        return elemMaxDamagePerMob;
    }
    
    private static double ElementalStaffAttackBonus(final Element elem, final double elemMaxDamagePerMob, final PlayerStats stats) {
        switch (elem) {
            case FIRE: {
                return elemMaxDamagePerMob / 100.0 * stats.element_fire;
            }
            case ICE: {
                return elemMaxDamagePerMob / 100.0 * stats.element_ice;
            }
            case LIGHTING: {
                return elemMaxDamagePerMob / 100.0 * stats.element_light;
            }
            case POISON: {
                return elemMaxDamagePerMob / 100.0 * stats.element_psn;
            }
            default: {
                return elemMaxDamagePerMob / 100.0 * stats.def;
            }
        }
    }
    
    private static void handlePickPocket(final MapleCharacter player, final MapleMonster mob, final AttackPair oned) {
        final int maxmeso = player.getBuffedValue(MapleBuffStat.敛财术);
        final ISkill skill = SkillFactory.getSkill(4211003);
        final MapleStatEffect s = skill.getEffect(player.getSkillLevel(skill));
        for (final Pair eachde : oned.attack) {
            final Integer eachd = (Integer)eachde.left;
            if (s.makeChanceResult()) {
                player.getMap().spawnMesoDrop(Math.min((int)Math.max(eachd / 20000.0 * maxmeso, 1.0), maxmeso), new Point((int)(mob.getTruePosition().getX() + Randomizer.nextInt(100) - 50.0), (int)mob.getTruePosition().getY()), mob, player, false, (byte)0);
            }
        }
    }
    
    private static double CalculateMaxWeaponDamagePerHit(final MapleCharacter player, final MapleMonster monster, final AttackInfo attack, final ISkill theSkill, final MapleStatEffect attackEffect, double maximumDamageToMonster, final Integer CriticalDamagePercent) {
        if (player.getMapId() / 1000000 == 914) {
            return 199999.0;
        }
        final List<Element> elements = new ArrayList<Element>();
        boolean defined = false;
        if (theSkill != null) {
            elements.add(theSkill.getElement());
            switch (theSkill.getId()) {
                case 3001004:
                case 33101001: {
                    defined = true;
                    break;
                }
                case 1000:
                case 10001000:
                case 20001000:
                case 20011000:
                case 30001000: {
                    maximumDamageToMonster = 40.0;
                    defined = true;
                    break;
                }
                case 1020:
                case 10001020:
                case 20001020:
                case 20011020:
                case 30001020: {
                    maximumDamageToMonster = 1.0;
                    defined = true;
                    break;
                }
                case 4331003: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 199999L : monster.getHp());
                    defined = true;
                    break;
                }
                case 3221007: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 199999L : monster.getMobMaxHp());
                    defined = true;
                    break;
                }
                case 1221011: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 199999L : (monster.getHp() - 1L));
                    defined = true;
                    break;
                }
                case 4211006: {
                    maximumDamageToMonster = 750000.0;
                    defined = true;
                    break;
                }
                case 1009:
                case 10001009:
                case 20001009:
                case 20011009:
                case 30001009: {
                    defined = true;
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? (monster.getMobMaxHp() / 30L * 100L) : monster.getMobMaxHp());
                    break;
                }
                case 3211006: {
                    if (monster.getStatusSourceID(MonsterStatus.冻结) == 3211003) {
                        defined = true;
                        maximumDamageToMonster = (double)monster.getHp();
                        break;
                    }
                    break;
                }
            }
        }
        if (player.getBuffedValue(MapleBuffStat.属性攻击) != null) {
            final int chargeSkillId = player.getBuffSource(MapleBuffStat.属性攻击);
            switch (chargeSkillId) {
                case 1211003:
                case 1211004: {
                    elements.add(Element.FIRE);
                    break;
                }
                case 1211005:
                case 1211006:
                case 21111005: {
                    elements.add(Element.ICE);
                    break;
                }
                case 1211007:
                case 1211008:
                case 15101006: {
                    elements.add(Element.LIGHTING);
                    break;
                }
                case 1221003:
                case 1221004:
                case 11111007: {
                    elements.add(Element.HOLY);
                    break;
                }
                case 12101005: {
                    elements.clear();
                    break;
                }
            }
        }
        if (player.getBuffedValue(MapleBuffStat.LIGHTNING_CHARGE) != null) {
            elements.add(Element.LIGHTING);
        }
        double elementalMaxDamagePerMonster = maximumDamageToMonster;
        if (elements.size() > 0) {
            double elementalEffect = 0.0;
            switch (attack.skill) {
                case 3211003: {
                    elementalEffect = attackEffect.getX() / 200.0;
                    break;
                }
                default: {
                    elementalEffect = 0.5;
                    break;
                }
            }
            for (final Element element : elements) {
                switch (monster.getEffectiveness(element)) {
                    case 免疫: {
                        elementalMaxDamagePerMonster = 1.0;
                        continue;
                    }
                    case 虚弱: {
                        elementalMaxDamagePerMonster *= 1.0 + elementalEffect;
                        continue;
                    }
                    case 增强: {
                        elementalMaxDamagePerMonster *= 1.0 - elementalEffect;
                        continue;
                    }
                }
            }
        }
        final short moblevel = monster.getStats().getLevel();
        final short d = (short)((moblevel > player.getLevel()) ? ((short)(moblevel - player.getLevel())) : 0);
        elementalMaxDamagePerMonster = elementalMaxDamagePerMonster * (1.0 - 0.01 * d) - monster.getStats().getPhysicalDefense() * 0.5;
        elementalMaxDamagePerMonster += elementalMaxDamagePerMonster / 100.0 * CriticalDamagePercent;
        if (theSkill != null && theSkill.isChargeSkill() && player.getKeyDownSkill_Time() == 0L) {
            return 0.0;
        }
        final MapleStatEffect homing = player.getStatForBuff(MapleBuffStat.导航辅助);
        if (homing != null && player.getLinkMid() == monster.getObjectId() && homing.getSourceId() == 5220011) {
            elementalMaxDamagePerMonster += elementalMaxDamagePerMonster * homing.getX();
        }
        final PlayerStats stat = player.getStat();
        elementalMaxDamagePerMonster += elementalMaxDamagePerMonster * (monster.getStats().isBoss() ? stat.bossdam_r : stat.dam_r) / 100.0;
        if (player.getDebugMessage()) {
            player.dropMessage("[伤害计算] 属性伤害:" + (int)elementalMaxDamagePerMonster);
        }
        if (elementalMaxDamagePerMonster > 199999.0) {
            if (!defined) {
                elementalMaxDamagePerMonster = 199999.0;
            }
        }
        else if (elementalMaxDamagePerMonster < 0.0) {
            elementalMaxDamagePerMonster = 1.0;
        }
        return elementalMaxDamagePerMonster;
    }
    
    public static AttackInfo DivideAttack(final AttackInfo attack, final int rate) {
        attack.real = false;
        if (rate <= 1) {
            return attack;
        }
        for (final AttackPair p : attack.allDamage) {
            if (p.attack != null) {
                for (final Pair<Integer, Boolean> pair : p.attack) {
                    final Pair<Integer, Boolean> eachd = pair;
                    pair.left /= rate;
                }
            }
        }
        return attack;
    }

    //远程普通攻击，技能攻击
    public static AttackInfo Modify_AttackCrit(final AttackInfo attack, final MapleCharacter chr, final int type) {
        final int CriticalRate = chr.getStat().passive_sharpeye_rate();
        final boolean shadow = (type == 2 && chr.getBuffedValue(MapleBuffStat.影分身) != null) || (type == 1 && chr.getBuffedValue(MapleBuffStat.MIRROR_IMAGE) != null);
        if (attack.skill != 4211006 && attack.skill != 3211003 && attack.skill != 4111004 && (CriticalRate > 0 || attack.skill == 4221001 || attack.skill == 3221007)) {
            for (final AttackPair p : attack.allDamage) {
                if (p.attack != null) {
                    int hit = 0;
                    final int mid_att = p.attack.size() / 2;
                    final List<Pair<Integer, Boolean>> eachd_copy = new ArrayList<Pair<Integer, Boolean>>(p.attack);
                    for (final Pair<Integer, Boolean> eachd : p.attack) {
                        ++hit;
                        if (!eachd.right) {
                            if (attack.skill == 4221001) {
                                eachd.right = (hit == 4 && Randomizer.nextInt(100) < 90);
                            }
                            else if (attack.skill == 3221007 || eachd.left > 199999) {
                                eachd.right = true;
                            }
                            else if (shadow && hit > mid_att) {
                                eachd.right = eachd_copy.get(hit - 1 - mid_att).right;
                            }
                            else {
                                eachd.right = (Randomizer.nextInt(100) < CriticalRate);
                            }
                            eachd_copy.get(hit - 1).right = eachd.right;
                        }
                    }
                }
            }
        }
        return attack;
    }
    
    public static final AttackInfo parseDmgMa(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 2121001:
            case 2221001:
            case 2321001:
            case 22121000:
            case 22151001: {
                ret.charge = lea.readInt();
                break;
            }
            default: {
                ret.charge = -1;
                break;
            }
        }
        lea.skip(1);
        ret.unk = 0;
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        ret.lastAttackTickCount = lea.readInt();
        ret.allDamage = new ArrayList<AttackPair>();
        for (int i = 0; i < ret.targets; ++i) {
            int oid = lea.readInt();
            lea.skip(14);
            List<Pair<Integer, Boolean>> allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();
            MapleMonster monster = chr.getMap().getMonsterByOid(oid);
            String PGInfo = "#b#e[破攻伤害]\r\n";
            for (int j = 0; j < ret.hits; ++j) {
                PGInfo = "";
                int damage = lea.readInt();
                int maxdamage = 199999 + chr.getVip() * 10000;
                boolean show = false;
                boolean baoji = false;
                boolean pogong = false;
                if (chr.getStat().getTotalMagic() >= 1999 && damage > 1) {
                    final ISkill skill = SkillFactory.getSkill(ret.skill);
                    int matk = skill.getEffect(chr.getSkillLevel(skill)).getMatk();
                    if (Randomizer.nextInt(100) < chr.getStat().passive_sharpeye_rate()) {
                        matk += chr.getStat().passive_sharpeye_percent();
                        baoji = true;
                    }
                    final float baseAttack = chr.getStat().getCurrentMaxBaseDamage();
                    int tmpdamage = (int)(baseAttack / 100.0 * matk / 100.0 * (80 + Randomizer.nextInt(21)));
                    if (monster != null) {
                        final Element element = (chr.getBuffedValue(MapleBuffStat.自然力重置) != null) ? Element.NEUTRAL : skill.getElement();
                        switch (monster.getEffectiveness(element)) {
                            case 免疫: {
                                tmpdamage = 1;
                                break;
                            }
                            case 正常: {
                                tmpdamage = (int)ElementalStaffAttackBonus(element, tmpdamage, chr.getStat());
                                break;
                            }
                            case 虚弱: {
                                tmpdamage = (int)ElementalStaffAttackBonus(element, tmpdamage * 1.5, chr.getStat());
                                break;
                            }
                            case 增强: {
                                tmpdamage = (int)ElementalStaffAttackBonus(element, tmpdamage * 0.5, chr.getStat());
                                break;
                            }
                            default: {
                                throw new RuntimeException("Unknown enum constant");
                            }
                        }
                         short moblevel = monster.getStats().getLevel();
                         short d = ((moblevel > chr.getLevel()) ? ((short)(moblevel - chr.getLevel())) : 0);
                        tmpdamage = (int)(tmpdamage * (1.0 - 0.01 * d) - monster.getStats().getMagicDefense() * 0.5);
                        if (tmpdamage < 0) {
                            tmpdamage = 1;
                        }
                        tmpdamage *= (monster.getStats().isBoss() ? chr.getStat().bossdam_r : chr.getStat().dam_r);
                    }
                    if (tmpdamage > damage) {
                        damage = tmpdamage;
                        show = true;
                    }
                }
                damage = calcMonsterDecreaseDamage(damage, monster, chr, true);
                if (show) {
                    if (baoji) {
                        PGInfo = "#b#e[破攻伤害]\r\n#r" + damage + "\r\n";
                    }
                    else {
                        PGInfo = "#b#e[破攻伤害]\r\n#d" + damage + "\r\n";
                    }
                }
                if (damage > maxdamage) {
                    damage = maxdamage;
                    pogong = true;
                }
                if (pogong) {
                    PGInfo = PGInfo + "#b#e[已达上限]\r\n#r" + damage;
                }
                allDamageNumbers.add(new Pair<Integer, Boolean>(damage, false));
            }
            if (PGInfo.length() > 12) {}
            lea.skip(4);
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
        }
        ret.position = lea.readPos();
        return ret;
    }
    
    public static final AttackInfo parseDmgM(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 4341002:
            case 4341003:
            case 5101004:
            case 5201002:
            case 14111006:
            case 15101003: {
                ret.charge = lea.readInt();
                break;
            }
            default: {
                ret.charge = 0;
                break;
            }
        }
        lea.skip(1);
        ret.unk = 0;
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        ret.lastAttackTickCount = lea.readInt();
        ret.allDamage = new ArrayList<AttackPair>();
        if (ret.skill == 4211006) {
            return parseMesoExplosion(lea, ret, chr);
        }
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(14);
            final List<Pair<Integer, Boolean>> allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();
            final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
            String PGInfo = "#b#e[破攻伤害]\r\n";
            for (int j = 0; j < ret.hits; ++j) {
                PGInfo = "";
                int damage = lea.readInt();
                final int maxdamage = 199999 + chr.getVip() * 10000;
                boolean show = false;
                boolean pogong = false;
                if (chr.getStat().getTotalWatk() > 1999 && damage > 1) {
                    damage = (int)(damage / 1999.0 * chr.getStat().getTotalWatk());
                    show = true;
                }
                if (ret.skill == 1221011 || ret.skill == 3221007) {
                    damage = 199999;
                }
                damage = calcMonsterDecreaseDamage(damage, monster, chr, true);
                if (show) {
                    PGInfo = "#b#e[破攻伤害]\r\n#d" + damage + "\r\n";
                }
                if (damage > maxdamage) {
                    damage = maxdamage;
                    pogong = true;
                }
                if (pogong) {
                    PGInfo = PGInfo + "#b#e[已达上限]\r\n#r" + damage;
                }
                allDamageNumbers.add(new Pair<Integer, Boolean>(damage, false));
            }
            if (PGInfo.length() > 12) {}
            lea.skip(4);
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
        }
        ret.position = lea.readPos();
        return ret;
    }


    //最终BOSS、扎昆
    public static AttackInfo parseDmgR(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 3121004:
            case 3221001:
            case 5221004:
            case 13111002: {
                lea.skip(4);
                break;
            }
        }
        ret.charge = -1;
        lea.skip(1);
        ret.unk = 0;
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        ret.lastAttackTickCount = lea.readInt();
        ret.starSlot = lea.readShort();
        ret.cashSlot = lea.readShort();
        ret.AOE = lea.readByte();
        ret.allDamage = new ArrayList<AttackPair>();
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(14);
            final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
            final List<Pair<Integer, Boolean>> allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();
            String PGInfo = "#b#e[破攻伤害]\r\n";
            for (int j = 0; j < ret.hits; ++j) {
                PGInfo = "";
                int damage = lea.readInt();
                final int maxdamage = 199999 + chr.getVip() * 10000;
                boolean show = false;
                boolean baoji = false;
                boolean pogong = false;
                if (damage > 1 && (ret.skill == 4121007 || ret.skill == 4001344 || ret.skill == 14001004 || ret.skill == 14111005)) {
                    final ISkill skill = SkillFactory.getSkill(ret.skill);
                    int watk = skill.getEffect(chr.getSkillLevel(skill)).getDamage();
                    if (Randomizer.nextInt(100) < chr.getStat().passive_sharpeye_rate()) {
                        watk += chr.getStat().passive_sharpeye_percent();
                        baoji = true;
                    }
                    final float baseAttack = chr.getStat().getCurrentMaxBaseDamage();
                    damage = (int)(baseAttack / 100.0 * watk / 100.0 * (80 + Randomizer.nextInt(21)));
                    if (monster != null) {
                        final Element element = (chr.getBuffedValue(MapleBuffStat.自然力重置) != null) ? Element.NEUTRAL : skill.getElement();
                        switch (monster.getEffectiveness(element)) {
                            case 免疫: {
                                damage = 1;
                                break;
                            }
                            case 虚弱: {
                                damage *= 1.5;
                                break;
                            }
                            case 增强: {
                                damage *= 0.5;
                                break;
                            }
                        }
                        final short moblevel = monster.getStats().getLevel();
                        final short d = ((moblevel > chr.getLevel()) ? ((short)(moblevel - chr.getLevel())) : 0);
                        damage = (int)(damage * (1.0 - 0.01 * d) - monster.getStats().getPhysicalDefense() * 0.5);
                        if (damage < 0) {
                            damage = 1;
                        }
                        damage *= (monster.getStats().isBoss() ? chr.getStat().bossdam_r : chr.getStat().dam_r);
                    }
                    show = true;
                } else if (chr.getStat().getTotalWatk() > 1999 && damage > 1) {
                    damage = (int)(damage / 1999.0 * chr.getStat().getTotalWatk());
                    show = true;
                }
                if (ret.skill == 1221011 || ret.skill == 3221007) {
                    damage = 199999;
                }
                damage = calcMonsterDecreaseDamage(damage, monster, chr, true);
                if (show) {
                    if (baoji) {
                        PGInfo = "#b#e[破攻伤害]\r\n#r" + damage + "\r\n";
                    }
                    else {
                        PGInfo = "#b#e[破攻伤害]\r\n#d" + damage + "\r\n";
                    }
                }
                if (damage > maxdamage) {
                    damage = maxdamage;
                    pogong = true;
                }
                if (pogong) {
                    PGInfo = PGInfo + "#b#e[已达上限]\r\n#r" + damage;
                }
                allDamageNumbers.add(new Pair<Integer, Boolean>(damage, false));
            }
            if (PGInfo.length() > 12) {
                chr.getClient().getSession().write(MaplePacketCreator.sendHint(PGInfo, 80, 5));
            }
            lea.skip(4);
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
        }
        lea.skip(4);
        ret.position = lea.readPos();
        return ret;
    }
    
    public static final AttackInfo parseMesoExplosion(final LittleEndianAccessor lea, final AttackInfo ret, final MapleCharacter chr) {
        if (ret.hits == 0) {
            lea.skip(4);
            final byte bullets = lea.readByte();
            for (int j = 0; j < bullets; ++j) {
                ret.allDamage.add(new AttackPair(lea.readInt(), null));
                lea.skip(1);
            }
            lea.skip(2);
            return ret;
        }
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(12);
            final byte bullets = lea.readByte();
            final List<Pair<Integer, Boolean>> allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();
            for (int k = 0; k < bullets; ++k) {
                final int damage = lea.readInt();
                allDamageNumbers.add(new Pair<Integer, Boolean>(damage, false));
            }
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
            lea.skip(4);
        }
        lea.skip(4);
        final byte bullets = lea.readByte();
        for (int l = 0; l < bullets; ++l) {
            ret.allDamage.add(new AttackPair(lea.readInt(), null));
            lea.skip(1);
        }
        lea.skip(2);
        return ret;
    }
    
    public static String Damage_AttackCount(final MapleCharacter player, final MapleStatEffect effect, final AttackInfo attack, final int attackCount) {
        String reason = "null";
        int last = attackCount;
        boolean mirror_fix = false;
        if (player.getJob() >= 411 && player.getJob() <= 412) {
            mirror_fix = true;
        }
        if (mirror_fix) {
            last *= 2;
        }
        if (attack.hits > last) {
            reason = "封包伤害次数 : " + last + " 封包伤害次数: " + attack.skill;
        }
        return reason;
    }
    
    public static String Damage_MobCount(final MapleCharacter player, final MapleStatEffect effect, final AttackInfo attack) {
        String reason = "null";
        if (attack.targets > effect.getMobCount()) {
            reason = "打怪数量过多， 封包数量: " + attack.targets + " 正确数量:" + effect.getMobCount();
        }
        return reason;
    }
    
    public static int maxDamage(final MapleCharacter chr, final AttackInfo ret, int damage) {
        final int VipCount = chr.getVip();
        final int maxdamage = 199999 + VipCount * 10000;
        final int randomNum = Randomizer.nextInt(20) + 80;
        int tempDamage = 0;
        for (final IItem item : chr.getInventory(MapleInventoryType.EQUIPPED)) {
            int ak = 0;
            if (item != null && item instanceof Equip) {
                ak = MapleItemInformationProvider.getInstance().getTotalStat((Equip)item);
            }
            tempDamage += ak * 10;
        }
        if (ret.skill != 14101006 && damage >= 199999) {
            tempDamage += (chr.getStat().getInt() + chr.getStat().getStr() + chr.getStat().getDex() + chr.getStat().getLuk()) * 5;
            damage = (tempDamage + 199999) * randomNum / 100;
            if (damage < 199999) {
                damage = 199999;
            }
            damage = Math.min(damage, 199999 + VipCount * 10000);
            chr.getClient().getSession().write(MaplePacketCreator.sendHint("#e[破攻伤害P]:#r" + damage + "#b ", 250, 5));
        }
        if (damage > maxdamage) {
            damage = maxdamage;
        }
        tempDamage = 0;
        return damage;
    }
    
    public static int calcMonsterDecreaseDamage(int damage, final MapleMonster monster, final MapleCharacter chr, final boolean show) {
        boolean jianshang = false;
        if (monster != null) {
            switch (monster.getId()) {
                case 9700002: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9700001: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9700006: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9700011: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8520000: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8510000: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9400593: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9400592: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9400591: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9400590: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9400589: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8500002: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9420522: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9420544: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9420549: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8810006: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8810001: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8810000: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8810005: {
                    damage *= (int)0.3;
                    jianshang = true;
                    break;
                }
                case 8800000: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8800001: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8800002: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 8820001: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9600025: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                case 9500363: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
                    case 9300215: {
                    damage *= 1;
                    jianshang = true;
                    break;
                }
            }
        }
        if (!jianshang || show) {}
        return damage;
    }
    
    static {
        charges = new int[] { 1211005, 1211006 };
    }
}
