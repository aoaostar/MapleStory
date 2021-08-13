package server;

import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleClient;
import client.PlayerStats;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.InventoryException;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.ModifyInventory;
import constants.GameConstants;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import server.maps.MapleMapObject;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.packet.MTSCSPacket;

public class MapleInventoryManipulator
{
    public static void addRing(final MapleCharacter chr, final int itemId, final int ringId, final int sn) {
        final CashItemInfo csi = CashItemFactory.getInstance().getItem(sn);
        if (csi == null) {
            return;
        }
        final IItem ring = chr.getCashInventory().toItem(csi, ringId);
        if (ring == null || ring.getUniqueId() != ringId || ring.getUniqueId() <= 0 || ring.getItemId() != itemId) {
            return;
        }
        chr.getCashInventory().addToInventory(ring);
        chr.getClient().getSession().write(MTSCSPacket.showBoughtCSItem(ring, sn, chr.getClient().getAccID()));
    }
    
    public static boolean addbyItem(final MapleClient c, final IItem item) {
        return addbyItem(c, item, false) >= 0;
    }
    
    public static short addbyItem(final MapleClient c, final IItem item, final boolean fromcs) {
        final MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        final short newSlot = c.getPlayer().getInventory(type).addItem(item);
        if (newSlot == -1) {
            if (!fromcs) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
            }
            return newSlot;
        }
        if (!fromcs) {
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, item));
        }
        c.getPlayer().havePartyQuest(item.getItemId());
        if (!fromcs && type.equals(MapleInventoryType.EQUIP)) {
            c.getPlayer().checkCopyItems();
        }
        return newSlot;
    }
    
    public static void gainItemPeriod(final MapleClient c, final int id, final short quantity, final int period) {
        gainItem(c, id, quantity, false, period, -1, "", (byte)0);
    }
    
    public static void gainItemPeriod(final MapleClient c, final int id, final short quantity, final long period, final String owner) {
        gainItem(c, id, quantity, false, period, -1, owner, (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity) {
        gainItem(c, id, quantity, false, 0L, -1, "", (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final long period, final byte Flag) {
        gainItem(c, id, quantity, false, period, -1, "", Flag);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final boolean randomStats) {
        gainItem(c, id, quantity, randomStats, 0L, -1, "", (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final boolean randomStats, final int slots) {
        gainItem(c, id, quantity, randomStats, 0L, slots, "", (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final long period) {
        gainItem(c, id, quantity, false, period, -1, "", (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final boolean randomStats, final long period, final int slots) {
        gainItem(c, id, quantity, randomStats, period, slots, "", (byte)0);
    }
    
    public static void gainItem(final MapleClient c, final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final byte Flag) {
        gainItem(id, quantity, randomStats, period, slots, owner, c, Flag);
    }
    
    public static void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg, final byte Flag) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);
            if (!checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.is飞镖道具(id) && !GameConstants.is子弹道具(id)) {
                final Equip item = (Equip)(randomStats ? ii.randomizeStats((Equip)ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0L) {
                    item.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte)(item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "你已获得称号 <" + name + ">";
                    cg.getPlayer().dropMessage(5, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                addbyItem(cg, item.copy());
            }
            else {
                addById(cg, id, quantity, (owner == null) ? "" : owner, null, period, Flag);
            }
        }
        else {
            removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
    }
    
    public static int getUniqueId(final int itemId, final MaplePet pet) {
        int uniqueid = -1;
        if (GameConstants.isPet(itemId)) {
            if (pet != null) {
                uniqueid = pet.getUniqueId();
            }
            else {
                uniqueid = MapleInventoryIdentifier.getInstance();
            }
        }
        else if (GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || MapleItemInformationProvider.getInstance().isCash(itemId)) {
            uniqueid = MapleInventoryIdentifier.getInstance();
        }
        return uniqueid;
    }
    
    public static boolean addById(final MapleClient c, final int itemId, final short quantity, final byte Flag) {
        return addById(c, itemId, quantity, null, null, 0L, Flag);
    }
    
    public static boolean addById(final MapleClient c, final int itemId, final short quantity, final String owner, final byte Flag) {
        return addById(c, itemId, quantity, owner, null, 0L, Flag);
    }
    
    public static byte addId(final MapleClient c, final int itemId, final short quantity, final String owner, final byte Flag) {
        return addId(c, itemId, quantity, owner, null, 0L, Flag);
    }
    
    public static byte addId(final MapleClient c, final int itemId, final short quantity, final String owner, final long period, final byte Flag) {
        return addId(c, itemId, quantity, owner, null, period, Flag);
    }
    
    public static boolean addById(final MapleClient c, final int itemId, final short quantity, final String owner, final MaplePet pet, final byte Flag) {
        return addById(c, itemId, quantity, owner, pet, 0L, Flag);
    }
    
    public static boolean addById(final MapleClient c, final int itemId, final short quantity, final String owner, final MaplePet pet, final long period, final byte Flag) {
        return addId(c, itemId, quantity, owner, pet, period, Flag) >= 0;
    }

    public static byte addId(MapleClient c, int itemId, short quantity, String owner, MaplePet pet, long period, byte Flag) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isPickupRestricted(itemId) && c.getPlayer().haveItem(itemId, 1, true, false)) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getSession().write(MaplePacketCreator.showItemUnavailable());
            return -1;
        }
        MapleInventoryType type = GameConstants.getInventoryType(itemId);
        int uniqueid = getUniqueId(itemId, pet);
        short newSlot = -1;
        if (!type.equals(MapleInventoryType.EQUIP)) {
            short slotMax = ii.getSlotMax(c, itemId);
            List<IItem> existing = c.getPlayer().getInventory(type).listById(itemId);
            if (!GameConstants.isRechargable(itemId)) {
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0 &&
                            i.hasNext()) {
                        Item eItem = (Item)i.next();
                        short oldQ = eItem.getQuantity();
                        if (oldQ < slotMax && (eItem.getOwner().equals(owner) || owner == null) && eItem.getExpiration() == -1L) {
                            short newQ = (short)Math.min(oldQ + quantity, slotMax);
                            quantity = (short)(quantity - newQ - oldQ);
                            eItem.setQuantity(newQ);
                            c.getSession().write(MaplePacketCreator.updateInventorySlot(type, (IItem)eItem, false));
                        }
                    }
                }
                while (quantity > 0) {
                    short newQ = (short)Math.min(quantity, slotMax);
                    if (newQ != 0) {
                        quantity = (short)(quantity - newQ);
                        Item nItem = new Item(itemId, (short)0, newQ, (byte)0, uniqueid);
                        newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                        if (newSlot == -1) {
                            c.getSession().write(MaplePacketCreator.getInventoryFull());
                            c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                            return -1;
                        }
                        if (owner != null)
                            nItem.setOwner(owner);
                        if (Flag > 0 && ii.isCash(nItem.getItemId())) {
                            byte flag = nItem.getFlag();
                            flag = (byte)(flag | ItemFlag.KARMA_EQ.getValue());
                            nItem.setFlag(flag);
                        }
                        if (period > 0L)
                            nItem.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
                        if (pet != null) {
                            nItem.setPet(pet);
                            pet.setInventoryPosition(newSlot);
                            c.getPlayer().addPet(pet);
                        }
                        c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem));
                        if (GameConstants.isRechargable(itemId) && quantity == 0)
                            break;
                        continue;
                    }
                    c.getPlayer().havePartyQuest(itemId);
                    c.getSession().write(MaplePacketCreator.enableActions());
                    return (byte)newSlot;
                }
            } else {
                Item nItem = new Item(itemId, (short)0, quantity, (byte)0, uniqueid);
                newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                if (newSlot == -1) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return -1;
                }
                if (period > 0L)
                    nItem.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
                c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem));
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        } else if (quantity == 1) {
            IItem nEquip = ii.getEquipById(itemId);
            if (owner != null)
                nEquip.setOwner(owner);
            nEquip.setUniqueId(uniqueid);
            if (Flag > 0 && ii.isCash(nEquip.getItemId())) {
                byte flag = nEquip.getFlag();
                flag = (byte)(flag | ItemFlag.KARMA_USE.getValue());
                nEquip.setFlag(flag);
            }
            if (period > 0L)
                nEquip.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
            newSlot = c.getPlayer().getInventory(type).addItem(nEquip);
            if (newSlot == -1) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                return -1;
            }
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, nEquip));
            c.getPlayer().checkCopyItems();
        } else {
            throw new InventoryException("Trying to create equip with non-one quantity");
        }
        c.getPlayer().havePartyQuest(itemId);
        return (byte)newSlot;
    }

    public static IItem addbyId_Gachapon(final MapleClient c, final int itemId, final short quantity) {
        return addbyId_Gachapon(c, itemId, quantity, null, 0L);
    }
    
    public static IItem addbyId_Gachapon(final MapleClient c, final int itemId, final short quantity, final String gmLog) {
        return addbyId_Gachapon(c, itemId, quantity, null, 0L);
    }
    
    public static IItem addbyId_Gachapon(final MapleClient c, final int itemId, short quantity, final String gmLog, final long period) {
        if (c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() == -1 || c.getPlayer().getInventory(MapleInventoryType.USE).getNextFreeSlot() == -1 || c.getPlayer().getInventory(MapleInventoryType.ETC).getNextFreeSlot() == -1 || c.getPlayer().getInventory(MapleInventoryType.SETUP).getNextFreeSlot() == -1) {
            return null;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isPickupRestricted(itemId) && c.getPlayer().haveItem(itemId, 1, true, false)) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getSession().write(MaplePacketCreator.showItemUnavailable());
            return null;
        }
        final MapleInventoryType type = GameConstants.getInventoryType(itemId);
        if (!type.equals(MapleInventoryType.EQUIP)) {
            final short slotMax = ii.getSlotMax(c, itemId);
            final List<IItem> existing = c.getPlayer().getInventory(type).listById(itemId);
            if (!GameConstants.isRechargable(itemId)) {
                IItem nItem = null;
                boolean recieved = false;
                if (existing.size() > 0) {
                    final Iterator<IItem> i = existing.iterator();
                    while (quantity > 0 && i.hasNext()) {
                        nItem = i.next();
                        final short oldQ = nItem.getQuantity();
                        if (oldQ < slotMax) {
                            recieved = true;
                            final short newQ = (short)Math.min(oldQ + quantity, slotMax);
                            quantity -= (short)(newQ - oldQ);
                            nItem.setQuantity(newQ);
                            c.getSession().write(MaplePacketCreator.updateInventorySlot(type, nItem, false));
                        }
                    }
                }
                while (quantity > 0) {
                    final short newQ2 = (short)Math.min(quantity, slotMax);
                    if (newQ2 == 0) {
                        break;
                    }
                    quantity -= newQ2;
                    nItem = new Item(itemId, (short)0, newQ2, (byte)0);
                    final short newSlot = c.getPlayer().getInventory(type).addItem(nItem);
                    if (newSlot == -1 && recieved) {
                        return nItem;
                    }
                    if (newSlot == -1) {
                        return null;
                    }
                    recieved = true;
                    if (gmLog != null) {
                        nItem.setGMLog(gmLog);
                    }
                    if (period > 0L) {
                        if (period < 1000L) {
                            nItem.setExpiration(System.currentTimeMillis() + period * 24L * 60L * 60L * 1000L);
                        }
                        else {
                            nItem.setExpiration(System.currentTimeMillis() + period);
                        }
                    }
                    c.getSession().write(MaplePacketCreator.addInventorySlot(type, nItem));
                    if (GameConstants.isRechargable(itemId) && quantity == 0) {
                        break;
                    }
                }
                if (recieved) {
                    c.getPlayer().havePartyQuest(nItem.getItemId());
                    return nItem;
                }
                return null;
            }
            else {
                final Item nItem2 = new Item(itemId, (short)0, quantity, (byte)0);
                final short newSlot2 = c.getPlayer().getInventory(type).addItem(nItem2);
                if (newSlot2 == -1) {
                    return null;
                }
                c.getSession().write(MaplePacketCreator.addInventorySlot(type, nItem2));
                c.getPlayer().havePartyQuest(nItem2.getItemId());
                return nItem2;
            }
        }
        else {
            if (quantity != 1) {
                throw new InventoryException("Trying to create equip with non-one quantity");
            }
            final IItem item = ii.randomizeStats((Equip)ii.getEquipById(itemId));
            final short newSlot3 = c.getPlayer().getInventory(type).addItem(item);
            if (newSlot3 == -1) {
                return null;
            }
            if (gmLog != null) {
                item.setGMLog(gmLog);
            }
            if (period > 0L) {
                if (period < 1000L) {
                    item.setExpiration(System.currentTimeMillis() + period * 24L * 60L * 60L * 1000L);
                }
                else {
                    item.setExpiration(System.currentTimeMillis() + period);
                }
            }
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, item, true));
            c.getPlayer().havePartyQuest(item.getItemId());
            return item;
        }
    }
    
    public static boolean addFromDrop(final MapleClient c, final IItem item, final boolean show) {
        return addFromDrop(c, item, show, false);
    }

    public static boolean addFromDrop(MapleClient c, IItem item, boolean show, boolean enhance) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isPickupRestricted(item.getItemId()) && c.getPlayer().haveItem(item.getItemId(), 1, true, false)) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getSession().write(MaplePacketCreator.showItemUnavailable());
            return false;
        }
        int before = c.getPlayer().itemQuantity(item.getItemId());
        short quantity = item.getQuantity();
        MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        if (!type.equals(MapleInventoryType.EQUIP)) {
            short slotMax = ii.getSlotMax(c, item.getItemId());
            List<IItem> existing = c.getPlayer().getInventory(type).listById(item.getItemId());
            if (!GameConstants.isRechargable(item.getItemId())) {
                if (quantity <= 0) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.showItemUnavailable());
                    return false;
                }
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0 &&
                            i.hasNext()) {
                        Item eItem = (Item)i.next();
                        short oldQ = eItem.getQuantity();
                        if (oldQ < slotMax && item.getOwner().equals(eItem.getOwner()) && item.getExpiration() == eItem.getExpiration()) {
                            short newQ = (short)Math.min(oldQ + quantity, slotMax);
                            quantity = (short)(quantity - newQ - oldQ);
                            eItem.setQuantity(newQ);
                            c.getSession().write(MaplePacketCreator.updateInventorySlot(type, (IItem)eItem, true));
                        }
                    }
                }
                while (quantity > 0) {
                    short newQ = (short)Math.min(quantity, slotMax);
                    quantity = (short)(quantity - newQ);
                    Item nItem = new Item(item.getItemId(), (short)0, newQ, item.getFlag());
                    nItem.setExpiration(item.getExpiration());
                    nItem.setOwner(item.getOwner());
                    nItem.setPet(item.getPet());
                    short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                    if (newSlot == -1) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        item.setQuantity((short)(quantity + newQ));
                        return false;
                    }
                    c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem, true));
                }
            } else {
                Item nItem = new Item(item.getItemId(), (short)0, quantity, item.getFlag());
                nItem.setExpiration(item.getExpiration());
                nItem.setOwner(item.getOwner());
                nItem.setPet(item.getPet());
                short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                if (newSlot == -1) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return false;
                }
                c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem));
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        } else if (quantity == 1) {
            if (enhance)
                item = checkEnhanced(item, c.getPlayer());
            short newSlot = c.getPlayer().getInventory(type).addItem(item);
            if (newSlot == -1) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                return false;
            }
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, item, true));
            c.getPlayer().checkCopyItems();
        } else {
            throw new RuntimeException("Trying to create equip with non-one quantity");
        }
        if (item.getQuantity() >= 50 && GameConstants.isUpgradeScroll(item.getItemId()))
            c.setMonitored(true);
        if (before == 0)
            switch (item.getItemId()) {
                case 4031875:
                    c.getPlayer().dropMessage(5, "You have gained a Powder Keg, you can give this in to Aramia of Henesys.");
                    break;
                case 4001246:
                    c.getPlayer().dropMessage(5, "You have gained a Warm Sun, you can give this in to Maple Tree Hill through @joyce.");
                    break;
                case 4001473:
                    c.getPlayer().dropMessage(5, "You have gained a Tree Decoration, you can give this in to White Christmas Hill through @joyce.");
                    break;
            }
        c.getPlayer().havePartyQuest(item.getItemId());
        if (show)
            c.getSession().write(MaplePacketCreator.getShowItemGain(item.getItemId(), item.getQuantity()));
        return true;
    }

    public static boolean 商店防止复制(final MapleClient c, final IItem item, final boolean show) {
        return 商店防止复制(c, item, show, false);
    }

    public static boolean 商店防止复制(MapleClient c, IItem item, boolean show, boolean enhance) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isPickupRestricted(item.getItemId()) && c.getPlayer().haveItem(item.getItemId(), 1, true, false)) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getSession().write(MaplePacketCreator.showItemUnavailable());
            return false;
        }
        int before = c.getPlayer().itemQuantity(item.getItemId());
        short quantity = item.getQuantity();
        MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        if (!type.equals(MapleInventoryType.EQUIP)) {
            short slotMax = ii.getSlotMax(c, item.getItemId());
            List<IItem> existing = c.getPlayer().getInventory(type).listById(item.getItemId());
            if (!GameConstants.isRechargable(item.getItemId())) {
                if (quantity <= 0) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.showItemUnavailable());
                    return false;
                }
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0 &&
                            i.hasNext()) {
                        Item eItem = (Item)i.next();
                        short oldQ = eItem.getQuantity();
                        if (oldQ < slotMax && item.getOwner().equals(eItem.getOwner()) && item.getExpiration() == eItem.getExpiration() && slotMax <= slotMax - oldQ) {
                            short newQ = (short)Math.min(oldQ + quantity, slotMax);
                            quantity = (short)(quantity - newQ - oldQ);
                            eItem.setQuantity(newQ);
                            c.getSession().write(MaplePacketCreator.updateInventorySlot(type, (IItem)eItem, true));
                        }
                    }
                }
                while (quantity > 0) {
                    short newQ = (short)Math.min(quantity, slotMax);
                    quantity = (short)(quantity - newQ);
                    Item nItem = new Item(item.getItemId(), (short)0, newQ, item.getFlag());
                    nItem.setExpiration(item.getExpiration());
                    nItem.setOwner(item.getOwner());
                    nItem.setPet(item.getPet());
                    short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                    if (newSlot == -1) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        item.setQuantity((short)(quantity + newQ));
                        return false;
                    }
                    c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem, true));
                }
            } else {
                Item nItem = new Item(item.getItemId(), (short)0, quantity, item.getFlag());
                nItem.setExpiration(item.getExpiration());
                nItem.setOwner(item.getOwner());
                nItem.setPet(item.getPet());
                short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                if (newSlot == -1) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return false;
                }
                c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem));
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        } else if (quantity == 1) {
            if (enhance)
                item = checkEnhanced(item, c.getPlayer());
            short newSlot = c.getPlayer().getInventory(type).addItem(item);
            if (newSlot == -1) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                return false;
            }
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, item, true));
        } else {
            throw new RuntimeException("Trying to create equip with non-one quantity");
        }
        if (item.getQuantity() >= 50 && GameConstants.isUpgradeScroll(item.getItemId()))
            c.setMonitored(true);
        if (before == 0)
            switch (item.getItemId()) {
                case 4031875:
                    c.getPlayer().dropMessage(5, "You have gained a Powder Keg, you can give this in to Aramia of Henesys.");
                    break;
                case 4001246:
                    c.getPlayer().dropMessage(5, "You have gained a Warm Sun, you can give this in to Maple Tree Hill through @joyce.");
                    break;
                case 4001473:
                    c.getPlayer().dropMessage(5, "You have gained a Tree Decoration, you can give this in to White Christmas Hill through @joyce.");
                    break;
            }
        c.getPlayer().havePartyQuest(item.getItemId());
        if (show)
            c.getSession().write(MaplePacketCreator.getShowItemGain(item.getItemId(), item.getQuantity()));
        return true;
    }

    public static boolean pet_addFromDrop(MapleClient c, IItem item, boolean show, boolean enhance) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isPickupRestricted(item.getItemId()) && c.getPlayer().haveItem(item.getItemId(), 1, true, false)) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getSession().write(MaplePacketCreator.showItemUnavailable());
            return false;
        }
        int before = c.getPlayer().itemQuantity(item.getItemId());
        short quantity = item.getQuantity();
        MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        if (!type.equals(MapleInventoryType.EQUIP)) {
            short slotMax = ii.getSlotMax(c, item.getItemId());
            List<IItem> existing = c.getPlayer().getInventory(type).listById(item.getItemId());
            if (!GameConstants.isRechargable(item.getItemId())) {
                if (quantity <= 0) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.showItemUnavailable());
                    return false;
                }
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0 &&
                            i.hasNext()) {
                        Item eItem = (Item)i.next();
                        short oldQ = eItem.getQuantity();
                        if (oldQ < slotMax && item.getOwner().equals(eItem.getOwner()) && item.getExpiration() == eItem.getExpiration()) {
                            short newQ = (short)Math.min(oldQ + quantity, slotMax);
                            quantity = (short)(quantity - newQ - oldQ);
                            eItem.setQuantity(newQ);
                            c.getSession().write(MaplePacketCreator.updateInventorySlot(type, (IItem)eItem, false));
                        }
                    }
                }
                while (quantity > 0) {
                    short newQ = (short)Math.min(quantity, slotMax);
                    quantity = (short)(quantity - newQ);
                    Item nItem = new Item(item.getItemId(), (short)0, newQ, item.getFlag());
                    nItem.setExpiration(item.getExpiration());
                    nItem.setOwner(item.getOwner());
                    nItem.setPet(item.getPet());
                    short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                    if (newSlot == -1) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        item.setQuantity((short)(quantity + newQ));
                        return false;
                    }
                    c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem, false));
                }
            } else {
                Item nItem = new Item(item.getItemId(), (short)0, quantity, item.getFlag());
                nItem.setExpiration(item.getExpiration());
                nItem.setOwner(item.getOwner());
                nItem.setPet(item.getPet());
                short newSlot = c.getPlayer().getInventory(type).addItem((IItem)nItem);
                if (newSlot == -1) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return false;
                }
                c.getSession().write(MaplePacketCreator.addInventorySlot(type, (IItem)nItem));
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        } else if (quantity == 1) {
            if (enhance)
                item = checkEnhanced(item, c.getPlayer());
            short newSlot = c.getPlayer().getInventory(type).addItem(item);
            if (newSlot == -1) {
                c.getSession().write(MaplePacketCreator.getInventoryFull());
                c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                return false;
            }
            c.getSession().write(MaplePacketCreator.addInventorySlot(type, item, false));
        } else {
            throw new RuntimeException("Trying to create equip with non-one quantity");
        }
        if (item.getQuantity() >= 50 && GameConstants.isUpgradeScroll(item.getItemId()))
            c.setMonitored(true);
        if (before == 0)
            switch (item.getItemId()) {
                case 4031875:
                    c.getPlayer().dropMessage(5, "You have gained a Powder Keg, you can give this in to Aramia of Henesys.");
                    break;
                case 4001246:
                    c.getPlayer().dropMessage(5, "You have gained a Warm Sun, you can give this in to Maple Tree Hill through @joyce.");
                    break;
                case 4001473:
                    c.getPlayer().dropMessage(5, "You have gained a Tree Decoration, you can give this in to White Christmas Hill through @joyce.");
                    break;
            }
        c.getPlayer().havePartyQuest(item.getItemId());
        if (show)
            c.getSession().write(MaplePacketCreator.getShowItemGain(item.getItemId(), item.getQuantity()));
        return true;
    }

    private static IItem checkEnhanced(final IItem before, final MapleCharacter chr) {
        if (before instanceof Equip) {
            final Equip eq = (Equip)before;
            if (eq.getState() == 0 && (eq.getUpgradeSlots() >= 1 || eq.getLevel() >= 1) && Randomizer.nextInt(100) > 80) {
                eq.resetPotential();
            }
        }
        return before;
    }
    
    private static int rand(final int min, final int max) {
        return Math.abs(Randomizer.rand(min, max));
    }
    
    public static boolean checkSpace(final MapleClient c, final int itemid, int quantity, final String owner) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (c.getPlayer() == null || (ii.isPickupRestricted(itemid) && c.getPlayer().haveItem(itemid, 1, true, false))) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        if (quantity <= 0 && !GameConstants.isRechargable(itemid)) {
            return false;
        }
        final MapleInventoryType type = GameConstants.getInventoryType(itemid);
        if (c.getPlayer() == null || c.getPlayer().getInventory(type) == null) {
            return false;
        }
        if (!type.equals(MapleInventoryType.EQUIP)) {
            final short slotMax = ii.getSlotMax(c, itemid);
            final List<IItem> existing = c.getPlayer().getInventory(type).listById(itemid);
            if (!GameConstants.isRechargable(itemid) && existing.size() > 0) {
                for (final IItem eItem : existing) {
                    final short oldQ = eItem.getQuantity();
                    if (oldQ < slotMax && owner != null && owner.equals(eItem.getOwner())) {
                        final short newQ = (short)Math.min(oldQ + quantity, slotMax);
                        quantity -= newQ - oldQ;
                    }
                    if (quantity <= 0) {
                        break;
                    }
                }
            }
            int numSlotsNeeded;
            if (slotMax > 0 && !GameConstants.isRechargable(itemid)) {
                numSlotsNeeded = (int)Math.ceil(quantity / (double)slotMax);
            }
            else {
                numSlotsNeeded = 1;
            }
            return !c.getPlayer().getInventory(type).isFull(numSlotsNeeded - 1);
        }
        return !c.getPlayer().getInventory(type).isFull();
    }
    
    public static void removeFromSlot(final MapleClient c, final MapleInventoryType type, final short slot, final short quantity, final boolean fromDrop) {
        removeFromSlot(c, type, slot, quantity, fromDrop, false);
    }
    
    public static void removeFromSlot(final MapleClient c, final MapleInventoryType type, final short slot, final short quantity, final boolean fromDrop, final boolean consume) {
        if (c.getPlayer() == null || c.getPlayer().getInventory(type) == null) {
            return;
        }
        final IItem item = c.getPlayer().getInventory(type).getItem(slot);
        if (item != null) {
            final boolean allowZero = consume && GameConstants.isRechargable(item.getItemId());
            c.getPlayer().getInventory(type).removeItem(slot, quantity, allowZero);
            if (item.getQuantity() == 0 && !allowZero) {
                c.getSession().write(MaplePacketCreator.clearInventoryItem(type, item.getPosition(), fromDrop));
            }
            else {
                c.getSession().write(MaplePacketCreator.updateInventorySlot(type, item, fromDrop));
            }
        }
    }
    
    public static boolean removeById(final MapleClient c, final MapleInventoryType type, final int itemId, final int quantity, final boolean fromDrop, final boolean consume) {
        int remremove = quantity;
        if (c.getPlayer() == null || c.getPlayer().getInventory(type) == null) {
            return false;
        }
        for (final IItem item : c.getPlayer().getInventory(type).listById(itemId)) {
            final int theQ = item.getQuantity();
            if (remremove <= theQ) {
                removeFromSlot(c, type, item.getPosition(), (short)remremove, fromDrop, consume);
                remremove = 0;
                break;
            }
            if (remremove <= theQ) {
                continue;
            }
            removeFromSlot(c, type, item.getPosition(), item.getQuantity(), fromDrop, consume);
            remremove -= theQ;
        }
        return remremove <= 0;
    }
    
    public static void move(final MapleClient c, final MapleInventoryType type, final short src, final short dst) {
        if (src < 0 || dst < 0 || dst > c.getPlayer().getInventory(type).getSlotLimit() || src == dst) {
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final IItem source = c.getPlayer().getInventory(type).getItem(src);
        final IItem initialTarget = c.getPlayer().getInventory(type).getItem(dst);
        if (source == null) {
            c.getPlayer().dropMessage(1, "移动道具失败，找不到移动道具的信息。");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        short olddstQ = -1;
        if (initialTarget != null) {
            olddstQ = initialTarget.getQuantity();
        }
        final short oldsrcQ = source.getQuantity();
        final short slotMax = ii.getSlotMax(c, source.getItemId());
        c.getPlayer().getInventory(type).move(src, dst, slotMax);
        if (!type.equals(MapleInventoryType.EQUIP) && initialTarget != null && initialTarget.getItemId() == source.getItemId() && initialTarget.getOwner().equals(source.getOwner()) && initialTarget.getExpiration() == source.getExpiration() && !GameConstants.isRechargable(source.getItemId()) && !type.equals(MapleInventoryType.CASH)) {
            if (olddstQ + oldsrcQ > slotMax) {
                c.getSession().write(MaplePacketCreator.moveAndMergeWithRestInventoryItem(type, src, dst, (short)(olddstQ + oldsrcQ - slotMax), slotMax));
            }
            else {
                c.getSession().write(MaplePacketCreator.moveAndMergeInventoryItem(type, src, dst, ((Item)c.getPlayer().getInventory(type).getItem(dst)).getQuantity()));
            }
        }
        else {
            c.getSession().write(MaplePacketCreator.moveInventoryItem(type, src, dst));
        }
    }
    
    public static void equip(final MapleClient c, final short src, final short dst) {
        boolean itemChanged = false;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleCharacter chr = c.getPlayer();
        if (chr == null) {
            return;
        }
        final PlayerStats statst = c.getPlayer().getStat();
        Equip source = (Equip)chr.getInventory(MapleInventoryType.EQUIP).getItem(src);
        Equip target = (Equip)chr.getInventory(MapleInventoryType.EQUIPPED).getItem(dst);
        if (source == null || source.getDurability() == 0) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (MapleItemInformationProvider.getInstance().isUntradeableOnEquip(source.getItemId())) {
            source.setFlag((byte)ItemFlag.UNTRADEABLE.getValue());
            itemChanged = true;
        }
        final Map<String, Integer> stats = ii.getEquipStats(source.getItemId());
        if (ii.isCash(source.getItemId()) && source.getUniqueId() <= 0) {
            source.setUniqueId(1);
            c.getSession().write(MaplePacketCreator.updateSpecialItemUse_(source, GameConstants.getInventoryType(source.getItemId()).getType()));
        }
        if (dst < -999 && !GameConstants.isEvanDragonItem(source.getItemId()) && !GameConstants.is豆豆装备(source.getItemId())) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (dst >= -999 && dst < -99 && stats.get("cash") == 0 && !GameConstants.is豆豆装备(source.getItemId()) && !GameConstants.isEffectRing(source.getItemId())) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (!ii.canEquip(stats, source.getItemId(), chr.getLevel(), chr.getJob(), chr.getFame(), statst.getTotalStr(), statst.getTotalDex(), statst.getTotalLuk(), statst.getTotalInt(), c.getPlayer().getStat().levelBonus)) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (GameConstants.isWeapon(source.getItemId()) && dst != -10 && dst != -11) {
            AutobanManager.getInstance().autoban(c, "Equipment hack, itemid " + source.getItemId() + " to slot " + dst);
            return;
        }
        if (!ii.isCash(source.getItemId()) && !GameConstants.isMountItemAvailable(source.getItemId(), c.getPlayer().getJob())) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        switch (dst) {
            case -6: {
                final IItem top = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-5));
                if (top == null || !GameConstants.isOverall(top.getItemId())) {
                    break;
                }
                if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return;
                }
                unequip(c, (short)(-5), chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                break;
            }
            case -5: {
                final IItem top = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-5));
                final IItem bottom = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-6));
                if (top != null && GameConstants.isOverall(source.getItemId())) {
                    if (chr.getInventory(MapleInventoryType.EQUIP).isFull((bottom != null && GameConstants.isOverall(source.getItemId())) ? 1 : 0)) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (short)(-5), chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                }
                if (bottom == null || !GameConstants.isOverall(source.getItemId())) {
                    break;
                }
                if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return;
                }
                unequip(c, (short)(-6), chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                break;
            }
            case -10: {
                final IItem weapon = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
                if (GameConstants.isKatara(source.getItemId())) {
                    if ((chr.getJob() != 900 && (chr.getJob() < 430 || chr.getJob() > 434)) || weapon == null || !GameConstants.isDagger(weapon.getItemId())) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        return;
                    }
                    break;
                }
                else {
                    if (weapon == null || !GameConstants.isTwoHanded(weapon.getItemId())) {
                        break;
                    }
                    if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                        c.getSession().write(MaplePacketCreator.getInventoryFull());
                        c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (short)(-11), chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                    break;
                }
            }
            case -11: {
                final IItem shield = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-10));
                if (shield == null || !GameConstants.isTwoHanded(source.getItemId())) {
                    break;
                }
                if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                    c.getSession().write(MaplePacketCreator.getInventoryFull());
                    c.getSession().write(MaplePacketCreator.getShowInventoryFull());
                    return;
                }
                unequip(c, (short)(-10), chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                break;
            }
        }
        source = (Equip)chr.getInventory(MapleInventoryType.EQUIP).getItem(src);
        target = (Equip)chr.getInventory(MapleInventoryType.EQUIPPED).getItem(dst);
        if (source == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        byte flag = source.getFlag();
        if (stats.get("equipTradeBlock") == 1) {
            if (!ItemFlag.UNTRADEABLE.check(flag)) {
                flag |= (byte)ItemFlag.UNTRADEABLE.getValue();
                source.setFlag(flag);
                c.getSession().write(MaplePacketCreator.updateSpecialItemUse_(source, GameConstants.getInventoryType(source.getItemId()).getType()));
            }
        }
        else if (ItemFlag.KARMA_EQ.check(flag)) {
            source.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
            c.getSession().write(MaplePacketCreator.updateSpecialItemUse(source, GameConstants.getInventoryType(source.getItemId()).getType()));
        }
        else if (ItemFlag.KARMA_USE.check(flag)) {
            source.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
            c.getSession().write(MaplePacketCreator.updateSpecialItemUse(source, GameConstants.getInventoryType(source.getItemId()).getType()));
        }
        chr.getInventory(MapleInventoryType.EQUIP).removeSlot(src);
        if (target != null) {
            chr.getInventory(MapleInventoryType.EQUIPPED).removeSlot(dst);
        }
        final List<ModifyInventory> mods = new ArrayList<ModifyInventory>();
        if (itemChanged) {
            mods.add(new ModifyInventory(3, source));
            mods.add(new ModifyInventory(0, source.copy()));
        }
        source.setPosition(dst);
        chr.getInventory(MapleInventoryType.EQUIPPED).addFromDB(source);
        if (target != null) {
            target.setPosition(src);
            chr.getInventory(MapleInventoryType.EQUIP).addFromDB(target);
        }
        if (GameConstants.isWeapon(source.getItemId())) {
            if (chr.getBuffedValue(MapleBuffStat.攻击加速) != null) {
                chr.cancelBuffStats(MapleBuffStat.攻击加速);
            }
            if (chr.getBuffedValue(MapleBuffStat.暗器伤人) != null) {
                chr.cancelBuffStats(MapleBuffStat.暗器伤人);
            }
            if (chr.getBuffedValue(MapleBuffStat.无形箭弩) != null) {
                chr.cancelBuffStats(MapleBuffStat.无形箭弩);
            }
            if (chr.getBuffedValue(MapleBuffStat.属性攻击) != null) {
                chr.cancelBuffStats(MapleBuffStat.属性攻击);
            }
            if (chr.getBuffedValue(MapleBuffStat.LIGHTNING_CHARGE) != null) {
                chr.cancelBuffStats(MapleBuffStat.LIGHTNING_CHARGE);
            }
        }
        if (source.getItemId() == 1122017) {
            c.getPlayer().dropMessage(5, "精灵吊坠已佩戴。计时开始。1小时后经验增加百分之10.");
            chr.startFairySchedule(true, true);
        }
        mods.add(new ModifyInventory(2, source, src));
        c.getSession().write(MaplePacketCreator.moveInventoryItem(MapleInventoryType.EQUIP, src, dst, (short)2));
        chr.equipChanged();
        c.getSession().write(MaplePacketCreator.updateSpecialItemUse_(source, GameConstants.getInventoryType(source.getItemId()).getType()));
    }
    
    public static void unequip(final MapleClient c, final short src, final short dst) {
        final Equip source = (Equip)c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(src);
        final Equip target = (Equip)c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(dst);
        if (dst < 0 || source == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (target != null && src <= 0) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            return;
        }
        c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeSlot(src);
        if (target != null) {
            c.getPlayer().getInventory(MapleInventoryType.EQUIP).removeSlot(dst);
        }
        source.setPosition(dst);
        c.getPlayer().getInventory(MapleInventoryType.EQUIP).addFromDB(source);
        if (target != null) {
            target.setPosition(src);
            c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(target);
        }
        if (GameConstants.isWeapon(source.getItemId())) {
            if (c.getPlayer().getBuffedValue(MapleBuffStat.攻击加速) != null) {
                c.getPlayer().cancelBuffStats(MapleBuffStat.攻击加速);
            }
            if (c.getPlayer().getBuffedValue(MapleBuffStat.暗器伤人) != null) {
                c.getPlayer().cancelBuffStats(MapleBuffStat.暗器伤人);
            }
            if (c.getPlayer().getBuffedValue(MapleBuffStat.无形箭弩) != null) {
                c.getPlayer().cancelBuffStats(MapleBuffStat.无形箭弩);
            }
            if (c.getPlayer().getBuffedValue(MapleBuffStat.属性攻击) != null) {
                c.getPlayer().cancelBuffStats(MapleBuffStat.属性攻击);
            }
        }
        if (source.getItemId() == 1122017) {
            c.getPlayer().dropMessage(5, "精灵吊坠已脱下。计时结束。");
            c.getPlayer().cancelFairySchedule(true);
        }
        c.getSession().write(MaplePacketCreator.moveInventoryItem(MapleInventoryType.EQUIP, src, dst, (short)1));
        c.getPlayer().equipChanged();
    }
    
    public static boolean drop(final MapleClient c, final MapleInventoryType type, final short src, final short quantity) {
        return drop(c, type, src, quantity, false);
    }
    
    public static boolean drop(final MapleClient c, MapleInventoryType type, final short src, final short quantity, final boolean npcInduced) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (src < 0) {
            type = MapleInventoryType.EQUIPPED;
        }
        if (c.getPlayer() == null) {
            return false;
        }
        final IItem source = c.getPlayer().getInventory(type).getItem(src);
        if (source == null || (!npcInduced && GameConstants.isPet(source.getItemId()))) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        if (ii.isCash(source.getItemId()) || source.getExpiration() > 0L) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        final byte flag = source.getFlag();
        final int id = source.getItemId();
        if (GameConstants.isRechargable(id) && source.getQuantity() == 0) {
            source.setQuantity((short)1);
        }
        if (quantity > source.getQuantity()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        if (ItemFlag.LOCK.check(flag) || (quantity != 1 && type == MapleInventoryType.EQUIP)) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        c.getPlayer().setCurrenttime(System.currentTimeMillis());
        if (c.getPlayer().getCurrenttime() - c.getPlayer().getLasttime() < 1000L) {
            c.getPlayer().dropMessage(1, "<温馨提醒>：请您慢点使用.");
            c.getSession().write(MaplePacketCreator.enableActions());
            return false;
        }
        c.getPlayer().setLasttime(System.currentTimeMillis());
        final Point dropPos = new Point(c.getPlayer().getPosition());
        c.getPlayer().getCheatTracker().checkDrop();
        if (quantity < source.getQuantity() && !GameConstants.isRechargable(source.getItemId())) {
            final IItem target = source.copy();
            target.setQuantity(quantity);
            source.setQuantity((short)(source.getQuantity() - quantity));
            c.getSession().write(MaplePacketCreator.dropInventoryItemUpdate(type, source));
            if (ii.isDropRestricted(target.getItemId()) || ii.isAccountShared(target.getItemId())) {
                if (ItemFlag.KARMA_EQ.check(flag)) {
                    target.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
                }
                else if (ItemFlag.KARMA_USE.check(flag)) {
                    target.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
                }
                else {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos);
                }
            }
            else if (GameConstants.isPet(source.getItemId()) || ItemFlag.UNTRADEABLE.check(flag)) {
                c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos);
            }
            else {
                c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
            }
        }
        else {
            c.getPlayer().getInventory(type).removeSlot(src);
            c.getSession().write(MaplePacketCreator.dropInventoryItem((src < 0) ? MapleInventoryType.EQUIP : type, src));
            if (src < 0) {
                c.getPlayer().equipChanged();
            }
            if (ii.isDropRestricted(source.getItemId()) || ii.isAccountShared(source.getItemId())) {
                if (ItemFlag.KARMA_EQ.check(flag)) {
                    source.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
                }
                else if (ItemFlag.KARMA_USE.check(flag)) {
                    source.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
                }
                else {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos);
                }
            }
            else if (GameConstants.isPet(source.getItemId()) || ItemFlag.UNTRADEABLE.check(flag)) {
                c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos);
            }
            else {
                c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
            }
        }
        return true;
    }
    
    public static void removeAllByEquipOnlyId(final MapleClient c, final int equipOnlyId) {
        if (c.getPlayer() == null) {
            return;
        }
        boolean locked = false;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<IItem> copyEquipItems = c.getPlayer().getInventory(MapleInventoryType.EQUIP).listByEquipOnlyId(equipOnlyId);
        for (final IItem item : copyEquipItems) {
            if (item != null) {
                if (!locked) {
                    short flag = item.getFlag();
                    flag |= (short)ItemFlag.LOCK.getValue();
                    flag |= (short)ItemFlag.UNTRADEABLE.getValue();
                    item.setFlag((byte)flag);
                    item.setOwner("复制装备");
                    c.getPlayer().forceUpdateItem(item);
                    c.getPlayer().dropMessage(5, "在背包中发现复制装备[" + ii.getName(item.getItemId()) + "]已经将其锁定。");
                    final String msgtext = "玩家 " + c.getPlayer().getName() + " ID: " + c.getPlayer().getId() + " (等级 " + c.getPlayer().getLevel() + ") 地图: " + c.getPlayer().getMapId() + " 在玩家背包中发现复制装备[" + ii.getName(item.getItemId()) + "]已经将其锁定。";
                    FileoutputUtil.log("log/复制装备.txt", msgtext + " 道具唯一ID: " + item.getEquipOnlyId());
                    locked = true;
                }
                else {
                    removeFromSlot(c, MapleInventoryType.EQUIP, item.getPosition(), item.getQuantity(), true, false);
                    c.getPlayer().dropMessage(5, "在背包中发现复制装备[" + ii.getName(item.getItemId()) + "]已经将其删除。");
                }
            }
        }
        locked = false;
        final List<IItem> copyEquipedItems = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).listByEquipOnlyId(equipOnlyId);
        for (final IItem item2 : copyEquipedItems) {
            if (item2 != null) {
                if (!locked) {
                    short flag2 = item2.getFlag();
                    flag2 |= (short)ItemFlag.LOCK.getValue();
                    flag2 |= (short)ItemFlag.UNTRADEABLE.getValue();
                    item2.setFlag((byte)flag2);
                    item2.setOwner("复制装备");
                    c.getPlayer().forceUpdateItem(item2);
                    c.getPlayer().dropMessage(5, "在穿戴中发现复制装备[" + ii.getName(item2.getItemId()) + "]已经将其锁定。");
                    final String msgtext2 = "玩家 " + c.getPlayer().getName() + " ID: " + c.getPlayer().getId() + " (等级 " + c.getPlayer().getLevel() + ") 地图: " + c.getPlayer().getMapId() + " 在玩家穿戴中发现复制装备[" + ii.getName(item2.getItemId()) + "]已经将其锁定。";
                    FileoutputUtil.log("log/复制装备.txt", msgtext2 + " 道具唯一ID: " + item2.getEquipOnlyId());
                    locked = true;
                }
                else {
                    removeFromSlot(c, MapleInventoryType.EQUIPPED, item2.getPosition(), item2.getQuantity(), true, false);
                    c.getPlayer().dropMessage(5, "在穿戴中发现复制装备[" + ii.getName(item2.getItemId()) + "]已经将其删除。");
                    c.getPlayer().equipChanged();
                }
            }
        }
        locked = false;
        final List<IItem> copyUseItems = c.getPlayer().getInventory(MapleInventoryType.USE).listByEquipOnlyId(equipOnlyId);
        for (final IItem item3 : copyUseItems) {
            if (item3 != null) {
                if (!locked) {
                    short flag3 = item3.getFlag();
                    flag3 |= (short)ItemFlag.LOCK.getValue();
                    flag3 |= (short)ItemFlag.UNTRADEABLE.getValue();
                    item3.setFlag((byte)flag3);
                    item3.setOwner("复制道具");
                    c.getPlayer().forceUpdateItem(item3);
                    c.getPlayer().dropMessage(5, "在消耗中发现复制道具[" + ii.getName(item3.getItemId()) + "]已经将其锁定。");
                    final String msgtext3 = "玩家 " + c.getPlayer().getName() + " ID: " + c.getPlayer().getId() + " (等级 " + c.getPlayer().getLevel() + ") 地图: " + c.getPlayer().getMapId() + " 在玩家消耗中发现复制道具[" + ii.getName(item3.getItemId()) + "]已经将其锁定。";
                    FileoutputUtil.log("Logs/复制装备.txt", msgtext3 + " 道具唯一ID: " + item3.getEquipOnlyId());
                    locked = true;
                }
                else {
                    removeFromSlot(c, MapleInventoryType.USE, item3.getPosition(), item3.getQuantity(), true, false);
                    c.getPlayer().dropMessage(5, "在消耗中发现复制道具[" + ii.getName(item3.getItemId()) + "]已经将其删除。");
                    c.getPlayer().equipChanged();
                }
            }
        }
        locked = false;
        final List<IItem> copyEtcItems = c.getPlayer().getInventory(MapleInventoryType.ETC).listByEquipOnlyId(equipOnlyId);
        for (final IItem item4 : copyEtcItems) {
            if (item4 != null) {
                if (!locked) {
                    short flag4 = item4.getFlag();
                    flag4 |= (short)ItemFlag.LOCK.getValue();
                    flag4 |= (short)ItemFlag.UNTRADEABLE.getValue();
                    item4.setFlag((byte)flag4);
                    item4.setOwner("复制道具");
                    c.getPlayer().forceUpdateItem(item4);
                    c.getPlayer().dropMessage(5, "在其他中发现复制道具[" + ii.getName(item4.getItemId()) + "]已经将其锁定。");
                    final String msgtext4 = "玩家 " + c.getPlayer().getName() + " ID: " + c.getPlayer().getId() + " (等级 " + c.getPlayer().getLevel() + ") 地图: " + c.getPlayer().getMapId() + " 在玩家其他中发现复制道具[" + ii.getName(item4.getItemId()) + "]已经将其锁定。";
                    FileoutputUtil.log("Logs/复制装备.txt", msgtext4 + " 道具唯一ID: " + item4.getEquipOnlyId());
                    locked = true;
                }
                else {
                    removeFromSlot(c, MapleInventoryType.ETC, item4.getPosition(), item4.getQuantity(), true, false);
                    c.getPlayer().dropMessage(5, "在其他中发现复制道具[" + ii.getName(item4.getItemId()) + "]已经将其删除。");
                    c.getPlayer().equipChanged();
                }
            }
        }
        locked = false;
        final List<IItem> copyCashItems = c.getPlayer().getInventory(MapleInventoryType.CASH).listByEquipOnlyId(equipOnlyId);
        for (final IItem item5 : copyCashItems) {
            if (item5 != null) {
                if (!locked) {
                    short flag5 = item5.getFlag();
                    flag5 |= (short)ItemFlag.LOCK.getValue();
                    flag5 |= (short)ItemFlag.UNTRADEABLE.getValue();
                    item5.setFlag((byte)flag5);
                    item5.setOwner("复制道具");
                    c.getPlayer().forceUpdateItem(item5);
                    c.getPlayer().dropMessage(5, "在现金道具中发现复制道具[" + ii.getName(item5.getItemId()) + "]已经将其锁定。");
                    final String msgtext5 = "玩家 " + c.getPlayer().getName() + " ID: " + c.getPlayer().getId() + " (等级 " + c.getPlayer().getLevel() + ") 地图: " + c.getPlayer().getMapId() + " 在玩家现金道具中发现复制道具[" + ii.getName(item5.getItemId()) + "]已经将其锁定。";
                    FileoutputUtil.log("Logs/复制装备.txt", msgtext5 + " 道具唯一ID: " + item5.getEquipOnlyId());
                    locked = true;
                }
                else {
                    removeFromSlot(c, MapleInventoryType.CASH, item5.getPosition(), item5.getQuantity(), true, false);
                    c.getPlayer().dropMessage(5, "在现金道具中发现复制道具[" + ii.getName(item5.getItemId()) + "]已经将其删除。");
                    c.getPlayer().equipChanged();
                }
            }
        }
    }
}
