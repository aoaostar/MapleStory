package handling.world;

import client.MapleCharacter;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;
import server.maps.MapleDoor;

public class MaplePartyCharacter implements Serializable
{
    private static long serialVersionUID;
    private String name;
    private int id;
    private int level;
    private int channel;
    private int jobid;
    private int mapid;
    private int doorTown;
    private int doorTarget;
    private int doorSkill;
    private Point doorPosition;
    private boolean online;
    
    public MaplePartyCharacter(final MapleCharacter maplechar) {
        this.doorTown = 999999999;
        this.doorTarget = 999999999;
        this.doorSkill = 0;
        this.doorPosition = new Point(0, 0);
        this.name = maplechar.getName();
        this.level = maplechar.getLevel();
        this.channel = maplechar.getClient().getChannel();
        this.id = maplechar.getId();
        this.jobid = maplechar.getJob();
        this.mapid = maplechar.getMapId();
        this.online = true;
        final List<MapleDoor> doors = maplechar.getDoors();
        if (doors.size() > 0) {
            final MapleDoor door = doors.get(0);
            this.doorTown = door.getTown().getId();
            this.doorTarget = door.getTarget().getId();
            this.doorSkill = door.getSkill();
            this.doorPosition = door.getTargetPosition();
        }
        else {
            this.doorPosition = new Point(maplechar.getPosition());
        }
    }
    
    public MaplePartyCharacter() {
        this.doorTown = 999999999;
        this.doorTarget = 999999999;
        this.doorSkill = 0;
        this.doorPosition = new Point(0, 0);
        this.name = "";
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public boolean isOnline() {
        return this.online;
    }
    
    public void setOnline(final boolean online) {
        this.online = online;
    }
    
    public int getMapid() {
        return this.mapid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getJobId() {
        return this.jobid;
    }
    
    public int getDoorTown() {
        return this.doorTown;
    }
    
    public int getDoorTarget() {
        return this.doorTarget;
    }
    
    public int getDoorSkill() {
        return this.doorSkill;
    }
    
    public Point getDoorPosition() {
        return this.doorPosition;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final MaplePartyCharacter other = (MaplePartyCharacter)obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    static {
        MaplePartyCharacter.serialVersionUID = 6215463252132450750L;
    }
}
