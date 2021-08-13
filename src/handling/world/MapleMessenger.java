package handling.world;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class MapleMessenger implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private final MapleMessengerCharacter[] members;
    private final String[] silentLink;
    private int id;
    
    public MapleMessenger(final int id, final MapleMessengerCharacter chrfor) {
        this.members = new MapleMessengerCharacter[3];
        this.silentLink = new String[3];
        this.id = id;
        this.addMem(0, chrfor);
    }
    
    public void addMem(final int pos, final MapleMessengerCharacter chrfor) {
        if (this.members[pos] != null) {
            return;
        }
        this.members[pos] = chrfor;
    }
    
    public boolean containsMembers(final MapleMessengerCharacter member) {
        return this.getPositionByName(member.getName()) < 4;
    }
    
    public void addMember(final MapleMessengerCharacter member) {
        final int position = this.getLowestPosition();
        if (position > -1 && position < 4) {
            this.addMem(position, member);
        }
    }
    
    public void removeMember(final MapleMessengerCharacter member) {
        final int position = this.getPositionByName(member.getName());
        if (position > -1 && position < 4) {
            this.members[position] = null;
        }
    }
    
    public void silentRemoveMember(final MapleMessengerCharacter member) {
        final int position = this.getPositionByName(member.getName());
        if (position > -1 && position < 4) {
            this.members[position] = null;
            this.silentLink[position] = member.getName();
        }
    }
    
    public void silentAddMember(final MapleMessengerCharacter member) {
        for (int i = 0; i < this.silentLink.length; ++i) {
            if (this.silentLink[i] != null && this.silentLink[i].equalsIgnoreCase(member.getName())) {
                this.addMem(i, member);
                this.silentLink[i] = null;
                return;
            }
        }
    }
    
    public void updateMember(final MapleMessengerCharacter member) {
        for (int i = 0; i < this.members.length; ++i) {
            final MapleMessengerCharacter chr = this.members[i];
            if (chr.equals(member)) {
                this.members[i] = null;
                this.addMem(i, member);
                return;
            }
        }
    }
    
    public int getLowestPosition() {
        for (int i = 0; i < this.members.length; ++i) {
            if (this.members[i] == null) {
                return i;
            }
        }
        return 4;
    }
    
    public int getPositionByName(final String name) {
        for (int i = 0; i < this.members.length; ++i) {
            final MapleMessengerCharacter messengerchar = this.members[i];
            if (messengerchar != null && messengerchar.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return 4;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return 31 + this.id;
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
        final MapleMessenger other = (MapleMessenger)obj;
        return this.id == other.id;
    }
    
    public Collection<MapleMessengerCharacter> getMembers() {
        return Arrays.asList(this.members);
    }
}
