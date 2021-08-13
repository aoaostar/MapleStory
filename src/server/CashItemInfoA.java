package server;

import client.inventory.MapleInventoryType;

public class CashItemInfoA
{
    private final int SN;
    private final int itemId;
    private final int count;
    private final int price;
    private final int period;
    private final int gender;
    private final boolean onSale;
    
    public static MapleInventoryType getInventoryType(final int itemId) {
        final byte type = (byte)(itemId / 1000000);
        if (type < 1 || type > 5) {
            return MapleInventoryType.UNDEFINED;
        }
        return MapleInventoryType.getByType(type);
    }
    
    public CashItemInfoA(final int SN, final int itemId, final int count, final int price, final int period, final int gender, final boolean onSale) {
        this.SN = SN;
        this.itemId = itemId;
        this.count = count;
        this.price = price;
        this.period = period;
        this.gender = gender;
        this.onSale = onSale;
    }
    
    public int getSN() {
        return this.SN;
    }
    
    public int getId() {
        return this.itemId;
    }
    
    public boolean genderEquals(final int g) {
        return g == this.gender || this.gender == 2;
    }
    
    public int getItemId() {
        return this.itemId;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int getPrice() {
        return this.price;
    }
    
    public int getPeriod() {
        return this.period;
    }
    
    public int getGender() {
        return this.gender;
    }
    
    public boolean onSale() {
        return this.onSale;
    }
    
    public int getItemId(final int i) {
        return this.itemId;
    }
}
