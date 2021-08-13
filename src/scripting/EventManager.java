package scripting;

import client.MapleCharacter;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptException;
import server.MapleSquad;
import server.Randomizer;
import server.Timer;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.OverrideMonsterStats;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;

public class EventManager
{
    private static int[] eventChannel;
    private Invocable iv;
    private int channel;
    private Map<String, EventInstanceManager> instances;
    private Properties props;
    private String name;
    
    public EventManager(final ChannelServer cserv, final Invocable iv, final String name) {
        this.instances = new WeakHashMap<String, EventInstanceManager>();
        this.props = new Properties();
        this.iv = iv;
        this.channel = cserv.getChannel();
        this.name = name;
    }
    
    public void cancel() {
        try {
            this.iv.invokeFunction("cancelSchedule", null);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : cancelSchedule:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : cancelSchedule:\n" + ex);
        }
    }
    
    public ScheduledFuture<?> schedule(final String methodName, final long delay) {
        return Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    EventManager.this.iv.invokeFunction(methodName, null);
                }
                catch (Exception ex) {
                    System.out.println("Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                    FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, delay);
    }
    
    public ScheduledFuture<?> schedule(final String methodName, final long delay, final EventInstanceManager eim) {
        return Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    EventManager.this.iv.invokeFunction(methodName, eim);
                }
                catch (Exception ex) {
                    System.out.println("Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                    FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, delay);
    }
    
    public ScheduledFuture<?> schedule(final String methodName, final EventInstanceManager eim, final long delay) {
        return Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    EventManager.this.iv.invokeFunction(methodName, eim);
                }
                catch (Exception ex) {
                    System.out.println("Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                    FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                }
            }
        }, delay);
    }
    
