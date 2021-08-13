package scripting;

import client.ISkill;
import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.MapleStat;
import client.SkillEntry;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.ItemLoader;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginServer;
import handling.world.CharacterTransfer;
import handling.world.MapleMessengerCharacter;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PlayerBuffStorage;
import handling.world.World;
import handling.world.guild.MapleGuild;
import handling.world.guild.MapleGuildAlliance;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.MapleShopFactory;
import server.MapleSquad;
import server.MapleStatEffect;
import server.MerchItemPackage;
import server.Randomizer;
import server.ServerProperties;
import server.SpeedRunner;
import server.StructPotentialItem;
import server.Timer;
import server.custom.forum.Forum_Reply;
import server.custom.forum.Forum_Section;
import server.custom.forum.Forum_Thread;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MapleMonsterInformationProvider;
import server.life.MonsterDropEntry;
import server.life.MonsterGlobalDropEntry;
import server.maps.AramiaFireWorks;
import server.maps.Event_DojoAgent;
import server.maps.Event_PyramidSubway;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.SpeedRunType;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.StringUtil;
import tools.packet.PlayerShopPacket;

public class NPCConversationManager extends AbstractPlayerInteraction
{
    private final MapleClient c;
    private final int npc;
    private final int questid;
    private String getText;
    private final byte type;
    private byte lastMsg;
    public boolean pendingDisposal;
    private final Invocable iv;
    private int wh;
    
    private static MerchItemPackage loadItemFrom_Database(final int charid, final int accountid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where characterid = ? OR accountid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accountid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                return null;
            }
            final int packageid = rs.getInt("PackageId");
            final MerchItemPackage pack = new MerchItemPackage();
            pack.setPackageid(packageid);
            pack.setMesos(rs.getInt("Mesos"));
            pack.setSentTime(rs.getLong("time"));
            ps.close();
            rs.close();
            final Map<Integer, Pair<IItem, MapleInventoryType>> items = ItemLoader.HIRED_MERCHANT.loadItems(false, charid);
            if (items != null) {
                final List<IItem> iters = new ArrayList<IItem>();
                for (final Pair<IItem, MapleInventoryType> z : items.values()) {
                    iters.add(z.left);
                }
                pack.setItems(iters);
            }
            return pack;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean hairExists(final int hair) {
        return MapleItemInformationProvider.getInstance().hairExists(hair);
    }
    
    public static boolean faceExists(final int face) {
        return MapleItemInformationProvider.getInstance().faceExists(face);
    }
    
    public NPCConversationManager(final MapleClient c, final int npc, final int questid, final byte type, final Invocable iv, final int wh) {
        super(c);
        this.lastMsg = -1;
        this.pendingDisposal = false;
        this.wh = 0;
        this.c = c;
        this.npc = npc;
        this.questid = questid;
        this.type = type;
        this.iv = iv;
        this.wh = wh;
    }
    
    public int getwh() {
        return this.wh;
    }
    
    public String ms() {
        final String result = "缘分";
        return result;
    }
    
    public Invocable getIv() {
        return this.iv;
    }
    
    public String serverName() {
        return this.c.getChannelServer().getServerName();
    }
    
    public int getNpc() {
        return this.npc;
    }
    
