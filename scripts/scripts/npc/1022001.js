/* Author: Xterminator
	NPC Name: 		Regular Cab
	Map(s): 		Victoria Road : Perion (102000000)
	Description: 		Perion Cab
*/

var status = 0;
var maps = Array(104000000, 100000000, 101000000, 103000000, 120000000, 105040300);
var cost = Array(1000, 1000, 1000, 1000, 1000, 1000);
var costBeginner = Array(100, 100, 100, 100, 100, 100);
var show;
var sCost;
var selectedMap = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0) {
	cm.dispose();
	return;
    } else if (status >= 2 && mode == 0) {
	cm.sendNext("这个镇上也有很多值得一看的地方。当你需要去另一个城市的时候，再回来找我吧！");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendNext("你好，我是普通出租车。如果你想安全快速的从一个城镇到另一个城镇，那就乘坐我们的出租车吧。我们很乐意带您到您的目的地去。只要你愿意支付相应的价格。");
    } else if (status == 1) {
	var job = cm.getJob();
	if (job == 0 || job == 1000 || job == 2000) {
	    var selStr = "We have a special 90% discount for beginners. 请选择您的目的地。支付费用之后，我将会传送你去往别的城镇。#b";
	    for (var i = 0; i < maps.length; i++) {
		selStr += "\r\n#L" + i + "##m" + maps[i] + "# (" + costBeginner[i] + " 金币)#l";
	    }
	} else {
	    var selStr = "请选择您的目的地。支付费用之后，我将会传送你去往别的城镇。#b";
	    for (var i = 0; i < maps.length; i++) {
		selStr += "\r\n#L" + i + "##m" + maps[i] + "# (" + cost[i] + " 金币)#l";
	    }
	}
	cm.sendSimple(selStr);
    } else if (status == 2) {
	var job = cm.getJob();
	if (job == 0 || job == 1000 || job == 2000) {
	    sCost = costBeginner[selection];
	    show = costBeginner[selection];
	} else {
	    sCost = cost[selection];
	    show = cost[selection];
	}
	cm.sendYesNo("你在这里没什么可干的，恩？你真的想去 #b#m" + maps[selection] + "##k? 这将花费你 #b" + show + " 金币#k.");
	selectedMap = selection;
    } else if (status == 3) {
	if (cm.getMeso() < sCost) {
	    cm.sendNext("You don't have enough mesos. Sorry to say this, but without them, you won't be able to ride the cab.");
	} else {
	    cm.gainMeso(-sCost);
	    cm.warp(maps[selectedMap]);
	}
	cm.dispose();
    }
}