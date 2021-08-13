package server.life;

import java.lang.ref.WeakReference;

public class SummonAttackEntry
{
    private final WeakReference<MapleMonster> mob;
    private final int damage;
    
    public SummonAttackEntry(final MapleMonster mob, final int damage) {
        this.mob = new WeakReference<MapleMonster>(mob);
        this.damage = damage;
    }
    
    public MapleMonster getMonster() {
        return this.mob.get();
    }
    
    public int getDamage() {
        return this.damage;
    }
}
