package server.movement;

import java.awt.Point;

public abstract class AbstractLifeMovement implements LifeMovement
{
    private Point position;
    private int duration;
    private int newstate;
    private int type;
    
    public AbstractLifeMovement(final int type, final Point position, final int duration, final int newstate) {
        this.type = type;
        this.position = position;
        this.duration = duration;
        this.newstate = newstate;
    }
    
    @Override
    public int getType() {
        return this.type;
    }
    
    @Override
    public int getDuration() {
        return this.duration;
    }
    
    @Override
    public int getNewstate() {
        return this.newstate;
    }
    
    @Override
    public Point getPosition() {
        return this.position;
    }
}
