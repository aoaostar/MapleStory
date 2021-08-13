package constants;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishingConstants
{
    private static FishingConstants instance;
    private static boolean CANLOG;
    private static final Logger log;
    private Properties itempb_cfg;
    private final String[] FishingItem;
    private final String[] FishingItemS;
    private final int FishingItemSJ;
    private final int FishingItemSL;
    private final int FishingItemSLS;
    private final int FishingVIPSJ;
    private final int FishingSJ;
    private final int FishingMeso;
    private final int FishingMesoS;
    private final int FishingExp;
    private final int FishingExpS;
    
    public static FishingConstants getInstance() {
        if (FishingConstants.instance == null) {
            FishingConstants.instance = new FishingConstants();
        }
        return FishingConstants.instance;
    }
    
    public FishingConstants() {
        this.itempb_cfg = new Properties();
        try {
            String path = System.getProperty("server_property_fish_path");
            final InputStreamReader is = new FileReader(path);
//            final InputStreamReader is = new FileReader("HuaiMS_钓鱼设置.properties");
            Throwable localThrowable2 = null;
            try {
                this.itempb_cfg.load(is);
            }
            catch (IOException localThrowable3) {
                localThrowable2 = localThrowable3;
                throw localThrowable3;
            }
            finally {
                if (is != null) {
                    if (localThrowable2 != null) {
                        try {
                            is.close();
                        }
                        catch (IOException x2) {
                            localThrowable2.addSuppressed(x2);
                        }
                    }
                    else {
                        is.close();
                    }
                }
            }
        }
        catch (Exception e) {
            FishingConstants.log.error("Could not configuration", e);
        }
        this.FishingItem = this.itempb_cfg.getProperty("FishingItem").split(",");
        this.FishingItemS = this.itempb_cfg.getProperty("FishingItemS").split(",");
        this.FishingItemSJ = Integer.parseInt(this.itempb_cfg.getProperty("FishingItemSJ"));
        this.FishingItemSLS = Integer.parseInt(this.itempb_cfg.getProperty("FishingItemSLS"));
        this.FishingItemSL = Integer.parseInt(this.itempb_cfg.getProperty("FishingItemSL"));
        this.FishingVIPSJ = Integer.parseInt(this.itempb_cfg.getProperty("FishingVIPSJ"));
        this.FishingSJ = Integer.parseInt(this.itempb_cfg.getProperty("FishingSJ"));
        this.FishingMeso = Integer.parseInt(this.itempb_cfg.getProperty("FishingMeso"));
        this.FishingMesoS = Integer.parseInt(this.itempb_cfg.getProperty("FishingMesoS"));
        this.FishingExp = Integer.parseInt(this.itempb_cfg.getProperty("FishingExp"));
        this.FishingExpS = Integer.parseInt(this.itempb_cfg.getProperty("FishingExpS"));
    }
    
    public String[] getFishingItem() {
        return this.FishingItem;
    }
    
    public String[] getFishingItemS() {
        return this.FishingItemS;
    }
    
    public int getFishingItemSJ() {
        return this.FishingItemSJ;
    }
    
    public int getFishingItemSLS() {
        return this.FishingItemSLS;
    }
    
    public int getFishingItemSL() {
        return this.FishingItemSL;
    }
    
    public int getFishingVIPSJ() {
        return this.FishingVIPSJ;
    }
    
    public int getFishingSJ() {
        return this.FishingSJ;
    }
    
    public int getFishingMeso() {
        return this.FishingMeso;
    }
    
    public int getFishingMesoS() {
        return this.FishingMesoS;
    }
    
    public int getFishingExp() {
        return this.FishingExp;
    }
    
    public int getFishingExpS() {
        return this.FishingExpS;
    }
    
    public boolean isCANLOG() {
        return FishingConstants.CANLOG;
    }
    
    public void setCANLOG(final boolean CANLOG) {
        FishingConstants.CANLOG = CANLOG;
    }
    
    static {
        FishingConstants.instance = null;
        log = LoggerFactory.getLogger(FishingConstants.class);
    }
}
