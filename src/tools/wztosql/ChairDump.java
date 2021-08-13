package tools.wztosql;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import server.MapleItemInformationProvider;
import tools.Pair;

public class ChairDump
{
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final FileOutputStream out = new FileOutputStream("ChairDump.txt", false);
        final StringBuilder sb = new StringBuilder();
        final int shopId = 145274;
        final int npcId = 9010000;
        sb.append("INSERT INTO shops (`shopid`, `npcid`) VALUES(").append(shopId).append(", ").append(npcId).append(");\r\n");
        final int price = 1;
        for (final Pair<Integer, String> item : MapleItemInformationProvider.getInstance().getAllItems2()) {
            if (item.getLeft() >= 3010000 && item.getLeft() < 3020000) {
                sb.append("INSERT INTO shopitems (`shopid`, `itemid`, `price`, `position`) VALUES(").append(shopId).append(", ").append(item.getLeft()).append(", ").append(price).append(", 0);\r\n");
            }
        }
        out.write(sb.toString().getBytes());
    }
}
