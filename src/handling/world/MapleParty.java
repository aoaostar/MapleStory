package handling.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapleParty implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private MaplePartyCharacter leader;
    private final List<MaplePartyCharacter> members;
    private int id;
    private final Map<Integer, Map<Integer, List<Integer>>> partyBuffs;
    
    public MapleParty(final int id, final MaplePartyCharacter chrfor) {
        this.members = new LinkedList<MaplePartyCharacter>();
        this.partyBuffs = new HashMap<Integer, Map<Integer, List<Integer>>>();
        this.leader = chrfor;
        this.members.add(this.leader);
        this.id = id;
    }
    
    public boolean containsMembers(final MaplePartyCharacter member) {
        return this.members.contains(member);
    }
    
    public void addMember(final MaplePartyCharacter member) {
        this.members.add(member);
    }
    
    public void removeMember(final MaplePartyCharacter member) {
        this.members.remove(member);
    }
    
    public void updateMember(final MaplePartyCharacter member) {
        for (int i = 0; i < this.members.size(); ++i) {
            final MaplePartyCharacter chr = this.members.get(i);
            if (chr.equals(member)) {
                this.members.set(i, member);
            }
        }
    }
    
    public MaplePartyCharacter getMemberById(final int id) {
        for (final MaplePartyCharacter chr : this.members) {
            if (chr.getId() == id) {
                return chr;
            }
        }
        return null;
    }
    
    public MaplePartyCharacter getMemberByIndex(final int index) {
        return this.members.get(index);
    }
    
    public Collection<MaplePartyCharacter> getMembers() {
        return new LinkedList<MaplePartyCharacter>(this.members);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public MaplePartyCharacter getLeader() {
        return this.leader;
    }
    
    public void setLeader(final MaplePartyCharacter nLeader) {
        this.leader = nLeader;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id;
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
        final MapleParty other = (MapleParty)obj;
        return this.id == other.id;
    }
    
    public void givePartyBuff(final int buffId, final int applyfrom, final int applyto) {
        if (this.partyBuffs.containsKey(buffId)) {
            if (this.partyBuffs.get(buffId).containsKey(applyfrom)) {
                if (!this.partyBuffs.get(buffId).keySet().isEmpty()) {
                    for (final Integer from : this.partyBuffs.get(buffId).keySet()) {
                        if (this.partyBuffs.get(buffId).get(from).contains(applyto)) {
                            this.partyBuffs.get(buffId).get(from).remove(this.partyBuffs.get(buffId).get(from).indexOf(applyto));
                        }
                        if (this.partyBuffs.get(buffId).get(from).isEmpty()) {
                            this.partyBuffs.get(buffId).remove(from);
                        }
                    }
                }
                if (this.partyBuffs != null && !this.partyBuffs.get(buffId).get(applyfrom).contains(applyto)) {
                    this.partyBuffs.get(buffId).get(applyfrom).add(applyto);
                }
            }
            else {
                final ArrayList applytos = new ArrayList();
                applytos.add(applyto);
                this.partyBuffs.get(buffId).put(applyfrom, applytos);
            }
        }
        else {
            final Map<Integer, List<Integer>> hMap = new HashMap<Integer, List<Integer>>();
            final ArrayList applytos2 = new ArrayList();
            applytos2.add(applyto);
            hMap.put(applyfrom, applytos2);
            this.partyBuffs.put(buffId, hMap);
        }
    }
}
