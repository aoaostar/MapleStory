package handling.channel.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import server.MapleDueyActions;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;

public class DueyHandler
{
    public static void DueyOperation(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final byte operation = slea.readByte();
        switch (operation) {
            case 1: {
                final String AS13Digit = slea.readMapleAsciiString();
                final int conv = c.getPlayer().getConversation();
                if (conv == 2) {
                    c.getSession().write(MaplePacketCreator.sendDuey((byte)10, loadItems(c.getPlayer())));
                    break;
                }
                break;
            }
            case 3: {
                if (c.getPlayer().getConversation() != 2) {
                    return;
                }
                final byte inventId = slea.readByte();
                final short itemPos = slea.readShort();
                final short amount = slea.readShort();
                final int mesos = slea.readInt();
                final String recipient = slea.readMapleAsciiString();
                final boolean quickdelivery = slea.readByte() > 0;
                final int finalcost = mesos + GameConstants.getTaxAmount(mesos) + (quickdelivery ? 0 : 5000);
                if (mesos >= 0 && mesos <= 100000000 && c.getPlayer().getMeso() >= finalcost) {
                    final int accid = MapleCharacterUtil.getIdByName(recipient);
                    if (accid != -1) {
                        if (accid != c.getAccID()) {
                            final boolean recipientOn = false;
                            if (inventId > 0) {
                                final MapleInventoryType inv = MapleInventoryType.getByType(inventId);
                                final IItem item = c.getPlayer().getInventory(inv).getItem((byte)itemPos);
                                if (item == null) {
                                    c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                                    return;
                                }
                                final byte flag = item.getFlag();
                                if (ItemFlag.UNTRADEABLE.check(flag) || ItemFlag.LOCK.check(flag)) {
                                    c.getSession().write(MaplePacketCreator.enableActions());
                                    return;
                                }
                                if (c.getPlayer().getItemQuantity(item.getItemId(), false) >= amount) {
                                    final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                                    if (!ii.isDropRestricted(item.getItemId()) && !ii.isAccountShared(item.getItemId())) {
                                        if (addItemToDB(item, amount, mesos, c.getPlayer().getName(), accid, recipientOn)) {
                                            if (GameConstants.is飞镖道具(item.getItemId()) || GameConstants.is子弹道具(item.getItemId())) {
                                                MapleInventoryManipulator.removeFromSlot(c, inv, (byte)itemPos, item.getQuantity(), true);
                                            }
                                            else {
                                                MapleInventoryManipulator.removeFromSlot(c, inv, (byte)itemPos, amount, true, false);
                                            }
                                            c.getPlayer().gainMeso(-finalcost, false);
                                            c.getSession().write(MaplePacketCreator.sendDuey((byte)19, null));
                                        }
                                        else {
                                            c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                                        }
                                    }
                                    else {
                                        c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                                    }
                                }
                                else {
                                    c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                                }
                            }
                            else if (addMesoToDB(mesos, c.getPlayer().getName(), accid, recipientOn)) {
                                c.getPlayer().gainMeso(-finalcost, false);
                                c.getSession().write(MaplePacketCreator.sendDuey((byte)19, null));
                            }
                            else {
                                c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                            }
                        }
                        else {
                            c.getSession().write(MaplePacketCreator.sendDuey((byte)15, null));
                        }
                    }
                    else {
                        c.getSession().write(MaplePacketCreator.sendDuey((byte)14, null));
                    }
                    break;
                }
                c.getSession().write(MaplePacketCreator.sendDuey((byte)12, null));
                break;
            }
            case 5: {
                if (c.getPlayer().getConversation() != 2) {
                    return;
                }
                final int packageid = slea.readInt();
                final MapleDueyActions dp = loadSingleItem(packageid, c.getPlayer().getId());
                if (dp == null) {
                    return;
                }
                if (dp.getItem() != null && !MapleInventoryManipulator.checkSpace(c, dp.getItem().getItemId(), dp.getItem().getQuantity(), dp.getItem().getOwner())) {
                    c.getSession().write(MaplePacketCreator.sendDuey((byte)16, null));
                    return;
                }
                if (dp.getMesos() < 0 || dp.getMesos() + c.getPlayer().getMeso() < 0) {
                    c.getSession().write(MaplePacketCreator.sendDuey((byte)17, null));
                    return;
                }
                removeItemFromDB(packageid, c.getPlayer().getId());
                if (dp.getItem() != null) {
                    MapleInventoryManipulator.addFromDrop(c, dp.getItem(), false);
                }
                if (dp.getMesos() != 0) {
                    c.getPlayer().gainMeso(dp.getMesos(), false);
                }
                c.getSession().write(MaplePacketCreator.removeItemFromDuey(false, packageid));
                break;
            }
            case 6: {
                if (c.getPlayer().getConversation() != 2) {
                    return;
                }
                final int packageid = slea.readInt();
                removeItemFromDB(packageid, c.getPlayer().getId());
                c.getSession().write(MaplePacketCreator.removeItemFromDuey(true, packageid));
                break;
            }
            case 8: {
                c.getPlayer().setConversation(0);
                break;
            }
            default: {
                System.out.println("Unhandled Duey operation : " + slea.toString());
                break;
            }
        }
    }
    
