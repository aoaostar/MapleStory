function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1) {
		cm.dispose();
		return;
	} else {
		status++;
		if(mode == 0) {
			cm.sendOk("旅途还很漫长，如果你感觉无聊，可以和船上的其他人聊聊！");
			cm.dispose();
			return;
		}
		if(status == 0) {
			cm.sendYesNo("一旦离开候船事。就会回到原来的地方。你确定要下船吗？");
		} else if(status == 1) {
			cm.warp(200000131);
			cm.dispose();
		}
	}
}
