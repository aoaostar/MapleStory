package scripting;

import client.MapleCharacter;
import client.MapleQuestStatus;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.script.ScriptException;
import server.MapleCarnivalParty;
import server.MapleItemInformationProvider;
import server.MapleSquad;
import server.Timer;
import server.life.MapleMonster;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.packet.UIPacket;

public class EventInstanceManager
{
    private List<MapleCharacter> chars;
    private List<Integer> dced;
    private List<MapleMonster> mobs;
    private Map<Integer, Integer> killCount;
    private EventManager em;
    private int channel;
    private String name;
    private Properties props;
    private long timeStarted;
    private long eventTime;
    private List<Integer> mapIds;
    private List<Boolean> isInstanced;
    private ScheduledFuture<?> eventTimer;
    private final ReentrantReadWriteLock mutex;
    private final Lock rL;
    private final Lock wL;
    private boolean disposed;
    
    public EventInstanceManager(final EventManager em, final String name, final int channel) {
        this.chars = new LinkedList<MapleCharacter>();
        this.dced = new LinkedList<Integer>();
        this.mobs = new LinkedList<MapleMonster>();
        this.killCount = new HashMap<Integer, Integer>();
        this.props = new Properties();
        this.timeStarted = 0L;
        this.eventTime = 0L;
        this.mapIds = new LinkedList<Integer>();
        this.isInstanced = new LinkedList<Boolean>();
        this.mutex = new ReentrantReadWriteLock();
        this.rL = this.mutex.readLock();
        this.wL = this.mutex.writeLock();
        this.disposed = false;
        this.em = em;
        this.name = name;
        this.channel = channel;
    }
    
    public void registerPlayer(final MapleCharacter chr) {
        if (this.disposed || chr == null) {
            return;
        }
        try {
            this.wL.lock();
            try {
                this.chars.add(chr);
            }
            finally {
                this.wL.unlock();
            }
            chr.setEventInstance(this);
            this.em.getIv().invokeFunction("playerEntry", this, chr);
        }
        catch (NullPointerException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
            ex.printStackTrace();
        }
        catch (Exception ex2) {
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerEntry:\n" + ex2);
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerEntry:\n" + ex2);
        }
    }
    
