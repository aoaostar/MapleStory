package handling.login;

import handling.MapleServerHandler;
import handling.mina.MapleCodecFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.IoBufferAllocator;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import server.ServerProperties;
import tools.Triple;

public class LoginServer
{
    public static int PORT;
    private static InetSocketAddress InetSocketadd;
    private static IoAcceptor acceptor;
    private static Map<Integer, Integer> load;
    private static String serverName;
    private static String eventMessage;
    private static byte flag;
    public static int maxCharacters;
    public static int userLimit;
    public static int usersOn;
    public static int 个人PK地图;
    public static int 组队PK地图;
    public static int 家族PK地图;
    private static boolean finishedShutdown;
    private static boolean adminOnly;
    private static final HashMap<Integer, Triple<String, String, Integer>> loginAuth;
    private static final HashSet<String> loginIPAuth;
    private static final LoginServer instance;
    
    public static LoginServer getInstance() {
        return LoginServer.instance;
    }
    
    public static void putLoginAuth(final int chrid, final String ip, final String tempIp, final int channel) {
        LoginServer.loginAuth.put(chrid, new Triple<String, String, Integer>(ip, tempIp, channel));
        LoginServer.loginIPAuth.add(ip);
    }
    
    public static Triple<String, String, Integer> getLoginAuth(final int chrid) {
        return LoginServer.loginAuth.remove(chrid);
    }
    
    public static boolean containsIPAuth(final String ip) {
        return LoginServer.loginIPAuth.contains(ip);
    }
    
    public static void removeIPAuth(final String ip) {
        LoginServer.loginIPAuth.remove(ip);
    }
    
    public static void addIPAuth(final String ip) {
        LoginServer.loginIPAuth.add(ip);
    }
    
    public static void addChannel(final int channel) {
        LoginServer.load.put(channel, 0);
    }
    
    public static void removeChannel(final int channel) {
        LoginServer.load.remove(channel);
    }
    
    public static void run_startup_configurations() {
        LoginServer.userLimit = Integer.valueOf(ServerProperties.getProperty("RoyMS.userLimit"));
        LoginServer.serverName = ServerProperties.getProperty("RoyMS.ServerName");
        LoginServer.eventMessage = ServerProperties.getProperty("RoyMS.EventMessage");
        LoginServer.flag = Byte.parseByte(ServerProperties.getProperty("RoyMS.Flag"));
        LoginServer.PORT = Integer.parseInt(ServerProperties.getProperty("RoyMS.LPort"));
        LoginServer.adminOnly = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.Admin", "false"));
        LoginServer.maxCharacters = Integer.parseInt(ServerProperties.getProperty("RoyMS.MaxCharacters"));
        LoginServer.个人PK地图 = Integer.parseInt(ServerProperties.getProperty("RoyMS.personPVP"));
        LoginServer.组队PK地图 = Integer.parseInt(ServerProperties.getProperty("RoyMS.teamPVP"));
        LoginServer.家族PK地图 = Integer.parseInt(ServerProperties.getProperty("RoyMS.familyPVP"));
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());
        LoginServer.acceptor = new NioSocketAcceptor();
        LoginServer.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MapleCodecFactory()));
        LoginServer.acceptor.setHandler(new MapleServerHandler(-1, false));
        ((SocketSessionConfig)LoginServer.acceptor.getSessionConfig()).setTcpNoDelay(true);
        try {
            LoginServer.acceptor.bind(new InetSocketAddress(LoginServer.PORT));
            System.out.println("登录服务器 : 启动端口 " + LoginServer.PORT);
        }
        catch (IOException e) {
            System.err.println("Binding to port " + LoginServer.PORT + " failed" + e);
        }
    }
    
    public static void shutdown() {
        if (LoginServer.finishedShutdown) {
            return;
        }
        System.out.println("正在关闭登录伺服器...");
        LoginServer.finishedShutdown = true;
    }
    
    public static String getServerName() {
        return LoginServer.serverName;
    }
    
    public static String getEventMessage() {
        return LoginServer.eventMessage;
    }
    
    public static byte getFlag() {
        return LoginServer.flag;
    }
    
    public static int getMaxCharacters() {
        return LoginServer.maxCharacters;
    }
    
    public static Map<Integer, Integer> getLoad() {
        return LoginServer.load;
    }
    
    public static void setLoad(final Map<Integer, Integer> load_, final int usersOn_) {
        LoginServer.load = load_;
        LoginServer.usersOn = usersOn_;
    }
    
    public static void setEventMessage(final String newMessage) {
        LoginServer.eventMessage = newMessage;
    }
    
    public static void setFlag(final byte newflag) {
        LoginServer.flag = newflag;
    }
    
    public static int getUserLimit() {
        return LoginServer.userLimit;
    }
    
    public static int getUsersOn() {
        return LoginServer.usersOn;
    }
    
    public static void setUserLimit(final int newLimit) {
        LoginServer.userLimit = newLimit;
    }
    
    public static int getNumberOfSessions() {
        return LoginServer.acceptor.getManagedSessions().size();
    }
    
    public static boolean isAdminOnly() {
        return LoginServer.adminOnly;
    }
    
    public static boolean isShutdown() {
        return LoginServer.finishedShutdown;
    }
    
    public static void setOn() {
        LoginServer.finishedShutdown = false;
    }
    
    public static int 个人PK地图() {
        return LoginServer.个人PK地图;
    }
    
    public static int 组队PK地图() {
        return LoginServer.组队PK地图;
    }
    
    public static int 家族PK地图() {
        return LoginServer.家族PK地图;
    }
    
    public static final void closeConn(final String ip) {
        final int count = 0;
        for (final IoSession ss : LoginServer.acceptor.getManagedSessions().values()) {
            if (ss.getRemoteAddress().toString().split(":")[0].equals(ip)) {
                ss.close(false);
            }
        }
    }
    
    static {
        LoginServer.PORT = 1314;
        LoginServer.load = new HashMap<Integer, Integer>();
        LoginServer.usersOn = 0;
        LoginServer.finishedShutdown = true;
        LoginServer.adminOnly = false;
        loginAuth = new HashMap<Integer, Triple<String, String, Integer>>();
        loginIPAuth = new HashSet<String>();
        instance = new LoginServer();
    }
}
