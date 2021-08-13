package tools.wztosql;

import database.DatabaseConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.MapleItemInformationProvider;
import server.quest.MapleQuest;
import server.quest.MapleQuestRequirementType;
import tools.Pair;

public class QuestDropCreator
{
    protected static String monsterQueryData;
    protected static List<Pair<Integer, String>> itemNameCache;
    protected static Map<Integer, Boolean> bossCache;
    private static MapleDataProvider questData;
    private static MapleData requirements;
    private static MapleData info;
    public static List<MapleQuest> quests;
    public static List<Integer> itemIDs;
    private static Connection con;
    
    public static int getItemAmountNeeded(final short questid, final int itemid) {
        MapleData data = null;
        try {
            data = QuestDropCreator.requirements.getChildByPath(String.valueOf(questid)).getChildByPath("1");
        }
        catch (NullPointerException ex) {
            return 0;
        }
        if (data != null) {
            for (final MapleData req : data.getChildren()) {
                final MapleQuestRequirementType type = MapleQuestRequirementType.getByWZName(req.getName());
                if (!type.equals(MapleQuestRequirementType.item)) {
                    continue;
                }
                for (final MapleData d : req.getChildren()) {
                    if (MapleDataTool.getInt(d.getChildByPath("id"), 0) == itemid) {
                        return MapleDataTool.getInt(d.getChildByPath("count"), 0);
                    }
                }
            }
        }
        return 0;
    }
    
    public static boolean isQuestRequirement(final int itemid) {
        for (final MapleQuest quest : QuestDropCreator.quests) {
            if (getItemAmountNeeded((short)quest.getId(), itemid) > 0) {
                return true;
            }
        }
        return false;
    }
    
    public static short getQuestID(final int itemid) {
        for (final MapleQuest quest : QuestDropCreator.quests) {
            if (getItemAmountNeeded((short)quest.getId(), itemid) > 0) {
                return (short)quest.getId();
            }
        }
        return 0;
    }
    
    public static void initializeMySQL() {
        DatabaseConnection.getConnection();
        QuestDropCreator.con = DatabaseConnection.getConnection();
    }
    
    public static void loadQuests() {
        QuestDropCreator.questData = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Quest.wz"));
        QuestDropCreator.requirements = QuestDropCreator.questData.getData("Check.img");
        QuestDropCreator.info = QuestDropCreator.questData.getData("QuestInfo.img");
        for (final MapleData quest : QuestDropCreator.info.getChildren()) {
            QuestDropCreator.quests.add(MapleQuest.getInstance(Integer.parseInt(quest.getName())));
        }
    }
    
    public static void loadQuestItems() {
        final List<Pair<Integer, String>> items = MapleItemInformationProvider.getInstance().getAllItems();
        for (final Pair<Integer, String> item : items) {
            final int itemid = item.getLeft();
            if (!QuestDropCreator.itemIDs.contains(itemid) && isQuestRequirement(itemid)) {
                QuestDropCreator.itemIDs.add(itemid);
            }
        }
    }
    
    public static void main(final String[] args) throws Exception {
        System.out.println("任务物品爆率更新");
        System.out.println("...");
        System.console().readLine();
        final long timeStart = System.currentTimeMillis();
        System.out.println("加载开始.\r\n");
        System.out.println("加载任务信息。。。");
        loadQuests();
        System.out.println("加载任务道具信息...");
        loadQuestItems();
        System.out.println("初始化到 MySQL...");
        initializeMySQL();
        System.out.println("加载信息完成.");
        try {
            final PreparedStatement ps = QuestDropCreator.con.prepareStatement("UPDATE drop_data SET questid = ? WHERE itemid = ?");
            final PreparedStatement psr = QuestDropCreator.con.prepareStatement("UPDATE reactordrops SET questid = ? WHERE itemid = ?");
            for (final Integer itemid : QuestDropCreator.itemIDs) {
                if (MapleItemInformationProvider.getInstance().isQuestItem(itemid)) {
                    final int questId = getQuestID(itemid);
                    ps.setInt(1, questId);
                    ps.setInt(2, itemid);
                    psr.setInt(1, questId);
                    psr.setInt(2, itemid);
                    ps.executeUpdate();
                    psr.executeUpdate();
                    System.out.println("任务道具更新: " + itemid + " 任务ID: " + questId);
                }
            }
            ps.close();
            psr.close();
        }
        catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        final long timeEnd = System.currentTimeMillis() - timeStart;
        System.out.println("更新任务爆率数据完成 耗时 " + (int)(timeEnd / 1000L) + " 秒.");
    }
    
    static {
        QuestDropCreator.monsterQueryData = "drop_data";
        QuestDropCreator.itemNameCache = new ArrayList<Pair<Integer, String>>();
        QuestDropCreator.bossCache = new HashMap<Integer, Boolean>();
        QuestDropCreator.quests = new ArrayList<MapleQuest>();
        QuestDropCreator.itemIDs = new ArrayList<Integer>();
    }
}
