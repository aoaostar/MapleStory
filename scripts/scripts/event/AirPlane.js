importPackage(Packages.tools);

var closeTime = 240000; //The time to close the gate
var beginTime = 300000; //The time to begin the ride
var rideTime = 60000; //The time that require move to destination
var KC_bfd;
var Plane_to_CBD;
var CBD_docked;
var CBD_bfd;
var Plane_to_KC;
var KC_docked;

var stopEntryTask;
var stopEntryOpen;
var takeoffTask;
var takeoffOpen;
var arrivedTask;
var arrivedOpen;


function init() {
    KC_bfd = em.getChannelServer().getMapFactory().getMap(540010100);
    CBD_bfd = em.getChannelServer().getMapFactory().getMap(540010001);
    Plane_to_CBD = em.getChannelServer().getMapFactory().getMap(540010101);
    Plane_to_KC = em.getChannelServer().getMapFactory().getMap(540010002);
    CBD_docked = em.getChannelServer().getMapFactory().getMap(103000000);
    KC_docked = em.getChannelServer().getMapFactory().getMap(540010000);
	stopEntryOpen = false;
	takeoffOpen = false;
	arrivedOpen = false;
    scheduleNew();
}

function scheduleNew() {
    stopEntryTask = em.schedule("stopEntry", closeTime);
	stopEntryOpen = true;
    takeoffTask = em.schedule("takeoff", beginTime);
	takeoffOpen = true;
}

function stopEntry() {
    em.setProperty("entry","false");
}

function takeoff() {
    em.setProperty("entry", "true");
    var temp1 = KC_bfd.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Plane_to_KC, Plane_to_KC.getPortal(0));
    }
    var temp2 = CBD_bfd.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(Plane_to_CBD, Plane_to_CBD.getPortal(0));
    }
    arrivedTask = em.schedule("arrived", rideTime);
	arrivedOpen = true;
    scheduleNew();
}

function arrived() {
    var temp1 = Plane_to_CBD.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(CBD_docked, CBD_docked.getPortal(0));
    }
    var temp2 = Plane_to_KC.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(KC_docked, KC_docked.getPortal(0));
    }
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
	
}