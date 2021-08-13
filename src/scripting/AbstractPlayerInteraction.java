package scripting;

import KinMS.db.CherryMSLottery;
import KinMS.db.CherryMScustomEventFactory;
import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import constants.GameConstants;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.World;
import handling.world.guild.MapleGuild;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.Randomizer;
import server.ServerProperties;
import server.Timer;
import server.custom.auction.AuctionItem;
import server.custom.auction.AuctionManager;
import server.custom.auction.AuctionPoint;
import server.custom.bossrank.BossRankInfo;
import server.custom.bossrank.BossRankManager;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.OverrideMonsterStats;
import server.maps.Event_DojoAgent;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleReactor;
import server.maps.SavedLocationType;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;
import tools.packet.PetPacket;
import tools.packet.UIPacket;

public abstract class AbstractPlayerInteraction
{
    private final MapleClient c;
    
    public AbstractPlayerInteraction(final MapleClient c) {
        this.c = c;
    }
    
    public MapleClient getClient() {
        return this.c;
    }
    
    public MapleClient getC() {
        return this.c;
    }
    
    public MapleCharacter getChar() {
        this.c.getPlayer().getInventory(MapleInventoryType.USE).listById(1).iterator();
        return this.c.getPlayer();
    }
    
    public ChannelServer getChannelServer() {
        return this.c.getChannelServer();
    }
    
    public MapleCharacter getPlayer() {
        return this.c.getPlayer();
    }
    
    public MapleMap getMap() {
        return this.c.getPlayer().getMap();
    }
    
    public EventManager getEventManager(final String event) {
        return this.c.getChannelServer().getEventSM().getEventManager(event);
    }
    
    public EventInstanceManager getEventInstance() {
        return this.c.getPlayer().getEventInstance();
    }
    
    public void forceRemovePlayerByCharName(final String name) {
        ChannelServer.forceRemovePlayerByCharName(name);
    }
    