    public int getQuest() {
        return this.questid;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public void safeDispose() {
        this.pendingDisposal = true;
    }
    
    public void dispose() {
        NPCScriptManager.getInstance().dispose(this.c);
    }
    
    public void askMapSelection(final String sel) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getMapSelection(this.npc, sel));
        this.lastMsg = 13;
    }
    
    public void sendNext(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendNextS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", type));
        this.lastMsg = 0;
    }
    
    public void sendPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", type));
        this.lastMsg = 0;
    }
    
    public void sendNextPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void PlayerToNpc(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", type));
        this.lastMsg = 0;
    }
    
    public void sendOk(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendOkS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", type));
        this.lastMsg = 0;
    }
    
    public void sendYesNo(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", (byte)0));
        this.lastMsg = 1;
    }
    
    public void sendYesNoS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", type));
        this.lastMsg = 1;
    }
    
    public void sendAcceptDecline(final String text) {
        this.askAcceptDecline(text);
    }
    
    public void sendAcceptDeclineNoESC(final String text) {
        this.askAcceptDeclineNoESC(text);
    }
    
    public void askAcceptDecline(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)11, text, "", (byte)0));
        this.lastMsg = 11;
    }
    
    public void askAcceptDeclineNoESC(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)12, text, "", (byte)0));
        this.lastMsg = 12;
    }
    
    public void askAvatar(final String text, final int card, final int... args) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkStyle(this.npc, text, card, args));
        this.lastMsg = 7;
    }
    
    public void sendSimple(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains("#L")) {
            this.sendNext(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", (byte)0));
        this.lastMsg = 4;
    }
    
    public void sendSimple(final String text, final int speaker) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains("#L")) {
            this.sendNext(text);
            return;
        }
        this.getClient().getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", (byte)speaker));
        this.lastMsg = 4;
    }
    
    public void sendSimpleS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains("#L")) {
            this.sendNextS(text, type);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", type));
        this.lastMsg = 4;
    }
    
    public void sendStyle(final String text, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkStyle(this.npc, text, 0, styles));
        this.lastMsg = 9;
    }
    
    public void sendStyle(final String text, final int caid, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkStyle(this.npc, text, caid, styles));
        this.lastMsg = 7;
    }
    
    public void sendGetNumber(final String text, final int def, final int min, final int max) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkNum(this.npc, text, def, min, max));
        this.lastMsg = 3;
    }
    
    public void sendGetText(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkText(this.npc, text));
        this.lastMsg = 2;
    }
    
    public void setGetText(final String text) {
        this.getText = text;
    }
    
    public String getText() {
        return this.getText;
    }
    
    public void setHair(final int hair) {
        this.getPlayer().setHair(hair);
        this.getPlayer().updateSingleStat(MapleStat.HAIR, hair);
        this.getPlayer().equipChanged();
    }
    
    public void setFace(final int face) {
        this.getPlayer().setFace(face);
        this.getPlayer().updateSingleStat(MapleStat.FACE, face);
        this.getPlayer().equipChanged();
    }
    
    public void setSkin(final int color) {
        this.getPlayer().setSkinColor((byte)color);
        this.getPlayer().updateSingleStat(MapleStat.SKIN, color);
        this.getPlayer().equipChanged();
    }
    
    public int setRandomAvatar(final int ticket, final int[] args_all) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        final int args = args_all[Randomizer.nextInt(args_all.length)];
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public int setAvatar(final int ticket, final int args) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public void sendStorage() {
        this.c.getPlayer().setConversation(4);
        this.c.getPlayer().getStorage().sendStorage(this.c, this.npc);
    }
    
    public void openShop(final int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(this.c);
    }
    
    public int gainGachaponItem(final int id, final int quantity) {
        return this.gainGachaponItem(id, quantity, this.c.getPlayer().getMap().getStreetName() + " - " + this.c.getPlayer().getMap().getMapName());
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness > 0) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 恭喜获得道具!", item, rareness, this.getPlayer().getClient().getChannel()).getBytes());
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg, final int 概率) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            if (概率 > 0) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得!大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()).getBytes());
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    
    public void changeJob(final int job) {
        this.c.getPlayer().changeJob(job);
    }
    
    public void startQuest(final int id) {
        MapleQuest.getInstance(id).start(this.getPlayer(), this.npc);
    }
    
    @Override
    public void completeQuest(final int id) {
        MapleQuest.getInstance(id).complete(this.getPlayer(), this.npc);
    }
    
    public void forfeitQuest(final int id) {
        MapleQuest.getInstance(id).forfeit(this.getPlayer());
    }
    
    public void forceStartQuest() {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    @Override
    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    public void forceStartQuest(final String customData) {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), customData);
    }
    
    public void completeQuest() {
        this.forceCompleteQuest();
    }
    
    public void forceCompleteQuest() {
        MapleQuest.getInstance(this.questid).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    @Override
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    public String getQuestCustomData() {
        return this.c.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).getCustomData();
    }
    
    public void setQuestCustomData(final String customData) {
        this.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).setCustomData(customData);
    }
    
    public int getLevel() {
        return this.getPlayer().getLevel();
    }
    
    public int getJobId() {
        return this.getPlayer().getJob();
    }
    
    public void changeJobById(final short job) {
        this.c.getPlayer().changeJob(job);
    }
    
    public int getMeso() {
        return this.getPlayer().getMeso();
    }
    
    public void gainAp(final int amount) {
        this.c.getPlayer().gainAp((short)amount);
    }
    
    public void expandInventory(final byte type, final int amt) {
        this.c.getPlayer().expandInventory(type, amt);
    }
    
    public void unequipEverything() {
        final MapleInventory equipped = this.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<Short> ids = new LinkedList<Short>();
        for (final IItem item : equipped.list()) {
            ids.add(item.getPosition());
        }
        for (final short id : ids) {
            MapleInventoryManipulator.unequip(this.getC(), id, equip.getNextFreeSlot());
        }
    }
    
    public void clearSkills() {
        final Map<ISkill, SkillEntry> skills = this.getPlayer().getSkills();
        for (final Map.Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            this.getPlayer().changeSkillLevel(skill.getKey(), (byte)0, (byte)0);
        }
    }
    
    public boolean hasSkill(final int skillid) {
        final ISkill theSkill = SkillFactory.getSkill(skillid);
        return theSkill != null && this.c.getPlayer().getSkillLevel(theSkill) > 0;
    }
    
    public void showEffect(final boolean broadcast, final String effect) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect(effect));
        }
        else {
            this.c.getSession().write(MaplePacketCreator.showEffect(effect));
        }
    }
    
    public void playSound(final boolean broadcast, final String sound) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.playSound(sound));
        }
        else {
            this.c.getSession().write(MaplePacketCreator.playSound(sound));
        }
    }
    
    public void environmentChange(final boolean broadcast, final String env) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.environmentChange(env, 2));
        }
        else {
            this.c.getSession().write(MaplePacketCreator.environmentChange(env, 2));
        }
    }
    
    public void updateBuddyCapacity(final int capacity) {
        this.c.getPlayer().setBuddyCapacity((byte)capacity);
    }
    
    public int getBuddyCapacity() {
        return this.c.getPlayer().getBuddyCapacity();
    }
    
    public int partyMembersInMap() {
        int inMap = 0;
        for (final MapleCharacter char2 : this.getPlayer().getMap().getCharactersThreadsafe()) {
            if (char2.getParty() == this.getPlayer().getParty()) {
                ++inMap;
            }
        }
        return inMap;
    }
    
    public List<MapleCharacter> getPartyMembers() {
        if (this.getPlayer().getParty() == null) {
            return null;
        }
        final List<MapleCharacter> chars = new LinkedList<MapleCharacter>();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            for (final ChannelServer channel : ChannelServer.getAllInstances()) {
                final MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
                if (ch != null) {
                    chars.add(ch);
                }
            }
        }
        return chars;
    }
    
    public void warpPartyWithExp(final int mapId, final int exp) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
            }
        }
    }
    
    public void warpPartyWithExpMeso(final int mapId, final int exp, final int meso) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
                curChar.gainMeso(meso, true);
            }
        }
    }
    
    public MapleSquad getSquad(final String type) {
        return this.c.getChannelServer().getMapleSquad(type);
    }
    
    public int getSquadAvailability(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        return squad.getStatus();
    }
    
    public boolean registerSquad(final String type, final int minutes, final String startText) {
        if (this.c.getChannelServer().getMapleSquad(type) == null) {
            final MapleSquad squad = new MapleSquad(this.c.getChannel(), type, this.c.getPlayer(), minutes * 60 * 1000, startText);
            final boolean ret = this.c.getChannelServer().addMapleSquad(squad, type);
            if (ret) {
                final MapleMap map = this.c.getPlayer().getMap();
                map.broadcastMessage(MaplePacketCreator.getClock(minutes * 60));
                map.broadcastMessage(MaplePacketCreator.serverNotice(6, this.c.getPlayer().getName() + startText));
            }
            else {
                squad.clear();
            }
            return ret;
        }
        return false;
    }
    
    public boolean getSquadList(final String type, final byte type_) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return false;
        }
        switch (type_) {
            case 0:
            case 3: {
                this.sendNext(squad.getSquadMemberString(type_));
                break;
            }
            case 1: {
                this.sendSimple(squad.getSquadMemberString(type_));
                break;
            }
            case 2: {
                if (squad.getBannedMemberSize() > 0) {
                    this.sendSimple(squad.getSquadMemberString(type_));
                    break;
                }
                this.sendNext(squad.getSquadMemberString(type_));
                break;
            }
        }
        return true;
    }
    
    public byte isSquadLeader(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getLeader() != null && squad.getLeader().getId() == this.c.getPlayer().getId()) {
            return 1;
        }
        return 0;
    }
    
    public boolean reAdd(final String eim, final String squad) {
        final EventInstanceManager eimz = this.getDisconnected(eim);
        final MapleSquad squadz = this.getSquad(squad);
        if (eimz != null && squadz != null) {
            squadz.reAddMember(this.getPlayer());
            eimz.registerPlayer(this.getPlayer());
            return true;
        }
        return false;
    }
    
    public void banMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.banMember(pos);
        }
    }
    
    public void acceptMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.acceptMember(pos);
        }
    }
    
    public String getReadableMillis(final long startMillis, final long endMillis) {
        return StringUtil.getReadableMillis(startMillis, endMillis);
    }
    
    public int addMember(final String type, final boolean join) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            return squad.addMember(this.c.getPlayer(), join);
        }
        return -1;
    }
    
    public byte isSquadMember(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getMembers().contains(this.c.getPlayer())) {
            return 1;
        }
        if (squad.isBanned(this.c.getPlayer())) {
            return 2;
        }
        return 0;
    }
    
    public void resetReactors() {
        this.getPlayer().getMap().resetReactors();
    }
    
    public void genericGuildMessage(final int code) {
        this.c.getSession().write(MaplePacketCreator.genericGuildMessage((byte)code));
    }
    
    public void disbandGuild() {
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0 || this.c.getPlayer().getGuildRank() != 1) {
            return;
        }
        World.Guild.disbandGuild(gid);
    }
    
    public void increaseGuildCapacity() {
        if (this.c.getPlayer().getMeso() < 2500000) {
            this.c.getSession().write(MaplePacketCreator.serverNotice(1, "你没有足够的金币."));
            return;
        }
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0) {
            return;
        }
        World.Guild.increaseGuildCapacity(gid);
        this.c.getPlayer().gainMeso(-2500000, true, false, true);
    }
    
    public void increaseCharacterSlots(final int price) {
        final int useNX = 1;
        final int slots = this.c.getCharacterSlots();
        if (slots >= LoginServer.getMaxCharacters()) {
            this.c.getPlayer().dropMessage(1, "角色列表已满无法增加！");
            return;
        }
        if (this.c.getPlayer().getCSPoints(useNX) < price) {
            return;
        }
        this.c.getPlayer().modifyCSPoints(useNX, -price, false);
        if (this.c.gainCharacterSlot()) {
            this.c.getPlayer().dropMessage(1, "角色列表已增加到：" + this.c.getCharacterSlots() + "个");
        }
    }
    
    public void displayGuildRanks() {
        this.c.getSession().write(MaplePacketCreator.showGuildRanks(this.npc, MapleGuildRanking.getInstance().getGuildRank()));
    }
    
    public boolean removePlayerFromInstance() {
        if (this.c.getPlayer().getEventInstance() != null) {
            this.c.getPlayer().getEventInstance().removePlayer(this.c.getPlayer());
            return true;
        }
        return false;
    }
    
    public boolean isPlayerInstance() {
        return this.c.getPlayer().getEventInstance() != null;
    }
    
    public void changeStat(final byte slot, final int type, final short amount) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slot);
        switch (type) {
            case 0: {
                sel.setStr(amount);
                break;
            }
            case 1: {
                sel.setDex(amount);
                break;
            }
            case 2: {
                sel.setInt(amount);
                break;
            }
            case 3: {
                sel.setLuk(amount);
                break;
            }
            case 4: {
                sel.setHp(amount);
                break;
            }
            case 5: {
                sel.setMp(amount);
                break;
            }
            case 6: {
                sel.setWatk(amount);
                break;
            }
            case 7: {
                sel.setMatk(amount);
                break;
            }
            case 8: {
                sel.setWdef(amount);
                break;
            }
            case 9: {
                sel.setMdef(amount);
                break;
            }
            case 10: {
                sel.setAcc(amount);
                break;
            }
            case 11: {
                sel.setAvoid(amount);
                break;
            }
            case 12: {
                sel.setHands(amount);
                break;
            }
            case 13: {
                sel.setSpeed(amount);
                break;
            }
            case 14: {
                sel.setJump(amount);
                break;
            }
            case 15: {
                sel.setUpgradeSlots((byte)amount);
                break;
            }
            case 16: {
                sel.setViciousHammer((byte)amount);
                break;
            }
            case 17: {
                sel.setLevel((byte)amount);
                break;
            }
            case 18: {
                sel.setEnhance((byte)amount);
                break;
            }
            case 19: {
                sel.setPotential1(amount);
                break;
            }
            case 20: {
                sel.setPotential2(amount);
                break;
            }
            case 21: {
                sel.setPotential3(amount);
                break;
            }
            case 22: {
                sel.setOwner(this.getText());
                break;
            }
        }
        this.c.getPlayer().equipChanged();
    }
    
    public void killAllMonsters() {
        final MapleMap map = this.c.getPlayer().getMap();
        final double range = Double.POSITIVE_INFINITY;
        for (final MapleMapObject monstermo : map.getMapObjectsInRange(this.c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
            final MapleMonster mob = (MapleMonster)monstermo;
            if (mob.getStats().isBoss()) {
                map.killMonster(mob, this.c.getPlayer(), false, false, (byte)1);
            }
        }
    }
    
    public void giveMerchantMesos() {
        long mesos = 0L;
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM hiredmerchants WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
            }
            else {
                mesos = rs.getLong("mesos");
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("UPDATE hiredmerchants SET mesos = 0 WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
        }
        this.c.getPlayer().gainMeso((int)mesos, true);
    }
    
    public void dc() {
        final MapleCharacter victim = this.c.getChannelServer().getPlayerStorage().getCharacterByName(this.c.getPlayer().getName());
        victim.getClient().getSession().close(true);
        victim.getClient().disconnect(true, false);
    }
    
    public long getMerchantMesos() {
        long mesos = 0L;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM hiredmerchants WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
            }
            else {
                mesos = rs.getLong("mesos");
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
        }
        return mesos;
    }
    
    public void openDuey() {
        this.c.getPlayer().setConversation(2);
        this.c.getSession().write(MaplePacketCreator.sendDuey((byte)9, null));
    }
    
    public void openMerchantItemStore() {
        this.c.getPlayer().setConversation(3);
        this.c.getSession().write(PlayerShopPacket.merchItemStore((byte)34));
    }
    
    public void openMerchantItemStore1() {
        final MerchItemPackage pack = loadItemFrom_Database(this.c.getPlayer().getId(), this.c.getPlayer().getAccountID());
        this.c.getSession().write(PlayerShopPacket.merchItemStore_ItemData(pack));
    }
    
    public int getDojoPoints() {
        return this.c.getPlayer().getDojo();
    }
    
    public int getDojoRecord() {
        return this.c.getPlayer().getDojoRecord();
    }
    
    public void setDojoRecord(final boolean reset) {
        this.c.getPlayer().setDojoRecord(reset);
    }
    
    public boolean start_DojoAgent(final boolean dojo, final boolean party) {
        if (dojo) {
            return Event_DojoAgent.warpStartDojo(this.c.getPlayer(), party);
        }
        return Event_DojoAgent.warpStartAgent(this.c.getPlayer(), party);
    }
    
    public boolean start_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpStartPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpStartSubway(this.c.getPlayer());
    }
    
    public boolean bonus_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpBonusPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpBonusSubway(this.c.getPlayer());
    }
    
    public short getKegs() {
        return AramiaFireWorks.getInstance().getKegsPercentage();
    }
    
    public void giveKegs(final int kegs) {
        AramiaFireWorks.getInstance().giveKegs(this.c.getPlayer(), kegs);
    }
    
    public short getSunshines() {
        return AramiaFireWorks.getInstance().getSunsPercentage();
    }
    
    public void addSunshines(final int kegs) {
        AramiaFireWorks.getInstance().giveSuns(this.c.getPlayer(), kegs);
    }
    
    public short getDecorations() {
        return AramiaFireWorks.getInstance().getDecsPercentage();
    }
    
    public void addDecorations(final int kegs) {
        try {
            AramiaFireWorks.getInstance().giveDecs(this.c.getPlayer(), kegs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public MapleInventory getInventory(final int type) {
        return this.c.getPlayer().getInventory(MapleInventoryType.getByType((byte)type));
    }
    
    public MapleCarnivalParty getCarnivalParty() {
        return this.c.getPlayer().getCarnivalParty();
    }
    
    public MapleCarnivalChallenge getNextCarnivalRequest() {
        return this.c.getPlayer().getNextCarnivalRequest();
    }
    
    public MapleCarnivalChallenge getCarnivalChallenge(final MapleCharacter chr) {
        return new MapleCarnivalChallenge(chr);
    }
    
    public void setHP(final short hp) {
        this.c.getPlayer().getStat().setHp(hp);
    }
    
    public void maxStats() {
        final List<Pair<MapleStat, Integer>> statup = new ArrayList<Pair<MapleStat, Integer>>(2);
        this.c.getPlayer().getStat().setStr((short)32767);
        this.c.getPlayer().getStat().setDex((short)32767);
        this.c.getPlayer().getStat().setInt((short)32767);
        this.c.getPlayer().getStat().setLuk((short)32767);
        this.c.getPlayer().getStat().setMaxHp((short)30000);
        this.c.getPlayer().getStat().setMaxMp((short)30000);
        this.c.getPlayer().getStat().setHp(30000);
        this.c.getPlayer().getStat().setMp(30000);
        statup.add(new Pair<MapleStat, Integer>(MapleStat.STR, 32767));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.DEX, 32767));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.LUK, 32767));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.INT, 32767));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.HP, 30000));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXHP, 30000));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MP, 30000));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXMP, 30000));
        this.c.getSession().write(MaplePacketCreator.updatePlayerStats(statup, this.c.getPlayer().getJob()));
    }
    
    public Pair<String, Map<Integer, String>> getSpeedRun(final String typ) {
        final SpeedRunType type = SpeedRunType.valueOf(typ);
        if (SpeedRunner.getInstance().getSpeedRunData(type) != null) {
            return SpeedRunner.getInstance().getSpeedRunData(type);
        }
        return new Pair<String, Map<Integer, String>>("", new HashMap<Integer, String>());
    }
    
    public boolean getSR(final Pair<String, Map<Integer, String>> ma, final int sel) {
        if (ma.getRight().get(sel) == null || ma.getRight().get(sel).length() <= 0) {
            this.dispose();
            return false;
        }
        this.sendOk(ma.getRight().get(sel));
        return true;
    }
    
    public Equip getEquip(final int itemid) {
        return (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemid);
    }
    
    public void setExpiration(final Object statsSel, final long expire) {
        if (statsSel instanceof Equip) {
            ((Equip)statsSel).setExpiration(System.currentTimeMillis() + expire * 24L * 60L * 60L * 1000L);
        }
    }
    
    public void setLock(final Object statsSel) {
        if (statsSel instanceof Equip) {
            final Equip eq = (Equip)statsSel;
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
        }
    }
    
    public boolean addFromDrop(final Object statsSel) {
        if (statsSel instanceof IItem) {
            final IItem it = (IItem)statsSel;
            return MapleInventoryManipulator.checkSpace(this.getClient(), it.getItemId(), it.getQuantity(), it.getOwner()) && MapleInventoryManipulator.addFromDrop(this.getClient(), it, false);
        }
        return false;
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type) {
        return this.replaceItem(slot, invType, statsSel, offset, type, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type, final boolean takeSlot) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        if (inv == null) {
            return false;
        }
        IItem item = this.getPlayer().getInventory(inv).getItem((byte)slot);
        if (item == null || statsSel instanceof IItem) {
            item = (IItem)statsSel;
        }
        if (offset > 0) {
            if (inv != MapleInventoryType.EQUIP) {
                return false;
            }
            final Equip eq = (Equip)item;
            if (takeSlot) {
                if (eq.getUpgradeSlots() < 1) {
                    return false;
                }
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() - 1));
            }
            if (type.equalsIgnoreCase("Slots")) {
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() + offset));
            }
            else if (type.equalsIgnoreCase("Level")) {
                eq.setLevel((byte)(eq.getLevel() + offset));
            }
            else if (type.equalsIgnoreCase("Hammer")) {
                eq.setViciousHammer((byte)(eq.getViciousHammer() + offset));
            }
            else if (type.equalsIgnoreCase("STR")) {
                eq.setStr((short)(eq.getStr() + offset));
            }
            else if (type.equalsIgnoreCase("DEX")) {
                eq.setDex((short)(eq.getDex() + offset));
            }
            else if (type.equalsIgnoreCase("INT")) {
                eq.setInt((short)(eq.getInt() + offset));
            }
            else if (type.equalsIgnoreCase("LUK")) {
                eq.setLuk((short)(eq.getLuk() + offset));
            }
            else if (type.equalsIgnoreCase("HP")) {
                eq.setHp((short)(eq.getHp() + offset));
            }
            else if (type.equalsIgnoreCase("MP")) {
                eq.setMp((short)(eq.getMp() + offset));
            }
            else if (type.equalsIgnoreCase("WATK")) {
                eq.setWatk((short)(eq.getWatk() + offset));
            }
            else if (type.equalsIgnoreCase("MATK")) {
                eq.setMatk((short)(eq.getMatk() + offset));
            }
            else if (type.equalsIgnoreCase("WDEF")) {
                eq.setWdef((short)(eq.getWdef() + offset));
            }
            else if (type.equalsIgnoreCase("MDEF")) {
                eq.setMdef((short)(eq.getMdef() + offset));
            }
            else if (type.equalsIgnoreCase("ACC")) {
                eq.setAcc((short)(eq.getAcc() + offset));
            }
            else if (type.equalsIgnoreCase("Avoid")) {
                eq.setAvoid((short)(eq.getAvoid() + offset));
            }
            else if (type.equalsIgnoreCase("Hands")) {
                eq.setHands((short)(eq.getHands() + offset));
            }
            else if (type.equalsIgnoreCase("Speed")) {
                eq.setSpeed((short)(eq.getSpeed() + offset));
            }
            else if (type.equalsIgnoreCase("Jump")) {
                eq.setJump((short)(eq.getJump() + offset));
            }
            else if (type.equalsIgnoreCase("ItemEXP")) {
                eq.setItemEXP(eq.getItemEXP() + offset);
            }
            else if (type.equalsIgnoreCase("Expiration")) {
                eq.setExpiration(eq.getExpiration() + offset);
            }
            else if (type.equalsIgnoreCase("Flag")) {
                eq.setFlag((byte)(eq.getFlag() + offset));
            }
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
            item = eq.copy();
        }
        MapleInventoryManipulator.removeFromSlot(this.getClient(), inv, (short)slot, item.getQuantity(), false);
        return MapleInventoryManipulator.addFromDrop(this.getClient(), item, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int upgradeSlots) {
        return this.replaceItem(slot, invType, statsSel, upgradeSlots, "Slots");
    }
    
    public boolean isCash(final int itemId) {
        return MapleItemInformationProvider.getInstance().isCash(itemId);
    }
    
    public void buffGuild(final int buff, final int duration, final String msg) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.getItemEffect(buff) != null && this.getPlayer().getGuildId() > 0) {
            final MapleStatEffect mse = ii.getItemEffect(buff);
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr.getGuildId() == this.getPlayer().getGuildId()) {
                        mse.applyTo(chr, chr, true, null, duration);
                        chr.dropMessage(5, "Your guild has gotten a " + msg + " buff.");
                    }
                }
            }
        }
    }
    
    public boolean createAlliance(final String alliancename) {
        final MapleParty pt = this.c.getPlayer().getParty();
        final MapleCharacter otherChar = this.c.getChannelServer().getPlayerStorage().getCharacterById(pt.getMemberByIndex(1).getId());
        if (otherChar == null || otherChar.getId() == this.c.getPlayer().getId()) {
            return false;
        }
        try {
            return World.Alliance.createAlliance(alliancename, this.c.getPlayer().getId(), otherChar.getId(), this.c.getPlayer().getGuildId(), otherChar.getGuildId());
        }
        catch (Exception re) {
            re.printStackTrace();
            return false;
        }
    }
    
    public boolean addCapacityToAlliance() {
        try {
            final MapleGuild gs = World.Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && World.Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && World.Alliance.changeAllianceCapacity(gs.getAllianceId())) {
                this.gainMeso(-MapleGuildAlliance.CHANGE_CAPACITY_COST);
                return true;
            }
        }
        catch (Exception re) {
            re.printStackTrace();
        }
        return false;
    }
    
    public boolean disbandAlliance() {
        try {
            final MapleGuild gs = World.Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && World.Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && World.Alliance.disbandAlliance(gs.getAllianceId())) {
                return true;
            }
        }
        catch (Exception re) {
            re.printStackTrace();
        }
        return false;
    }
    
    public byte getLastMsg() {
        return this.lastMsg;
    }
    
    public void setLastMsg(final byte last) {
        this.lastMsg = last;
    }
    
    public void maxAllSkills() {
        for (final ISkill skil : SkillFactory.getAllSkills()) {
            if (GameConstants.isApplicableSkill(skil.getId())) {
                this.teachSkill(skil.getId(), skil.getMaxLevel(), skil.getMaxLevel());
            }
        }
    }
    
    public void resetStats(final int str, final int dex, final int z, final int luk) {
        this.c.getPlayer().resetStats(str, dex, z, luk);
    }
    
    public boolean dropItem(final int slot, final int invType, final int quantity) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        return inv != null && MapleInventoryManipulator.drop(this.c, inv, (short)slot, (short)quantity, true);
    }
    
    public List<Integer> getAllPotentialInfo() {
        return new ArrayList<Integer>(MapleItemInformationProvider.getInstance().getAllPotentialInfo().keySet());
    }
    
    public String getPotentialInfo(final int id) {
        final List<StructPotentialItem> potInfo = MapleItemInformationProvider.getInstance().getPotentialInfo(id);
        final StringBuilder builder = new StringBuilder("#b#ePOTENTIAL INFO FOR ID: ");
        builder.append(id);
        builder.append("#n#k\r\n\r\n");
        int minLevel = 1;
        int maxLevel = 10;
        for (final StructPotentialItem item : potInfo) {
            builder.append("#eLevels ");
            builder.append(minLevel);
            builder.append("~");
            builder.append(maxLevel);
            builder.append(": #n");
            builder.append(item.toString());
            minLevel += 10;
            maxLevel += 10;
            builder.append("\r\n");
        }
        return builder.toString();
    }
    
    public void sendRPS() {
        this.c.getSession().write(MaplePacketCreator.getRPSMode((byte)8, -1, -1, -1));
    }
    
    public void setQuestRecord(final Object ch, final int questid, final String data) {
        ((MapleCharacter)ch).getQuestNAdd(MapleQuest.getInstance(questid)).setCustomData(data);
    }
    
    public void doWeddingEffect2(final Object ch) {
        final MapleCharacter chr = (MapleCharacter)ch;
        this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(this.getPlayer().getName() + ", 你愿意娶 " + chr.getName() + " 为妻吗？无论她将来是富有还是贫穷、或无论她将来身体健康或不适，你都愿意和她永远在一起吗？"));
        Timer.CloneTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(chr.getName() + ", 你愿意嫁给 " + NPCConversationManager.this.getPlayer().getName() + " 吗？无论他将来是富有还是贫穷、或无论他将来身体健康或不适，你都愿意和他永远在一起吗?"));
                }
            }
        }, 10000L);
        Timer.CloneTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    if (NPCConversationManager.this.getPlayer() != null) {
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "3");
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160002, "0");
                    }
                    else if (chr != null) {
                        NPCConversationManager.this.setQuestRecord(chr, 160001, "3");
                        NPCConversationManager.this.setQuestRecord(chr, 160002, "0");
                    }
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "2");
                    NPCConversationManager.this.setQuestRecord(chr, 160001, "2");
                    NPCConversationManager.this.sendNPCText("好，我以圣灵、圣父、圣子的名义宣布：" + NPCConversationManager.this.getPlayer().getName() + " 和 " + chr.getName() + ", 结为夫妻。 希望你们在!" + chr.getClient().getChannelServer().getServerName() + " 游戏中玩的愉快!", 9201002);
                    NPCConversationManager.this.getMap().startExtendedMapEffect("You may now kiss the bride, " + NPCConversationManager.this.getPlayer().getName() + "!", 5120006);
                    if (chr.getGuildId() > 0) {
                        World.Guild.guildPacket(chr.getGuildId(), MaplePacketCreator.sendMarriage(false, chr.getName()));
                    }
                    if (chr.getFamilyId() > 0) {
                        World.Family.familyPacket(chr.getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), chr.getId());
                    }
                    if (NPCConversationManager.this.getPlayer().getGuildId() > 0) {
                        World.Guild.guildPacket(NPCConversationManager.this.getPlayer().getGuildId(), MaplePacketCreator.sendMarriage(false, NPCConversationManager.this.getPlayer().getName()));
                    }
                    if (NPCConversationManager.this.getPlayer().getFamilyId() > 0) {
                        World.Family.familyPacket(NPCConversationManager.this.getPlayer().getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), NPCConversationManager.this.getPlayer().getId());
                    }
                }
            }
        }, 20000L);
    }
    
    public void openDD(final int type) {
        this.c.getSession().write(MaplePacketCreator.openBeans(this.getPlayer().getBeans(), type));
    }
    
    public void worldMessage(final String text) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, text).getBytes());
    }
    
    public int getBeans() {
        return this.getClient().getPlayer().getBeans();
    }
    
    public void gainBeans(final int s) {
        this.getPlayer().gainBeans(s);
        this.c.getSession().write(MaplePacketCreator.updateBeans(this.c.getPlayer().getId(), s));
    }
    
    public int getHyPay(final int type) {
        return this.getPlayer().getHyPay(type);
    }
    
    public void szhs(final String ss) {
        this.c.getSession().write(MaplePacketCreator.游戏屏幕中间黄色字体(ss));
    }
    
    public void szhs(final String ss, final int id) {
        this.c.getSession().write(MaplePacketCreator.游戏屏幕中间黄色字体(ss, id));
    }
    
    public int gainHyPay(final int hypay) {
        return this.getPlayer().gainHyPay(hypay);
    }
    
    public int addHyPay(final int hypay) {
        return this.getPlayer().addHyPay(hypay);
    }
    
    public int delPayReward(final int pay) {
        return this.getPlayer().delPayReward(pay);
    }
    
    public int getItemLevel(final int id) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ii.getReqLevel(id);
    }
    
    public void alatPQ() {
    }
    
    public void xlkc(final long days) {
        final MapleQuestStatus marr = this.getPlayer().getQuestNoAdd(MapleQuest.getInstance(122700));
        if (marr != null && marr.getCustomData() != null && Long.parseLong(marr.getCustomData()) >= System.currentTimeMillis()) {
            this.getPlayer().dropMessage(1, "项链扩充失败，您已经进行过项链扩充。");
        }
        else {
            final String customData = String.valueOf(System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L);
            this.getPlayer().getQuestNAdd(MapleQuest.getInstance(122700)).setCustomData(customData);
            this.getPlayer().dropMessage(1, "项链" + days + "扩充扩充成功！");
        }
    }
    
    public String checkDrop(final int mobId) {
        int rate = this.getClient().getChannelServer().getDropRate();
        final MapleMonster mob = MapleLifeFactory.getMonster(mobId);
        if (MapleLifeFactory.getMonster(mobId) != null && mob.getStats().isBoss()) {
            rate = this.getClient().getChannelServer().getBossDropRate();
        }
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            int itemId = 0;
            int ch = 0;
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterDropEntry de = ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    if (ii.itemExists(itemId)) {
                        if (num == 0) {
                            name.append("当前怪物 #o").append(mobId).append("# 的爆率为:\r\n");
                            name.append("--------------------------------------\r\n");
                        }
                        String namez = "#z" + itemId + "#";
                        if (itemId == 0) {
                            itemId = 4031041;
                            namez = de.Minimum * this.getClient().getChannelServer().getMesoRate() + " - " + de.Maximum * this.getClient().getChannelServer().getMesoRate() + " 的金币";
                        }
                        ch = de.chance * rate;
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append(" - ").append(((ch >= 999999) ? 1000000 : ch) / 10000.0).append("%的爆率. ").append((de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0) ? ("需要接受任务: " + MapleQuest.getInstance(de.questid).getName()) : "").append("\r\n");
                        ++num;
                    }
                }
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "没有找到这个怪物的爆率数据。";
    }
    
    public String checkDropper(final int itemid) {
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        final List<Integer> ret = new LinkedList<Integer>();
        try {
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
            ps.setInt(1, itemid);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int dropperid = rs.getInt("dropperid");
                ret.add(dropperid);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("[database error]" + e);
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final StringBuilder name = new StringBuilder();
        if (ret.size() > 0) {
            name.append("    当前物品 #e#b#v").append(itemid).append("##z").append(itemid).append("##k#n 的掉落为：#e\r\n");
            for (int i = 0; i < ret.size(); ++i) {
                name.append("    #o").append((int)ret.get(i)).append("#\r\n");
            }
        }
        if (name.length() > 0) {
            return name.toString();
        }
        return "没有找到这个物品的爆率数据。";
    }

    public String checkMapDrop() {
        List<MonsterGlobalDropEntry> ranks = new ArrayList(MapleMonsterInformationProvider.getInstance().getGlobalDrop());
        int mapid = this.c.getPlayer().getMap().getId();
        int cashServerRate = getClient().getChannelServer().getCashRate();
        int globalServerRate = 1;
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); i++) {
                MonsterGlobalDropEntry de = ranks.get(i);
                if (de.continent < 0 || (de.continent < 10 && mapid / 100000000 == de.continent) || (de.continent < 100 && mapid / 10000000 == de.continent) || (de.continent < 1000 && mapid / 1000000 == de.continent)) {
                    int itemId = de.itemId;
                    if (num == 0) {
                        name.append("当前地图 #r").append(mapid).append("#k - #m").append(mapid).append("# 的全局爆率为:");
                        name.append("\r\n--------------------------------------\r\n");
                    }
                    String names = "#z" + itemId + "#";
                    if (itemId == 0 && cashServerRate != 0) {
                        itemId = 4031041;
                        names = (de.Minimum * cashServerRate) + " - " + (de.Maximum * cashServerRate) + " 的抵用卷";
                    }
                    int chance = de.chance * globalServerRate;
                    if (getPlayer().isAdmin()) {
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(names).append(" - ").append(Integer.valueOf((chance >= 999999) ? 1000000 : chance).doubleValue() / 10000.0D).append("%的爆率. ").append((de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0) ? ("需要接受任务: " + MapleQuest.getInstance(de.questid).getName()) : "").append("\r\n");
                    } else {
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(names).append((de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0) ? ("需要接受任务: " + MapleQuest.getInstance(de.questid).getName()) : "").append("\r\n");
                    }
                    num++;
                }
            }
            if (name.length() > 0)
                return name.toString();
        }
        return "当前地图没有设置全局爆率。";
    }

    public int 获取签到奖励领取状态() {
        int money = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                money = rs.getInt("qiandaolb");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return money;
    }
    
    public void 设置签到奖励领取状态(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET qiandaolb =qiandaolb+ " + slot + " WHERE id = " + cid + "")) {
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public int 获取礼包领取状态() {
        int money = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                money = rs.getInt("lb");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return money;
    }
    
    public void 设置礼包领取状态(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET lb =lb+ " + slot + " WHERE id = " + cid + "")) {
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public int getzb() {
        int money = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                money = rs.getInt("money");
            }
            limitCheck.close();
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return money;
    }
    
    public void setzb(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET money =money+ " + slot + " WHERE id = " + cid + "")) {
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public int getmoneyb() {
        int moneyb = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            ResultSet rs;
            try (final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM accounts WHERE id=" + cid + "")) {
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    moneyb = rs.getInt("moneyb");
                }
            }
            rs.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
        return moneyb;
    }
    
    public void setmoneyb(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET moneyb =moneyb+ " + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public MapleMapFactory getMapFactory() {
        return this.getClient().getChannelServer().getMapFactory();
    }
    
    public void warpBackoff() {
        this.c.sendPacket(MaplePacketCreator.stopClock());
    }
    
    public void warpBack(final int mid, final int retmap, final int time) {
        final MapleMap warpMap = this.c.getChannelServer().getMapFactory().getMap(mid);
        this.c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
        this.c.sendPacket(MaplePacketCreator.getClock(time));
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                final MapleMap warpMap = NPCConversationManager.this.c.getChannelServer().getMapFactory().getMap(retmap);
                if (NPCConversationManager.this.c.getPlayer() != null) {
                    NPCConversationManager.this.c.sendPacket(MaplePacketCreator.stopClock());
                    NPCConversationManager.this.c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
                    NPCConversationManager.this.c.getPlayer().dropMessage(6, "到达目的地ヘ!");
                }
            }
        }, 1000 * time);
    }
    
    public void warpMapWithClock(final int mid, final int seconds) {
        this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(seconds));
        Timer.MapTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (NPCConversationManager.this.c.getPlayer() != null) {
                    for (final MapleCharacter chr : NPCConversationManager.this.c.getPlayer().getMap().getCharactersThreadsafe()) {
                        chr.changeMap(mid);
                    }
                }
            }
        }, seconds * 1000);
    }
    
    public void showlvl() {
        this.c.sendPacket(MaplePacketCreator.showlevelRanks(this.npc, MapleGuildRanking.getInstance().getLevelRank()));
    }
    
    public void showmeso() {
        this.c.sendPacket(MaplePacketCreator.showmesoRanks(this.npc, MapleGuildRanking.getInstance().getMesoRank()));
    }
    
    public void ShowMarrageEffect() {
        this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.sendMarrageEffect());
    }
    
    public int 给全服发点卷(final int 数量, final int 类型) {
        int count = 0;
        try {
            if (数量 <= 0 || 类型 <= 0) {
                return 0;
            }
            if (类型 == 1 || 类型 == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(类型, 数量);
                        String cash = null;
                        if (类型 == 1) {
                            cash = "点卷";
                        }
                        else if (类型 == 2) {
                            cash = "抵用卷";
                        }
                        ++count;
                    }
                }
            }
            else if (类型 == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(数量, true);
                        ++count;
                    }
                }
            }
            else if (类型 == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(数量, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给全服发点卷出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给当前地图发点卷(final int 数量, final int 类型) {
        int count = 0;
        final int mapId = this.c.getPlayer().getMapId();
        try {
            if (数量 <= 0 || 类型 <= 0) {
                return 0;
            }
            if (类型 == 1 || 类型 == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.modifyCSPoints(类型, 数量);
                        String cash = null;
                        if (类型 == 1) {
                            cash = "点卷";
                        }
                        else if (类型 == 2) {
                            cash = "抵用卷";
                        }
                        ++count;
                    }
                }
            }
            else if (类型 == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.gainMeso(数量, true);
                        ++count;
                    }
                }
            }
            else if (类型 == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        if (mch.getMapId() != mapId) {
                            continue;
                        }
                        mch.gainExp(数量, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给当前地图发点卷出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给当前频道发点卷(final int 数量, final int 类型) {
        int count = 0;
        final int chlId = this.c.getPlayer().getMap().getChannel();
        try {
            if (数量 <= 0 || 类型 <= 0) {
                return 0;
            }
            if (类型 == 1 || 类型 == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(类型, 数量);
                        String cash = null;
                        if (类型 == 1) {
                            cash = "点卷";
                        }
                        else if (类型 == 2) {
                            cash = "抵用卷";
                        }
                        ++count;
                    }
                }
            }
            else if (类型 == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(数量, true);
                        ++count;
                    }
                }
            }
            else if (类型 == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    if (cserv1.getChannel() != chlId) {
                        continue;
                    }
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(数量, true, false, true);
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给当前频道发点卷出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给全服发物品(final int 物品ID, final int 数量, final int 力量, final int 敏捷, final int 智力, final int 运气, final int HP, final int MP, final int 可加卷次数, final String 制作人名字, final int 给予时间, final String 是否可以交易, final int 攻击力, final int 魔法力, final int 物理防御, final int 魔法防御) {
        int count = 0;
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)) || (type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100)) {
                            final Equip item = (Equip)ii.getEquipById(物品ID);
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            if (力量 > 0 && 力量 <= 32767) {
                                item.setStr((short)力量);
                            }
                            if (敏捷 > 0 && 敏捷 <= 32767) {
                                item.setDex((short)敏捷);
                            }
                            if (智力 > 0 && 智力 <= 32767) {
                                item.setInt((short)智力);
                            }
                            if (运气 > 0 && 运气 <= 32767) {
                                item.setLuk((short)运气);
                            }
                            if (攻击力 > 0 && 攻击力 <= 32767) {
                                item.setWatk((short)攻击力);
                            }
                            if (魔法力 > 0 && 魔法力 <= 32767) {
                                item.setMatk((short)魔法力);
                            }
                            if (物理防御 > 0 && 物理防御 <= 32767) {
                                item.setWdef((short)物理防御);
                            }
                            if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                item.setMdef((short)魔法防御);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("可以交易".equals(是否可以交易)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (给予时间 > 0) {
                                item.setExpiration(System.currentTimeMillis() + 给予时间 * 24 * 60 * 60 * 1000);
                            }
                            if (可加卷次数 > 0) {
                                item.setUpgradeSlots((byte)可加卷次数);
                            }
                            if (制作人名字 != null) {
                                item.setOwner(制作人名字);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short)数量, "", null, 给予时间, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
                    mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short)数量, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给全服发物品出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给当前地图发物品(final int 物品ID, final int 数量, final int 力量, final int 敏捷, final int 智力, final int 运气, final int HP, final int MP, final int 可加卷次数, final String 制作人名字, final int 给予时间, final String 是否可以交易, final int 攻击力, final int 魔法力, final int 物理防御, final int 魔法防御) {
        int count = 0;
        final int mapId = this.c.getPlayer().getMapId();
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getMapId() != mapId) {
                        continue;
                    }
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)) || (type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100)) {
                            final Equip item = (Equip)ii.getEquipById(物品ID);
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            if (力量 > 0 && 力量 <= 32767) {
                                item.setStr((short)力量);
                            }
                            if (敏捷 > 0 && 敏捷 <= 32767) {
                                item.setDex((short)敏捷);
                            }
                            if (智力 > 0 && 智力 <= 32767) {
                                item.setInt((short)智力);
                            }
                            if (运气 > 0 && 运气 <= 32767) {
                                item.setLuk((short)运气);
                            }
                            if (攻击力 > 0 && 攻击力 <= 32767) {
                                item.setWatk((short)攻击力);
                            }
                            if (魔法力 > 0 && 魔法力 <= 32767) {
                                item.setMatk((short)魔法力);
                            }
                            if (物理防御 > 0 && 物理防御 <= 32767) {
                                item.setWdef((short)物理防御);
                            }
                            if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                item.setMdef((short)魔法防御);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("可以交易".equals(是否可以交易)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (给予时间 > 0) {
                                item.setExpiration(System.currentTimeMillis() + 给予时间 * 24 * 60 * 60 * 1000);
                            }
                            if (可加卷次数 > 0) {
                                item.setUpgradeSlots((byte)可加卷次数);
                            }
                            if (制作人名字 != null) {
                                item.setOwner(制作人名字);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short)数量, "", null, 给予时间, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
                    mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short)数量, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给当前地图发物品出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给当前频道发物品(final int 物品ID, final int 数量, final int 力量, final int 敏捷, final int 智力, final int 运气, final int HP, final int MP, final int 可加卷次数, final String 制作人名字, final int 给予时间, final String 是否可以交易, final int 攻击力, final int 魔法力, final int 物理防御, final int 魔法防御) {
        int count = 0;
        final int chlId = this.c.getPlayer().getMap().getChannel();
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                if (cserv1.getChannel() != chlId) {
                    continue;
                }
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return 0;
                        }
                        if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)) || (type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100)) {
                            final Equip item = (Equip)ii.getEquipById(物品ID);
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            if (力量 > 0 && 力量 <= 32767) {
                                item.setStr((short)力量);
                            }
                            if (敏捷 > 0 && 敏捷 <= 32767) {
                                item.setDex((short)敏捷);
                            }
                            if (智力 > 0 && 智力 <= 32767) {
                                item.setInt((short)智力);
                            }
                            if (运气 > 0 && 运气 <= 32767) {
                                item.setLuk((short)运气);
                            }
                            if (攻击力 > 0 && 攻击力 <= 32767) {
                                item.setWatk((short)攻击力);
                            }
                            if (魔法力 > 0 && 魔法力 <= 32767) {
                                item.setMatk((short)魔法力);
                            }
                            if (物理防御 > 0 && 物理防御 <= 32767) {
                                item.setWdef((short)物理防御);
                            }
                            if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                item.setMdef((short)魔法防御);
                            }
                            if (HP > 0 && HP <= 30000) {
                                item.setHp((short)HP);
                            }
                            if (MP > 0 && MP <= 30000) {
                                item.setMp((short)MP);
                            }
                            if ("可以交易".equals(是否可以交易)) {
                                byte flag = item.getFlag();
                                if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                    flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                }
                                else {
                                    flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                }
                                item.setFlag(flag);
                            }
                            if (给予时间 > 0) {
                                item.setExpiration(System.currentTimeMillis() + 给予时间 * 24 * 60 * 60 * 1000);
                            }
                            if (可加卷次数 > 0) {
                                item.setUpgradeSlots((byte)可加卷次数);
                            }
                            if (制作人名字 != null) {
                                item.setOwner(制作人名字);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) {
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        }
                        else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short)数量, "", null, 给予时间, (byte)0);
                        }
                    }
                    else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
                    mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short)数量, true));
                    ++count;
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给当前频道发物品出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 是否是认证玩家() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as DATA FROM 会员 WHERE name = ?")) {
                ps.setString(1, this.getName());
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("DATA");
                    }
                }
            }
        }
        catch (SQLException Ex) {
            System.err.println("查询认证玩家出错 - 数据库查询失败：" + Ex);
        }
        return 0;
    }
    
    public int 白名单() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as DATA FROM 白名单 WHERE name = ?")) {
                ps.setString(1, this.getName());
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("DATA");
                    }
                }
            }
        }
        catch (SQLException Ex) {
            System.err.println("查询白名单出错 - 数据库查询失败：" + Ex);
        }
        return 0;
    }
    
    public boolean 判断当前地图是否已禁用此脚本(final int scriptId) {
        try {
            final int mapId = this.c.getPlayer().getMapId();
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as DATA FROM 禁用脚本地图 WHERE scriptId = ? AND mapId = ?")) {
                ps.setInt(1, scriptId);
                ps.setInt(2, mapId);
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("DATA") > 0;
                    }
                }
            }
        }
        catch (SQLException Ex) {
            System.err.println("判断当前地图是否已禁用此脚本出错 - 数据库查询失败：" + Ex);
        }
        return false;
    }
    
    public int 认证主播() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as DATA FROM 认证主播 WHERE name = ?")) {
                ps.setString(1, this.getName());
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("DATA");
                    }
                }
            }
        }
        catch (SQLException Ex) {
            System.err.println("查询认证主播出错 - 数据库查询失败：" + Ex);
        }
        return 0;
    }
    
    public int 传送当前地图所有人到指定地图(final int destMapId, final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap tomap = this.getMapFactory().getMap(destMapId);
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (tomap != null && frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    final MapleCharacter chr = (MapleCharacter)mmo;
                    if (chr.getId() == myId) {
                        if (!includeSelf) {
                            continue;
                        }
                        chr.changeMap(tomap, tomap.getPortal(0));
                        ++count;
                    }
                    else {
                        chr.changeMap(tomap, tomap.getPortal(0));
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("传送当前地图所有人到指定地图出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 杀死当前地图所有人(final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (chr.getId() == myId) {
                            if (!includeSelf) {
                                continue;
                            }
                            chr.setHp(0);
                            chr.updateSingleStat(MapleStat.HP, 0);
                            ++count;
                        }
                        else {
                            chr.setHp(0);
                            chr.updateSingleStat(MapleStat.HP, 0);
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("杀死当前地图所有人出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 复活当前地图所有人(final Boolean includeSelf) {
        int count = 0;
        final int myMapId = this.c.getPlayer().getMapId();
        final int myId = this.c.getPlayer().getId();
        try {
            final MapleMap frommap = this.getMapFactory().getMap(myMapId);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (frommap != null && list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (chr.getId() == myId) {
                            if (!includeSelf) {
                                continue;
                            }
                            chr.getStat().setHp(chr.getStat().getMaxHp());
                            chr.updateSingleStat(MapleStat.HP, chr.getStat().getMaxHp());
                            chr.getStat().setMp(chr.getStat().getMaxMp());
                            chr.updateSingleStat(MapleStat.MP, chr.getStat().getMaxMp());
                            chr.dispelDebuffs();
                            ++count;
                        }
                        else {
                            chr.getStat().setHp(chr.getStat().getMaxHp());
                            chr.updateSingleStat(MapleStat.HP, chr.getStat().getMaxHp());
                            chr.getStat().setMp(chr.getStat().getMaxMp());
                            chr.updateSingleStat(MapleStat.MP, chr.getStat().getMaxMp());
                            chr.dispelDebuffs();
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("复活当前地图所有人出错：" + e.getMessage());
        }
        return count;
    }
    
    public void 跟踪玩家(final String charName) {
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : chl.getPlayerStorage().getAllCharacters()) {
                if (chr.getName() == charName) {
                    this.c.getPlayer().changeMap(chr.getMapId());
                }
            }
        }
    }
    
    public int 给指定地图发物品(int 地图ID, final int 物品ID, final int 数量, final int 力量, final int 敏捷, final int 智力, final int 运气, final int HP, final int MP, final int 可加卷次数, final String 制作人名字, final int 给予时间, final String 是否可以交易, final int 攻击力, final int 魔法力, final int 物理防御, final int 魔法防御) {
        int count = 0;
        if (地图ID < 1) {
            地图ID = this.c.getPlayer().getMapId();
        }
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            final MapleMap frommap = this.getMapFactory().getMap(地图ID);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (list != null && frommap.getCharactersSize() > 0) {
                for (final MapleMapObject mmo : list) {
                    if (mmo != null) {
                        final MapleCharacter chr = (MapleCharacter)mmo;
                        if (数量 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(chr.getClient(), 物品ID, 数量, "")) {
                                return 0;
                            }
                            if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)) || (type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100)) {
                                final Equip item = (Equip)ii.getEquipById(物品ID);
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short)力量);
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short)敏捷);
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short)智力);
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short)运气);
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short)攻击力);
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short)魔法力);
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short)物理防御);
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short)魔法防御);
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short)HP);
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short)MP);
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    byte flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                    }
                                    else {
                                        flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag(flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + 给予时间 * 24 * 60 * 60 * 1000);
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte)可加卷次数);
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) {
                                    final String msg = "你已获得称号 <" + name + ">";
                                    chr.dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(chr.getClient(), item.copy());
                            }
                            else {
                                MapleInventoryManipulator.addById(chr.getClient(), 物品ID, (short)数量, "", null, 给予时间, (byte)0);
                            }
                        }
                        else {
                            MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                        }
                        chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short)数量, true));
                        ++count;
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给指定地图发物品出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 给指定地图发物品(final int 地图ID, final int 物品ID, final int 数量) {
        return this.给指定地图发物品(地图ID, 物品ID, 数量, 0, 0, 0, 0, 0, 0, 0, "", 0, "", 0, 0, 0, 0);
    }
    
    public int 给指定地图发点卷(int 地图ID, final int 数量, final int 类型) {
        int count = 0;
        final String name = this.c.getPlayer().getName();
        if (地图ID < 1) {
            地图ID = this.c.getPlayer().getMapId();
        }
        try {
            if (数量 <= 0 || 类型 <= 0) {
                return 0;
            }
            final MapleMap frommap = this.getMapFactory().getMap(地图ID);
            final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
            if (list != null && frommap.getCharactersSize() > 0) {
                if (类型 == 1 || 类型 == 2) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.modifyCSPoints(类型, 数量);
                            String cash = null;
                            if (类型 == 1) {
                                cash = "点卷";
                            }
                            else if (类型 == 2) {
                                cash = "抵用卷";
                            }
                            ++count;
                        }
                    }
                }
                else if (类型 == 3) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.gainMeso(数量, true);
                            ++count;
                        }
                    }
                }
                else if (类型 == 4) {
                    for (final MapleMapObject mmo : list) {
                        if (mmo != null) {
                            final MapleCharacter chr = (MapleCharacter)mmo;
                            chr.gainExp(数量, true, false, true);
                            ++count;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.c.getPlayer().dropMessage("给指定地图发点卷出错：" + e.getMessage());
        }
        return count;
    }
    
    public int 获取指定地图玩家数量(final int mapId) {
        return this.getMapFactory().getMap(mapId).characterSize();
    }
    
    public void 给指定地图发公告(final int mapId, final String msg, final int itemId) {
        this.getMapFactory().getMap(mapId).startMapEffect(msg, itemId);
    }
    
    @Override
    public String getServerName() {
        return ServerProperties.getProperty("RoyMS.ServerName");
    }
    
    public void 克隆() {
        this.c.getPlayer().cloneLook();
    }
    
    public void 取消克隆() {
        this.c.getPlayer().disposeClones();
    }
    
    public void 设置天气(final int 天气ID) {
        if (this.c.getPlayer().getMap().getPermanentWeather() > 0) {
            this.c.getPlayer().getMap().setPermanentWeather(0);
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.removeMapEffect());
        }
        else if (!MapleItemInformationProvider.getInstance().itemExists(天气ID) || 天气ID / 10000 != 512) {
            this.c.getPlayer().dropMessage(5, "无效的天气ID。");
        }
        else {
            this.c.getPlayer().getMap().setPermanentWeather(天气ID);
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.startMapEffect("", 天气ID, false));
            this.c.getPlayer().dropMessage(5, "地图天气已启用。");
        }
    }
    
    public void MapleMSpvpkills() {
        MapleGuildRanking.MapleMSpvpkills(this.c, this.npc);
    }
    
    public void MapleMSpvpdeaths() {
        MapleGuildRanking.MapleMSpvpdeaths(this.c, this.npc);
    }
    
    public void 爆物开关() {
        this.c.getPlayer().getMap().toggleDrops();
    }
    
    public void 发言(final String[] 内容) {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter victim : cserv.getPlayerStorage().getAllCharacters()) {
                if (victim.getId() != this.c.getPlayer().getId()) {
                    victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(内容, 1), victim.isGM(), 0));
                }
            }
        }
    }
    
    public void 人气排行榜() {
        MapleGuild.人气排行(this.getClient(), this.npc);
    }
    
    public void 豆豆排行榜() {
        MapleGuild.豆豆排行(this.getClient(), this.npc);
    }
    
    public void 战斗力排行榜() {
        MapleGuild.战斗力排行(this.getClient(), this.npc);
    }
    
    public void 破攻排行榜() {
        MapleGuild.破攻排行(this.getClient(), this.npc);
    }
    
    public void 杀怪排行榜() {
        MapleGuild.杀怪排行榜(this.getClient(), this.npc);
    }
    
    public void 总在线时间排行榜() {
        MapleGuild.总在线时间排行(this.getClient(), this.npc);
    }
    
    public int 查询今日在线时间() {
        int data = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement psu = con.prepareStatement("SELECT todayOnlineTime FROM characters WHERE id = ?");
            psu.setInt(1, this.c.getPlayer().getId());
            final ResultSet rs = psu.executeQuery();
            if (rs.next()) {
                data = rs.getInt("todayOnlineTime");
            }
            rs.close();
            psu.close();
        }
        catch (SQLException ex) {
            System.err.println("查询今日在线时间出错：" + ex.getMessage());
        }
        return data;
    }
    
    public int 查询总在线时间() {
        int data = 0;
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement psu = con.prepareStatement("SELECT totalOnlineTime FROM characters WHERE id = ?");
            psu.setInt(1, this.c.getPlayer().getId());
            final ResultSet rs = psu.executeQuery();
            if (rs.next()) {
                data = rs.getInt("totalOnlineTime");
            }
            rs.close();
            psu.close();
        }
        catch (SQLException ex) {
            System.err.println("查询总在线时间出错：" + ex.getMessage());
        }
        return data;
    }
    
    public int 查询在线人数() {
        int count = 0;
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            count += chl.getPlayerStorage().getAllCharacters().size();
        }
        return count;
    }
    
    public static int 获取最高玩家等级() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(level) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高等级玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `level` FROM characters WHERE gm = 0 ORDER BY `level` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("level");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int 获取最高玩家人气() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(fame) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高人气玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `fame` FROM characters WHERE gm = 0 ORDER BY `fame` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("fame");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int 获取最高玩家金币() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(meso) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高金币玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `meso` FROM characters WHERE gm = 0 ORDER BY `meso` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("meso");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int 获取最高玩家在线() {
        int data = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(totalOnlineTime) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高在线玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `totalOnlineTime` FROM characters WHERE gm = 0 ORDER BY `totalOnlineTime` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("totalOnlineTime");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static String 获取家族名称(final int guildId) {
        String data = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public int 获取自己等级排名() {
        int DATA = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank FROM (SELECT @rownum := @rownum + 1 AS rank, `name`, `level`, `id` FROM characters, (SELECT @rownum := 0) r WHERE gm = 0 ORDER BY `level` DESC) AS T1 WHERE `id` = ?");
            ps.setInt(1, this.c.getPlayer().getId());
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DATA = rs.getInt("rank");
                }
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取自己等级排名出错 - 数据库查询失败：" + Ex);
        }
        return DATA;
    }
    
    public String GetPlayerList() {
        String ret = "";
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                if (!chr.isGM()) {
                    ret = ret + "#b#L" + chr.getId() + "#[跟踪] 玩家名:  #r" + chr.getName() + " #k#l \r\n";
                }
            }
        }
        return ret;
    }
    
    public MapleCharacter GetPlayer(final int cid) {
        MapleCharacter ret = null;
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                if (!chr.isGM() && chr.getId() == cid) {
                    ret = chr;
                }
            }
        }
        return ret;
    }
    
    @Override
    public String getItemName(final int id) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ii.getName(id);
    }
    
    public void 进入商城1() {
        try {
            final MapleCharacter chr = this.c.getPlayer();
            if (chr.getBuffedValue(MapleBuffStat.召唤兽) != null) {
                chr.cancelEffectFromBuffStat(MapleBuffStat.召唤兽);
            }
            final String[] socket = this.c.getChannelServer().getIP().split(":");
            final ChannelServer ch = ChannelServer.getInstance(this.c.getChannel());
            chr.changeRemoval();
            if (chr.getMessenger() != null) {
                final MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(chr);
                World.Messenger.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
            }
            PlayerBuffStorage.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
            PlayerBuffStorage.addCooldownsToStorage(chr.getId(), chr.getCooldowns());
            PlayerBuffStorage.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
            World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), -10);
            ch.removePlayer(chr);
            this.c.updateLoginState(MapleClient.CHANGE_CHANNEL, this.c.getSessionIPAddress());
            this.c.getSession().write(MaplePacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(CashShopServer.getIP().split(":")[1])));
            chr.saveToDB(false, false);
            chr.getMap().removePlayer(chr);
            this.c.getPlayer().expirationTask(true, false);
            this.c.setPlayer(null);
            this.c.setReceiving(false);
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(NPCConversationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void 进入商城2() {
        try {
            final MapleCharacter chr = this.c.getPlayer();
            final String[] socket = this.c.getChannelServer().getIP().split(":");
            final ChannelServer ch = ChannelServer.getInstance(this.c.getChannel());
            chr.changeRemoval();
            if (chr.getMessenger() != null) {
                final MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(chr);
                World.Messenger.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
            }
            PlayerBuffStorage.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
            PlayerBuffStorage.addCooldownsToStorage(chr.getId(), chr.getCooldowns());
            PlayerBuffStorage.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
            World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), -20);
            ch.removePlayer(chr);
            this.c.updateLoginState(MapleClient.CHANGE_CHANNEL, this.c.getSessionIPAddress());
            this.c.getSession().write(MaplePacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(CashShopServer.getIP().split(":")[1])));
            chr.saveToDB(false, false);
            chr.getMap().removePlayer(chr);
            this.c.getPlayer().expirationTask(true, false);
            this.c.setPlayer(null);
            this.c.setReceiving(false);
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(NPCConversationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int 获取分解的矿石() {
        return GameConstants.分解的矿石();
    }
    
    public int getMount(final int s) {
        return GameConstants.getMountS(s);
    }
    
    public void 喇叭(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 2: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 3: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 4: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 5: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(6, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 6: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(18, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
            case 7: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(10, this.c.getChannel(), "[全服公告] : " + msg).getBytes());
                break;
            }
        }
    }
    
    public void 即时存档() {
        this.c.getPlayer().saveToDB(true, true);
    }
    
    public void 刷新状态() {
        this.c.getPlayer().getClient().getSession().write(MaplePacketCreator.getCharInfo(this.c.getPlayer()));
        this.c.getPlayer().getMap().removePlayer(this.c.getPlayer());
        this.c.getPlayer().getMap().addPlayer(this.c.getPlayer());
        this.c.getSession().write(MaplePacketCreator.enableActions());
    }
    
    public void 刷新地图() {
        final boolean custMap = true;
        final int mapid = this.c.getPlayer().getMapId();
        final MapleMap map = custMap ? this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid) : this.c.getPlayer().getMap();
        if (this.c.getPlayer().getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>(map.getCharacters());
            for (final MapleCharacter m : mcs) {
                int x = 0;
                while (x < 5) {
                    try {
                        m.changeMap(newMap, newPor);
                    }
                    catch (Throwable t) {
                        ++x;
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    public void 组队征集喇叭(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[组队征集令] : " + msg).getBytes());
                break;
            }
            case 2: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[组队征集令]: " + msg).getBytes());
                break;
            }
            case 3: {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[组队征集令]: " + msg).getBytes());
                break;
            }
        }
    }
    
    public void deleteItem(final int inventorytype) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("Select * from inventoryitems where characterid=? and inventorytype=?");
            ps.setInt(1, this.getPlayer().getId());
            ps.setInt(2, inventorytype);
            final ResultSet re = ps.executeQuery();
            MapleInventoryType type = null;
            switch (inventorytype) {
                case 1: {
                    type = MapleInventoryType.EQUIP;
                    break;
                }
                case 2: {
                    type = MapleInventoryType.USE;
                    break;
                }
                case 3: {
                    type = MapleInventoryType.SETUP;
                    break;
                }
                case 4: {
                    type = MapleInventoryType.ETC;
                    break;
                }
                case 5: {
                    type = MapleInventoryType.CASH;
                    break;
                }
            }
            while (re.next()) {
                MapleInventoryManipulator.removeById(this.getC(), type, re.getInt("itemid"), re.getInt("quantity"), true, true);
            }
            re.close();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public void 全服漂浮喇叭(final String msg, final int itemId) {
        int ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                mch.startMapEffect(msg, itemId);
                ++ret;
            }
        }
    }
    
    public void 气泡喇叭(final String 气泡喇叭) {
        this.getClient().getSession().write(MaplePacketCreator.sendHint(气泡喇叭, 200, 200));
    }
    
    public int getHour() {
        return Calendar.getInstance().get(11);
    }
    
    public int getMin() {
        return Calendar.getInstance().get(12);
    }
    
    public int getSec() {
        return Calendar.getInstance().get(13);
    }
    
    public void Startqmdb() throws InterruptedException, SQLException {
        for (int ii = 0; ii <= 20; ++ii) {
            Thread.sleep(700L);
            final int 总数 = this.getPlayer().获取全民夺宝总数();
            final double a = Math.random() * 总数 + 1.0;
            final int A = new Double(a).intValue();
            final Iterator<ChannelServer> iterator = ChannelServer.getAllInstances().iterator();
            if (iterator.hasNext()) {
                final ChannelServer cserv1 = iterator.next();
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.getClient().getSession().write(MaplePacketCreator.sendHint("#b===========全民冒险岛==========#k\r\n==============================#r\r\n#b========全民夺宝活动开始=======#k\r\n==============================#r\r\n#b===========随机抽取中==========#k\r\n◆正在随机抽选中奖的幸运玩家◆\r\n#b===========幸运玩家===========#r\r\n" + mch.全民夺宝2(A), 200, 200));
                    if (ii == 20) {
                        mch.getClient().getSession().write(MaplePacketCreator.sendHint("#e#r★★★★★全民夺宝★★★★★\r\n中奖玩家：" + mch.全民夺宝2(A), 200, 200));
                        mch.startMapEffect("★恭喜玩家:" + mch.全民夺宝2(A) + " 赢得了 [全民夺宝] !!★", 5120025);
                        this.c.getSession().write(MaplePacketCreator.enableActions());
                    }
                }
            }
        }
    }
    
    public void 谁是卧底() {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() < 6) {
            return;
        }
        final int cMap = this.getPlayer().getMapId();
        final int 随机给予卧底值 = Randomizer.nextInt(6);
        final int 随机给予卧底值2 = Randomizer.nextInt(6);
        final int 人数 = this.getPlayer().getParty().getMembers().size();
        int 确定人数 = 0;
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && curChar.getMapId() == cMap) {
                ++确定人数;
                if (随机给予卧底值 == 随机给予卧底值2) {}
                if (人数 != 确定人数) {
                    continue;
                }
                final Timer.MapTimer tMan = Timer.MapTimer.getInstance();
                tMan.schedule(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 60000L);
            }
        }
    }
    
    public void 家族排行榜() {
        MapleGuild.displayGuildRanks(this.getClient(), this.npc);
    }
    
    public void 等级排行榜() {
        MapleGuild.displayLevelRanks(this.getClient(), this.npc);
    }
    
    public void 金币排行榜() {
        MapleGuild.meso(this.getClient(), this.npc);
    }
    
    public void VIP排行榜() {
        MapleGuild.VIP排行(this.getClient(), this.npc);
    }
    
    public int getEquipId(final byte slot) {
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final Equip eu = (Equip)equip.getItem(slot);
        if (eu == null) {
            return -1;
        }
        return equip.getItem(slot).getItemId();
    }
    
    public int getUseId(final byte slot) {
        final MapleInventory use = this.getPlayer().getInventory(MapleInventoryType.USE);
        return use.getItem(slot).getItemId();
    }
    
    public int getSetupId(final byte slot) {
        final MapleInventory setup = this.getPlayer().getInventory(MapleInventoryType.SETUP);
        return setup.getItem(slot).getItemId();
    }
    
    public int getCashId(final byte slot) {
        final MapleInventory cash = this.getPlayer().getInventory(MapleInventoryType.CASH);
        return cash.getItem(slot).getItemId();
    }
    
    public int getETCId(final byte slot) {
        final MapleInventory etc = this.getPlayer().getInventory(MapleInventoryType.ETC);
        return etc.getItem(slot).getItemId();
    }
    
    public String EquipList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<String> stra = new LinkedList<String>();
        for (final IItem item : equip.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String UseList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory use = c.getPlayer().getInventory(MapleInventoryType.USE);
        final List<String> stra = new LinkedList<String>();
        for (final IItem item : use.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String CashList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory cash = c.getPlayer().getInventory(MapleInventoryType.CASH);
        final List<String> stra = new LinkedList<String>();
        for (final IItem item : cash.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String ETCList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory etc = c.getPlayer().getInventory(MapleInventoryType.ETC);
        final List<String> stra = new LinkedList<String>();
        for (final IItem item : etc.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String SetupList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory setup = c.getPlayer().getInventory(MapleInventoryType.SETUP);
        final List<String> stra = new LinkedList<String>();
        for (final IItem item : setup.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public int[] getCanHair(final int[] hairs) {
        final List<Integer> canHair = new ArrayList<Integer>();
        final List<Integer> cantHair = new ArrayList<Integer>();
        for (final int hair : hairs) {
            if (hairExists(hair)) {
                canHair.add(hair);
            }
            else {
                cantHair.add(hair);
            }
        }
        if (cantHair.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("正在读取的发型里有");
            sb.append(cantHair.size()).append("个发型客户端不支持显示，已经被清除：");
            for (int i = 0; i < cantHair.size(); ++i) {
                sb.append(cantHair.get(i));
                if (i < cantHair.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getHair = new int[canHair.size()];
        for (int i = 0; i < canHair.size(); ++i) {
            getHair[i] = canHair.get(i);
        }
        return getHair;
    }
    
    public int[] getCanFace(final int[] faces) {
        final List<Integer> canFace = new ArrayList<Integer>();
        final List<Integer> cantFace = new ArrayList<Integer>();
        for (final int face : faces) {
            if (faceExists(face)) {
                canFace.add(face);
            }
            else {
                cantFace.add(face);
            }
        }
        if (cantFace.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("正在读取的脸型里有");
            sb.append(cantFace.size()).append("个脸型客户端不支持显示，已经被清除：");
            for (int i = 0; i < cantFace.size(); ++i) {
                sb.append(cantFace.get(i));
                if (i < cantFace.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getFace = new int[canFace.size()];
        for (int i = 0; i < canFace.size(); ++i) {
            getFace[i] = canFace.get(i);
        }
        return getFace;
    }
    
    public void 刷新能力值() {
        if (this.c.getPlayer().getRemainingAp() < 0) {
            this.c.getPlayer().setRemainingAp((short)0);
        }
    }
    
    public void 重载事件() {
        final Iterator<ChannelServer> iterator = ChannelServer.getAllInstances().iterator();
        if (iterator.hasNext()) {
            final ChannelServer instance = iterator.next();
            instance.reloadEvents();
        }
    }
    
    public void 重载任务() {
        MapleQuest.clearQuests();
    }
    
    public void 重载商店() {
        MapleShopFactory.getInstance().clear();
    }
    
    public void 重载传送点() {
        PortalScriptManager.getInstance().clearScripts();
    }
    
    public void 重载爆率() {
        MapleMonsterInformationProvider.getInstance().clearDrops();
    }
    
    public void 重载反应堆() {
        ReactorScriptManager.getInstance().clearDrops();
    }
    
    @Override
    public void openNpc(final int id) {
        NPCScriptManager.getInstance().start(this.getClient(), id);
    }
    
    @Override
    public void openNpc(final int id, final int wh) {
        NPCScriptManager.getInstance().dispose(this.c);
        NPCScriptManager.getInstance().start(this.getClient(), id, wh);
    }
    
    public int getjf() {
        return this.c.getPlayer().getjf();
    }
    
    public void gainjf(final int s) {
        this.c.getPlayer().gainjf(s);
    }
    
    public void setjf(final int s) {
        this.c.getPlayer().setjf(s);
    }
    
    public int getdjjl() {
        return this.c.getPlayer().getdjjl();
    }
    
    public void setdjjl(final int s) {
        this.c.getPlayer().setdjjl(s);
    }
    
    public void gaindjjl(final int s) {
        this.c.getPlayer().gaindjjl(s);
    }
    
    public void EnterCS() {
        try {
            final MapleCharacter chr = this.c.getPlayer();
            final String[] socket = this.c.getChannelServer().getIP().split(":");
            final ChannelServer ch = ChannelServer.getInstance(this.c.getChannel());
            chr.changeRemoval();
            if (chr.getMessenger() != null) {
                final MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(chr);
                World.Messenger.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
            }
            PlayerBuffStorage.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
            PlayerBuffStorage.addCooldownsToStorage(chr.getId(), chr.getCooldowns());
            PlayerBuffStorage.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
            World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), -20);
            ch.removePlayer(chr);
            this.c.updateLoginState(MapleClient.CHANGE_CHANNEL, this.c.getSessionIPAddress());
            this.c.getSession().write(MaplePacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(CashShopServer.getIP().split(":")[1])));
            chr.saveToDB(false, false);
            chr.getMap().removePlayer(chr);
            this.c.setPlayer(null);
            this.c.setReceiving(false);
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(NPCConversationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Forum_Section> getForum() {
        return Forum_Section.getAllSection();
    }
    
    public boolean addSection(final String name) {
        return Forum_Section.addSection(name);
    }
    
    public boolean deleteSection(final int id) {
        return Forum_Section.deleteSection(id);
    }
    
    public Forum_Section getSectionById(final int id) {
        return Forum_Section.getSectionById(id);
    }
    
    public ArrayList<Forum_Thread> getCurrentAllThread(final int sid) {
        return Forum_Thread.getCurrentAllThread(sid);
    }
    
    public Forum_Thread getThreadById(final int sid, final int id) {
        return Forum_Thread.getThreadById(sid, id);
    }
    
    public Forum_Thread getThreadByName(final int sid, final String name) {
        return Forum_Thread.getThreadByName(sid, name);
    }
    
    public boolean addThread(final int sid, final String tname, final int cid, final String cname) {
        return Forum_Thread.addThread(sid, tname, cid, cname);
    }
    
    public boolean deleteThread(final int sid, final int tid) {
        return Forum_Thread.deleteThread(sid, tid, false);
    }
    
    public ArrayList<Forum_Reply> getCurrentAllReply(final int tid) {
        return Forum_Reply.getCurrentAllReply(tid);
    }
    
    public boolean addReply(final int tid, final int cid, final String cname, final String news) {
        return Forum_Reply.addReply(tid, cid, cname, news);
    }
    
    public MapleMonster 获取怪物id(final int id) {
        return this.c.getPlayer().getMap().getMonsterById(id);
    }
}
