var status = -1;
var map = 105040322;
var num = 6;
var maxp = 1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status <= 1) {
	    cm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
		var selStr = "选择一个你想要去的房间吧.";
		for (var i = 1; i < num; i++) {
			selStr += "\r\n#b#L" + i + "#极度恐惧怪物房间 " + i + " (" + cm.getPlayerCount(map + i) + "/" + maxp + ")#l#k";
		}
	cm.sendSimple(selStr);
    } else if (status == 1) {
		if (selection < 0 || selection >= num) {
			cm.dispose();
		} else if (cm.getHour() < 1 || cm.getHour() > 22 ) {
           cm.sendNext("恐惧怪物房间开放时间；#r21:00-22:00。");
            cm.dispose();
		} else if (cm.getPlayerCount(map + selection) >= maxp) {
			cm.sendNext("这个房间已经满人，请稍后再尝试!");
			status = -1;
		} else {
			cm.warp(map + selection, 0);
			cm.worldMessage(6,"玩家 "+cm.getName()+" 进入【极度恐惧的怪物房间】。");
			cm.dispose();

		}
    }
}