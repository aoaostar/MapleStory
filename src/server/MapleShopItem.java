package server;

public class MapleShopItem
{
    private final short buyable;
    private final int itemId;
    private final int price;
    
    public MapleShopItem(final short buyable, final int itemId, final int price) {
        this.buyable = buyable;
        this.itemId = itemId;
        this.price = price;
    }
    
    public short getBuyable() {
        return this.buyable;
    }
    
    public int getItemId() {
        return this.itemId;
    }
    
    public int getPrice() {
        return this.price;
    }
}
