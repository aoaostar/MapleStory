package server.shops;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import java.util.ArrayList;
import java.util.List;
import server.MapleInventoryManipulator;
import server.maps.MapleMapObject;
import tools.packet.PlayerShopPacket;

public class MaplePlayerShop extends AbstractPlayerStore
{
    private int boughtnumber;
    private final List<String> bannedList;
    
    public MaplePlayerShop(final MapleCharacter owner, final int itemId, final String desc) {
        super(owner, itemId, desc, "", 3);
        this.boughtnumber = 0;
        this.bannedList = new ArrayList<String>();
    }
    
    @Override
    public void buy(final MapleClient c, final int item, final short quantity) {
        final MaplePlayerShopItem pItem = this.items.get(item);
        if (pItem.bundles > 0) {
            final IItem newItem = pItem.item.copy();
            newItem.setQuantity((short)(quantity * newItem.getQuantity()));
            final byte flag = newItem.getFlag();
            if (ItemFlag.KARMA_EQ.check(flag)) {
                newItem.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
            }
            else if (ItemFlag.KARMA_USE.check(flag)) {
                newItem.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
            }
            final int gainmeso = pItem.price * quantity;
            if (c.getPlayer().getMeso() >= gainmeso) {
                if (this.getMCOwner().getMeso() + gainmeso > 0 && MapleInventoryManipulator.checkSpace(c, newItem.getItemId(), newItem.getQuantity(), newItem.getOwner()) && MapleInventoryManipulator.addFromDrop(c, newItem, false)) {
                    final MaplePlayerShopItem maplePlayerShopItem = pItem;
                    maplePlayerShopItem.bundles -= quantity;
                    this.bought.add(new BoughtItem(newItem.getItemId(), quantity, gainmeso, c.getPlayer().getName()));
                    c.getPlayer().gainMeso(-gainmeso, false);
                    this.getMCOwner().gainMeso(gainmeso, false);
                    if (pItem.bundles <= 0) {
                        ++this.boughtnumber;
                        if (this.boughtnumber == this.items.size()) {
                            this.closeShop(true, true);
                            return;
                        }
                    }
                }
                else {
                    c.getPlayer().dropMessage(1, "你的背包已满.");
                }
            }
            else {
                c.getPlayer().dropMessage(1, "You do not have enough mesos.");
            }
            this.getMCOwner().getClient().getSession().write(PlayerShopPacket.shopItemUpdate(this));
        }
    }
    
    @Override
    public byte getShopType() {
        return 2;
    }
    
    @Override
    public void closeShop(final boolean saveItems, final boolean remove) {
        final MapleCharacter owner = this.getMCOwner();
        this.removeAllVisitors(10, 1);
        this.getMap().removeMapObject(this);
        for (final MaplePlayerShopItem items : this.getItems()) {
            if (items.bundles > 0) {
                final IItem newItem = items.item.copy();
                newItem.setQuantity((short)(items.bundles * newItem.getQuantity()));
                if (!MapleInventoryManipulator.addFromDrop(owner.getClient(), newItem, false)) {
                    this.saveItems();
                    break;
                }
                items.bundles = 0;
            }
        }
        owner.setPlayerShop(null);
        this.update();
        this.getMCOwner().getClient().getSession().write(PlayerShopPacket.shopErrorMessage(10, 1));
    }
    
    public void banPlayer(final String name) {
        if (!this.bannedList.contains(name)) {
            this.bannedList.add(name);
        }
        for (int i = 0; i < 3; ++i) {
            final MapleCharacter chr = this.getVisitor(i);
            if (chr.getName().equals(name)) {
                chr.getClient().getSession().write(PlayerShopPacket.shopErrorMessage(5, 1));
                chr.setPlayerShop(null);
                this.removeVisitor(chr);
            }
        }
    }
    
    public boolean isBanned(final String name) {
        return this.bannedList.contains(name);
    }
}
