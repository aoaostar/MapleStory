package client;

import database.DatabaseConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import tools.Pair;
import tools.data.output.MaplePacketLittleEndianWriter;


public class MapleKeyLayout implements Serializable
{
    private static long serialVersionUID;
    private boolean changed;
    private Map<Integer, Pair<Byte, Integer>> keymap;
    
    public MapleKeyLayout() {
        this.changed = false;
        this.keymap = new HashMap<Integer, Pair<Byte, Integer>>();
    }
    
    public MapleKeyLayout(final Map<Integer, Pair<Byte, Integer>> keys) {
        this.changed = false;
        this.keymap = keys;
    }
    
    public Map<Integer, Pair<Byte, Integer>> Layout() {
        this.changed = true;
        return this.keymap;
    }
    
    public void writeData(final MaplePacketLittleEndianWriter mplew) {
        for (int x = 0; x < 90; ++x) {
            final Pair<Byte, Integer> binding = this.keymap.get(x);
            if (binding != null) {
                mplew.write(binding.getLeft());
                mplew.writeInt(binding.getRight());
            }
            else {
                mplew.write(0);
                mplew.writeInt(0);
            }
        }
    }
    
    public void saveKeys(final int charid) throws SQLException {
        if (!this.changed || this.keymap.size() == 0) {
            return;
        }
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM keymap WHERE `characterid` = ?");
        ps.setInt(1, charid);
        rs = ps.executeQuery();
        while (rs.next()) {
            final int key = rs.getInt("key");
            boolean find = false;
            for (final Map.Entry<Integer, Pair<Byte, Integer>> keybinding : this.keymap.entrySet()) {
                if (key == keybinding.getKey()) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                ps2 = con.prepareStatement("DELETE FROM keymap WHERE `characterid` = ? AND `key` = ?");
                ps2.setInt(1, charid);
                ps2.setInt(2, key);
                ps2.execute();
                ps2.close();
            }
        }
        ps.close();
        rs.close();
        ps = con.prepareStatement("SELECT * FROM keymap WHERE `characterid` = ? AND `key` = ? LIMIT 1");
        ps.setInt(1, charid);
        for (final Map.Entry<Integer, Pair<Byte, Integer>> keybinding2 : this.keymap.entrySet()) {
            final int key2 = keybinding2.getKey();
            ps.setInt(2, key2);
            rs = ps.executeQuery();
            if (rs.next()) {
                ps2 = con.prepareStatement("UPDATE keymap SET `type` = ?, `action` = ? WHERE `characterid` = ? AND `key` = ?");
                ps2.setInt(1, keybinding2.getValue().getLeft());
                ps2.setInt(2, keybinding2.getValue().getRight());
                ps2.setInt(3, charid);
                ps2.setInt(4, key2);
                ps2.executeUpdate();
                ps2.close();
            }
            else {
                ps2 = con.prepareStatement("INSERT INTO keymap (`characterid`, `key`, `type`, `action`) VALUES (?, ?, ?, ?)");
                ps2.setInt(1, charid);
                ps2.setInt(2, key2);
                ps2.setInt(3, keybinding2.getValue().getLeft());
                ps2.setInt(4, keybinding2.getValue().getRight());
                ps2.execute();
                ps2.close();
            }
            rs.close();
        }
        ps.close();
    }
    
    static {
        MapleKeyLayout.serialVersionUID = 9179541993413738569L;
    }
}
