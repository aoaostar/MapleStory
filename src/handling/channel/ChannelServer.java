package handling.channel;

import client.MapleCharacter;
import client.MapleClient;
import constants.GameConstants;
import handling.ByteArrayMaplePacket;
import handling.MaplePacket;
import handling.MapleServerHandler;
import handling.cashshop.CashShopServer;
import handling.login.LoginServer;
import handling.mina.MapleCodecFactory;
import handling.world.CheaterData;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.IoBufferAllocator;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import scripting.EventScriptManager;
import server.MapleSquad;
import server.ServerProperties;
import server.Timer;
import server.events.MapleCoconut;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.events.MapleFitness;
import server.events.MapleOla;
import server.events.MapleOxQuiz;
import server.events.MapleSnowball;
import server.life.PlayerNPC;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import server.shops.HiredMerchant;
import tools.CollectionUtil;
import tools.ConcurrentEnumMap;
import tools.MaplePacketCreator;

public class ChannelServer implements Serializable
{
    public static long serverStartTime;
    private static final short DEFAULT_PORT = 2524;
    private static final Map<Integer, ChannelServer> instances;
    private int expRate;
    private int mesoRate;
    private int dropRate;
    private int cashRate;
    private int BossdropRate;
    private int doubleExp;
    private int doubleMeso;
    private int doubleDrop;
    public short port;
    private final int channel;
    private int running_MerchantID;
    private int flags;
    private String serverMessage;
    private String key;
    private String ip;
    private String serverName;
    private boolean shutdown;
    private boolean finishedShutdown;
    private boolean MegaphoneMuteState;
    private boolean adminOnly;
    private PlayerStorage players;
    private MapleServerHandler serverHandler;
    private IoAcceptor acceptor;
    private final MapleMapFactory mapFactory;
    private EventScriptManager eventSM;
    private final Map<MapleSquad.MapleSquadType, MapleSquad> mapleSquads;
    private final Map<Integer, HiredMerchant> merchants;
    private final Map<Integer, PlayerNPC> playerNPCs;
    private ReentrantReadWriteLock merchLock;
    private final ReentrantReadWriteLock squadLock;
    private int eventmap;
    private final Map<MapleEventType, MapleEvent> events;
    private final boolean debugMode = false;
    private int instanceId;
    private boolean warpcsshop;
    private boolean warpmts;
    
    public static Set<Integer> getAllInstance() {
        return new HashSet<Integer>(ChannelServer.instances.keySet());
    }
    
    public static ChannelServer newInstance(final int channel) {
        return new ChannelServer(channel);
    }
    
    public static ChannelServer getInstance(final int channel) {
        return ChannelServer.instances.get(channel);
    }
    
    public static Collection<ChannelServer> getAllInstances() {
        return Collections.unmodifiableCollection((Collection<? extends ChannelServer>)ChannelServer.instances.values());
    }
    
    public static void startChannel_Main() {
        ChannelServer.serverStartTime = System.currentTimeMillis();
        int ch = Integer.parseInt(ServerProperties.getProperty("RoyMS.Count", "0"));
        if (ch > 10) {
            ch = 10;
        }
        for (int i = 0; i < ch; ++i) {
            newInstance(i + 1).run_startup_configurations();
        }
    }
    
    public static void startChannel(final int channel) {
        ChannelServer.serverStartTime = System.currentTimeMillis();
        for (int i = 0; i < Integer.parseInt(ServerProperties.getProperty("RoyMS.Count", "0")); ++i) {
            if (channel == i + 1) {
                newInstance(i + 1).run_startup_configurations();
                break;
            }
        }
    }
    
    public static Set<Integer> getChannelServer() {
        return new HashSet<Integer>(ChannelServer.instances.keySet());
    }
    
    public static int getChannelCount() {
        return ChannelServer.instances.size();
    }
    
    public static Map<Integer, Integer> getChannelLoad() {
        final Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        for (final ChannelServer cs : ChannelServer.instances.values()) {
            ret.put(cs.getChannel(), cs.getConnectedClients());
        }
        return ret;
    }
    
