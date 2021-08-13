package client.inventory;

import constants.GameConstants;

public class ModifyInventory {
    private int mode;

    private IItem item;

    private short oldPos;

    public ModifyInventory(int mode, IItem item) {
        this.mode = mode;
        this.item = item.copy();
    }

    public ModifyInventory(int mode, IItem item, short oldPos) {
        this.mode = mode;
        this.item = item.copy();
        this.oldPos = oldPos;
    }

    public int getMode() {
        return this.mode;
    }

    public int getInventoryType() {
        return GameConstants.getInventoryType(this.item.getItemId()).getType();
    }

    public short getPosition() {
        return this.item.getPosition();
    }

    public short getOldPosition() {
        return this.oldPos;
    }

    public short getQuantity() {
        return this.item.getQuantity();
    }

    public IItem getItem() {
        return this.item;
    }

    public void clear() {
        this.item = null;
    }

    public static class Types {
        public static int ADD = 0;

        public static int UPDATE = 1;

        public static int MOVE = 2;

        public static int REMOVE = 3;
    }
}
