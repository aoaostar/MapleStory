package server.events;

import client.MapleCharacter;
import client.MapleDisease;
import java.util.concurrent.ScheduledFuture;
import server.Timer;
import server.life.MobSkillFactory;
import server.maps.MapleMap;
import tools.MaplePacketCreator;

public class MapleSnowball extends MapleEvent
{
    private final MapleSnowballs[] balls;
    
    public MapleSnowball(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.balls = new MapleSnowballs[2];
    }
    
    @Override
    public void unreset() {
        super.unreset();
        for (int i = 0; i < 2; ++i) {
            this.getSnowBall(i).resetSchedule();
            this.resetSnowBall(i);
        }
    }
    
    @Override
    public void reset() {
        super.reset();
        this.makeSnowBall(0);
        this.makeSnowBall(1);
    }
    
    @Override
    public void startEvent() {
        for (int i = 0; i < 2; ++i) {
            final MapleSnowballs ball = this.getSnowBall(i);
            ball.broadcast(this.getMap(0), 0);
            ball.setInvis(false);
            ball.broadcast(this.getMap(0), 5);
            this.getMap(0).broadcastMessage(MaplePacketCreator.enterSnowBall());
        }
    }
    
    public void resetSnowBall(final int teamz) {
        this.balls[teamz] = null;
    }
    
    public void makeSnowBall(final int teamz) {
        this.resetSnowBall(teamz);
        this.balls[teamz] = new MapleSnowballs(teamz);
    }
    
    public MapleSnowballs getSnowBall(final int teamz) {
        return this.balls[teamz];
    }
    
    public static class MapleSnowballs
    {
        private int position;
        private final int team;
        private int startPoint;
        private boolean invis;
        private boolean hittable;
        private int snowmanhp;
        private ScheduledFuture<?> snowmanSchedule;
        
