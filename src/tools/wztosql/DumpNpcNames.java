package tools.wztosql;

import database.DatabaseConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.StringUtil;

public class DumpNpcNames
{
    private final Connection con;
    private static final Map<Integer, String> npcNames;
    
    public DumpNpcNames() {
        this.con = DatabaseConnection.getConnection();
    }
    
    public static void main(final String[] args) throws SQLException {
        System.out.println("Dumping npc name data.");
        final DumpNpcNames dump = new DumpNpcNames();
        dump.dumpNpcNameData();
        System.out.println("Dump complete.");
    }
    
    public void dumpNpcNameData() throws SQLException {
        final File dataFile = new File(System.getProperty("wzPath") + "/Npc.wz");
        final File strDataFile = new File(System.getProperty("wzPath") + "/String.wz");
        final MapleDataProvider npcData = MapleDataProviderFactory.getDataProvider(dataFile);
        final MapleDataProvider stringDataWZ = MapleDataProviderFactory.getDataProvider(strDataFile);
        final MapleData npcStringData = stringDataWZ.getData("Npc.img");
        try (final PreparedStatement ps = this.con.prepareStatement("DELETE FROM `wz_npcnamedata`")) {
            ps.execute();
        }
        for (final MapleData c : npcStringData) {
            final int nid = Integer.parseInt(c.getName());
            final String n = StringUtil.getLeftPaddedStr(nid + ".img", '0', 11);
            try {
                if (npcData.getData(n) == null) {
                    continue;
                }
                final String name = MapleDataTool.getString("name", c, "MISSINGNO");
                if (name.contains("Maple TV") || name.contains("Baby Moon Bunny")) {
                    continue;
                }
                DumpNpcNames.npcNames.put(nid, name);
            }
            catch (NullPointerException ex2) {}
            catch (RuntimeException ex3) {}
        }
        for (final int key : DumpNpcNames.npcNames.keySet()) {
            try {
                try (final PreparedStatement ps2 = this.con.prepareStatement("INSERT INTO `wz_npcnamedata` (`npc`, `name`) VALUES (?, ?)")) {
                    ps2.setInt(1, key);
                    ps2.setString(2, DumpNpcNames.npcNames.get(key));
                    ps2.execute();
                }
                System.out.println("key: " + key + " name: " + DumpNpcNames.npcNames.get(key));
            }
            catch (Exception ex) {
                System.out.println("Failed to save key " + key);
            }
        }
    }
    
    static {
        npcNames = new HashMap<Integer, String>();
    }
}
