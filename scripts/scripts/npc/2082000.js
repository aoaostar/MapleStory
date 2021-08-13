var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			cm.sendNext("开往天空之城的飞艇每#b10分钟#k一班。如果你需要去#b天空之城#k的话。我想我可以帮助你！怎么样？要不要去天空之城？");
		} else if (status == 1) {
			cm.sendOk("很好，因为冒险的改革。现在乘坐飞艇已经不在需要购票了。你可以直接去找#b乘务员 塔咪#k前往天空之城。");
		} else if (status == 2) {
			cm.dispose();
		}
	}
}