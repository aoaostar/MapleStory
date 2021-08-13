package server.life;

public class MonsterDropEntry
{
    public short questid;
    public int itemId;
    public int chance;
    public int Minimum;
    public int Maximum;
    
    public MonsterDropEntry(final int itemId, final int chance, final int Minimum, final int Maximum, final short questid) {
        this.itemId = itemId;
        this.chance = chance;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
    }
}
