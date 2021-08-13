

//odinms_MS
importPackage(net.sf.odinms.tools);
importPackage(net.sf.odinms.client);

var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			
			cm.sendSimple("\r\n\r\n每天都可以来领取请大家注意!\r\n\r\n\r\n\r\n#L2#领取每日抵用卷 ");
			} else if (status == 1) {
			if (selection == 1) {
				cm.sendOk("\t\t\t#e#b外挂一时爽，全家火葬场#n#d\r\n====================================================#k\r\n本服务器开放现金道具交易，任何商城购买的道具均可以和其他玩家#r交易一次#k！点击玩家右键--即可使用#r现金道具交易#k功能！我们提倡玩家进行交易！汗水应该得到回报！#d\r\n====================================================#k\r\n本服务器按照官方服务器进行维护与修复。功能以仿官方为主！喜欢BT重口味的玩家请绕道！#d\r\n====================================================#k\r\n我们不出售#rBT装备，属性点，攻击力，各种装备礼包！人人平等！为了维护游戏平衡！玩家发现BUG请及时反馈给我们进行维护一个更好的游戏环境！#k#d\r\n====================================================#k\r\n本服务器开放现金道具交易，任何商城购买的道具均可以和其他玩家#r交易一次#k！点击玩家右键--即可使用#r现金道具交易#k功能！我们提倡玩家进行交易！汗水应该得到回报！#d\r\n====================================================#k\r\n本服务器按照官方服务器进行维护与修复。功能以仿官方为主！喜欢BT重口味的玩家请绕道！#d\r\n====================================================#k\r\n#e#b服务器无赞助 客服QQ：2215672221.");
				cm.dispose();
			}else if(selection == 2) {
						if((cm.getBossLog('每日抵用') < 1) && cm.getLevel() > 9) {
						cm.sendOk("#b亲爱的#r#h ##b你好，欢迎你的到来！！每日都会为您准备2K抵用卷！！\r\n\r\n");
                        cm.gainDY(2000);
					cm.serverNotice("["+cm.getName()+"]领取了每日抵用，大家记得去领取哦！");
					cm.setBossLog('每日抵用');
					}else{
					cm.sendOk("你已经领取过一次,请明天再来看看.或者您还未到10级别。");
					cm.dispose();
				}									
			} 
		}
	}
}
