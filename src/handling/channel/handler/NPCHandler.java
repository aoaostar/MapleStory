package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.RockPaperScissors;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import handling.SendPacketOpcode;
import scripting.NPCConversationManager;
import scripting.NPCScriptManager;
import server.AutobanManager;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShop;
import server.MapleStorage;
import server.life.MapleNPC;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.data.output.MaplePacketLittleEndianWriter;

public class NPCHandler
{
    public static final void NPCAnimation(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final int length = (int)slea.available();
        if (length < 4) {
            return;
        }
        final MapleMap map = c.getPlayer().getMap();
        if (map == null) {
            return;
        }
        final int oid = slea.readInt();
        final MapleNPC npc = map.getNPCByOid(oid);
        if (npc == null) {
            if (c.getPlayer().isAdmin()) {
                c.getPlayer().dropMessage("NPC OID =" + oid);
            }
            return;
        }
        switch (npc.getId()) {
            case 2103:
            case 10000:
            case 1010100:
            case 1012003:
            case 1012106:
            case 1032004:
            case 1052103:
            case 1061100: {}
            default: {
                if (!c.getPlayer().isMapObjectVisible(npc)) {
                    return;
                }
                final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
                mplew.writeShort(SendPacketOpcode.NPC_ACTION.getValue());
                mplew.writeInt(oid);
                if (length == 6) {
                    mplew.writeShort(slea.readShort());
                }
                else {
                    if (length <= 9) {
                        if (c.getPlayer().isAdmin()) {
                            c.getPlayer().dropMessage("NPC, Packet:" + slea.toString());
                        }
                        return;
                    }
                    mplew.write(slea.read(length - 13));
                }
                c.sendPacket(mplew.getPacket());
            }
        }
    }
    
