package handling.cashshop.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MapleRing;
import constants.GameConstants;
import constants.OtherSettings;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.CharacterTransfer;
import handling.world.World;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.CashItemFactory;
import server.CashItemInfo;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MTSCSPacket;

public class CashShopOperation
{
    public static void LeaveCS(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final String[] socket = c.getChannelServer().getIP().split(":");
        CashShopServer.getPlayerStorageMTS().deregisterPlayer(chr);
        CashShopServer.getPlayerStorage().deregisterPlayer(chr);
        final String ip = c.getSessionIPAddress();
        LoginServer.putLoginAuth(chr.getId(), ip.substring(ip.indexOf(47) + 1, ip.length()), c.getTempIP(), c.getChannel());
        c.updateLoginState(1, ip);
        try {
            chr.saveToDB(false, true);
            c.setReceiving(false);
            World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), c.getChannel());
            c.getSession().write(MaplePacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1])));
        }
        catch (NumberFormatException | UnknownHostException ex2) {
            throw new RuntimeException(ex2);
        }
    }
    
    public static void EnterCS(final int playerid, final MapleClient c) {
        CharacterTransfer transfer = CashShopServer.getPlayerStorage().getPendingCharacter(playerid);
        boolean mts = false;
        if (transfer == null) {
            transfer = CashShopServer.getPlayerStorageMTS().getPendingCharacter(playerid);
            mts = true;
            if (transfer == null) {
                c.getSession().close(true);
                return;
            }
        }
        final MapleCharacter chr = MapleCharacter.ReconstructChr(transfer, c, false);
        c.setPlayer(chr);
        c.setAccID(chr.getAccountID());
        final int state = c.getLoginState();
        boolean allowLogin = false;
        if ((state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL) && !World.isCharacterListConnected(c.loadCharacterNames(c.getWorld()))) {
            allowLogin = true;
        }
        if (!allowLogin) {
            c.setPlayer(null);
            c.getSession().close(true);
            return;
        }
        c.updateLoginState(MapleClient.LOGIN_LOGGEDIN, c.getSessionIPAddress());
        if (mts) {
            CashShopServer.getPlayerStorage().registerPlayer(chr);
            c.getSession().write(MTSCSPacket.warpCS(c));
            CSUpdate(c);
        }
        else {
            CashShopServer.getPlayerStorage().registerPlayer(chr);
            c.getSession().write(MTSCSPacket.warpCSS(c));
            CSUpdate(c);
        }
    }
    
    public static void CSUpdate(final MapleClient c) {
        c.sendPacket(MTSCSPacket.showCashInventory(c));
        c.getSession().write(MTSCSPacket.sendWishList(c.getPlayer(), false));
        c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
        c.getSession().write(MTSCSPacket.getCSGifts(c));
    }
    
    public static void TouchingCashShop(final MapleClient c) {
        c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
    }
    
    public static void CouponCode(final String code, final MapleClient c) {
        boolean validcode = false;
        int type = -1;
        int item = -1;
        try {
            validcode = MapleCharacterUtil.getNXCodeValid(code.toUpperCase(), validcode);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (validcode) {
            try {
                type = MapleCharacterUtil.getNXCodeType(code);
                item = MapleCharacterUtil.getNXCodeItem(code);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            if (type != 4) {
                try {
                    MapleCharacterUtil.setNXCodeUsed(c.getPlayer().getName(), code);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            final Map<Integer, IItem> itemz = new HashMap<Integer, IItem>();
            int maplePoints = 0;
            int mesos = 0;
            switch (type) {
                case 1:
                case 2: {
                    c.getPlayer().modifyCSPoints(type, item, false);
                    maplePoints = item;
                    break;
                }
                case 3: {
                    final CashItemInfo itez = CashItemFactory.getInstance().getItem(item);
                    if (itez == null) {
                        c.getSession().write(MTSCSPacket.sendCSFail(0));
                        doCSPackets(c);
                        return;
                    }
                    final byte slot = MapleInventoryManipulator.addId(c, itez.getId(), (short)1, "", (byte)0);
                    if (slot <= -1) {
                        c.getSession().write(MTSCSPacket.sendCSFail(0));
                        doCSPackets(c);
                        return;
                    }
                    itemz.put(item, c.getPlayer().getInventory(GameConstants.getInventoryType(item)).getItem(slot));
                    break;
                }
                case 4: {
                    c.getPlayer().modifyCSPoints(1, item, false);
                    maplePoints = item;
                    break;
                }
                case 5: {
                    c.getPlayer().gainMeso(item, false);
                    mesos = item;
                    break;
                }
            }
            c.getSession().write(MTSCSPacket.showCouponRedeemedItem(itemz, mesos, maplePoints, c));
        }
        else {
            c.getSession().write(MTSCSPacket.sendCSFail(validcode ? 165 : 167));
        }
        doCSPackets(c);
    }
    
    public static void BuyCashItem(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final OtherSettings item_id = new OtherSettings();
        final String[] itembp_id = item_id.getItempb_id();
        final String[] itemjy_id = item_id.getItemjy_id();
        final int action = slea.readByte();
        switch (action) {
            case 3: {
                final int useNX = slea.readByte() + 1;
                final int snCS = slea.readInt();
                final CashItemInfo item = CashItemFactory.getInstance().getItem(snCS);
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                if (item == null) {
                    chr.dropMessage(1, "该物品暂未开放！");
                    doCSPackets(c);
                    return;
                }
                for (int i = 0; i < itembp_id.length; ++i) {
                    if (item.getId() == Integer.parseInt(itembp_id[i])) {
                        c.getPlayer().dropMessage(1, "这个物品是禁止购买的.");
                        doCSPackets(c);
                        return;
                    }
                }
                if (item.getPrice() < 100) {
                    c.getPlayer().dropMessage(1, "价格(" + item.getPrice() + ")低于100点卷的物品是禁止购买的.");
                    doCSPackets(c);
                    return;
                }
                if (item != null && chr.getCSPoints(useNX) >= item.getPrice()) {
                    if (!ii.isCash(item.getId())) {
                        if (c.getPlayer().getInventory(GameConstants.getInventoryType(item.getId())).getNextFreeSlot() < 0) {
                            chr.dropMessage(1, "背包没空位了！");
                            doCSPackets(c);
                            return;
                        }
                        final byte pos = MapleInventoryManipulator.addId(c, item.getId(), (short)item.getCount(), null, item.getPeriod(), (byte)0);
                        if (pos < 0) {
                            chr.dropMessage(1, "背包坐标出错！\r\n可能是背包没空位了！");
                            doCSPackets(c);
                            return;
                        }
                        chr.modifyCSPoints(useNX, -item.getPrice(), false);
                        chr.dropMessage(1, "购买成功！\r\n物品自动放入了背包！");
                    }
                    else {
                        chr.modifyCSPoints(useNX, -item.getPrice(), false);
                        if (item.getPrice() < 100) {
                            c.getPlayer().dropMessage(1, "价格低于100点卷的物品是禁止购买的.");
                            doCSPackets(c);
                            return;
                        }
                        final IItem itemz = chr.getCashInventory().toItem(item);
                        if (itemz != null && itemz.getUniqueId() > 0 && itemz.getItemId() == item.getId() && itemz.getQuantity() == item.getCount()) {
                            if (useNX == 1) {
                                byte flag = itemz.getFlag();
                                boolean 交易 = true;
                                for (int j = 0; j < itemjy_id.length; ++j) {
                                    if (itemz.getItemId() == Integer.parseInt(itemjy_id[j])) {
                                        交易 = false;
                                    }
                                }
                                if (交易) {
                                    if (itemz.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                    }
                                    else {
                                        flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                    }
                                    itemz.setFlag(flag);
                                }
                            }
                            chr.getCashInventory().addToInventory(itemz);
                            c.getSession().write(MTSCSPacket.showBoughtCSItem(itemz, item.getSN(), c.getAccID()));
                        }
                        else {
                            c.getSession().write(MTSCSPacket.sendCSFail(0));
                        }
                    }
                }
                else {
                    c.getSession().write(MTSCSPacket.sendCSFail(0));
                }
                c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 4:
            case 32: {
                final int snCS2 = slea.readInt();
                final int type = slea.readByte() + 1;
                final String recipient = slea.readMapleAsciiString();
                final String message = slea.readMapleAsciiString();
                final CashItemInfo item2 = CashItemFactory.getInstance().getItem(snCS2);
                final IItem itemz2 = chr.getCashInventory().toItem(item2);
                if (c.getPlayer().isAdmin()) {
                    System.out.println("包裹购买 ID: " + snCS2);
                }
                if (item2.getPrice() < 100) {
                    c.getPlayer().dropMessage(1, "价格低于100点卷的物品是禁止购买的.");
                    doCSPackets(c);
                    return;
                }
                if (itemz2 == null || itemz2.getUniqueId() <= 0 || itemz2.getItemId() != item2.getId() || itemz2.getQuantity() != item2.getCount()) {
                    c.getPlayer().dropMessage(1, "这个物品是禁止购买的.");
                    doCSPackets(c);
                    break;
                }
                if (item2 == null || c.getPlayer().getCSPoints(type) < item2.getPrice() || message.length() > 73 || message.length() < 1) {
                    c.getSession().write(MTSCSPacket.sendCSFail(0));
                    doCSPackets(c);
                    return;
                }
                final Pair<Integer, Pair<Integer, Integer>> info = MapleCharacterUtil.getInfoByName(recipient, c.getPlayer().getWorld());
                if (info == null || info.getLeft() <= 0 || info.getLeft() == c.getPlayer().getId() || info.getRight().getLeft() == c.getAccID()) {
                    c.getSession().write(MTSCSPacket.sendCSFail(162));
                    doCSPackets(c);
                    return;
                }
                if (!item2.genderEquals(info.getRight().getRight())) {
                    c.getSession().write(MTSCSPacket.sendCSFail(163));
                    doCSPackets(c);
                    return;
                }
                c.getPlayer().getCashInventory().gift(info.getLeft(), c.getPlayer().getName(), message, item2.getSN(), MapleInventoryIdentifier.getInstance());
                c.getPlayer().modifyCSPoints(type, -item2.getPrice(), false);
                c.getSession().write(MTSCSPacket.sendGift(item2.getId(), item2.getCount(), recipient));
                break;
            }
            case 5: {
                chr.clearWishlist();
                if (slea.available() < 40L) {
                    c.getSession().write(MTSCSPacket.sendCSFail(0));
                    doCSPackets(c);
                    return;
                }
                final int[] wishlist = new int[10];
                for (int k = 0; k < 10; ++k) {
                    wishlist[k] = slea.readInt();
                }
                chr.setWishlist(wishlist);
                c.getSession().write(MTSCSPacket.sendWishList(chr, true));
                break;
            }
            case 6: {
                final int yue = slea.readByte() + 1;
                final boolean youhuijia = slea.readByte() > 0;
                if (youhuijia) {
                    final int snCS3 = slea.readInt();
                    byte types = 1;
                    switch (snCS3) {
                        case 50200018: {
                            types = 1;
                            break;
                        }
                        case 50200019: {
                            types = 2;
                            break;
                        }
                        case 50200020: {
                            types = 3;
                            break;
                        }
                        case 50200021: {
                            types = 4;
                            break;
                        }
                        case 50200043: {
                            types = 5;
                            break;
                        }
                    }
                    final MapleInventoryType type2 = MapleInventoryType.getByType(types);
                    if (chr.isAdmin()) {
                        System.out.println("增加道具栏  snCS " + snCS3 + " 扩充: " + types);
                    }
                    if (chr.getCSPoints(yue) >= 1100 && chr.getInventory(type2).getSlotLimit() < 96) {
                        chr.modifyCSPoints(yue, -1100, false);
                        chr.getInventory(type2).addSlot((byte)8);
                        chr.dropMessage(1, "扩充" + snCS3 + "成功，当前栏位: " + chr.getInventory(type2).getSlotLimit() + " 个。");
                        RefreshCashShop(c);
                        chr.getStorage().saveToDB();
                    }
                    else {
                        chr.dropMessage(1, "扩充" + snCS3 + "失败，点卷余额不足或者栏位已超过上限。");
                    }
                    break;
                }
                final MapleInventoryType type3 = MapleInventoryType.getByType(slea.readByte());
                if (chr.getCSPoints(yue) >= 600 && chr.getInventory(type3).getSlotLimit() < 96) {
                    chr.modifyCSPoints(yue, -600, false);
                    chr.getInventory(type3).addSlot((byte)4);
                    chr.dropMessage(1, "背包已增加到 " + chr.getInventory(type3).getSlotLimit() + " 个。");
                    RefreshCashShop(c);
                    chr.getStorage().saveToDB();
                }
                else {
                    chr.dropMessage(1, "扩充失败，点卷余额不足或者栏位已达到上限。");
                    c.getSession().write(MTSCSPacket.sendCSFail(164));
                }
                break;
            }
            case 7: {
                final int yue = slea.readByte() + 1;
                final int youhuijia2 = (slea.readByte() > 0) ? 2 : 1;
                if (chr.getCSPoints(yue) >= ((youhuijia2 == 2) ? 1100 : 600) && chr.getStorage().getSlots() < 97 - 4 * youhuijia2) {
                    chr.modifyCSPoints(yue, (youhuijia2 == 2) ? -1100 : -600, false);
                    chr.getStorage().increaseSlots((byte)(4 * youhuijia2));
                    chr.getStorage().saveToDB();
                    chr.dropMessage(1, "仓库扩充成功，当前栏位: " + chr.getStorage().getSlots() + " 个。");
                    RefreshCashShop(c);
                    break;
                }
                chr.dropMessage(1, "仓库扩充失败，点卷余额不足或者栏位已超过上限 96 个位置。");
                break;
            }
            case 8: {
                final int useNX2 = slea.readByte() + 1;
                final CashItemInfo item2 = CashItemFactory.getInstance().getItem(slea.readInt());
                final int slots = c.getCharacterSlots();
                if (slots >= LoginServer.getMaxCharacters()) {
                    chr.dropMessage(1, "角色列表已满无法增加！");
                }
                if (item2 == null || c.getPlayer().getCSPoints(useNX2) < item2.getPrice() || slots > LoginServer.getMaxCharacters()) {
                    c.getSession().write(MTSCSPacket.sendCSFail(0));
                    doCSPackets(c);
                    return;
                }
                c.getPlayer().modifyCSPoints(useNX2, -item2.getPrice(), false);
                if (c.gainCharacterSlot()) {
                    c.getSession().write(MTSCSPacket.increasedStorageSlots(slots + 1));
                    chr.dropMessage(1, "角色列表已增加到：" + c.getCharacterSlots() + "个");
                    break;
                }
                c.getSession().write(MTSCSPacket.sendCSFail(0));
                break;
            }
            case 13: {
                final int uniqueid = slea.readInt();
                slea.readInt();
                slea.readByte();
                final byte type4 = slea.readByte();
                final byte unknown = slea.readByte();
                final IItem item3 = c.getPlayer().getCashInventory().findByCashId(uniqueid);
                if (item3 != null && item3.getQuantity() > 0 && MapleInventoryManipulator.checkSpace(c, item3.getItemId(), item3.getQuantity(), item3.getOwner())) {
                    final IItem item_ = item3.copy();
                    final byte slot = (byte)MapleInventoryManipulator.addbyItem(c, item_, true);
                    if (slot >= 0) {
                        if (item_.getPet() != null) {
                            item_.getPet().setInventoryPosition(type4);
                            c.getPlayer().addPet(item_.getPet());
                        }
                        c.getPlayer().getCashInventory().removeFromInventory(item3);
                        c.getSession().write(MTSCSPacket.confirmFromCSInventory(item_, type4));
                    }
                    else {
                        c.getSession().write(MaplePacketCreator.serverNotice(1, "您的包裹已满."));
                    }
                    break;
                }
                c.getSession().write(MaplePacketCreator.serverNotice(1, "放入背包错误A." + item3));
                break;
            }
            case 14: {
                final int uniqueid = (int)slea.readLong();
                final MapleInventoryType type5 = MapleInventoryType.getByType(slea.readByte());
                final IItem item4 = c.getPlayer().getInventory(type5).findByUniqueId(uniqueid);
                if (item4 != null && item4.getQuantity() > 0 && item4.getUniqueId() > 0 && c.getPlayer().getCashInventory().getItemsSize() < 100) {
                    final IItem item_2 = item4.copy();
                    c.getPlayer().getInventory(type5).removeItem(item4.getPosition(), item4.getQuantity(), false);
                    final int sn = CashItemFactory.getInstance().getItemSN(item_2.getItemId());
                    if (item_2.getPet() != null) {
                        c.getPlayer().removePet(item_2.getPet());
                    }
                    item_2.setPosition((short)0);
                    item_2.setGMLog("购物商场购买: " + FileoutputUtil.CurrentReadable_Time());
                    c.getPlayer().getCashInventory().addToInventory(item_2);
                    c.sendPacket(MTSCSPacket.confirmToCSInventory(item4, c.getAccID(), sn));
                }
                else {
                    c.sendPacket(MTSCSPacket.sendCSFail(177));
                }
                RefreshCashShop(c);
                break;
            }
            case 29:
            case 36: {
                int sn2 = slea.readInt();
                if (sn2 == 209000310) {
                    sn2 = 20900026;
                }
                final CashItemInfo item2 = CashItemFactory.getInstance().getItem(sn2);
                final String partnerName = slea.readMapleAsciiString();
                final String msg = slea.readMapleAsciiString();
                final IItem itemz3 = chr.getCashInventory().toItem(item2);
                for (int l = 0; l < itembp_id.length; ++l) {
                    if (item2.getId() == Integer.parseInt(itembp_id[l])) {
                        c.getPlayer().dropMessage(1, "这个物品是禁止购买的.");
                        doCSPackets(c);
                        return;
                    }
                }
                if (item2 == null || !GameConstants.isEffectRing(item2.getId()) || c.getPlayer().getCSPoints(1) < item2.getPrice() || msg.length() > 73 || msg.length() < 1) {
                    chr.dropMessage(1, "购买戒指错误：\r\n你没有足够的点卷或者该物品不存在。。");
                    doCSPackets(c);
                    return;
                }
                if (!item2.genderEquals(c.getPlayer().getGender())) {
                    chr.dropMessage(1, "购买戒指错误：B\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                if (c.getPlayer().getCashInventory().getItemsSize() >= 100) {
                    chr.dropMessage(1, "购买戒指错误：C\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                if (item2.getPrice() == 2990) {}
                final Pair<Integer, Pair<Integer, Integer>> info2 = MapleCharacterUtil.getInfoByName(partnerName, c.getPlayer().getWorld());
                if (info2 == null || info2.getLeft() <= 0 || info2.getLeft() == c.getPlayer().getId()) {
                    chr.dropMessage(1, "购买戒指错误：D\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                if (info2.getRight().getLeft() == c.getAccID()) {
                    chr.dropMessage(1, "购买戒指错误：E\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                if (info2.getRight().getRight() == c.getPlayer().getGender() && action == 29) {
                    chr.dropMessage(1, "购买戒指错误：F\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                final int err = MapleRing.createRing(item2.getId(), c.getPlayer(), partnerName, msg, info2.getLeft(), item2.getSN());
                if (err != 1) {
                    chr.dropMessage(1, "购买戒指错误：G\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                c.getPlayer().modifyCSPoints(1, -item2.getPrice(), false);
                c.getSession().write(MTSCSPacket.商城送礼物(item2.getId(), item2.getCount(), partnerName));
                chr.sendNote(partnerName, partnerName + " 您已收到" + chr.getName() + "送给您的礼物，请进入现金商城查看！");
                final int chz = World.Find.findChannel(partnerName);
                if (chz > 0) {
                    final MapleCharacter receiver = ChannelServer.getInstance(chz).getPlayerStorage().getCharacterByName(partnerName);
                    if (receiver != null) {
                        receiver.showNote();
                    }
                }
                doCSPackets(c);
                return;
            }
            case 31: {
                final int type6 = slea.readByte() + 1;
                final int snID = slea.readInt();
                final CashItemInfo item5 = CashItemFactory.getInstance().getItem(snID);
                for (int m = 0; m < itembp_id.length; ++m) {
                    if (snID == Integer.parseInt(itembp_id[m])) {
                        c.getPlayer().dropMessage(1, "这个物品是禁止购买的.");
                        doCSPackets(c);
                        return;
                    }
                }
                if (c.getPlayer().isAdmin()) {
                    System.out.println("礼包购买 ID: " + snID);
                }
                switch (snID) {
                    case 10001818: {
                        c.getPlayer().dropMessage(1, "这个物品是禁止购买的.");
                        doCSPackets(c);
                        break;
                    }
                }
                List<CashItemInfo> ccc = null;
                if (item5 != null) {
                    ccc = CashItemFactory.getInstance().getPackageItems(item5.getId());
                }
                if (item5 == null || ccc == null || c.getPlayer().getCSPoints(type6) < item5.getPrice()) {
                    chr.dropMessage(1, "购买礼包错误：\r\n你没有足够的点卷或者该物品不存在。");
                    doCSPackets(c);
                    return;
                }
                if (!item5.genderEquals(c.getPlayer().getGender())) {
                    chr.dropMessage(1, "购买礼包错误：B\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                if (c.getPlayer().getCashInventory().getItemsSize() >= 100 - ccc.size()) {
                    chr.dropMessage(1, "购买礼包错误：C\r\n请联系GM！。");
                    doCSPackets(c);
                    return;
                }
                final Map<Integer, IItem> ccz = new HashMap<Integer, IItem>();
                for (final CashItemInfo i2 : ccc) {
                    for (final int iz : GameConstants.cashBlock) {
                        if (i2.getId() == iz) {}
                    }
                    final IItem itemz4 = chr.getCashInventory().toItem(i2, chr, MapleInventoryManipulator.getUniqueId(i2.getId(), null), "");
                    if (itemz4 != null && itemz4.getUniqueId() > 0) {
                        if (itemz4.getItemId() != i2.getId()) {
                            continue;
                        }
                        ccz.put(i2.getSN(), itemz4);
                        c.getPlayer().getCashInventory().addToInventory(itemz4);
                        c.getSession().write(MTSCSPacket.showBoughtCSItem(itemz4, item5.getSN(), c.getAccID()));
                    }
                }
                chr.modifyCSPoints(type6, -item5.getPrice(), false);
                break;
            }
            case 42: {
                final int snCS3 = slea.readInt();
                if (snCS3 == 50200031 && c.getPlayer().getCSPoints(1) >= 500) {
                    c.getPlayer().modifyCSPoints(1, -500);
                    c.getPlayer().modifyCSPoints(2, 500);
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "兑换500抵用卷成功"));
                }
                else if (snCS3 == 50200032 && c.getPlayer().getCSPoints(1) >= 1000) {
                    c.getPlayer().modifyCSPoints(1, -1000);
                    c.getPlayer().modifyCSPoints(2, 1000);
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "兑换抵1000用卷成功"));
                }
                else if (snCS3 == 50200033 && c.getPlayer().getCSPoints(1) >= 5000) {
                    c.getPlayer().modifyCSPoints(1, -5000);
                    c.getPlayer().modifyCSPoints(2, 5000);
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "兑换5000抵用卷成功"));
                }
                else {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "没有找到这个道具的信息！\r\n或者你点卷不足无法兑换！"));
                }
                c.getSession().write(MTSCSPacket.enableCSorMTS());
                c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 33: {
                final int isMesoGMItem = 1;
                if (isMesoGMItem == 1) {
                    chr.dropMessage(1, "禁止购买。");
                    c.getPlayer().saveToDB(true, true);
                    c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final CashItemInfo item2 = CashItemFactory.getInstance().getItem(slea.readInt());
                if (item2 == null || !MapleItemInformationProvider.getInstance().isQuestItem(item2.getId())) {
                    c.getSession().write(MTSCSPacket.sendCSFail(0));
                    doCSPackets(c);
                    return;
                }
                if (c.getPlayer().getMeso() < item2.getPrice()) {
                    c.getSession().write(MTSCSPacket.sendCSFail(184));
                    doCSPackets(c);
                    return;
                }
                if (c.getPlayer().getInventory(GameConstants.getInventoryType(item2.getId())).getNextFreeSlot() < 0) {
                    c.getSession().write(MTSCSPacket.sendCSFail(177));
                    doCSPackets(c);
                    return;
                }
                for (final int iz2 : GameConstants.cashBlock) {
                    if (item2.getId() == iz2) {
                        c.getPlayer().dropMessage(1, GameConstants.getCashBlockedMsg(item2.getId()));
                        doCSPackets(c);
                        return;
                    }
                }
                final byte pos2 = MapleInventoryManipulator.addId(c, item2.getId(), (short)item2.getCount(), null, (byte)0);
                if (pos2 < 0) {
                    c.getSession().write(MTSCSPacket.sendCSFail(177));
                    doCSPackets(c);
                    return;
                }
                chr.gainMeso(-item2.getPrice(), false);
                c.getSession().write(MTSCSPacket.showBoughtCSQuestItem(item2.getPrice(), (short)item2.getCount(), pos2, item2.getId()));
                break;
            }
            default: {
                c.getSession().write(MTSCSPacket.sendCSFail(0));
                break;
            }
        }
        doCSPackets(c);
    }
    
    private static MapleInventoryType getInventoryType(final int id) {
        switch (id) {
            case 50200075: {
                return MapleInventoryType.EQUIP;
            }
            case 50200074: {
                return MapleInventoryType.USE;
            }
            case 50200073: {
                return MapleInventoryType.ETC;
            }
            default: {
                return MapleInventoryType.UNDEFINED;
            }
        }
    }
    
    private static void RefreshCashShop(final MapleClient c) {
        c.sendPacket(MTSCSPacket.showCashInventory(c));
        c.sendPacket(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
        c.sendPacket(MTSCSPacket.enableCSUse());
        c.getPlayer().getCashInventory().checkExpire(c);
    }
    
    private static void doCSPackets(final MapleClient c) {
        c.getSession().write(MTSCSPacket.getCSInventory(c));
        c.getSession().write(MTSCSPacket.enableCSorMTS());
        c.getSession().write(MTSCSPacket.sendWishList(c.getPlayer(), false));
        c.getSession().write(MTSCSPacket.showNXMapleTokens(c.getPlayer()));
        c.getSession().write(MaplePacketCreator.enableActions());
        c.getPlayer().getCashInventory().checkExpire(c);
    }
}
