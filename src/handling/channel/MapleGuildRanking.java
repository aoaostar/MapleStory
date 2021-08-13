package handling.channel;

import client.MapleClient;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import server.Timer;
import tools.MaplePacketCreator;

public class MapleGuildRanking
{
    private static final MapleGuildRanking instance;
    private final List<GuildRankingInfo> ranks;
    private final List<levelRankingInfo> ranks1;
    private final List<mesoRankingInfo> ranks2;
    
    public MapleGuildRanking() {
        this.ranks = new LinkedList<GuildRankingInfo>();
        this.ranks1 = new LinkedList<levelRankingInfo>();
        this.ranks2 = new LinkedList<mesoRankingInfo>();
    }
    
    public static MapleGuildRanking getInstance() {
        return MapleGuildRanking.instance;
    }
    
    public void RankingUpdate() {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                try {
                    MapleGuildRanking.this.reload();
                    MapleGuildRanking.this.showLevelRank();
                    MapleGuildRanking.this.showMesoRank();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println("Could not update rankings");
                }
            }
        }, 3600000L, 3600000L);
    }
    
    public List<GuildRankingInfo> getGuildRank() {
        if (this.ranks.isEmpty()) {
            this.reload();
        }
        return this.ranks;
    }
    
    public List<levelRankingInfo> getLevelRank() {
        if (this.ranks1.isEmpty()) {
            this.showLevelRank();
        }
        return this.ranks1;
    }
    
    public List<mesoRankingInfo> getMesoRank() {
        if (this.ranks2.isEmpty()) {
            this.showMesoRank();
        }
        return this.ranks2;
    }
    
    private void reload() {
        this.ranks.clear();
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM guilds ORDER BY `GP` DESC LIMIT 50")) {
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final GuildRankingInfo rank = new GuildRankingInfo(rs.getString("name"), rs.getInt("GP"), rs.getInt("logo"), rs.getInt("logoColor"), rs.getInt("logoBG"), rs.getInt("logoBGColor"));
                this.ranks.add(rank);
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("家族排行错误" + e);
        }
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
    
    private void showLevelRank() {
        this.ranks1.clear();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm < 1 ORDER BY `level` DESC LIMIT 100");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final levelRankingInfo rank1 = new levelRankingInfo(rs.getString("name"), rs.getInt("level"), rs.getInt("str"), rs.getInt("dex"), rs.getInt("int"), rs.getInt("luk"));
                this.ranks1.add(rank1);
            }
            ps.close();
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("人物排行错误");
        }
    }
    
    private void showMesoRank() {
        this.ranks2.clear();
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT *, ( chr.meso + s.meso ) as money FROM `characters` as chr , `storages` as s WHERE chr.gm < 1  AND s.accountid = chr.accountid ORDER BY money DESC LIMIT 20")) {
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final mesoRankingInfo rank2 = new mesoRankingInfo(rs.getString("name"), rs.getLong("money"), rs.getInt("str"), rs.getInt("dex"), rs.getInt("int"), rs.getInt("luk"));
                this.ranks2.add(rank2);
            }
            rs.close();
        }
        catch (SQLException e) {
            System.err.println("金币排行错误");
        }
    }
    
    static {
        instance = new MapleGuildRanking();
    }
    
    public static class mesoRankingInfo
    {
        private final String name;
        private final long meso;
        private final int str;
        private final int dex;
        private final int _int;
        private final int luk;
        
        public mesoRankingInfo(final String name, final long meso, final int str, final int dex, final int intt, final int luk) {
            this.name = name;
            this.meso = meso;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }
        
        public String getName() {
            return this.name;
        }
        
        public long getMeso() {
            return this.meso;
        }
        
        public int getStr() {
            return this.str;
        }
        
        public int getDex() {
            return this.dex;
        }
        
        public int getInt() {
            return this._int;
        }
        
        public int getLuk() {
            return this.luk;
        }
    }
    
    public static class levelRankingInfo
    {
        private final String name;
        private final int level;
        private final int str;
        private final int dex;
        private final int _int;
        private final int luk;
        
        public levelRankingInfo(final String name, final int level, final int str, final int dex, final int intt, final int luk) {
            this.name = name;
            this.level = level;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getLevel() {
            return this.level;
        }
        
        public int getStr() {
            return this.str;
        }
        
        public int getDex() {
            return this.dex;
        }
        
        public int getInt() {
            return this._int;
        }
        
        public int getLuk() {
            return this.luk;
        }
    }
    
    public static class GuildRankingInfo
    {
        private final String name;
        private final int gp;
        private final int logo;
        private final int logocolor;
        private final int logobg;
        private final int logobgcolor;
        
        public GuildRankingInfo(final String name, final int gp, final int logo, final int logocolor, final int logobg, final int logobgcolor) {
            this.name = name;
            this.gp = gp;
            this.logo = logo;
            this.logocolor = logocolor;
            this.logobg = logobg;
            this.logobgcolor = logobgcolor;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getGP() {
            return this.gp;
        }
        
        public int getLogo() {
            return this.logo;
        }
        
        public int getLogoColor() {
            return this.logocolor;
        }
        
        public int getLogoBg() {
            return this.logobg;
        }
        
        public int getLogoBgColor() {
            return this.logobgcolor;
        }
    }
}
