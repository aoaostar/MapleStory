package client;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuddyEntry
{
    private final String name;
    private String group;
    private final int characterId;
    private final int level;
    private final int job;
    private boolean visible;
    private int channel;
    
    public static BuddyEntry getByNameFromDB(final String buddyName) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id, name, level, job FROM characters WHERE name = ?");
            ps.setString(1, buddyName);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BuddyEntry(rs.getString("name"), rs.getInt("id"), BuddyList.DEFAULT_GROUP, -1, false, rs.getInt("level"), rs.getInt("job"));
            }
            return null;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static BuddyEntry getByIdfFromDB(final int buddyCharId) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id, name, level, job FROM characters WHERE id = ?");
            ps.setInt(1, buddyCharId);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BuddyEntry(rs.getString("name"), rs.getInt("id"), BuddyList.DEFAULT_GROUP, -1, true, rs.getInt("level"), rs.getInt("job"));
            }
            return null;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public BuddyEntry(final String name, final int characterId, final String group, final int channel, final boolean visible, final int level, final int job) {
        this.name = name;
        this.characterId = characterId;
        this.group = group;
        this.channel = channel;
        this.visible = visible;
        this.level = level;
        this.job = job;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public void setChannel(final int channel) {
        this.channel = channel;
    }
    
    public boolean isOnline() {
        return this.channel >= 0;
    }
    
    public void setOffline() {
        this.channel = -1;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCharacterId() {
        return this.characterId;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getJob() {
        return this.job;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public void setGroup(final String newGroup) {
        this.group = newGroup;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.characterId;
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final BuddyEntry other = (BuddyEntry)obj;
        return this.characterId == other.characterId;
    }
}
