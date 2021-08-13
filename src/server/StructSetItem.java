package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StructSetItem
{
    public byte completeCount;
    public byte setItemID;
    public Map<Integer, SetItem> items;
    public List<Integer> itemIDs;
    
    public StructSetItem() {
        this.items = new LinkedHashMap<Integer, SetItem>();
        this.itemIDs = new ArrayList<Integer>();
    }
    
    public Map<Integer, SetItem> getItems() {
        return new LinkedHashMap<Integer, SetItem>(this.items);
    }
    
    public static class SetItem
    {
        public int incPDD;
        public int incMDD;
        public int incSTR;
        public int incDEX;
        public int incINT;
        public int incLUK;
        public int incACC;
        public int incPAD;
        public int incMAD;
        public int incSpeed;
        public int incMHP;
        public int incMMP;
    }
}
