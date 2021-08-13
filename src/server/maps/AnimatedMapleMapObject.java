package server.maps;

public interface AnimatedMapleMapObject extends MapleMapObject
{
    int getStance();
    
    void setStance(final int p0);
    
    boolean isFacingLeft();
}
