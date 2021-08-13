package scripting;

import client.MapleClient;
import constants.GameConstants;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;

public class NPCScriptManager extends AbstractScriptManager {
    private final Map<MapleClient, NPCConversationManager> mapleClientNPCConversationManagerMap = new WeakHashMap<>();
    private static final NPCScriptManager npcScriptManager = new NPCScriptManager();


    public static final NPCScriptManager getInstance() {
        return NPCScriptManager.npcScriptManager;
    }

    public void start(final MapleClient c, final int npc) {
        this.start(c, npc, 0);
    }

    public final void start(final MapleClient c, final int npc, final int wh) {
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (c.getPlayer().isGM()) {
                if (wh == 0) {
                    c.getPlayer().dropMessage("[系统提示]您已经建立与NPC:" + npc + "的对话。");
                } else {
                    c.getPlayer().dropMessage("[系统提示]您已经建立与NPC:" + npc + "_" + wh + "的对话。");
                }
            }
            if (!this.mapleClientNPCConversationManagerMap.containsKey(c)) {
                Invocable iv;
                if (wh == 0) {
                    iv = this.getInvocable("npc"+ File.separator + npc + ".js", c, true);
                } else {
                    iv = this.getInvocable("npc"+File.separator + npc + "_" + wh + ".js", c, true);
                }
                ScriptEngine scriptengine = (ScriptEngine) iv;
                NPCConversationManager cm;
                if (wh == 0) {
                    cm = new NPCConversationManager(c, npc, -1, (byte) (-1), iv, 0);
                } else {
                    cm = new NPCConversationManager(c, npc, -1, (byte) (-1), iv, wh);
                }
                this.mapleClientNPCConversationManagerMap.put(c, cm);
                if (iv == null || getInstance() == null) {
                    if (wh == 0) {
                        switch (GameConstants.game) {
                            case 0: {
                                cm.sendOk("欢迎来到#b寻梦冒险岛#k。你找我有什么事吗？\r\n我的ID是: #r" + npc + "#k.\r\n 有问题联系#bGM#k");
                                break;
                            }
                            default: {
                                cm.sendOk("欢迎来到#b寻梦冒险岛#k。你找我有什么事吗？\r\n我的ID是: #r" + npc + "#k.\r\n 有问题联系#bGM#k");
                                break;
                            }
                        }
                    } else {
                        cm.sendOk("欢迎来到#b寻梦冒险岛#k。你找我有什么事吗？\r\n我的ID是: #r" + npc + "#k.\r\n 有问题联系#bGM#k");
                    }
                    cm.dispose();
                    return;
                }
                scriptengine.put("cm", cm);
                scriptengine.put("npcid", npc);
                c.getPlayer().setConversation(1);
                try {
                    iv.invokeFunction("start");
                } catch (NoSuchMethodException nsme) {
                    iv.invokeFunction("action", 1, 0, 0);
                }

            } else {
                getInstance().dispose(c);
                c.getSession().write(MaplePacketCreator.enableActions());
            }
        } catch (Exception e) {
            System.err.println("NPC 腳本錯誤, 它ID為 : " + npc + "_" + wh + "." + e);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示] NPC " + npc + "_" + wh + "腳本錯誤 " + e + "");
            }
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing NPC script, NPC ID : " + npc + "_" + wh + "." + e);
            this.dispose(c);
        } finally {

            lock.unlock();
        }
    }

    public void action(final MapleClient c, final byte mode, final byte type, final int selection) {
        this.action(c, mode, type, selection, 0);
    }

    public final void action(final MapleClient c, final byte mode, final byte type, final int selection, final int wh) {
        if (mode != -1) {
            final NPCConversationManager cm = this.mapleClientNPCConversationManagerMap.get(c);
            if (cm == null || cm.getLastMsg() > -1) {
                return;
            }
            final Lock lock = c.getNPCLock();
            lock.lock();
            try {
                if (cm.pendingDisposal) {
                    this.dispose(c);
                } else if (wh == 0) {
                    cm.getIv().invokeFunction("action", mode, type, selection);
                } else {
                    cm.getIv().invokeFunction("action", mode, type, selection, wh);
                }
            } catch (Exception e) {
                if (c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系統提示] NPC " + cm.getNpc() + "_" + wh + "腳本錯誤 " + e + "");
                }
                System.err.println("NPC 腳本錯誤. 它ID為 : " + cm.getNpc() + "_" + wh + ":" + e);
                this.dispose(c);
                FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing NPC script, NPC ID : " + cm.getNpc() + "_" + wh + "." + e);
            } finally {
                lock.unlock();
            }
        }
    }

    public final void startQuest(final MapleClient c, final int npc, final int quest) {
        if (!MapleQuest.getInstance(quest).canStart(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.mapleClientNPCConversationManagerMap.containsKey(c)) {
                final Invocable iv = this.getInvocable("quest"+File.separator + quest + ".js", c, true);
                if (iv == null) {
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine) iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, (byte) 0, iv, 0);
                this.mapleClientNPCConversationManagerMap.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                if (c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系統提示]您已經建立與任務腳本:" + quest + "的往來。");
                }
                iv.invokeFunction("start", 1, 0, 0);
            } else {
                this.dispose(c);
            }
        } catch (Exception e) {
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        } finally {
            lock.unlock();
        }
    }

    public final void startQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = this.mapleClientNPCConversationManagerMap.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            } else {
                cm.getIv().invokeFunction("start", mode, type, selection);
            }
        } catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + cm.getQuest() + "錯誤...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        } finally {
            lock.unlock();
        }
    }

    public final void endQuest(final MapleClient c, final int npc, final int quest, final boolean customEnd) {
        if (!customEnd && !MapleQuest.getInstance(quest).canComplete(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.mapleClientNPCConversationManagerMap.containsKey(c)) {
                final Invocable iv = this.getInvocable("quest"+File.separator + quest + ".js", c, true);
                if (iv == null) {
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine) iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, (byte) 1, iv, 0);
                this.mapleClientNPCConversationManagerMap.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                iv.invokeFunction("end", 1, 0, 0);
            }
        } catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + quest + "錯誤...NPC: " + quest + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        } finally {
            lock.unlock();
        }
    }

    public final void endQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = this.mapleClientNPCConversationManagerMap.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            } else {
                cm.getIv().invokeFunction("end", mode, type, selection);
            }
        } catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + cm.getQuest() + "錯誤...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        } finally {
            lock.unlock();
        }
    }

    public final void dispose(final MapleClient c) {
        final NPCConversationManager npccm = this.mapleClientNPCConversationManagerMap.get(c);
        if (npccm != null) {
            String scriptsPath = System.getProperty("scripts_path");
            this.mapleClientNPCConversationManagerMap.remove(c);
            if (npccm.getType() == -1) {
                if (npccm.getwh() == 0) {
                    c.removeScriptEngine(scriptsPath +"scripts"+File.separator+"npc"+File.separator + npccm.getNpc() + ".js");
                } else {
                    c.removeScriptEngine(scriptsPath +"scripts"+File.separator+"npc"+File.separator + npccm.getNpc() + "_" + npccm.getwh() + ".js");
                }
                c.removeScriptEngine(scriptsPath +"scripts"+File.separator+"npc"+File.separator+"notcoded.js");
            } else {
                c.removeScriptEngine(scriptsPath +"scripts"+File.separator+"quest"+File.separator + npccm.getQuest() + ".js");
            }
        }
        if (c.getPlayer() != null && c.getPlayer().getConversation() == 1) {
            c.getPlayer().setConversation(0);
        }
    }

    public final NPCConversationManager getCM(final MapleClient c) {
        return this.mapleClientNPCConversationManagerMap.get(c);
    }

}
