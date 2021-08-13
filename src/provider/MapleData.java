package provider;

import java.util.List;
import provider.WzXML.MapleDataType;


public interface MapleData extends MapleDataEntity, Iterable<MapleData>
{
    String getName();
    
    MapleDataType getType();
    
    List<MapleData> getChildren();
    
    MapleData getChildByPath(final String p0);
    
    Object getData();
}
