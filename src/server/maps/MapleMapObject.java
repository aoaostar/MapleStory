package server.maps;

import client.MapleClient;
import java.awt.Point;

public interface MapleMapObject
{
    int getObjectId();
    
    void setObjectId(final int p0);
    
    MapleMapObjectType getType();
    
    Point getPosition();
    
    void setPosition(final Point p0);
    
    void sendSpawnData(final MapleClient p0);
    
    void sendDestroyData(final MapleClient p0);
}
