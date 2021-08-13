package client;

import constants.GameConstants;
import database.DatabaseConnection;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import tools.Pair;

public class MapleCharacterUtil
{
    private static final Pattern namePattern;
    private static final Pattern petPattern;
    
    public static boolean canCreateChar(final String name) {
        return getIdByName(name) == -1 && isEligibleCharName(name);
    }
    
    public static boolean isEligibleCharName(final String name) {
        if (name.length() > 15) {
            return false;
        }
        if (name.length() < 2) {
            return false;
        }
        for (final String z : GameConstants.RESERVED) {
            if (name.indexOf(z) != -1) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean canChangePetName(final String name) {
        if (MapleCharacterUtil.petPattern.matcher(name).matches()) {
            for (final String z : GameConstants.RESERVED) {
                if (name.indexOf(z) != -1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static String makeMapleReadable(final String in) {
        String wui = in.replace('I', 'i');
        wui = wui.replace('l', 'L');
        wui = wui.replace("rn", "Rn");
        wui = wui.replace("vv", "Vv");
        wui = wui.replace("VV", "Vv");
        return wui;
    }
    
    public static int getIdByName(final String name) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT id FROM characters WHERE name = ?");
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final int id = rs.getInt("id");
            rs.close();
            ps.close();
            return id;
        }
        catch (SQLException e) {
            System.err.println("error 'getIdByName' " + e);
            return -1;
        }
    }
    
    public static boolean PromptPoll(final int accountid) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean prompt = false;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("SELECT * from game_poll_reply where AccountId = ?");
            ps.setInt(1, accountid);
            rs = ps.executeQuery();
            prompt = !rs.next();
        }
        catch (SQLException ex) {}
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException ex2) {}
        }
        return prompt;
    }
    
    public static boolean SetPoll(final int accountid, final int selection) {
        if (!PromptPoll(accountid)) {
            return false;
        }
        PreparedStatement ps = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO game_poll_reply (AccountId, SelectAns) VALUES (?, ?)");
            ps.setInt(1, accountid);
            ps.setInt(2, selection);
            ps.execute();
        }
        catch (SQLException ex) {}
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ex2) {}
        }
        return true;
    }
    
    public static int Change_SecondPassword(final int accid, final String password, final String newpassword) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * from accounts where id = ?");
            ps.setInt(1, accid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            String secondPassword = rs.getString("2ndpassword");
            final String salt2 = rs.getString("salt2");
            if (secondPassword != null && salt2 != null) {
                secondPassword = LoginCrypto.rand_r(secondPassword);
            }
            else if (secondPassword == null && salt2 == null) {
                rs.close();
                ps.close();
                return 0;
            }
            if (!check_ifPasswordEquals(secondPassword, password, salt2)) {
                rs.close();
                ps.close();
                return 1;
            }
            rs.close();
            ps.close();
            String SHA1hashedsecond;
            try {
                SHA1hashedsecond = LoginCryptoLegacy.encodeSHA1(newpassword);
            }
            catch (UnsupportedEncodingException | NoSuchAlgorithmException ex2) {
                ex2.printStackTrace();
                return -2;
            }
            ps = con.prepareStatement("UPDATE accounts set 2ndpassword = ?, salt2 = ? where id = ?");
            ps.setString(1, SHA1hashedsecond);
            ps.setString(2, null);
            ps.setInt(3, accid);
            if (!ps.execute()) {
                ps.close();
                return 2;
            }
            ps.close();
            return -2;
        }
        catch (SQLException e2) {
            System.err.println("error 'getIdByName' " + e2);
            return -2;
        }
    }
    
    private static boolean check_ifPasswordEquals(final String passhash, final String pwd, final String salt) {
        return (LoginCryptoLegacy.isLegacyPassword(passhash) && LoginCryptoLegacy.checkPassword(pwd, passhash)) || (salt == null && LoginCrypto.checkSha1Hash(passhash, pwd)) || LoginCrypto.checkSaltedSha512Hash(passhash, pwd, salt);
    }
    
    public static Pair<Integer, Pair<Integer, Integer>> getInfoByName(final String name, final int world) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE name = ? AND world = ?");
            ps.setString(1, name);
            ps.setInt(2, world);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return null;
            }
            final Pair<Integer, Pair<Integer, Integer>> id = new Pair<Integer, Pair<Integer, Integer>>(rs.getInt("id"), new Pair<Integer, Integer>(rs.getInt("accountid"), rs.getInt("gender")));
            rs.close();
            ps.close();
            return id;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setNXCodeUsed(final String name, final String code) throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("UPDATE nxcode SET `user` = ?, `valid` = 0 WHERE code = ?");
        ps.setString(1, name);
        ps.setString(2, code);
        ps.execute();
        ps.close();
    }
    
    public static void sendNote(final String to, final String name, final String msg, final int fame) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("INSERT INTO notes (`to`, `from`, `message`, `timestamp`, `gift`) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, to);
            ps.setString(2, name);
            ps.setString(3, msg);
            ps.setLong(4, System.currentTimeMillis());
            ps.setInt(5, fame);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Unable to send note" + e);
        }
    }
    
    public static boolean getNXCodeValid(final String code, boolean validcode) throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("SELECT `valid` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            validcode = (rs.getInt("valid") > 0);
        }
        rs.close();
        ps.close();
        return validcode;
    }
    
    public static int getNXCodeType(final String code) throws SQLException {
        int type = -1;
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("SELECT `type` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            type = rs.getInt("type");
        }
        rs.close();
        ps.close();
        return type;
    }
    
    public static int getNXCodeItem(final String code) throws SQLException {
        int item = -1;
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("SELECT `item` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            item = rs.getInt("item");
        }
        rs.close();
        ps.close();
        return item;
    }
    
    static {
        namePattern = Pattern.compile("[a-zA-Z0-9_-]{3,12}");
        petPattern = Pattern.compile("[a-zA-Z0-9_-]{4,12}");
    }
}
