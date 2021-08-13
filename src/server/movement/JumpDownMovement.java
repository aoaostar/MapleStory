package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class JumpDownMovement extends AbstractLifeMovement
{
    private Point pixelsPerSecond;
    private Point offset;
    private int unk;
    private int fh;
    
    public JumpDownMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }
    
    public Point getPixelsPerSecond() {
        return this.pixelsPerSecond;
    }
    
    public void setPixelsPerSecond(final Point wobble) {
        this.pixelsPerSecond = wobble;
    }
    
    public Point getOffset() {
        return this.offset;
    }
    
    public void setOffset(final Point wobble) {
        this.offset = wobble;
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
        lew.writePos(this.pixelsPerSecond);
        lew.writeShort(this.unk);
        lew.writeShort(this.fh);
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