    public ScheduledFuture<?> scheduleAtTimestamp(final String methodName, final long timestamp) {
        return Timer.EventTimer.getInstance().scheduleAtTimestamp(new Runnable() {
            @Override
            public void run() {
                try {
                    EventManager.this.iv.invokeFunction(methodName, null);
                }
                catch (ScriptException ex) {
                    System.out.println("Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex);
                }
                catch (NoSuchMethodException ex2) {
                    System.out.println("Event name : " + EventManager.this.name + ", method Name : " + methodName + ":\n" + ex2);
                }
            }
        }, timestamp);
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public EventInstanceManager getInstance(final String name) {
        return this.instances.get(name);
    }
    
    public Collection<EventInstanceManager> getInstances() {
        return Collections.unmodifiableCollection((Collection<? extends EventInstanceManager>)this.instances.values());
    }
    
    public EventInstanceManager newInstance(final String name) {
        final EventInstanceManager ret = new EventInstanceManager(this, name, this.channel);
        this.instances.put(name, ret);
        return ret;
    }
    
    public void disposeInstance(final String name) {
        this.instances.remove(name);
        if (this.getProperty("state") != null && this.instances.size() == 0) {
            this.setProperty("state", "0");
        }
        if (this.getProperty("leader") != null && this.instances.size() == 0 && this.getProperty("leader").equals("false")) {
            this.setProperty("leader", "true");
        }
        if (this.name.equals("CWKPQ")) {
            final MapleSquad squad = ChannelServer.getInstance(this.channel).getMapleSquad("CWKPQ");
            if (squad != null) {
                squad.clear();
            }
        }
    }
    
    public Invocable getIv() {
        return this.iv;
    }
    
    public void setProperty(final String key, final String value) {
        this.props.setProperty(key, value);
    }
    
    public String getProperty(final String key) {
        return this.props.getProperty(key);
    }
    
    public final Properties getProperties() {
        return this.props;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void startInstance() {
        try {
            this.iv.invokeFunction("setup", null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup:\n" + ex);
        }
    }
    
    public void startInstance(final String mapid, final MapleCharacter chr) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", mapid);
            eim.registerCarnivalParty(chr, chr.getMap(), (byte)0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup:\n" + ex);
        }
    }
    
    public void startInstance_Party(final String mapid, final MapleCharacter chr) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", mapid);
            eim.registerParty(chr.getParty(), chr.getMap());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup:\n" + ex);
        }
    }
    
    public void startInstance(final MapleCharacter character, final String leader) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", null);
            eim.registerPlayer(character);
            eim.setProperty("leader", leader);
            eim.setProperty("guildid", String.valueOf(character.getGuildId()));
            this.setProperty("guildid", String.valueOf(character.getGuildId()));
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-Guild:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-Guild:\n" + ex);
        }
    }
    
    public void startInstance_CharID(final MapleCharacter character) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", character.getId());
            eim.registerPlayer(character);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-CharID:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-CharID:\n" + ex);
        }
    }
    
    public void startInstance(final MapleCharacter character) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", null);
            eim.registerPlayer(character);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-character:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-character:\n" + ex);
        }
    }
    
    public void startInstance(final MapleParty party, final MapleMap map) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", party.getId());
            eim.registerParty(party, map);
        }
        catch (ScriptException ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-partyid:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-partyid:\n" + ex);
        }
        catch (Exception ex2) {
            this.startInstance_NoID(party, map, ex2);
        }
    }
    
    public void startInstance_NoID(final MapleParty party, final MapleMap map) {
        this.startInstance_NoID(party, map, null);
    }
    
    public void startInstance_NoID(final MapleParty party, final MapleMap map, final Exception old) {
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", null);
            eim.registerParty(party, map);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-party:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-party:\n" + ex + "\n" + ((old == null) ? "no old exception" : old));
        }
    }
    
    public void startInstance(final EventInstanceManager eim, final String leader) {
        try {
            this.iv.invokeFunction("setup", eim);
            eim.setProperty("leader", leader);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-leader:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-leader:\n" + ex);
        }
    }
    
    public void startInstance(final MapleSquad squad, final MapleMap map) {
        this.startInstance(squad, map, -1);
    }
    
    public void startInstance(final MapleSquad squad, final MapleMap map, final int questID) {
        if (squad.getStatus() == 0) {
            return;
        }
        if (!squad.getLeader().isGM()) {
            if (squad.getMembers().size() < squad.getType().i) {
                squad.getLeader().dropMessage(5, "这个远征队至少要有 " + squad.getType().i + " 人以上才可以开战.");
                return;
            }
            if (this.name.equals("CWKPQ") && squad.getJobs().size() < 5) {
                squad.getLeader().dropMessage(5, "The squad requires members from every type of job.");
                return;
            }
        }
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", squad.getLeaderName());
            eim.registerSquad(squad, map, questID);
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-squad:\n" + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Event name : " + this.name + ", method Name : setup-squad:\n" + ex);
        }
    }
    
    public void startInstance(final MapleSquad squad, final MapleMap map, final String bossid) {
        if (squad.getStatus() == 0) {
            return;
        }
        if (!squad.getLeader().isGM()) {
            final int mapid = map.getId();
            int chrSize = 0;
            for (final String chr : squad.getMembers()) {
                final MapleCharacter player = squad.getChar(chr);
                if (player != null && player.getMapId() == mapid) {
                    ++chrSize;
                }
            }
            if (chrSize < squad.getType().i) {
                squad.getLeader().dropMessage(5, "远征队中人员少于 " + squad.getType().i + " 人，无法开始远征任务。注意必须队伍中的角色在线且在同一地图。当前人数: " + chrSize);
                return;
            }
            if (this.name.equals("CWKPQ") && squad.getJobs().size() < 5) {
                squad.getLeader().dropMessage(5, "远征队中成员职业的类型小于5种，无法开始远征任务。");
                return;
            }
        }
        try {
            final EventInstanceManager eim = (EventInstanceManager)this.iv.invokeFunction("setup", squad.getLeaderName());
            eim.registerSquad(squad, map, Integer.parseInt(bossid));
        }
        catch (Exception ex) {
            System.out.println("Event name : " + this.name + ", method Name : setup-squad:\n" + ex);
            FileoutputUtil.log("log/Script_Except.log", "Event name : " + this.name + ", method Name : setup-squad:\n" + ex);
        }
    }
    
    public void warpAllPlayer(final int from, final int to) {
        final MapleMap tomap = this.getMapFactory().getMap(to);
        final MapleMap frommap = this.getMapFactory().getMap(from);
        final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
        if (tomap != null && frommap != null && list != null && frommap.getCharactersSize() > 0) {
            for (final MapleMapObject mmo : list) {
                ((MapleCharacter)mmo).changeMap(tomap, tomap.getPortal(0));
            }
        }
    }
    
    public void setAllPlayerBossLog(final int map, final String bosslog, final int type) {
        final MapleMap frommap = this.getMapFactory().getMap(map);
        final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
        if (frommap != null && list != null && frommap.getCharactersSize() > 0) {
            for (final MapleMapObject mmo : list) {
                ((MapleCharacter)mmo).setBossLog(bosslog, type);
            }
        }
    }
    
    public int online() {
        final Connection con = DatabaseConnection.getConnection();
        int count = 0;
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT count(*) as cc FROM accounts WHERE loggedin = 2");
            final ResultSet re = ps.executeQuery();
            while (re.next()) {
                count = re.getInt("cc");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(EventInstanceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    public MapleMapFactory getMapFactory() {
        return this.getChannelServer().getMapFactory();
    }
    
    public OverrideMonsterStats newMonsterStats() {
        return new OverrideMonsterStats();
    }
    
    public List<MapleCharacter> newCharList() {
        return new ArrayList<MapleCharacter>();
    }
    
    public MapleMonster getMonster(final int id) {
        return MapleLifeFactory.getMonster(id);
    }
    
    public void broadcastShip(final int mapid, final int effect) {
        this.getMapFactory().getMap(mapid).broadcastMessage(MaplePacketCreator.boatPacket(effect));
    }
    
    public void broadcastChangeMusic(final int mapid) {
        this.getMapFactory().getMap(mapid).broadcastMessage(MaplePacketCreator.musicChange("Bgm04/ArabPirate"));
    }
    
    public void broadcastYellowMsg(final String msg) {
    }
    
    public void broadcastServerMsg(final int type, final String msg, final boolean weather) {
        if (!weather) {
            this.getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(type, msg));
        }
        else {
            for (final MapleMap load : this.getMapFactory().getAllMaps()) {
                if (load.getCharactersSize() > 0) {
                    load.startMapEffect(msg, type);
                }
            }
        }
    }
    
    public boolean scheduleRandomEvent() {
        boolean omg = false;
        for (int i = 0; i < EventManager.eventChannel.length; ++i) {
            omg |= this.scheduleRandomEventInChannel(EventManager.eventChannel[i]);
        }
        return omg;
    }
    
    public boolean scheduleRandomEventInChannel(final int chz) {
        final ChannelServer cs = ChannelServer.getInstance(chz);
        if (cs == null || cs.getEvent() > -1) {
            return false;
        }
        MapleEventType t;
        MapleEventType x = null;
        for (t = null; t == null; t = x) {
            final MapleEventType[] values = MapleEventType.values();
            for (int length = values.length, i = 0; i < length; ++i) {
                x = values[i];
                if (Randomizer.nextInt(MapleEventType.values().length) == 0) {
                    break;
                }
            }
        }
        final String msg = MapleEvent.scheduleEvent(t, cs);
        if (msg.length() > 0) {
            this.broadcastYellowMsg(msg);
            return false;
        }
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (cs.getEvent() >= 0) {
                    MapleEvent.setEvent(cs, true);
                }
            }
        }, 180000L);
        return true;
    }
    
    public void setWorldEvent() {
        for (int i = 0; i < EventManager.eventChannel.length; ++i) {
            EventManager.eventChannel[i] = Randomizer.nextInt(ChannelServer.getAllInstances().size()) + i;
        }
    }
    
    static {
        EventManager.eventChannel = new int[2];
    }
}
