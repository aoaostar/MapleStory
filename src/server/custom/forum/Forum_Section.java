package server.custom.forum;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tools.FileoutputUtil;

public class Forum_Section
{
    private int Id;
    private String Name;
    private static ArrayList<Forum_Section> AllSection;
    
    public Forum_Section() {
    }
    
    public Forum_Section(final int id, final String name) {
        this.Id = id;
        this.Name = name;
    }
    
    public static ArrayList<Forum_Section> getAllSection() {
        return Forum_Section.AllSection;
    }
    
    public static void setAllSection(final ArrayList<Forum_Section> allSection) {
        Forum_Section.AllSection = allSection;
    }
    
    public int getId() {
        return this.Id;
    }
    
    public void setId(final int id) {
        this.Id = id;
    }
    
    public String getName() {
        return this.Name;
    }
    
    public void setName(final String name) {
        this.Name = name;
    }
    
    public static ArrayList<Forum_Section> loadAllSection() {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM forum_section");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int id = rs.getInt("id");
                final String name = rs.getString("name");
                Forum_Section.AllSection.add(new Forum_Section(id, name));
            }
            rs.close();
            ps.close();
            Forum_Thread.loadAllThread();
            return Forum_Section.AllSection;
        }
        catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            return null;
        }
    }
    
    public static boolean addSection(final String name) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            if (getSectionByName(name) != null) {
                return false;
            }
            final StringBuilder query = new StringBuilder();
            query.append("INSERT INTO forum_section(name) VALUES (?)");
            final PreparedStatement ps = con.prepareStatement(query.toString());
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
            Forum_Section.AllSection.add(getSectionByNameToSql(name));
            return true;
        }
        catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            return false;
        }
    }
    
    public static boolean deleteSection(final int id) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            boolean isExist = false;
            if (getSectionById(id) != null) {
                Forum_Section.AllSection.remove(getSectionById(id));
                isExist = true;
            }
            if (!isExist) {
                return isExist;
            }
            Forum_Thread.deleteThread(id, 0, true);
            final StringBuilder query2 = new StringBuilder();
            query2.append("DELETE FROM forum_section WHERE id = ?");
            final PreparedStatement ps = con.prepareStatement(query2.toString());
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            return false;
        }
    }
    
    public static Forum_Section getSectionById(final int id) {
        final ArrayList<Forum_Section> allSection = getAllSection();
        for (final Forum_Section fs : allSection) {
            if (fs.getId() == id) {
                return fs;
            }
        }
        return null;
    }
    
    public static Forum_Section getSectionByIdToSql(final int id) {
        String name = "";
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM forum_section WHERE id = ?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            return new Forum_Section(id, name);
        }
        catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            return null;
        }
    }
    
    public static Forum_Section getSectionByName(final String name) {
        final ArrayList<Forum_Section> allSection = getAllSection();
        for (final Forum_Section fs : allSection) {
            if (fs.getName().equals(name)) {
                return fs;
            }
        }
        return null;
    }
    
    public static Forum_Section getSectionByNameToSql(final String name) {
        int id = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM forum_section WHERE name = ?");
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            return new Forum_Section(id, name);
        }
        catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            return null;
        }
    }
    
    static {
        Forum_Section.AllSection = new ArrayList<Forum_Section>();
    }
}
