package client.inventory;

import java.io.*;
import database.*;
import java.sql.*;
import client.*;
import com.mysql.jdbc.exceptions.*;
import server.*;
import java.util.*;

public class MapleRing implements Serializable
{
    private static final long serialVersionUID = 9179541993413738579L;
    private final int ringId;
    private final int ringId2;
    private final int partnerId;
    private final int itemId;
    private String partnerName;
    private boolean equipped;
    
    public static Equip loadFromDb(final IItem ring) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM rings WHERE ringid = ?");
            ps.setInt(1, ring.getUniqueId());
            final ResultSet rs = ps.executeQuery();
            rs.next();
            final MapleRing ret = new MapleRing(ring.getItemId(), rs.getInt("partnerRingId"), rs.getInt("partnerChrId"), rs.getInt("itemid"), rs.getString("partnerName"));
            ret.setEquipped(false);
            final Equip eq = new Equip(ring.getItemId(), ring.getPosition(), ring.getUniqueId(), ring.getFlag());
            rs.close();
            ps.close();
            return eq;
        }
        catch (SQLException ex) {
            return null;
        }
    }
    
    public static MapleRing loadFromDb(final int ringId) {
        return loadFromDb(ringId, false);
    }
    
    public static MapleRing loadFromDb(final int ringId, final boolean equipped) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM rings WHERE ringId = ?");
            ps.setInt(1, ringId);
            final ResultSet rs = ps.executeQuery();
            MapleRing ret = null;
            if (rs.next()) {
                ret = new MapleRing(ringId, rs.getInt("partnerRingId"), rs.getInt("partnerChrId"), rs.getInt("itemid"), rs.getString("partnerName"));
                ret.setEquipped(equipped);
            }
            rs.close();
            ps.close();
            return ret;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void addToDB(final int itemid, final MapleCharacter chr, final String player, final int id, final int[] ringId) throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO rings (ringId, itemid, partnerChrId, partnerName, partnerRingId) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, ringId[0]);
        ps.setInt(2, itemid);
        ps.setInt(3, chr.getId());
        ps.setString(4, chr.getName());
        ps.setInt(5, ringId[1]);
        ps.executeUpdate();
        ps.close();
        ps = con.prepareStatement("INSERT INTO rings (ringId, itemid, partnerChrId, partnerName, partnerRingId) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, ringId[1]);
        ps.setInt(2, itemid);
        ps.setInt(3, id);
        ps.setString(4, player);
        ps.setInt(5, ringId[0]);
        ps.executeUpdate();
        ps.close();
    }
    
    public static int createRing(final int itemid, final MapleCharacter partner1, final String partner2, final String msg, final int id2, final int sn) {
        try {
            if (partner1 == null) {
                return -2;
            }
            if (id2 <= 0) {
                return -1;
            }
            return makeRing(itemid, partner1, partner2, id2, msg, sn);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int makeRing(final int itemid, final MapleCharacter partner1, final String partner2, final int id2, final String msg, final int sn) throws Exception {
        final int[] ringID = { MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance() };
        try {
            addToDB(itemid, partner1, partner2, id2, ringID);
        }
        catch (MySQLIntegrityConstraintViolationException mslcve) {
            return 0;
        }
        MapleInventoryManipulator.addRing(partner1, itemid, ringID[1], sn);
        partner1.getCashInventory().gift(id2, partner1.getName(), msg, sn, ringID[0]);
        return 1;
    }
    
    public static void removeRingFromDb(final MapleCharacter player) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM rings WHERE partnerChrId = ?");
            ps.setInt(1, player.getId());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                return;
            }
            final int otherId = rs.getInt("partnerRingId");
            final int otherotherId = rs.getInt("ringId");
            rs.close();
            ps.close();
            ps = con.prepareStatement("DELETE FROM rings WHERE ringId = ? OR ringId = ?");
            ps.setInt(1, otherotherId);
            ps.setInt(2, otherId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sex) {
            sex.printStackTrace();
        }
    }
    
    private MapleRing(final int id, final int id2, final int partnerId, final int itemid, final String partnerName) {
        this.equipped = false;
        this.ringId = id;
        this.ringId2 = id2;
        this.partnerId = partnerId;
        this.itemId = itemid;
        this.partnerName = partnerName;
    }
    
    public int getRingId() {
        return this.ringId;
    }
    
    public int getPartnerRingId() {
        return this.ringId2;
    }
    
    public int getPartnerChrId() {
        return this.partnerId;
    }
    
    public int getItemId() {
        return this.itemId;
    }
    
    public boolean isEquipped() {
        return this.equipped;
    }
    
    public void setEquipped(final boolean equipped) {
        this.equipped = equipped;
    }
    
    public String getPartnerName() {
        return this.partnerName;
    }
    
    public void setPartnerName(final String partnerName) {
        this.partnerName = partnerName;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MapleRing && ((MapleRing)o).getRingId() == this.getRingId();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.ringId;
        return hash;
    }
    
    public static class RingComparator implements Comparator<MapleRing>, Serializable
    {
        @Override
        public int compare(final MapleRing o1, final MapleRing o2) {
            if (o1.ringId < o2.ringId) {
                return -1;
            }
            if (o1.ringId == o2.ringId) {
                return 0;
            }
            return 1;
        }
    }
}
