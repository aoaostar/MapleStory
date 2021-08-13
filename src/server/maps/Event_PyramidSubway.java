package server.maps;

import client.MapleCharacter;
import client.MapleQuestStatus;
import handling.channel.ChannelServer;
import handling.world.MaplePartyCharacter;
import java.awt.Point;
import java.util.concurrent.ScheduledFuture;
import server.Randomizer;
import server.Timer;
import server.life.MapleLifeFactory;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;

public class Event_PyramidSubway
{
    private int kill;
    private int cool;
    private int miss;
    private int skill;
    private final int type;
    private int energybar;
    private boolean broaded;
    private ScheduledFuture<?> energyBarDecrease;
    private ScheduledFuture<?> timerSchedule;
    private ScheduledFuture<?> yetiSchedule;
    
    public static boolean warpStartSubway(final MapleCharacter c) {
        final int mapid = 910320100;
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int i = 0; i < 5; ++i) {
            final MapleMap map = ch.getMapFactory().getMap(mapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                changeMap(c, map, 25, 30);
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpBonusSubway(final MapleCharacter c) {
        final int mapid = 910320010;
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int i = 0; i < 20; ++i) {
            final MapleMap map = ch.getMapFactory().getMap(mapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                c.changeMap(map, map.getPortal(0));
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpNextMap_Subway(final MapleCharacter c) {
        final int currentmap = c.getMapId();
        final int thisStage = (currentmap - 910320100) / 100;
        MapleMap map = c.getMap();
        clearMap(map, true);
        final ChannelServer ch = c.getClient().getChannelServer();
        if (thisStage >= 2) {
            map = ch.getMapFactory().getMap(910330001);
            changeMap(c, map, 1, 200, 1);
            return true;
        }
        final int nextmapid = 910320100 + (thisStage + 1) * 100;
        for (int i = 0; i < 5; ++i) {
            map = ch.getMapFactory().getMap(nextmapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                changeMap(c, map, 1, 200, 1);
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpStartPyramid(final MapleCharacter c, final int difficulty) {
        final int mapid = 926010100 + difficulty * 1000;
        int minLevel = 40;
        int maxLevel = 60;
        switch (difficulty) {
            case 1: {
                minLevel = 45;
                break;
            }
            case 2: {
                minLevel = 50;
                break;
            }
            case 3: {
                minLevel = 61;
                maxLevel = 200;
                break;
            }
        }
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int i = 0; i < 5; ++i) {
            final MapleMap map = ch.getMapFactory().getMap(mapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                changeMap(c, map, minLevel, maxLevel);
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpBonusPyramid(final MapleCharacter c, final int difficulty) {
        final int mapid = 926010010 + difficulty * 20;
        final ChannelServer ch = c.getClient().getChannelServer();
        for (int i = 0; i < 20; ++i) {
            final MapleMap map = ch.getMapFactory().getMap(mapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                c.changeMap(map, map.getPortal(0));
                return true;
            }
        }
        return false;
    }
    
    public static boolean warpNextMap_Pyramid(final MapleCharacter c, final int difficulty) {
        final int currentmap = c.getMapId();
        final int thisStage = (currentmap - (926010100 + difficulty * 1000)) / 100;
        MapleMap map = c.getMap();
        clearMap(map, true);
        final ChannelServer ch = c.getClient().getChannelServer();
        if (thisStage >= 4) {
            map = ch.getMapFactory().getMap(926020001 + difficulty);
            changeMap(c, map, 1, 200, 1);
            return true;
        }
        final int nextmapid = 926010100 + (thisStage + 1) * 100 + difficulty * 1000;
        for (int i = 0; i < 5; ++i) {
            map = ch.getMapFactory().getMap(nextmapid + i);
            if (map.getCharactersSize() == 0) {
                clearMap(map, false);
                changeMap(c, map, 1, 200, 1);
                return true;
            }
        }
        return false;
    }
    
    public static void changeMap(final MapleCharacter c, final MapleMap map, final int minLevel, final int maxLevel) {
        changeMap(c, map, minLevel, maxLevel, 0);
    }
    
    public static void changeMap(final MapleCharacter c, final MapleMap map, final int minLevel, final int maxLevel, final int clear) {
        final MapleMap oldMap = c.getMap();
        if (c.getParty() != null && c.getParty().getMembers().size() > 1) {
            for (final MaplePartyCharacter mpc : c.getParty().getMembers()) {
                final MapleCharacter chr = oldMap.getCharacterById(mpc.getId());
                if (chr != null && chr.getId() != c.getId() && chr.getLevel() >= minLevel && chr.getLevel() <= maxLevel) {
                    if (clear == 1) {
                        chr.getClient().getSession().write(MaplePacketCreator.showEffect("pvp/victory"));
                    }
                    else if (clear == 2) {
                        chr.getClient().getSession().write(MaplePacketCreator.showEffect("pvp/lose"));
                    }
                    chr.changeMap(map, map.getPortal(0));
                }
            }
        }
        if (clear == 1) {
            c.getClient().getSession().write(MaplePacketCreator.showEffect("pvp/victory"));
        }
        else if (clear == 2) {
            c.getClient().getSession().write(MaplePacketCreator.showEffect("pvp/lose"));
        }
        c.changeMap(map, map.getPortal(0));
    }
    
    private static void clearMap(final MapleMap map, final boolean check) {
        if (check && map.getCharactersSize() > 0) {
            return;
        }
        map.resetFully(false);
    }
    
    public Event_PyramidSubway(final MapleCharacter c) {
        this.kill = 0;
        this.cool = 0;
        this.miss = 0;
        this.skill = 0;
        this.energybar = 100;
        this.broaded = false;
        final int mapid = c.getMapId();
        if (mapid / 10000 == 91032) {
            this.type = -1;
        }
        else {
            this.type = mapid % 10000 / 1000;
        }
        if (c.getParty() == null || c.getParty().getLeader().equals(new MaplePartyCharacter(c))) {
            this.commenceTimerNextMap(c, 1);
            this.energyBarDecrease = Timer.MapTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    Event_PyramidSubway.this.energybar -= ((c.getParty() != null && c.getParty().getMembers().size() > 1) ? 10 : 5);
                    if (Event_PyramidSubway.this.broaded) {
                        c.getMap().respawn(true);
                    }
                    else {
                        Event_PyramidSubway.this.broaded = true;
                    }
                    if (Event_PyramidSubway.this.energybar <= 0) {
                        Event_PyramidSubway.this.fail(c);
                    }
                }
            }, 1000L);
        }
    }
    
    public void fullUpdate(final MapleCharacter c, final int stage) {
        this.broadcastEnergy(c, "massacre_party", (c.getParty() == null) ? 0 : c.getParty().getMembers().size());
        this.broadcastEnergy(c, "massacre_miss", this.miss);
        this.broadcastEnergy(c, "massacre_cool", this.cool);
        this.broadcastEnergy(c, "massacre_skill", this.skill);
        this.broadcastEnergy(c, "massacre_laststage", stage - 1);
        this.broadcastEnergy(c, "massacre_hit", this.kill);
        this.broadcastUpdate(c);
    }
    
    public void commenceTimerNextMap(final MapleCharacter c, final int stage) {
        if (this.timerSchedule != null) {
            this.timerSchedule.cancel(false);
            this.timerSchedule = null;
        }
        if (this.yetiSchedule != null) {
            this.yetiSchedule.cancel(false);
            this.yetiSchedule = null;
        }
        final MapleMap ourMap = c.getMap();
        final int time = ((this.type == -1) ? 180 : ((stage == 1) ? 240 : 300)) - 1;
        if (c.getParty() != null && c.getParty().getMembers().size() > 1) {
            for (final MaplePartyCharacter mpc : c.getParty().getMembers()) {
                final MapleCharacter chr = ourMap.getCharacterById(mpc.getId());
                if (chr != null) {
                    chr.getClient().getSession().write(MaplePacketCreator.getClock(time));
                    chr.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/number/" + stage));
                    chr.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/stage"));
                    chr.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/start"));
                    this.fullUpdate(chr, stage);
                }
            }
        }
        else {
            c.getClient().getSession().write(MaplePacketCreator.getClock(time));
            c.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/number/" + stage));
            c.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/stage"));
            c.getClient().getSession().write(MaplePacketCreator.showEffect("killing/first/start"));
            this.fullUpdate(c, stage);
        }
        if (this.type != -1 && (stage == 4 || stage == 5)) {
            final Point pos = c.getPosition();
            final MapleMap map = c.getMap();
            this.yetiSchedule = Timer.MapTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (map.countMonsterById(9300021) <= ((stage == 4) ? 1 : 2)) {
                        map.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(9300021), new Point(pos));
                    }
                }
            }, 10000L);
        }
        this.timerSchedule = Timer.MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                boolean ret = false;
                if (Event_PyramidSubway.this.type == -1) {
                    ret = Event_PyramidSubway.warpNextMap_Subway(c);
                }
                else {
                    ret = Event_PyramidSubway.warpNextMap_Pyramid(c, Event_PyramidSubway.this.type);
                }
                if (!ret) {
                    Event_PyramidSubway.this.fail(c);
                }
            }
        }, time * 1000L);
    }
    
    public void onKill(final MapleCharacter c) {
        ++this.kill;
        if (Randomizer.nextInt(100) < 5) {
            this.broadcastEnergy(c, "massacre_cool", ++this.cool);
        }
        this.energybar += 5;
        if (this.energybar > 100) {
            this.energybar = 100;
        }
        if (this.type != -1) {
            for (int i = 5; i >= 1; --i) {
                if ((this.kill + this.cool) % (i * 100) == 0 && Randomizer.nextInt(100) < 50) {
                    this.broadcastEffect(c, "killing/yeti" + (i - 1));
                    break;
                }
            }
            if ((this.kill + this.cool) % 500 == 0) {
                this.broadcastEnergy(c, "massacre_skill", ++this.skill);
            }
        }
        this.broadcastUpdate(c);
        this.broadcastEnergy(c, "massacre_hit", this.kill);
    }
    
    public void onMiss(final MapleCharacter c) {
        ++this.miss;
        this.energybar -= 5;
        this.broadcastUpdate(c);
        this.broadcastEnergy(c, "massacre_miss", this.miss);
    }
    
    public boolean onSkillUse(final MapleCharacter c) {
        if (this.skill > 0 && this.type != -1) {
            this.broadcastEnergy(c, "massacre_skill", --this.skill);
            return true;
        }
        return false;
    }
    
    public void onChangeMap(final MapleCharacter c, final int newmapid) {
        if ((newmapid == 910330001 && this.type == -1) || (newmapid == 926020001 + this.type && this.type != -1)) {
            this.succeed(c);
        }
        else if (this.type == -1 && (newmapid < 910320100 || newmapid > 910320304)) {
            this.dispose(c);
        }
        else if (this.type != -1 && (newmapid < 926010100 || newmapid > 926013504)) {
            this.dispose(c);
        }
        else if (c.getParty() == null || c.getParty().getLeader().equals(new MaplePartyCharacter(c))) {
            this.commenceTimerNextMap(c, newmapid % 1000 / (this.energybar = 100));
        }
    }
    
    public void succeed(final MapleCharacter c) {
        final MapleQuestStatus record = c.getQuestNAdd(MapleQuest.getInstance((this.type == -1) ? 7662 : 7760));
        String data = record.getCustomData();
        if (data == null) {
            record.setCustomData("0");
            data = record.getCustomData();
        }
        final int mons = Integer.parseInt(data);
        final int tk = this.kill + this.cool;
        record.setCustomData(String.valueOf(mons + tk));
        byte rank = 4;
        if (this.type == -1) {
            if (tk >= 2000) {
                rank = 0;
            }
            else if (tk >= 1500 && tk <= 1999) {
                rank = 1;
            }
            else if (tk >= 1000 && tk <= 1499) {
                rank = 2;
            }
            else if (tk >= 500 && tk <= 999) {
                rank = 3;
            }
        }
        else if (tk >= 3000) {
            rank = 0;
        }
        else if (tk >= 2000 && tk <= 2999) {
            rank = 1;
        }
        else if (tk >= 1500 && tk <= 1999) {
            rank = 2;
        }
        else if (tk >= 500 && tk <= 1499) {
            rank = 3;
        }
        int pt = 0;
        Label_0581: {
            switch (this.type) {
                case 0: {
                    switch (rank) {
                        case 0: {
                            pt = 60500;
                            break;
                        }
                        case 1: {
                            pt = 55000;
                            break;
                        }
                        case 2: {
                            pt = 46750;
                            break;
                        }
                        case 3: {
                            pt = 22000;
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (rank) {
                        case 0: {
                            pt = 66000;
                            break;
                        }
                        case 1: {
                            pt = 60000;
                            break;
                        }
                        case 2: {
                            pt = 51750;
                            break;
                        }
                        case 3: {
                            pt = 24000;
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (rank) {
                        case 0: {
                            pt = 71500;
                            break;
                        }
                        case 1: {
                            pt = 65000;
                            break;
                        }
                        case 2: {
                            pt = 55250;
                            break;
                        }
                        case 3: {
                            pt = 26000;
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (rank) {
                        case 0: {
                            pt = 77000;
                            break;
                        }
                        case 1: {
                            pt = 70000;
                            break;
                        }
                        case 2: {
                            pt = 59500;
                            break;
                        }
                        case 3: {
                            pt = 28000;
                            break;
                        }
                    }
                    break;
                }
                default: {
                    switch (rank) {
                        case 0: {
                            pt = 22000;
                            break Label_0581;
                        }
                        case 1: {
                            pt = 17000;
                            break Label_0581;
                        }
                        case 2: {
                            pt = 10750;
                            break Label_0581;
                        }
                        case 3: {
                            pt = 7000;
                            break Label_0581;
                        }
                    }
                    break;
                }
            }
        }
        int exp = 0;
        if (rank < 4) {
            exp = (this.kill * 2 + this.cool * 10 + pt) * c.getClient().getChannelServer().getExpRate();
            c.gainExp(exp, true, false, false);
        }
        c.getClient().getSession().write(MaplePacketCreator.showEffect("pvp/victory"));
        c.getClient().getSession().write(MaplePacketCreator.sendPyramidResult(rank, exp));
        this.dispose(c);
    }
    
    public void fail(final MapleCharacter c) {
        MapleMap map;
        if (this.type == -1) {
            map = c.getClient().getChannelServer().getMapFactory().getMap(910320001);
        }
        else {
            map = c.getClient().getChannelServer().getMapFactory().getMap(926010001 + this.type);
        }
        changeMap(c, map, 1, 200, 2);
        this.dispose(c);
    }
    
    public void dispose(final MapleCharacter c) {
        final boolean lead = this.energyBarDecrease != null && this.timerSchedule != null;
        if (this.energyBarDecrease != null) {
            this.energyBarDecrease.cancel(false);
            this.energyBarDecrease = null;
        }
        if (this.timerSchedule != null) {
            this.timerSchedule.cancel(false);
            this.timerSchedule = null;
        }
        if (this.yetiSchedule != null) {
            this.yetiSchedule.cancel(false);
            this.yetiSchedule = null;
        }
        if (c.getParty() != null && lead && c.getParty().getMembers().size() > 1) {
            this.fail(c);
            return;
        }
        c.setPyramidSubway(null);
    }
    
    public void broadcastUpdate(final MapleCharacter c) {
        final MapleMap map = c.getMap();
        if (c.getParty() != null && c.getParty().getMembers().size() > 1) {
            for (final MaplePartyCharacter mpc : c.getParty().getMembers()) {
                final MapleCharacter chr = map.getCharacterById(mpc.getId());
                if (chr != null) {
                    chr.getClient().getSession().write(MaplePacketCreator.sendPyramidUpdate(this.energybar));
                }
            }
        }
        else {
            c.getClient().getSession().write(MaplePacketCreator.sendPyramidUpdate(this.energybar));
        }
    }
    
    public void broadcastEffect(final MapleCharacter c, final String effect) {
        c.getClient().getSession().write(MaplePacketCreator.showEffect(effect));
    }
    
    public void broadcastEnergy(final MapleCharacter c, final String type, final int amount) {
        c.getClient().getSession().write(MaplePacketCreator.sendPyramidEnergy(type, String.valueOf(amount)));
    }
}
