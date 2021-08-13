package server;

import java.awt.Point;
import provider.MapleData;
import provider.MapleDataTool;
import server.maps.MapleGenericPortal;
import server.maps.MapleMapPortal;

public class PortalFactory
{
    private int nextDoorPortal;
    
    public PortalFactory() {
        this.nextDoorPortal = 128;
    }
    
    public MaplePortal makePortal(final int type, final MapleData portal) {
        MapleGenericPortal ret = null;
        if (type == 2) {
            ret = new MapleMapPortal();
        }
        else {
            ret = new MapleGenericPortal(type);
        }
        this.loadPortal(ret, portal);
        return ret;
    }
    
    private void loadPortal(final MapleGenericPortal myPortal, final MapleData portal) {
        myPortal.setName(MapleDataTool.getString(portal.getChildByPath("pn")));
        myPortal.setTarget(MapleDataTool.getString(portal.getChildByPath("tn")));
        myPortal.setTargetMapId(MapleDataTool.getInt(portal.getChildByPath("tm")));
        myPortal.setPosition(new Point(MapleDataTool.getInt(portal.getChildByPath("x")), MapleDataTool.getInt(portal.getChildByPath("y"))));
        String script = MapleDataTool.getString("script", portal, null);
        if (script != null && script.equals("")) {
            script = null;
        }
        myPortal.setScriptName(script);
        if (myPortal.getType() == 6) {
            myPortal.setId(this.nextDoorPortal);
            ++this.nextDoorPortal;
        }
        else {
            myPortal.setId(Integer.parseInt(portal.getName()));
        }
    }
}
