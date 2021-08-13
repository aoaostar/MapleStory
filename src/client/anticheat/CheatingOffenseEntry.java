package client.anticheat;

public class CheatingOffenseEntry
{
    private final CheatingOffense offense;
    private int count;
    private final int characterid;
    private long lastOffense;
    private final long firstOffense;
    private String param;
    private int dbid;
    
    public CheatingOffenseEntry(final CheatingOffense offense, final int characterid) {
        this.count = 0;
        this.dbid = -1;
        this.offense = offense;
        this.characterid = characterid;
        this.firstOffense = System.currentTimeMillis();
    }
    
    public CheatingOffense getOffense() {
        return this.offense;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int getChrfor() {
        return this.characterid;
    }
    
    public void incrementCount() {
        ++this.count;
        this.lastOffense = System.currentTimeMillis();
    }
    
    public boolean isExpired() {
        return this.lastOffense < System.currentTimeMillis() - this.offense.getValidityDuration();
    }
    
    public int getPoints() {
        return this.count * this.offense.getPoints();
    }
    
    public String getParam() {
        return this.param;
    }
    
    public void setParam(final String param) {
        this.param = param;
    }
    
    public long getLastOffenseTime() {
        return this.lastOffense;
    }
    
    public int getDbId() {
        return this.dbid;
    }
    
    public void setDbId(final int dbid) {
        this.dbid = dbid;
    }
}
