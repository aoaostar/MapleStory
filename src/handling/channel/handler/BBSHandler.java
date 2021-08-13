package handling.channel.handler;

import client.MapleClient;
import handling.world.World;
import handling.world.guild.MapleBBSThread;
import java.util.Iterator;
import java.util.List;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class BBSHandler
{
    private static String correctLength(final String in, final int maxSize) {
        if (in.length() > maxSize) {
            return in.substring(0, maxSize);
        }
        return in;
    }
    
    public static void BBSOperatopn(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        int localthreadid = 0;
        final byte action = slea.readByte();
        switch (action) {
            case 0: {
                final boolean bEdit = slea.readByte() > 0;
                if (bEdit) {
                    localthreadid = slea.readInt();
                }
                final boolean bNotice = slea.readByte() > 0;
                final String title = correctLength(slea.readMapleAsciiString(), 25);
                final String text = correctLength(slea.readMapleAsciiString(), 600);
                final int icon = slea.readInt();
                if (icon >= 100 && icon <= 106) {
                    if (!c.getPlayer().haveItem(5290000 + icon - 100, 1, false, true)) {
                        return;
                    }
                }
                else if (icon < 0 || icon > 2) {
                    return;
                }
                if (!bEdit) {
                    newBBSThread(c, title, text, icon, bNotice);
                    break;
                }
                editBBSThread(c, title, text, icon, localthreadid);
                break;
            }
            case 1: {
                localthreadid = slea.readInt();
                deleteBBSThread(c, localthreadid);
                break;
            }
            case 2: {
                final int start = slea.readInt();
                listBBSThreads(c, start * 10);
                break;
            }
            case 3: {
                localthreadid = slea.readInt();
                displayThread(c, localthreadid);
                break;
            }
            case 4: {
                localthreadid = slea.readInt();
                final String text = correctLength(slea.readMapleAsciiString(), 25);
                newBBSReply(c, localthreadid, text);
                break;
            }
            case 5: {
                localthreadid = slea.readInt();
                final int replyid = slea.readInt();
                deleteBBSReply(c, localthreadid, replyid);
                break;
            }
        }
    }
    
    private static void listBBSThreads(final MapleClient c, final int start) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        c.getSession().write((Object)MaplePacketCreator.BBSThreadList(World.Guild.getBBS(c.getPlayer().getGuildId()), start));
    }
    
    private static void newBBSReply(final MapleClient c, final int localthreadid, final String text) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.addBBSReply(c.getPlayer().getGuildId(), localthreadid, text, c.getPlayer().getId());
        displayThread(c, localthreadid);
    }
    
    private static void editBBSThread(final MapleClient c, final String title, final String text, final int icon, final int localthreadid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.editBBSThread(c.getPlayer().getGuildId(), localthreadid, title, text, icon, c.getPlayer().getId(), c.getPlayer().getGuildRank());
        displayThread(c, localthreadid);
    }
    
    private static void newBBSThread(final MapleClient c, final String title, final String text, final int icon, final boolean bNotice) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        displayThread(c, World.Guild.addBBSThread(c.getPlayer().getGuildId(), title, text, icon, bNotice, c.getPlayer().getId()));
    }
    
    private static void deleteBBSThread(final MapleClient c, final int localthreadid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.deleteBBSThread(c.getPlayer().getGuildId(), localthreadid, c.getPlayer().getId(), c.getPlayer().getGuildRank());
    }
    
    private static void deleteBBSReply(final MapleClient c, final int localthreadid, final int replyid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.deleteBBSReply(c.getPlayer().getGuildId(), localthreadid, replyid, c.getPlayer().getId(), c.getPlayer().getGuildRank());
        displayThread(c, localthreadid);
    }
    
    private static void displayThread(final MapleClient c, final int localthreadid) {
        if (c.getPlayer().getGuildId() <= 0) {
            return;
        }
        final List<MapleBBSThread> bbsList = World.Guild.getBBS(c.getPlayer().getGuildId());
        if (bbsList != null) {
            for (final MapleBBSThread t : bbsList) {
                if (t != null && t.localthreadID == localthreadid) {
                    c.getSession().write((Object)MaplePacketCreator.showThread(t));
                }
            }
        }
    }
}
