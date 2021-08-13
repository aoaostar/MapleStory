package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public interface LifeMovementFragment
{
    void serialize(final LittleEndianWriter p0);
    
    Point getPosition();
}
