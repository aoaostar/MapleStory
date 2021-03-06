package KinMS.db;

import client.MapleCharacter;
import handling.channel.ChannelServer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import server.ServerProperties;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import tools.MaplePacketCreator;

public class CherryMSLotteryImpl implements CherryMSLottery
{
    private static CherryMSLotteryImpl instance;
    private ChannelServer cserv;
    private MapleMapFactory mapFactory;
    public boolean jjc;
    private int zjNum;
    Collection<MapleCharacter> characters;
    private long alltouzhu;
    private long allpeichu;
    
    private CherryMSLotteryImpl() {
        this.characters = new ArrayList<MapleCharacter>();
    }
    
    public static CherryMSLotteryImpl getInstance() {
        if (CherryMSLotteryImpl.instance == null) {
            CherryMSLotteryImpl.instance = new CherryMSLotteryImpl();
        }
        return CherryMSLotteryImpl.instance;
    }
    
    private CherryMSLotteryImpl(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        this.characters = new ArrayList<MapleCharacter>();
        this.cserv = cserv;
        this.mapFactory = mapFactory;
    }
    
    public static CherryMSLotteryImpl getInstance(final ChannelServer cserv, final MapleMapFactory mapFactory) {
        if (CherryMSLotteryImpl.instance == null) {
            CherryMSLotteryImpl.instance = new CherryMSLotteryImpl(cserv, mapFactory);
        }
        return CherryMSLotteryImpl.instance;
    }
    
    @Override
    public ChannelServer getChannelServer() {
        return this.cserv;
    }
    
    @Override
    public MapleMapFactory getMapleMapFactory() {
        return this.mapFactory;
    }
    
    public static int getDatetimemm() {
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("mm");
        final String datetime = sdf.format(date);
        return Integer.parseInt(datetime);
    }
    
    @Override
    public void warp(final int map, final MapleCharacter c) {
        final MapleMap target = this.getWarpMap(map, c);
        c.changeMap(target, target.getPortal(0));
    }
    
    private MapleMap getWarpMap(final int map, final MapleCharacter c) {
        MapleMap target;
        if (c.getEventInstance() == null) {
            target = ChannelServer.getInstance(c.getClient().getChannel()).getMapFactory().getMap(map);
        }
        else {
            target = c.getEventInstance().getMapInstance(map);
        }
        return target;
    }
    
    @Override
    public long getAllpeichu() {
        return this.allpeichu;
    }
    
    @Override
    public void setAllpeichu(final long allpeichu) {
        this.allpeichu = allpeichu;
    }
    
    @Override
    public long getAlltouzhu() {
        return this.alltouzhu;
    }
    
    @Override
    public void setAlltouzhu(final long alltouzhu) {
        this.alltouzhu = alltouzhu;
    }
    
    @Override
    public Collection<MapleCharacter> getCharacters() {
        return this.characters;
    }
    
    @Override
    public void setCharacters(final Collection<MapleCharacter> characters) {
        this.characters = characters;
    }
    
    @Override
    public void addChar(final MapleCharacter chr) {
        this.characters.add(chr);
    }
    
    @Override
    public int getZjNum() {
        return this.zjNum;
    }
    
    @Override
    public void setZjNum(final int zjNum) {
        this.zjNum = zjNum;
    }
    
    @Override
    public void doLottery() {
        this.drawalottery();
    }
    
    @Override
    public int getTouNumbyType(final int type) {
        int count = 0;
        for (final MapleCharacter chr : this.characters) {
            if (chr.getTouzhuType() == type) {
                ++count;
            }
        }
        return count;
    }
    
