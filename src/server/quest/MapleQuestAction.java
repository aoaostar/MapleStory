package server.quest;

import client.ISkill;
import client.MapleCharacter;
import client.MapleQuestStatus;
import client.MapleStat;
import client.SkillFactory;
import client.inventory.InventoryException;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataTool;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.Randomizer;
import tools.MaplePacketCreator;

public class MapleQuestAction implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private final MapleQuestActionType type;
    private final MapleData data;
    private final MapleQuest quest;
    
    private static boolean canGetItem(final MapleData item, final MapleCharacter c) {
        if (item.getChildByPath("gender") != null) {
            final int gender = MapleDataTool.getInt(item.getChildByPath("gender"));
            if (gender != 2 && gender != c.getGender()) {
                return false;
            }
        }
        if (item.getChildByPath("job") != null) {
            final int job = MapleDataTool.getInt(item.getChildByPath("job"));
            final List<Integer> code = getJobBy5ByteEncoding(job);
            boolean jobFound = false;
            for (final int codec : code) {
                if (codec / 100 == c.getJob() / 100) {
                    jobFound = true;
                    break;
                }
            }
            if (!jobFound && item.getChildByPath("jobEx") != null) {
                final int jobEx = MapleDataTool.getInt(item.getChildByPath("jobEx"));
                final List<Integer> codeEx = getJobBy5ByteEncoding(jobEx);
                for (final int codec2 : codeEx) {
                    if (codec2 / 100 == c.getJob() / 100) {
                        jobFound = true;
                        break;
                    }
                }
            }
            return jobFound;
        }
        return true;
    }
    
    private static List<Integer> getJobBy5ByteEncoding(final int encoded) {
        final List<Integer> ret = new ArrayList<Integer>();
        if ((encoded & 0x1) != 0x0) {
            ret.add(0);
        }
        if ((encoded & 0x2) != 0x0) {
            ret.add(100);
        }
        if ((encoded & 0x4) != 0x0) {
            ret.add(200);
        }
        if ((encoded & 0x8) != 0x0) {
            ret.add(300);
        }
        if ((encoded & 0x10) != 0x0) {
            ret.add(400);
        }
        if ((encoded & 0x20) != 0x0) {
            ret.add(500);
        }
        if ((encoded & 0x400) != 0x0) {
            ret.add(1000);
        }
        if ((encoded & 0x800) != 0x0) {
            ret.add(1100);
        }
        if ((encoded & 0x1000) != 0x0) {
            ret.add(1200);
        }
        if ((encoded & 0x2000) != 0x0) {
            ret.add(1300);
        }
        if ((encoded & 0x4000) != 0x0) {
            ret.add(1400);
        }
        if ((encoded & 0x8000) != 0x0) {
            ret.add(1500);
        }
        if ((encoded & 0x20000) != 0x0) {
            ret.add(2001);
            ret.add(2200);
        }
        if ((encoded & 0x100000) != 0x0) {
            ret.add(2000);
            ret.add(2001);
        }
        if ((encoded & 0x200000) != 0x0) {
            ret.add(2100);
        }
        if ((encoded & 0x400000) != 0x0) {
            ret.add(2001);
            ret.add(2200);
        }
        if ((encoded & 0x40000000) != 0x0) {
            ret.add(3000);
            ret.add(3200);
            ret.add(3300);
            ret.add(3500);
        }
        return ret;
    }
    
    public MapleQuestAction(final MapleQuestActionType type, final MapleData data, final MapleQuest quest) {
        this.type = type;
        this.data = data;
        this.quest = quest;
    }
    
    public boolean RestoreLostItem(final MapleCharacter c, final int itemid) {
        if (this.type == MapleQuestActionType.item) {
            for (final MapleData iEntry : this.data.getChildren()) {
                final int retitem = MapleDataTool.getInt(iEntry.getChildByPath("id"), -1);
                final int counts = MapleDataTool.getInt(iEntry.getChildByPath("count"), -1);
                if (retitem == itemid) {
                    if (!c.haveItem(retitem, counts, true, false)) {
                        c.removeAll(retitem);
                        MapleInventoryManipulator.addById(c.getClient(), retitem, (short)counts, (byte)0);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public void runStart(final MapleCharacter c, final Integer extSelection) {
        switch (this.type) {
            case exp: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                c.gainExp(MapleDataTool.getInt(this.data, 0) * GameConstants.getExpRate_Quest(c.getLevel()), true, true, true);
                break;
            }
            case item: {
                final Map<Integer, Integer> props = new HashMap<Integer, Integer>();
                for (final MapleData iEntry : this.data.getChildren()) {
                    final MapleData prop = iEntry.getChildByPath("prop");
                    if (prop != null && MapleDataTool.getInt(prop) != -1 && canGetItem(iEntry, c)) {
                        for (int i = 0; i < MapleDataTool.getInt(iEntry.getChildByPath("prop")); ++i) {
                            props.put(props.size(), MapleDataTool.getInt(iEntry.getChildByPath("id")));
                        }
                    }
                }
                int selection = 0;
                int extNum = 0;
                if (props.size() > 0) {
                    selection = props.get(Randomizer.nextInt(props.size()));
                }
                for (final MapleData iEntry2 : this.data.getChildren()) {
                    if (!canGetItem(iEntry2, c)) {
                        continue;
                    }
                    final int id = MapleDataTool.getInt(iEntry2.getChildByPath("id"), -1);
                    if (iEntry2.getChildByPath("prop") != null) {
                        if (MapleDataTool.getInt(iEntry2.getChildByPath("prop")) == -1) {
                            if (extSelection != extNum++) {
                                continue;
                            }
                        }
                        else if (id != selection) {
                            continue;
                        }
                    }
                    final short count = (short)MapleDataTool.getInt(iEntry2.getChildByPath("count"), 1);
                    if (count < 0) {
                        try {
                            MapleInventoryManipulator.removeById(c.getClient(), GameConstants.getInventoryType(id), id, count * -1, true, false);
                        }
                        catch (InventoryException ie) {
                            System.err.println("[h4x] Completing a quest without meeting the requirements" + ie);
                        }
                        c.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, count, true));
                    }
                    else {
                        final int period = MapleDataTool.getInt(iEntry2.getChildByPath("period"), 0) / 1440;
                        final String name = MapleItemInformationProvider.getInstance().getName(id);
                        if (id / 10000 == 114 && name != null && name.length() > 0) {
                            final String msg = "你已獲得稱號 <" + name + ">";
                            c.dropMessage(5, msg);
                            c.dropMessage(5, msg);
                        }
                        MapleInventoryManipulator.addById(c.getClient(), id, count, "", null, period, (byte)0);
                        c.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, count, true));
                    }
                }
                break;
            }
            case nextQuest: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                c.getClient().getSession().write(MaplePacketCreator.updateQuestFinish(this.quest.getId(), status.getNpc(), MapleDataTool.getInt(this.data)));
                break;
            }
            case money: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                c.gainMeso(MapleDataTool.getInt(this.data, 0), true, false, true);
                break;
            }
            case quest: {
                for (final MapleData qEntry : this.data) {
                    c.updateQuest(new MapleQuestStatus(MapleQuest.getInstance(MapleDataTool.getInt(qEntry.getChildByPath("id"))), (byte)MapleDataTool.getInt(qEntry.getChildByPath("state"), 0)));
                }
                break;
            }
            case skill: {
                for (final MapleData sEntry : this.data) {
                    final int skillid = MapleDataTool.getInt(sEntry.getChildByPath("id"));
                    final int skillLevel = MapleDataTool.getInt(sEntry.getChildByPath("skillLevel"), 0);
                    final int masterLevel = MapleDataTool.getInt(sEntry.getChildByPath("masterLevel"), 0);
                    final ISkill skillObject = SkillFactory.getSkill(skillid);
                    for (final MapleData applicableJob : sEntry.getChildByPath("job")) {
                        if (skillObject.isBeginnerSkill() || c.getJob() == MapleDataTool.getInt(applicableJob)) {
                            c.changeSkillLevel(skillObject, (byte)Math.max(skillLevel, c.getSkillLevel(skillObject)), (byte)Math.max(masterLevel, c.getMasterLevel(skillObject)));
                            break;
                        }
                    }
                }
                break;
            }
            case pop: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                final int fameGain = MapleDataTool.getInt(this.data, 0);
                c.addFame(fameGain);
                c.updateSingleStat(MapleStat.FAME, c.getFame());
                c.getClient().getSession().write(MaplePacketCreator.getShowFameGain(fameGain));
                break;
            }
            case buffItemID: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                final int tobuff = MapleDataTool.getInt(this.data, -1);
                if (tobuff == -1) {
                    break;
                }
                MapleItemInformationProvider.getInstance().getItemEffect(tobuff).applyTo(c);
                break;
            }
            case sp: {
                final MapleQuestStatus status = c.getQuest(this.quest);
                if (status.getForfeited() > 0) {
                    break;
                }
                for (final MapleData iEntry3 : this.data.getChildren()) {
                    final int sp_val = MapleDataTool.getInt(iEntry3.getChildByPath("sp_value"), 0);
                    if (iEntry3.getChildByPath("job") != null) {
                        int finalJob = 0;
                        for (final MapleData jEntry : iEntry3.getChildByPath("job").getChildren()) {
                            final int job_val = MapleDataTool.getInt(jEntry, 0);
                            if (c.getJob() >= job_val && job_val > finalJob) {
                                finalJob = job_val;
                            }
                        }
                        if (finalJob == 0) {
                            c.gainSP(sp_val);
                        }
                        else {
                            c.gainSP(sp_val, GameConstants.getSkillBook(finalJob));
                        }
                    }
                    else {
                        c.gainSP(sp_val);
                    }
                }
                break;
            }
        }
    }
    
    public boolean checkEnd(final MapleCharacter c, final Integer extSelection) {
        switch (this.type) {
            case item: {
                final Map<Integer, Integer> props = new HashMap<Integer, Integer>();
                for (final MapleData iEntry : this.data.getChildren()) {
                    final MapleData prop = iEntry.getChildByPath("prop");
                    if (prop != null && MapleDataTool.getInt(prop) != -1 && canGetItem(iEntry, c)) {
                        for (int i = 0; i < MapleDataTool.getInt(iEntry.getChildByPath("prop")); ++i) {
                            props.put(props.size(), MapleDataTool.getInt(iEntry.getChildByPath("id")));
                        }
                    }
                }
                int selection = 0;
                int extNum = 0;
                if (props.size() > 0) {
                    selection = props.get(Randomizer.nextInt(props.size()));
                }
                byte eq = 0;
                byte use = 0;
                byte setup = 0;
                byte etc = 0;
                byte cash = 0;
                for (final MapleData iEntry2 : this.data.getChildren()) {
                    if (!canGetItem(iEntry2, c)) {
                        continue;
                    }
                    final int id = MapleDataTool.getInt(iEntry2.getChildByPath("id"), -1);
                    if (iEntry2.getChildByPath("prop") != null) {
                        if (MapleDataTool.getInt(iEntry2.getChildByPath("prop")) == -1) {
                            if (extSelection != extNum++) {
                                continue;
                            }
                        }
                        else if (id != selection) {
                            continue;
                        }
                    }
                    final short count = (short)MapleDataTool.getInt(iEntry2.getChildByPath("count"), 1);
                    if (count < 0) {
                        if (!c.haveItem(id, count, false, true)) {
                            c.dropMessage(1, "You are short of some item to complete quest.");
                            return false;
                        }
                        continue;
                    }
                    else {
                        if (MapleItemInformationProvider.getInstance().isPickupRestricted(id) && c.haveItem(id, 1, true, false)) {
                            c.dropMessage(1, "You have this item already: " + MapleItemInformationProvider.getInstance().getName(id));
                            return false;
                        }
                        switch (GameConstants.getInventoryType(id)) {
                            case EQUIP: {
                                ++eq;
                                continue;
                            }
                            case USE: {
                                ++use;
                                continue;
                            }
                            case SETUP: {
                                ++setup;
                                continue;
                            }
                            case ETC: {
                                ++etc;
                                continue;
                            }
                            case CASH: {
                                ++cash;
                                continue;
                            }
                        }
                    }
                }
                if (c.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < eq) {
                    c.dropMessage(1, "请为您的装备栏腾出空间.");
                    return false;
                }
                if (c.getInventory(MapleInventoryType.USE).getNumFreeSlot() < use) {
                    c.dropMessage(1, "请为您的消耗栏腾出空间.");
                    return false;
                }
                if (c.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < setup) {
                    c.dropMessage(1, "请为您的设置栏腾出空间.");
                    return false;
                }
                if (c.getInventory(MapleInventoryType.ETC).getNumFreeSlot() < etc) {
                    c.dropMessage(1, "请为您的其他栏腾出空间.");
                    return false;
                }
                if (c.getInventory(MapleInventoryType.CASH).getNumFreeSlot() < cash) {
                    c.dropMessage(1, "请为您的特殊栏腾出空间.");
                    return false;
                }
                return true;
            }
            case money: {
                final int meso = MapleDataTool.getInt(this.data, 0);
                if (c.getMeso() + meso < 0) {
                    c.dropMessage(1, "Meso exceed the max amount, 2147483647.");
                    return false;
                }
                if (meso < 0 && c.getMeso() < Math.abs(meso)) {
                    c.dropMessage(1, "Insufficient meso.");
                    return false;
                }
                return true;
            }
            default: {
                return true;
            }
        }
    }
    
    public void runEnd(final MapleCharacter c, final Integer extSelection) {
        switch (this.type) {
            case exp: {
                c.gainExp(MapleDataTool.getInt(this.data, 0) * GameConstants.getExpRate_Quest(c.getLevel()), true, true, true);
                break;
            }
            case item: {
                final Map<Integer, Integer> props = new HashMap<Integer, Integer>();
                for (final MapleData iEntry : this.data.getChildren()) {
                    final MapleData prop = iEntry.getChildByPath("prop");
                    if (prop != null && MapleDataTool.getInt(prop) != -1 && canGetItem(iEntry, c)) {
                        for (int i = 0; i < MapleDataTool.getInt(iEntry.getChildByPath("prop")); ++i) {
                            props.put(props.size(), MapleDataTool.getInt(iEntry.getChildByPath("id")));
                        }
                    }
                }
                int selection = 0;
                int extNum = 0;
                if (props.size() > 0) {
                    selection = props.get(Randomizer.nextInt(props.size()));
                }
                for (final MapleData iEntry2 : this.data.getChildren()) {
                    if (!canGetItem(iEntry2, c)) {
                        continue;
                    }
                    final int id = MapleDataTool.getInt(iEntry2.getChildByPath("id"), -1);
                    if (iEntry2.getChildByPath("prop") != null) {
                        if (MapleDataTool.getInt(iEntry2.getChildByPath("prop")) == -1) {
                            if (extSelection != extNum++) {
                                continue;
                            }
                        }
                        else if (id != selection) {
                            continue;
                        }
                    }
                    final short count = (short)MapleDataTool.getInt(iEntry2.getChildByPath("count"), 1);
                    if (count < 0) {
                        MapleInventoryManipulator.removeById(c.getClient(), GameConstants.getInventoryType(id), id, count * -1, true, false);
                        c.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, count, true));
                    }
                    else {
                        final int period = MapleDataTool.getInt(iEntry2.getChildByPath("period"), 0) / 1440;
                        final String name = MapleItemInformationProvider.getInstance().getName(id);
                        if (id / 10000 == 114 && name != null && name.length() > 0) {
                            final String msg = "You have attained title <" + name + ">";
                            c.dropMessage(5, msg);
                            c.dropMessage(5, msg);
                        }
                        MapleInventoryManipulator.addById(c.getClient(), id, count, "", null, period, (byte)0);
                        c.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, count, true));
                    }
                }
                break;
            }
            case nextQuest: {
                c.getClient().getSession().write(MaplePacketCreator.updateQuestFinish(this.quest.getId(), c.getQuest(this.quest).getNpc(), MapleDataTool.getInt(this.data)));
                break;
            }
            case money: {
                c.gainMeso(MapleDataTool.getInt(this.data, 0), true, false, true);
                break;
            }
            case quest: {
                for (final MapleData qEntry : this.data) {
                    c.updateQuest(new MapleQuestStatus(MapleQuest.getInstance(MapleDataTool.getInt(qEntry.getChildByPath("id"))), (byte)MapleDataTool.getInt(qEntry.getChildByPath("state"), 0)));
                }
                break;
            }
            case skill: {
                for (final MapleData sEntry : this.data) {
                    final int skillid = MapleDataTool.getInt(sEntry.getChildByPath("id"));
                    final int skillLevel = MapleDataTool.getInt(sEntry.getChildByPath("skillLevel"), 0);
                    final int masterLevel = MapleDataTool.getInt(sEntry.getChildByPath("masterLevel"), 0);
                    final ISkill skillObject = SkillFactory.getSkill(skillid);
                    for (final MapleData applicableJob : sEntry.getChildByPath("job")) {
                        if (skillObject.isBeginnerSkill() || c.getJob() == MapleDataTool.getInt(applicableJob)) {
                            c.changeSkillLevel(skillObject, (byte)Math.max(skillLevel, c.getSkillLevel(skillObject)), (byte)Math.max(masterLevel, c.getMasterLevel(skillObject)));
                            break;
                        }
                    }
                }
                break;
            }
            case pop: {
                final int fameGain = MapleDataTool.getInt(this.data, 0);
                c.addFame(fameGain);
                c.updateSingleStat(MapleStat.FAME, c.getFame());
                c.getClient().getSession().write(MaplePacketCreator.getShowFameGain(fameGain));
                break;
            }
            case buffItemID: {
                final int tobuff = MapleDataTool.getInt(this.data, -1);
                if (tobuff == -1) {
                    break;
                }
                MapleItemInformationProvider.getInstance().getItemEffect(tobuff).applyTo(c);
                break;
            }
            case sp: {
                for (final MapleData iEntry3 : this.data.getChildren()) {
                    final int sp_val = MapleDataTool.getInt(iEntry3.getChildByPath("sp_value"), 0);
                    if (iEntry3.getChildByPath("job") != null) {
                        int finalJob = 0;
                        for (final MapleData jEntry : iEntry3.getChildByPath("job").getChildren()) {
                            final int job_val = MapleDataTool.getInt(jEntry, 0);
                            if (c.getJob() >= job_val && job_val > finalJob) {
                                finalJob = job_val;
                            }
                        }
                        c.gainSP(sp_val, GameConstants.getSkillBook(finalJob));
                    }
                    else {
                        c.gainSP(sp_val);
                    }
                }
                break;
            }
        }
    }
    
    public MapleQuestActionType getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.type + ": " + this.data;
    }
}
