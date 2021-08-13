var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.dispose();
	return;
    }
    if (status == 0) {
		if (cm.getPlayer().getLevel() < 40 && cm.haveItem(4032494)) {
			cm.sendYesNo("你想移动到隐藏地图?");
		} else {
			cm.sendOk("你需要小于40级，需要进入要有9400611勋章.");
			cm.dispose();
		}
} else {
	cm.spawnMob_map(9400611, 677000007,38,73 );
	cm.warp(677000006,0);
	cm.dispose();
    }
}