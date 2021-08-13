package server.maps;

import client.MapleCharacter;
import handling.world.World;
import java.awt.Point;
import server.Randomizer;
import server.Timer;
import server.life.MapleLifeFactory;
import tools.MaplePacketCreator;

public class AramiaFireWorks
{
    public static final int KEG_ID = 4031875;
    public static final int SUN_ID = 4001246;
    public static final int DEC_ID = 4001473;
    public static final int MAX_KEGS = 10000;
    public static final int MAX_SUN = 14000;
    public static final int MAX_DEC = 18000;
    private static final AramiaFireWorks instance;
    private static final int[] arrayMob;
    private static final int[] arrayX;
    private static final int[] arrayY;
    private static final int[] array_X;
    private static final int[] array_Y;
    private static final int flake_Y = 149;
    private short kegs;
    private short sunshines;
    private short decorations;
    
    public AramiaFireWorks() {
        this.kegs = 0;
        this.sunshines = 2333;
        this.decorations = 3000;
    }
    
    public static AramiaFireWorks getInstance() {
        return AramiaFireWorks.instance;
    }
    
    public void giveKegs(final MapleCharacter c, final int kegs) {
        this.kegs += (short)kegs;
        if (this.kegs >= 10000) {
            this.kegs = 0;
            this.broadcastEvent(c);
        }
    }
    
    private void broadcastServer(final MapleCharacter c, final int itemid) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, itemid, "<頻道 " + c.getClient().getChannel() + "> 弓箭手村邱比特公園即將開始發射煙火!").getBytes());
    }
    
    public short getKegsPercentage() {
        return (short)(this.kegs / 10000 * 10000);
    }
    
    private void broadcastEvent(final MapleCharacter c) {
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AramiaFireWorks.this.startEvent(c.getClient().getChannelServer().getMapFactory().getMap(209080000));
            }
        }, 10000L);
    }
    
    private void startEvent(final MapleMap map) {
        map.startMapEffect("雪人大大出現啦", 5120000);
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AramiaFireWorks.this.spawnMonster(map);
            }
        }, 5000L);
    }
    
    private void spawnMonster(final MapleMap map) {
        for (int i = 0; i < AramiaFireWorks.arrayMob.length; ++i) {
            final Point pos = new Point(AramiaFireWorks.arrayX[i], AramiaFireWorks.arrayY[i]);
            map.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(AramiaFireWorks.arrayMob[i]), pos);
        }
    }
    
    public void giveSuns(final MapleCharacter c, final int kegs) {
        this.sunshines += (short)kegs;
        final MapleMap map = c.getClient().getChannelServer().getMapFactory().getMap(555000000);
        final MapleReactor reactor = map.getReactorByName("XmasTree");
        for (int gogo = kegs + 2333; gogo > 0; gogo -= 2333) {
            switch (reactor.getState()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4: {
                    if (this.sunshines >= 2333 * (2 + reactor.getState())) {
                        reactor.setState((byte)(reactor.getState() + 1));
                        reactor.setTimerActive(false);
                        map.broadcastMessage(MaplePacketCreator.triggerReactor(reactor, reactor.getState()));
                        break;
                    }
                    break;
                }
                default: {
                    if (this.sunshines >= 2333) {
                        map.resetReactors();
                        break;
                    }
                    break;
                }
            }
        }
        if (this.sunshines >= 14000) {
            this.sunshines = 0;
            this.broadcastSun(c);
        }
    }
    
    public short getSunsPercentage() {
        return (short)(this.sunshines / 14000 * 10000);
    }
    
    private void broadcastSun(final MapleCharacter c) {
        this.broadcastServer(c, 4001246);
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AramiaFireWorks.this.startSun(c.getClient().getChannelServer().getMapFactory().getMap(970010000));
            }
        }, 10000L);
    }
    
    private void startSun(final MapleMap map) {
        map.startMapEffect("The tree is bursting with sunshine!", 5121010);
        for (int i = 0; i < 3; ++i) {
            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    AramiaFireWorks.this.spawnItem(map);
                }
            }, 5000 + i * 10000);
        }
    }
    
    private void spawnItem(final MapleMap map) {
        for (int i = 0; i < Randomizer.nextInt(5) + 10; ++i) {
            final Point pos = new Point(AramiaFireWorks.array_X[i], AramiaFireWorks.array_Y[i]);
            map.spawnAutoDrop((Randomizer.nextInt(3) == 1) ? 3010025 : 4001246, pos);
        }
    }
    
    public void giveDecs(final MapleCharacter c, final int kegs) {
        this.decorations += (short)kegs;
        final MapleMap map = c.getClient().getChannelServer().getMapFactory().getMap(555000000);
        final MapleReactor reactor = map.getReactorByName("XmasTree");
        for (int gogo = kegs + 3000; gogo > 0; gogo -= 3000) {
            switch (reactor.getState()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4: {
                    if (this.decorations >= 3000 * (2 + reactor.getState())) {
                        reactor.setState((byte)(reactor.getState() + 1));
                        reactor.setTimerActive(false);
                        map.broadcastMessage(MaplePacketCreator.triggerReactor(reactor, reactor.getState()));
                        break;
                    }
                    break;
                }
                default: {
                    if (this.decorations >= 3000) {
                        map.resetReactors();
                        break;
                    }
                    break;
                }
            }
        }
        if (this.decorations >= 18000) {
            this.decorations = 0;
            this.broadcastDec(c);
        }
    }
    
    public short getDecsPercentage() {
        return (short)(this.decorations / 18000 * 10000);
    }
    
    private void broadcastDec(final MapleCharacter c) {
        this.broadcastServer(c, 4001473);
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AramiaFireWorks.this.startDec(c.getClient().getChannelServer().getMapFactory().getMap(555000000));
            }
        }, 10000L);
    }
    
    private void startDec(final MapleMap map) {
        map.startMapEffect("The tree is bursting with snow!", 5120000);
        for (int i = 0; i < 3; ++i) {
            Timer.EventTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    AramiaFireWorks.this.spawnDec(map);
                }
            }, 5000 + i * 10000);
        }
    }
    
    private void spawnDec(final MapleMap map) {
        for (int i = 0; i < Randomizer.nextInt(10) + 40; ++i) {
            final Point pos = new Point(Randomizer.nextInt(800) - 400, 149);
            map.spawnAutoDrop((Randomizer.nextInt(15) == 1) ? 2060006 : 2060006, pos);
        }
    }
    
    static {
        instance = new AramiaFireWorks();
        arrayMob = new int[] { 9400708 };
        arrayX = new int[] { -115 };
        arrayY = new int[] { 154 };
        array_X = new int[] { 720, 180, 630, 270, 360, 540, 450, 142, 142, 218, 772, 810, 848, 232, 308, 142 };
        array_Y = new int[] { 1234, 1234, 1174, 1234, 1174, 1174, 1174, 1260, 1234, 1234, 1234, 1234, 1234, 1114, 1114, 1140 };
    }
}
