package client;

import java.io.*;
import database.*;
import java.sql.*;
import java.util.*;
import tools.*;

public class BuddyList implements Serializable
{
    public static String DEFAULT_GROUP;
    private Map<Integer, BuddyEntry> buddies;
    private byte capacity;
    private Deque<BuddyEntry> pendingReqs;
    private boolean changed;
    
    public static int getBuddyCount(final int chrId, final int pending) {
        int count = 0;
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as buddyCount FROM buddies WHERE characterid = ? AND pending = ?")) {
            ps.setInt(1, chrId);
            ps.setInt(2, pending);
            try (final ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("BuddyListHandler: getBuudyCount From DB is Error.");
                }
                count = rs.getInt("buddyCount");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }
    
    public static int getBuddyCapacity(final int charId) {
        int capacity = -1;
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT buddyCapacity FROM characters WHERE id = ?")) {
            ps.setInt(1, charId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    capacity = rs.getInt("buddyCapacity");
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return capacity;
    }
    
    public static int getBuddyPending(final int chrId, final int buddyId) {
        int pending = -1;
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT pending FROM buddies WHERE characterid = ? AND buddyid = ?")) {
            ps.setInt(1, chrId);
            ps.setInt(2, buddyId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pending = rs.getInt("pending");
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pending;
    }
    
    public static void addBuddyToDB(final MapleCharacter player, final BuddyEntry buddy) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("INSERT INTO buddies (`characterid`, `buddyid`, `groupname`, `pending`) VALUES (?, ?, ?, 1)")) {
                ps.setInt(1, buddy.getCharacterId());
                ps.setInt(2, player.getId());
                ps.setString(3, buddy.getGroup());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public BuddyList(final byte capacity) {
        this.pendingReqs = new LinkedList<BuddyEntry>();
        this.changed = false;
        this.buddies = new LinkedHashMap<Integer, BuddyEntry>();
        this.capacity = capacity;
    }
    
    public BuddyList(final int capacity) {
        this.pendingReqs = new LinkedList<BuddyEntry>();
        this.changed = false;
        this.buddies = new LinkedHashMap<Integer, BuddyEntry>();
        this.capacity = (byte)capacity;
    }
    
    public boolean contains(final int characterId) {
        return this.buddies.containsKey(characterId);
    }
    
    public boolean containsVisible(final int charId) {
        final BuddyEntry ble = this.buddies.get(charId);
        return ble != null && ble.isVisible();
    }
    
    public byte getCapacity() {
        return this.capacity;
    }
    
    public void setCapacity(final byte newCapacity) {
        this.capacity = newCapacity;
    }
    
    public BuddyEntry get(final int characterId) {
        return this.buddies.get(characterId);
    }
    
    public BuddyEntry get(final String characterName) {
        final String searchName = characterName.toLowerCase();
        for (final BuddyEntry ble : this.buddies.values()) {
            if (ble.getName().toLowerCase().equals(searchName)) {
                return ble;
            }
        }
        return null;
    }
    
    public void put(final BuddyEntry newEntry) {
        this.buddies.put(newEntry.getCharacterId(), newEntry);
        this.changed = true;
    }
    
    public void remove(final int characterId) {
        this.buddies.remove(characterId);
        this.changed = true;
    }
    
    public Collection<BuddyEntry> getBuddies() {
        return this.buddies.values();
    }
    
    public boolean isFull() {
        return this.buddies.size() >= this.capacity;
    }
    
    public Collection<Integer> getBuddiesIds() {
        return this.buddies.keySet();
    }
    
    public void loadFromTransfer(final Map<BuddyEntry, Boolean> data) {
        for (final Map.Entry<BuddyEntry, Boolean> qs : data.entrySet()) {
            final BuddyEntry buddyid = qs.getKey();
            final boolean pair = qs.getValue();
            if (!pair) {
                this.pendingReqs.push(buddyid);
            }
            else {
                this.put(new BuddyEntry(buddyid.getName(), buddyid.getCharacterId(), buddyid.getGroup(), -1, true, buddyid.getLevel(), buddyid.getJob()));
            }
        }
    }
    
    public void loadFromDb(final int characterId) throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT b.buddyid, b.pending, c.name as buddyname, c.job as buddyjob, c.level as buddylevel, b.groupname FROM buddies as b, characters as c WHERE c.id = b.buddyid AND b.characterid = ?");
        ps.setInt(1, characterId);
        final ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            final int buddyid = rs.getInt("buddyid");
            final String buddyname = rs.getString("buddyname");
            if (rs.getInt("pending") == 1) {
                this.pendingReqs.push(new BuddyEntry(buddyname, buddyid, rs.getString("groupname"), -1, false, rs.getInt("buddylevel"), rs.getInt("buddyjob")));
            }
            else {
                this.put(new BuddyEntry(buddyname, buddyid, rs.getString("groupname"), -1, true, rs.getInt("buddylevel"), rs.getInt("buddyjob")));
            }
        }
        rs.close();
        ps.close();
        ps = con.prepareStatement("DELETE FROM buddies WHERE pending = 1 AND characterid = ?");
        ps.setInt(1, characterId);
        ps.executeUpdate();
        ps.close();
    }
    
    public BuddyEntry pollPendingRequest() {
        return this.pendingReqs.pollLast();
    }
    
    public void addBuddyRequest(final MapleClient client, final int buddyId, final String buddyName, final int buddyChannel, final int buddyLevel, final int buddyJob) {
        this.put(new BuddyEntry(buddyName, buddyId, BuddyList.DEFAULT_GROUP, buddyChannel, false, buddyLevel, buddyJob));
        if (this.pendingReqs.isEmpty()) {
            client.sendPacket(MaplePacketCreator.requestBuddylistAdd(buddyId, buddyName, buddyLevel, buddyJob));
        }
        else {
            final BuddyEntry newPair = new BuddyEntry(buddyName, buddyId, BuddyList.DEFAULT_GROUP, -1, false, buddyJob, buddyLevel);
            this.pendingReqs.push(newPair);
        }
    }
    
    public void setChanged(final boolean v) {
        this.changed = v;
    }
    
    public boolean changed() {
        return this.changed;
    }
    
    static {
        BuddyList.DEFAULT_GROUP = "其他";
    }
    
    public enum BuddyOperation
    {
        ADDED, 
        DELETED;
    }
    
    public enum BuddyAddResult
    {
        BUDDYLIST_FULL, 
        ALREADY_ON_LIST, 
        OK;
    }
}
