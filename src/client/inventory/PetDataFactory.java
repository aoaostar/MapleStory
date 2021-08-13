package client.inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;

public class PetDataFactory {
    private static final MapleDataProvider dataRoot = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Item.wz"));

    private static final Map<Pair<Integer, Integer>, PetCommand> petCommands = new HashMap<>();

    private static final Map<Integer, Integer> petHunger = new HashMap<>();

    public static PetCommand getPetCommand(int petId, int skillId) {
        PetCommand ret = petCommands.get(new Pair(Integer.valueOf(petId), Integer.valueOf(skillId)));
        if (ret != null)
            return ret;
        MapleData skillData = dataRoot.getData("Pet/" + petId + ".img");
        int prob = 0;
        int inc = 0;
        if (skillData != null) {
            prob = MapleDataTool.getInt("interact/" + skillId + "/prob", skillData, 0);
            inc = MapleDataTool.getInt("interact/" + skillId + "/inc", skillData, 0);
        }
        ret = new PetCommand(petId, skillId, prob, inc);
        petCommands.put(new Pair(Integer.valueOf(petId), Integer.valueOf(skillId)), ret);
        return ret;
    }

    public static int getHunger(int petId) {
        Integer ret = petHunger.get(Integer.valueOf(petId));
        if (ret != null)
            return ret.intValue();
        MapleData hungerData = dataRoot.getData("Pet/" + petId + ".img").getChildByPath("info/hungry");
        ret = Integer.valueOf(MapleDataTool.getInt(hungerData, 1));
        petHunger.put(Integer.valueOf(petId), ret);
        return ret.intValue();
    }
}
