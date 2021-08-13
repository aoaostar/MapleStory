package handling.channel.handler;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.PlayerStats;
import client.SkillFactory;
import client.anticheat.CheatingOffense;
import client.inventory.Equip;
import client.inventory.IEquip;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.inventory.MapleMount;
import client.inventory.MaplePet;
import constants.GameConstants;
import handling.world.MaplePartyCharacter;
import handling.world.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import scripting.NPCScriptManager;
import server.AutobanManager;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShopFactory;
import server.PredictCardFactory;
import server.RandomRewards;
import server.Randomizer;
import server.StructRewardItem;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.maps.FieldLimitType;
import server.maps.MapleLove;
import server.maps.MapleMap;
import server.maps.MapleMapItem;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleMist;
import server.quest.MapleQuest;
import server.shops.HiredMerchant;
import server.shops.IMaplePlayerShop;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MTSCSPacket;
import tools.packet.PetPacket;
import tools.packet.PlayerShopPacket;

public class InventoryHandler
{
    public static int OWL_ID;
    
    public static void ItemMove(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().getPlayerShop() != null || c.getPlayer().getConversation() > 0 || c.getPlayer().getTrade() != null) {
            return;
        }
        c.getPlayer().updateTick(slea.readInt());
        final MapleInventoryType type = MapleInventoryType.getByType(slea.readByte());
        final short src = slea.readShort();
        final short dst = slea.readShort();
        final short quantity = slea.readShort();
        if (src < 0 && dst > 0) {
            MapleInventoryManipulator.unequip(c, src, dst);
        }
        else if (dst < 0) {
            MapleInventoryManipulator.equip(c, src, dst);
        }
        else if (dst == 0) {
            MapleInventoryManipulator.drop(c, type, src, quantity);
        }
        else {
            if (c.getPlayer().getGMLevel() > 0) {
                final int itemided = c.getPlayer().getInventory(type).getItem(src).getItemId();
                c.getPlayer().dropMessage("此物品的ID是:" + itemided);
            }
            MapleInventoryManipulator.move(c, type, src, dst);
        }
    }
    
    public static final void ItemSort(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        c.getPlayer().updateTick(slea.readInt());
        final MapleInventoryType pInvType = MapleInventoryType.getByType(slea.readByte());
        if (pInvType == MapleInventoryType.UNDEFINED) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final MapleInventory pInv = c.getPlayer().getInventory(pInvType);
        boolean sorted = false;
        while (!sorted) {
            final byte freeSlot = (byte)pInv.getNextFreeSlot();
            if (freeSlot != -1) {
                byte itemSlot = -1;
                for (byte i = (byte)(freeSlot + 1); i <= pInv.getSlotLimit(); ++i) {
                    if (pInv.getItem(i) != null) {
                        itemSlot = i;
                        break;
                    }
                }
                if (itemSlot > 0) {
                    MapleInventoryManipulator.move(c, pInvType, itemSlot, freeSlot);
                }
                else {
                    sorted = true;
                }
            }
            else {
                sorted = true;
            }
        }
        c.getSession().write(MaplePacketCreator.finishedSort(pInvType.getType()));
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static final void ItemGather(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        c.getPlayer().updateTick(slea.readInt());
        final byte mode = slea.readByte();
        final MapleInventoryType invType = MapleInventoryType.getByType(mode);
        final MapleInventory Inv = c.getPlayer().getInventory(invType);
        final List<IItem> itemMap = new LinkedList<IItem>();
        for (final IItem item : Inv.list()) {
            itemMap.add(item.copy());
        }
        for (final IItem itemStats : itemMap) {
            if (itemStats.getItemId() != 5110000) {
                MapleInventoryManipulator.removeById(c, invType, itemStats.getItemId(), itemStats.getQuantity(), true, false);
            }
        }
        final List<IItem> sortedItems = sortItems(itemMap);
        for (final IItem item2 : sortedItems) {
            if (item2.getItemId() != 5110000) {
                MapleInventoryManipulator.addFromDrop(c, item2, false);
            }
        }
        c.getSession().write(MaplePacketCreator.finishedGather(mode));
        c.getSession().write(MaplePacketCreator.enableActions());
        itemMap.clear();
        sortedItems.clear();
    }
    
    private static final List<IItem> sortItems(final List<IItem> passedMap) {
        final List<Integer> itemIds = new ArrayList<Integer>();
        for (final IItem item : passedMap) {
            itemIds.add(item.getItemId());
        }
        Collections.sort(itemIds);
        final List<IItem> sortedList = new LinkedList<IItem>();
        for (final Integer val : itemIds) {
            for (final IItem item2 : passedMap) {
                if (val == item2.getItemId()) {
                    sortedList.add(item2);
                    passedMap.remove(item2);
                    break;
                }
            }
        }
        return sortedList;
    }
    
    public static boolean UseRewardItem(final byte slot, final int itemId, final MapleClient c, final MapleCharacter chr) {
        final IItem toUse = c.getPlayer().getInventory(GameConstants.getInventoryType(itemId)).getItem(slot);
        c.getSession().write(MaplePacketCreator.enableActions());
        if (toUse != null && toUse.getQuantity() >= 1 && toUse.getItemId() == itemId) {
            if (chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() > -1 && chr.getInventory(MapleInventoryType.USE).getNextFreeSlot() > -1 && chr.getInventory(MapleInventoryType.SETUP).getNextFreeSlot() > -1 && chr.getInventory(MapleInventoryType.ETC).getNextFreeSlot() > -1) {
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                final Pair<Integer, List<StructRewardItem>> rewards = ii.getRewardItem(itemId);
                if (rewards != null && rewards.getLeft() > 0) {
                    boolean rewarded = false;
                    while (!rewarded) {
                        for (final StructRewardItem reward : rewards.getRight()) {
                            if (reward.prob > 0 && Randomizer.nextInt(rewards.getLeft()) < reward.prob) {
                                if (GameConstants.getInventoryType(reward.itemid) == MapleInventoryType.EQUIP) {
                                    final IItem item = ii.getEquipById(reward.itemid);
                                    if (reward.period > 0L) {
                                        item.setExpiration(System.currentTimeMillis() + reward.period * 60L * 60L * 10L);
                                    }
                                    MapleInventoryManipulator.addbyItem(c, item);
                                }
                                else {
                                    MapleInventoryManipulator.addById(c, reward.itemid, reward.quantity, (byte)0);
                                }
                                MapleInventoryManipulator.removeById(c, GameConstants.getInventoryType(itemId), itemId, 1, false, false);
                                rewarded = true;
                                return true;
                            }
                        }
                    }
                }
                else {
                    chr.dropMessage(6, "Unknown error.");
                }
            }
            else {
                chr.dropMessage(6, "你有一個欄位滿了 請空出來再打開");
            }
        }
        return false;
    }
    
    public static void QuestKJ(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getCSPoints(2) < 200) {
            chr.dropMessage(1, "你没有足够的抵用卷！");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final byte action = (byte)(slea.readByte() + 1);
        short quest = slea.readShort();
        if (quest < 0) {
            quest += 65536;
        }
        if (chr == null) {
            return;
        }
        final MapleQuest q = MapleQuest.getInstance(quest);
        switch (action) {
            case 2: {
                final int npc = slea.readInt();
                q.complete(chr, npc);
                break;
            }
        }
        chr.modifyCSPoints(2, -200);
    }
    
    public static void UseItem(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMapId() == 749040100 || chr.getMap() == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final long time = System.currentTimeMillis();
        if (chr.getNextConsume() > time) {
            chr.dropMessage(5, "暂时无法使用这个道具，请稍后在试。");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        c.getPlayer().updateTick(slea.readInt());
        final byte slot = (byte)slea.readShort();
        final int itemId = slea.readInt();
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != itemId) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (!FieldLimitType.PotionUse.check(chr.getMap().getFieldLimit()) || chr.getMapId() == 610030600) {
            if (MapleItemInformationProvider.getInstance().getItemEffect(toUse.getItemId()).applyTo(chr)) {
                MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
                if (chr.getMap().getConsumeItemCoolTime() > 0) {
                    chr.setNextConsume(time + chr.getMap().getConsumeItemCoolTime() * 1000);
                }
            }
        }
        else {
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    public static void UseReturnScroll(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (!chr.isAlive() || chr.getMapId() == 749040100) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        c.getPlayer().updateTick(slea.readInt());
        final byte slot = (byte)slea.readShort();
        final int itemId = slea.readInt();
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != itemId) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (MapleItemInformationProvider.getInstance().getItemEffect(toUse.getItemId()).applyReturnScroll(chr)) {
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
        }
        else {
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    public static boolean UseUpgradeScroll(final byte slot, final byte dst, final byte ws, final MapleClient c, final MapleCharacter chr) {
        return UseUpgradeScroll(slot, dst, ws, c, chr, 0);
    }
    
    public static boolean UseUpgradeScroll(final byte slot, final byte dst, final byte ws, final MapleClient c, final MapleCharacter chr, final int vegas) {
        boolean whiteScroll = false;
        boolean legendarySpirit = false;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if ((ws & 0x2) == 0x2) {
            whiteScroll = true;
        }
        IEquip toScroll;
        if (dst < 0) {
            toScroll = (IEquip)chr.getInventory(MapleInventoryType.EQUIPPED).getItem(dst);
        }
        else {
            legendarySpirit = true;
            toScroll = (IEquip)chr.getInventory(MapleInventoryType.EQUIP).getItem(dst);
        }
        if (toScroll == null) {
            return false;
        }
        final byte oldLevel = toScroll.getLevel();
        final byte oldEnhance = toScroll.getEnhance();
        final byte oldState = toScroll.getState();
        final byte oldFlag = toScroll.getFlag();
        final byte oldSlots = toScroll.getUpgradeSlots();
        final boolean checkIfGM = c.getPlayer().isGM();
        final IItem scroll = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (scroll == null) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if (!GameConstants.isSpecialScroll(scroll.getItemId()) && !GameConstants.isCleanSlate(scroll.getItemId()) && !GameConstants.isEquipScroll(scroll.getItemId()) && !GameConstants.isPotentialScroll(scroll.getItemId())) {
            if (toScroll.getUpgradeSlots() < 1) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                return false;
            }
        }
        else if (GameConstants.isEquipScroll(scroll.getItemId())) {
            if (toScroll.getUpgradeSlots() >= 1 || toScroll.getEnhance() >= 100 || vegas > 0 || ii.isCash(toScroll.getItemId())) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                return false;
            }
        }
        else if (GameConstants.isPotentialScroll(scroll.getItemId()) && (toScroll.getState() >= 1 || (toScroll.getLevel() == 0 && toScroll.getUpgradeSlots() == 0) || vegas > 0 || ii.isCash(toScroll.getItemId()))) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if (!GameConstants.canScroll(toScroll.getItemId()) && !GameConstants.isChaosScroll(toScroll.getItemId())) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if ((GameConstants.isCleanSlate(scroll.getItemId()) || GameConstants.isTablet(scroll.getItemId()) || GameConstants.isChaosScroll(scroll.getItemId())) && (vegas > 0 || ii.isCash(toScroll.getItemId()))) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if (GameConstants.isTablet(scroll.getItemId()) && toScroll.getDurability() < 0) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if (!GameConstants.isTablet(scroll.getItemId()) && toScroll.getDurability() >= 0) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        IItem wscroll = null;
        final List<Integer> scrollReqs = ii.getScrollReqs(scroll.getItemId());
        if (scrollReqs.size() > 0 && !scrollReqs.contains(toScroll.getItemId())) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return false;
        }
        if (whiteScroll) {
            wscroll = chr.getInventory(MapleInventoryType.USE).findById(2340000);
            if (wscroll == null) {
                whiteScroll = false;
            }
        }
        if (scroll.getItemId() == 2049115 && toScroll.getItemId() != 1003068) {
            return false;
        }
        if (GameConstants.isTablet(scroll.getItemId())) {
            switch (scroll.getItemId() % 1000 / 100) {
                case 0: {
                    if (GameConstants.isTwoHanded(toScroll.getItemId()) || !GameConstants.isWeapon(toScroll.getItemId())) {
                        return false;
                    }
                    break;
                }
                case 1: {
                    if (!GameConstants.isTwoHanded(toScroll.getItemId()) || !GameConstants.isWeapon(toScroll.getItemId())) {
                        return false;
                    }
                    break;
                }
                case 2: {
                    if (GameConstants.isAccessory(toScroll.getItemId()) || GameConstants.isWeapon(toScroll.getItemId())) {
                        return false;
                    }
                    break;
                }
                case 3: {
                    if (!GameConstants.isAccessory(toScroll.getItemId()) || GameConstants.isWeapon(toScroll.getItemId())) {
                        return false;
                    }
                    break;
                }
            }
        }
        else if (!GameConstants.isAccessoryScroll(scroll.getItemId()) && !GameConstants.isChaosScroll(scroll.getItemId()) && !GameConstants.isCleanSlate(scroll.getItemId()) && !GameConstants.isEquipScroll(scroll.getItemId()) && !GameConstants.isPotentialScroll(scroll.getItemId()) && !ii.canScroll(scroll.getItemId(), toScroll.getItemId())) {
            return false;
        }
        if (GameConstants.isAccessoryScroll(scroll.getItemId()) && !GameConstants.isAccessory(toScroll.getItemId())) {
            return false;
        }
        if (scroll.getQuantity() <= 0) {
            return false;
        }
        if (legendarySpirit && vegas == 0 && chr.getSkillLevel(SkillFactory.getSkill(1003)) <= 0 && chr.getSkillLevel(SkillFactory.getSkill(10001003)) <= 0 && chr.getSkillLevel(SkillFactory.getSkill(20001003)) <= 0 && chr.getSkillLevel(SkillFactory.getSkill(20011003)) <= 0 && chr.getSkillLevel(SkillFactory.getSkill(30001003)) <= 0) {
            AutobanManager.getInstance().addPoints(c, 50, 120000L, "Using the Skill 'Legendary Spirit' without having it.");
            return false;
        }
        final IEquip scrolled = (IEquip)ii.scrollEquipWithId(toScroll, scroll, whiteScroll, chr, vegas, checkIfGM);
        IEquip.ScrollResult scrollSuccess;
        if (scrolled == null) {
            scrollSuccess = IEquip.ScrollResult.CURSE;
        }
        else if (scrolled.getLevel() > oldLevel || scrolled.getEnhance() > oldEnhance || scrolled.getState() > oldState || scrolled.getFlag() > oldFlag) {
            scrollSuccess = IEquip.ScrollResult.SUCCESS;
        }
        else if (GameConstants.isCleanSlate(scroll.getItemId()) && scrolled.getUpgradeSlots() > oldSlots) {
            scrollSuccess = IEquip.ScrollResult.SUCCESS;
        }
        else {
            scrollSuccess = IEquip.ScrollResult.FAIL;
        }
        chr.getInventory(MapleInventoryType.USE).removeItem(scroll.getPosition(), (short)1, false);
        if (whiteScroll) {
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, wscroll.getPosition(), (short)1, false, false);
        }
        if (scrollSuccess == IEquip.ScrollResult.CURSE) {
            c.getSession().write(MaplePacketCreator.scrolledItem(scroll, toScroll, true, false));
            if (dst < 0) {
                chr.getInventory(MapleInventoryType.EQUIPPED).removeItem(toScroll.getPosition());
            }
            else {
                chr.getInventory(MapleInventoryType.EQUIP).removeItem(toScroll.getPosition());
            }
        }
        else if (vegas == 0) {
            c.getSession().write(MaplePacketCreator.scrolledItem(scroll, scrolled, false, false));
        }
        chr.getMap().broadcastMessage(chr, MaplePacketCreator.getScrollEffect(c.getPlayer().getId(), scrollSuccess, legendarySpirit), vegas == 0);
        if (dst < 0 && (scrollSuccess == IEquip.ScrollResult.SUCCESS || scrollSuccess == IEquip.ScrollResult.CURSE) && vegas == 0) {
            chr.equipChanged();
        }
        return true;
    }
    
    public static void UseCatchItem(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        c.getPlayer().updateTick(slea.readInt());
        final byte slot = (byte)slea.readShort();
        final int itemid = slea.readInt();
        final MapleMonster mob = chr.getMap().getMonsterByOid(slea.readInt());
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse != null && toUse.getQuantity() > 0 && toUse.getItemId() == itemid && mob != null) {
            switch (itemid) {
                case 2270004: {
                    final MapleMap map = chr.getMap();
                    if (mob.getHp() <= mob.getMobMaxHp() / 2L) {
                        map.broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)1));
                        map.killMonster(mob, chr, true, false, (byte)0);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, itemid, 1, false, false);
                        MapleInventoryManipulator.addById(c, 4001169, (short)1, (byte)0);
                        break;
                    }
                    map.broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)0));
                    chr.dropMessage(5, "怪物的生命力还很强大,无法捕捉.");
                    break;
                }
                case 2270002: {
                    final MapleMap map = chr.getMap();
                    if (mob.getHp() <= mob.getMobMaxHp() / 2L) {
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)1));
                        map.killMonster(mob, chr, true, false, (byte)0);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, itemid, 1, false, false);
                        c.getPlayer().setAPQScore(c.getPlayer().getAPQScore() + 1);
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.updateAriantPQRanking(c.getPlayer().getName(), c.getPlayer().getAPQScore(), false));
                        break;
                    }
                    c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)0));
                    c.sendPacket(MaplePacketCreator.catchMob(mob.getId(), itemid, (byte)0));
                    break;
                }
                case 2270000: {
                    if (mob.getId() != 9300101) {
                        break;
                    }
                    final MapleMap map = c.getPlayer().getMap();
                    map.broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)1));
                    map.killMonster(mob, chr, true, false, (byte)0);
                    MapleInventoryManipulator.addById(c, 1902000, (short)1, null, (byte)0);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, itemid, 1, false, false);
                    break;
                }
                case 2270003: {
                    if (mob.getId() != 9500320) {
                        break;
                    }
                    final MapleMap map = c.getPlayer().getMap();
                    if (mob.getHp() <= mob.getMobMaxHp() / 2L) {
                        map.broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)1));
                        map.killMonster(mob, chr, true, false, (byte)0);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, itemid, 1, false, false);
                        break;
                    }
                    map.broadcastMessage(MaplePacketCreator.catchMonster(mob.getId(), itemid, (byte)0));
                    chr.dropMessage(5, "怪物的生命力还很强大,无法捕捉.");
                    break;
                }
            }
        }
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static void UseMountFood(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        c.getPlayer().updateTick(slea.readInt());
        final byte slot = (byte)slea.readShort();
        final int itemid = slea.readInt();
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        final MapleMount mount = chr.getMount();
        if (itemid / 10000 == 226 && toUse != null && toUse.getQuantity() > 0 && toUse.getItemId() == itemid && mount != null) {
            final int fatigue = mount.getFatigue();
            boolean levelup = false;
            mount.setFatigue((byte)(-30));
            if (fatigue > 0) {
                mount.increaseExp();
                final int level = mount.getLevel();
                if (mount.getExp() >= GameConstants.getMountExpNeededForLevel(level + 1) && level < 31) {
                    mount.setLevel((byte)(level + 1));
                    levelup = true;
                }
            }
            chr.getMap().broadcastMessage(MaplePacketCreator.updateMount(chr, levelup));
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
        }
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static void UsePenguinBox(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final List<Integer> gift = new ArrayList<Integer>();
        final byte slot = (byte)slea.readShort();
        final int item = slea.readInt();
        final IItem toUse = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse.getItemId() != item) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() <= 2) {
            c.getPlayer().dropMessage(1, "您无法获得物品\r\n背包装备栏剩余栏位不足\r\n装备栏最少留下3个空格");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (c.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() <= 2) {
            c.getPlayer().dropMessage(1, "您无法获得物品\r\n背包消耗栏剩余栏位不足\r\n消耗栏最少留下3个空格");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (c.getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() <= 2) {
            c.getPlayer().dropMessage(1, "您无法获得物品\r\n背包设置栏剩余栏位不足\r\n设置栏最少留下3个空格");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (c.getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() <= 2) {
            c.getPlayer().dropMessage(1, "您无法获得物品\r\n背包其他栏剩余栏位不足\r\n其他栏最少留下3个空格");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (c.getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() <= 2) {
            c.getPlayer().dropMessage(1, "您无法获得物品\r\n背包特殊栏剩余栏位不足\r\n特殊栏最少留下3个空格");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        switch (item) {
            case 2022570: {
                gift.add(1302119);
                gift.add(1312045);
                gift.add(1322073);
                break;
            }
            case 2022571: {
                gift.add(1372053);
                gift.add(1382070);
                break;
            }
            case 2022572: {
                gift.add(1462066);
                gift.add(1452073);
                break;
            }
            case 2022573: {
                gift.add(1332088);
                gift.add(1472089);
                break;
            }
            case 2022574: {
                gift.add(1482037);
                gift.add(1492038);
                break;
            }
            case 2022575: {
                gift.add(1040145);
                gift.add(1041148);
                break;
            }
            case 2022576: {
                gift.add(1050155);
                gift.add(1051191);
                break;
            }
            case 2022577: {
                gift.add(1040146);
                gift.add(1041149);
                break;
            }
            case 2022578: {
                gift.add(1040147);
                gift.add(1041150);
                break;
            }
            case 2022579: {
                gift.add(1052208);
                break;
            }
            case 2022580: {
                gift.add(1072399);
                gift.add(1060134);
                gift.add(1061156);
                break;
            }
            case 2022581: {
                gift.add(1072400);
                break;
            }
            case 2022582: {
                gift.add(1072401);
                gift.add(1060135);
                gift.add(1061157);
                break;
            }
            case 2022583: {
                gift.add(1072402);
                gift.add(1060136);
                gift.add(1061158);
                break;
            }
            case 2022336: {
                NPCScriptManager.getInstance().start(c, 9900004, 6666);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022670: {
                NPCScriptManager.getInstance().start(c, 9900004, 6000);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022613: {
                NPCScriptManager.getInstance().start(c, 9900004, 6001);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022615: {
                NPCScriptManager.getInstance().start(c, 9900004, 6002);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022618: {
                NPCScriptManager.getInstance().start(c, 9900004, 6003);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022465: {
                NPCScriptManager.getInstance().start(c, 9900004, 6004);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022466: {
                NPCScriptManager.getInstance().start(c, 9900004, 6005);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022467: {
                NPCScriptManager.getInstance().start(c, 9900004, 6006);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
            case 2022468: {
                NPCScriptManager.getInstance().start(c, 9900004, 6007);
                c.getSession().write(MaplePacketCreator.enableActions());
                break;
            }
        }
        if (gift.isEmpty()) {
            if (c.getPlayer().isGM()) {}
        }
        else {
            final int rand = ThreadLocalRandom.current().nextInt(gift.size());
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
            MapleInventoryManipulator.addById(c, gift.get(rand), (short)1, (byte)0);
            gift.clear();
        }
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static void SunziBF(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        slea.readInt();
        final byte slot = (byte)slea.readShort();
        final int itemid = slea.readInt();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final IItem item = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);
        if (item == null || item.getItemId() != itemid || c.getPlayer().getLevel() > 255) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final int expGained = ii.getExpCache(itemid) * c.getChannelServer().getExpRate();
        c.getPlayer().gainExp(expGained, true, false, false);
        c.getSession().write(MaplePacketCreator.enableActions());
        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
    }
    
    public static void UseSummonBag(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (!chr.isAlive()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (chr.getMapId() >= 910000000 && chr.getMapId() <= 910000022) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().dropMessage(5, "市场无法使用召唤包.");
            return;
        }
        c.getPlayer().updateTick(slea.readInt());
        final byte slot = (byte)slea.readShort();
        final int itemId = slea.readInt();
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse != null && toUse.getQuantity() >= 1 && toUse.getItemId() == itemId) {
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
            if (c.getPlayer().isGM() || !FieldLimitType.SummoningBag.check(chr.getMap().getFieldLimit())) {
                final List<Pair<Integer, Integer>> toSpawn = MapleItemInformationProvider.getInstance().getSummonMobs(itemId);
                if (toSpawn == null) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return;
                }
                final int type = 0;
                for (int i = 0; i < toSpawn.size(); ++i) {
                    if (Randomizer.nextInt(99) <= toSpawn.get(i).getRight()) {
                        final MapleMonster ht = MapleLifeFactory.getMonster(toSpawn.get(i).getLeft());
                        chr.getMap().spawnMonster_sSack(ht, chr.getPosition(), type);
                    }
                }
            }
        }
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static void UseTreasureChest(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final short slot = slea.readShort();
        final int itemid = slea.readInt();
        final boolean useCash = slea.readByte() > 0;
        final IItem toUse = chr.getInventory(MapleInventoryType.ETC).getItem((byte)slot);
        if (toUse == null || toUse.getQuantity() <= 0 || toUse.getItemId() != itemid) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        int reward = 0;
        int keyIDforRemoval = 0;
        String box = null;
        String key = null;
        int price = 0;
        switch (toUse.getItemId()) {
            case 4280000: {
                reward = RandomRewards.getInstance().getGoldBoxReward();
                keyIDforRemoval = 5490000;
                box = "永恒的谜之蛋";
                key = "永恒的热度";
                price = 800;
                break;
            }
            case 4280001: {
                reward = RandomRewards.getInstance().getSilverBoxReward();
                keyIDforRemoval = 5490001;
                box = "重生的谜之蛋";
                key = "重生的热度";
                price = 500;
                break;
            }
            default: {
                return;
            }
        }
        int amount = 1;
        switch (reward) {
            case 2000004: {
                amount = 200;
                break;
            }
            case 2000005: {
                amount = 100;
                break;
            }
        }
        if (useCash && chr.getCSPoints(2) < price) {
            chr.dropMessage(1, "抵用券不足" + price + "点");
            c.getSession().write(MaplePacketCreator.enableActions());
        }
        else if (chr.getInventory(MapleInventoryType.CASH).countById(keyIDforRemoval) < 0) {
            chr.dropMessage(1, "孵化" + box + "需要" + key + "，请到商城购买！");
            c.getSession().write(MaplePacketCreator.enableActions());
        }
        else if (chr.getInventory(MapleInventoryType.CASH).countById(keyIDforRemoval) > 0 || (useCash && chr.getCSPoints(2) > price)) {
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(c, reward, (short)amount);
            if (item == null) {
                chr.dropMessage(1, "孵化失败，请重试一次。\r\n你的背包可能满了");
                c.getSession().write(MaplePacketCreator.enableActions());
                return;
            }
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.ETC, slot, (short)1, true);
            if (useCash) {
                chr.modifyCSPoints(2, -price, true);
            }
            else {
                MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, keyIDforRemoval, 1, true, false);
            }
            c.getSession().write(MaplePacketCreator.getShowItemGain(reward, (short)amount, true));
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness > 0 || reward > 0) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 从" + box + "中获得{" + ii.getName(item.getItemId()) + "}！大家一起恭喜他（她）吧！！！！", item, rareness, c.getChannel()));
            }
        }
        else {
            chr.dropMessage(5, "孵化" + box + "失败\r\n请检查是否有" + key + "\r\n或者抵用卷是否有" + price + "点。");
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    public static void UseCashItem(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final byte slot = (byte)slea.readShort();
        final int itemId = slea.readInt();
        final IItem toUse = c.getPlayer().getInventory(MapleInventoryType.CASH).getItem(slot);
        if (toUse == null || toUse.getItemId() != itemId || toUse.getQuantity() < 1) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        boolean used = false;
        boolean cc = false;
        Label_10360: {
            switch (itemId) {
                case 5042000: {
                    c.getPlayer().changeMap(701000200);
                    used = true;
                    break;
                }
                case 5042001: {
                    used = true;
                    break;
                }
                case 5043000: {
                    final short questid = slea.readShort();
                    final int npcid = slea.readInt();
                    final MapleQuest quest = MapleQuest.getInstance(questid);
                    if (c.getPlayer().getQuest(quest).getStatus() == 1 && quest.canComplete(c.getPlayer(), npcid)) {
                        final int mapId = MapleLifeFactory.getNPCLocation(npcid);
                        if (mapId != -1) {
                            final MapleMap map = c.getChannelServer().getMapFactory().getMap(mapId);
                            if (map.containsNPC(npcid) && !FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit()) && !FieldLimitType.VipRock.check(map.getFieldLimit()) && c.getPlayer().getEventInstance() == null) {
                                c.getPlayer().changeMap(map, map.getPortal(0));
                            }
                            used = true;
                        }
                        else {
                            c.getPlayer().dropMessage(1, "发生未知错误.");
                        }
                        break;
                    }
                    break;
                }
                case 5040000:
                case 5040001:
                case 5041000: {
                    if (slea.readByte() == 0) {
                        final MapleMap target = c.getChannelServer().getMapFactory().getMap(slea.readInt());
                        if (target != null && ((itemId == 5041000 && c.getPlayer().isRockMap(target.getId())) || (itemId != 5041000 && c.getPlayer().isRegRockMap(target.getId()))) && !FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit()) && !FieldLimitType.VipRock.check(target.getFieldLimit()) && c.getPlayer().getEventInstance() == null) {
                            c.getPlayer().changeMap(target, target.getPortal(0));
                            used = true;
                        }
                        break;
                    }
                    final MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(slea.readMapleAsciiString());
                    if (victim != null && !victim.isGM() && c.getPlayer().getEventInstance() == null && victim.getEventInstance() == null) {
                        if (!FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit()) && !FieldLimitType.VipRock.check(c.getChannelServer().getMapFactory().getMap(victim.getMapId()).getFieldLimit()) && (itemId == 5041000 || victim.getMapId() / 100000000 == c.getPlayer().getMapId() / 100000000)) {
                            c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
                            used = true;
                        }
                    }
                    else {
                        c.getPlayer().dropMessage(1, "在此频道未找到该玩家.");
                    }
                    break;
                }
                case 5050000: {
                    final List<Pair<MapleStat, Integer>> statupdate = new ArrayList<Pair<MapleStat, Integer>>(2);
                    final int apto = slea.readInt();
                    final int apfrom = slea.readInt();
                    if (apto == apfrom) {
                        break;
                    }
                    final int job = c.getPlayer().getJob();
                    final PlayerStats playerst = c.getPlayer().getStat();
                    used = true;
                    if (apfrom == 8192 && apto != 32768) {
                        c.sendPacket(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (apfrom == 32768 && apto != 8192) {
                        c.sendPacket(MaplePacketCreator.enableActions());
                        return;
                    }
                    switch (apto) {
                        case 256: {
                            if (playerst.getStr() >= 999) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 512: {
                            if (playerst.getDex() >= 999) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 1024: {
                            if (playerst.getInt() >= 999) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 2048: {
                            if (playerst.getLuk() >= 999) {
                                used = false;
                                break;
                            }
                            break;
                        }
                    }
                    switch (apfrom) {
                        case 256: {
                            if (playerst.getStr() <= 4) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 512: {
                            if (playerst.getDex() <= 4) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 1024: {
                            if (playerst.getInt() <= 4) {
                                used = false;
                                break;
                            }
                            break;
                        }
                        case 2048: {
                            if (playerst.getLuk() <= 4) {
                                used = false;
                                break;
                            }
                            break;
                        }
                    }
                    if (used) {
                        switch (apto) {
                            case 256: {
                                final int toSet = playerst.getStr() + 1;
                                playerst.setStr((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.STR, toSet));
                                break;
                            }
                            case 512: {
                                final int toSet = playerst.getDex() + 1;
                                playerst.setDex((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.DEX, toSet));
                                break;
                            }
                            case 1024: {
                                final int toSet = playerst.getInt() + 1;
                                playerst.setInt((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.INT, toSet));
                                break;
                            }
                            case 2048: {
                                final int toSet = playerst.getLuk() + 1;
                                playerst.setLuk((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.LUK, toSet));
                            }
                        }
                        switch (apfrom) {
                            case 256: {
                                final int toSet = playerst.getStr() - 1;
                                playerst.setStr((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.STR, toSet));
                                break;
                            }
                            case 512: {
                                final int toSet = playerst.getDex() - 1;
                                playerst.setDex((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.DEX, toSet));
                                break;
                            }
                            case 1024: {
                                final int toSet = playerst.getInt() - 1;
                                playerst.setInt((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.INT, toSet));
                                break;
                            }
                            case 2048: {
                                final int toSet = playerst.getLuk() - 1;
                                playerst.setLuk((short)toSet);
                                statupdate.add(new Pair<MapleStat, Integer>(MapleStat.LUK, toSet));
                            }
                        }
                        c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, c.getPlayer().getJob()));
                        break;
                    }
                    break;
                }
                case 5050001:
                case 5050002:
                case 5050003:
                case 5050004: {
                    final int skill1 = slea.readInt();
                    final int skill2 = slea.readInt();
                    final ISkill skillSPTo = SkillFactory.getSkill(skill1);
                    final ISkill skillSPFrom = SkillFactory.getSkill(skill2);
                    if (skillSPTo.isBeginnerSkill()) {
                        break;
                    }
                    if (skillSPFrom.isBeginnerSkill()) {
                        break;
                    }
                    if (c.getPlayer().getSkillLevel(skillSPTo) + 1 <= skillSPTo.getMaxLevel() && c.getPlayer().getSkillLevel(skillSPFrom) > 0) {
                        c.getPlayer().changeSkillLevel(skillSPFrom, (byte)(c.getPlayer().getSkillLevel(skillSPFrom) - 1), c.getPlayer().getMasterLevel(skillSPFrom));
                        c.getPlayer().changeSkillLevel(skillSPTo, (byte)(c.getPlayer().getSkillLevel(skillSPTo) + 1), c.getPlayer().getMasterLevel(skillSPTo));
                        used = true;
                        break;
                    }
                    break;
                }
                case 5060000: {
                    IItem daojuquming = null;
                    final int zhuangbeicao = slea.readShort();
                    daojuquming = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((byte)zhuangbeicao);
                    c.getSession().write(MaplePacketCreator.serverNotice(5, "请将道具直接点在你需要刻名的装备上."));
                    if (daojuquming == null) {
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    daojuquming.setOwner(c.getPlayer().getName());
                    c.getSession().write(MaplePacketCreator.updateEquipSlot(daojuquming));
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getSession().write(MaplePacketCreator.serverNotice(5, "道具刻名成功~！"));
                    break;
                }
                case 5080000:
                case 5080001:
                case 5080002:
                case 5080003: {
                    final MapleLove love = new MapleLove(c.getPlayer(), c.getPlayer().getPosition(), c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId(), slea.readMapleAsciiString(), itemId);
                    c.getPlayer().getMap().spawnLove(love);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    break;
                }
                case 5201004: {
                    final int DDcount = 20;
                    c.getPlayer().gainBeans(DDcount);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getPlayer().dropMessage(5, "成功充值20豆豆！！");
                    c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), DDcount));
                    cc = true;
                    break;
                }
                case 5201005: {
                    final int DDcount = 50;
                    c.getPlayer().gainBeans(DDcount);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getPlayer().dropMessage(5, "成功充值50豆豆！！");
                    c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), DDcount));
                    cc = true;
                    c.getPlayer().saveToDB(true, true);
                    break;
                }
                case 5201001: {
                    final int DDcount = 500;
                    c.getPlayer().gainBeans(DDcount);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getPlayer().dropMessage(5, "成功充值500豆豆！！");
                    c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), DDcount));
                    cc = true;
                    c.getPlayer().saveToDB(true, true);
                    break;
                }
                case 5201000: {
                    final int DDcount = 2000;
                    c.getPlayer().gainBeans(DDcount);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getPlayer().dropMessage(5, "成功充值2000豆豆！！");
                    c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), DDcount));
                    cc = true;
                    c.getPlayer().saveToDB(true, true);
                    break;
                }
                case 5201002: {
                    final int DDcount = 3000;
                    c.getPlayer().gainBeans(DDcount);
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                    c.getPlayer().dropMessage(5, "成功充值3000豆豆！！");
                    c.getSession().write(MaplePacketCreator.updateBeans(c.getPlayer().getId(), DDcount));
                    cc = true;
                    c.getPlayer().saveToDB(true, true);
                    break;
                }
                case 5520000: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && !ItemFlag.KARMA_EQ.check(item.getFlag()) && !ItemFlag.KARMA_USE.check(item.getFlag()) && ((itemId == 5520000 && MapleItemInformationProvider.getInstance().isKarmaEnabled(item.getItemId())) || MapleItemInformationProvider.getInstance().isPKarmaEnabled(item.getItemId()))) {
                        byte flag = item.getFlag();
                        if (type == MapleInventoryType.EQUIP) {
                            flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                        }
                        else {
                            flag |= (byte)ItemFlag.KARMA_USE.getValue();
                        }
                        flag &= (byte)~ItemFlag.UNTRADEABLE.getValue();
                        item.setFlag(flag);
                        c.getPlayer().forceReAddItem_Flag(item, type);
                        used = true;
                        break;
                    }
                    break;
                }
                case 5570000: {
                    slea.readInt();
                    final Equip item2 = (Equip)c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte)slea.readInt());
                    if (item2 == null) {
                        break;
                    }
                    if (GameConstants.canHammer(item2.getItemId()) && MapleItemInformationProvider.getInstance().getSlots(item2.getItemId()) > 0 && item2.getViciousHammer() <= 2) {
                        item2.setViciousHammer((byte)(item2.getViciousHammer() + 1));
                        item2.setUpgradeSlots((byte)(item2.getUpgradeSlots() + 1));
                        c.getPlayer().forceReAddItem(item2, MapleInventoryType.EQUIP);
                        used = true;
                        cc = true;
                        break;
                    }
                    c.getPlayer().dropMessage(5, "你不得在这个物品上使用它.");
                    cc = true;
                    break;
                }
                case 5060001: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && item.getExpiration() == -1L) {
                        byte flag = item.getFlag();
                        flag |= (byte)ItemFlag.LOCK.getValue();
                        item.setFlag(flag);
                        c.getPlayer().forceReAddItem_Flag(item, type);
                        used = true;
                        break;
                    }
                    break;
                }
                case 5061000: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && item.getExpiration() == -1L) {
                        byte flag = item.getFlag();
                        flag |= (byte)ItemFlag.LOCK.getValue();
                        item.setFlag(flag);
                        item.setExpiration(System.currentTimeMillis() + 604800000L);
                        c.getPlayer().forceReAddItem_Flag(item, type);
                        used = true;
                        break;
                    }
                    break;
                }
                case 5061001: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && item.getExpiration() == -1L) {
                        short flag2 = item.getFlag();
                        flag2 |= (short)ItemFlag.LOCK.getValue();
                        item.setFlag((byte)flag2);
                        long days = 0L;
                        switch (itemId) {
                            case 5061001: {
                                days = 30L;
                                break;
                            }
                        }
                        if (days > 0L) {
                            item.setExpiration(System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L);
                        }
                        c.getPlayer().forceUpdateItem(type, item);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                        c.getPlayer().dropMessage(5, "使用封印之锁 物品ID: " + itemId + " 天数: " + days);
                        c.getSession().write(MaplePacketCreator.enableActions());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "使用道具出现错误.");
                    break;
                }
                case 5061002: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && item.getExpiration() == -1L) {
                        short flag2 = item.getFlag();
                        flag2 |= (short)ItemFlag.LOCK.getValue();
                        item.setFlag((byte)flag2);
                        long days = 0L;
                        switch (itemId) {
                            case 5061002: {
                                days = 90L;
                                break;
                            }
                        }
                        if (days > 0L) {
                            item.setExpiration(System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L);
                        }
                        c.getPlayer().forceUpdateItem(type, item);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                        c.getPlayer().dropMessage(5, "使用封印之锁 物品ID: " + itemId + " 天数: " + days);
                        c.getSession().write(MaplePacketCreator.enableActions());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "使用道具出现错误.");
                    break;
                }
                case 5061003: {
                    final MapleInventoryType type = MapleInventoryType.getByType((byte)slea.readInt());
                    final IItem item = c.getPlayer().getInventory(type).getItem((byte)slea.readInt());
                    if (item != null && item.getExpiration() == -1L) {
                        short flag2 = item.getFlag();
                        flag2 |= (short)ItemFlag.LOCK.getValue();
                        item.setFlag((byte)flag2);
                        long days = 0L;
                        switch (itemId) {
                            case 5061003: {
                                days = 365L;
                                break;
                            }
                        }
                        if (days > 0L) {
                            item.setExpiration(System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L);
                        }
                        c.getPlayer().forceUpdateItem(type, item);
                        MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, itemId, 1, true, false);
                        c.getPlayer().dropMessage(5, "使用封印之锁 物品ID: " + itemId + " 天数: " + days);
                        c.getSession().write(MaplePacketCreator.enableActions());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "使用道具出现错误.");
                    break;
                }
                case 5070000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final StringBuilder sb = new StringBuilder();
                    addMedalString(c.getPlayer(), sb);
                    sb.append(c.getPlayer().getName());
                    sb.append(" : ");
                    sb.append(message);
                    final boolean ear = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(2, sb.toString()));
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(2, sb.toString()));
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5071000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final boolean ear2 = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    final StringBuilder sb2 = new StringBuilder();
                    addMedalString(c.getPlayer(), sb2);
                    sb2.append(c.getPlayer().getName());
                    sb2.append(" : ");
                    sb2.append(message);
                    if (c.getPlayer().isPlayer()) {
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(2, sb2.toString()));
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(2, sb2.toString()));
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5077000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final byte numLines = slea.readByte();
                    if (numLines > 3) {
                        return;
                    }
                    final List<String> messages = new LinkedList<String>();
                    for (int i = 0; i < numLines; ++i) {
                        final String message2 = slea.readMapleAsciiString();
                        if (message2.length() > 65) {
                            break;
                        }
                        messages.add(c.getPlayer().getName() + " : " + message2);
                    }
                    final boolean ear3 = slea.readByte() > 0;
                    if ((c.getPlayer().isPlayer() && messages.indexOf("幹") != -1) || messages.indexOf("豬") != -1 || messages.indexOf("笨") != -1 || messages.indexOf("靠") != -1 || messages.indexOf("腦包") != -1 || messages.indexOf("腦") != -1 || messages.indexOf("智障") != -1 || messages.indexOf("白目") != -1 || messages.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.tripleSmega(messages, ear3, c.getChannel()).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + messages);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.tripleSmega(messages, ear3, c.getChannel()).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + messages);
                    }
                    used = true;
                    break;
                }
                case 5073000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final StringBuilder sb = new StringBuilder();
                    addMedalString(c.getPlayer(), sb);
                    sb.append(c.getPlayer().getName());
                    sb.append(" : ");
                    sb.append(message);
                    final boolean ear = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5074000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final StringBuilder sb = new StringBuilder();
                    addMedalString(c.getPlayer(), sb);
                    sb.append(c.getPlayer().getName());
                    sb.append(" : ");
                    sb.append(message);
                    final boolean ear = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5072000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須要10等以上才能使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final StringBuilder sb = new StringBuilder();
                    addMedalString(c.getPlayer(), sb);
                    sb.append(c.getPlayer().getName());
                    sb.append(" : ");
                    sb.append(message);
                    final boolean ear = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, c.getChannel(), sb.toString(), ear).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5076000: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String message = slea.readMapleAsciiString();
                    if (message.length() > 65) {
                        break;
                    }
                    final StringBuilder sb = new StringBuilder();
                    addMedalString(c.getPlayer(), sb);
                    sb.append(c.getPlayer().getName());
                    sb.append(" : ");
                    sb.append(message);
                    final boolean ear = slea.readByte() > 0;
                    IItem item3 = null;
                    if (slea.readByte() == 1) {
                        final byte invType = (byte)slea.readInt();
                        final byte pos = (byte)slea.readInt();
                        item3 = c.getPlayer().getInventory(MapleInventoryType.getByType(invType)).getItem(pos);
                    }
                    if ((c.getPlayer().isPlayer() && message.indexOf("幹") != -1) || message.indexOf("豬") != -1 || message.indexOf("笨") != -1 || message.indexOf("靠") != -1 || message.indexOf("腦包") != -1 || message.indexOf("腦") != -1 || message.indexOf("智障") != -1 || message.indexOf("白目") != -1 || message.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone(sb.toString(), ear, c.getChannel(), item3).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone(sb.toString(), ear, c.getChannel(), item3).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + message);
                    }
                    used = true;
                    break;
                }
                case 5075000:
                case 5075001:
                case 5075002: {
                    c.getPlayer().dropMessage(5, "没有mapletvs广播消息.");
                    break;
                }
                case 5075003:
                case 5075004:
                case 5075005: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必須等級10級以上才可以使用.");
                        break;
                    }
                    final int tvType = itemId % 10;
                    if (tvType == 3) {
                        slea.readByte();
                    }
                    final boolean ear2 = tvType != 1 && tvType != 2 && slea.readByte() > 1;
                    MapleCharacter victim2 = (tvType == 1 || tvType == 4) ? null : c.getChannelServer().getPlayerStorage().getCharacterByName(slea.readMapleAsciiString());
                    if (tvType == 0 || tvType == 3) {
                        victim2 = null;
                    }
                    else if (victim2 == null) {
                        c.getPlayer().dropMessage(1, "这个角色不是在频道里.");
                        break;
                    }
                    final String message3 = slea.readMapleAsciiString();
                    World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, c.getChannel(), c.getPlayer().getName() + " : " + message3, ear2).getBytes());
                    break;
                }
                case 5090000:
                case 5090100: {
                    final String sendTo = slea.readMapleAsciiString();
                    final String msg = slea.readMapleAsciiString();
                    c.getPlayer().sendNote(sendTo, msg);
                    used = true;
                    break;
                }
                case 5100000: {
                    c.getPlayer().getMap().broadcastMessage(MTSCSPacket.playCashSong(5100000, c.getPlayer().getName()));
                    used = true;
                    break;
                }
                case 5152049:
                case 5152100:
                case 5152101:
                case 5152102:
                case 5152103:
                case 5152104:
                case 5152105:
                case 5152106:
                case 5152107: {
                    final MapleCharacter chr = c.getPlayer();
                    final int color = (itemId - 5152100) * 100;
                    if (chr.isGM()) {
                        System.out.println("使用一次性隐形眼镜 - 道具: " + itemId + " 颜色: " + color);
                    }
                    if (color >= 0) {
                        changeFace(chr, color);
                        used = true;
                        break;
                    }
                    chr.dropMessage(1, "使用一次性隐形眼镜出现错误.");
                    break;
                }
                case 5190000:
                case 5190001:
                case 5190002:
                case 5190003:
                case 5190004:
                case 5190005:
                case 5190006:
                case 5190007:
                case 5190008: {
                    final int uniqueid = (int)slea.readLong();
                    MaplePet pet = c.getPlayer().getPet(0);
                    int slo = 0;
                    if (pet == null) {
                        break;
                    }
                    if (pet.getUniqueId() != uniqueid) {
                        pet = c.getPlayer().getPet(1);
                        slo = 1;
                        if (pet == null) {
                            break;
                        }
                        if (pet.getUniqueId() != uniqueid) {
                            pet = c.getPlayer().getPet(2);
                            slo = 2;
                            if (pet == null) {
                                break;
                            }
                            if (pet.getUniqueId() != uniqueid) {
                                break;
                            }
                        }
                    }
                    final MaplePet.PetFlag zz = MaplePet.PetFlag.getByAddId(itemId);
                    if (zz != null && !zz.check(pet.getFlags())) {
                        pet.setFlags(pet.getFlags() | zz.getValue());
                        c.getSession().write(PetPacket.updatePet(pet, c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte)pet.getInventoryPosition()), true));
                        c.getPlayer().getClient().getSession().write(PetPacket.petStatUpdate(c.getPlayer()));
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getSession().write(MTSCSPacket.changePetFlag(uniqueid, true, zz.getValue()));
                        used = true;
                        break;
                    }
                    break;
                }
                case 5191000:
                case 5191001:
                case 5191002:
                case 5191003:
                case 5191004: {
                    final int uniqueid = (int)slea.readLong();
                    MaplePet pet = c.getPlayer().getPet(0);
                    int slo = 0;
                    if (pet == null) {
                        break;
                    }
                    if (pet.getUniqueId() != uniqueid) {
                        pet = c.getPlayer().getPet(1);
                        slo = 1;
                        if (pet == null) {
                            break;
                        }
                        if (pet.getUniqueId() != uniqueid) {
                            pet = c.getPlayer().getPet(2);
                            slo = 2;
                            if (pet == null) {
                                break;
                            }
                            if (pet.getUniqueId() != uniqueid) {
                                break;
                            }
                        }
                    }
                    final MaplePet.PetFlag zz = MaplePet.PetFlag.getByDelId(itemId);
                    if (zz != null && zz.check(pet.getFlags())) {
                        pet.setFlags(pet.getFlags() - zz.getValue());
                        c.getSession().write(PetPacket.updatePet(pet, c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte)pet.getInventoryPosition()), true));
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getSession().write(MTSCSPacket.changePetFlag(uniqueid, false, zz.getValue()));
                        used = true;
                        break;
                    }
                    break;
                }
                case 5170000: {
                    final MaplePet pet2 = c.getPlayer().getPet(0);
                    final int slo2 = 0;
                    if (pet2 == null) {
                        break;
                    }
                    final String nName = slea.readMapleAsciiString();
                    pet2.setName(nName);
                    c.getSession().write(PetPacket.updatePet(pet2, c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte)pet2.getInventoryPosition()), true));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().getMap().broadcastMessage(MTSCSPacket.changePetName(c.getPlayer(), nName, slo2));
                    used = true;
                    break;
                }
                case 5240000:
                case 5240001:
                case 5240002:
                case 5240003:
                case 5240004:
                case 5240005:
                case 5240006:
                case 5240007:
                case 5240008:
                case 5240009:
                case 5240010:
                case 5240011:
                case 5240012:
                case 5240013:
                case 5240014:
                case 5240015:
                case 5240016:
                case 5240017:
                case 5240018:
                case 5240019:
                case 5240020:
                case 5240021:
                case 5240022:
                case 5240023:
                case 5240024:
                case 5240025:
                case 5240026:
                case 5240027:
                case 5240028: {
                    MaplePet pet2 = c.getPlayer().getPet(0);
                    if (pet2 == null) {
                        break;
                    }
                    if (!pet2.canConsume(itemId)) {
                        pet2 = c.getPlayer().getPet(1);
                        if (pet2 == null) {
                            break;
                        }
                        if (!pet2.canConsume(itemId)) {
                            pet2 = c.getPlayer().getPet(2);
                            if (pet2 == null) {
                                break;
                            }
                            if (!pet2.canConsume(itemId)) {
                                break;
                            }
                        }
                    }
                    final byte petindex = c.getPlayer().getPetIndex(pet2);
                    pet2.setFullness(100);
                    if (pet2.getCloseness() < 30000) {
                        if (pet2.getCloseness() + 100 > 30000) {
                            pet2.setCloseness(30000);
                        }
                        else {
                            pet2.setCloseness(pet2.getCloseness() + 100);
                        }
                        if (pet2.getCloseness() >= GameConstants.getClosenessNeededForLevel(pet2.getLevel() + 1)) {
                            pet2.setLevel(pet2.getLevel() + 1);
                            c.getSession().write(PetPacket.showOwnPetLevelUp(c.getPlayer().getPetIndex(pet2)));
                            c.getPlayer().getMap().broadcastMessage(PetPacket.showPetLevelUp(c.getPlayer(), petindex));
                        }
                    }
                    c.getSession().write(PetPacket.updatePet(pet2, c.getPlayer().getInventory(MapleInventoryType.CASH).getItem(pet2.getInventoryPosition()), true));
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), PetPacket.commandResponse(c.getPlayer().getId(), (byte)1, petindex, true, true), true);
                    used = true;
                    break;
                }
                case 5230000: {
                    final int itemSearch = slea.readInt();
                    final List<HiredMerchant> hms = c.getChannelServer().searchMerchant(itemSearch);
                    if (hms.size() > 0) {
                        c.getSession().write(MaplePacketCreator.getOwlSearched(itemSearch, hms));
                        used = true;
                        break;
                    }
                    c.getPlayer().dropMessage(1, "无法找到该项目.");
                    break;
                }
                case 5280001:
                case 5281000:
                case 5281001: {
                    final Rectangle bounds = new Rectangle((int)c.getPlayer().getPosition().getX(), (int)c.getPlayer().getPosition().getY(), 1, 1);
                    final MapleMist mist = new MapleMist(bounds, c.getPlayer());
                    c.getPlayer().getMap().spawnMist(mist, 10000, true);
                    c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getChatText(c.getPlayer().getId(), "Oh no, I farted!", false, 1));
                    c.getSession().write(MaplePacketCreator.enableActions());
                    used = true;
                    break;
                }
                case 5320000: {
                    final String name = slea.readMapleAsciiString();
                    final String otherName = slea.readMapleAsciiString();
                    final long unk = slea.readInt();
                    final long unk_2 = slea.readInt();
                    final int cardId = slea.readByte();
                    final short unk_3 = slea.readShort();
                    final byte unk_4 = slea.readByte();
                    final int comm = Randomizer.rand(0, 6);
                    final PredictCardFactory pcf = PredictCardFactory.getInstance();
                    final PredictCardFactory.PredictCard Card = pcf.getPredictCard(cardId);
                    final PredictCardFactory.PredictCardComment Comment = pcf.getPredictCardComment(comm);
                    if (Card == null) {
                        break;
                    }
                    if (Comment == null) {
                        break;
                    }
                    c.getPlayer().dropMessage(5, "爱情占卜成功。");
                    final int love2 = Randomizer.rand(1, Comment.score) + 5;
                    c.getSession().write(MTSCSPacket.show塔罗牌(name, otherName, love2, cardId, Comment.effectType));
                    used = true;
                    break;
                }
                case 5370000: {
                    if (c.getPlayer().getMapId() / 1000000 == 109) {
                        c.getPlayer().dropMessage(1, "请勿在活动地图使用黑板");
                        break;
                    }
                    c.getPlayer().setChalkboard(slea.readMapleAsciiString());
                    break;
                }
                case 5370001: {
                    if (c.getPlayer().getMapId() / 1000000 == 910) {
                        c.getPlayer().setChalkboard(slea.readMapleAsciiString());
                        break;
                    }
                    break;
                }
                case 5390000:
                case 5390001:
                case 5390002:
                case 5390003:
                case 5390004:
                case 5390005:
                case 5390006: {
                    if (c.getPlayer().getLevel() < 10) {
                        c.getPlayer().dropMessage(5, "必须等级10级以上才可以使用.");
                        break;
                    }
                    if (!c.getPlayer().getCheatTracker().canAvatarSmega2()) {
                        c.getPlayer().dropMessage(6, "很抱歉為了防止刷廣,所以你每10秒只能用一次.");
                        break;
                    }
                    if (c.getChannelServer().getMegaphoneMuteState()) {
                        c.getPlayer().dropMessage(5, "目前喇叭停止使用.");
                        break;
                    }
                    final String text = slea.readMapleAsciiString();
                    if (text.length() > 55) {
                        break;
                    }
                    final boolean ear2 = slea.readByte() != 0;
                    if ((c.getPlayer().isPlayer() && text.indexOf("幹") != -1) || text.indexOf("豬") != -1 || text.indexOf("笨") != -1 || text.indexOf("靠") != -1 || text.indexOf("腦包") != -1 || text.indexOf("腦") != -1 || text.indexOf("智障") != -1 || text.indexOf("白目") != -1 || text.indexOf("白吃") != -1) {
                        c.getPlayer().dropMessage("說髒話是不禮貌的，請勿說髒話。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        return;
                    }
                    if (c.getPlayer().isPlayer()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.getAvatarMega(c.getPlayer(), c.getChannel(), itemId, text, ear2).getBytes());
                        System.out.println("[玩家廣播頻道 " + c.getPlayer().getName() + "] : " + text);
                    }
                    else if (c.getPlayer().isGM()) {
                        World.Broadcast.broadcastSmega(MaplePacketCreator.getAvatarMega(c.getPlayer(), c.getChannel(), itemId, text, ear2).getBytes());
                        System.out.println("[ＧＭ廣播頻道 " + c.getPlayer().getName() + "] : " + text);
                    }
                    used = true;
                    break;
                }
                case 5450000: {
                    MapleShopFactory.getInstance().getShop(61).sendShop(c);
                    used = true;
                    break;
                }
                case 5500001:
                case 5500002: {
                    final IItem item4 = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slea.readShort());
                    final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                    final int days2 = 20;
                    if (item4 != null && !GameConstants.isAccessory(item4.getItemId()) && item4.getExpiration() > -1L && !ii.isCash(item4.getItemId()) && System.currentTimeMillis() + 8640000000L > item4.getExpiration() + days2 * 24 * 60 * 60 * 1000L) {
                        boolean change = true;
                        for (final String z : GameConstants.RESERVED) {
                            if (c.getPlayer().getName().indexOf(z) != -1 || item4.getOwner().indexOf(z) != -1) {
                                change = false;
                            }
                        }
                        if (change) {
                            item4.setExpiration(item4.getExpiration() + days2 * 24 * 60 * 60 * 1000);
                            c.getPlayer().forceReAddItem(item4, MapleInventoryType.EQUIPPED);
                            used = true;
                        }
                        else {
                            c.getPlayer().dropMessage(1, "此装备无法使用.");
                        }
                        break;
                    }
                    break;
                }
                default: {
                    switch (itemId / 10000) {
                        case 512: {
                            final MapleItemInformationProvider ii2 = MapleItemInformationProvider.getInstance();
                            final String msg = ii2.getMsg(itemId).replaceFirst("%s", c.getPlayer().getName()).replaceFirst("%s", slea.readMapleAsciiString());
                            c.getPlayer().getMap().startMapEffect(msg, itemId);
                            final int buff = ii2.getStateChangeItem(itemId);
                            if (buff != 0) {
                                for (final MapleCharacter mChar : c.getPlayer().getMap().getCharactersThreadsafe()) {
                                    ii2.getItemEffect(buff).applyTo(mChar);
                                }
                            }
                            used = true;
                            break Label_10360;
                        }
                        case 510: {
                            c.getPlayer().getMap().startJukebox(c.getPlayer().getName(), itemId);
                            used = true;
                            break Label_10360;
                        }
                        case 520: {
                            final int mesars = MapleItemInformationProvider.getInstance().getMeso(itemId);
                            if (mesars <= 0 || c.getPlayer().getMeso() >= Integer.MAX_VALUE - mesars) {
                                break Label_10360;
                            }
                            used = true;
                            if (Math.random() > 0.1) {
                                final int gainmes = Randomizer.nextInt(mesars);
                                c.getPlayer().gainMeso(gainmes, false);
                                c.getSession().write(MTSCSPacket.sendMesobagSuccess(gainmes));
                                break Label_10360;
                            }
                            c.getSession().write(MTSCSPacket.sendMesobagFailed());
                            break Label_10360;
                        }
                        case 553: {
                            UseRewardItem(slot, itemId, c, c.getPlayer());
                            break Label_10360;
                        }
                        default: {
                            System.out.println("Unhandled CS item : " + itemId);
                            System.out.println(slea.toString(true));
                            break Label_10360;
                        }
                    }
                }
            }
        }
        if (used) {
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.CASH, slot, (short)1, false, true);
        }
        c.getSession().write(MaplePacketCreator.enableActions());
        c.getSession().write(MaplePacketCreator.enableActions());
        if (cc) {
            if (!c.getPlayer().isAlive() || c.getPlayer().getEventInstance() != null || FieldLimitType.ChannelSwitch.check(c.getPlayer().getMap().getFieldLimit())) {
                c.getPlayer().dropMessage(1, "刷新人物数据失败.");
                return;
            }
            c.getPlayer().dropMessage(5, "正在刷新人数据.请等待...");
            c.getPlayer().fakeRelog();
        }
    }
    
    public static void Pickup_Player(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (c.getPlayer().getPlayerShop() != null || c.getPlayer().getConversation() > 0 || c.getPlayer().getTrade() != null) {
            return;
        }
        chr.updateTick(slea.readInt());
        slea.skip(1);
        final Point Client_Reportedpos = slea.readPos();
        if (chr == null || chr.getMap() == null) {
            return;
        }
        final MapleMapObject ob = chr.getMap().getMapObject(slea.readInt(), MapleMapObjectType.ITEM);
        if (ob == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final MapleMapItem mapitem = (MapleMapItem)ob;
        final Lock lock = mapitem.getLock();
        lock.lock();
        try {
            if (mapitem.isPickedUp()) {
                c.getSession().write(MaplePacketCreator.enableActions());
                return;
            }
            if (mapitem.getOwner() != chr.getId() && ((!mapitem.isPlayerDrop() && mapitem.getDropType() == 0) || (mapitem.isPlayerDrop() && chr.getMap().getEverlast()))) {
                c.getSession().write(MaplePacketCreator.enableActions());
                return;
            }
            if (!mapitem.isPlayerDrop() && mapitem.getDropType() == 1 && mapitem.getOwner() != chr.getId() && (chr.getParty() == null || chr.getParty().getMemberById(mapitem.getOwner()) == null)) {
                c.getSession().write(MaplePacketCreator.enableActions());
                return;
            }
            final double Distance = Client_Reportedpos.distanceSq(mapitem.getPosition());
            if (Distance > 2500.0) {
                chr.getCheatTracker().registerOffense(CheatingOffense.全图吸物_客户端, String.valueOf(Distance));
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM消息] " + chr.getName() + " ID: " + chr.getId() + " (等级 " + chr.getLevel() + ") 全屏捡物。地图ID: " + chr.getMapId() + " 范围: " + Distance).getBytes());
            }
            else if (chr.getPosition().distanceSq(mapitem.getPosition()) > 640000.0) {
                chr.getCheatTracker().registerOffense(CheatingOffense.全图吸物_服务端);
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM消息] " + chr.getName() + " ID: " + chr.getId() + " (等级 " + chr.getLevel() + ") 全屏捡物。地图ID: " + chr.getMapId() + " 范围: " + Distance).getBytes());
            }
            if (mapitem.getMeso() > 0) {
                if (chr.getParty() != null && mapitem.getOwner() != chr.getId()) {
                    final List<MapleCharacter> toGive = new LinkedList<MapleCharacter>();
                    for (final MaplePartyCharacter z : chr.getParty().getMembers()) {
                        final MapleCharacter m = chr.getMap().getCharacterById(z.getId());
                        if (m != null) {
                            toGive.add(m);
                        }
                    }
                    for (final MapleCharacter i : toGive) {
                        i.gainMeso(mapitem.getMeso() / toGive.size() + (i.getStat().hasPartyBonus ? ((int)(mapitem.getMeso() / 20.0)) : 0), true, true);
                    }
                }
                else {
                    chr.gainMeso(mapitem.getMeso(), true, true);
                }
                removeItem(chr, mapitem, ob);
            }
            else if (MapleItemInformationProvider.getInstance().isPickupBlocked(mapitem.getItem().getItemId())) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().dropMessage(5, "这个道具无法捡取.");
            }
            else if (useItem(c, mapitem.getItemId())) {
                removeItem(c.getPlayer(), mapitem, ob);
            }
            else if (MapleInventoryManipulator.checkSpace(c, mapitem.getItem().getItemId(), mapitem.getItem().getQuantity(), mapitem.getItem().getOwner())) {
                if (mapitem.getItem().getQuantity() >= 50 && GameConstants.isUpgradeScroll(mapitem.getItem().getItemId())) {
                    c.setMonitored(true);
                }
                if (MapleInventoryManipulator.addFromDrop(c, mapitem.getItem(), true, mapitem.getDropper() instanceof MapleMonster)) {
                    removeItem(chr, mapitem, ob);
                }
            }
            else {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public static void Pickup_Pet(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null) {
            return;
        }
        final byte petz = c.getPlayer().getPetIndex((int)slea.readLong());
        final MaplePet pet = chr.getPet(petz);
        slea.skip(1);
        chr.updateTick(slea.readInt());
        final Point Client_Reportedpos = slea.readPos();
        final MapleMapObject ob = chr.getMap().getMapObject(slea.readInt(), MapleMapObjectType.ITEM);
        if (ob == null || pet == null) {
            return;
        }
        final MapleMapItem mapitem = (MapleMapItem)ob;
        if (mapitem.isPickedUp()) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return;
        }
        if (mapitem.getOwner() != chr.getId() && mapitem.isPlayerDrop()) {
            return;
        }
        if (mapitem.getOwner() != chr.getId() && ((!mapitem.isPlayerDrop() && mapitem.getDropType() == 0) || (mapitem.isPlayerDrop() && chr.getMap().getEverlast()))) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (!mapitem.isPlayerDrop() && mapitem.getDropType() == 1 && mapitem.getOwner() != chr.getId() && (chr.getParty() == null || chr.getParty().getMemberById(mapitem.getOwner()) == null)) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (mapitem.isPlayerDrop() && mapitem.getDropType() == 2 && mapitem.getOwner() == chr.getId()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (mapitem.isPlayerDrop() && mapitem.getDropType() == 0 && mapitem.getOwner() == chr.getId() && mapitem.getMeso() != 0) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final double Distance = Client_Reportedpos.distanceSq(mapitem.getPosition());
        if (Distance > 10000.0 && (mapitem.getMeso() > 0 || mapitem.getItemId() != 4001025)) {
            chr.getCheatTracker().registerOffense(CheatingOffense.宠物全图吸物_客户端, String.valueOf(Distance));
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM消息] " + chr.getName() + " ID: " + chr.getId() + " (等级 " + chr.getLevel() + ") 全屏宠吸。地图ID: " + chr.getMapId() + " 范围: " + Distance).getBytes());
        }
        else if (pet.getPos().distanceSq(mapitem.getPosition()) > 640000.0) {
            chr.getCheatTracker().registerOffense(CheatingOffense.宠物全图吸物_服务端);
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM消息] " + chr.getName() + " ID: " + chr.getId() + " (等级 " + chr.getLevel() + ") 全屏宠吸。地图ID: " + chr.getMapId() + " 范围: " + Distance).getBytes());
        }
        if (mapitem.getMeso() > 0) {
            if (chr.getParty() != null && mapitem.getOwner() != chr.getId()) {
                final List<MapleCharacter> toGive = new LinkedList<MapleCharacter>();
                final int splitMeso = mapitem.getMeso() * 40 / 100;
                for (final MaplePartyCharacter z : chr.getParty().getMembers()) {
                    final MapleCharacter m = chr.getMap().getCharacterById(z.getId());
                    if (m != null && m.getId() != chr.getId()) {
                        toGive.add(m);
                    }
                }
                for (final MapleCharacter i : toGive) {
                    i.gainMeso(splitMeso / toGive.size() + (i.getStat().hasPartyBonus ? ((int)(mapitem.getMeso() / 20.0)) : 0), true);
                }
                chr.gainMeso(mapitem.getMeso() - splitMeso, true);
            }
            else {
                chr.gainMeso(mapitem.getMeso(), true);
            }
            removeItem_Pet(chr, mapitem, petz);
        }
        else if (MapleItemInformationProvider.getInstance().isPickupBlocked(mapitem.getItemId()) || mapitem.getItemId() / 10000 == 291) {
            c.getSession().write(MaplePacketCreator.enableActions());
        }
        else if (useItem(c, mapitem.getItemId())) {
            removeItem_Pet(chr, mapitem, petz);
        }
        else if (MapleInventoryManipulator.checkSpace(c, mapitem.getItemId(), mapitem.getItem().getQuantity(), mapitem.getItem().getOwner())) {
            if (mapitem.getItem().getQuantity() >= 50 && mapitem.getItemId() == 2340000) {
                c.setMonitored(true);
            }
            MapleInventoryManipulator.pet_addFromDrop(c, mapitem.getItem(), true, mapitem.getDropper() instanceof MapleMonster);
            removeItem_Pet(chr, mapitem, petz);
        }
    }
    
    public static boolean useItem(final MapleClient c, final int id) {
        if (GameConstants.isUse(id)) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final byte consumeval = ii.isConsumeOnPickup(id);
            if (consumeval > 0) {
                if (consumeval == 2) {
                    if (c.getPlayer().getParty() != null) {
                        for (final MaplePartyCharacter pc : c.getPlayer().getParty().getMembers()) {
                            final MapleCharacter chr = c.getPlayer().getMap().getCharacterById(pc.getId());
                            if (chr != null) {
                                ii.getItemEffect(id).applyTo(chr);
                            }
                        }
                    }
                    else {
                        ii.getItemEffect(id).applyTo(c.getPlayer());
                    }
                }
                else {
                    ii.getItemEffect(id).applyTo(c.getPlayer());
                }
                c.getSession().write(MaplePacketCreator.getShowItemGain(id, (short)1));
                return true;
            }
        }
        return false;
    }
    
    public static void removeItem_Pet(final MapleCharacter chr, final MapleMapItem mapitem, final int pet) {
        mapitem.setPickedUp(true);
        chr.getMap().broadcastMessage(MaplePacketCreator.removeItemFromMap(mapitem.getObjectId(), 5, chr.getId(), pet), mapitem.getPosition());
        chr.getMap().removeMapObject(mapitem);
    }
    
    private static void removeItem(final MapleCharacter chr, final MapleMapItem mapitem, final MapleMapObject ob) {
        mapitem.setPickedUp(true);
        chr.getMap().broadcastMessage(MaplePacketCreator.removeItemFromMap(mapitem.getObjectId(), 2, chr.getId()), mapitem.getPosition());
        chr.getMap().removeMapObject(ob);
    }
    
    private static void addMedalString(final MapleCharacter c, final StringBuilder sb) {
        final IItem medal = c.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-26));
        if (medal != null) {
            sb.append("<");
            sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
            sb.append("> ");
        }
    }
    
    public static void OwlMinerva(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final byte slot = (byte)slea.readShort();
        final int itemid = slea.readInt();
        final IItem toUse = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse != null && toUse.getQuantity() > 0 && toUse.getItemId() == itemid && itemid == 2310000) {
            final int itemSearch = slea.readInt();
            final List<HiredMerchant> hms = c.getChannelServer().searchMerchant(itemSearch);
            if (hms.size() > 0) {
                c.getSession().write(MaplePacketCreator.getOwlSearched(itemSearch, hms));
                MapleInventoryManipulator.removeById(c, MapleInventoryType.USE, itemid, 1, true, false);
            }
            else {
                c.getPlayer().dropMessage(1, "没有找到这个道具.");
            }
        }
        c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public static void Owl(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().haveItem(5230000, 1, true, false) || c.getPlayer().haveItem(5230001, 1, true, false)) {
            if (c.getPlayer().getMapId() >= 910000000 && c.getPlayer().getMapId() <= 910000022) {
                c.getSession().write(MaplePacketCreator.getOwlOpen());
            }
            else {
                c.getPlayer().dropMessage(5, "商店搜索器只能在自由市场使用.");
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        }
    }
    
    public static void OwlWarp(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        c.getSession().write(MaplePacketCreator.enableActions());
        if (c.getPlayer().getMapId() >= 910000000 && c.getPlayer().getMapId() <= 910000022 && c.getPlayer().getPlayerShop() == null) {
            final int id = slea.readInt();
            final int map = slea.readInt();
            if (map >= 910000001 && map <= 910000022) {
                final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(map);
                c.getPlayer().changeMap(mapp, mapp.getPortal(0));
                HiredMerchant merchant = null;
                switch (InventoryHandler.OWL_ID) {
                    case 0: {
                        final List<MapleMapObject> objects = mapp.getAllHiredMerchantsThreadsafe();
                        for (final MapleMapObject ob : objects) {
                            if (ob instanceof IMaplePlayerShop) {
                                final IMaplePlayerShop ips = (IMaplePlayerShop)ob;
                                if (!(ips instanceof HiredMerchant)) {
                                    continue;
                                }
                                final HiredMerchant merch = (HiredMerchant)ips;
                                if (merch.getOwnerId() == id) {
                                    merchant = merch;
                                    break;
                                }
                                continue;
                            }
                        }
                        break;
                    }
                    case 1: {
                        final List<MapleMapObject> objects = mapp.getAllHiredMerchantsThreadsafe();
                        for (final MapleMapObject ob : objects) {
                            if (ob instanceof IMaplePlayerShop) {
                                final IMaplePlayerShop ips = (IMaplePlayerShop)ob;
                                if (!(ips instanceof HiredMerchant)) {
                                    continue;
                                }
                                final HiredMerchant merch = (HiredMerchant)ips;
                                if (merch.getStoreId() == id) {
                                    merchant = merch;
                                    break;
                                }
                                continue;
                            }
                        }
                        break;
                    }
                    default: {
                        final MapleMapObject ob2 = mapp.getMapObject(id, MapleMapObjectType.HIRED_MERCHANT);
                        if (!(ob2 instanceof IMaplePlayerShop)) {
                            break;
                        }
                        final IMaplePlayerShop ips2 = (IMaplePlayerShop)ob2;
                        if (ips2 instanceof HiredMerchant) {
                            merchant = (HiredMerchant)ips2;
                            break;
                        }
                        break;
                    }
                }
                if (merchant != null) {
                    if (merchant.isOwner(c.getPlayer())) {
                        merchant.setOpen(false);
                        merchant.removeAllVisitors(16, 0);
                        c.getPlayer().setPlayerShop(merchant);
                        c.getSession().write(PlayerShopPacket.getHiredMerch(c.getPlayer(), merchant, false));
                    }
                    else if (!merchant.isOpen() || !merchant.isAvailable()) {
                        c.getPlayer().dropMessage(1, "主人正在整理商店物品\r\n请稍后再度光临！.");
                    }
                    else if (merchant.getFreeSlot() == -1) {
                        c.getPlayer().dropMessage(1, "店铺已达到最大人数\r\n请稍后再度光临！.");
                    }
                    else if (merchant.isInBlackList(c.getPlayer().getName())) {
                        c.getPlayer().dropMessage(1, "你被禁止进入该店铺");
                    }
                    else {
                        c.getPlayer().setPlayerShop(merchant);
                        merchant.addVisitor(c.getPlayer());
                        c.getSession().write(PlayerShopPacket.getHiredMerch(c.getPlayer(), merchant, false));
                    }
                }
                else {
                    c.getPlayer().dropMessage(1, "主人正在整理商店物品\r\n请稍后再度光临！");
                }
            }
        }
    }
    
    public static boolean UseSkillBook(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        slea.skip(4);
        final byte slot = (byte)slea.readShort();
        final int itemId = slea.readInt();
        final IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);
        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != itemId) {
            return false;
        }
        final Map<String, Integer> skilldata = MapleItemInformationProvider.getInstance().getSkillStats(toUse.getItemId());
        if (skilldata == null) {
            return false;
        }
        boolean canuse = false;
        boolean success = false;
        final int skill = 0;
        final int maxlevel = 0;
        final int SuccessRate = skilldata.get("success");
        final int ReqSkillLevel = skilldata.get("reqSkillLevel");
        final int MasterLevel = skilldata.get("masterLevel");
        byte i = 0;
        while (true) {
            final Integer CurrentLoopedSkillId = skilldata.get("skillid" + i);
            ++i;
            if (CurrentLoopedSkillId == null) {
                break;
            }
            if (Math.floor(CurrentLoopedSkillId / 10000) != chr.getJob()) {
                continue;
            }
            final ISkill CurrSkillData = SkillFactory.getSkill(CurrentLoopedSkillId);
            if (chr.getSkillLevel(CurrSkillData) >= ReqSkillLevel && chr.getMasterLevel(CurrSkillData) < MasterLevel) {
                canuse = true;
                if (Randomizer.nextInt(99) <= SuccessRate && SuccessRate != 0) {
                    success = true;
                    final ISkill skill2 = CurrSkillData;
                    chr.changeSkillLevel(skill2, chr.getSkillLevel(skill2), (byte)MasterLevel);
                }
                else {
                    success = false;
                }
                MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short)1, false);
                break;
            }
            canuse = false;
        }
        c.getSession().write(MaplePacketCreator.useSkillBook(chr, skill, maxlevel, canuse, success));
        c.getSession().write(MaplePacketCreator.enableActions());
        return canuse;
    }
    
    private static void changeFace(final MapleCharacter player, final int color) {
        if (player.getFace() % 1000 < 100) {
            player.setFace(player.getFace() + color);
        }
        else if (player.getFace() % 1000 >= 100 && player.getFace() % 1000 < 200) {
            player.setFace(player.getFace() - 100 + color);
        }
        else if (player.getFace() % 1000 >= 200 && player.getFace() % 1000 < 300) {
            player.setFace(player.getFace() - 200 + color);
        }
        else if (player.getFace() % 1000 >= 300 && player.getFace() % 1000 < 400) {
            player.setFace(player.getFace() - 300 + color);
        }
        else if (player.getFace() % 1000 >= 400 && player.getFace() % 1000 < 500) {
            player.setFace(player.getFace() - 400 + color);
        }
        else if (player.getFace() % 1000 >= 500 && player.getFace() % 1000 < 600) {
            player.setFace(player.getFace() - 500 + color);
        }
        else if (player.getFace() % 1000 >= 600 && player.getFace() % 1000 < 700) {
            player.setFace(player.getFace() - 600 + color);
        }
        else if (player.getFace() % 1000 >= 700 && player.getFace() % 1000 < 800) {
            player.setFace(player.getFace() - 700 + color);
        }
        player.updateSingleStat(MapleStat.FACE, player.getFace());
        player.equipChanged();
    }
    
    static {
        InventoryHandler.OWL_ID = 2;
    }
}
