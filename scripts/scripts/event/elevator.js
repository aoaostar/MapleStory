var goingUpTask;
var onUpTask;
var goingDownTask;
var onDownTask;
var goingUpOpen = false;
var onUpOpen = false;
var goingDownOpen = false;
var onDownOpen = false;


function init() {
    scheduleNew();
	goingUpOpen = false;
	onUpOpen = false;
	goingDownOpen = false;
	onDownOpen = false;
}

function scheduleNew() {
    em.setProperty("isUp","true");
    em.setProperty("isDown","true");
    onDown();
}

function onDown() {
    em.getChannelServer().getMapFactory().getMap(222020100).resetReactors();
    em.warpAllPlayer(222020210, 222020211);
    em.setProperty("isDown","true");
    goingUpTask = em.schedule("goingUp", 60000);
	goingUpOpen = true;
}

function goingUp() {
    em.warpAllPlayer(222020110, 222020111);
    em.setProperty("isDown","false");
    onUpTask = em.schedule("onUp", 50000);
	onUpOpen = true;
    em.getChannelServer().getMapFactory().getMap(222020100).setReactorState();
}

function onUp() {
    em.getChannelServer().getMapFactory().getMap(222020200).resetReactors();
    em.warpAllPlayer(222020111, 222020200);
    em.setProperty("isUp","true");
    goingDownTask = em.schedule("goingDown", 60000);
	goingDownOpen = true;
}

function goingDown() {
    em.warpAllPlayer(222020211, 222020100);
    em.setProperty("isUp","false");
    onDownTask = em.schedule("onDown", 50000);
	onDownOpen = true;
    em.getChannelServer().getMapFactory().getMap(222020200).setReactorState();
}

function cancelSchedule() {
	if( goingUpOpen ) {
		goingUpTask.cancel(true);
	}
	if( onUpOpen ) {
		onUpTask.cancel(true);
	}
	if( goingDownOpen ) {
		goingDownTask.cancel(true);
	}
	if( onDownOpen ) {
		onDownTask.cancel(true);
	}
}