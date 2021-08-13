package client;

import java.io.Serializable;

public class SkillEntry implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    public byte skillevel;
    public byte masterlevel;
    public long expiration;
    
    public SkillEntry(final byte skillevel, final byte masterlevel, final long expiration) {
        this.skillevel = skillevel;
        this.masterlevel = masterlevel;
        this.expiration = expiration;
    }
}
