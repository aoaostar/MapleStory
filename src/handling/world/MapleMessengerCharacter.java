package handling.world;

import client.MapleCharacter;
import java.io.Serializable;

public class MapleMessengerCharacter implements Serializable
{
    private static long serialVersionUID;
    private String name;
    private int id;
    private int channel;
    private boolean online;
    
    public MapleMessengerCharacter(final MapleCharacter maplechar) {
        this.name = "";
        this.id = -1;
        this.channel = -1;
        this.online = false;
        this.name = maplechar.getName();
        this.channel = maplechar.getClient().getChannel();
        this.id = maplechar.getId();
        this.online = true;
    }
    
    public MapleMessengerCharacter() {
        this.name = "";
        this.id = -1;
        this.channel = -1;
        this.online = false;
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
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
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
        final MapleMessengerCharacter other = (MapleMessengerCharacter)obj;
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
        MapleMessengerCharacter.serialVersionUID = 6215463252132450750L;
    }
}
