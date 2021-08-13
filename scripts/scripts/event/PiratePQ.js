/* 
 * This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
	THIS  FILE WAS MADE BY JVLAPLE. REMOVING THIS NOTICE MEANS YOU CAN'T USE THIS SCRIPT OR ANY OTHER SCRIPT PROVIDED BY JVLAPLE.
 */

/*
 * @Author Jvlaple
 * 
 * Pirate Party Quest for OdinMS 
   odinMS -  Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>
	This script and all others associated with it Copyright (C) Jvlaple 2008.
 */

importPackage(java.lang);
importPackage(net.sf.odinms.world);
importPackage(net.sf.odinms.client);
importPackage(net.sf.odinms.server.maps);

var exitMap;
var instanceId;
var minPlayers = 1;

function init() {
	instanceId = 1;
}

function monsterValue(eim, mobId) {
	return 1;
}

function setup() {
	instanceId = em.getChannelServer().getInstanceId();
	exitMap = em.getChannelServer().getMapFactory().getMap(925100700); //Teh exit map :) <---------
	var instanceName = "PiratePQ" + instanceId;

	var eim = em.newInstance(instanceName);
	
	var mf = eim.getMapFactory();
	
	em.getChannelServer().addInstanceId();
	
	var map = mf.getMap(925100000);//wutt
	var startMap = eim.getMapInstance(925100000);
	var headMap = eim.getMapInstance(925100100);
	var deckMap1 = eim.getMapInstance(925100200);
	var bellflowerMap1 = eim.getMapInstance(925100201);
	var devotedMap1 = eim.getMapInstance(925100202);
	var deckMap2 = eim.getMapInstance(925100300);
	var bellflowerMap2 = eim.getMapInstance(925100301);
	var devotedMap2 = eim.getMapInstance(925100302);
	var eliminateMap = eim.getMapInstance(925100400);
	var bossMap = eim.getMapInstance(925100500);
	startMap.shuffleReactors();
	deckMap1.shuffleReactors();
	deckMap2.shuffleReactors();
    startMap.getPortal(3).setScriptName("davy_next0");
    headMap.getPortal(1).setScriptName("davy_next1");
	//Through the Deck  I = nextmapportal = 2 area of bellflower I = 1; lord pirates servant  I=3
	//Area of bellflower I - Exitportal = 1;
	//Lord pirates servant  I = Exitportal = 1;
	//Through the Deck  II = nextmapportal = 2 area of bellflower I = 1; lord pirates servant  I=3
	//Area of bellflower I I- Exitportal = 1;
	//Lord pirates servant  II = Exitportal = 1;
	//eliminate pirates - nextportal = 1;
	eim.setProperty("openedBoxes", 0); //This is used to change the boss in stage 6 o.O
	eim.setProperty("entryTimestamp",System.currentTimeMillis() + (5 * 60000)); //Current time
	//em.schedule("broadcastClock", 1500);
	em.schedule("timeOut", 30000);
	
	return eim;
}

function playerEntry(eim, player) {
	var map = eim.getMapInstance(925100000);
	player.changeMap(map, map.getPortal(0));
	
	//THE CLOCK IS SHIT o_O
	player.getClient().getSession().write(net.sf.odinms.tools.MaplePacketCreator.getClock((Long.parseLong(eim.getProperty("entryTimestamp")) - System.currentTimeMillis()) / 1000));
}

function playerDead(eim, player) {
}

function playerRevive(eim, player) {
	if (eim.isLeader(player)) { //check for party leader
		//boot whole party and end
		var party = eim.getPlayers();
		for (var i = 0; i < party.size(); i++) {
			playerExit(eim, party.get(i));
		}
		eim.dispose();
	}
	else { //boot dead player
		// If only 5 players are left, uncompletable:
		var party = eim.getPlayers();
		if (party.size() <= minPlayers) {
			for (var i = 0; i < party.size(); i++) {
				playerExit(eim,party.get(i));
			}
			eim.dispose();
		}
		else
			playerExit(eim, player);
	}
}

function playerDisconnected(eim, player) {
	if (eim.isLeader(player)) { //check for party leader
		//PWN THE PARTY (KICK OUT)
		var party = eim.getPlayers();
		for (var i = 0; i < party.size(); i++) {
			if (party.get(i).equals(player)) {
				removePlayer(eim, player);
			}			
			else {
				playerExit(eim, party.get(i));
			}
		}
		eim.dispose();
	}
	else { //KICK THE D/CED CUNT
		// If only 5 players are left, uncompletable:
		var party = eim.getPlayers();
		if (party.size() < minPlayers) {
			for (var i = 0; i < party.size(); i++) {
				playerExit(eim,party.get(i));
			}
			eim.dispose();
		}
		else
			playerExit(eim, player);
	}
}

