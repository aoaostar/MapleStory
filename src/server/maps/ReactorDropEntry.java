package server.maps;

public class ReactorDropEntry
{
    public int itemId;
    public int chance;
    public int questid;
    public int assignedRangeStart;
    public int assignedRangeLength;
    
    public ReactorDropEntry(final int itemId, final int chance, final int questid) {
        this.itemId = itemId;
        this.chance = chance;
        this.questid = questid;
    }
}
