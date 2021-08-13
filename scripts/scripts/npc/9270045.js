function start() {
	cm.sendYesNo("你想离开吗?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
			cm.刷新地图();
		cm.warp(541020700,6);
		
	}
	cm.dispose();
}
