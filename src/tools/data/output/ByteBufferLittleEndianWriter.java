package tools.data.output;

import org.apache.mina.core.buffer.IoBuffer;

public class ByteBufferLittleEndianWriter extends GenericLittleEndianWriter
{
    private IoBuffer bb;
    
    public ByteBufferLittleEndianWriter() {
        this(50, true);
    }
    
    public ByteBufferLittleEndianWriter(final int size) {
        this(size, false);
    }
    
    public ByteBufferLittleEndianWriter(final int initialSize, final boolean autoExpand) {
        (this.bb = IoBuffer.allocate(initialSize)).setAutoExpand(autoExpand);
        this.setByteOutputStream(new ByteBufferOutputstream(this.bb));
    }
    
    public IoBuffer getFlippedBB() {
        return this.bb.flip();
    }
    
    public IoBuffer getByteBuffer() {
        return this.bb;
    }
}
