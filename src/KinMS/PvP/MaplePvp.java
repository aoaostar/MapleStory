package KinMS.PvP;

import handling.channel.handler.*;
import client.*;
import java.awt.*;
import java.util.*;
import server.maps.*;
import server.*;
import tools.*;
import handling.world.*;
import server.life.*;
import handling.*;

public class MaplePvp
{
    private static PvpAttackInfo parsePvpAttack(final AttackInfo attack, final MapleCharacter player, final MapleStatEffect effect) {
        final PvpAttackInfo ret = new PvpAttackInfo();
        double maxdamage = player.getLevel() + 100.0;
        final int skillId = attack.skill;
        ret.skillId = skillId;
        ret.critRate = 8;
        ret.ignoreDef = 0;
        ret.skillDamage = 1000;
        ret.mobCount = 3;
        ret.attackCount = 1;
        final int pvpRange = attack.isCloseRangeAttack ? 35 : 70;
        ret.facingLeft = (attack.animation < 0);
        if (skillId != 0 && effect != null) {
            ret.skillDamage = effect.getDamage();
            ret.mobCount = Math.max(1, effect.getMobCount());
            ret.attackCount = Math.max(effect.getBulletCount(), effect.getAttackCount());
            ret.box = effect.calculateBoundingBox(player.getTruePosition(), ret.facingLeft, pvpRange);
        }
        else {
            ret.box = calculateBoundingBox(player.getTruePosition(), ret.facingLeft, pvpRange);
        }
        final boolean mirror = player.getBuffedValue(MapleBuffStat.影分身) != null;
        final PvpAttackInfo pvpAttackInfo = ret;
        pvpAttackInfo.attackCount *= (mirror ? 2 : 1);
        maxdamage *= ret.skillDamage / 100.0;
        ret.maxDamage = maxdamage * ret.attackCount;
        if (player.isGM()) {
            player.dropMessage(6, "Pvp伤害解析 - 最大攻击: " + maxdamage + " 数量: " + ret.mobCount + " 次数: " + ret.attackCount + " 爆击: " + ret.critRate + " 无视: " + ret.ignoreDef + " 技能伤害: " + ret.skillDamage);
        }
        return ret;
    }
    
    private static Rectangle calculateBoundingBox(final Point posFrom, final boolean facingLeft, final int range) {
        final Point lt = new Point(-70, -30);
        final Point rb = new Point(-10, 0);
        Point mylt;
        Point myrb;
        if (facingLeft) {
            mylt = new Point(lt.x + posFrom.x - range, lt.y + posFrom.y);
            myrb = new Point(rb.x + posFrom.x, rb.y + posFrom.y);
        }
        else {
            myrb = new Point(lt.x * -1 + posFrom.x + range, rb.y + posFrom.y);
            mylt = new Point(rb.x * -1 + posFrom.x, lt.y + posFrom.y);
        }
        return new Rectangle(mylt.x, mylt.y, myrb.x - mylt.x, myrb.y - mylt.y);
    }
    
    public static boolean inArea(final MapleCharacter chr) {
        for (final Rectangle rect : chr.getMap().getAreas()) {
            if (rect.contains(chr.getTruePosition())) {
                return true;
            }
        }
        return false;
    }
    
