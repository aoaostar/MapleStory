/*
Zakum Altar - Summons Zakum.
*/

function act() {
    rm.changeMusic("Bgm06/FinalFight");
	rm.warp(910000023);
	//rm.getMap().spawnZakum(-10, -215);
    rm.mapMessage("扎昆被火焰之眼的力量召喚出來了。");
	if (!rm.getPlayer().isGM()) {
		rm.getMap().startSpeedRun();
	}
}
