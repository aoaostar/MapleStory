package handling.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MapleCodecFactory implements ProtocolCodecFactory
{
    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;
    
    public MapleCodecFactory() {
        this.encoder = new MaplePacketEncoder();
        this.decoder = new MaplePacketDecoder();
    }
    
    public ProtocolEncoder getEncoder() throws Exception {
        return this.encoder;
    }
    
    public ProtocolDecoder getDecoder() throws Exception {
        return this.decoder;
    }
    
    public ProtocolEncoder getEncoder(final IoSession session) throws Exception {
        return this.encoder;
    }
    
    public ProtocolDecoder getDecoder(final IoSession session) throws Exception {
        return this.decoder;
    }
}
