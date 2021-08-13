package handling;

import java.io.Serializable;

public interface MaplePacket extends Serializable
{
    byte[] getBytes();
    
    Runnable getOnSend();
    
    void setOnSend(final Runnable p0);
}
