package tools.packet;

import client.ISkill;
import client.MapleCharacter;
import client.MapleCoolDownValueHolder;
import client.MapleQuestStatus;
import client.SkillEntry;
import client.inventory.IEquip;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import constants.GameConstants;
import constants.ServerConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import server.MapleItemInformationProvider;
import server.movement.LifeMovementFragment;
import server.shops.AbstractPlayerStore;
import server.shops.IMaplePlayerShop;
import tools.DateUtil;
import tools.KoreanDateUtil;
import tools.Pair;
import tools.data.output.LittleEndianWriter;
import tools.data.output.MaplePacketLittleEndianWriter;

public class PacketHelper
{
    private static final long FT_UT_OFFSET = 116444592000000000L;
    public static long MAX_TIME;
    public static byte[] unk1;
    public static byte[] unk2;
    
    public static long getKoreanTimestamp(final long realTimestamp) {
        if (realTimestamp == -1L) {
            return PacketHelper.MAX_TIME;
        }
        final long time = realTimestamp / 1000L / 60L;
        return time * 600000000L + 116444592000000000L;
    }
    
    public static long getTime(final long realTimestamp) {
        if (realTimestamp == -1L) {
            return PacketHelper.MAX_TIME;
        }
        final long time = realTimestamp / 1000L;
        return time * 10000000L + 116444592000000000L;
    }
    
    public static long getFileTimestamp(long timeStampinMillis, final boolean roundToMinutes) {
        if (TimeZone.getDefault().inDaylightTime(new Date())) {
            timeStampinMillis -= 3600000L;
        }
        long time;
        if (roundToMinutes) {
            time = timeStampinMillis / 1000L / 60L * 600000000L;
        }
        else {
            time = timeStampinMillis * 10000L;
        }
        return time + 116444592000000000L;
    }
    
