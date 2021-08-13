package provider;

import java.io.File;
import provider.WzXML.XMLWZFile;

public class MapleDataProviderFactory
{
    private static final String wzPath;
    
    private static MapleDataProvider getWZ(final Object in, final boolean provideImages) {
        if (in instanceof File) {
            final File fileIn = (File)in;
            return new XMLWZFile(fileIn);
        }
        throw new IllegalArgumentException("Can't create data provider for input " + in);
    }
    
    public static MapleDataProvider getDataProvider(final Object in) {
        return getWZ(in, false);
    }
    
    public static MapleDataProvider getImageProvidingDataProvider(final Object in) {
        return getWZ(in, true);
    }
    
    public static File fileInwzPath(final String filename) {
        return new File(MapleDataProviderFactory.wzPath, filename);
    }
    
    static {
        wzPath = System.getProperty("wzPath", "wz");
    }
}