        public static void hitSnowball(final MapleCharacter chr) {
            final int team = (chr.getPosition().y <= -80) ? 1 : 0;
            final MapleSnowball sb = (MapleSnowball)chr.getClient().getChannelServer().getEvent(MapleEventType.雪球赛);
            final MapleSnowballs ball = sb.getSnowBall(team);
            if (ball != null && !ball.isInvis()) {
                final boolean snowman = chr.getPosition().x < -360 && chr.getPosition().x > -560;
                if (!snowman) {
                    final int damage = ((Math.random() < 0.01 || (chr.getPosition().x > ball.getLeftX() && chr.getPosition().x < ball.getRightX())) && ball.isHittable()) ? 10 : 0;
                    chr.getMap().broadcastMessage(MaplePacketCreator.hitSnowBall(team, damage, 0, 1));
                    if (damage == 0) {
                        if (Math.random() < 0.2) {
                            chr.getClient().getSession().write(MaplePacketCreator.leftKnockBack());
                            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
                        }
                    }
                    else {
                        ball.setPositionX(ball.getPosition() + 1);
                        if (ball.getPosition() == 255 || ball.getPosition() == 511 || ball.getPosition() == 767) {
                            ball.setStartPoint(chr.getMap());
                            chr.getMap().broadcastMessage(MaplePacketCreator.rollSnowball(4, sb.getSnowBall(0), sb.getSnowBall(1)));
                        }
                        else if (ball.getPosition() == 899) {
                            final MapleMap map = chr.getMap();
                            for (int i = 0; i < 2; ++i) {
                                sb.getSnowBall(i).setInvis(true);
                                map.broadcastMessage(MaplePacketCreator.rollSnowball(i + 2, sb.getSnowBall(0), sb.getSnowBall(1)));
                            }
                            chr.getMap().broadcastMessage(MaplePacketCreator.serverNotice(6, "[恭喜] " + ((team == 0) ? "蓝队" : "红队") + " 赢得胜利!"));
                            for (final MapleCharacter chrz : chr.getMap().getCharactersThreadsafe()) {
                                if ((team == 0 && chrz.getPosition().y > -80) || (team == 1 && chrz.getPosition().y <= -80)) {
                                    sb.givePrize(chrz);
                                }
                                sb.warpBack(chrz);
                            }
                            sb.unreset();
                        }
                        else if (ball.getPosition() < 899) {
                            chr.getMap().broadcastMessage(MaplePacketCreator.rollSnowball(4, sb.getSnowBall(0), sb.getSnowBall(1)));
                            ball.setInvis(false);
                        }
                    }
                }
                else if (ball.getPosition() < 899) {
                    int damage = 15;
                    if (Math.random() < 0.3) {
                        damage = 0;
                    }
                    if (Math.random() < 0.05) {
                        damage = 45;
                    }
                    chr.getMap().broadcastMessage(MaplePacketCreator.hitSnowBall(team + 2, damage, 0, 0));
                    ball.setSnowmanHP(ball.getSnowmanHP() - damage);
                    if (damage > 0) {
                        chr.getMap().broadcastMessage(MaplePacketCreator.rollSnowball(0, sb.getSnowBall(0), sb.getSnowBall(1)));
                        if (ball.getSnowmanHP() <= 0) {
                            ball.setSnowmanHP(7500);
                            final MapleSnowballs oBall = sb.getSnowBall((team == 0) ? 1 : 0);
                            oBall.setHittable(false);
                            final MapleMap map2 = chr.getMap();
                            oBall.broadcast(map2, 4);
                            oBall.snowmanSchedule = Timer.EventTimer.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    oBall.setHittable(true);
                                    oBall.broadcast(map2, 5);
                                }
                            }, 10000L);
                            for (final MapleCharacter chrz2 : chr.getMap().getCharactersThreadsafe()) {
                                if ((ball.getTeam() == 0 && chr.getPosition().y < -80) || (ball.getTeam() == 1 && chr.getPosition().y > -80)) {
                                    chrz2.giveDebuff(MapleDisease.诱惑, MobSkillFactory.getMobSkill(128, 1));
                                }
                            }
                        }
                    }
                }
            }
        }
        
        public MapleSnowballs(final int team_) {
            this.position = 0;
            this.startPoint = 0;
            this.invis = true;
            this.hittable = true;
            this.snowmanhp = 7500;
            this.snowmanSchedule = null;
            this.team = team_;
        }
        
        public void resetSchedule() {
            if (this.snowmanSchedule != null) {
                this.snowmanSchedule.cancel(false);
                this.snowmanSchedule = null;
            }
        }
        
        public int getTeam() {
            return this.team;
        }
        
        public int getPosition() {
            return this.position;
        }
        
        public void setPositionX(final int pos) {
            this.position = pos;
        }
        
        public void setStartPoint(final MapleMap map) {
            this.broadcast(map, ++this.startPoint);
        }
        
        public boolean isInvis() {
            return this.invis;
        }
        
        public void setInvis(final boolean i) {
            this.invis = i;
        }
        
        public boolean isHittable() {
            return this.hittable && !this.invis;
        }
        
        public void setHittable(final boolean b) {
            this.hittable = b;
        }
        
        public int getSnowmanHP() {
            return this.snowmanhp;
        }
        
        public void setSnowmanHP(final int shp) {
            this.snowmanhp = shp;
        }
        
        public void broadcast(final MapleMap map, final int message) {
            for (final MapleCharacter chr : map.getCharactersThreadsafe()) {
                if ((this.team == 0 && chr.getPosition().y > -80) || (this.team == 1 && chr.getPosition().y <= -80)) {
                    chr.getClient().getSession().write(MaplePacketCreator.snowballMessage(this.team, message));
                }
            }
        }
        
        public int getLeftX() {
            return this.position * 3 + 175;
        }
        
        public int getRightX() {
            return this.getLeftX() + 275;
        }
    }
}
