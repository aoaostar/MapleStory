/**
 * @author: Eric
 * @script: CPQ
 * @func: Official GMS-like Monster Carnival
 */

importPackage(Packages.tools.packet);

var exitMap = 0;//退出地图
var waitingMap = 1;//等待地图
var reviveMap = 2;//复活地图
var fieldMap = 3;//场地图
var winnerMap = 4;//赢家地图
var loserMap = 5;//失败者地图

function init() {
    // *For use ONLY with Development v117.2*
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup(mapid) {
    var map = parseInt(mapid);
    var eim = em.newInstance("cpq" + mapid);
    eim.setInstanceMap(980000000); // <exit>
    eim.setInstanceMap(map);
    eim.setInstanceMap(map + 2);
    eim.setInstanceMap(map + 1).resetFully();
    eim.setInstanceMap(map + 3);
    eim.setInstanceMap(map + 4);
    eim.setProperty("forfeit", "false");
    eim.setProperty("blue", "-1");
    eim.setProperty("red", "-1");
    eim.setProperty("started", "false");
    var portal = eim.getMapInstance(reviveMap).getPortal("pt00");
    var scriptStart = map % 1000; // 地图最后三位数
    var scriptEnd = scriptStart / 100; // 地图最后三位数的第一位数。
    portal.setScriptName("MCrevive" + scriptEnd); // portals one through six calculated and used
    //portal.setScriptName("MCrevive"); // portals one through six calculated and used

    return eim;
}

function playerEntry(eim, player) {
    player.changeMap(eim.getMapInstance(waitingMap), eim.getMapInstance(waitingMap).getPortal(0));
    player.tryPartyQuest(1301);
}


function registerCarnivalParty(eim, carnivalParty) {
    if (eim.getProperty("red").equals("-1")) {
        eim.setProperty("red", carnivalParty.getLeader().getId() + "");
        eim.schedule("end", 3 * 60 * 1000); // 3 minutes
    } else {
        eim.setProperty("blue", carnivalParty.getLeader().getId() + "");
        eim.schedule("start", 10000);
    }
}

function leftParty(eim, player) {
    disbandParty(eim);
}

function disbandParty(eim) {
    disposeAll(eim);
}

function disposeAll(eim) {
    var iter = eim.getPlayers().iterator();
    while (iter.hasNext()) {
        var player = iter.next();
        eim.unregisterPlayer(player);
        player.changeMap(eim.getMapInstance(exitMap), eim.getMapInstance(exitMap).getPortal(0));
        player.getCarnivalParty().removeMember(player);
    }
    eim.dispose();
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    player.getCarnivalParty().removeMember(player);
    player.changeMap(eim.getMapInstance(exitMap), eim.getMapInstance(exitMap).getPortal(0));
    eim.disposeIfPlayerBelow(0, 0);
}

function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
    player.getCarnivalParty().removeMember(player);
    player.getMap().removePlayer(player);
    player.setMap(eim.getMapInstance(exitMap));
    eim.disposeIfPlayerBelow(0, 0);
}


function getParty(eim, property) {
    var chr = em.getChannelServer().getPlayerStorage().getCharacterById(parseInt(eim.getProperty(property)));
    if (chr == null) {
        eim.broadcastPlayerMsg(5, "队伍中的队长" + property + " 没有找到.");
        disposeAll(eim);
        return null;
    } else {
        return chr.getCarnivalParty();
    }
}

function start(eim) {
    eim.setProperty("started", "true");
    eim.startEventTimer(10 * 60 * 1000);
    getParty(eim, "blue").warp(eim.getMapInstance(fieldMap), "blue00");
    getParty(eim, "red").warp(eim.getMapInstance(fieldMap), "red00");
}

function monsterKilled(eim, chr, cp) {
    chr.getCarnivalParty().addCP(chr, cp); // this is for the specific party (red/blue)
    chr.CPUpdate(false, chr.getAvailableCP(), chr.getTotalCP(), 0); // this will update for the player who killed the mob
    chr.CPUpdate(true, chr.getAvailableCP(), chr.getTotalCP(), 0); // this will update for the player's carnival team (their party)
}

function monsterValue(eim, mobId) {
    return 0;
}

function end(eim) {
    if (!eim.getProperty("started").equals("true")) {
        disposeAll(eim);
    }
}

function warpOut(eim) {
    if (!eim.getProperty("started").equals("true")) {
        if (eim.getProperty("blue").equals("-1")) {
            disposeAll(eim);
        }
    } else {
        var blueParty = getParty(eim, "blue");
        var redParty = getParty(eim, "red");
        if (blueParty.isWinner()) {
            blueParty.warp(eim.getMapInstance(winnerMap), 0);
            redParty.warp(eim.getMapInstance(loserMap), 0);
        } else {
            redParty.warp(eim.getMapInstance(winnerMap), 0);
            blueParty.warp(eim.getMapInstance(loserMap), 0);
        }
        eim.disposeIfPlayerBelow(100, 0);
    }
}

function scheduledTimeout(eim) {
    eim.stopEventTimer();
    if (!eim.getProperty("started").equals("true")) {
        if (eim.getProperty("blue").equals("-1")) {
            disposeAll(eim);
        }
    } else {
        var blueParty = getParty(eim, "blue");
        var redParty = getParty(eim, "red");
        if (blueParty.getTotalCP() > redParty.getTotalCP()) {
            blueParty.setWinner(true);
        } else if (redParty.getTotalCP() > blueParty.getTotalCP()) {
            redParty.setWinner(true);
        }
        blueParty.displayMatchResult();
        redParty.displayMatchResult();
        eim.schedule("warpOut", 10000);
    }
}

function playerRevive(eim, player) {
    if (player.getAvailableCP() >= 10) {
        player.getCarnivalParty().useCP(player, 10); // otherwise we go -10 and when we respawn we can crash..
    }
    player.CPUpdate(true, player.getAvailableCP(), player.getTotalCP(), 0); // due to the player dying, it will update a -10 CP difference from the CP board.
    player.addHP(50);
    player.changeMap(eim.getMapInstance(reviveMap), eim.getMapInstance(reviveMap).getPortal(0));
    player.CPUpdate(false, player.getAvailableCP(), player.getTotalCP(), 0); // CP boards aren't present within revival maps, but we will update here because we are taking cp.
    return true;
}

function playerDisconnected(eim, player) {
    player.setMap(eim.getMapInstance(exitMap));
    eim.broadcastPlayerMsg(5, "[" + player.getName() + "] 团队 [" + (player.getCarnivalParty().getTeam() == 0 ? "冒险岛红队" : "冒险岛蓝队") + "] 退出怪物嘉年华."); // forgot about the packet xD
    eim.unregisterPlayer(player);
    if ((player.getCarnivalParty().getMembers().size() - 1) < 1) {
        player.getCarnivalParty().removeMember(player);
        disposeAll(eim);
    } else {
        player.getCarnivalParty().removeMember(player);
    }
}

function onMapLoad(eim, chr) {
    if (!eim.getProperty("started").equals("true")) {
        disposeAll(eim);
    } else if (chr.getCarnivalParty().getTeam() == 1) {
        var blueParty = getParty(eim, "blue");
        chr.startMonsterCarnival(blueParty.getAvailableCP(), blueParty.getTotalCP());
    } else {
        var redParty = getParty(eim, "red");
        chr.startMonsterCarnival(redParty.getAvailableCP(), redParty.getTotalCP());
    }
}

function cancelSchedule() {}
function clearPQ(eim) {}
function allMonstersDead(eim) {}
function changedMap(eim, chr, mapid) {}
function playerDead(eim, player) {} // we handle this in playerRevive now