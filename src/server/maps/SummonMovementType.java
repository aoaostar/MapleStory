package server.maps;

public enum SummonMovementType
{
    不会移动(0), 
    飞行跟随(1), 
    WALK_STATIONARY(2), 
    跟随并且随机移动打怪(3), 
    CIRCLE_STATIONARY(4);
    
    private final int val;
    
    private SummonMovementType(final int val) {
        this.val = val;
    }
    
    public int getValue() {
        return this.val;
    }
}
