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
		cm.sendNext("还有别的事在这里没办完吗？");
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		cm.sendYesNo("你好。我是码头的售票员。你想离开金银岛，前往其他地区吗？从这里开往神秘岛的#b天空之城站#k的飞艇以#b整点为基准，每15分钟出发一班#k，你是不是去想要去？");
	} else if (status == 1) {
		cm.sendNext("如果想去天空之城，请和右边的 #b检票员#k 说话。");
		cm.dispose();
		}
	}
}