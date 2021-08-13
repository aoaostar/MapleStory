var status = -1;
var map = 780000002;
var num = 1;
var maxp = 5;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status <= 0) {
	    cm.dispose();
	    return;
	}
		
	status--;
    }
    if (status == 0) {
		var selStr = "";
		for (var i = 0; i < num; i++) {
			selStr += "\r\n#b#L" + i + "#怪物擂台大作战【第三战场】 " + i + " (" + cm.getPlayerCount(map + i) + "/" + maxp + ")#l#k";
		}
	cm.sendSimple(selStr);
    } else if (status == 1) {
		if (selection < 0 || selection >= num) {
			cm.dispose();
		
		} else if (cm.getMapId() == 780000002) {
        cm.sendOk("你已经在第三战场了。");
        cm.dispose();
		 
		} else if (cm.getPlayerCount(map + selection) >= maxp) {
			cm.sendNext("怪物擂台之初始战场已经满人，请等待里面的人进入下一关，或者退出擂台，你才可以加入!");
			status = -1;
		} else {
			cm.warp(map + selection, 0);
			cm.gainItem(4000001,-1000);
			cm.gainItem(4000002,-1000);
			cm.worldMessage(6,"玩家 "+cm.getName()+" 成功进入怪物大作战【第三战场】。");
			cm.dispose();

		}
    }
}