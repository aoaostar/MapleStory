package tools.data.input;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamByteStream implements ByteInputStream
{
    private final InputStream is;
    private long read;
    
    public InputStreamByteStream(final InputStream is) {
        this.read = 0L;
        this.is = is;
    }
    
    @Override
    public int readByte() {
        try {
            final int temp = this.is.read();
            if (temp == -1) {
                throw new RuntimeException("EOF");
            }
            ++this.read;
            return temp;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public long getBytesRead() {
        return this.read;
    }
    
    @Override
    public long available() {
        try {
            return this.is.available();
        }
        catch (IOException e) {
            System.err.println("ERROR" + e);
            return 0L;
        }
    }
    
    @Override
    public String toString(final boolean b) {
        return this.toString();
    }
}
