/**
 * @触发条件：开拍卖功能
 * @每日签到：领取物品 npc
 * @npcName：冒险岛运营员
 * @npcID：   9900004
 **/
importPackage(net.sf.cherry.client);
var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 正方箭头 + "  07:00-07:59 领 奖\r\n";
		    var a2 = "#L2#" + 正方箭头 + "  12:00-12:59 领 奖\r\n";
			var a3 = "#L3#" + 正方箭头 + "  21:00-21:59 领 奖\r\n";


            cm.sendSimple("#r每天都有好礼相送，请准时来哦~~#k!!!\r\n\r\n"+a1+""+a2+""+a3+"");

        } else if (status == 1) {
         	if (selection == 1) { //5点
			if (cm.getBossLog('mrhl07') ==0 && cm.getHour() == 7 && minute == 7){          // {&& cm.haveItem(4031210)
				cm.setBossLog("mrhl07");
				
				cm.gainItem(2000005, 50);
				cm.sendOk("恭喜你领取#r  7;00-7:59 #k成功");
				cm.worldMessage(6,"[每日好礼]：恭喜，玩家 "+cm.getName()+" 领取 7;00-7:59 奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("不在领取的时间段，或者你已经领取过了。");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 2) { //10
			if (cm.getBossLog('mrhl12') ==0 && cm.getHour() == 12){          // {&& cm.haveItem(4031210)
				cm.setBossLog("mrhl12");
				
				cm.gainItem(2000005, 50);
				cm.sendOk("恭喜你领取#r  12;00-12:59 #k成功");
				cm.worldMessage(6,"[每日好礼]：恭喜，玩家 "+cm.getName()+" 领取 12;00-12:59 奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("不在领取的时间段，或者你已经领取过了。");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 3) { //兑换点卷
			if(cm.getBossLog('mrlj333')<1 ){
				
			if (cm.getBossLog('mrhl21') ==0 && cm.getHour() == 21){
				cm.setBossLog("mrhl21");
				
				cm.gainItem(2000005, 50);
				cm.sendOk("恭喜你领取#r  21;00-21:59 #k成功");
				cm.worldMessage(6,"[每日好礼]：恭喜，玩家 "+cm.getName()+" 领取 21;00-21:59 奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("不在领取的时间段，或者你已经领取过了。");
				cm.dispose();
				return;
			}
			}
			else{
				cm.sendOk("今天已经领取今天所有的每日好礼奖励。");
				cm.dispose();
			}
	    	}
        }
    }
}
