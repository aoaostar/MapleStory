package tools.data.input;

import java.io.IOException;

public interface SeekableInputStreamBytestream extends ByteInputStream
{
    void seek(final long p0) throws IOException;
    
    long getPosition() throws IOException;
    
    String toString(final boolean p0);
}
