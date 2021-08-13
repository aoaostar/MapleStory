package server.maps;

public abstract class AbstractAnimatedMapleMapObject extends AbstractMapleMapObject implements AnimatedMapleMapObject
{
    private int stance;
    
    @Override
    public int getStance() {
        return this.stance;
    }
    
    @Override
    public void setStance(final int stance) {
        this.stance = stance;
    }
    
    @Override
    public boolean isFacingLeft() {
        return this.getStance() % 2 != 0;
    }
    
    public int getFacingDirection() {
        return this.getStance() % 2;
    }
}
