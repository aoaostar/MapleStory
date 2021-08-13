package server;

public interface ShutdownServerMBean extends Runnable
{
    void shutdown();
}
