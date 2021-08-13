package server.events;

import client.MapleCharacter;
import handling.MaplePacket;
import handling.channel.ChannelServer;
import handling.world.World;
import server.MapleInventoryManipulator;
import server.RandomRewards;
import server.Timer;
import server.maps.MapleMap;
import server.maps.SavedLocationType;
import tools.MaplePacketCreator;

public abstract class MapleEvent
{
    protected int[] mapid;
    protected int channel;
    protected boolean isRunning;
    
    public static void setEvent(final ChannelServer cserv, final boolean auto) {
        if (auto) {
            for (final MapleEventType t : MapleEventType.values()) {
                final MapleEvent e = cserv.getEvent(t);
                if (e.isRunning) {
                    for (final int i : e.mapid) {
                        if (cserv.getEvent() == i) {
                            e.broadcast(MaplePacketCreator.serverNotice(0, "距离活动开始只剩下一分钟!"));
                            e.broadcast(MaplePacketCreator.getClock(60));
                            Timer.EventTimer.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    e.startEvent();
                                }
                            }, 60000L);
                            break;
                        }
                    }
                }
            }
        }
        cserv.setEvent(-1);
    }
    
    public static void mapLoad(final MapleCharacter chr, final int channel) {
        if (chr == null) {
            return;
        }
        for (final MapleEventType t : MapleEventType.values()) {
            final MapleEvent e = ChannelServer.getInstance(channel).getEvent(t);
            if (e.isRunning) {
                if (chr.getMapId() == 109050000) {
                    e.finished(chr);
                }
                for (final int i : e.mapid) {
                    if (chr.getMapId() == i) {
                        e.onMapLoad(chr);
                    }
                }
            }
        }
    }
    
    public static void onStartEvent(final MapleCharacter chr) {
        for (final MapleEventType t : MapleEventType.values()) {
            final MapleEvent e = chr.getClient().getChannelServer().getEvent(t);
            if (e.isRunning) {
                for (final int i : e.mapid) {
                    if (chr.getMapId() == i) {
                        e.startEvent();
                        chr.dropMessage(5, String.valueOf(t) + " 活动开始");
                    }
                }
            }
        }
    }
    
    public static String scheduleEvent(final MapleEventType event, final ChannelServer cserv) {
        if (cserv.getEvent() != -1 || cserv.getEvent(event) == null) {
            return "改活动已经被禁止安排了.";
        }
        for (final int i : cserv.getEvent(event).mapid) {
            if (cserv.getMapFactory().getMap(i).getCharactersSize() > 0) {
                return "该活动已经在执行中.";
            }
        }
        cserv.setEvent(cserv.getEvent(event).mapid[0]);
        cserv.getEvent(event).reset();
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "活动 " + String.valueOf(event) + " 即将在频道 " + cserv.getChannel() + " 举行 , 要参加的玩家请到频道 " + cserv.getChannel() + ".请找到自由市场相框活动npc并进入！").getBytes());
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "活动 " + String.valueOf(event) + " 即将在频道 " + cserv.getChannel() + " 举行 , 要参加的玩家请到频道 " + cserv.getChannel() + ".请找到自由市场相框活动npc并进入！").getBytes());
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "活动 " + String.valueOf(event) + " 即将在频道 " + cserv.getChannel() + " 举行 , 要参加的玩家请到频道 " + cserv.getChannel() + ".请找到自由市场相框活动npc并进入！").getBytes());
        return "";
    }
    
    public MapleEvent(final int channel, final int[] mapid) {
        this.isRunning = false;
        this.channel = channel;
        this.mapid = mapid;
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public MapleMap getMap(final int i) {
        return this.getChannelServer().getMapFactory().getMap(this.mapid[i]);
    }
    
    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public void broadcast(final MaplePacket packet) {
        for (int i = 0; i < this.mapid.length; ++i) {
            this.getMap(i).broadcastMessage(packet);
        }
    }
    
    public void givePrize(final MapleCharacter chr) {
        final int reward = RandomRewards.getInstance().getEventReward();
        switch (reward) {
            case 0: {
                chr.gainMeso(66666, true, false, false);
                chr.dropMessage(5, "你获得 166666 冒险币");
                break;
            }
            case 1: {
                chr.gainMeso(399999, true, false, false);
                chr.dropMessage(5, "你获得 399999 冒险币");
                break;
            }
            case 2: {
                chr.modifyCSPoints(0, 200, false);
                chr.dropMessage(5, "你获得 200 点卷");
                break;
            }
            case 3: {
                chr.addFame(2);
                chr.dropMessage(5, "你获得 2 人气");
                break;
            }
        }
        if (MapleInventoryManipulator.checkSpace(chr.getClient(), 4032226, 1, "")) {
            MapleInventoryManipulator.addById(chr.getClient(), 4032226, (short)1, (byte)0);
            chr.dropMessage(5, "你获得 1 个黄金猪猪");
        }
        else {
            chr.gainMeso(100000, true, false, false);
            chr.dropMessage(5, "由于你背包满了。所以只能给予你冒险币！");
        }
    }
    
    public void finished(final MapleCharacter chr) {
    }
    
    public void onMapLoad(final MapleCharacter chr) {
    }
    
    public void startEvent() {
    }
    
    public void warpBack(final MapleCharacter chr) {
        int map = chr.getSavedLocation(SavedLocationType.EVENT);
        if (map <= -1) {
            map = 104000000;
        }
        final MapleMap mapp = chr.getClient().getChannelServer().getMapFactory().getMap(map);
        chr.changeMap(mapp, mapp.getPortal(0));
    }
    
    public void reset() {
        this.isRunning = true;
    }
    
    public void unreset() {
        this.isRunning = false;
    }
}
