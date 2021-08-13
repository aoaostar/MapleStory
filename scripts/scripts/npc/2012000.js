var ticket = new Array(4031047, 4031074, 4031331, 4031576);
var cost = new Array(5000, 6000, 30000, 6000);
var tmsg = new Array(15, 10, 10, 10);
var mapNames = new Array("魔法密林", "玩具城", "神木村", "阿里安特");
var mapName2 = new Array("魔法密林", "玩具城", "神木村", "阿里安特");
var select;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1) {
		cm.dispose();
	} else {
		if(mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if(mode == 0) {
			cm.sendNext("你还有什么事情在这里没办完吗？");
			cm.dispose();
			return;
		}
		if(mode == 1) {
			status++;
		}
		if(status == 0) {
			var where = "您好，我是天空之城售票员，我负责销售开往各地船票。你想购买去那里的船票呢？";
			for (var i = 0; i < ticket.length; i++) {
				where += "\r\n#L" + i + "##b" + mapNames[i] + "#k#l";
			}
			cm.sendSimple(where);
		} else if(status == 1) {
			select = selection;
			cm.sendYesNo("天空之城开往 #b" + mapName2[select] + "#k 的飞船每 "+tmsg[select]+" 分钟一班，船票的售价 #b"+cost[select]+" 金币#k。你确定要购买开往 #b"+mapName2[select]+"#k 的船票吗？");
		} else if(status == 2) {
			if(cm.getMeso() < cost[select] || !cm.canHold(ticket[select])) {
				cm.sendOk("你确定你有 #b" + cost[select] + " 金币#k吗？ 没有船票是不可以登船的。");
				cm.dispose();
			} else {
				cm.gainMeso(-cost[select]);
				cm.gainItem(ticket[select],1);
				cm.dispose();
			}
		}
	}
}