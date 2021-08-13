package client.inventory;

public class InventoryException extends RuntimeException
{
    private static long serialVersionUID;
    
    public InventoryException() {
    }
    
    public InventoryException(final String msg) {
        super(msg);
    }
    
    static {
        InventoryException.serialVersionUID = 1L;
    }
}
