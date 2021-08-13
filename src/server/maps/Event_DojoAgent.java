package server.maps;

import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.world.MaplePartyCharacter;
import java.awt.Point;
import server.Randomizer;
import server.Timer;
import server.life.MapleLifeFactory;
import tools.MaplePacketCreator;

public class Event_DojoAgent
{
    private static final int baseAgentMapId = 970030000;
    private static final Point point1;
    private static final Point point2;
    private static final Point point3;
    
    public static boolean warpStartAgent(final MapleCharacter c, final boolean party) {
        final int stage = 1;
        final int mapid = 970030000 + stage * 100;
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int i = mapid; i < mapid + 15; ++i) {
            final MapleMap map = ch.getMapFactory().getMap(i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                c.changeMap(map, map.getPortal(0));
                map.respawn(true);
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpNextMap_Agent(final MapleCharacter c, final boolean fromResting) {
        final int currentmap = c.getMapId();
        final int thisStage = (currentmap - 970030000) / 100;
        MapleMap map = c.getMap();
        final int bossid = 9500336 + thisStage;
        if (map.countMonsterById(bossid) > 0) {
            return false;
        }
        if (!fromResting) {
            clearMap(map, true);
        }
        final ChannelServer ch = c.getClient().getChannelServer();
        if (currentmap >= 970032700 && currentmap <= 970032800) {
            map = ch.getMapFactory().getMap(970030000);
            c.changeMap(map, map.getPortal(0));
            return true;
        }
        int i;
        for (int nextmapid = i = 970030000 + (thisStage + 1) * 100; i < nextmapid + 7; ++i) {
            map = ch.getMapFactory().getMap(i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                c.changeMap(map, map.getPortal(0));
                map.respawn(true);
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpStartDojo(final MapleCharacter c, final boolean party) {
        int stage = 1;
        if (party || stage <= -1 || stage > 38) {
            stage = 1;
        }
        int mapid = 925020000 + stage * 100;
        boolean canenter = false;
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int x = 0; x < 15; ++x) {
            boolean canenterr = true;
            for (int i = 1; i < 39; ++i) {
                final MapleMap map = ch.getMapFactory().getMap(925020000 + 100 * i + x);
                if (map.getCharactersSize() > 0) {
                    canenterr = false;
                    break;
                }
                clearMap(map, false);
            }
            if (canenterr) {
                canenter = true;
                mapid += x;
                break;
            }
        }
        final MapleMap map2 = ch.getMapFactory().getMap(mapid);
        final MapleMap mapidd = c.getMap();
        if (canenter) {
            if (party && c.getParty() != null) {
                for (final MaplePartyCharacter mem : c.getParty().getMembers()) {
                    final MapleCharacter chr = mapidd.getCharacterById(mem.getId());
                    if (chr != null) {
                        chr.changeMap(map2, map2.getPortal(0));
                    }
                }
            }
            else {
                c.changeMap(map2, map2.getPortal(0));
            }
            spawnMonster(map2, stage);
        }
        return canenter;
    }
    
    public static boolean warpNextMap(final MapleCharacter c, final boolean fromResting) {
        try {
            final MapleMap currentmap = c.getMap();
            final int temp = (currentmap.getId() - 925000000) / 100;
            final int thisStage = temp - temp / 100 * 100;
            final int points = getDojoPoints(thisStage);
            final ChannelServer ch = c.getClient().getChannelServer();
            if (!fromResting) {
                clearMap(currentmap, true);
                if (c.getParty() != null && c.getParty().getMembers().size() > 1) {
                    for (final MaplePartyCharacter mem : c.getParty().getMembers()) {
                        final MapleCharacter chr = currentmap.getCharacterById(mem.getId());
                        if (chr != null) {
                            final int point = points * 3;
                            chr.setDojo(chr.getDojo() + point);
                            chr.getClient().getSession().write(MaplePacketCreator.Mulung_Pts(point, chr.getDojo()));
                        }
                    }
                }
                else {
                    final int point2 = (points + 1) * 3;
                    c.setDojo(c.getDojo() + point2);
                    c.getClient().getSession().write(MaplePacketCreator.Mulung_Pts(point2, c.getDojo()));
                }
            }
            if (currentmap.getId() >= 925023800 && currentmap.getId() <= 925023814) {
                final MapleMap map = ch.getMapFactory().getMap(925020003);
                if (c.getParty() != null) {
                    for (final MaplePartyCharacter mem2 : c.getParty().getMembers()) {
                        final MapleCharacter chr2 = currentmap.getCharacterById(mem2.getId());
                        if (chr2 != null) {
                            chr2.changeMap(map, map.getPortal(1));
                        }
                    }
                }
                else {
                    c.changeMap(map, map.getPortal(1));
                }
                return true;
            }
            final MapleMap map = ch.getMapFactory().getMap(currentmap.getId() + 100);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                if (c.getParty() != null) {
                    for (final MaplePartyCharacter mem2 : c.getParty().getMembers()) {
                        final MapleCharacter chr2 = currentmap.getCharacterById(mem2.getId());
                        if (chr2 != null) {
                            chr2.changeMap(map, map.getPortal(0));
                        }
                    }
                }
                else {
                    c.changeMap(map, map.getPortal(0));
                }
                spawnMonster(map, thisStage + 1);
                return true;
            }
            final int basemap = currentmap.getId() / 100 * 100 + 100;
            for (int x = 0; x < 15; ++x) {
                final MapleMap mapz = ch.getMapFactory().getMap(basemap + x);
                if (mapz.getCharactersSize() == 0) {
                    clearMap(mapz, false);
                    if (c.getParty() != null) {
                        for (final MaplePartyCharacter mem3 : c.getParty().getMembers()) {
                            final MapleCharacter chr3 = currentmap.getCharacterById(mem3.getId());
                            if (chr3 != null) {
                                chr3.changeMap(mapz, mapz.getPortal(0));
                            }
                        }
                    }
                    else {
                        c.changeMap(mapz, mapz.getPortal(0));
                    }
                    spawnMonster(mapz, thisStage + 1);
                    return true;
                }
            }
        }
        catch (Exception rm) {
            rm.printStackTrace();
        }
        return false;
    }
    
    private static void clearMap(final MapleMap map, final boolean check) {
        if (check && map.getCharactersSize() != 0) {
            return;
        }
        map.resetFully();
    }
    
    private static int getDojoPoints(final int stage) {
        switch (stage) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                return 1;
            }
            case 7:
            case 8:
            case 9:
            case 10:
            case 11: {
                return 2;
            }
            case 13:
            case 14:
            case 15:
            case 16:
            case 17: {
                return 3;
            }
            case 19:
            case 20:
            case 21:
            case 22:
            case 23: {
                return 4;
            }
            case 25:
            case 26:
            case 27:
            case 28:
            case 29: {
                return 5;
            }
            case 31:
            case 32:
            case 33:
            case 34:
            case 35: {
                return 6;
            }
            case 37:
            case 38: {
                return 7;
            }
            default: {
                return 0;
            }
        }
    }

    private static void spawnMonster(final MapleMap map, int stage) {
        final int mobid;
        switch (stage) {
            case 1:
                mobid = 9300184;
                break;
            case 2:
                mobid = 9300185;
                break;
            case 3:
                mobid = 9300186;
                break;
            case 4:
                mobid = 9300187;
                break;
            case 5:
                mobid = 9300188;
                break;
            case 7:
                mobid = 9300189;
                break;
            case 8:
                mobid = 9300190;
                break;
            case 9:
                mobid = 9300191;
                break;
            case 10:
                mobid = 9300192;
                break;
            case 11:
                mobid = 9300193;
                break;
            case 13:
                mobid = 9300194;
                break;
            case 14:
                mobid = 9300195;
                break;
            case 15:
                mobid = 9300196;
                break;
            case 16:
                mobid = 9300197;
                break;
            case 17:
                mobid = 9300198;
                break;
            case 19:
                mobid = 9300199;
                break;
            case 20:
                mobid = 9300200;
                break;
            case 21:
                mobid = 9300201;
                break;
            case 22:
                mobid = 9300202;
                break;
            case 23:
                mobid = 9300203;
                break;
            case 25:
                mobid = 9300204;
                break;
            case 26:
                mobid = 9300205;
                break;
            case 27:
                mobid = 9300206;
                break;
            case 28:
                mobid = 9300207;
                break;
            case 29:
                mobid = 9300208;
                break;
            case 31:
                mobid = 9300209;
                break;
            case 32:
                mobid = 9300210;
                break;
            case 33:
                mobid = 9300211;
                break;
            case 34:
                mobid = 9300212;
                break;
            case 35:
                mobid = 9300213;
                break;
            case 37:
                mobid = 9300214;
                break;
            case 38:
                mobid = 9300215;
                break;
            default:
                return;
        }
        if (mobid != 0) {
            final int rand = Randomizer.nextInt(3);
            Timer.MapTimer.getInstance().schedule(new Runnable() {
                public void run() {
                    map.spawnMonsterWithEffect(MapleLifeFactory.getMonster(mobid), 15, (rand == 0) ? Event_DojoAgent.point1 : ((rand == 1) ? Event_DojoAgent.point2 : Event_DojoAgent.point3));
                }
            },3000L);
        }
    }
    static {
        point1 = new Point(140, 0);
        point2 = new Point(-193, 0);
        point3 = new Point(355, 0);
    }
}
