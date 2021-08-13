package server.maps;

import client.MapleClient;
import handling.MaplePacket;
import tools.MaplePacketCreator;
import tools.packet.MTSCSPacket;

public class MapleMapEffect
{
    private String msg;
    private int itemId;
    private boolean active;
    private boolean jukebox;
    
    public MapleMapEffect(final String msg, final int itemId) {
        this.msg = "";
        this.itemId = 0;
        this.active = true;
        this.jukebox = false;
        this.msg = msg;
        this.itemId = itemId;
    }
    
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    public void setJukebox(final boolean actie) {
        this.jukebox = actie;
    }
    
    public boolean isJukebox() {
        return this.jukebox;
    }
    
    public MaplePacket makeDestroyData() {
        return this.jukebox ? MTSCSPacket.playCashSong(0, "") : MaplePacketCreator.removeMapEffect();
    }
    
    public MaplePacket makeStartData() {
        return this.jukebox ? MTSCSPacket.playCashSong(this.itemId, this.msg) : MaplePacketCreator.startMapEffect(this.msg, this.itemId, this.active);
    }
    
    public void sendStartData(final MapleClient c) {
        c.getSession().write((Object)this.makeStartData());
    }
}