    public static void NPCShop(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final byte bmode = slea.readByte();
        if (chr == null) {
            return;
        }
        switch (bmode) {
            case 0: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                slea.skip(2);
                final int itemId = slea.readInt();
                final short quantity = slea.readShort();
                shop.buy(c, itemId, quantity);
                break;
            }
            case 1: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                final byte slot = (byte)slea.readShort();
                final int itemId2 = slea.readInt();
                final short quantity2 = slea.readShort();
                shop.sell(c, GameConstants.getInventoryType(itemId2), slot, quantity2);
                break;
            }
            case 2: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                final byte slot = (byte)slea.readShort();
                shop.recharge(c, slot);
                break;
            }
            default: {
                chr.setConversation(0);
                break;
            }
        }
    }
    
    public static void NPCTalk(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || chr.getMap() == null) {
            return;
        }
        final MapleNPC npc = chr.getMap().getNPCByOid(slea.readInt());
        slea.readInt();
        if (npc == null) {
            return;
        }
        chr.setCurrenttime(System.currentTimeMillis());
        if (chr.getCurrenttime() - chr.getLasttime() < chr.getDeadtime()) {
            chr.dropMessage("悠着点!");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        chr.setLasttime(System.currentTimeMillis());
        if (chr.getConversation() != 0) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (npc.hasShop()) {
            c.getSession().write(MaplePacketCreator.confirmShopTransaction((byte)20));
            chr.setConversation(1);
            npc.sendShop(c);
        } else {
            NPCScriptManager.getInstance().start(c, npc.getId());
        }
    }
    
    public static final void QuestAction(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final byte action = slea.readByte();
        short quest = slea.readShort();
        if (quest < 0) {
            quest += 65536;
        }
        if (chr == null) {
            return;
        }
        if (!chr.canQuestAction()) {
            chr.dropMessage(1, "提交操作过快请稍后！");
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        final MapleQuest q = MapleQuest.getInstance(quest);
        switch (action) {
            case 0: {
                chr.updateTick(slea.readInt());
                final int itemid = slea.readInt();
                MapleQuest.getInstance(quest).RestoreLostItem(chr, itemid);
                break;
            }
            case 1: {
                final int npc = slea.readInt();
                q.start(chr, npc);
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage("开始任务[" + quest + "] NPC: " + npc);
                    break;
                }
                break;
            }
            case 2: {
                final int npc = slea.readInt();
                chr.updateTick(slea.readInt());
                if (slea.available() >= 4L) {
                    q.complete(chr, npc, slea.readInt());
                }
                else {
                    q.complete(chr, npc);
                }
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage("完成任务[" + quest + "] NPC: " + npc);
                    break;
                }
                break;
            }
            case 3: {
                if (GameConstants.canForfeit(q.getId())) {
                    q.forfeit(chr);
                    break;
                }
                chr.dropMessage(1, "你不可以放弃这个任务.");
                break;
            }
            case 4: {
                final int npc = slea.readInt();
                slea.readInt();
                NPCScriptManager.getInstance().startQuest(c, npc, quest);
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage("脚本开始任务[" + quest + "] NPC: " + npc);
                    break;
                }
                break;
            }
            case 5: {
                final int npc = slea.readInt();
                NPCScriptManager.getInstance().endQuest(c, npc, quest, false);
                c.getSession().write(MaplePacketCreator.showSpecialEffect(9));
                chr.getMap().broadcastMessage(chr, MaplePacketCreator.showSpecialEffect(chr.getId(), 9), false);
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage("脚本完成任务[" + quest + "] NPC: " + npc);
                    break;
                }
                break;
            }
        }
    }
    
    public static void Storage(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final byte mode = slea.readByte();
        if (chr == null) {
            return;
        }
        final MapleStorage storage = chr.getStorage();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        switch (mode) {
            case 4: {
                final byte type = slea.readByte();
                final byte slot = storage.getSlot(MapleInventoryType.getByType(type), slea.readByte());
                final IItem item = storage.takeOut(slot);
                if (ii.isCash(item.getItemId())) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                if (item != null) {
                    if (!MapleInventoryManipulator.checkSpace(c, item.getItemId(), item.getQuantity(), item.getOwner())) {
                        storage.store(item);
                        chr.dropMessage(1, "你的物品栏已经满了..");
                    }
                    else {
                        MapleInventoryManipulator.addFromDrop(c, item, false);
                    }
                    storage.sendTakenOut(c, GameConstants.getInventoryType(item.getItemId()));
                    break;
                }
                System.out.println("[作弊] " + chr.getName() + " (等级 " + chr.getLevel() + ") 试图从仓库取出不存在的道具.");
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 5: {
                final byte slot2 = (byte)slea.readShort();
                final int itemId = slea.readInt();
                if (itemId >= 1112446 && itemId <= 1112495) {
                    c.getPlayer().dropMessage(1, "禁止存入仓库");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                short quantity = slea.readShort();
                if (quantity < 1) {
                    AutobanManager.getInstance().autoban(c, "试图存入到仓库的道具数量: " + quantity + " 道具ID: " + itemId);
                    return;
                }
                if (storage.isFull()) {
                    c.getSession().write(MaplePacketCreator.getStorageFull());
                    return;
                }
                final MapleInventoryType type2 = GameConstants.getInventoryType(itemId);
                if (chr.getInventory(type2).getItem(slot2) == null) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                if (chr.getMeso() < 100) {
                    chr.dropMessage(1, "你没有足够的金币.需要100金币");
                    break;
                }
                final IItem item2 = chr.getInventory(type2).getItem(slot2).copy();
                if (ii.isCash(item2.getItemId())) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                if (GameConstants.isPet(item2.getItemId())) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final byte flag = item2.getFlag();
                if (ii.isPickupRestricted(item2.getItemId()) && storage.findById(item2.getItemId()) != null) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                if (item2.getItemId() == itemId && (item2.getQuantity() >= quantity || GameConstants.is飞镖道具(itemId) || GameConstants.is子弹道具(itemId))) {
                    if (ii.isDropRestricted(item2.getItemId())) {
                        if (ItemFlag.KARMA_EQ.check(flag)) {
                            item2.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
                        }
                        else if (ItemFlag.KARMA_USE.check(flag)) {
                            item2.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
                        }
                        else {
                            if (!ItemFlag.LOCK.check(flag)) {
                                c.getSession().write(MaplePacketCreator.enableActions());
                                return;
                            }
                            item2.setFlag((byte)(flag - ItemFlag.LOCK.getValue()));
                        }
                    }
                    if (GameConstants.is飞镖道具(itemId) || GameConstants.is子弹道具(itemId)) {
                        quantity = item2.getQuantity();
                    }
                    chr.gainMeso(-100, false, true, false);
                    MapleInventoryManipulator.removeFromSlot(c, type2, slot2, quantity, false);
                    item2.setQuantity(quantity);
                    storage.store(item2);
                    storage.sendStored(c, GameConstants.getInventoryType(itemId));
                    break;
                }
                AutobanManager.getInstance().addPoints(c, 1000, 0L, "试图存入到仓库的道具: " + itemId + " 数量: " + quantity + " 当前玩家用道具: " + item2.getItemId() + " 数量: " + item2.getQuantity());
            }
            case 6: {
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 7: {
                int meso = slea.readInt();
                final int storageMesos = storage.getMeso();
                final int playerMesos = chr.getMeso();
                if ((meso > 0 && storageMesos >= meso) || (meso < 0 && playerMesos >= -meso)) {
                    if (meso < 0 && storageMesos - meso < 0) {
                        meso = -(Integer.MAX_VALUE - storageMesos);
                        if (-meso > playerMesos) {
                            return;
                        }
                    }
                    else if (meso > 0 && playerMesos + meso < 0) {
                        meso = Integer.MAX_VALUE - playerMesos;
                        if (meso > storageMesos) {
                            return;
                        }
                    }
                    storage.setMeso(storageMesos - meso);
                    chr.gainMeso(meso, false, true, false);
                    storage.sendMeso(c);
                    break;
                }
                AutobanManager.getInstance().addPoints(c, 1000, 0L, "Trying to store or take out unavailable amount of mesos (" + meso + "/" + storage.getMeso() + "/" + c.getPlayer().getMeso() + ")");
            }
            case 8: {
                storage.close();
                chr.setConversation(0);
                break;
            }
            default: {
                System.out.println("未知的仓库操作包 0x0: " + mode);
                break;
            }
        }
    }
    
    public static void NPCMoreTalk(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter player = c.getPlayer();
        if (player == null) {
            return;
        }
        final byte lastMsg = slea.readByte();
        final byte action = slea.readByte();
        final NPCConversationManager cm = NPCScriptManager.getInstance().getCM(c);
        if (cm == null || c.getPlayer().getConversation() == 0 || cm.getLastMsg() != lastMsg) {
            return;
        }
        cm.setLastMsg((byte)(-1));
        if (lastMsg == 2) {
            if (action != 0) {
                cm.setGetText(slea.readMapleAsciiString());
                switch (cm.getType()) {
                    case 0: {
                        NPCScriptManager.getInstance().startQuest(c, action, lastMsg, -1);
                        break;
                    }
                    case 1: {
                        NPCScriptManager.getInstance().endQuest(c, action, lastMsg, -1);
                        break;
                    }
                    default: {
                        NPCScriptManager.getInstance().action(c, action, lastMsg, -1);
                        break;
                    }
                }
            }
            else {
                cm.dispose();
            }
        }
        else {
            int selection = -1;
            if (slea.available() >= 4L) {
                selection = slea.readInt();
            }
            else if (slea.available() > 0L) {
                selection = slea.readByte();
            }
            if (lastMsg == 4 && selection == -1) {
                cm.dispose();
                return;
            }
            if (selection >= -1 && action != -1) {
                switch (cm.getType()) {
                    case 0: {
                        NPCScriptManager.getInstance().startQuest(c, action, lastMsg, selection);
                        break;
                    }
                    case 1: {
                        NPCScriptManager.getInstance().endQuest(c, action, lastMsg, selection);
                        break;
                    }
                    default: {
                        NPCScriptManager.getInstance().action(c, action, lastMsg, selection);
                        break;
                    }
                }
            }
            else {
                cm.dispose();
            }
        }
    }
    
    public static void UpdateQuest(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final MapleQuest quest = MapleQuest.getInstance(slea.readShort());
        if (quest != null) {
            c.getPlayer().updateQuest(c.getPlayer().getQuest(quest), true);
        }
    }
    
    public static void RPSGame(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (slea.available() == 0L || !c.getPlayer().getMap().containsNPC(9000019)) {
            if (c.getPlayer().getRPS() != null) {
                c.getPlayer().getRPS().dispose(c);
            }
            return;
        }
        final byte mode = slea.readByte();
        switch (mode) {
            case 0:
            case 5: {
                if (c.getPlayer().getRPS() != null) {
                    c.getPlayer().getRPS().reward(c);
                }
                if (c.getPlayer().getMeso() >= 1000) {
                    c.getPlayer().setRPS(new RockPaperScissors(c, mode));
                    break;
                }
                c.getSession().write(MaplePacketCreator.getRPSMode((byte)8, -1, -1, -1));
                break;
            }
            case 1: {
                if (c.getPlayer().getRPS() == null || !c.getPlayer().getRPS().answer(c, slea.readByte())) {
                    c.getSession().write(MaplePacketCreator.getRPSMode((byte)13, -1, -1, -1));
                    break;
                }
                break;
            }
            case 2: {
                if (c.getPlayer().getRPS() == null || !c.getPlayer().getRPS().timeOut(c)) {
                    c.getSession().write(MaplePacketCreator.getRPSMode((byte)13, -1, -1, -1));
                    break;
                }
                break;
            }
            case 3: {
                if (c.getPlayer().getRPS() == null || !c.getPlayer().getRPS().nextRound(c)) {
                    c.getSession().write(MaplePacketCreator.getRPSMode((byte)13, -1, -1, -1));
                    break;
                }
                break;
            }
            case 4: {
                if (c.getPlayer().getRPS() != null) {
                    c.getPlayer().getRPS().dispose(c);
                    break;
                }
                c.getSession().write(MaplePacketCreator.getRPSMode((byte)13, -1, -1, -1));
                break;
            }
        }
    }
}
