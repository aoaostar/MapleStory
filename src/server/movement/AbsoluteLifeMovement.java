package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class AbsoluteLifeMovement extends AbstractLifeMovement
{
    private Point pixelsPerSecond;
    private Point offset;
    private int unk;
    
    public AbsoluteLifeMovement(final int type, final Point position, final int duration, final int newstate) {
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
    
    @Override
    public void serialize(final LittleEndianWriter lew) {
        lew.write(this.getType());
        lew.writePos(this.getPosition());
        lew.writePos(this.pixelsPerSecond);
        lew.writeShort(this.unk);
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
