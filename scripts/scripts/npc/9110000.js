var 雪山 = "#fEffect/SkillName1.img/1001003/雪山#";
var status = -1;
var map = 209000001;
var num = 15;
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
		var selStr = " - ";
		for (var i = 1; i < num; i++) {
			selStr += "\r\n#b#L" + i + "#奇遇" + i + " (" + cm.getPlayerCount(map + i) + "/" + maxp + ")#l#k";
		}
	cm.sendSimple(selStr);
    } else if (status == 1) {
		if (selection < 0 || selection >= num) {
			cm.dispose();
		} else if (cm.getPlayer().getClient().getChannel() !=1 ) {
			cm.sendOk("只有 #r2#k 线才可以进入。");
			cm.dispose();
            cm.dispose();
		} else if (cm.getPlayerCount(map + selection) >= maxp) {
			cm.sendNext("这个房间已经满人，请稍后再尝试!");
			status = -1;
		} else {
			cm.warp(map + selection, 0);
			cm.setBossLog("kjfj");
			cm.worldMessage(6,"玩家 "+cm.getName()+" 今日第 "+ cm.getChar().getBossLog("kjfj")+" 次进入【极度恐惧的怪物房间】。");
			cm.dispose();

		}
    }
}