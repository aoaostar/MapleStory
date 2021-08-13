package server.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataEntity;
import provider.WzXML.MapleDataType;

public class MapleCustomQuestData implements MapleData, Serializable
{
    private static final long serialVersionUID = -8600005891655365066L;
    private final List<MapleCustomQuestData> children;
    private final String name;
    private final Object data;
    private final MapleDataEntity parent;
    
    public MapleCustomQuestData(final String name, final Object data, final MapleDataEntity parent) {
        this.children = new LinkedList<MapleCustomQuestData>();
        this.name = name;
        this.data = data;
        this.parent = parent;
    }
    
    public void addChild(final MapleData child) {
        this.children.add((MapleCustomQuestData)child);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public MapleDataType getType() {
        return MapleDataType.UNKNOWN_TYPE;
    }
    
    @Override
    public List<MapleData> getChildren() {
        MapleData[] ret = new MapleData[this.children.size()];
        ret = this.children.toArray(ret);
        return new ArrayList<MapleData>(Arrays.asList(ret));
    }
    
    @Override
    public MapleData getChildByPath(final String name) {
        if (name.equals(this.name)) {
            return this;
        }
        String lookup;
        String nextName;
        if (name.indexOf("/") == -1) {
            lookup = name;
            nextName = name;
        }
        else {
            lookup = name.substring(0, name.indexOf("/"));
            nextName = name.substring(name.indexOf("/") + 1);
        }
        for (final MapleData child : this.children) {
            if (child.getName().equals(lookup)) {
                return child.getChildByPath(nextName);
            }
        }
        return null;
    }
    
    @Override
    public Object getData() {
        return this.data;
    }
    
    @Override
    public Iterator<MapleData> iterator() {
        return this.getChildren().iterator();
    }
    
    @Override
    public MapleDataEntity getParent() {
        return this.parent;
    }
}
