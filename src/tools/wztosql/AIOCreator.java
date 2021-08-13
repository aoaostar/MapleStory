package tools.wztosql;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import server.MapleItemInformationProvider;
import tools.Pair;

public class AIOCreator
{
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final MapleItemInformationProvider provider = MapleItemInformationProvider.getInstance();
        for (final Pair<Integer, String> iteminfo : provider.getAllItems2()) {
            final int id = iteminfo.getLeft();
            if ((id / 10000 >= 100 && id / 10000 <= 153) || id / 10000 == 190 || id / 10000 == 191) {
                Lists.ALL.add(id);
            }
        }
        createSQLQuery();
    }
    
    public static void createSQLQuery() throws FileNotFoundException, IOException {
        final FileOutputStream out = new FileOutputStream("AIO.txt", false);
        final StringBuilder sb = new StringBuilder();
        for (int id = 100; id <= 153; ++id) {
            addLine(sb, "INSERT INTO `shops` (`shopid`, `npcid`) VALUES ('" + id * 1000 + "', '9900002');");
        }
        addLine(sb, "INSERT INTO `shops` (`shopid`, `npcid`) VALUES ('190000', '9900002');");
        addLine(sb, "INSERT INTO `shops` (`shopid`, `npcid`) VALUES ('191000', '9900002');");
        if (!Lists.ALL.isEmpty()) {
            for (final int id2 : Lists.ALL) {
                addLine(sb, "INSERT INTO `shopitems` (`shopid`, `itemid`, `price`, `position`, `reqitem`, `reqitemq`, `rank`, `buyable`, `category`, `minLevel`, `expiration`) VALUES ('" + id2 / 10000 * 1000 + "', '" + id2 + "', '" + getPrice(id2) + "', '0', '0', '0', '0', '0', '0', '0', '0');");
            }
        }
        System.out.println("Success");
        out.write(sb.toString().getBytes());
    }
    
    public static void addLine(final StringBuilder sb, final String string) {
        sb.append(string).append("\r\n");
    }
    
    public static int getPrice(final int id) {
        return 1;
    }
    
    public static class ItemType
    {
    }
    
    public static class WeaponType
    {
    }
    
    public static class Lists
    {
        public static List<Integer> ALL;
        
        static {
            Lists.ALL = new LinkedList<Integer>();
        }
    }
}
