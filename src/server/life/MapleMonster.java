package server.life;

import client.ISkill;
import client.MapleBuffStat;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleDisease;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import constants.GameConstants;
import constants.ServerConstants;
import handling.MaplePacket;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import scripting.EventInstanceManager;
import server.MapleItemInformationProvider;
import server.Randomizer;
import server.Timer;
import server.maps.MapScriptMethods;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.ConcurrentEnumMap;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.packet.MobPacket;

public class MapleMonster extends AbstractLoadedMapleLife
{
    private final ReentrantReadWriteLock poisonsLock;
    private final LinkedList<MonsterStatusEffect> poisons;
    private MapleMonsterStats overrideStats;
    private MapleMonsterStats stats;
    private OverrideMonsterStats ostats;
    private long hp;
    private int mp;
    private byte venom_counter;
    private byte carnivalTeam;
    private MapleMap map;
    private WeakReference<MapleMonster> sponge;
    private int linkoid;
    private int lastNode;
    private int lastNodeController;
    private int highestDamageChar;
    private WeakReference<MapleCharacter> controller;
    private boolean fake;
    private boolean dropsDisabled;
    private boolean controllerHasAggro;
    private boolean controllerKnowsAboutAggro;
    private Collection<AttackerEntry> attackers;
    private EventInstanceManager eventInstance;
    private MonsterListener listener;
    private MaplePacket reflectpack;
    private MaplePacket nodepack;
    private Map<MonsterStatus, MonsterStatusEffect> stati;
    private Map<Integer, Long> usedSkills;
    private int stolen;
    private ScheduledFuture<?> dropItemSchedule;
    private boolean shouldDropItem;
    
    public MapleMonster(final int id, final MapleMonsterStats stats) {
        super(id);
        this.poisonsLock = new ReentrantReadWriteLock();
        this.poisons = new LinkedList<MonsterStatusEffect>();
        this.ostats = null;
        this.sponge = new WeakReference<MapleMonster>(null);
        this.linkoid = 0;
        this.lastNode = -1;
        this.lastNodeController = -1;
        this.highestDamageChar = 0;
        this.controller = new WeakReference<MapleCharacter>(null);
        this.attackers = new LinkedList<AttackerEntry>();
        this.listener = null;
        this.reflectpack = null;
        this.nodepack = null;
        this.stati = new ConcurrentEnumMap<MonsterStatus, MonsterStatusEffect>(MonsterStatus.class);
        this.stolen = -1;
        this.shouldDropItem = false;
        this.initWithStats(stats);
    }
    
    public MapleMonster(final MapleMonster monster) {
        super(monster);
        this.poisonsLock = new ReentrantReadWriteLock();
        this.poisons = new LinkedList<MonsterStatusEffect>();
        this.ostats = null;
        this.sponge = new WeakReference<MapleMonster>(null);
        this.linkoid = 0;
        this.lastNode = -1;
        this.lastNodeController = -1;
        this.highestDamageChar = 0;
        this.controller = new WeakReference<MapleCharacter>(null);
        this.attackers = new LinkedList<AttackerEntry>();
        this.listener = null;
        this.reflectpack = null;
        this.nodepack = null;
        this.stati = new ConcurrentEnumMap<MonsterStatus, MonsterStatusEffect>(MonsterStatus.class);
        this.stolen = -1;
        this.shouldDropItem = false;
        this.initWithStats(monster.stats);
    }
    
    private void initWithStats(final MapleMonsterStats stats) {
        this.setStance(5);
        this.stats = stats;
        this.hp = stats.getHp();
        this.mp = stats.getMp();
        this.venom_counter = 0;
        this.carnivalTeam = -1;
        this.fake = false;
        this.dropsDisabled = false;
        if (stats.getNoSkills() > 0) {
            this.usedSkills = new HashMap<Integer, Long>();
        }
    }
    
    public MapleMonsterStats getStats() {
        return this.stats;
    }
    
    public void disableDrops() {
        this.dropsDisabled = true;
    }
    
    public boolean dropsDisabled() {
        return this.dropsDisabled;
    }
    
    public void setSponge(final MapleMonster mob) {
        this.sponge = new WeakReference<MapleMonster>(mob);
    }
    
    public void setMap(final MapleMap map) {
        this.map = map;
        this.startDropItemSchedule();
    }
    
    public long getHp() {
        return this.hp;
    }
    
    public void setHp(final long hp) {
        this.hp = hp;
    }
    
    public long getMobMaxHp() {
        if (this.ostats != null) {
            return this.ostats.getHp();
        }
        return this.stats.getHp();
    }
    
    public int getMp() {
        return this.mp;
    }
    
    public void setMp(int mp) {
        if (mp < 0) {
            mp = 0;
        }
        this.mp = mp;
    }
    
    public int getMobMaxMp() {
        if (this.ostats != null) {
            return this.ostats.getMp();
        }
        return this.stats.getMp();
    }
    
    public int getMobExp() {
        if (this.ostats != null) {
            return this.ostats.getExp();
        }
        return this.stats.getExp();
    }
    
    public void setOverrideStats(final OverrideMonsterStats ostats) {
        this.ostats = ostats;
        this.hp = ostats.getHp();
        this.mp = ostats.getMp();
    }
    
    public MapleMonster getSponge() {
        return this.sponge.get();
    }
    
    public byte getVenomMulti() {
        return this.venom_counter;
    }
    
    public void setVenomMulti(final byte venom_counter) {
        this.venom_counter = venom_counter;
    }
    
