package client.inventory;

import client.MapleCharacter;
import constants.GameConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import server.MapleItemInformationProvider;
import tools.MaplePacketCreator;

public class MapleInventory implements Iterable<IItem>, Serializable {
    private final Map<Short, IItem> inventory;

    private byte slotLimit = 0;

    private final MapleInventoryType type;

    public MapleInventory(MapleInventoryType type, byte slotLimit) {
        this.inventory = new LinkedHashMap<>();
        this.slotLimit = slotLimit;
        this.type = type;
    }

    public void addSlot(byte slot) {
        this.slotLimit = (byte)(this.slotLimit + slot);
        if (this.slotLimit > 96)
            this.slotLimit = 96;
    }

    public byte getSlotLimit() {
        return this.slotLimit;
    }

    public void setSlotLimit(byte slot) {
        if (slot > 96)
            slot = 96;
        this.slotLimit = slot;
    }

    public IItem findById(int itemId) {
        for (IItem item : this.inventory.values()) {
            if (item.getItemId() == itemId)
                return item;
        }
        return null;
    }

    public IItem findByUniqueId(int itemId) {
        for (IItem item : this.inventory.values()) {
            if (item.getUniqueId() == itemId)
                return item;
        }
        return null;
    }

    public int countById(int itemId) {
        int possesed = 0;
        for (IItem item : this.inventory.values()) {
            if (item.getItemId() == itemId)
                possesed += item.getQuantity();
        }
        return possesed;
    }

    public List<IItem> listById(int itemId) {
        List<IItem> ret = new ArrayList<>();
        for (IItem item : this.inventory.values()) {
            if (item.getItemId() == itemId)
                ret.add(item);
        }
        if (ret.size() > 1)
            Collections.sort(ret);
        return ret;
    }

    public Collection<IItem> list() {
        return this.inventory.values();
    }

    public short addItem(IItem item) {
        short slotId = getNextFreeSlot();
        if (slotId < 0)
            return -1;
        this.inventory.put(Short.valueOf(slotId), item);
        item.setPosition(slotId);
        return slotId;
    }

    public void addFromDB(IItem item) {
        if (item.getPosition() < 0 && !this.type.equals(MapleInventoryType.EQUIPPED))
            return;
        this.inventory.put(Short.valueOf(item.getPosition()), item);
    }

    public void move(short sSlot, short dSlot, short slotMax) {
        if (dSlot > this.slotLimit)
            return;
        Item source = (Item)this.inventory.get(Short.valueOf(sSlot));
        Item target = (Item)this.inventory.get(Short.valueOf(dSlot));
        if (source == null)
            throw new InventoryException("Trying to move empty slot");
        if (target == null) {
            source.setPosition(dSlot);
            this.inventory.put(Short.valueOf(dSlot), source);
            this.inventory.remove(Short.valueOf(sSlot));
        } else if (target.getItemId() == source.getItemId() && !GameConstants.isThrowingStar(source.getItemId()) && !GameConstants.isBullet(source.getItemId()) && target.getOwner().equals(source.getOwner()) && target.getExpiration() == source.getExpiration()) {
            if (this.type.getType() == MapleInventoryType.EQUIP.getType() || this.type.getType() == MapleInventoryType.CASH.getType()) {
                swap(target, source);
            } else if (source.getQuantity() + target.getQuantity() > slotMax) {
                source.setQuantity((short)(source.getQuantity() + target.getQuantity() - slotMax));
                target.setQuantity(slotMax);
            } else {
                target.setQuantity((short)(source.getQuantity() + target.getQuantity()));
                this.inventory.remove(Short.valueOf(sSlot));
            }
        } else {
            swap(target, source);
        }
    }

    private void swap(IItem source, IItem target) {
        this.inventory.remove(Short.valueOf(source.getPosition()));
        this.inventory.remove(Short.valueOf(target.getPosition()));
        short swapPos = source.getPosition();
        source.setPosition(target.getPosition());
        target.setPosition(swapPos);
        this.inventory.put(Short.valueOf(source.getPosition()), source);
        this.inventory.put(Short.valueOf(target.getPosition()), target);
    }

    public IItem getItem(short slot) {
        return this.inventory.get(Short.valueOf(slot));
    }

    public void removeItem(short slot) {
        removeItem(slot, (short)1, false);
    }

    public void removeItem(short slot, short quantity, boolean allowZero) {
        removeItem(slot, quantity, allowZero, null);
    }

    public void removeItem(short slot, short quantity, boolean allowZero, MapleCharacter chr) {
        IItem item = this.inventory.get(Short.valueOf(slot));
        if (item == null)
            return;
        item.setQuantity((short)(item.getQuantity() - quantity));
        if (item.getQuantity() < 0)
            item.setQuantity((short)0);
        if (item.getQuantity() == 0 && !allowZero)
            removeSlot(slot);
        if (chr != null) {
            chr.getClient().sendPacket(MaplePacketCreator.modifyInventory(false, new ModifyInventory(ModifyInventory.Types.REMOVE, item)));
            chr.dropMessage(5, "期限道具[" + MapleItemInformationProvider.getInstance().getName(item.getItemId()) + "]已经过期");
        }
    }

    public void removeSlot(short slot) {
        this.inventory.remove(Short.valueOf(slot));
    }

    public boolean isFull() {
        return (this.inventory.size() >= this.slotLimit);
    }

    public boolean isFull(int margin) {
        return (this.inventory.size() + margin >= this.slotLimit);
    }

    public short getNextFreeSlot() {
        if (isFull())
            return -1;
        for (short i = 1; i <= this.slotLimit; i = (short)(i + 1)) {
            if (!this.inventory.keySet().contains(Short.valueOf(i)))
                return i;
        }
        return -1;
    }

    public short getNumFreeSlot() {
        if (isFull())
            return 0;
        byte free = 0;
        for (short i = 1; i <= this.slotLimit; i = (short)(i + 1)) {
            if (!this.inventory.keySet().contains(Short.valueOf(i)))
                free = (byte)(free + 1);
        }
        return (short)free;
    }

    public MapleInventoryType getType() {
        return this.type;
    }

    public Iterator<IItem> iterator() {
        return Collections.<IItem>unmodifiableCollection(this.inventory.values()).iterator();
    }

    public List<IItem> listByEquipOnlyId(int equipOnlyId) {
        List<IItem> ret = new ArrayList<>();
        for (IItem item : this.inventory.values()) {
            if (item.getEquipOnlyId() > 0 && item.getEquipOnlyId() == equipOnlyId)
                ret.add(item);
        }
        if (ret.size() > 1)
            Collections.sort(ret);
        return ret;
    }

    public IItem findByEquipOnlyId(long onlyId, int itemId) {
        for (IItem item : this.inventory.values()) {
            if (item.getEquipOnlyId() == onlyId && item.getItemId() == itemId)
                return item;
        }
        return null;
    }
}
