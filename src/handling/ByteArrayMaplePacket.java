package handling;

import tools.HexTool;

public class ByteArrayMaplePacket implements MaplePacket
{
    public static long serialVersionUID;
    private final byte[] data;
    private transient Runnable onSend;
    
    public ByteArrayMaplePacket(final byte[] data) {
        this.data = data;
    }
    
    @Override
    public byte[] getBytes() {
        return this.data;
    }
    
    @Override
    public Runnable getOnSend() {
        return this.onSend;
    }
    
    @Override
    public void setOnSend(final Runnable onSend) {
        this.onSend = onSend;
    }
    
    @Override
    public String toString() {
        return HexTool.toString(this.data);
    }
    
    static {
        ByteArrayMaplePacket.serialVersionUID = -7997681658570958848L;
    }
}
