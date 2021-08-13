package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class ChairMovement extends AbstractLifeMovement
{
    private int unk;
    
    public ChairMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }
    
    public int getUnk() {
        return this.unk;
    }
    
    public void setUnk(final int unk) {
        this.unk = unk;
    }
    
    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.writeShort(this.getPosition().x);
        lew.writeShort(this.getPosition().y);
        lew.writeShort(this.unk);
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
