importPackage(net.sf.odinms.client);

var cb;
var ride;

function start() {
	status = -1;
	cb = cm.getEventManager("Cabin");
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
			if(cb.getProperty("entry").equals("true")) {
				cm.sendYesNo("非常好，船上还有足够的位置，请准备好开始登船，我们将进入漫长的旅行，你是不是想登船？");
			} else if(cb.getProperty("entry").equals("false") && cb.getProperty("docked").equals("true")) {
				cm.sendOk("本次航班已经出发，请耐心等待下一次航班。");
				cm.dispose();
			} else {
				cm.sendOk("出发前1分钟就停止让客人登船了，请注意站台时间。不过也不要来的太晚了！");
				cm.dispose();
			}
		} else if(status == 1) {
			cb.getInstance("Cabin").registerPlayer(cm.getChar());
			cm.dispose();
		}
	}
}