    public static boolean forceRemovePlayerByCharName(final String Name) {
        for (final ChannelServer ch : getAllInstances()) {
            Collection<MapleCharacter> chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter c : chrs) {
                if (c.getName().equalsIgnoreCase(Name)) {
                    try {
                        if (c.getMap() != null) {
                            c.getMap().removePlayer(c);
                        }
                        if (c.getClient() != null) {
                            c.getClient().disconnect(true, false, false);
                            c.getClient().getSession().close(true);
                        }
                    }
                    catch (Exception ex) {}
                    chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
                    if (chrs.contains(c)) {
                        ch.removePlayer(c);
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }
    
    public static void forceRemovePlayerByAccId(final MapleClient c, final int accid) {
        for (final ChannelServer ch : getAllInstances()) {
            Collection<MapleCharacter> chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter chr : chrs) {
                if (chr.getAccountID() == accid) {
                    try {
                        if (chr.getClient() != null && chr.getClient() != c) {
                            chr.getClient().disconnect(true, false, false);
                        }
                    }
                    catch (Exception ex) {}
                    chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
                    if (chr.getClient() == c) {
                        continue;
                    }
                    if (chrs.contains(chr)) {
                        ch.removePlayer(chr);
                    }
                    if (chr.getMap() == null) {
                        continue;
                    }
                    chr.getMap().removePlayer(chr);
                }
            }
        }
        try {
            final Collection<MapleCharacter> chrs2 = CashShopServer.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter chr2 : chrs2) {
                if (chr2.getAccountID() == accid) {
                    try {
                        if (chr2.getClient() == null || chr2.getClient() == c) {
                            continue;
                        }
                        chr2.getClient().disconnect(true, false, false);
                    }
                    catch (Exception ex2) {}
                }
            }
        }
        catch (Exception ex3) {}
    }
    
    public static void forceRemovePlayerByAccId(final int accid) {
        for (final ChannelServer ch : getAllInstances()) {
            Collection<MapleCharacter> chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter c : chrs) {
                if (c.getAccountID() == accid) {
                    try {
                        if (c.getClient() != null) {
                            c.getClient().disconnect(true, false, false);
                        }
                    }
                    catch (Exception ex) {}
                    chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
                    if (chrs.contains(c)) {
                        ch.removePlayer(c);
                    }
                    if (c.getMap() == null) {
                        continue;
                    }
                    c.getMap().removePlayer(c);
                }
            }
        }
    }
    
    private ChannelServer(final int channel) {
        this.BossdropRate = 1;
        this.doubleExp = 1;
        this.doubleMeso = 1;
        this.doubleDrop = 1;
        this.port = 2524;
        this.running_MerchantID = 0;
        this.flags = 0;
        this.shutdown = false;
        this.finishedShutdown = false;
        this.MegaphoneMuteState = false;
        this.adminOnly = false;
        this.mapleSquads = new ConcurrentEnumMap<MapleSquad.MapleSquadType, MapleSquad>(MapleSquad.MapleSquadType.class);
        this.merchants = new HashMap<Integer, HiredMerchant>();
        this.playerNPCs = new HashMap<Integer, PlayerNPC>();
        this.merchLock = new ReentrantReadWriteLock();
        this.squadLock = new ReentrantReadWriteLock();
        this.eventmap = -1;
        this.events = new EnumMap<MapleEventType, MapleEvent>(MapleEventType.class);
        this.instanceId = 0;
        this.channel = channel;
        this.mapFactory = new MapleMapFactory(channel);
        this.merchLock = new ReentrantReadWriteLock(true);
    }
    
    public void loadEvents() {
        if (this.events.size() != 0) {
            return;
        }
        this.events.put(MapleEventType.打椰子比赛, new MapleCoconut(this.channel, MapleEventType.打椰子比赛.mapids));
        this.events.put(MapleEventType.打瓶盖比赛, new MapleCoconut(this.channel, MapleEventType.打瓶盖比赛.mapids));
        this.events.put(MapleEventType.向高地, new MapleFitness(this.channel, MapleEventType.向高地.mapids));
        this.events.put(MapleEventType.上楼上楼, new MapleOla(this.channel, MapleEventType.上楼上楼.mapids));
        this.events.put(MapleEventType.快速0X猜题, new MapleOxQuiz(this.channel, MapleEventType.快速0X猜题.mapids));
        this.events.put(MapleEventType.雪球赛, new MapleSnowball(this.channel, MapleEventType.雪球赛.mapids));
    }
    
