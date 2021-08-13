package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import java.util.ArrayList;
import java.util.List;

import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class BeanGame
{
    public static void BeanGame1(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final List<Beans> beansInfo = new ArrayList<Beans>();
        final int type = slea.readByte();
        int 力度 = 0;
        int 豆豆序號 = 0;
        if (type == 1) {
            力度 = slea.readShort();
            chr.setBeansRange(力度);
            c.getSession().write(MaplePacketCreator.enableActions());
        }
        else if (type == 0) {
            力度 = slea.readShort();
            豆豆序號 = slea.readInt() + 1;
            chr.setBeansRange(力度);
            chr.setBeansNum(豆豆序號);
            if (豆豆序號 == 1) {
                chr.setCanSetBeansNum(false);
            }
        }
        else if (type == 2) {
            if (type == 11 || type == 0) {
                力度 = slea.readShort();
                豆豆序號 = slea.readInt() + 1;
                chr.setBeansRange(力度);
                chr.setBeansNum(豆豆序號);
                if (豆豆序號 == 1) {
                    chr.setCanSetBeansNum(false);
                }
            }
        }
        else if (type == 6) {
            slea.skip(1);
            final int 循環次數 = slea.readByte();
            if (循環次數 == 0) {
                return;
            }
            if (循環次數 != 1) {
                slea.skip((循環次數 - 1) * 8);
            }
            if (chr.isCanSetBeansNum()) {
                chr.setBeansNum(chr.getBeansNum() + 循環次數);
            }
            chr.gainBeans(-循環次數);
            chr.setCanSetBeansNum(true);
        }
        else if (type == 11 || type == 6) {
            力度 = slea.readShort();
            chr.setBeansRange(力度);
            final byte size = (byte)(slea.readByte() + 1);
            final short Pos = slea.readShort();
            final byte Type = (byte)(slea.readByte() + 1);
            c.getSession().write(MaplePacketCreator.showBeans(力度, size, Pos, Type));
        }
        else {
            System.out.println("未處理的類型【" + type + "】\n包" + slea.toString());
        }
    }
    
    private static int getBeanType() {
        final int random = rand(1, 100);
        int beanType = 0;
        switch (random) {
            case 2: {
                beanType = 1;
                break;
            }
            case 49: {
                beanType = 2;
                break;
            }
            case 99: {
                beanType = 3;
                break;
            }
        }
        return beanType;
    }
    
    private static int rand(final int lbound, final int ubound) {
        return (int)(Math.random() * (ubound - lbound + 1) + lbound);
    }
    
    public static void BeanGame2(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), c.getPlayer().getBeans()));
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public class Beans
    {
        private final int number;
        private final int type;
        private final int pos;
        
        public Beans(final int pos, final int type, final int number) {
            this.pos = pos;
            this.number = number;
            this.type = type;
        }
        
        public int getType() {
            return this.type;
        }
        
        public int getNumber() {
            return this.number;
        }
        
        public int getPos() {
            return this.pos;
        }
    }
}
