package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.World;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class PartyHandler
{
    public static void DenyPartyRequest(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final int action = slea.readByte();
        final int partyid = slea.readInt();
        if (c.getPlayer().getParty() == null) {
            final MapleParty party = World.Party.getParty(partyid);
            if (party != null) {
                switch (action) {
                    case 27: {
                        if (party.getMembers().size() < 6) {
                            World.Party.updateParty(partyid, PartyOperation.JOIN, new MaplePartyCharacter(c.getPlayer()));
                            c.getPlayer().receivePartyMemberHP();
                            c.getPlayer().updatePartyMemberHP();
                            break;
                        }
                        c.getSession().write(MaplePacketCreator.partyStatusMessage(17));
                        break;
                    }
                    case 22: {
                        final MapleCharacter cfrom = c.getChannelServer().getPlayerStorage().getCharacterById(party.getLeader().getId());
                        if (cfrom != null) {
                            cfrom.getClient().getSession().write(MaplePacketCreator.partyStatusMessage(23, c.getPlayer().getName()));
                            break;
                        }
                        break;
                    }
                }
            }
            else {
                c.getPlayer().dropMessage(5, "要参加的队伍不存在。");
            }
        }
        else {
            c.getPlayer().dropMessage(5, "您已经有一个组队，无法加入其他组队!");
        }
    }
    
    public static void PartyOperatopn(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final int operation = slea.readByte();
        MapleParty party = c.getPlayer().getParty();
        final MaplePartyCharacter partyplayer = new MaplePartyCharacter(c.getPlayer());
        switch (operation) {
            case 1: {
                if (party == null) {
                    party = World.Party.createParty(partyplayer);
                    c.getPlayer().setParty(party);
                    c.getSession().write(MaplePacketCreator.partyCreated(party.getId()));
                    break;
                }
                if (partyplayer.equals(party.getLeader()) && party.getMembers().size() == 1) {
                    c.getSession().write(MaplePacketCreator.partyCreated(party.getId()));
                    break;
                }
                c.getPlayer().dropMessage(5, "你不能创建一个组队,因为你已经存在一个队伍中");
                break;
            }
            case 2: {
                if (party != null) {
                    if (partyplayer.equals(party.getLeader())) {
                        World.Party.updateParty(party.getId(), PartyOperation.DISBAND, partyplayer);
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().disbandParty();
                        }
                        if (c.getPlayer().getPyramidSubway() != null) {
                            c.getPlayer().getPyramidSubway().fail(c.getPlayer());
                        }
                    }
                    else {
                        World.Party.updateParty(party.getId(), PartyOperation.LEAVE, partyplayer);
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().leftParty(c.getPlayer());
                        }
                        if (c.getPlayer().getPyramidSubway() != null) {
                            c.getPlayer().getPyramidSubway().fail(c.getPlayer());
                        }
                    }
                    c.getPlayer().setParty(null);
                    break;
                }
                break;
            }
            case 3: {
                final int partyid = slea.readInt();
                if (party != null) {
                    c.getPlayer().dropMessage(5, "你已经有队伍了，无法再加入其他队伍。请退组再试！");
                    break;
                }
                party = World.Party.getParty(partyid);
                if (party == null) {
                    c.getPlayer().dropMessage(5, "要加入的队伍不存在");
                    break;
                }
                if (party.getMembers().size() < 6) {
                    World.Party.updateParty(party.getId(), PartyOperation.JOIN, partyplayer);
                    c.getPlayer().receivePartyMemberHP();
                    c.getPlayer().updatePartyMemberHP();
                    break;
                }
                c.getSession().write(MaplePacketCreator.partyStatusMessage(17));
                break;
            }
            case 4: {
                final MapleCharacter invited = c.getChannelServer().getPlayerStorage().getCharacterByName(slea.readMapleAsciiString());
                if (invited == null) {
                    c.getSession().write(MaplePacketCreator.partyStatusMessage(18));
                    break;
                }
                if (invited.getParty() != null || party == null) {
                    c.getSession().write(MaplePacketCreator.partyStatusMessage(16));
                    break;
                }
                if (party.getMembers().size() < 6) {
                    invited.getClient().getSession().write(MaplePacketCreator.partyInvite(c.getPlayer()));
                    break;
                }
                c.getSession().write(MaplePacketCreator.partyStatusMessage(17));
                break;
            }
            case 5: {
                if (party == null || partyplayer == null) {
                    break;
                }
                if (!partyplayer.equals(party.getLeader())) {
                    break;
                }
                if (partyplayer.equals(party.getLeader())) {
                    final MaplePartyCharacter expelled = party.getMemberById(slea.readInt());
                    if (expelled != null) {
                        World.Party.updateParty(party.getId(), PartyOperation.EXPEL, expelled);
                        if (c.getPlayer().getEventInstance() != null && expelled.isOnline()) {
                            c.getPlayer().getEventInstance().disbandParty();
                        }
                        if (c.getPlayer().getPyramidSubway() != null && expelled.isOnline()) {
                            c.getPlayer().getPyramidSubway().fail(c.getPlayer());
                        }
                    }
                    break;
                }
                break;
            }
            case 6: {
                if (party != null) {
                    final MaplePartyCharacter newleader = party.getMemberById(slea.readInt());
                    if (newleader != null && partyplayer.equals(party.getLeader())) {
                        party.setLeader(newleader);
                        World.Party.updateParty(party.getId(), PartyOperation.SILENT_UPDATE, newleader);
                    }
                    c.getSession().write(MaplePacketCreator.enableActions());
                    break;
                }
                break;
            }
            default: {
                System.out.println("未知的队伍操作. 0x0" + operation);
                break;
            }
        }
    }
}
