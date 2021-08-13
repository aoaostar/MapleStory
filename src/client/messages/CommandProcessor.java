package client.messages;

import constants.*;
import client.*;
import database.*;

import java.sql.*;

import tools.*;
import client.messages.commands.*;

import java.util.*;
import java.lang.reflect.*;

public class CommandProcessor {
    private static HashMap<String, CommandObject> commands;
    private static HashMap<Integer, ArrayList<String>> commandList;

    private static void sendDisplayMessage(final MapleClient c, final String msg, final ServerConstants.CommandType type) {
        if (c.getPlayer() == null) {
            return;
        }
        switch (type) {
            case NORMAL: {
                c.getPlayer().dropMessage(6, msg);
                break;
            }
            case TRADE: {
                c.getPlayer().dropMessage(-2, "錯誤 : " + msg);
                break;
            }
        }
    }

    public static boolean processCommand(final MapleClient c, final String line, final ServerConstants.CommandType type) {
        if (line.charAt(0) != ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()) {//管理员命令
            if (c.getPlayer().getGMLevel() > ServerConstants.PlayerGMRank.NORMAL.getLevel() && (line.charAt(0) == ServerConstants.PlayerGMRank.GM.getCommandPrefix() || line.charAt(0) == ServerConstants.PlayerGMRank.ADMIN.getCommandPrefix() || line.charAt(0) == ServerConstants.PlayerGMRank.INTERN.getCommandPrefix())) {
                final String[] splitted = line.split(" ");
                splitted[0] = splitted[0].toLowerCase();
                if (line.charAt(0) == '!') {
                    final CommandObject co = CommandProcessor.commands.get(splitted[0]);
                    if (splitted[0].equals("!help")) {
                        dropHelp(c, 0);
                        return true;
                    }
                    if (co == null || co.getType() != type) {
                        sendDisplayMessage(c, "输入的命令不存在.", type);
                        return true;
                    }
                    if (c.getPlayer().getGMLevel() >= co.getReqGMLevel()) {
                        final int ret = co.execute(c, splitted);
                        if (ret > 0 && c.getPlayer() != null) {
                            logGMCommandToDB(c.getPlayer(), line);
                            System.out.println("[ " + c.getPlayer().getName() + " ] 使用了指令: " + line);
                        }
                    } else {
                        sendDisplayMessage(c, "您的权限等级不足以使用次命令.", type);
                    }
                    return true;
                }
            }
            return false;
        } else {//普通玩家命令
            final String[] splitted = line.split(" ");
            splitted[0] = splitted[0].toLowerCase();
            final CommandObject co = CommandProcessor.commands.get(splitted[0]);
            if (co == null || co.getType() != type) {
                sendDisplayMessage(c, "输入的玩家命令不存在,可以使用 @帮助/@help 来查看指令.", type);
                return true;
            }
            try {
                co.execute(c, splitted);
            } catch (Exception e) {
                sendDisplayMessage(c, "有错误.", type);
                if (c.getPlayer().isGM()) {
                    sendDisplayMessage(c, "错误: " + e, type);
                }
            }
            return true;
        }
    }

    private static void logGMCommandToDB(final MapleCharacter player, final String command) {
        PreparedStatement ps = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO gmlog (cid, name, command, mapid, ip) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, player.getId());
            ps.setString(2, player.getName());
            ps.setString(3, command);
            ps.setInt(4, player.getMap().getId());
            ps.setString(5, player.getClient().getSessionIPAddress());
            ps.executeUpdate();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, ex);
            ex.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException ex2) {
            }
        }
    }

    public static void dropHelp(final MapleClient c, final int type) {
        final StringBuilder sb = new StringBuilder("指令列表:\r\n ");
        int check = 0;
        if (type == 0) {
            check = c.getPlayer().getGMLevel();
        }
        for (int i = 0; i <= check; ++i) {
            if (CommandProcessor.commandList.containsKey(i)) {
                sb.append((type == 1) ? "VIP" : "").append("权限等級： ").append(i).append("\r\n");
                for (final String s : CommandProcessor.commandList.get(i)) {
                    sb.append(s);
                    sb.append(" \r\n");
                }
            }
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(9010000, (byte) 0, sb.toString(), "00 00", (byte) 0));
    }

    static {
        CommandProcessor.commands = new HashMap<String, CommandObject>();
        CommandProcessor.commandList = new HashMap<Integer, ArrayList<String>>();
        final Class[] array;
        final Class[] CommandFiles = array = new Class[]{PlayerCommand.class, GMCommand.class, InternCommand.class, AdminCommand.class};
        for (final Class clasz : array) {
            try {
                final ServerConstants.PlayerGMRank rankNeeded = (ServerConstants.PlayerGMRank) clasz.getMethod("getPlayerLevelRequired", (Class[]) new Class[0]).invoke(null, (Object[]) null);
                final Class[] a = clasz.getDeclaredClasses();
                final ArrayList<String> cL = new ArrayList<String>();
                for (final Class c : a) {
                    try {
                        if (!Modifier.isAbstract(c.getModifiers()) && !c.isSynthetic()) {
                            final Object o = c.newInstance();
                            boolean enabled;
                            try {
                                enabled = c.getDeclaredField("enabled").getBoolean(c.getDeclaredField("enabled"));
                            } catch (NoSuchFieldException ex3) {
                                enabled = true;
                            }
                            if (o instanceof CommandExecute && enabled) {
                                cL.add(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase());
                                CommandProcessor.commands.put(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase(), new CommandObject(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase(), (CommandExecute) o, rankNeeded.getLevel()));
                            }
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException ex6) {
                        ex6.printStackTrace();
                        FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex6);
                    }
                }
                Collections.sort(cL);
                CommandProcessor.commandList.put(rankNeeded.getLevel(), cL);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex7) {
                ex7.printStackTrace();
                FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex7);
            }
        }
    }
}
