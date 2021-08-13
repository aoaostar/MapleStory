package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class ChangeEquipSpecialAwesome implements LifeMovementFragment
{
    private int wui;
    
    public ChangeEquipSpecialAwesome(final int wui) {
        this.wui = wui;
    }
    
    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(10);
        lew.write(this.wui);
    }
    
    @Override
    public Point getPosition() {
        return new Point(0, 0);
    }
}
