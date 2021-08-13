package tools.packet;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.SkillEntry;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import constants.ServerConstants;
import database.DatabaseConnection;
import handling.MaplePacket;
import handling.SendPacketOpcode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import server.CashItemFactory;
import server.CashItemInfo;
import server.CashShop;
import server.MTSStorage;
import tools.HexTool;
import tools.KoreanDateUtil;
import tools.Pair;
import tools.data.output.MaplePacketLittleEndianWriter;

public class MTSCSPacket
{
    private static byte[] warpCS;
    private static byte[] CHAR_INFO_MAGIC;
    
    public static MaplePacket warpCS(final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        final MapleCharacter chr = c.getPlayer();
        if (ServerConstants.调试输出封包) {
            System.out.println("warpCS--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPEN.getValue());
        mplew.writeLong(-1L);
        mplew.write(0);
        PacketHelper.addCharStats(mplew, chr);
        mplew.write(20);
        mplew.write(0);
        mplew.writeInt(chr.getMeso());
        mplew.writeInt(chr.getId());
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.USE).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.SETUP).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.ETC).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.CASH).getSlotLimit());
        mplew.writeLong(PacketHelper.getTime(System.currentTimeMillis()));
        MapleInventory iv = chr.getInventory(MapleInventoryType.EQUIPPED);
        final Collection<IItem> equippedC = iv.list();
        final List<Item> equipped = new ArrayList<Item>(equippedC.size());
        for (final IItem item : equippedC) {
            equipped.add((Item)item);
        }
        Collections.sort(equipped);
        for (final Item item2 : equipped) {
            if (item2.getPosition() < 0 && item2.getPosition() > -100) {
                PacketHelper.addItemInfo(mplew, item2, false, false);
            }
        }
        mplew.write(0);
        for (final Item item2 : equipped) {
            if (item2.getPosition() <= -100 && item2.getPosition() > -1000) {
                PacketHelper.addItemInfo(mplew, item2, false, false);
            }
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.EQUIP);
        for (final IItem item : iv.list()) {
            PacketHelper.addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.USE);
        for (final IItem item : iv.list()) {
            PacketHelper.addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.SETUP);
        for (final IItem item : iv.list()) {
            PacketHelper.addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.ETC);
        for (final IItem item : iv.list()) {
            PacketHelper.addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        iv = chr.getInventory(MapleInventoryType.CASH);
        for (final IItem item : iv.list()) {
            PacketHelper.addItemInfo(mplew, item, false, false);
        }
        mplew.write(0);
        final Map<ISkill, SkillEntry> skills = chr.getSkills();
        mplew.writeShort(skills.size());
        for (final Map.Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            mplew.writeInt(skill.getKey().getId());
            mplew.writeInt(skill.getValue().skillevel);
            if (skill.getKey().isFourthJob()) {
                mplew.writeInt(skill.getValue().masterlevel);
            }
        }
        mplew.writeShort(0);
        mplew.writeShort(3);
        mplew.writeInt(662990);
        mplew.write(HexTool.getByteArrayFromHexString("5A 5A 5A 5A 45 46 47 48 49 5A B0 1D 01 00 34 95 08 00 00 11 00 F0 03 00 4D CE 43 44 63 CA 01 08 04 00 05 05 49 70 FD C9 01 F1 03 00 07 0B 20 44 63 CA 01 09 04 00 E6 FA 4E 70 FD C9 01 F2 03 80 F7 05 23 44 63 CA 01 0A 04 00 F4 21 56 70 FD C9 01 F3 03 80 51 68 25 44 63 CA 01 0B 04 00 F0 C7 CB E1 00 CA 01 39 20 00 7D C1 6D C3 5A CA 01 F4 03 80 AB CA 27 44 63 CA 01 0C 04 00 DD 95 0A 44 63 CA 01 FC 03 80 6A FA 47 44 63 CA 01 B0 1D 80 28 6F 6F 80 63 CA 01 F5 03 00 9C C5 2A 44 63 CA 01 F6 03 00 F6 27 2D 44 63 CA 01 98 12 80 C4 5C 4A 44 63 CA 01 F7 03 80 40 85 32 44 63 CA 01"));
        mplew.writeLong(0L);
        for (int i = 0; i < 15; ++i) {
            mplew.write(MTSCSPacket.CHAR_INFO_MAGIC);
        }
        mplew.write(HexTool.getByteArrayFromHexString("00 00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
        mplew.writeMapleAsciiString(chr.getClient().getAccountName());
        mplew.write(HexTool.getByteArrayFromHexString("46 00 00 00 07 A5 9B 00 08 A5 9B 00 09 A5 9B 00 0A A5 9B 00 0B A5 9B 00 0C A5 9B 00 0D A5 9B 00 0E A5 9B 00 0F A5 9B 00 10 A5 9B 00 11 A5 9B 00 12 A5 9B 00 13 A5 9B 00 14 A5 9B 00 15 A5 9B 00 16 A5 9B 00 17 A5 9B 00 18 A5 9B 00 19 A5 9B 00 1A A5 9B 00 1B A5 9B 00 1C A5 9B 00 1D A5 9B 00 1E A5 9B 00 1F A5 9B 00 20 A5 9B 00 21 A5 9B 00 22 A5 9B 00 23 A5 9B 00 24 A5 9B 00 25 A5 9B 00 26 A5 9B 00 27 A5 9B 00 28 A5 9B 00 29 A5 9B 00 2A A5 9B 00 2B A5 9B 00 2C A5 9B 00 2D A5 9B 00 2E A5 9B 00 2F A5 9B 00 30 A5 9B 00 31 A5 9B 00 32 A5 9B 00 33 A5 9B 00 34 A5 9B 00 35 A5 9B 00 36 A5 9B 00 37 A5 9B 00 38 A5 9B 00 39 A5 9B 00 3A A5 9B 00 3B A5 9B 00 3C A5 9B 00 3D A5 9B 00 3E A5 9B 00 3F A5 9B 00 40 A5 9B 00 41 A5 9B 00 42 A5 9B 00 43 A5 9B 00 44 A5 9B 00 45 A5 9B 00 46 A5 9B 00 47 A5 9B 00 48 A5 9B 00 49 A5 9B 00 4A A5 9B 00 4B A5 9B 00 4C A5 9B 00"));
        mplew.writeShort(0);
        mplew.writeZeroBytes(123);
        final int[] itemz = CashItemFactory.getInstance().getBestItems();
        for (int j = 1; j <= 8; ++j) {
            for (int k = 0; k <= 1; ++k) {
                for (int item3 = 0; item3 < itemz.length; ++item3) {
                    mplew.writeInt(j);
                    mplew.writeInt(k);
                    mplew.writeInt(itemz[item3]);
                }
            }
        }
        mplew.write(HexTool.getByteArrayFromHexString("00 00 00 00 00"));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR("warpCS-201：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket warpCSS(final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        final MapleCharacter chr = c.getPlayer();
        if (ServerConstants.调试输出封包) {
            System.out.println("warpCS--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPEN.getValue());
        mplew.writeLong(-1L);
        mplew.write(0);
        PacketHelper.addCharStats(mplew, chr);
        mplew.write(20);
        mplew.write(0);
        mplew.writeInt(0);
        mplew.writeInt(chr.getId());
        mplew.writeInt(0);
        mplew.writeInt(0);
        mplew.write(chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.USE).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.SETUP).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.ETC).getSlotLimit());
        mplew.write(chr.getInventory(MapleInventoryType.CASH).getSlotLimit());
        mplew.writeLong(0L);
        mplew.write(0);
        mplew.write(0);
        mplew.write(0);
        mplew.write(0);
        mplew.write(0);
        mplew.write(0);
        mplew.write(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(3);
        mplew.writeInt(662990);
        mplew.write(HexTool.getByteArrayFromHexString("5A 5A 5A 5A 45 46 47 48 49 5A B0 1D 01 00 34 95 08 00 00 11 00 F0 03 00 4D CE 43 44 63 CA 01 08 04 00 05 05 49 70 FD C9 01 F1 03 00 07 0B 20 44 63 CA 01 09 04 00 E6 FA 4E 70 FD C9 01 F2 03 80 F7 05 23 44 63 CA 01 0A 04 00 F4 21 56 70 FD C9 01 F3 03 80 51 68 25 44 63 CA 01 0B 04 00 F0 C7 CB E1 00 CA 01 39 20 00 7D C1 6D C3 5A CA 01 F4 03 80 AB CA 27 44 63 CA 01 0C 04 00 DD 95 0A 44 63 CA 01 FC 03 80 6A FA 47 44 63 CA 01 B0 1D 80 28 6F 6F 80 63 CA 01 F5 03 00 9C C5 2A 44 63 CA 01 F6 03 00 F6 27 2D 44 63 CA 01 98 12 80 C4 5C 4A 44 63 CA 01 F7 03 80 40 85 32 44 63 CA 01"));
        mplew.writeLong(0L);
        for (int i = 0; i < 15; ++i) {
            mplew.write(MTSCSPacket.CHAR_INFO_MAGIC);
        }
        mplew.write(HexTool.getByteArrayFromHexString("00 00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
        mplew.writeMapleAsciiString(chr.getClient().getAccountName());
        mplew.write(HexTool.getByteArrayFromHexString("46 00 00 00 07 A5 9B 00 08 A5 9B 00 09 A5 9B 00 0A A5 9B 00 0B A5 9B 00 0C A5 9B 00 0D A5 9B 00 0E A5 9B 00 0F A5 9B 00 10 A5 9B 00 11 A5 9B 00 12 A5 9B 00 13 A5 9B 00 14 A5 9B 00 15 A5 9B 00 16 A5 9B 00 17 A5 9B 00 18 A5 9B 00 19 A5 9B 00 1A A5 9B 00 1B A5 9B 00 1C A5 9B 00 1D A5 9B 00 1E A5 9B 00 1F A5 9B 00 20 A5 9B 00 21 A5 9B 00 22 A5 9B 00 23 A5 9B 00 24 A5 9B 00 25 A5 9B 00 26 A5 9B 00 27 A5 9B 00 28 A5 9B 00 29 A5 9B 00 2A A5 9B 00 2B A5 9B 00 2C A5 9B 00 2D A5 9B 00 2E A5 9B 00 2F A5 9B 00 30 A5 9B 00 31 A5 9B 00 32 A5 9B 00 33 A5 9B 00 34 A5 9B 00 35 A5 9B 00 36 A5 9B 00 37 A5 9B 00 38 A5 9B 00 39 A5 9B 00 3A A5 9B 00 3B A5 9B 00 3C A5 9B 00 3D A5 9B 00 3E A5 9B 00 3F A5 9B 00 40 A5 9B 00 41 A5 9B 00 42 A5 9B 00 43 A5 9B 00 44 A5 9B 00 45 A5 9B 00 46 A5 9B 00 47 A5 9B 00 48 A5 9B 00 49 A5 9B 00 4A A5 9B 00 4B A5 9B 00 4C A5 9B 00"));
        final Collection<CashItemInfo.CashModInfo> cmi = CashItemFactory.getInstance().getAllModInfo();
        mplew.writeShort(cmi.size());
        for (final CashItemInfo.CashModInfo cm : cmi) {
            addModCashItemInfo(mplew, cm);
        }
        mplew.writeZeroBytes(123);
        final int[] itemz = CashItemFactory.getInstance().getBestItems();
        for (int j = 1; j <= 8; ++j) {
            for (int k = 0; k <= 1; ++k) {
                for (int item = 0; item < itemz.length; ++item) {
                    mplew.writeInt(j);
                    mplew.writeInt(k);
                    mplew.writeInt(itemz[item]);
                }
            }
        }
        mplew.write(HexTool.getByteArrayFromHexString("00 00 00 00 00"));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static void addModCashItemInfo(final MaplePacketLittleEndianWriter mplew, final CashItemInfo.CashModInfo item) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addModCashItemInfo--------------------");
        }
        final int flags = item.flags;
        mplew.writeInt(item.sn);
        mplew.writeInt(flags);
        if ((flags & 0x1) != 0x0) {
            mplew.writeInt(item.itemid);
        }
        if ((flags & 0x2) != 0x0) {
            mplew.writeShort(item.count);
        }
        if ((flags & 0x10) != 0x0) {
            mplew.write(item.priority);
        }
        if ((flags & 0x4) != 0x0) {
            mplew.writeInt(item.discountPrice);
        }
        if ((flags & 0x20) != 0x0) {
            mplew.writeShort(item.period);
        }
        if ((flags & 0x200) != 0x0) {
            mplew.write(item.gender);
        }
        if ((flags & 0x400) != 0x0) {
            mplew.write(item.showUp ? 1 : 0);
        }
        if ((flags & 0x800) != 0x0) {
            mplew.write(item.mark);
        }
        if ((flags & 0x10000) != 0x0) {
            final List<CashItemInfo> pack = CashItemFactory.getInstance().getPackageItems(item.sn);
            if (pack == null) {
                mplew.write(0);
            }
            else {
                mplew.write(pack.size());
                for (int i = 0; i < pack.size(); ++i) {
                    mplew.writeInt(pack.get(i).getSN());
                }
            }
        }
    }
    
    public static MaplePacket sendBlockedMessage(final int type) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendBlockedMessage--------------------");
        }
        mplew.writeShort(SendPacketOpcode.BLOCK_MSG.getValue());
        mplew.write(type);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket playCashSong(final int itemid, final String name) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("playCashSong--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CASH_SONG.getValue());
        mplew.writeInt(itemid);
        mplew.writeMapleAsciiString(name);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket show塔罗牌(final String name, final String otherName, final int love, final int cardId, final int commentId) {
        if (ServerConstants.调试输出封包) {
            System.out.println("playCashSong--------------------");
        }
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.SHOW_PREDICT_CARD.getValue());
        mplew.writeMapleAsciiString(name);
        mplew.writeMapleAsciiString(otherName);
        mplew.writeInt(love);
        mplew.writeInt(cardId);
        mplew.writeInt(commentId);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket useCharm(final byte charmsleft, final byte daysleft) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("useCharm--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        mplew.write(6);
        mplew.write(1);
        mplew.write(charmsleft);
        mplew.write(daysleft);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket useWheel(final int charmsleft) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("useWheel--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        mplew.write(21);
        mplew.writeLong(charmsleft);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket itemExpired(final int itemid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("itemExpired--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_STATUS_INFO.getValue());
        mplew.write(2);
        mplew.writeInt(itemid);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket ViciousHammer(final boolean start, final int hammered) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("ViciousHammer--------------------");
        }
        mplew.writeShort(SendPacketOpcode.VICIOUS_HAMMER.getValue());
        if (start) {
            mplew.write(49);
            mplew.writeInt(0);
            mplew.writeInt(hammered);
        }
        else {
            mplew.write(53);
            mplew.writeInt(0);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket changePetFlag(final int uniqueId, final boolean added, final int flagAdded) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("changePetFlag--------------------");
        }
        mplew.writeShort(SendPacketOpcode.PET_FLAG_CHANGE.getValue());
        mplew.writeLong(uniqueId);
        mplew.write(added ? 1 : 0);
        mplew.writeShort(flagAdded);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket changePetName(final MapleCharacter chr, final String newname, final int slot) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("changePetName--------------------");
        }
        mplew.writeShort(SendPacketOpcode.PET_NAMECHANGE.getValue());
        mplew.writeInt(chr.getId());
        mplew.write(0);
        mplew.writeMapleAsciiString(newname);
        mplew.write(slot);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showNotes(final ResultSet notes, final int count) throws SQLException {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showNotes--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SHOW_NOTES.getValue());
        mplew.write(3);
        mplew.write(count);
        for (int i = 0; i < count; ++i) {
            mplew.writeInt(notes.getInt("id"));
            mplew.writeMapleAsciiString(notes.getString("from"));
            mplew.writeMapleAsciiString(notes.getString("message"));
            mplew.writeLong(PacketHelper.getKoreanTimestamp(notes.getLong("timestamp")));
            mplew.write(notes.getInt("gift"));
            notes.next();
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket useChalkboard(final int charid, final String msg) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("useChalkboard--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CHALKBOARD.getValue());
        mplew.writeInt(charid);
        if (msg == null || msg.length() <= 0) {
            mplew.write(0);
        }
        else {
            mplew.write(1);
            mplew.writeMapleAsciiString(msg);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getTrockRefresh(final MapleCharacter chr, final boolean vip, final boolean delete) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getTrockRefresh--------------------");
        }
        mplew.writeShort(SendPacketOpcode.TROCK_LOCATIONS.getValue());
        mplew.write(delete ? 2 : 3);
        mplew.write(vip ? 1 : 0);
        if (vip) {
            final int[] map = chr.getRocks();
            for (int i = 0; i < 10; ++i) {
                mplew.writeInt(map[i]);
            }
        }
        else {
            final int[] map = chr.getRegRocks();
            for (int i = 0; i < 5; ++i) {
                mplew.writeInt(map[i]);
            }
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendWishList(final MapleCharacter chr, final boolean update) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendWishList--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(70);
        final Connection con = DatabaseConnection.getConnection();
        int i = 10;
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT sn FROM wishlist WHERE characterid = ? LIMIT 10");
            ps.setInt(1, chr.getAccountID());
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mplew.writeInt(rs.getInt("sn"));
                --i;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            System.out.println("Error getting wishlist data:" + se);
        }
        while (i > 0) {
            mplew.writeInt(0);
            --i;
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showCashInventory(final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(66);
        final CashShop mci = c.getPlayer().getCashInventory();
        mplew.writeShort(mci.getItemsSize());
        for (final IItem itemz : mci.getInventory()) {
            addCashItemInfo(mplew, itemz, c.getAccID(), 0);
        }
        mplew.writeShort(c.getPlayer().getStorage().getSlots());
        mplew.writeShort(c.getCharacterSlots());
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showNXMapleTokens(final MapleCharacter chr) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showNXMapleTokens--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_UPDATE.getValue());
        mplew.writeInt(chr.getCSPoints(1));
        mplew.writeInt(chr.getCSPoints(2));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showBoughtCSPackage(final Map<Integer, IItem> ccc, final int accid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showBoughtCSPackage--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(126);
        mplew.write(ccc.size());
        for (final Map.Entry<Integer, IItem> sn : ccc.entrySet()) {
            addCashItemInfo(mplew, sn.getValue(), accid, sn.getKey());
        }
        mplew.writeShort(1);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showBoughtCSItem(final int itemid, final int sn, final int uniqueid, final int accid, final int quantity, final String giftFrom, final long expire) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showBoughtCSItemA--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(76);
        addCashItemInfo(mplew, uniqueid, accid, itemid, sn, quantity, giftFrom, expire);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showBoughtCSItem(final IItem item, final int sn, final int accid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showBoughtCSItemB--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(76);
        addCashItemInfo(mplew, item, accid, sn);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static void addCashItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final int accId, final int sn) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addCashItemInfoA--------------------");
        }
        addCashItemInfo(mplew, item, accId, sn, true);
    }
    
    public static void addCashItemInfo(final MaplePacketLittleEndianWriter mplew, final IItem item, final int accId, final int sn, final boolean isFirst) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addCashItemInfoB--------------------");
        }
        addCashItemInfo(mplew, item.getUniqueId(), accId, item.getItemId(), sn, item.getQuantity(), item.getGiftFrom(), item.getExpiration(), isFirst);
    }
    
    public static void addCashItemInfo(final MaplePacketLittleEndianWriter mplew, final int uniqueid, final int accId, final int itemid, final int sn, final int quantity, final String sender, final long expire) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addCashItemInfoC--------------------");
        }
        addCashItemInfo(mplew, uniqueid, accId, itemid, sn, quantity, sender, expire, true);
    }
    
    public static void addCashItemInfo(final MaplePacketLittleEndianWriter mplew, final int uniqueid, final int accId, final int itemid, final int sn, final int quantity, final String sender, final long expire, final boolean isFirst) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addCashItemInfoD--------------------");
        }
        mplew.writeLong((uniqueid > 0) ? ((long)uniqueid) : 0L);
        mplew.writeLong(accId);
        mplew.writeInt(itemid);
        mplew.writeInt(isFirst ? sn : 0);
        mplew.writeShort(quantity);
        mplew.writeAsciiString(sender, 13);
        PacketHelper.addExpirationTime(mplew, expire);
        mplew.writeLong(0L);
    }
    
    public static MaplePacket showBoughtCSQuestItem(final int price, final short quantity, final byte position, final int itemid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showBoughtCSQuestItem--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(111);
        mplew.writeInt(price);
        mplew.writeShort(quantity);
        mplew.writeShort(position);
        mplew.writeInt(itemid);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendCSFail(final int err) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendCSFail--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(106);
        mplew.write(err);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showCouponRedeemedItem(final int itemid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showCouponRedeemedItemA--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.writeShort(60);
        mplew.writeInt(0);
        mplew.writeInt(1);
        mplew.writeShort(1);
        mplew.writeShort(26);
        mplew.writeInt(itemid);
        mplew.writeInt(0);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showCouponRedeemedItem(final Map<Integer, IItem> items, final int mesos, final int maplePoints, final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showCouponRedeemedItemB--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(60);
        mplew.write(items.size());
        for (final Map.Entry<Integer, IItem> item : items.entrySet()) {
            addCashItemInfo(mplew, item.getValue(), c.getAccID(), item.getKey());
        }
        mplew.writeLong(maplePoints);
        mplew.writeInt(mesos);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket enableCSorMTS() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("enableCSorMTS--------------------");
        }
        mplew.write(HexTool.getByteArrayFromHexString("15 00 01 00 00 00 00"));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket enableCSUse() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("enableCSUse--------------------");
        }
        mplew.writeShort(18);
        mplew.writeInt(0);
        mplew.writeShort(0);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getCSInventory(final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(66);
        final CashShop mci = c.getPlayer().getCashInventory();
        mplew.writeShort(mci.getItemsSize());
        for (final IItem itemz : mci.getInventory()) {
            Integer sn = null;
            try {
                sn = CashItemFactory.getInstance().getSnFromId(itemz.getItemId());
            }
            catch (Exception ex) {}
            mplew.writeLong(itemz.getUniqueId());
            mplew.writeLong(c.getAccID());
            mplew.writeInt(itemz.getItemId());
            mplew.writeInt((sn == null) ? 0 : ((int)sn));
            mplew.writeShort(itemz.getQuantity());
            mplew.writeAsciiString(itemz.getGiftFrom());
            for (int i = itemz.getGiftFrom().getBytes().length; i < 13; ++i) {
                mplew.write(0);
            }
            PacketHelper.addExpirationTime(mplew, itemz.getExpiration());
            mplew.writeLong(0L);
        }
        mplew.writeShort(4);
        mplew.writeShort(3);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getCSGifts(final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getCSGifts--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(68);
        final List<Pair<IItem, String>> mci = c.getPlayer().getCashInventory().loadGifts();
        mplew.writeShort(mci.size());
        for (final Pair<IItem, String> mcz : mci) {
            mplew.writeLong(mcz.getLeft().getUniqueId());
            mplew.writeInt(mcz.getLeft().getItemId());
            mplew.writeAsciiString(mcz.getLeft().getGiftFrom(), 13);
            mplew.writeAsciiString(mcz.getRight(), 73);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket cashItemExpired(final int uniqueid) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("cashItemExpired--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(82);
        mplew.writeLong(uniqueid);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendGift(final int itemid, final int quantity, final String receiver) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendGift--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(83);
        mplew.writeMapleAsciiString(receiver);
        mplew.writeInt(itemid);
        mplew.writeShort(quantity);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket increasedInvSlots(final int inv, final int slots) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("increasedInvSlots--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(101);
        mplew.write(inv);
        mplew.writeShort(slots);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket increasedStorageSlots(final int slots) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("increasedStorageSlots--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(103);
        mplew.writeShort(slots);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket confirmToCSInventory(final IItem item, final int accId, final int sn) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("confirmToCSInventory--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(95);
        mplew.writeLong(item.getUniqueId());
        mplew.writeLong(accId);
        mplew.writeInt(item.getItemId());
        mplew.writeInt(sn);
        mplew.writeShort(item.getQuantity());
        mplew.writeAsciiString(item.getGiftFrom(), 13);
        PacketHelper.addExpirationTime(mplew, item.getExpiration());
        mplew.writeLong(0L);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket confirmFromCSInventory(final IItem item, final short pos) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("confirmFromCSInventory--------------------");
        }
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.write(93);
        mplew.writeShort(pos);
        PacketHelper.addItemInfo(mplew, item, true, true);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendMesobagFailed() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendMesobagFailed--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MESOBAG_FAILURE.getValue());
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendMesobagSuccess(final int mesos) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendMesobagSuccess--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MESOBAG_SUCCESS.getValue());
        mplew.writeInt(mesos);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket startMTS(final MapleCharacter chr, final MapleClient c) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("startMTS--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPEN.getValue());
        PacketHelper.addCharacterInfo(mplew, chr);
        mplew.writeMapleAsciiString(c.getAccountName());
        mplew.writeInt(ServerConstants.MTS_MESO);
        mplew.writeInt(ServerConstants.MTS_TAX);
        mplew.writeInt(ServerConstants.MTS_BASE);
        mplew.writeInt(24);
        mplew.writeInt(168);
        mplew.writeLong(PacketHelper.getTime(System.currentTimeMillis()));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendMTS(final List<MTSStorage.MTSItemInfo> items, final int tab, final int type, final int page, final int pages) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendMTS--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(21);
        mplew.writeInt(pages * 10);
        mplew.writeInt(items.size());
        mplew.writeInt(tab);
        mplew.writeInt(type);
        mplew.writeInt(page);
        mplew.write(1);
        mplew.write(1);
        for (final MTSStorage.MTSItemInfo item : items) {
            addMTSItemInfo(mplew, item);
        }
        mplew.write(1);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket showMTSCash(final MapleCharacter p) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("showMTSCash--------------------");
        }
        mplew.writeShort(SendPacketOpcode.GET_MTS_TOKENS.getValue());
        mplew.writeInt(p.getCSPoints(2));
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSWantedListingOver(final int nx, final int items) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSWantedListingOver--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(61);
        mplew.writeInt(nx);
        mplew.writeInt(items);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSConfirmSell() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSConfirmSell--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(29);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSFailSell() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSFailSell--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(30);
        mplew.write(66);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSConfirmBuy() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSConfirmBuy--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(51);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSFailBuy() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSFailBuy--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(52);
        mplew.write(66);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSConfirmCancel() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSConfirmCancel--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(37);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSFailCancel() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSFailCancel--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(38);
        mplew.write(66);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getMTSConfirmTransfer(final int quantity, final int pos) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getMTSConfirmTransfer--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(39);
        mplew.writeInt(quantity);
        mplew.writeInt(pos);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    private static void addMTSItemInfo(final MaplePacketLittleEndianWriter mplew, final MTSStorage.MTSItemInfo item) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addMTSItemInfo--------------------");
        }
        PacketHelper.addItemInfo(mplew, item.getItem(), true, true);
        mplew.writeInt(item.getId());
        mplew.writeInt(item.getTaxes());
        mplew.writeInt(item.getPrice());
        mplew.writeInt(0);
        mplew.writeInt(KoreanDateUtil.getQuestTimestamp(item.getEndingDate()));
        mplew.writeInt(KoreanDateUtil.getQuestTimestamp(item.getEndingDate()));
        mplew.writeMapleAsciiString(item.getSeller());
        mplew.writeMapleAsciiString(item.getSeller());
        mplew.writeZeroBytes(28);
    }
    
    public static MaplePacket getNotYetSoldInv(final List<MTSStorage.MTSItemInfo> items) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getNotYetSoldInv--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(35);
        mplew.writeInt(items.size());
        for (final MTSStorage.MTSItemInfo item : items) {
            addMTSItemInfo(mplew, item);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getTransferInventory(final List<IItem> items, final boolean changed) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getTransferInventory--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        mplew.write(33);
        mplew.writeInt(items.size());
        int i = 0;
        for (final IItem item : items) {
            PacketHelper.addItemInfo(mplew, item, true, true);
            mplew.writeInt(Integer.MAX_VALUE - i);
            mplew.writeInt(110);
            mplew.writeInt(1011);
            mplew.writeZeroBytes(48);
            ++i;
        }
        mplew.writeInt(-47 + i - 1);
        mplew.write(changed ? 1 : 0);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket addToCartMessage(final boolean fail, final boolean remove) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("addToCartMessage--------------------");
        }
        mplew.writeShort(SendPacketOpcode.MTS_OPERATION.getValue());
        if (remove) {
            if (fail) {
                mplew.write(44);
                mplew.writeInt(-1);
            }
            else {
                mplew.write(43);
            }
        }
        else if (fail) {
            mplew.write(42);
            mplew.writeInt(-1);
        }
        else {
            mplew.write(41);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket 商城送礼物(final int itemid, final int quantity, final String receiver) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.CS_OPERATION.getValue());
        mplew.writeMapleAsciiString(receiver);
        mplew.writeInt(itemid);
        mplew.writeShort(quantity);
        return mplew.getPacket();
    }
    
    static {
        MTSCSPacket.warpCS = HexTool.getByteArrayFromHexString("00 00 00 63 00 74 00 65 00 64 00 2F 00 32 00 00 00 00 00 02 00 11 00 AD 01 08 06 02 00 00 00 33 00 00 00 05 00 13 00 AF 00 08 06 A0 01 14 00 30 E1 7B 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 02 00 18 00 B2 01 08 06 02 00 00 00 33 00 00 00 08 00 1A 00 B4 00 0A 06 B8 01 14 00 A8 10 88 06 69 00 6C 00 6C 00 2F 00 39 00 30 00 30 00 31 00");
        MTSCSPacket.CHAR_INFO_MAGIC = new byte[] { -1, -55, -102, 59 };
    }
}
