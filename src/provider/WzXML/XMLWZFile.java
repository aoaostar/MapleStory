package provider.WzXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataEntity;
import provider.MapleDataProvider;

public class XMLWZFile implements MapleDataProvider
{
    private final File root;
    private final WZDirectoryEntry rootForNavigation;
    
    public XMLWZFile(final File fileIn) {
        this.root = fileIn;
        this.rootForNavigation = new WZDirectoryEntry(fileIn.getName(), 0, 0, null);
        this.fillMapleDataEntitys(this.root, this.rootForNavigation);
    }
    
    private void fillMapleDataEntitys(final File lroot, final WZDirectoryEntry wzdir) {
        for (final File file : lroot.listFiles()) {
            final String fileName = file.getName();
            if (file.isDirectory() && !fileName.endsWith(".img")) {
                final WZDirectoryEntry newDir = new WZDirectoryEntry(fileName, 0, 0, wzdir);
                wzdir.addDirectory(newDir);
                this.fillMapleDataEntitys(file, newDir);
            }
            else if (fileName.endsWith(".xml")) {
                wzdir.addFile(new WZFileEntry(fileName.substring(0, fileName.length() - 4), 0, 0, wzdir));
            }
        }
    }
    
    @Override
    public MapleData getData(final String path) {
        final File dataFile = new File(this.root, path + ".xml");
        final File imageDataDir = new File(this.root, path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(dataFile);
        }
        catch (FileNotFoundException e2) {
            throw new RuntimeException("Datafile " + path + " does not exist in " + this.root.getAbsolutePath());
        }
        XMLDomMapleData domMapleData;
        try {
            domMapleData = new XMLDomMapleData(fis, imageDataDir.getParentFile());
        }
        finally {
            try {
                fis.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return domMapleData;
    }
    
    @Override
    public MapleDataDirectoryEntry getRoot() {
        return this.rootForNavigation;
    }
}
