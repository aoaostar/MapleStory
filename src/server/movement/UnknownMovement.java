package server.movement;

import java.awt.Point;
import tools.data.output.LittleEndianWriter;

public class UnknownMovement extends AbstractLifeMovement
{
    private Point pixelsPerSecond;
    private int unk;
    private int fh;
    
    public UnknownMovement(final int type, final Point position, final int duration, final int newstate) {
        super(type, position, duration, newstate);
    }
    
    public Point getPixelsPerSecond() {
        return this.pixelsPerSecond;
    }
    
    public void setPixelsPerSecond(final Point wobble) {
        this.pixelsPerSecond = wobble;
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
        lew.writeShort(this.unk);
        lew.writeShort(this.getPosition().x);
        lew.writeShort(this.getPosition().y);
        lew.writeShort(this.pixelsPerSecond.x);
        lew.writeShort(this.pixelsPerSecond.y);
        lew.writeShort(this.fh);
        lew.write(this.getNewstate());
        lew.writeShort(this.getDuration());
    }
}
