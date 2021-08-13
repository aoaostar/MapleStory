package server;

import client.inventory.IItem;

public class MapleDueyActions
{
    private String sender;
    private IItem item;
    private int mesos;
    private int quantity;
    private long sentTime;
    private int packageId;
    
    public MapleDueyActions(final int pId, final IItem item) {
        this.sender = null;
        this.item = null;
        this.mesos = 0;
        this.quantity = 1;
        this.packageId = 0;
        this.item = item;
        this.quantity = item.getQuantity();
        this.packageId = pId;
    }
    
    public MapleDueyActions(final int pId) {
        this.sender = null;
        this.item = null;
        this.mesos = 0;
        this.quantity = 1;
        this.packageId = 0;
        this.packageId = pId;
    }
    
    public String getSender() {
        return this.sender;
    }
    
    public void setSender(final String name) {
        this.sender = name;
    }
    
    public IItem getItem() {
        return this.item;
    }
    
    public int getMesos() {
        return this.mesos;
    }
    
    public void setMesos(final int set) {
        this.mesos = set;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public int getPackageId() {
        return this.packageId;
    }
    
    public void setSentTime(final long sentTime) {
        this.sentTime = sentTime;
    }
    
    public long getSentTime() {
        return this.sentTime;
    }
}
