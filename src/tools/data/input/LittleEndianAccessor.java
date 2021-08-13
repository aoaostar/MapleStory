package tools.data.input;

import java.awt.Point;

public interface LittleEndianAccessor
{
    byte readByte();
    
    int readByteAsInt();
    
    char readChar();
    
    short readShort();
    
    int readInt();
    
    long readLong();
    
    void skip(final int p0);
    
    byte[] read(final int p0);
    
    float readFloat();
    
    double readDouble();
    
    String readAsciiString(final int p0);
    
    String readMapleAsciiString();
    
    Point readPos();
    
    long getBytesRead();
    
    long available();
    
    String toString(final boolean p0);
}
