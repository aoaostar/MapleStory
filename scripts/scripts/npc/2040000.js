var cost = 6000;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1) {
		cm.dispose();
	} else {
		if(mode == 1) {
			status++;
		}
		if(mode == 0) {
			cm.sendNext("你还有什么别的事情在这里没有处理好吗？");
			cm.dispose();
			return;
		}
		if(status == 0) {
			cm.sendYesNo("您好~！我是美尔，我负责销售开往天空之城的车票。 开往天空之城的火车每 #b10分钟#k 一班，车票的售价为 #b"+cost+" 金币#k。 确定要购买 #b#t4031045##k吗？");
		} else if(status == 1) {
			if(cm.getMeso() >= cost && cm.canHold(4031045)) {
				cm.gainItem(4031045,1);
				cm.gainMeso(-cost);
			} else {
				cm.sendOk("你确定有 #b"+cost+" 金币#k吗？");
			}
			cm.dispose();
		}
	}
}