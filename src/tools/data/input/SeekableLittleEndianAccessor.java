package tools.data.input;

public interface SeekableLittleEndianAccessor extends LittleEndianAccessor
{
    void seek(final long p0);
    
    long getPosition();
}
