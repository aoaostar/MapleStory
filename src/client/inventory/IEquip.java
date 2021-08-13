package client.inventory;

public interface IEquip extends IItem
{
    public static final int ARMOR_RATIO = 350000;
    public static final int WEAPON_RATIO = 700000;
    
    byte getUpgradeSlots();
    
    byte getLevel();
    
    byte getViciousHammer();
    
    int getItemEXP();
    
    int getExpPercentage();
    
    byte getEquipLevel();
    
    int getEquipLevels();
    
    int getEquipExp();
    
    int getEquipExpForLevel();
    
    int getBaseLevel();
    
    short getStr();
    
    short getDex();
    
    short getInt();
    
    short getLuk();
    
    short getHp();
    
    short getMp();
    
    short getWatk();
    
    short getMatk();
    
    short getWdef();
    
    short getMdef();
    
    short getAcc();
    
    short getAvoid();
    
    short getHands();
    
    short getSpeed();
    
    short getJump();
    
    int getDurability();
    
    byte getEnhance();
    
    byte getState();
    
    short getPotential1();
    
    short getPotential2();
    
    short getPotential3();
    
    short getHpR();
    
    short getMpR();
    
    public enum ScrollResult
    {
        SUCCESS, 
        FAIL, 
        CURSE;
    }
}
