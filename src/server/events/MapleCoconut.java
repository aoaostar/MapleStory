package server.events;

import client.MapleCharacter;
import java.util.LinkedList;
import java.util.List;
import server.Timer;
import tools.MaplePacketCreator;

public class MapleCoconut extends MapleEvent
{
    private final List<MapleCoconuts> coconuts;
    private final int[] coconutscore;
    private int countBombing;
    private int countFalling;
    private int countStopped;
    
    public MapleCoconut(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.coconuts = new LinkedList<MapleCoconuts>();
        this.coconutscore = new int[2];
        this.countBombing = 0;
        this.countFalling = 0;
        this.countStopped = 0;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.resetCoconutScore();
    }
    
    @Override
    public void unreset() {
        super.unreset();
        this.resetCoconutScore();
        this.setHittable(false);
    }
    
    @Override
    public void onMapLoad(final MapleCharacter chr) {
        chr.getClient().getSession().write(MaplePacketCreator.coconutScore(this.getCoconutScore()));
    }
    
    public MapleCoconuts getCoconut(final int id) {
        return this.coconuts.get(id);
    }
    
    public List<MapleCoconuts> getAllCoconuts() {
        return this.coconuts;
    }
    
    public void setHittable(final boolean hittable) {
        for (final MapleCoconuts nut : this.coconuts) {
            nut.setHittable(hittable);
        }
    }
    
    public int getBombings() {
        return this.countBombing;
    }
    
    public void bombCoconut() {
        --this.countBombing;
    }
    
    public int getFalling() {
        return this.countFalling;
    }
    
    public void fallCoconut() {
        --this.countFalling;
    }
    
    public int getStopped() {
        return this.countStopped;
    }
    
    public void stopCoconut() {
        --this.countStopped;
    }
    
    public int[] getCoconutScore() {
        return this.coconutscore;
    }
    
    public int getMapleScore() {
        return this.coconutscore[0];
    }
    
    public int getStoryScore() {
        return this.coconutscore[1];
    }
    
    public void addMapleScore() {
        final int[] coconutscore = this.coconutscore;
        final int n = 0;
        ++coconutscore[n];
    }
    
    public void addStoryScore() {
        final int[] coconutscore = this.coconutscore;
        final int n = 1;
        ++coconutscore[n];
    }
    
    public void resetCoconutScore() {
        this.coconutscore[0] = 0;
        this.coconutscore[1] = 0;
        this.countBombing = 80;
        this.countFalling = 1001;
        this.countStopped = 20;
        this.coconuts.clear();
        for (int i = 0; i < 506; ++i) {
            this.coconuts.add(new MapleCoconuts());
        }
    }
    
    @Override
    public void startEvent() {
        this.reset();
        this.setHittable(true);
        this.getMap(0).broadcastMessage(MaplePacketCreator.serverNotice(5, "活动开始!!"));
        this.getMap(0).broadcastMessage(MaplePacketCreator.hitCoconut(true, 0, 0));
        this.getMap(0).broadcastMessage(MaplePacketCreator.getClock(360));
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (MapleCoconut.this.getMapleScore() == MapleCoconut.this.getStoryScore()) {
                    MapleCoconut.this.bonusTime();
                }
                else if (MapleCoconut.this.getMapleScore() > MapleCoconut.this.getStoryScore()) {
                    for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                        if (chr.getCoconutTeam() == 0) {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/victory"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Victory"));
                        }
                        else {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/lose"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Failed"));
                        }
                    }
                    MapleCoconut.this.warpOut();
                }
                else {
                    for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                        if (chr.getCoconutTeam() == 1) {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/victory"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Victory"));
                        }
                        else {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/lose"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Failed"));
                        }
                    }
                    MapleCoconut.this.warpOut();
                }
            }
        }, 360000L);
    }
    
    public void bonusTime() {
        this.getMap(0).broadcastMessage(MaplePacketCreator.getClock(120));
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (MapleCoconut.this.getMapleScore() == MapleCoconut.this.getStoryScore()) {
                    for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                        chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/lose"));
                        chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Failed"));
                    }
                    MapleCoconut.this.warpOut();
                }
                else if (MapleCoconut.this.getMapleScore() > MapleCoconut.this.getStoryScore()) {
                    for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                        if (chr.getCoconutTeam() == 0) {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/victory"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Victory"));
                        }
                        else {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/lose"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Failed"));
                        }
                    }
                    MapleCoconut.this.warpOut();
                }
                else {
                    for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                        if (chr.getCoconutTeam() == 1) {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/victory"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Victory"));
                        }
                        else {
                            chr.getClient().getSession().write(MaplePacketCreator.showEffect("event/coconut/lose"));
                            chr.getClient().getSession().write(MaplePacketCreator.playSound("Coconut/Failed"));
                        }
                    }
                    MapleCoconut.this.warpOut();
                }
            }
        }, 120000L);
    }
    
    public void warpOut() {
        this.setHittable(false);
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (final MapleCharacter chr : MapleCoconut.this.getMap(0).getCharactersThreadsafe()) {
                    if ((MapleCoconut.this.getMapleScore() > MapleCoconut.this.getStoryScore() && chr.getCoconutTeam() == 0) || (MapleCoconut.this.getStoryScore() > MapleCoconut.this.getMapleScore() && chr.getCoconutTeam() == 1)) {
                        MapleCoconut.this.givePrize(chr);
                    }
                    MapleCoconut.this.warpBack(chr);
                }
                MapleCoconut.this.unreset();
            }
        }, 12000L);
    }
    
    public static class MapleCoconuts
    {
        private int hits;
        private boolean hittable;
        private boolean stopped;
        private long hittime;
        
        public MapleCoconuts() {
            this.hits = 0;
            this.hittable = false;
            this.stopped = false;
            this.hittime = System.currentTimeMillis();
        }
        
        public void hit() {
            this.hittime = System.currentTimeMillis() + 1000L;
            ++this.hits;
        }
        
        public int getHits() {
            return this.hits;
        }
        
        public void resetHits() {
            this.hits = 0;
        }
        
        public boolean isHittable() {
            return this.hittable;
        }
        
        public void setHittable(final boolean hittable) {
            this.hittable = hittable;
        }
        
        public boolean isStopped() {
            return this.stopped;
        }
        
        public void setStopped(final boolean stopped) {
            this.stopped = stopped;
        }
        
        public long getHitTime() {
            return this.hittime;
        }
    }
}