    public static void addQuestInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        final List<MapleQuestStatus> started = chr.getStartedQuests();
        mplew.writeShort(started.size());
        for (final MapleQuestStatus q : started) {
            mplew.writeShort(q.getQuest().getId());
            mplew.writeMapleAsciiString((q.getCustomData() != null) ? q.getCustomData() : "");
        }
        final List<MapleQuestStatus> completed = chr.getCompletedQuests();
        mplew.writeShort(completed.size());
        for (final MapleQuestStatus q2 : completed) {
            mplew.writeShort(q2.getQuest().getId());
            final int time = KoreanDateUtil.getQuestTimestamp(q2.getCompletionTime());
            mplew.writeLong(time);
        }
    }
    
    public static void addSkillInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        final Map<ISkill, SkillEntry> skills = chr.getSkills();
        mplew.writeShort(skills.size());
        for (final Map.Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            mplew.writeInt(skill.getKey().getId());
            mplew.writeInt(skill.getValue().skillevel);
            if (skill.getKey().isFourthJob()) {
                mplew.writeInt(skill.getValue().masterlevel);
            }
        }
    }
    
    public static void addCoolDownInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        final List<MapleCoolDownValueHolder> cd = chr.getCooldowns();
        mplew.writeShort(cd.size());
        for (final MapleCoolDownValueHolder cooling : cd) {
            mplew.writeInt(cooling.skillId);
            mplew.writeShort((int)(cooling.length + cooling.startTime - System.currentTimeMillis()) / 1000);
        }
    }
    
    public static void addRocksInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        final int[] mapz = chr.getRegRocks();
        for (int i = 0; i < 5; ++i) {
            mplew.writeInt(mapz[i]);
        }
        final int[] map = chr.getRocks();
        for (int j = 0; j < 10; ++j) {
            mplew.writeInt(map[j]);
        }
    }
    
    public static void addMonsterBookInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeInt(chr.getMonsterBookCover());
        mplew.write(0);
        chr.getMonsterBook().addCardPacket(mplew);
    }
    
    public static void addRingInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeShort(0);
        final Pair<List<MapleRing>, List<MapleRing>> aRing = chr.getRings(true);
        final List<MapleRing> cRing = aRing.getLeft();
        mplew.writeShort(cRing.size());
        for (final MapleRing ring : cRing) {
            mplew.writeInt(ring.getPartnerChrId());
            mplew.writeAsciiString(ring.getPartnerName(), 13);
            mplew.writeLong(ring.getRingId());
            mplew.writeLong(ring.getPartnerRingId());
        }
        final List<MapleRing> fRing = aRing.getRight();
        mplew.writeShort(fRing.size());
        for (final MapleRing ring2 : fRing) {
            mplew.writeInt(ring2.getPartnerChrId());
            mplew.writeAsciiString(ring2.getPartnerName(), 13);
            mplew.writeLong(ring2.getRingId());
            mplew.writeLong(ring2.getPartnerRingId());
            mplew.writeInt(ring2.getItemId());
        }
        mplew.writeShort(0);
    }
    
    public static void addInventoryInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeMapleAsciiString(chr.getName());
        mplew.writeInt(chr.getMeso());
        mplew.writeInt(chr.getId());
        mplew.writeInt(chr.getBeans());
        mplew.writeInt(0);
        mplew.write(chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit());
        if (ServerConstants.调试输出封包) {
            System.out.println("-------背包装备格子数据输出：" + chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit());
        }
        mplew.write(chr.getInventory(MapleInventoryType.USE).getSlotLimit());
        if (ServerConstants.调试输出封包) {
            System.out.println("-------背包消耗格子数据输出：" + chr.getInventory(MapleInventoryType.USE).getSlotLimit());
        }
        mplew.write(chr.getInventory(MapleInventoryType.SETUP).getSlotLimit());
        if (ServerConstants.调试输出封包) {
            System.out.println("-------背包特殊格子数据输出：" + chr.getInventory(MapleInventoryType.SETUP).getSlotLimit());
        }
        mplew.write(chr.getInventory(MapleInventoryType.ETC).getSlotLimit());
        if (ServerConstants.调试输出封包) {
            System.out.println("-------背包其他格子数据输出：" + chr.getInventory(MapleInventoryType.ETC).getSlotLimit());
        }
        mplew.write(chr.getInventory(MapleInventoryType.CASH).getSlotLimit());
        if (ServerConstants.调试输出封包) {
            System.out.println("-------背包现金格子数据输出：" + chr.getInventory(MapleInventoryType.CASH).getSlotLimit());
        }
        mplew.writeLong(getTime(System.currentTimeMillis()));
        MapleInventory iv = chr.getInventory(MapleInventoryType.EQUIPPED);
        final Collection<IItem> equippedC = iv.list();
        final List<Item> equipped = new ArrayList<Item>(equippedC.size());
        for (final IItem item : equippedC) {
            equipped.add((Item)item);
        }
        Collections.sort(equipped);
        for (final Item item2 : equipped) {
            if (item2.getPosition() < 0 && item2.getPosition() > -100) {
                addItemInfo(mplew, item2, false, false);
            }
        }
        mplew.write(0);
        for (final Item item2 : equipped) {
            if (item2.getPosition() <= -100 && item2.getPosition() > -1000) {
                addItemInfo(mplew, item2, false, false);
            }
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.EQUIP);
        for (final IItem item : iv.list()) {
            addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.USE);
        for (final IItem item : iv.list()) {
            addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.SETUP);
        for (final IItem item : iv.list()) {
            addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.ETC);
        for (final IItem item : iv.list()) {
            addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.CASH);
        for (final IItem item : iv.list()) {
            addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
    }
    
    public static void addCharStats(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeInt(chr.getId());
        mplew.writeAsciiString(chr.getName(), 13);
        mplew.write(chr.getGender());
        mplew.write(chr.getSkinColor());
        mplew.writeInt(chr.getFace());
        mplew.writeInt(chr.getHair());
        mplew.writeZeroBytes(24);
        mplew.write(chr.getLevel());
        mplew.writeShort(chr.getJob());
        chr.getStat().connectData(mplew);
        mplew.writeShort(chr.getRemainingAp());
        mplew.writeShort(chr.getRemainingSp());
        mplew.writeInt(chr.getExp());
        mplew.writeShort(chr.getFame());
        mplew.writeInt(0);
        mplew.writeLong(getTime(System.currentTimeMillis()));
        mplew.writeInt(chr.getMapId());
        mplew.write(chr.getInitialSpawnpoint());
    }
    
    public static void addCharLook(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr, final boolean mega) {
        addCharLook(mplew, chr, mega, true);
    }
    
    public static void addCharLook(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr, final boolean mega, final boolean channelserver) {
        mplew.write(chr.getGender());
        mplew.write(chr.getSkinColor());
        mplew.writeInt(chr.getFace());
        mplew.write(mega ? 0 : 1);
        mplew.writeInt(chr.getHair());
        final Map<Byte, Integer> myEquip = new LinkedHashMap<Byte, Integer>();
        final Map<Byte, Integer> maskedEquip = new LinkedHashMap<Byte, Integer>();
        final MapleInventory equip = chr.getInventory(MapleInventoryType.EQUIPPED);
        for (final IItem item : equip.list()) {
            if (item.getPosition() < -128) {
                continue;
            }
            byte pos = (byte)(item.getPosition() * -1);
            if (pos < 100 && myEquip.get(pos) == null) {
                myEquip.put(pos, item.getItemId());
            }
            else if ((pos > 100 || pos == -128) && pos != 111) {
                pos = (byte)((pos == -128) ? 28 : (pos - 100));
                if (myEquip.get(pos) != null) {
                    maskedEquip.put(pos, myEquip.get(pos));
                }
                myEquip.put(pos, item.getItemId());
            }
            else {
                if (myEquip.get(pos) == null) {
                    continue;
                }
                maskedEquip.put(pos, item.getItemId());
            }
        }
        for (final Map.Entry<Byte, Integer> entry : myEquip.entrySet()) {
            mplew.write(entry.getKey());
            mplew.writeInt(entry.getValue());
        }
        mplew.write(255);
        for (final Map.Entry<Byte, Integer> entry : maskedEquip.entrySet()) {
            mplew.write(entry.getKey());
            mplew.writeInt(entry.getValue());
        }
        mplew.write(255);
        final IItem cWeapon = equip.getItem((short)(-111));
        mplew.writeInt((cWeapon != null) ? cWeapon.getItemId() : 0);
        for (int i = 0; i < 3; ++i) {
            if (channelserver) {
                mplew.writeInt((chr.getPet(i) != null) ? chr.getPet(i).getPetItemId() : 0);
            }
            else {
                mplew.writeInt(0);
            }
        }
    }
    
    public static void addExpirationTime(final MaplePacketLittleEndianWriter mplew, final long time) {
        mplew.write(0);
        mplew.writeShort(1408);
        if (time != -1L) {
            mplew.writeInt(KoreanDateUtil.getItemTimestamp(time));
            mplew.write(1);
        }
        else {
            mplew.writeInt(400967355);
            mplew.write(2);
        }
    }
    
    public static void addDDItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final boolean zeroPosition, final boolean leaveOut, final boolean cs) {
        short pos = item.getPosition();
        if (zeroPosition) {
            if (!leaveOut) {
                mplew.write(0);
            }
        }
        else if (pos <= -1) {
            pos = (byte)(pos * -1);
            if (pos > 100) {
                mplew.write(pos - 100);
            }
            else {
                mplew.write(pos);
            }
        }
        else {
            mplew.write(item.getPosition());
        }
        mplew.write((byte)((item.getPet() != null) ? 3 : item.getType()));
        mplew.writeInt(item.getItemId());
        final boolean hasUniqueId = item.getUniqueId() > 0;
        mplew.write(hasUniqueId ? 1 : 0);
        if (hasUniqueId) {
            mplew.writeLong(item.getUniqueId());
        }
        addExpirationTime(mplew, item.getExpiration());
        if (item.getType() == 1) {
            final IEquip equip = (IEquip)item;
            mplew.write(equip.getUpgradeSlots());
            mplew.write(equip.getLevel());
            mplew.writeShort(equip.getStr());
            mplew.writeShort(equip.getDex());
            mplew.writeShort(equip.getInt());
            mplew.writeShort(equip.getLuk());
            mplew.writeShort(equip.getHp());
            mplew.writeShort(equip.getMp());
            mplew.writeShort(equip.getWatk());
            mplew.writeShort(equip.getMatk());
            mplew.writeShort(equip.getWdef());
            mplew.writeShort(equip.getMdef());
            mplew.writeShort(equip.getAcc());
            mplew.writeShort(equip.getAvoid());
            mplew.writeShort(equip.getHands());
            mplew.writeShort(equip.getSpeed());
            mplew.writeShort(equip.getJump());
            mplew.writeMapleAsciiString(equip.getOwner());
            mplew.writeShort(equip.getFlag());
            mplew.write(0);
            mplew.write(0);
            mplew.writeShort(0);
            mplew.writeShort(0);
            mplew.write(0);
            mplew.write(0);
            mplew.writeLong(0L);
            mplew.writeShort(0);
            mplew.writeShort(0);
            mplew.writeShort(0);
            mplew.writeLong(DateUtil.getFileTimestamp(System.currentTimeMillis()));
            mplew.writeInt(-1);
        }
        else {
            mplew.writeShort(item.getQuantity());
            mplew.writeMapleAsciiString(item.getOwner());
            mplew.writeShort(0);
            if (GameConstants.is飞镖道具(item.getItemId()) || GameConstants.is子弹道具(item.getItemId())) {
                mplew.writeInt(2);
                mplew.writeShort(84);
                mplew.write(0);
                mplew.write(52);
            }
        }
    }
    
    public static void addItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final boolean zeroPosition, final boolean leaveOut) {
        addItemInfo(mplew, item, zeroPosition, leaveOut, false);
    }
    
    public static void addItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final boolean zeroPosition, final boolean leaveOut, final boolean trade) {
        short pos = item.getPosition();
        if (zeroPosition) {
            if (!leaveOut) {
                mplew.write(0);
            }
        }
        else if (pos <= -1) {
            pos = (byte)(pos * -1);
            if (pos == -128) {
                mplew.write(28);
            }
            else if (pos > 100) {
                mplew.write(pos - 100);
            }
            else {
                mplew.write(pos);
            }
        }
        else {
            mplew.write(item.getPosition());
        }
        mplew.write((byte)((item.getPet() != null) ? 3 : item.getType()));
        mplew.writeInt(item.getItemId());
        final boolean hasUniqueId = item.getUniqueId() > 0;
        mplew.write(hasUniqueId ? 1 : 0);
        if (hasUniqueId) {
            mplew.writeLong(item.getUniqueId());
        }
        if (item.getPet() != null) {
            addPetItemInfo(mplew, item, item.getPet(), true);
        }
        else {
            addExpirationTime(mplew, item.getExpiration());
            if (item.getType() == 1) {
                final IEquip equip = (IEquip)item;
                mplew.write(equip.getUpgradeSlots());
                mplew.write(equip.getLevel());
                mplew.writeShort(equip.getStr());
                mplew.writeShort(equip.getDex());
                mplew.writeShort(equip.getInt());
                mplew.writeShort(equip.getLuk());
                mplew.writeShort(equip.getHp());
                mplew.writeShort(equip.getMp());
                mplew.writeShort(equip.getWatk());
                mplew.writeShort(equip.getMatk());
                mplew.writeShort(equip.getWdef());
                mplew.writeShort(equip.getMdef());
                mplew.writeShort(equip.getAcc());
                mplew.writeShort(equip.getAvoid());
                mplew.writeShort(equip.getHands());
                mplew.writeShort(equip.getSpeed());
                mplew.writeShort(equip.getJump());
                mplew.writeMapleAsciiString(equip.getOwner());
                mplew.writeShort(equip.getFlag());
                if (!hasUniqueId) {
                    mplew.write(0);
                    mplew.write(Math.max(equip.getBaseLevel(), equip.getEquipLevel()));
                    mplew.writeInt(equip.getExpPercentage());
                    mplew.writeInt(equip.getViciousHammer());
                    mplew.writeLong(0L);
                }
                else {
                    mplew.writeShort(0);
                    mplew.writeShort(0);
                    mplew.writeShort(0);
                    mplew.writeShort(0);
                    mplew.writeShort(0);
                }
                if (GameConstants.is豆豆装备(equip.getItemId())) {
                    mplew.writeInt(0);
                    mplew.writeLong(DateUtil.getFileTimestamp(System.currentTimeMillis()));
                }
                else {
                    addExpirationTime(mplew, item.getExpiration());
                }
                mplew.writeInt(-1);
            }
            else {
                mplew.writeShort(item.getQuantity());
                mplew.writeMapleAsciiString(item.getOwner());
                mplew.writeShort(item.getFlag());
                if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
                    mplew.writeInt(2);
                    mplew.writeShort(84);
                    mplew.write(0);
                    mplew.write(52);
                }
            }
        }
    }
    
    public static void serializeMovementList(final LittleEndianWriter lew, final List<LifeMovementFragment> moves) {
        lew.write(moves.size());
        for (final LifeMovementFragment move : moves) {
            move.serialize(lew);
        }
    }
    
    public static void addAnnounceBox(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        if (chr.getPlayerShop() != null && chr.getPlayerShop().isOwner(chr) && chr.getPlayerShop().getShopType() != 1 && chr.getPlayerShop().isAvailable()) {
            addInteraction(mplew, chr.getPlayerShop());
        }
        else {
            mplew.write(0);
        }
    }
    
    public static void addInteraction(final MaplePacketLittleEndianWriter mplew, final IMaplePlayerShop shop) {
        mplew.write(shop.getGameType());
        mplew.writeInt(((AbstractPlayerStore)shop).getObjectId());
        mplew.writeMapleAsciiString(shop.getDescription());
        if (shop.getShopType() != 1) {
            mplew.write((shop.getPassword().length() > 0) ? 1 : 0);
        }
        mplew.write(shop.getItemId() % 10);
        mplew.write(shop.getSize());
        mplew.write(shop.getMaxSize());
        if (shop.getShopType() != 1) {
            mplew.write(shop.isOpen() ? 0 : 1);
        }
    }
    
    public static void addCharacterInfo(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeLong(-1L);
        mplew.write(0);
        addCharStats(mplew, chr);
        mplew.write(chr.getBuddylist().getCapacity());
        mplew.write(1);
        addInventoryInfo(mplew, chr);
        addSkillInfo(mplew, chr);
        addCoolDownInfo(mplew, chr);
        addQuestInfo(mplew, chr);
        addRingInfo(mplew, chr);
        addRocksInfo(mplew, chr);
        addMonsterBookInfo(mplew, chr);
        chr.QuestInfoPacket(mplew);
        mplew.writeInt(0);
        mplew.writeShort(0);
    }
    
    public static void addPetItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final MaplePet pet, final boolean active) {
        if (item == null) {
            mplew.writeLong(getKoreanTimestamp((long)(System.currentTimeMillis() * 1.5)));
        }
        else {
            addExpirationTime(mplew, (item.getExpiration() <= System.currentTimeMillis()) ? -1L : item.getExpiration());
        }
        mplew.writeAsciiString(pet.getName(), 13);
        mplew.write(pet.getLevel());
        mplew.writeShort(pet.getCloseness());
        mplew.write(pet.getFullness());
        if (item == null) {
            mplew.writeLong(getKoreanTimestamp((long)(System.currentTimeMillis() * 1.5)));
        }
        else {
            addExpirationTime(mplew, (item.getExpiration() <= System.currentTimeMillis()) ? -1L : item.getExpiration());
        }
        mplew.writeShort(0);
        mplew.writeShort(pet.getFlags());
        mplew.writeInt((pet.getPetItemId() == 5000054 && pet.getSecondsLeft() > 0) ? pet.getSecondsLeft() : 0);
        mplew.write(0);
        mplew.write((byte)(active ? (pet.getSummoned() ? pet.getSummonedValue() : 0) : 0));
    }
    
    private static void addRingItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final boolean zeroPosition, final boolean leaveOut, final boolean cs) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        boolean ring = false;
        IEquip equip = null;
        if (item.getType() == 1) {
            equip = (IEquip)item;
            if (equip.getRing() != null) {
                ring = true;
            }
        }
        short pos = item.getPosition();
        boolean masking = false;
        boolean equipped = false;
        if (zeroPosition) {
            if (!leaveOut) {
                mplew.write(0);
            }
        }
        else if (pos <= -1) {
            pos *= -1;
            if (pos > 100 || pos == -128 || ring) {
                masking = true;
                mplew.write(pos - 100);
            }
            else {
                mplew.write(pos);
            }
            equipped = true;
        }
        else {
            mplew.write(item.getPosition());
        }
        mplew.write(item.getType());
        mplew.writeInt(item.getItemId());
        mplew.write(1);
        mplew.writeInt(equip.getUniqueId());
        mplew.writeInt(0);
        mplew.writeLong(DateUtil.getFileTimestamp(item.getExpiration()));
        mplew.write(equip.getUpgradeSlots());
        mplew.write(equip.getLevel());
        mplew.writeShort(equip.getStr());
        mplew.writeShort(equip.getDex());
        mplew.writeShort(equip.getInt());
        mplew.writeShort(equip.getLuk());
        mplew.writeShort(equip.getHp());
        mplew.writeShort(equip.getMp());
        mplew.writeShort(equip.getWatk());
        mplew.writeShort(equip.getMatk());
        mplew.writeShort(equip.getWdef());
        mplew.writeShort(equip.getMdef());
        mplew.writeShort(equip.getAcc());
        mplew.writeShort(equip.getAvoid());
        mplew.writeShort(equip.getHands());
        mplew.writeShort(equip.getSpeed());
        mplew.writeShort(equip.getJump());
        mplew.writeMapleAsciiString(equip.getOwner());
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.write(1);
        mplew.write(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeLong(DateUtil.getFileTimestamp(System.currentTimeMillis()));
        mplew.writeInt(-1);
    }
    
    static {
        PacketHelper.MAX_TIME = 150842304000000000L;
        PacketHelper.unk1 = new byte[] { 0, 64, -32, -3 };
        PacketHelper.unk2 = new byte[] { 59, 55, 79, 1 };
    }
}
