package client;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.StringUtil;

public class SkillFactory
{
    private static final Map<Integer, ISkill> skills;
    private static final Map<Integer, List<Integer>> skillsByJob;
    private static final Map<Integer, SummonSkillEntry> SummonSkillInformation;
    private static final MapleData stringData;
    private static final MapleDataProvider datasource;


    public static ISkill getSkill(final int id) {
        if (SkillFactory.skills.size() != 0) {
            return SkillFactory.skills.get(id);
        }
        System.out.println("加载 技能完成 :::");
        final MapleDataProvider datasource = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Skill.wz"));
        final MapleDataDirectoryEntry root = datasource.getRoot();
        for (final MapleDataFileEntry topDir : root.getFiles()) {
            if (topDir.getName().length() <= 8) {
                for (final MapleData data : datasource.getData(topDir.getName())) {
                    if (data.getName().equals("skill")) {
                        for (final MapleData data2 : data) {
                            if (data2 != null) {
                                final int skillid = Integer.parseInt(data2.getName());
                                final Skill skil = Skill.loadFromData(skillid, data2);
                                List<Integer> job = SkillFactory.skillsByJob.get(skillid / 10000);
                                if (job == null) {
                                    job = new ArrayList<Integer>();
                                    SkillFactory.skillsByJob.put(skillid / 10000, job);
                                }
                                job.add(skillid);
                                skil.setName(getName(skillid));
                                SkillFactory.skills.put(skillid, skil);
                                final MapleData summon_data = data2.getChildByPath("summon/attack1/info");
                                if (summon_data == null) {
                                    continue;
                                }
                                final SummonSkillEntry sse = new SummonSkillEntry();
                                sse.attackAfter = (short)MapleDataTool.getInt("attackAfter", summon_data, 999999);
                                sse.type = (byte)MapleDataTool.getInt("type", summon_data, 0);
                                sse.mobCount = (byte)MapleDataTool.getInt("mobCount", summon_data, 1);
                                SkillFactory.SummonSkillInformation.put(skillid, sse);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static ISkill getSkill1(final int id) {
        ISkill ret = SkillFactory.skills.get(id);
        if (ret != null) {
            return ret;
        }
        synchronized (SkillFactory.skills) {
            ret = SkillFactory.skills.get(id);
            if (ret == null) {
                final int job = id / 10000;
                final MapleData skillroot = SkillFactory.datasource.getData(StringUtil.getLeftPaddedStr(String.valueOf(job), '0', 3) + ".img");
                final MapleData skillData = skillroot.getChildByPath("skill/" + StringUtil.getLeftPaddedStr(String.valueOf(id), '0', 7));
                if (skillData != null) {
                    ret = Skill.loadFromData(id, skillData);
                }
                SkillFactory.skills.put(id, ret);
            }
            return ret;
        }
    }
    
    public static List<Integer> getSkillsByJob(final int jobId) {
        return SkillFactory.skillsByJob.get(jobId);
    }
    
    public static String getSkillName(final int id) {
        final ISkill skil = getSkill(id);
        if (skil != null) {
            return skil.getName();
        }
        return null;
    }
    
    public static String getName(final int id) {
        String strId = Integer.toString(id);
        strId = StringUtil.getLeftPaddedStr(strId, '0', 7);
        final MapleData skillroot = SkillFactory.stringData.getChildByPath(strId);
        if (skillroot != null) {
            return MapleDataTool.getString(skillroot.getChildByPath("name"), "");
        }
        return null;
    }
    
    public static SummonSkillEntry getSummonData(final int skillid) {
        return SkillFactory.SummonSkillInformation.get(skillid);
    }
    
    public static Collection<ISkill> getAllSkills() {
        return SkillFactory.skills.values();
    }
    
    static {
        skills = new HashMap<Integer, ISkill>();
        skillsByJob = new HashMap<Integer, List<Integer>>();
        SummonSkillInformation = new HashMap<Integer, SummonSkillEntry>();
        stringData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/String.wz")).getData("Skill.img");
        datasource = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Skill.wz"));
    }
}
