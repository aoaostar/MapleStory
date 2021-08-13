package handling.login.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import handling.channel.ChannelServer;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.login.LoginWorker;
import java.util.Calendar;
import java.util.List;
import server.MapleItemInformationProvider;
import server.ServerProperties;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.KoreanDateUtil;
import tools.MaplePacketCreator;
import tools.StringUtil;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.LoginPacket;

public class CharLoginHandler
{
    private static boolean loginFailCount(final MapleClient c) {
        ++c.loginAttempt;
        return c.loginAttempt > 5;
    }
    
    public static final void login(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final String login = slea.readMapleAsciiString();
        final String pwd = slea.readMapleAsciiString();
        c.setAccountName(login);
        final int[] bytes = new int[6];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = slea.readByteAsInt();
        }
        final StringBuilder sps = new StringBuilder();
        for (int j = 0; j < bytes.length; ++j) {
            sps.append(StringUtil.getLeftPaddedStr(Integer.toHexString(bytes[j]).toUpperCase(), '0', 2));
            sps.append("-");
        }
        String macData = sps.toString();
        macData = macData.substring(0, macData.length() - 1);
        c.setMac(macData);
        String ip = c.getSession().getRemoteAddress().toString().split(":")[0];
        ip = ip.substring(1, ip.length());
        final int s = c.getHandSome2();
        final int getS = c.getHandSome(login);
        boolean 防万能 = false;
        if (!ip.equalsIgnoreCase("127.0.0.1") && getS == s) {
            防万能 = false;
        }
        final boolean ipBan = c.hasBannedIP();
        final boolean macBan = c.isBannedMac(macData);
        final boolean banned = ipBan || macBan || 防万能;
        int loginok = 0;
        if (!Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.AutoRegister")) || !AutoRegister.autoRegister || AutoRegister.getAccountExists(login) || banned) {
            AutoRegister.success = true;
            AutoRegister.mac = true;
            loginok = c.login(login, pwd, ipBan || macBan || 防万能);
            final Calendar tempbannedTill = c.getTempBanCalendar();
            if (loginok == 0 && (ipBan || macBan || 防万能) && !c.isGm()) {
                loginok = 3;
                if (macBan || ipBan) {
                    MapleCharacter.ban(c.getSession().getRemoteAddress().toString().split(":")[0], "Enforcing account ban, account " + login, false, 4, false);
                }
                if (防万能) {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "请使用本服专用登录器登录。"));
                    c.getSession().write(LoginPacket.getLoginFailed(1));
                    return;
                }
            }
            if (loginok != 0) {
                if (!loginFailCount(c)) {
                    c.getSession().write(LoginPacket.getLoginFailed(loginok));
                }
            }
            else if (tempbannedTill.getTimeInMillis() != 0L) {
                if (!loginFailCount(c)) {
                    c.getSession().write(LoginPacket.getTempBan(KoreanDateUtil.getTempBanTimestamp(tempbannedTill.getTimeInMillis()), c.getBanReason()));
                }
            }
            else {
                FileoutputUtil.logToFile("Logs/ACPW.txt", "ACC: " + login + " PW: " + pwd + " MAC : " + macData + " IP: " + c.getSession().getRemoteAddress().toString() + "\r\n");
                c.updateMacs();
                c.loginAttempt = 0;
                LoginWorker.registerClient(c);
            }
            return;
        }
        if (pwd.equalsIgnoreCase("disconnect") || pwd.equalsIgnoreCase("fixme") || pwd.equalsIgnoreCase("admin") || pwd.equalsIgnoreCase("000000")) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "你不可以使用这个作为密码."));
            c.getSession().write(LoginPacket.getLoginFailed(1));
            return;
        }
        AutoRegister.createAccount(login, pwd, c.getSession().getRemoteAddress().toString(), macData);
        if (AutoRegister.success && AutoRegister.mac) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "友情提示：账号创建成功,请尝试重新登录!\r\n拒绝一切第三方辅助程序\r\n提倡手动从我做起-举报开挂有奖\r\n！"));
        }
        else if (!AutoRegister.mac) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "友情提示：账号创建失败，你已经注册过账号，一个机器码只能注册一个账号"));
        }
        AutoRegister.success = true;
        AutoRegister.mac = true;
        c.getSession().write(LoginPacket.getLoginFailed(1));
    }
    
    public static final void SetGenderRequest(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final byte gender = slea.readByte();
        final String username = slea.readMapleAsciiString();
        if (c.getAccountName().equals(username)) {
            c.setGender(gender);
            c.updateSecondPassword();
            c.updateGender();
            c.getSession().write(LoginPacket.getGenderChanged(c));
            c.getSession().write(MaplePacketCreator.licenseRequest());
            c.updateLoginState(MapleClient.LOGIN_NOTLOGGEDIN, c.getSessionIPAddress());
        }
        else {
            c.getSession().close(true);
        }
    }
    
    public static final void ServerListRequest(final MapleClient c) {
        c.getSession().write(LoginPacket.getServerList(0, LoginServer.getServerName(), LoginServer.getLoad()));
        c.getSession().write(LoginPacket.getEndOfServerList());
    }
    
    public static final void ServerStatusRequest(final MapleClient c) {
        final int numPlayer = LoginServer.getUsersOn();
        final int userLimit = LoginServer.getUserLimit();
        if (numPlayer >= userLimit) {
            c.getSession().write(LoginPacket.getServerStatus(2));
        }
        else if (numPlayer * 2 >= userLimit) {
            c.getSession().write(LoginPacket.getServerStatus(1));
        }
        else {
            c.getSession().write(LoginPacket.getServerStatus(0));
        }
    }
    
    public static void CharlistRequest(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final int server = slea.readByte();
        final int channel = slea.readByte() + 1;
        slea.readInt();
        c.setWorld(server);
        c.setChannel(channel);
        final List<MapleCharacter> chars = c.loadCharacters(server);
        if (chars != null) {
            c.getSession().write(LoginPacket.getCharList(c.getSecondPassword() != null, chars, c.getCharacterSlots()));
        }
        else {
            c.getSession().close(true);
        }
    }
    
    public static void CheckCharName(final String name, final MapleClient c) {
        c.getSession().write(LoginPacket.charNameResponse(name, !MapleCharacterUtil.canCreateChar(name) || LoginInformationProvider.getInstance().isForbiddenName(name)));
    }
    
    public static void CreateChar(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final String name = slea.readMapleAsciiString();
        final int JobType = slea.readInt();
        //冒险家
        final boolean mxj = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.mxj"));
        //骑士团
        final boolean qst = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.qst"));
        //战神
        final boolean zs = Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.zs"));
        if (!qst && JobType == 0) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "暂未开放骑士团职业！,请联系GM开放"));
            c.getSession().write(LoginPacket.getLoginFailed(1));
            return;
        }
        if (!mxj && JobType == 1) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "暂未开放冒险家职业！,请联系GM开放"));
            c.getSession().write(LoginPacket.getLoginFailed(1));
            return;
        }
        if (!zs && JobType == 2) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "暂未开放战神职业！,请联系GM开放"));
            c.getSession().write(LoginPacket.getLoginFailed(1));
            return;
        }
        final short db = 0;
        final int face = slea.readInt();
        final int hair = slea.readInt();
        final int hairColor = 0;
        final byte skinColor = 0;
        final int top = slea.readInt();
        final int bottom = slea.readInt();
        final int shoes = slea.readInt();
        final int weapon = slea.readInt();
        final byte gender = c.getGender();
        switch (gender) {
            case 0: {
                if (face != 20100 && face != 20401 && face != 20402) {
                    return;
                }
                if (hair != 30030 && hair != 30027 && hair != 30000) {
                    return;
                }
                if (top != 1040002 && top != 1040006 && top != 1040010 && top != 1042167) {
                    return;
                }
                if (bottom != 1060002 && bottom != 1060006 && bottom != 1062115) {
                    return;
                }
                break;
            }
            case 1: {
                if (face != 21002 && face != 21700 && face != 21201) {
                    return;
                }
                if (hair != 31002 && hair != 31047 && hair != 31057) {
                    return;
                }
                if (top != 1041002 && top != 1041006 && top != 1041010 && top != 1041011 && top != 1042167) {
                    return;
                }
                if (bottom != 1061002 && bottom != 1061008 && bottom != 1062115) {
                    return;
                }
                break;
            }
            default: {
                return;
            }
        }
        if (shoes != 1072001 && shoes != 1072005 && shoes != 1072037 && shoes != 1072038 && shoes != 1072383) {
            return;
        }
        if (weapon != 1302000 && weapon != 1322005 && weapon != 1312004 && weapon != 1442079) {
            return;
        }
        final MapleCharacter newchar = MapleCharacter.getDefault(c, JobType);
        newchar.setWorld((byte)c.getWorld());
        newchar.setFace(face);
        newchar.setHair(hair + hairColor);
        newchar.setGender(gender);
        newchar.setName(name);
        newchar.setSkinColor(skinColor);
        final MapleInventory equip = newchar.getInventory(MapleInventoryType.EQUIPPED);
        final MapleItemInformationProvider li = MapleItemInformationProvider.getInstance();
        IItem item = li.getEquipById(top);
        item.setPosition((short)(-5));
        equip.addFromDB(item);
        item = li.getEquipById(bottom);
        item.setPosition((short)(-6));
        equip.addFromDB(item);
        item = li.getEquipById(shoes);
        item.setPosition((short)(-7));
        equip.addFromDB(item);
        item = li.getEquipById(weapon);
        item.setPosition((short)(-11));
        equip.addFromDB(item);
        switch (JobType) {
            case 0: {
                newchar.setQuestAdd(MapleQuest.getInstance(20022), (byte)1, "1");
                newchar.setQuestAdd(MapleQuest.getInstance(20010), (byte)1, null);
                newchar.setQuestAdd(MapleQuest.getInstance(20000), (byte)1, null);
                newchar.setQuestAdd(MapleQuest.getInstance(20015), (byte)1, null);
                newchar.setQuestAdd(MapleQuest.getInstance(20020), (byte)1, null);
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161047, (short)0, (short)1, (byte)0));
                newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2022336, (short)0, (short)1, (byte)0));
                break;
            }
            case 1: {
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161001, (short)0, (short)1, (byte)0));
                newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2022336, (short)0, (short)1, (byte)0));
                break;
            }
            case 2: {
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161048, (short)0, (short)1, (byte)0));
                newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2022336, (short)0, (short)1, (byte)0));
                break;
            }
        }
        if (JobType == 0) {
            newchar.setQuestAdd(MapleQuest.getInstance(20022), (byte)1, "1");
            newchar.setQuestAdd(MapleQuest.getInstance(20010), (byte)1, null);
        }
        if (MapleCharacterUtil.canCreateChar(name) && !LoginInformationProvider.getInstance().isForbiddenName(name)) {
            MapleCharacter.saveNewCharToDB(newchar, JobType, JobType == 1 && db == 0);
            c.getSession().write(LoginPacket.addNewCharEntry(newchar, true));
            c.createdChar(newchar.getId());
        }
        else {
            c.getSession().write(LoginPacket.addNewCharEntry(newchar, false));
        }
    }
    
    public static void Character_WithoutSecondPassword(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final int charId = slea.readInt();
        if (!c.isLoggedIn() || loginFailCount(c) || !c.login_Auth(charId)) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (ChannelServer.getInstance(c.getChannel()) == null || c.getWorld() != 0) {
            c.getSession().close(true);
            return;
        }
        if (c.getIdleTask() != null) {
            c.getIdleTask().cancel(true);
        }
        final String ip = c.getSessionIPAddress();
        LoginServer.putLoginAuth(charId, ip.substring(ip.indexOf(47) + 1, ip.length()), c.getTempIP(), c.getChannel());
        c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, ip);
        c.getSession().write(MaplePacketCreator.getServerIP(Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1]), charId));
    }
    
    public static void Welcome(final MapleClient c) {
    }
}
