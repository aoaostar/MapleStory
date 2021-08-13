package constants;

import server.ServerProperties;
public class ServerConstants
{
    public static boolean PollEnabled;
    public static String Poll_Question;
    public static String[] Poll_Answers;
    public static MapleType MAPLE_TYPE;
    public static short MAPLE_VERSION;
    public static String MAPLE_PATCH;
    public static boolean Use_Fixed_IV;
    public static int MIN_MTS;
    public static int MTS_BASE;
    public static int MTS_TAX;
    public static int MTS_MESO;
    public static int CHANNEL_COUNT;
    public static boolean 封包显示;
    public static boolean 调试输出封包;
    public static boolean 自动注册;
    public static boolean PACKET_ERROR_OFF;
    public static boolean Super_password;
    public static boolean clientAutoDisconnect;
    public static String superpw;
    public static String PACKET_ERROR;
    public static int Channel;
    public static int removePlayerFromMap;
    public static boolean loadop;
    
    public void setPACKET_ERROR(final String ERROR) {
        ServerConstants.PACKET_ERROR = ERROR;
    }
    
    public String getPACKET_ERROR() {
        return ServerConstants.PACKET_ERROR;
    }
    
    public void setChannel(final int ERROR) {
        ServerConstants.Channel = ERROR;
    }
    
    public int getChannel() {
        return ServerConstants.Channel;
    }
    
    public void setRemovePlayerFromMap(final int ERROR) {
        ServerConstants.removePlayerFromMap = ERROR;
    }
    
    public int getRemovePlayerFromMap() {
        return ServerConstants.removePlayerFromMap;
    }
    
    public static boolean getAutoReg() {
        return ServerConstants.自动注册;
    }
    
    public static String ChangeAutoReg() {
        ServerConstants.自动注册 = !getAutoReg();
        return ServerConstants.自动注册 ? "开启" : "关闭";
    }
    
    public static byte Class_Bonus_EXP(final int job) {
        switch (job) {
            case 3000:
            case 3200:
            case 3210:
            case 3211:
            case 3212:
            case 3300:
            case 3310:
            case 3311:
            case 3312:
            case 3500:
            case 3510:
            case 3511:
            case 3512: {
                return 10;
            }
            default: {
                return 0;
            }
        }
    }
    
    static {
        ServerConstants.PollEnabled = false;
        ServerConstants.Poll_Question = "Are you mudkiz?";
        ServerConstants.Poll_Answers = new String[] { "test1", "test2", "test3" };
        ServerConstants.MAPLE_TYPE = MapleType.中国;
        ServerConstants.MAPLE_VERSION = 79;
        ServerConstants.MAPLE_PATCH = "1";
        ServerConstants.Use_Fixed_IV = false;
        ServerConstants.MIN_MTS = 110;
        ServerConstants.MTS_BASE = 100;
        ServerConstants.MTS_TAX = 10;
        ServerConstants.MTS_MESO = 5000;
        ServerConstants.CHANNEL_COUNT = 200;
        ServerConstants.封包显示 = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.封包显示", "false"));
        ServerConstants.调试输出封包 = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.调试输出封包", "false"));
        ServerConstants.自动注册 = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.AutoRegister", "false"));
        ServerConstants.PACKET_ERROR_OFF = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.记录38错误", "false"));
        ServerConstants.Super_password = false;
        ServerConstants.clientAutoDisconnect = true;
        ServerConstants.superpw = "";
        ServerConstants.PACKET_ERROR = "";
        ServerConstants.Channel = 0;
        ServerConstants.removePlayerFromMap = 0;
        ServerConstants.loadop = true;
    }
    
    public enum PlayerGMRank
    {
        NORMAL('@', 0), 
        INTERN('!', 1), 
        GM('!', 2), 
        ADMIN('!', 3);
        
        private final char commandPrefix;
        private final int level;
        
        private PlayerGMRank(final char ch, final int level) {
            this.commandPrefix = ch;
            this.level = level;
        }
        
        public char getCommandPrefix() {
            return this.commandPrefix;
        }
        
        public int getLevel() {
            return this.level;
        }
    }
    
    public enum CommandType
    {
        NORMAL(0), 
        TRADE(1);
        
        private final int level;
        
        private CommandType(final int level) {
            this.level = level;
        }
        
        public int getType() {
            return this.level;
        }
    }
    
    public enum MapleType
    {
        中国(4, "GB18030");
        
        final byte type;
        final String ascii;
        
        private MapleType(final int type, final String ascii) {
            this.type = (byte)type;
            this.ascii = ascii;
        }
        
        public String getAscii() {
            return this.ascii;
        }
        
        public byte getType() {
            return this.type;
        }
        
        public static MapleType getByType(final byte type) {
            for (final MapleType l : values()) {
                if (l.getType() == type) {
                    return l;
                }
            }
            return MapleType.中国;
        }
    }
}
