package handling.world;

import client.BuddyEntry;
import client.BuddyList;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleCoolDownValueHolder;
import client.MapleDiseaseValueHolder;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.PetDataFactory;
import database.DatabaseConnection;
import handling.MaplePacket;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.PlayerStorage;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyCharacter;
import handling.world.guild.MapleBBSThread;
import handling.world.guild.MapleGuild;
import handling.world.guild.MapleGuildAlliance;
import handling.world.guild.MapleGuildCharacter;
import handling.world.guild.MapleGuildSummary;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import server.Timer;
import server.maps.MapleMap;
import server.maps.MapleMapItem;
import tools.CollectionUtil;
import tools.MaplePacketCreator;
import tools.packet.PetPacket;


public class World
{
    public static boolean isShutDown;
    private static final int CHANNELS_PER_THREAD = 3;
    
    public static void init() {
        Find.findChannel(0);
        Guild.lock.toString();
        Alliance.lock.toString();
        Family.lock.toString();
        Messenger.getMessenger(0);
        Party.getParty(0);
    }
    
    public static String getStatus() throws RemoteException {
        final StringBuilder ret = new StringBuilder();
        int totalUsers = 0;
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            ret.append("Channel ");
            ret.append(cs.getChannel());
            ret.append(": ");
            final int channelUsers = cs.getConnectedClients();
            totalUsers += channelUsers;
            ret.append(channelUsers);
            ret.append(" users\n");
        }
        ret.append("Total users online: ");
        ret.append(totalUsers);
        ret.append("\n");
        return ret.toString();
    }
    
    public static Map<Integer, Integer> getConnected() {
        final Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        int total = 0;
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            final int curConnected = cs.getConnectedClients();
            ret.put(cs.getChannel(), curConnected);
            total += curConnected;
        }
        ret.put(0, total);
        return ret;
    }
    
    public static List<CheaterData> getCheaters() {
        final List<CheaterData> allCheaters = new ArrayList<CheaterData>();
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            allCheaters.addAll(cs.getCheaters());
        }
        Collections.sort(allCheaters);
        return CollectionUtil.copyFirst(allCheaters, 10);
    }
    
    public static boolean isConnected(final String charName) {
        return Find.findChannel(charName) > 0;
    }
    
    public static void toggleMegaphoneMuteState() {
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            cs.toggleMegaphoneMuteState();
        }
    }
    
    public static void ChannelChange_Data(final CharacterTransfer Data, final int characterid, final int toChannel) {
        getStorage(toChannel).registerPendingPlayer(Data, characterid);
    }
    
    public static boolean isCharacterListConnected(final List<String> charName) {
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            for (final String c : charName) {
                if (cs.getPlayerStorage().getCharacterByName(c) != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean hasMerchant(final int accountID) {
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            if (cs.containsMerchant(accountID)) {
                return true;
            }
        }
        return false;
    }
    
    public static PlayerStorage getStorage(final int channel) {
        if (channel == -20) {
            return CashShopServer.getPlayerStorageMTS();
        }
        if (channel == -10) {
            return CashShopServer.getPlayerStorage();
        }
        return ChannelServer.getInstance(channel).getPlayerStorage();
    }
    
    public static void scheduleRateDelay(final String type, final long delay) {
        Timer.WorldTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                final String rate = type;
                if (rate.equals("经验")) {
                    for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                        cservs.setExpRate(1);
                    }
                }
                else if (rate.equals("爆率")) {
                    for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                        cservs.setDropRate(1);
                    }
                }
                else if (rate.equals("金币")) {
                    for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                        cservs.setMesoRate(1);
                    }
                }
                else if (rate.equalsIgnoreCase("boss爆率")) {
                    for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                        cservs.setBossDropRate(1);
                    }
                }
                else if (rate.equals("宠物经验")) {}
                for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                    cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, " 系统双倍活动已经结束。系统已成功自动切换为正常游戏模式！"));
                }
            }
        }, delay * 1000L);
    }
    
    public static void handleCooldowns(final MapleCharacter chr, final int numTimes, final boolean hurt) {
        if (chr == null) {
            return;
        }
        final long now = System.currentTimeMillis();
        for (final MapleCoolDownValueHolder m : chr.getCooldowns()) {
            if (m.startTime + m.length < now) {
                final int skil = m.skillId;
                chr.removeCooldown(skil);
                chr.getClient().getSession().write(MaplePacketCreator.skillCooldown(skil, 0));
            }
        }
        for (final MapleDiseaseValueHolder i : chr.getAllDiseases()) {
            if (i.startTime + i.length < now) {
                chr.dispelDebuff(i.disease);
            }
        }
        if (numTimes % 100 == 0) {
            for (final MaplePet pet : chr.getPets()) {
                if (pet.getSummoned()) {
                    if (pet.getPetItemId() == 5000054 && pet.getSecondsLeft() > 0) {
                        pet.setSecondsLeft(pet.getSecondsLeft() - 1);
                        if (pet.getSecondsLeft() <= 0) {
                            chr.unequipPet(pet, true);
                            return;
                        }
                    }
                    final int newFullness = pet.getFullness() - PetDataFactory.getHunger(pet.getPetItemId());
                    if (newFullness <= 5) {
                        pet.setFullness(15);
                        chr.unequipPet(pet, true);
                    }
                    else {
                        pet.setFullness(newFullness);
                        chr.getClient().getSession().write(PetPacket.updatePet(pet, chr.getInventory(MapleInventoryType.CASH).getItem(pet.getInventoryPosition()), true));
                    }
                }
            }
        }
        if (chr.isAlive()) {
            if (chr.canRecovery()) {
                chr.doRecovery();
            }
            if (chr.canExpiration(now)) {
                chr.expirationTask(true);
            }
            if (chr.getDiseaseSize() > 0) {
                for (final MapleDiseaseValueHolder i : chr.getAllDiseases()) {
                    if (i != null && i.startTime + i.length < now) {
                        chr.dispelDebuff(i.disease);
                    }
                }
            }
            if (numTimes % 7 == 0 && chr.getMount() != null && chr.getMount().canTire(now)) {
                chr.getMount().increaseFatigue();
                if (chr.getMount().getFatigue() >= 90) {
                    chr.dropMessage(5, "坐骑疲劳值快满了，请使用坐骑疲劳恢复药。");
                }
            }
            if (numTimes % 26 == 0) {
                for (final MaplePet pet : chr.getSummonedPets()) {
                    if (pet.getPetItemId() == 5000054 && pet.getSecondsLeft() > 0) {
                        pet.setSecondsLeft(pet.getSecondsLeft() - 1);
                        if (pet.getSecondsLeft() <= 0) {
                            chr.unequipPet(pet, true);
                            return;
                        }
                    }
                    final int newFullness = pet.getFullness() - PetDataFactory.getHunger(pet.getPetItemId());
                    if (newFullness <= 5) {
                        pet.setFullness(15);
                        chr.unequipPet(pet, true);
                        chr.dropMessage(5, "宠物肚子饿，回家了~！");
                    }
                    else {
                        pet.setFullness(newFullness);
                        chr.getClient().getSession().write(PetPacket.updatePet(pet, chr.getInventory(MapleInventoryType.CASH).getItem(pet.getInventoryPosition()), true));
                    }
                }
            }
            if (hurt && chr.getInventory(MapleInventoryType.EQUIPPED).findById(chr.getMap().getHPDecProtect()) == null) {
                if ((chr.getMapId() == 749040100 && chr.getInventory(MapleInventoryType.CASH).findById(5451000) == null) || (chr.getMapId() >= 211000000 && chr.getMapId() <= 211999999)) {
                    chr.addHP(-chr.getMap().getHPDec());
                }
                else if (chr.getMapId() != 749040100) {
                    chr.addHP(-chr.getMap().getHPDec());
                }
            }
        }
    }
    
    public static String getAllowLoginTip(final List<String> charNames) {
        final StringBuilder ret = new StringBuilder("账号下其他角色在游戏: ");
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final String name : charNames) {
                if (cserv.isConnected(name)) {
                    ret.append(name);
                    ret.append(" ");
                }
            }
        }
        return ret.toString();
    }
    
    public static void registerRespawn() {
        Timer.WorldTimer.getInstance().register(new Respawn(), 5000L);
        System.out.println("[刷怪线程] 已经启动...");
    }
    
    public static void handleMap(final MapleMap map, final int numTimes, final int size) {
        if (map.getItemsSize() > 0) {
            for (final MapleMapItem item : map.getAllItemsThreadsafe()) {
                if (item.shouldExpire()) {
                    item.expire(map);
                }
                else {
                    if (!item.shouldFFA()) {
                        continue;
                    }
                    item.setDropType((byte)2);
                }
            }
        }
        if (map.characterSize() > 0) {
            if (map.canSpawn()) {
                map.respawn(false);
            }
            final boolean hurt = map.canHurt();
            for (final MapleCharacter chr : map.getCharactersThreadsafe()) {
                handleCooldowns(chr, numTimes, hurt);
            }
        }
        if (numTimes % 10 == 0 && map.getId() == 220080001 && map.playerCount() == 0) {
            ChannelServer.getInstance(map.getChannel()).getMapFactory().getMap(220080000).resetReactors();
        }
    }
    
    static {
        World.isShutDown = false;
    }
    
    public static class Party
    {
        private static Map<Integer, MapleParty> parties;
        private static AtomicInteger runningPartyId;
        
        public static void partyChat(final int partyid, final String chattext, final String namefrom) {
            final MapleParty party = getParty(partyid);
            if (party == null) {
                throw new IllegalArgumentException("no party with the specified partyid exists");
            }
            for (final MaplePartyCharacter partychar : party.getMembers()) {
                final int ch = Find.findChannel(partychar.getName());
                if (ch > 0) {
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(partychar.getName());
                    if (chr == null || chr.getName().equalsIgnoreCase(namefrom)) {
                        continue;
                    }
                    chr.getClient().getSession().write(MaplePacketCreator.multiChat(namefrom, chattext, 1));
                }
            }
        }
        
        public static void updateParty(final int partyid, final PartyOperation operation, final MaplePartyCharacter target) {
            final MapleParty party = getParty(partyid);
            if (party == null) {
                return;
            }
            switch (operation) {
                case JOIN: {
                    party.addMember(target);
                    break;
                }
                case EXPEL:
                case LEAVE: {
                    party.removeMember(target);
                    break;
                }
                case DISBAND: {
                    disbandParty(partyid);
                    break;
                }
                case SILENT_UPDATE:
                case LOG_ONOFF: {
                    party.updateMember(target);
                    break;
                }
                case CHANGE_LEADER: {
                    party.setLeader(target);
                    break;
                }
                default: {
                    throw new RuntimeException("Unhandeled updateParty operation " + operation.name());
                }
            }
            for (final MaplePartyCharacter partychar : party.getMembers()) {
                final int ch = Find.findChannel(partychar.getName());
                if (ch > 0) {
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(partychar.getName());
                    if (chr == null) {
                        continue;
                    }
                    if (operation == PartyOperation.DISBAND) {
                        chr.setParty(null);
                    }
                    else {
                        chr.setParty(party);
                    }
                    chr.getClient().getSession().write(MaplePacketCreator.updateParty(chr.getClient().getChannel(), party, operation, target));
                }
            }
            switch (operation) {
                case EXPEL:
                case LEAVE: {
                    final int ch2 = Find.findChannel(target.getName());
                    if (ch2 <= 0) {
                        break;
                    }
                    final MapleCharacter chr2 = ChannelServer.getInstance(ch2).getPlayerStorage().getCharacterByName(target.getName());
                    if (chr2 != null) {
                        chr2.getClient().getSession().write(MaplePacketCreator.updateParty(chr2.getClient().getChannel(), party, operation, target));
                        chr2.setParty(null);
                        break;
                    }
                    break;
                }
            }
        }
        
        public static MapleParty createParty(final MaplePartyCharacter chrfor) {
            final int partyid = Party.runningPartyId.getAndIncrement();
            final MapleParty party = new MapleParty(partyid, chrfor);
            Party.parties.put(party.getId(), party);
            return party;
        }
        
        public static MapleParty getParty(final int partyid) {
            return Party.parties.get(partyid);
        }
        
        public static MapleParty disbandParty(final int partyid) {
            return Party.parties.remove(partyid);
        }
        
        static {
            Party.parties = new HashMap<Integer, MapleParty>();
            Party.runningPartyId = new AtomicInteger();
            final Connection con = DatabaseConnection.getConnection();
            try {
                final PreparedStatement ps = con.prepareStatement("SELECT MAX(party)+2 FROM characters");
                final ResultSet rs = ps.executeQuery();
                rs.next();
                Party.runningPartyId.set(rs.getInt(1));
                rs.close();
                ps.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static class Buddy
    {
        public static void buddyChat(final int[] recipientCharacterIds, final int cidFrom, final String nameFrom, final String chattext) {
            for (final int characterId : recipientCharacterIds) {
                final int ch = Find.findChannel(characterId);
                if (ch > 0) {
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(characterId);
                    if (chr != null && chr.getBuddylist().containsVisible(cidFrom)) {
                        chr.getClient().getSession().write(MaplePacketCreator.multiChat(nameFrom, chattext, 0));
                    }
                }
            }
        }
        
        private static void updateBuddies(final int characterId, final int channel, final Collection<Integer> buddies, final boolean offline, final int gmLevel, final boolean isHidden) {
            for (final Integer buddy : buddies) {
                final int ch = Find.findChannel(buddy);
                if (ch > 0) {
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(buddy);
                    if (chr == null) {
                        continue;
                    }
                    final BuddyEntry ble = chr.getBuddylist().get(characterId);
                    if (ble == null || !ble.isVisible()) {
                        continue;
                    }
                    int mcChannel;
                    if (offline || (isHidden && chr.getGMLevel() < gmLevel)) {
                        ble.setChannel(-1);
                        mcChannel = -1;
                    }
                    else {
                        ble.setChannel(channel);
                        mcChannel = channel - 1;
                    }
                    chr.getBuddylist().put(ble);
                    chr.getClient().sendPacket(MaplePacketCreator.updateBuddyChannel(ble.getCharacterId(), mcChannel));
                }
            }
        }
        
        public static void buddyChanged(final int cid, final int cidFrom, final String name, final int channel, final BuddyList.BuddyOperation operation, final int level, final int job, final String group) {
            final int ch = Find.findChannel(cid);
            if (ch > 0) {
                final MapleCharacter addChar = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(cid);
                if (addChar != null) {
                    final BuddyList buddylist = addChar.getBuddylist();
                    switch (operation) {
                        case ADDED: {
                            if (buddylist.contains(cidFrom)) {
                                buddylist.put(new BuddyEntry(name, cidFrom, group, channel, true, level, job));
                                addChar.getClient().getSession().write(MaplePacketCreator.updateBuddyChannel(cidFrom, channel - 1));
                                break;
                            }
                            break;
                        }
                        case DELETED: {
                            if (buddylist.contains(cidFrom)) {
                                buddylist.put(new BuddyEntry(name, cidFrom, group, -1, buddylist.get(cidFrom).isVisible(), level, job));
                                addChar.getClient().getSession().write(MaplePacketCreator.updateBuddyChannel(cidFrom, -1));
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
        
        public static BuddyList.BuddyAddResult requestBuddyAdd(final String addName, final int channelFrom, final int cidFrom, final String nameFrom, final int levelFrom, final int jobFrom) {
            final int ch = Find.findChannel(cidFrom);
            if (ch > 0) {
                final MapleCharacter addChar = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(addName);
                if (addChar != null) {
                    final BuddyList buddylist = addChar.getBuddylist();
                    if (buddylist.isFull()) {
                        return BuddyList.BuddyAddResult.BUDDYLIST_FULL;
                    }
                    if (!buddylist.contains(cidFrom)) {
                        buddylist.addBuddyRequest(addChar.getClient(), cidFrom, nameFrom, channelFrom, levelFrom, jobFrom);
                    }
                    else if (buddylist.containsVisible(cidFrom)) {
                        return BuddyList.BuddyAddResult.ALREADY_ON_LIST;
                    }
                }
            }
            return BuddyList.BuddyAddResult.OK;
        }
        
        public static void loggedOn(final String name, final int characterId, final int channel, final Collection<Integer> buddies, final int gmLevel, final boolean isHidden) {
            updateBuddies(characterId, channel, buddies, false, gmLevel, isHidden);
        }
        
        public static void loggedOff(final String name, final int characterId, final int channel, final Collection<Integer> buddies, final int gmLevel, final boolean isHidden) {
            updateBuddies(characterId, channel, buddies, true, gmLevel, isHidden);
        }
    }
    
    public static class Messenger
    {
        private static final Map<Integer, MapleMessenger> messengers;
        private static final AtomicInteger runningMessengerId;
        
        public static MapleMessenger createMessenger(final MapleMessengerCharacter chrfor) {
            final int messengerid = Messenger.runningMessengerId.getAndIncrement();
            final MapleMessenger messenger = new MapleMessenger(messengerid, chrfor);
            Messenger.messengers.put(messenger.getId(), messenger);
            return messenger;
        }
        
        public static void declineChat(final String target, final String namefrom) {
            final int ch = Find.findChannel(target);
            if (ch > 0) {
                final ChannelServer cs = ChannelServer.getInstance(ch);
                final MapleCharacter chr = cs.getPlayerStorage().getCharacterByName(target);
                if (chr != null) {
                    final MapleMessenger messenger = chr.getMessenger();
                    if (messenger != null) {
                        chr.getClient().getSession().write(MaplePacketCreator.messengerNote(namefrom, 5, 0));
                    }
                }
            }
        }
        
        public static MapleMessenger getMessenger(final int messengerid) {
            return Messenger.messengers.get(messengerid);
        }
        
        public static void leaveMessenger(final int messengerid, final MapleMessengerCharacter target) {
            final MapleMessenger messenger = getMessenger(messengerid);
            if (messenger == null) {
                throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }
            final int position = messenger.getPositionByName(target.getName());
            messenger.removeMember(target);
            for (final MapleMessengerCharacter mmc : messenger.getMembers()) {
                if (mmc != null) {
                    final int ch = Find.findChannel(mmc.getId());
                    if (ch <= 0) {
                        continue;
                    }
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(mmc.getName());
                    if (chr == null) {
                        continue;
                    }
                    chr.getClient().getSession().write(MaplePacketCreator.removeMessengerPlayer(position));
                }
            }
        }
        
        public static void silentLeaveMessenger(final int messengerid, final MapleMessengerCharacter target) {
            final MapleMessenger messenger = getMessenger(messengerid);
            if (messenger == null) {
                throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }
            messenger.silentRemoveMember(target);
        }
        
        public static void silentJoinMessenger(final int messengerid, final MapleMessengerCharacter target) {
            final MapleMessenger messenger = getMessenger(messengerid);
            if (messenger == null) {
                throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }
            messenger.silentAddMember(target);
        }
        
        public static void updateMessenger(final int messengerid, final String namefrom, final int fromchannel) {
            final MapleMessenger messenger = getMessenger(messengerid);
            final int position = messenger.getPositionByName(namefrom);
            for (final MapleMessengerCharacter messengerchar : messenger.getMembers()) {
                if (messengerchar != null && !messengerchar.getName().equals(namefrom)) {
                    final int ch = Find.findChannel(messengerchar.getName());
                    if (ch <= 0) {
                        continue;
                    }
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr == null) {
                        continue;
                    }
                    final MapleCharacter from = ChannelServer.getInstance(fromchannel).getPlayerStorage().getCharacterByName(namefrom);
                    chr.getClient().getSession().write(MaplePacketCreator.updateMessengerPlayer(namefrom, from, position, fromchannel - 1));
                }
            }
        }
        
        public static void joinMessenger(final int messengerid, final MapleMessengerCharacter target, final String from, final int fromchannel) {
            final MapleMessenger messenger = getMessenger(messengerid);
            if (messenger == null) {
                throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }
            messenger.addMember(target);
            final int position = messenger.getPositionByName(target.getName());
            for (final MapleMessengerCharacter messengerchar : messenger.getMembers()) {
                if (messengerchar != null) {
                    final int mposition = messenger.getPositionByName(messengerchar.getName());
                    final int ch = Find.findChannel(messengerchar.getName());
                    if (ch <= 0) {
                        continue;
                    }
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr == null) {
                        continue;
                    }
                    if (!messengerchar.getName().equals(from)) {
                        final MapleCharacter fromCh = ChannelServer.getInstance(fromchannel).getPlayerStorage().getCharacterByName(from);
                        chr.getClient().getSession().write(MaplePacketCreator.addMessengerPlayer(from, fromCh, position, fromchannel - 1));
                        fromCh.getClient().getSession().write(MaplePacketCreator.addMessengerPlayer(chr.getName(), chr, mposition, messengerchar.getChannel() - 1));
                    }
                    else {
                        chr.getClient().getSession().write(MaplePacketCreator.joinMessenger(mposition));
                    }
                }
            }
        }
        
        public static void messengerChat(final int messengerid, final String chattext, final String namefrom) {
            final MapleMessenger messenger = getMessenger(messengerid);
            if (messenger == null) {
                throw new IllegalArgumentException("No messenger with the specified messengerid exists");
            }
            for (final MapleMessengerCharacter messengerchar : messenger.getMembers()) {
                if (messengerchar != null && !messengerchar.getName().equals(namefrom)) {
                    final int ch = Find.findChannel(messengerchar.getName());
                    if (ch <= 0) {
                        continue;
                    }
                    final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(messengerchar.getName());
                    if (chr == null) {
                        continue;
                    }
                    chr.getClient().getSession().write(MaplePacketCreator.messengerChat(chattext));
                }
                else {
                    if (messengerchar == null) {
                        continue;
                    }
                    final int ch = Find.findChannel(messengerchar.getName());
                    if (ch <= 0) {
                        continue;
                    }
                    ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(messengerchar.getName());
                }
            }
        }
        
        public static void messengerInvite(final String sender, final int messengerid, final String target, final int fromchannel, final boolean gm) {
            if (World.isConnected(target)) {
                final int ch = Find.findChannel(target);
                if (ch > 0) {
                    final MapleCharacter from = ChannelServer.getInstance(fromchannel).getPlayerStorage().getCharacterByName(sender);
                    final MapleCharacter targeter = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(target);
                    if (targeter != null && targeter.getMessenger() == null) {
                        if (!targeter.isGM() || gm) {
                            targeter.getClient().getSession().write(MaplePacketCreator.messengerInvite(sender, messengerid));
                            from.getClient().getSession().write(MaplePacketCreator.messengerNote(target, 4, 1));
                        }
                        else {
                            from.getClient().getSession().write(MaplePacketCreator.messengerNote(target, 4, 0));
                        }
                    }
                    else {
                        from.getClient().getSession().write(MaplePacketCreator.messengerChat(sender + " : " + target + " is already using Maple Messenger"));
                    }
                }
            }
        }
        
        static {
            messengers = new HashMap<Integer, MapleMessenger>();
            (runningMessengerId = new AtomicInteger()).set(1);
        }
    }
    
    public static class Guild
    {
        private static final Map<Integer, MapleGuild> guilds;
        private static ReentrantReadWriteLock lock;
        
        public static int createGuild(final int leaderId, final String name) {
            return MapleGuild.createGuild(leaderId, name);
        }
        
        public static MapleGuild getGuild(final int id) {
            MapleGuild ret = null;
            Guild.lock.readLock().lock();
            try {
                ret = Guild.guilds.get(id);
            }
            finally {
                Guild.lock.readLock().unlock();
            }
            if (ret == null) {
                Guild.lock.writeLock().lock();
                try {
                    ret = new MapleGuild(id);
                    if (ret == null || ret.getId() <= 0 || !ret.isProper()) {
                        return null;
                    }
                    Guild.guilds.put(id, ret);
                }
                finally {
                    Guild.lock.writeLock().unlock();
                }
            }
            return ret;
        }
        
        public static MapleGuild getGuildByName(final String guildName) {
            Guild.lock.readLock().lock();
            try {
                for (final MapleGuild g : Guild.guilds.values()) {
                    if (g.getName().equalsIgnoreCase(guildName)) {
                        return g;
                    }
                }
                return null;
            }
            finally {
                Guild.lock.readLock().unlock();
            }
        }
        
        public static MapleGuild getGuild(final MapleCharacter mc) {
            return getGuild(mc.getGuildId());
        }
        
        public static void setGuildMemberOnline(final MapleGuildCharacter mc, final boolean bOnline, final int channel) {
            final MapleGuild g = getGuild(mc.getGuildId());
            if (g != null) {
                g.setOnline(mc.getId(), bOnline, channel);
            }
        }
        
        public static void guildPacket(final int gid, final MaplePacket message) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.broadcast(message);
            }
        }
        
        public static int addGuildMember(final MapleGuildCharacter mc) {
            final MapleGuild g = getGuild(mc.getGuildId());
            if (g != null) {
                return g.addGuildMember(mc);
            }
            return 0;
        }
        
        public static void leaveGuild(final MapleGuildCharacter mc) {
            final MapleGuild g = getGuild(mc.getGuildId());
            if (g != null) {
                g.leaveGuild(mc);
            }
        }
        
        public static void guildChat(final int gid, final String name, final int cid, final String msg) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.guildChat(name, cid, msg);
            }
        }
        
        public static void changeRank(final int gid, final int cid, final int newRank) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.changeRank(cid, newRank);
            }
        }
        
        public static void expelMember(final MapleGuildCharacter initiator, final String name, final int cid) {
            final MapleGuild g = getGuild(initiator.getGuildId());
            if (g != null) {
                g.expelMember(initiator, name, cid);
            }
        }
        
        public static void setGuildNotice(final int gid, final String notice) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.setGuildNotice(notice);
            }
        }
        
        public static void memberLevelJobUpdate(final MapleGuildCharacter mc) {
            final MapleGuild g = getGuild(mc.getGuildId());
            if (g != null) {
                g.memberLevelJobUpdate(mc);
            }
        }
        
        public static void changeRankTitle(final int gid, final String[] ranks) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.changeRankTitle(ranks);
            }
        }
        
        public static void setGuildEmblem(final int gid, final short bg, final byte bgcolor, final short logo, final byte logocolor) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.setGuildEmblem(bg, bgcolor, logo, logocolor);
            }
        }
        
        public static void disbandGuild(final int gid) {
            final MapleGuild g = getGuild(gid);
            Guild.lock.writeLock().lock();
            try {
                if (g != null) {
                    g.disbandGuild();
                    Guild.guilds.remove(gid);
                }
            }
            finally {
                Guild.lock.writeLock().unlock();
            }
        }
        
        public static void deleteGuildCharacter(final int guildid, final int charid) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                final MapleGuildCharacter mc = g.getMGC(charid);
                if (mc != null) {
                    if (mc.getGuildRank() > 1) {
                        g.leaveGuild(mc);
                    }
                    else {
                        g.disbandGuild();
                    }
                }
            }
        }
        
        public static boolean increaseGuildCapacity(final int gid) {
            final MapleGuild g = getGuild(gid);
            return g != null && g.increaseCapacity();
        }
        
        public static void gainGP(final int gid, final int amount) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.gainGP(amount);
            }
        }
        
        public static int getGP(final int gid) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                return g.getGP();
            }
            return 0;
        }
        
        public static int getInvitedId(final int gid) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                return g.getInvitedId();
            }
            return 0;
        }
        
        public static void setInvitedId(final int gid, final int inviteid) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                g.setInvitedId(inviteid);
            }
        }
        
        public static int getGuildLeader(final String guildName) {
            final MapleGuild mga = getGuildByName(guildName);
            if (mga != null) {
                return mga.getLeaderId();
            }
            return 0;
        }
        
        public static void save() {
            System.out.println("Saving guilds...");
            Guild.lock.writeLock().lock();
            try {
                for (final MapleGuild a : Guild.guilds.values()) {
                    a.writeToDB(false);
                }
            }
            finally {
                Guild.lock.writeLock().unlock();
            }
        }
        
        public static List<MapleBBSThread> getBBS(final int gid) {
            final MapleGuild g = getGuild(gid);
            if (g != null) {
                return g.getBBS();
            }
            return null;
        }
        
        public static int addBBSThread(final int guildid, final String title, final String text, final int icon, final boolean bNotice, final int posterID) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                return g.addBBSThread(title, text, icon, bNotice, posterID);
            }
            return -1;
        }
        
        public static void editBBSThread(final int guildid, final int localthreadid, final String title, final String text, final int icon, final int posterID, final int guildRank) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                g.editBBSThread(localthreadid, title, text, icon, posterID, guildRank);
            }
        }
        
        public static void deleteBBSThread(final int guildid, final int localthreadid, final int posterID, final int guildRank) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                g.deleteBBSThread(localthreadid, posterID, guildRank);
            }
        }
        
        public static void addBBSReply(final int guildid, final int localthreadid, final String text, final int posterID) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                g.addBBSReply(localthreadid, text, posterID);
            }
        }
        
        public static void deleteBBSReply(final int guildid, final int localthreadid, final int replyid, final int posterID, final int guildRank) {
            final MapleGuild g = getGuild(guildid);
            if (g != null) {
                g.deleteBBSReply(localthreadid, replyid, posterID, guildRank);
            }
        }
        
        public static void changeEmblem(final int gid, final int affectedPlayers, final MapleGuildSummary mgs) {
            Broadcast.sendGuildPacket(affectedPlayers, MaplePacketCreator.guildEmblemChange(gid, mgs.getLogoBG(), mgs.getLogoBGColor(), mgs.getLogo(), mgs.getLogoColor()), -1, gid);
            setGuildAndRank(affectedPlayers, -1, -1, -1);
        }
        
        public static void setGuildAndRank(final int cid, final int guildid, final int rank, final int alliancerank) {
            final int ch = Find.findChannel(cid);
            if (ch == -1) {
                return;
            }
            final MapleCharacter mc = World.getStorage(ch).getCharacterById(cid);
            if (mc == null) {
                return;
            }
            boolean bDifferentGuild;
            if (guildid == -1 && rank == -1) {
                bDifferentGuild = true;
            }
            else {
                bDifferentGuild = (guildid != mc.getGuildId());
                mc.setGuildId(guildid);
                mc.setGuildRank((byte)rank);
                mc.setAllianceRank((byte)alliancerank);
                mc.saveGuildStatus();
            }
            if (bDifferentGuild && ch > 0) {
                mc.getMap().broadcastMessage(mc, MaplePacketCreator.removePlayerFromMap(cid, mc), false);
                mc.getMap().broadcastMessage(mc, MaplePacketCreator.spawnPlayerMapobject(mc), false);
            }
        }
        
        static {
            guilds = new LinkedHashMap<Integer, MapleGuild>();
            Guild.lock = new ReentrantReadWriteLock();
            System.out.println("加载 家族 :::");
            final Collection<MapleGuild> allGuilds = MapleGuild.loadAll();
            for (final MapleGuild g : allGuilds) {
                if (g.isProper()) {
                    Guild.guilds.put(g.getId(), g);
                }
            }
        }
    }
    
    public static class Broadcast
    {
        public static void broadcastSmega(final byte[] message) {
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.broadcastSmega(message);
            }
        }
        
        public static void broadcastGMMessage(final byte[] message) {
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.broadcastGMMessage(message);
            }
        }
        
        public static void broadcastMessage(final byte[] message) {
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.broadcastMessage(message);
            }
        }
        
        public static void sendPacket(final List<Integer> targetIds, final MaplePacket packet, final int exception) {
            for (final int i : targetIds) {
                if (i == exception) {
                    continue;
                }
                final int ch = Find.findChannel(i);
                if (ch < 0) {
                    continue;
                }
                final MapleCharacter c = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(i);
                if (c == null) {
                    continue;
                }
                c.getClient().getSession().write(packet);
            }
        }
        
        public static void sendGuildPacket(final int targetIds, final MaplePacket packet, final int exception, final int guildid) {
            if (targetIds == exception) {
                return;
            }
            final int ch = Find.findChannel(targetIds);
            if (ch < 0) {
                return;
            }
            final MapleCharacter c = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(targetIds);
            if (c != null && c.getGuildId() == guildid) {
                c.getClient().getSession().write(packet);
            }
        }
        
        public static void sendFamilyPacket(final int targetIds, final MaplePacket packet, final int exception, final int guildid) {
            if (targetIds == exception) {
                return;
            }
            final int ch = Find.findChannel(targetIds);
            if (ch < 0) {
                return;
            }
            final MapleCharacter c = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(targetIds);
            if (c != null && c.getFamilyId() == guildid) {
                c.getClient().getSession().write(packet);
            }
        }
        
        public static void broadcastMessage(final MaplePacket serverNotice) {
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.broadcastMessage(serverNotice);
            }
        }
    }
    
    public static class Client
    {
        private static final ArrayList<MapleClient> clients;
        
        public static void addClient(final MapleClient c) {
            if (!Client.clients.contains(c)) {
                Client.clients.add(c);
            }
        }
        
        public static boolean removeClient(final MapleClient c) {
            return Client.clients.remove(c);
        }
        
        public static ArrayList<MapleClient> getClients() {
            return Client.clients;
        }
        
        static {
            clients = new ArrayList<MapleClient>();
        }
    }
    
    public static class Find
    {
        private static final ReentrantReadWriteLock lock;
        private static final HashMap<Integer, Integer> idToChannel;
        private static final HashMap<String, Integer> nameToChannel;
        
        public static void register(final int id, final String name, final int channel) {
            Find.lock.writeLock().lock();
            try {
                Find.idToChannel.put(id, channel);
                Find.nameToChannel.put(name.toLowerCase(), channel);
            }
            finally {
                Find.lock.writeLock().unlock();
            }
        }
        
        public static void forceDeregister(final int id) {
            Find.lock.writeLock().lock();
            try {
                Find.idToChannel.remove(id);
            }
            finally {
                Find.lock.writeLock().unlock();
            }
        }
        
        public static void forceDeregister(final String id) {
            Find.lock.writeLock().lock();
            try {
                Find.nameToChannel.remove(id.toLowerCase());
            }
            finally {
                Find.lock.writeLock().unlock();
            }
        }
        
        public static void forceDeregister(final int id, final String name) {
            Find.lock.writeLock().lock();
            try {
                Find.idToChannel.remove(id);
                Find.nameToChannel.remove(name.toLowerCase());
            }
            finally {
                Find.lock.writeLock().unlock();
            }
        }
        
        public static int findChannel(final int id) {
            Find.lock.readLock().lock();
            Integer ret;
            try {
                ret = Find.idToChannel.get(id);
            }
            finally {
                Find.lock.readLock().unlock();
            }
            if (ret == null) {
                return -1;
            }
            if (ret != -10 && ret != -20 && ChannelServer.getInstance(ret) == null) {
                forceDeregister(id);
                return -1;
            }
            return ret;
        }
        
        public static int findChannel(final String st) {
            Find.lock.readLock().lock();
            Integer ret;
            try {
                ret = Find.nameToChannel.get(st.toLowerCase());
            }
            finally {
                Find.lock.readLock().unlock();
            }
            if (ret == null) {
                return -1;
            }
            if (ret != -10 && ret != -20 && ChannelServer.getInstance(ret) == null) {
                forceDeregister(st);
                return -1;
            }
            return ret;
        }
        
        public static CharacterIdChannelPair[] multiBuddyFind(final int charIdFrom, final Collection<Integer> characterIds) {
            final List<CharacterIdChannelPair> foundsChars = new ArrayList<CharacterIdChannelPair>(characterIds.size());
            for (final int i : characterIds) {
                final int channel = findChannel(i);
                if (channel > 0) {
                    foundsChars.add(new CharacterIdChannelPair(i, channel));
                }
            }
            Collections.sort(foundsChars);
            return foundsChars.toArray(new CharacterIdChannelPair[foundsChars.size()]);
        }
        
        static {
            lock = new ReentrantReadWriteLock();
            idToChannel = new HashMap<Integer, Integer>();
            nameToChannel = new HashMap<String, Integer>();
        }
    }
    
    public static class Alliance
    {
        private static final Map<Integer, MapleGuildAlliance> alliances;
        private static ReentrantReadWriteLock lock;
        
        public static MapleGuildAlliance getAlliance(final int allianceid) {
            MapleGuildAlliance ret = null;
            Alliance.lock.readLock().lock();
            try {
                ret = Alliance.alliances.get(allianceid);
            }
            finally {
                Alliance.lock.readLock().unlock();
            }
            if (ret == null) {
                Alliance.lock.writeLock().lock();
                try {
                    ret = new MapleGuildAlliance(allianceid);
                    if (ret == null || ret.getId() <= 0) {
                        return null;
                    }
                    Alliance.alliances.put(allianceid, ret);
                }
                finally {
                    Alliance.lock.writeLock().unlock();
                }
            }
            return ret;
        }
        
        public static int getAllianceLeader(final int allianceid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            if (mga != null) {
                return mga.getLeaderId();
            }
            return 0;
        }
        
        public static void updateAllianceRanks(final int allianceid, final String[] ranks) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            if (mga != null) {
                mga.setRank(ranks);
            }
        }
        
        public static void updateAllianceNotice(final int allianceid, final String notice) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            if (mga != null) {
                mga.setNotice(notice);
            }
        }
        
        public static boolean canInvite(final int allianceid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.getCapacity() > mga.getNoGuilds();
        }
        
        public static boolean changeAllianceLeader(final int allianceid, final int cid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.setLeaderId(cid);
        }
        
        public static boolean changeAllianceRank(final int allianceid, final int cid, final int change) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.changeAllianceRank(cid, change);
        }
        
        public static boolean changeAllianceCapacity(final int allianceid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.setCapacity();
        }
        
        public static boolean disbandAlliance(final int allianceid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.disband();
        }
        
        public static boolean addGuildToAlliance(final int allianceid, final int gid) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.addGuild(gid);
        }
        
        public static boolean removeGuildFromAlliance(final int allianceid, final int gid, final boolean expelled) {
            final MapleGuildAlliance mga = getAlliance(allianceid);
            return mga != null && mga.removeGuild(gid, expelled);
        }
        
        public static void sendGuild(final int allianceid) {
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            if (alliance != null) {
                sendGuild(MaplePacketCreator.getAllianceUpdate(alliance), -1, allianceid);
                sendGuild(MaplePacketCreator.getGuildAlliance(alliance), -1, allianceid);
            }
        }
        
        public static void sendGuild(final MaplePacket packet, final int exceptionId, final int allianceid) {
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            if (alliance != null) {
                for (int i = 0; i < alliance.getNoGuilds(); ++i) {
                    final int gid = alliance.getGuildId(i);
                    if (gid > 0 && gid != exceptionId) {
                        Guild.guildPacket(gid, packet);
                    }
                }
            }
        }
        
        public static boolean createAlliance(final String alliancename, final int cid, final int cid2, final int gid, final int gid2) {
            final int allianceid = MapleGuildAlliance.createToDb(cid, alliancename, gid, gid2);
            if (allianceid <= 0) {
                return false;
            }
            final MapleGuild g = Guild.getGuild(gid);
            final MapleGuild g_ = Guild.getGuild(gid2);
            g.setAllianceId(allianceid);
            g_.setAllianceId(allianceid);
            g.changeARank(true);
            g_.changeARank(false);
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            sendGuild(MaplePacketCreator.createGuildAlliance(alliance), -1, allianceid);
            sendGuild(MaplePacketCreator.getAllianceInfo(alliance), -1, allianceid);
            sendGuild(MaplePacketCreator.getGuildAlliance(alliance), -1, allianceid);
            sendGuild(MaplePacketCreator.changeAlliance(alliance, true), -1, allianceid);
            return true;
        }
        
        public static void allianceChat(final int gid, final String name, final int cid, final String msg) {
            final MapleGuild g = Guild.getGuild(gid);
            if (g != null) {
                final MapleGuildAlliance ga = getAlliance(g.getAllianceId());
                if (ga != null) {
                    for (int i = 0; i < ga.getNoGuilds(); ++i) {
                        final MapleGuild g_ = Guild.getGuild(ga.getGuildId(i));
                        if (g_ != null) {
                            g_.allianceChat(name, cid, msg);
                        }
                    }
                }
            }
        }
        
        public static void setNewAlliance(final int gid, final int allianceid) {
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            final MapleGuild guild = Guild.getGuild(gid);
            if (alliance != null && guild != null) {
                for (int i = 0; i < alliance.getNoGuilds(); ++i) {
                    if (gid == alliance.getGuildId(i)) {
                        guild.setAllianceId(allianceid);
                        guild.broadcast(MaplePacketCreator.getAllianceInfo(alliance));
                        guild.broadcast(MaplePacketCreator.getGuildAlliance(alliance));
                        guild.broadcast(MaplePacketCreator.changeAlliance(alliance, true));
                        guild.changeARank();
                        guild.writeToDB(false);
                    }
                    else {
                        final MapleGuild g_ = Guild.getGuild(alliance.getGuildId(i));
                        if (g_ != null) {
                            g_.broadcast(MaplePacketCreator.addGuildToAlliance(alliance, guild));
                            g_.broadcast(MaplePacketCreator.changeGuildInAlliance(alliance, guild, true));
                        }
                    }
                }
            }
        }
        
        public static void setOldAlliance(final int gid, final boolean expelled, final int allianceid) {
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            final MapleGuild g_ = Guild.getGuild(gid);
            if (alliance != null) {
                for (int i = 0; i < alliance.getNoGuilds(); ++i) {
                    final MapleGuild guild = Guild.getGuild(alliance.getGuildId(i));
                    if (guild == null) {
                        if (gid != alliance.getGuildId(i)) {
                            alliance.removeGuild(gid, false);
                        }
                    }
                    else if (g_ == null || gid == alliance.getGuildId(i)) {
                        guild.changeARank(5);
                        guild.setAllianceId(0);
                        guild.broadcast(MaplePacketCreator.disbandAlliance(allianceid));
                    }
                    else if (g_ != null) {
                        guild.broadcast(MaplePacketCreator.serverNotice(5, "[" + g_.getName() + "] Guild has left the alliance."));
                        guild.broadcast(MaplePacketCreator.changeGuildInAlliance(alliance, g_, false));
                        guild.broadcast(MaplePacketCreator.removeGuildFromAlliance(alliance, g_, expelled));
                    }
                }
            }
            if (gid == -1) {
                Alliance.lock.writeLock().lock();
                try {
                    Alliance.alliances.remove(allianceid);
                }
                finally {
                    Alliance.lock.writeLock().unlock();
                }
            }
        }
        
        public static List<MaplePacket> getAllianceInfo(final int allianceid, final boolean start) {
            final List<MaplePacket> ret = new ArrayList<MaplePacket>();
            final MapleGuildAlliance alliance = getAlliance(allianceid);
            if (alliance != null) {
                if (start) {
                    ret.add(MaplePacketCreator.getAllianceInfo(alliance));
                    ret.add(MaplePacketCreator.getGuildAlliance(alliance));
                }
                ret.add(MaplePacketCreator.getAllianceUpdate(alliance));
            }
            return ret;
        }
        
        public static void save() {
            System.out.println("Saving alliances...");
            Alliance.lock.writeLock().lock();
            try {
                for (final MapleGuildAlliance a : Alliance.alliances.values()) {
                    a.saveToDb();
                }
            }
            finally {
                Alliance.lock.writeLock().unlock();
            }
        }
        
        static {
            alliances = new LinkedHashMap<Integer, MapleGuildAlliance>();
            Alliance.lock = new ReentrantReadWriteLock();
            System.out.println("加载 家族联盟 :::");
            final Collection<MapleGuildAlliance> allGuilds = MapleGuildAlliance.loadAll();
            for (final MapleGuildAlliance g : allGuilds) {
                Alliance.alliances.put(g.getId(), g);
            }
        }
    }
    
    public static class Family
    {
        private static final Map<Integer, MapleFamily> families;
        private static ReentrantReadWriteLock lock;
        
        public static MapleFamily getFamily(final int id) {
            MapleFamily ret = null;
            Family.lock.readLock().lock();
            try {
                ret = Family.families.get(id);
            }
            finally {
                Family.lock.readLock().unlock();
            }
            if (ret == null) {
                Family.lock.writeLock().lock();
                try {
                    ret = new MapleFamily(id);
                    if (ret == null || ret.getId() <= 0 || !ret.isProper()) {
                        return null;
                    }
                    Family.families.put(id, ret);
                }
                finally {
                    Family.lock.writeLock().unlock();
                }
            }
            return ret;
        }
        
        public static void memberFamilyUpdate(final MapleFamilyCharacter mfc, final MapleCharacter mc) {
            final MapleFamily f = getFamily(mfc.getFamilyId());
            if (f != null) {
                f.memberLevelJobUpdate(mc);
            }
        }
        
        public static void setFamilyMemberOnline(final MapleFamilyCharacter mfc, final boolean bOnline, final int channel) {
            final MapleFamily f = getFamily(mfc.getFamilyId());
            if (f != null) {
                f.setOnline(mfc.getId(), bOnline, channel);
            }
        }
        
        public static int setRep(final int fid, final int cid, final int addrep, final int oldLevel) {
            final MapleFamily f = getFamily(fid);
            if (f != null) {
                return f.setRep(cid, addrep, oldLevel);
            }
            return 0;
        }
        
        public static void save() {
            System.out.println("Saving families...");
            Family.lock.writeLock().lock();
            try {
                for (final MapleFamily a : Family.families.values()) {
                    a.writeToDB(false);
                }
            }
            finally {
                Family.lock.writeLock().unlock();
            }
        }
        
        public static void setFamily(final int familyid, final int seniorid, final int junior1, final int junior2, final int currentrep, final int totalrep, final int cid) {
            final int ch = Find.findChannel(cid);
            if (ch == -1) {
                return;
            }
            final MapleCharacter mc = World.getStorage(ch).getCharacterById(cid);
            if (mc == null) {
                return;
            }
            final boolean bDifferent = mc.getFamilyId() != familyid || mc.getSeniorId() != seniorid || mc.getJunior1() != junior1 || mc.getJunior2() != junior2;
            mc.setFamily(familyid, seniorid, junior1, junior2);
            mc.setCurrentRep(currentrep);
            mc.setTotalRep(totalrep);
            if (bDifferent) {
                mc.saveFamilyStatus();
            }
        }
        
        public static void familyPacket(final int gid, final MaplePacket message, final int cid) {
            final MapleFamily f = getFamily(gid);
            if (f != null) {
                f.broadcast(message, -1, f.getMFC(cid).getPedigree());
            }
        }
        
        public static void disbandFamily(final int gid) {
            final MapleFamily g = getFamily(gid);
            Family.lock.writeLock().lock();
            try {
                if (g != null) {
                    g.disbandFamily();
                    Family.families.remove(gid);
                }
            }
            finally {
                Family.lock.writeLock().unlock();
            }
        }
        
        static {
            families = new LinkedHashMap<Integer, MapleFamily>();
            Family.lock = new ReentrantReadWriteLock();
            System.out.println("加载 冒险学院 :::");
            final Collection<MapleFamily> allGuilds = MapleFamily.loadAll();
            for (final MapleFamily g : allGuilds) {
                if (g.isProper()) {
                    Family.families.put(g.getId(), g);
                }
            }
        }
    }
    
    public static class Respawn implements Runnable
    {
        private int numTimes;
        
        public Respawn() {
            this.numTimes = 0;
        }
        
        @Override
        public void run() {
            ++this.numTimes;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleMap map : cserv.getMapFactory().getAllMaps()) {
                    World.handleMap(map, this.numTimes, map.getCharactersSize());
                }
                for (final MapleMap map : cserv.getMapFactory().getAllInstanceMaps()) {
                    World.handleMap(map, this.numTimes, map.getCharactersSize());
                }
            }
        }
    }
}