    public void damage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
        this.damage(from, damage, updateAttackTime, 0);
    }
    
    public void damage(final MapleCharacter from, final long damage, final boolean updateAttackTime, final int lastSkill) {
        if (from == null || damage <= 0L || !this.isAlive()) {
            return;
        }
        AttackerEntry attacker = null;
        if (from.getParty() != null) {
            attacker = new PartyAttackerEntry(from.getParty().getId(), this.map.getChannel());
        }
        else {
            attacker = new SingleAttackerEntry(from, this.map.getChannel());
        }
        boolean replaced = false;
        for (final AttackerEntry aentry : this.attackers) {
            if (aentry.equals(attacker)) {
                attacker = aentry;
                replaced = true;
                break;
            }
        }
        if (!replaced) {
            this.attackers.add(attacker);
        }
        final long rDamage = Math.max(0L, Math.min(damage, this.hp));
        attacker.addDamage(from, rDamage, updateAttackTime);
        if (this.stats.getSelfD() != -1) {
            this.hp -= rDamage;
            if (this.hp > 0L) {
                if (this.hp < this.stats.getSelfDHp()) {
                    this.map.killMonster(this, from, false, false, this.stats.getSelfD(), lastSkill);
                }
                else {
                    for (final AttackerEntry mattacker : this.attackers) {
                        for (final AttackingMapleCharacter cattacker : mattacker.getAttackers()) {
                            if (cattacker.getAttacker().getMap() == from.getMap() && cattacker.getLastAttackTime() >= System.currentTimeMillis() - 4000L) {
                                cattacker.getAttacker().getClient().getSession().write(MobPacket.showMonsterHP(this.getObjectId(), (int)Math.ceil(this.hp * 100.0 / this.getMobMaxHp())));
                            }
                        }
                    }
                }
            }
            else {
                this.map.killMonster(this, from, true, false, (byte)1, lastSkill);
            }
        }
        else {
            if (this.sponge.get() != null && this.sponge.get().hp > 0L) {
                final MapleMonster mapleMonster = this.sponge.get();
                mapleMonster.hp -= rDamage;
                if (this.sponge.get().hp <= 0L) {
                    this.map.broadcastMessage(MobPacket.showBossHP(this.sponge.get().getId(), -1L, this.sponge.get().getMobMaxHp()));
                    this.map.killMonster(this.sponge.get(), from, true, false, (byte)1, lastSkill);
                }
                else {
                    this.map.broadcastMessage(MobPacket.showBossHP(this.sponge.get()));
                }
            }
            if (this.hp > 0L) {
                this.hp -= rDamage;
                if (this.eventInstance != null) {
                    this.eventInstance.monsterDamaged(from, this, (int)rDamage);
                }
                else {
                    final EventInstanceManager em = from.getEventInstance();
                    if (em != null) {
                        em.monsterDamaged(from, this, (int)rDamage);
                    }
                }
                if (this.sponge.get() == null && this.hp > 0L) {
                    switch (this.stats.getHPDisplayType()) {
                        case 0: {
                            this.map.broadcastMessage(MobPacket.showBossHP(this), this.getPosition());
                            break;
                        }
                        case 1: {
                            this.map.broadcastMessage(from, MobPacket.damageFriendlyMob(this, damage, true), false);
                            break;
                        }
                        case -1:
                        case 2: {
                            final int oid = this.getObjectId();
                            final double percent = this.hp * 100.0 / this.getMobMaxHp();
                            final int show = (int)Math.ceil(percent);
                            this.map.broadcastMessage(MobPacket.showMonsterHP(this.getObjectId(), (int)Math.ceil(this.hp * 100.0 / this.getMobMaxHp())));
                            from.mulung_EnergyModify(true);
                            break;
                        }
                        case 3: {
                            for (final AttackerEntry mattacker2 : this.attackers) {
                                for (final AttackingMapleCharacter cattacker2 : mattacker2.getAttackers()) {
                                    if (cattacker2.getAttacker().getMap() == from.getMap() && cattacker2.getLastAttackTime() >= System.currentTimeMillis() - 4000L) {
                                        cattacker2.getAttacker().getClient().getSession().write(MobPacket.showMonsterHP(this.getObjectId(), (int)Math.ceil(this.hp * 100.0 / this.getMobMaxHp())));
                                    }
                                }
                            }
                            break;
                        }
                        default: {
                            System.out.println(this.stats.isBoss() + " " + this.stats.getHPDisplayType());
                            break;
                        }
                    }
                }
                if (this.hp <= 0L) {
                    if (this.stats.getHPDisplayType() == 0 || this.stats.getHPDisplayType() == -1) {
                        this.map.broadcastMessage(MobPacket.showBossHP(this.getId(), -1L, this.getMobMaxHp()), this.getPosition());
                    }
                    this.map.killMonster(this, from, true, false, (byte)1, lastSkill);
                }
            }
        }
        this.startDropItemSchedule();
    }
    
    public void heal(final int hp, final int mp, final boolean broadcast) {
        final long TotalHP = this.getHp() + hp;
        final int TotalMP = this.getMp() + mp;
        if (TotalHP >= this.getMobMaxHp()) {
            this.setHp(this.getMobMaxHp());
        }
        else {
            this.setHp(TotalHP);
        }
        if (TotalMP >= this.getMp()) {
            this.setMp(this.getMp());
        }
        else {
            this.setMp(TotalMP);
        }
        if (broadcast) {
            this.map.broadcastMessage(MobPacket.healMonster(this.getObjectId(), hp));
        }
        else if (this.sponge.get() != null) {
            final MapleMonster mapleMonster = this.sponge.get();
            mapleMonster.hp += hp;
        }
    }
    
    public void giveExpToCharacter(final MapleCharacter attacker, int exp, final boolean highestDamage, final int numExpSharers, final byte pty, final byte Class_Bonus_EXP_PERCENT, final byte 网吧特别经验_百分比, final int lastskillID) {
        if (highestDamage) {
            if (this.eventInstance != null) {
                this.eventInstance.monsterKilled(attacker, this);
            }
            else {
                final EventInstanceManager em = attacker.getEventInstance();
                if (em != null) {
                    em.monsterKilled(attacker, this);
                }
            }
            this.highestDamageChar = attacker.getId();
        }
        if (exp > 0) {
            final MonsterStatusEffect mse = this.stati.get(MonsterStatus.挑衅);
            if (mse != null) {
                exp += (int)(exp * (mse.getX() / 100.0));
            }
            final Integer holySymbol = attacker.getBuffedValue(MapleBuffStat.神圣祈祷);
            if (holySymbol != null) {
                if (numExpSharers == 1) {
                    exp *= (int)(1.0 + holySymbol / 500.0);
                }
                else {
                    exp *= (int)(1.0 + holySymbol / 100.0);
                }
            }
            if (attacker.hasDisease(MapleDisease.诅咒)) {
                exp /= 2;
            }
            exp *= attacker.getEXPMod() * (int)(attacker.getStat().expBuff / 100.0);
            exp = Math.min(Integer.MAX_VALUE, exp * ((attacker.getLevel() < 10) ? GameConstants.getExpRate_Below10(attacker.getJob()) : ChannelServer.getInstance(this.map.getChannel()).getExpRate()));
            int Class_Bonus_EXP = 0;
            if (Class_Bonus_EXP_PERCENT > 0) {
                Class_Bonus_EXP = (int)(exp / 100.0 * Class_Bonus_EXP_PERCENT);
            }
            int 网吧特别经验 = 0;
            if (网吧特别经验_百分比 > 0) {
                网吧特别经验 = (int)(exp / 100.0 * 网吧特别经验_百分比);
            }
            attacker.gainExpMonster(exp, true, highestDamage, pty, Class_Bonus_EXP, 网吧特别经验);
        }
        attacker.mobKilled(this.getId(), lastskillID);
    }
    
    public int killBy(final MapleCharacter killer, final int lastSkill) {
        final int totalBaseExp = this.getMobExp();
        AttackerEntry highest = null;
        long highdamage = 0L;
        for (final AttackerEntry attackEntry : this.attackers) {
            if (attackEntry.getDamage() > highdamage) {
                highest = attackEntry;
                highdamage = attackEntry.getDamage();
            }
        }
        for (final AttackerEntry attackEntry2 : this.attackers) {
            final int baseExp = (int)Math.ceil(totalBaseExp * (attackEntry2.getDamage() / (double)this.getMobMaxHp()));
            attackEntry2.killedMob(this.getMap(), baseExp, attackEntry2 == highest, lastSkill);
        }
        final MapleCharacter controll = this.controller.get();
        if (controll != null) {
            controll.getClient().getSession().write(MobPacket.stopControllingMonster(this.getObjectId()));
            controll.stopControllingMonster(this);
        }
        switch (this.getId()) {
            default: {
                this.spawnRevives(this.getMap());
                if (this.eventInstance != null) {
                    this.eventInstance.unregisterMonster(this);
                    this.eventInstance = null;
                }
                if (killer != null && killer.getPyramidSubway() != null) {
                    killer.getPyramidSubway().onKill(killer);
                }
                final MapleMonster oldSponge = this.getSponge();
                this.sponge = new WeakReference<MapleMonster>(null);
                if (oldSponge != null && oldSponge.isAlive()) {
                    boolean set = true;
                    for (final MapleMapObject mon : this.map.getAllMonstersThreadsafe()) {
                        final MapleMonster mons = (MapleMonster)mon;
                        if (mons.getObjectId() != oldSponge.getObjectId() && mons.getObjectId() != this.getObjectId() && (mons.getSponge() == oldSponge || mons.getLinkOid() == oldSponge.getObjectId())) {
                            set = false;
                            break;
                        }
                    }
                    if (set) {
                        this.map.killMonster(oldSponge, killer, true, false, (byte)1);
                    }
                }
                this.nodepack = null;
                this.reflectpack = null;
                this.stati.clear();
                this.cancelDropItem();
                if (this.listener != null) {
                    this.listener.monsterKilled();
                }
                final int v1 = this.highestDamageChar;
                this.highestDamageChar = 0;
                return v1;
            }
        }
    }
    
    public void spawnRevives(final MapleMap map) {
        final List<Integer> toSpawn = this.stats.getRevives();
        if (toSpawn == null) {
            return;
        }
        MapleMonster spongy = null;
        switch (this.getId()) {
            case 8810118:
            case 8810119:
            case 8810120:
            case 8810121: {
                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeFactory.getMonster(i);
                    mob.setPosition(this.getPosition());
                    if (this.eventInstance != null) {
                        this.eventInstance.registerMonster(mob);
                    }
                    if (this.dropsDisabled()) {
                        mob.disableDrops();
                    }
                    switch (mob.getId()) {
                        case 8810119:
                        case 8810120:
                        case 8810121:
                        case 8810122: {
                            spongy = mob;
                            continue;
                        }
                    }
                }
                if (spongy != null) {
                    map.spawnRevives(spongy, this.getObjectId());
                    for (final MapleMapObject mon : map.getAllMonstersThreadsafe()) {
                        final MapleMonster mons = (MapleMonster)mon;
                        if (mons.getObjectId() != spongy.getObjectId() && (mons.getSponge() == this || mons.getLinkOid() == this.getObjectId())) {
                            mons.setSponge(spongy);
                            mons.setLinkOid(spongy.getObjectId());
                        }
                    }
                    break;
                }
                break;
            }
            case 8810026:
            case 8810130:
            case 8820008:
            case 8820009:
            case 8820010:
            case 8820011:
            case 8820012:
            case 8820013: {
                final List<MapleMonster> mobs = new ArrayList<MapleMonster>();
                for (final int j : toSpawn) {
                    final MapleMonster mob2 = MapleLifeFactory.getMonster(j);
                    mob2.setPosition(this.getPosition());
                    if (this.eventInstance != null) {
                        this.eventInstance.registerMonster(mob2);
                    }
                    if (this.dropsDisabled()) {
                        mob2.disableDrops();
                    }
                    switch (mob2.getId()) {
                        case 8810018:
                        case 8810118:
                        case 8820009:
                        case 8820010:
                        case 8820011:
                        case 8820012:
                        case 8820013:
                        case 8820014: {
                            spongy = mob2;
                            continue;
                        }
                        default: {
                            mobs.add(mob2);
                            continue;
                        }
                    }
                }
                if (spongy != null) {
                    map.spawnRevives(spongy, this.getObjectId());
                    for (final MapleMonster k : mobs) {
                        k.setSponge(spongy);
                        map.spawnRevives(k, this.getObjectId());
                    }
                    break;
                }
                break;
            }
            default: {
                for (final int i : toSpawn) {
                    final MapleMonster mob = MapleLifeFactory.getMonster(i);
                    if (this.eventInstance != null) {
                        this.eventInstance.registerMonster(mob);
                    }
                    mob.setPosition(this.getPosition());
                    if (this.dropsDisabled()) {
                        mob.disableDrops();
                    }
                    map.spawnRevives(mob, this.getObjectId());
                    if (mob.getId() == 9300216) {
                        map.broadcastMessage(MaplePacketCreator.environmentChange("Dojang/clear", 4));
                        map.broadcastMessage(MaplePacketCreator.environmentChange("dojang/end/clear", 3));
                    }
                }
                break;
            }
        }
    }
    
    public boolean isAlive() {
        return this.hp > 0L;
    }
    
    public void setCarnivalTeam(final byte team) {
        this.carnivalTeam = team;
    }
    
    public byte getCarnivalTeam() {
        return this.carnivalTeam;
    }
    
    public MapleCharacter getController() {
        return this.controller.get();
    }
    
    public void setController(final MapleCharacter controller) {
        this.controller = new WeakReference<MapleCharacter>(controller);
    }
    
    public void switchController(final MapleCharacter newController, final boolean immediateAggro) {
        final MapleCharacter controllers = this.getController();
        if (controllers == newController) {
            return;
        }
        if (controllers != null) {
            controllers.stopControllingMonster(this);
            controllers.getClient().getSession().write(MobPacket.stopControllingMonster(this.getObjectId()));
        }
        newController.controlMonster(this, immediateAggro);
        this.setController(newController);
        if (immediateAggro) {
            this.setControllerHasAggro(true);
        }
        this.setControllerKnowsAboutAggro(false);
        if (this.getId() == 9300275 && this.map.getId() >= 921120100 && this.map.getId() < 921120500) {
            if (this.lastNodeController != -1 && this.lastNodeController != newController.getId()) {
                this.resetShammos(newController.getClient());
            }
            else {
                this.setLastNodeController(newController.getId());
            }
        }
    }
    
    public final void resetShammos(final MapleClient c) {
        this.map.killAllMonsters(true);
        this.map.broadcastMessage(MaplePacketCreator.serverNotice(5, "A player has moved too far from Shammos. Shammos is going back to the start."));
        Timer.EtcTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (c.getPlayer() != null) {
                    c.getPlayer().changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().getPortal(0));
                    if (MapleMonster.this.map.getCharactersThreadsafe().size() > 1) {
                        MapScriptMethods.startScript_FirstUser(c, "shammos_Fenter");
                    }
                }
            }
        }, 500L);
    }
    
    public void addListener(final MonsterListener listener) {
        this.listener = listener;
    }
    
    public boolean isControllerHasAggro() {
        return this.controllerHasAggro;
    }
    
    public void setControllerHasAggro(final boolean controllerHasAggro) {
        this.controllerHasAggro = controllerHasAggro;
    }
    
    public boolean isControllerKnowsAboutAggro() {
        return this.controllerKnowsAboutAggro;
    }
    
    public void setControllerKnowsAboutAggro(final boolean controllerKnowsAboutAggro) {
        this.controllerKnowsAboutAggro = controllerKnowsAboutAggro;
    }
    
    @Override
    public void sendSpawnData(final MapleClient client) {
        if (!this.isAlive()) {
            return;
        }
        if (this.isFake()) {
            client.getSession().write(MobPacket.spawnFakeMonster(this, 0));
        }
        else {
            client.getSession().write(MobPacket.spawnMonster(this, false));
        }
        if (this.reflectpack != null) {
            client.getSession().write(this.reflectpack);
        }
        if (this.lastNode >= 0 && this.getId() == 9300275 && this.map.getId() >= 921120100 && this.map.getId() < 921120500) {
            if (this.lastNodeController != -1) {
                this.resetShammos(client);
            }
            else {
                this.setLastNodeController(client.getPlayer().getId());
            }
        }
    }
    
    @Override
    public void sendDestroyData(final MapleClient client) {
        if (this.getEventInstance() != null && this.lastNode >= 0) {
            this.resetShammos(client);
        }
        else {
            client.getSession().write(MobPacket.killMonster(this.getObjectId(), 0));
        }
        if (this.getController() != null && client.getPlayer() != null && client.getPlayer().getId() == this.getController().getId()) {
            client.getPlayer().stopControllingMonster(this);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.stats.getName());
        sb.append("(");
        sb.append(this.getId());
        sb.append(") (等級 ");
        sb.append(this.stats.getLevel());
        sb.append(") 在 (X");
        sb.append(this.getPosition().x);
        sb.append("/ Y");
        sb.append(this.getPosition().y);
        sb.append(") 坐标 ");
        sb.append(this.getHp());
        sb.append("/ ");
        sb.append(this.getMobMaxHp());
        sb.append("血量, ");
        sb.append(this.getMp());
        sb.append("/ ");
        sb.append(this.getMobMaxMp());
        sb.append(" 魔力, 反应堆: ");
        sb.append(this.getObjectId());
        sb.append(" || 仇恨目标 : ");
        final MapleCharacter chr = this.controller.get();
        sb.append((chr != null) ? chr.getName() : "无");
        return sb.toString();
    }
    
    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.MONSTER;
    }
    
    public EventInstanceManager getEventInstance() {
        return this.eventInstance;
    }
    
    public void setEventInstance(final EventInstanceManager eventInstance) {
        this.eventInstance = eventInstance;
    }
    
    public int getStatusSourceID(final MonsterStatus status) {
        final MonsterStatusEffect effect = this.stati.get(status);
        if (effect != null) {
            return effect.getSkill();
        }
        return -1;
    }
    
    public ElementalEffectiveness getEffectiveness(final Element e) {
        if (this.stati.size() > 0 && this.stati.get(MonsterStatus.巫毒术) != null) {
            return ElementalEffectiveness.正常;
        }
        return this.stats.getEffectiveness(e);
    }
    
    public void applyStatus(final MapleCharacter from, final MonsterStatusEffect status, final boolean poison, final long duration, final boolean venom) {
        this.applyStatus(from, status, poison, duration, venom, true);
    }
    
    public void applyStatus(final MapleCharacter from, final MonsterStatusEffect status, final boolean poison, long duration, final boolean venom, final boolean checkboss) {
        if (!this.isAlive()) {
            return;
        }
        final ISkill skilz = SkillFactory.getSkill(status.getSkill());
        if (skilz != null) {
            switch (this.stats.getEffectiveness(skilz.getElement())) {
                case 免疫:
                case 增强: {
                    return;
                }
                case 正常:
                case 虚弱: {
                    break;
                }
                default: {
                    return;
                }
            }
        }
        final int statusSkill = status.getSkill();
        Label_0277: {
            switch (statusSkill) {
                case 2111006: {
                    switch (this.stats.getEffectiveness(Element.POISON)) {
                        case 免疫:
                        case 增强: {
                            return;
                        }
                        default: {
                            break Label_0277;
                        }
                    }
                }
                case 2211006: {
                    switch (this.stats.getEffectiveness(Element.ICE)) {
                        case 免疫:
                        case 增强: {
                            return;
                        }
                        default: {
                            break Label_0277;
                        }
                    }
                }
                case 4120005:
                case 4220005:
                case 14110004: {
                    switch (this.stats.getEffectiveness(Element.POISON)) {
                        case 免疫:
                        case 增强: {
                            return;
                        }
                        default: {
                            break Label_0277;
                        }
                    }
                }
            }
        }
        final MonsterStatus stat = status.getStati();
        if (this.stats.isNoDoom() && stat == MonsterStatus.巫毒术) {
            return;
        }
        if (this.getId() == 9600000 && (stat == MonsterStatus.冻结 || stat == MonsterStatus.中毒 || stat == MonsterStatus.眩晕)) {
            return;
        }
        if (this.stats.isBoss()) {
            if (this.stats.isBoss() && (stat == MonsterStatus.眩晕 || stat == MonsterStatus.中毒)) {
                return;
            }
            if (checkboss && stat != MonsterStatus.速度 && stat != MonsterStatus.忍者伏击 && stat != MonsterStatus.中毒 && stat != MonsterStatus.物攻) {
                return;
            }
        }
        if ((this.stats.isFriendly() || this.isFake()) && (stat == MonsterStatus.眩晕 || stat == MonsterStatus.速度 || stat == MonsterStatus.中毒)) {
            return;
        }
        final MonsterStatusEffect oldEffect = this.stati.get(stat);
        if (oldEffect != null) {
            this.stati.remove(stat);
            if (oldEffect.getStati() == null) {
                oldEffect.cancelTask();
                oldEffect.cancelPoisonSchedule();
            }
        }
        final Timer.MobTimer timerManager = Timer.MobTimer.getInstance();
        final Runnable cancelTask = new Runnable() {
            @Override
            public void run() {
                MapleMonster.this.cancelStatus(stat);
            }
        };
        if (poison && this.getHp() > 1L) {
            final int poisonDamage = (int)Math.min(32767L, (long)(this.getMobMaxHp() / (70.0 - from.getSkillLevel(status.getSkill())) + 0.999));
            status.setValue(MonsterStatus.中毒, poisonDamage);
            status.setPoisonSchedule(timerManager.register(new PoisonTask(poisonDamage, from, status, cancelTask, false), 1000L, 1000L));
            if (from.isGM()) {
                from.dropMessage(6, "正在给予 怪物: " + this.getId() + " BUFF状态: " + status.getStati().name() + " 是否中毒: " + poison + " BUFF持续时间: " + duration);
            }
            if (duration >= 60000L) {
                duration = 10000L;
            }
        }
        else if (venom) {
            int poisonLevel = 0;
            int matk = 0;
            switch (from.getJob()) {
                case 412: {
                    poisonLevel = from.getSkillLevel(SkillFactory.getSkill(4120005));
                    if (poisonLevel <= 0) {
                        return;
                    }
                    matk = SkillFactory.getSkill(4120005).getEffect(poisonLevel).getMatk();
                    break;
                }
                case 422: {
                    poisonLevel = from.getSkillLevel(SkillFactory.getSkill(4220005));
                    if (poisonLevel <= 0) {
                        return;
                    }
                    matk = SkillFactory.getSkill(4220005).getEffect(poisonLevel).getMatk();
                    break;
                }
                case 1411:
                case 1412: {
                    poisonLevel = from.getSkillLevel(SkillFactory.getSkill(14110004));
                    if (poisonLevel <= 0) {
                        return;
                    }
                    matk = SkillFactory.getSkill(14110004).getEffect(poisonLevel).getMatk();
                    break;
                }
                case 434: {
                    poisonLevel = from.getSkillLevel(SkillFactory.getSkill(4340001));
                    if (poisonLevel <= 0) {
                        return;
                    }
                    matk = SkillFactory.getSkill(4340001).getEffect(poisonLevel).getMatk();
                    break;
                }
                default: {
                    return;
                }
            }
            final int luk = from.getStat().getLuk();
            final int maxDmg = (int)Math.ceil(Math.min(32767.0, 0.2 * luk * matk));
            final int minDmg = (int)Math.ceil(Math.min(32767.0, 0.1 * luk * matk));
            int gap = maxDmg - minDmg;
            if (gap == 0) {
                gap = 1;
            }
            int poisonDamage2 = 0;
            for (int i = 0; i < this.getVenomMulti(); ++i) {
                poisonDamage2 += Randomizer.nextInt(gap) + minDmg;
            }
            poisonDamage2 = Math.min(32767, poisonDamage2);
            status.setValue(MonsterStatus.中毒, poisonDamage2);
            status.setPoisonSchedule(timerManager.register(new PoisonTask(poisonDamage2, from, status, cancelTask, false), 1000L, 1000L));
        }
        else if (statusSkill == 4111003 || statusSkill == 14111001) {
            status.setPoisonSchedule(timerManager.schedule(new PoisonTask((int)(this.getMobMaxHp() / 50.0 + 0.999), from, status, cancelTask, true), 3500L));
        }
        else if (statusSkill == 4121004 || statusSkill == 4221004) {
            int damage = (from.getStat().getStr() + from.getStat().getLuk()) * 2 * 0 + 100;
            status.setPoisonSchedule(timerManager.register(new PoisonTask(damage, from, status, cancelTask, false), 1000L, 1000L));
            if (damage > 0) {
                if (damage >= this.hp) {
                    damage = (int)(this.hp - 1L);
                }
                this.damage(from, damage, false);
            }
        }
        this.stati.put(stat, status);
        this.map.broadcastMessage(MobPacket.applyMonsterStatus(this.getObjectId(), status), this.getPosition());
        if (this.getController() != null && !this.getController().isMapObjectVisible(this)) {
            this.getController().getClient().getSession().write(MobPacket.applyMonsterStatus(this.getObjectId(), status));
        }
        int aniTime = 0;
        if (skilz != null) {
            aniTime = skilz.getAnimationTime();
        }
        final ScheduledFuture<?> schedule = timerManager.schedule(cancelTask, duration + aniTime);
        status.setCancelTask(schedule);
    }
    
    public void dispelSkill(final MobSkill skillId) {
        final List<MonsterStatus> toCancel = new ArrayList<MonsterStatus>();
        for (final Map.Entry<MonsterStatus, MonsterStatusEffect> effects : this.stati.entrySet()) {
            if (effects.getValue().getMobSkill() != null && effects.getValue().getMobSkill().getSkillId() == skillId.getSkillId()) {
                toCancel.add(effects.getKey());
            }
        }
        for (final MonsterStatus stat : toCancel) {
            this.cancelStatus(stat);
        }
    }
    
    public void applyMonsterBuff(final Map<MonsterStatus, Integer> effect, final int skillId, final long duration, final MobSkill skill, final List<Integer> reflection) {
        final Timer.MobTimer timerManager = Timer.MobTimer.getInstance();
        final Runnable cancelTask = new Runnable() {
            @Override
            public void run() {
                if (reflection.size() > 0) {
                    MapleMonster.this.reflectpack = null;
                }
                if (MapleMonster.this.isAlive()) {
                    for (final MonsterStatus z : effect.keySet()) {
                        MapleMonster.this.cancelStatus(z);
                    }
                }
            }
        };
        for (final Map.Entry<MonsterStatus, Integer> z : effect.entrySet()) {
            final MonsterStatusEffect effectz = new MonsterStatusEffect(z.getKey(), z.getValue(), 0, skill, true);
            this.stati.put(z.getKey(), effectz);
        }
        if (reflection.size() > 0) {
            this.reflectpack = MobPacket.applyMonsterStatus(this.getObjectId(), effect, reflection, skill);
            this.map.broadcastMessage(this.reflectpack, this.getPosition());
            if (this.getController() != null && !this.getController().isMapObjectVisible(this)) {
                this.getController().getClient().getSession().write(this.reflectpack);
            }
        }
        else {
            for (final Map.Entry<MonsterStatus, Integer> z : effect.entrySet()) {
                this.map.broadcastMessage(MobPacket.applyMonsterStatus(this.getObjectId(), z.getKey(), z.getValue(), skill), this.getPosition());
                if (this.getController() != null && !this.getController().isMapObjectVisible(this)) {
                    this.getController().getClient().getSession().write(MobPacket.applyMonsterStatus(this.getObjectId(), z.getKey(), z.getValue(), skill));
                }
            }
        }
        timerManager.schedule(cancelTask, duration);
    }
    
    public void setTempEffectiveness(final Element e, final long milli) {
        this.stats.setEffectiveness(e, ElementalEffectiveness.虚弱);
        Timer.MobTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                MapleMonster.this.stats.removeEffectiveness(e);
            }
        }, milli);
    }
    
    public boolean isBuffed(final MonsterStatus status) {
        return this.stati.containsKey(status);
    }
    
    public MonsterStatusEffect getBuff(final MonsterStatus status) {
        return this.stati.get(status);
    }
    
    public void setFake(final boolean fake) {
        this.fake = fake;
    }
    
    public boolean isFake() {
        return this.fake;
    }
    
    public MapleMap getMap() {
        return this.map;
    }
    
    public List<Pair<Integer, Integer>> getSkills() {
        return this.stats.getSkills();
    }
    
    public boolean hasSkill(final int skillId, final int level) {
        return this.stats.hasSkill(skillId, level);
    }
    
    public long getLastSkillUsed(final int skillId) {
        if (this.usedSkills.containsKey(skillId)) {
            return this.usedSkills.get(skillId);
        }
        return 0L;
    }
    
    public void setLastSkillUsed(final int skillId, final long now, final long cooltime) {
        switch (skillId) {
            case 140: {
                this.usedSkills.put(skillId, now + cooltime * 2L);
                this.usedSkills.put(141, now);
                break;
            }
            case 141: {
                this.usedSkills.put(skillId, now + cooltime * 2L);
                this.usedSkills.put(140, now + cooltime);
                break;
            }
            default: {
                this.usedSkills.put(skillId, now + cooltime);
                break;
            }
        }
    }
    
    public byte getNoSkills() {
        return this.stats.getNoSkills();
    }
    
    public boolean isFirstAttack() {
        return this.stats.isFirstAttack();
    }
    
    public int getBuffToGive() {
        return this.stats.getBuffToGive();
    }
    
    public int getExp() {
        if (this.overrideStats != null) {
            return this.overrideStats.getExp();
        }
        return this.stats.getExp();
    }
    
    public int getLinkOid() {
        return this.linkoid;
    }
    
    public void setLinkOid(final int lo) {
        this.linkoid = lo;
    }
    
    public Map<MonsterStatus, MonsterStatusEffect> getStati() {
        return this.stati;
    }
    
    public void addEmpty() {
        this.stati.put(MonsterStatus.空白BUFF, new MonsterStatusEffect(MonsterStatus.空白BUFF, 0, 0, null, false));
        this.stati.put(MonsterStatus.召唤怪物, new MonsterStatusEffect(MonsterStatus.召唤怪物, 0, 0, null, false));
    }
    
    public int getStolen() {
        return this.stolen;
    }
    
    public void setStolen(final int s) {
        this.stolen = s;
    }
    
    public void handleSteal(final MapleCharacter chr) {
        double showdown = 100.0;
        final MonsterStatusEffect mse = this.getBuff(MonsterStatus.挑衅);
        if (mse != null) {
            showdown += mse.getX();
        }
        final ISkill steal = SkillFactory.getSkill(4201004);
        final int level = chr.getSkillLevel(steal);
        final int chServerrate = ChannelServer.getInstance(chr.getClient().getChannel()).getDropRate();
        if (level > 0 && !this.getStats().isBoss() && this.stolen == -1 && steal.getEffect(level).makeChanceResult()) {
            final MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
            final List<MonsterDropEntry> de = mi.retrieveDrop(this.getId());
            if (de == null) {
                this.stolen = 0;
                return;
            }
            final List<MonsterDropEntry> dropEntry = new ArrayList<MonsterDropEntry>(de);
            Collections.shuffle(dropEntry);
            for (final MonsterDropEntry d : dropEntry) {
                if (d.itemId > 0 && d.questid == 0 && d.itemId / 10000 != 238 && Randomizer.nextInt(999999) < (int)(10 * d.chance * chServerrate * chr.getDropMod() * (chr.getStat().dropBuff / 100.0) * (showdown / 100.0))) {
                    IItem idrop;
                    if (GameConstants.getInventoryType(d.itemId) == MapleInventoryType.EQUIP) {
                        final Equip eq = (Equip)MapleItemInformationProvider.getInstance().getEquipById(d.itemId);
                        idrop = MapleItemInformationProvider.getInstance().randomizeStats(eq);
                    }
                    else {
                        idrop = new Item(d.itemId, (short)0, (short)((d.Maximum != 1) ? (Randomizer.nextInt(d.Maximum - d.Minimum) + d.Minimum) : 1), (byte)0);
                    }
                    this.stolen = d.itemId;
                    this.map.spawnMobDrop(idrop, this.map.calcDropPos(this.getPosition(), this.getTruePosition()), this, chr, (byte)0, (short)0);
                    break;
                }
            }
        }
        else {
            this.stolen = 0;
        }
    }
    
    public void setLastNode(final int lastNode) {
        this.lastNode = lastNode;
    }
    
    public int getLastNode() {
        return this.lastNode;
    }
    
    public void setLastNodeController(final int lastNode) {
        this.lastNodeController = lastNode;
    }
    
    public int getLastNodeController() {
        return this.lastNodeController;
    }
    
    public void cancelStatus(final MonsterStatus stat) {
        if (stat == MonsterStatus.空白BUFF || stat == MonsterStatus.召唤怪物) {
            return;
        }
        final MonsterStatusEffect mse = this.stati.get(stat);
        if (mse == null || !this.isAlive()) {
            return;
        }
        if (mse.isReflect()) {
            this.reflectpack = null;
        }
        mse.cancelPoisonSchedule();
        final MapleCharacter con = this.getController();
        if (con != null) {
            this.map.broadcastMessage(con, MobPacket.cancelMonsterStatus(this.getObjectId(), stat), this.getTruePosition());
            con.getClient().getSession().write(MobPacket.cancelMonsterStatus(this.getObjectId(), stat));
        }
        else {
            this.map.broadcastMessage(MobPacket.cancelMonsterStatus(this.getObjectId(), stat), this.getTruePosition());
        }
        this.stati.remove(stat);
    }
    
    public void cancelDropItem() {
        if (this.dropItemSchedule != null) {
            this.dropItemSchedule.cancel(false);
            this.dropItemSchedule = null;
        }
    }

    public void startDropItemSchedule() {
        final int itemId;
        cancelDropItem();
        if (this.stats.getDropItemPeriod() <= 0 || !isAlive())
            return;
        switch (getId()) {
            case 9300061:
                itemId = 4001101;
                break;
            case 9300102:
                itemId = 4031507;
                break;
            default:
                return;
        }
        this.shouldDropItem = false;
        this.dropItemSchedule = Timer.MobTimer.getInstance().register(new Runnable() {
            public void run() {
                if (MapleMonster.this.isAlive() && MapleMonster.this.map != null)
                    if (MapleMonster.this.shouldDropItem) {
                        MapleMonster.this.map.spawnAutoDrop(itemId, MapleMonster.this.getPosition());
                    } else {
                        MapleMonster.this.shouldDropItem = true;
                    }
            }
        },(this.stats.getDropItemPeriod() * 1000));
    }

    public MaplePacket getNodePacket() {
        return this.nodepack;
    }
    
    public void setNodePacket(final MaplePacket np) {
        this.nodepack = np;
    }
    
    public void killed() {
        if (this.listener != null) {
            this.listener.monsterKilled();
        }
        this.listener = null;
    }
    
    public void changeLevel(final int newLevel, final boolean pqMob) {
        this.ostats = new ChangeableStats(this.stats, newLevel, pqMob);
        this.hp = this.ostats.getHp();
        this.mp = this.ostats.getMp();
    }
    
    public Double getRange() {
        return GameConstants.maxViewRangeSq();
    }
    
    private static class AttackingMapleCharacter
    {
        private final MapleCharacter attacker;
        private long lastAttackTime;
        
        public AttackingMapleCharacter(final MapleCharacter attacker, final long lastAttackTime) {
            this.attacker = attacker;
            this.lastAttackTime = lastAttackTime;
        }
        
        public long getLastAttackTime() {
            return this.lastAttackTime;
        }
        
        public void setLastAttackTime(final long lastAttackTime) {
            this.lastAttackTime = lastAttackTime;
        }
        
        public MapleCharacter getAttacker() {
            return this.attacker;
        }
    }
    
    private static class ExpMap
    {
        public int exp;
        public byte ptysize;
        public byte Class_Bonus_EXP;
        public byte 网吧特别经验;
        
        public ExpMap(final int exp, final byte ptysize, final byte Class_Bonus_EXP, final byte 网吧特别经验) {
            this.exp = exp;
            this.ptysize = ptysize;
            this.Class_Bonus_EXP = Class_Bonus_EXP;
            this.网吧特别经验 = 网吧特别经验;
        }
    }
    
    private static class OnePartyAttacker
    {
        public MapleParty lastKnownParty;
        public long damage;
        public long lastAttackTime;
        
        public OnePartyAttacker(final MapleParty lastKnownParty, final long damage) {
            this.lastKnownParty = lastKnownParty;
            this.damage = damage;
            this.lastAttackTime = System.currentTimeMillis();
        }
    }
    
    private class PoisonTask implements Runnable
    {
        private final int poisonDamage;
        private final MapleCharacter chr;
        private final MonsterStatusEffect status;
        private final Runnable cancelTask;
        private final boolean shadowWeb;
        private final MapleMap map;
        
        private PoisonTask(final int poisonDamage, final MapleCharacter chr, final MonsterStatusEffect status, final Runnable cancelTask, final boolean shadowWeb) {
            this.poisonDamage = poisonDamage;
            this.chr = chr;
            this.status = status;
            this.cancelTask = cancelTask;
            this.shadowWeb = shadowWeb;
            this.map = chr.getMap();
        }
        
        @Override
        public void run() {
            long damage = this.poisonDamage;
            if (damage >= MapleMonster.this.hp) {
                damage = MapleMonster.this.hp - 1L;
                if (!this.shadowWeb) {
                    this.cancelTask.run();
                    this.status.cancelTask();
                }
            }
            if (MapleMonster.this.hp > 1L && damage > 0L) {
                MapleMonster.this.damage(this.chr, damage, false);
                if (this.shadowWeb) {
                    this.map.broadcastMessage(MobPacket.damageMonster(MapleMonster.this.getObjectId(), damage), MapleMonster.this.getPosition());
                }
            }
        }
    }
    
    private class SingleAttackerEntry implements AttackerEntry
    {
        private long damage;
        private final int chrid;
        private long lastAttackTime;
        private final int channel;
        
        public SingleAttackerEntry(final MapleCharacter from, final int cserv) {
            this.damage = 0L;
            this.chrid = from.getId();
            this.channel = cserv;
        }
        
        @Override
        public void addDamage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
            if (this.chrid == from.getId()) {
                this.damage += damage;
                if (updateAttackTime) {
                    this.lastAttackTime = System.currentTimeMillis();
                }
            }
        }
        
        @Override
        public List<AttackingMapleCharacter> getAttackers() {
            final MapleCharacter chr = MapleMonster.this.map.getCharacterById(this.chrid);
            if (chr != null) {
                return Collections.singletonList(new AttackingMapleCharacter(chr, this.lastAttackTime));
            }
            return Collections.emptyList();
        }
        
        @Override
        public boolean contains(final MapleCharacter chr) {
            return this.chrid == chr.getId();
        }
        
        @Override
        public long getDamage() {
            return this.damage;
        }
        
        @Override
        public void killedMob(final MapleMap map, final int baseExp, final boolean mostDamage, final int lastSkill) {
            final MapleCharacter chr = map.getCharacterById(this.chrid);
            if (chr != null && chr.isAlive()) {
                MapleMonster.this.giveExpToCharacter(chr, baseExp, mostDamage, 1, (byte)0, (byte)0, (byte)0, lastSkill);
            }
        }
        
        @Override
        public int hashCode() {
            return this.chrid;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final SingleAttackerEntry other = (SingleAttackerEntry)obj;
            return this.chrid == other.chrid;
        }
    }
    
    private class PartyAttackerEntry implements AttackerEntry
    {
        private long totDamage;
        private final Map<Integer, OnePartyAttacker> attackers;
        private final int partyid;
        private final int channel;
        
        public PartyAttackerEntry(final int partyid, final int cserv) {
            this.attackers = new HashMap<Integer, OnePartyAttacker>(6);
            this.partyid = partyid;
            this.channel = cserv;
        }
        
        @Override
        public List<AttackingMapleCharacter> getAttackers() {
            final List<AttackingMapleCharacter> ret = new ArrayList<AttackingMapleCharacter>(this.attackers.size());
            for (final Map.Entry<Integer, OnePartyAttacker> entry : this.attackers.entrySet()) {
                final MapleCharacter chr = MapleMonster.this.map.getCharacterById(entry.getKey());
                if (chr != null) {
                    ret.add(new AttackingMapleCharacter(chr, entry.getValue().lastAttackTime));
                }
            }
            return ret;
        }
        
        private Map<MapleCharacter, OnePartyAttacker> resolveAttackers() {
            final Map<MapleCharacter, OnePartyAttacker> ret = new HashMap<MapleCharacter, OnePartyAttacker>(this.attackers.size());
            for (final Map.Entry<Integer, OnePartyAttacker> aentry : this.attackers.entrySet()) {
                final MapleCharacter chr = MapleMonster.this.map.getCharacterById(aentry.getKey());
                if (chr != null) {
                    ret.put(chr, aentry.getValue());
                }
            }
            return ret;
        }
        
        @Override
        public boolean contains(final MapleCharacter chr) {
            return this.attackers.containsKey(chr.getId());
        }
        
        @Override
        public long getDamage() {
            return this.totDamage;
        }
        
        @Override
        public void addDamage(final MapleCharacter from, final long damage, final boolean updateAttackTime) {
            final OnePartyAttacker oldPartyAttacker = this.attackers.get(from.getId());
            if (oldPartyAttacker != null) {
                final OnePartyAttacker onePartyAttacker2 = oldPartyAttacker;
                onePartyAttacker2.damage += damage;
                oldPartyAttacker.lastKnownParty = from.getParty();
                if (updateAttackTime) {
                    oldPartyAttacker.lastAttackTime = System.currentTimeMillis();
                }
            }
            else {
                final OnePartyAttacker onePartyAttacker = new OnePartyAttacker(from.getParty(), damage);
                this.attackers.put(from.getId(), onePartyAttacker);
                if (!updateAttackTime) {
                    onePartyAttacker.lastAttackTime = 0L;
                }
            }
            this.totDamage += damage;
        }
        
        @Override
        public void killedMob(final MapleMap map, final int baseExp, final boolean mostDamage, final int lastSkill) {
            MapleCharacter highest = null;
            long highestDamage = 0L;
            int iexp = 0;
            final Map<MapleCharacter, ExpMap> expMap = new HashMap<MapleCharacter, ExpMap>(6);
            byte added_组队经验值 = 0;
            for (final Map.Entry<MapleCharacter, OnePartyAttacker> attacker : this.resolveAttackers().entrySet()) {
                final MapleParty party = attacker.getValue().lastKnownParty;
                double averagePartyLevel = 0.0;
                byte Class_Bonus_EXP = 0;
                byte 网吧特别经验 = 0;
                final List<MapleCharacter> expApplicable = new ArrayList<MapleCharacter>();
                for (final MaplePartyCharacter partychar : party.getMembers()) {
                    if (attacker.getKey().getLevel() - partychar.getLevel() <= 5 || MapleMonster.this.stats.getLevel() - partychar.getLevel() <= 5) {
                        final MapleCharacter pchr = map.getCharacterById(partychar.getId());
                        if (pchr == null || !pchr.isAlive() || pchr.getMap() != map) {
                            continue;
                        }
                        expApplicable.add(pchr);
                        averagePartyLevel += pchr.getLevel();
                        if (Class_Bonus_EXP == 0) {
                            Class_Bonus_EXP = ServerConstants.Class_Bonus_EXP(pchr.getJob());
                        }
                        if (pchr.getStat().equippedWelcomeBackRing && 网吧特别经验 == 0) {
                            网吧特别经验 = 80;
                        }
                        if (!pchr.getStat().hasPartyBonus || added_组队经验值 >= 4) {
                            continue;
                        }
                        ++added_组队经验值;
                    }
                }
                final long iDamage = attacker.getValue().damage;
                if (iDamage > highestDamage) {
                    highest = attacker.getKey();
                    highestDamage = iDamage;
                }
                final double innerBaseExp = baseExp * (iDamage / (double)this.totDamage);
                double expBonus = 1.0;
                if (expApplicable.size() > 1) {
                    expBonus = 1.1 + 0.05 * expApplicable.size();
                    averagePartyLevel /= expApplicable.size();
                }
                final double expFraction = innerBaseExp * expBonus / (expApplicable.size() + 1);
                for (final MapleCharacter expReceiver : expApplicable) {
                    final Integer oexp = (expMap.get(expReceiver) == null) ? 0 : expMap.get(expReceiver).exp;
                    if (oexp == null) {
                        iexp = 0;
                    }
                    else {
                        iexp = oexp;
                    }
                    final double expWeight = (expReceiver == attacker.getKey()) ? 2.0 : 1.0;
                    double levelMod = expReceiver.getLevel() / averagePartyLevel;
                    if (levelMod > 1.0 || this.attackers.containsKey(expReceiver.getId())) {
                        levelMod = 1.0;
                    }
                    iexp += (int)Math.round(expFraction * expWeight * levelMod);
                    expMap.put(expReceiver, new ExpMap(iexp, (byte)(expApplicable.size() + added_组队经验值), Class_Bonus_EXP, 网吧特别经验));
                }
            }
            for (final Map.Entry<MapleCharacter, ExpMap> expReceiver2 : expMap.entrySet()) {
                final ExpMap expmap = expReceiver2.getValue();
                MapleMonster.this.giveExpToCharacter(expReceiver2.getKey(), expmap.exp, mostDamage && expReceiver2.getKey() == highest, expMap.size(), expmap.ptysize, expmap.Class_Bonus_EXP, expmap.网吧特别经验, lastSkill);
            }
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.partyid;
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final PartyAttackerEntry other = (PartyAttackerEntry)obj;
            return this.partyid == other.partyid;
        }
    }
    
    private interface AttackerEntry
    {
        List<AttackingMapleCharacter> getAttackers();
        
        void addDamage(final MapleCharacter p0, final long p1, final boolean p2);
        
        long getDamage();
        
        boolean contains(final MapleCharacter p0);
        
        void killedMob(final MapleMap p0, final int p1, final boolean p2, final int p3);
    }
}
