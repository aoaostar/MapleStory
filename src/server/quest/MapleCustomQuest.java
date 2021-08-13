package server.quest;

import database.DatabaseConnection;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MapleCustomQuest extends MapleQuest implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    
    public MapleCustomQuest(final int id) {
        super(id);
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM questrequirements WHERE questid = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final Blob blob = rs.getBlob("data");
                final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blob.getBytes(1L, (int)blob.length())));
                final MapleCustomQuestData data = (MapleCustomQuestData)ois.readObject();
                final MapleQuestRequirement req = new MapleQuestRequirement(this, MapleQuestRequirementType.getByWZName(data.getName()), data);
                final byte status = rs.getByte("status");
                if (status == 0) {
                    this.startReqs.add(req);
                }
                else {
                    if (status != 1) {
                        continue;
                    }
                    this.completeReqs.add(req);
                }
            }
            rs.close();
            ps.close();
            ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM questactions WHERE questid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                final Blob blob2 = rs.getBlob("data");
                final ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(blob2.getBytes(1L, (int)blob2.length())));
                final MapleCustomQuestData data = (MapleCustomQuestData)ois2.readObject();
                final MapleQuestAction act = new MapleQuestAction(MapleQuestActionType.getByWZName(data.getName()), data, this);
                final byte status2 = rs.getByte("status");
                if (status2 == 0) {
                    this.startActs.add(act);
                }
                else {
                    if (status2 != 1) {
                        continue;
                    }
                    this.completeActs.add(act);
                }
            }
            rs.close();
            ps.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading custom quest from SQL." + e);
        }
    }
}
