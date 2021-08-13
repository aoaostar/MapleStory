package handling.channel.handler;

import client.ISkill;
import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import client.SummonSkillEntry;
import client.anticheat.CheatingOffense;
import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import server.MapleStatEffect;
import server.life.MapleMonster;
import server.life.SummonAttackEntry;
import server.maps.AnimatedMapleMapObject;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleSummon;
import server.maps.SummonMovementType;
import server.movement.LifeMovementFragment;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MobPacket;

public class SummonHandler
{
    public static void MoveSummon(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final int oid = slea.readInt();
        final Point startPos = new Point(slea.readShort(), slea.readShort());
        final List<LifeMovementFragment> res = MovementParse.parseMovement(slea, 4);
        if (chr == null) {
            return;
        }
        for (final MapleSummon sum : chr.getSummons()) {
            if (sum.getObjectId() == oid && sum.getMovementType() != SummonMovementType.不会移动) {
                final Point pos = sum.getPosition();
                MovementParse.updatePosition(res, sum, 0);
                if (res.size() > 0) {
                    chr.getMap().broadcastMessage(chr, MaplePacketCreator.moveSummon(chr.getId(), oid, startPos, res), sum.getPosition());
                    break;
                }
                break;
            }
        }
    }
    
    public static void DamageSummon(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMap() == null) {
            return;
        }
        final int unkByte = slea.readByte();
        final int damage = slea.readInt();
        final int monsterIdFrom = slea.readInt();
        final Iterator<MapleSummon> iter = chr.getSummonsReadLock().iterator();
        boolean remove = false;
        try {
            while (iter.hasNext()) {
                final MapleSummon summon = iter.next();
                if (summon.is替身术() && summon.getOwnerId() == chr.getId() && damage > 0) {
                    summon.addHP((short)(-damage));
                    if (summon.getHP() <= 0) {
                        remove = true;
                    }
                    chr.getMap().broadcastMessage(chr, MaplePacketCreator.damageSummon(chr.getId(), summon.getSkill(), damage, unkByte, monsterIdFrom), summon.getTruePosition());
                    break;
                }
            }
        }
        finally {
            chr.unlockSummonsReadLock();
        }
        if (remove) {
            chr.cancelEffectFromBuffStat(MapleBuffStat.替身术);
        }
    }
    
    public static void SummonAttack(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMap() == null) {
            return;
        }
        final MapleMap map = chr.getMap();
        final MapleMapObject obj = map.getMapObject(slea.readInt(), MapleMapObjectType.SUMMON);
        if (obj == null) {
            return;
        }
        final MapleSummon summon = (MapleSummon)obj;
        if (summon.getOwnerId() != chr.getId() || summon.getSkillLevel() <= 0) {
            return;
        }
        final SummonSkillEntry sse = SkillFactory.getSummonData(summon.getSkill());
        if (sse == null) {
            return;
        }
        slea.skip(8);
        final int tick = slea.readInt();
        chr.updateTick(tick);
        summon.CheckSummonAttackFrequency(chr, tick);
        slea.skip(8);
        final byte animation = slea.readByte();
        slea.skip(8);
        final int numAttacked = slea.readByte();
        if (numAttacked > sse.mobCount) {
            chr.getCheatTracker().registerOffense(CheatingOffense.召唤兽攻击怪物数量异常);
            chr.dropMessage(5, "[警告] 请不要使用非法程序。召唤兽攻击怪物数量错误.");
            return;
        }
        final List<SummonAttackEntry> allDamage = new ArrayList<SummonAttackEntry>();
        chr.getCheatTracker().checkSummonAttack();
        for (int i = 0; i < numAttacked; ++i) {
            final MapleMonster mob = map.getMonsterByOid(slea.readInt());
            if (mob != null) {
                if (chr.getPosition().distanceSq(mob.getPosition()) > 400000.0) {
                    chr.getCheatTracker().registerOffense(CheatingOffense.召唤兽攻击范围过大);
                    chr.dropMessage(5, "[警告] 请不要使用非法程序。召唤兽攻击范围过大.");
                }
                slea.skip(14);
                final int damage = slea.readInt();
                allDamage.add(new SummonAttackEntry(mob, damage));
            }
        }
        if (!summon.isChangedMap()) {}
        final ISkill summonSkill = SkillFactory.getSkill(summon.getSkill());
        final MapleStatEffect summonEffect = summonSkill.getEffect(summon.getSkillLevel());
        if (summonEffect == null) {
            chr.dropMessage(5, "召唤兽攻击出现错误 => 攻击效果为空.");
            return;
        }
        for (final SummonAttackEntry attackEntry : allDamage) {
            final int toDamage = attackEntry.getDamage();
            final MapleMonster mob2 = attackEntry.getMonster();
            if (toDamage > 0 && summonEffect.getMonsterStati().size() > 0 && summonEffect.makeChanceResult()) {
                for (final Map.Entry<MonsterStatus, Integer> z : summonEffect.getMonsterStati().entrySet()) {
                    mob2.applyStatus(chr, new MonsterStatusEffect(z.getKey(), z.getValue(), summonSkill.getId(), null, false), summonEffect.isPoison(), 4000L, false);
                }
            }
            if (!chr.isGM() && toDamage >= 199999) {
                chr.getClient().getSession().write(MaplePacketCreator.serverNotice(1, "召唤兽攻击过高，你已被断开连接"));
                c.disconnect(true, true);
                return;
            }
            mob2.damage(chr, toDamage, true);
            chr.checkMonsterAggro(mob2);
            if (mob2.isAlive()) {
                continue;
            }
            chr.getClient().getSession().write(MobPacket.killMonster(mob2.getObjectId(), 1));
        }
        if (summon.isGaviota()) {
            chr.getMap().broadcastMessage(MaplePacketCreator.removeSummon(summon, true));
            chr.getMap().removeMapObject(summon);
            chr.removeVisibleMapObject(summon);
            chr.cancelEffectFromBuffStat(MapleBuffStat.召唤兽);
            chr.cancelEffectFromBuffStat(MapleBuffStat.REAPER);
        }
    }
}
