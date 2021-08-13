var status = -1;
var map = 209000014;
var num = 2;
var maxp = 300;


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
		var rs = cm.获取指定地图玩家数量(209000015);
			var jy = cm.getPlayer().getExp();
			 var wj =  cm.获取指定地图玩家数量(209000015);
			  var jb = cm.getMeso();
		var selStr = "#r擂 台 争 霸 赛 #k，欢迎你参加 - \r\n当前擂台人数;#r"+rs+"#k\r\n金币平均收益；#d"+((wj*10000)+(jb/10))+" #k\r\n金币平均损失；#d"+jb/10*wj+" #k\r\n经验平均收益；#d"+jy/50*wj+" #k\r\n经验平均损失；#d"+jy/10*wj+" #k\r\n#r说明；所有收益与参加的玩家数量密切相关。#k";
		for (var i = 1; i < num; i++) {
			selStr += "\r\n#b#L" + i + "#进入擂台争霸赛#l#k";
		}
	cm.sendSimple(selStr);
    } else if (status == 1) {
		if (selection < 0 || selection >= num) {
			cm.dispose();
		} else if (cm.getPlayerCount(map + selection) >= maxp) {
			cm.sendNext("这个房间已经满人，请稍后再尝试!");
			status = -1;
		} else {
			cm.warp(map + selection, 0);
			cm.gainItem(4032217,-2000);
			cm.dispose();

		}
    }
}