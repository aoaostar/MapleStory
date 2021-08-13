package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class BounceMovement extends AbstractLifeMovement
{
    private int unk;
    private int fh;
    
    public BounceMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }
    
    public int getUnk() {
        return this.unk;
    }
    
    public void setUnk(final int unk) {
        this.unk = unk;
    }
    
    public int getFH() {
        return this.fh;
    }
    
    public void setFH(final int fh) {
        this.fh = fh;
    }
    
    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.writePos(this.getPosition());
        lew.writeShort(this.getUnk());
        lew.writeShort(this.getFH());
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
