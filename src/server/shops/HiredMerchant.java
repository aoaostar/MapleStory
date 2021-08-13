package server.shops;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import constants.GameConstants;
import handling.channel.ChannelServer;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.Timer;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.MaplePacketCreator;
import tools.packet.PlayerShopPacket;

public class HiredMerchant extends AbstractPlayerStore
{
    public ScheduledFuture<?> schedule;
    private final List<String> blacklist;
    private int storeid;
    private final long start;
    
    public HiredMerchant(final MapleCharacter owner, final int itemId, final String desc) {
        super(owner, itemId, desc, "", 3);
        this.start = System.currentTimeMillis();
        this.blacklist = new LinkedList<String>();
        this.schedule = Timer.EtcTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                HiredMerchant.this.closeShop(true, true);
            }
        }, 86400000L);
    }
    
    @Override
    public byte getShopType() {
        return 1;
    }
    
    public void setStoreid(final int storeid) {
        this.storeid = storeid;
    }
    
    public List<MaplePlayerShopItem> searchItem(final int itemSearch) {
        final List<MaplePlayerShopItem> itemz = new LinkedList<MaplePlayerShopItem>();
        for (final MaplePlayerShopItem item : this.items) {
            if (item.item.getItemId() == itemSearch && item.bundles > 0) {
                itemz.add(item);
            }
        }
        return itemz;
    }
    
    @Override
    public void buy(final MapleClient c, final int item, final short quantity) {
        final MaplePlayerShopItem pItem = this.items.get(item);
        final IItem shopItem = pItem.item;
        final IItem newItem = shopItem.copy();
        final short perbundle = newItem.getQuantity();
        final int theQuantity = pItem.price * quantity;
        newItem.setQuantity((short)(quantity * perbundle));
        final byte flag = newItem.getFlag();
        if (ItemFlag.KARMA_EQ.check(flag)) {
            newItem.setFlag((byte)(flag - ItemFlag.KARMA_EQ.getValue()));
        }
        else if (ItemFlag.KARMA_USE.check(flag)) {
            newItem.setFlag((byte)(flag - ItemFlag.KARMA_USE.getValue()));
        }
        if (!c.getPlayer().canHold(newItem.getItemId())) {
            c.getPlayer().dropMessage(1, "背包已满");
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if (MapleInventoryManipulator.checkSpace(c, newItem.getItemId(), newItem.getQuantity(), newItem.getOwner())) {
            final int gainmeso = this.getMeso() + theQuantity - GameConstants.EntrustedStoreTax(theQuantity);
            if (gainmeso > 0) {
                this.setMeso(gainmeso);
                final MaplePlayerShopItem tmp167_165 = pItem;
                tmp167_165.bundles -= quantity;
                MapleInventoryManipulator.addFromDrop(c, newItem, false);
                this.bought.add(new BoughtItem(newItem.getItemId(), quantity, theQuantity, c.getPlayer().getName()));
                c.getPlayer().gainMeso(-theQuantity, false);
                this.saveItems();
                final MapleCharacter chr = this.getMCOwnerWorld();
                final String itemText = MapleItemInformationProvider.getInstance().getName(newItem.getItemId()) + " (" + perbundle + ") x " + quantity + " 已经被卖出。 剩余数量: " + pItem.bundles + " 购买者: " + c.getPlayer().getName();
                if (chr != null) {
                    chr.dropMessage(-5, "您雇佣商店里面的道具: " + itemText);
                }
                System.out.println("[雇佣] " + ((chr != null) ? chr.getName() : this.getOwnerName()) + " 雇佣商店卖出: " + newItem.getItemId() + " - " + itemText + " 价格: " + theQuantity);
            }
            else {
                c.getPlayer().dropMessage(1, "金币不足.");
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        }
        else {
            c.getPlayer().dropMessage(1, "背包已满\r\n请留1格以上位置\r\n在进行购买物品\r\n防止非法复制");
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    @Override
    public void closeShop(final boolean saveItems, final boolean remove) {
        if (this.schedule != null) {
            this.schedule.cancel(false);
        }
        if (saveItems) {
            this.saveItems();
            this.items.clear();
        }
        if (remove) {
            ChannelServer.getInstance(this.channel).removeMerchant(this);
            this.getMap().broadcastMessage(PlayerShopPacket.destroyHiredMerchant(this.getOwnerId()));
        }
        this.getMap().removeMapObject(this);
        try {
            for (final ChannelServer ch : ChannelServer.getAllInstances()) {
                MapleMap map = null;
                for (int i = 910000001; i <= 910000022; ++i) {
                    map = ch.getMapFactory().getMap(i);
                    if (map != null) {
                        final List<MapleMapObject> HMS = map.getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.HIRED_MERCHANT));
                        for (final MapleMapObject HM : HMS) {
                            final HiredMerchant HMM = (HiredMerchant)HM;
                            if (HMM.getOwnerId() == this.getOwnerId()) {
                                map.removeMapObject(this);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        this.schedule = null;
    }
    
    public int getTimeLeft() {
        return (int)((System.currentTimeMillis() - this.start) / 1000L);
    }
    
    public int getStoreId() {
        return this.storeid;
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.HIRED_MERCHANT;
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        if (this.isAvailable()) {
            client.getSession().write(PlayerShopPacket.destroyHiredMerchant(this.getOwnerId()));
        }
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (this.isAvailable()) {
            client.getSession().write(PlayerShopPacket.spawnHiredMerchant(this));
        }
    }
    
    public boolean isInBlackList(final String bl) {
        return this.blacklist.contains(bl);
    }
    
    public void addBlackList(final String bl) {
        this.blacklist.add(bl);
    }
    
    public void removeBlackList(final String bl) {
        this.blacklist.remove(bl);
    }
    
    public void sendBlackList(final MapleClient c) {
        c.getSession().write(PlayerShopPacket.MerchantBlackListView(this.blacklist));
    }
    
    public void sendVisitor(final MapleClient c) {
        c.getSession().write(PlayerShopPacket.MerchantVisitorView(this.visitors));
    }
    
    @Override
    public int getMapId() {
        return this.map;
    }
}
