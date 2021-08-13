package server;

import java.util.HashMap;
import java.util.Map;

public class MapleShopFactory
{
    private static final MapleShopFactory instance;
    private final Map<Integer, MapleShop> shops;
    private final Map<Integer, MapleShop> npcShops;
    
    public MapleShopFactory() {
        this.shops = new HashMap<Integer, MapleShop>();
        this.npcShops = new HashMap<Integer, MapleShop>();
    }
    
    public static MapleShopFactory getInstance() {
        return MapleShopFactory.instance;
    }
    
    public void clear() {
        this.shops.clear();
        this.npcShops.clear();
    }
    
    public MapleShop getShop(final int shopId) {
        if (this.shops.containsKey(shopId)) {
            return this.shops.get(shopId);
        }
        return this.loadShop(shopId, true);
    }
    
    public MapleShop getShopForNPC(final int npcId) {
        if (this.npcShops.containsKey(npcId)) {
            return this.npcShops.get(npcId);
        }
        return this.loadShop(npcId, false);
    }
    
    private MapleShop loadShop(final int id, final boolean isShopId) {
        final MapleShop ret = MapleShop.createFromDB(id, isShopId);
        if (ret != null) {
            this.shops.put(ret.getId(), ret);
            this.npcShops.put(ret.getNpcId(), ret);
        }
        else if (isShopId) {
            this.shops.put(id, null);
        }
        else {
            this.npcShops.put(id, null);
        }
        return ret;
    }
    
    static {
        instance = new MapleShopFactory();
    }
}
