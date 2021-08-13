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
			if (cm.getHour() == 0 || cm.getHour() == 1 || cm.getHour() == 2 || cm.getHour() == 3 || cm.getHour() == 4 || cm.getHour() == 5 || cm.getHour() == 6 || cm.getHour() == 7 || cm.getHour() == 8 || cm.getHour() == 9

|| cm.getHour() == 10 || cm.getHour() == 11 || cm.getHour() == 12 || cm.getHour() == 13	|| cm.getHour() == 14 || cm.getHour() == 15 || cm.getHour() == 16

 || cm.getHour() == 17 || cm.getHour() == 18 || cm.getHour() == 19 || cm.getHour() == 20 || cm.getHour() == 21 || cm.getHour() == 23 || cm.getHour() == 24 		){//hour=时//month=月//
	cm.sendOk("游山玩水活动时间为；#r22 ：00 - 22 ：59");
     cm.dispose();
	 }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 正方箭头 + "第 一 阶 段【射手村】\r\n";
		    var a2 = "#L2#" + 正方箭头 + "第 二 阶 段【射手村训练场】\r\n";
			var a3 = "#L3#" + 正方箭头 + "第 三 阶 段【明珠港和江套近乎】\r\n";
			var a4 = "#L4#" + 正方箭头 + "第 四 阶 段【去赌博场看看，然后去玛亚的家】\r\n";
		    var a5 = "#L5#" + 正方箭头 + "第 五 阶 段【酷男孩的提示】\r\n";
			var a6 = "#L6#" + 正方箭头 + "第 六 阶 段【听说勇士部落商店的苹果不错】\r\n";
			var a7 = "#L7#" + 正方箭头 + "第 七 阶 段【前往战士的殿堂】\r\n";
		    var a8 = "#L8#" + 正方箭头 + "第 八 阶 段【废弃药店的柠檬不错】\r\n";
			var a9 = "#L9#" + 正方箭头 + "第 九 阶 段【魔法密林宠物专家】\r\n";
			var a10 = "#L10#" + 正方箭头 + "第 十 阶 段【XXX】\r\n";
		    var a11 = "#L11#" + 正方箭头 + "第 十一 阶 段【XXX】\r\n";
			var a12= "#L12#" + 正方箭头 + "第 十二 阶 段【XXX】\r\n";


            cm.sendSimple("#d天时地利人合，你还有什么得不到的呢？\r\n活动开始了，请赶快到指定地图，根据提示获得奖励吧!!!\r\n\r\n"+a1+""+a2+""+a3+""+a4+""+a5+""+a6+""+a7+""+a8+""+a9+""+a10+""+a11+""+a12+"");

        } else if (status == 1) {
         	if (selection == 1) { 
			if (cm.getBossLog('sss1') ==0 && cm.getMapId() == 100000000){
				cm.setBossLog("sss1");
				cm.setBossLog("sss2");
				cm.gainExp(200000);
				cm.sendOk("恭喜你完成第一阶段奖励，现在请继续第二阶段");
				cm.worldMessage(6,"[游山玩水]：恭喜，玩家 "+cm.getName()+" 完成第一阶段，现在请按开始第二阶段 。");
				cm.dispose();
			} else {
				cm.sendOk("你已经完成这一个阶段了，无法重复领取哦。或者你所在的位置不对呢。");
				cm.dispose();
				return;
			}
	    	}
			if (selection == 2) { //10
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
			if (selection == 3) { //10
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
			if (selection == 4) { //10
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
			if (selection == 5) { //10
			if (cm.getBossLog('sss5') ==1 && cm.getMapId() == 100010100 || cm.getMapId() == 100020000 ){
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
			if (selection == 6) { //10
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
