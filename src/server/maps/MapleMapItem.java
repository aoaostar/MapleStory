package server.maps;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import java.awt.Point;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import tools.MaplePacketCreator;

public class MapleMapItem extends AbstractMapleMapObject
{
    protected IItem item;
    protected MapleMapObject dropper;
    protected int character_ownerid;
    protected int meso;
    protected int questid;
    protected byte type;
    protected boolean pickedUp;
    protected boolean playerDrop;
    protected boolean randDrop;
    protected long nextExpiry;
    protected long nextFFA;
    private ReentrantLock lock;
    
    public MapleMapItem(final IItem item, final Point position, final MapleMapObject dropper, final MapleCharacter owner, final byte type, final boolean playerDrop) {
        this.meso = 0;
        this.questid = -1;
        this.pickedUp = false;
        this.randDrop = false;
        this.nextExpiry = 0L;
        this.nextFFA = 0L;
        this.lock = new ReentrantLock();
        this.setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.type = type;
        this.playerDrop = playerDrop;
    }
    
    public MapleMapItem(final IItem item, final Point position, final MapleMapObject dropper, final MapleCharacter owner, final byte type, final boolean playerDrop, final int questid) {
        this.meso = 0;
        this.questid = -1;
        this.pickedUp = false;
        this.randDrop = false;
        this.nextExpiry = 0L;
        this.nextFFA = 0L;
        this.lock = new ReentrantLock();
        this.setPosition(position);
        this.item = item;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.type = type;
        this.playerDrop = playerDrop;
        this.questid = questid;
    }
    
    public MapleMapItem(final int meso, final Point position, final MapleMapObject dropper, final MapleCharacter owner, final byte type, final boolean playerDrop) {
        this.meso = 0;
        this.questid = -1;
        this.pickedUp = false;
        this.randDrop = false;
        this.nextExpiry = 0L;
        this.nextFFA = 0L;
        this.lock = new ReentrantLock();
        this.setPosition(position);
        this.item = null;
        this.dropper = dropper;
        this.character_ownerid = owner.getId();
        this.meso = meso;
        this.type = type;
        this.playerDrop = playerDrop;
    }
    
    public MapleMapItem(final Point position, final IItem item) {
        this.meso = 0;
        this.questid = -1;
        this.pickedUp = false;
        this.randDrop = false;
        this.nextExpiry = 0L;
        this.nextFFA = 0L;
        this.lock = new ReentrantLock();
        this.setPosition(position);
        this.item = item;
        this.character_ownerid = 0;
        this.type = 2;
        this.playerDrop = false;
        this.randDrop = true;
    }
    
    public IItem getItem() {
        return this.item;
    }
    
    public void setItem(final IItem z) {
        this.item = z;
    }
    
    public int getQuest() {
        return this.questid;
    }
    
    public int getItemId() {
        if (this.getMeso() > 0) {
            return this.meso;
        }
        return this.item.getItemId();
    }
    
    public MapleMapObject getDropper() {
        return this.dropper;
    }
    
    public int getOwner() {
        return this.character_ownerid;
    }
    
    public int getMeso() {
        return this.meso;
    }
    
    public boolean isPlayerDrop() {
        return this.playerDrop;
    }
    
    public boolean isPickedUp() {
        return this.pickedUp;
    }
    
    public void setPickedUp(final boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
    
    public byte getDropType() {
        return this.type;
    }
    
    public void setDropType(final byte z) {
        this.type = z;
    }
    
    public boolean isRandDrop() {
        return this.randDrop;
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.ITEM;
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (this.questid <= 0 || client.getPlayer().getQuestStatus(this.questid) == 1) {
            client.getSession().write((Object)MaplePacketCreator.dropItemFromMapObject(this, null, this.getPosition(), (byte)2));
        }
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().write((Object)MaplePacketCreator.removeItemFromMap(this.getObjectId(), 1, 0));
    }
    
    public Lock getLock() {
        return this.lock;
    }
    
    public void registerExpire(final long time) {
        this.nextExpiry = System.currentTimeMillis() + time;
    }
    
    public void registerFFA(final long time) {
        this.nextFFA = System.currentTimeMillis() + time;
    }
    
    public boolean shouldExpire() {
        return !this.pickedUp && this.nextExpiry > 0L && this.nextExpiry < System.currentTimeMillis();
    }
    
    public boolean shouldFFA() {
        return !this.pickedUp && this.type < 2 && this.nextFFA > 0L && this.nextFFA < System.currentTimeMillis();
    }
    
    public void expire(final MapleMap map) {
        this.pickedUp = true;
        map.broadcastMessage(MaplePacketCreator.removeItemFromMap(this.getObjectId(), 0, 0));
        map.removeMapObject(this);
    }
    
    public boolean hasFFA() {
        return this.nextFFA > 0L;
    }
}
