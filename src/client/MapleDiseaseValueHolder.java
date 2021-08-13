package client;

import java.io.Serializable;

public class MapleDiseaseValueHolder implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    public long startTime;
    public long length;
    public MapleDisease disease;
    
    public MapleDiseaseValueHolder(final MapleDisease disease, final long startTime, final long length) {
        this.disease = disease;
        this.startTime = startTime;
        this.length = length;
    }
}
