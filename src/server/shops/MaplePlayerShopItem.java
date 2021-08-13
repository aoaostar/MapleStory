package server.shops;

import client.inventory.IItem;

public class MaplePlayerShopItem
{
    public IItem item;
    public short bundles;
    public int price;
    public byte flag;
    
    public MaplePlayerShopItem(final IItem item, final short bundles, final int price, final byte flag) {
        this.item = item;
        this.bundles = bundles;
        this.price = price;
        this.flag = flag;
    }
}
