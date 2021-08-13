package KinMS.db;

import handling.channel.ChannelServer;
import server.maps.MapleMapFactory;

public class CherryMScustomEventFactory
{
    private static CherryMScustomEventFactory instance;
    private static boolean CANLOG;
    
    public boolean isCANLOG() {
        return CherryMScustomEventFactory.CANLOG;
    }
    
    public void setCANLOG(boolean CANLOG) {
        CANLOG = CANLOG;
    }
    
    public static CherryMScustomEventFactory getInstance() {
        if (CherryMScustomEventFactory.instance == null) {
            CherryMScustomEventFactory.instance = new CherryMScustomEventFactory();
        }
        return CherryMScustomEventFactory.instance;
    }
    
    public CherryMSLottery getCherryMSLottery() {
        return CherryMSLotteryImpl.getInstance();
    }
    
    public CherryMSLottery getCherryMSLottery(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        return CherryMSLotteryImpl.getInstance(cserv, mapFactory);
    }
    
    static {
        CherryMScustomEventFactory.instance = null;
    }
}
