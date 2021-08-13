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
		if ( cm.getHour() < 20 || cm.getHour() > 20){//hour=时//month=月//
	cm.sendOk("每日寻宝开放时间；#r2 0 ：0 0 - 2 0 ：5 9 \r\n                   ");
     cm.dispose();
	 }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 正方箭头 + "  每 日 寻 宝\r\n";
		   


            cm.sendSimple("#r每天寻寻寻宝，心情呗呗的好~(*^__^*) 嘻嘻…#k!!!\r\n\r\n"+a1+"");

        } else if (status == 1) {
         	if (selection == 1) { //5点
			if (cm.getBossLog('mrxb') < 5 ){          // {&& cm.haveItem(4031210)
				cm.setBossLog("mrxb");
			
				cm.warp(109010000);
				cm.sendOk("快，抓紧时间到处找找。宝箱就在这些地方");
				cm.worldMessage(6,"[每日寻宝]：玩家 "+cm.getName()+" 进入寻宝地方 。");
				cm.dispose();
			} else {
				cm.sendOk("你今天已经没有机会了，明天来吧");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 2) { //10
			if (cm.getBossLog('mrhl12') ==0 && cm.getHour() == 12){          // {&& cm.haveItem(4031210)
				cm.setBossLog("mrhl12");
				cm.setBossLog("zymxd");
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
				cm.setBossLog("mrhl21");
				cm.setBossLog("zymxd");
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
