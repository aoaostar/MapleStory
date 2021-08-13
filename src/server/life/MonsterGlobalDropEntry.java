package server.life;

public class MonsterGlobalDropEntry
{
    public byte dropType;
    public short questid;
    public int itemId;
    public int chance;
    public int Minimum;
    public int Maximum;
    public int continent;
    public boolean onlySelf;
    
    public MonsterGlobalDropEntry(final int itemId, final int chance, final int continent, final byte dropType, final int Minimum, final int Maximum, final short questid) {
        this.onlySelf = false;
        this.itemId = itemId;
        this.chance = chance;
        this.dropType = dropType;
        this.continent = continent;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
    }
    
    public MonsterGlobalDropEntry(final int itemId, final int chance, final int continent, final byte dropType, final int Minimum, final int Maximum, final short questid, final boolean onlySelf) {
        this.onlySelf = false;
        this.itemId = itemId;
        this.chance = chance;
        this.dropType = dropType;
        this.continent = continent;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
        this.onlySelf = onlySelf;
    }
}
