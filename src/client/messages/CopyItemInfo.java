package client.messages;

public class CopyItemInfo
{
    public int itemId;
    public int chrId;
    public String name;
    public boolean first;
    
    public CopyItemInfo(final int itemId, final int chrId, final String name) {
        this.itemId = itemId;
        this.chrId = chrId;
        this.name = name;
        this.first = true;
    }
    
    public boolean isFirst() {
        return this.first;
    }
    
    public void setFirst(final boolean f) {
        this.first = f;
    }
}