    public void warp(final int map) {
        final MapleMap mapz = this.getWarpMap(map);
        try {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        }
        catch (Exception e) {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }
    
    public void warpPlayer(final int map, final int map2) {
        if (this.c.getPlayer().getMapId() == map) {
            final MapleMap mapz = this.getWarpMap(map2);
            try {
                this.c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
            }
            catch (Exception e) {
                this.c.getPlayer().changeMap(mapz, mapz.getPortal(0));
            }
        }
        else {
            this.c.getPlayer().dropMessage(5, "NPC传送地图函数不匹配！");
        }
    }
    
    public void warp_Instanced(final int map) {
        final MapleMap mapz = this.getMap_Instanced(map);
        try {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        }
        catch (Exception e) {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }
    
    public void warp(final int map, final int portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (portal != 0 && map == this.c.getPlayer().getMapId()) {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
        else {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }
    
    public void warpS(final int map, final int portal) {
        final MapleMap mapz = this.getWarpMap(map);
        this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }
    
    public void warp(final int map, String portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        if (map == this.c.getPlayer().getMapId()) {
            final Point portalPos = new Point(this.c.getPlayer().getMap().getPortal(portal).getPosition());
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
        else {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }
    
    public void warpS(final int map, String portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }
    
    public void warpMap(final int mapid, final int portal) {
        final MapleMap map = this.getMap(mapid);
        for (final MapleCharacter chr : this.c.getPlayer().getMap().getCharactersThreadsafe()) {
            chr.changeMap(map, map.getPortal(portal));
        }
    }
    
    public void playPortalSE() {
        this.c.getSession().write(MaplePacketCreator.showOwnBuffEffect(0, 5));
    }
    
    private MapleMap getWarpMap(final int map) {
        return ChannelServer.getInstance(this.c.getChannel()).getMapFactory().getMap(map);
    }
    
    public MapleMap getMap(final int map) {
        return this.getWarpMap(map);
    }
    
    public MapleMap getMap_Instanced(final int map) {
        return (this.c.getPlayer().getEventInstance() == null) ? this.getMap(map) : this.c.getPlayer().getEventInstance().getMapInstance(map);
    }
    
    public void spawnMap(final int MapID, final int MapID2) {
        for (final ChannelServer chan : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (this.getC().getChannel() != chr.getClient().getChannel() || chr.getMapId() != MapID) {
                    continue;
                }
                this.warp(MapID2);
            }
        }
    }
    
    public void spawnMap(final int MapID) {
        for (final ChannelServer chan : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                if (this.getC().getChannel() != chr.getClient().getChannel() || chr.getMapId() != this.getMapId()) {
                    continue;
                }
                this.warp(MapID);
            }
        }
    }
    
    public void spawnMobLevel(final int mobId, final int level) {
        this.spawnMobLevel(mobId, 1, level, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobLevel(final int mobId, final int quantity, final int level) {
        this.spawnMobLevel(mobId, quantity, level, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobLevel(final int mobId, final int quantity, final int level, final int x, final int y) {
        this.spawnMobLevel(mobId, quantity, level, new Point(x, y));
    }
    
    public void spawnMobLevel(final int mobId, final int quantity, final int level, final Point pos) {
        for (int i = 0; i < quantity; ++i) {
            final MapleMonster mob = MapleLifeFactory.getMonster(mobId);
            if (mob == null || !mob.getStats().isChangeable()) {
                if (this.c.getPlayer().isAdmin()) {
                    this.c.getPlayer().dropMessage(6, "[系统提示] spawnMobLevel召唤怪物出错，ID为: " + mobId + " 怪物不存在或者该怪物无法使用这个函数来改变怪物的属性！");
                }
            }
            else {
                mob.changeLevel(level, false);
                this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, pos);
            }
        }
    }
    
    public void spawnMobStats(final int mobId, final long newhp, final int newExp) {
        this.spawnMobStats(mobId, 1, newhp, newExp, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobStats(final int mobId, final int quantity, final long newhp, final int newExp) {
        this.spawnMobStats(mobId, quantity, newhp, newExp, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobStats(final int mobId, final int quantity, final long newhp, final int newExp, final int x, final int y) {
        this.spawnMobStats(mobId, quantity, newhp, newExp, new Point(x, y));
    }
    
    public void spawnMobStats(final int mobId, final int quantity, final long newhp, final int newExp, final Point pos) {
        for (int i = 0; i < quantity; ++i) {
            final MapleMonster mob = MapleLifeFactory.getMonster(mobId);
            if (mob == null) {
                if (this.c.getPlayer().isAdmin()) {
                    this.c.getPlayer().dropMessage(6, "[系统提示] spawnMobStats召唤怪物出错，ID为: " + mobId + " 怪物不存在！");
                }
            }
            else {
                final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, mob.getMobMaxMp(), (newExp <= 0) ? mob.getMobExp() : newExp, false);
                mob.setOverrideStats(overrideStats);
                this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, pos);
            }
        }
    }
    
    public void spawnMobMultipler(final int mobId, final int multipler) {
        this.spawnMobMultipler(mobId, 1, multipler, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobMultipler(final int mobId, final int quantity, final int multipler) {
        this.spawnMobMultipler(mobId, quantity, multipler, this.c.getPlayer().getTruePosition());
    }
    
    public void spawnMobMultipler(final int mobId, final int quantity, final int multipler, final int x, final int y) {
        this.spawnMobMultipler(mobId, quantity, multipler, new Point(x, y));
    }
    
    public void spawnMobMultipler(final int mobId, final int quantity, final int multipler, final Point pos) {
        for (int i = 0; i < quantity; ++i) {
            final MapleMonster mob = MapleLifeFactory.getMonster(mobId);
            if (mob == null) {
                if (this.c.getPlayer().isAdmin()) {
                    this.c.getPlayer().dropMessage(6, "[系统提示] spawnMobMultipler召唤怪物出错，ID为: " + mobId + " 怪物不存在！");
                }
            }
            else {
                final OverrideMonsterStats overrideStats = new OverrideMonsterStats(mob.getMobMaxHp() * multipler, mob.getMobMaxMp() * multipler, mob.getMobExp() + multipler * 100, false);
                mob.setOverrideStats(overrideStats);
                this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, pos);
            }
        }
    }
    
    public void spawnMonster(final int id, final int qty) {
        this.spawnMob(id, qty, new Point(this.c.getPlayer().getPosition()));
    }
    
    public void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map) {
        for (int i = 0; i < qty; ++i) {
            this.getMap(map).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), new Point(x, y));
        }
    }
    
    public void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map, final int hp) {
        for (int i = 0; i < qty; ++i) {
            this.getMap(map).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), new Point(x, y), hp);
        }
    }
    
    public void spawnMob(final int id, final int qty, final int x, final int y) {
        this.spawnMob(id, qty, new Point(x, y));
    }
    
    public void spawnMob_map(final int id, final int mapid, final int x, final int y) {
        this.spawnMob_map(id, mapid, new Point(x, y));
    }
    
    public void spawnMob_map(final int id, final int mapid, final Point pos) {
        this.c.getChannelServer().getMapFactory().getMap(mapid).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
    }
    
    public void spawnMob(final int id, final int x, final int y) {
        this.spawnMob(id, 1, new Point(x, y));
    }
    
    public void spawnMob(final int id, final int qty, final Point pos) {
        for (int i = 0; i < qty; ++i) {
            this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }
    
    public void killMob(final int ids) {
        this.c.getPlayer().getMap().killMonster(ids);
    }
    
    public void killAllMob() {
        this.c.getPlayer().getMap().killAllMonsters(true);
    }
    
    public void addHP(final int delta) {
        this.c.getPlayer().addHP(delta);
    }
    
    public void setPlayerStat(final String type, final int x) {
        switch (type) {
            case "LVL": {
                this.c.getPlayer().setLevel((short)x);
                break;
            }
            case "STR": {
                this.c.getPlayer().getStat().setStr((short)x);
                break;
            }
            case "DEX": {
                this.c.getPlayer().getStat().setDex((short)x);
                break;
            }
            case "INT": {
                this.c.getPlayer().getStat().setInt((short)x);
                break;
            }
            case "LUK": {
                this.c.getPlayer().getStat().setLuk((short)x);
                break;
            }
            case "HP": {
                this.c.getPlayer().getStat().setHp(x);
                break;
            }
            case "MP": {
                this.c.getPlayer().getStat().setMp(x);
                break;
            }
            case "MAXHP": {
                this.c.getPlayer().getStat().setMaxHp((short)x);
                break;
            }
            case "MAXMP": {
                this.c.getPlayer().getStat().setMaxMp((short)x);
                break;
            }
            case "RAP": {
                this.c.getPlayer().setRemainingAp((short)x);
                break;
            }
            case "RSP": {
                this.c.getPlayer().setRemainingSp((short)x);
                break;
            }
            case "GID": {
                this.c.getPlayer().setGuildId(x);
                break;
            }
            case "GRANK": {
                this.c.getPlayer().setGuildRank((byte)x);
                break;
            }
            case "ARANK": {
                this.c.getPlayer().setAllianceRank((byte)x);
                break;
            }
            case "GENDER": {
                this.c.getPlayer().setGender((byte)x);
                break;
            }
            case "FACE": {
                this.c.getPlayer().setFace(x);
                break;
            }
            case "HAIR": {
                this.c.getPlayer().setHair(x);
                break;
            }
        }
    }
    
    public int getPlayerStat(final String type) {
        switch (type) {
            case "LVL": {
                return this.c.getPlayer().getLevel();
            }
            case "STR": {
                return this.c.getPlayer().getStat().getStr();
            }
            case "DEX": {
                return this.c.getPlayer().getStat().getDex();
            }
            case "INT": {
                return this.c.getPlayer().getStat().getInt();
            }
            case "LUK": {
                return this.c.getPlayer().getStat().getLuk();
            }
            case "HP": {
                return this.c.getPlayer().getStat().getHp();
            }
            case "MP": {
                return this.c.getPlayer().getStat().getMp();
            }
            case "MAXHP": {
                return this.c.getPlayer().getStat().getMaxHp();
            }
            case "MAXMP": {
                return this.c.getPlayer().getStat().getMaxMp();
            }
            case "RAP": {
                return this.c.getPlayer().getRemainingAp();
            }
            case "RSP": {
                return this.c.getPlayer().getRemainingSp();
            }
            case "GID": {
                return this.c.getPlayer().getGuildId();
            }
            case "GRANK": {
                return this.c.getPlayer().getGuildRank();
            }
            case "ARANK": {
                return this.c.getPlayer().getAllianceRank();
            }
            case "GM": {
                return this.c.getPlayer().isGM() ? 1 : 0;
            }
            case "ADMIN": {
                return this.c.getPlayer().isAdmin() ? 1 : 0;
            }
            case "GENDER": {
                return this.c.getPlayer().getGender();
            }
            case "FACE": {
                return this.c.getPlayer().getFace();
            }
            case "HAIR": {
                return this.c.getPlayer().getHair();
            }
            default: {
                return -1;
            }
        }
    }
    
    public String getName() {
        return this.c.getPlayer().getName();
    }
    
    public boolean haveItem(final int itemid) {
        return this.haveItem(itemid, 1);
    }
    
    public boolean haveItem(final int itemid, final int quantity) {
        return this.haveItem(itemid, quantity, false, true);
    }
    
    public boolean haveItem(final int itemid, final int quantity, final boolean checkEquipped, final boolean greaterOrEquals) {
        return this.c.getPlayer().haveItem(itemid, quantity, checkEquipped, greaterOrEquals);
    }
    
    public boolean canHold() {
        for (int i = 1; i <= 5; ++i) {
            if (this.c.getPlayer().getInventory(MapleInventoryType.getByType((byte)i)).getNextFreeSlot() <= -1) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canHold(final int itemid) {
        return this.c.getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }
    
    public boolean canHold(final int itemid, final int quantity) {
        return MapleInventoryManipulator.checkSpace(this.c, itemid, quantity, "");
    }
    
    public MapleQuestStatus getQuestRecord(final int id) {
        return this.c.getPlayer().getQuestNAdd(MapleQuest.getInstance(id));
    }
    
    public byte getQuestStatus(final int id) {
        return this.c.getPlayer().getQuestStatus(id);
    }
    
    public void completeQuest(final int id) {
        this.c.getPlayer().setQuestAdd(id);
    }
    
    public boolean isQuestActive(final int id) {
        return this.getQuestStatus(id) == 1;
    }
    
    public boolean isQuestFinished(final int id) {
        return this.getQuestStatus(id) == 2;
    }
    
    public void showQuestMsg(final String msg) {
        this.c.getSession().write(MaplePacketCreator.showQuestMsg(msg));
    }
    
    public void forceStartQuest(final int id, final String data) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, data);
    }
    
    public void forceStartQuest(final int id, final int data, final boolean filler) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, filler ? String.valueOf(data) : null);
    }
    
    public void clearAranPolearm() {
        this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeItem((short)(-11));
    }
    
    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, null);
    }
    
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(this.getPlayer(), 0);
    }
    
    public void spawnNpc(final int npcId) {
        this.c.getPlayer().getMap().spawnNpc(npcId, this.c.getPlayer().getPosition());
    }
    
    public void spawnNpc(final int npcId, final int x, final int y) {
        this.c.getPlayer().getMap().spawnNpc(npcId, new Point(x, y));
    }
    
    public void spawnNpc(final int npcId, final Point pos) {
        this.c.getPlayer().getMap().spawnNpc(npcId, pos);
    }
    
    public void removeNpc(final int mapid, final int npcId) {
        this.c.getChannelServer().getMapFactory().getMap(mapid).removeNpc(npcId);
    }
    
