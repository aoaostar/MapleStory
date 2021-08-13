var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
	if (status >= 0 && mode == 0) {
		cm.sendNext("美丽的上海外滩，难道你不想去看看吗！真遗憾。");
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		cm.sendYesNo("嗨！我是洪姓驾驶员，我负责驾驶飞往上海的飞机。经过长年的飞行，我的驾驶技术已经很了不得。如果你有 #b2000 金币#k. 我就可以带你去美丽的 #b上海外滩#k 怎么样？要去吗？");
	} else if (status == 1) {
		if (cm.getMeso() < 2000) {
			cm.sendNext("你确定你有 #b2000 金币#k？ 如果没有，我可不能免费送你去。");
			cm.dispose();
		} else {
			cm.gainMeso(-2000);
			cm.warp(701000000);
			cm.dispose();
			}		
		}
	}
}