    @Override
    public void drawalottery() {
        this.zjNum = (int)(Math.random() * 6.0D + 1.0D);
        String zjNames2 = "";
        String zjNames3 = "";
        String zjNames6 = "";
        int toucount2 = 0;
        int toucount3 = 0;
        int toucount6 = 0;
        int zhongcount2 = 0;
        int zhongcount3 = 0;
        int zhongcount6 = 0;
        long sumNX = 0L;
        long peiNX = 0L;
        int zjpeople = 0;
        Collection<MapleCharacter> drawchars = this.characters;
        if (drawchars != null) {
            for (MapleCharacter chr : drawchars) {
                int charType = chr.getTouzhuType();
                int charNum = chr.getTouzhuNum();
                int charZhuNX = chr.getTouzhuNX();
                chr.setTouzhuType(0);
                chr.setTouzhuNum(0);
                chr.setTouzhuNX(0);
                sumNX += charZhuNX;
                if (charType == 2) {
                    toucount2++;
                    if (this.zjNum == 1 || this.zjNum == 3 || (this.zjNum == 5 && charNum == 1) || this.zjNum == 2 || this.zjNum == 4 || (this.zjNum == 6 && charNum == 2)) {
                        charZhuNX *= Integer.parseInt(ServerProperties.getProperty("RoyMS.??????A??????", "2"));
                        charZhuNX -= charZhuNX * Integer.parseInt(ServerProperties.getProperty("RoyMS.???????????????", "5")) / 100;
                        chr.modifyCSPoints(1, charZhuNX);
                        chr.dropMessage(1, "??????????????????" + this.zjNum + "??? \r\n????????????????????????????????????5%??????????????????:" + charZhuNX);
                        peiNX += charZhuNX;
                        zjNames6 = zjNames6 + chr.getName() + ":??????" + peiNX + "?????? ";
                        zhongcount2++;
                        zjpeople++;
                        getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, "[????????????]" + Integer.parseInt(ServerProperties.getProperty("RoyMS.??????A??????", "2")) + "???????????????:" + zjNames6 + "????????????(???)???"));
                    } else {
                        chr.dropMessage(1, "??????????????????" + this.zjNum + "???\r\n??????????????????????????????????????????");
                    }
                }
                if (charType == 3) {
                    toucount3++;
                    if ((this.zjNum > 4 && charNum > 4) || (this.zjNum < 3 && charNum < 3) || (this.zjNum >= 3 && this.zjNum <= 4 && charNum <= 4 && charNum >= 3)) {
                        charZhuNX *= Integer.parseInt(ServerProperties.getProperty("RoyMS.??????B??????", "3"));
                        charZhuNX -= charZhuNX * Integer.parseInt(ServerProperties.getProperty("RoyMS.???????????????", "5")) / 100;
                        chr.modifyCSPoints(1, charZhuNX);
                        chr.dropMessage(1, "??????????????????" + this.zjNum + "??? \r\n????????????????????????????????????5%??????????????????:" + charZhuNX);
                        peiNX += charZhuNX;
                        zjNames6 = zjNames6 + chr.getName() + ":??????" + peiNX + "?????? ";
                        zhongcount3++;
                        zjpeople++;
                        getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, "[????????????]" + Integer.parseInt(ServerProperties.getProperty("RoyMS.??????B??????", "3")) + "???????????????:" + zjNames6 + "????????????(???)???"));
                    } else {
                        chr.dropMessage(1, "??????????????????" + this.zjNum + "???\r\n??????????????????????????????????????????");
                    }
                }
                if (charType == 6) {
                    toucount6++;
                    if (this.zjNum == charNum) {
                        charZhuNX *= Integer.parseInt(ServerProperties.getProperty("RoyMS.??????C??????", "6"));
                        charZhuNX -= charZhuNX * Integer.parseInt(ServerProperties.getProperty("RoyMS.???????????????", "5")) / 100;
                        chr.modifyCSPoints(1, charZhuNX);
                        chr.dropMessage(1, "??????????????????" + this.zjNum + "??? \r\n????????????????????????????????????5%??????????????????:" + charZhuNX);
                        peiNX += charZhuNX;
                        zjNames6 = zjNames6 + chr.getName() + ":??????" + peiNX + "?????? ";
                        zhongcount6++;
                        zjpeople++;
                        getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, "[????????????]" + Integer.parseInt(ServerProperties.getProperty("RoyMS.??????C??????", "6")) + "???????????????:" + zjNames6 + "????????????(???)???"));
                        continue;
                    }
                    chr.dropMessage(1, "??????????????????" + this.zjNum + "???\r\n??????????????????????????????????????????");
                }
            }
            this.alltouzhu += sumNX;
            this.allpeichu += peiNX;
            this.characters.removeAll(drawchars);
        }
        int peoplecount = 0;
        if (drawchars != null)
            peoplecount = drawchars.size();
        getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, ServerProperties.getProperty("RoyMS.????????????")));
        if ("".equals(zjNames6));
    }

    static {
        CherryMSLotteryImpl.instance = null;
    }
}
