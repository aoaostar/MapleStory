package server.maps;

import client.MapleClient;
import client.anticheat.CheatingOffense;
import handling.channel.ChannelServer;
import java.awt.Point;
import scripting.PortalScriptManager;
import server.MaplePortal;
import tools.MaplePacketCreator;

public class MapleGenericPortal implements MaplePortal
{
    private String name;
    private String target;
    private String scriptName;
    private Point position;
    private int targetmap;
    private final int type;
    private int id;
    private boolean portalState;
    
    public MapleGenericPortal(final int type) {
        this.portalState = true;
        this.type = type;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Point getPosition() {
        return this.position;
    }
    
    @Override
    public String getTarget() {
        return this.target;
    }
    
    @Override
    public int getTargetMapId() {
        return this.targetmap;
    }
    
    @Override
    public int getType() {
        return this.type;
    }
    
    @Override
    public String getScriptName() {
        return this.scriptName;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setPosition(final Point position) {
        this.position = position;
    }
    
    public void setTarget(final String target) {
        this.target = target;
    }
    
    public void setTargetMapId(final int targetmapid) {
        this.targetmap = targetmapid;
    }
    
    @Override
    public void setScriptName(final String scriptName) {
        this.scriptName = scriptName;
    }
    
    @Override
    public void enterPortal(final MapleClient c) {
        if (this.getPosition().distanceSq(c.getPlayer().getPosition()) > 22500.0) {
            c.getPlayer().getCheatTracker().registerOffense(CheatingOffense.使用过远传送点);
        }
        final MapleMap currentmap = c.getPlayer().getMap();
        if (this.portalState || c.getPlayer().isGM()) {
            if (this.getScriptName() != null) {
                c.getPlayer().checkFollow();
                try {
                    PortalScriptManager.getInstance().executePortalScript(this, c);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (this.getTargetMapId() != 999999999) {
                final MapleMap to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(this.getTargetMapId());
                if (!c.getPlayer().isGM()) {
                    if (to == null) {
                        c.getPlayer().dropMessage(5, "本地图目前尚未开放.");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (to.getLevelLimit() > 0 && to.getLevelLimit() > c.getPlayer().getLevel()) {
                        c.getPlayer().dropMessage(5, "You are too low of a level to enter this place.");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                }
                else if (to == null) {
                    c.getPlayer().dropMessage(5, "本地图目前尚未开放.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                c.getPlayer().changeMapPortal(to, (to.getPortal(this.getTarget()) == null) ? to.getPortal(0) : to.getPortal(this.getTarget()));
            }
        }
        if (c != null && c.getPlayer() != null && c.getPlayer().getMap() == currentmap) {
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    @Override
    public boolean getPortalState() {
        return this.portalState;
    }
    
    @Override
    public void setPortalState(final boolean ps) {
        this.portalState = ps;
    }
}
