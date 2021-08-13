package tools;

import client.inventory.MapleInventoryType;
import database.DatabaseConnection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.CashItemFactory;
import server.CashItemInfo;
import server.MapleItemInformationProvider;

public class CashShopDumper
{
    private static final MapleDataProvider data;
    
    public static CashItemInfo.CashModInfo getModInfo(final int sn) {
        CashItemInfo.CashModInfo ret = null;
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?")) {
            ps.setInt(1, sn);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = new CashItemInfo.CashModInfo(sn, rs.getInt("discount_price"), rs.getInt("mark"), rs.getInt("showup") > 0, rs.getInt("itemid"), rs.getInt("priority"), rs.getInt("package") > 0, rs.getInt("period"), rs.getInt("gender"), rs.getInt("count"), rs.getInt("meso"), rs.getInt("unk_1"), rs.getInt("unk_2"), rs.getInt("unk_3"), rs.getInt("extra_flags"));
                }
            }
        }
        catch (Exception ex) {
            FilePrinter.printError("CashShopDumper.txt", ex);
        }
        return ret;
    }
    
    public static void main(final String[] args) {
        final CashItemInfo.CashModInfo m = getModInfo(20000393);
        CashItemFactory.getInstance().initialize();
        final Collection<CashItemInfo.CashModInfo> list = CashItemFactory.getInstance().getAllModInfo();
        final Connection con = DatabaseConnection.getConnection();
        final List<Integer> itemids = new ArrayList<Integer>();
        final List<Integer> qq = new ArrayList<Integer>();
        final Map<Integer, List<String>> dics = new HashMap<Integer, List<String>>();
        for (final MapleData field : CashShopDumper.data.getData("Commodity.img").getChildren()) {
            try {
                final int itemId = MapleDataTool.getIntConvert("ItemId", field, 0);
                final int sn = MapleDataTool.getIntConvert("SN", field, 0);
                final int count = MapleDataTool.getIntConvert("Count", field, 0);
                final int price = MapleDataTool.getIntConvert("Price", field, 0);
                final int priority = MapleDataTool.getIntConvert("Priority", field, 0);
                final int period = MapleDataTool.getIntConvert("Period", field, 0);
                final int gender = MapleDataTool.getIntConvert("Gender", field, -1);
                final int meso = MapleDataTool.getIntConvert("Meso", field, 0);
                if (itemId == 0) {
                    continue;
                }
                final int cat = itemId / 10000;
                if (dics.get(cat) == null) {
                    dics.put(cat, new ArrayList<String>());
                }
                boolean check = false;
                if (meso > 0) {
                    check = true;
                }
                if (MapleItemInformationProvider.getInstance().getInventoryType(itemId) == MapleInventoryType.EQUIP && !MapleItemInformationProvider.getInstance().isCashItem(itemId)) {
                    check = true;
                }
                if (MapleItemInformationProvider.getInstance().getInventoryType(itemId) == MapleInventoryType.EQUIP && period > 0) {
                    check = true;
                }
                if (check) {
                    System.out.println(MapleItemInformationProvider.getInstance().getName(itemId));
                }
                else {
                    final PreparedStatement ps = con.prepareStatement("INSERT INTO cashshop_modified_items (serial, showup,itemid,priority,period,gender,count,meso,discount_price,mark, unk_1, unk_2, unk_3) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setInt(1, sn);
                    ps.setInt(2, 1);
                    ps.setInt(3, itemId);
                    ps.setInt(4, 0);
                    ps.setInt(5, period);
                    ps.setInt(6, gender);
                    ps.setInt(7, (count > 1) ? count : 0);
                    ps.setInt(8, meso);
                    ps.setInt(9, 0);
                    ps.setInt(10, 0);
                    ps.setInt(11, 0);
                    ps.setInt(12, 0);
                    ps.setInt(13, 0);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            catch (SQLException ex) {
                FilePrinter.printError("CashShopDumper.txt", ex);
            }
        }
        for (final Integer key : dics.keySet()) {
            final File fout = new File("cashshopItems/" + key.toString() + ".sql");
            final List<String> l = dics.get(key);
            FileOutputStream fos = null;
            try {
                if (!fout.exists()) {
                    fout.createNewFile();
                }
                fos = new FileOutputStream(fout);
                final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for (int i = 0; i < l.size(); ++i) {
                    bw.write(l.get(i));
                    bw.newLine();
                }
                bw.close();
            }
            catch (FileNotFoundException ex2) {
                FilePrinter.printError("CashShopDumper.txt", ex2);
            }
            catch (IOException ex3) {
                FilePrinter.printError("CashShopDumper.txt", ex3);
            }
            finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                }
                catch (IOException ex4) {
                    FilePrinter.printError("CashShopDumper.txt", ex4);
                }
            }
        }
    }
    
    static {
        data = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Etc.wz"));
    }
}
