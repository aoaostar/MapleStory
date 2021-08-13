package provider.WzXML;

import provider.MapleDataEntity;
import provider.MapleDataFileEntry;

public class WZFileEntry extends WZEntry implements MapleDataFileEntry
{
    private int offset;
    
    public WZFileEntry(final String name, final int size, final int checksum, final MapleDataEntity parent) {
        super(name, size, checksum, parent);
    }
    
    @Override
    public int getOffset() {
        return this.offset;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
