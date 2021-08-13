package server.events;

public enum MapleEventType
{
    打椰子比赛("椰子比赛", new int[] { 109080000 }), 
    打瓶盖比赛("打瓶盖", new int[] { 109080010 }), 
    向高地("向高地", new int[] { 109040000, 109040001, 109040002, 109040003, 109040004 }), 
    上楼上楼("上楼~上楼~", new int[] { 109030001, 109030002, 109030003 }), 
    快速0X猜题("快速OX猜题", new int[] { 109020001 }), 
    雪球赛("雪球赛", new int[] { 109060000 });
    
    public String command;
    public int[] mapids;
    
    MapleEventType(final String comm, final int[] mapids) {
        this.command = comm;
        this.mapids = mapids;
    }
    
    public static MapleEventType getByString(final String splitted) {
        for (final MapleEventType t : values()) {
            if (t.command.equalsIgnoreCase(splitted)) {
                return t;
            }
        }
        return null;
    }
}
