package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleDisease;
import java.util.List;
import server.MapleCarnivalFactory;
import server.Randomizer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MonsterCarnivalPacket;

public class MonsterCarnivalHandler
{
    public static void MonsterCarnival(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().getCarnivalParty() == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final int tab = slea.readByte();
        final int num = slea.readInt();
        switch (tab) {
            case 0: {
                final List<Pair<Integer, Integer>> mobs = c.getPlayer().getMap().getMobsToSpawn();
                if (num >= mobs.size() || c.getPlayer().getAvailableCP() < mobs.get(num).right) {
                    c.getPlayer().dropMessage(5, "你没有足够的CP.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final MapleMonster mons = MapleLifeFactory.getMonster(mobs.get(num).left);
                if (c.getPlayer().isGM()) {
                    System.out.println("tab：" + tab);
                    System.out.println("num：" + num);
                    System.out.println("mons：" + mons);
                    System.out.println("num：" + num);
                    System.out.println("判断A：" + mons != null);
                    System.out.println("判断B：" + c.getPlayer().getMap().makeCarnivalSpawn(c.getPlayer().getCarnivalParty().getTeam(), mons, num));
                }
                if (mons != null && c.getPlayer().getMap().makeCarnivalSpawn(c.getPlayer().getCarnivalParty().getTeam(), mons, num)) {
                    c.getPlayer().getCarnivalParty().useCP(c.getPlayer(), mobs.get(num).right);
                    c.getPlayer().CPUpdate(false, c.getPlayer().getAvailableCP(), c.getPlayer().getTotalCP(), 0);
                    for (final MapleCharacter chr : c.getPlayer().getMap().getCharactersThreadsafe()) {
                        chr.CPUpdate(true, c.getPlayer().getCarnivalParty().getAvailableCP(), c.getPlayer().getCarnivalParty().getTotalCP(), c.getPlayer().getCarnivalParty().getTeam());
                    }
                    c.getPlayer().getMap().broadcastMessage(MonsterCarnivalPacket.playerSummoned(c.getPlayer().getName(), tab, num));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    break;
                }
                c.getPlayer().dropMessage(5, "你不能再召唤怪物了.");
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 1: {
                final List<Integer> skillid = c.getPlayer().getMap().getSkillIds();
                if (num >= skillid.size()) {
                    c.getPlayer().dropMessage(5, "发生了一个错误.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final MapleCarnivalFactory.MCSkill skil = MapleCarnivalFactory.getInstance().getSkill(skillid.get(num));
                if (skil == null || c.getPlayer().getAvailableCP() < skil.cpLoss) {
                    c.getPlayer().dropMessage(5, "你没有足够的CP.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final MapleDisease dis = skil.getDisease();
                boolean found = false;
                for (final MapleCharacter chr2 : c.getPlayer().getMap().getCharactersThreadsafe()) {
                    if ((chr2.getParty() == null || (c.getPlayer().getParty() != null && chr2.getParty().getId() != c.getPlayer().getParty().getId())) && (skil.targetsAll || Randomizer.nextBoolean())) {
                        found = true;
                        if (dis == null) {
                            chr2.dispel();
                        }
                        else if (skil.getSkill() == null) {
                            chr2.giveDebuff(dis, 1, 30000L, MapleDisease.getByDisease(dis), 1);
                        }
                        else {
                            chr2.giveDebuff(dis, skil.getSkill());
                        }
                        if (!skil.targetsAll) {
                            break;
                        }
                        continue;
                    }
                }
                if (found) {
                    c.getPlayer().getCarnivalParty().useCP(c.getPlayer(), skil.cpLoss);
                    c.getPlayer().CPUpdate(false, c.getPlayer().getAvailableCP(), c.getPlayer().getTotalCP(), 0);
                    for (final MapleCharacter chr2 : c.getPlayer().getMap().getCharactersThreadsafe()) {
                        chr2.CPUpdate(true, c.getPlayer().getCarnivalParty().getAvailableCP(), c.getPlayer().getCarnivalParty().getTotalCP(), c.getPlayer().getCarnivalParty().getTeam());
                        chr2.dropMessage(5, "[" + ((c.getPlayer().getCarnivalParty().getTeam() == 0) ? "红队" : "蓝队") + "] " + c.getPlayer().getName() + " has used a skill. [" + dis.name() + "].");
                    }
                    c.getPlayer().getMap().broadcastMessage(MonsterCarnivalPacket.playerSummoned(c.getPlayer().getName(), tab, num));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    break;
                }
                c.getPlayer().dropMessage(5, "发生错误B.");
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2: {
                final MapleCarnivalFactory.MCSkill skil2 = MapleCarnivalFactory.getInstance().getGuardian(num);
                if (skil2 == null || c.getPlayer().getAvailableCP() < skil2.cpLoss) {
                    c.getPlayer().dropMessage(5, "你没有足够的CP.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                if (c.getPlayer().getMap().makeCarnivalReactor(c.getPlayer().getCarnivalParty().getTeam(), num)) {
                    c.getPlayer().getCarnivalParty().useCP(c.getPlayer(), skil2.cpLoss);
                    c.getPlayer().CPUpdate(false, c.getPlayer().getAvailableCP(), c.getPlayer().getTotalCP(), 0);
                    for (final MapleCharacter chr3 : c.getPlayer().getMap().getCharactersThreadsafe()) {
                        chr3.CPUpdate(true, c.getPlayer().getCarnivalParty().getAvailableCP(), c.getPlayer().getCarnivalParty().getTotalCP(), c.getPlayer().getCarnivalParty().getTeam());
                    }
                    c.getPlayer().getMap().broadcastMessage(MonsterCarnivalPacket.playerSummoned(c.getPlayer().getName(), tab, num));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    break;
                }
                c.getPlayer().dropMessage(5, "你不能再召唤了.");
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
        }
    }
}
