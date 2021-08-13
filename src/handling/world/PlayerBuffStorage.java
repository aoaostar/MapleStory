package handling.world;

import client.MapleCoolDownValueHolder;
import client.MapleDiseaseValueHolder;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerBuffStorage implements Serializable
{
    private static final Map<Integer, List<PlayerBuffValueHolder>> buffs;
    private static final Map<Integer, List<MapleCoolDownValueHolder>> coolDowns;
    private static final Map<Integer, List<MapleDiseaseValueHolder>> diseases;
    
    public static void addBuffsToStorage(final int chrid, final List<PlayerBuffValueHolder> toStore) {
        PlayerBuffStorage.buffs.put(chrid, toStore);
    }
    
    public static void addCooldownsToStorage(final int chrid, final List<MapleCoolDownValueHolder> toStore) {
        PlayerBuffStorage.coolDowns.put(chrid, toStore);
    }
    
    public static void addDiseaseToStorage(final int chrid, final List<MapleDiseaseValueHolder> toStore) {
        PlayerBuffStorage.diseases.put(chrid, toStore);
    }
    
    public static List<PlayerBuffValueHolder> getBuffsFromStorage(final int chrid) {
        return PlayerBuffStorage.buffs.remove(chrid);
    }
    
    public static List<MapleCoolDownValueHolder> getCooldownsFromStorage(final int chrid) {
        return PlayerBuffStorage.coolDowns.remove(chrid);
    }
    
    public static List<MapleDiseaseValueHolder> getDiseaseFromStorage(final int chrid) {
        return PlayerBuffStorage.diseases.remove(chrid);
    }
    
    static {
        buffs = new ConcurrentHashMap<Integer, List<PlayerBuffValueHolder>>();
        coolDowns = new ConcurrentHashMap<Integer, List<MapleCoolDownValueHolder>>();
        diseases = new ConcurrentHashMap<Integer, List<MapleDiseaseValueHolder>>();
    }
}
