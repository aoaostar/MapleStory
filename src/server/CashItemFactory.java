package server;

import database.DatabaseConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;

public class CashItemFactory
{
    private static final CashItemFactory instance;
    private static final int[] bestItems;
    private boolean initialized;
    private final Map<Integer, CashItemInfo> itemStats;
    private final Map<Integer, List<CashItemInfo>> itemPackage;
    private final Map<Integer, CashItemInfo.CashModInfo> itemMods;
    private final MapleDataProvider data;
    private final MapleDataProvider itemStringInfo;
    private final Map<Integer, Integer> idLookup;
    
    protected CashItemFactory() {
        this.initialized = false;
        this.itemStats = new HashMap<Integer, CashItemInfo>();
        this.itemPackage = new HashMap<Integer, List<CashItemInfo>>();
        this.itemMods = new HashMap<Integer, CashItemInfo.CashModInfo>();
        this.data = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Etc.wz"));
        this.itemStringInfo = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/String.wz"));
        this.idLookup = new HashMap<Integer, Integer>();
    }
    
    public static CashItemFactory getInstance() {
        return CashItemFactory.instance;
    }
    
    public void initialize() {
        System.out.println("商城 :::");
        final List<Integer> itemids = new ArrayList<Integer>();
        for (final MapleData field : this.data.getData("Commodity.img").getChildren()) {
            final int SN = MapleDataTool.getIntConvert("SN", field, 0);
            final int itemId = MapleDataTool.getIntConvert("ItemId", field, 0);
            final CashItemInfo stats = new CashItemInfo(MapleDataTool.getIntConvert("ItemId", field, 0), MapleDataTool.getIntConvert("Count", field, 1), MapleDataTool.getIntConvert("Price", field, 0), SN, MapleDataTool.getIntConvert("Period", field, 0), MapleDataTool.getIntConvert("Gender", field, 2), MapleDataTool.getIntConvert("OnSale", field, 0) > 0 && MapleDataTool.getIntConvert("Price", field, 0) > 0);
            if (SN > 0) {
                this.itemStats.put(SN, stats);
                this.idLookup.put(itemId, SN);
            }
            if (itemId > 0) {
                itemids.add(itemId);
            }
        }
        for (final int i : itemids) {
            this.getPackageItems(i);
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items");
                 final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final CashItemInfo.CashModInfo ret = new CashItemInfo.CashModInfo(rs.getInt("serial"), rs.getInt("discount_price"), rs.getInt("mark"), rs.getInt("showup") > 0, rs.getInt("itemid"), rs.getInt("priority"), rs.getInt("package") > 0, rs.getInt("period"), rs.getInt("gender"), rs.getInt("count"), rs.getInt("meso"), rs.getInt("unk_1"), rs.getInt("unk_2"), rs.getInt("unk_3"), rs.getInt("extra_flags"));
                    if (ret.showUp) {
                        this.itemMods.put(ret.sn, ret);
                        final CashItemInfo cc = this.itemStats.get(ret.sn);
                        if (cc == null) {
                            continue;
                        }
                        ret.toCItem(cc);
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (final int i : this.itemStats.keySet()) {
            this.getItem(i);
        }
        this.initialized = true;
    }
    
    public CashItemInfo getItem(final int sn) {
        final CashItemInfo stats = this.itemStats.get(sn);
        final CashItemInfo.CashModInfo z = this.getModInfo(sn);
        if (z != null && z.showUp) {
            return z.toCItem(stats);
        }
        if (stats == null || !stats.onSale()) {
            return null;
        }
        return stats;
    }
    
    public List<CashItemInfo> getPackageItems(final int itemId) {
        if (this.itemPackage.get(itemId) != null) {
            return this.itemPackage.get(itemId);
        }
        final List<CashItemInfo> packageItems = new ArrayList<CashItemInfo>();
        final MapleData b = this.data.getData("CashPackage.img");
        if (b == null || b.getChildByPath(itemId + "/SN") == null) {
            return null;
        }
        for (final MapleData d : b.getChildByPath(itemId + "/SN").getChildren()) {
            packageItems.add(this.itemStats.get(MapleDataTool.getIntConvert(d)));
        }
        this.itemPackage.put(itemId, packageItems);
        return packageItems;
    }
    
    public CashItemInfo.CashModInfo getModInfo(final int sn) {
        return this.itemMods.get(sn);
    }
    
    public Collection<CashItemInfo.CashModInfo> getAllModInfo() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.itemMods.values();
    }
    
    public int[] getBestItems() {
        return CashItemFactory.bestItems;
    }
    
    public int getSnFromId(final int itemId) {
        return this.idLookup.get(itemId);
    }
    
    public void clearCashShop() {
        this.itemStats.clear();
        this.itemPackage.clear();
        this.itemMods.clear();
        this.idLookup.clear();
        this.initialized = false;
        this.initialize();
    }
    
    public int getItemSN(final int itemid) {
        for (final Map.Entry<Integer, CashItemInfo> ci : this.itemStats.entrySet()) {
            if (ci.getValue().getId() == itemid) {
                return ci.getValue().getSN();
            }
        }
        return 0;
    }
    
    static {
        instance = new CashItemFactory();
        bestItems = new int[] { 10099994, 20490094, 10099993, 60090092, 50290004 };
    }
}
