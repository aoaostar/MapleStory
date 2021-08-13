package server.maps;

import java.awt.Point;

public abstract class AbstractMapleMapObject implements MapleMapObject
{
    private final Point position;
    private int objectId;
    
    public AbstractMapleMapObject() {
        this.position = new Point();
    }
    
    @Override
    public abstract MapleMapObjectType getType();
    
    public Point getTruePosition() {
        return this.position;
    }
    
    @Override
    public Point getPosition() {
        return new Point(this.position);
    }
    
    @Override
    public void setPosition(final Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }
    
    @Override
    public int getObjectId() {
        return this.objectId;
    }
    
    @Override
    public void setObjectId(final int id) {
        this.objectId = id;
    }
}
