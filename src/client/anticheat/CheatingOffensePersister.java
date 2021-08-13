package client.anticheat;

import server.Timer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CheatingOffensePersister
{
    private static final CheatingOffensePersister instance;
    private final Set<CheatingOffenseEntry> toPersist;
    private final Lock mutex;
    
    public static CheatingOffensePersister getInstance() {
        return CheatingOffensePersister.instance;
    }
    
    private CheatingOffensePersister() {
        this.toPersist = new LinkedHashSet<CheatingOffenseEntry>();
        this.mutex = new ReentrantLock();
        Timer.CheatTimer.getInstance().register(new PersistingTask(), 61000L);
    }
    
    public void persistEntry(final CheatingOffenseEntry coe) {
        this.mutex.lock();
        try {
            this.toPersist.remove(coe);
            this.toPersist.add(coe);
        }
        finally {
            this.mutex.unlock();
        }
    }
    
    static {
        instance = new CheatingOffensePersister();
    }
    
    public class PersistingTask implements Runnable
    {
        @Override
        public void run() {
            CheatingOffensePersister.this.mutex.lock();
            try {
                CheatingOffensePersister.this.toPersist.clear();
            }
            finally {
                CheatingOffensePersister.this.mutex.unlock();
            }
        }
    }
}
