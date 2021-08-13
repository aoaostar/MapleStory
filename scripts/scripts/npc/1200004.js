importPackage(net.sf.odinms.client);
var menu = new Array("里恩","明珠港","明珠港","里恩");
var cost = new Array(80,80,80,80);
var EnToJ;
var display = "";
var btwmsg;
var method;

function start() {
	status = -1;
	EnToJ = cm.getEventManager("EnToJ");
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1) {
		cm.dispose();
		return;
	} else {
		if(mode == 0 && status == 0) {
			cm.dispose();
			return;
		} else if(mode == 0) {
			cm.sendNext("不想去就算了！");
			cm.dispose();
			return;
		}
		status++;
		if (status == 0) {
			for(var i=0; i < menu.length; i++) {
				if(cm.getChar().getMapId() == 140020300 && i < 1) {
					display += "\r\n#L"+i+"#移动时间大约是#b1分钟#k，费用是#b("+cost[i]+")#k金币。";
				}
			}
			if(cm.getChar().getMapId() == 104000000) {
				cm.sendYesNo("我亲爱的朋友，你要去#b里恩#k了吗？我可以直接送你到冰雪岛的#b里恩#k……不过最近也要用#b800金币#k买票。要去里恩吗？");
			} else {
				cm.sendSimple("你要离开里恩了吗？这船可以到金银岛的#b明珠港#k……不过#b最近80#k要花钱买票了。要去明珠港吗？大概1分钟左右就到了。\r\n" + display);
			}
		} else if(status == 1) {
			if(selection == 2) {
				cm.sendYesNo("你确定要去 #b"+menu[2]+"#k ？ 那么你要付给我 #b"+cost[2]+" 金币#k。");
			} else {
				if(cm.getMeso() < cost[selection]) {
					cm.sendNext("你确定你有足够的金币？");
					cm.dispose();
				} else {
					if(cm.getChar().getMapId() == 104000000) {
						cm.gainMeso(-800);
						cm.warpBack(200090060, 140020300, 80);
						cm.dispose();
					} else {
						if(EnToJ.getProperty("isRiding").equals("false")) {
							cm.gainMeso(-cost[selection]);
							EnToJ.newInstance("EnToJ");
							EnToJ.setProperty("myRide",selection);
							EnToJ.getInstance("EnToJ").registerPlayer(cm.getChar());
							cm.dispose();
						} else {
							cm.sendOk("系统运算错误，请更换频道后再上船！");
							cm.dispose();
						}
					}
				}
			}
		} else if(status == 2) {
			if(cm.getMeso() < cost[2]) {
				cm.sendNext("你确定你有足够的金币？");
				cm.dispose();
			} else {
				cm.sendOk("系统运算错误，请更换频道后再上船！");
				cm.dispose();
			}
		}
	}
}
