package server;

import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import constants.GameConstants;
import constants.OtherSettings;
import constants.ServerConstants;
import database.DatabaseConnection;
import gui.RoyMS;
import handling.MapleServerHandler;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.world.World;
import handling.world.family.MapleFamilyBuff;
import handling.world.guild.MapleGuild;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.StringUtil;

public class Start
{
    public static boolean Check;
    private static RoyMS CashGui;
    public static Start instance;
    private static int maxUsers;
    private static ServerSocket srvSocket;
    private static final int srvPort = 6350;
    private MapleClient c;

    public static void main(final String[] args) throws InterruptedException {
        String homePath = System.getProperty("homePath");
        String scriptsPath = System.getProperty("scriptsPath");
        String wzPath = System.getProperty("wzPath");
        System.setProperty("server_property_file_path", homePath + "server.properties");
        System.setProperty("server_property_db_path", homePath + "db.properties");
        System.setProperty("server_property_shop_path", homePath + "shop.properties");
        System.setProperty("server_property_fish_path", homePath + "fish.properties");
        System.setProperty("wzPath", wzPath);
        System.setProperty("scripts_path", scriptsPath);
        System.setProperty("server_name", "寻梦");
        OtherSettings.getInstance();
        Start.instance.run();
    }
    
    public void run() throws InterruptedException {
        final long start = System.currentTimeMillis();
        checkSingleInstance();
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.Admin"))) {
            printSection("[!!! 已开启只能管理员登录模式 !!!]");
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.AutoRegister"))) {
            System.out.println("加载 自动注册完成 :::");
        }
        try {
            try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0")) {
                ps.executeUpdate();
            }
            try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET lastGainHM = 0")) {
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException("[数据库异常] 请检查数据库链接。目前无法连接到MySQL数据库.");
        }
        System.out.println("服务端 开始启动...版本号：079H3 2020-10-13");
        System.out.println("当前操作系统: " + System.getProperty("sun.desktop"));
        System.out.println("服务器地址: " + ServerProperties.getProperty("RoyMS.IP") + ":" + LoginServer.PORT);
        System.out.println("游戏版本: " + ServerConstants.MAPLE_TYPE + " v." + ServerConstants.MAPLE_VERSION + "." + ServerConstants.MAPLE_PATCH);
        System.out.println("主服务器: 蓝蜗牛");
        World.init();
        runThread();
        loadData();
        System.out.print("加载\"登入\"服务...");
        LoginServer.run_startup_configurations();
        System.out.println("正在加载频道...");
        ChannelServer.startChannel_Main();
        System.out.println("频道加载完成!\r\n");
        System.out.print("正在加载商城...");
        CashShopServer.run_startup_configurations();
        printSection("刷怪线程");
        World.registerRespawn();
        Timer.CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000L);
        onlineTime(1);
        memoryRecical(10);
        MapleServerHandler.registerMBean();
        LoginServer.setOn();
        System.out.println("\r\n经验倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Exp")) + "  物品倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Drop")) + "  金币倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Meso")) + "  BOSS爆率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.BDrop")));
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.检测复制装备", "false"))) {
            checkCopyItemFromSql();
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.防万能检测", "false"))) {
            System.out.println("启动防万能检测");
            startCheck();
        }
        final long now = System.currentTimeMillis() - start;
        final long seconds = now / 1000L;
        final long ms = now % 1000L;
        System.out.println("加载完成, 耗时: " + seconds + "秒" + ms + "毫秒\r\n");
