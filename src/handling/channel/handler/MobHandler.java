package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.MapleInventoryType;
import java.awt.Point;
import java.util.List;
import server.MapleInventoryManipulator;
import server.Randomizer;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.MobSkillFactory;
import server.maps.AnimatedMapleMapObject;
import server.maps.MapleMap;
import server.movement.LifeMovementFragment;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MobPacket;

public class MobHandler
{
    public static final void MoveMonster(SeekableLittleEndianAccessor slea, MapleClient c, MapleCharacter chr) {
        List<LifeMovementFragment> res;
        if (chr == null || chr.getMap() == null)
            return;
        int oid = slea.readInt();
        MapleMonster monster = chr.getMap().getMonsterByOid(oid);
        if (monster == null) {
            chr.addMoveMob(oid);
            return;
        }
        short moveid = slea.readShort();
        boolean useSkill = (slea.readByte() > 0);
        byte skill = slea.readByte();
        int skill1 = slea.readByte() & 0xFF;
        int skill2 = slea.readByte();
        int skill3 = slea.readByte();
        int skill4 = slea.readByte();
        int realskill = 0;
        int level = 0;
        if (useSkill) {
            byte size = monster.getNoSkills();
            boolean used = false;
            if (size > 0) {
                Pair<Integer, Integer> skillToUse = monster.getSkills().get((byte)Randomizer.nextInt(size));
                realskill = ((Integer)skillToUse.getLeft()).intValue();
                level = ((Integer)skillToUse.getRight()).intValue();
                MobSkill mobSkill = MobSkillFactory.getMobSkill(realskill, level);
                if (mobSkill != null && !mobSkill.checkCurrentBuff(chr, monster)) {
                    long now = System.currentTimeMillis();
                    long ls = monster.getLastSkillUsed(realskill);
                    if (ls == 0L || now - ls > mobSkill.getCoolTime()) {
                        monster.setLastSkillUsed(realskill, now, mobSkill.getCoolTime());
                        int reqHp = (int)((float)monster.getHp() / (float)monster.getMobMaxHp() * 100.0F);
                        if (reqHp <= mobSkill.getHP()) {
                            used = true;
                            mobSkill.applyEffect(chr, monster, true);
                        }
                    }
                }
            }
            if (!used) {
                realskill = 0;
                level = 0;
            }
        }
        slea.readByte();
        slea.readInt();
        slea.readLong();
        Point startPos = slea.readPos();
        try {
            res = MovementParse.parseMovement(slea, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.Movement_Log, e);
            FileoutputUtil.log(FileoutputUtil.Movement_Log, "怪物ID " + monster.getId() + ", AIOBE Type2:\r\n" + slea.toString(true));
            return;
        }
        c.getSession().write(MobPacket.moveMonsterResponse(monster.getObjectId(), moveid, monster.getMp(), monster.isControllerHasAggro(), realskill, level));
        if (res != null) {
            MapleMap map = chr.getMap();
            MovementParse.updatePosition(res, monster, -1);
            map.moveMonster(monster, monster.getPosition());
            map.broadcastMessage(chr, MobPacket.moveMonster(useSkill, skill, skill1, skill2, skill3, skill4, monster.getObjectId(), startPos, monster.getPosition(), res), monster.getPosition());
            if (!chr.isGM())
                chr.getCheatTracker().checkMoveMonster(monster.getPosition(), chr);
        }
    }

    public static void FriendlyDamage(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final MapleMap map = chr.getMap();
        if (map == null) {
            return;
        }
        final MapleMonster mobfrom = map.getMonsterByOid(slea.readInt());
        slea.skip(4);
        final MapleMonster mobto = map.getMonsterByOid(slea.readInt());
        if (mobfrom != null && mobto != null && mobto.getStats().isFriendly()) {
            final int damage = mobto.getStats().getLevel() * Randomizer.nextInt(mobto.getStats().getLevel()) / 2;
            mobto.damage(chr, damage, true);
            checkShammos(chr, mobto, map);
        }
    }
    
    public static void checkShammos(final MapleCharacter chr, final MapleMonster mobto, final MapleMap map) {
        if (!mobto.isAlive() && mobto.getId() == 9300275) {
            for (final MapleCharacter chrz : map.getCharactersThreadsafe()) {
                if (chrz.getParty() != null && chrz.getParty().getLeader().getId() == chrz.getId()) {
                    if (chrz.haveItem(2022698)) {
                        MapleInventoryManipulator.removeById(chrz.getClient(), MapleInventoryType.USE, 2022698, 1, false, true);
                        mobto.heal((int)mobto.getMobMaxHp(), mobto.getMobMaxMp(), true);
                        return;
                    }
                    break;
                }
            }
            map.broadcastMessage(MaplePacketCreator.serverNotice(6, "未能保护好这个怪物."));
            final MapleMap mapp = chr.getClient().getChannelServer().getMapFactory().getMap(921120001);
            for (final MapleCharacter chrz2 : map.getCharactersThreadsafe()) {
                chrz2.changeMap(mapp, mapp.getPortal(0));
            }
        }
        else if (mobto.getId() == 9300275 && mobto.getEventInstance() != null) {
            mobto.getEventInstance().setProperty("HP", String.valueOf(mobto.getHp()));
        }
    }
    
    public static void MonsterBomb(final int oid, final MapleCharacter chr) {
        final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
        if (monster == null || !chr.isAlive() || chr.isHidden()) {
            return;
        }
        final byte selfd = monster.getStats().getSelfD();
        if (selfd != -1) {
            chr.getMap().killMonster(monster, chr, false, false, selfd);
        }
    }
    
    public static void AutoAggro(final int monsteroid, final MapleCharacter chr) {
        if (chr == null || chr.getMap() == null || chr.isHidden()) {
            return;
        }
        final MapleMonster monster = chr.getMap().getMonsterByOid(monsteroid);
        if (monster != null && chr.getPosition().distanceSq(monster.getPosition()) < 200000.0) {
            if (monster.getController() != null) {
                if (chr.getMap().getCharacterById(monster.getController().getId()) == null) {
                    monster.switchController(chr, true);
                }
                else {
                    monster.switchController(monster.getController(), true);
                }
            }
            else {
                monster.switchController(chr, true);
            }
        }
    }
}
