package provider;

import java.util.List;

public interface MapleDataDirectoryEntry extends MapleDataEntry
{
    List<MapleDataDirectoryEntry> getSubdirectories();
    
    List<MapleDataFileEntry> getFiles();
    
    MapleDataEntry getEntry(final String p0);
}
