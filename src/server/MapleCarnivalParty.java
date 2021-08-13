package server;

import client.MapleCharacter;
import handling.channel.ChannelServer;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import server.maps.MapleMap;
import tools.MaplePacketCreator;

public class MapleCarnivalParty
{
    private final List<Integer> members;
    private final WeakReference<MapleCharacter> leader;
    private final byte team;
    private final int channel;
    private short availableCP;
    private short totalCP;
    private boolean winner;
    
    public MapleCarnivalParty(final MapleCharacter owner, final List<MapleCharacter> members1, final byte team1) {
        this.members = new LinkedList<Integer>();
        this.availableCP = 0;
        this.totalCP = 0;
        this.winner = false;
        this.leader = new WeakReference<MapleCharacter>(owner);
        for (final MapleCharacter mem : members1) {
            this.members.add(mem.getId());
            mem.setCarnivalParty(this);
        }
        this.team = team1;
        this.channel = owner.getClient().getChannel();
    }
    
    public MapleCharacter getLeader() {
        return this.leader.get();
    }
    
    public void addCP(final MapleCharacter player, final int ammount) {
        this.totalCP += (short)ammount;
        this.availableCP += (short)ammount;
        player.addCP(ammount);
    }
    
    public int getTotalCP() {
        return this.totalCP;
    }
    
    public int getAvailableCP() {
        return this.availableCP;
    }
    
    public void useCP(final MapleCharacter player, final int ammount) {
        this.availableCP -= (short)ammount;
        player.useCP(ammount);
    }
    
    public List<Integer> getMembers() {
        return this.members;
    }
    
    public int getTeam() {
        return this.team;
    }
    
    public void warp(final MapleMap map, final String portalname) {
        for (final int chr : this.members) {
            final MapleCharacter c = ChannelServer.getInstance(this.channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.changeMap(map, map.getPortal(portalname));
            }
        }
    }
    
    public void warp(final MapleMap map, final int portalid) {
        for (final int chr : this.members) {
            final MapleCharacter c = ChannelServer.getInstance(this.channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.changeMap(map, map.getPortal(portalid));
            }
        }
    }
    
    public boolean allInMap(final MapleMap map) {
        for (final int chr : this.members) {
            if (map.getCharacterById(chr) == null) {
                return false;
            }
        }
        return true;
    }
    
    public void removeMember(final MapleCharacter chr) {
        for (int i = 0; i < this.members.size(); ++i) {
            if (this.members.get(i) == chr.getId()) {
                this.members.remove(i);
                chr.setCarnivalParty(null);
            }
        }
    }
    
    public boolean isWinner() {
        return this.winner;
    }
    
    public void setWinner(final boolean status) {
        this.winner = status;
    }
    
    public void displayMatchResult() {
        final String effect = this.winner ? "quest/carnival/win" : "quest/carnival/lose";
        final String sound = this.winner ? "MobCarnival/Win" : "MobCarnival/Lose";
        boolean done = false;
        for (final int chr : this.members) {
            final MapleCharacter c = ChannelServer.getInstance(this.channel).getPlayerStorage().getCharacterById(chr);
            if (c != null) {
                c.getClient().getSession().write(MaplePacketCreator.showEffect(effect));
                c.getClient().getSession().write(MaplePacketCreator.playSound(sound));
                if (done) {
                    continue;
                }
                done = true;
                c.getMap().killAllMonsters(true);
                c.getMap().setSpawns(false);
            }
        }
    }
}
