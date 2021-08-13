var bm;

function start() {
	status = -1;
	bm = cm.getEventManager("Boats");
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1) {
		cm.dispose();
		return;
	} else {
		status++;
		if(mode == 0) {
			cm.sendNext("你还有什么事情再这里没有完成吗？");
			cm.dispose();
			return;
		}
		if (status == 0) {
			if(bm == null) {
				cm.sendNext("脚本发生错误，请联系管理员解决。");
				cm.dispose();
			} else if(bm.getProperty("entry").equals("true")) {
				cm.sendYesNo("坐上船之后，需要飞很久才能到达目的地。如果你在这里有急事要办的话，请先把事情办完，怎么样？你要上船吗？");
			} else if(bm.getProperty("entry").equals("false") && bm.getProperty("docked").equals("true")) {
				cm.sendNext("本次航班已经出发，请等待下一次航班。");
				cm.dispose();
			} else {
				cm.sendNext("出发前5分钟开始才可以入场。请稍等一下。不过也别来的太晚。出发前1分钟就会结束出航准备，无法让客人登船了。");
				cm.dispose();
			}
		} else if(status == 1) {
			cm.warp(101000301);
			cm.dispose();
		}
	}
}
