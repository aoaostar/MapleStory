var status = -1;
var map = 209000012;
var num = 3;
var maxp = 3;


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
		var selStr = "#r强强对抗赛，进入房间参加会消耗对抗积分10%。";
		for (var i = 1; i < num; i++) {
			selStr += "\r\n#b#L" + i + "#强强对抗赛 " + i + " (" + cm.getPlayerCount(map + i) + "/" + maxp + ")#l#k";
		}
	cm.sendSimple(selStr);
    } else if (status == 1) {
		if (selection < 0 || selection >= num) {
			cm.dispose();
		} else if (cm.getPlayerCount(map + selection) >= maxp) {
			cm.sendNext("这个房间已经满人，请稍后再尝试!");
			status = -1;
		} else {
			var jf = cm.getBossRank("对抗积分",2);
			cm.warp(map + selection, 0);
			cm.setBossRankCount("对抗积分","-"+jf/10+"");
			cm.dispose();

		}
    }
}