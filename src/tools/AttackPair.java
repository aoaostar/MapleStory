package tools;

import java.util.List;

public class AttackPair
{
    public int objectid;
    public List<Pair<Integer, Boolean>> attack;
    
    public AttackPair(final int objectid, final List<Pair<Integer, Boolean>> attack) {
        this.objectid = objectid;
        this.attack = attack;
    }
}
