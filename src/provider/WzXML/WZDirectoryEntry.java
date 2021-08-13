package provider.WzXML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataEntity;
import provider.MapleDataEntry;
import provider.MapleDataFileEntry;

public class WZDirectoryEntry extends WZEntry implements MapleDataDirectoryEntry
{
    private List<MapleDataDirectoryEntry> subdirs;
    private List<MapleDataFileEntry> files;
    private Map<String, MapleDataEntry> entries;
    
    public WZDirectoryEntry(final String name, final int size, final int checksum, final MapleDataEntity parent) {
        super(name, size, checksum, parent);
        this.subdirs = new ArrayList<MapleDataDirectoryEntry>();
        this.files = new ArrayList<MapleDataFileEntry>();
        this.entries = new HashMap<String, MapleDataEntry>();
    }
    
    public WZDirectoryEntry() {
        super(null, 0, 0, null);
        this.subdirs = new ArrayList<MapleDataDirectoryEntry>();
        this.files = new ArrayList<MapleDataFileEntry>();
        this.entries = new HashMap<String, MapleDataEntry>();
    }
    
    public void addDirectory(final MapleDataDirectoryEntry dir) {
        this.subdirs.add(dir);
        this.entries.put(dir.getName(), dir);
    }
    
    public void addFile(final MapleDataFileEntry fileEntry) {
        this.files.add(fileEntry);
        this.entries.put(fileEntry.getName(), fileEntry);
    }
    
    @Override
    public List<MapleDataDirectoryEntry> getSubdirectories() {
        return Collections.unmodifiableList((List<? extends MapleDataDirectoryEntry>)this.subdirs);
    }
    
    @Override
    public List<MapleDataFileEntry> getFiles() {
        return Collections.unmodifiableList((List<? extends MapleDataFileEntry>)this.files);
    }
    
    @Override
    public MapleDataEntry getEntry(final String name) {
        return this.entries.get(name);
    }
}
