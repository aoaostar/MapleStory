package tools.data.input;

public interface ByteInputStream
{
    int readByte();
    
    long getBytesRead();
    
    long available();
    
    String toString(final boolean p0);
}
