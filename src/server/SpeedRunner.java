package server;

import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import server.maps.SpeedRunType;
import tools.Pair;
import tools.StringUtil;

public class SpeedRunner
{
    private static final SpeedRunner instance;
    private final Map<SpeedRunType, Pair<String, Map<Integer, String>>> speedRunData;
    
    public static SpeedRunner getInstance() {
        return SpeedRunner.instance;
    }
    
    private SpeedRunner() {
        this.speedRunData = new EnumMap<SpeedRunType, Pair<String, Map<Integer, String>>>(SpeedRunType.class);
    }
    
    public Pair<String, Map<Integer, String>> getSpeedRunData(final SpeedRunType type) {
        return this.speedRunData.get(type);
    }
    
    public void addSpeedRunData(final SpeedRunType type, final Pair<StringBuilder, Map<Integer, String>> mib) {
        this.speedRunData.put(type, new Pair<String, Map<Integer, String>>(mib.getLeft().toString(), mib.getRight()));
    }
    
    public void removeSpeedRunData(final SpeedRunType type) {
        this.speedRunData.remove(type);
    }
    
    public void loadSpeedRuns() throws SQLException {
        if (this.speedRunData.size() > 0) {
            return;
        }
        for (final SpeedRunType type : SpeedRunType.values()) {
            this.loadSpeedRunData(type);
        }
    }
    
    public void loadSpeedRunData(final SpeedRunType type) throws SQLException {
        final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM speedruns WHERE type = ? ORDER BY time LIMIT 25");
        ps.setString(1, type.name());
        final StringBuilder ret = new StringBuilder("#rThese are the speedrun times for " + StringUtil.makeEnumHumanReadable(type.name()) + ".#k\r\n\r\n");
        final Map<Integer, String> rett = new LinkedHashMap<Integer, String>();
        final ResultSet rs = ps.executeQuery();
        int rank = 1;
        boolean changed;
        for (boolean cont = changed = rs.first(); cont; cont = rs.next()) {
            this.addSpeedRunData(ret, rett, rs.getString("members"), rs.getString("leader"), rank, rs.getString("timestring"));
            ++rank;
        }
        rs.close();
        ps.close();
        if (changed) {
            this.speedRunData.put(type, new Pair<String, Map<Integer, String>>(ret.toString(), rett));
        }
    }
    
    public Pair<StringBuilder, Map<Integer, String>> addSpeedRunData(final StringBuilder ret, final Map<Integer, String> rett, final String members, final String leader, final int rank, final String timestring) {
        final StringBuilder rettt = new StringBuilder();
        final String[] membrz = members.split(",");
        rettt.append("#b该远征队 ").append(leader).append("'成功挑战排名为 ").append(rank).append(".#k\r\n\r\n");
        for (int i = 0; i < membrz.length; ++i) {
            rettt.append("#r#e");
            rettt.append(i + 1);
            rettt.append(".#n ");
            rettt.append(membrz[i]);
            rettt.append("#k\r\n");
        }
        rett.put(rank, rettt.toString());
        ret.append("#b");
        if (membrz.length > 1) {
            ret.append("#L");
            ret.append(rank);
            ret.append("#");
        }
        ret.append("Rank #e");
        ret.append(rank);
        ret.append("#n#k : ");
        ret.append(leader);
        ret.append(", in ");
        ret.append(timestring);
        if (membrz.length > 1) {
            ret.append("#l");
        }
        ret.append("\r\n");
        return new Pair<StringBuilder, Map<Integer, String>>(ret, rett);
    }
    
    static {
        instance = new SpeedRunner();
    }
}
