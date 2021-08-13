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
			cm.sendOk("你会作出你的决定的，和其他人聊天吧！");
			cm.dispose();
			return;
		}
		if(status == 0) {
			cm.sendYesNo("你真的要离开候车室吗？这样是不退车票的哦。你确定要离开？");
		} else if(status == 1) {
			if(cm.getChar().getMapId() == 220000111)
				cm.warp(220000110);
			else
				cm.warp(220000121);
			cm.dispose();
		}
	}
}
