package server.life;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import handling.world.World;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.maps.MapleMap;
import tools.MaplePacketCreator;

public class PlayerNPC extends MapleNPC
{
    private Map<Byte, Integer> equips;
    private int mapid;
    private int face;
    private int hair;
    private int charId;
    private byte skin;
    private byte gender;
    private int[] pets;
    
    public static void loadAll() {
        final List<PlayerNPC> toAdd = new ArrayList<PlayerNPC>();
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM playernpcs");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                toAdd.add(new PlayerNPC(rs));
            }
            rs.close();
            ps.close();
        }
        catch (Exception se) {
            se.printStackTrace();
        }
        for (final PlayerNPC npc : toAdd) {
            npc.addToServer();
        }
    }
    
    public static void updateByCharId(final MapleCharacter chr) {
        if (World.Find.findChannel(chr.getId()) > 0) {
            for (final PlayerNPC npc : ChannelServer.getInstance(World.Find.findChannel(chr.getId())).getAllPlayerNPC()) {
                npc.update(chr);
            }
        }
    }
    
    public PlayerNPC(final ResultSet rs) throws Exception {
        super(rs.getInt("ScriptId"), rs.getString("name"));
        this.equips = new HashMap<Byte, Integer>();
        this.pets = new int[3];
        this.hair = rs.getInt("hair");
        this.face = rs.getInt("face");
        this.mapid = rs.getInt("map");
        this.skin = rs.getByte("skin");
        this.charId = rs.getInt("charid");
        this.gender = rs.getByte("gender");
        this.setCoords(rs.getInt("x"), rs.getInt("y"), rs.getInt("dir"), rs.getInt("Foothold"));
        final String[] pet = rs.getString("pets").split(",");
        for (int i = 0; i < 3; ++i) {
            if (pet[i] != null) {
                this.pets[i] = Integer.parseInt(pet[i]);
            }
            else {
                this.pets[i] = 0;
            }
        }
        final Connection con = DatabaseConnection.getConnection();
        final PreparedStatement ps = con.prepareStatement("SELECT * FROM playernpcs_equip WHERE NpcId = ?");
        ps.setInt(1, this.getId());
        final ResultSet rs2 = ps.executeQuery();
        while (rs2.next()) {
            this.equips.put(rs2.getByte("equippos"), rs2.getInt("equipid"));
        }
        rs2.close();
        ps.close();
    }
    
    public PlayerNPC(final MapleCharacter cid, final int npc, final MapleMap map, final MapleCharacter base) {
        super(npc, cid.getName());
        this.equips = new HashMap<Byte, Integer>();
        this.pets = new int[3];
        this.charId = cid.getId();
        this.mapid = map.getId();
        this.setCoords(base.getPosition().x, base.getPosition().y, 0, base.getFH());
        this.update(cid);
    }
    
    public void setCoords(final int x, final int y, final int f, final int fh) {
        this.setPosition(new Point(x, y));
        this.setCy(y);
        this.setRx0(x - 50);
        this.setRx1(x + 50);
        this.setF(f);
        this.setFh(fh);
    }
    
    public void addToServer() {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.addPlayerNPC(this);
        }
    }
    
    public void removeFromServer() {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.removePlayerNPC(this);
        }
    }
    
    public void update(final MapleCharacter chr) {
        if (chr == null || this.charId != chr.getId()) {
            return;
        }
        this.setName(chr.getName());
        this.setHair(chr.getHair());
        this.setFace(chr.getFace());
        this.setSkin(chr.getSkinColor());
        this.setGender(chr.getGender());
        this.setPets(chr.getPets());
        this.equips = new HashMap<Byte, Integer>();
        for (final IItem item : chr.getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (item.getPosition() < -128) {
                continue;
            }
            this.equips.put((byte)item.getPosition(), item.getItemId());
        }
        this.saveToDB();
    }
    
    public void destroy() {
        this.destroy(false);
    }
    
    public void destroy(final boolean remove) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM playernpcs WHERE scriptid = ?");
            ps.setInt(1, this.getId());
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM playernpcs_equip WHERE npcid = ?");
            ps.setInt(1, this.getId());
            ps.executeUpdate();
            ps.close();
            if (remove) {
                this.removeFromServer();
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    public void saveToDB() {
        final Connection con = DatabaseConnection.getConnection();
        try {
            if (this.getNPCFromWZ() == null) {
                this.destroy(true);
                return;
            }
            this.destroy();
            PreparedStatement ps = con.prepareStatement("INSERT INTO playernpcs(name, hair, face, skin, x, y, map, charid, scriptid, foothold, dir, gender, pets) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, this.getName());
            ps.setInt(2, this.getHair());
            ps.setInt(3, this.getFace());
            ps.setInt(4, this.getSkin());
            ps.setInt(5, this.getPosition().x);
            ps.setInt(6, this.getPosition().y);
            ps.setInt(7, this.getMapId());
            ps.setInt(8, this.getCharId());
            ps.setInt(9, this.getId());
            ps.setInt(10, this.getFh());
            ps.setInt(11, this.getF());
            ps.setInt(12, this.getGender());
            final String[] pet = { "0", "0", "0" };
            for (int i = 0; i < 3; ++i) {
                if (this.pets[i] > 0) {
                    pet[i] = String.valueOf(this.pets[i]);
                }
            }
            ps.setString(13, pet[0] + "," + pet[1] + "," + pet[2]);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("INSERT INTO playernpcs_equip(npcid, charid, equipid, equippos) VALUES (?, ?, ?, ?)");
            ps.setInt(1, this.getId());
            ps.setInt(2, this.getCharId());
            for (final Map.Entry<Byte, Integer> equip : this.equips.entrySet()) {
                ps.setInt(3, equip.getValue());
                ps.setInt(4, equip.getKey());
                ps.executeUpdate();
            }
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    public Map<Byte, Integer> getEquips() {
        return this.equips;
    }
    
    public byte getSkin() {
        return this.skin;
    }
    
    public int getGender() {
        return this.gender;
    }
    
    public int getFace() {
        return this.face;
    }
    
    public int getHair() {
        return this.hair;
    }
    
    public int getCharId() {
        return this.charId;
    }
    
    public int getMapId() {
        return this.mapid;
    }
    
    public void setSkin(final byte s) {
        this.skin = s;
    }
    
    public void setFace(final int f) {
        this.face = f;
    }
    
    public void setHair(final int h) {
        this.hair = h;
    }
    
    public void setGender(final int g) {
        this.gender = (byte)g;
    }
    
    public int getPet(final int i) {
        return (this.pets[i] > 0) ? this.pets[i] : 0;
    }
    
    public void setPets(final List<MaplePet> p) {
        for (int i = 0; i < 3; ++i) {
            if (p != null && p.size() > i && p.get(i) != null) {
                this.pets[i] = p.get(i).getPetItemId();
            }
            else {
                this.pets[i] = 0;
            }
        }
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        client.getSession().write(MaplePacketCreator.spawnNPC(this, true));
        client.getSession().write(MaplePacketCreator.spawnPlayerNPC(this));
        client.getSession().write(MaplePacketCreator.spawnNPCRequestController(this, true));
    }
    
    public MapleNPC getNPCFromWZ() {
        final MapleNPC npc = MapleLifeFactory.getNPC(this.getId());
        if (npc != null) {
            npc.setName(this.getName());
        }
        return npc;
    }
}
