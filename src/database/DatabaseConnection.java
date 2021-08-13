package database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnection
{
    private static final HashMap<Integer, ConWrapper> connections;
    private static final ReentrantLock lock;
    private static String dbDriver;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;
    private static boolean propsInited;
    private static final Properties dbProps;
    private static long connectionTimeOut;
    public static int CLOSE_CURRENT_RESULT;
    public static int KEEP_CURRENT_RESULT;
    public static int CLOSE_ALL_RESULTS;
    public static int SUCCESS_NO_INFO;
    public static int EXECUTE_FAILED;
    public static int RETURN_GENERATED_KEYS;
    public static int NO_GENERATED_KEYS;
    
    public static Connection getConnection() {
        final Thread cThread = Thread.currentThread();
        final int threadID = (int)cThread.getId();
        ConWrapper ret = DatabaseConnection.connections.get(threadID);
        if (ret == null) {
            final Connection retCon = connectToDB();
            ret = new ConWrapper(threadID, retCon);
            ret.id = threadID;
            DatabaseConnection.connections.put(threadID, ret);
        }
        return ret.getConnection();
    }
    
    private static long getWaitTimeout(final Connection con) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SHOW VARIABLES LIKE 'wait_timeout'");
            if (rs.next()) {
                return Math.max(1000, rs.getInt(2) * 1000 - 1000);
            }
            return -1L;
        }
        catch (SQLException ex) {
            final long n = -1L;
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex2) {}
                finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (SQLException ex3) {}
                    }
                }
            }
            return n;
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex4) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (SQLException ex5) {}
                    }
                }
                finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (SQLException ex6) {}
                    }
                }
            }
        }
    }
    
    private static Connection connectToDB() {
        if (!DatabaseConnection.propsInited) {
            try {
                String path = System.getProperty("server_property_db_path");
//                System.out.println("load db pro"+path);
                final FileReader fR = new FileReader(path);
                DatabaseConnection.dbProps.load(fR);
                fR.close();
            }
            catch (IOException ex) {
                throw new DatabaseException(ex);
            }
            DatabaseConnection.dbDriver = DatabaseConnection.dbProps.getProperty("driverClassName");
            DatabaseConnection.dbUrl = DatabaseConnection.dbProps.getProperty("url");
            DatabaseConnection.dbUser = DatabaseConnection.dbProps.getProperty("username");
            DatabaseConnection.dbPass = DatabaseConnection.dbProps.getProperty("password");
            try {
                DatabaseConnection.connectionTimeOut = Long.parseLong(DatabaseConnection.dbProps.getProperty("timeout"));
            }
            catch (NumberFormatException e2) {
                System.out.println("[DB信息] 无法读取超时信息，使用默认值: " + DatabaseConnection.connectionTimeOut + " ");
            }
        }
        try {
            Class.forName(DatabaseConnection.dbDriver);
        }
        catch (ClassNotFoundException e3) {
            System.out.println("[DB信息] 找不到JDBC驱动程序。");
        }
        try {
            final Connection con = DriverManager.getConnection(DatabaseConnection.dbUrl, DatabaseConnection.dbUser, DatabaseConnection.dbPass);
            if (!DatabaseConnection.propsInited) {
                final long timeout = getWaitTimeout(con);
                if (timeout == -1L) {
                    System.out.println("[DB信息] 无法读取 Wait_Timeout, using " + DatabaseConnection.connectionTimeOut + " instead.");
                }
                else {
                    DatabaseConnection.connectionTimeOut = timeout;
                    System.out.println("数据库正在加载.请稍等....");
                }
                DatabaseConnection.propsInited = true;
            }
            return con;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    
    public static void closeAll() throws SQLException {
        for (final ConWrapper con : DatabaseConnection.connections.values()) {
            con.connection.close();
        }
        DatabaseConnection.connections.clear();
    }
    
    public static void closeTimeout() {
        int i = 0;
        DatabaseConnection.lock.lock();
        final List<Integer> keys = new ArrayList<Integer>(DatabaseConnection.connections.keySet());
        try {
            for (final Integer tid : keys) {
                final ConWrapper con = DatabaseConnection.connections.get(tid);
                if (con.close()) {
                    ++i;
                }
            }
        }
        finally {
            DatabaseConnection.lock.unlock();
        }
    }
    
    static {
        connections = new HashMap<Integer, ConWrapper>();
        lock = new ReentrantLock();
        DatabaseConnection.propsInited = false;
        dbProps = new Properties();
        DatabaseConnection.connectionTimeOut = 300000L;
        DatabaseConnection.CLOSE_CURRENT_RESULT = 1;
        DatabaseConnection.KEEP_CURRENT_RESULT = 2;
        DatabaseConnection.CLOSE_ALL_RESULTS = 3;
        DatabaseConnection.SUCCESS_NO_INFO = -2;
        DatabaseConnection.EXECUTE_FAILED = -3;
        DatabaseConnection.RETURN_GENERATED_KEYS = 1;
        DatabaseConnection.NO_GENERATED_KEYS = 2;
    }
    
    public static class ConWrapper
    {
        private final int tid;
        private long lastAccessTime;
        private Connection connection;
        private int id;
        
        public ConWrapper(final int tid, final Connection con) {
            this.lastAccessTime = 0L;
            this.tid = tid;
            this.connection = con;
        }
        
        public Connection getConnection() {
            if (this.expiredConnection()) {
                System.out.println("[DB信息] 连接 " + this.id + " 已经超时.重新连接...");
                try {
                    this.connection.close();
                }
                catch (SQLException ex) {}
                this.connection = connectToDB();
            }
            this.lastAccessTime = System.currentTimeMillis();
            return this.connection;
        }
        
        public boolean expiredConnection() {
            if (this.lastAccessTime == 0L) {
                return false;
            }
            try {
                return System.currentTimeMillis() - this.lastAccessTime >= DatabaseConnection.connectionTimeOut || this.connection.isClosed();
            }
            catch (SQLException ex) {
                return true;
            }
        }
        
        public boolean close() {
            boolean ret = false;
            if (this.connection == null) {
                ret = false;
            }
            else {
                try {
                    DatabaseConnection.lock.lock();
                    try {
                        Label_0058: {
                            if (!this.expiredConnection()) {
                                if (!this.connection.isValid(10)) {
                                    break Label_0058;
                                }
                            }
                            try {
                                this.connection.close();
                                ret = true;
                            }
                            catch (SQLException e) {
                                ret = false;
                            }
                        }
                        DatabaseConnection.connections.remove(this.tid);
                    }
                    finally {
                        DatabaseConnection.lock.unlock();
                    }
                }
                catch (SQLException ex) {
                    ret = false;
                }
            }
            return ret;
        }
    }
}
