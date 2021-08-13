package handling.world;

import client.BuddyEntry;
import client.ISkill;
import client.MapleCharacter;
import client.MapleQuestStatus;
import client.SkillEntry;
import client.inventory.MapleMount;
import client.inventory.MaplePet;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import server.quest.MapleQuest;
import tools.Pair;

public class CharacterTransfer implements Externalizable
{
    public int characterid;
    public int accountid;
    public int exp;
    public int shaguai;
    public int skillzq;
    public int bosslog;
    public int PGMaxDamage;
    public int jzname;
    public int mrsjrw;
    public int mrsgrw;
    public int mrsbossrw;
    public int mrfbrw;
    public int hythd;
    public int mrsgrwa;
    public int mrsbossrwa;
    public int mrfbrwa;
    public int mrsgrws;
    public int mrsbossrws;
    public int mrfbrws;
    public int mrsgrwas;
    public int mrsbossrwas;
    public int mrfbrwas;
    public int ddj;
    public int vip;
    public long vipexpired;
    public int djjl;
    public int qiandao;
    public int jf;
    public int beans;
    public int meso;
    public int hair;
    public int face;
    public int mapid;
    public int guildid;
    public int partyid;
    public int messengerid;
    public int mBookCover;
    public int dojo;
    public int ACash;
    public int MaplePoints;
    public int mount_itemid;
    public int mount_exp;
    public int points;
    public int vpoints;
    public int marriageId;
    public int familyid;
    public int seniorid;
    public int junior1;
    public int junior2;
    public int currentrep;
    public int totalrep;
    public int expression;
    public int constellation;
    public int blood;
    public int month;
    public int day;
    public int battleshipHP;
    public int prefix;
    public byte channel;
    public byte dojoRecord;
    public byte gender;
    public byte gmLevel;
    public byte guildrank;
    public byte alliancerank;
    public byte clonez;
    public byte fairyExp;
    public byte buddysize;
    public byte world;
    public byte initialSpawnPoint;
    public byte skinColor;
    public byte mount_level;
    public byte mount_Fatigue;
    public byte subcategory;
    public long lastfametime;
    public long TranferTime;
    public long lastGainHM;
    public String tempIP;
    public String name;
    public String accountname;
    public String BlessOfFairy;
    public String chalkboard;
    public String charmessage;
    public short level;
    public short fame;
    public short str;
    public short dex;
    public short int_;
    public short luk;
    public short maxhp;
    public short maxmp;
    public short hp;
    public short mp;
    public short remainingAp;
    public short hpApUsed;
    public short job;
    public Object inventorys;
    public Object skillmacro;
    public Object storage;
    public Object cs;
    public int[] savedlocation;
    public int[] wishlist;
    public int[] rocks;
    public int[] remainingSp;
    public int[] regrocks;
    public byte[] petStore;
    public Object pvpStats;
    public int pvpDeaths;
    public int pvpKills;
    public int pvpVictory;
    public boolean DebugMessage;
    public Map<Integer, Integer> mbook;
    public Map<Integer, Pair<Byte, Integer>> keymap;
    public List<Integer> finishedAchievements;
    public List<Integer> famedcharacters;
    public Map<BuddyEntry, Boolean> buddies;
    public Map<Integer, Object> Quest;
    public Map<Integer, String> InfoQuest;
    public Map<Integer, SkillEntry> Skills;
    
    public CharacterTransfer() {
        this.mbook = new LinkedHashMap<Integer, Integer>();
        this.keymap = new LinkedHashMap<Integer, Pair<Byte, Integer>>();
        this.finishedAchievements = new ArrayList<Integer>();
        this.famedcharacters = new ArrayList<Integer>();
        this.buddies = new LinkedHashMap<BuddyEntry, Boolean>();
        this.Quest = new LinkedHashMap<Integer, Object>();
        this.InfoQuest = new LinkedHashMap<Integer, String>();
        this.Skills = new LinkedHashMap<Integer, SkillEntry>();
    }
    
