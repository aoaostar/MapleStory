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
            var a1 = "#L1##g二转#k【元素魔导师】 - 火毒模式\r\n";
		    var a2 = "#L2##g二转#k【元素魔导师】 - 冰雷模式\r\n";
			var a3 = "#L3##b三转#k【元素魔导师】 - 火毒模式\r\n\r\n";
			var a4 = "#L4##b三转#k【元素魔导师】 - 冰雷模式\r\n";
			var a5 = "#L5##r四转#k【元素魔导师】 - 火毒模式\r\n\r\n";
			var a6 = "#L6##r四转#k【元素魔导师】 - 冰雷模式\r\n";



            cm.sendSimple("#d-元素魔导师-#k\r\n\r\n#r职业简介；掌控了冰，火，雷，毒，四元素的强大魔法师。#k\r\n\r\n\r\n"+a6+""+a5+""+a4+""+a3+""+a2+""+a1+"");

            } else if (status == 1) {
         	if (selection == 1) { 
			if(cm.haveItem(1142231)&& cm.getPlayer().getLevel() > 29){

				
				cm.dispose();
			    cm.openNpc(2000,501);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			}
			if (selection == 2) { 
			if(cm.haveItem(1142231) && cm.getPlayer().getLevel() > 29){

				
				cm.dispose();
			    cm.openNpc(2000,502);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			}
			if (selection == 3) { 
			if(cm.haveItem(1142231) && cm.getPlayer().getLevel() > 69){

				
				cm.dispose();
			    cm.openNpc(2000,503);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			}
			if (selection == 4) { 
			if(cm.haveItem(1142231) && cm.getPlayer().getLevel() > 69){

				
				cm.dispose();
			    cm.openNpc(2000,504);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			
		    }
			if (selection == 5) { 
			if(cm.haveItem(1142231) && cm.getPlayer().getLevel() > 119){

				
				cm.dispose();
			    cm.openNpc(2000,505);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			}
			if (selection == 6) { 
			if(cm.haveItem(1142231) && cm.getPlayer().getLevel() > 119){

				
				cm.dispose();
			    cm.openNpc(2000,506);
			} else {
				cm.sendOk("请把法师用勋章放入背包，来切换技能。");
				cm.dispose();
				return;
			}
			
			
			
			
			
			
			
			

	    	}
			if (selection == 1001) { //10
			if (cm.getBossLog('sss2') ==1 && cm.getMapId() == 104040000 && cm.haveItem(4000000,50)){
				cm.setBossLog("sss2");
				cm.setBossLog("sss3");
				cm.gainItem(4000000, -50);
				cm.gainNX(100);
				cm.sendOk("恭喜你完成第二阶段奖励，现在请继续第三阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第二阶段，现在请按开始第三阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("需要完成第一阶段\r\n需要50个#i4000000#");
				cm.dispose();
				return;
			}
			}
			if (selection == 30) { //10
			if (cm.getBossLog('sss3') ==1 && cm.getMapId() == 104000000 && cm.getBossLog('ssss1')  > 3){
				cm.setBossLog("sss3");
				cm.setBossLog("sss4");
				cm.gainNX(103);
				cm.sendOk("恭喜你完成第三阶段奖励，现在请继续第四阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第三阶段，现在请按开始第四阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("怎样了？去和江套近乎吧？或者你已经完成了。");
				cm.dispose();
				return;
			}
			}
			if (selection == 40) { //10
			if (cm.getBossLog('sss4') ==1 && cm.getMapId() == 100000001 && cm.getBossLog('ssss2')  > 1){
				cm.setBossLog("sss4");
				cm.setBossLog("sss5");
				cm.gainNX(104);
				cm.sendOk("恭喜你完成第四阶段奖励，现在请继续第五阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第四阶段，现在请按开始第五阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("去过赌场了吗?之后再去玛亚的家！恩，这阶段你完成了？");
				cm.dispose();
				return;
			}
			}
			if (selection == 50) { //10
			if (cm.getBossLog('sss5') ==1 && cm.getMapId() == 100010100 || cm.getMapId() == 100020000 && cm.getBossLog('ssss3')  > 0){
				cm.setBossLog("sss5");
				cm.setBossLog("sss6");
				cm.gainNX(105);
				cm.sendOk("恭喜你完成第五阶段奖励，现在请继续第六阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第五阶段，现在请按开始第六阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("酷男孩是不是很酷？你根据他的提示来了吗");
				cm.dispose();
				return;
			}
			}
			if (selection == 60) { //10
			if (cm.getBossLog('sss6') ==1 && cm.getMapId() == 102000002  && cm.haveItem(2010000,1)){
				cm.setBossLog("sss6");
				cm.setBossLog("sss7");
				cm.gainItem(2010000, -200);
				cm.gainNX(106);
				cm.sendOk("恭喜你完成第六阶段奖励，现在请继续第七阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第六阶段，现在请按开始第七阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("恩，多吃苹果很好。买了就吃，别犹豫，哈哈哈哈");
				cm.dispose();
				return;
			}
			}
			if (selection == 7) { //10
			if (cm.getBossLog('sss7') ==1 && cm.getMapId() == 102000004 ){
				cm.setBossLog("sss7");
				cm.setBossLog("sss8");
				cm.gainNX(106);
				cm.sendOk("恭喜你完成第七阶段奖励，现在请继续第八阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第七阶段，现在请按开始第八阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("战士的殿堂，神圣的殿堂。");
				cm.dispose();
				return;
			}
			}
			if (selection == 8) { //10
			if (cm.getBossLog('sss8') ==1 && cm.getMapId() == 103000002 ){
				cm.setBossLog("sss8");
				cm.setBossLog("sss9");
				cm.gainNX(108);
				cm.sendOk("恭喜你完成第八阶段奖励，现在请继续第九阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第八阶段，现在请按开始第九阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("酸爽~~。");
				cm.dispose();
				return;
			}
			}
			if (selection == 9) { //10
			if (cm.getBossLog('sss9') ==1 && cm.getMapId() == 101000200 ){
				cm.setBossLog("sss9");
				cm.setBossLog("sss10");
				cm.setBossLog("zymxd");
				cm.setBossLog("zymxd");
				cm.setBossLog("zymxd");
				cm.setBossLog("zymxd");
				cm.setBossLog("zymxd");
				cm.gainNX(10000);
				cm.warp(910000000, 0);
				cm.sendOk("恭喜你完成第九阶段奖励，\r\n点劵 x #r1000 #k~");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第九阶段，现在他开始回自由市场领取奖励了 。");
				cm.dispose();
			} else {
				cm.sendOk("生命在于奇迹。");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 100) { //兑换点卷
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
