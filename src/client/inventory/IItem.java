package client.inventory;

public interface IItem extends Comparable<IItem>
{
    byte getType();
    
    short getPosition();
    
    byte getFlag();
    
    boolean getLocked();
    
    short getQuantity();
    
    String getOwner();
    
    String getGMLog();
    
    int getItemId();
    
    MaplePet getPet();
    
    int getUniqueId();
    
    IItem copy();
    
    long getExpiration();
    
    void setFlag(final byte p0);
    
    void setLocked(final byte p0);
    
    void setUniqueId(final int p0);
    
    void setPosition(final short p0);
    
    void setExpiration(final long p0);
    
    void setOwner(final String p0);
    
    void setGMLog(final String p0);
    
    void setQuantity(final short p0);
    
    void setGiftFrom(final String p0);
    
    void setEquipLevel(final byte p0);
    
    byte getEquipLevel();
    
    String getGiftFrom();
    
    MapleRing getRing();
    
    int getEquipOnlyId();
    
    boolean hasSetOnlyId();
    
    void setEquipOnlyId(final int p0);
}
