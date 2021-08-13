package server.custom.bossrank;

public class BossRankInfo
{
    private int cid;
    private String cname;
    private String bossname;
    private int points;
    private int count;
    
    public int getCid() {
        return this.cid;
    }
    
    public void setCid(final int cid) {
        this.cid = cid;
    }
    
    public String getCname() {
        return this.cname;
    }
    
    public void setCname(final String cname) {
        this.cname = cname;
    }
    
    public String getBossname() {
        return this.bossname;
    }
    
    public void setBossname(final String bossname) {
        this.bossname = bossname;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void setPoints(final int points) {
        this.points = points;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
}
