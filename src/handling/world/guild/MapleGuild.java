package handling.world.guild;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import database.DatabaseConnection;
import handling.MaplePacket;
import handling.world.World;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import tools.MaplePacketCreator;
import tools.StringUtil;
import tools.data.output.MaplePacketLittleEndianWriter;
import tools.packet.UIPacket;

public class MapleGuild implements Serializable
{
    public static long serialVersionUID;
    private List<MapleGuildCharacter> members;
    private String[] rankTitles;
    private String name;
    private String notice;
    private int id;
    private int gp;
    private int logo;
    private int logoColor;
    private int leader;
    private int capacity;
    private int logoBG;
    private int logoBGColor;
    private int signature;
    private boolean bDirty;
    private boolean proper;
    private int allianceid;
    private int invitedid;
    private Map<Integer, MapleBBSThread> bbs;
    private ReentrantReadWriteLock lock;
    private Lock rL;
    private Lock wL;
    private boolean init;
    
    public static void displayGuildRanks(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `GP`, `logoBG`, `logoBGColor`, `logo`, `logoColor` FROM guilds ORDER BY `GP` DESC LIMIT 50");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.showGuildRanks(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {}
    }
    
    public static void meso(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`,`meso`,`vip`, `level`FROM characters ORDER BY `meso` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.showMesoRanks(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {}
    }
    
    public static void 战斗力排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement ps = con.prepareStatement("select `name`, ((`level` * 15) + CASE WHEN fame < 0 THEN 0 ELSE fame END + (maxhp / 50) + (maxmp / 50) + str + dex + luk + `int`) AS `data`, `level`, meso from characters order by `data` desc LIMIT 100")) {
                rs = ps.executeQuery();
                c.getSession().write(MaplePacketCreator.showCustomRanks(npcid, rs));
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("战斗力排行出错！");
        }
    }
    
    public static void 破攻排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement ps = con.prepareStatement("select `name`, `PGMaxDamage` AS `data`, `level`, meso from characters order by `data` desc LIMIT 100")) {
                rs = ps.executeQuery();
                c.getSession().write(MaplePacketCreator.showCustomRanks(npcid, rs));
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("破攻排行出错！");
        }
    }
    
    public static void 总在线时间排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement ps = con.prepareStatement("select `name`, totalOnlineTime AS `data`, `level`, meso from characters order by `data` desc LIMIT 100")) {
                rs = ps.executeQuery();
                c.getSession().write(MaplePacketCreator.showCustomRanks(npcid, rs));
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("总在线时间排行出错！");
        }
    }
    
    public static void displayLevelRanks(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `vip`, `level`, `meso` FROM characters ORDER BY `level` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.showLevelRanks(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {}
    }
    
    public static void 豆豆排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement ps = con.prepareStatement("select `name`, beans AS `data`, `level`, meso from characters order by `data` desc LIMIT 100")) {
                rs = ps.executeQuery();
                c.getSession().write(MaplePacketCreator.showCustomRanks(npcid, rs));
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("豆豆排行出错！");
        }
    }
    
    public static void VIP排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `vip`, `level`, `meso` FROM characters ORDER BY `vip` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.showVipRanks(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {}
    }
    
    public static void MapleMSpvpdeaths(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `pvpdeaths`, `str`, `dex`, `int`, `luk` FROM characters ORDER BY `pvpdeaths` DESC LIMIT 20");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.MapleMSpvpdeaths(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (Exception e) {
            System.out.println("failed to display guild ranks." + e);
        }
    }
    
    public static void MapleMSpvpkills(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `pvpkills`, `str`, `dex`, `int`, `luk` FROM characters ORDER BY `pvpkills` WHERE gm < 1  DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.MapleMSpvpkills(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (Exception e) {
            System.out.println("failed to display guild ranks." + e);
        }
    }
    
    public static void 人气排行(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `fame`, `level`, `meso` FROM characters ORDER BY `fame` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.showRQRanks(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {}
    }
    
    public static Collection<MapleGuild> loadAll() {
        final Collection<MapleGuild> ret = new ArrayList<MapleGuild>();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT guildid FROM guilds");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final MapleGuild g = new MapleGuild(rs.getInt("guildid"));
                if (g.getId() > 0) {
                    ret.add(g);
                }
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            System.err.println("unable to read guild information from sql");
            se.printStackTrace();
        }
        return ret;
    }
    
    public static int createGuild(final int leaderId, final String name) {
        if (name.length() > 12) {
            return 0;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT guildid FROM guilds WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                rs.close();
                ps.close();
                return 0;
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("INSERT INTO guilds (`leader`, `name`, `signature`, `alliance`) VALUES (?, ?, ?, 0)", 1);
            ps.setInt(1, leaderId);
            ps.setString(2, name);
            ps.setInt(3, (int)(System.currentTimeMillis() / 1000L));
            ps.execute();
            rs = ps.getGeneratedKeys();
            int ret = 0;
            if (rs.next()) {
                ret = rs.getInt(1);
            }
            rs.close();
            ps.close();
            return ret;
        }
        catch (SQLException se) {
            System.err.println("SQL THROW");
            se.printStackTrace();
            return 0;
        }
    }
    
    public static MapleGuildResponse sendInvite(final MapleClient c, final String targetName) {
        final MapleCharacter mc = c.getChannelServer().getPlayerStorage().getCharacterByName(targetName);
        if (mc == null) {
            return MapleGuildResponse.NOT_IN_CHANNEL;
        }
        if (mc.getGuildId() > 0) {
            return MapleGuildResponse.ALREADY_IN_GUILD;
        }
        mc.getClient().getSession().write(MaplePacketCreator.guildInvite(c.getPlayer().getGuildId(), c.getPlayer().getName(), c.getPlayer().getLevel(), c.getPlayer().getJob()));
        return null;
    }
    
    public static void setOfflineGuildStatus(final int guildid, final byte guildrank, final byte alliancerank, final int cid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE characters SET guildid = ?, guildrank = ?, alliancerank = ? WHERE id = ?");
            ps.setInt(1, guildid);
            ps.setInt(2, guildrank);
            ps.setInt(3, alliancerank);
            ps.setInt(4, cid);
            ps.execute();
            ps.close();
        }
        catch (SQLException se) {
            System.out.println("SQLException: " + se.getLocalizedMessage());
            se.printStackTrace();
        }
    }
    
    public MapleGuild(final int guildid) {
        this.members = new CopyOnWriteArrayList<MapleGuildCharacter>();
        this.rankTitles = new String[5];
        this.bDirty = true;
        this.proper = true;
        this.allianceid = 0;
        this.invitedid = 0;
        this.bbs = new HashMap<Integer, MapleBBSThread>();
        this.lock = new ReentrantReadWriteLock();
        this.rL = this.lock.readLock();
        this.wL = this.lock.writeLock();
        this.init = false;
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildid);
            ResultSet rs = ps.executeQuery();
            if (!rs.first()) {
                rs.close();
                ps.close();
                this.id = -1;
                return;
            }
            this.id = guildid;
            this.name = rs.getString("name");
            this.gp = rs.getInt("GP");
            this.logo = rs.getInt("logo");
            this.logoColor = rs.getInt("logoColor");
            this.logoBG = rs.getInt("logoBG");
            this.logoBGColor = rs.getInt("logoBGColor");
            this.capacity = rs.getInt("capacity");
            this.rankTitles[0] = rs.getString("rank1title");
            this.rankTitles[1] = rs.getString("rank2title");
            this.rankTitles[2] = rs.getString("rank3title");
            this.rankTitles[3] = rs.getString("rank4title");
            this.rankTitles[4] = rs.getString("rank5title");
            this.leader = rs.getInt("leader");
            this.notice = rs.getString("notice");
            this.signature = rs.getInt("signature");
            this.allianceid = rs.getInt("alliance");
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT id, name, level, job, guildrank, alliancerank FROM characters WHERE guildid = ? ORDER BY guildrank ASC, name ASC");
            ps.setInt(1, guildid);
            rs = ps.executeQuery();
            if (!rs.first()) {
                System.err.println("No members in guild " + this.id + ".  Impossible... guild is disbanding");
                rs.close();
                ps.close();
                this.proper = false;
                return;
            }
            boolean leaderCheck = false;
            do {
                if (rs.getInt("id") == this.leader) {
                    leaderCheck = true;
                }
                this.members.add(new MapleGuildCharacter(rs.getInt("id"), rs.getShort("level"), rs.getString("name"), (byte)(-1), rs.getInt("job"), rs.getByte("guildrank"), guildid, rs.getByte("alliancerank"), false));
            } while (rs.next());
            rs.close();
            ps.close();
            if (!leaderCheck) {
                System.err.println("Leader " + this.leader + " isn't in guild " + this.id + ".  Impossible... guild is disbanding.");
                this.proper = false;
                return;
            }
            ps = con.prepareStatement("SELECT * FROM bbs_threads WHERE guildid = ? ORDER BY localthreadid DESC");
            ps.setInt(1, guildid);
            rs = ps.executeQuery();
            while (rs.next()) {
                final MapleBBSThread thread = new MapleBBSThread(rs.getInt("localthreadid"), rs.getString("name"), rs.getString("startpost"), rs.getLong("timestamp"), guildid, rs.getInt("postercid"), rs.getInt("icon"));
                final PreparedStatement pse = con.prepareStatement("SELECT * FROM bbs_replies WHERE threadid = ?");
                pse.setInt(1, rs.getInt("threadid"));
                final ResultSet rse = pse.executeQuery();
                while (rse.next()) {
                    thread.replies.put(thread.replies.size(), new MapleBBSThread.MapleBBSReply(thread.replies.size(), rse.getInt("postercid"), rse.getString("content"), rse.getLong("timestamp")));
                }
                rse.close();
                pse.close();
                this.bbs.put(rs.getInt("localthreadid"), thread);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            System.err.println("unable to read guild information from sql");
            se.printStackTrace();
        }
    }
    
    public boolean isProper() {
        return this.proper;
    }
    
    public void writeGPToDB() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final StringBuilder buf = new StringBuilder("UPDATE guilds SET GP = ?");
            buf.append(" WHERE guildid = ?");
            final PreparedStatement ps = con.prepareStatement(buf.toString());
            ps.setInt(1, this.gp);
            ps.setInt(2, this.id);
            ps.execute();
            ps.close();
        }
        catch (SQLException se) {
            System.err.println("Error saving guildGP to SQL");
            se.printStackTrace();
        }
    }
    
    public void writeToDB(final boolean bDisband) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            if (!bDisband) {
                final StringBuilder buf = new StringBuilder("UPDATE guilds SET GP = ?, logo = ?, logoColor = ?, logoBG = ?, logoBGColor = ?, ");
                for (int i = 1; i < 6; ++i) {
                    buf.append("rank").append(i).append("title = ?, ");
                }
                buf.append("capacity = ?, notice = ?, alliance = ? WHERE guildid = ?");
                PreparedStatement ps = con.prepareStatement(buf.toString());
                ps.setInt(1, this.gp);
                ps.setInt(2, this.logo);
                ps.setInt(3, this.logoColor);
                ps.setInt(4, this.logoBG);
                ps.setInt(5, this.logoBGColor);
                ps.setString(6, this.rankTitles[0]);
                ps.setString(7, this.rankTitles[1]);
                ps.setString(8, this.rankTitles[2]);
                ps.setString(9, this.rankTitles[3]);
                ps.setString(10, this.rankTitles[4]);
                ps.setInt(11, this.capacity);
                ps.setString(12, this.notice);
                ps.setInt(13, this.allianceid);
                ps.setInt(14, this.id);
                ps.execute();
                ps.close();
                ps = con.prepareStatement("DELETE FROM bbs_threads WHERE guildid = ?");
                ps.setInt(1, this.id);
                ps.execute();
                ps.close();
                ps = con.prepareStatement("DELETE FROM bbs_replies WHERE guildid = ?");
                ps.setInt(1, this.id);
                ps.execute();
                ps.close();
                ps = con.prepareStatement("INSERT INTO bbs_threads(`postercid`, `name`, `timestamp`, `icon`, `startpost`, `guildid`, `localthreadid`) VALUES(?, ?, ?, ?, ?, ?, ?)", 1);
                ps.setInt(6, this.id);
                for (final MapleBBSThread bb : this.bbs.values()) {
                    ps.setInt(1, bb.ownerID);
                    ps.setString(2, bb.name);
                    ps.setLong(3, bb.timestamp);
                    ps.setInt(4, bb.icon);
                    ps.setString(5, bb.text);
                    ps.setInt(7, bb.localthreadID);
                    ps.executeUpdate();
                    final ResultSet rs = ps.getGeneratedKeys();
                    if (!rs.next()) {
                        rs.close();
                    }
                    else {
                        final PreparedStatement pse = con.prepareStatement("INSERT INTO bbs_replies (`threadid`, `postercid`, `timestamp`, `content`, `guildid`) VALUES (?, ?, ?, ?, ?)");
                        pse.setInt(5, this.id);
                        for (final MapleBBSThread.MapleBBSReply r : bb.replies.values()) {
                            pse.setInt(1, rs.getInt(1));
                            pse.setInt(2, r.ownerID);
                            pse.setLong(3, r.timestamp);
                            pse.setString(4, r.content);
                            pse.execute();
                        }
                        pse.close();
                        rs.close();
                    }
                }
                ps.close();
            }
            else {
                PreparedStatement ps2 = con.prepareStatement("UPDATE characters SET guildid = 0, guildrank = 5, alliancerank = 5 WHERE guildid = ?");
                ps2.setInt(1, this.id);
                ps2.execute();
                ps2.close();
                ps2 = con.prepareStatement("DELETE FROM bbs_threads WHERE guildid = ?");
                ps2.setInt(1, this.id);
                ps2.execute();
                ps2.close();
                ps2 = con.prepareStatement("DELETE FROM bbs_replies WHERE guildid = ?");
                ps2.setInt(1, this.id);
                ps2.execute();
                ps2.close();
                ps2 = con.prepareStatement("DELETE FROM guilds WHERE guildid = ?");
                ps2.setInt(1, this.id);
                ps2.execute();
                ps2.close();
                if (this.allianceid > 0) {
                    final MapleGuildAlliance alliance = World.Alliance.getAlliance(this.allianceid);
                    if (alliance != null) {
                        alliance.removeGuild(this.id, false);
                    }
                }
                this.broadcast(MaplePacketCreator.guildDisband(this.id));
            }
        }
        catch (SQLException se) {
            System.err.println("Error saving guild to SQL");
            se.printStackTrace();
        }
    }
    
    public static void 杀怪排行榜(final MapleClient c, final int npcid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `shaguai`, `str`, `dex`, `int`, `luk` FROM characters ORDER BY `shaguai` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            c.getSession().write(MaplePacketCreator.杀怪排行榜(npcid, rs));
            ps.close();
            rs.close();
        }
        catch (Exception e) {
            System.err.println("获取杀怪排行出错" + e);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getLeaderId() {
        return this.leader;
    }
    
    public MapleCharacter getLeader(final MapleClient c) {
        return c.getChannelServer().getPlayerStorage().getCharacterById(this.leader);
    }
    
    public int getGP() {
        return this.gp;
    }
    
    public int getLogo() {
        return this.logo;
    }
    
    public void setLogo(final int l) {
        this.logo = l;
    }
    
    public int getLogoColor() {
        return this.logoColor;
    }
    
    public void setLogoColor(final int c) {
        this.logoColor = c;
    }
    
    public int getLogoBG() {
        return this.logoBG;
    }
    
    public void setLogoBG(final int bg) {
        this.logoBG = bg;
    }
    
    public int getLogoBGColor() {
        return this.logoBGColor;
    }
    
    public void setLogoBGColor(final int c) {
        this.logoBGColor = c;
    }
    
    public String getNotice() {
        if (this.notice == null) {
            return "";
        }
        return this.notice;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCapacity() {
        return this.capacity;
    }
    
    public int getSignature() {
        return this.signature;
    }
    
    public void broadcast(final MaplePacket packet) {
        this.broadcast(packet, -1, BCOp.NONE);
    }
    
    public void broadcast(final MaplePacket packet, final int exception) {
        this.broadcast(packet, exception, BCOp.NONE);
    }
    
    public void broadcast(final MaplePacket packet, final int exceptionId, final BCOp bcop) {
        this.wL.lock();
        try {
            this.buildNotifications();
        }
        finally {
            this.wL.unlock();
        }
        this.rL.lock();
        try {
            for (final MapleGuildCharacter mgc : this.members) {
                if (bcop == BCOp.DISBAND) {
                    if (mgc.isOnline()) {
                        World.Guild.setGuildAndRank(mgc.getId(), 0, 5, 5);
                    }
                    else {
                        setOfflineGuildStatus(0, (byte)5, (byte)5, mgc.getId());
                    }
                }
                else {
                    if (!mgc.isOnline() || mgc.getId() == exceptionId) {
                        continue;
                    }
                    if (bcop == BCOp.EMBELMCHANGE) {
                        World.Guild.changeEmblem(this.id, mgc.getId(), new MapleGuildSummary(this));
                    }
                    else {
                        World.Broadcast.sendGuildPacket(mgc.getId(), packet, exceptionId, this.id);
                    }
                }
            }
        }
        finally {
            this.rL.unlock();
        }
    }
    
    private void buildNotifications() {
        if (!this.bDirty) {
            return;
        }
        final List<Integer> mem = new LinkedList<Integer>();
        for (final MapleGuildCharacter mgc : this.members) {
            if (!mgc.isOnline()) {
                continue;
            }
            if (mem.contains(mgc.getId()) || mgc.getGuildId() != this.id) {
                this.members.remove(mgc);
            }
            else {
                mem.add(mgc.getId());
            }
        }
        this.bDirty = false;
    }
    
    public void setOnline(final int cid, final boolean online, final int channel) {
        boolean bBroadcast = true;
        for (final MapleGuildCharacter mgc : this.members) {
            if (mgc.getGuildId() == this.id && mgc.getId() == cid) {
                if (mgc.isOnline() == online) {
                    bBroadcast = false;
                }
                mgc.setOnline(online);
                mgc.setChannel((byte)channel);
                break;
            }
        }
        if (bBroadcast) {
            this.broadcast(MaplePacketCreator.guildMemberOnline(this.id, cid, online), cid);
            if (this.allianceid > 0) {
                World.Alliance.sendGuild(MaplePacketCreator.allianceMemberOnline(this.allianceid, this.id, cid, online), this.id, this.allianceid);
            }
        }
        this.bDirty = true;
        this.init = true;
    }
    
    public void guildChat(final String name, final int cid, final String msg) {
        this.broadcast(MaplePacketCreator.multiChat(name, msg, 2), cid);
    }
    
    public void allianceChat(final String name, final int cid, final String msg) {
        this.broadcast(MaplePacketCreator.multiChat(name, msg, 3), cid);
    }
    
    public String getRankTitle(final int rank) {
        return this.rankTitles[rank - 1];
    }
    
    public int getAllianceId() {
        return this.allianceid;
    }
    
    public int getInvitedId() {
        return this.invitedid;
    }
    
    public void setInvitedId(final int iid) {
        this.invitedid = iid;
    }
    
    public void setAllianceId(final int a) {
        this.allianceid = a;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE guilds SET alliance = ? WHERE guildid = ?");
            ps.setInt(1, a);
            ps.setInt(2, this.id);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Saving allianceid ERROR" + e);
        }
    }
    
    public int addGuildMember(final MapleGuildCharacter mgc) {
        this.wL.lock();
        try {
            if (this.members.size() >= this.capacity) {
                return 0;
            }
            for (int i = this.members.size() - 1; i >= 0; --i) {
                if (this.members.get(i).getGuildRank() < 5 || this.members.get(i).getName().compareTo(mgc.getName()) < 0) {
                    this.members.add(i + 1, mgc);
                    this.bDirty = true;
                    break;
                }
            }
        }
        finally {
            this.wL.unlock();
        }
        this.gainGP(50);
        this.broadcast(MaplePacketCreator.newGuildMember(mgc));
        if (this.allianceid > 0) {
            World.Alliance.sendGuild(this.allianceid);
        }
        return 1;
    }
    
    public void leaveGuild(final MapleGuildCharacter mgc) {
        this.broadcast(MaplePacketCreator.memberLeft(mgc, false));
        this.gainGP(-50);
        this.wL.lock();
        try {
            this.bDirty = true;
            this.members.remove(mgc);
            if (mgc.isOnline()) {
                World.Guild.setGuildAndRank(mgc.getId(), 0, 5, 5);
            }
            else {
                setOfflineGuildStatus(0, (byte)5, (byte)5, mgc.getId());
            }
            if (this.allianceid > 0) {
                World.Alliance.sendGuild(this.allianceid);
            }
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public void expelMember(final MapleGuildCharacter initiator, final String name, final int cid) {
        this.wL.lock();
        try {
            for (final MapleGuildCharacter mgc : this.members) {
                if (mgc.getId() == cid && initiator.getGuildRank() < mgc.getGuildRank()) {
                    this.broadcast(MaplePacketCreator.memberLeft(mgc, true));
                    this.bDirty = true;
                    this.gainGP(-50);
                    if (this.allianceid > 0) {
                        World.Alliance.sendGuild(this.allianceid);
                    }
                    if (mgc.isOnline()) {
                        World.Guild.setGuildAndRank(cid, 0, 5, 5);
                    }
                    else {
                        MapleCharacterUtil.sendNote(mgc.getName(), initiator.getName(), "You have been expelled from the guild.", 0);
                        setOfflineGuildStatus(0, (byte)5, (byte)5, cid);
                    }
                    this.members.remove(mgc);
                    break;
                }
            }
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public void changeARank() {
        this.changeARank(false);
    }
    
    public void changeARank(final boolean leader) {
        for (final MapleGuildCharacter mgc : this.members) {
            if (this.leader == mgc.getId()) {
                this.changeARank(mgc.getId(), leader ? 1 : 2);
            }
            else {
                this.changeARank(mgc.getId(), 3);
            }
        }
    }
    
    public void changeARank(final int newRank) {
        for (final MapleGuildCharacter mgc : this.members) {
            this.changeARank(mgc.getId(), newRank);
        }
    }
    
    public void changeARank(final int cid, final int newRank) {
        if (this.allianceid <= 0) {
            return;
        }
        for (final MapleGuildCharacter mgc : this.members) {
            if (cid == mgc.getId()) {
                if (mgc.isOnline()) {
                    World.Guild.setGuildAndRank(cid, this.id, mgc.getGuildRank(), newRank);
                }
                else {
                    setOfflineGuildStatus((short)this.id, mgc.getGuildRank(), (byte)newRank, cid);
                }
                mgc.setAllianceRank((byte)newRank);
                World.Alliance.sendGuild(this.allianceid);
                return;
            }
        }
        System.err.println("INFO: unable to find the correct id for changeRank({" + cid + "}, {" + newRank + "})");
    }
    
    public void changeRank(final int cid, final int newRank) {
        for (final MapleGuildCharacter mgc : this.members) {
            if (cid == mgc.getId()) {
                if (mgc.isOnline()) {
                    World.Guild.setGuildAndRank(cid, this.id, newRank, mgc.getAllianceRank());
                }
                else {
                    setOfflineGuildStatus((short)this.id, (byte)newRank, mgc.getAllianceRank(), cid);
                }
                mgc.setGuildRank((byte)newRank);
                this.broadcast(MaplePacketCreator.changeRank(mgc));
                return;
            }
        }
        System.err.println("INFO: unable to find the correct id for changeRank({" + cid + "}, {" + newRank + "})");
    }
    
    public void setGuildNotice(final String notice) {
        this.notice = notice;
        this.broadcast(MaplePacketCreator.guildNotice(this.id, notice));
    }
    
    public void memberLevelJobUpdate(final MapleGuildCharacter mgc) {
        for (final MapleGuildCharacter member : this.members) {
            if (member.getId() == mgc.getId()) {
                final int old_level = member.getLevel();
                final int old_job = member.getJobId();
                member.setJobId(mgc.getJobId());
                member.setLevel((short)mgc.getLevel());
                if (mgc.getLevel() > old_level) {
                    this.gainGP((mgc.getLevel() - old_level) * mgc.getLevel() / 10, false);
                }
                if (old_level != mgc.getLevel()) {}
                if (old_job != mgc.getJobId()) {}
                this.broadcast(MaplePacketCreator.guildMemberLevelJobUpdate(mgc));
                if (this.allianceid > 0) {
                    World.Alliance.sendGuild(MaplePacketCreator.updateAlliance(mgc, this.allianceid), this.id, this.allianceid);
                    break;
                }
                break;
            }
        }
    }
    
    public void changeRankTitle(final String[] ranks) {
        for (int i = 0; i < 5; ++i) {
            this.rankTitles[i] = ranks[i];
        }
        this.broadcast(MaplePacketCreator.rankTitleChange(this.id, ranks));
    }
    
    public void disbandGuild() {
        this.writeToDB(true);
        this.broadcast(null, -1, BCOp.DISBAND);
    }
    
    public void setGuildEmblem(final short bg, final byte bgcolor, final short logo, final byte logocolor) {
        this.logoBG = bg;
        this.logoBGColor = bgcolor;
        this.logo = logo;
        this.logoColor = logocolor;
        this.broadcast(null, -1, BCOp.EMBELMCHANGE);
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE guilds SET logo = ?, logoColor = ?, logoBG = ?, logoBGColor = ? WHERE guildid = ?");
            ps.setInt(1, logo);
            ps.setInt(2, this.logoColor);
            ps.setInt(3, this.logoBG);
            ps.setInt(4, this.logoBGColor);
            ps.setInt(5, this.id);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Saving guild logo / BG colo ERROR");
            e.printStackTrace();
        }
    }
    
    public MapleGuildCharacter getMGC(final int cid) {
        for (final MapleGuildCharacter mgc : this.members) {
            if (mgc.getId() == cid) {
                return mgc;
            }
        }
        return null;
    }
    
    public boolean increaseCapacity() {
        if (this.capacity >= 100 || this.capacity + 5 > 100) {
            return false;
        }
        this.capacity += 5;
        this.broadcast(MaplePacketCreator.guildCapacityChange(this.id, this.capacity));
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE guilds SET capacity = ? WHERE guildid = ?");
            ps.setInt(1, this.capacity);
            ps.setInt(2, this.id);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Saving guild capacity ERROR");
            e.printStackTrace();
        }
        return true;
    }
    
    public void gainGP(final int amount) {
        this.gainGP(amount, true);
    }
    
    public void gainGP(int amount, final boolean broadcast) {
        if (amount == 0) {
            return;
        }
        if (amount + this.gp < 0) {
            amount = -this.gp;
        }
        this.gp += amount;
        this.broadcast(MaplePacketCreator.updateGP(this.id, this.gp));
        if (broadcast) {
            this.broadcast(UIPacket.getGPMsg(amount));
        }
    }
    
    public void addMemberData(final MaplePacketLittleEndianWriter mplew) {
        mplew.write(this.members.size());
        for (final MapleGuildCharacter mgc : this.members) {
            mplew.writeInt(mgc.getId());
        }
        for (final MapleGuildCharacter mgc : this.members) {
            mplew.writeAsciiString(StringUtil.getRightPaddedStr(mgc.getName(), '\0', 13));
            mplew.writeInt(mgc.getJobId());
            mplew.writeInt(mgc.getLevel());
            mplew.writeInt(mgc.getGuildRank());
            mplew.writeInt(mgc.isOnline() ? 1 : 0);
            mplew.writeInt(this.signature);
            mplew.writeInt(mgc.getAllianceRank());
        }
    }
    
    public Collection<MapleGuildCharacter> getMembers() {
        return Collections.unmodifiableCollection((Collection<? extends MapleGuildCharacter>)this.members);
    }
    
    public boolean isInit() {
        return this.init;
    }
    
    public List<MapleBBSThread> getBBS() {
        final List<MapleBBSThread> ret = new ArrayList<MapleBBSThread>(this.bbs.values());
        Collections.sort(ret, new MapleBBSThread.ThreadComparator());
        return ret;
    }
    
    public int addBBSThread(final String title, final String text, final int icon, final boolean bNotice, final int posterID) {
        final int add = (this.bbs.get(0) == null) ? 1 : 0;
        final int ret = bNotice ? 0 : Math.max(1, this.bbs.size() + add);
        this.bbs.put(ret, new MapleBBSThread(ret, title, text, System.currentTimeMillis(), this.id, posterID, icon));
        return ret;
    }
    
    public void editBBSThread(final int localthreadid, final String title, final String text, final int icon, final int posterID, final int guildRank) {
        final MapleBBSThread thread = this.bbs.get(localthreadid);
        if (thread != null && (thread.ownerID == posterID || guildRank <= 2)) {
            this.bbs.put(localthreadid, new MapleBBSThread(localthreadid, title, text, System.currentTimeMillis(), this.id, thread.ownerID, icon));
        }
    }
    
    public void deleteBBSThread(final int localthreadid, final int posterID, final int guildRank) {
        final MapleBBSThread thread = this.bbs.get(localthreadid);
        if (thread != null && (thread.ownerID == posterID || guildRank <= 2)) {
            this.bbs.remove(localthreadid);
        }
    }
    
    public void addBBSReply(final int localthreadid, final String text, final int posterID) {
        final MapleBBSThread thread = this.bbs.get(localthreadid);
        if (thread != null) {
            thread.replies.put(thread.replies.size(), new MapleBBSThread.MapleBBSReply(thread.replies.size(), posterID, text, System.currentTimeMillis()));
        }
    }
    
    public void deleteBBSReply(final int localthreadid, final int replyid, final int posterID, final int guildRank) {
        final MapleBBSThread thread = this.bbs.get(localthreadid);
        if (thread != null) {
            final MapleBBSThread.MapleBBSReply reply = thread.replies.get(replyid);
            if (reply != null && (reply.ownerID == posterID || guildRank <= 2)) {
                thread.replies.remove(replyid);
            }
        }
    }
    
    public int getPrefix(final MapleCharacter chr) {
        return chr.getPrefix();
    }
    
    static {
        MapleGuild.serialVersionUID = 6322150443228168192L;
    }
    
    private enum BCOp
    {
        NONE, 
        DISBAND, 
        EMBELMCHANGE;
    }
}
