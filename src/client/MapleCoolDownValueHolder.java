package client;

public class MapleCoolDownValueHolder
{
    public int skillId;
    public long startTime;
    public long length;
    
    public MapleCoolDownValueHolder(final int skillId, final long startTime, final long length) {
        this.skillId = skillId;
        this.startTime = startTime;
        this.length = length;
    }
}
