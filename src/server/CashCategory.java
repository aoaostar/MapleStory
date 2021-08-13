package server;

public class CashCategory
{
    private final int id;
    private final int parent;
    private final int flag;
    private final int sold;
    private final String name;
    
    public CashCategory(final int id, final String name, final int parent, final int flag, final int sold) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.flag = flag;
        this.sold = sold;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getParentDirectory() {
        return this.parent;
    }
    
    public int getFlag() {
        return this.flag;
    }
    
    public int getSold() {
        return this.sold;
    }
    
    public enum CSFlag
    {
        NORMAL(0), 
        NEW(1), 
        HOT(2);
        
        private final int value;
        
        private CSFlag(final int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
}
