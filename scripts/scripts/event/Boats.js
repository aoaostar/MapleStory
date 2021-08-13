importPackage(Packages.client);
importPackage(Packages.tools);
importPackage(Packages.server.life);

var closeTime = 120000; //船关闭搭乘的时间
var beginTime = 120000; //船启航的时间
// 1秒=1000毫秒10分钟=10*60*1000=600000 毫秒
var rideTime = 600000; //搭船所需时间
var invasionTime = 60000; //确认蝙蝠怪召唤的时间
var Orbis_btf;
var Boat_to_Orbis;
var Orbis_Boat_Cabin;
var Orbis_docked;
var Ellinia_btf;
var Ellinia_Boat_Cabin;
var Ellinia_docked;

var stopEntryTask;
var stopEntryOpen;
var takeoffTask;
var takeoffOpen;
var arrivedTask;
var arrivedOpen;
var invasionTask;
var invasionOpen;

function init() {
    Orbis_btf = em.getChannelServer().getMapFactory().getMap(200000112);
    Ellinia_btf = em.getChannelServer().getMapFactory().getMap(101000301);
    Boat_to_Orbis = em.getChannelServer().getMapFactory().getMap(200090010);
    Boat_to_Ellinia = em.getChannelServer().getMapFactory().getMap(200090000);
    Orbis_Boat_Cabin = em.getChannelServer().getMapFactory().getMap(200090011);
    Ellinia_Boat_Cabin = em.getChannelServer().getMapFactory().getMap(200090001);
    Orbis_docked = em.getChannelServer().getMapFactory().getMap(200000100);
    Ellinia_docked = em.getChannelServer().getMapFactory().getMap(101000300);
    Orbis_Station = em.getChannelServer().getMapFactory().getMap(200000111);
	stopEntryOpen = false;
	takeoffOpen = false;
	arrivedOpen = false;
	invasionOpen = false;
    OBoatsetup();
    EBoatsetup();
    scheduleNew();
}

function scheduleNew() {
    // Ellinia_docked.setDocked(true);
    // Orbis_Station.setDocked(true);
    Ellinia_docked.broadcastMessage(MaplePacketCreator.boatPacket(true));
    Orbis_Station.broadcastMessage(MaplePacketCreator.boatPacket(true));
    em.setProperty("docked", "true");
    em.setProperty("entry", "true");
    em.setProperty("haveBalrog", "false");
    em.setProperty("haveBalrog1", "false");
    stopEntryTask = em.schedule("stopentry", closeTime);
	stopEntryOpen = true;
    takeoffTask = em.schedule("takeoff", beginTime);
	takeoffOpen = true;
}

function stopentry() {
    em.setProperty("entry", "false");
    Orbis_Boat_Cabin.resetReactors();
    Ellinia_Boat_Cabin.resetReactors();
}

function takeoff() {
    em.setProperty("docked", "false");
    var temp1 = Orbis_btf.getCharacters().iterator();
    while (temp1.hasNext()) {
        temp1.next().changeMap(Boat_to_Ellinia, Boat_to_Ellinia.getPortal(0));
    }
    var temp2 = Ellinia_btf.getCharacters().iterator();
    while (temp2.hasNext()) {
        temp2.next().changeMap(Boat_to_Orbis, Boat_to_Orbis.getPortal(0));
    }
    //Ellinia_docked.setDocked(false);
    //Orbis_Station.setDocked(false);
    Ellinia_docked.broadcastMessage(MaplePacketCreator.boatPacket(false));
    Orbis_Station.broadcastMessage(MaplePacketCreator.boatPacket(false));
    invasionTask = em.schedule("invasion", invasionTime);
	invasionOpen = true;
    arrivedTask = em.schedule("arrived", rideTime);
	arrivedOpen = true;
}

function arrived() {
    var temp1 = Boat_to_Orbis.getCharacters().iterator();
    while (temp1.hasNext()) {
        temp1.next().changeMap(Orbis_docked, Orbis_docked.getPortal(0));
    }
    var temp2 = Orbis_Boat_Cabin.getCharacters().iterator();
    while (temp2.hasNext()) {
        temp2.next().changeMap(Orbis_docked, Orbis_docked.getPortal(0));
    }
    var temp3 = Boat_to_Ellinia.getCharacters().iterator();
    while (temp3.hasNext()) {
        temp3.next().changeMap(Ellinia_docked, Ellinia_docked.getPortal(0));
    }
    var temp4 = Ellinia_Boat_Cabin.getCharacters().iterator();
    while (temp4.hasNext()) {
        temp4.next().changeMap(Ellinia_docked, Ellinia_docked.getPortal(0));
    }
    Boat_to_Orbis.killAllMonsters(false);
    Boat_to_Ellinia.killAllMonsters(false);
    scheduleNew();
}

function invasion() {
    //var numspawn;
    //var chance = Math.floor(Math.random() * 10);
    //if (chance <= 4)
    //    numspawn = 0;
    //else if (chance == 5)
    //    numspawn = 1;
    //else
    //    numspawn = 2;
    //if (numspawn == 2) {
		var numspawn;
		numspawn = 2;
        for (var i = 0; i < numspawn; i++) {
            Boat_to_Orbis.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(8150000), new java.awt.Point(485, -221));
            Boat_to_Ellinia.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(8150000), new java.awt.Point(-590, -221));
        }
        // Boat_to_Orbis.setDocked(true);
        // Boat_to_Ellinia.setDocked(true);
        Boat_to_Orbis.broadcastMessage(MaplePacketCreator.boatEffect(1034));
        Boat_to_Ellinia.broadcastMessage(MaplePacketCreator.boatEffect(1034));
        Boat_to_Orbis.broadcastMessage(MaplePacketCreator.musicChange("Bgm04/ArabPirate"));
        Boat_to_Ellinia.broadcastMessage(MaplePacketCreator.musicChange("Bgm04/ArabPirate"));
        em.setProperty("haveBalrog", "true");
    //} else if (numspawn == 1) {
    //    //Boat_to_Orbis.setDocked(true);
    //     // Boat_to_Ellinia.setDocked(true);
    //    Boat_to_Orbis.broadcastMessage(MaplePacketCreator.boatEffect(1034));
    //    Boat_to_Ellinia.broadcastMessage(MaplePacketCreator.boatEffect(1034));
    //    Boat_to_Orbis.broadcastMessage(MaplePacketCreator.musicChange("Bgm04/ArabPirate"));
    //    Boat_to_Ellinia.broadcastMessage(MaplePacketCreator.musicChange("Bgm04/ArabPirate"));
    //    em.setProperty("haveBalrog1", "true");
    //}
}

function OBoatsetup() {
    em.getChannelServer().getMapFactory().getMap(200090011).getPortal("out00").setScriptName("OBoat1");
    em.getChannelServer().getMapFactory().getMap(200090011).getPortal("out01").setScriptName("OBoat2");
}

function EBoatsetup() {
    em.getChannelServer().getMapFactory().getMap(200090001).getPortal("out00").setScriptName("EBoat1");
    em.getChannelServer().getMapFactory().getMap(200090001).getPortal("out01").setScriptName("EBoat2");
}

function cancelSchedule() {
	if( stopEntryOpen ) {
		stopEntryTask.cancel(true);
	}
	if( takeoffOpen ) {
		takeoffTask.cancel(true);
	}
	if( arrivedOpen ) {
		arrivedTask.cancel(true);
	}
	if( invasionOpen ) {
		invasionTask.cancel(true);
	}
}