    private static boolean addMesoToDB(final int mesos, final String sName, final int recipientID, final boolean isOn) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("INSERT INTO dueypackages (RecieverId, SenderName, Mesos, TimeStamp, Checked, Type) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, recipientID);
            ps.setString(2, sName);
            ps.setInt(3, mesos);
            ps.setLong(4, System.currentTimeMillis());
            ps.setInt(5, isOn ? 0 : 1);
            ps.setInt(6, 3);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }
    
    private static boolean addItemToDB(final IItem item, final int quantity, final int mesos, final String sName, final int recipientID, final boolean isOn) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("INSERT INTO dueypackages (RecieverId, SenderName, Mesos, TimeStamp, Checked, Type) VALUES (?, ?, ?, ?, ?, ?)", 1);
            ps.setInt(1, recipientID);
            ps.setString(2, sName);
            ps.setInt(3, mesos);
            ps.setLong(4, System.currentTimeMillis());
            ps.setInt(5, isOn ? 0 : 1);
            ps.setInt(6, item.getType());
            ps.executeUpdate();
            final ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ItemLoader.DUEY.saveItems(Collections.singletonList(new Pair<IItem, MapleInventoryType>(item, GameConstants.getInventoryType(item.getItemId()))), rs.getInt(1));
            }
            rs.close();
            ps.close();
            return true;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }
    
    public static List<MapleDueyActions> loadItems(final MapleCharacter chr) {
        final List<MapleDueyActions> packages = new LinkedList<MapleDueyActions>();
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM dueypackages WHERE RecieverId = ?");
            ps.setInt(1, chr.getId());
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final MapleDueyActions dueypack = getItemByPID(rs.getInt("packageid"));
                dueypack.setSender(rs.getString("SenderName"));
                dueypack.setMesos(rs.getInt("Mesos"));
                dueypack.setSentTime(rs.getLong("TimeStamp"));
                packages.add(dueypack);
            }
            rs.close();
            ps.close();
            return packages;
        }
        catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }
    
    public static MapleDueyActions loadSingleItem(final int packageid, final int charid) {
        final List<MapleDueyActions> packages = new LinkedList<MapleDueyActions>();
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM dueypackages WHERE PackageId = ? and RecieverId = ?");
            ps.setInt(1, packageid);
            ps.setInt(2, charid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final MapleDueyActions dueypack = getItemByPID(packageid);
                dueypack.setSender(rs.getString("SenderName"));
                dueypack.setMesos(rs.getInt("Mesos"));
                dueypack.setSentTime(rs.getLong("TimeStamp"));
                packages.add(dueypack);
                rs.close();
                ps.close();
                return dueypack;
            }
            rs.close();
            ps.close();
            return null;
        }
        catch (SQLException se) {
            return null;
        }
    }
    
    public static void reciveMsg(final MapleClient c, final int recipientId) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("UPDATE dueypackages SET Checked = 0 where RecieverId = ?");
            ps.setInt(1, recipientId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    private static void removeItemFromDB(final int packageid, final int charid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("DELETE FROM dueypackages WHERE PackageId = ? and RecieverId = ?");
            ps.setInt(1, packageid);
            ps.setInt(2, charid);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    private static MapleDueyActions getItemByPID(final int packageid) {
        try {
            final Map<Integer, Pair<IItem, MapleInventoryType>> iter = ItemLoader.DUEY.loadItems(false, packageid);
            if (iter != null && iter.size() > 0) {
                final Iterator<Pair<IItem, MapleInventoryType>> iterator = iter.values().iterator();
                if (iterator.hasNext()) {
                    final Pair<IItem, MapleInventoryType> i = iterator.next();
                    return new MapleDueyActions(packageid, i.getLeft());
                }
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return new MapleDueyActions(packageid);
    }
}
