package tools.wztosql;

import database.DatabaseConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;

public class DumpOxQuizData
{
    private final Connection con;
    static CharsetEncoder asciiEncoder;
    
    public DumpOxQuizData() {
        this.con = DatabaseConnection.getConnection();
    }
    
    public static void main(final String[] args) throws FileNotFoundException, IOException, SQLException {
        System.out.println("OXQuiz.img Loading ...");
        final DumpOxQuizData dump = new DumpOxQuizData();
        dump.dumpOxData();
        System.out.println("Ox quiz data is complete");
    }
    
    public void dumpOxData() throws SQLException {
        final MapleDataProvider stringProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Etc.wz"));
        final MapleData ox = stringProvider.getData("OXQuiz.img");
        PreparedStatement ps = this.con.prepareStatement("DELETE FROM `wz_oxdata`");
        ps.execute();
        ps.close();
        for (final MapleData child1 : ox.getChildren()) {
            for (final MapleData child2 : child1.getChildren()) {
                final MapleData q = child2.getChildByPath("q");
                final MapleData d = child2.getChildByPath("d");
                final int a = MapleDataTool.getInt(child2.getChildByPath("a"));
                String qs = "";
                String ds = "";
                String as;
                if (a == 0) {
                    as = "x";
                }
                else {
                    as = "o";
                }
                if (q != null) {
                    qs = (String)q.getData();
                }
                if (d != null) {
                    ds = (String)d.getData();
                }
                if (DumpOxQuizData.asciiEncoder.canEncode(child1.getName()) && DumpOxQuizData.asciiEncoder.canEncode(child2.getName()) && DumpOxQuizData.asciiEncoder.canEncode(qs) && DumpOxQuizData.asciiEncoder.canEncode(ds)) {
                    if (!DumpOxQuizData.asciiEncoder.canEncode(as)) {
                        continue;
                    }
                    ps = this.con.prepareStatement("INSERT INTO `wz_oxdata` (`questionset`, `questionid`, `question`, `display`, `answer`) VALUES (?, ?, ?, ?, ?)");
                    ps.setString(1, child1.getName());
                    ps.setString(2, child2.getName());
                    ps.setString(3, qs);
                    ps.setString(4, ds);
                    ps.setString(5, as);
                    ps.execute();
                    ps.close();
                }
            }
        }
    }
    
    static {
        DumpOxQuizData.asciiEncoder = Charset.forName("ISO-8859-1").newEncoder();
    }
}
