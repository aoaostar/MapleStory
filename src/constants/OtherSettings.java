package constants;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtherSettings
{
    private static OtherSettings instance;
    private static boolean CANLOG;
    private static final Logger log;
    private Properties itempb_cfg;
    private String[] itempb_id;
    private String[] itemjy_id;
    private String[] itemgy_id;
    private String[] mappb_id;
    
    public static OtherSettings getInstance() {
        if (OtherSettings.instance == null) {
            OtherSettings.instance = new OtherSettings();
        }
        return OtherSettings.instance;
    }
    
    public OtherSettings() {
        this.itempb_cfg = new Properties();
        try {
            String path = System.getProperty("server_property_file_path");
            final InputStreamReader is = new FileReader(path);
//            final InputStreamReader is = new FileReader("HuaiMS_服务端配置.properties");
            this.itempb_cfg.load(is);
            is.close();
            this.itempb_id = this.itempb_cfg.getProperty("cashban").split(",");
            this.itemjy_id = this.itempb_cfg.getProperty("cashjy", "0").split(",");
            this.itemgy_id = this.itempb_cfg.getProperty("gysj", "0").split(",");
        }
        catch (IOException e) {
            OtherSettings.log.error("Could not configuration", (Throwable)e);
        }
    }
    
    public String[] getItempb_id() {
        return this.itempb_id;
    }
    
    public String[] getItemgy_id() {
        return this.itemgy_id;
    }
    
    public String[] getItemjy_id() {
        return this.itemjy_id;
    }
    
    public String[] getMappb_id() {
        return this.mappb_id;
    }
    
    public boolean isCANLOG() {
        return OtherSettings.CANLOG;
    }
    
    public void setCANLOG(final boolean CANLOG) {
        OtherSettings.CANLOG = CANLOG;
    }
    
    static {
        OtherSettings.instance = null;
        log = LoggerFactory.getLogger((Class)OtherSettings.class);
    }
}
