package server.life;

import client.MapleClient;
import server.MapleShopFactory;
import server.maps.MapleMapObjectType;
import tools.MaplePacketCreator;

public class MapleNPC extends AbstractLoadedMapleLife
{
    private String name;
    private boolean custom;
    
    public MapleNPC(final int id, final String name) {
        super(id);
        this.name = "MISSINGNO";
        this.custom = false;
        this.name = name;
    }
    
    public boolean hasShop() {
        return MapleShopFactory.getInstance().getShopForNPC(this.getId()) != null;
    }
    
    public void sendShop(final MapleClient c) {
        if (c.getPlayer().isGM()) {
            c.getPlayer().dropMessage("您已经建立与商店npc[" + this.getId() + "]的连接");
        }
        MapleShopFactory.getInstance().getShopForNPC(this.getId()).sendShop(c);
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (this.getId() < 9901000) {
            client.getSession().write(MaplePacketCreator.spawnNPC(this, true));
            client.getSession().write(MaplePacketCreator.spawnNPCRequestController(this, true));
        }
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().write(MaplePacketCreator.removeNPC(this.getObjectId()));
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.NPC;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String n) {
        this.name = n;
    }
    
    public boolean isCustom() {
        return this.custom;
    }
    
    public void setCustom(final boolean custom) {
        this.custom = custom;
    }
}
