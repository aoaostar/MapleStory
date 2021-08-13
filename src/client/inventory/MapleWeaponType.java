package client.inventory;

public enum MapleWeaponType
{
    NOT_A_WEAPON(4.0f), 
    BOW(3.4f), 
    CLAW(3.6f), 
    DAGGER(4.0f), 
    CROSSBOW(3.6f), 
    AXE1H(4.4f), 
    SWORD1H(4.0f), 
    BLUNT1H(4.4f), 
    AXE2H(4.8f), 
    SWORD2H(4.6f), 
    BLUNT2H(4.8f), 
    POLE_ARM(5.0f), 
    SPEAR(5.0f), 
    STAFF(3.6f), 
    WAND(3.6f), 
    KNUCKLE(4.8f), 
    GUN(3.6f), 
    KATARA(4.0f);
    
    private final float damageMultiplier;
    
    private MapleWeaponType(final float maxDamageMultiplier) {
        this.damageMultiplier = maxDamageMultiplier;
    }
    
    public float getMaxDamageMultiplier() {
        return this.damageMultiplier;
    }
}
