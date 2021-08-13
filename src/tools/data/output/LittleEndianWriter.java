package tools.data.output;

import java.awt.Point;

public interface LittleEndianWriter
{
    void writeZeroBytes(final int p0);
    
    void write(final byte[] p0);
    
    void write(final byte p0);
    
    void write(final int p0);
    
    void writeInt(final int p0);
    
    void writeShort(final short p0);
    
    void writeShort(final int p0);
    
    void writeLong(final long p0);
    
    void writeAsciiString(final String p0);
    
    void writeAsciiString(final String p0, final int p1);
    
    void writePos(final Point p0);
    
    void writeMapleAsciiString(final String p0);
}