    public void changedMap(final MapleCharacter chr, final int mapid) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("changedMap", this, chr, mapid);
        }
        catch (NullPointerException ex2) {}
        catch (Exception ex) {
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "副本名称" + this.em.getName() + ", 实例名称 : " + this.name + ", 方法名称 : changedMap:\n" + ex);
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : changedMap:\n" + ex);
        }
    }
    
    public void timeOut(final long delay, final EventInstanceManager eim) {
        if (this.disposed || eim == null) {
            return;
        }
        this.eventTimer = Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (EventInstanceManager.this.disposed || eim == null || EventInstanceManager.this.em == null) {
                    return;
                }
                try {
                    EventInstanceManager.this.em.getIv().invokeFunction("scheduledTimeout", eim);
                }
                catch (Exception ex) {
                    FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : scheduledTimeout:\n" + ex);
                    System.out.println("Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : scheduledTimeout:\n" + ex);
                }
            }
        }, delay);
    }
    
    public final void forceRemovePlayerByCharName(final String name) {
        ChannelServer.forceRemovePlayerByCharName(name);
    }
    
    public void stopEventTimer() {
        this.eventTime = 0L;
        this.timeStarted = 0L;
        if (this.eventTimer != null) {
            this.eventTimer.cancel(false);
        }
    }
    
    public void restartEventTimer(final long time) {
        try {
            if (this.disposed) {
                return;
            }
            this.timeStarted = System.currentTimeMillis();
            this.eventTime = time;
            if (this.eventTimer != null) {
                this.eventTimer.cancel(false);
            }
            this.eventTimer = null;
            final int timesend = (int)time / 1000;
            for (final MapleCharacter chr : this.getPlayers()) {
                chr.getClient().getSession().write(MaplePacketCreator.getClock(timesend));
            }
            this.timeOut(time, this);
        }
        catch (Exception ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n");
            ex.printStackTrace();
        }
    }
    
    public boolean isSquadLeader(final MapleCharacter tt, final MapleSquad.MapleSquadType ttt) {
        return tt.getClient().getChannelServer().getMapleSquad(ttt).getLeader().equals(tt);
    }
    
    public void startEventTimer(final long time) {
        this.restartEventTimer(time);
    }
    
    public int getInstanceId() {
        return ChannelServer.getInstance(1).getInstanceId();
    }
    
    public void addInstanceId() {
        ChannelServer.getInstance(1).addInstanceId();
    }
    
    public boolean isTimerStarted() {
        return this.eventTime > 0L && this.timeStarted > 0L;
    }
    
    public long getTimeLeft() {
        return this.eventTime - (System.currentTimeMillis() - this.timeStarted);
    }
    
    public void registerParty(final MapleParty party, final MapleMap map) {
        if (this.disposed) {
            return;
        }
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter c = map.getCharacterById(pc.getId());
            this.registerPlayer(c);
        }
    }
    
    public void unregisterPlayer(final MapleCharacter chr) {
        if (this.disposed) {
            chr.setEventInstance(null);
            return;
        }
        this.wL.lock();
        try {
            this.unregisterPlayer_NoLock(chr);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    private boolean unregisterPlayer_NoLock(final MapleCharacter chr) {
        if (this.name.equals("CWKPQ")) {
            final MapleSquad squad = ChannelServer.getInstance(this.channel).getMapleSquad("CWKPQ");
            if (squad != null) {
                squad.removeMember(chr.getName());
                if (squad.getLeaderName().equals(chr.getName())) {
                    this.em.setProperty("leader", "false");
                }
            }
        }
        chr.setEventInstance(null);
        if (this.disposed) {
            return false;
        }
        if (this.chars.contains(chr)) {
            this.chars.remove(chr);
            return true;
        }
        return false;
    }
    
    public final boolean disposeIfPlayerBelow(final byte size, final int towarp) {
        if (this.disposed) {
            return true;
        }
        MapleMap map = null;
        if (towarp > 0) {
            map = this.getMapFactory().getMap(towarp);
        }
        this.wL.lock();
        try {
            if (this.chars.size() <= size) {
                final List<MapleCharacter> chrs = new LinkedList<MapleCharacter>(this.chars);
                for (final MapleCharacter chr : chrs) {
                    this.unregisterPlayer_NoLock(chr);
                    if (towarp > 0) {
                        chr.changeMap(map, map.getPortal(0));
                    }
                }
                this.dispose_NoLock();
                return true;
            }
        }
        finally {
            this.wL.unlock();
        }
        return false;
    }
    
    public final void saveBossQuest(final int points) {
        if (this.disposed) {
            return;
        }
        for (final MapleCharacter chr : this.getPlayers()) {
            final MapleQuestStatus record = chr.getQuestNAdd(MapleQuest.getInstance(150001));
            if (record.getCustomData() != null) {
                record.setCustomData(String.valueOf(points + Integer.parseInt(record.getCustomData())));
            }
            else {
                record.setCustomData(String.valueOf(points));
            }
        }
    }
    
    public List<MapleCharacter> getPlayers() {
        if (this.disposed) {
            return Collections.emptyList();
        }
        this.rL.lock();
        try {
            return new LinkedList<MapleCharacter>(this.chars);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public List<Integer> getDisconnected() {
        return this.dced;
    }
    
    public final int getPlayerCount() {
        if (this.disposed) {
            return 0;
        }
        return this.chars.size();
    }
    
    public void registerMonster(final MapleMonster mob) {
        if (this.disposed) {
            return;
        }
        this.mobs.add(mob);
        mob.setEventInstance(this);
    }
    
    public void unregisterMonster(final MapleMonster mob) {
        mob.setEventInstance(null);
        if (this.disposed) {
            return;
        }
        this.mobs.remove(mob);
        if (this.mobs.size() == 0) {
            try {
                this.em.getIv().invokeFunction("allMonstersDead", this);
            }
            catch (Exception ex) {
                FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : allMonstersDead:\n" + ex);
                System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : allMonstersDead:\n" + ex);
            }
        }
    }
    
    public void playerKilled(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("playerDead", this, chr);
        }
        catch (Exception ex) {
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerDead:\n" + ex);
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerDead:\n" + ex);
        }
    }
    
    public boolean revivePlayer(final MapleCharacter chr) {
        if (this.disposed) {
            return false;
        }
        try {
            final Object b = this.em.getIv().invokeFunction("playerRevive", this, chr);
            if (b instanceof Boolean) {
                return (boolean)b;
            }
        }
        catch (Exception ex) {
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerRevive:\n" + ex);
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerRevive:\n" + ex);
        }
        return true;
    }
    
    public void playerDisconnected(final MapleCharacter chr, final int idz) {
        if (this.disposed) {
            return;
        }
        byte ret;
        try {
            ret = ((Double)this.em.getIv().invokeFunction("playerDisconnected", this, chr)).byteValue();
        }
        catch (Exception e) {
            ret = 0;
        }
        this.wL.lock();
        try {
            if (this.disposed) {
                return;
            }
            this.dced.add(idz);
            if (chr != null) {
                this.unregisterPlayer_NoLock(chr);
            }
            if (ret == 0) {
                if (this.getPlayerCount() <= 0) {
                    this.dispose_NoLock();
                }
            }
            else if ((ret > 0 && this.getPlayerCount() < ret) || (ret < 0 && (this.isLeader(chr) || this.getPlayerCount() < ret * -1))) {
                final List<MapleCharacter> chrs = new LinkedList<MapleCharacter>(this.chars);
                for (final MapleCharacter player : chrs) {
                    if (player.getId() != idz) {
                        this.removePlayer(player);
                    }
                }
                this.dispose_NoLock();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public void monsterKilled(final MapleCharacter chr, final MapleMonster mob) {
        if (this.disposed) {
            return;
        }
        try {
            Integer kc = this.killCount.get(chr.getId());
            final int inc = ((Double)this.em.getIv().invokeFunction("monsterValue", this, mob.getId())).intValue();
            if (this.disposed) {
                return;
            }
            if (kc == null) {
                kc = inc;
            }
            else {
                kc += inc;
            }
            this.killCount.put(chr.getId(), kc);
            if (chr.getCarnivalParty() != null && (mob.getStats().getPoint() > 0 || mob.getStats().getCP() > 0)) {
                this.em.getIv().invokeFunction("monsterKilled", this, chr, (mob.getStats().getCP() > 0) ? mob.getStats().getCP() : mob.getStats().getPoint());
            }
        }
        catch (ScriptException ex) {
            System.out.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
        }
        catch (NoSuchMethodException ex2) {
            System.out.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex2);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex2);
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex3);
        }
    }
    
    public void monsterDamaged(final MapleCharacter chr, final MapleMonster mob, final int damage) {
        if (this.disposed || mob.getId() != 9700037) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("monsterDamaged", this, chr, mob.getId(), damage);
        }
        catch (ScriptException ex) {
            System.out.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
        }
        catch (NoSuchMethodException ex2) {
            System.out.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex2);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex2);
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex3);
        }
    }
    
    public int getKillCount(final MapleCharacter chr) {
        if (this.disposed) {
            return 0;
        }
        final Integer kc = this.killCount.get(chr.getId());
        if (kc == null) {
            return 0;
        }
        return kc;
    }
    
    public void dispose_NoLock() {
        if (this.disposed || this.em == null) {
            return;
        }
        final String emN = this.em.getName();
        try {
            this.disposed = true;
            for (final MapleCharacter chr : this.chars) {
                chr.setEventInstance(null);
            }
            this.chars.clear();
            this.chars = null;
            for (final MapleMonster mob : this.mobs) {
                mob.setEventInstance(null);
            }
            this.mobs.clear();
            this.mobs = null;
            this.killCount.clear();
            this.killCount = null;
            this.dced.clear();
            this.dced = null;
            this.timeStarted = 0L;
            this.eventTime = 0L;
            this.props.clear();
            this.props = null;
            for (int i = 0; i < this.mapIds.size(); ++i) {
                if (this.isInstanced.get(i)) {
                    this.getMapFactory().removeInstanceMap(this.mapIds.get(i));
                }
            }
            this.mapIds.clear();
            this.mapIds = null;
            this.isInstanced.clear();
            this.isInstanced = null;
            this.em.disposeInstance(this.name);
        }
        catch (Exception e) {
            System.out.println("Caused by : " + emN + " instance name: " + this.name + " method: dispose: " + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + emN + ", Instance name : " + this.name + ", method Name : dispose:\n" + e);
        }
    }
    
    public void dispose() {
        this.wL.lock();
        try {
            this.dispose_NoLock();
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public List<MapleMonster> getMobs() {
        return this.mobs;
    }
    
    public final void broadcastPlayerMsg(final int type, final String msg) {
        if (this.disposed) {
            return;
        }
        for (final MapleCharacter chr : this.getPlayers()) {
            chr.getClient().getSession().write(MaplePacketCreator.serverNotice(type, msg));
        }
    }
    
    public final MapleMap createInstanceMap(final int mapid) {
        if (this.disposed) {
            return null;
        }
        final int assignedid = this.getChannelServer().getEventSM().getNewInstanceMapId();
        this.mapIds.add(assignedid);
        this.isInstanced.add(true);
        return this.getMapFactory().CreateInstanceMap(mapid, true, true, true, assignedid);
    }
    
    public final MapleMap createInstanceMapS(final int mapid) {
        if (this.disposed) {
            return null;
        }
        final int assignedid = this.getChannelServer().getEventSM().getNewInstanceMapId();
        this.mapIds.add(assignedid);
        this.isInstanced.add(true);
        return this.getMapFactory().CreateInstanceMap(mapid, false, false, false, assignedid);
    }
    
    public final MapleMap setInstanceMap(final int mapid) {
        if (this.disposed) {
            return this.getMapFactory().getMap(mapid);
        }
        this.mapIds.add(mapid);
        this.isInstanced.add(false);
        return this.getMapFactory().getMap(mapid);
    }
    
    public final MapleMapFactory getMapFactory() {
        return this.getChannelServer().getMapFactory();
    }
    
    public final MapleMap getMapInstance(final int args) {
        if (this.disposed) {
            return null;
        }
        try {
            boolean instanced = false;
            int trueMapID = -1;
            if (args >= this.mapIds.size()) {
                trueMapID = args;
            }
            else {
                trueMapID = this.mapIds.get(args);
                instanced = this.isInstanced.get(args);
            }
            MapleMap map = null;
            if (!instanced) {
                map = this.getMapFactory().getMap(trueMapID);
                if (map == null) {
                    return null;
                }
                if (map.getCharactersSize() == 0 && this.em.getProperty("shuffleReactors") != null && this.em.getProperty("shuffleReactors").equals("true")) {
                    map.shuffleReactors();
                }
            }
            else {
                map = this.getMapFactory().getInstanceMap(trueMapID);
                if (map == null) {
                    return null;
                }
                if (map.getCharactersSize() == 0 && this.em.getProperty("shuffleReactors") != null && this.em.getProperty("shuffleReactors").equals("true")) {
                    map.shuffleReactors();
                }
            }
            return map;
        }
        catch (NullPointerException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
            ex.printStackTrace();
            return null;
        }
    }
    
    public final void schedule(final String methodName, final long delay) {
        if (this.disposed) {
            return;
        }
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (EventInstanceManager.this.disposed || EventInstanceManager.this == null || EventInstanceManager.this.em == null) {
                    return;
                }
                try {
                    EventInstanceManager.this.em.getIv().invokeFunction(methodName, EventInstanceManager.this);
                }
                catch (NullPointerException ex2) {}
                catch (Exception ex) {
                    System.out.println("Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                    FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name(schedule) : " + methodName + " :\n" + ex);
                }
            }
        }, delay);
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final void setProperty(final String key, final String value) {
        if (this.disposed) {
            return;
        }
        this.props.setProperty(key, value);
    }
    
    public final Object setProperty(final String key, final String value, final boolean prev) {
        if (this.disposed) {
            return null;
        }
        return this.props.setProperty(key, value);
    }
    
    public final String getProperty(final String key) {
        if (this.disposed) {
            return "";
        }
        return this.props.getProperty(key);
    }
    
    public final Properties getProperties() {
        return this.props;
    }
    
    public final void leftParty(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("leftParty", this, chr);
        }
        catch (Exception ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : leftParty:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : leftParty:\n" + ex);
        }
    }
    
    public final void disbandParty() {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("disbandParty", this);
        }
        catch (Exception ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : disbandParty:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : disbandParty:\n" + ex);
        }
    }
    
    public final void finishPQ() {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("clearPQ", this);
        }
        catch (Exception ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : clearPQ:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : clearPQ:\n" + ex);
        }
    }
    
    public final void removePlayer(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("playerExit", this, chr);
        }
        catch (Exception ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerExit:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerExit:\n" + ex);
        }
    }
    
    public final void registerCarnivalParty(final MapleCharacter leader, final MapleMap map, final byte team) {
        if (this.disposed) {
            return;
        }
        leader.clearCarnivalRequests();
        final List<MapleCharacter> characters = new LinkedList<MapleCharacter>();
        final MapleParty party = leader.getParty();
        if (party == null) {
            return;
        }
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter c = map.getCharacterById(pc.getId());
            if (c != null) {
                characters.add(c);
                this.registerPlayer(c);
                c.resetCP();
            }
        }
        final MapleCarnivalParty carnivalParty = new MapleCarnivalParty(leader, characters, team);
        try {
            this.em.getIv().invokeFunction("registerCarnivalParty", this, carnivalParty);
        }
        catch (ScriptException ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : registerCarnivalParty:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : registerCarnivalParty:\n" + ex);
        }
        catch (NoSuchMethodException ex2) {}
    }
    
    public void onMapLoad(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("onMapLoad", this, chr);
        }
        catch (ScriptException ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : onMapLoad:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : onMapLoad:\n" + ex);
        }
        catch (NoSuchMethodException ex2) {}
    }
    
    public boolean isLeader(final MapleCharacter chr) {
        return chr != null && chr.getParty() != null && chr.getParty().getLeader().getId() == chr.getId();
    }
    
    public void registerSquad(final MapleSquad squad, final MapleMap map, final int questID) {
        if (this.disposed) {
            return;
        }
        final int mapid = map.getId();
        for (final String chr : squad.getMembers()) {
            final MapleCharacter player = squad.getChar(chr);
            if (player != null && player.getMapId() == mapid) {
                if (questID > 0) {
                    player.getQuestNAdd(MapleQuest.getInstance(questID)).setCustomData(String.valueOf(System.currentTimeMillis()));
                }
                this.registerPlayer(player);
            }
        }
        squad.setStatus((byte)2);
    }
    
    public boolean isDisconnected(final MapleCharacter chr) {
        return !this.disposed && this.dced.contains(chr.getId());
    }
    
    public void removeDisconnected(final int id) {
        if (this.disposed) {
            return;
        }
        this.dced.remove(id);
    }
    
    public EventManager getEventManager() {
        return this.em;
    }
    
    public void applyBuff(final MapleCharacter chr, final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(chr);
        chr.getClient().getSession().write(UIPacket.getStatusMsg(id));
    }
    
    public boolean check() {
        for (final MapleCharacter chr : this.getPlayers()) {
            if (chr.getLevel() < 30 || chr.getLevel() > 50) {
                return false;
            }
        }
        return true;
    }
    
    public boolean check1() {
        for (final MapleCharacter chr : this.getPlayers()) {
            if (chr.getLevel() < 51 || chr.getLevel() > 120) {
                return false;
            }
        }
        return true;
    }
}
