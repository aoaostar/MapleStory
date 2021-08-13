package server;

public class CashItem
{
    private final int category;
    private final int subcategory;
    private final int parent;
    private final int sn;
    private final int itemid;
    private final int flag;
    private final int price;
    private final int discountPrice;
    private final int quantity;
    private final int expire;
    private final int gender;
    private final int likes;
    private final String image;
    
    public CashItem(final int category, final int subcategory, final int parent, final String image, final int sn, final int itemid, final int flag, final int price, final int discountPrice, final int quantity, final int expire, final int gender, final int likes) {
        this.category = category;
        this.subcategory = subcategory;
        this.parent = parent;
        this.image = image;
        this.sn = sn;
        this.itemid = itemid;
        this.flag = flag;
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.expire = expire;
        this.gender = gender;
        this.likes = likes;
    }
    
    public int getCategory() {
        return this.category;
    }
    
    public int getSubCategory() {
        return this.subcategory;
    }
    
    public int getParent() {
        return this.parent;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public int getSN() {
        return this.sn;
    }
    
    public int getItemId() {
        return this.itemid;
    }
    
    public int getFlag() {
        return this.flag;
    }
    
    public int getPrice() {
        return this.price;
    }
    
    public int getDiscountPrice() {
        return this.discountPrice;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public int getExpire() {
        return this.expire;
    }
    
    public int getGender() {
        return this.gender;
    }
    
    public int getLikes() {
        return this.likes;
    }
}
