package database;

public class DatabaseException extends RuntimeException
{
    private static long serialVersionUID;
    
    public DatabaseException(final String msg) {
        super(msg);
    }
    
    public DatabaseException(final Exception e) {
    }
    
    public DatabaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    static {
        DatabaseException.serialVersionUID = -420103154764822555L;
    }
}