function leftParty(eim, player) {			
	// If only 5 players are left, uncompletable:
	var party = eim.getPlayers();
	if (party.size() <= minPlayers) {
		for (var i = 0; i < party.size(); i++) {
			playerExit(eim,party.get(i));
		}
		eim.dispose();
	}
	else
		playerExit(eim, player);
}

function disbandParty(eim) {
	//boot whole party and end
	var party = eim.getPlayers();
	for (var i = 0; i < party.size(); i++) {
		playerExit(eim, party.get(i));
	}
	eim.dispose();
}

function playerExit(eim, player) {
	eim.unregisterPlayer(player);
	player.changeMap(exitMap, exitMap.getPortal(0));
}

//Those offline cuntts
function removePlayer(eim, player) {
	eim.unregisterPlayer(player);
	player.getMap().removePlayer(player);
	player.setMap(exitMap);
}

function clearPQ(eim) {
	//HTPQ does nothing special with winners
	var party = eim.getPlayers();
	for (var i = 0; i < party.size(); i++) {
		playerExit(eim, party.get(i));
	}
	eim.dispose();
}

function allMonstersDead(eim) {
        //Open Portal? o.O
}

function cancelSchedule() {
}

function timeOut() {
	var iter = em.getInstances().iterator();
	while (iter.hasNext()) {
		var eim = iter.next();
		var secondsLeft = (Long.parseLong(eim.getProperty("entryTimestamp")) - System.currentTimeMillis()) / 1000;
		if (secondsLeft < 10) {
			if (eim.getPlayerCount() > 0) {
				var pIter = eim.getPlayers().iterator();
				while (pIter.hasNext()) {
					playerExit(eim, pIter.next());
				}
			}
			eim.dispose();
		} else {
			eim.schedule("timeOut", 200);
		}
	}
}

//function setClock(eim, mins) {
//	eim.setProperty("entryTimeStamp", 1000 * 60 * mins);
//}
//
//function playerClocks(eim, player) {
//  if (player.getMap().hasTimer() == false){
//		player.getClient().getSession().write(net.sf.odinms.tools.MaplePacketCreator.getClock((Long.parseLong(eim.getProperty("entryTimestamp")) - System.currentTimeMillis()) / 1000));
//		//player.getMap().setTimer(true);
//	}
//}
//
//function playerTimer(eim, player) {
//	if (player.getMap().hasTimer() == false) {
//		player.getMap().setTimer(true);
//	}
//}
//
//function broadcastClock(eim, player) {
//	//var party = eim.getPlayers();
//	var iter = em.getInstances().iterator();
//	while (iter.hasNext()) {
//		var eim = iter.next();
//		if (eim.getPlayerCount() > 0) {
//			var pIter = eim.getPlayers().iterator();
//			while (pIter.hasNext()) {
//				playerClocks(eim, pIter.next());
//			}
//		}
//		//em.schedule("broadcastClock", 1600);
//	}
//	// for (var kkl = 0; kkl < party.size(); kkl++) {
//		// party.get(kkl).getMap().setTimer(true);
//	// }
//	var iterr = em.getInstances().iterator();
//	while (iterr.hasNext()) {
//		var eim = iterr.next();
//		if (eim.getPlayerCount() > 0) {
//			var pIterr = eim.getPlayers().iterator();
//			while (pIterr.hasNext()) {
//				//playerClocks(eim, pIter.next());
//				playerTimer(eim, pIterr.next());
//			}
//		}
//		//em.schedule("broadcastClock", 1600);
//	}
//	em.schedule("broadcastClock", 1600);
//}

//function setStage(eim, mins) {
	//eim.setProperty("entryTimeStamp", 60000 * mins);
	//em.schedule("timeOut", 60000 * mins);
//}



/*Jvlaple's Checklist...

Entry [X]
Stg 1[X]
Stg 2 [X]
stg 3[x]
stg 4[x]
stg 4a[x]
stg 4b[x]
stg 5[x]
stg 6[x]
rewards[x]
100% Working clock [X]

*/
