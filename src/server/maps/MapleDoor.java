package server.maps;

import client.MapleCharacter;
import client.MapleClient;
import java.awt.Point;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import server.MaplePortal;
import tools.MaplePacketCreator;

public class MapleDoor extends AbstractMapleMapObject
{
    private WeakReference<MapleCharacter> owner;
    private MapleMap town;
    private MaplePortal townPortal;
    private MapleMap target;
    private int skillId;
    private int ownerId;
    private Point targetPosition;
    
    public MapleDoor(final MapleCharacter owner, final Point targetPosition, final int skillId) {
        this.owner = new WeakReference<>(owner);
        this.ownerId = owner.getId();
        this.target = owner.getMap();
        this.setPosition(this.targetPosition = targetPosition);
        this.town = this.target.getReturnMap();
        this.townPortal = this.getFreePortal();
        this.skillId = skillId;
    }
    
    public MapleDoor(final MapleDoor origDoor) {
        this.owner = new WeakReference<>(origDoor.owner.get());
        this.town = origDoor.town;
        this.townPortal = origDoor.townPortal;
        this.target = origDoor.target;
        this.targetPosition = origDoor.targetPosition;
        this.townPortal = origDoor.townPortal;
        this.skillId = origDoor.skillId;
        this.ownerId = origDoor.ownerId;
        this.setPosition(this.townPortal.getPosition());
    }
    
    public int getSkill() {
        return this.skillId;
    }
    
    public int getOwnerId() {
        return this.ownerId;
    }
    
    private MaplePortal getFreePortal() {
        final List<MaplePortal> freePortals = new ArrayList<MaplePortal>();
        for (final MaplePortal port : this.town.getPortals()) {
            if (port.getType() == 6) {
                freePortals.add(port);
            }
        }
        Collections.sort(freePortals, new Comparator<MaplePortal>() {
            @Override
            public int compare(final MaplePortal o1, final MaplePortal o2) {
                if (o1.getId() < o2.getId()) {
                    return -1;
                }
                if (o1.getId() == o2.getId()) {
                    return 0;
                }
                return 1;
            }
        });
        for (final MapleMapObject obj : this.town.getAllDoorsThreadsafe()) {
            final MapleDoor door = (MapleDoor)obj;
            if (door.getOwner() != null && door.getOwner().getParty() != null && this.getOwner() != null && this.getOwner().getParty() != null && this.getOwner().getParty().getMemberById(door.getOwnerId()) != null) {
                freePortals.remove(door.getTownPortal());
            }
        }
        if (freePortals.size() <= 0) {
            return null;
        }
        return freePortals.iterator().next();
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (this.getOwner() == null) {
            return;
        }
        if (this.target.getId() == client.getPlayer().getMapId() || this.getOwnerId() == client.getPlayer().getId() || (this.getOwner() != null && this.getOwner().getParty() != null && this.getOwner().getParty().getMemberById(client.getPlayer().getId()) != null)) {
            client.getSession().write(MaplePacketCreator.spawnDoor(this.getOwnerId(), (this.town.getId() == client.getPlayer().getMapId()) ? this.townPortal.getPosition() : this.targetPosition, true));
            if (this.getOwner() != null && this.getOwner().getParty() != null && (this.getOwnerId() == client.getPlayer().getId() || this.getOwner().getParty().getMemberById(client.getPlayer().getId()) != null)) {
                client.getSession().write(MaplePacketCreator.partyPortal(this.town.getId(), this.target.getId(), this.skillId, this.targetPosition));
            }
            client.getSession().write(MaplePacketCreator.spawnPortal(this.town.getId(), this.target.getId(), this.skillId, this.targetPosition));
        }
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        if (this.getOwner() == null) {
            return;
        }
        if (this.target.getId() == client.getPlayer().getMapId() || this.getOwnerId() == client.getPlayer().getId() || (this.getOwner() != null && this.getOwner().getParty() != null && this.getOwner().getParty().getMemberById(client.getPlayer().getId()) != null)) {
            if (this.getOwner().getParty() != null && (this.getOwnerId() == client.getPlayer().getId() || this.getOwner().getParty().getMemberById(client.getPlayer().getId()) != null)) {
                client.getSession().write(MaplePacketCreator.partyPortal(999999999, 999999999, 0, new Point(-1, -1)));
            }
            client.getSession().write(MaplePacketCreator.removeDoor(this.getOwnerId(), false));
            client.getSession().write(MaplePacketCreator.removeDoor(this.getOwnerId(), true));
        }
    }
    
    public void warp(final MapleCharacter chr, final boolean toTown) {
        if (chr.getId() == this.getOwnerId() || (this.getOwner() != null && this.getOwner().getParty() != null && this.getOwner().getParty().getMemberById(chr.getId()) != null)) {
            if (!toTown) {
                chr.changeMap(this.target, this.targetPosition);
            }
            else {
                chr.changeMap(this.town, this.townPortal);
            }
        }
        else {
            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    public MapleCharacter getOwner() {
        return this.owner.get();
    }
    
    public MapleMap getTown() {
        return this.town;
    }
    
    public MaplePortal getTownPortal() {
        return this.townPortal;
    }
    
    public MapleMap getTarget() {
        return this.target;
    }
    
    public Point getTargetPosition() {
        return this.targetPosition;
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.DOOR;
    }
}
