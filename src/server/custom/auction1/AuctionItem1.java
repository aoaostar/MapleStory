package server.custom.auction1;

import client.inventory.IItem;

public class AuctionItem1
{
    private long id;
    private int characterid;
    private String characterName;
    private AuctionState1 AuctionState1;
    private int buyer;
    private String buyerName;
    private int price;
    private IItem item;
    private int quantity;
    
    public long getId() {
        return this.id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }
    
    public IItem getItem() {
        return this.item;
    }
    
    public void setItem(final IItem item) {
        this.item = item;
    }
    
    public int getPrice() {
        return this.price;
    }
    
    public void setPrice(final int price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
    
    public AuctionState1 getAuctionState() {
        return this.AuctionState1;
    }
    
    public void setAuctionState(final AuctionState1 AuctionState1) {
        this.AuctionState1 = AuctionState1;
    }
    
    public int getCharacterid() {
        return this.characterid;
    }
    
    public void setCharacterid(final int characterid) {
        this.characterid = characterid;
    }
    
    public int getBuyer() {
        return this.buyer;
    }
    
    public void setBuyer(final int buyer) {
        this.buyer = buyer;
    }
    
    public String getCharacterName() {
        return this.characterName;
    }
    
    public void setCharacterName(final String characterName) {
        this.characterName = characterName;
    }
    
    public String getBuyerName() {
        return this.buyerName;
    }
    
    public void setBuyerName(final String buyerName) {
        this.buyerName = buyerName;
    }
    
    void setPoint(final int aInt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    long getPoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
