package server.custom.auction;

public class AuctionPoint
{
    private int characterid;
    private long point;
    private long point_sell;
    private long point_buy;
    
    public int getCharacterid() {
        return this.characterid;
    }
    
    public void setCharacterid(final int characterid) {
        this.characterid = characterid;
    }
    
    public long getPoint() {
        return this.point;
    }
    
    public void setPoint(final long point) {
        this.point = point;
    }
    
    public long getPoint_sell() {
        return this.point_sell;
    }
    
    public void setPoint_sell(final long point_sell) {
        this.point_sell = point_sell;
    }
    
    public long getPoint_buy() {
        return this.point_buy;
    }
    
    public void setPoint_buy(final long point_buy) {
        this.point_buy = point_buy;
    }
}
