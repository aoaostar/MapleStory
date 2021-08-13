package handling.login.handler;

import client.MapleClient;
import handling.SendPacketOpcode;
import tools.FileoutputUtil;
import tools.StringUtil;
import tools.data.input.SeekableLittleEndianAccessor;

public class PacketErrorHandler
{
    public static void handlePacket(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (slea.available() >= 6L) {
            slea.skip(6);
            final short badPacketSize = slea.readShort();
            slea.skip(4);
            final int pHeader = slea.readShort();
            String pHeaderStr = Integer.toHexString(pHeader).toUpperCase();
            pHeaderStr = StringUtil.getLeftPaddedStr(pHeaderStr, '0', 4);
            final String op = lookupRecv(pHeader);
            String from = "";
            if (c.getPlayer() != null) {
                from = "\r\n时间：" + FileoutputUtil.CurrentReadable_Time() + "  角色: " + c.getPlayer().getName() + "  等级(" + c.getPlayer().getLevel() + ") 职业: " + c.getPlayer().getJob() + " \r\n";
            }
            final String Recv = "封包出错: \r\n" + op + " [" + pHeaderStr + "] (" + (badPacketSize - 6) + ")\r\n" + slea.toString(true);
            FileoutputUtil.packetLog("log/封包出错.log", from + Recv);
        }
    }
    
    private static String lookupRecv(final int val) {
        for (final SendPacketOpcode op : SendPacketOpcode.values()) {
            if (op.getValue() == val) {
                return op.name();
            }
        }
        return "UNKNOWN";
    }
}