    private static void monsterBomb(final MapleCharacter player, final MapleCharacter attacked, final MapleMap map, final PvpAttackInfo attack) {
        if (player == null || attacked == null || map == null) {
            return;
        }
        double maxDamage = attack.maxDamage;
        boolean isCritDamage = false;
        if (player.getLevel() > attacked.getLevel() + 10) {
            maxDamage *= 1.05;
        }
        else if (player.getLevel() < attacked.getLevel() - 10) {
            maxDamage /= 1.05;
        }
        else if (player.getLevel() > attacked.getLevel() + 20) {
            maxDamage *= 1.1;
        }
        else if (player.getLevel() < attacked.getLevel() - 20) {
            maxDamage /= 1.1;
        }
        else if (player.getLevel() > attacked.getLevel() + 30) {
            maxDamage *= 1.15;
        }
        else if (player.getLevel() < attacked.getLevel() - 30) {
            maxDamage /= 1.15;
        }
        if (Randomizer.nextInt(100) < attack.critRate) {
            maxDamage *= 1.5;
            isCritDamage = true;
        }
        int attackedDamage = (int)Math.floor(Math.random() * ((int)maxDamage * 0.35) + (int)maxDamage * 0.65);
        final int MAX_PVP_DAMAGE = (int)(player.getStat().getLimitBreak(player) / 100.0);
        final int MIN_PVP_DAMAGE = 100;
        if (attackedDamage > MAX_PVP_DAMAGE) {
            attackedDamage = MAX_PVP_DAMAGE;
        }
        if (attackedDamage < MIN_PVP_DAMAGE) {
            attackedDamage = MIN_PVP_DAMAGE;
        }
        int hploss = attackedDamage;
        int mploss = 0;
        if (attackedDamage > 0) {
            if (attacked.getBuffedValue(MapleBuffStat.魔法盾) != null) {
                mploss = (int)(attackedDamage * (attacked.getBuffedValue(MapleBuffStat.魔法盾) / 100.0));
                hploss -= mploss;
                if (attacked.getBuffedValue(MapleBuffStat.终极无限) != null) {
                    mploss = 0;
                }
                else if (mploss > attacked.getStat().getMp()) {
                    mploss = attacked.getStat().getMp();
                    hploss -= mploss;
                }
                attacked.addMPHP(-hploss, -mploss);
            }
            else {
                attacked.addHP(-hploss);
            }
        }
        final MapleMonster pvpMob = MapleLifeFactory.getMonster(9400711);
        map.spawnMonsterOnGroundBelow(pvpMob, attacked.getPosition());
        map.broadcastMessage(MaplePacketCreator.PVPdamagePlayer(attacked.getId(), 2, pvpMob.getId(), hploss));
        if (isCritDamage) {
            player.dropMessage(6, "你对玩家 " + attacked.getName() + " 造成了 " + hploss + " 点爆击伤害! 对方血量: " + attacked.getStat().getHp() + "/" + attacked.getStat().getCurrentMaxHp());
            attacked.dropMessage(6, "玩家 " + player.getName() + " 对你造成了 " + hploss + " 点爆击伤害!");
        }
        else {
            player.dropTopMsg("你对玩家 " + attacked.getName() + " 造成了 " + hploss + " 点伤害! 对方血量: " + attacked.getStat().getHp() + "/" + attacked.getStat().getCurrentMaxHp());
            attacked.dropTopMsg("玩家 " + player.getName() + " 对你造成了 " + hploss + " 点伤害!");
        }
        map.killMonster(pvpMob, player, false, false, (byte)1);
        if (attacked.getStat().getHp() <= 0 && !attacked.isAlive()) {
            int expReward = attacked.getLevel() * 10 * (attacked.getLevel() / player.getLevel());
            final int gpReward = (int)Math.floor(Math.random() * 10.0 + 10.0);
            if (player.getPvpKills() * 0.25 >= player.getPvpDeaths()) {
                expReward *= 2;
            }
            player.gainExp(expReward, true, false, true);
            if (player.getGuildId() > 0 && player.getGuildId() != attacked.getGuildId()) {
                World.Guild.gainGP(player.getGuildId(), gpReward);
            }
            player.gainPvpKill();
            player.dropMessage(6, "你击败了玩家 " + attacked.getName() + "!! ");
            final int pvpVictory = attacked.getPvpVictory();
            attacked.gainPvpDeath();
            attacked.dropMessage(6, player.getName() + " 将你击败!");
            final MaplePacket packet = MaplePacketCreator.serverNotice(10, "[Pvp] 玩家 " + player.getName() + " 终结了 " + attacked.getName() + " 的 " + pvpVictory + " 连斩。");
            if (pvpVictory >= 5 && pvpVictory < 10) {
                map.broadcastMessage(packet);
            }
            else if (pvpVictory >= 10 && pvpVictory < 20) {
                player.getClient().getChannelServer().broadcastMessage(packet);
            }
            else if (pvpVictory >= 20) {
                World.Broadcast.broadcastMessage(packet);
            }
        }
    }
    
    public static synchronized void doPvP(final MapleCharacter player, final MapleMap map, final AttackInfo attack, final MapleStatEffect effect) {
        final PvpAttackInfo pvpAttack = parsePvpAttack(attack, player, effect);
        int mobCount = 0;
        for (final MapleCharacter attacked : player.getMap().getCharactersIntersect(pvpAttack.box)) {
            if (attacked.getId() != player.getId() && attacked.isAlive() && !attacked.isHidden() && mobCount < pvpAttack.mobCount) {
                ++mobCount;
                monsterBomb(player, attacked, map, pvpAttack);
            }
        }
    }
    
    public static synchronized void doPartyPvP(final MapleCharacter player, final MapleMap map, final AttackInfo attack, final MapleStatEffect effect) {
        final PvpAttackInfo pvpAttack = parsePvpAttack(attack, player, effect);
        int mobCount = 0;
        for (final MapleCharacter attacked : player.getMap().getCharactersIntersect(pvpAttack.box)) {
            if (attacked.getId() != player.getId() && attacked.isAlive() && !attacked.isHidden() && (player.getParty() == null || player.getParty() != attacked.getParty()) && mobCount < pvpAttack.mobCount) {
                ++mobCount;
                monsterBomb(player, attacked, map, pvpAttack);
            }
        }
    }
    
    public static synchronized void doGuildPvP(final MapleCharacter player, final MapleMap map, final AttackInfo attack, final MapleStatEffect effect) {
        final PvpAttackInfo pvpAttack = parsePvpAttack(attack, player, effect);
        int mobCount = 0;
        for (final MapleCharacter attacked : player.getMap().getCharactersIntersect(pvpAttack.box)) {
            if (attacked.getId() != player.getId() && attacked.isAlive() && !attacked.isHidden() && (player.getGuildId() == 0 || player.getGuildId() != attacked.getGuildId()) && mobCount < pvpAttack.mobCount) {
                ++mobCount;
                monsterBomb(player, attacked, map, pvpAttack);
            }
        }
    }
}
