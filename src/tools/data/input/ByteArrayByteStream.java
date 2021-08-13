package tools.data.input;


import java.io.IOException;
import tools.HexTool;

public class ByteArrayByteStream implements SeekableInputStreamBytestream
{
    private int pos;
    private long bytesRead;
    private final byte[] arr;
    
    public ByteArrayByteStream(final byte[] arr) {
        this.pos = 0;
        this.bytesRead = 0L;
        this.arr = arr;
    }
    
    @Override
    public long getPosition() {
        return this.pos;
    }
    
    @Override
    public void seek(final long offset) throws IOException {
        this.pos = (int)offset;
    }
    
    @Override
    public long getBytesRead() {
        return this.bytesRead;
    }
    
    @Override
    public int readByte() {
        ++this.bytesRead;
        return this.arr[this.pos++] & 0xFF;
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    @Override
    public String toString(final boolean b) {
        String nows = "";
        if (this.arr.length - this.pos > 0) {
            final byte[] now = new byte[this.arr.length - this.pos];
            System.arraycopy(this.arr, this.pos, now, 0, this.arr.length - this.pos);
            nows = HexTool.toString(now);
        }
        if (b) {
            return "All: " + HexTool.toString(this.arr) + "\nNow: " + nows;
        }
        return "Data: " + nows;
    }
    
    @Override
    public long available() {
        return this.arr.length - this.pos;
    }
}