    public void forceStartReactor(final int mapid, final int id) {
        final MapleMap map = this.c.getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.forceStartReactor(this.c);
                break;
            }
        }
    }
    
    public void destroyReactor(final int mapid, final int id) {
        final MapleMap map = this.c.getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.hitReactor(this.c);
                break;
            }
        }
    }
    
    public void hitReactor(final int mapid, final int id) {
        final MapleMap map = this.c.getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.hitReactor(this.c);
                break;
            }
        }
    }
    
    public int getJob() {
        return this.c.getPlayer().getJob();
    }
    
    public int getNX(final int 类型) {
        return this.c.getPlayer().getCSPoints(类型);
    }
    
    public void gainD(final int amount) {
        this.c.getPlayer().modifyCSPoints(2, amount, true);
    }
    
    public void gainNX(final int amount) {
        this.c.getPlayer().modifyCSPoints(1, amount, true);
    }
    
    public void gainItemPeriod(final int id, final short quantity, final int period) {
        this.gainItem(id, quantity, false, period, -1, "", (byte)0);
    }
    
    public void gainItemPeriod(final int id, final short quantity, final long period, final String owner) {
        this.gainItem(id, quantity, false, period, -1, owner, (byte)0);
    }
    
    public void gainItem(final int id, final short quantity) {
        this.gainItem(id, quantity, false, 0L, -1, "", (byte)0);
    }
    
    public void gainItem(final int id, final short quantity, final long period, final byte Flag) {
        this.gainItem(id, quantity, false, period, -1, "", Flag);
    }
    
    public void gainItem(final int id, final short quantity, final boolean randomStats) {
        this.gainItem(id, quantity, randomStats, 0L, -1, "", (byte)0);
    }
    
    public void gainItem(final int id, final short quantity, final boolean randomStats, final int slots) {
        this.gainItem(id, quantity, randomStats, 0L, slots, "", (byte)0);
    }
    
    public void gainItem(final int id, final short quantity, final long period) {
        this.gainItem(id, quantity, false, period, -1, "", (byte)0);
    }
    
    public void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots) {
        this.gainItem(id, quantity, randomStats, period, slots, "", (byte)0);
    }
    
    public void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final byte Flag) {
        this.gainItem(id, quantity, randomStats, period, slots, owner, this.c, Flag);
    }
    
    public void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg, final byte Flag) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);
            if (!MapleInventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.is飞镖道具(id) && !GameConstants.is子弹道具(id)) {
                final Equip item = (Equip)(randomStats ? ii.randomizeStats((Equip)ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0L) {
                    item.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte)(item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "你已获得称号 <" + name + ">";
                    cg.getPlayer().dropMessage(5, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            }
            else {
                MapleInventoryManipulator.addById(cg, id, quantity, (owner == null) ? "" : owner, null, period, Flag);
            }
        }
        else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }
    
    public void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int watk, final int matk) {
        this.gainItem(id, str, dex, luk, Int, 0, 0, watk, matk, 0, 0, 0, 0, 0, 0);
    }
    
    public void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd) {
        this.gainItemS(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c, 0L);
    }
    
    public void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final long time) {
        this.gainItemS(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c, time);
    }
    
    public void gainItemS(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final MapleClient cg, final long time) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(id);
        if (!MapleInventoryManipulator.checkSpace(cg, id, 1, "")) {
            return;
        }
        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
            final Equip item = (Equip)ii.getEquipById(id);
            final String name = ii.getName(id);
            if (id / 10000 == 114 && name != null && name.length() > 0) {
                final String msg = "你已获得称号 <" + name + ">";
                cg.getPlayer().dropMessage(5, msg);
                cg.getPlayer().dropMessage(5, msg);
            }
            if (time > 0L) {
                item.setExpiration(System.currentTimeMillis() + time * 60L * 60L * 1000L);
            }
            if (str > 0) {
                item.setStr((short)str);
            }
            if (dex > 0) {
                item.setDex((short)dex);
            }
            if (luk > 0) {
                item.setLuk((short)luk);
            }
            if (Int > 0) {
                item.setInt((short)Int);
            }
            if (hp > 0) {
                item.setHp((short)hp);
            }
            if (mp > 0) {
                item.setMp((short)mp);
            }
            if (watk > 0) {
                item.setWatk((short)watk);
            }
            if (matk > 0) {
                item.setMatk((short)matk);
            }
            if (wdef > 0) {
                item.setWdef((short)wdef);
            }
            if (mdef > 0) {
                item.setMdef((short)mdef);
            }
            if (hb > 0) {
                item.setAvoid((short)hb);
            }
            if (mz > 0) {
                item.setAcc((short)mz);
            }
            if (ty > 0) {
                item.setJump((short)ty);
            }
            if (yd > 0) {
                item.setSpeed((short)yd);
            }
            MapleInventoryManipulator.addbyItem(cg, item.copy());
        }
        else {
            MapleInventoryManipulator.addById(cg, id, (short)1, "", (byte)0);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, (short)1, true));
    }
    
    public void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int watk, final int matk, final int suo) {
        this.给予道具(id, str, dex, luk, Int, 0, 0, watk, matk, 0, 0, 0, 0, 0, 0, this.c, 0, 0, 0, 0, suo);
    }
    
    public void 给予道具(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final int ksjcs, final int ysjcs, final int jcz, final int suo) {
        this.给予道具(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c, 0, ksjcs, ysjcs, jcz, suo);
    }
    
    public void 给予道具(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final int time, final int ksjcs, final int ysjcs, final int jcz, final int suo) {
        this.给予道具(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c, time, ksjcs, ysjcs, jcz, suo);
    }
    
    public void 给予道具(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final MapleClient cg, final int time, final int ksjcs, final int ysjcs, final int jcz, final int suo) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(id);
        if (!MapleInventoryManipulator.checkSpace(cg, id, 1, "")) {
            return;
        }
        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.is飞镖道具(id) && !GameConstants.is子弹道具(id)) {
            final Equip item = (Equip)ii.getEquipById(id);
            final String name = ii.getName(id);
            if (id / 10000 == 114 && name != null && name.length() > 0) {
                final String msg = "你已获得称号 <" + name + ">";
                cg.getPlayer().dropMessage(5, msg);
                cg.getPlayer().dropMessage(5, msg);
            }
            if (time > 0) {
                item.setExpiration(System.currentTimeMillis() + time * 60 * 60 * 1000);
            }
            if (str > 0) {
                item.setStr((short)str);
            }
            if (dex > 0) {
                item.setDex((short)dex);
            }
            if (luk > 0) {
                item.setLuk((short)luk);
            }
            if (Int > 0) {
                item.setInt((short)Int);
            }
            if (hp > 0) {
                item.setHp((short)hp);
            }
            if (mp > 0) {
                item.setMp((short)mp);
            }
            if (watk > 0) {
                item.setWatk((short)watk);
            }
            if (matk > 0) {
                item.setMatk((short)matk);
            }
            if (wdef > 0) {
                item.setWdef((short)wdef);
            }
            if (mdef > 0) {
                item.setMdef((short)mdef);
            }
            if (hb > 0) {
                item.setAvoid((short)hb);
            }
            if (mz > 0) {
                item.setAcc((short)mz);
            }
            if (ty > 0) {
                item.setJump((short)ty);
            }
            if (yd > 0) {
                item.setSpeed((short)yd);
            }
            if (ksjcs >= 0) {
                item.setUpgradeSlots((byte)ksjcs);
            }
            if (ysjcs > 0) {
                item.setLevel((byte)ysjcs);
            }
            if (jcz > 0) {
                item.setViciousHammer((byte)jcz);
            }
            if (suo > 0) {
                item.setLocked((byte)suo);
            }
            MapleInventoryManipulator.addbyItem(cg, item.copy());
        }
        else {
            MapleInventoryManipulator.addById(cg, id, (short)1, "", (byte)0);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, (short)1, true));
    }
    
    public boolean transferCashEquipStat(final short fromSlot, final short toSlot) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final Equip fromEquip = (Equip)equip.getItem(fromSlot);
        final Equip toEquip = (Equip)equip.getItem(toSlot);
        if (fromEquip == null || toEquip == null) {
            return false;
        }
        final boolean isCash1 = ii.isCash(fromEquip.getItemId());
        final boolean isCash2 = ii.isCash(toEquip.getItemId());
        if (!isCash1 || !isCash2) {
            return false;
        }
        if (fromEquip.getStr() > 0) {
            toEquip.setStr(fromEquip.getStr());
        }
        if (fromEquip.getDex() > 0) {
            toEquip.setDex(fromEquip.getDex());
        }
        if (fromEquip.getInt() > 0) {
            toEquip.setInt(fromEquip.getInt());
        }
        if (fromEquip.getLuk() > 0) {
            toEquip.setLuk(fromEquip.getLuk());
        }
        if (fromEquip.getHp() > 0) {
            toEquip.setHp(fromEquip.getHp());
        }
        if (fromEquip.getMp() > 0) {
            toEquip.setMp(fromEquip.getMp());
        }
        if (fromEquip.getWatk() > 0) {
            toEquip.setWatk(fromEquip.getWatk());
        }
        if (fromEquip.getWdef() > 0) {
            toEquip.setWdef(fromEquip.getWdef());
        }
        if (fromEquip.getMatk() > 0) {
            toEquip.setMatk(fromEquip.getMatk());
        }
        if (fromEquip.getMdef() > 0) {
            toEquip.setMdef(fromEquip.getMdef());
        }
        this.getPlayer().getInventory(MapleInventoryType.EQUIP).removeItem(fromSlot);
        this.c.getSession().write(MaplePacketCreator.clearInventoryItem(MapleInventoryType.EQUIP, fromSlot, false));
        this.c.getSession().write(MaplePacketCreator.updateSpecialItemUse_(toEquip, GameConstants.getInventoryType(toEquip.getItemId()).getType()));
        this.c.getSession().write(MaplePacketCreator.enableActions());
        return true;
    }
    
    public void enableActions() {
        NPCScriptManager.getInstance().dispose(this.c);
        this.c.getSession().write(MaplePacketCreator.enableActions());
        this.c.getPlayer().dropMessage(1, "假死已处理完毕.");
        this.c.getPlayer().dropMessage(6, "当前延迟 " + this.c.getPlayer().getClient().getLatency() + " 毫秒");
    }
    
    public void changeMusic(final String songName) {
        this.getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange(songName));
    }
    
    public void cs(final String songName) {
        this.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect(songName));
    }
    
    public void worldMessage(final int type, final int channel, final String message, final boolean smegaEar) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(type, channel, message, smegaEar).getBytes());
    }
    
    public void worldMessage(final int type, final String message) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(type, message).getBytes());
    }
    
    public void givePartyExp_PQ(final int maxLevel, final double mod) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            final int amount = (int)Math.round(GameConstants.getExpNeededForLevel((this.getPlayer().getLevel() > maxLevel) ? (maxLevel + this.getPlayer().getLevel() / 10) : this.getPlayer().getLevel()) / (Math.min(this.getPlayer().getLevel(), maxLevel) / 10.0) / mod);
            this.gainExp(amount);
            return;
        }
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                final int amount2 = (int)Math.round(GameConstants.getExpNeededForLevel((curChar.getLevel() > maxLevel) ? (maxLevel + curChar.getLevel() / 10) : curChar.getLevel()) / (Math.min(curChar.getLevel(), maxLevel) / 10.0) / mod);
                curChar.gainExp(amount2, true, true, true);
            }
        }
    }
    
    public void playerMessage(final String message) {
        this.playerMessage(5, message);
    }
    
    public void mapMessage(final String message) {
        this.mapMessage(5, message);
    }
    
    public void guildMessage(final String message) {
        this.guildMessage(5, message);
    }
    
    public void playerMessage(final int type, final String message) {
        this.c.getSession().write(MaplePacketCreator.serverNotice(type, message));
    }
    
    public void mapMessage(final int type, final String message) {
        this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(type, message));
    }
    
    public void guildMessage(final int type, final String message) {
        if (this.getPlayer().getGuildId() > 0) {
            World.Guild.guildPacket(this.getPlayer().getGuildId(), MaplePacketCreator.serverNotice(type, message));
        }
    }
    
    public MapleGuild getGuild() {
        return this.getGuild(this.getPlayer().getGuildId());
    }
    
    public MapleGuild getGuild(final int guildid) {
        return World.Guild.getGuild(guildid);
    }
    
    public MapleParty getParty() {
        return this.c.getPlayer().getParty();
    }
    
    public int getCurrentPartyId(final int mapid) {
        return this.getMap(mapid).getCurrentPartyId();
    }
    
    public void czdt(final int MapID) {
        final MapleCharacter player = this.c.getPlayer();
        final int mapid = MapID;
        final MapleMap map = player.getMap();
        if (player.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>(map.getCharacters());
            for (final MapleCharacter m : mcs) {
                final int x = 0;
                if (x < 5) {
                    continue;
                }
            }
        }
    }
    
    public boolean isLeader() {
        return this.getParty() != null && this.getParty().getLeader().getId() == this.c.getPlayer().getId();
    }
    
    public boolean isParty() {
        return this.getParty() != null;
    }
    
    public boolean isAllPartyMembersAllowedJob(final int job) {
        if (this.c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : this.c.getPlayer().getParty().getMembers()) {
            if (mem.getJobId() / 100 != job) {
                return false;
            }
        }
        return true;
    }
    
    public boolean allMembersHere() {
        if (this.c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : this.c.getPlayer().getParty().getMembers()) {
            final MapleCharacter chr = this.c.getPlayer().getMap().getCharacterById(mem.getId());
            if (chr == null) {
                return false;
            }
        }
        return true;
    }
    
    public void resetPartyBossLog(final String bosslog) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().resetBossLog(bosslog);
            return;
        }
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.resetBossLog(bosslog);
            }
        }
    }
    
    public void warpParty(final int mapId) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.warp(mapId, 0);
            return;
        }
        final MapleMap target = this.getMap(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }
    
    public void warpParty(final int mapId, final String portal) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.warp(mapId, portal);
            return;
        }
        final MapleMap target = this.getMap(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(portal));
            }
        }
    }
    
    public void warpParty(final int mapId, final int portal) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (portal < 0) {
                this.warp(mapId);
            }
            else {
                this.warp(mapId, portal);
            }
            return;
        }
        final boolean rand = portal < 0;
        final MapleMap target = this.getMap(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                if (rand) {
                    try {
                        curChar.changeMap(target, target.getPortal(Randomizer.nextInt(target.getPortals().size())));
                    }
                    catch (Exception e) {
                        curChar.changeMap(target, target.getPortal(0));
                    }
                }
                else {
                    curChar.changeMap(target, target.getPortal(portal));
                }
            }
        }
    }
    
    public void warpParty_Instanced(final int mapId) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.warp_Instanced(mapId);
            return;
        }
        final MapleMap target = this.getMap_Instanced(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }
    
    public void gainDY(final int gain) {
        this.c.getPlayer().modifyCSPoints(2, gain, true);
    }
    
    public void gainMeso(final int gain) {
        this.c.getPlayer().gainMeso(gain, true, false, true);
    }
    
    public void gainExp(final int gain) {
        this.c.getPlayer().gainExp(gain, true, true, true);
    }
    
    public void gainExpR(final int gain) {
        this.c.getPlayer().gainExp(gain * this.c.getChannelServer().getExpRate(), true, true, true);
    }
    
    public void givePartyItems(final int id, final short quantity, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            if (quantity >= 0) {
                MapleInventoryManipulator.addById(chr.getClient(), id, quantity, (byte)0);
            }
            else {
                MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(id), id, -quantity, true, false);
            }
            chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
        }
    }
    
    public void 给组队物品(final int id, final short quantity) {
        this.givePartyItems(id, quantity, false);
    }
    
    public void 给予组队经验队长双倍(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            if (party.equals(chr.getParty().getLeader())) {
                this.getPlayer().dropMessage(5, "由于你是队长系统给了你双倍的经验值");
                chr.gainExp(amount * 2, true, true, true);
            }
            else {
                chr.gainExp(amount, true, true, true);
            }
        }
    }
    
    public void 给予组队物品队长双倍(final int id, final short quantity, final boolean removeAll) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainItem(id, (short)(removeAll ? (-this.getPlayer().itemQuantity(id)) : quantity));
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                if (chr.equals(this.getPlayer().getParty().getLeader())) {
                    this.getPlayer().dropMessage(5, "由于你是队长系统给了你双倍数量的奖励");
                    this.gainItem(id, (short)(removeAll ? (-curChar.itemQuantity(id)) : (quantity * 2)), false, 0L, 0, "", curChar.getClient(), (byte)0);
                }
                else {
                    this.gainItem(id, (short)(removeAll ? (-curChar.itemQuantity(id)) : quantity), false, 0L, 0, "", curChar.getClient(), (byte)0);
                }
            }
        }
    }
    
    public void givePartyItems(final int id, final short quantity, final boolean removeAll) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainItem(id, (short)(removeAll ? (-this.getPlayer().itemQuantity(id)) : quantity));
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                this.gainItem(id, (short)(removeAll ? (-curChar.itemQuantity(id)) : quantity), false, 0L, 0, "", curChar.getClient(), (byte)0);
            }
        }
    }
    
    public void givePartyExp(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.gainExp(amount, true, true, true);
        }
    }
    
    public void 给组队经验(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainExp(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.gainExp(amount, true, true, true);
            }
        }
    }
    
    public void givePartyNX(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.modifyCSPoints(1, amount, true);
        }
    }
    
    public void 给组队抵用卷(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainDY(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.modifyCSPoints(2, amount, true);
            }
        }
    }
    
    public void 给组队金币(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainMeso(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.gainMeso(amount, true);
            }
        }
    }
    
    public void 给组队点卷(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainNX(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.modifyCSPoints(1, amount, true);
            }
        }
    }
    
    public void givePartyFb(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (this.getPlayer().getmrfbrws() > this.getFBRW()) {
                this.gainFBRW(amount);
            }
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null && curChar.getmrfbrws() > curChar.getFBRW()) {
                curChar.gainFBRW(amount);
            }
        }
    }
    
    public void givePartyFba(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (this.getPlayer().getmrfbrwas() > this.getFBRWA()) {
                this.gainFBRWA(amount);
            }
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null && curChar.getmrfbrwas() > curChar.getFBRWA()) {
                curChar.gainFBRWA(amount);
            }
        }
    }
    
    public void endPartyQuest(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.endPartyQuest(amount);
        }
    }
    
    public void endPartyQuest(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().endPartyQuest(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.endPartyQuest(amount);
            }
        }
    }
    
    public void removeFromParty(final int id, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int possesed = chr.getInventory(GameConstants.getInventoryType(id)).countById(id);
            if (possesed > 0) {
                MapleInventoryManipulator.removeById(this.c, GameConstants.getInventoryType(id), id, possesed, true, false);
                chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, (short)(-possesed), true));
            }
        }
    }
    
    public void removeFromParty(final int id) {
        this.givePartyItems(id, (short)0, true);
    }
    
    public void useSkill(final int skill, final int level) {
        if (level <= 0) {
            return;
        }
        SkillFactory.getSkill(skill).getEffect(level).applyTo(this.c.getPlayer());
    }
    
    public void useItem(final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(this.c.getPlayer());
        this.c.getSession().write(UIPacket.getStatusMsg(id));
    }
    
    public void cancelItem(final int id) {
        this.c.getPlayer().cancelEffect(MapleItemInformationProvider.getInstance().getItemEffect(id), false, -1L);
    }
    
    public int getMorphState() {
        return this.c.getPlayer().getMorphState();
    }
    
    public void removeAll(final int id) {
        this.c.getPlayer().removeAll(id);
    }
    
    public void gainCloseness(final int closeness, final int index) {
        final MaplePet pet = this.getPlayer().getPet(index);
        if (pet != null) {
            pet.setCloseness(pet.getCloseness() + closeness);
            this.getClient().getSession().write(PetPacket.updatePet(pet, this.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte)pet.getInventoryPosition()), true));
        }
    }
    
    public void gainClosenessAll(final int closeness) {
        for (final MaplePet pet : this.getPlayer().getPets()) {
            if (pet != null) {
                pet.setCloseness(pet.getCloseness() + closeness);
                this.getClient().getSession().write(PetPacket.updatePet(pet, this.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte)pet.getInventoryPosition()), true));
            }
        }
    }
    
    public void resetMap(final int mapid) {
        this.getMap(mapid).resetFully();
    }
    
    public void resetMapS() {
        final MapleCharacter player = this.c.getPlayer();
        final int mapid = player.getMapId();
        final MapleMap map = player.getMap();
        if (player.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
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
    
    public void openNpc(final int id) {
        NPCScriptManager.getInstance().start(this.getClient(), id);
    }
    
    public void openNpc(final int id, final int wh) {
        NPCScriptManager.getInstance().start(this.getClient(), id, wh);
    }
    
    public void serverNotice(final String Text) {
        this.getClient().getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, Text));
    }
    
    public void openNpc(final MapleClient cg, final int id) {
        NPCScriptManager.getInstance().start(cg, id);
    }
    
    public int getMapId() {
        return this.c.getPlayer().getMap().getId();
    }
    
    public boolean haveMonster(final int mobid) {
        for (final MapleMapObject obj : this.c.getPlayer().getMap().getAllMonstersThreadsafe()) {
            final MapleMonster mob = (MapleMonster)obj;
            if (mob.getId() == mobid) {
                return true;
            }
        }
        return false;
    }
    
    public int getChannelNumber() {
        return this.c.getChannel();
    }
    
    public int getMonsterCount(final int mapid) {
        return this.c.getChannelServer().getMapFactory().getMap(mapid).getNumMonsters();
    }
    
    public void teachSkill(final int id, final byte level, final byte masterlevel) {
        this.getPlayer().changeSkillLevel(SkillFactory.getSkill(id), level, masterlevel);
    }
    
    public void teachSkill(final int id, byte level) {
        final ISkill skil = SkillFactory.getSkill(id);
        if (this.getPlayer().getSkillLevel(skil) > level) {
            level = this.getPlayer().getSkillLevel(skil);
        }
        this.getPlayer().changeSkillLevel(skil, level, skil.getMaxLevel());
    }
    
    public int getPlayerCount(final int mapid) {
        return this.c.getChannelServer().getMapFactory().getMap(mapid).getCharactersSize();
    }
    
    public int getMobCount(final int mapId) {
        return this.c.getChannelServer().getMapFactory().getMap(mapId).getNumMonsters();
    }
    
    public void dojo_getUp() {
        this.c.getSession().write(MaplePacketCreator.updateInfoQuest(1207, "pt=1;min=4;belt=1;tuto=1"));
        this.c.getSession().write(MaplePacketCreator.dojoWarpUp());
    }
    
    public boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting) {
        if (dojo) {
            return Event_DojoAgent.warpNextMap(this.c.getPlayer(), fromresting);
        }
        return Event_DojoAgent.warpNextMap_Agent(this.c.getPlayer(), fromresting);
    }
    
    public int dojo_getPts() {
        return this.c.getPlayer().getDojo();
    }
    
    public byte getShopType() {
        return this.c.getPlayer().getPlayerShop().getShopType();
    }
    
    public MapleEvent getEvent(final String loc) {
        return this.c.getChannelServer().getEvent(MapleEventType.valueOf(loc));
    }
    
    public int getSavedLocation(final String loc) {
        final Integer ret = this.c.getPlayer().getSavedLocation(SavedLocationType.fromString(loc));
        if (ret == null || ret == -1) {
            return 100000000;
        }
        return ret;
    }
    
    public void saveLocation(final String loc) {
        this.c.getPlayer().saveLocation(SavedLocationType.fromString(loc));
    }
    
    public void saveReturnLocation(final String loc) {
        this.c.getPlayer().saveLocation(SavedLocationType.fromString(loc), this.c.getPlayer().getMap().getReturnMap().getId());
    }
    
    public void clearSavedLocation(final String loc) {
        this.c.getPlayer().clearSavedLocation(SavedLocationType.fromString(loc));
    }
    
    public void summonMsg(final String msg) {
        if (!this.c.getPlayer().hasSummon()) {
            this.playerSummonHint(true);
        }
        this.c.getSession().write(UIPacket.summonMessage(msg));
    }
    
    public void summonMsg(final int type) {
        if (!this.c.getPlayer().hasSummon()) {
            this.playerSummonHint(true);
        }
        this.c.getSession().write(UIPacket.summonMessage(type));
    }
    
    public void HSText(final String msg) {
        this.c.getSession().write(MaplePacketCreator.HSText(msg));
    }
    
    public void showInstruction(final String msg, final int width, final int height) {
        this.c.getSession().write(MaplePacketCreator.sendHint(msg, width, height));
    }
    
    public void playerSummonHint(final boolean summon) {
        this.c.getPlayer().setHasSummon(summon);
        this.c.getSession().write(UIPacket.summonHelper(summon));
    }
    
    public String getInfoQuest(final int id) {
        return this.c.getPlayer().getInfoQuest(id);
    }
    
    public void updateInfoQuest(final int id, final String data) {
        this.c.getPlayer().updateInfoQuest(id, data);
    }
    
    public boolean getEvanIntroState(final String data) {
        return this.getInfoQuest(22013).equals(data);
    }
    
    public void updateEvanIntroState(final String data) {
        this.updateInfoQuest(22013, data);
    }
    
    public void Aran_Start() {
        this.c.getSession().write(UIPacket.Aran_Start());
    }
    
    public void evanTutorial(final String data, final int v1) {
        this.c.getSession().write(MaplePacketCreator.getEvanTutorial(data));
    }
    
    public void AranTutInstructionalBubble(final String data) {
        this.c.getSession().write(UIPacket.AranTutInstructionalBalloon(data));
    }
    
    public void ShowWZEffect(final String data) {
        this.c.getSession().write(UIPacket.AranTutInstructionalBalloon(data));
    }
    
    public void showWZEffect(final String data, final int info) {
        this.c.getSession().write(UIPacket.ShowWZEffect(data, info));
    }
    
    public void MovieClipIntroUI(final boolean enabled) {
        this.c.getSession().write(UIPacket.IntroDisableUI(enabled));
        this.c.getSession().write(UIPacket.IntroLock(enabled));
    }
    
    public MapleInventoryType getInvType(final int i) {
        return MapleInventoryType.getByType((byte)i);
    }
    
    public String getItemName(final int id) {
        return MapleItemInformationProvider.getInstance().getName(id);
    }
    
    public void gainPet(int id, String name, int level, int closeness, int fullness, final long period) {
        if (id > 5010000 || id < 5000000) {
            id = 5000000;
        }
        if (level > 30) {
            level = 30;
        }
        if (closeness > 30000) {
            closeness = 30000;
        }
        if (fullness > 100) {
            fullness = 100;
        }
        name = this.getItemName(id);
        try {
            MapleInventoryManipulator.addById(this.c, id, (short)1, "", MaplePet.createPet(id, name, level, closeness, fullness, MapleInventoryIdentifier.getInstance(), (id == 5000054) ? ((int)period) : 0), period, (byte)0);
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeSlot(final int invType, final byte slot, final short quantity) {
        MapleInventoryManipulator.removeFromSlot(this.c, this.getInvType(invType), slot, quantity, true);
    }
    
    public void gainGP(final int gp) {
        if (this.getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.gainGP(this.getPlayer().getGuildId(), gp);
    }
    
    public int getGP() {
        if (this.getPlayer().getGuildId() <= 0) {
            return 0;
        }
        return World.Guild.getGP(this.getPlayer().getGuildId());
    }
    
    public void showMapEffect(final String path) {
        this.getClient().getSession().write(UIPacket.MapEff(path));
    }
    
    public int 判断物品数量(final int itemid) {
        return this.getPlayer().itemQuantity(itemid);
    }
    
    public EventInstanceManager getDisconnected(final String event) {
        final EventManager em = this.getEventManager(event);
        if (em == null) {
            return null;
        }
        for (final EventInstanceManager eim : em.getInstances()) {
            if (eim.isDisconnected(this.c.getPlayer()) && eim.getPlayerCount() > 0) {
                return eim;
            }
        }
        return null;
    }
    
    public boolean isAllReactorState(final int reactorId, final int state) {
        boolean ret = false;
        for (final MapleReactor r : this.getMap().getAllReactorsThreadsafe()) {
            if (r.getReactorId() == reactorId) {
                ret = (r.getState() == state);
            }
        }
        return ret;
    }
    
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
    
    public void spawnMonster(final int id) {
        this.spawnMonster(id, 1, new Point(this.getPlayer().getPosition()));
    }
    
    public void spawnMonster(final int id, final int x, final int y) {
        this.spawnMonster(id, 1, new Point(x, y));
    }
    
    public void spawnMonster(final int id, final int qty, final int x, final int y) {
        this.spawnMonster(id, qty, new Point(x, y));
    }
    
    public void spawnMonster(final int id, final int qty, final Point pos) {
        for (int i = 0; i < qty; ++i) {
            this.getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }
    
    public void sendNPCText(final String text, final int npc) {
        this.getMap().broadcastMessage(MaplePacketCreator.getNPCTalk(npc, (byte)0, text, "00 00", (byte)0));
    }
    
    public boolean getTempFlag(final int flag) {
        return (this.c.getChannelServer().getTempFlag() & flag) == flag;
    }
    
    public int getGamePoints() {
        return this.c.getPlayer().getGamePoints();
    }
    
    public void gainGamePoints(final int amount) {
        this.c.getPlayer().gainGamePoints(amount);
    }
    
    public void resetGamePoints() {
        this.c.getPlayer().resetGamePoints();
    }
    
    public int getGamePointsPD() {
        return this.c.getPlayer().getGamePointsPD();
    }
    
    public void gainGamePointsPD(final int amount) {
        this.c.getPlayer().gainGamePointsPD(amount);
    }
    
    public void resetGamePointsPD() {
        this.c.getPlayer().resetGamePointsPD();
    }
    
    public int getPS() {
        return this.c.getPlayer().getGamePointsPS();
    }
    
    public void gainPS(final int amount) {
        this.c.getPlayer().gainGamePointsPS(amount);
    }
    
    public void resetPS() {
        this.c.getPlayer().resetGamePointsPS();
    }
    
    public int getskillzq() {
        return this.c.getPlayer().getskillzq();
    }
    
    public void setskillzq(final int amount) {
        this.c.getPlayer().setskillzq(amount);
    }
    
    public int getscjs() {
        return this.c.getPlayer().getskillzq();
    }
    
    public int getSJRW() {
        return this.c.getPlayer().getSJRW();
    }
    
    public void gainSJRW(final int amount) {
        this.c.getPlayer().gainSJRW(amount);
    }
    
    public void resetSJRW() {
        this.c.getPlayer().resetSJRW();
    }
    
    public int getFBRW() {
        return this.c.getPlayer().getFBRW();
    }
    
    public void gainFBRW(final int amount) {
        this.c.getPlayer().gainFBRW(amount);
    }
    
    public void resetFBRW() {
        this.c.getPlayer().resetFBRW();
    }
    
    public int getFBRWA() {
        return this.c.getPlayer().getFBRWA();
    }
    
    public void gainFBRWA(final int amount) {
        this.c.getPlayer().gainFBRWA(amount);
    }
    
    public void resetFBRWA() {
        this.c.getPlayer().resetFBRWA();
    }
    
    public int getSGRW() {
        return this.c.getPlayer().getSGRW();
    }
    
    public void gainSGRW(final int amount) {
        this.c.getPlayer().gainSGRW(amount);
    }
    
    public void resetSGRW() {
        this.c.getPlayer().resetSGRW();
    }
    
    public int getSGRWA() {
        return this.c.getPlayer().getSGRWA();
    }
    
    public void gainSGRWA(final int amount) {
        this.c.getPlayer().gainSGRWA(amount);
    }
    
    public void resetSGRWA() {
        this.c.getPlayer().resetSGRWA();
    }
    
    public int getSBOSSRW() {
        return this.c.getPlayer().getSBOSSRW();
    }
    
    public void gainSBOSSRW(final int amount) {
        this.c.getPlayer().gainSBOSSRW(amount);
    }
    
    public void resetSBOSSRW() {
        this.c.getPlayer().resetSBOSSRW();
    }
    
    public int getSBOSSRWA() {
        return this.c.getPlayer().getSBOSSRWA();
    }
    
    public void gainSBOSSRWA(final int amount) {
        this.c.getPlayer().gainSBOSSRWA(amount);
    }
    
    public void resetSBOSSRWA() {
        this.c.getPlayer().resetSBOSSRWA();
    }
    
    public int getlb() {
        return this.c.getPlayer().getlb();
    }
    
    public void gainlb(final int amount) {
        this.c.getPlayer().gainlb(amount);
    }
    
    public void resetlb() {
        this.c.getPlayer().resetlb();
    }
    
    public int getvip() {
        return this.c.getPlayer().getvip();
    }
    
    public void gainvip(final int amount) {
        this.c.getPlayer().gainvip(amount);
    }
    
    public void setvip(final int s) {
        this.c.getPlayer().setvip(s);
    }
    
    public boolean beibao(final int A) {
        return (this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() > -1 && A == 1) || (this.c.getPlayer().getInventory(MapleInventoryType.USE).getNextFreeSlot() > -1 && A == 2) || (this.c.getPlayer().getInventory(MapleInventoryType.SETUP).getNextFreeSlot() > -1 && A == 3) || (this.c.getPlayer().getInventory(MapleInventoryType.ETC).getNextFreeSlot() > -1 && A == 4) || (this.c.getPlayer().getInventory(MapleInventoryType.CASH).getNextFreeSlot() > -1 && A == 5);
    }
    
    public void 记录组队bosslog(final int bossid) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().setbosslog(bossid);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.setbosslog(bossid);
            }
        }
    }
    
    public boolean beibao(final int A, final int kw) {
        return (this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() > kw && A == 1) || (this.c.getPlayer().getInventory(MapleInventoryType.USE).getNextFreeSlot() > kw && A == 2) || (this.c.getPlayer().getInventory(MapleInventoryType.SETUP).getNextFreeSlot() > kw && A == 3) || (this.c.getPlayer().getInventory(MapleInventoryType.ETC).getNextFreeSlot() > kw && A == 4) || (this.c.getPlayer().getInventory(MapleInventoryType.CASH).getNextFreeSlot() > kw && A == 5);
    }
    
    public int getFishingJF() {
        return this.c.getPlayer().getFishingJF(1);
    }
    
    public void gainFishingJF(final int amount) {
        this.c.getPlayer().gainFishingJF(amount);
    }
    
    public void addFishingJF(final int amount) {
        this.c.getPlayer().addFishingJF(amount);
    }
    
    public void openWeb(final String web) {
        this.c.getSession().write(MaplePacketCreator.openWeb(web));
    }
    
    public int getBossLog(final String bossid) {
        return this.getPlayer().getBossLog(bossid);
    }
    
    public int getBossLogType(final String bossid) {
        return this.getPlayer().getBossLogType(bossid);
    }
    
    public void setBossLog(final String bossid) {
        this.getPlayer().setBossLog(bossid);
    }
    
    public void setBossLog(final String bossid, final int type) {
        this.getPlayer().setBossLog(bossid, type);
    }
    
    public void resetBossLog(final String bossid) {
        this.getPlayer().resetBossLog(bossid);
    }
    
    public void givePartyBossLog(final String bossid) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.setBossLog(bossid);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.setBossLog(bossid);
            }
        }
    }
    
    public int getOneTimeLog(final String bossid) {
        return this.getPlayer().getOneTimeLog(bossid);
    }
    
    public void setOneTimeLog(final String bossid) {
        this.getPlayer().setOneTimeLog(bossid);
    }
    
    public void resetAp() {
        final boolean beginner = this.getPlayer().getJob() == 0 || this.getPlayer().getJob() == 1000 || this.getPlayer().getJob() == 2001;
        this.getPlayer().resetStatsByJob(beginner);
    }
    
    public void displayGuide(final int guide) {
        this.c.getSession().write(MaplePacketCreator.displayGuide(guide));
    }
    
    public void lockUI() {
        this.c.getPlayer();
        MapleCharacter.tutorial = true;
        this.c.getSession().write(UIPacket.IntroLock(true));
        this.c.getSession().write(UIPacket.IntroDisableUI(true));
    }
    
    public void unlockUI() {
        this.c.getPlayer();
        MapleCharacter.tutorial = false;
        this.c.getSession().write(UIPacket.IntroLock(false));
        this.c.getSession().write(UIPacket.IntroDisableUI(false));
    }
    
    public int MarrageChecking() {
        if (this.getPlayer().getParty() == null) {
            return -1;
        }
        if (this.getPlayer().getMarriageId() > 0) {
            return 0;
        }
        if (this.getPlayer().getParty().getMembers().size() != 2) {
            return 1;
        }
        if (this.getPlayer().getGender() == 0 && !this.getPlayer().haveItem(1050121) && !this.getPlayer().haveItem(1050122) && !this.getPlayer().haveItem(1050113)) {
            return 5;
        }
        if (this.getPlayer().getGender() == 1 && !this.getPlayer().haveItem(1051129) && !this.getPlayer().haveItem(1051130) && !this.getPlayer().haveItem(1051114)) {
            return 5;
        }
        if (!this.getPlayer().haveItem(1112001)) {
            return 6;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            if (chr.getId() == this.getPlayer().getId()) {
                continue;
            }
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar == null) {
                return 2;
            }
            if (curChar.getMarriageId() > 0) {
                return 3;
            }
            if (curChar.getGender() == this.getPlayer().getGender()) {
                return 4;
            }
            if (curChar.getGender() == 0 && !curChar.haveItem(1050121) && !curChar.haveItem(1050122) && !curChar.haveItem(1050113)) {
                return 5;
            }
            if (curChar.getGender() == 1 && !curChar.haveItem(1051129) && !curChar.haveItem(1051130) && !curChar.haveItem(1051114)) {
                return 5;
            }
            if (!curChar.haveItem(1112001)) {
                return 6;
            }
        }
        return 9;
    }
    
    public int getPartyFormID() {
        int curCharID = -1;
        if (this.getPlayer().getParty() == null) {
            curCharID = -1;
        }
        else if (this.getPlayer().getMarriageId() > 0) {
            curCharID = -2;
        }
        else if (this.getPlayer().getParty().getMembers().size() != 2) {
            curCharID = -3;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            if (chr.getId() == this.getPlayer().getId()) {
                continue;
            }
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar == null) {
                curCharID = -4;
            }
            else {
                curCharID = chr.getId();
            }
        }
        return curCharID;
    }
    
    public void warpByName(final int mapid, final String chrname) {
        final MapleCharacter chr = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chrname);
        if (chr == null) {
            this.c.getPlayer().dropMessage(1, "找不到这个角色。");
            this.c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final MapleMap mapz = this.getWarpMap(mapid);
        try {
            chr.changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
            chr.getClient().removeClickedNPC();
            NPCScriptManager.getInstance().dispose(chr.getClient());
            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
        }
        catch (Exception e) {
            chr.changeMap(mapz, mapz.getPortal(0));
            chr.getClient().removeClickedNPC();
            NPCScriptManager.getInstance().dispose(chr.getClient());
            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
        }
    }
    
    public void mapChangeTimer(final int map, final int nextmap, final int time, final boolean notice) {
        final List<MapleCharacter> current = this.c.getChannelServer().getMapFactory().getMap(map).getCharacters();
        this.c.getChannelServer().getMapFactory().getMap(map).broadcastMessage(MaplePacketCreator.getClock(time));
        if (notice) {
            this.c.getChannelServer().getMapFactory().getMap(map).startMapEffect("当计时器结束时，您将被移出地图。", 5120041);
        }
        Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (current != null) {
                    for (final MapleCharacter chrs : current) {
                        chrs.changeMap(nextmap, 0);
                    }
                }
            }
        }, time * 1000);
    }
    
    public void hideNpc(final int npcId) {
        this.c.getPlayer().getMap().hideNpc(npcId);
    }
    
    public void respawn(final boolean force) {
        this.c.getPlayer().getMap().respawn(force);
    }
    
    public boolean touzhu(final int type, final int nx, final int num) {
        boolean flage = false;
        if (nx >= 50) {
            final CherryMSLottery cherryMSLottery = CherryMScustomEventFactory.getInstance().getCherryMSLottery();
            this.getPlayer().setTouzhuType(type);
            this.getPlayer().setTouzhuNX(nx);
            this.getPlayer().setTouzhuNum(num);
            this.getPlayer().modifyCSPoints(1, -nx);
            cherryMSLottery.addChar(this.getPlayer());
            flage = true;
        }
        else {
            flage = false;
        }
        return flage;
    }
    
    public int seeTouzhuByType(final int type) {
        return CherryMScustomEventFactory.getInstance().getCherryMSLottery().getTouNumbyType(type);
    }
    
    public long seeAlltouzhu() {
        return CherryMScustomEventFactory.getInstance().getCherryMSLottery().getAlltouzhu();
    }
    
    public long seeAllpeichu() {
        return CherryMScustomEventFactory.getInstance().getCherryMSLottery().getAllpeichu();
    }
    
    public List<IItem> getItemsByType(final byte type) {
        final List<IItem> items = new ArrayList<IItem>();
        final MapleInventoryType itemtype = MapleInventoryType.getByType(type);
        final MapleInventory mi = this.getPlayer().getInventory(itemtype);
        if (mi != null) {
            for (final IItem item : mi.list()) {
                items.add(item);
            }
        }
        return items;
    }
    
    public int 获取当前星期() {
        return Calendar.getInstance().get(7);
    }
    
    public String getServerName() {
        return ServerProperties.getProperty("RoyMS.ServerName");
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
    
    public List<BossRankInfo> getBossRankPointsTop(final String bossname) {
        return BossRankManager.getInstance().getRank(bossname, 1);
    }
    
    public List<BossRankInfo> getBossRankCountTop(final String bossname) {
        return BossRankManager.getInstance().getRank(bossname, 2);
    }
    
    public List<BossRankInfo> getBossRankTop(final String bossname, final byte type) {
        return BossRankManager.getInstance().getRank(bossname, type);
    }
    
    public int setBossRankPoints(final String bossname) {
        return this.setBossRank(this.getPlayer().getId(), this.getPlayer().getName(), bossname, (byte)1, 1);
    }
    
    public int setBossRankCount(final String bossname) {
        return this.setBossRank(this.getPlayer().getId(), this.getPlayer().getName(), bossname, (byte)2, 1);
    }
    
    public int setBossRankPoints(final String bossname, final int add) {
        return this.setBossRank(this.getPlayer().getId(), this.getPlayer().getName(), bossname, (byte)1, add);
    }
    
    public int setBossRankCount(final String bossname, final int add) {
        return this.setBossRank(this.getPlayer().getId(), this.getPlayer().getName(), bossname, (byte)2, add);
    }
    
    public int setBossRank(final String bossname, final byte type, final int add) {
        return this.setBossRank(this.getPlayer().getId(), this.getPlayer().getName(), bossname, type, add);
    }
    
    public int setBossRank(final int cid, final String cname, final String bossname, final byte type, final int add) {
        return BossRankManager.getInstance().setLog(cid, cname, bossname, type, add);
    }
    
    public int getBossRankPoints(final String bossname) {
        return this.getBossRank(bossname, (byte)1);
    }
    
    public int getBossRankCount(final String bossname) {
        return this.getBossRank(bossname, (byte)2);
    }
    
    public int getBossRank(final String bossname, final byte type) {
        return this.getBossRank(this.getPlayer().getId(), bossname, type);
    }
    
    public int getBossRank(final int cid, final String bossname, final byte type) {
        int ret = -1;
        final BossRankInfo info = BossRankManager.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1: {
                ret = info.getPoints();
                break;
            }
            case 2: {
                ret = info.getCount();
                break;
            }
        }
        return ret;
    }
    
    public int auction_putIn(final IItem source, final short quantity) {
        return AuctionManager.getInstance().putInt(this.getPlayer(), source, quantity);
    }
    
    public int auction_takeOutAuctionItem(final long id) {
        return AuctionManager.getInstance().takeOutAuctionItem(this.getPlayer(), id);
    }
    
    public int auction_setPutaway(final long id, final int price) {
        return AuctionManager.getInstance().setPutaway(id, price);
    }
    
    public int auction_soldOut(final long id) {
        return AuctionManager.getInstance().soldOut(id);
    }
    
    public int auction_buy(final long id) {
        return AuctionManager.getInstance().buy(this.getPlayer(), id);
    }
    
    public AuctionPoint auction_getAuctionPoint() {
        return AuctionManager.getInstance().getAuctionPoint(this.getPlayer().getId());
    }
    
    public AuctionPoint auction_getAuctionPoint(final int characterid) {
        return AuctionManager.getInstance().getAuctionPoint(characterid);
    }
    
    public int auction_addPoint(final long point) {
        return AuctionManager.getInstance().addPoint(this.getPlayer().getId(), point);
    }
    
    public AuctionItem auction_findById(final long id) {
        return AuctionManager.getInstance().findById(id);
    }
    
    public List<AuctionItem> auction_findByCharacterId() {
        return AuctionManager.getInstance().findByCharacterId(this.getPlayer().getId());
    }
    
    public List<AuctionItem> auction_findByCharacterId(final int characterid) {
        return AuctionManager.getInstance().findByCharacterId(characterid);
    }
    
    public List<AuctionItem> auction_findByItemType(final int inventorytype) {
        return AuctionManager.getInstance().findByItemType(inventorytype);
    }
    
    public int auction_deletePlayerSold() {
        return AuctionManager.getInstance().deletePlayerSold(this.getPlayer().getId());
    }
    
    public int itemQuantity(final int itemid) {
        return this.getPlayer().itemQuantity(itemid);
    }
    
    public void debug(final String s) {
        System.out.println(s);
    }
}