//        CashGui();
        Boolean loadGui = Boolean.valueOf(ServerProperties.getProperty("RoyMS.loadGui","false"));
        if(loadGui){
            System.out.println("加载GUI工具");
            CashGui();
        }
    }
    
    public static void runThread() {
        System.out.print("\r\n正在加载线程");
        Timer.WorldTimer.getInstance().start();
        Timer.EtcTimer.getInstance().start();
        Timer.MapTimer.getInstance().start();
        Timer.MobTimer.getInstance().start();
        Timer.CloneTimer.getInstance().start();
        Timer.CheatTimer.getInstance().start();
        System.out.print(".");
        Timer.EventTimer.getInstance().start();
        Timer.BuffTimer.getInstance().start();
        Timer.TimerManager.getInstance().start();
        Timer.PingTimer.getInstance().start();
        Timer.PGTimer.getInstance().start();
        System.out.println("完成!\r\n");
    }
    
    public static void loadData() {
        System.out.println("载入数据(因为数据量大可能比较久而且内存消耗会飙升)");
        System.out.println("加载等级经验数据");
        GameConstants.LoadExp();
        System.out.println("加载排名信息数据");
        MapleGuildRanking.getInstance().RankingUpdate();
        System.out.println("加载公会数据并清理不存在公会");
        MapleGuild.loadAll();
        System.out.println("加载任务数据");
        MapleQuest.initQuests();
        MapleLifeFactory.loadQuestCounts();
        System.out.println("加载爆物数据");
        MapleMonsterInformationProvider.getInstance().retrieveGlobal();
        System.out.println("加载脏话检测系统");
        LoginInformationProvider.getInstance();
        System.out.println("加载道具数据");
        ItemMakerFactory.getInstance();
        MapleItemInformationProvider.getInstance().load();
        System.out.println("加载技能数据");
        SkillFactory.getSkill(99999999);
        MobSkillFactory.getInstance();
        MapleFamilyBuff.getBuffEntry();
        System.out.println("加载SpeedRunner");
        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));
        try {
            SpeedRunner.getInstance().loadSpeedRuns();
        }
        catch (SQLException e) {
            System.out.println("SpeedRunner错误:" + e);
        }
        System.out.println("加载随机奖励系统");
        RandomRewards.getInstance();
        System.out.println("加载0X问答系统");
        MapleOxQuizFactory.getInstance().initialize();
        System.out.println("加载嘉年华数据");
        MapleCarnivalFactory.getInstance();
        System.out.println("加载角色类排名数据");
        System.out.println("加载商城道具数据，数据较为庞大，请耐心等待");
        CashItemFactory.getInstance().initialize();
        MapleMapFactory.loadCustomLife();
    }
    
    public static void 自动存档(final int time) {
        System.out.println("服务端启用自动存档." + time + "分钟自动执行数据存档.");
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                int ppl = 0;
                try {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            ++ppl;
                            chr.saveToDB(false, false);
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }, 60000 * time);
    }

    //在线时间
    public static void onlineTime(final int time) {
        System.out.println("服务端启用在线时间统计." + time + "分钟记录一次在线时间.");
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                try {
                    for (final ChannelServer chan : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            chr.gainGamePoints(1);
                            if (chr.getGamePoints() >= 5) {
                                continue;
                            }
                            chr.resetFBRW();
                            chr.resetFBRWA();
                            chr.resetSBOSSRW();
                            chr.resetSBOSSRWA();
                            chr.resetSGRW();
                            chr.resetSGRWA();
                            chr.resetSJRW();
                            chr.resetlb();
                            chr.setmrsjrw(0);
                            chr.setmrfbrw(0);
                            chr.setmrsgrw(0);
                            chr.setmrsbossrw(0);
                            chr.setmrfbrwa(0);
                            chr.setmrsgrwa(0);
                            chr.setmrsbossrwa(0);
                            chr.setmrfbrwas(0);
                            chr.setmrsgrwas(0);
                            chr.setmrsbossrwas(0);
                            chr.setmrfbrws(0);
                            chr.setmrsgrws(0);
                            chr.setmrsbossrws(0);
                            chr.resetGamePointsPS();
                            chr.resetGamePointsPD();
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }, 60000 * time);
    }
    
    protected static void checkSingleInstance() {
        try {
            Start.srvSocket = new ServerSocket(srvPort);
        }
        catch (IOException ex) {
            if (ex.getMessage().indexOf("Address already in use: JVM_Bind") >= 0) {
                System.out.println("在一台主机上同时只能启动一个进程(Only one instance allowed)。");
            }
            System.exit(0);
        }
    }
    
    protected static void checkCopyItemFromSql() {
        System.out.println("服务端启用 防复制系统，发现复制装备.进行删除处理功能");
        final List<Integer> equipOnlyIds = new ArrayList<Integer>();
        final Map<Integer, Integer> checkItems = new HashMap<Integer, Integer>();
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE equipOnlyId > 0");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int itemId = rs.getInt("itemId");
                final int equipOnlyId = rs.getInt("equipOnlyId");
                if (equipOnlyId > 0) {
                    if (checkItems.containsKey(equipOnlyId)) {
                        if (checkItems.get(equipOnlyId) != itemId) {
                            continue;
                        }
                        equipOnlyIds.add(equipOnlyId);
                    }
                    else {
                        checkItems.put(equipOnlyId, itemId);
                    }
                }
            }
            rs.close();
            ps.close();
            Collections.sort(equipOnlyIds);
            for (final int i : equipOnlyIds) {
                ps = con.prepareStatement("DELETE FROM inventoryitems WHERE equipOnlyId = ?");
                ps.setInt(1, i);
                ps.executeUpdate();
                ps.close();
                System.out.println("发现复制装备 该装备的唯一ID: " + i + " 已进行删除处理..");
                FileoutputUtil.log("装备复制.txt", "发现复制装备 该装备的唯一ID: " + i + " 已进行删除处理..");
            }
        }
        catch (SQLException ex) {
            System.out.println("[EXCEPTION] 清理复制装备出现错误." + ex);
        }
    }
    
    public void startServer() throws InterruptedException {
        final long start = System.currentTimeMillis();
        checkSingleInstance();
        System.out.println("======================================");
        System.out.println(ServerProperties.getProperty("RoyMS.Admin"));
        System.out.println("========================");
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.Admin"))) {
            printSection("[!!! 已开启只能管理员登录模式 !!!]");
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.AutoRegister"))) {
            System.out.println("加载 自动注册完成 :::");
        }
        try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0")) {
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException("[数据库异常] 请检查数据库链接。目前无法连接到MySQL数据库.");
        }
        System.out.println("服务端 开始启动...");
        System.out.println("当前操作系统: " + System.getProperty("sun.desktop"));
        System.out.println("服务器地址: " + ServerProperties.getProperty("RoyMS.IP") + ":" + LoginServer.PORT);
        System.out.println("游戏版本: " + ServerConstants.MAPLE_TYPE + " v." + ServerConstants.MAPLE_VERSION + "." + ServerConstants.MAPLE_PATCH);
        System.out.println("作者Roy: 游戏仅供学习和娱乐，禁止用于商业用途");
        World.init();
        runThread();
        loadData();
        System.out.print("加载\"登入\"服务...");
        LoginServer.run_startup_configurations();
        System.out.println("正在加载频道...");
        ChannelServer.startChannel_Main();
        System.out.println("频道加载完成!\r\n");
        System.out.print("正在加载商城...");
        CashShopServer.run_startup_configurations();
        printSection("刷怪线程");
        World.registerRespawn();
        Timer.CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000L);
        onlineTime(1);
        memoryRecical(360);
        MapleServerHandler.registerMBean();
        LoginServer.setOn();
        System.out.println("\r\n经验倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Exp")) + "  物品倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Drop")) + "  金币倍率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.Meso")) + "  BOSS爆率：" + Integer.parseInt(ServerProperties.getProperty("RoyMS.BDrop")));
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.检测复制装备", "false"))) {
            checkCopyItemFromSql();
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("RoyMS.防万能检测", "false"))) {
            System.out.println("启动防万能检测");
            startCheck();
        }
        final long now = System.currentTimeMillis() - start;
        final long seconds = now / 1000L;
        final long ms = now % 1000L;
        System.out.println("加载完成, 耗时: " + seconds + "秒" + ms + "毫秒\r\n");
        System.out.println("服务端开启完毕，可以登入游戏了！");
    }
    
    public static void CashGui() {
        if (Start.CashGui != null) {
            Start.CashGui.dispose();
        }
        (Start.CashGui = new RoyMS()).setVisible(true);
    }

    //在线统计
    public static void onlineStatistics(final int time) {
        System.out.println("服务端启用在线统计." + time + "分钟统计一次在线的人数信息.");
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                final Map<Integer, Integer> connected = World.getConnected();
                final StringBuilder conStr = new StringBuilder(FileoutputUtil.CurrentReadable_Time() + " 在线人数: ");
                for (final int i : connected.keySet()) {
                    if (i == 0) {
                        final int users = connected.get(i);
                        conStr.append(StringUtil.getRightPaddedStr(String.valueOf(users), ' ', 3));
                        if (users > Start.maxUsers) {
                            Start.maxUsers = users;
                        }
                        conStr.append(" 最高在线: ");
                        conStr.append(Start.maxUsers);
                        break;
                    }
                }
                System.out.println(conStr.toString());
                if (Start.maxUsers > 0) {
                    FileoutputUtil.log("log/在线统计.log", conStr.toString());
                }
            }
        }, 60000 * time);
    }
    
    public static void printSection(String s) {
        for (s = "-[ " + s + " ]"; s.getBytes().length < 79; s = "=" + s) {}
        System.out.println(s);
    }
    
    public static void startCheck() {
        System.out.println("服务端启用检测.30秒检测一次角色是否与登录器断开连接.");
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                for (final ChannelServer cserv_ : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv_.getPlayerStorage().getAllCharacters()) {
                        if (chr != null) {
                            chr.startCheck();
                        }
                    }
                }
            }
        }, 30000L);
    }

    //内存回收
    public static void memoryRecical(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        }, 60000 * time);
    }
    
    static {
        Start.Check = true;
        Start.instance = new Start();
        Start.maxUsers = 0;
        Start.srvSocket = null;
    }
    
    public static class Shutdown implements Runnable
    {
        @Override
        public void run() {
            new Thread(ShutdownServer.getInstance()).start();
        }
    }
}
