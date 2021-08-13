package handling.channel.handler;

import client.MapleClient;
import scripting.EventManager;
import scripting.NPCScriptManager;
import tools.MaplePacketCreator;
public class UserInterfaceHandler
{
    public static void CygnusSummon_NPCRequest(final MapleClient c) {
        if (c.getPlayer().getJob() == 2000) {
            NPCScriptManager.getInstance().start(c, 1202000);
        }
        else if (c.getPlayer().getJob() == 1000) {
            NPCScriptManager.getInstance().start(c, 1101008);
        }
    }
    
    public static void ShipObjectRequest(final int mapid, final MapleClient c) {
        int effect = 3;
        switch (mapid) {
            case 101000300:
            case 200000111: {
                final EventManager em = c.getChannelServer().getEventSM().getEventManager("Boats");
                if (em != null && em.getProperty("docked").equals("true")) {
                    effect = 1;
                    break;
                }
                break;
            }
            case 200000121:
            case 220000110: {
                final EventManager em = c.getChannelServer().getEventSM().getEventManager("Trains");
                if (em != null && em.getProperty("docked").equals("true")) {
                    effect = 1;
                    break;
                }
                break;
            }
            case 200000151:
            case 260000100: {
                final EventManager em = c.getChannelServer().getEventSM().getEventManager("Geenie");
                if (em != null && em.getProperty("docked").equals("true")) {
                    effect = 1;
                    break;
                }
                break;
            }
            case 200000131:
            case 240000110: {
                final EventManager em = c.getChannelServer().getEventSM().getEventManager("Flight");
                if (em != null && em.getProperty("docked").equals("true")) {
                    effect = 1;
                    break;
                }
                break;
            }
            case 200090000:
            case 200090010: {
                final EventManager em = c.getChannelServer().getEventSM().getEventManager("Boats");
                if (em != null && em.getProperty("haveBalrog").equals("true")) {
                    effect = 1;
                    break;
                }
                return;
            }
            default: {
                System.out.println("Unhandled ship object, MapID : " + mapid);
                break;
            }
        }
        c.getSession().write(MaplePacketCreator.boatPacket(effect));
    }
}
