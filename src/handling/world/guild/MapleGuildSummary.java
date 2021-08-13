package handling.world.guild;

import java.io.Serializable;

public class MapleGuildSummary implements Serializable
{
    public static long serialVersionUID;
    private final String name;
    private final short logoBG;
    private final byte logoBGColor;
    private final short logo;
    private final byte logoColor;
    private final int allianceid;
    
    public MapleGuildSummary(final MapleGuild g) {
        this.name = g.getName();
        this.logoBG = (short)g.getLogoBG();
        this.logoBGColor = (byte)g.getLogoBGColor();
        this.logo = (short)g.getLogo();
        this.logoColor = (byte)g.getLogoColor();
        this.allianceid = g.getAllianceId();
    }
    
    public String getName() {
        return this.name;
    }
    
    public short getLogoBG() {
        return this.logoBG;
    }
    
    public byte getLogoBGColor() {
        return this.logoBGColor;
    }
    
    public short getLogo() {
        return this.logo;
    }
    
    public byte getLogoColor() {
        return this.logoColor;
    }
    
    public int getAllianceId() {
        return this.allianceid;
    }
    
    static {
        MapleGuildSummary.serialVersionUID = 3565477792085301248L;
    }
}
