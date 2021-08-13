package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import handling.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MerchItemPackage;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.PlayerShopPacket;

public class HiredMerchantHandler
{
    public static final void UseHiredMerchant(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().getMap().allowPersonalShop()) {
            final byte state = checkExistance(c.getPlayer().getAccountID(), c.getPlayer().getId());
            switch (state) {
                case 1: {
                    c.getPlayer().dropMessage(1, "请先去领取你之前摆摊的东西");
                    break;
                }
                case 0: {
                    final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                    if (!merch) {
                        c.getSession().write(PlayerShopPacket.sendTitleBox());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "请换个地方开或者是你已经有开店了");
                    break;
                }
                default: {
                    c.getPlayer().dropMessage(1, "发生未知错误.");
                    break;
                }
            }
        }
        else {
            c.getSession().close();
        }
    }
    
    private static byte checkExistance(final int accid, final int charid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where accountid = ? OR characterid = ?");
            ps.setInt(1, accid);
            ps.setInt(2, charid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                rs.close();
                return 1;
            }
            rs.close();
            ps.close();
            return 0;
        }
        catch (SQLException se) {
            return -1;
        }
    }
    
    public static void MerchantItemStore(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        if (chr == null) {
            return;
        }
        final byte operation = slea.readByte();
        switch (operation) {
            case 20: {
                slea.readMapleAsciiString();
                final int conv = c.getPlayer().getConversation();
                final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                if (merch) {
                    c.getPlayer().dropMessage(1, "请关闭商店后再试一次.");
                    c.getPlayer().setConversation(0);
                    break;
                }
                if (conv == 3) {
                    final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID(), chr);
                    if (pack == null) {
                        c.getPlayer().dropMessage(1, "你没有物品可以领取!");
                        deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID());
                        c.getPlayer().setConversation(0);
                    }
                    else if (pack.getItems().size() <= 0) {
                        if (!check(c.getPlayer(), pack)) {
                            c.getSession().write(PlayerShopPacket.merchItem_Message((byte)33));
                            return;
                        }
                        if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack.getPackageid())) {
                            FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_雇佣金币领取记录.txt", " 领回金币 " + pack.getMesos());
                            c.getPlayer().gainMeso(pack.getMesos(), false);
                            c.getPlayer().setConversation(0);
                            c.getPlayer().dropMessage("领取金币" + pack.getMesos());
                        }
                        else {
                            c.getPlayer().dropMessage(1, "发生未知错误。");
                        }
                        c.getPlayer().setConversation(0);
                        c.getSession().write(MaplePacketCreator.enableActions());
                    }
                    else {
                        c.getSession().write(PlayerShopPacket.merchItemStore_ItemData(pack));
                    }
                    break;
                }
                break;
            }
            case 25: {
                if (c.getPlayer().getConversation() != 3) {
                    return;
                }
                c.getSession().write(PlayerShopPacket.merchItemStore((byte)36));
                break;
            }
            case 26: {
                if (c.getPlayer().getConversation() != 3) {
                    c.getPlayer().dropMessage(1, "发生未知错误1.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final MerchItemPackage pack2 = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID(), chr);
                if (pack2 == null) {
                    c.getPlayer().dropMessage(1, "发生未知错误。\r\n你没有物品可以领取！");
                    return;
                }
                if (!check(c.getPlayer(), pack2)) {
                    c.getPlayer().dropMessage(1, "因为背包空间不足，无法领取道具.");
                    c.getSession().write(PlayerShopPacket.merchItem_Message((byte)33));
                    return;
                }
                if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack2.getPackageid())) {
                    if (pack2.getMesos() > 0) {
                        c.getPlayer().gainMeso(pack2.getMesos(), false);
                        if (chr.isGM()) {
                            chr.dropMessage(6, "[雇佣] " + chr.getName() + " 雇佣取回获得金币: " + pack2.getMesos() + " 时间: " + FileoutputUtil.CurrentReadable_Date());
                        }
                        System.out.println("[雇佣] " + chr.getName() + " 雇佣取回获得金币: " + pack2.getMesos() + " 时间: " + FileoutputUtil.CurrentReadable_Date());
                        FileoutputUtil.hiredMerchLog(chr.getName(), "雇佣取回获得金币: " + pack2.getMesos());
                    }
                    for (final IItem item : pack2.getItems()) {
                        MapleInventoryManipulator.addFromDrop(c, item, false);
                        if (chr.isGM()) {
                            chr.dropMessage(6, "名称：" + chr.getName() + "雇佣取回 获得道具：" + item.getItemId() + " - " + MapleItemInformationProvider.getInstance().getName(item.getItemId()) + " 数量：" + item.getQuantity());
                        }
                        FileoutputUtil.hiredMerchLog(chr.getName(), "雇佣取回获得道具: " + item.getItemId() + " - " + MapleItemInformationProvider.getInstance().getName(item.getItemId()) + " 数量: " + item.getQuantity());
                    }
                    c.getSession().write(PlayerShopPacket.merchItem_Message((byte)29));
                    break;
                }
                c.getPlayer().dropMessage(1, "发生未知错误.");
                break;
            }
            case 27: {
                c.getPlayer().setConversation(0);
                break;
            }
            default: {
                System.out.println("弗兰德里：雇佣商店未知的操作类型 " + operation);
                break;
            }
        }
    }
    
    private static void getShopItem(final MapleClient c) {
        if (c.getPlayer().getConversation() != 3) {
            return;
        }
        final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID(), c.getPlayer());
        if (pack == null) {
            c.getPlayer().dropMessage(1, "发生未知错误。");
            return;
        }
        if (!check(c.getPlayer(), pack)) {
            c.getPlayer().dropMessage(1, "你背包格子不够。");
            return;
        }
        if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack.getPackageid())) {
            c.getPlayer().gainMeso(pack.getMesos(), false);
            for (final IItem item : pack.getItems()) {
                MapleInventoryManipulator.addFromDrop(c, item, false);
            }
            c.getPlayer().dropMessage(5, "领取成功。");
        }
        else {
            c.getPlayer().dropMessage(1, "发生未知错误。");
        }
    }
    
    private static boolean check(final MapleCharacter chr, final MerchItemPackage pack) {
        if (chr.getMeso() + pack.getMesos() < 0) {
            System.out.println("[雇佣] " + chr.getName() + " 雇佣取回道具金币检测错误 时间: " + FileoutputUtil.CurrentReadable_Date());
            FileoutputUtil.hiredMerchLog(chr.getName(), "雇佣取回道具金币检测错误");
            return false;
        }
        byte eq = 0;
        byte use = 0;
        byte setup = 0;
        byte etc = 0;
        byte cash = 0;
        for (final IItem item : pack.getItems()) {
            final MapleInventoryType invtype = GameConstants.getInventoryType(item.getItemId());
            if (null != invtype) {
                switch (invtype) {
                    case EQUIP: {
                        ++eq;
                        break;
                    }
                    case USE: {
                        ++use;
                        break;
                    }
                    case SETUP: {
                        ++setup;
                        break;
                    }
                    case ETC: {
                        ++etc;
                        break;
                    }
                    case CASH: {
                        ++cash;
                        break;
                    }
                }
            }
            if (MapleItemInformationProvider.getInstance().isPickupRestricted(item.getItemId()) && chr.haveItem(item.getItemId(), 1)) {
                System.out.println("[雇佣] " + chr.getName() + " 雇佣取回道具是否可以捡取错误 时间: " + FileoutputUtil.CurrentReadable_Date());
                FileoutputUtil.hiredMerchLog(chr.getName(), "雇佣取回道具是否可以捡取错误");
                return false;
            }
        }
        if (chr.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < eq || chr.getInventory(MapleInventoryType.USE).getNumFreeSlot() < use || chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < setup || chr.getInventory(MapleInventoryType.ETC).getNumFreeSlot() < etc || chr.getInventory(MapleInventoryType.CASH).getNumFreeSlot() < cash) {
            System.out.println("[雇佣] " + chr.getName() + " 雇佣取回道具背包空间不够 时间: " + FileoutputUtil.CurrentReadable_Date());
            FileoutputUtil.hiredMerchLog(chr.getName(), "雇佣取回道具背包空间不够");
            return false;
        }
        return true;
    }
    
    private static boolean deletePackage(final int charid, final int accid, final int packageid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("DELETE from hiredmerch where characterid = ? OR accountid = ? OR packageid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accid);
            ps.setInt(3, packageid);
            ps.execute();
            ps.close();
            ItemLoader.HIRED_MERCHANT.saveItems(null, packageid, accid, charid);
            return true;
        }
        catch (SQLException e) {
            System.out.println("删除弗洛兰德道具信息出错" + e);
            return false;
        }
    }
    
    private static boolean deletePackage(final int charid, final int accid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("DELETE from hiredmerch where characterid = ? OR accountid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accid);
            ps.execute();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("删除弗洛兰德道具信息出错" + e);
            return false;
        }
    }
    
    private static MerchItemPackage loadItemFrom_Database(final int charid, final int accountid, final MapleCharacter chr) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where characterid = ? OR accountid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accountid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                return null;
            }
            final int packageid = rs.getInt("PackageId");
            final MerchItemPackage pack = new MerchItemPackage();
            pack.setPackageid(packageid);
            pack.setSentTime(rs.getLong("time"));
            ps.close();
            rs.close();
            final Map<Integer, Pair<IItem, MapleInventoryType>> items = ItemLoader.HIRED_MERCHANT.loadItems_hm(packageid, accountid);
            final int mesos = chr.getMerchantMeso();
            if (mesos == 0 && items.isEmpty()) {
                FileoutputUtil.hiredMerchLog(chr.getName(), "加载弗洛兰德道具信息 金币 " + mesos + " 是否有道具 " + items.size());
                return null;
            }
            pack.setMesos(mesos);
            if (!items.isEmpty()) {
                final List<IItem> iters = new ArrayList<IItem>();
                for (final Pair<IItem, MapleInventoryType> z : items.values()) {
                    iters.add(z.left);
                }
                pack.setItems(iters);
            }
            FileoutputUtil.hiredMerchLog(chr.getName(), "弗洛兰德取回最后返回 金币: " + mesos + " 道具数量: " + items.size());
            return pack;
        }
        catch (SQLException e) {
            System.out.println("加载弗洛兰德道具信息出错" + e);
            return null;
        }
    }
}
