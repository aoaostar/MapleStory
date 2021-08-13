package tools.data.input;

import java.awt.Point;
import java.io.UnsupportedEncodingException;

public class GenericLittleEndianAccessor implements LittleEndianAccessor
{
    private final ByteInputStream bs;
    
    public GenericLittleEndianAccessor(final ByteInputStream bs) {
        this.bs = bs;
    }
    
    @Override
    public int readByteAsInt() {
        return this.bs.readByte();
    }
    
    @Override
    public byte readByte() {
        return (byte)this.bs.readByte();
    }
    
    @Override
    public int readInt() {
        final int byte1 = this.bs.readByte();
        final int byte2 = this.bs.readByte();
        final int byte3 = this.bs.readByte();
        final int byte4 = this.bs.readByte();
        return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
    }
    
    @Override
    public short readShort() {
        final int byte1 = this.bs.readByte();
        final int byte2 = this.bs.readByte();
        return (short)((byte2 << 8) + byte1);
    }
    
    @Override
    public char readChar() {
        return (char)this.readShort();
    }
    
    @Override
    public long readLong() {
        final int byte1 = this.bs.readByte();
        final int byte2 = this.bs.readByte();
        final int byte3 = this.bs.readByte();
        final int byte4 = this.bs.readByte();
        final int byte5 = this.bs.readByte();
        final int byte6 = this.bs.readByte();
        final int byte7 = this.bs.readByte();
        final int byte8 = this.bs.readByte();
        return (byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) + (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
    }
    
    @Override
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public String readAsciiString(final int n) {
        final byte[] ret = new byte[n];
        for (int x = 0; x < n; ++x) {
            ret[x] = this.readByte();
        }
        try {
            final String str = new String(ret, "gbk");
            return str;
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e);
            return null;
        }
    }
    
    @Override
    public long getBytesRead() {
        return this.bs.getBytesRead();
    }
    
    @Override
    public String readMapleAsciiString() {
        return this.readAsciiString(this.readShort());
    }
    
    @Override
    public Point readPos() {
        final int x = this.readShort();
        final int y = this.readShort();
        return new Point(x, y);
    }
    
    @Override
    public byte[] read(final int num) {
        final byte[] ret = new byte[num];
        for (int x = 0; x < num; ++x) {
            ret[x] = this.readByte();
        }
        return ret;
    }
    
    @Override
    public void skip(final int num) {
        for (int x = 0; x < num; ++x) {
            this.readByte();
        }
    }
    
    @Override
    public long available() {
        return this.bs.available();
    }
    
    @Override
    public String toString() {
        return this.bs.toString();
    }
    
    @Override
    public String toString(final boolean b) {
        return this.bs.toString(b);
    }
}
