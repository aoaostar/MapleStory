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
			if (cm.getHour() < 1 || cm.getHour() > 23 ){//hour=时//month=月//
	cm.sendOk("每日活跃度领奖时间为；#r19：00 - 23 ：00");
     cm.dispose();
	 }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 正方箭头 + "活跃度 5  点领奖\r\n";
		    var a2 = "#L2#" + 正方箭头 + "活跃度 10 点领奖\r\n";
			var a3 = "#L3#" + 正方箭头 + "活跃度 20 点领奖\r\n";


            cm.sendSimple("#d你好这里每日活跃度领奖处，你今天是否活跃呢。!!!\r\n\r\n"+a1+""+a2+""+a3+"");

        } else if (status == 1) {
         	if (selection == 1) { //5点
			if (cm.getBossLog('zymxd') >=5 && cm.getBossLog('hydlj') == 0){          // {&& cm.haveItem(4031210)
				cm.setBossLog("hydlj");
				cm.setBossLog("hydlj5");
				cm.gainItem(2000005, 50);
				cm.sendOk("恭喜你领取5点活跃度奖励#i2000005#x50");
				cm.worldMessage(6,"[活跃度奖励]：恭喜，玩家 "+cm.getName()+" 领取 5 点活跃度奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("你的活跃度不够，或者你已经领取过了。");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 2) { //10
			if (cm.getBossLog('zymxd') >=10 && cm.getBossLog('hydlj5') == 1){          // {&& cm.haveItem(4031210)
				//cm.gainItem(3994416, -100);
				//cm.gainItem(3994416, -100);
				cm.setBossLog("hydlj5");
				cm.gainNX(500);
				cm.sendOk("恭喜你领取10点活跃度奖励#r点劵 x 500");
				cm.worldMessage(6,"[活跃度奖励]：恭喜，玩家 "+cm.getName()+" 领取 10 点活跃度奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("你的活跃度不够，或者你已经领取过了");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 3) { //兑换点卷
			if(cm.getBossLog('hydlj55')<1 ){
				
			if ( cm.getBossLog('zymxd') >=20 && cm.getBossLog('hydlj5') == 2 ) {
				//cm.gainItem(3994416, -100);
				cm.setBossLog('hydlj55');
				cm.setBossLog('hydlj5');
				cm.gainNX(2000);
				cm.sendOk("恭喜你领取20点活跃度奖励#r点劵 x 2000");
				cm.worldMessage(6,"[活跃度奖励]：恭喜，玩家 "+cm.getName()+" 领取 20 点活跃度奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("你的活跃度不够，或者你已经领取过了");
				cm.dispose();
				return;
			}
			}
			else{
				cm.sendOk("今天已经领取今天所有的活跃度奖励。");
				cm.dispose();
			}
	    	}
        }
    }
}
