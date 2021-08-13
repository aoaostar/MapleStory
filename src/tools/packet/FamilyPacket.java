package tools.packet;

import client.MapleCharacter;
import constants.ServerConstants;
import handling.MaplePacket;
import handling.SendPacketOpcode;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyBuff;
import handling.world.family.MapleFamilyCharacter;
import java.util.List;
import tools.Pair;
import tools.data.output.MaplePacketLittleEndianWriter;

public class FamilyPacket
{
    public static MaplePacket getFamilyData() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getFamilyData--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY.getValue());
        final List<MapleFamilyBuff.MapleFamilyBuffEntry> entries = MapleFamilyBuff.getBuffEntry();
        mplew.writeInt(entries.size());
        for (final MapleFamilyBuff.MapleFamilyBuffEntry entry : entries) {
            mplew.write(entry.type);
            mplew.writeInt(entry.rep * 100);
            mplew.writeInt(entry.count);
            mplew.writeMapleAsciiString(entry.name);
            mplew.writeMapleAsciiString(entry.desc);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket changeRep(final int r) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("changeRep--------------------");
        }
        mplew.writeShort(SendPacketOpcode.REP_INCREASE.getValue());
        mplew.writeInt(r);
        mplew.writeInt(0);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getFamilyInfo(final MapleCharacter chr) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getFamilyInfo--------------------");
        }
        mplew.writeShort(SendPacketOpcode.OPEN_FAMILY.getValue());
        mplew.writeInt(chr.getCurrentRep());
        mplew.writeInt(chr.getTotalRep());
        mplew.writeInt(chr.getTotalRep());
        mplew.writeShort(chr.getNoJuniors());
        mplew.writeShort(2);
        mplew.writeShort(chr.getNoJuniors());
        final MapleFamily family = World.Family.getFamily(chr.getFamilyId());
        if (family != null) {
            mplew.writeInt(family.getLeaderId());
            mplew.writeMapleAsciiString(family.getLeaderName());
            mplew.writeMapleAsciiString(family.getNotice());
        }
        else {
            mplew.writeLong(0L);
        }
        final List<Pair<Integer, Integer>> b = chr.usedBuffs();
        mplew.writeInt(b.size());
        for (final Pair<Integer, Integer> ii : b) {
            mplew.writeInt(ii.getLeft());
            mplew.writeInt(ii.getRight());
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static void addFamilyCharInfo(final MapleFamilyCharacter ldr, final MaplePacketLittleEndianWriter mplew) {
        if (ServerConstants.调试输出封包) {
            System.out.println("addFamilyCharInfo--------------------");
        }
        mplew.writeInt(ldr.getId());
        mplew.writeInt(ldr.getSeniorId());
        mplew.writeShort(ldr.getJobId());
        mplew.write(ldr.getLevel());
        mplew.write(ldr.isOnline() ? 1 : 0);
        mplew.writeInt(ldr.getCurrentRep());
        mplew.writeInt(ldr.getTotalRep());
        mplew.writeInt(ldr.getTotalRep());
        mplew.writeInt(ldr.getTotalRep());
        mplew.writeLong(Math.max(ldr.getChannel(), 0));
        mplew.writeMapleAsciiString(ldr.getName());
    }
    
    public static MaplePacket getFamilyPedigree(final MapleCharacter chr) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getFamilyPedigree--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SEND_PEDIGREE.getValue());
        mplew.writeInt(chr.getId());
        final MapleFamily family = World.Family.getFamily(chr.getFamilyId());
        int descendants = 2;
        int gens = 0;
        int generations = 0;
        if (family == null) {
            mplew.writeInt(2);
            addFamilyCharInfo(new MapleFamilyCharacter(chr, 0, 0, 0, 0), mplew);
        }
        else {
            mplew.writeInt(family.getMFC(chr.getId()).getPedigree().size() + 1);
            addFamilyCharInfo(family.getMFC(family.getLeaderId()), mplew);
            if (chr.getSeniorId() > 0) {
                final MapleFamilyCharacter senior = family.getMFC(chr.getSeniorId());
                if (senior.getSeniorId() > 0) {
                    addFamilyCharInfo(family.getMFC(senior.getSeniorId()), mplew);
                }
                addFamilyCharInfo(senior, mplew);
            }
        }
        addFamilyCharInfo((chr.getMFC() == null) ? new MapleFamilyCharacter(chr, 0, 0, 0, 0) : chr.getMFC(), mplew);
        if (family != null) {
            if (chr.getSeniorId() > 0) {
                final MapleFamilyCharacter senior = family.getMFC(chr.getSeniorId());
                if (senior != null) {
                    if (senior.getJunior1() > 0 && senior.getJunior1() != chr.getId()) {
                        addFamilyCharInfo(family.getMFC(senior.getJunior1()), mplew);
                    }
                    else if (senior.getJunior2() > 0 && senior.getJunior2() != chr.getId()) {
                        addFamilyCharInfo(family.getMFC(senior.getJunior2()), mplew);
                    }
                }
            }
            if (chr.getJunior1() > 0) {
                addFamilyCharInfo(family.getMFC(chr.getJunior1()), mplew);
            }
            if (chr.getJunior2() > 0) {
                addFamilyCharInfo(family.getMFC(chr.getJunior2()), mplew);
            }
            if (chr.getJunior1() > 0) {
                final MapleFamilyCharacter junior = family.getMFC(chr.getJunior1());
                if (junior.getJunior1() > 0) {
                    ++descendants;
                    addFamilyCharInfo(family.getMFC(junior.getJunior1()), mplew);
                }
                if (junior.getJunior2() > 0) {
                    ++descendants;
                    addFamilyCharInfo(family.getMFC(junior.getJunior2()), mplew);
                }
            }
            if (chr.getJunior2() > 0) {
                final MapleFamilyCharacter junior = family.getMFC(chr.getJunior2());
                if (junior.getJunior1() > 0) {
                    ++descendants;
                    addFamilyCharInfo(family.getMFC(junior.getJunior1()), mplew);
                }
                if (junior.getJunior2() > 0) {
                    ++descendants;
                    addFamilyCharInfo(family.getMFC(junior.getJunior2()), mplew);
                }
            }
            gens = family.getGens();
            generations = family.getMemberSize();
        }
        mplew.writeLong(descendants);
        mplew.writeInt(gens);
        mplew.writeInt(-1);
        mplew.writeInt(generations);
        if (family != null) {
            if (chr.getJunior1() > 0) {
                final MapleFamilyCharacter junior = family.getMFC(chr.getJunior1());
                if (junior.getJunior1() > 0) {
                    mplew.writeInt(junior.getJunior1());
                    mplew.writeInt(family.getMFC(junior.getJunior1()).getDescendants());
                }
                if (junior.getJunior2() > 0) {
                    mplew.writeInt(junior.getJunior2());
                    mplew.writeInt(family.getMFC(junior.getJunior2()).getDescendants());
                }
            }
            if (chr.getJunior2() > 0) {
                final MapleFamilyCharacter junior = family.getMFC(chr.getJunior2());
                if (junior.getJunior1() > 0) {
                    mplew.writeInt(junior.getJunior1());
                    mplew.writeInt(family.getMFC(junior.getJunior1()).getDescendants());
                }
                if (junior.getJunior2() > 0) {
                    mplew.writeInt(junior.getJunior2());
                    mplew.writeInt(family.getMFC(junior.getJunior2()).getDescendants());
                }
            }
        }
        final List<Pair<Integer, Integer>> b = chr.usedBuffs();
        mplew.writeInt(b.size());
        for (final Pair<Integer, Integer> ii : b) {
            mplew.writeInt(ii.getLeft());
            mplew.writeInt(ii.getRight());
        }
        mplew.writeShort(2);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendFamilyInvite(final int cid, final int otherLevel, final int otherJob, final String inviter) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendFamilyInvite--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY_INVITE.getValue());
        mplew.writeInt(cid);
        mplew.writeMapleAsciiString(inviter);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket getSeniorMessage(final String name) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("getSeniorMessage--------------------");
        }
        mplew.writeShort(SendPacketOpcode.SENIOR_MESSAGE.getValue());
        mplew.writeMapleAsciiString(name);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket sendFamilyJoinResponse(final boolean accepted, final String added) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("sendFamilyJoinResponse--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY_JUNIOR.getValue());
        mplew.write(accepted ? 1 : 0);
        mplew.writeMapleAsciiString(added);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket familyBuff(final int type, final int buffnr, final int amount, final int time) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("familyBuff--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY_BUFF.getValue());
        mplew.write(type);
        if (type >= 2 && type <= 4) {
            mplew.writeInt(buffnr);
            mplew.writeInt((type == 3) ? 0 : amount);
            mplew.writeInt((type == 2) ? 0 : amount);
            mplew.write(0);
            mplew.writeInt(time);
        }
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket cancelFamilyBuff() {
        if (ServerConstants.调试输出封包) {
            System.out.println("cancelFamilyBuff--------------------");
        }
        return familyBuff(0, 0, 0, 0);
    }
    
    public static MaplePacket familyLoggedIn(final boolean online, final String name) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("familyLoggedIn--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY_LOGGEDIN.getValue());
        mplew.write(online ? 1 : 0);
        mplew.writeMapleAsciiString(name);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
    
    public static MaplePacket familySummonRequest(final String name, final String mapname) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        if (ServerConstants.调试输出封包) {
            System.out.println("familySummonRequest--------------------");
        }
        mplew.writeShort(SendPacketOpcode.FAMILY_USE_REQUEST.getValue());
        mplew.writeMapleAsciiString(name);
        mplew.writeMapleAsciiString(mapname);
        if (ServerConstants.PACKET_ERROR_OFF) {
            final ServerConstants ERROR = new ServerConstants();
            ERROR.setPACKET_ERROR(" 暂未定义 ：\r\n" + mplew.getPacket() + "\r\n\r\n");
        }
        return mplew.getPacket();
    }
}
