package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class TeleportMovement extends AbsoluteLifeMovement
{
    public TeleportMovement(final int type, final Point position, final int duration, final int newstate, final int newfh) {
        super(type, position, duration, newstate);
    }
    
    public TeleportMovement(final int type, final Point position, final int newstate) {
        super(type, position, 0, newstate);
    }
    
    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.writeShort(this.getPosition().x);
        lew.writeShort(this.getPosition().y);
        lew.writeShort(this.getPixelsPerSecond().x);
        lew.writeShort(this.getPixelsPerSecond().y);
        lew.write(this.getNewstate());
    }
}
