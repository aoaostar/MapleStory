package server.life;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;


public class MobSkillFactory
{
    private static final Map<Pair<Integer, Integer>, MobSkill> mobSkills;
    private static final MapleDataProvider dataSource;
    private static final MapleData skillRoot;
    
    public static MobSkillFactory getInstance() {
        return SingletonHolder.instance;
    }
    
    public static MobSkill getMobSkill(final int skillId, final int level) {
        MobSkill ret = MobSkillFactory.mobSkills.get(new Pair(skillId, level));
        if (ret != null) {
            return ret;
        }
        if (MobSkillFactory.skillRoot == null || MobSkillFactory.skillRoot.getChildren() == null || MobSkillFactory.skillRoot.getChildByPath(String.valueOf(skillId)) == null || MobSkillFactory.skillRoot.getChildByPath(String.valueOf(skillId)).getChildren() == null || MobSkillFactory.skillRoot.getChildByPath(String.valueOf(skillId)).getChildByPath("level") == null) {
            return null;
        }
        final MapleData skillData = MobSkillFactory.skillRoot.getChildByPath(skillId + "/level/" + level);
        if (skillData != null && skillData.getChildren() != null) {
            final List<Integer> toSummon = new ArrayList<Integer>();
            for (int i = 0; i > -1 && skillData.getChildByPath(String.valueOf(i)) != null; ++i) {
                toSummon.add(MapleDataTool.getInt(skillData.getChildByPath(String.valueOf(i)), 0));
            }
            final MapleData ltd = skillData.getChildByPath("lt");
            Point lt = null;
            Point rb = null;
            if (ltd != null) {
                lt = (Point)ltd.getData();
                rb = (Point)skillData.getChildByPath("rb").getData();
            }
            ret = new MobSkill(skillId, level);
            ret.addSummons(toSummon);
            ret.setCoolTime(MapleDataTool.getInt("interval", skillData, 0) * 1000);
            ret.setDuration(MapleDataTool.getInt("time", skillData, 1) * 1000);
            ret.setHp(MapleDataTool.getInt("hp", skillData, 100));
            ret.setMpCon(MapleDataTool.getInt(skillData.getChildByPath("mpCon"), 0));
            ret.setSpawnEffect(MapleDataTool.getInt("summonEffect", skillData, 0));
            ret.setX(MapleDataTool.getInt("x", skillData, 1));
            ret.setY(MapleDataTool.getInt("y", skillData, 1));
            ret.setProp(MapleDataTool.getInt("prop", skillData, 100) / 100.0f);
            ret.setLimit((short)MapleDataTool.getInt("limit", skillData, 0));
            ret.setLtRb(lt, rb);
            MobSkillFactory.mobSkills.put(new Pair<Integer, Integer>(skillId, level), ret);
        }
        return ret;
    }
    
    static {
        mobSkills = new HashMap<Pair<Integer, Integer>, MobSkill>();
        dataSource = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Skill.wz"));
        skillRoot = MobSkillFactory.dataSource.getData("MobSkill.img");
    }
    
    private static class SingletonHolder
    {
        protected static final MobSkillFactory instance;
        
        static {
            instance = new MobSkillFactory();
        }
    }
}
