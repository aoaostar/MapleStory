package server.life;

public class BanishInfo
{
    private final int map;
    private final String portal;
    private final String msg;
    
    public BanishInfo(final String msg, final int map, final String portal) {
        this.msg = msg;
        this.map = map;
        this.portal = portal;
    }
    
    public int getMap() {
        return this.map;
    }
    
    public String getPortal() {
        return this.portal;
    }
    
    public String getMsg() {
        return this.msg;
    }
}
