package tools;

import java.net.SocketAddress;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.DummySession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.write.WriteRequest;

public class MockIOSession extends DummySession
{
    protected void updateTrafficMask() {
    }
    
    public IoSessionConfig getConfig() {
        return null;
    }
    
    public IoFilterChain getFilterChain() {
        return null;
    }
    
    public IoHandler getHandler() {
        return null;
    }
    
    public SocketAddress getLocalAddress() {
        return null;
    }
    
    public SocketAddress getRemoteAddress() {
        return null;
    }
    
    public IoService getService() {
        return null;
    }
    
    public SocketAddress getServiceAddress() {
        return null;
    }
    
    protected void close0() {
    }
    
    public WriteFuture write(final Object message, final SocketAddress remoteAddress) {
        return null;
    }
    
    public WriteFuture write(final Object message) {
        return null;
    }
    
    protected void write0(final WriteRequest writeRequest) {
    }
}