    public CharacterTransfer(final MapleCharacter chr) {
        this.mbook = new LinkedHashMap<Integer, Integer>();
        this.keymap = new LinkedHashMap<Integer, Pair<Byte, Integer>>();
        this.finishedAchievements = new ArrayList<Integer>();
        this.famedcharacters = new ArrayList<Integer>();
        this.buddies = new LinkedHashMap<BuddyEntry, Boolean>();
        this.Quest = new LinkedHashMap<Integer, Object>();
        this.InfoQuest = new LinkedHashMap<Integer, String>();
        this.Skills = new LinkedHashMap<Integer, SkillEntry>();
        this.lastGainHM = chr.getLastHM();
        this.DebugMessage = chr.getDebugMessage();
        this.characterid = chr.getId();
        this.accountid = chr.getAccountID();
        this.accountname = chr.getClient().getAccountName();
        this.channel = (byte)chr.getClient().getChannel();
        this.ACash = chr.getCSPoints(1);
        this.MaplePoints = chr.getCSPoints(2);
        this.vpoints = chr.getVPoints();
        this.name = chr.getName();
        this.fame = chr.getFame();
        this.gender = chr.getGender();
        this.level = chr.getLevel();
        this.str = chr.getStat().getStr();
        this.dex = chr.getStat().getDex();
        this.int_ = chr.getStat().getInt();
        this.luk = chr.getStat().getLuk();
        this.hp = chr.getStat().getHp();
        this.mp = chr.getStat().getMp();
        this.maxhp = chr.getStat().getMaxHp();
        this.maxmp = chr.getStat().getMaxMp();
        this.exp = chr.getExp();
        this.hpApUsed = chr.getHpApUsed();
        this.remainingAp = chr.getRemainingAp();
        this.remainingSp = chr.getRemainingSps();
        this.beans = chr.getBeans();
        this.meso = chr.getMeso();
        this.skinColor = chr.getSkinColor();
        this.job = chr.getJob();
        this.hair = chr.getHair();
        this.face = chr.getFace();
        this.mapid = chr.getMapId();
        this.initialSpawnPoint = chr.getInitialSpawnpoint();
        this.marriageId = chr.getMarriageId();
        this.world = chr.getWorld();
        this.guildid = chr.getGuildId();
        this.guildrank = chr.getGuildRank();
        this.alliancerank = chr.getAllianceRank();
        this.gmLevel = (byte)chr.getGMLevel();
        this.points = chr.getPoints();
        this.fairyExp = chr.getFairyExp();
        this.clonez = chr.getNumClones();
        this.petStore = chr.getPetStores();
        this.subcategory = chr.getSubcategory();
        this.currentrep = chr.getCurrentRep();
        this.totalrep = chr.getTotalRep();
        this.familyid = chr.getFamilyId();
        this.seniorid = chr.getSeniorId();
        this.junior1 = chr.getJunior1();
        this.junior2 = chr.getJunior2();
        this.charmessage = chr.getcharmessage();
        this.expression = chr.getexpression();
        this.constellation = chr.getconstellation();
        this.skillzq = chr.getskillzq();
        this.bosslog = chr.getbosslog();
        this.PGMaxDamage = chr.getPGMaxDamage();
        this.jzname = chr.getjzname();
        this.mrfbrw = chr.getmrfbrw();
        this.mrsbossrw = chr.getmrsbossrw();
        this.mrsgrw = chr.getmrsgrw();
        this.mrsjrw = chr.getmrsjrw();
        this.hythd = chr.gethythd();
        this.mrfbrwa = chr.getmrfbrwa();
        this.mrsbossrwa = chr.getmrsbossrwa();
        this.mrsgrwa = chr.getmrsgrwa();
        this.mrfbrwas = chr.getmrfbrwas();
        this.vip = chr.getvip();
        this.vipexpired = chr.getVipexpired();
        this.mrsbossrwas = chr.getmrsbossrwas();
        this.mrsgrwas = chr.getmrsgrwas();
        this.mrfbrws = chr.getmrfbrws();
        this.mrsbossrws = chr.getmrsbossrws();
        this.mrsgrws = chr.getmrsgrws();
        this.ddj = chr.getddj();
        this.djjl = chr.getdjjl();
        this.qiandao = chr.getqiandao();
        this.jf = chr.getjf();
        this.blood = chr.getblood();
        this.month = chr.getmonth();
        this.day = chr.getday();
        this.battleshipHP = chr.currentBattleshipHP();
        this.prefix = chr.getPrefix();
        this.pvpDeaths = chr.getPvpDeaths();
        this.pvpKills = chr.getPvpKills();
        this.pvpVictory = chr.getPvpVictory();
        this.shaguai = chr.getSG();
        boolean uneq = false;
        for (int i = 0; i < this.petStore.length; ++i) {
            final MaplePet pet = chr.getPet(i);
            if (this.petStore[i] == 0) {
                this.petStore[i] = -1;
            }
            if (pet != null) {
                uneq = true;
                this.petStore[i] = (byte)Math.max(this.petStore[i], pet.getInventoryPosition());
            }
        }
        if (uneq) {
            chr.unequipAllPets();
        }
        for (final BuddyEntry qs : chr.getBuddylist().getBuddies()) {
            this.buddies.put(qs, qs.isVisible());
        }
        this.buddysize = chr.getBuddyCapacity();
        this.partyid = chr.getPartyId();
        if (chr.getMessenger() != null) {
            this.messengerid = chr.getMessenger().getId();
        }
        else {
            this.messengerid = 0;
        }
        this.mBookCover = chr.getMonsterBookCover();
        this.dojo = chr.getDojo();
        this.dojoRecord = (byte)chr.getDojoRecord();
        this.InfoQuest = chr.getInfoQuest_Map();
        for (final Map.Entry<MapleQuest, MapleQuestStatus> qs2 : chr.getQuest_Map().entrySet()) {
            this.Quest.put(qs2.getKey().getId(), qs2.getValue());
        }
        this.mbook = chr.getMonsterBook().getCards();
        this.inventorys = chr.getInventorys();
        for (final Map.Entry<ISkill, SkillEntry> qs3 : chr.getSkills().entrySet()) {
            this.Skills.put(qs3.getKey().getId(), qs3.getValue());
        }
        this.BlessOfFairy = chr.getBlessOfFairyOrigin();
        this.chalkboard = chr.getChalkboard();
        this.skillmacro = chr.getMacros();
        this.tempIP = chr.getClient().getTempIP();
        this.keymap = chr.getKeyLayout().Layout();
        this.savedlocation = chr.getSavedLocations();
        this.wishlist = chr.getWishlist();
        this.rocks = chr.getRocks();
        this.regrocks = chr.getRegRocks();
        for (final Integer zz : chr.getFamedCharacters()) {
            this.famedcharacters.add(zz);
        }
        this.lastfametime = chr.getLastFameTime();
        this.storage = chr.getStorage();
        this.cs = chr.getCashInventory();
        this.pvpStats = chr.getPvpStats();
        final MapleMount mount = chr.getMount();
        this.mount_itemid = mount.getItemId();
        this.mount_Fatigue = mount.getFatigue();
        this.mount_level = mount.getLevel();
        this.mount_exp = mount.getExp();
        this.TranferTime = System.currentTimeMillis();
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.lastGainHM = in.readLong();
        this.DebugMessage = in.readBoolean();
        this.characterid = in.readInt();
        this.accountid = in.readInt();
        this.accountname = in.readUTF();
        this.channel = in.readByte();
        this.ACash = in.readInt();
        this.MaplePoints = in.readInt();
        this.name = in.readUTF();
        this.fame = in.readShort();
        this.gender = in.readByte();
        this.level = in.readShort();
        this.str = in.readShort();
        this.dex = in.readShort();
        this.int_ = in.readShort();
        this.luk = in.readShort();
        this.hp = in.readShort();
        this.mp = in.readShort();
        this.maxhp = in.readShort();
        this.maxmp = in.readShort();
        this.exp = in.readInt();
        this.hpApUsed = in.readShort();
        this.remainingAp = in.readShort();
        this.remainingSp = new int[in.readByte()];
        for (int i = 0; i < this.remainingSp.length; ++i) {
            this.remainingSp[i] = in.readInt();
        }
        this.beans = in.readInt();
        this.meso = in.readInt();
        this.skinColor = in.readByte();
        this.job = in.readShort();
        this.hair = in.readInt();
        this.face = in.readInt();
        this.mapid = in.readInt();
        this.initialSpawnPoint = in.readByte();
        this.world = in.readByte();
        this.guildid = in.readInt();
        this.guildrank = in.readByte();
        this.alliancerank = in.readByte();
        this.gmLevel = in.readByte();
        this.points = in.readInt();
        this.vpoints = in.readInt();
        if (in.readByte() == 1) {
            this.BlessOfFairy = in.readUTF();
        }
        else {
            this.BlessOfFairy = null;
        }
        if (in.readByte() == 1) {
            this.chalkboard = in.readUTF();
        }
        else {
            this.chalkboard = null;
        }
        this.clonez = in.readByte();
        this.skillmacro = in.readObject();
        this.lastfametime = in.readLong();
        this.storage = in.readObject();
        this.cs = in.readObject();
        this.mount_itemid = in.readInt();
        this.mount_Fatigue = in.readByte();
        this.mount_level = in.readByte();
        this.mount_exp = in.readInt();
        this.partyid = in.readInt();
        this.messengerid = in.readInt();
        this.mBookCover = in.readInt();
        this.dojo = in.readInt();
        this.dojoRecord = in.readByte();
        this.inventorys = in.readObject();
        this.fairyExp = in.readByte();
        this.subcategory = in.readByte();
        this.marriageId = in.readInt();
        this.familyid = in.readInt();
        this.seniorid = in.readInt();
        this.junior1 = in.readInt();
        this.junior2 = in.readInt();
        this.currentrep = in.readInt();
        this.totalrep = in.readInt();
        this.charmessage = in.readUTF();
        this.expression = in.readByte();
        this.constellation = in.readInt();
        this.skillzq = in.readInt();
        this.bosslog = in.readInt();
        this.PGMaxDamage = in.readInt();
        this.jzname = in.readInt();
        this.mrfbrw = in.readInt();
        this.mrsbossrw = in.readInt();
        this.mrsgrw = in.readInt();
        this.mrsjrw = in.readInt();
        this.hythd = in.readInt();
        this.mrfbrwa = in.readInt();
        this.mrsbossrwa = in.readInt();
        this.mrsgrwa = in.readInt();
        this.mrfbrwas = in.readInt();
        this.mrsbossrwas = in.readInt();
        this.mrsgrwas = in.readInt();
        this.mrfbrws = in.readInt();
        this.mrsbossrws = in.readInt();
        this.mrsgrws = in.readInt();
        this.ddj = in.readInt();
        this.vip = in.readInt();
        this.vipexpired = in.readLong();
        this.djjl = in.readInt();
        this.qiandao = in.readInt();
        this.jf = in.readInt();
        this.blood = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.battleshipHP = in.readInt();
        this.prefix = in.readInt();
        this.tempIP = in.readUTF();
        this.shaguai = in.readInt();
        for (int mbooksize = in.readShort(), j = 0; j < mbooksize; ++j) {
            this.mbook.put(in.readInt(), in.readInt());
        }
        for (int skillsize = in.readShort(), k = 0; k < skillsize; ++k) {
            this.Skills.put(in.readInt(), new SkillEntry(in.readByte(), in.readByte(), in.readLong()));
        }
        this.buddysize = in.readByte();
        final short addedbuddysize = in.readShort();
        for (int l = 0; l < addedbuddysize; ++l) {
            this.buddies.put(new BuddyEntry(in.readUTF(), in.readInt(), in.readUTF(), in.readInt(), in.readBoolean(), in.readInt(), in.readInt()), in.readBoolean());
        }
        for (int questsize = in.readShort(), m = 0; m < questsize; ++m) {
            this.Quest.put(in.readInt(), in.readObject());
        }
        for (int achievesize = in.readShort(), i2 = 0; i2 < achievesize; ++i2) {
            this.finishedAchievements.add(in.readInt());
        }
        for (int famesize = in.readInt(), i3 = 0; i3 < famesize; ++i3) {
            this.famedcharacters.add(in.readInt());
        }
        final int savesize = in.readShort();
        this.savedlocation = new int[savesize];
        for (int i4 = 0; i4 < savesize; ++i4) {
            this.savedlocation[i4] = in.readInt();
        }
        final int wsize = in.readShort();
        this.wishlist = new int[wsize];
        for (int i5 = 0; i5 < wsize; ++i5) {
            this.wishlist[i5] = in.readInt();
        }
        final int rsize = in.readShort();
        this.rocks = new int[rsize];
        for (int i6 = 0; i6 < rsize; ++i6) {
            this.rocks[i6] = in.readInt();
        }
        final int resize = in.readShort();
        this.regrocks = new int[resize];
        for (int i7 = 0; i7 < resize; ++i7) {
            this.regrocks[i7] = in.readInt();
        }
        for (int infosize = in.readShort(), i8 = 0; i8 < infosize; ++i8) {
            this.InfoQuest.put(in.readInt(), in.readUTF());
        }
        for (int keysize = in.readInt(), i9 = 0; i9 < keysize; ++i9) {
            this.keymap.put(in.readInt(), new Pair<Byte, Integer>(in.readByte(), in.readInt()));
        }
        this.petStore = new byte[in.readByte()];
        for (int i9 = 0; i9 < 3; ++i9) {
            this.petStore[i9] = in.readByte();
        }
        this.TranferTime = System.currentTimeMillis();
    }
    
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeLong(this.lastGainHM);
        out.writeBoolean(this.DebugMessage);
        out.writeInt(this.characterid);
        out.writeInt(this.accountid);
        out.writeUTF(this.accountname);
        out.writeByte(this.channel);
        out.writeInt(this.ACash);
        out.writeInt(this.MaplePoints);
        out.writeUTF(this.name);
        out.writeShort(this.fame);
        out.writeByte(this.gender);
        out.writeShort(this.level);
        out.writeShort(this.str);
        out.writeShort(this.dex);
        out.writeShort(this.int_);
        out.writeShort(this.luk);
        out.writeShort(this.hp);
        out.writeShort(this.mp);
        out.writeShort(this.maxhp);
        out.writeShort(this.maxmp);
        out.writeInt(this.exp);
        out.writeShort(this.hpApUsed);
        out.writeShort(this.remainingAp);
        out.writeByte(this.remainingSp.length);
        for (int i = 0; i < this.remainingSp.length; ++i) {
            out.writeInt(this.remainingSp[i]);
        }
        out.writeInt(this.beans);
        out.writeInt(this.meso);
        out.writeByte(this.skinColor);
        out.writeShort(this.job);
        out.writeInt(this.hair);
        out.writeInt(this.face);
        out.writeInt(this.mapid);
        out.writeByte(this.initialSpawnPoint);
        out.writeByte(this.world);
        out.writeInt(this.guildid);
        out.writeByte(this.guildrank);
        out.writeByte(this.alliancerank);
        out.writeByte(this.gmLevel);
        out.writeInt(this.points);
        out.writeInt(this.vpoints);
        out.writeByte((this.BlessOfFairy != null) ? 1 : 0);
        if (this.BlessOfFairy != null) {
            out.writeUTF(this.BlessOfFairy);
        }
        out.writeByte((this.chalkboard != null) ? 1 : 0);
        if (this.chalkboard != null) {
            out.writeUTF(this.chalkboard);
        }
        out.writeByte(this.clonez);
        out.writeObject(this.skillmacro);
        out.writeLong(this.lastfametime);
        out.writeObject(this.storage);
        out.writeObject(this.cs);
        out.writeInt(this.mount_itemid);
        out.writeByte(this.mount_Fatigue);
        out.writeByte(this.mount_level);
        out.writeInt(this.mount_exp);
        out.writeInt(this.partyid);
        out.writeInt(this.messengerid);
        out.writeInt(this.mBookCover);
        out.writeInt(this.dojo);
        out.writeByte(this.dojoRecord);
        out.writeObject(this.inventorys);
        out.writeByte(this.fairyExp);
        out.writeByte(this.subcategory);
        out.writeInt(this.marriageId);
        out.writeUTF(this.tempIP);
        out.writeInt(this.familyid);
        out.writeInt(this.seniorid);
        out.writeInt(this.junior1);
        out.writeInt(this.junior2);
        out.writeInt(this.currentrep);
        out.writeInt(this.totalrep);
        out.writeInt(this.battleshipHP);
        out.writeUTF(this.charmessage);
        out.writeInt(this.expression);
        out.writeInt(this.constellation);
        out.writeInt(this.skillzq);
        out.writeInt(this.bosslog);
        out.writeInt(this.PGMaxDamage);
        out.writeInt(this.jzname);
        out.writeInt(this.mrfbrw);
        out.writeInt(this.mrsbossrw);
        out.writeInt(this.mrsgrw);
        out.writeInt(this.mrsjrw);
        out.writeInt(this.mrfbrwa);
        out.writeInt(this.mrsbossrwa);
        out.writeInt(this.mrsgrwa);
        out.writeInt(this.mrfbrwas);
        out.writeInt(this.mrsbossrwas);
        out.writeInt(this.mrsgrwas);
        out.writeInt(this.mrfbrws);
        out.writeInt(this.mrsbossrws);
        out.writeInt(this.mrsgrws);
        out.writeInt(this.hythd);
        out.writeInt(this.ddj);
        out.writeInt(this.djjl);
        out.writeInt(this.qiandao);
        out.writeInt(this.jf);
        out.writeInt(this.vip);
        out.writeLong(this.vipexpired);
        out.writeInt(this.blood);
        out.writeInt(this.month);
        out.writeInt(this.day);
        out.writeInt(this.prefix);
        out.writeInt(this.shaguai);
        out.writeShort(this.mbook.size());
        for (final Map.Entry<Integer, Integer> ms : this.mbook.entrySet()) {
            out.writeInt(ms.getKey());
            out.writeInt(ms.getValue());
        }
        out.writeShort(this.Skills.size());
        for (final Map.Entry<Integer, SkillEntry> qs : this.Skills.entrySet()) {
            out.writeInt(qs.getKey());
            out.writeByte(qs.getValue().skillevel);
            out.writeByte(qs.getValue().masterlevel);
            out.writeLong(qs.getValue().expiration);
        }
        out.writeByte(this.buddysize);
        out.writeShort(this.buddies.size());
        for (final Map.Entry<BuddyEntry, Boolean> qs2 : this.buddies.entrySet()) {
            out.writeUTF(qs2.getKey().getName());
            out.writeInt(qs2.getKey().getCharacterId());
            out.writeUTF(qs2.getKey().getGroup());
            out.writeInt(qs2.getKey().getChannel());
            out.writeBoolean(qs2.getValue());
            out.writeInt(qs2.getKey().getLevel());
            out.writeInt(qs2.getKey().getJob());
            out.writeBoolean(qs2.getValue());
        }
        out.writeShort(this.Quest.size());
        for (final Map.Entry<Integer, Object> qs3 : this.Quest.entrySet()) {
            out.writeInt(qs3.getKey());
            out.writeObject(qs3.getValue());
        }
        out.writeShort(this.finishedAchievements.size());
        for (final Integer zz : this.finishedAchievements) {
            out.writeInt(zz);
        }
        out.writeInt(this.famedcharacters.size());
        for (final Integer zz : this.famedcharacters) {
            out.writeInt(zz);
        }
        out.writeShort(this.savedlocation.length);
        for (final int zz2 : this.savedlocation) {
            out.writeInt(zz2);
        }
        out.writeShort(this.wishlist.length);
        for (final int zz2 : this.wishlist) {
            out.writeInt(zz2);
        }
        out.writeShort(this.rocks.length);
        for (final int zz2 : this.rocks) {
            out.writeInt(zz2);
        }
        out.writeShort(this.regrocks.length);
        for (final int zz2 : this.regrocks) {
            out.writeInt(zz2);
        }
        out.writeShort(this.InfoQuest.size());
        for (final Map.Entry<Integer, String> qs4 : this.InfoQuest.entrySet()) {
            out.writeInt(qs4.getKey());
            out.writeUTF(qs4.getValue());
        }
        out.writeInt(this.keymap.size());
        for (final Map.Entry<Integer, Pair<Byte, Integer>> qs5 : this.keymap.entrySet()) {
            out.writeInt(qs5.getKey());
            out.writeByte(qs5.getValue().left);
            out.writeInt(qs5.getValue().right);
        }
        out.writeByte(this.petStore.length);
        for (int i = 0; i < this.petStore.length; ++i) {
            out.writeByte(this.petStore[i]);
        }
    }
}
