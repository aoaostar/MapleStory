package client;

import database.DatabaseConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaplePvpStats implements Serializable
{
    private static final long serialVersionUID = -639523813413728519L;
    private int watk;
    private int matk;
    private int wdef;
    private int mdef;
    private int acc;
    private int avoid;
    private int wdef_rate;
    private int mdef_rate;
    private int ignore_def;
    private int damage_rate;
    private int ignore_damage;
    
    public MaplePvpStats(final int watk, final int matk, final int wdef, final int mdef, final int acc, final int avoid, final int wdef_rate, final int mdef_rate, final int ignore_def, final int damage_rate, final int ignore_damage) {
        this.watk = watk;
        this.matk = matk;
        this.wdef = wdef;
        this.mdef = mdef;
        this.acc = acc;
        this.avoid = avoid;
        this.wdef_rate = wdef_rate;
        this.mdef_rate = mdef_rate;
        this.ignore_def = ignore_def;
        this.damage_rate = damage_rate;
        this.ignore_damage = ignore_damage;
    }
    
    public static MaplePvpStats loadOrCreateFromDB(final int accountId) {
        MaplePvpStats ret = null;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM pvpstats WHERE accountid = ?");
            ps.setInt(1, accountId);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret = new MaplePvpStats(rs.getInt("watk"), rs.getInt("matk"), rs.getInt("wdef"), rs.getInt("mdef"), rs.getInt("acc"), rs.getInt("avoid"), rs.getInt("wdef_rate"), rs.getInt("mdef_rate"), rs.getInt("ignore_def"), rs.getInt("damage_rate"), rs.getInt("ignore_damage"));
            }
            else {
                final PreparedStatement psu = con.prepareStatement("INSERT INTO pvpstats (accountid, watk, matk, wdef, mdef, acc, avoid, wdef_rate, mdef_rate, ignore_def, damage_rate, ignore_damage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                psu.setInt(1, accountId);
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(4, 0);
                psu.setInt(5, 0);
                psu.setInt(6, 100);
                psu.setInt(7, 0);
                psu.setInt(8, 0);
                psu.setInt(9, 0);
                psu.setInt(10, 0);
                psu.setInt(11, 0);
                psu.setInt(12, 0);
                psu.executeUpdate();
                psu.close();
                ret = new MaplePvpStats(0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("加载角色 Pvp 属性出现错误." + ex);
        }
        return ret;
    }
    
    public void saveToDb(final int accountId) {
        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE pvpstats SET watk = ?, matk = ?, wdef = ?, mdef = ?, acc = ?, avoid = ?, wdef_rate = ?, mdef_rate = ?, ignore_def = ?, damage_rate = ?, ignore_damage = ? WHERE accountId = ?");
            ps.setInt(1, accountId);
            ps.setInt(2, this.watk);
            ps.setInt(3, this.matk);
            ps.setInt(4, this.wdef);
            ps.setInt(5, this.mdef);
            ps.setInt(6, this.acc);
            ps.setInt(7, this.avoid);
            ps.setInt(8, this.wdef_rate);
            ps.setInt(9, this.mdef_rate);
            ps.setInt(10, this.ignore_def);
            ps.setInt(11, this.damage_rate);
            ps.setInt(12, this.ignore_damage);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("保存角色 Pvp 属性出现错误." + ex);
        }
    }
    
    public int getWatk() {
        return this.watk;
    }
    
    public void setWatk(final int gain) {
        this.watk = gain;
    }
    
    public void gainWatk(final int gain) {
        this.watk += gain;
    }
    
    public int getMatk() {
        return this.matk;
    }
    
    public void setMatk(final int gain) {
        this.matk = gain;
    }
    
    public void gainMatk(final int gain) {
        this.matk += gain;
    }
    
    public int getWdef() {
        return this.wdef;
    }
    
    public void setWdef(final int gain) {
        this.wdef = gain;
    }
    
    public void gainWdef(final int gain) {
        this.wdef += gain;
    }
    
    public int getMdef() {
        return this.mdef;
    }
    
    public void setMdef(final int gain) {
        this.mdef = gain;
    }
    
    public void gainMdef(final int gain) {
        this.mdef += gain;
    }
    
    public int getAcc() {
        return this.acc;
    }
    
    public void setAcc(final int gain) {
        this.acc = gain;
    }
    
    public void gainAcc(final int gain) {
        this.acc += gain;
    }
    
    public int getAvoid() {
        return this.avoid;
    }
    
    public void setAvoid(final int gain) {
        this.avoid = gain;
    }
    
    public void gainAvoid(final int gain) {
        this.avoid += gain;
    }
    
    public int getWdefRate() {
        return this.wdef_rate;
    }
    
    public void setWdefRate(final int gain) {
        this.wdef_rate = gain;
    }
    
    public void gainWdefRate(final int gain) {
        this.wdef_rate += gain;
    }
    
    public int getMdefRate() {
        return this.mdef_rate;
    }
    
    public void setMdefRate(final int gain) {
        this.mdef_rate = gain;
    }
    
    public void gainMdefRate(final int gain) {
        this.mdef_rate += gain;
    }
    
    public int getIgnoreDef() {
        return this.ignore_def;
    }
    
    public void setIgnoreDef(final int gain) {
        this.ignore_def = gain;
    }
    
    public void gainIgnoreDef(final int gain) {
        this.ignore_def += gain;
    }
    
    public int getDamageRate() {
        return this.damage_rate;
    }
    
    public void setDamageRate(final int gain) {
        this.damage_rate = gain;
    }
    
    public void gainDamageRate(final int gain) {
        this.damage_rate += gain;
    }
    
    public int getIgnoreDamage() {
        return this.ignore_damage;
    }
    
    public void setIgnoreDamage(final int gain) {
        this.ignore_damage = gain;
    }
    
    public void gainIgnoreDamage(final int gain) {
        this.ignore_damage += gain;
    }
}
