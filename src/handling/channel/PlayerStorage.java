package handling.channel;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import handling.MaplePacket;
import handling.world.CharacterTransfer;
import handling.world.CheaterData;
import handling.world.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import server.Timer;

public class PlayerStorage
{
    private final ReentrantReadWriteLock mutex;
    private final Lock rL;
    private final Lock wL;
    private final ReentrantReadWriteLock mutex2;
    private final Lock rL2;
    private final Lock wL2;
    private final Map<String, MapleCharacter> nameToChar;
    private final Map<Integer, MapleCharacter> idToChar;
    private final Map<Integer, CharacterTransfer> PendingCharacter;
    private final int channel;
    
    public PlayerStorage(final int channel) {
        this.mutex = new ReentrantReadWriteLock();
        this.rL = this.mutex.readLock();
        this.wL = this.mutex.writeLock();
        this.mutex2 = new ReentrantReadWriteLock();
        this.rL2 = this.mutex2.readLock();
        this.wL2 = this.mutex2.writeLock();
        this.nameToChar = new HashMap<String, MapleCharacter>();
        this.idToChar = new HashMap<Integer, MapleCharacter>();
        this.PendingCharacter = new HashMap<Integer, CharacterTransfer>();
        this.channel = channel;
        Timer.PingTimer.getInstance().schedule(new PersistingTask(), 900000L);
    }
    
    public Collection<MapleCharacter> getAllCharacters() {
        this.rL.lock();
        try {
            return Collections.unmodifiableCollection((Collection<? extends MapleCharacter>)this.idToChar.values());
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public void registerPlayer(final MapleCharacter chr) {
        this.wL.lock();
        try {
            this.nameToChar.put(chr.getName().toLowerCase(), chr);
            this.idToChar.put(chr.getId(), chr);
        }
        finally {
            this.wL.unlock();
        }
        World.Find.register(chr.getId(), chr.getName(), this.channel);
    }
    
    public void registerPendingPlayer(final CharacterTransfer chr, final int playerid) {
        this.wL2.lock();
        try {
            this.PendingCharacter.put(playerid, chr);
        }
        finally {
            this.wL2.unlock();
        }
    }
    
    public void deregisterPlayer(final MapleCharacter chr) {
        this.wL.lock();
        try {
            this.nameToChar.remove(chr.getName().toLowerCase());
            this.idToChar.remove(chr.getId());
        }
        finally {
            this.wL.unlock();
        }
        World.Find.forceDeregister(chr.getId(), chr.getName());
    }
    
    public void deregisterPlayer(final int idz, final String namez) {
        this.wL.lock();
        try {
            this.nameToChar.remove(namez.toLowerCase());
            this.idToChar.remove(idz);
        }
        finally {
            this.wL.unlock();
        }
        World.Find.forceDeregister(idz, namez);
    }
    
    public void deregisterPendingPlayer(final int charid) {
        this.wL2.lock();
        try {
            this.PendingCharacter.remove(charid);
        }
        finally {
            this.wL2.unlock();
        }
    }
    
    public CharacterTransfer getPendingCharacter(final int charid) {
        this.rL2.lock();
        CharacterTransfer toreturn;
        try {
            toreturn = this.PendingCharacter.get(charid);
        }
        finally {
            this.rL2.unlock();
        }
        if (toreturn != null) {
            this.deregisterPendingPlayer(charid);
        }
        return toreturn;
    }
    
    public MapleCharacter getCharacterByName(final String name) {
        this.rL.lock();
        try {
            return this.nameToChar.get(name.toLowerCase());
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public MapleCharacter getCharacterById(final int id) {
        this.rL.lock();
        try {
            return this.idToChar.get(id);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public int getConnectedClients() {
        return this.idToChar.size();
    }
    
    public List<CheaterData> getCheaters() {
        final List<CheaterData> cheaters = new ArrayList<CheaterData>();
        this.rL.lock();
        try {
            for (final MapleCharacter chr : this.nameToChar.values()) {
                if (chr.getCheatTracker().getPoints() > 0) {
                    cheaters.add(new CheaterData(chr.getCheatTracker().getPoints(), MapleCharacterUtil.makeMapleReadable(chr.getName()) + " (" + chr.getCheatTracker().getPoints() + ") " + chr.getCheatTracker().getSummary()));
                }
            }
        }
        finally {
            this.rL.unlock();
        }
        return cheaters;
    }
    
    public void disconnectAll() {
        this.disconnectAll(false);
    }
    
    public void disconnectAll(final boolean checkGM) {
        this.wL.lock();
        try {
            final Iterator<MapleCharacter> itr = this.nameToChar.values().iterator();
            while (itr.hasNext()) {
                final MapleCharacter chr = itr.next();
                if (!chr.isGM() || !checkGM) {
                    chr.getClient().disconnect(false, false, true);
                    chr.getClient().getSession().close(true);
                    World.Find.forceDeregister(chr.getId(), chr.getName());
                    itr.remove();
                }
            }
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public String getOnlinePlayers(final boolean byGM) {
        final StringBuilder sb = new StringBuilder();
        if (byGM) {
            this.rL.lock();
            try {
                final Iterator<MapleCharacter> itr = this.nameToChar.values().iterator();
                while (itr.hasNext()) {
                    sb.append(MapleCharacterUtil.makeMapleReadable(itr.next().getName()));
                    sb.append(", ");
                }
            }
            finally {
                this.rL.unlock();
            }
        }
        else {
            this.rL.lock();
            try {
                for (final MapleCharacter chr : this.nameToChar.values()) {
                    if (!chr.isGM()) {
                        sb.append(MapleCharacterUtil.makeMapleReadable(chr.getName()));
                        sb.append(", ");
                    }
                }
            }
            finally {
                this.rL.unlock();
            }
        }
        return sb.toString();
    }
    
    public void broadcastPacket(final MaplePacket data) {
        this.rL.lock();
        try {
            final Iterator<MapleCharacter> itr = this.nameToChar.values().iterator();
            while (itr.hasNext()) {
                itr.next().getClient().getSession().write(data);
            }
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public void broadcastSmegaPacket(final MaplePacket data) {
        this.rL.lock();
        try {
            for (final MapleCharacter chr : this.nameToChar.values()) {
                if (chr.getClient().isLoggedIn() && chr.getSmega()) {
                    chr.getClient().getSession().write(data);
                }
            }
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public void broadcastGMPacket(final MaplePacket data) {
        this.rL.lock();
        try {
            for (final MapleCharacter chr : this.nameToChar.values()) {
                if (chr.getClient().isLoggedIn() && chr.isGM()) {
                    chr.getClient().getSession().write(data);
                }
            }
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public List<MapleCharacter> getAllCharactersThreadSafe() {
        final List<MapleCharacter> ret = new ArrayList<MapleCharacter>();
        ret.addAll(this.getAllCharacters());
        return ret;
    }
    
    public class PersistingTask implements Runnable
    {
        @Override
        public void run() {
            PlayerStorage.this.wL2.lock();
            try {
                final long currenttime = System.currentTimeMillis();
                final Iterator<Map.Entry<Integer, CharacterTransfer>> itr = PlayerStorage.this.PendingCharacter.entrySet().iterator();
                while (itr.hasNext()) {
                    if (currenttime - itr.next().getValue().TranferTime > 40000L) {
                        itr.remove();
                    }
                }
                Timer.PingTimer.getInstance().schedule(new PersistingTask(), 900000L);
            }
            finally {
                PlayerStorage.this.wL2.unlock();
            }
        }
    }
}