    public void run_startup_configurations() {
        this.setChannel(this.channel);
        try {
            this.expRate = Integer.parseInt(ServerProperties.getProperty("RoyMS.Exp"));
            this.mesoRate = Integer.parseInt(ServerProperties.getProperty("RoyMS.Meso"));
            this.dropRate = Integer.parseInt(ServerProperties.getProperty("RoyMS.Drop"));
            this.BossdropRate = Integer.parseInt(ServerProperties.getProperty("RoyMS.BDrop"));
            this.cashRate = Integer.parseInt(ServerProperties.getProperty("RoyMS.Cash"));
            this.serverMessage = ServerProperties.getProperty("RoyMS.EventMessage");
            this.serverName = ServerProperties.getProperty("RoyMS.ServerName");
            this.flags = Integer.parseInt(ServerProperties.getProperty("RoyMS.WFlags", "0"));
            this.adminOnly = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.Admin", "false"));
            this.eventSM = new EventScriptManager(this, ServerProperties.getProperty("RoyMS.Events").split(","));
            this.port = Short.parseShort(ServerProperties.getProperty("RoyMS.Port" + this.channel, String.valueOf(2524 + this.channel)));
            this.warpcsshop = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.warpcsshop", "false"));
            this.warpmts = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.warpmts", "false"));
            this.ip = ServerProperties.getProperty("RoyMS.IP") +":"+ this.port;
//            this.ip = "49.235.142.128:"+ this.port;
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
//        switch (GameConstants.game) {
//            case 0: {
//                this.ip = "127.0.0.1:" + this.port;
//                break;
//            }
//            default: {
//                this.ip = "127.0.0.1:" + this.port;
//                break;
//            }
//        }
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());
        this.acceptor = new NioSocketAcceptor();
        this.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));
        this.players = new PlayerStorage(this.channel);
        this.loadEvents();
        final Timer tMan = Timer.TimerManager.getInstance();
        try {
            this.acceptor.setHandler(new MapleServerHandler(this.channel, false));
            this.acceptor.bind(new InetSocketAddress(this.port));
            ((SocketSessionConfig)this.acceptor.getSessionConfig()).setTcpNoDelay(true);
            System.out.println("频道 " + this.channel + ": 启动端口 " + this.port + ": 服务器IP " + this.ip + "");
            this.eventSM.init();
        }
        catch (IOException e2) {
            System.out.println("Binding to port " + this.port + " failed (ch: " + this.getChannel() + ")" + e2);
        }
    }
    
    public void shutdown(final Object threadToNotify) {
        if (this.finishedShutdown) {
            return;
        }
        this.broadcastPacket(MaplePacketCreator.serverNotice(0, "这个频道正在关闭中."));
        this.shutdown = true;
        System.out.println("频道 " + this.channel + " 正在清理活动脚本...");
        this.eventSM.cancel();
        System.out.println("频道 " + this.channel + ", 正在保存所有角色数据...");
        System.out.println("频道 " + this.channel + ", Saving characters...");
        System.out.println("频道 " + this.channel + ", 解除绑定端口...");
        ChannelServer.instances.remove(this.channel);
        LoginServer.removeChannel(this.channel);
        this.setFinishShutdown();
    }
    
    public boolean hasFinishedShutdown() {
        return this.finishedShutdown;
    }
    
    public MapleMapFactory getMapFactory() {
        return this.mapFactory;
    }
    
    public void addPlayer(final MapleCharacter chr) {
        this.getPlayerStorage().registerPlayer(chr);
        chr.getClient().getSession().write(MaplePacketCreator.serverMessage(this.serverMessage));
    }
    
    public PlayerStorage getPlayerStorage() {
        if (this.players == null) {
            this.players = new PlayerStorage(this.channel);
        }
        return this.players;
    }
    
    public void removePlayer(final MapleCharacter chr) {
        this.getPlayerStorage().deregisterPlayer(chr);
    }
    
    public void removePlayer(final int idz, final String namez) {
        this.getPlayerStorage().deregisterPlayer(idz, namez);
    }
    
    public String getServerMessage() {
        return this.serverMessage;
    }
    
    public void setServerMessage(final String newMessage) {
        this.serverMessage = newMessage;
        this.broadcastPacket(MaplePacketCreator.serverMessage(this.serverMessage));
    }
    
    public void broadcastPacket(final MaplePacket data) {
        this.getPlayerStorage().broadcastPacket(data);
    }
    
    public void broadcastSmegaPacket(final MaplePacket data) {
        this.getPlayerStorage().broadcastSmegaPacket(data);
    }
    
    public void broadcastGMPacket(final MaplePacket data) {
        this.getPlayerStorage().broadcastGMPacket(data);
    }
    
    public int getExpRate() {
        return this.expRate * this.doubleExp;
    }
    
    public void setExpRate(final int expRate) {
        this.expRate = expRate;
    }
    
    public int getCashRate() {
        return this.cashRate;
    }
    
    public void setCashRate(final int cashRate) {
        this.cashRate = cashRate;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public void setChannel(final int channel) {
        ChannelServer.instances.put(channel, this);
        LoginServer.addChannel(channel);
    }
    
    public String getSocket() {
        return this.ip;
    }
    
    public String getIP() {
        return this.ip;
    }
    
    public String getIPA() {
        return this.ip;
    }
    
    public boolean isShutdown() {
        return this.shutdown;
    }
    
    public int getLoadedMaps() {
        return this.mapFactory.getLoadedMaps();
    }
    
    public EventScriptManager getEventSM() {
        return this.eventSM;
    }
    
    public void reloadEvents() {
        this.eventSM.cancel();
        (this.eventSM = new EventScriptManager(this, ServerProperties.getProperty("RoyMS.Events").split(","))).init();
    }
    
    public int getBossDropRate() {
        return this.BossdropRate;
    }
    
    public void setBossDropRate(final int dropRate) {
        this.BossdropRate = dropRate;
    }
    
    public int getMesoRate() {
        return this.mesoRate * this.doubleMeso;
    }
    
    public void setMesoRate(final int mesoRate) {
        this.mesoRate = mesoRate;
    }
    
    public int getDropRate() {
        return this.dropRate * this.doubleDrop;
    }
    
    public void setDropRate(final int dropRate) {
        this.dropRate = dropRate;
    }
    
    public int getDoubleExp() {
        if (this.doubleExp < 0 || this.doubleExp > 2) {
            return 1;
        }
        return this.doubleExp;
    }
    
    public void setDoubleExp(final int doubleExp) {
        if (doubleExp < 0 || doubleExp > 2) {
            this.doubleExp = 1;
        }
        else {
            this.doubleExp = doubleExp;
        }
    }
    
    public int getDoubleMeso() {
        if (this.doubleMeso < 0 || this.doubleMeso > 2) {
            return 1;
        }
        return this.doubleMeso;
    }
    
    public void setDoubleMeso(final int doubleMeso) {
        if (doubleMeso < 0 || doubleMeso > 2) {
            this.doubleMeso = 1;
        }
        else {
            this.doubleMeso = doubleMeso;
        }
    }
    
    public int getDoubleDrop() {
        if (this.doubleDrop < 0 || this.doubleDrop > 2) {
            return 1;
        }
        return this.doubleDrop;
    }
    
    public void setDoubleDrop(final int doubleDrop) {
        if (doubleDrop < 0 || doubleDrop > 2) {
            this.doubleDrop = 1;
        }
        else {
            this.doubleDrop = doubleDrop;
        }
    }
    
    public Map<MapleSquad.MapleSquadType, MapleSquad> getAllSquads() {
        return Collections.unmodifiableMap((Map<? extends MapleSquad.MapleSquadType, ? extends MapleSquad>)this.mapleSquads);
    }
    
    public MapleSquad getMapleSquad(final String type) {
        return this.getMapleSquad(MapleSquad.MapleSquadType.valueOf(type.toLowerCase()));
    }
    
    public MapleSquad getMapleSquad(final MapleSquad.MapleSquadType type) {
        return this.mapleSquads.get(type);
    }
    
    public boolean addMapleSquad(final MapleSquad squad, final String type) {
        final MapleSquad.MapleSquadType types = MapleSquad.MapleSquadType.valueOf(type.toLowerCase());
        if (types != null && !this.mapleSquads.containsKey(types)) {
            this.mapleSquads.put(types, squad);
            squad.scheduleRemoval();
            return true;
        }
        return false;
    }
    
    public boolean removeMapleSquad(final MapleSquad squad, final MapleSquad.MapleSquadType type) {
        if (type != null && this.mapleSquads.containsKey(type) && this.mapleSquads.get(type) == squad) {
            this.mapleSquads.remove(type);
            return true;
        }
        return false;
    }
    
    public boolean removeMapleSquad(final MapleSquad.MapleSquadType types) {
        if (types != null && this.mapleSquads.containsKey(types)) {
            this.mapleSquads.remove(types);
            return true;
        }
        return false;
    }

    public int closeAllMerchant() {
        int ret = 0;
        this.merchLock.writeLock().lock();
        try {
            Iterator<Map.Entry<Integer, HiredMerchant>> merchants_ = this.merchants.entrySet().iterator();
            while (merchants_.hasNext()) {
                HiredMerchant hm = (HiredMerchant)((Map.Entry)merchants_.next()).getValue();
                hm.closeShop(true, false);
                hm.getMap().removeMapObject(hm);
                merchants_.remove();
                ret++;
            }
        } finally {
            this.merchLock.writeLock().unlock();
        }
        return ret;
    }
    
    public int addMerchant(final HiredMerchant hMerchant) {
        this.merchLock.writeLock().lock();
        int runningmer = 0;
        try {
            runningmer = this.running_MerchantID;
            this.merchants.put(this.running_MerchantID, hMerchant);
            ++this.running_MerchantID;
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
        return runningmer;
    }
    
    public void removeMerchant(final HiredMerchant hMerchant) {
        this.merchLock.writeLock().lock();
        try {
            this.merchants.remove(hMerchant.getStoreId());
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
    }

    public boolean containsMerchant(int accid) {
        boolean contains = false;
        this.merchLock.readLock().lock();
        try {
            Iterator<HiredMerchant> itr = this.merchants.values().iterator();
            while (itr.hasNext()) {
                if ((itr.next()).getOwnerAccId() == accid) {
                    contains = true;
                    break;
                }
            }
        } finally {
            this.merchLock.readLock().unlock();
        }
        return contains;
    }

    public List<HiredMerchant> searchMerchant(int itemSearch) {
        List<HiredMerchant> list = new LinkedList<>();
        this.merchLock.readLock().lock();
        try {
            Iterator<HiredMerchant> itr = this.merchants.values().iterator();
            while (itr.hasNext()) {
                HiredMerchant hm = itr.next();
                if (hm.searchItem(itemSearch).size() > 0)
                    list.add(hm);
            }
        } finally {
            this.merchLock.readLock().unlock();
        }
        return list;
    }
    
    public void toggleMegaphoneMuteState() {
        this.MegaphoneMuteState = !this.MegaphoneMuteState;
    }
    
    public boolean getMegaphoneMuteState() {
        return this.MegaphoneMuteState;
    }
    
    public int getEvent() {
        return this.eventmap;
    }
    
    public void setEvent(final int ze) {
        this.eventmap = ze;
    }
    
    public MapleEvent getEvent(final MapleEventType t) {
        return this.events.get(t);
    }
    
    public Collection<PlayerNPC> getAllPlayerNPC() {
        return this.playerNPCs.values();
    }
    
    public PlayerNPC getPlayerNPC(final int id) {
        return this.playerNPCs.get(id);
    }
    
    public void addPlayerNPC(final PlayerNPC npc) {
        if (this.playerNPCs.containsKey(npc.getId())) {
            this.removePlayerNPC(npc);
        }
        this.playerNPCs.put(npc.getId(), npc);
        this.getMapFactory().getMap(npc.getMapId()).addMapObject(npc);
    }
    
    public void removePlayerNPC(final PlayerNPC npc) {
        if (this.playerNPCs.containsKey(npc.getId())) {
            this.playerNPCs.remove(npc.getId());
            this.getMapFactory().getMap(npc.getMapId()).removeMapObject(npc);
        }
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public void setServerName(final String sn) {
        this.serverName = sn;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setShutdown() {
        this.shutdown = true;
        System.out.println("频道 " + this.channel + " 已开始关闭.");
    }
    
    public void setFinishShutdown() {
        this.finishedShutdown = true;
        System.out.println("频道 " + this.channel + " 已关闭完成.");
    }
    
    public boolean isAdminOnly() {
        return this.adminOnly;
    }
    
    public MapleServerHandler getServerHandler() {
        return this.serverHandler;
    }
    
    public int getTempFlag() {
        return this.flags;
    }
    
    public int getConnectedClients() {
        return this.getPlayerStorage().getConnectedClients();
    }
    
    public List<CheaterData> getCheaters() {
        final List<CheaterData> cheaters = this.getPlayerStorage().getCheaters();
        Collections.sort(cheaters);
        return CollectionUtil.copyFirst(cheaters, 20);
    }
    
    public void broadcastMessage(final byte[] message) {
        this.broadcastPacket(new ByteArrayMaplePacket(message));
    }
    
    public void broadcastMessage(final MaplePacket message) {
        this.broadcastPacket(message);
    }
    
    public void broadcastSmega(final byte[] message) {
        this.broadcastSmegaPacket(new ByteArrayMaplePacket(message));
    }
    
    public void broadcastGMMessage(final byte[] message) {
        this.broadcastGMPacket(new ByteArrayMaplePacket(message));
    }
    
    public void saveAll() {
        int ppl = 0;
        for (final MapleCharacter chr : this.players.getAllCharactersThreadSafe()) {
            if (chr != null) {
                ++ppl;
                chr.saveToDB(false, false);
            }
        }
        System.out.println("[自动存档] 已经将频道 " + this.channel + " 的 " + ppl + " 个玩家保存到数据中.");
    }


    //系统发放奖励
    public void AutoNx(final int dy) {
        this.mapFactory.getMap(741000208).AutoNx(dy);
        this.mapFactory.getMap(910000000).AutoNx(dy);
        this.mapFactory.getMap(209080100).AutoNx(dy);
    }
    
    public void AutoTime(final int dy) {
        try {
            for (final ChannelServer chan : getAllInstances()) {
                for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                    if (chr == null) {
                        continue;
                    }
                    chr.gainGamePoints(1);
                    if (chr.getGamePoints() >= 5) {
                        continue;
                    }
                    chr.resetGamePointsPD();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public int getInstanceId() {
        return this.instanceId;
    }
    
    public void addInstanceId() {
        ++this.instanceId;
    }
    
    public void shutdown() {
        if (this.finishedShutdown) {
            return;
        }
        this.broadcastPacket(MaplePacketCreator.serverNotice(0, "游戏即将关闭维护..."));
        this.shutdown = true;
        System.out.println("频道 " + this.channel + " 正在清理活动脚本...");
        this.eventSM.cancel();
        System.out.println("频道 " + this.channel + " 正在保存所有角色数据...");
        System.out.println("频道 " + this.channel + " 解除绑定端口...");
        this.acceptor.unbind((SocketAddress)new InetSocketAddress(this.port));
        ChannelServer.instances.remove(this.channel);
        this.setFinishShutdown();
    }
    
    public boolean WarpCSShop() {
        return this.warpcsshop;
    }
    
    public boolean WarpMTS() {
        return this.warpmts;
    }

    public void closeAllMerchants() {
        int ret = 0;
        long Start = System.currentTimeMillis();
        this.merchLock.writeLock().lock();
        try {
            Iterator<Map.Entry<Integer, HiredMerchant>> hmit = this.merchants.entrySet().iterator();
            while (hmit.hasNext()) {
                ((HiredMerchant)((Map.Entry)hmit.next()).getValue()).closeShop(true, false);
                hmit.remove();
                ret++;
            }
        } catch (Exception e) {
            System.out.println("关闭雇佣商店出现错误..." + e);
        } finally {
            this.merchLock.writeLock().unlock();
        }
        System.out.println("频道 " + this.channel + " 共保存雇佣商店: " + ret + " | 耗时: " + (System.currentTimeMillis() - Start) + " 毫秒.");
    }
    public boolean isConnected(final String name) {
        return this.getPlayerStorage().getCharacterByName(name) != null;
    }
    
    static {
        instances = new HashMap<Integer, ChannelServer>();
    }
}
