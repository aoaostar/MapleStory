package server.events;

import client.MapleCharacter;
import java.util.concurrent.ScheduledFuture;
import server.Timer;
import tools.MaplePacketCreator;

public class MapleFitness extends MapleEvent
{
    private static final long serialVersionUID = 845748950824L;
    private final long time = 600000L;
    private long timeStarted;
    private ScheduledFuture<?> fitnessSchedule;
    private ScheduledFuture<?> msgSchedule;
    
    public MapleFitness(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.timeStarted = 0L;
    }
    
    @Override
    public void finished(final MapleCharacter chr) {
        this.givePrize(chr);
    }
    
    @Override
    public void onMapLoad(final MapleCharacter chr) {
        if (this.isTimerStarted()) {
            chr.getClient().getSession().write(MaplePacketCreator.getClock((int)(this.getTimeLeft() / 1000L)));
        }
    }
    
    @Override
    public void startEvent() {
        this.unreset();
        super.reset();
        this.broadcast(MaplePacketCreator.getClock(600));
        this.timeStarted = System.currentTimeMillis();
        this.checkAndMessage();
        final Timer.EventTimer instance = Timer.EventTimer.getInstance();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MapleFitness.this.mapid.length; ++i) {
                    for (final MapleCharacter chr : MapleFitness.this.getMap(i).getCharactersThreadsafe()) {
                        MapleFitness.this.warpBack(chr);
                    }
                }
                MapleFitness.this.unreset();
            }
        };
        this.getClass();
        this.fitnessSchedule = instance.schedule(r, 600000L);
        this.broadcast(MaplePacketCreator.serverNotice(0, "门已打开。记得在光柱门↑键进入。"));
    }
    
    public boolean isTimerStarted() {
        return this.timeStarted > 0L;
    }
    
    public long getTime() {
        return 600000L;
    }
    
    public void resetSchedule() {
        this.timeStarted = 0L;
        if (this.fitnessSchedule != null) {
            this.fitnessSchedule.cancel(false);
        }
        this.fitnessSchedule = null;
        if (this.msgSchedule != null) {
            this.msgSchedule.cancel(false);
        }
        this.msgSchedule = null;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(false);
    }
    
    @Override
    public void unreset() {
        super.unreset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(true);
    }
    
    public long getTimeLeft() {
        return 600000L - (System.currentTimeMillis() - this.timeStarted);
    }
    
    public void checkAndMessage() {
        this.msgSchedule = Timer.EventTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                final long timeLeft = MapleFitness.this.getTimeLeft();
                if (timeLeft > 9000L && timeLeft < 11000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "对于你们无法战胜的比赛，我们希望您下一次战胜它！下次再见~"));
                }
                else if (timeLeft > 11000L && timeLeft < 101000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "好吧，你剩下的时间没有多少了。请抓紧时间!"));
                }
                else if (timeLeft > 101000L && timeLeft < 241000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "第4阶段，是最后一项 [冒险岛体能测试]. 请不要在最后一刻放弃，拼尽全力. 丰厚的奖励在最高层等着你哦!"));
                }
                else if (timeLeft > 241000L && timeLeft < 301000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "第三阶段，有很多陷阱，你可能会看到他们，但你不能踩他们.按照你的方式进行下去吧."));
                }
                else if (timeLeft > 301000L && timeLeft < 361000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请务必慢慢地移动，小心掉到下面去。"));
                }
                else if (timeLeft > 361000L && timeLeft < 501000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请记住，如果你在活动期间死掉了，你会从游戏中淘汰. 如果你想补充HP，使用药水或移动之前先恢复HP。"));
                }
                else if (timeLeft > 501000L && timeLeft < 601000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "最重要的是你需要知道，不要被猴子扔的香蕉打中，请在规定的时间完成一切！"));
                }
                else if (timeLeft > 601000L && timeLeft < 661000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "第二阶段障碍是猴子扔香蕉. 请确保沿着正确的路线移动，躲避它们的攻击。"));
                }
                else if (timeLeft > 661000L && timeLeft < 701000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请记住，如果你在活动期间死掉了，你会从游戏中淘汰. 如果你想补充HP，使用药水或移动之前先恢复HP。"));
                }
                else if (timeLeft > 701000L && timeLeft < 781000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "每个人都该参加 [冒险岛体能测试] 参加这个项目，无论完成的顺序，都会获得奖励，所以只是娱乐，慢慢来，清除了4个阶段。"));
                }
                else if (timeLeft > 781000L && timeLeft < 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "不要畏惧苦难，继续下去，享受游戏，重在娱乐嘛."));
                }
                else if (timeLeft > 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "[冒险岛体能测试]有4个阶段，如果你碰巧在游戏过程中死亡，你会从比赛被淘汰，所以请注意这一点。"));
                }
            }
        }, 90000L);
    }
}
