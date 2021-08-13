package handling.login;

import client.MapleClient;
import handling.channel.ChannelServer;
import java.util.Map;
import server.Timer;
import tools.MaplePacketCreator;
import tools.packet.LoginPacket;

public class LoginWorker
{
    private static long lastUpdate;
    
    public static void registerClient(final MapleClient c) {
        if (LoginServer.isAdminOnly() && !c.isGm()) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "管管已设置仅管理员登录。\r\n我们目前正在修复几个问题，\r\n请耐心等待"));
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }
        if (System.currentTimeMillis() - LoginWorker.lastUpdate > 600000L) {
            LoginWorker.lastUpdate = System.currentTimeMillis();
            final Map<Integer, Integer> load = ChannelServer.getChannelLoad();
            int usersOn = 0;
            if (load == null || load.size() <= 0) {
                LoginWorker.lastUpdate = 0L;
                c.getSession().write(LoginPacket.getLoginFailed(7));
                return;
            }
            final double loadFactor = 1200.0 / (LoginServer.getUserLimit() / (double)load.size());
            for (final Map.Entry<Integer, Integer> entry : load.entrySet()) {
                usersOn += entry.getValue();
                load.put(entry.getKey(), Math.min(1200, (int)(entry.getValue() * loadFactor)));
            }
            LoginServer.setLoad(load, usersOn);
            LoginWorker.lastUpdate = System.currentTimeMillis();
        }
        if (c.finishLogin() == 0) {
            if (c.getGender() == 10) {
                c.getSession().write(LoginPacket.getGenderNeeded(c));
            }
            else {
                c.getSession().write(LoginPacket.getAuthSuccessRequest(c));
                c.getSession().write(LoginPacket.getServerList(0, LoginServer.getServerName(), LoginServer.getLoad()));
                c.getSession().write(LoginPacket.getEndOfServerList());
            }
            c.setIdleTask(Timer.PingTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                }
            }, 6000000L));
        }
        else if (c.getGender() == 10) {
            c.getSession().write(LoginPacket.getGenderNeeded(c));
        }
        else {
            c.getSession().write(LoginPacket.getAuthSuccessRequest(c));
            c.getSession().write(LoginPacket.getServerList(0, LoginServer.getServerName(), LoginServer.getLoad()));
            c.getSession().write(LoginPacket.getEndOfServerList());
        }
    }
    
    static {
        LoginWorker.lastUpdate = 0L;
    }
}
