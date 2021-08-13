package server;

import client.MapleClient;
import handling.world.World;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;

public class AutobanManager implements Runnable
{
    private static final int AUTOBAN_POINTS = 5000;
    private static final AutobanManager instance;
    private final Map<Integer, Integer> points;
    private final Map<Integer, List<String>> reasons;
    private final Set<ExpirationEntry> expirations;
    private final ReentrantLock lock;
    
    public AutobanManager() {
        this.points = new HashMap<Integer, Integer>();
        this.reasons = new HashMap<Integer, List<String>>();
        this.expirations = new TreeSet<ExpirationEntry>();
        this.lock = new ReentrantLock(true);
    }
    
    public static AutobanManager getInstance() {
        return AutobanManager.instance;
    }
    
    public void autoban(final MapleClient c, final String reason) {
        if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
            c.getPlayer().dropMessage(5, "[WARNING] A/b triggled : " + reason);
            return;
        }
        this.addPoints(c, AUTOBAN_POINTS, 0L, reason);
    }
    
    public void addPoints(final MapleClient c, final int points, final long expiration, final String reason) {
        this.lock.lock();
        try {
            final int acc = c.getPlayer().getAccountID();
            if (this.points.containsKey(acc)) {
                final int SavedPoints = this.points.get(acc);
                if (SavedPoints >= AUTOBAN_POINTS) {
                    return;
                }
                this.points.put(acc, SavedPoints + points);
                final List<String> reasonList = this.reasons.get(acc);
                reasonList.add(reason);
            }
            else {
                this.points.put(acc, points);
                final List<String> reasonList = new LinkedList<String>();
                reasonList.add(reason);
                this.reasons.put(acc, reasonList);
            }
            if (this.points.get(acc) >= AUTOBAN_POINTS) {
                if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
                    c.getPlayer().dropMessage(5, "[WARNING] A/b triggled : " + reason);
                    return;
                }
                final StringBuilder sb = new StringBuilder("a/b ");
                sb.append(c.getPlayer().getName());
                sb.append(" (IP ");
                sb.append(c.getSession().getRemoteAddress().toString());
                sb.append("): ");
                sb.append(" (MAC ");
                sb.append(c.getMac());
                sb.append("): ");
                for (final String s : this.reasons.get(acc)) {
                    sb.append(s);
                    sb.append(", ");
                }
                FileoutputUtil.logToFile_chr(c.getPlayer(), FileoutputUtil.ban_log, sb.toString());
                c.getPlayer().ban(sb.toString(), false, true, false);
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "封号系统：玩家" + c.getPlayer().getName() + "使用非法程序，账号已被封处理'").getBytes());
                c.disconnect(true, false);
            }
            else if (expiration > 0L) {
                this.expirations.add(new ExpirationEntry(System.currentTimeMillis() + expiration, acc, points));
            }
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public void run() {
        final long now = System.currentTimeMillis();
        for (final ExpirationEntry e : this.expirations) {
            if (e.time > now) {
                return;
            }
            this.points.put(e.acc, this.points.get(e.acc) - e.points);
        }
    }
    
    static {
        instance = new AutobanManager();
    }
    
    private static class ExpirationEntry implements Comparable<ExpirationEntry>
    {
        public long time;
        public int acc;
        public int points;
        
        public ExpirationEntry(final long time, final int acc, final int points) {
            this.time = time;
            this.acc = acc;
            this.points = points;
        }
        
        @Override
        public int compareTo(final ExpirationEntry o) {
            return (int)(this.time - o.time);
        }
        
        @Override
        public boolean equals(final Object oth) {
            if (!(oth instanceof ExpirationEntry)) {
                return false;
            }
            final ExpirationEntry ee = (ExpirationEntry)oth;
            return this.time == ee.time && this.points == ee.points && this.acc == ee.acc;
        }
    }
}
