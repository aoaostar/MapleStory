package client;

import client.anticheat.CheatTracker;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.ItemLoader;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MapleMount;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import client.inventory.ModifyInventory;
import constants.GameConstants;
import constants.ServerConstants;
import database.DatabaseConnection;
import database.DatabaseException;
import handling.MaplePacket;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.CharacterTransfer;
import handling.world.MapleMessenger;
import handling.world.MapleMessengerCharacter;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.PlayerBuffStorage;
import handling.world.PlayerBuffValueHolder;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyBuff;
import handling.world.family.MapleFamilyCharacter;
import handling.world.guild.MapleGuild;
import handling.world.guild.MapleGuildCharacter;
import org.apache.mina.core.session.IoSession;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import scripting.EventInstanceManager;
import scripting.NPCScriptManager;
import server.AutobanManager;
import server.CashShop;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.MapleShop;
import server.MapleStatEffect;
import server.MapleStorage;
import server.MapleTrade;
import server.RandomRewards;
import server.Randomizer;
import server.ServerProperties;
import server.Timer;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.PlayerNPC;
import server.maps.AbstractAnimatedMapleMapObject;
import server.maps.Event_PyramidSubway;
import server.maps.FieldLimitType;
import server.maps.MapleDoor;
import server.maps.MapleFoothold;
import server.maps.MapleMap;
import server.maps.MapleMapEffect;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleSummon;
import server.maps.SavedLocationType;
import server.movement.LifeMovementFragment;
import server.quest.MapleQuest;
import server.shops.IMaplePlayerShop;
import tools.ConcurrentEnumMap;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.MockIOSession;
import tools.Pair;
import tools.data.output.MaplePacketLittleEndianWriter;
import tools.packet.MTSCSPacket;
import tools.packet.MobPacket;
import tools.packet.MonsterCarnivalPacket;
import tools.packet.PetPacket;
import tools.packet.PlayerShopPacket;
import tools.packet.UIPacket;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapleCharacter extends AbstractAnimatedMapleMapObject implements Serializable
{
    private static final long serialVersionUID = 845748950829L;
    private static final String[] ariantroomleader;
    private static final int[] ariantroomslot;
    public static boolean tutorial;
    private String name;
    private String chalktext;
    private String BlessOfFairy_Origin;
    private String charmessage;
    private long lastComboTime;
    private long lastfametime;
    private long keydown_skill;
    private byte dojoRecord;
    private byte gmLevel;
    private byte gender;
    private byte initialSpawnPoint;
    private byte skinColor;
    private byte guildrank;
    private byte allianceRank;
    private byte world;
    private byte fairyExp;
    private byte numClones;
    private byte subcategory;
    private short level;
    private short mulung_energy;
    private short aranCombo;
    private short availableCP;
    private short totalCP;
    private short fame;
    private short hpApUsed;
    private short job;
    private short remainingAp;
    private int accountid;
    private int id;
    private int meso;
    private int exp;
    private int hair;
    private int face;
    private int mapid;
    private int bookCover;
    private int dojo;
    private int guildid;
    private int fallcounter;
    private int maplepoints;
    private int acash;
    private int chair;
    private int itemEffect;
    private int points;
    private int vpoints;
    private int rank;
    private int rankMove;
    private int jobRank;
    private int jobRankMove;
    private int marriageId;
    private int marriageItemId;
    private int currentrep;
    private int totalrep;
    private int linkMid;
    private int coconutteam;
    private int followid;
    private int battleshipHP;
    private int expression;
    private int constellation;
    private int blood;
    private int month;
    private int day;
    private int beans;
    private int beansNum;
    private int beansRange;
    private int prefix;
    private transient Map<Integer, Integer> linkMobs;
    private boolean canSetBeansNum;
    private Point old;
    private boolean smega;
    private boolean hidden;
    private boolean hasSummon;
    private int[] wishlist;
    private int[] rocks;
    private int[] savedLocations;
    private int[] regrocks;
    private int[] remainingSp;
    private transient AtomicInteger inst;
    private transient List<LifeMovementFragment> lastres;
    private List<Integer> lastmonthfameids;
    private List<MapleDoor> doors;
    private List<MaplePet> pets;
    private transient WeakReference<MapleCharacter>[] clones;
    private transient Set<MapleMonster> controlled;
    private transient Set<MapleMapObject> visibleMapObjects;
    private transient ReentrantReadWriteLock visibleMapObjectsLock;
    private transient ReentrantReadWriteLock summonsLock;
    private transient ReentrantReadWriteLock controlledLock;
    private final Map<MapleQuest, MapleQuestStatus> quests;
    private Map<Integer, String> questinfo;
    private final Map<ISkill, SkillEntry> skills;
    private final transient Map<MapleBuffStat, MapleBuffStatValueHolder> effects;
    private transient List<MapleSummon> summons;
    private final transient Map<Integer, MapleCoolDownValueHolder> coolDowns;
    private final transient Map<MapleDisease, MapleDiseaseValueHolder> diseases;
    private CashShop cs;
    private transient Deque<MapleCarnivalChallenge> pendingCarnivalRequests;
    private transient MapleCarnivalParty carnivalParty;
    private BuddyList buddylist;
    private MonsterBook monsterbook;
    private transient CheatTracker anticheat;
    private MapleClient client;
    private PlayerStats stats;
    private transient PlayerRandomStream CRand;
    private transient MapleMap map;
    private transient MapleShop shop;
    private transient RockPaperScissors rps;
    private MapleStorage storage;
    private transient MapleTrade trade;
    private MapleMount mount;
    private final List<Integer> finishedAchievements;
    private MapleMessenger messenger;
    private byte[] petStore;
    private transient IMaplePlayerShop playerShop;
    private MapleParty party;
    private boolean invincible;
    private boolean canTalk;
    private boolean clone;
    private boolean followinitiator;
    private boolean followon;
    private MapleGuildCharacter mgc;
    private MapleFamilyCharacter mfc;
    private transient EventInstanceManager eventInstance;
    private MapleInventory[] inventory;
    private SkillMacro[] skillMacros;
    private MapleKeyLayout keylayout;
    private transient ScheduledFuture<?> beholderHealingSchedule;
    private transient ScheduledFuture<?> beholderBuffSchedule;
    private transient ScheduledFuture<?> BerserkSchedule;
    private transient ScheduledFuture<?> dragonBloodSchedule;
    private transient ScheduledFuture<?> fairySchedule;
    private transient ScheduledFuture<?> mapTimeLimitTask;
    private transient ScheduledFuture<?> fishing;
    private long nextConsume;
    private long pqStartTime;
    private transient Event_PyramidSubway pyramidSubway;
    private transient List<Integer> pendingExpiration;
    private transient List<Integer> pendingSkills;
    private final transient Map<Integer, Integer> movedMobs;
    private String teleportname;
    private int APQScore;
    private long lasttime;
    private long currenttime;
    private long deadtime;
    private MapleCharacter chars;
    private final long nengl = 0L;
    private final long nengls = 0L;
    public int apprentice;
    public int master;
    public boolean DebugMessage;
    public int ariantScore;
    public long lastGainHM;
    private long lastFishingTime;
    private int skillzq;
    private int bosslog;
    private int PGMaxDamage;
    private int jzname;
    private int mrsjrw;
    private int mrsgrw;
    private int mrsbossrw;
    private int mrfbrw;
    private int hythd;
    private int mrsgrwa;
    private int mrsbossrwa;
    private int mrfbrwa;
    private int mrsgrws;
    private int mrsbossrws;
    private int mrfbrws;
    private int mrsgrwas;
    private int mrsbossrwas;
    private int mrfbrwas;
    private int ddj;
    private int vip;
    private long vipexpired;
    private int djjl;
    private int qiandao;
    private int jf;
    private int shaguai;
    public int curPGDamage;
    public transient ScheduledFuture<?> pgSchedule;
    private transient MapleLieDetector antiMacro;
    private long lastHPTime;
    private long lastMPTime;
    private long lastCheckPeriodTime;
    private long lastMoveItemTime;
    private long lastQuestTime;
    public long lastRecoveryTime;
    private int MobVac;
    private int MobVac2;
    private int touzhuNum;
    private int touzhuType;
    private int touzhuNX;
    private transient MaplePvpStats pvpStats;
    private int pvpDeaths;
    private int pvpKills;
    private int pvpVictory;
    private long lastExpirationTime;
    Runnable cancelEnergyRunnable;
    private boolean cancelEnergy;
    
    public static MapleCharacter getDefault(final MapleClient client, final int type) {
        final MapleCharacter ret = new MapleCharacter(false);
        ret.client = client;
        ret.map = null;
        ret.exp = 0;
        ret.gmLevel = 0;
        ret.job = (short)((type == 1) ? 0 : ((type == 0) ? 1000 : ((type == 3) ? 2001 : ((type == 4) ? 3000 : 2000))));
        ret.beans = 0;
        ret.meso = 0;
        ret.level = 1;
        ret.remainingAp = 0;
        ret.fame = 0;
        ret.accountid = client.getAccID();
        ret.buddylist = new BuddyList((byte)20);
        ret.stats.str = 12;
        ret.stats.dex = 5;
        ret.stats.int_ = 4;
        ret.stats.luk = 4;
        ret.stats.maxhp = 50;
        ret.stats.hp = 50;
        ret.stats.maxmp = 50;
        ret.stats.mp = 50;
        ret.prefix = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, ret.accountid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret.client.setAccountName(rs.getString("name"));
                ret.acash = rs.getInt("ACash");
                ret.maplepoints = rs.getInt("mPoints");
                ret.points = rs.getInt("points");
                ret.vpoints = rs.getInt("vpoints");
                ret.lastGainHM = rs.getLong("lastGainHM");
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error getting character default" + e);
        }
        return ret;
    }

    public static MapleCharacter ReconstructChr(CharacterTransfer ct, MapleClient client, boolean isChannel) {
        MapleCharacter ret = new MapleCharacter(true);
        ret.client = client;
        if (!isChannel)
            ret.client.setChannel(ct.channel);
        ret.DebugMessage = ct.DebugMessage;
        ret.id = ct.characterid;
        ret.name = ct.name;
        ret.level = ct.level;
        ret.fame = ct.fame;
        ret.CRand = new PlayerRandomStream();
        ret.stats.str = ct.str;
        ret.stats.dex = ct.dex;
        ret.stats.int_ = ct.int_;
        ret.stats.luk = ct.luk;
        ret.stats.maxhp = ct.maxhp;
        ret.stats.maxmp = ct.maxmp;
        ret.stats.hp = ct.hp;
        ret.stats.mp = ct.mp;
        ret.chalktext = ct.chalkboard;
        ret.exp = ct.exp;
        ret.hpApUsed = ct.hpApUsed;
        ret.remainingSp = ct.remainingSp;
        ret.remainingAp = ct.remainingAp;
        ret.beans = ct.beans;
        ret.meso = ct.meso;
        ret.gmLevel = ct.gmLevel;
        ret.skinColor = ct.skinColor;
        ret.gender = ct.gender;
        ret.job = ct.job;
        ret.hair = ct.hair;
        ret.face = ct.face;
        ret.accountid = ct.accountid;
        ret.mapid = ct.mapid;
        ret.initialSpawnPoint = ct.initialSpawnPoint;
        ret.world = ct.world;
        ret.bookCover = ct.mBookCover;
        ret.dojo = ct.dojo;
        ret.dojoRecord = ct.dojoRecord;
        ret.guildid = ct.guildid;
        ret.guildrank = ct.guildrank;
        ret.allianceRank = ct.alliancerank;
        ret.points = ct.points;
        ret.vpoints = ct.vpoints;
        ret.fairyExp = ct.fairyExp;
        ret.marriageId = ct.marriageId;
        ret.currentrep = ct.currentrep;
        ret.totalrep = ct.totalrep;
        ret.charmessage = ct.charmessage;
        ret.expression = ct.expression;
        ret.constellation = ct.constellation;
        ret.skillzq = ct.skillzq;
        ret.bosslog = ct.bosslog;
        ret.PGMaxDamage = ct.PGMaxDamage;
        ret.jzname = ct.jzname;
        ret.mrfbrw = ct.mrfbrw;
        ret.mrsbossrw = ct.mrsbossrw;
        ret.mrsgrw = ct.mrsgrw;
        ret.mrfbrwa = ct.mrfbrwa;
        ret.mrsbossrwa = ct.mrsbossrwa;
        ret.mrsgrwa = ct.mrsgrwa;
        ret.mrfbrws = ct.mrfbrws;
        ret.mrsbossrws = ct.mrsbossrws;
        ret.mrsgrws = ct.mrsgrws;
        ret.mrfbrwas = ct.mrfbrwas;
        ret.mrsbossrwas = ct.mrsbossrwas;
        ret.mrsgrwas = ct.mrsgrwas;
        ret.mrsjrw = ct.mrsjrw;
        ret.hythd = ct.hythd;
        ret.ddj = ct.ddj;
        ret.vip = ct.vip;
        ret.vipexpired = ct.vipexpired;
        ret.djjl = ct.djjl;
        ret.qiandao = ct.qiandao;
        ret.jf = ct.jf;
        ret.blood = ct.blood;
        ret.month = ct.month;
        ret.day = ct.day;
        ret.pvpDeaths = ct.pvpDeaths;
        ret.pvpKills = ct.pvpKills;
        ret.pvpVictory = ct.pvpVictory;
        ret.shaguai = ct.shaguai;
        ret.makeMFC(ct.familyid, ct.seniorid, ct.junior1, ct.junior2);
        if (ret.guildid > 0)
            ret.mgc = new MapleGuildCharacter(ret);
        ret.buddylist = new BuddyList(ct.buddysize);
        ret.subcategory = ct.subcategory;
        ret.prefix = ct.prefix;
        if (isChannel) {
            MapleMapFactory mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
            ret.map = mapFactory.getMap(ret.mapid);
            if (ret.map == null) {
                ret.map = mapFactory.getMap(100000000);
            } else if (ret.map.getForcedReturnId() != 999999999) {
                ret.map = ret.map.getForcedReturnMap();
            }
            MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
            if (portal == null) {
                portal = ret.map.getPortal(0);
                ret.initialSpawnPoint = 0;
            }
            ret.setPosition(portal.getPosition());
            int messengerid = ct.messengerid;
            if (messengerid > 0)
                ret.messenger = World.Messenger.getMessenger(messengerid);
        } else {
            ret.messenger = null;
        }
        int partyid = ct.partyid;
        if (partyid >= 0) {
            MapleParty party = World.Party.getParty(partyid);
            if (party != null && party.getMemberById(ret.id) != null)
                ret.party = party;
        }
        for (Map.Entry<Integer, Object> qs : (Iterable<Map.Entry<Integer, Object>>)ct.Quest.entrySet()) {
            MapleQuest quest = MapleQuest.getInstance(((Integer)qs.getKey()).intValue());
            MapleQuestStatus queststatus_from = (MapleQuestStatus)qs.getValue();
            MapleQuestStatus queststatus = new MapleQuestStatus(quest, queststatus_from.getStatus());
            queststatus.setForfeited(queststatus_from.getForfeited());
            queststatus.setCustomData(queststatus_from.getCustomData());
            queststatus.setCompletionTime(queststatus_from.getCompletionTime());
            if (queststatus_from.getMobKills() != null)
                for (Map.Entry<Integer, Integer> mobkills : queststatus_from.getMobKills().entrySet())
                    queststatus.setMobKills(((Integer)mobkills.getKey()).intValue(), ((Integer)mobkills.getValue()).intValue());
            ret.quests.put(quest, queststatus);
        }
        for (Map.Entry<Integer, SkillEntry> qs : (Iterable<Map.Entry<Integer, SkillEntry>>)ct.Skills.entrySet())
            ret.skills.put(SkillFactory.getSkill(((Integer)qs.getKey()).intValue()), qs.getValue());
        for (Integer zz : ct.finishedAchievements)
            ret.finishedAchievements.add(zz);
        ret.monsterbook = new MonsterBook(ct.mbook);
        ret.inventory = (MapleInventory[])ct.inventorys;
        ret.BlessOfFairy_Origin = ct.BlessOfFairy;
        ret.skillMacros = (SkillMacro[])ct.skillmacro;
        ret.petStore = ct.petStore;
        ret.keylayout = new MapleKeyLayout(ct.keymap);
        ret.questinfo = ct.InfoQuest;
        ret.savedLocations = ct.savedlocation;
        ret.wishlist = ct.wishlist;
        ret.rocks = ct.rocks;
        ret.regrocks = ct.regrocks;
        ret.buddylist.loadFromTransfer(ct.buddies);
        ret.keydown_skill = 0L;
        ret.lastfametime = ct.lastfametime;
        ret.lastmonthfameids = ct.famedcharacters;
        ret.storage = (MapleStorage)ct.storage;
        ret.pvpStats = (MaplePvpStats)ct.pvpStats;
        ret.cs = (CashShop)ct.cs;
        client.setAccountName(ct.accountname);
        ret.acash = ct.ACash;
        ret.lastGainHM = ct.lastGainHM;
        ret.maplepoints = ct.MaplePoints;
        ret.numClones = ct.clonez;
        ret.mount = new MapleMount(ret, ct.mount_itemid, GameConstants.isKOC(ret.job) ? 10001004 : (GameConstants.isAran(ret.job) ? 20001004 : (GameConstants.isEvan(ret.job) ? 20011004 : 1004)), ct.mount_Fatigue, ct.mount_level, ct.mount_exp);
        ret.stats.recalcLocalStats(true);
        return ret;
    }

    public static MapleCharacter loadCharFromDB(final int charid, final MapleClient client, final boolean channelserver) {
        final MapleCharacter ret = new MapleCharacter(channelserver);
        ret.client = client;
        ret.id = charid;
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, charid);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Loading the Char Failed (char not found)");
            }
            ret.name = rs.getString("name");
            ret.level = rs.getShort("level");
            ret.fame = rs.getShort("fame");
            ret.stats.str = rs.getShort("str");
            ret.stats.dex = rs.getShort("dex");
            ret.stats.int_ = rs.getShort("int");
            ret.stats.luk = rs.getShort("luk");
            ret.stats.maxhp = rs.getShort("maxhp");
            ret.stats.maxmp = rs.getShort("maxmp");
            ret.stats.hp = rs.getShort("hp");
            ret.stats.mp = rs.getShort("mp");
            ret.exp = rs.getInt("exp");
            ret.hpApUsed = rs.getShort("hpApUsed");
            final String[] sp = rs.getString("sp").split(",");
            for (int i = 0; i < ret.remainingSp.length; ++i) {
                ret.remainingSp[i] = Integer.parseInt(sp[i]);
            }
            ret.remainingAp = rs.getShort("ap");
            ret.beans = rs.getInt("beans");
            ret.meso = rs.getInt("meso");
            ret.gmLevel = rs.getByte("gm");
            ret.skinColor = rs.getByte("skincolor");
            ret.gender = rs.getByte("gender");
            ret.job = rs.getShort("job");
            ret.hair = rs.getInt("hair");
            ret.face = rs.getInt("face");
            ret.accountid = rs.getInt("accountid");
            ret.mapid = rs.getInt("map");
            ret.initialSpawnPoint = rs.getByte("spawnpoint");
            ret.world = rs.getByte("world");
            ret.guildid = rs.getInt("guildid");
            ret.guildrank = rs.getByte("guildrank");
            ret.allianceRank = rs.getByte("allianceRank");
            ret.currentrep = rs.getInt("currentrep");
            ret.totalrep = rs.getInt("totalrep");
            ret.makeMFC(rs.getInt("familyid"), rs.getInt("seniorid"), rs.getInt("junior1"), rs.getInt("junior2"));
            if (ret.guildid > 0) {
                ret.mgc = new MapleGuildCharacter(ret);
            }
            ret.buddylist = new BuddyList(rs.getByte("buddyCapacity"));
            ret.subcategory = rs.getByte("subcategory");
            ret.mount = new MapleMount(ret, 0, (ret.job > 1000 && ret.job < 2000) ? 10001004 : ((ret.job >= 2000) ? ((ret.job == 2001 || (ret.job >= 2200 && ret.job <= 2218)) ? 20011004 : ((ret.job >= 3000) ? 30001004 : 20001004)) : 1004), (byte)0, (byte)1, 0);
            ret.rank = rs.getInt("rank");
            ret.rankMove = rs.getInt("rankMove");
            ret.jobRank = rs.getInt("jobRank");
            ret.jobRankMove = rs.getInt("jobRankMove");
            ret.marriageId = rs.getInt("marriageId");
            ret.charmessage = rs.getString("charmessage");
            ret.expression = rs.getInt("expression");
            ret.constellation = rs.getInt("constellation");
            ret.skillzq = rs.getInt("skillzq");
            ret.bosslog = rs.getInt("bosslog");
            ret.PGMaxDamage = rs.getInt("PGMaxDamage");
            ret.jzname = rs.getInt("jzname");
            ret.mrfbrw = rs.getInt("mrfbrw");
            ret.mrsbossrw = rs.getInt("mrsbossrw");
            ret.mrsgrw = rs.getInt("mrsgrw");
            ret.mrfbrws = rs.getInt("mrfbrws");
            ret.mrsbossrws = rs.getInt("mrsbossrws");
            ret.mrsgrws = rs.getInt("mrsgrws");
            ret.mrsjrw = rs.getInt("mrsjrw");
            ret.hythd = rs.getInt("hythd");
            ret.mrfbrwa = rs.getInt("mrfbrwa");
            ret.mrsbossrwa = rs.getInt("mrsbossrwa");
            ret.mrsgrwa = rs.getInt("mrsgrwa");
            ret.mrfbrwas = rs.getInt("mrfbrwas");
            ret.mrsbossrwas = rs.getInt("mrsbossrwas");
            ret.mrsgrwas = rs.getInt("mrsgrwas");
            ret.blood = rs.getInt("blood");
            ret.ddj = rs.getInt("ddj");
            ret.vip = rs.getInt("vip");
            ret.vipexpired = rs.getLong("vipexpired");
            ret.djjl = rs.getInt("djjl");
            ret.qiandao = rs.getInt("qiandao");
            ret.jf = rs.getInt("jf");
            ret.pvpDeaths = rs.getInt("pvpDeaths");
            ret.pvpKills = rs.getInt("pvpKills");
            ret.pvpVictory = rs.getInt("pvpVictory");
            ret.month = rs.getInt("month");
            ret.day = rs.getInt("day");
            ret.prefix = rs.getInt("prefix");
            ret.shaguai = rs.getInt("shaguai");
            if (channelserver) {
                ret.pvpStats = MaplePvpStats.loadOrCreateFromDB(ret.accountid);
                ret.antiMacro = new MapleLieDetector(ret);
                final MapleMapFactory mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
                ret.map = mapFactory.getMap(ret.mapid);
                if (ret.map == null) {
                    ret.map = mapFactory.getMap(100000000);
                }
                MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
                if (portal == null) {
                    portal = ret.map.getPortal(0);
                    ret.initialSpawnPoint = 0;
                }
                ret.setPosition(portal.getPosition());
                final int partyid = rs.getInt("party");
                if (partyid >= 0) {
                    final MapleParty party = World.Party.getParty(partyid);
                    if (party != null && party.getMemberById(ret.id) != null) {
                        ret.party = party;
                    }
                }
                ret.bookCover = rs.getInt("monsterbookcover");
                ret.dojo = rs.getInt("dojo_pts");
                ret.dojoRecord = rs.getByte("dojoRecord");
                final String[] pets = rs.getString("pets").split(",");
                for (int j = 0; j < ret.petStore.length; ++j) {
                    ret.petStore[j] = Byte.parseByte(pets[j]);
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT * FROM achievements WHERE accountid = ?");
                ps.setInt(1, ret.accountid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.finishedAchievements.add(rs.getInt("achievementid"));
                }
            }
            rs.close();
            ps.close();
            boolean compensate_previousEvans = false;
            ps = con.prepareStatement("SELECT * FROM queststatus WHERE characterid = ?");
            ps.setInt(1, charid);
            rs = ps.executeQuery();
            pse = con.prepareStatement("SELECT * FROM queststatusmobs WHERE queststatusid = ?");
            while (rs.next()) {
                final int id = rs.getInt("quest");
                if (id == 170000) {
                    compensate_previousEvans = true;
                }
                final MapleQuest q = MapleQuest.getInstance(id);
                final MapleQuestStatus status = new MapleQuestStatus(q, rs.getByte("status"));
                final long cTime = rs.getLong("time");
                if (cTime > -1L) {
                    status.setCompletionTime(cTime * 1000L);
                }
                status.setForfeited(rs.getInt("forfeited"));
                status.setCustomData(rs.getString("customData"));
                ret.quests.put(q, status);
                pse.setInt(1, rs.getInt("queststatusid"));
                final ResultSet rsMobs = pse.executeQuery();
                while (rsMobs.next()) {
                    status.setMobKills(rsMobs.getInt("mob"), rsMobs.getInt("count"));
                }
                rsMobs.close();
            }
            rs.close();
            ps.close();
            pse.close();
            if (channelserver) {
                ret.CRand = new PlayerRandomStream();
                ret.monsterbook = MonsterBook.loadCards(charid);
                ps = con.prepareStatement("SELECT * FROM inventoryslot where characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    throw new RuntimeException("No Inventory slot column found in SQL. [inventoryslot]*********************");
                }
                ret.getInventory(MapleInventoryType.EQUIP).setSlotLimit(rs.getByte("equip"));
                ret.getInventory(MapleInventoryType.USE).setSlotLimit(rs.getByte("use"));
                ret.getInventory(MapleInventoryType.SETUP).setSlotLimit(rs.getByte("setup"));
                ret.getInventory(MapleInventoryType.ETC).setSlotLimit(rs.getByte("etc"));
                ret.getInventory(MapleInventoryType.CASH).setSlotLimit(rs.getByte("cash"));
                ps.close();
                rs.close();
                for (final Pair<IItem, MapleInventoryType> mit : ItemLoader.装备道具.loadItems(false, charid).values()) {
                    ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                    if (mit.getLeft().getPet() != null) {
                        ret.pets.add(mit.getLeft().getPet());
                    }
                }
                ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                ps.setInt(1, ret.accountid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ret.getClient().setAccountName(rs.getString("name"));
                    ret.lastGainHM = rs.getLong("lastGainHM");
                    ret.acash = rs.getInt("ACash");
                    ret.maplepoints = rs.getInt("mPoints");
                    ret.points = rs.getInt("points");
                    ret.vpoints = rs.getInt("vpoints");
                    if (rs.getTimestamp("lastlogon") != null) {
                        final Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(rs.getTimestamp("lastlogon").getTime());
                    }
                    rs.close();
                    ps.close();
                    ps = con.prepareStatement("UPDATE accounts SET lastlogon = CURRENT_TIMESTAMP() WHERE id = ?");
                    ps.setInt(1, ret.accountid);
                    ps.executeUpdate();
                }
                else {
                    rs.close();
                }
                ps.close();
                ps = con.prepareStatement("SELECT * FROM questinfo WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.questinfo.put(rs.getInt("quest"), rs.getString("customData"));
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT skillid, skilllevel, masterlevel, expiration FROM skills WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    final ISkill skil = SkillFactory.getSkill(rs.getInt("skillid"));
                    if (skil != null && GameConstants.isApplicableSkill(rs.getInt("skillid"))) {
                        ret.skills.put(skil, new SkillEntry(rs.getByte("skilllevel"), rs.getByte("masterlevel"), rs.getLong("expiration")));
                    }
                    else {
                        if (skil != null) {
                            continue;
                        }
                        final int[] remainingSp = ret.remainingSp;
                        final int skillBookForSkill = GameConstants.getSkillBookForSkill(rs.getInt("skillid"));
                        remainingSp[skillBookForSkill] += rs.getByte("skilllevel");
                    }
                }
                rs.close();
                ps.close();
                ret.expirationTask(false);
                ps = con.prepareStatement("SELECT * FROM characters WHERE accountid = ? ORDER BY level DESC");
                ps.setInt(1, ret.accountid);
                rs = ps.executeQuery();
                byte maxlevel_ = 0;
                while (rs.next()) {
                    if (rs.getInt("id") != charid) {
                        byte maxlevel = (byte)(rs.getShort("level") / 10);
                        if (maxlevel > 20) {
                            maxlevel = 20;
                        }
                        if (maxlevel <= maxlevel_) {
                            continue;
                        }
                        maxlevel_ = maxlevel;
                        ret.BlessOfFairy_Origin = rs.getString("name");
                    }
                    else {
                        if (charid >= 17000 || compensate_previousEvans || ret.job < 2200 || ret.job > 2218) {
                            continue;
                        }
                        for (int k = 0; k <= GameConstants.getSkillBook(ret.job); ++k) {
                            final int[] remainingSp2 = ret.remainingSp;
                            final int n = k;
                            remainingSp2[n] += 2;
                        }
                        ret.setQuestAdd(MapleQuest.getInstance(170000), (byte)0, null);
                    }
                }
                ret.skills.put(SkillFactory.getSkill(GameConstants.getBOF_ForJob(ret.job)), new SkillEntry(maxlevel_, (byte)0, -1L));
                ps.close();
                rs.close();
                for (int k = 0; k < 5; ++k) {
                    ret.skillMacros[k] = new SkillMacro(0, 0, 0, "", 0, k);
                }
                ps = con.prepareStatement("SELECT * FROM skillmacros WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    final int position = rs.getInt("position");
                    final SkillMacro macro = new SkillMacro(rs.getInt("skill1"), rs.getInt("skill2"), rs.getInt("skill3"), rs.getString("name"), rs.getInt("shout"), position);
                    ret.skillMacros[position] = macro;
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT `key`,`type`,`action` FROM keymap WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                final Map<Integer, Pair<Byte, Integer>> keyb = ret.keylayout.Layout();
                while (rs.next()) {
                    keyb.put(rs.getInt("key"), new Pair<Byte, Integer>(rs.getByte("type"), rs.getInt("action")));
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT `locationtype`,`map` FROM savedlocations WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.savedLocations[rs.getInt("locationtype")] = rs.getInt("map");
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT `characterid_to`,`when` FROM famelog WHERE characterid = ? AND DATEDIFF(NOW(),`when`) < 30");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                ret.lastfametime = 0L;
                ret.lastmonthfameids = new ArrayList<Integer>(31);
                while (rs.next()) {
                    ret.lastfametime = Math.max(ret.lastfametime, rs.getTimestamp("when").getTime());
                    ret.lastmonthfameids.add(rs.getInt("characterid_to"));
                }
                rs.close();
                ps.close();
                ret.buddylist.loadFromDb(charid);
                ret.storage = MapleStorage.loadStorage(ret.accountid);
                ret.cs = new CashShop(ret.accountid, charid, ret.getJob());
                ps = con.prepareStatement("SELECT sn FROM wishlist WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                int l = 0;
                while (rs.next()) {
                    ret.wishlist[l] = rs.getInt("sn");
                    ++l;
                }
                while (l < 10) {
                    ret.wishlist[l] = 0;
                    ++l;
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT mapid FROM trocklocations WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                int r = 0;
                while (rs.next()) {
                    ret.rocks[r] = rs.getInt("mapid");
                    ++r;
                }
                while (r < 10) {
                    ret.rocks[r] = 999999999;
                    ++r;
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT mapid FROM regrocklocations WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                r = 0;
                while (rs.next()) {
                    ret.regrocks[r] = rs.getInt("mapid");
                    ++r;
                }
                while (r < 5) {
                    ret.regrocks[r] = 999999999;
                    ++r;
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT * FROM mountdata WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException("No mount data found on SQL column");
                }
                final IItem mount = ret.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-18));
                ret.mount = new MapleMount(ret, (mount != null) ? mount.getItemId() : 0, (ret.job > 1000 && ret.job < 2000) ? 10001004 : ((ret.job >= 2000) ? ((ret.job == 2001 || ret.job >= 2200) ? 20011004 : ((ret.job >= 3000) ? 30001004 : 20001004)) : 1004), rs.getByte("Fatigue"), rs.getByte("Level"), rs.getInt("Exp"));
                ps.close();
                rs.close();
                ret.stats.recalcLocalStats(true);
            }
            else {
                for (final Pair<IItem, MapleInventoryType> mit : ItemLoader.装备道具.loadItems(true, charid).values()) {
                    ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                }
            }
        }
        catch (SQLException ess) {
            ess.printStackTrace();
            System.out.println("加载角色数据信息出错...");
            FileoutputUtil.outputFileError("logs/Packet_Except.log", ess);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException ex) {}
        }
        return ret;
    }
    
    public static void saveNewCharToDB(final MapleCharacter chr, final int type, final boolean db) {
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con.setTransactionIsolation(1);
            con.setAutoCommit(false);
            ps = con.prepareStatement("INSERT INTO characters (level, fame, str, dex, luk, `int`, exp, hp, mp, maxhp, maxmp, sp, ap, gm, skincolor, gender, job, hair, face, map, meso, hpApUsed, spawnpoint, party, buddyCapacity, monsterbookcover, dojo_pts, dojoRecord, pets, subcategory, marriageId, currentrep, totalrep, prefix, accountid, name, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
            ps.setInt(1, 1);
            ps.setShort(2, (short)0);
            final PlayerStats stat = chr.stats;
            ps.setShort(3, stat.getStr());
            ps.setShort(4, stat.getDex());
            ps.setShort(5, stat.getInt());
            ps.setShort(6, stat.getLuk());
            ps.setInt(7, 0);
            ps.setShort(8, stat.getHp());
            ps.setShort(9, stat.getMp());
            ps.setShort(10, stat.getMaxHp());
            ps.setShort(11, stat.getMaxMp());
            ps.setString(12, "0,0,0,0,0,0,0,0,0,0");
            ps.setShort(13, (short)0);
            ps.setInt(14, chr.getClient().gm ? 5 : 0);
            ps.setByte(15, chr.skinColor);
            ps.setByte(16, chr.gender);
            ps.setShort(17, chr.job);
            ps.setInt(18, chr.hair);
            ps.setInt(19, chr.face);
            ps.setInt(20, (type == 1) ? 0 : ((type == 0) ? 130030000 : ((type == 2) ? 914000000 : 910000000)));
            ps.setInt(21, chr.meso);
            ps.setShort(22, (short)0);
            ps.setByte(23, (byte)0);
            ps.setInt(24, -1);
            ps.setByte(25, chr.buddylist.getCapacity());
            ps.setInt(26, 0);
            ps.setInt(27, 0);
            ps.setInt(28, 0);
            ps.setString(29, "-1,-1,-1");
            ps.setInt(30, 0);
            ps.setInt(31, 0);
            ps.setInt(32, 0);
            ps.setInt(33, 0);
            ps.setInt(34, chr.prefix);
            ps.setInt(35, chr.getAccountID());
            ps.setString(36, chr.name);
            ps.setByte(37, chr.world);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (!rs.next()) {
                throw new DatabaseException("Inserting char failed.");
            }
            chr.id = rs.getInt(1);
            ps.close();
            rs.close();
            ps = con.prepareStatement("INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", 1);
            pse = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (final MapleQuestStatus q : chr.quests.values()) {
                ps.setInt(2, q.getQuest().getId());
                ps.setInt(3, q.getStatus());
                ps.setInt(4, (int)(q.getCompletionTime() / 1000L));
                ps.setInt(5, q.getForfeited());
                ps.setString(6, q.getCustomData());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();
                if (q.hasMobKills()) {
                    for (final int mob : q.getMobKills().keySet()) {
                        pse.setInt(1, rs.getInt(1));
                        pse.setInt(2, mob);
                        pse.setInt(3, q.getMobKills(mob));
                        pse.executeUpdate();
                    }
                }
                rs.close();
            }
            ps.close();
            pse.close();
            ps = con.prepareStatement("INSERT INTO inventoryslot (characterid, `equip`, `use`, `setup`, `etc`, `cash`) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setByte(2, (byte)32);
            ps.setByte(3, (byte)32);
            ps.setByte(4, (byte)32);
            ps.setByte(5, (byte)32);
            ps.setByte(6, (byte)60);
            ps.execute();
            ps.close();
            ps = con.prepareStatement("INSERT INTO mountdata (characterid, `Level`, `Exp`, `Fatigue`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setByte(2, (byte)1);
            ps.setInt(3, 0);
            ps.setByte(4, (byte)0);
            ps.execute();
            ps.close();
            final List<Pair<IItem, MapleInventoryType>> listing = new ArrayList<Pair<IItem, MapleInventoryType>>();
            for (final MapleInventory iv : chr.inventory) {
                for (final IItem item : iv.list()) {
                    listing.add(new Pair<IItem, MapleInventoryType>(item, iv.getType()));
                }
            }
            ItemLoader.装备道具.saveItems(listing, con, chr.id);
            final int[] array1 = { 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 23, 25, 26, 27, 29, 31, 34, 35, 37, 38, 40, 41, 43, 44, 45, 46, 48, 50, 56, 57, 59, 60, 61, 62, 63, 64, 65 };
            final int[] array2 = { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6 };
            final int[] array3 = { 10, 12, 13, 18, 24, 21, 8, 5, 0, 4, 1, 19, 14, 15, 52, 2, 17, 11, 3, 20, 16, 23, 9, 50, 51, 6, 22, 7, 53, 54, 100, 101, 102, 103, 104, 105, 106 };
            ps = con.prepareStatement("INSERT INTO keymap (characterid, `key`, `type`, `action`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (int i = 0; i < array1.length; ++i) {
                ps.setInt(2, array1[i]);
                ps.setInt(3, array2[i]);
                ps.setInt(4, array3[i]);
                ps.execute();
            }
            ps.close();
            con.commit();
        }
        catch (DatabaseException ex2) {}
        catch (SQLException e) {
            e.printStackTrace();
            FileoutputUtil.outputFileError("logs/Packet_Except.log", e);
            System.err.println("[charsave] Error saving character data");
            try {
                con.rollback();
            }
            catch (SQLException ex) {
                e.printStackTrace();
                FileoutputUtil.outputFileError("logs/Packet_Except.log", ex);
                System.err.println("[charsave] Error Rolling Back");
            }
            try {
                if (pse != null) {
                    pse.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(4);
            }
            catch (SQLException e2) {
                e2.printStackTrace();
                FileoutputUtil.outputFileError("logs/Packet_Except.log", e2);
                System.err.println("[charsave] Error going back to autocommit mode");
            }
        }
        finally {
            try {
                if (pse != null) {
                    pse.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(4);
            }
            catch (SQLException e3) {
                e3.printStackTrace();
                FileoutputUtil.outputFileError("logs/Packet_Except.log", e3);
                System.err.println("[charsave] Error going back to autocommit mode");
            }
        }
    }
    
    public static void deleteWhereCharacterId(final Connection con, final String sql, final int id) throws SQLException {
        final PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public static boolean ban(final String id, final String reason, final boolean accountId, final int gmlevel, final boolean hellban) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            if (id.matches("/[0-9]{1,3}/..*")) {
                if (id != "/127.0.0.1") {}
                return true;
            }
            PreparedStatement ps;
            if (accountId) {
                ps = con.prepareStatement("SELECT id FROM accounts WHERE name = ?");
            }
            else {
                ps = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
            }
            boolean ret = false;
            ps.setString(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int z = rs.getInt(1);
                final PreparedStatement psb = con.prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE id = ? AND gm < ?");
                psb.setString(1, reason);
                psb.setInt(2, z);
                psb.setInt(3, gmlevel);
                psb.execute();
                psb.close();
                if (gmlevel > 100) {
                    final PreparedStatement psa = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                    psa.setInt(1, z);
                    final ResultSet rsa = psa.executeQuery();
                    if (rsa.next()) {
                        final String sessionIP = rsa.getString("sessionIP");
                        if (sessionIP != null && sessionIP.matches("/[0-9]{1,3}/..*")) {
                            final PreparedStatement psz = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                            psz.setString(1, sessionIP);
                            psz.execute();
                            psz.close();
                        }
                        if (rsa.getString("macs") != null) {
                            final String[] macData = rsa.getString("macs").split(", ");
                            if (macData.length > 0) {
                                MapleClient.banMacs(macData);
                            }
                        }
                        if (hellban) {
                            final PreparedStatement pss = con.prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE email = ?" + ((sessionIP == null) ? "" : " OR SessionIP = ?"));
                            pss.setString(1, reason);
                            pss.setString(2, rsa.getString("email"));
                            if (sessionIP != null) {
                                pss.setString(3, sessionIP);
                            }
                            pss.execute();
                            pss.close();
                        }
                    }
                    rsa.close();
                    psa.close();
                }
                ret = true;
            }
            rs.close();
            ps.close();
            return ret;
        }
        catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
            return false;
        }
    }
    
    public static String getAriantRoomLeaderName(final int room) {
        return MapleCharacter.ariantroomleader[room];
    }
    
    public static int getAriantSlotsRoom(final int room) {
        return MapleCharacter.ariantroomslot[room];
    }
    
    public static void removeAriantRoom(final int room) {
        MapleCharacter.ariantroomleader[room] = "";
        MapleCharacter.ariantroomslot[room] = 0;
    }
    
    public static void setAriantRoomLeader(final int room, final String charname) {
        MapleCharacter.ariantroomleader[room] = charname;
    }
    
    public static void setAriantSlotRoom(final int room, final int slot) {
        MapleCharacter.ariantroomslot[room] = slot;
    }
    
    private MapleCharacter(final boolean ChannelServer) {
        this.guildrank = 5;
        this.allianceRank = 5;
        this.fairyExp = 10;
        this.guildid = 0;
        this.fallcounter = 0;
        this.rank = 1;
        this.rankMove = 0;
        this.jobRank = 1;
        this.jobRankMove = 0;
        this.marriageItemId = 0;
        this.linkMid = 0;
        this.coconutteam = 0;
        this.followid = 0;
        this.battleshipHP = 0;
        this.old = new Point(0, 0);
        this.hasSummon = false;
        this.remainingSp = new int[10];
        this.skills = new LinkedHashMap<ISkill, SkillEntry>();
        this.effects = new ConcurrentEnumMap<MapleBuffStat, MapleBuffStatValueHolder>(MapleBuffStat.class);
        this.coolDowns = new LinkedHashMap<Integer, MapleCoolDownValueHolder>();
        this.diseases = new ConcurrentEnumMap<MapleDisease, MapleDiseaseValueHolder>(MapleDisease.class);
        this.finishedAchievements = new ArrayList<Integer>();
        this.invincible = false;
        this.canTalk = true;
        this.clone = false;
        this.followinitiator = false;
        this.followon = false;
        this.skillMacros = new SkillMacro[5];
        this.nextConsume = 0L;
        this.pqStartTime = 0L;
        this.pyramidSubway = null;
        this.pendingExpiration = null;
        this.pendingSkills = null;
        this.movedMobs = new HashMap<Integer, Integer>();
        this.teleportname = "";
        this.lasttime = 0L;
        this.currenttime = 0L;
        this.deadtime = 1000L;
        this.apprentice = 0;
        this.master = 0;
        this.DebugMessage = false;
        this.ariantScore = 0;
        this.skillzq = 0;
        this.bosslog = 0;
        this.PGMaxDamage = 0;
        this.jzname = 0;
        this.mrsjrw = 0;
        this.mrsgrw = 0;
        this.mrsbossrw = 0;
        this.mrfbrw = 0;
        this.hythd = 0;
        this.mrsgrwa = 0;
        this.mrsbossrwa = 0;
        this.mrfbrwa = 0;
        this.mrsgrws = 0;
        this.mrsbossrws = 0;
        this.mrfbrws = 0;
        this.mrsgrwas = 0;
        this.mrsbossrwas = 0;
        this.mrfbrwas = 0;
        this.ddj = 0;
        this.vip = 0;
        this.vipexpired = 0l;
        this.djjl = 0;
        this.qiandao = 0;
        this.jf = 0;
        this.shaguai = 0;
        this.curPGDamage = 0;
        this.lastRecoveryTime = 0L;
        this.MobVac = 0;
        this.MobVac2 = 0;
        this.cancelEnergyRunnable = new Runnable() {
            @Override
            public void run() {
                final Integer energyLevel = 0;
                MapleCharacter.this.setBuffedValue(MapleBuffStat.能量获得, energyLevel);
                final List<Pair<MapleBuffStat, Integer>> stat = Collections.singletonList(new Pair<MapleBuffStat, Integer>(MapleBuffStat.能量获得, energyLevel));
                MapleCharacter.this.client.getSession().write(MaplePacketCreator.能量条(stat, 0));
                MapleCharacter.this.cancelEnergy = true;
            }
        };
        this.cancelEnergy = true;
        this.setStance(0);
        this.setPosition(new Point(0, 0));
        this.inventory = new MapleInventory[MapleInventoryType.values().length];
        for (final MapleInventoryType type : MapleInventoryType.values()) {
            this.inventory[type.ordinal()] = new MapleInventory(type, (byte)100);
        }
        this.quests = new LinkedHashMap<MapleQuest, MapleQuestStatus>();
        this.stats = new PlayerStats(this);
        for (int i = 0; i < this.remainingSp.length; ++i) {
            this.remainingSp[i] = 0;
        }
        if (ChannelServer) {
            this.lastMoveItemTime = 0L;
            this.lastCheckPeriodTime = 0L;
            this.lastQuestTime = 0L;
            this.lastHPTime = 0L;
            this.lastMPTime = 0L;
            this.lastComboTime = 0L;
            this.mulung_energy = 0;
            this.aranCombo = 0;
            this.keydown_skill = 0L;
            this.smega = true;
            this.petStore = new byte[3];
            for (int i = 0; i < this.petStore.length; ++i) {
                this.petStore[i] = -1;
            }
            this.wishlist = new int[10];
            this.rocks = new int[10];
            this.regrocks = new int[5];
            this.clones = (WeakReference<MapleCharacter>[])new WeakReference[25];
            for (int i = 0; i < this.clones.length; ++i) {
                this.clones[i] = new WeakReference<MapleCharacter>(null);
            }
            (this.inst = new AtomicInteger()).set(0);
            this.keylayout = new MapleKeyLayout();
            this.doors = new ArrayList<MapleDoor>();
            this.controlled = new LinkedHashSet<MapleMonster>();
            this.summons = new LinkedList<MapleSummon>();
            this.summonsLock = new ReentrantReadWriteLock();
            this.visibleMapObjects = new LinkedHashSet<MapleMapObject>();
            this.visibleMapObjectsLock = new ReentrantReadWriteLock();
            this.controlledLock = new ReentrantReadWriteLock();
            this.pendingCarnivalRequests = new LinkedList<MapleCarnivalChallenge>();
            this.savedLocations = new int[SavedLocationType.values().length];
            for (int i = 0; i < SavedLocationType.values().length; ++i) {
                this.savedLocations[i] = -1;
            }
            this.questinfo = new LinkedHashMap<Integer, String>();
            this.anticheat = new CheatTracker(this);
            this.pets = new ArrayList<MaplePet>();
        }
    }
    
    public void saveToDB(final boolean dc, final boolean fromcs) {
        if (this.isClone()) {
            return;
        }
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        final PreparedStatement ps4 = null;
        ResultSet rs = null;
        try {
            con.setTransactionIsolation(1);
            con.setAutoCommit(false);
            ps = con.prepareStatement("UPDATE characters SET level = ?, fame = ?, str = ?, dex = ?, luk = ?, `int` = ?, exp = ?, hp = ?, mp = ?, maxhp = ?, maxmp = ?, sp = ?, ap = ?, gm = ?, skincolor = ?, gender = ?, job = ?, hair = ?, face = ?, map = ?, meso = ?, hpApUsed = ?, spawnpoint = ?, party = ?, buddyCapacity = ?, monsterbookcover = ?, dojo_pts = ?, dojoRecord = ?, pets = ?, subcategory = ?, marriageId = ?, currentrep = ?, totalrep = ?, charmessage = ?, expression = ?, constellation = ?, blood = ?, month = ?, day = ?, beans = ?, prefix = ?, skillzq = ?, bosslog = ?, PGMaxDamage = ?, jzname = ?, mrfbrw = ?, mrsjrw = ?, mrsgrw = ?, mrsbossrw = ?, hythd = ?, mrsgrwa = ?, mrfbrwa = ?, mrsbossrwa = ?, mrsgrws = ?,  mrsbossrws = ?, mrfbrws = ?, mrsgrwas = ?,  mrsbossrwas = ?, mrfbrwas = ?, ddj = ?, vip = ?, djjl = ?, qiandao = ?, jf = ?, pvpDeaths = ?, pvpKills = ?, pvpVictory = ?, shaguai = ?, name = ? WHERE id = ?", DatabaseConnection.RETURN_GENERATED_KEYS);
            ps.setInt(1, this.level);
            ps.setShort(2, this.fame);
            ps.setShort(3, this.stats.getStr());
            ps.setShort(4, this.stats.getDex());
            ps.setShort(5, this.stats.getLuk());
            ps.setShort(6, this.stats.getInt());
            ps.setInt(7, this.exp);
            ps.setShort(8, (short)((this.stats.getHp() < 1) ? 50 : this.stats.getHp()));
            ps.setShort(9, this.stats.getMp());
            ps.setShort(10, this.stats.getMaxHp());
            ps.setShort(11, this.stats.getMaxMp());
            final StringBuilder sps = new StringBuilder();
            for (int i = 0; i < this.remainingSp.length; ++i) {
                sps.append(this.remainingSp[i]);
                sps.append(",");
            }
            final String sp = sps.toString();
            ps.setString(12, sp.substring(0, sp.length() - 1));
            ps.setShort(13, this.remainingAp);
            ps.setByte(14, this.gmLevel);
            ps.setByte(15, this.skinColor);
            ps.setByte(16, this.gender);
            ps.setShort(17, this.job);
            ps.setInt(18, this.hair);
            ps.setInt(19, this.face);
            if (!fromcs && this.map != null) {
                if (this.map.getForcedReturnId() != 999999999) {
                    ps.setInt(20, this.map.getForcedReturnId());
                }
                else {
                    ps.setInt(20, (this.stats.getHp() < 1) ? this.map.getReturnMapId() : this.map.getId());
                }
            }
            else {
                ps.setInt(20, this.mapid);
            }
            ps.setInt(21, this.meso);
            ps.setShort(22, this.hpApUsed);
            if (this.map == null) {
                ps.setByte(23, (byte)0);
            }
            else {
                final MaplePortal closest = this.map.findClosestSpawnpoint(this.getPosition());
                ps.setByte(23, (byte)((closest != null) ? closest.getId() : 0));
            }
            ps.setInt(24, (this.party != null) ? this.party.getId() : -1);
            ps.setShort(25, this.buddylist.getCapacity());
            ps.setInt(26, this.bookCover);
            ps.setInt(27, this.dojo);
            ps.setInt(28, this.dojoRecord);
            final StringBuilder petz = new StringBuilder();
            int petLength = 0;
            for (final MaplePet pet : this.pets) {
                pet.saveToDb();
                if (pet.getSummoned()) {
                    petz.append(pet.getInventoryPosition());
                    petz.append(",");
                    ++petLength;
                }
            }
            while (petLength < 3) {
                petz.append("-1,");
                ++petLength;
            }
            final String petstring = petz.toString();
            ps.setString(29, petstring.substring(0, petstring.length() - 1));
            ps.setByte(30, this.subcategory);
            ps.setInt(31, this.marriageId);
            ps.setInt(32, this.currentrep);
            ps.setInt(33, this.totalrep);
            ps.setString(34, this.charmessage);
            ps.setInt(35, this.expression);
            ps.setInt(36, this.constellation);
            ps.setInt(37, this.blood);
            ps.setInt(38, this.month);
            ps.setInt(39, this.day);
            ps.setInt(40, this.beans);
            ps.setInt(41, this.prefix);
            ps.setInt(42, this.skillzq);
            ps.setInt(43, this.bosslog);
            ps.setInt(44, this.PGMaxDamage);
            ps.setInt(45, this.jzname);
            ps.setInt(46, this.mrfbrw);
            ps.setInt(47, this.mrsjrw);
            ps.setInt(48, this.mrsgrw);
            ps.setInt(49, this.mrsbossrw);
            ps.setInt(50, this.hythd);
            ps.setInt(51, this.mrsgrwa);
            ps.setInt(52, this.mrfbrwa);
            ps.setInt(53, this.mrsbossrwa);
            ps.setInt(54, this.mrsgrws);
            ps.setInt(55, this.mrsbossrws);
            ps.setInt(56, this.mrfbrws);
            ps.setInt(57, this.mrsgrwas);
            ps.setInt(58, this.mrsbossrwas);
            ps.setInt(59, this.mrfbrwas);
            ps.setInt(60, this.ddj);
            ps.setInt(61, this.vip);
            ps.setInt(62, this.djjl);
            ps.setInt(63, this.qiandao);
            ps.setInt(64, this.jf);
            ps.setInt(65, this.pvpDeaths);
            ps.setInt(66, this.pvpKills);
            ps.setInt(67, this.pvpVictory);
            ps.setInt(68, this.shaguai);
            ps.setString(69, this.name);
            ps.setInt(70, this.id);
            if (ps.executeUpdate() < 1) {
                ps.close();
                throw new DatabaseException("Character not in database (" + this.id + ")");
            }
            ps.close();
            ps = con.prepareStatement("UPDATE skillmacros SET `skill1` = ?, `skill2` = ?, `skill3` = ?, `name` = ?, `shout` = ? WHERE `characterid` = ? and `position` = ?");
            ps.setInt(6, this.id);
            for (int j = 0; j < 5; ++j) {
                final SkillMacro macro = this.skillMacros[j];
                if (macro != null) {
                    ps.setInt(1, macro.getSkill1());
                    ps.setInt(2, macro.getSkill2());
                    ps.setInt(3, macro.getSkill3());
                    ps.setString(4, macro.getName());
                    ps.setInt(5, macro.getShout());
                    ps.setInt(7, j);
                    ps.executeUpdate();
                }
            }
            ps = con.prepareStatement("UPDATE inventoryslot SET `equip` = ?, `use` = ?, `setup` = ?, `etc` = ?, `cash` = ? WHERE characterid = ?");
            ps.setByte(1, this.getInventory(MapleInventoryType.EQUIP).getSlotLimit());
            ps.setByte(2, this.getInventory(MapleInventoryType.USE).getSlotLimit());
            ps.setByte(3, this.getInventory(MapleInventoryType.SETUP).getSlotLimit());
            ps.setByte(4, this.getInventory(MapleInventoryType.ETC).getSlotLimit());
            ps.setByte(5, this.getInventory(MapleInventoryType.CASH).getSlotLimit());
            ps.setInt(6, this.id);
            ps.executeUpdate();
            ps.close();
            final List<Pair<IItem, MapleInventoryType>> listing = new ArrayList<Pair<IItem, MapleInventoryType>>();
            for (final MapleInventory iv : this.inventory) {
                for (final IItem item : iv.list()) {
                    listing.add(new Pair<IItem, MapleInventoryType>(item, iv.getType()));
                }
            }
            if (con != null) {
                ItemLoader.装备道具.saveItems(listing, con, this.id);
            }
            else {
                ItemLoader.装备道具.saveItems(listing, this.id);
            }
            ps = con.prepareStatement("SELECT * FROM questinfo WHERE `characterid` = ? AND `quest` = ? LIMIT 1");
            ps.setInt(1, this.id);
            for (final Map.Entry<Integer, String> q : this.questinfo.entrySet()) {
                final int questID = q.getKey();
                ps.setInt(2, questID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ps2 = con.prepareStatement("UPDATE questinfo SET `customData` = ? WHERE `characterid` = ? AND `quest` = ?");
                    ps2.setString(1, q.getValue());
                    ps2.setInt(2, this.id);
                    ps2.setInt(3, questID);
                    ps2.executeUpdate();
                    ps2.close();
                }
                else {
                    ps2 = con.prepareStatement("INSERT INTO questinfo (`characterid`, `quest`, `customData`) VALUES (?, ?, ?)");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, q.getKey());
                    ps2.setString(3, q.getValue());
                    ps2.executeUpdate();
                    ps2.close();
                }
                rs.close();
            }
            ps.close();
            ps = con.prepareStatement("SELECT * FROM queststatus WHERE `characterid` = ? AND `quest` = ? LIMIT 1");
            ps.setInt(1, this.id);
            for (final MapleQuestStatus q2 : this.quests.values()) {
                final int questID = q2.getQuest().getId();
                ps.setInt(2, questID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ps2 = con.prepareStatement("UPDATE queststatus SET `status` = ?, `time` = ?, `forfeited` = ?, `customData` = ? WHERE `characterid` = ? AND `quest` = ?");
                    ps2.setInt(1, q2.getStatus());
                    ps2.setInt(2, (int)(q2.getCompletionTime() / 1000L));
                    ps2.setInt(3, q2.getForfeited());
                    ps2.setString(4, q2.getCustomData());
                    ps2.setInt(5, this.id);
                    ps2.setInt(6, questID);
                    ps2.executeUpdate();
                    final int queststatusid = rs.getInt("queststatusid");
                    if (q2.hasMobKills()) {
                        ps3 = con.prepareStatement("UPDATE queststatusmobs SET `count` = ? WHERE `queststatusid` = ? AND `mob` = ?");
                        for (final int mob : q2.getMobKills().keySet()) {
                            ps3.setInt(1, q2.getMobKills(mob));
                            ps3.setInt(2, queststatusid);
                            ps3.setInt(3, mob);
                            ps3.executeUpdate();
                        }
                        ps3.close();
                    }
                    rs.close();
                    ps2.close();
                }
                else {
                    ps2 = con.prepareStatement("INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", 1);
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, q2.getQuest().getId());
                    ps2.setInt(3, q2.getStatus());
                    ps2.setInt(4, (int)(q2.getCompletionTime() / 1000L));
                    ps2.setInt(5, q2.getForfeited());
                    ps2.setString(6, q2.getCustomData());
                    ps2.executeUpdate();
                    rs.close();
                    rs = ps2.getGeneratedKeys();
                    rs.next();
                    if (q2.hasMobKills()) {
                        ps3 = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
                        for (final int mob2 : q2.getMobKills().keySet()) {
                            ps3.setInt(1, rs.getInt(1));
                            ps3.setInt(2, mob2);
                            ps3.setInt(3, q2.getMobKills(mob2));
                            ps3.executeUpdate();
                        }
                        ps3.close();
                    }
                    rs.close();
                    ps2.close();
                }
            }
            ps.close();
            ps = con.prepareStatement("SELECT * FROM skills WHERE `characterid` = ?");
            ps.setInt(1, this.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int skillID = rs.getInt("skillid");
                boolean find = false;
                for (final Map.Entry<ISkill, SkillEntry> skill : this.skills.entrySet()) {
                    if (skill.getKey().getId() == skillID) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    ps2 = con.prepareStatement("DELETE FROM skills WHERE `characterid` = ? AND `skillid` = ?");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, skillID);
                    ps2.execute();
                    ps2.close();
                }
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("SELECT * FROM skills WHERE `characterid` = ? AND `skillid` = ? LIMIT 1");
            ps.setInt(1, this.id);
            for (final Map.Entry<ISkill, SkillEntry> skill2 : this.skills.entrySet()) {
                final int skillID2 = skill2.getKey().getId();
                if (!GameConstants.isApplicableSkill(skillID2)) {
                    continue;
                }
                ps.setInt(2, skillID2);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ps2 = con.prepareStatement("UPDATE skills SET `skilllevel` = ?, `masterlevel` = ?, `expiration` = ? WHERE `characterid` = ? AND `skillid` = ?");
                    ps2.setByte(1, skill2.getValue().skillevel);
                    ps2.setByte(2, skill2.getValue().masterlevel);
                    ps2.setLong(3, skill2.getValue().expiration);
                    ps2.setInt(4, this.id);
                    ps2.setInt(5, skill2.getKey().getId());
                    ps2.executeUpdate();
                    ps2.close();
                }
                else {
                    ps2 = con.prepareStatement("INSERT INTO skills (characterid, skillid, skilllevel, masterlevel, expiration) VALUES (?, ?, ?, ?, ?)");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, skill2.getKey().getId());
                    ps2.setByte(3, skill2.getValue().skillevel);
                    ps2.setByte(4, skill2.getValue().masterlevel);
                    ps2.setLong(5, skill2.getValue().expiration);
                    ps2.execute();
                }
                rs.close();
            }
            ps.close();
            final List<MapleCoolDownValueHolder> cd = this.getCooldowns();
            if (dc && cd.size() > 0) {
                ps = con.prepareStatement("INSERT INTO skills_cooldowns (charid, SkillID, StartTime, length) VALUES (?, ?, ?, ?)");
                ps.setInt(1, this.getId());
                for (final MapleCoolDownValueHolder cooling : cd) {
                    ps.setInt(2, cooling.skillId);
                    ps.setLong(3, cooling.startTime);
                    ps.setLong(4, cooling.length);
                    ps.execute();
                }
                ps.close();
            }
            ps = con.prepareStatement("SELECT * FROM savedlocations WHERE `characterid` = ?");
            ps.setInt(1, this.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int locationType = rs.getInt("locationtype");
                boolean find2 = false;
                for (final SavedLocationType savedLocationType : SavedLocationType.values()) {
                    if (this.savedLocations[savedLocationType.getValue()] != -1 && savedLocationType.getValue() == locationType) {
                        find2 = true;
                        break;
                    }
                }
                if (!find2) {
                    ps2 = con.prepareStatement("DELETE FROM savedlocations WHERE `characterid` = ? AND `locationtype` = ?");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, locationType);
                    ps2.execute();
                    ps2.close();
                }
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("SELECT * FROM savedlocations WHERE `characterid` = ? AND `locationtype` = ? LIMIT 1");
            ps.setInt(1, this.id);
            for (final SavedLocationType savedLocationType2 : SavedLocationType.values()) {
                final int locationType2 = savedLocationType2.getValue();
                ps.setInt(2, locationType2);
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (this.savedLocations[savedLocationType2.getValue()] != -1) {
                        ps2 = con.prepareStatement("UPDATE savedlocations SET `map` = ? WHERE `characterid` = ? AND `locationtype` = ?");
                        ps2.setInt(1, this.savedLocations[savedLocationType2.getValue()]);
                        ps2.setInt(2, this.id);
                        ps2.setInt(3, savedLocationType2.getValue());
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
                else if (this.savedLocations[savedLocationType2.getValue()] != -1) {
                    ps2 = con.prepareStatement("INSERT INTO savedlocations (characterid, `locationtype`, `map`) VALUES (?, ?, ?)");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, savedLocationType2.getValue());
                    ps2.setInt(3, this.savedLocations[savedLocationType2.getValue()]);
                    ps2.execute();
                    ps2.close();
                }
                rs.close();
            }
            ps.close();
            if (this.buddylist.changed()) {
                ps = con.prepareStatement("SELECT * FROM buddies WHERE `characterid` = ?");
                ps.setInt(1, this.id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    final int buddyID = rs.getInt("buddyid");
                    boolean find2 = false;
                    for (final BuddyEntry entry : this.buddylist.getBuddies()) {
                        if (entry != null && entry.getCharacterId() == buddyID) {
                            find2 = true;
                            break;
                        }
                    }
                    if (!find2) {
                        ps2 = con.prepareStatement("DELETE FROM buddies WHERE `characterid` = ? AND `buddyid` = ?");
                        ps2.setInt(1, this.id);
                        ps2.setInt(2, buddyID);
                        ps2.execute();
                        ps2.close();
                    }
                }
                ps.close();
                rs.close();
                ps = con.prepareStatement("SELECT * FROM buddies WHERE `characterid` = ? AND `buddyid` = ? LIMIT 1");
                ps.setInt(1, this.id);
                for (final BuddyEntry entry2 : this.buddylist.getBuddies()) {
                    final int buddyID2 = entry2.getCharacterId();
                    ps.setInt(2, buddyID2);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        if (entry2 != null) {
                            ps2 = con.prepareStatement("UPDATE buddies SET `pending` = ?, `groupname` = ? WHERE `characterid` = ? AND `buddyid` = ?");
                            ps2.setInt(1, entry2.isVisible() ? 0 : 1);
                            ps2.setString(2, entry2.getGroup());
                            ps2.setInt(3, this.id);
                            ps2.setInt(4, buddyID2);
                            ps2.executeUpdate();
                            ps2.close();
                        }
                    }
                    else if (entry2 != null) {
                        ps2 = con.prepareStatement("INSERT INTO buddies (`characterid`, `buddyid`, `pending`, `groupname`) VALUES (?, ?, ?, ?)");
                        ps2.setInt(1, this.id);
                        ps2.setInt(2, buddyID2);
                        ps2.setInt(3, entry2.isVisible() ? 0 : 1);
                        ps2.setString(4, entry2.getGroup());
                        ps2.execute();
                        ps2.close();
                    }
                    rs.close();
                }
                ps.close();
            }
            ps = con.prepareStatement("UPDATE accounts SET `ACash` = ?, `mPoints` = ?, `points` = ?, `vpoints` = ? WHERE id = ?");
            ps.setInt(1, this.acash);
            ps.setInt(2, this.maplepoints);
            ps.setInt(3, this.points);
            ps.setInt(4, this.vpoints);
            ps.setInt(5, this.client.getAccID());
            ps.execute();
            ps.close();
            if (this.storage != null) {
                this.storage.saveToDB();
            }
            ps = con.prepareStatement("UPDATE accounts SET `lastGainHM` = ? WHERE id = ?");
            ps.setLong(1, this.lastGainHM);
            ps.setInt(2, this.client.getAccID());
            ps.execute();
            ps.close();
            if (this.cs != null) {
                this.cs.save();
            }
            PlayerNPC.updateByCharId(this);
            this.keylayout.saveKeys(this.id);
            this.mount.saveMount(this.id);
            this.monsterbook.saveCards(this.id);
            this.pvpStats.saveToDb(this.accountid);
            this.deleteWhereCharacterId(con, "DELETE FROM wishlist WHERE characterid = ?");
            for (int k = 0; k < this.getWishlistSize(); ++k) {
                ps = con.prepareStatement("INSERT INTO wishlist(characterid, sn) VALUES(?, ?) ");
                ps.setInt(1, this.getId());
                ps.setInt(2, this.wishlist[k]);
                ps.execute();
                ps.close();
            }
            ps = con.prepareStatement("SELECT * FROM trocklocations WHERE `characterid` = ?");
            ps.setInt(1, this.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int mapid = rs.getInt("mapid");
                boolean find2 = false;
                for (int l = 0; l < this.rocks.length; ++l) {
                    if (this.rocks[l] == mapid) {
                        find2 = true;
                        break;
                    }
                }
                if (!find2) {
                    ps2 = con.prepareStatement("DELETE FROM trocklocations WHERE `characterid` = ? AND `mapid` = ?");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, mapid);
                    ps2.execute();
                    ps2.close();
                }
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("SELECT * FROM trocklocations WHERE `characterid` = ? AND `mapid` = ?");
            ps.setInt(1, this.id);
            for (int k = 0; k < this.rocks.length; ++k) {
                final int mapid2 = this.rocks[k];
                ps.setInt(2, mapid2);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    ps2 = con.prepareStatement("INSERT INTO trocklocations (`characterid`, `mapid`) VALUES (?, ?)");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, mapid2);
                    ps2.execute();
                    ps2.close();
                }
                rs.close();
            }
            ps.close();
            ps = con.prepareStatement("SELECT * FROM regrocklocations WHERE `characterid` = ?");
            ps.setInt(1, this.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int mapid = rs.getInt("mapid");
                boolean find2 = false;
                for (int l = 0; l < this.regrocks.length; ++l) {
                    if (this.regrocks[l] == mapid) {
                        find2 = true;
                        break;
                    }
                }
                if (!find2) {
                    ps2 = con.prepareStatement("DELETE FROM regrocklocations WHERE `characterid` = ? AND `mapid` = ?");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, mapid);
                    ps2.execute();
                    ps2.close();
                }
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("SELECT * FROM regrocklocations WHERE `characterid` = ? AND `mapid` = ?");
            ps.setInt(1, this.id);
            for (int k = 0; k < this.regrocks.length; ++k) {
                final int mapid2 = this.regrocks[k];
                ps.setInt(2, mapid2);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    ps2 = con.prepareStatement("INSERT INTO regrocklocations (`characterid`, `mapid`) VALUES (?, ?)");
                    ps2.setInt(1, this.id);
                    ps2.setInt(2, mapid2);
                    ps2.execute();
                    ps2.close();
                }
                rs.close();
            }
            ps.close();
            con.commit();
        }
        catch (SQLException ex2) {}
        catch (DatabaseException ex3) {}
        catch (UnsupportedOperationException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
            System.err.println(MapleClient.getLogMessage(this, "[charsave] 保存角色数据出现错误") + e);
            try {
                con.rollback();
            }
            catch (SQLException ex) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, ex);
                System.err.println(MapleClient.getLogMessage(this, "[charsave] 保存角色数据出现错误") + e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(4);
            }
            catch (SQLException e2) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e2);
                System.err.println(MapleClient.getLogMessage(this, "[charsave] Error going back to autocommit mode") + e2);
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(4);
            }
            catch (SQLException e3) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e3);
                System.err.println(MapleClient.getLogMessage(this, "[charsave] Error going back to autocommit mode") + e3);
            }
        }
    }
    
    private void deleteWhereCharacterId(final Connection con, final String sql) throws SQLException {
        deleteWhereCharacterId(con, sql, this.id);
    }
    
    public PlayerStats getStat() {
        return this.stats;
    }
    
    public PlayerRandomStream CRand() {
        return this.CRand;
    }
    
    public void QuestInfoPacket(final MaplePacketLittleEndianWriter mplew) {
        mplew.writeShort(this.questinfo.size());
        for (final Map.Entry<Integer, String> q : this.questinfo.entrySet()) {
            mplew.writeShort(q.getKey());
            mplew.writeMapleAsciiString((q.getValue() == null) ? "" : q.getValue());
        }
    }
    
    public void updateInfoQuest(final int questid, final String data) {
        this.questinfo.put(questid, data);
        this.client.getSession().write(MaplePacketCreator.updateInfoQuest(questid, data));
    }
    
    public String getInfoQuest(final int questid) {
        if (this.questinfo.containsKey(questid)) {
            return this.questinfo.get(questid);
        }
        return "";
    }
    
    public int getNumQuest() {
        int i = 0;
        for (final MapleQuestStatus q : this.quests.values()) {
            if (q.getStatus() == 2 && !q.isCustom()) {
                ++i;
            }
        }
        return i;
    }
    
    public byte getQuestStatus(final int quest) {
        return this.getQuest(MapleQuest.getInstance(quest)).getStatus();
    }
    
    public MapleQuestStatus getQuest(final MapleQuest quest) {
        if (!this.quests.containsKey(quest)) {
            return new MapleQuestStatus(quest, (byte)0);
        }
        return this.quests.get(quest);
    }
    
    public void setQuestAdd(final int quest) {
        this.setQuestAddZ(MapleQuest.getInstance(quest), (byte)2, null);
    }
    
    public void setQuestAddZ(final MapleQuest quest, final byte status, final String customData) {
        final MapleQuestStatus stat = new MapleQuestStatus(quest, status);
        stat.setCustomData(customData);
        this.quests.put(quest, stat);
    }
    
    public void setQuestAdd(final MapleQuest quest, final byte status, final String customData) {
        if (!this.quests.containsKey(quest)) {
            final MapleQuestStatus stat = new MapleQuestStatus(quest, status);
            stat.setCustomData(customData);
            this.quests.put(quest, stat);
        }
    }
    
    public MapleQuestStatus getQuestNAdd(final MapleQuest quest) {
        if (!this.quests.containsKey(quest)) {
            final MapleQuestStatus status = new MapleQuestStatus(quest, (byte)0);
            this.quests.put(quest, status);
            return status;
        }
        return this.quests.get(quest);
    }
    
    public MapleQuestStatus getQuestRemove(final MapleQuest quest) {
        return this.quests.remove(quest);
    }
    
    public MapleQuestStatus getQuestNoAdd(final MapleQuest quest) {
        return this.quests.get(quest);
    }
    
    public void updateQuest(final MapleQuestStatus quest) {
        this.updateQuest(quest, false);
    }
    
    public void updateQuest(final MapleQuestStatus quest, final boolean update) {
        this.quests.put(quest.getQuest(), quest);
        if (!quest.isCustom()) {
            this.client.getSession().write(MaplePacketCreator.updateQuest(quest));
            if (quest.getStatus() == 1 && !update) {
                this.client.getSession().write(MaplePacketCreator.updateQuestInfo(this, quest.getQuest().getId(), quest.getNpc(), (byte)8));
            }
        }
    }
    
    public Map<Integer, String> getInfoQuest_Map() {
        return this.questinfo;
    }
    
    public Map<MapleQuest, MapleQuestStatus> getQuest_Map() {
        return this.quests;
    }
    
    public boolean isActiveBuffedValue(final int skillid) {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skillid) {
                return true;
            }
        }
        return false;
    }
    
    public Integer getBuffedValue(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        return (mbsvh == null) ? null : Integer.valueOf(mbsvh.value);
    }
    
    public Integer getBuffedSkill_X(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        return mbsvh.effect.getX();
    }
    
    public Integer getBuffedSkill_Y(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        return mbsvh.effect.getY();
    }
    
    public boolean isBuffFrom(final MapleBuffStat stat, final ISkill skill) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(stat);
        return mbsvh != null && mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skill.getId();
    }
    
    public int getBuffSource(final MapleBuffStat stat) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(stat);
        return (mbsvh == null) ? -1 : mbsvh.effect.getSourceId();
    }
    
    public int getItemQuantity(final int itemid, final boolean checkEquipped) {
        int possesed = this.inventory[GameConstants.getInventoryType(itemid).ordinal()].countById(itemid);
        if (checkEquipped) {
            possesed += this.inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        return possesed;
    }
    
    public void setBuffedValue(final MapleBuffStat effect, final int value) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        if (mbsvh == null) {
            return;
        }
        mbsvh.value = value;
    }
    
    public Long getBuffedStarttime(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        return (mbsvh == null) ? null : Long.valueOf(mbsvh.startTime);
    }
    
    public MapleStatEffect getStatForBuff(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = this.effects.get(effect);
        return (mbsvh == null) ? null : mbsvh.effect;
    }
    
    private void prepareDragonBlood(final MapleStatEffect bloodEffect) {
        if (this.dragonBloodSchedule != null) {
            this.dragonBloodSchedule.cancel(false);
        }
        this.dragonBloodSchedule = Timer.BuffTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                if (MapleCharacter.this.stats.getHp() - bloodEffect.getX() > 1) {
                    MapleCharacter.this.cancelBuffStats(MapleBuffStat.龙之力);
                }
                else {
                    MapleCharacter.this.addHP(-bloodEffect.getX());
                    MapleCharacter.this.client.getSession().write(MaplePacketCreator.showOwnBuffEffect(bloodEffect.getSourceId(), 5));
                    MapleCharacter.this.map.broadcastMessage(MapleCharacter.this, MaplePacketCreator.showBuffeffect(MapleCharacter.this.getId(), bloodEffect.getSourceId(), 5), false);
                }
            }
        }, 4000L, 4000L);
    }
    
    public void startMapTimeLimitTask(int time, final MapleMap to) {
        this.client.getSession().write(MaplePacketCreator.getClock(time));
        time *= 1000;
        this.mapTimeLimitTask = Timer.MapTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                MapleCharacter.this.changeMap(to, to.getPortal(0));
            }
        }, time, time);
    }
    
    public void startFishingTask(final boolean VIP) {
        final int time = 5000;
        this.cancelFishingTask();
        this.fishing = Timer.EtcTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                final boolean expMulti = MapleCharacter.this.haveItem(2300001, 1, false, true);
                if (!expMulti && !MapleCharacter.this.haveItem(2300000, 1, false, true)) {
                    MapleCharacter.this.cancelFishingTask();
                    return;
                }
                MapleInventoryManipulator.removeById(MapleCharacter.this.client, MapleInventoryType.USE, expMulti ? 2300001 : 2300000, 1, false, false);
                final int randval = RandomRewards.getInstance().getFishingReward();
                final int tmp = Randomizer.nextInt(10000);
                final int tmp2 = Randomizer.nextInt(10000);
                if (tmp2 == 9998) {
                    if (MapleItemInformationProvider.getInstance().itemExists(2101070)) {
                        if (MapleCharacter.this.getInventory(GameConstants.getInventoryType(2101070)).getNextFreeSlot() > -1) {
                            MapleInventoryManipulator.addById(MapleCharacter.this.client, 2101070, (short)1, (byte)0);
                            MapleCharacter.this.client.getSession().write( UIPacket.fishingUpdate((byte)0, 2101070));
                            MapleCharacter.this.getClient().getSession().write(UIPacket.getTopMsg("钓鱼获得:[" + MapleItemInformationProvider.getInstance().getName(2101070) + "]!"));
                            MapleCharacter.this.dropMessage(6, "钓鱼获得:[" + MapleItemInformationProvider.getInstance().getName(2101070) + "]!");
                            final String msg = MapleCharacter.this.getName() + "钓到了传说中的大金鱼，据说其肚内藏有无尽的宝物！";
                            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, MapleCharacter.this.getClient().getChannel(), "[钓鱼公告] : " + msg).getBytes());
                        }
                        else {
                            MapleCharacter.this.dropMessage(5, "背包满了!停止钓鱼!");
                            MapleCharacter.this.dropTopMsg("背包满了!停止钓鱼!");
                            MapleCharacter.this.cancelFishingTask();
                        }
                    }
                }
                else if (tmp == 9998) {
                    MapleCharacter.this.dropMessage(5, "什么都没发生");
                    MapleCharacter.this.dropTopMsg("什么都没发生");
                }
                else if (tmp % 3 == 0) {
                    if (MapleItemInformationProvider.getInstance().itemExists(randval)) {
                        if (MapleCharacter.this.getInventory(GameConstants.getInventoryType(randval)).getNextFreeSlot() > -1) {
                            MapleInventoryManipulator.addById(MapleCharacter.this.client, randval, (short)1, (byte)0);
                            MapleCharacter.this.client.getSession().write(UIPacket.fishingUpdate((byte)0, randval));
                            MapleCharacter.this.getClient().getSession().write(UIPacket.getTopMsg("钓鱼获得:[" + MapleItemInformationProvider.getInstance().getName(randval) + "]!"));
                            MapleCharacter.this.dropMessage(6, "钓鱼获得:[" + MapleItemInformationProvider.getInstance().getName(randval) + "]!");
                        }
                        else {
                            MapleCharacter.this.dropMessage(5, "背包满了!停止钓鱼!");
                            MapleCharacter.this.dropTopMsg("背包满了!停止钓鱼!");
                            MapleCharacter.this.cancelFishingTask();
                        }
                    }
                }
                else {
                    MapleCharacter.this.dropMessage(5, "运气背，什么都没钓到");
                }
                MapleCharacter.this.map.broadcastMessage(UIPacket.fishingCaught(MapleCharacter.this.id));
            }
        }, time, time);
    }
    
    public void dropTopMsg(final String message) {
        this.client.getSession().write(UIPacket.getTopMsg(message));
    }
    
    public void cancelMapTimeLimitTask() {
        if (this.mapTimeLimitTask != null) {
            this.mapTimeLimitTask.cancel(false);
            this.mapTimeLimitTask = null;
        }
    }
    
    public void cancelFishingTask() {
        if (this.fishing != null) {
            this.fishing.cancel(false);
        }
    }
    
    public void registerEffect(final MapleStatEffect effect, final long starttime, final ScheduledFuture<?> schedule) {
        this.registerEffect(effect, starttime, schedule, effect.getStatups());
    }
    
    public void registerEffect(final MapleStatEffect effect, final long starttime, final ScheduledFuture<?> schedule,  List<Pair<MapleBuffStat, Integer>> statups) {
        if (effect.is隐藏术()) {
            this.hidden = true;
            this.map.broadcastMessage(this, MaplePacketCreator.removePlayerFromMap(this.getId(), this), false);
        }
        else if (effect.isDragonBlood()) {
            this.prepareDragonBlood(effect);
        }
        else if (effect.isBerserk()) {
            this.checkBerserk();
        }
        else if (effect.isMonsterRiding_()) {
            this.getMount().startSchedule();
        }
        else if (effect.is灵魂助力()) {
            this.prepareBeholderEffect();
        }
        else if (effect.getSourceId() == 1001 || effect.getSourceId() == 10001001 || effect.getSourceId() == 1001) {
            this.prepareRecovery();
        }
        int clonez = 0;
        for (final Pair<MapleBuffStat, Integer> statup : statups) {
            if (statup.getLeft() == MapleBuffStat.ILLUSION) {
                clonez = statup.getRight();
            }
            final int value = statup.getRight();
            if (statup.getLeft() == MapleBuffStat.骑兽技能 && effect.getSourceId() == 5221006 && this.battleshipHP <= 0) {
                this.battleshipHP = value;
            }
            this.effects.put(statup.getLeft(), new MapleBuffStatValueHolder(effect, starttime, schedule, value));
        }
        if (clonez > 0) {
            final int cloneSize = Math.max(this.getNumClones(), this.getCloneSize());
            if (clonez > cloneSize) {
                for (int i = 0; i < clonez - cloneSize; ++i) {
                    this.cloneLook();
                }
            }
        }
        this.stats.recalcLocalStats();
    }
    
    public List<MapleBuffStat> getBuffStats(final MapleStatEffect effect, final long startTime) {
        final List<MapleBuffStat> bstats = new ArrayList<MapleBuffStat>();
        final Map<MapleBuffStat, MapleBuffStatValueHolder> allBuffs = new EnumMap<MapleBuffStat, MapleBuffStatValueHolder>(this.effects);
        for (final Map.Entry<MapleBuffStat, MapleBuffStatValueHolder> stateffect : allBuffs.entrySet()) {
            final MapleBuffStatValueHolder mbsvh = stateffect.getValue();
            if (mbsvh.effect.sameSource(effect) && (startTime == -1L || startTime == mbsvh.startTime)) {
                bstats.add(stateffect.getKey());
            }
        }
        return bstats;
    }
    
    private boolean deregisterBuffStats(final List<MapleBuffStat> stats) {
        boolean clonez = false;
        final List<MapleBuffStatValueHolder> effectsToCancel = new ArrayList<MapleBuffStatValueHolder>(stats.size());
        for (final MapleBuffStat stat : stats) {
            final MapleBuffStatValueHolder mbsvh = this.effects.remove(stat);
            if (mbsvh != null) {
                boolean addMbsvh = true;
                for (final MapleBuffStatValueHolder contained : effectsToCancel) {
                    if (mbsvh.startTime == contained.startTime && contained.effect == mbsvh.effect) {
                        addMbsvh = false;
                    }
                }
                if (addMbsvh) {
                    effectsToCancel.add(mbsvh);
                }
                if (stat == MapleBuffStat.召唤兽 || stat == MapleBuffStat.替身术 || stat == MapleBuffStat.灵魂助力 || stat == MapleBuffStat.REAPER || stat == MapleBuffStat.RAINING_MINES) {
                    final int summonId = mbsvh.effect.getSourceId();
                    final List<MapleSummon> toRemove = new ArrayList<MapleSummon>();
                    this.visibleMapObjectsLock.writeLock().lock();
                    this.summonsLock.writeLock().lock();
                    try {
                        for (final MapleSummon summon : this.summons) {
                            if (summon.getSkill() == summonId || (stat == MapleBuffStat.RAINING_MINES && summonId == 33101008) || (summonId == 35121009 && summon.getSkill() == 35121011) || ((summonId == 86 || summonId == 88 || summonId == 91 || summonId == 180 || summonId == 96) && summon.getSkill() == summonId + 999) || ((summonId == 1085 || summonId == 1087 || summonId == 1090 || summonId == 1179 || summonId == 1154) && summon.getSkill() == summonId - 999)) {
                                this.map.broadcastMessage(MaplePacketCreator.removeSummon(summon, true));
                                this.map.removeMapObject(summon);
                                this.visibleMapObjects.remove(summon);
                                toRemove.add(summon);
                            }
                            if (summon.getSkill() == 1321007) {
                                if (this.beholderHealingSchedule != null) {
                                    this.beholderHealingSchedule.cancel(false);
                                    this.beholderHealingSchedule = null;
                                }
                                if (this.beholderBuffSchedule == null) {
                                    continue;
                                }
                                this.beholderBuffSchedule.cancel(false);
                                this.beholderBuffSchedule = null;
                            }
                        }
                        for (final MapleSummon s : toRemove) {
                            this.summons.remove(s);
                        }
                    }
                    finally {
                        this.summonsLock.writeLock().unlock();
                        this.visibleMapObjectsLock.writeLock().unlock();
                    }
                }
                else if (stat == MapleBuffStat.龙之力) {
                    if (this.dragonBloodSchedule == null) {
                        continue;
                    }
                    this.dragonBloodSchedule.cancel(false);
                    this.dragonBloodSchedule = null;
                }
                else if (stat == MapleBuffStat.神圣祈祷) {
                    this.cancelBuffStats(MapleBuffStat.神圣祈祷);
                }
                else if (stat == MapleBuffStat.灵魂助力) {
                    this.cancelBuffStats(MapleBuffStat.灵魂助力);
                }
                else {
                    if (stat != MapleBuffStat.ILLUSION) {
                        continue;
                    }
                    this.disposeClones();
                    clonez = true;
                }
            }
        }
        for (final MapleBuffStatValueHolder cancelEffectCancelTasks : effectsToCancel) {
            if (this.getBuffStats(cancelEffectCancelTasks.effect, cancelEffectCancelTasks.startTime).isEmpty() && cancelEffectCancelTasks.schedule != null) {
                cancelEffectCancelTasks.schedule.cancel(false);
            }
        }
        return clonez;
    }
    
    public void cancelEffect(final MapleStatEffect effect, final boolean overwrite, final long startTime) {
        if (effect == null) {
            return;
        }
        this.cancelEffect(effect, overwrite, startTime, effect.getStatups());
    }
    
    public void cancelEffect(final MapleStatEffect effect, final boolean overwrite, final long startTime, final List<Pair<MapleBuffStat, Integer>> statups) {
        if (effect == null) {
            return;
        }
        List<MapleBuffStat> buffstats;
        if (!overwrite) {
            buffstats = this.getBuffStats(effect, startTime);
        }
        else {
            buffstats = new ArrayList<MapleBuffStat>(statups.size());
            for (final Pair<MapleBuffStat, Integer> statup : statups) {
                buffstats.add(statup.getLeft());
            }
        }
        if (buffstats.size() <= 0) {
            return;
        }
        final boolean clonez = this.deregisterBuffStats(buffstats);
        if (effect.is时空门()) {
            if (!this.getDoors().isEmpty()) {
                final MapleDoor door = this.getDoors().iterator().next();
                for (final MapleCharacter chr : door.getTarget().getCharacters()) {
                    door.sendDestroyData(chr.client);
                }
                for (final MapleCharacter chr : door.getTown().getCharacters()) {
                    door.sendDestroyData(chr.client);
                }
                for (final MapleDoor destroyDoor : this.getDoors()) {
                    door.getTarget().removeMapObject(destroyDoor);
                    door.getTown().removeMapObject(destroyDoor);
                }
                this.removeDoor();
                this.silentPartyUpdate();
            }
        }
        else if (effect.isMonsterRiding_()) {
            this.getMount().cancelSchedule();
        }
        else if (effect.isMonsterRiding()) {
            this.cancelEffectFromBuffStat(MapleBuffStat.金属机甲);
        }
        else if (effect.isMonsterS()) {
            this.getMount().cancelSchedule();
        }
        else if (effect.is神圣祈祷()) {
            this.cancelBuffStats(MapleBuffStat.神圣祈祷);
        }
        else if (effect.isAranCombo()) {
            this.aranCombo = 0;
        }
        if (!overwrite) {
            if (effect.isMonsterS()) {
                this.cancelPlayerBuffs(buffstats, effect);
            }
            else {
                this.cancelPlayerBuffs(buffstats);
            }
            if (effect.is隐藏术() && this.client.getChannelServer().getPlayerStorage().getCharacterById(this.getId()) != null) {
                this.hidden = false;
                this.map.broadcastMessage(this, MaplePacketCreator.spawnPlayerMapobject(this), false);
                for (final MaplePet pet : this.pets) {
                    if (pet.getSummoned()) {
                        this.map.broadcastMessage(this, PetPacket.showPet(this, pet, false, false), false);
                    }
                }
                final WeakReference<MapleCharacter>[] clones = this.clones;
                final int length = clones.length;
                final int n = 0;
                if (n < length) {
                    final WeakReference<MapleCharacter> chr2 = clones[n];
                    if (chr2.get() != null) {
                        this.map.broadcastMessage(chr2.get(), MaplePacketCreator.spawnPlayerMapobject(chr2.get()), false);
                    }
                }
            }
        }
        if (!clonez) {
            final WeakReference<MapleCharacter>[] clones2 = this.clones;
            final int length2 = clones2.length;
            final int n2 = 0;
            if (n2 < length2) {
                final WeakReference<MapleCharacter> chr2 = clones2[n2];
                if (chr2.get() != null) {
                    chr2.get().cancelEffect(effect, overwrite, startTime);
                }
            }
        }
    }
    
    public void cancelBuffStats(final MapleBuffStat... stat) {
        final List<MapleBuffStat> buffStatList = Arrays.asList(stat);
        this.deregisterBuffStats(buffStatList);
        this.cancelPlayerBuffs(buffStatList);
    }
    
    public void cancelEffectFromBuffStat(final MapleBuffStat stat) {
        if (this.effects.get(stat) != null) {
            this.cancelEffect(this.effects.get(stat).effect, false, -1L);
        }
    }
    
    private void cancelPlayerBuffs(final List<MapleBuffStat> buffstats) {
        final boolean write = this.client.getChannelServer().getPlayerStorage().getCharacterById(this.getId()) != null;
        if (buffstats.contains(MapleBuffStat.导航辅助)) {
            if (write) {
                this.client.getSession().write(MaplePacketCreator.cancelHoming());
            }
        }
        else if (buffstats.contains(MapleBuffStat.骑兽技能)) {
            this.client.getSession().write(MaplePacketCreator.cancelBuffMONSTER(buffstats));
            this.map.broadcastMessage(this, MaplePacketCreator.cancelForeignBuffMONSTER(this.getId(), buffstats), false);
        }
        else {
            this.client.getSession().write(MaplePacketCreator.cancelBuff(buffstats));
            this.map.broadcastMessage(this, MaplePacketCreator.cancelForeignBuff(this.getId(), buffstats), false);
        }
    }
    
    private void cancelPlayerBuffs(final List<MapleBuffStat> buffstats, final MapleStatEffect effect) {
        if (effect.isMonsterS()) {
            this.client.getSession().write(MaplePacketCreator.cancelBuffMONSTERS(buffstats));
            this.map.broadcastMessage(this, MaplePacketCreator.cancelForeignBuffMONSTERS(this.getId(), buffstats), false);
        }
    }
    
    public void dispel() {
        if (!this.isHidden()) {
            final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
            for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
                if (mbsvh.effect.isSkill() && mbsvh.schedule != null && !mbsvh.effect.isMorph()) {
                    this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                }
            }
        }
    }
    
    public void dispelSkill(final int skillid) {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (skillid == 0) {
                if (mbsvh.effect.isSkill() && (mbsvh.effect.getSourceId() == 4331003 || mbsvh.effect.getSourceId() == 4331002 || mbsvh.effect.getSourceId() == 4341002 || mbsvh.effect.getSourceId() == 22131001 || mbsvh.effect.getSourceId() == 1321007 || mbsvh.effect.getSourceId() == 2121005 || mbsvh.effect.getSourceId() == 2221005 || mbsvh.effect.getSourceId() == 2311006 || mbsvh.effect.getSourceId() == 2321003 || mbsvh.effect.getSourceId() == 3111002 || mbsvh.effect.getSourceId() == 3111005 || mbsvh.effect.getSourceId() == 3211002 || mbsvh.effect.getSourceId() == 3211005 || mbsvh.effect.getSourceId() == 4111002)) {
                    this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                    break;
                }
                continue;
            }
            else {
                if (mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skillid) {
                    this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                    break;
                }
                continue;
            }
        }
    }
    
    public void dispelBuff(final int skillid) {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.getSourceId() == skillid) {
                this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }
    
    public void cancelAllBuffs_() {
        this.effects.clear();
    }
    
    public void cancelAllBuffs() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
        }
    }
    
    public void cancelMorphs() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            switch (mbsvh.effect.getSourceId()) {
                case 5111005:
                case 5121003:
                case 13111005:
                case 15111002: {}
                default: {
                    if (!mbsvh.effect.isMorph()) {
                        continue;
                    }
                    this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                    continue;
                }
            }
        }
    }
    
    public int getMorphState() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMorph()) {
                return mbsvh.effect.getSourceId();
            }
        }
        return -1;
    }
    
    public void silentGiveBuffs(final List<PlayerBuffValueHolder> buffs) {
        if (buffs == null) {
            return;
        }
        for (final PlayerBuffValueHolder mbsvh : buffs) {
            mbsvh.effect.silentApplyBuff(this, mbsvh.startTime);
        }
    }
    
    public List<PlayerBuffValueHolder> getAllBuffs() {
        final List<PlayerBuffValueHolder> ret = new ArrayList<PlayerBuffValueHolder>();
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            ret.add(new PlayerBuffValueHolder(mbsvh.startTime, mbsvh.effect));
        }
        return ret;
    }
    
    public void cancelMagicDoor() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(this.effects.values());
        for (final MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.is时空门()) {
                this.cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }
    
    public int getSkillLevel(final int skillid) {
        return this.getSkillLevel(SkillFactory.getSkill(skillid));
    }
    
    public final void handleEnergyCharge(final int skillid, final int targets) {
        final ISkill echskill = SkillFactory.getSkill(skillid);
        final byte skilllevel = this.getSkillLevel(echskill);
        if (skilllevel > 0) {
            final MapleStatEffect echeff = echskill.getEffect(skilllevel);
            if (targets > 0) {
                if (this.getBuffedValue(MapleBuffStat.能量获得) == null) {
                    echeff.applyEnergyBuff(this, true);
                }
                else {
                    Integer energyLevel = this.getBuffedValue(MapleBuffStat.能量获得);
                    if (energyLevel <= 10000) {
                        energyLevel += echeff.getX() * targets;
                        this.client.getSession().write(MaplePacketCreator.showOwnBuffEffect(skillid, 2));
                        this.map.broadcastMessage(this, MaplePacketCreator.showBuffeffect(this.id, skillid, 2), false);
                        if (energyLevel >= 10000) {
                            energyLevel = 10000;
                            if (this.cancelEnergy) {
                                Timer.BuffTimer.getInstance().schedule(this.cancelEnergyRunnable, 100000L);
                                this.cancelEnergy = false;
                            }
                        }
                        final List<Pair<MapleBuffStat, Integer>> stat = Collections.singletonList(new Pair<MapleBuffStat, Integer>(MapleBuffStat.能量获得, energyLevel));
                        this.client.getSession().write(MaplePacketCreator.能量条(stat, energyLevel / 1000));
                        this.setBuffedValue(MapleBuffStat.能量获得, energyLevel);
                    }
                }
            }
        }
    }
    
    public void handleBattleshipHP(final int damage) {
        if (this.isActiveBuffedValue(5221006)) {
            this.battleshipHP -= damage;
            if (this.battleshipHP <= 0) {
                this.battleshipHP = 0;
                final MapleStatEffect effect = this.getStatForBuff(MapleBuffStat.骑兽技能);
                this.client.getSession().write(MaplePacketCreator.skillCooldown(5221006, effect.getCooldown()));
                this.addCooldown(5221006, System.currentTimeMillis(), effect.getCooldown() * 1000);
                this.dispelSkill(5221006);
            }
        }
    }
    
    public void handleOrbgain() {
        if (this.getBuffedValue(MapleBuffStat.斗气集中) == null) {
            return;
        }
        final int orbcount = this.getBuffedValue(MapleBuffStat.斗气集中);
        ISkill combo = null;
        ISkill advcombo = null;
        switch (this.getJob()) {
            case 1110:
            case 1111:
            case 1112: {
                combo = SkillFactory.getSkill(11111001);
                advcombo = SkillFactory.getSkill(11110005);
                break;
            }
            default: {
                combo = SkillFactory.getSkill(1111002);
                advcombo = SkillFactory.getSkill(1120003);
                break;
            }
        }
        MapleStatEffect ceffect = null;
        final int advComboSkillLevel = this.getSkillLevel(advcombo);
        if (advComboSkillLevel > 0) {
            ceffect = advcombo.getEffect(advComboSkillLevel);
        }
        else {
            if (this.getSkillLevel(combo) <= 0) {
                return;
            }
            ceffect = combo.getEffect(this.getSkillLevel(combo));
        }
        if (orbcount < ceffect.getX() + 1) {
            int neworbcount = orbcount + 1;
            if (advComboSkillLevel > 0 && ceffect.makeChanceResult() && neworbcount < ceffect.getX() + 1) {
                ++neworbcount;
            }
            final List<Pair<MapleBuffStat, Integer>> stat = Collections.singletonList(new Pair<MapleBuffStat, Integer>(MapleBuffStat.斗气集中, neworbcount));
            this.setBuffedValue(MapleBuffStat.斗气集中, neworbcount);
            int duration = ceffect.getDuration();
            duration += (int)(this.getBuffedStarttime(MapleBuffStat.斗气集中) - System.currentTimeMillis());
            this.client.getSession().write(MaplePacketCreator.giveBuff(combo.getId(), duration, stat, ceffect));
            this.map.broadcastMessage(this, MaplePacketCreator.giveForeignBuff(this, this.getId(), stat, ceffect), false);
        }
    }
    
    public void handleOrbconsume() {
        ISkill combo = null;
        switch (this.getJob()) {
            case 1110:
            case 1111: {
                combo = SkillFactory.getSkill(11111001);
                break;
            }
            default: {
                combo = SkillFactory.getSkill(1111002);
                break;
            }
        }
        if (this.getSkillLevel(combo) <= 0) {
            return;
        }
        final MapleStatEffect ceffect = this.getStatForBuff(MapleBuffStat.斗气集中);
        if (ceffect == null) {
            return;
        }
        final List<Pair<MapleBuffStat, Integer>> stat = Collections.singletonList(new Pair<MapleBuffStat, Integer>(MapleBuffStat.斗气集中, 1));
        this.setBuffedValue(MapleBuffStat.斗气集中, 1);
        int duration = ceffect.getDuration();
        duration += (int)(this.getBuffedStarttime(MapleBuffStat.斗气集中) - System.currentTimeMillis());
        this.client.getSession().write(MaplePacketCreator.giveBuff(combo.getId(), duration, stat, ceffect));
        this.map.broadcastMessage(this, MaplePacketCreator.giveForeignBuff(this, this.getId(), stat, ceffect), false);
    }
    
    public void silentEnforceMaxHpMp() {
        this.stats.setMp(this.stats.getMp());
        this.stats.setHp(this.stats.getHp(), true);
    }
    
    public void enforceMaxHpMp() {
        final List<Pair<MapleStat, Integer>> statups = new ArrayList<Pair<MapleStat, Integer>>(2);
        if (this.stats.getMp() > this.stats.getCurrentMaxMp()) {
            this.stats.setMp(this.stats.getMp());
            statups.add(new Pair<MapleStat, Integer>(MapleStat.MP, (int)this.stats.getMp()));
        }
        if (this.stats.getHp() > this.stats.getCurrentMaxHp()) {
            this.stats.setHp(this.stats.getHp());
            statups.add(new Pair<MapleStat, Integer>(MapleStat.HP, (int)this.stats.getHp()));
        }
        if (statups.size() > 0) {
            this.client.getSession().write( MaplePacketCreator.updatePlayerStats(statups, this.getJob()));
        }
    }
    
    public MapleMap getMap() {
        return this.map;
    }
    
    public MonsterBook getMonsterBook() {
        return this.monsterbook;
    }
    
    public void setMap(final MapleMap newmap) {
        this.map = newmap;
    }
    
    public void setMap(final int PmapId) {
        this.mapid = PmapId;
    }
    
    public int getMapId() {
        if (this.map != null) {
            return this.map.getId();
        }
        return this.mapid;
    }
    
    public byte getInitialSpawnpoint() {
        return this.initialSpawnPoint;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getBlessOfFairyOrigin() {
        return this.BlessOfFairy_Origin;
    }
    
    public short getLevel() {
        return this.level;
    }
    
    public short getFame() {
        return this.fame;
    }
    
    public int getDojo() {
        return this.dojo;
    }
    
    public int getDojoRecord() {
        return this.dojoRecord;
    }
    
    public int getFallCounter() {
        return this.fallcounter;
    }
    
    public MapleClient getClient() {
        return this.client;
    }
    
    public void setClient(final MapleClient client) {
        this.client = client;
    }
    
    public int getExp() {
        return this.exp;
    }
    
    public short getRemainingAp() {
        return this.remainingAp;
    }
    
    public int getRemainingSp() {
        return this.remainingSp[GameConstants.getSkillBook(this.job)];
    }
    
    public int getRemainingSp(final int skillbook) {
        return this.remainingSp[skillbook];
    }
    
    public int[] getRemainingSps() {
        return this.remainingSp;
    }
    
    public int getRemainingSpSize() {
        int ret = 0;
        for (int i = 0; i < this.remainingSp.length; ++i) {
            if (this.remainingSp[i] > 0) {
                ++ret;
            }
        }
        return ret;
    }
    
    public short getHpApUsed() {
        return this.hpApUsed;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHpApUsed(final short hpApUsed) {
        this.hpApUsed = hpApUsed;
    }
    
    public byte getSkinColor() {
        return this.skinColor;
    }
    
    public void setSkinColor(final byte skinColor) {
        this.skinColor = skinColor;
    }
    
    public short getJob() {
        return this.job;
    }
    
    public byte getGender() {
        return this.gender;
    }
    
    public int getHair() {
        return this.hair;
    }
    
    public int getFace() {
        return this.face;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setExp(final int exp) {
        this.exp = exp;
    }
    
    public void setHair(final int hair) {
        this.hair = hair;
    }
    
    public void setFace(final int face) {
        this.face = face;
    }
    
    public void setFame(final short fame) {
        this.fame = fame;
    }
    
    public void setDojo(final int dojo) {
        this.dojo = dojo;
    }
    
    public void setDojoRecord(final boolean reset) {
        if (reset) {
            this.dojo = 0;
            this.dojoRecord = 0;
        }
        else {
            ++this.dojoRecord;
        }
    }
    
    public void setFallCounter(final int fallcounter) {
        this.fallcounter = fallcounter;
    }
    
    public Point getOldPosition() {
        return this.old;
    }
    
    public void setOldPosition(final Point x) {
        this.old = x;
    }
    
    public void setRemainingAp(final short remainingAp) {
        this.remainingAp = remainingAp;
    }
    
    public void setRemainingSp(final int remainingSp) {
        this.remainingSp[GameConstants.getSkillBook(this.job)] = remainingSp;
    }
    
    public void setRemainingSp(final int remainingSp, final int skillbook) {
        this.remainingSp[skillbook] = remainingSp;
    }
    
    public void setGender(final byte gender) {
        this.gender = gender;
    }
    
    public void setInvincible(final boolean invinc) {
        this.invincible = invinc;
    }
    
    public boolean isInvincible() {
        return this.invincible;
    }
    
    public CheatTracker getCheatTracker() {
        return this.anticheat;
    }
    
    public BuddyList getBuddylist() {
        return this.buddylist;
    }
    
    public void addFame(final int famechange) {
        this.fame += (short)famechange;
    }
    
    public void changeMapBanish(final int mapid, final String portal, final String msg) {
        this.dropMessage(5, msg);
        final MapleMap map = this.client.getChannelServer().getMapFactory().getMap(mapid);
        if (map != null) {
            this.changeMap(map, map.getPortal(portal));
        }
    }
    
    public void changeMap(final int to) {
        final MapleMap map = ChannelServer.getInstance(this.getClient().getChannel()).getMapFactory().getMap(to);
        this.changeMapInternal(map, map.getPortal(0).getPosition(), MaplePacketCreator.getWarpToMap(map, 0, this), map.getPortal(0));
    }
    
    public void changeMap(final int map, final int portal) {
        final MapleMap warpMap = this.client.getChannelServer().getMapFactory().getMap(map);
        this.changeMap(warpMap, warpMap.getPortal(portal));
    }
    
    public void changeMap(final MapleMap to, final Point pos) {
        this.changeMapInternal(to, pos, MaplePacketCreator.getWarpToMap(to, 128, this), null);
    }
    
    public void changeMap(final MapleMap to, final MaplePortal pto) {
        this.changeMapInternal(to, pto.getPosition(), MaplePacketCreator.getWarpToMap(to, pto.getId(), this), null);
    }
    
    public void changeMapPortal(final MapleMap to, final MaplePortal pto) {
        this.changeMapInternal(to, pto.getPosition(), MaplePacketCreator.getWarpToMap(to, pto.getId(), this), pto);
    }
    
    public void changeMapInternal(final MapleMap to, final Point pos, final MaplePacket warpPacket, final MaplePortal pto) {
        if (to == null) {
            return;
        }
        this.saveToDB(false, false);
        final int nowmapid = this.map.getId();
        if (this.eventInstance != null) {
            this.eventInstance.changedMap(this, to.getId());
        }
        final boolean pyramid = this.pyramidSubway != null;
        if (this.map.getId() == nowmapid) {
            this.client.getSession().write(warpPacket);
            this.map.removePlayer(this);
            if (!this.isClone() && this.client.getChannelServer().getPlayerStorage().getCharacterById(this.getId()) != null) {
                this.map = to;
                this.setPosition(pos);
                this.setStance(0);
                this.setPosition(new Point(pos.x, pos.y - 50));
                to.addPlayer(this);
                this.stats.relocHeal();
            }
        }
        if (this.party != null) {
            this.silentPartyUpdate();
            this.getClient().getSession().write(MaplePacketCreator.updateParty(this.getClient().getChannel(), this.party, PartyOperation.SILENT_UPDATE, null));
            this.updatePartyMemberHP();
        }
        if (pyramid && this.pyramidSubway != null) {
            this.pyramidSubway.onChangeMap(this, to.getId());
        }
    }
    
    public void leaveMap(final MapleMap map) {
        this.controlledLock.writeLock().lock();
        this.visibleMapObjectsLock.writeLock().lock();
        try {
            for (final MapleMonster mons : this.controlled) {
                if (mons != null) {
                    mons.setController(null);
                    mons.setControllerHasAggro(false);
                    map.updateMonsterController(mons);
                }
            }
            this.controlled.clear();
            this.visibleMapObjects.clear();
        }
        finally {
            this.controlledLock.writeLock().unlock();
            this.visibleMapObjectsLock.writeLock().unlock();
        }
        if (this.chair != 0) {
            this.chair = 0;
        }
        this.cancelFishingTask();
        this.cancelMapTimeLimitTask();
        if (this.getTrade() != null) {
            MapleTrade.cancelTrade(this.getTrade(), this.client);
        }
    }
    
    public void changeJob(final int newJob) {
        try {
            final boolean isEv = GameConstants.isEvan(this.job) || GameConstants.isResist(this.job);
            this.job = (short)newJob;
            if (newJob != 0 && newJob != 1000 && newJob != 2000 && newJob != 2001 && newJob != 3000) {
                if (isEv) {
                    final int[] remainingSp = this.remainingSp;
                    final int skillBook = GameConstants.getSkillBook(newJob);
                    remainingSp[skillBook] += 5;
                    this.client.getSession().write(UIPacket.getSPMsg((byte)5, (short)newJob));
                }
                else {
                    final int[] remainingSp2 = this.remainingSp;
                    final int skillBook2 = GameConstants.getSkillBook(newJob);
                    ++remainingSp2[skillBook2];
                    if (newJob % 10 >= 2) {
                        final int[] remainingSp3 = this.remainingSp;
                        final int skillBook3 = GameConstants.getSkillBook(newJob);
                        remainingSp3[skillBook3] += 2;
                    }
                }
            }
            if (newJob > 0 && !this.isGM()) {
                this.resetStatsByJob(true);
                if (!GameConstants.isEvan(newJob)) {
                    if (this.getLevel() > ((newJob == 200) ? 8 : 10) && newJob % 100 == 0 && newJob % 1000 / 100 > 0) {
                        final int[] remainingSp4 = this.remainingSp;
                        final int skillBook4 = GameConstants.getSkillBook(newJob);
                        remainingSp4[skillBook4] += 3 * (this.getLevel() - ((newJob == 200) ? 8 : 10));
                    }
                }
                else if (newJob == 2200) {
                    MapleQuest.getInstance(22100).forceStart(this, 0, null);
                    MapleQuest.getInstance(22100).forceComplete(this, 0);
                    this.expandInventory((byte)1, 4);
                    this.expandInventory((byte)2, 4);
                    this.expandInventory((byte)3, 4);
                    this.expandInventory((byte)4, 4);
                    this.client.getSession().write(MaplePacketCreator.getEvanTutorial("UI/tutorial/evan/14/0"));
                    this.dropMessage(5, "The baby Dragon hatched and appears to have something to tell you. Click the baby Dragon to start a conversation.");
                }
            }
            this.client.getSession().write(MaplePacketCreator.updateSp(this, false, isEv));
            this.updateSingleStat(MapleStat.JOB, newJob);
            int maxhp = this.stats.getMaxHp();
            int maxmp = this.stats.getMaxMp();
            switch (this.job) {
                case 100:
                case 1100:
                case 2100:
                case 3200: {
                    maxhp += Randomizer.rand(200, 250);
                    break;
                }
                case 200:
                case 2200:
                case 2210: {
                    maxmp += Randomizer.rand(100, 150);
                    break;
                }
                case 300:
                case 400:
                case 500:
                case 3300:
                case 3500: {
                    maxhp += Randomizer.rand(100, 150);
                    maxmp += Randomizer.rand(25, 50);
                    break;
                }
                case 110: {
                    maxhp += Randomizer.rand(300, 350);
                    break;
                }
                case 120:
                case 130:
                case 510:
                case 512:
                case 1110:
                case 2110:
                case 3210: {
                    maxhp += Randomizer.rand(300, 350);
                    break;
                }
                case 210:
                case 220:
                case 230: {
                    maxmp += Randomizer.rand(400, 450);
                    break;
                }
                case 310:
                case 312:
                case 320:
                case 322:
                case 410:
                case 412:
                case 420:
                case 422:
                case 430:
                case 520:
                case 522:
                case 1310:
                case 1410:
                case 3310:
                case 3510: {
                    maxhp += Randomizer.rand(300, 350);
                    maxhp += Randomizer.rand(150, 200);
                    break;
                }
                case 800:
                case 900: {
                    maxhp += 30000;
                    maxhp += 30000;
                    break;
                }
            }
            if (maxhp >= 30000) {
                maxhp = 30000;
            }
            if (maxmp >= 30000) {
                maxmp = 30000;
            }
            this.stats.setMaxHp((short)maxhp);
            this.stats.setMaxMp((short)maxmp);
            this.stats.setHp((short)maxhp);
            this.stats.setMp((short)maxmp);
            final List<Pair<MapleStat, Integer>> statup = new ArrayList<Pair<MapleStat, Integer>>(4);
            statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXHP, maxhp));
            statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXMP, maxmp));
            statup.add(new Pair<MapleStat, Integer>(MapleStat.HP, maxhp));
            statup.add(new Pair<MapleStat, Integer>(MapleStat.MP, maxmp));
            this.stats.recalcLocalStats();
            this.client.getSession().write(MaplePacketCreator.updatePlayerStats(statup, this.getJob()));
            this.map.broadcastMessage(this, MaplePacketCreator.showForeignEffect(this.getId(), 8), false);
            this.silentPartyUpdate();
            this.guildUpdate();
            this.familyUpdate();
            this.baseSkills();
        }
        catch (Exception e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e);
        }
    }
    
    public void baseSkills() {
        if (GameConstants.getJobNumber(this.job) >= 3) {
            final List<Integer> skills = SkillFactory.getSkillsByJob(this.job);
            if (skills != null) {
                for (final int i : skills) {
                    final ISkill skil = SkillFactory.getSkill(i);
                    if (skil != null && !skil.isInvisible() && skil.isFourthJob() && this.getSkillLevel(skil) <= 0 && this.getMasterLevel(skil) <= 0 && skil.getMasterLevel() > 0) {
                        this.changeSkillLevel(skil, (byte)0, (byte)skil.getMasterLevel());
                    }
                }
            }
        }
    }
    
    public void gainAp(final short ap) {
        this.remainingAp += ap;
        this.updateSingleStat(MapleStat.AVAILABLEAP, this.remainingAp);
    }
    
    public void gainSP(final int sp) {
        final int[] remainingSp = this.remainingSp;
        final int skillBook = GameConstants.getSkillBook(this.job);
        remainingSp[skillBook] += sp;
        this.client.getSession().write(MaplePacketCreator.updateSp(this, false));
        this.client.getSession().write(UIPacket.getSPMsg((byte)sp, this.job));
    }
    
    public void gainSP(final int sp, final int skillbook) {
        final int[] remainingSp = this.remainingSp;
        remainingSp[skillbook] += sp;
        this.client.getSession().write(MaplePacketCreator.updateSp(this, false));
        this.client.getSession().write(UIPacket.getSPMsg((byte)sp, this.job));
    }
    
    public void resetSP(final int sp) {
        for (int i = 0; i < this.remainingSp.length; ++i) {
            this.remainingSp[i] = sp;
        }
        this.updateSingleStat(MapleStat.AVAILABLESP, this.getRemainingSp());
    }
    
    public void resetAPSP() {
        for (int i = 0; i < this.remainingSp.length; ++i) {
            this.remainingSp[i] = 0;
        }
        this.client.getSession().write(MaplePacketCreator.updateSp(this, false));
        this.gainAp((short)(-this.remainingAp));
    }

    public int getAllSkillLevels() {
        int rett = 0;
        for (Map.Entry<ISkill, SkillEntry> ret : this.skills.entrySet()) {
            if (!((Skill)ret.getKey()).isBeginnerSkill() && ((SkillEntry)ret.getValue()).skillevel > 0)
                rett += ((SkillEntry)ret.getValue()).skillevel;
        }
        return rett;
    }
    
    public void changeSkillLevel(final ISkill skill, final byte newLevel, final byte newMasterlevel) {
        if (skill == null) {
            return;
        }
        this.changeSkillLevel(skill, newLevel, newMasterlevel, skill.isTimeLimited() ? (System.currentTimeMillis() + 2592000000L) : -1L);
    }
    
    public void changeSkillLevel(final ISkill skill, final byte newLevel, final byte newMasterlevel, final long expiration) {
        if (skill == null || (!GameConstants.isApplicableSkill(skill.getId()) && !GameConstants.isApplicableSkill_(skill.getId()))) {
            return;
        }
        this.client.getSession().write(MaplePacketCreator.updateSkill(skill.getId(), newLevel, newMasterlevel, expiration));
        if (newLevel == 0 && newMasterlevel == 0) {
            if (!this.skills.containsKey(skill)) {
                return;
            }
            this.skills.remove(skill);
        }
        else {
            this.skills.put(skill, new SkillEntry(newLevel, newMasterlevel, expiration));
        }
        if (GameConstants.isRecoveryIncSkill(skill.getId())) {
            this.stats.relocHeal();
        }
        else if (GameConstants.isElementAmp_Skill(skill.getId())) {
            this.stats.recalcLocalStats();
        }
    }
    
    public void changeSkillLevel_Skip(final ISkill skill, final byte newLevel, final byte newMasterlevel) {
        if (skill == null) {
            return;
        }
        this.client.getSession().write(MaplePacketCreator.updateSkill(skill.getId(), newLevel, newMasterlevel, -1L));
        if (newLevel == 0 && newMasterlevel == 0) {
            if (this.skills.containsKey(skill)) {
                this.skills.remove(skill);
            }
        }
        else {
            this.skills.put(skill, new SkillEntry(newLevel, newMasterlevel, -1L));
        }
    }
    
    public void playerDead() {
        final MapleStatEffect statss = this.getStatForBuff(MapleBuffStat.灵魂之石);
        if (statss != null) {
            this.dropMessage(5, "你已经被灵魂石复活了。");
            this.getStat().setHp(this.getStat().getMaxHp() / 100 * statss.getX());
            this.updateSingleStat(MapleStat.HP, this.getHp());
            this.setStance(0);
            this.changeMap(this.getMap(), this.getMap().getPortal(0));
            return;
        }
        final int[] charmID = { 5130000, 5130002, 5131000, 4031283, 4140903 };
        int possesed = 0;
        int i;
        for (i = 0; i < charmID.length; ++i) {
            final int quantity = this.getItemQuantity(charmID[i], false);
            if (possesed == 0 && quantity > 0) {
                possesed = quantity;
                break;
            }
        }
        if (possesed > 0) {
            --possesed;
            this.getClient().getSession().write(MaplePacketCreator.serverNotice(5, "因使用了 [护身符] 死亡后您的经验不会减少！剩余 (" + possesed + " 个)"));
            MapleInventoryManipulator.removeById(this.getClient(), MapleItemInformationProvider.getInstance().getInventoryType(charmID[i]), charmID[i], 1, true, false);
        }
        else {
            if (this.getEventInstance() != null) {
                this.getEventInstance().playerKilled(this);
            }
            this.dispelSkill(0);
            this.cancelEffectFromBuffStat(MapleBuffStat.变身);
            this.cancelEffectFromBuffStat(MapleBuffStat.骑兽技能);
            this.cancelEffectFromBuffStat(MapleBuffStat.召唤兽);
            this.cancelEffectFromBuffStat(MapleBuffStat.REAPER);
            this.cancelEffectFromBuffStat(MapleBuffStat.替身术);
            this.checkFollow();
            if (this.job != 0 && this.job != 1000 && this.job != 2000) {
                float diepercentage = 0.0f;
                final int expforlevel = GameConstants.getExpNeededForLevel(this.level);
                if (this.map.isTown() || FieldLimitType.RegularExpLoss.check(this.map.getFieldLimit())) {
                    diepercentage = 0.01f;
                }
                else {
                    float v8 = 0.0f;
                    if (this.job / 100 == 3) {
                        v8 = 0.08f;
                    }
                    else {
                        v8 = 0.2f;
                    }
                    diepercentage = (float)(v8 / this.stats.getLuk() + 0.05);
                }
                int v9 = (int)(this.exp - (long)(expforlevel * (double)diepercentage));
                if (v9 < 0) {
                    v9 = 0;
                }
                this.exp = v9;
            }
            this.updateSingleStat(MapleStat.EXP, this.exp);
            if (!this.stats.checkEquipDurabilitys(this, -100)) {
                this.dropMessage(5, "耐久度已经归零.");
            }
            if (this.pyramidSubway != null) {
                this.stats.setHp(50);
                this.pyramidSubway.fail(this);
            }
        }
    }
    
    public void updatePartyMemberHP() {
        if (this.party != null) {
            final int channel = this.client.getChannel();
            for (final MaplePartyCharacter partychar : this.party.getMembers()) {
                if (partychar.getMapid() == this.getMapId() && partychar.getChannel() == channel) {
                    final MapleCharacter other = ChannelServer.getInstance(channel).getPlayerStorage().getCharacterByName(partychar.getName());
                    if (other == null) {
                        continue;
                    }
                    this.updateSingleStat(MapleStat.HP, this.stats.getHp());
                    other.getClient().getSession().write(MaplePacketCreator.updatePartyMemberHP(this.getId(), this.stats.getHp(), this.stats.getCurrentMaxHp()));
                }
            }
        }
    }
    
    public void receivePartyMemberHP() {
        if (this.party == null) {
            return;
        }
        final int channel = this.client.getChannel();
        for (final MaplePartyCharacter partychar : this.party.getMembers()) {
            if (partychar.getMapid() == this.getMapId() && partychar.getChannel() == channel) {
                final MapleCharacter other = ChannelServer.getInstance(channel).getPlayerStorage().getCharacterByName(partychar.getName());
                if (other == null) {
                    continue;
                }
                this.client.getSession().write(MaplePacketCreator.updatePartyMemberHP(other.getId(), other.getStat().getHp(), other.getStat().getCurrentMaxHp()));
            }
        }
    }
    
    public void healHP(final int delta) {
        this.addHP(delta);
    }
    
    public void healMP(final int delta) {
        this.addMP(delta);
    }
    
    public void addHP(final int delta) {
        if (this.stats.setHp(this.stats.getHp() + delta)) {
            this.updateSingleStat(MapleStat.HP, this.stats.getHp());
        }
    }
    
    public void addMP(final int delta) {
        if (this.stats.setMp(this.stats.getMp() + delta)) {
            this.updateSingleStat(MapleStat.MP, this.stats.getMp());
        }
    }
    
    public void addMPHP(final int hpDiff, final int mpDiff) {
        final List<Pair<MapleStat, Integer>> statups = new ArrayList<Pair<MapleStat, Integer>>();
        if (this.stats.setHp(this.stats.getHp() + hpDiff)) {
            statups.add(new Pair<MapleStat, Integer>(MapleStat.HP, (int)this.stats.getHp()));
        }
        if (this.stats.setMp(this.stats.getMp() + mpDiff)) {
            statups.add(new Pair<MapleStat, Integer>(MapleStat.MP, (int)this.stats.getMp()));
        }
        if (statups.size() > 0) {
            this.client.getSession().write(MaplePacketCreator.updatePlayerStats(statups, this.getJob()));
        }
    }
    
    public boolean canQuestAction() {
        if (this.lastQuestTime + 250L > System.currentTimeMillis()) {
            return false;
        }
        this.lastQuestTime = System.currentTimeMillis();
        return true;
    }
    
    private void prepareRecovery() {
        this.lastRecoveryTime = System.currentTimeMillis();
    }
    
    public boolean canRecovery() {
        return this.lastRecoveryTime > 0L && this.lastRecoveryTime + 5000L < System.currentTimeMillis() + 5000L;
    }
    
    public void doRecovery() {
        final MapleStatEffect eff = this.getStatForBuff(MapleBuffStat.团队治疗);
        if (eff != null) {
            this.prepareRecovery();
            if (this.stats.getHp() > this.stats.getCurrentMaxHp()) {
                this.cancelEffectFromBuffStat(MapleBuffStat.团队治疗);
            }
            else {
                this.healHP(eff.getX());
            }
        }
    }
    
    public boolean canHP() {
        if (this.lastHPTime + 5000L > System.currentTimeMillis()) {
            return false;
        }
        this.lastHPTime = System.currentTimeMillis();
        return true;
    }
    
    public boolean canMP() {
        if (this.lastMPTime + 5000L > System.currentTimeMillis()) {
            return false;
        }
        this.lastMPTime = System.currentTimeMillis();
        return true;
    }
    
    public boolean canCheckPeriod() {
        if (this.lastCheckPeriodTime + 30000L > System.currentTimeMillis()) {
            return false;
        }
        this.lastCheckPeriodTime = System.currentTimeMillis();
        return true;
    }
    
    public boolean canMoveItem() {
        if (this.lastMoveItemTime + 250L > System.currentTimeMillis()) {
            return false;
        }
        this.lastMoveItemTime = System.currentTimeMillis();
        return true;
    }
    
    public void updateSingleStat(final MapleStat stat, final int newval) {
        this.updateSingleStat(stat, newval, false);
    }
    
    public void updateSingleStat(final MapleStat stat, final int newval, final boolean itemReaction) {
        final Pair<MapleStat, Integer> statpair = new Pair<MapleStat, Integer>(stat, newval);
        this.client.getSession().write(MaplePacketCreator.updatePlayerStats(Collections.singletonList(statpair), itemReaction, this.getJob()));
    }
    
    public void gainExp(final int total, final boolean show, final boolean inChat, final boolean white) {
        try {
            final int prevexp = this.getExp();
            int needed = GameConstants.getExpNeededForLevel(this.level);
            if (this.level >= Integer.parseInt(ServerProperties.getProperty("RoyMS.MLevel")) || (GameConstants.isKOC(this.job) && this.level >= Integer.parseInt(ServerProperties.getProperty("RoyMS.QLevel")))) {
                if (this.exp + total > needed) {
                    this.setExp(needed);
                }
                else {
                    this.exp += total;
                }
            }
            else {
                boolean leveled = false;
                if (this.exp + total >= needed) {
                    this.exp += total;
                    this.levelUp();
                    leveled = true;
                    needed = GameConstants.getExpNeededForLevel(this.level);
                    if (this.exp > needed) {
                        this.setExp(needed);
                    }
                }
                else {
                    this.exp += total;
                }
                if (total > 0) {
                    this.familyRep(prevexp, needed, leveled);
                }
            }
            if (total != 0) {
                if (this.exp < 0) {
                    if (total > 0) {
                        this.setExp(needed);
                    }
                    else if (total < 0) {
                        this.setExp(0);
                    }
                }
                if (show) {
                    this.client.getSession().write(MaplePacketCreator.GainEXP_Others(total, inChat, white));
                }
                this.updateSingleStat(MapleStat.EXP, this.getExp());
            }
        }
        catch (Exception e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e);
        }
    }
    
    public void familyRep(final int prevexp, final int needed, final boolean leveled) {
        if (this.mfc != null) {
            final int onepercent = needed / 100;
            int percentrep = prevexp / onepercent + this.getExp() / onepercent;
            if (leveled) {
                percentrep = 100 - percentrep + this.level / 2;
            }
            if (percentrep > 0) {
                final int sensen = World.Family.setRep(this.mfc.getFamilyId(), this.mfc.getSeniorId(), percentrep, this.level);
                if (sensen > 0) {
                    World.Family.setRep(this.mfc.getFamilyId(), sensen, percentrep / 2, this.level);
                }
            }
        }
    }
    
    public void gainExpMonster(final int gain, final boolean show, final boolean white, final byte pty, final int Class_Bonus_EXP, int 网吧特别经验) {
        int 组队经验值 = 0;
        int 结婚奖励经验值 = 0;
        int 道具佩戴附加经验值 = 0;
        final int prevexp = this.getExp();
        long totalExp = gain + Class_Bonus_EXP + 道具佩戴附加经验值 + 网吧特别经验 + 组队经验值 + 结婚奖励经验值;
        if (this.marriageId > 0) {
            final MapleCharacter marrChr = this.map.getCharacterById(this.marriageId);
            if (marrChr != null) {
                结婚奖励经验值 = (int)(gain / 100.0 * 30.0);
                totalExp += 结婚奖励经验值;
            }
        }
        if (this.hasEquipped(1122017)) {
            道具佩戴附加经验值 = (int)(gain / 100.0 * this.fairyExp);
            totalExp += 道具佩戴附加经验值;
        }
        if (this.haveItem(1142145)) {
            网吧特别经验 = (int)(gain / 100.0 * 30.0);
            totalExp += 网吧特别经验;
        }
        if (pty > 1) {
            组队经验值 = (int)((float)(gain / 20.0) * (pty + 1));
            totalExp += 组队经验值;
        }
        if (gain > 0 && totalExp < gain) {
            totalExp = 2147483647L;
        }
        int needed = GameConstants.getExpNeededForLevel(this.level);
        if (this.level >= Integer.parseInt(ServerProperties.getProperty("RoyMS.MLevel")) || (GameConstants.isKOC(this.job) && this.level >= Integer.parseInt(ServerProperties.getProperty("RoyMS.QLevel")))) {
            if (this.exp + totalExp > needed) {
                this.setExp(needed);
            }
            else {
                this.exp += (int)totalExp;
            }
        }
        else {
            boolean leveled = false;
            if (this.exp + totalExp >= needed) {
                this.exp += (int)totalExp;
                this.levelUp();
                leveled = true;
                needed = GameConstants.getExpNeededForLevel(this.level);
                if (this.exp > needed) {
                    this.setExp(needed);
                }
            }
            else {
                this.exp += (int)totalExp;
            }
            if (totalExp > 0L) {
                this.familyRep(prevexp, needed, leveled);
            }
        }
        if (gain != 0) {
            if (this.exp < 0) {
                if (gain > 0) {
                    this.setExp(GameConstants.getExpNeededForLevel(this.level));
                }
                else if (gain < 0) {
                    this.setExp(0);
                }
            }
            this.updateSingleStat(MapleStat.EXP, this.getExp());
            if (show) {
                this.client.getSession().write(MaplePacketCreator.GainEXP_Monster(gain, white, 结婚奖励经验值, 组队经验值, Class_Bonus_EXP, 道具佩戴附加经验值, 网吧特别经验));
            }
        }
    }
    
    public void forceReAddItem_NoUpdate(final IItem item, final MapleInventoryType type) {
        this.getInventory(type).removeSlot(item.getPosition());
        this.getInventory(type).addFromDB(item);
    }
    
    public void forceReAddItem(final IItem item, final MapleInventoryType type) {
        this.forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            this.client.getSession().write(MaplePacketCreator.updateSpecialItemUse(item, (byte)((type == MapleInventoryType.EQUIPPED) ? 1 : type.getType())));
        }
    }
    
    public void forceReAddItem_Flag(final IItem item, final MapleInventoryType type) {
        this.forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            this.client.getSession().write(MaplePacketCreator.updateSpecialItemUse_(item, (byte)((type == MapleInventoryType.EQUIPPED) ? 1 : type.getType())));
        }
    }
    
    public void silentPartyUpdate() {
        if (this.party != null) {
            World.Party.updateParty(this.party.getId(), PartyOperation.SILENT_UPDATE, new MaplePartyCharacter(this));
        }
    }
    
    public boolean isGM() {
        return this.gmLevel > 0;
    }
    
    public boolean isAdmin() {
        return this.gmLevel >= 2;
    }
    
    public int getGMLevel() {
        return this.gmLevel;
    }
    
    public boolean isPlayer() {
        return this.gmLevel == 0;
    }
    
    public boolean hasGmLevel(final int level) {
        return this.gmLevel >= level;
    }
    
    public MapleInventory getInventory(final MapleInventoryType type) {
        return this.inventory[type.ordinal()];
    }
    
    public MapleInventory[] getInventorys() {
        return this.inventory;
    }
    
    public void expirationTask() {
        this.expirationTask(false);
    }
    
    public void expirationTask(final boolean pending) {
        this.expirationTask(false, pending);
    }
    
    public void expirationTask(final boolean packet, final boolean pending) {
        if (pending) {
            if (this.pendingExpiration != null) {
                for (final Integer z : this.pendingExpiration) {
                    this.client.sendPacket(MTSCSPacket.itemExpired(z));
                }
            }
            this.pendingExpiration = null;
            if (this.pendingSkills != null) {
                for (final Integer z : this.pendingSkills) {
                    this.client.sendPacket(MaplePacketCreator.updateSkill(z, 0, 0, -1L));
                    this.client.sendPacket(MaplePacketCreator.serverNotice(5, "[" + SkillFactory.getSkillName(z) + "] 技能已过期，无法使用。 "));
                }
            }
            this.pendingSkills = null;
            return;
        }
        final List<Integer> ret = new ArrayList<Integer>();
        final long currenttime = System.currentTimeMillis();
        final List<Pair<MapleInventoryType, IItem>> toberemove = new ArrayList<Pair<MapleInventoryType, IItem>>();
        final List<IItem> tobeunlock = new ArrayList<IItem>();
        for (final MapleInventoryType inv : MapleInventoryType.values()) {
            for (final IItem item : this.getInventory(inv)) {
                final long expiration = item.getExpiration();
                if (expiration != -1L && !GameConstants.isPet(item.getItemId()) && currenttime > expiration) {
                    if (ItemFlag.LOCK.check(item.getFlag())) {
                        tobeunlock.add(item);
                    }
                    else {
                        if (currenttime <= expiration) {
                            continue;
                        }
                        toberemove.add(new Pair<MapleInventoryType, IItem>(inv, item));
                    }
                }
                else {
                    if (item.getItemId() != 5000054 || item.getPet() == null || item.getPet().getSecondsLeft() > 0) {
                        continue;
                    }
                    toberemove.add(new Pair<MapleInventoryType, IItem>(inv, item));
                }
            }
        }
        for (final Pair<MapleInventoryType, IItem> itemz : toberemove) {
            final IItem item2 = itemz.getRight();
            ret.add(item2.getItemId());
            if (packet) {
                this.getInventory(itemz.getLeft()).removeItem(item2.getPosition(), item2.getQuantity(), false, this);
            }
            else {
                this.getInventory(itemz.getLeft()).removeItem(item2.getPosition(), item2.getQuantity(), false);
            }
        }
        for (final IItem itemz2 : tobeunlock) {
            itemz2.setExpiration(-1L);
            itemz2.setFlag((byte)(itemz2.getFlag() - ItemFlag.LOCK.getValue()));
        }
        this.pendingExpiration = ret;
        final List<Integer> skilz = new ArrayList<Integer>();
        final List<ISkill> toberem = new ArrayList<ISkill>();
        for (final Map.Entry<ISkill, SkillEntry> skil : this.skills.entrySet()) {
            if (skil.getValue().expiration != -1L && currenttime > skil.getValue().expiration) {
                toberem.add(skil.getKey());
            }
        }
        for (final ISkill skil2 : toberem) {
            skilz.add(skil2.getId());
            this.skills.remove(skil2);
        }
        this.pendingSkills = skilz;
    }
    
    public MapleShop getShop() {
        return this.shop;
    }
    
    public void setShop(final MapleShop shop) {
        this.shop = shop;
    }
    
    public int getMeso() {
        return this.meso;
    }
    
    public int[] getSavedLocations() {
        return this.savedLocations;
    }
    
    public int getSavedLocation(final SavedLocationType type) {
        return this.savedLocations[type.getValue()];
    }
    
    public void saveLocation(final SavedLocationType type) {
        this.savedLocations[type.getValue()] = this.getMapId();
    }
    
    public void saveLocation(final SavedLocationType type, final int mapz) {
        this.savedLocations[type.getValue()] = mapz;
    }
    
    public void clearSavedLocation(final SavedLocationType type) {
        this.savedLocations[type.getValue()] = -1;
    }
    
    public int getDY() {
        return this.maplepoints;
    }
    
    public void setDY(final int set) {
        this.maplepoints = set;
    }
    
    public void gainDY(final int gain) {
        this.maplepoints += gain;
    }
    
    public int getjf() {
        return this.jf;
    }
    
    public void setjf(final int count) {
        this.jf = count;
    }
    
    public void gainjf(final int count) {
        this.jf += count;
    }
    
    public void gainMeso(final int gain, final boolean show) {
        this.gainMeso(gain, show, false, false);
    }
    
    public void gainMeso(final int gain, final boolean show, final boolean enableActions) {
        this.gainMeso(gain, show, enableActions, false);
    }
    
    public void gainMeso(final int gain, final boolean show, final boolean enableActions, final boolean inChat) {
        if (this.meso + gain < 0) {
            this.client.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        this.meso += gain;
        this.updateSingleStat(MapleStat.MESO, this.meso, enableActions);
        if (show) {
            this.client.getSession().write(MaplePacketCreator.showMesoGain(gain, inChat));
        }
    }
    
    public void controlMonster(final MapleMonster monster, final boolean aggro) {
        if (this.clone || monster == null) {
            return;
        }
        monster.setController(this);
        this.controlledLock.writeLock().lock();
        try {
            this.controlled.add(monster);
        }
        finally {
            this.controlledLock.writeLock().unlock();
        }
        this.client.getSession().write(MobPacket.controlMonster(monster, false, aggro));
    }
    
    public void stopControllingMonster(final MapleMonster monster) {
        if (this.clone || monster == null) {
            return;
        }
        this.controlledLock.writeLock().lock();
        try {
            if (this.controlled.contains(monster)) {
                this.controlled.remove(monster);
            }
        }
        finally {
            this.controlledLock.writeLock().unlock();
        }
    }
    
    public void checkMonsterAggro(final MapleMonster monster) {
        if (this.clone || monster == null) {
            return;
        }
        if (monster.getController() == this) {
            monster.setControllerHasAggro(true);
        }
        else {
            monster.switchController(this, true);
        }
    }
    
    public Set<MapleMonster> getControlled() {
        return this.controlled;
    }
    
    public int getControlledSize() {
        return this.controlled.size();
    }
    
    public int getAccountID() {
        return this.accountid;
    }
    
    public void mobKilled(final int id, final int skillID) {
        for (final MapleQuestStatus q : this.quests.values()) {
            if (q.getStatus() == 1) {
                if (!q.hasMobKills()) {
                    continue;
                }
                if (!q.mobKilled(id, skillID)) {
                    continue;
                }
                this.client.getSession().write(MaplePacketCreator.updateQuestMobKills(q));
                if (!q.getQuest().canComplete(this, null)) {
                    continue;
                }
                this.client.getSession().write(MaplePacketCreator.getShowQuestCompletion(q.getQuest().getId()));
            }
        }
    }
    
    public List<MapleQuestStatus> getStartedQuests() {
        final List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (final MapleQuestStatus q : this.quests.values()) {
            if (q.getStatus() == 1 && !q.isCustom()) {
                ret.add(q);
            }
        }
        return ret;
    }
    
    public List<MapleQuestStatus> getCompletedQuests() {
        final List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (final MapleQuestStatus q : this.quests.values()) {
            if (q.getStatus() == 2 && !q.isCustom()) {
                ret.add(q);
            }
        }
        return ret;
    }
    
    public Map<ISkill, SkillEntry> getSkills() {
        return Collections.unmodifiableMap((Map<? extends ISkill, ? extends SkillEntry>)this.skills);
    }
    
    public byte getSkillLevel(final ISkill skill) {
        final SkillEntry ret = this.skills.get(skill);
        if (ret == null || ret.skillevel <= 0) {
            return 0;
        }
        return (byte)Math.min(skill.getMaxLevel(), ret.skillevel + (skill.isBeginnerSkill() ? 0 : this.stats.incAllskill));
    }
    
    public byte getMasterLevel(final int skill) {
        return this.getMasterLevel(SkillFactory.getSkill(skill));
    }
    
    public byte getMasterLevel(final ISkill skill) {
        final SkillEntry ret = this.skills.get(skill);
        if (ret == null) {
            return 0;
        }
        return ret.masterlevel;
    }
    
    public void levelUp() {
        if (GameConstants.isKOC(this.job)) {
            if (this.level <= 70) {
                this.remainingAp += 6;
            }
            else {
                this.remainingAp += 5;
            }
        }
        else {
            this.remainingAp += 5;
        }
        int maxhp = this.stats.getMaxHp();
        int maxmp = this.stats.getMaxMp();
        if (this.job == 0 || this.job == 1000 || this.job == 2000 || this.job == 2001 || this.job == 3000) {
            maxhp += Randomizer.rand(12, 16);
            maxmp += Randomizer.rand(10, 12);
        }
        else if (this.job >= 100 && this.job <= 132) {
            final ISkill improvingMaxHP = SkillFactory.getSkill(1000001);
            final int slevel = this.getSkillLevel(improvingMaxHP);
            if (slevel > 0) {
                maxhp += improvingMaxHP.getEffect(slevel).getX();
            }
            maxhp += Randomizer.rand(24, 28);
            maxmp += Randomizer.rand(4, 6);
        }
        else if (this.job >= 200 && this.job <= 232) {
            final ISkill improvingMaxMP = SkillFactory.getSkill(2000001);
            final int slevel = this.getSkillLevel(improvingMaxMP);
            if (slevel > 0) {
                maxmp += improvingMaxMP.getEffect(slevel).getX() * 2;
            }
            maxhp += Randomizer.rand(10, 14);
            maxmp += Randomizer.rand(22, 24);
        }
        else if (this.job >= 3200 && this.job <= 3212) {
            maxhp += Randomizer.rand(20, 24);
            maxmp += Randomizer.rand(42, 44);
        }
        else if ((this.job >= 300 && this.job <= 322) || (this.job >= 400 && this.job <= 434) || (this.job >= 1300 && this.job <= 1311) || (this.job >= 1400 && this.job <= 1411) || (this.job >= 3300 && this.job <= 3312)) {
            maxhp += Randomizer.rand(20, 24);
            maxmp += Randomizer.rand(14, 16);
        }
        else if ((this.job >= 500 && this.job <= 522) || (this.job >= 3500 && this.job <= 3512)) {
            final ISkill improvingMaxHP = SkillFactory.getSkill(5100000);
            final int slevel = this.getSkillLevel(improvingMaxHP);
            if (slevel > 0) {
                maxhp += improvingMaxHP.getEffect(slevel).getX();
            }
            maxhp += Randomizer.rand(22, 26);
            maxmp += Randomizer.rand(18, 22);
        }
        else if (this.job >= 1100 && this.job <= 1111) {
            final ISkill improvingMaxHP = SkillFactory.getSkill(11000000);
            final int slevel = this.getSkillLevel(improvingMaxHP);
            if (slevel > 0) {
                maxhp += improvingMaxHP.getEffect(slevel).getX();
            }
            maxhp += Randomizer.rand(24, 28);
            maxmp += Randomizer.rand(4, 6);
        }
        else if (this.job >= 1200 && this.job <= 1211) {
            final ISkill improvingMaxMP = SkillFactory.getSkill(12000000);
            final int slevel = this.getSkillLevel(improvingMaxMP);
            if (slevel > 0) {
                maxmp += improvingMaxMP.getEffect(slevel).getX() * 2;
            }
            maxhp += Randomizer.rand(10, 14);
            maxmp += Randomizer.rand(22, 24);
        }
        else if (this.job >= 1500 && this.job <= 1512) {
            final ISkill improvingMaxHP = SkillFactory.getSkill(15100000);
            final int slevel = this.getSkillLevel(improvingMaxHP);
            if (slevel > 0) {
                maxhp += improvingMaxHP.getEffect(slevel).getX();
            }
            maxhp += Randomizer.rand(22, 26);
            maxmp += Randomizer.rand(18, 22);
        }
        else if (this.job >= 2100 && this.job <= 2112) {
            maxhp += Randomizer.rand(50, 52);
            maxmp += Randomizer.rand(4, 6);
        }
        else if (this.job >= 2200 && this.job <= 2218) {
            maxhp += Randomizer.rand(12, 16);
            maxmp += Randomizer.rand(50, 52);
        }
        else {
            maxhp += Randomizer.rand(50, 100);
            maxmp += Randomizer.rand(50, 100);
        }
        maxmp += this.stats.getTotalInt() / 10;
        this.exp -= GameConstants.getExpNeededForLevel(this.level);
        ++this.level;
        final int level = this.getLevel();
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[升级提示]" + this.getName() + "在" + this.getMap().getMapName() + " 等级达到" + level + "级，大家一起祝贺一下吧。"));
        if (level == 3 || level == 6 || level == 9 || level == 12 || level == 15 || level == 18 || level == 21 || level == 24 || level == 27 || level == 30 || level == 33 || level == 36 || level == 39 || level == 42 || level == 45 || level == 48 || level == 51 || level == 54 || level == 57 || level == 60 || level == 63 || level == 66 || level == 69 || level == 72 || level == 75 || level == 78 || level == 81 || level == 84 || level == 87 || level == 90 || level == 93 || level == 96 || level == 99) {}
        maxhp = Math.min(30000, maxhp);
        maxmp = Math.min(30000, maxmp);
        final List<Pair<MapleStat, Integer>> statup = new ArrayList<Pair<MapleStat, Integer>>(8);
        statup.add(new Pair<MapleStat, Integer>(MapleStat.AVAILABLEAP, (int)this.remainingAp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXHP, maxhp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MAXMP, maxmp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.HP, maxhp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.MP, maxmp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.EXP, this.exp));
        statup.add(new Pair<MapleStat, Integer>(MapleStat.LEVEL, level));
        if (this.isGM() || (this.job != 0 && this.job != 1000 && this.job != 2000 && this.job != 2001 && this.job != 3000)) {
            final int[] remainingSp = this.remainingSp;
            final int skillBook = GameConstants.getSkillBook(this.job);
            remainingSp[skillBook] += 3;
            this.client.getSession().write(MaplePacketCreator.updateSp(this, false));
        }
        else if (level <= 10) {
            this.stats.setStr((short)(this.stats.getStr() + this.remainingAp));
            this.remainingAp = 0;
            statup.add(new Pair<MapleStat, Integer>(MapleStat.STR, (int)this.stats.getStr()));
        }
        statup.add(new Pair<MapleStat, Integer>(MapleStat.AVAILABLEAP, (int)this.remainingAp));
        this.stats.setMaxHp((short)maxhp);
        this.stats.setMaxMp((short)maxmp);
        this.stats.setHp((short)maxhp);
        this.stats.setMp((short)maxmp);
        this.client.getSession().write(MaplePacketCreator.updatePlayerStats(statup, this.getJob()));
        this.map.broadcastMessage(this, MaplePacketCreator.showForeignEffect(this.getId(), 0), false);
        this.stats.recalcLocalStats();
        this.silentPartyUpdate();
        this.guildUpdate();
        this.familyUpdate();
        this.saveToDB(false, false);
    }
    
    public void changeKeybinding(final int key, final byte type, final int action) {
        if (type != 0) {
            this.keylayout.Layout().put(key, new Pair<Byte, Integer>(type, action));
        }
        else {
            this.keylayout.Layout().remove(key);
        }
    }
    
    public void sendMacros() {
        for (int i = 0; i < 5; ++i) {
            if (this.skillMacros[i] != null) {
                this.client.getSession().write(MaplePacketCreator.getMacros(this.skillMacros));
                break;
            }
        }
    }
    
    public void updateMacros(final int position, final SkillMacro updateMacro) {
        this.skillMacros[position] = updateMacro;
    }
    
    public SkillMacro[] getMacros() {
        return this.skillMacros;
    }
    
    public void tempban(final String reason, final Calendar duration, final int greason, final boolean IPMac) {
        if (IPMac) {
            this.client.banMacs();
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
            ps.setString(1, this.client.getSession().getRemoteAddress().toString().split(":")[0]);
            ps.execute();
            ps.close();
            this.client.getSession().close(true);
            ps = con.prepareStatement("UPDATE accounts SET tempban = ?, banreason = ?, greason = ? WHERE id = ?");
            final Timestamp TS = new Timestamp(duration.getTimeInMillis());
            ps.setTimestamp(1, TS);
            ps.setString(2, reason);
            ps.setInt(3, greason);
            ps.setInt(4, this.accountid);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error while tempbanning" + ex);
        }
    }
    
    public boolean ban(final String reason, final boolean IPMac, final boolean autoban, boolean hellban) {
        hellban = false;
        if (this.lastmonthfameids == null) {
            throw new RuntimeException("Trying to ban a non-loaded character (testhack)");
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET banned = ?, banreason = ? WHERE id = ?");
            ps.setInt(1, autoban ? 2 : 1);
            ps.setString(2, reason);
            ps.setInt(3, this.accountid);
            ps.execute();
            ps.close();
            this.client.banMacs();
            if (hellban) {
                final PreparedStatement psa = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                psa.setInt(1, this.accountid);
                final ResultSet rsa = psa.executeQuery();
                if (rsa.next()) {
                    final PreparedStatement pss = con.prepareStatement("UPDATE accounts SET banned = ?, banreason = ? WHERE email = ? ");
                    pss.setInt(1, autoban ? 2 : 1);
                    pss.setString(2, reason);
                    pss.setString(3, rsa.getString("email"));
                    pss.execute();
                    pss.close();
                }
                rsa.close();
                psa.close();
            }
        }
        catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
            return false;
        }
        this.client.getSession().close(true);
        return true;
    }
    
    @Override
    public int getObjectId() {
        return this.getId();
    }
    
    @Override
    public void setObjectId(final int id) {
        throw new UnsupportedOperationException();
    }
    
    public MapleStorage getStorage() {
        return this.storage;
    }
    
    public void addVisibleMapObject(final MapleMapObject mo) {
        if (this.clone) {
            return;
        }
        this.visibleMapObjectsLock.writeLock().lock();
        try {
            this.visibleMapObjects.add(mo);
        }
        finally {
            this.visibleMapObjectsLock.writeLock().unlock();
        }
    }
    
    public void removeVisibleMapObject(final MapleMapObject mo) {
        if (this.clone) {
            return;
        }
        this.visibleMapObjectsLock.writeLock().lock();
        try {
            this.visibleMapObjects.remove(mo);
        }
        finally {
            this.visibleMapObjectsLock.writeLock().unlock();
        }
    }
    
    public boolean isMapObjectVisible(final MapleMapObject mo) {
        this.visibleMapObjectsLock.readLock().lock();
        try {
            return !this.clone && this.visibleMapObjects.contains(mo);
        }
        finally {
            this.visibleMapObjectsLock.readLock().unlock();
        }
    }
    
    public Collection<MapleMapObject> getAndWriteLockVisibleMapObjects() {
        this.visibleMapObjectsLock.writeLock().lock();
        return this.visibleMapObjects;
    }
    
    public void unlockWriteVisibleMapObjects() {
        this.visibleMapObjectsLock.writeLock().unlock();
    }
    
    public boolean isAlive() {
        return this.stats.getHp() > 0;
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().write(MaplePacketCreator.removePlayerFromMap(this.getObjectId(), this));
        for (final WeakReference<MapleCharacter> chr : this.clones) {
            if (chr.get() != null) {
                chr.get().sendDestroyData(client);
            }
        }
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (client.getPlayer().allowedToTarget(this)) {
            client.getSession().write(MaplePacketCreator.spawnPlayerMapobject(this));
            if (this.getParty() != null) {
                this.updatePartyMemberHP();
                this.receivePartyMemberHP();
            }
            for (final MaplePet pet : this.pets) {
                if (pet.getSummoned()) {
                    client.getSession().write(PetPacket.showPet(this, pet, false, false));
                    client.sendPacket(PetPacket.petStatUpdate(this));
                }
            }
            for (final WeakReference<MapleCharacter> chr : this.clones) {
                if (chr.get() != null) {
                    chr.get().sendSpawnData(client);
                }
            }
            if (this.summons != null && this.summons.size() > 0) {
                this.summonsLock.readLock().lock();
                try {
                    for (final MapleSummon summon : this.summons) {
                        if (summon.getOwner() != null) {
                            client.getSession().write(MaplePacketCreator.spawnSummon(summon, false));
                        }
                    }
                }
                finally {
                    this.summonsLock.readLock().unlock();
                }
            }
            if (this.followid <= 0 || this.followon) {}
        }
    }
    
    public void equipChanged() {
        this.map.broadcastMessage(this, MaplePacketCreator.updateCharLook(this), false);
        this.map.broadcastMessage(MaplePacketCreator.loveEffect());
        this.stats.recalcLocalStats();
        if (this.getMessenger() != null) {
            World.Messenger.updateMessenger(this.getMessenger().getId(), this.getName(), this.client.getChannel());
        }
        this.saveToDB(false, false);
    }
    
    public MaplePet getPet(final int index) {
        byte count = 0;
        for (final MaplePet pet : this.pets) {
            if (pet.getSummoned()) {
                if (count == index) {
                    return pet;
                }
                ++count;
            }
        }
        return null;
    }
    
    public void addPet(final MaplePet pet) {
        if (this.pets.contains(pet)) {
            this.pets.remove(pet);
        }
        this.pets.add(pet);
    }
    
    public void removePet(final MaplePet pet) {
        pet.setSummoned(0);
        this.pets.remove(pet);
    }
    
    public List<MaplePet> getSummonedPets() {
        final List<MaplePet> ret = new ArrayList<MaplePet>();
        for (int i = 0; i < 3; ++i) {
            ret.add(null);
        }
        for (final MaplePet pet : this.pets) {
            if (pet != null && pet.getSummoned()) {
                final int index = pet.getSummonedValue() - 1;
                ret.remove(index);
                ret.add(index, pet);
            }
        }
        final List<Integer> nullArr = new ArrayList<Integer>();
        nullArr.add(null);
        ret.removeAll(nullArr);
        return ret;
    }
    
    public MaplePet getSummonedPet(final int index) {
        for (final MaplePet pet : this.getSummonedPets()) {
            if (pet.getSummonedValue() - 1 == index) {
                return pet;
            }
        }
        return null;
    }
    
    public void shiftPetsRight() {
        final List<MaplePet> petsz = this.getSummonedPets();
        if (petsz.size() < 3) {
            if (petsz.size() >= 1) {
                final boolean[] indexBool = { false, false, false };
                for (int i = 0; i < 3; ++i) {
                    for (final MaplePet p : petsz) {
                        if (p.getSummonedValue() == i + 1) {
                            indexBool[i] = true;
                        }
                    }
                }
                if (petsz.size() > 1) {
                    if (!indexBool[2]) {
                        petsz.get(0).setSummoned(2);
                        petsz.get(1).setSummoned(3);
                    }
                    else if (!indexBool[1]) {
                        petsz.get(0).setSummoned(2);
                    }
                }
                else if (indexBool[0]) {
                    petsz.get(0).setSummoned(2);
                }
            }
        }
    }
    
    public int getPetSlotNext() {
        final List<MaplePet> petsz = this.getSummonedPets();
        int index = 0;
        if (petsz.size() >= 3) {
            this.unequipPet(this.getSummonedPet(0), false);
        }
        else {
            final boolean[] indexBool = { false, false, false };
            for (int i = 0; i < 3; ++i) {
                for (final MaplePet p : petsz) {
                    if (p.getSummonedValue() == i + 1) {
                        indexBool[i] = true;
                    }
                }
            }
            for (final boolean b : indexBool) {
                if (!b) {
                    break;
                }
                ++index;
            }
            index = Math.min(index, 2);
            for (final MaplePet p2 : petsz) {
                if (p2.getSummonedValue() == index + 1) {
                    this.unequipPet(p2, false);
                }
            }
        }
        return index;
    }
    
    public byte getPetIndex(final MaplePet petz) {
        return (byte)Math.max(-1, petz.getSummonedValue() - 1);
    }
    
    public byte getPetIndex(final int petId) {
        for (final MaplePet pet : this.getSummonedPets()) {
            if (pet.getUniqueId() == petId) {
                return (byte)Math.max(-1, pet.getSummonedValue() - 1);
            }
        }
        return -1;
    }
    
    public byte getPetIndexById(final int petId) {
        for (final MaplePet pet : this.getSummonedPets()) {
            if (pet.getPetItemId() == petId) {
                return (byte)Math.max(-1, pet.getSummonedValue() - 1);
            }
        }
        return -1;
    }
    
    public List<MaplePet> getPets() {
        return this.pets;
    }
    
    public void unequipAllPets() {
        for (final MaplePet pet : this.getSummonedPets()) {
            this.unequipPet(pet, false);
        }
    }
    
    public void unequipPet(final MaplePet pet, final boolean hunger) {
        if (pet.getSummoned()) {
            pet.saveToDb();
            final List<MaplePet> summonedPets = this.getSummonedPets();
            if (summonedPets.contains(pet)) {
                summonedPets.remove(pet);
                int i = 1;
                for (final MaplePet p : summonedPets) {
                    if (p == null) {
                        continue;
                    }
                    p.setSummoned(i);
                    ++i;
                }
            }
            if (this.map != null) {
                this.map.broadcastMessage(this, PetPacket.showPet(this, pet, true, hunger), true);
            }
            pet.setSummoned(0);
            this.client.sendPacket(PetPacket.petStatUpdate(this));
            this.client.sendPacket(MaplePacketCreator.enableActions());
        }
    }
    
    public long getLastFameTime() {
        return this.lastfametime;
    }
    
    public List<Integer> getFamedCharacters() {
        return this.lastmonthfameids;
    }
    
    public FameStatus canGiveFame(final MapleCharacter from) {
        if (this.lastfametime >= System.currentTimeMillis() - 86400000L) {
            return FameStatus.NOT_TODAY;
        }
        if (from == null || this.lastmonthfameids == null || this.lastmonthfameids.contains(from.getId())) {
            return FameStatus.NOT_THIS_MONTH;
        }
        return FameStatus.OK;
    }
    
    public void hasGivenFame(final MapleCharacter to) {
        this.lastfametime = System.currentTimeMillis();
        this.lastmonthfameids.add(to.getId());
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("INSERT INTO famelog (characterid, characterid_to) VALUES (?, ?)");
            ps.setInt(1, this.getId());
            ps.setInt(2, to.getId());
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("ERROR writing famelog for char " + this.getName() + " to " + to.getName() + e);
        }
    }
    
    public MapleKeyLayout getKeyLayout() {
        return this.keylayout;
    }
    
    public MapleParty getParty() {
        return this.party;
    }
    
    public int getPartyId() {
        return (this.party != null) ? this.party.getId() : -1;
    }
    
    public byte getWorld() {
        return this.world;
    }
    
    public void setWorld(final byte world) {
        this.world = world;
    }
    
    public void setParty(final MapleParty party) {
        this.party = party;
    }
    
    public MapleTrade getTrade() {
        return this.trade;
    }
    
    public void setTrade(final MapleTrade trade) {
        this.trade = trade;
    }
    
    public EventInstanceManager getEventInstance() {
        return this.eventInstance;
    }
    
    public void setEventInstance(final EventInstanceManager eventInstance) {
        this.eventInstance = eventInstance;
    }
    
    public void addDoor(final MapleDoor door) {
        this.doors.add(door);
    }
    
    public void clearDoors() {
        this.doors.clear();
    }
    
    public List<MapleDoor> getDoors() {
        return new ArrayList<MapleDoor>(this.doors);
    }
    
    public void setSmega() {
        if (this.smega) {
            this.smega = false;
            this.dropMessage(5, "You have set megaphone to disabled mode");
        }
        else {
            this.smega = true;
            this.dropMessage(5, "You have set megaphone to enabled mode");
        }
    }
    
    public boolean getSmega() {
        return this.smega;
    }
    
    public List<MapleSummon> getSummons() {
        return this.summons;
    }
    
    public List<MapleSummon> getSummonsReadLock() {
        this.summonsLock.readLock().lock();
        return this.summons;
    }
    
    public int getSummonsSize() {
        return this.summons.size();
    }
    
    public void unlockSummonsReadLock() {
        this.summonsLock.readLock().unlock();
    }
    
    public void addSummon(final MapleSummon s) {
        this.summonsLock.writeLock().lock();
        try {
            this.summons.add(s);
        }
        finally {
            this.summonsLock.writeLock().unlock();
        }
    }
    
    public void removeSummon(final MapleSummon s) {
        this.summonsLock.writeLock().lock();
        try {
            this.summons.remove(s);
        }
        finally {
            this.summonsLock.writeLock().unlock();
        }
    }
    
    public int getChair() {
        return this.chair;
    }
    
    public int getItemEffect() {
        return this.itemEffect;
    }
    
    public void setChair(final int chair) {
        this.chair = chair;
        this.stats.relocHeal();
    }
    
    public void setItemEffect(final int itemEffect) {
        this.itemEffect = itemEffect;
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.PLAYER;
    }
    
    public int getFamilyId() {
        if (this.mfc == null) {
            return 0;
        }
        return this.mfc.getFamilyId();
    }
    
    public int getSeniorId() {
        if (this.mfc == null) {
            return 0;
        }
        return this.mfc.getSeniorId();
    }
    
    public int getJunior1() {
        if (this.mfc == null) {
            return 0;
        }
        return this.mfc.getJunior1();
    }
    
    public int getJunior2() {
        if (this.mfc == null) {
            return 0;
        }
        return this.mfc.getJunior2();
    }
    
    public int getCurrentRep() {
        return this.currentrep;
    }
    
    public int getTotalRep() {
        return this.totalrep;
    }
    
    public int getVip() {
        return this.vip;
    }

    public long getVipexpired() {
        return vipexpired;
    }

    public void setVipexpired(long vipexpired) {
        this.vipexpired = vipexpired;
    }

    public void setCurrentRep(final int _rank) {
        this.currentrep = _rank;
        if (this.mfc != null) {
            this.mfc.setCurrentRep(_rank);
        }
    }
    
    public void setTotalRep(final int _rank) {
        this.totalrep = _rank;
        if (this.mfc != null) {
            this.mfc.setTotalRep(_rank);
        }
    }
    
    public int getGuildId() {
        return this.guildid;
    }
    
    public byte getGuildRank() {
        return this.guildrank;
    }
    
    public void setGuildId(final int _id) {
        this.guildid = _id;
        if (this.guildid > 0) {
            if (this.mgc == null) {
                this.mgc = new MapleGuildCharacter(this);
            }
            else {
                this.mgc.setGuildId(this.guildid);
            }
        }
        else {
            this.mgc = null;
        }
    }
    
    public void setGuildRank(final byte _rank) {
        this.guildrank = _rank;
        if (this.mgc != null) {
            this.mgc.setGuildRank(_rank);
        }
    }
    
    public MapleGuildCharacter getMGC() {
        return this.mgc;
    }
    
    public void setAllianceRank(final byte rank) {
        this.allianceRank = rank;
        if (this.mgc != null) {
            this.mgc.setAllianceRank(rank);
        }
    }
    
    public byte getAllianceRank() {
        return this.allianceRank;
    }
    
    public MapleGuild getGuild() {
        if (this.getGuildId() <= 0) {
            return null;
        }
        return World.Guild.getGuild(this.getGuildId());
    }
    
    public void guildUpdate() {
        if (this.guildid <= 0) {
            return;
        }
        this.mgc.setLevel(this.level);
        this.mgc.setJobId(this.job);
        World.Guild.memberLevelJobUpdate(this.mgc);
    }
    
    public void saveGuildStatus() {
        MapleGuild.setOfflineGuildStatus(this.guildid, this.guildrank, this.allianceRank, this.id);
    }
    
    public void familyUpdate() {
        if (this.mfc == null) {
            return;
        }
        World.Family.memberFamilyUpdate(this.mfc, this);
    }
    
    public void saveFamilyStatus() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE characters SET familyid = ?, seniorid = ?, junior1 = ?, junior2 = ? WHERE id = ?");
            if (this.mfc == null) {
                ps.setInt(1, 0);
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
            }
            else {
                ps.setInt(1, this.mfc.getFamilyId());
                ps.setInt(2, this.mfc.getSeniorId());
                ps.setInt(3, this.mfc.getJunior1());
                ps.setInt(4, this.mfc.getJunior2());
            }
            ps.setInt(5, this.id);
            ps.execute();
            ps.close();
        }
        catch (SQLException se) {
            System.out.println("SQLException: " + se.getLocalizedMessage());
            se.printStackTrace();
        }
    }
    
    public void modifyCSPoints(final int type, final int quantity) {
        this.modifyCSPoints(type, quantity, false);
    }
    
    public void dropMessage(final String message) {
        this.dropMessage(6, message);
    }
    
    public void modifyCSPoints(final int type, final int quantity, final boolean show) {
        switch (type) {
            case 1: {
                if (this.acash + quantity < 0) {
                    if (show) {
                        this.dropMessage(5, "你的点卷已经满了");
                    }
                    return;
                }
                this.acash += quantity;
                break;
            }
            case 2: {
                if (this.maplepoints + quantity < 0) {
                    if (show) {
                        this.dropMessage(5, "你的抵用卷已经满了.");
                    }
                    return;
                }
                this.maplepoints += quantity;
                break;
            }
        }
        if (show && quantity != 0) {
            this.dropMessage(5, "你已经 " + ((quantity > 0) ? "获得 " : "使用 ") + quantity + ((type == 1) ? " 点卷." : " 抵用卷."));
        }
    }
    
    public int getCSPoints(final int type) {
        switch (type) {
            case 1: {
                return this.acash;
            }
            case 2: {
                return this.maplepoints;
            }
            default: {
                return 0;
            }
        }
    }
    
    public boolean hasEquipped(final int itemid) {
        return this.inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid) >= 1;
    }
    
    public boolean haveItem(final int itemid, final int quantity, final boolean checkEquipped, final boolean greaterOrEquals) {
        final MapleInventoryType type = GameConstants.getInventoryType(itemid);
        int possesed = this.inventory[type.ordinal()].countById(itemid);
        if (checkEquipped && type == MapleInventoryType.EQUIP) {
            possesed += this.inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        if (greaterOrEquals) {
            return possesed >= quantity;
        }
        return possesed == quantity;
    }
    
    public boolean haveItem(final int itemid, final int quantity) {
        return this.haveItem(itemid, quantity, true, true);
    }
    
    public boolean haveItem(final int itemid) {
        return this.haveItem(itemid, 1, true, true);
    }
    
    public void maxAllSkills() {
        final MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/String.wz"));
        final MapleData skilldData = dataProvider.getData("Skill.img");
        for (final MapleData skill_ : skilldData.getChildren()) {
            try {
                final Skill skill = (Skill)SkillFactory.getSkill1(Integer.parseInt(skill_.getName()));
                if (this.level < 0) {
                    continue;
                }
                this.changeSkillLevel(skill, skill.getMaxLevel(), skill.getMaxLevel());
            }
            catch (NumberFormatException nfe) {
                break;
            }
            catch (NullPointerException ex) {}
        }
    }
    
    public void setAPQScore(final int score) {
        this.APQScore = score;
    }
    
    public int getAPQScore() {
        return this.APQScore;
    }
    
    public long getLasttime() {
        return this.lasttime;
    }
    
    public void setLasttime(final long lasttime) {
        this.lasttime = lasttime;
    }
    
    public long getCurrenttime() {
        return this.currenttime;
    }
    
    public void setCurrenttime(final long currenttime) {
        this.currenttime = currenttime;
    }
    
    public void petUpdateStats(final MaplePet pet) {
        final List<ModifyInventory> mods = new LinkedList<ModifyInventory>();
        final IItem Pet = this.getInventory(MapleInventoryType.CASH).getItem((byte)pet.getInventoryPosition());
        mods.add(new ModifyInventory(3, Pet));
        mods.add(new ModifyInventory(0, Pet));
        this.getClient().getSession().write(MaplePacketCreator.modifyInventory(false, mods));
    }
    
    public void forceUpdateItem(final IItem item) {
        this.forceUpdateItem(item, false);
    }
    
    public void forceUpdateItem(final IItem item, final boolean updateTick) {
        final List<ModifyInventory> mods = new LinkedList<ModifyInventory>();
        mods.add(new ModifyInventory(3, item));
        mods.add(new ModifyInventory(0, item));
        this.client.getSession().write(MaplePacketCreator.modifyInventory(false, new ModifyInventory(ModifyInventory.Types.UPDATE, item)));
    }
    
    public void forceUpdateItem(final MapleInventoryType type, final IItem item) {
        this.client.getSession().write(MaplePacketCreator.clearInventoryItem(type, item.getPosition(), false));
        this.client.getSession().write(MaplePacketCreator.addInventorySlot(type, item, false));
    }
    
    public MapleLieDetector getAntiMacro() {
        return this.antiMacro;
    }
    
    public void startLieDetector(final boolean isItem) {
        if (!this.getAntiMacro().inProgress()) {
            this.getAntiMacro().startLieDetector(this.getName(), isItem, false);
        }
    }
    
    public byte getBuddyCapacity() {
        return this.buddylist.getCapacity();
    }
    
    public void setBuddyCapacity(final byte capacity) {
        this.buddylist.setCapacity(capacity);
        this.client.getSession().write(MaplePacketCreator.updateBuddyCapacity(capacity));
    }
    
    public MapleMessenger getMessenger() {
        return this.messenger;
    }
    
    public void setMessenger(final MapleMessenger messenger) {
        this.messenger = messenger;
    }
    
    public void addCooldown(final int skillId, final long startTime, final long length) {
        this.coolDowns.put(skillId, new MapleCoolDownValueHolder(skillId, startTime, length));
    }
    
    public void removeCooldown(final int skillId) {
        if (this.coolDowns.containsKey(skillId)) {
            this.coolDowns.remove(skillId);
        }
    }
    
    public boolean skillisCooling(final int skillId) {
        return this.coolDowns.containsKey(skillId);
    }
    
    public void giveCoolDowns(final int skillid, final long starttime, final long length) {
        this.addCooldown(skillid, starttime, length);
    }
    
    public void giveCoolDowns(final List<MapleCoolDownValueHolder> cooldowns) {
        if (cooldowns != null) {
            for (final MapleCoolDownValueHolder cooldown : cooldowns) {
                this.coolDowns.put(cooldown.skillId, cooldown);
            }
        }
        else {
            try {
                final Connection con = DatabaseConnection.getConnection();
                final PreparedStatement ps = con.prepareStatement("SELECT SkillID,StartTime,length FROM skills_cooldowns WHERE charid = ?");
                ps.setInt(1, this.getId());
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getLong("length") + rs.getLong("StartTime") - System.currentTimeMillis() <= 0L) {
                        continue;
                    }
                    this.giveCoolDowns(rs.getInt("SkillID"), rs.getLong("StartTime"), rs.getLong("length"));
                }
                ps.close();
                rs.close();
                this.deleteWhereCharacterId(con, "DELETE FROM skills_cooldowns WHERE charid = ?");
            }
            catch (SQLException e) {
                System.err.println("Error while retriving cooldown from SQL storage");
            }
        }
    }
    
    public List<MapleCoolDownValueHolder> getCooldowns() {
        return new ArrayList<MapleCoolDownValueHolder>(this.coolDowns.values());
    }
    
    public List<MapleDiseaseValueHolder> getAllDiseases() {
        return new ArrayList<MapleDiseaseValueHolder>(this.diseases.values());
    }
    
    public boolean hasDisease(final MapleDisease dis) {
        return this.diseases.keySet().contains(dis);
    }
    
    public void giveDebuff(final MapleDisease disease, final MobSkill skill) {
        this.giveDebuff(disease, skill.getX(), skill.getDuration(), skill.getSkillId(), skill.getSkillLevel());
    }
    
    public void giveDebuff(final MapleDisease disease, final int x, final long duration, final int skillid, final int level) {
        final List<Pair<MapleDisease, Integer>> debuff = Collections.singletonList(new Pair<MapleDisease, Integer>(disease, x));
        if (!this.hasDisease(disease) && this.diseases.size() < 2) {
            if (disease != MapleDisease.诱惑 && disease != MapleDisease.眩晕 && this.isActiveBuffedValue(2321005)) {
                return;
            }
            this.diseases.put(disease, new MapleDiseaseValueHolder(disease, System.currentTimeMillis(), duration));
            this.client.getSession().write(MaplePacketCreator.giveDebuff(debuff, skillid, level, (int)duration));
            this.map.broadcastMessage(this, MaplePacketCreator.giveForeignDebuff(this.id, debuff, skillid, level), false);
            if (x > 0 && disease == MapleDisease.中毒) {
                this.addHP((int)(-(x * ((duration - this.stats.decreaseDebuff) / 1000L))));
            }
        }
    }
    
    public void giveSilentDebuff(final List<MapleDiseaseValueHolder> ld) {
        if (ld != null) {
            for (final MapleDiseaseValueHolder disease : ld) {
                this.diseases.put(disease.disease, disease);
            }
        }
    }
    
    public void dispelDebuff(final MapleDisease debuff) {
        if (this.hasDisease(debuff)) {
            final long mask = debuff.getValue();
            final boolean first = debuff.isFirst();
            this.client.getSession().write(MaplePacketCreator.cancelDebuff(mask, first));
            this.map.broadcastMessage(this, MaplePacketCreator.cancelForeignDebuff(this.id, mask, first), false);
            this.diseases.remove(debuff);
        }
    }
    
    public void dispelDebuffs() {
        final List<MapleDisease> diseasess = new ArrayList<MapleDisease>(this.diseases.keySet());
        for (final MapleDisease d : diseasess) {
            this.dispelDebuff(d);
        }
    }
    
    public void cancelAllDebuffs() {
        this.diseases.clear();
    }
    
    public int getDiseaseSize() {
        return this.diseases.size();
    }
    
    public void setLevel(final short level) {
        this.level = (short)(level - 1);
    }
    
    public void sendNote(final String to, final String msg) {
        this.sendNote(to, msg, 0);
    }
    
    public void sendNote(final String to, final String msg, final int fame) {
        MapleCharacterUtil.sendNote(to, this.getName(), msg, fame);
    }
    
    public void showNote() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM notes WHERE `to`=?", 1005, 1008);
            ps.setString(1, this.getName());
            final ResultSet rs = ps.executeQuery();
            rs.last();
            final int count = rs.getRow();
            rs.first();
            this.client.getSession().write(MTSCSPacket.showNotes(rs, count));
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Unable to show note" + e);
        }
    }
    
    public void deleteNote(final int id, final int fame) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT gift FROM notes WHERE `id`=?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("gift") == fame && fame > 0) {
                this.addFame(fame);
                this.updateSingleStat(MapleStat.FAME, this.getFame());
                this.client.getSession().write(MaplePacketCreator.getShowFameGain(fame));
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("DELETE FROM notes WHERE `id`=?");
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Unable to delete note" + e);
        }
    }
    
    public void mulung_EnergyModify(final boolean inc) {
        if (inc) {
            if (this.mulung_energy + 100 > 10000) {
                this.mulung_energy = 10000;
            }
            else {
                this.mulung_energy += 100;
            }
        }
        else {
            this.mulung_energy = 0;
        }
    }
    
    public void writeMulungEnergy() {
    }
    
    public void writeEnergy(final String type, final String inc) {
    }
    
    public void writeStatus(final String type, final String inc) {
    }
    
    public void writePoint(final String type, final String inc) {
    }
    
    public short getCombo() {
        return this.aranCombo;
    }
    
    public void setCombo(final short combo) {
        this.aranCombo = combo;
    }
    
    public long getLastCombo() {
        return this.lastComboTime;
    }
    
    public void setLastComboTime(final long time) {
        this.lastComboTime = time;
    }
    
    public long getKeyDownSkill_Time() {
        return this.keydown_skill;
    }
    
    public void setKeyDownSkill_Time(final long keydown_skill) {
        this.keydown_skill = keydown_skill;
    }
    
    public void checkBerserk() {
        if (this.BerserkSchedule != null) {
            this.BerserkSchedule.cancel(false);
            this.BerserkSchedule = null;
        }
        final ISkill BerserkX = SkillFactory.getSkill(1320006);
        final int skilllevel = this.getSkillLevel(BerserkX);
        if (skilllevel >= 1) {
            final MapleStatEffect ampStat = BerserkX.getEffect(skilllevel);
            this.stats.Berserk = (this.stats.getHp() * 100 / this.stats.getMaxHp() <= ampStat.getX());
            this.client.getSession().write(MaplePacketCreator.showOwnBuffEffect(1320006, 1, (byte)(this.stats.Berserk ? 1 : 0)));
            this.map.broadcastMessage(this, MaplePacketCreator.showBuffeffect(this.getId(), 1320006, 1, (byte)(this.stats.Berserk ? 1 : 0)), false);
            this.BerserkSchedule = Timer.BuffTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    MapleCharacter.this.checkBerserk();
                }
            }, 10000L);
        }
    }
    
    private void prepareBeholderEffect() {
        if (this.beholderHealingSchedule != null) {
            this.beholderHealingSchedule.cancel(false);
        }
        if (this.beholderBuffSchedule != null) {
            this.beholderBuffSchedule.cancel(false);
        }
        final ISkill bHealing = SkillFactory.getSkill(1320008);
        final int bHealingLvl = this.getSkillLevel(bHealing);
        final int berserkLvl = this.getSkillLevel(SkillFactory.getSkill(1320006));
        if (bHealingLvl > 0) {
            final MapleStatEffect healEffect = bHealing.getEffect(bHealingLvl);
            final int healInterval = healEffect.getX() * 1000;
            this.beholderHealingSchedule = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    final int remhppercentage = (int)Math.ceil(MapleCharacter.this.getStat().getHp() * 100.0 / MapleCharacter.this.getStat().getMaxHp());
                    if (berserkLvl == 0 || remhppercentage >= berserkLvl + 10) {
                        MapleCharacter.this.addHP(healEffect.getHp());
                    }
                    MapleCharacter.this.client.getSession().write(MaplePacketCreator.showOwnBuffEffect(1321007, 2));
                    MapleCharacter.this.map.broadcastMessage(MaplePacketCreator.summonSkill(MapleCharacter.this.getId(), 1321007, 5));
                    MapleCharacter.this.map.broadcastMessage(MapleCharacter.this, MaplePacketCreator.showBuffeffect(MapleCharacter.this.getId(), 1321007, 2), false);
                }
            }, healInterval, healInterval);
        }
        final ISkill bBuff = SkillFactory.getSkill(1320009);
        final int bBuffLvl = this.getSkillLevel(bBuff);
        if (bBuffLvl > 0) {
            final MapleStatEffect buffEffect = bBuff.getEffect(bBuffLvl);
            final int buffInterval = buffEffect.getX() * 1000;
            this.beholderBuffSchedule = Timer.BuffTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    buffEffect.applyTo(MapleCharacter.this);
                    MapleCharacter.this.client.getSession().write(MaplePacketCreator.showOwnBuffEffect(1321007, 2));
                    MapleCharacter.this.map.broadcastMessage(MaplePacketCreator.summonSkill(MapleCharacter.this.getId(), 1321007, Randomizer.nextInt(3) + 6));
                    MapleCharacter.this.map.broadcastMessage(MapleCharacter.this, MaplePacketCreator.showBuffeffect(MapleCharacter.this.getId(), 1321007, 2), false);
                }
            }, buffInterval, buffInterval);
        }
    }
    
    public void setChalkboard(final String text) {
        this.chalktext = text;
        this.map.broadcastMessage(MTSCSPacket.useChalkboard(this.getId(), text));
    }
    
    public String getChalkboard() {
        return this.chalktext;
    }
    
    public MapleMount getMount() {
        return this.mount;
    }
    
    public int[] getWishlist() {
        return this.wishlist;
    }
    
    public void clearWishlist() {
        for (int i = 0; i < 10; ++i) {
            this.wishlist[i] = 0;
        }
    }
    
    public int getWishlistSize() {
        int ret = 0;
        for (int i = 0; i < 10; ++i) {
            if (this.wishlist[i] > 0) {
                ++ret;
            }
        }
        return ret;
    }
    
    public void setWishlist(final int[] wl) {
        this.wishlist = wl;
    }
    
    public int[] getRocks() {
        return this.rocks;
    }
    
    public int getRockSize() {
        int ret = 0;
        for (int i = 0; i < 10; ++i) {
            if (this.rocks[i] != 999999999) {
                ++ret;
            }
        }
        return ret;
    }
    
    public void deleteFromRocks(final int map) {
        for (int i = 0; i < 10; ++i) {
            if (this.rocks[i] == map) {
                this.rocks[i] = 999999999;
                break;
            }
        }
    }
    
    public void addRockMap() {
        if (this.getRockSize() >= 10) {
            return;
        }
        for (int i = 0; i < 10; ++i) {
            if (this.rocks[i] == 999999999) {
                this.rocks[i] = this.getMapId();
                return;
            }
        }
        this.rocks[this.getRockSize()] = this.getMapId();
    }
    
    public boolean isRockMap(final int id) {
        for (int i = 0; i < 10; ++i) {
            if (this.rocks[i] == id) {
                return true;
            }
        }
        return false;
    }
    
    public int[] getRegRocks() {
        return this.regrocks;
    }
    
    public int getRegRockSize() {
        int ret = 0;
        for (int i = 0; i < 5; ++i) {
            if (this.regrocks[i] != 999999999) {
                ++ret;
            }
        }
        return ret;
    }
    
    public void deleteFromRegRocks(final int map) {
        for (int i = 0; i < 5; ++i) {
            if (this.regrocks[i] == map) {
                this.regrocks[i] = 999999999;
                break;
            }
        }
    }
    
    public void addRegRockMap() {
        if (this.getRegRockSize() >= 5) {
            return;
        }
        for (int i = 0; i < 5; ++i) {
            if (this.regrocks[i] == 999999999) {
                this.regrocks[i] = this.getMapId();
                return;
            }
        }
        this.regrocks[this.getRegRockSize()] = this.getMapId();
    }
    
    public boolean isRegRockMap(final int id) {
        for (int i = 0; i < 5; ++i) {
            if (this.regrocks[i] == id) {
                return true;
            }
        }
        return false;
    }
    
    public List<LifeMovementFragment> getLastRes() {
        return this.lastres;
    }
    
    public void setLastRes(final List<LifeMovementFragment> lastres) {
        this.lastres = lastres;
    }
    
    public void setMonsterBookCover(final int bookCover) {
        this.bookCover = bookCover;
    }
    
    public int getMonsterBookCover() {
        return this.bookCover;
    }
    
    public int getOneTimeLog(final String bossid) {
        final Connection con1 = DatabaseConnection.getConnection();
        try {
            int ret_count = 0;
            final PreparedStatement ps = con1.prepareStatement("select count(*) from onetimelog where characterid = ? and log = ?");
            ps.setInt(1, this.id);
            ps.setString(2, bossid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            }
            else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            return ret_count;
        }
        catch (SQLException Ex) {
            return -1;
        }
    }
    
    public void setOneTimeLog(final String bossid) {
        final Connection con1 = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con1.prepareStatement("insert into onetimelog (characterid, log) values (?,?)");
            ps.setInt(1, this.id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public int getBossLog(final String boss) {
        return this.getBossLog(boss, 0);
    }
    
    public int getBossLog(final String boss, final int type) {
        try {
            int count = 0;
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bosslog WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, this.id);
            ps.setString(2, boss);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
                final Timestamp bossTime = rs.getTimestamp("time");
                rs.close();
                ps.close();
                if (type == 0) {
                    if (bossTime != null) {
                        final Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(bossTime.getTime());
                        if (cal.get(6) + 1 <= Calendar.getInstance().get(6) || cal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                            count = 0;
                            ps = con.prepareStatement("UPDATE bosslog SET count = 0  WHERE characterid = ? AND bossid = ?");
                            ps.setInt(1, this.id);
                            ps.setString(2, boss);
                            ps.executeUpdate();
                        }
                    }
                    rs.close();
                    ps.close();
                    ps = con.prepareStatement("UPDATE bosslog SET time = CURRENT_TIMESTAMP() WHERE characterid = ? AND bossid = ?");
                    ps.setInt(1, this.id);
                    ps.setString(2, boss);
                    ps.executeUpdate();
                }
            } else {
                final PreparedStatement psu = con.prepareStatement("INSERT INTO bosslog (characterid, bossid, count, type) VALUES (?, ?, ?, ?)");
                psu.setInt(1, this.id);
                psu.setString(2, boss);
                psu.setInt(3, 0);
                psu.setInt(4, type);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            return count;
        }
        catch (SQLException Ex) {
            System.err.println("Error while read bosslog." + Ex);
            return -1;
        }
    }
    
    public int getBossLogType(final String boss) {
        try {
            int type = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM bosslog WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, this.id);
            ps.setString(2, boss);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                type = rs.getInt("type");
            }
            rs.close();
            ps.close();
            return type;
        }
        catch (SQLException Ex) {
            System.err.println("Error while read bosslog." + Ex);
            return -1;
        }
    }
    
    public int getBossLogChannel(final String boss) {
        try {
            int channel = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM bosslog WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, this.id);
            ps.setString(2, boss);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                channel = rs.getInt("channel");
            }
            rs.close();
            ps.close();
            return channel;
        }
        catch (SQLException Ex) {
            System.err.println("Error while read bosslog." + Ex);
            return 0;
        }
    }
    
    public void setBossLog(final String boss) {
        this.setBossLog(boss, 0);
    }
    
    public void setBossLog(final String boss, final int type) {
        this.setBossLog(boss, type, 1);
    }
    
    public void setBossLog(final String boss, final int type, final int count) {
        final int bossCount = this.getBossLog(boss, type);
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE bosslog SET count = ?, type = ?, time = CURRENT_TIMESTAMP(), channel = ? WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, bossCount + count);
            ps.setInt(2, type);
            ps.setInt(3, this.client.getChannel());
            ps.setInt(4, this.id);
            ps.setString(5, boss);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("Error while set bosslog." + Ex);
        }
    }
    
    public void resetBossLog(final String boss) {
        this.resetBossLog(boss, 0);
    }
    
    public void resetBossLog(final String boss, final int type) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE bosslog SET count = ?, type = ?, time = CURRENT_TIMESTAMP(), channel = ? WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, 0);
            ps.setInt(2, type);
            ps.setInt(3, 0);
            ps.setInt(4, this.id);
            ps.setString(5, boss);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("Error while reset bosslog." + Ex);
        }
    }
    
    public void setPrizeLog(final String bossid) {
        final Connection con1 = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con1.prepareStatement("insert into Prizelog (accid, bossid) values (?,?)");
            ps.setInt(1, this.getClient().getAccID());
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex) {}
    }
    
    public int getPrizeLog(final String bossid) {
        final Connection con1 = DatabaseConnection.getConnection();
        try {
            int ret_count = 0;
            final PreparedStatement ps = con1.prepareStatement("select count(*) from Prizelog where accid = ? and bossid = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setString(2, bossid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            }
            else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            return ret_count;
        }
        catch (SQLException Wx) {
            return -1;
        }
    }
    
    public void dropMessage(final int type, final String message) {
        if (type == -2) {
            this.client.getSession().write( PlayerShopPacket.shopChat(message, 0));
        }
        else {
            this.client.getSession().write(MaplePacketCreator.serverNotice(type, message));
        }
    }
    
    public IMaplePlayerShop getPlayerShop() {
        return this.playerShop;
    }
    
    public void setPlayerShop(final IMaplePlayerShop playerShop) {
        this.playerShop = playerShop;
    }
    
    public int getConversation() {
        return this.inst.get();
    }
    
    public void setConversation(final int inst) {
        this.inst.set(inst);
    }
    
    public MapleCarnivalParty getCarnivalParty() {
        return this.carnivalParty;
    }
    
    public void setCarnivalParty(final MapleCarnivalParty party) {
        this.carnivalParty = party;
    }
    
    public void addCP(final int ammount) {
        this.totalCP += (short)ammount;
        this.availableCP += (short)ammount;
    }
    
    public void useCP(final int ammount) {
        this.availableCP -= (short)ammount;
    }
    
    public int getAvailableCP() {
        return this.availableCP;
    }
    
    public int getTotalCP() {
        return this.totalCP;
    }
    
    public void resetCP() {
        this.totalCP = 0;
        this.availableCP = 0;
    }
    
    public void addCarnivalRequest(final MapleCarnivalChallenge request) {
        this.pendingCarnivalRequests.add(request);
    }
    
    public MapleCarnivalChallenge getNextCarnivalRequest() {
        return this.pendingCarnivalRequests.pollLast();
    }
    
    public void clearCarnivalRequests() {
        this.pendingCarnivalRequests = new LinkedList<MapleCarnivalChallenge>();
    }
    
    public void startMonsterCarnival(final int enemyavailable, final int enemytotal) {
        this.client.getSession().write( MonsterCarnivalPacket.startMonsterCarnival(this, enemyavailable, enemytotal));
    }
    
    public void CPUpdate(final boolean party, final int available, final int total, final int team) {
        this.client.getSession().write(MonsterCarnivalPacket.CPUpdate(party, available, total, team));
    }
    
    public void playerDiedCPQ(final String name, final int lostCP, final int team) {
        this.client.getSession().write(MonsterCarnivalPacket.playerDiedMessage(name, lostCP, team));
    }
    
    public boolean getCanTalk() {
        return this.canTalk;
    }
    
    public void canTalk(final boolean talk) {
        this.canTalk = talk;
    }
    
    public int getMaxHp() {
        return this.stats.maxhp;
    }
    
    public void setMaxHp(final short maxhp) {
        this.stats.setMaxHp(maxhp);
    }
    
    public int getMaxMp() {
        return this.stats.maxmp;
    }
    
    public void setMaxMp(final short maxmp) {
        this.stats.setMaxMp(maxmp);
    }
    
    public int getHp() {
        return this.stats.hp;
    }
    
    public void setHp(final int hp) {
        this.stats.setHp(hp);
    }
    
    public int getMp() {
        return this.stats.mp;
    }
    
    public void setMp(final int mp) {
        this.stats.setMp(mp);
    }
    
    public int getStr() {
        return this.stats.str;
    }
    
    public int getDex() {
        return this.stats.dex;
    }
    
    public int getLuk() {
        return this.stats.luk;
    }
    
    public int getInt() {
        return this.stats.int_;
    }
    
    public int getEXPMod() {
        return this.stats.expMod;
    }
    
    public int getDropMod() {
        return this.stats.dropMod;
    }
    
    public int getCashMod() {
        return this.stats.cashMod;
    }
    
    public void setPoints(final int p) {
        this.points = p;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void setVPoints(final int p) {
        this.vpoints = p;
    }
    
    public int getVPoints() {
        return this.vpoints;
    }
    
    public CashShop getCashInventory() {
        return this.cs;
    }
    
    public void removeAll(final int id) {
        this.removeAll(id, true, false);
    }
    
    public void removeAll(final int id, final boolean show, final boolean checkEquipped) {
        MapleInventoryType type = GameConstants.getInventoryType(id);
        int possessed = this.getInventory(type).countById(id);
        if (possessed > 0) {
            MapleInventoryManipulator.removeById(this.getClient(), type, id, possessed, true, false);
            if (show) {
                this.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, (short)(-possessed), true));
            }
        }
        if (checkEquipped && type == MapleInventoryType.EQUIP) {
            type = MapleInventoryType.EQUIPPED;
            possessed = this.getInventory(type).countById(id);
            if (possessed > 0) {
                MapleInventoryManipulator.removeById(this.getClient(), type, id, possessed, true, false);
                if (show) {
                    this.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, (short)(-possessed), true));
                }
                this.equipChanged();
            }
        }
    }
    
    public Pair<List<MapleRing>, List<MapleRing>> getRings(final boolean equip) {
        MapleInventory iv = this.getInventory(MapleInventoryType.EQUIPPED);
        final Collection<IItem> equippedC = iv.list();
        final List<Item> equipped = new ArrayList<Item>(equippedC.size());
        for (final IItem item : equippedC) {
            equipped.add((Item)item);
        }
        Collections.sort(equipped);
        final List<MapleRing> crings = new ArrayList<MapleRing>();
        final List<MapleRing> frings = new ArrayList<MapleRing>();
        for (final Item item2 : equipped) {
            if (item2.getRing() != null) {
                final MapleRing ring = item2.getRing();
                ring.setEquipped(true);
                if (!GameConstants.isFriendshipRing(item2.getItemId()) && !GameConstants.isCrushRing(item2.getItemId())) {
                    continue;
                }
                if (equip) {
                    if (GameConstants.isCrushRing(item2.getItemId())) {
                        crings.add(ring);
                    }
                    else {
                        if (!GameConstants.isFriendshipRing(item2.getItemId())) {
                            continue;
                        }
                        frings.add(ring);
                    }
                }
                else if (crings.isEmpty() && GameConstants.isCrushRing(item2.getItemId())) {
                    crings.add(ring);
                }
                else {
                    if (!frings.isEmpty() || !GameConstants.isFriendshipRing(item2.getItemId())) {
                        continue;
                    }
                    frings.add(ring);
                }
            }
        }
        if (equip) {
            iv = this.getInventory(MapleInventoryType.EQUIP);
            for (final IItem item3 : iv.list()) {
                if (item3.getRing() != null && GameConstants.isEffectRing(item3.getItemId())) {
                    final MapleRing ring = item3.getRing();
                    ring.setEquipped(false);
                    if (GameConstants.isFriendshipRing(item3.getItemId())) {
                        frings.add(ring);
                    }
                    else {
                        if (!GameConstants.isCrushRing(item3.getItemId())) {
                            continue;
                        }
                        crings.add(ring);
                    }
                }
            }
        }
        Collections.sort(frings, new MapleRing.RingComparator());
        Collections.sort(crings, new MapleRing.RingComparator());
        return new Pair<List<MapleRing>, List<MapleRing>>(crings, frings);
    }
    
    public int getFH() {
        final MapleFoothold fh = this.getMap().getFootholds().findBelow(this.getPosition());
        if (fh != null) {
            return fh.getId();
        }
        return 0;
    }
    
    public void startFairySchedule(final boolean exp) {
        this.startFairySchedule(exp, false);
    }
    
    public void startFairySchedule(final boolean exp, final boolean equipped) {
        final int gamepoints = this.getGamePoints();
        if (gamepoints > 0 && gamepoints < 60) {
            this.fairyExp = 10;
        }
        else if (gamepoints >= 60 && gamepoints < 120) {
            this.fairyExp = 20;
        }
        else if (gamepoints >= 120) {
            this.fairyExp = 30;
        }
        this.cancelFairySchedule(exp);
        if (this.fairyExp < 30 && this.stats.equippedFairy) {
            if (equipped) {
                this.dropMessage(5, "您装备了精灵吊坠在1小时后经验获取将增加到 " + (this.fairyExp + 10) + "%.请保持在线哟~！！");
            }
            this.fairySchedule = Timer.EtcTimer.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (MapleCharacter.this.fairyExp < 30 && MapleCharacter.this.stats.equippedFairy) {
                        final int gamepoints = MapleCharacter.this.getGamePoints();
                        if (gamepoints > 0 && gamepoints < 60) {
                            MapleCharacter.this.fairyExp = 10;
                        }
                        else if (gamepoints >= 60 && gamepoints < 120) {
                            MapleCharacter.this.fairyExp = 20;
                        }
                        else if (gamepoints >= 120) {
                            MapleCharacter.this.fairyExp = 30;
                        }
                        MapleCharacter.this.dropMessage(5, "精灵吊坠经验获取量增加到 " + MapleCharacter.this.fairyExp + "%.");
                        MapleCharacter.this.startFairySchedule(false, true);
                    }
                    else {
                        MapleCharacter.this.cancelFairySchedule(!MapleCharacter.this.stats.equippedFairy);
                    }
                }
            }, 1800000L);
        }
        else {
            this.cancelFairySchedule(!this.stats.equippedFairy);
        }
    }
    
    public void cancelFairySchedule(final boolean exp) {
        if (this.fairySchedule != null) {
            this.fairySchedule.cancel(false);
            this.fairySchedule = null;
        }
        if (exp) {
            this.fairyExp = 10;
        }
    }
    
    public byte getFairyExp() {
        return this.fairyExp;
    }
    
    public int getCoconutTeam() {
        return this.coconutteam;
    }
    
    public void setCoconutTeam(final int team) {
        this.coconutteam = team;
    }
    
    public void spawnPet(final byte slot) {
        this.spawnPet(slot, false, true);
    }
    
    public void spawnPet(final byte slot, final boolean lead) {
        this.spawnPet(slot, lead, true);
    }
    
    public void spawnPet(final byte slot, final boolean lead, final boolean broadcast) {
        final IItem item = this.getInventory(MapleInventoryType.CASH).getItem(slot);
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (item == null || !GameConstants.isPet(item.getItemId())) {
            this.client.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        switch (item.getItemId()) {
            case 5000028:
            case 5000047: {
                final MaplePet pet = MaplePet.createPet(item.getItemId() + 1, MapleInventoryIdentifier.getInstance());
                if (pet != null) {
                    MapleInventoryManipulator.addById(this.client, item.getItemId() + 1, (short)1, item.getOwner(), pet, 45L, (byte)0);
                    MapleInventoryManipulator.removeFromSlot(this.client, MapleInventoryType.CASH, slot, (short)1, false);
                    break;
                }
                break;
            }
            default: {
                final MaplePet pet = item.getPet();
                if (pet == null || (item.getItemId() == 5000054 && pet.getSecondsLeft() <= 0) || (item.getExpiration() != -1L && item.getExpiration() <= System.currentTimeMillis())) {
                    break;
                }
                if (pet.getSummoned()) {
                    this.unequipPet(pet, false);
                    break;
                }
                int leadid = 8;
                if (GameConstants.isKOC(this.getJob())) {
                    leadid = 10000018;
                }
                else if (GameConstants.isAran(this.getJob())) {
                    leadid = 20000024;
                }
                if (this.getSkillLevel(SkillFactory.getSkill(leadid)) == 0 && this.getPet(0) != null) {
                    this.unequipPet(this.getPet(0), false);
                }
                else if (lead) {
                    this.shiftPetsRight();
                }
                final Point position;
                final Point pos = position = this.getPosition();
                position.y -= 12;
                pet.setPos(pos);
                try {
                    pet.setFh(this.getMap().getFootholds().findBelow(pos).getId());
                }
                catch (NullPointerException e) {
                    pet.setFh(0);
                }
                pet.setStance(0);
                pet.setSummoned(this.getPetSlotNext() + 1);
                this.addPet(pet);
                if (broadcast) {
                    this.getMap().broadcastMessage(this, PetPacket.showPet(this, pet, false, false), true);
                    this.client.sendPacket(PetPacket.petStatUpdate(this));
                }
                break;
            }
        }
        this.client.sendPacket(PetPacket.emptyStatUpdate());
    }
    
    public void addMoveMob(final int mobid) {
        if (this.movedMobs.containsKey(mobid)) {
            this.movedMobs.put(mobid, this.movedMobs.get(mobid) + 1);
            if (this.movedMobs.get(mobid) > 30) {
                for (final MapleCharacter chr : this.getMap().getCharactersThreadsafe()) {
                    if (chr.getMoveMobs().containsKey(mobid)) {
                        chr.getClient().getSession().write(MobPacket.killMonster(mobid, 1));
                        chr.getMoveMobs().remove(mobid);
                    }
                }
            }
        }
        else {
            this.movedMobs.put(mobid, 1);
        }
    }
    
    public Map<Integer, Integer> getMoveMobs() {
        return this.movedMobs;
    }
    
    public int getLinkMid() {
        return this.linkMid;
    }
    
    public void setLinkMid(final int lm) {
        this.linkMid = lm;
    }
    
    public boolean isClone() {
        return this.clone;
    }
    
    public void setClone(final boolean c) {
        this.clone = c;
    }
    
    public WeakReference<MapleCharacter>[] getClones() {
        return this.clones;
    }
    
    public MapleCharacter cloneLooks() {
        final MapleClient cs = new MapleClient(null, null, (IoSession)new MockIOSession());
        final int minus = this.getId() + Randomizer.nextInt(this.getId());
        final MapleCharacter ret = new MapleCharacter(true);
        ret.id = minus;
        ret.client = cs;
        ret.exp = 0;
        ret.meso = 0;
        ret.beans = this.beans;
        ret.blood = this.blood;
        ret.month = this.month;
        ret.day = this.day;
        ret.charmessage = this.charmessage;
        ret.expression = this.expression;
        ret.constellation = this.constellation;
        ret.remainingAp = 0;
        ret.fame = 0;
        ret.accountid = this.client.getAccID();
        ret.name = this.name;
        ret.level = this.level;
        ret.fame = this.fame;
        ret.job = this.job;
        ret.hair = this.hair;
        ret.face = this.face;
        ret.skinColor = this.skinColor;
        ret.bookCover = this.bookCover;
        ret.monsterbook = this.monsterbook;
        ret.mount = this.mount;
        ret.CRand = new PlayerRandomStream();
        ret.gmLevel = this.gmLevel;
        ret.gender = this.gender;
        ret.mapid = this.map.getId();
        ret.map = this.map;
        ret.setStance(this.getStance());
        ret.chair = this.chair;
        ret.itemEffect = this.itemEffect;
        ret.guildid = this.guildid;
        ret.currentrep = this.currentrep;
        ret.totalrep = this.totalrep;
        ret.stats = this.stats;
        ret.effects.putAll(this.effects);
        if (ret.effects.get(MapleBuffStat.ILLUSION) != null) {
            ret.effects.remove(MapleBuffStat.ILLUSION);
        }
        if (ret.effects.get(MapleBuffStat.召唤兽) != null) {
            ret.effects.remove(MapleBuffStat.召唤兽);
        }
        if (ret.effects.get(MapleBuffStat.REAPER) != null) {
            ret.effects.remove(MapleBuffStat.REAPER);
        }
        if (ret.effects.get(MapleBuffStat.替身术) != null) {
            ret.effects.remove(MapleBuffStat.替身术);
        }
        ret.guildrank = this.guildrank;
        ret.allianceRank = this.allianceRank;
        ret.hidden = this.hidden;
        ret.setPosition(new Point(this.getPosition()));
        for (final IItem equip : this.getInventory(MapleInventoryType.EQUIPPED)) {
            ret.getInventory(MapleInventoryType.EQUIPPED).addFromDB(equip);
        }
        ret.skillMacros = this.skillMacros;
        ret.keylayout = this.keylayout;
        ret.questinfo = this.questinfo;
        ret.savedLocations = this.savedLocations;
        ret.wishlist = this.wishlist;
        ret.rocks = this.rocks;
        ret.regrocks = this.regrocks;
        ret.buddylist = this.buddylist;
        ret.keydown_skill = 0L;
        ret.lastmonthfameids = this.lastmonthfameids;
        ret.lastfametime = this.lastfametime;
        ret.storage = this.storage;
        ret.cs = this.cs;
        ret.client.setAccountName(this.client.getAccountName());
        ret.acash = this.acash;
        ret.lastGainHM = this.lastGainHM;
        ret.maplepoints = this.maplepoints;
        ret.clone = true;
        ret.client.setChannel(this.client.getChannel());
        System.out.println("cloneLooks输出：" + this.client.getChannel());
        while (this.map.getCharacterById(ret.id) != null || this.client.getChannelServer().getPlayerStorage().getCharacterById(ret.id) != null) {
            final MapleCharacter mapleCharacter = ret;
            ++mapleCharacter.id;
        }
        ret.client.setPlayer(ret);
        return ret;
    }
    
    public void cloneLook() {
        if (this.clone) {
            return;
        }
        for (int i = 0; i < this.clones.length; ++i) {
            if (this.clones[i].get() == null) {
                final MapleCharacter newp = this.cloneLooks();
                this.map.addPlayer(newp);
                this.map.broadcastMessage(MaplePacketCreator.updateCharLook(newp));
                this.map.movePlayer(newp, this.getPosition());
                this.clones[i] = new WeakReference<MapleCharacter>(newp);
                return;
            }
        }
    }
    
    public void disposeClones() {
        this.numClones = 0;
        for (int i = 0; i < this.clones.length; ++i) {
            if (this.clones[i].get() != null) {
                this.map.removePlayer(this.clones[i].get());
                this.clones[i].get().getClient().disconnect(false, false);
                this.clones[i] = new WeakReference<MapleCharacter>(null);
                ++this.numClones;
            }
        }
    }
    
    public int getCloneSize() {
        int z = 0;
        for (int i = 0; i < this.clones.length; ++i) {
            if (this.clones[i].get() != null) {
                ++z;
            }
        }
        return z;
    }
    
    public void spawnClones() {
        if (this.numClones == 0 && this.stats.hasClone) {
            this.cloneLook();
        }
        for (int i = 0; i < this.numClones; ++i) {
            this.cloneLook();
        }
        this.numClones = 0;
    }
    
    public byte getNumClones() {
        return this.numClones;
    }
    
    public void spawnSavedPets() {
        for (int i = 0; i < this.petStore.length; ++i) {
            if (this.petStore[i] > -1) {
                this.spawnPet(this.petStore[i], false, false);
            }
        }
        this.client.getSession().write(PetPacket.petStatUpdate(this));
        this.petStore = new byte[] { -1, -1, -1 };
    }
    
    public byte[] getPetStores() {
        return this.petStore;
    }
    
    public void resetStats(final int str, final int dex, final int int_, final int luk) {
        final List<Pair<MapleStat, Integer>> stat = new ArrayList<Pair<MapleStat, Integer>>(2);
        int total = this.stats.getStr() + this.stats.getDex() + this.stats.getLuk() + this.stats.getInt() + this.getRemainingAp();
        total -= str;
        this.stats.setStr((short)str);
        total -= dex;
        this.stats.setDex((short)dex);
        total -= int_;
        this.stats.setInt((short)int_);
        total -= luk;
        this.stats.setLuk((short)luk);
        this.setRemainingAp((short)total);
        if (this.getRemainingAp() < 0) {
            this.remainingAp = 0;
        }
        stat.add(new Pair<MapleStat, Integer>(MapleStat.STR, str));
        stat.add(new Pair<MapleStat, Integer>(MapleStat.DEX, dex));
        stat.add(new Pair<MapleStat, Integer>(MapleStat.INT, int_));
        stat.add(new Pair<MapleStat, Integer>(MapleStat.LUK, luk));
        stat.add(new Pair<MapleStat, Integer>(MapleStat.AVAILABLEAP, total));
        this.client.getSession().write(MaplePacketCreator.updatePlayerStats(stat, false, this.getJob()));
    }
    
    public Event_PyramidSubway getPyramidSubway() {
        return this.pyramidSubway;
    }
    
    public void setPyramidSubway(final Event_PyramidSubway ps) {
        this.pyramidSubway = ps;
    }
    
    public byte getSubcategory() {
        if (this.job >= 430 && this.job <= 434) {
            return 1;
        }
        return this.subcategory;
    }
    
    public int itemQuantity(final int itemid) {
        return this.getInventory(GameConstants.getInventoryType(itemid)).countById(itemid);
    }
    
    public void setRPS(final RockPaperScissors rps) {
        this.rps = rps;
    }
    
    public RockPaperScissors getRPS() {
        return this.rps;
    }
    
    public long getNextConsume() {
        return this.nextConsume;
    }
    
    public void setNextConsume(final long nc) {
        this.nextConsume = nc;
    }
    
    public int getRank() {
        return this.rank;
    }
    
    public int getRankMove() {
        return this.rankMove;
    }
    
    public int getJobRank() {
        return this.jobRank;
    }
    
    public int getJobRankMove() {
        return this.jobRankMove;
    }
    
    public void changeChannel(final int channel) {
        final Integer energyLevel = this.getBuffedValue(MapleBuffStat.能量获得);
        if (energyLevel != null && energyLevel > 0) {
            this.setBuffedValue(MapleBuffStat.能量获得, energyLevel);
            final List<Pair<MapleBuffStat, Integer>> stat = Collections.singletonList(new Pair<MapleBuffStat, Integer>(MapleBuffStat.能量获得, energyLevel));
            this.client.getSession().write(MaplePacketCreator.能量条(stat, 0));
        }
        final String[] socket = this.client.getChannelServer().getIP().split(":");
        final ChannelServer toch = ChannelServer.getInstance(channel);
        if (channel == this.client.getChannel() || toch == null || toch.isShutdown()) {
            return;
        }
        this.changeRemoval();
        final ChannelServer ch = ChannelServer.getInstance(this.client.getChannel());
        if (this.getMessenger() != null) {
            World.Messenger.silentLeaveMessenger(this.getMessenger().getId(), new MapleMessengerCharacter(this));
        }
        PlayerBuffStorage.addBuffsToStorage(this.getId(), this.getAllBuffs());
        PlayerBuffStorage.addCooldownsToStorage(this.getId(), this.getCooldowns());
        PlayerBuffStorage.addDiseaseToStorage(this.getId(), this.getAllDiseases());
        World.ChannelChange_Data(new CharacterTransfer(this), this.getId(), channel);
        ch.removePlayer(this);
        this.client.updateLoginState(MapleClient.CHANGE_CHANNEL, this.client.getSessionIPAddress());
        final String s = this.client.getSessionIPAddress();
        LoginServer.addIPAuth(s.substring(s.indexOf(47) + 1, s.length()));
        try {
            this.client.getSession().write(MaplePacketCreator.getChannelChange(InetAddress.getByName(socket[0]), Integer.parseInt(toch.getIP().split(":")[1])));
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(MapleCharacter.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.saveToDB(false, false);
        this.getMap().removePlayer(this);
        this.client.setPlayer(null);
        this.client.setReceiving(false);
        this.expirationTask(true, false);
    }
    
    public void expandInventory(final byte type, final int amount) {
        final MapleInventory inv = this.getInventory(MapleInventoryType.getByType(type));
        if (inv.getSlotLimit() < 96) {
            inv.addSlot((byte)amount);
            this.client.getSession().write(MaplePacketCreator.getSlotUpdate(type, inv.getSlotLimit()));
        }
    }
    
    public boolean allowedToTarget(final MapleCharacter other) {
        return other != null && (!other.isHidden() || this.getGMLevel() >= other.getGMLevel());
    }
    
    public int getFollowId() {
        return this.followid;
    }
    
    public void setFollowId(final int fi) {
        this.followid = fi;
        if (fi == 0) {
            this.followinitiator = false;
            this.followon = false;
        }
    }
    
    public void setFollowInitiator(final boolean fi) {
        this.followinitiator = fi;
    }
    
    public void setFollowOn(final boolean fi) {
        this.followon = fi;
    }
    
    public boolean isFollowOn() {
        return this.followon;
    }
    
    public boolean isFollowInitiator() {
        return this.followinitiator;
    }
    
    public void checkFollow() {
        if (this.followon) {
            final MapleCharacter tt = this.map.getCharacterById(this.followid);
            if (tt != null) {
                tt.setFollowId(0);
            }
            this.setFollowId(0);
        }
    }
    
    public int getMarriageId() {
        return this.marriageId;
    }
    
    public void setMarriageId(final int mi) {
        this.marriageId = mi;
    }
    
    public int getMarriageItemId() {
        return this.marriageItemId;
    }
    
    public void setMarriageItemId(final int mi) {
        this.marriageItemId = mi;
    }
    
    public boolean isStaff() {
        return this.gmLevel > ServerConstants.PlayerGMRank.NORMAL.getLevel();
    }
    
    public boolean startPartyQuest(final int questid) {
        boolean ret = false;
        if (!this.quests.containsKey(MapleQuest.getInstance(questid)) || !this.questinfo.containsKey(questid)) {
            final MapleQuestStatus status = this.getQuestNAdd(MapleQuest.getInstance(questid));
            status.setStatus((byte)1);
            this.updateQuest(status);
            switch (questid) {
                case 1300:
                case 1301:
                case 1302: {
                    this.updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0;gvup=0;vic=0;lose=0;draw=0");
                    break;
                }
                case 1204: {
                    this.updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have0=0;have1=0;have2=0;have3=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                }
                case 1206: {
                    this.updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have0=0;have1=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                }
                default: {
                    this.updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                }
            }
            ret = true;
        }
        return ret;
    }
    
    public String getOneInfo(final int questid, final String key) {
        if (!this.questinfo.containsKey(questid) || key == null) {
            return null;
        }
        final String[] split3;
        final String[] split = split3 = this.questinfo.get(questid).split(";");
        for (final String x : split3) {
            final String[] split2 = x.split("=");
            if (split2.length == 2 && split2[0].equals(key)) {
                return split2[1];
            }
        }
        return null;
    }
    
    public void updateOneInfo(final int questid, final String key, final String value) {
        if (!this.questinfo.containsKey(questid) || key == null || value == null) {
            return;
        }
        final String[] split = this.questinfo.get(questid).split(";");
        boolean changed = false;
        final StringBuilder newQuest = new StringBuilder();
        for (final String x : split) {
            final String[] split2 = x.split("=");
            if (split2.length == 2) {
                if (split2[0].equals(key)) {
                    newQuest.append(key).append("=").append(value);
                }
                else {
                    newQuest.append(x);
                }
                newQuest.append(";");
                changed = true;
            }
        }
        this.updateInfoQuest(questid, changed ? newQuest.toString().substring(0, newQuest.toString().length() - 1) : newQuest.toString());
    }
    
    public void recalcPartyQuestRank(final int questid) {
        if (!this.startPartyQuest(questid)) {
            final String oldRank = this.getOneInfo(questid, "rank");
            if (oldRank == null || oldRank.equals("S")) {
                return;
            }
            final String[] split = this.questinfo.get(questid).split(";");
            String newRank = null;
            final String s = oldRank;
            switch (s) {
                case "A": {
                    newRank = "S";
                    break;
                }
                case "B": {
                    newRank = "A";
                    break;
                }
                case "C": {
                    newRank = "B";
                    break;
                }
                case "D": {
                    newRank = "C";
                    break;
                }
                case "F": {
                    newRank = "D";
                    break;
                }
                default: {
                    return;
                }
            }
            final List<Pair<String, Pair<String, Integer>>> questInfo = MapleQuest.getInstance(questid).getInfoByRank(newRank);
            for (final Pair<String, Pair<String, Integer>> q : questInfo) {
                boolean found = false;
                final String val = this.getOneInfo(questid, q.right.left);
                if (val == null) {
                    return;
                }
                int vall = 0;
                try {
                    vall = Integer.parseInt(val);
                }
                catch (NumberFormatException e) {
                    return;
                }
                final String s2 = q.left;
                switch (s2) {
                    case "less": {
                        found = (vall < q.right.right);
                        break;
                    }
                    case "more": {
                        found = (vall > q.right.right);
                        break;
                    }
                    case "equal": {
                        found = (vall == q.right.right);
                        break;
                    }
                }
                if (!found) {
                    return;
                }
            }
            this.updateOneInfo(questid, "rank", newRank);
        }
    }
    
    public void tryPartyQuest(final int questid) {
        try {
            this.startPartyQuest(questid);
            this.pqStartTime = System.currentTimeMillis();
            this.updateOneInfo(questid, "try", String.valueOf(Integer.parseInt(this.getOneInfo(questid, "try")) + 1));
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("tryPartyQuest error");
        }
    }
    
    public void endPartyQuest(final int questid) {
        try {
            this.startPartyQuest(questid);
            if (this.pqStartTime > 0L) {
                final long changeTime = System.currentTimeMillis() - this.pqStartTime;
                final int mins = (int)(changeTime / 1000L / 60L);
                final int secs = (int)(changeTime / 1000L % 60L);
                final int mins2 = Integer.parseInt(this.getOneInfo(questid, "min"));
                final int secs2 = Integer.parseInt(this.getOneInfo(questid, "sec"));
                if (mins2 <= 0 || mins < mins2) {
                    this.updateOneInfo(questid, "min", String.valueOf(mins));
                    this.updateOneInfo(questid, "sec", String.valueOf(secs));
                    this.updateOneInfo(questid, "date", FileoutputUtil.CurrentReadable_Date());
                }
                final int newCmp = Integer.parseInt(this.getOneInfo(questid, "cmp")) + 1;
                this.updateOneInfo(questid, "cmp", String.valueOf(newCmp));
                this.updateOneInfo(questid, "CR", String.valueOf((int)Math.ceil(newCmp * 100.0 / Integer.parseInt(this.getOneInfo(questid, "try")))));
                this.recalcPartyQuestRank(questid);
                this.pqStartTime = 0L;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("endPartyQuest error");
        }
    }
    
    public void havePartyQuest(final int itemId) {
        int questid = 0;
        int index = -1;
        switch (itemId) {
            case 1002798: {
                questid = 1200;
                break;
            }
            case 1072369: {
                questid = 1201;
                break;
            }
            case 1022073: {
                questid = 1202;
                break;
            }
            case 1082232: {
                questid = 1203;
                break;
            }
            case 1002571:
            case 1002572:
            case 1002573:
            case 1002574: {
                questid = 1204;
                index = itemId - 1002571;
                break;
            }
            case 1122010: {
                questid = 1205;
                break;
            }
            case 1032060:
            case 1032061: {
                questid = 1206;
                index = itemId - 1032060;
                break;
            }
            case 3010018: {
                questid = 1300;
                break;
            }
            case 1122007: {
                questid = 1301;
                break;
            }
            case 1122058: {
                questid = 1302;
                break;
            }
            default: {
                return;
            }
        }
        this.startPartyQuest(questid);
        this.updateOneInfo(questid, "have" + ((index == -1) ? "" : Integer.valueOf(index)), "1");
    }
    
    public void resetStatsByJob(final boolean beginnerJob) {
        final int baseJob = beginnerJob ? (this.job % 1000) : (this.job % 1000 / 100 * 100);
        switch (baseJob) {
            case 100: {
                this.resetStats(25, 4, 4, 4);
                break;
            }
            case 200: {
                this.resetStats(4, 4, 20, 4);
                break;
            }
            case 300:
            case 400: {
                this.resetStats(4, 25, 4, 4);
                break;
            }
            case 500: {
                this.resetStats(4, 20, 4, 4);
                break;
            }
        }
    }
    
    public boolean hasSummon() {
        return this.hasSummon;
    }
    
    public void setHasSummon(final boolean summ) {
        this.hasSummon = summ;
    }
    
    public void removeDoor() {
        final MapleDoor door = this.getDoors().iterator().next();
        for (final MapleCharacter chr : door.getTarget().getCharactersThreadsafe()) {
            door.sendDestroyData(chr.getClient());
        }
        for (final MapleCharacter chr : door.getTown().getCharactersThreadsafe()) {
            door.sendDestroyData(chr.getClient());
        }
        for (final MapleDoor destroyDoor : this.getDoors()) {
            door.getTarget().removeMapObject(destroyDoor);
            door.getTown().removeMapObject(destroyDoor);
        }
        this.clearDoors();
    }
    
    public void changeRemoval() {
        this.changeRemoval(false);
    }
    
    public void changeRemoval(final boolean dc) {
        if (this.getTrade() != null) {
            MapleTrade.cancelTrade(this.getTrade(), this.client);
        }
        if (this.getCheatTracker() != null) {
            this.getCheatTracker().dispose();
        }
        if (!dc) {
            this.cancelEffectFromBuffStat(MapleBuffStat.骑兽技能);
            this.cancelEffectFromBuffStat(MapleBuffStat.召唤兽);
            this.cancelEffectFromBuffStat(MapleBuffStat.REAPER);
            this.cancelEffectFromBuffStat(MapleBuffStat.替身术);
        }
        if (this.getPyramidSubway() != null) {
            this.getPyramidSubway().dispose(this);
        }
        if (this.playerShop != null && !dc) {
            this.playerShop.removeVisitor(this);
            if (this.playerShop.isOwner(this)) {
                this.playerShop.setOpen(true);
            }
        }
        if (!this.getDoors().isEmpty()) {
            this.removeDoor();
        }
        this.disposeClones();
        NPCScriptManager.getInstance().dispose(this.client);
    }
    
    public void updateTick(final int newTick) {
        this.anticheat.updateTick(newTick);
    }
    
    public boolean canUseFamilyBuff(final MapleFamilyBuff.MapleFamilyBuffEntry buff) {
        final MapleQuestStatus stat = this.getQuestNAdd(MapleQuest.getInstance(buff.questID));
        if (stat.getCustomData() == null) {
            stat.setCustomData("0");
        }
        return Long.parseLong(stat.getCustomData()) + 86400000L < System.currentTimeMillis();
    }
    
    public void useFamilyBuff(final MapleFamilyBuff.MapleFamilyBuffEntry buff) {
        final MapleQuestStatus stat = this.getQuestNAdd(MapleQuest.getInstance(buff.questID));
        stat.setCustomData(String.valueOf(System.currentTimeMillis()));
    }
    
    public List<Pair<Integer, Integer>> usedBuffs() {
        final List<Pair<Integer, Integer>> used = new ArrayList<Pair<Integer, Integer>>();
        for (final MapleFamilyBuff.MapleFamilyBuffEntry buff : MapleFamilyBuff.getBuffEntry()) {
            if (!this.canUseFamilyBuff(buff)) {
                used.add(new Pair<Integer, Integer>(buff.index, buff.count));
            }
        }
        return used;
    }
    
    public String getTeleportName() {
        return this.teleportname;
    }
    
    public void setTeleportName(final String tname) {
        this.teleportname = tname;
    }
    
    public int getNoJuniors() {
        if (this.mfc == null) {
            return 0;
        }
        return this.mfc.getNoJuniors();
    }
    
    public MapleFamilyCharacter getMFC() {
        return this.mfc;
    }
    
    public void makeMFC(final int familyid, final int seniorid, final int junior1, final int junior2) {
        if (familyid > 0) {
            final MapleFamily f = World.Family.getFamily(familyid);
            if (f == null) {
                this.mfc = null;
            }
            else {
                this.mfc = f.getMFC(this.id);
                if (this.mfc == null) {
                    this.mfc = f.addFamilyMemberInfo(this, seniorid, junior1, junior2);
                }
                if (this.mfc.getSeniorId() != seniorid) {
                    this.mfc.setSeniorId(seniorid);
                }
                if (this.mfc.getJunior1() != junior1) {
                    this.mfc.setJunior1(junior1);
                }
                if (this.mfc.getJunior2() != junior2) {
                    this.mfc.setJunior2(junior2);
                }
            }
        }
        else {
            this.mfc = null;
        }
    }
    
    public void setFamily(final int newf, final int news, final int newj1, final int newj2) {
        if (this.mfc == null || newf != this.mfc.getFamilyId() || news != this.mfc.getSeniorId() || newj1 != this.mfc.getJunior1() || newj2 != this.mfc.getJunior2()) {
            this.makeMFC(newf, news, newj1, newj2);
        }
    }
    
    public int maxBattleshipHP(final int skillid) {
        return this.getSkillLevel(skillid) * 5000 + (this.getLevel() - 120) * 3000;
    }
    
    public int currentBattleshipHP() {
        return this.battleshipHP;
    }
    
    public void sendEnglishQuiz(final String msg) {
        this.client.getSession().write(MaplePacketCreator.englishQuizMsg(msg));
    }
    
    public void fakeRelog() {
        this.client.getSession().write(MaplePacketCreator.getCharInfo(this));
        final MapleMap mapp = this.getMap();
        mapp.removePlayer(this);
        mapp.addPlayer(this);
        this.client.getSession().write(MaplePacketCreator.serverNotice(5, "刷新人数据完成..."));
    }
    
    public String getcharmessage() {
        return this.charmessage;
    }
    
    public void setcharmessage(final String s) {
        this.charmessage = s;
    }
    
    public int getexpression() {
        return this.expression;
    }
    
    public void setexpression(final int s) {
        this.expression = s;
    }
    
    public int getconstellation() {
        return this.constellation;
    }
    
    public void setconstellation(final int s) {
        this.constellation = s;
    }
    
    public int getblood() {
        return this.blood;
    }
    
    public void setblood(final int s) {
        this.blood = s;
    }
    
    public int getmonth() {
        return this.month;
    }
    
    public void setmonth(final int s) {
        this.month = s;
    }
    
    public int getday() {
        return this.day;
    }
    
    public void setday(final int s) {
        this.day = s;
    }
    
    public int getTeam() {
        return this.coconutteam;
    }
    
    public void setTeam(final int team) {
        this.coconutteam = team;
    }
    
    public int getBeans() {
        return this.beans;
    }
    
    public void gainBeans(final int s) {
        this.beans += s;
    }
    
    public void setBeans(final int s) {
        this.beans = s;
    }
    
    public int getBeansNum() {
        return this.beansNum;
    }
    
    public void setBeansNum(final int beansNum) {
        this.beansNum = beansNum;
    }
    
    public int getBeansRange() {
        return this.beansRange;
    }
    
    public void setBeansRange(int beansRange) {
        beansRange = beansRange;
    }
    
    public boolean isCanSetBeansNum() {
        return this.canSetBeansNum;
    }
    
    public void setCanSetBeansNum(final boolean canSetBeansNum) {
        this.canSetBeansNum = canSetBeansNum;
    }
    
    public boolean haveGM() {
        return this.gmLevel >= 2 && this.gmLevel <= 3;
    }
    
    public void setprefix(final int prefix) {
        this.prefix = prefix;
    }
    
    public int getPrefix() {
        return this.prefix;
    }
    
    public void startMapEffect(final String msg, final int itemId) {
        this.startMapEffect(msg, itemId, 10000);
    }
    
    public void startMapEffect1(final String msg, final int itemId) {
        this.startMapEffect(msg, itemId, 20000);
    }
    
    public void startMapEffect(final String msg, final int itemId, final int duration) {
        final MapleMapEffect mapEffect = new MapleMapEffect(msg, itemId);
        this.getClient().getSession().write(mapEffect.makeStartData());
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                MapleCharacter.this.getClient().getSession().write(mapEffect.makeDestroyData());
            }
        }, duration);
    }
    
    public long getDeadtime() {
        return this.deadtime;
    }
    
    public void setDeadtime(final long deadtime) {
        this.deadtime = deadtime;
    }
    
    public void increaseEquipExp(final int mobexp) {
        final MapleItemInformationProvider mii = MapleItemInformationProvider.getInstance();
        try {
            for (final IItem item : this.getInventory(MapleInventoryType.EQUIPPED).list()) {
                final Equip nEquip = (Equip)item;
                final String itemName = mii.getName(nEquip.getItemId());
                if (itemName == null) {
                    continue;
                }
                if ((itemName.contains("重生") || nEquip.getEquipLevel() >= 4) && (!itemName.contains("永恒") || nEquip.getEquipLevel() >= 6)) {
                    continue;
                }
                nEquip.gainItemExp(this.client, mobexp, itemName.contains("永恒"));
            }
        }
        catch (Exception ex) {}
    }
    
    public void petName(final String name) {
        final MaplePet pet = this.getPet(0);
        if (pet == null) {
            this.getClient().getSession().write(MaplePacketCreator.serverNotice(1, "请召唤一只宠物出来！"));
            this.getClient().getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        pet.setName(name);
        this.getClient().getSession().write(PetPacket.updatePet(pet, this.getInventory(MapleInventoryType.CASH).getItem(pet.getInventoryPosition()), true));
        this.getClient().getSession().write(MaplePacketCreator.enableActions());
        this.getClient().getPlayer().getMap().broadcastMessage(this.getClient().getPlayer(), MTSCSPacket.changePetName(this.getClient().getPlayer(), name, 1), true);
    }
    
    public void reloadC() {
        this.client.getSession().write(MaplePacketCreator.getCharInfo(this.client.getPlayer()));
        this.client.getPlayer().getMap().removePlayer(this.client.getPlayer());
        this.client.getPlayer().getMap().addPlayer(this.client.getPlayer());
    }
    
    public void maxSkills() {
        for (final ISkill sk : SkillFactory.getAllSkills()) {
            this.changeSkillLevel(sk, sk.getMaxLevel(), sk.getMaxLevel());
        }
    }
    
    public void UpdateCash() {
        this.getClient().getSession().write(MaplePacketCreator.showCharCash(this));
    }
    
    public void addAriantScore() {
        ++this.ariantScore;
    }
    
    public void resetAriantScore() {
        this.ariantScore = 0;
    }
    
    public int getAriantScore() {
        return this.ariantScore;
    }
    
    public void updateAriantScore() {
        this.getMap().broadcastMessage(MaplePacketCreator.updateAriantScore(this.getName(), this.getAriantScore(), false));
    }
    
    public int getAveragePartyLevel() {
        int averageLevel = 0;
        int size = 0;
        for (final MaplePartyCharacter pl : this.getParty().getMembers()) {
            averageLevel += pl.getLevel();
            ++size;
        }
        if (size <= 0) {
            return this.level;
        }
        averageLevel /= size;
        return averageLevel;
    }
    
    public int getAverageMapLevel() {
        int averageLevel = 0;
        int size = 0;
        for (final MapleCharacter pl : this.getMap().getCharacters()) {
            averageLevel += pl.getLevel();
            ++size;
        }
        if (size <= 0) {
            return this.level;
        }
        averageLevel /= size;
        return averageLevel;
    }
    
    public void setApprentice(final int app) {
        this.apprentice = app;
    }
    
    public boolean hasApprentice() {
        return this.apprentice > 0;
    }
    
    public int getMaster() {
        return this.master;
    }
    
    public int getApprentice() {
        return this.apprentice;
    }
    
    public MapleCharacter getApp() {
        return this.client.getChannelServer().getPlayerStorage().getCharacterById(this.apprentice);
    }
    
    public MapleCharacter getMster() {
        return this.client.getChannelServer().getPlayerStorage().getCharacterById(this.master);
    }
    
    public void setMaster(final int mstr) {
        this.master = mstr;
    }
    
    public MapleRing getMarriageRing(final boolean incluedEquip) {
        MapleInventory iv = this.getInventory(MapleInventoryType.EQUIPPED);
        final Collection<IItem> equippedC = iv.list();
        final List<Item> equipped = new ArrayList<Item>(equippedC.size());
        for (final IItem item : equippedC) {
            equipped.add((Item)item);
        }
        for (final Item item2 : equipped) {
            if (item2.getRing() != null) {
                final MapleRing ring = item2.getRing();
                ring.setEquipped(true);
                if (GameConstants.isMarriageRing(item2.getItemId())) {
                    return ring;
                }
                continue;
            }
        }
        if (incluedEquip) {
            iv = this.getInventory(MapleInventoryType.EQUIP);
            for (final IItem item : iv.list()) {
                if (item.getRing() != null && GameConstants.isMarriageRing(item.getItemId())) {
                    final MapleRing ring = item.getRing();
                    ring.setEquipped(false);
                    return ring;
                }
            }
        }
        return null;
    }
    
    public void setDebugMessage(final boolean control) {
        this.DebugMessage = control;
    }
    
    public boolean getDebugMessage() {
        return this.DebugMessage;
    }
    
    public int getNX() {
        return this.getCSPoints(1);
    }
    
    public boolean canHold(final int itemid) {
        return this.getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }
    
    public int getIntRecord(final int questID) {
        final MapleQuestStatus stat = this.getQuestNAdd(MapleQuest.getInstance(questID));
        if (stat.getCustomData() == null) {
            stat.setCustomData("0");
        }
        return Integer.parseInt(stat.getCustomData());
    }
    
    public int getIntNoRecord(final int questID) {
        final MapleQuestStatus stat = this.getQuestNoAdd(MapleQuest.getInstance(questID));
        if (stat == null || stat.getCustomData() == null) {
            return 0;
        }
        return Integer.parseInt(stat.getCustomData());
    }
    
    public void updatePetEquip() {
        if (this.getIntNoRecord(122221) > 0) {
            this.client.getSession().write(MaplePacketCreator.petAutoHP(this.getIntRecord(122221)));
        }
        if (this.getIntNoRecord(122222) > 0) {
            this.client.getSession().write(MaplePacketCreator.petAutoMP(this.getIntRecord(122222)));
        }
    }
    
    public void spawnBomb() {
        final MapleMonster bomb = MapleLifeFactory.getMonster(9300166);
        bomb.changeLevel(250, true);
        this.getMap().spawnMonster_sSack(bomb, this.getPosition(), -2);
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                MapleCharacter.this.map.killMonster(bomb, MapleCharacter.this.client.getPlayer(), false, false, (byte)1);
            }
        }, 10000L);
    }
    
    public boolean isAriantPQMap() {
        switch (this.getMapId()) {
            case 980010101:
            case 980010201:
            case 980010301: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void addMobVac(final int type) {
        if (type == 1) {
            ++this.MobVac;
        }
        else if (type == 2) {
            ++this.MobVac2;
        }
    }
    
    public int getMobVac(final int type) {
        switch (type) {
            case 1: {
                return this.MobVac;
            }
            case 2: {
                return this.MobVac2;
            }
            default: {
                return 0;
            }
        }
    }
    
    public void gainIten(final int id, final int amount) {
        MapleInventoryManipulator.addById(this.getClient(), id, (short)amount, (byte)0);
    }
    
    public long getLastHM() {
        return this.lastGainHM;
    }
    
    public void setLastHM(final long newTime) {
        this.lastGainHM = newTime;
    }
    
    public boolean inIntro() {
        return MapleCharacter.tutorial;
    }
    
    public void checkCopyItems() {
        final List<Integer> equipOnlyIds = new ArrayList<Integer>();
        final Map<Integer, Integer> checkItems = new HashMap<Integer, Integer>();
        for (final IItem item : this.getInventory(MapleInventoryType.EQUIP).list()) {
            final int equipOnlyId = item.getEquipOnlyId();
            if (equipOnlyId > 0) {
                if (checkItems.containsKey(equipOnlyId)) {
                    if (checkItems.get(equipOnlyId) != item.getItemId()) {
                        continue;
                    }
                    equipOnlyIds.add(equipOnlyId);
                }
                else {
                    checkItems.put(equipOnlyId, item.getItemId());
                }
            }
        }
        for (final IItem item : this.getInventory(MapleInventoryType.EQUIPPED).list()) {
            final int equipOnlyId = item.getEquipOnlyId();
            if (equipOnlyId > 0) {
                if (checkItems.containsKey(equipOnlyId)) {
                    if (checkItems.get(equipOnlyId) != item.getItemId()) {
                        continue;
                    }
                    equipOnlyIds.add(equipOnlyId);
                }
                else {
                    checkItems.put(equipOnlyId, item.getItemId());
                }
            }
        }
        for (final IItem item : this.getInventory(MapleInventoryType.ETC).list()) {
            final int equipOnlyId = item.getEquipOnlyId();
            if (equipOnlyId > 0) {
                if (checkItems.containsKey(equipOnlyId)) {
                    if (checkItems.get(equipOnlyId) != item.getItemId()) {
                        continue;
                    }
                    equipOnlyIds.add(equipOnlyId);
                }
                else {
                    checkItems.put(equipOnlyId, item.getItemId());
                }
            }
        }
        for (final IItem item : this.getInventory(MapleInventoryType.USE).list()) {
            final int equipOnlyId = item.getEquipOnlyId();
            if (equipOnlyId > 0) {
                if (checkItems.containsKey(equipOnlyId)) {
                    if (checkItems.get(equipOnlyId) != item.getItemId()) {
                        continue;
                    }
                    equipOnlyIds.add(equipOnlyId);
                }
                else {
                    checkItems.put(equipOnlyId, item.getItemId());
                }
            }
        }
        for (final IItem item : this.getInventory(MapleInventoryType.CASH).list()) {
            final int equipOnlyId = item.getEquipOnlyId();
            if (equipOnlyId > 0) {
                if (checkItems.containsKey(equipOnlyId)) {
                    if (checkItems.get(equipOnlyId) != item.getItemId()) {
                        continue;
                    }
                    equipOnlyIds.add(equipOnlyId);
                }
                else {
                    checkItems.put(equipOnlyId, item.getItemId());
                }
            }
        }
        boolean autoban = false;
        for (final Integer equipOnlyId2 : equipOnlyIds) {
            MapleInventoryManipulator.removeAllByEquipOnlyId(this.client, equipOnlyId2);
            autoban = true;
        }
        if (autoban) {
            AutobanManager.getInstance().autoban(this.client, "无理由.");
        }
        checkItems.clear();
        equipOnlyIds.clear();
    }
    
    public int getskillzq() {
        return this.skillzq;
    }
    
    public void setskillzq(final int s) {
        this.skillzq = s;
    }
    
    public int getbosslog() {
        return this.bosslog;
    }
    
    public void setbosslog(final int s) {
        this.bosslog = s;
    }
    
    public int getPGMaxDamage() {
        return this.PGMaxDamage;
    }
    
    public void setPGMaxDamage(final int s) {
        this.PGMaxDamage = s;
    }
    
    public int getjzname() {
        return this.jzname;
    }
    
    public void setjzname(final int s) {
        this.jzname = s;
    }
    
    public int getmrsgrw() {
        return this.mrsgrw;
    }
    
    public void setmrsgrw(final int s) {
        this.mrsgrw = s;
    }
    
    public int getmrsgrwa() {
        return this.mrsgrwa;
    }
    
    public void setmrsgrwa(final int s) {
        this.mrsgrwa = s;
    }
    
    public int getmrsgrwas() {
        return this.mrsgrwas;
    }
    
    public void setmrsgrwas(final int s) {
        this.mrsgrwas = s;
    }
    
    public int getmrsgrws() {
        return this.mrsgrws;
    }
    
    public void setmrsgrws(final int s) {
        this.mrsgrws = s;
    }
    
    public int gethythd() {
        return this.hythd;
    }
    
    public void sethythd(final int s) {
        this.hythd = s;
    }
    
    public int getmrsjrw() {
        return this.mrsjrw;
    }
    
    public void setmrsjrw(final int s) {
        this.mrsjrw = s;
    }
    
    public int getmrfbrw() {
        return this.mrfbrw;
    }
    
    public void setmrfbrw(final int s) {
        this.mrfbrw = s;
    }
    
    public int getmrsbossrw() {
        return this.mrsbossrw;
    }
    
    public void setmrsbossrw(final int s) {
        this.mrsbossrw = s;
    }
    
    public int getmrfbrws() {
        return this.mrfbrws;
    }
    
    public void setmrfbrws(final int s) {
        this.mrfbrws = s;
    }
    
    public int getmrsbossrws() {
        return this.mrsbossrws;
    }
    
    public void setmrsbossrws(final int s) {
        this.mrsbossrws = s;
    }
    
    public int getmrfbrwa() {
        return this.mrfbrwa;
    }
    
    public void setmrfbrwa(final int s) {
        this.mrfbrwa = s;
    }
    
    public int getmrsbossrwa() {
        return this.mrsbossrwa;
    }
    
    public void setmrsbossrwa(final int s) {
        this.mrsbossrwa = s;
    }
    
    public int getmrfbrwas() {
        return this.mrfbrwas;
    }
    
    public void setmrfbrwas(final int s) {
        this.mrfbrwas = s;
    }
    
    public int getvip() {
        return this.vip;
    }
    
    public void setvip(final int s) {
        this.vip = s;
    }


    
    public void gainvip(final int s) {
        this.vip += s;
    }
    
    public int getddj() {
        return this.ddj;
    }
    
    public void setddj(final int s) {
        this.ddj = s;
    }
    
    public void gainddj(final int s) {
        this.ddj += s;
    }
    
    public int getdjjl() {
        return this.djjl;
    }
    
    public void setdjjl(final int s) {
        this.djjl = s;
    }
    
    public void gaindjjl(final int s) {
        this.djjl += s;
    }
    
    public int getSG() {
        return this.shaguai;
    }
    
    public void setSG(final int s) {
        this.shaguai = s;
    }
    
    public void gainSG(final int s) {
        this.shaguai += s;
    }
    
    public int getqiandao() {
        return this.qiandao;
    }
    
    public void setqiandao(final int s) {
        this.qiandao = s;
    }
    
    public void gainqiandao(final int s) {
        this.qiandao += s;
    }
    
    public int getmrsbossrwas() {
        return this.mrsbossrwas;
    }
    
    public void setmrsbossrwas(final int s) {
        this.mrsbossrwas = s;
    }
    
    public int 获取全民夺宝总数() throws SQLException {
        final Connection con = DatabaseConnection.getConnection();
        final String sql = "SELECT count(*) from qmdbplayer";
        final PreparedStatement ps = con.prepareStatement(sql);
        final ResultSet rs = ps.executeQuery();
        int count = -1;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return count;
    }
    
    public int 全民夺宝(final int type) {
        int pay = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from qmdb");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                switch (type) {
                    case 1: {
                        pay = rs.getInt("itemid");
                        break;
                    }
                    case 2: {
                        pay = rs.getInt("money");
                        break;
                    }
                    case 3: {
                        pay = rs.getInt("characterid");
                        break;
                    }
                    case 4: {
                        pay = rs.getInt("type");
                        break;
                    }
                    case 5: {
                        pay = rs.getInt("sl");
                        break;
                    }
                    default: {
                        pay = 0;
                        break;
                    }
                }
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("查询全民夺宝信息错误: " + ex);
        }
        return pay;
    }
    
    public String 全民夺宝2(final int id) {
        String pay = "";
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from qmdbplayer where id = " + id + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pay = rs.getString("name");
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("查询全民夺宝信息Name错误: " + ex);
        }
        return pay;
    }
    
    public int 全民夺宝3(final int id) {
        int pay = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from qmdbplayer where id = " + id + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pay = rs.getInt("characterid");
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("查询全民夺宝信息Id错误: " + ex);
        }
        return pay;
    }
    
    public String 领取日志() {
        String result = "";
        final int i = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM qmdblog");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = result + "#b时间#r#e[" + rs.getTimestamp("sj") + "]#n#k\r\n幸运玩家：#b#e" + rs.getString("name") + "#n #k赢取奖励:#b#e#z" + rs.getInt("itemid") + "#x" + rs.getInt("sl") + "#n\r\n---------------------------------------------\r\n";
            }
        }
        catch (SQLException ex) {
            return "";
        }
        return result;
    }
    
    public int 玩家获得物品(final int id, final String name) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE qmdb SET characterid = " + id + ",name = " + name + ",type = 1");
            final PreparedStatement ps2 = con.prepareStatement("UPDATE qmdblog SET sj = CURRENT_TIMESTAMP(),characterid = " + id + ",name = " + name + "");
            ps.executeUpdate();
            ps2.executeUpdate();
            ps.close();
            ps2.cancel();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("数据库操作错误，方法:玩家获得物品(int id,String name) " + ex);
            return 0;
        }
    }
    
    public int 玩家获得物品2(final int lx) {
        int pay = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from qmdb where characterid  = " + this.getId() + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (lx == 1) {
                    pay = rs.getInt("itemid");
                }
                else if (lx == 2) {
                    pay = rs.getInt("sl");
                }
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("查询全民夺宝信息Id错误: " + ex);
        }
        return pay;
    }
    
    public void 全民夺宝删除() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("Truncate Table qmdb ");
            final PreparedStatement ps2 = con.prepareStatement("Truncate Table qmdbplayer ");
            ps.executeUpdate();
            ps2.executeUpdate();
            ps.close();
            ps2.cancel();
        }
        catch (SQLException ex) {
            System.err.println("数据库操作错误，全民夺宝删除 " + ex);
        }
    }
    
    public void 参加全民夺宝() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement psu = con.prepareStatement("insert into qmdbplayer (characterid, name) VALUES (?, ?)");
            psu.setInt(1, this.getId());
            psu.setString(2, this.getName());
            psu.executeUpdate();
            psu.close();
        }
        catch (SQLException ex) {
            System.err.println("参加全民夺宝发生了错误: " + ex);
        }
    }
    
    public int getSJRW() {
        try {
            int sjrw = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sjrw = rs.getInt("sjrw");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    sjrw = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET sjrw = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, sjrw) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return sjrw;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的0点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainSJRW(final int amount) {
        final int sjrw = this.getSJRW() + amount;
        this.updateSJRW(sjrw);
    }
    
    public void resetSJRW() {
        this.updateSJRW(0);
    }
    
    public void updateSJRW(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET sjrw = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getFBRW() {
        try {
            int fbrw = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fbrw = rs.getInt("fbrw");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    fbrw = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET fbrw = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, fbrw) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return fbrw;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainFBRW(final int amount) {
        final int fbrw = this.getFBRW() + amount;
        this.updateFBRW(fbrw);
    }
    
    public void resetFBRW() {
        this.updateFBRW(0);
    }
    
    public void updateFBRW(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET fbrw = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getFBRWA() {
        try {
            int fbrwa = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fbrwa = rs.getInt("fbrwa");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    fbrwa = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET fbrwa = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, fbrwa) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return fbrwa;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainFBRWA(final int amount) {
        final int fbrw = this.getFBRWA() + amount;
        this.updateFBRWA(fbrw);
    }
    
    public void resetFBRWA() {
        this.updateFBRWA(0);
    }
    
    public void updateFBRWA(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET fbrwa = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getSGRW() {
        try {
            int sgrw = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sgrw = rs.getInt("sgrw");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    sgrw = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET sgrw = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, sgrw) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return sgrw;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainSGRW(final int amount) {
        final int sgrw = this.getSGRW() + amount;
        this.updateSGRW(sgrw);
    }
    
    public void resetSGRW() {
        this.updateSGRW(0);
    }
    
    public void updateSGRW(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET sgrw = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getSGRWA() {
        try {
            int sgrwa = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sgrwa = rs.getInt("sgrwa");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    sgrwa = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET sgrwa = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, sgrwa) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return sgrwa;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainSGRWA(final int amount) {
        final int sgrw = this.getSGRWA() + amount;
        this.updateSGRWA(sgrw);
    }
    
    public void resetSGRWA() {
        this.updateSGRWA(0);
    }
    
    public void updateSGRWA(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET sgrwa = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getSBOSSRW() {
        try {
            int sbossrw = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sbossrw = rs.getInt("sbossrw");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    sbossrw = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET sbossrw = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, sbossrw) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return sbossrw;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainSBOSSRW(final int amount) {
        final int sbossrw = this.getSBOSSRW() + amount;
        this.updateSBOSSRW(sbossrw);
    }
    
    public void resetSBOSSRW() {
        this.updateSBOSSRW(0);
    }
    
    public void updateSBOSSRW(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET sbossrw = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getSBOSSRWA() {
        try {
            int sbossrwa = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sbossrwa = rs.getInt("sbossrwa");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    sbossrwa = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET sbossrwa = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, sbossrwa) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return sbossrwa;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainSBOSSRWA(final int amount) {
        final int sbossrw = this.getSBOSSRWA() + amount;
        this.updateSBOSSRWA(sbossrw);
    }
    
    public void resetSBOSSRWA() {
        this.updateSBOSSRWA(0);
    }
    
    public void updateSBOSSRWA(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET sbossrwa = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getlb() {
        try {
            int lb = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lb = rs.getInt("lb");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    lb = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET lb = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, lb) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return lb;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainlb(final int amount) {
        final int lb = this.getlb() + amount;
        this.updatelb(lb);
    }
    
    public void resetlb() {
        this.updatelb(0);
    }
    
    public void updatelb(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET lb = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getGamePoints() {
        try {
            int gamePoints = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gamePoints = rs.getInt("gamePoints");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    gamePoints = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET gamePoints = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, gamePoints) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return gamePoints;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public int getGamePointsPD() {
        try {
            int gamePointsPD = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gamePointsPD = rs.getInt("gamePointspd");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    gamePointsPD = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET gamePointspd = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, gamePointspd) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return gamePointsPD;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainGamePoints(final int amount) {
        final int gamePoints = this.getGamePoints() + amount;
        this.updateGamePoints(gamePoints);
    }
    
    public void gainGamePointsPD(final int amount) {
        final int gamePointsPD = this.getGamePointsPD() + amount;
        this.updateGamePointsPD(gamePointsPD);
    }
    
    public void resetGamePointsPD() {
        this.updateGamePointsPD(0);
    }
    
    public void updateGamePointsPD(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePointspd = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public void resetGamePoints() {
        this.updateGamePoints(0);
    }
    
    public void updateGamePoints(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePoints = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getGamePointsRQ() {
        try {
            int gamePointsRQ = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gamePointsRQ = rs.getInt("gamePointsrq");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    gamePointsRQ = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET gamePointsrq = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, gamePointsrq) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return gamePointsRQ;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainGamePointsRQ(final int amount) {
        final int gamePointsRQ = this.getGamePointsRQ() + amount;
        this.updateGamePointsRQ(gamePointsRQ);
    }
    
    public void resetGamePointsRQ() {
        this.updateGamePointsRQ(0);
    }
    
    public void updateGamePointsRQ(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePointsrq = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getGamePointsPS() {
        try {
            int gamePointsRQ = 0;
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts_info WHERE accId = ? AND worldId = ?");
            ps.setInt(1, this.getClient().getAccID());
            ps.setInt(2, this.getWorld());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gamePointsRQ = rs.getInt("gamePointsps");
                final Timestamp updateTime = rs.getTimestamp("updateTime");
                final Calendar sqlcal = Calendar.getInstance();
                if (updateTime != null) {
                    sqlcal.setTimeInMillis(updateTime.getTime());
                }
                if (sqlcal.get(5) + 1 <= Calendar.getInstance().get(5) || sqlcal.get(2) + 1 <= Calendar.getInstance().get(2) || sqlcal.get(1) + 1 <= Calendar.getInstance().get(1)) {
                    gamePointsRQ = 0;
                    final PreparedStatement psu = con.prepareStatement("UPDATE accounts_info SET gamePointsps = 0, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
                    psu.setInt(1, this.getClient().getAccID());
                    psu.setInt(2, this.getWorld());
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                final PreparedStatement psu2 = con.prepareStatement("INSERT INTO accounts_info (accId, worldId, gamePointsps) VALUES (?, ?, ?)");
                psu2.setInt(1, this.getClient().getAccID());
                psu2.setInt(2, this.getWorld());
                psu2.setInt(3, 0);
                psu2.executeUpdate();
                psu2.close();
            }
            rs.close();
            ps.close();
            return gamePointsRQ;
        }
        catch (SQLException Ex) {
            System.err.println("获取角色帐号的在线时间点出现错误 - 数据库查询失败" + Ex);
            return -1;
        }
    }
    
    public void gainGamePointsPS(final int amount) {
        final int gamePointsPS = this.getGamePointsPS() + amount;
        this.updateGamePointsPS(gamePointsPS);
    }
    
    public void resetGamePointsPS() {
        this.updateGamePointsPS(0);
    }
    
    public void updateGamePointsPS(final int amount) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePointsps = ?, updateTime = CURRENT_TIMESTAMP() WHERE accId = ? AND worldId = ?");
            ps.setInt(1, amount);
            ps.setInt(2, this.getClient().getAccID());
            ps.setInt(3, this.getWorld());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
        }
    }
    
    public int getHyPay(final int type) {
        int pay = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from hypay where accname = ?");
            ps.setString(1, this.getClient().getAccountName());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                switch (type) {
                    case 1: {
                        pay = rs.getInt("pay");
                        break;
                    }
                    case 2: {
                        pay = rs.getInt("payUsed");
                        break;
                    }
                    case 3: {
                        pay = rs.getInt("pay") + rs.getInt("payUsed");
                        break;
                    }
                    case 4: {
                        pay = rs.getInt("payReward");
                        break;
                    }
                    default: {
                        pay = 0;
                        break;
                    }
                }
            }
            else {
                final PreparedStatement psu = con.prepareStatement("insert into hypay (accname, pay, payUsed, payReward) VALUES (?, ?, ?, ?)");
                psu.setString(1, this.getClient().getAccountName());
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(4, 0);
                psu.executeUpdate();
                psu.close();
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("获取充值信息发生错误: " + ex);
        }
        return pay;
    }
    
    public int gainHyPay(final int hypay) {
        final int pay = this.getHyPay(1);
        final int payUsed = this.getHyPay(2);
        final int payReward = this.getHyPay(4);
        if (hypay <= 0) {
            return 0;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE hypay SET pay = ? ,payUsed = ? ,payReward = ? where accname = ?");
            ps.setInt(1, pay + hypay);
            ps.setInt(2, payUsed);
            ps.setInt(3, payReward);
            ps.setString(4, this.getClient().getAccountName());
            ps.executeUpdate();
            ps.close();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("加减充值信息发生错误: " + ex);
            return 0;
        }
    }
    
    public int addHyPay(final int hypay) {
        final int pay = this.getHyPay(1);
        final int payUsed = this.getHyPay(2);
        final int payReward = this.getHyPay(4);
        if (hypay > pay) {
            return -1;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE hypay SET pay = ? ,payUsed = ? ,payReward = ? where accname = ?");
            ps.setInt(1, pay - hypay);
            ps.setInt(2, payUsed + hypay);
            ps.setInt(3, payReward + hypay);
            ps.setString(4, this.getClient().getAccountName());
            ps.executeUpdate();
            ps.close();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("加减充值信息发生错误: " + ex);
            return -1;
        }
    }
    
    public int delPayReward(final int pay) {
        final int payReward = this.getHyPay(4);
        if (pay <= 0) {
            return -1;
        }
        if (pay > payReward) {
            return -1;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE hypay SET payReward = ? where accname = ?");
            ps.setInt(1, payReward - pay);
            ps.setString(2, this.getClient().getAccountName());
            ps.executeUpdate();
            ps.close();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("加减消费奖励信息发生错误: " + ex);
            return -1;
        }
    }
    
    public int getFishingJF(final int type) {
        int jf = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from fishingjf where accname = ?");
            ps.setString(1, this.getClient().getAccountName());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                switch (type) {
                    case 1: {
                        jf = rs.getInt("fishing");
                        break;
                    }
                    case 2: {
                        jf = rs.getInt("XX");
                        break;
                    }
                    case 3: {
                        jf = rs.getInt("XXX");
                        break;
                    }
                    default: {
                        jf = 0;
                        break;
                    }
                }
            }
            else {
                final PreparedStatement psu = con.prepareStatement("insert into fishingjf (accname, fishing, XX, XXX) VALUES (?, ?, ?, ?)");
                psu.setString(1, this.getClient().getAccountName());
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(4, 0);
                psu.executeUpdate();
                psu.close();
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("获取钓鱼积分信息发生错误: " + ex);
        }
        return jf;
    }
    
    public int gainFishingJF(final int hypay) {
        final int jf = this.getFishingJF(1);
        final int XX = this.getFishingJF(2);
        final int XXX = this.getFishingJF(3);
        if (hypay <= 0) {
            return 0;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE fishingjf SET fishing = ? ,XX = ? ,XXX = ? where accname = ?");
            ps.setInt(1, hypay + jf);
            ps.setInt(2, XX);
            ps.setInt(3, XXX);
            ps.setString(4, this.getClient().getAccountName());
            ps.executeUpdate();
            ps.close();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("加减钓鱼积分信息发生错误: " + ex);
            return 0;
        }
    }
    
    public int addFishingJF(final int hypay) {
        final int jf = this.getFishingJF(1);
        final int XX = this.getFishingJF(2);
        final int XXX = this.getFishingJF(3);
        if (hypay > jf) {
            return -1;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE fishingjf SET fishing = ? ,XX = ? ,XXX = ? where accname = ?");
            ps.setInt(1, jf - hypay);
            ps.setInt(2, XX);
            ps.setInt(3, XXX);
            ps.setString(4, this.getClient().getAccountName());
            ps.executeUpdate();
            ps.close();
            return 1;
        }
        catch (SQLException ex) {
            System.err.println("加减钓鱼积分信息发生错误: " + ex);
            return -1;
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.getTruePosition().x - 25, this.getTruePosition().y - 75, 50, 75);
    }
    
    public int getTouzhuNX() {
        return this.touzhuNX;
    }
    
    public void setTouzhuNX(final int touzhuNX) {
        this.touzhuNX = touzhuNX;
    }
    
    public int getTouzhuNum() {
        return this.touzhuNum;
    }
    
    public void setTouzhuNum(final int touzhuNum) {
        this.touzhuNum = touzhuNum;
    }
    
    public int getTouzhuType() {
        return this.touzhuType;
    }
    
    public void setTouzhuType(final int touzhuType) {
        this.touzhuType = touzhuType;
    }
    
    public MaplePvpStats getPvpStats() {
        return this.pvpStats;
    }
    
    public int getPvpKills() {
        return this.pvpKills;
    }
    
    public void gainPvpKill() {
        ++this.pvpKills;
        ++this.pvpVictory;
        if (this.pvpVictory == 5) {
            this.map.broadcastMessage(MaplePacketCreator.serverNotice(6, "[Pvp] 玩家 " + this.getName() + " 已经达到 5 连斩。"));
        }
        else if (this.pvpVictory == 10) {
            this.client.getChannelServer().broadcastMessage(MaplePacketCreator.serverNotice(6, "[Pvp] 玩家 " + this.getName() + " 已经达到 10 连斩。"));
        }
        else if (this.pvpVictory >= 20) {
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[Pvp] 玩家 " + this.getName() + " 已经达到 " + this.pvpVictory + " 连斩。他(她)在频道 " + this.client.getChannel() + " 地图 " + this.map.getMapName() + " 中喊道谁能赐我一死."));
        }
        else {
            this.dropMessage(6, "当前: " + this.pvpVictory + " 连斩.");
        }
    }
    
    public int getPvpDeaths() {
        return this.pvpDeaths;
    }
    
    public void gainPvpDeath() {
        ++this.pvpDeaths;
        this.pvpVictory = 0;
    }
    
    public int getPvpVictory() {
        return this.pvpVictory;
    }
    
    public int getMerchantMeso() {
        int mesos = 0;
        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * from hiredmerch where characterid = ?");
            ps.setInt(1, this.id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mesos = rs.getInt("Mesos");
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            System.err.println("获取雇佣商店金币发生错误" + se);
        }
        return mesos;
    }
    
    public boolean canExpiration(final long now) {
        return this.lastExpirationTime > 0L && this.lastExpirationTime + 60000L < now;
    }
    
    public void startCheck() {
        final String mac = this.client.getMac();
        if (!this.client.isBanndMac2(mac) && this.client.getHandSome(this.client.getAccountName()) == this.client.getHandSome2()) {
            System.out.println("[作弊] 检测到玩家 " + this.getName() + " 登录器关闭，系统对其进行断开连接处理。");
            FileoutputUtil.packetLog("logs/防万能检测.txt", "玩家名称：" + this.getName() + " 账号在数据库的ID：" + this.getAccountID() + "检测到其与登录器断开连接。服务器对他执行断线处理。他的MAC地址：" + this.getClient().getMac() + "\r\n");
            this.sendPolice();
        }
        else if (this.client.getHandSome(this.client.getAccountName()) == 100) {
            System.out.println("[发现偷渡者] 检测到玩家 " + this.getName() + " 非法进入游戏");
            FileoutputUtil.packetLog("logs/防万能检测.txt", "玩家名称：" + this.getName() + " 账号在数据库的ID：" + this.getAccountID() + "检测到其非法进入游戏。服务器对他执行断线处理。他的MAC地址：" + this.getClient().getMac() + "\r\n");
            this.sendPolice();
        }
    }
    
    public void sendPolice() {
        this.client.getSession().write(MaplePacketCreator.serverNotice(1, "检测到登录器关闭，游戏即将断开。"));
        Timer.WorldTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                MapleCharacter.this.client.disconnect(true, false);
                if (MapleCharacter.this.client.getSession().isConnected()) {
                    MapleCharacter.this.client.getSession().close(true);
                }
                FileoutputUtil.packetLog("玩家被断开连接.txt", MapleCharacter.this.getName() + " 源代码 第8776行 原因：防万能检测到其与登陆器断开，服务器断开他的连接\r\n");
            }
        }, 6000L);
    }
    
    public int 获取怪物数量(final int mapId) {
        return this.client.getChannelServer().getMapFactory().getMap(mapId).getNumMonsters();
    }
    
    public void 刷新地图() {
        final boolean custMap = true;
        final int mapid = this.getMapId();
        final MapleMap map = custMap ? this.getClient().getChannelServer().getMapFactory().getMap(mapid) : this.getMap();
        if (this.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.getClient().getChannelServer().getMapFactory().getMap(mapid);
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
    
    public int 获取角色数量(final int mapid) {
        return this.client.getChannelServer().getMapFactory().getMap(mapid).getCharactersSize();
    }
    
    public void refreshPGDamage() {
        this.curPGDamage = (int)this.stats.getCurrentMaxBaseDamage();
        if (this.curPGDamage > this.getPGMaxDamage()) {
            this.setPGMaxDamage(this.curPGDamage);
        }
    }
    
    static {
        ariantroomleader = new String[3];
        ariantroomslot = new int[3];
        MapleCharacter.tutorial = false;
    }
    
    public enum FameStatus
    {
        OK, 
        NOT_TODAY, 
        NOT_THIS_MONTH
    }
}
