package server.quest;

public enum MapleQuestActionType
{
    UNDEFINED(-1), 
    exp(0), 
    item(1), 
    nextQuest(2), 
    money(3), 
    quest(4), 
    skill(5), 
    pop(6), 
    buffItemID(7), 
    infoNumber(8), 
    yes(9), 
    no(10), 
    sp(11);
    
    byte type;
    
    private MapleQuestActionType(final int type) {
        this.type = (byte)type;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public static MapleQuestActionType getByType(final byte type) {
        for (final MapleQuestActionType l : values()) {
            if (l.getType() == type) {
                return l;
            }
        }
        return null;
    }
    
    public static MapleQuestActionType getByWZName(final String name) {
        try {
            return valueOf(name);
        }
        catch (IllegalArgumentException ex) {
            return MapleQuestActionType.UNDEFINED;
        }
    }
}
