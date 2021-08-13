package tools.wztosql;

import database.DatabaseConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.quest.MapleQuestActionType;
import server.quest.MapleQuestRequirementType;
import tools.Pair;

public class DumpQuests
{
    private final MapleDataProvider quest;
    protected boolean hadError;
    protected boolean update;
    protected int id;
    private final Connection con;
    
    public DumpQuests(final boolean update) throws Exception {
        this.hadError = false;
        this.update = false;
        this.id = 0;
        this.con = DatabaseConnection.getConnection();
        this.update = update;
        this.quest = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Quest.wz"));
        if (this.quest == null) {
            this.hadError = true;
        }
    }
    
    public boolean isHadError() {
        return this.hadError;
    }
    
    public void dumpQuests() throws Exception {
        if (!this.hadError) {
            final PreparedStatement psai = this.con.prepareStatement("INSERT INTO wz_questactitemdata(uniqueid, itemid, count, period, gender, job, jobEx, prop) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            final PreparedStatement psas = this.con.prepareStatement("INSERT INTO wz_questactskilldata(uniqueid, skillid, skillLevel, masterLevel) VALUES (?, ?, ?, ?)");
            final PreparedStatement psaq = this.con.prepareStatement("INSERT INTO wz_questactquestdata(uniqueid, quest, state) VALUES (?, ?, ?)");
            final PreparedStatement ps = this.con.prepareStatement("INSERT INTO wz_questdata(questid, name, autoStart, autoPreComplete, viewMedalItem, selectedSkillID, blocked, autoAccept, autoComplete) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            final PreparedStatement psr = this.con.prepareStatement("INSERT INTO wz_questreqdata(questid, type, name, stringStore, intStoresFirst, intStoresSecond) VALUES (?, ?, ?, ?, ?, ?)");
            final PreparedStatement psq = this.con.prepareStatement("INSERT INTO wz_questpartydata(questid, rank, mode, property, value) VALUES(?,?,?,?,?)");
            final PreparedStatement psa = this.con.prepareStatement("INSERT INTO wz_questactdata(questid, type, name, intStore, applicableJobs, uniqueid) VALUES (?, ?, ?, ?, ?, ?)");
            try {
                this.dumpQuests(psai, psas, psaq, ps, psr, psq, psa);
            }
            catch (Exception e) {
                System.out.println(this.id + " quest.");
                e.printStackTrace();
                this.hadError = true;
            }
            finally {
                psai.executeBatch();
                psai.close();
                psas.executeBatch();
                psas.close();
                psaq.executeBatch();
                psaq.close();
                psa.executeBatch();
                psa.close();
                psr.executeBatch();
                psr.close();
                psq.executeBatch();
                psq.close();
                ps.executeBatch();
                ps.close();
            }
        }
    }
    
    public void delete(final String sql) throws Exception {
        final PreparedStatement ps = this.con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }
    
    public boolean doesExist(final String sql) throws Exception {
        final PreparedStatement ps = this.con.prepareStatement(sql);
        final ResultSet rs = ps.executeQuery();
        final boolean ret = rs.next();
        rs.close();
        ps.close();
        return ret;
    }
    
    public void dumpQuests(final PreparedStatement psai, final PreparedStatement psas, final PreparedStatement psaq, final PreparedStatement ps, final PreparedStatement psr, final PreparedStatement psq, final PreparedStatement psa) throws Exception {
        if (!this.update) {
            this.delete("DELETE FROM wz_questdata");
            this.delete("DELETE FROM wz_questactdata");
            this.delete("DELETE FROM wz_questactitemdata");
            this.delete("DELETE FROM wz_questactskilldata");
            this.delete("DELETE FROM wz_questactquestdata");
            this.delete("DELETE FROM wz_questreqdata");
            this.delete("DELETE FROM wz_questpartydata");
            System.out.println("Deleted wz_questdata successfully.");
        }
        final MapleData checkz = this.quest.getData("Check.img");
        final MapleData actz = this.quest.getData("Act.img");
        final MapleData infoz = this.quest.getData("QuestInfo.img");
        final MapleData pinfoz = this.quest.getData("PQuest.img");
        System.out.println("Adding into wz_questdata.....");
        int uniqueid = 0;
        for (final MapleData qz : checkz.getChildren()) {
            this.id = Integer.parseInt(qz.getName());
            if (this.update && this.doesExist("SELECT * FROM wz_questdata WHERE questid = " + this.id)) {
                continue;
            }
            ps.setInt(1, this.id);
            for (int i = 0; i < 2; ++i) {
                final MapleData reqData = qz.getChildByPath(String.valueOf(i));
                if (reqData != null) {
                    psr.setInt(1, this.id);
                    psr.setInt(2, i);
                    for (final MapleData req : reqData.getChildren()) {
                        if (MapleQuestRequirementType.getByWZName(req.getName()) == MapleQuestRequirementType.UNDEFINED) {
                            continue;
                        }
                        psr.setString(3, req.getName());
                        if (req.getName().equals("fieldEnter")) {
                            psr.setString(4, String.valueOf(MapleDataTool.getIntConvert("0", req, 0)));
                        }
                        else if (req.getName().equals("end") || req.getName().equals("startscript") || req.getName().equals("endscript")) {
                            psr.setString(4, MapleDataTool.getString(req, ""));
                        }
                        else {
                            psr.setString(4, String.valueOf(MapleDataTool.getInt(req, 0)));
                        }
                        final StringBuilder intStore1 = new StringBuilder();
                        final StringBuilder intStore2 = new StringBuilder();
                        final List<Pair<Integer, Integer>> dataStore = new LinkedList<Pair<Integer, Integer>>();
                        if (req.getName().equals("job")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                dataStore.add(new Pair<Integer, Integer>(i, MapleDataTool.getInt(child.get(x), -1)));
                            }
                        }
                        else if (req.getName().equals("skill")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                final MapleData childdata = child.get(x);
                                if (childdata != null) {
                                    dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("acquire"), 0)));
                                }
                            }
                        }
                        else if (req.getName().equals("quest")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                final MapleData childdata = child.get(x);
                                if (childdata != null) {
                                    dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("state"), 0)));
                                }
                            }
                        }
                        else if (req.getName().equals("item") || req.getName().equals("mob")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                final MapleData childdata = child.get(x);
                                if (childdata != null) {
                                    dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("count"), 0)));
                                }
                            }
                        }
                        else if (req.getName().equals("mbcard")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                final MapleData childdata = child.get(x);
                                if (childdata != null) {
                                    dataStore.add(new Pair<Integer, Integer>(MapleDataTool.getInt(childdata.getChildByPath("id"), 0), MapleDataTool.getInt(childdata.getChildByPath("min"), 0)));
                                }
                            }
                        }
                        else if (req.getName().equals("pet")) {
                            final List<MapleData> child = req.getChildren();
                            for (int x = 0; x < child.size(); ++x) {
                                final MapleData childdata = child.get(x);
                                if (childdata != null) {
                                    dataStore.add(new Pair<Integer, Integer>(i, MapleDataTool.getInt(childdata.getChildByPath("id"), 0)));
                                }
                            }
                        }
                        for (final Pair<Integer, Integer> data : dataStore) {
                            if (intStore1.length() > 0) {
                                intStore1.append(", ");
                                intStore2.append(", ");
                            }
                            intStore1.append(data.getLeft());
                            intStore2.append(data.getRight());
                        }
                        psr.setString(5, intStore1.toString());
                        psr.setString(6, intStore2.toString());
                        psr.addBatch();
                    }
                }
                final MapleData actData = actz.getChildByPath(this.id + "/" + i);
                if (actData != null) {
                    psa.setInt(1, this.id);
                    psa.setInt(2, i);
                    for (final MapleData act : actData.getChildren()) {
                        if (MapleQuestActionType.getByWZName(act.getName()) == MapleQuestActionType.UNDEFINED) {
                            continue;
                        }
                        psa.setString(3, act.getName());
                        if (act.getName().equals("sp")) {
                            psa.setInt(4, MapleDataTool.getIntConvert("0/sp_value", act, 0));
                        }
                        else {
                            psa.setInt(4, MapleDataTool.getInt(act, 0));
                        }
                        final StringBuilder applicableJobs = new StringBuilder();
                        if (act.getName().equals("sp") || act.getName().equals("skill")) {
                            for (int index = 0; act.getChildByPath(index + "/job") != null; ++index) {
                                for (final MapleData d : act.getChildByPath(index + "/job")) {
                                    if (applicableJobs.length() > 0) {
                                        applicableJobs.append(", ");
                                    }
                                    applicableJobs.append(MapleDataTool.getInt(d, 0));
                                }
                            }
                        }
                        else if (act.getChildByPath("job") != null) {
                            for (final MapleData d2 : act.getChildByPath("job")) {
                                if (applicableJobs.length() > 0) {
                                    applicableJobs.append(", ");
                                }
                                applicableJobs.append(MapleDataTool.getInt(d2, 0));
                            }
                        }
                        psa.setString(5, applicableJobs.toString());
                        psa.setInt(6, -1);
                        if (act.getName().equals("item")) {
                            ++uniqueid;
                            psa.setInt(6, uniqueid);
                            psai.setInt(1, uniqueid);
                            for (final MapleData iEntry : act.getChildren()) {
                                psai.setInt(2, MapleDataTool.getInt("id", iEntry, 0));
                                psai.setInt(3, MapleDataTool.getInt("count", iEntry, 0));
                                psai.setInt(4, MapleDataTool.getInt("period", iEntry, 0));
                                psai.setInt(5, MapleDataTool.getInt("gender", iEntry, 2));
                                psai.setInt(6, MapleDataTool.getInt("job", iEntry, -1));
                                psai.setInt(7, MapleDataTool.getInt("jobEx", iEntry, -1));
                                if (iEntry.getChildByPath("prop") == null) {
                                    psai.setInt(8, -2);
                                }
                                else {
                                    psai.setInt(8, MapleDataTool.getInt("prop", iEntry, -1));
                                }
                                psai.addBatch();
                            }
                        }
                        else if (act.getName().equals("skill")) {
                            ++uniqueid;
                            psa.setInt(6, uniqueid);
                            psas.setInt(1, uniqueid);
                            for (final MapleData sEntry : act) {
                                psas.setInt(2, MapleDataTool.getInt("id", sEntry, 0));
                                psas.setInt(3, MapleDataTool.getInt("skillLevel", sEntry, 0));
                                psas.setInt(4, MapleDataTool.getInt("masterLevel", sEntry, 0));
                                psas.addBatch();
                            }
                        }
                        else if (act.getName().equals("quest")) {
                            ++uniqueid;
                            psa.setInt(6, uniqueid);
                            psaq.setInt(1, uniqueid);
                            for (final MapleData sEntry : act) {
                                psaq.setInt(2, MapleDataTool.getInt("id", sEntry, 0));
                                psaq.setInt(3, MapleDataTool.getInt("state", sEntry, 0));
                                psaq.addBatch();
                            }
                        }
                        psa.addBatch();
                    }
                }
            }
            final MapleData infoData = infoz.getChildByPath(String.valueOf(this.id));
            if (infoData != null) {
                ps.setString(2, MapleDataTool.getString("name", infoData, ""));
                ps.setInt(3, (MapleDataTool.getInt("autoStart", infoData, 0) > 0) ? 1 : 0);
                ps.setInt(4, (MapleDataTool.getInt("autoPreComplete", infoData, 0) > 0) ? 1 : 0);
                ps.setInt(5, MapleDataTool.getInt("viewMedalItem", infoData, 0));
                ps.setInt(6, MapleDataTool.getInt("selectedSkillID", infoData, 0));
                ps.setInt(7, MapleDataTool.getInt("blocked", infoData, 0));
                ps.setInt(8, MapleDataTool.getInt("autoAccept", infoData, 0));
                ps.setInt(9, MapleDataTool.getInt("autoComplete", infoData, 0));
            }
            else {
                ps.setString(2, "");
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.setInt(8, 0);
                ps.setInt(9, 0);
            }
            ps.addBatch();
            final MapleData pinfoData = pinfoz.getChildByPath(String.valueOf(this.id));
            if (pinfoData != null && pinfoData.getChildByPath("rank") != null) {
                psq.setInt(1, this.id);
                for (final MapleData d3 : pinfoData.getChildByPath("rank")) {
                    psq.setString(2, d3.getName());
                    for (final MapleData c : d3) {
                        psq.setString(3, c.getName());
                        for (final MapleData b : c) {
                            psq.setString(4, b.getName());
                            psq.setInt(5, MapleDataTool.getInt(b, 0));
                            psq.addBatch();
                        }
                    }
                }
            }
            System.out.println("Added quest: " + this.id);
        }
        System.out.println("Done wz_questdata...");
    }
    
    public int currentId() {
        return this.id;
    }
    
    public static void main(final String[] args) {
        boolean hadError = false;
        boolean update = false;
        final long startTime = System.currentTimeMillis();
        for (final String file : args) {
            if (file.equalsIgnoreCase("-update")) {
                update = true;
            }
        }
        int currentQuest = 0;
        try {
            final DumpQuests dq = new DumpQuests(update);
            System.out.println("Dumping quests");
            dq.dumpQuests();
            hadError |= dq.isHadError();
            currentQuest = dq.currentId();
        }
        catch (Exception e) {
            hadError = true;
            e.printStackTrace();
            System.out.println(currentQuest + " quest.");
        }
        final long endTime = System.currentTimeMillis();
        final double elapsedSeconds = (endTime - startTime) / 1000.0;
        final int elapsedSecs = (int)elapsedSeconds % 60;
        final int elapsedMinutes = (int)(elapsedSeconds / 60.0);
        String withErrors = "";
        if (hadError) {
            withErrors = " with errors";
        }
        System.out.println("Finished" + withErrors + " in " + elapsedMinutes + " minutes " + elapsedSecs + " seconds");
    }
}
