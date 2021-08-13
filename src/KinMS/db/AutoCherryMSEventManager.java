package KinMS.db;

import handling.channel.ChannelServer;
import server.maps.MapleMapFactory;

public class AutoCherryMSEventManager implements Runnable
{
    private static AutoCherryMSEventManager instance;
    private ChannelServer cserv;
    private MapleMapFactory mapFactory;
    
    private AutoCherryMSEventManager() {
    }
    
    private AutoCherryMSEventManager(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        this.cserv = cserv;
        this.mapFactory = mapFactory;
    }
    
    public static AutoCherryMSEventManager newInstance() {
        return new AutoCherryMSEventManager();
    }
    
    public static AutoCherryMSEventManager newInstance(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        return AutoCherryMSEventManager.instance = new AutoCherryMSEventManager(cserv, mapFactory);
    }
    
    public static AutoCherryMSEventManager getInstance() {
        if (AutoCherryMSEventManager.instance == null) {
            AutoCherryMSEventManager.instance = new AutoCherryMSEventManager();
        }
        return AutoCherryMSEventManager.instance;
    }
    
    public static AutoCherryMSEventManager getInstance(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        if (AutoCherryMSEventManager.instance == null) {
            AutoCherryMSEventManager.instance = new AutoCherryMSEventManager(cserv, mapFactory);
        }
        return AutoCherryMSEventManager.instance;
    }
    
    public ChannelServer getChannelServer() {
        return this.cserv;
    }
    
    public MapleMapFactory getMapleMapFactory() {
        return this.mapFactory;
    }
    
    @Override
    public void run() {
        CherryMScustomEventFactory.getInstance().getCherryMSLottery(this.cserv, this.mapFactory).doLottery();
    }
    
    static {
        AutoCherryMSEventManager.instance = null;
    }
}
