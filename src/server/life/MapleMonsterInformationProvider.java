package server.life;

import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapleMonsterInformationProvider
{
    private static final MapleMonsterInformationProvider instance;
    private final Map<Integer, List<MonsterDropEntry>> drops;
    private final List<MonsterGlobalDropEntry> globaldrops;
    
    public static MapleMonsterInformationProvider getInstance() {
        return MapleMonsterInformationProvider.instance;
    }
    
    protected MapleMonsterInformationProvider() {
        this.drops = new HashMap<Integer, List<MonsterDropEntry>>();
        this.globaldrops = new ArrayList<MonsterGlobalDropEntry>();
        this.retrieveGlobal();
    }
    
    public List<MonsterGlobalDropEntry> getGlobalDrop() {
        return this.globaldrops;
    }
    
    public void retrieveGlobal() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            final Connection con = DatabaseConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE chance > 0");
            rs = ps.executeQuery();
            while (rs.next()) {
                this.globaldrops.add(new MonsterGlobalDropEntry(rs.getInt("itemid"), rs.getInt("chance"), rs.getInt("continent"), rs.getByte("dropType"), rs.getInt("minimum_quantity"), rs.getInt("maximum_quantity"), rs.getShort("questid")));
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error retrieving drop" + e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException ex) {}
        }
    }
    
    public List<MonsterDropEntry> retrieveDrop(final int monsterId) {
        if (this.drops.containsKey(monsterId)) {
            return this.drops.get(monsterId);
        }
        final List<MonsterDropEntry> ret = new LinkedList<MonsterDropEntry>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE dropperid = ?");
            ps.setInt(1, monsterId);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int itemid = rs.getInt("itemid");
                int chance = rs.getInt("chance");
                if (GameConstants.getInventoryType(itemid) == MapleInventoryType.EQUIP) {
                    chance /= 3;
                }
                ret.add(new MonsterDropEntry(itemid, chance, rs.getInt("minimum_quantity"), rs.getInt("maximum_quantity"), rs.getShort("questid")));
            }
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException ignore) {
                return ret;
            }
        }
        catch (SQLException e) {
            return ret;
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException ignore2) {
                return ret;
            }
        }
        this.drops.put(monsterId, ret);
        return ret;
    }
    
    public void clearDrops() {
        this.drops.clear();
        this.globaldrops.clear();
        this.retrieveGlobal();
    }
    
    static {
        instance = new MapleMonsterInformationProvider();
    }
}
