importPackage(net.sf.odinms.client);
var status = 0;

var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";


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

	    var textz = "\r\n#e欢迎来到#r冒险岛每日任务专区.每个任务都可以完成.获取相对应的奖励，建议新人每日都做#l\r\n#b注：材料不够会被系统吞掉，吞掉一律不赔，请注意#l\r\n";

		textz += "#d#L0#收集#v4000000##v4000016##v4000019#各#r50#d个可得经验20W+金币100W+抵押1000\r\n";

		textz += "#d#L1#收集#v4000173##r 120 #d个可得经验20W、点券1000\r\n";

		textz += "#d#L2#收集#v4000121##r 120 #d个可得金币200W、点券1000\r\n";

		textz += "#d#L3#收集#v4031227##r 1 #d个可得经验10W、抵押券1000、黄金枫叶30个\r\n";

		textz += "#d#L4#收集#v4001085##v4001030#各#r 1 #d个可得#r#v4251202#1个#v2022279#5个 金币3000W\r\n";

		//textz += "#d#L5#收集#v4000161##b#z4000161##r 100 #d个可兑换#r#v4032226#10只\r\n";

		//textz += "#d#L6#收集#v4000052##b#z4000052##r 100 #d个可兑换#r#v4032226#10只\r\n";

		//textz += "#d#L7#收集#v4000190##b#z4000190##r 100 #d个可兑换#r#v4032226#10只\r\n";

		//textz += "#d#L8#收集#v4001085##b#z4001085##r 1 #d个\r\n  可兑换#r1000点#d卷#l\r\n";

		//textz += "#d#L9#收集#v4001084##b#z4001084##r 1 #d个\r\n  可兑换#r1000点#d卷#l\r\n";

		//textz += "#d#L10#收集#v4001083##b#z4001083##r 1 #d个\r\n  可兑换#r2000点#d卷#l\r\n";
                cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0) {
if (cm.haveItem(4000000,50) && cm.haveItem(4000016,50) && cm.haveItem(4000019,50) && cm.getBossLog('PlayQuest110') < 1) {
			
		cm.setBossLog('PlayQuest110');      
        cm.gainExp(+200000);
		cm.gainMeso(+1000000);
        cm.gainDY(+1000);
		cm.gainItem(4000000,-50);
		cm.gainItem(4000016,-50);
		cm.gainItem(4000019,-50);
		cm.sendOk("恭喜，任务完成！");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();
	    


}else if (selection == 1) {
	if (cm.haveItem(4000173,120) && cm.getBossLog('PlayQuest111') < 1) {
			
		cm.setBossLog('PlayQuest111');      
        cm.gainExp(+200000);
	//	cm.gainMeso(+1000000);
        cm.gainNX(+1000);
		//cm.gainItem(4000000,-50);
		//cm.gainItem(4000016,-50);
		cm.gainItem(4000173,-120);
		cm.sendOk("恭喜，任务完成！");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();
	    

}else if (selection == 2) {
	if (cm.haveItem(4000121,120) && cm.getBossLog('PlayQuest112') < 1) {
			
		cm.setBossLog('PlayQuest112');      
        cm.gainMeso(+2000000);
	//	cm.gainMeso(+1000000);
        cm.gainNX(+1000);
		//cm.gainItem(4000000,-50);
		//cm.gainItem(4000016,-50);
		cm.gainItem(4000121,-120);
		cm.sendOk("恭喜，任务完成！");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 3){
	if (cm.haveItem(4031227,1) && cm.getBossLog('PlayQuest113') < 1) {
			
		cm.setBossLog('PlayQuest113');      
        cm.gainExp(+100000);
	//	cm.gainMeso(+1000000);
        cm.gainDY(+1000);
		cm.gainItem(4000313,30);
		//cm.gainItem(4000016,-50);
		cm.gainItem(4031227,-1);
		cm.sendOk("恭喜，任务完成！");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 4){
	if (cm.haveItem(4001085,1) && cm.haveItem(4001030,1) && cm.getBossLog('PlayQuest114') < 1) {
			
		cm.setBossLog('PlayQuest114');      
       // cm.gainExp(+200000);
		//cm.gainMeso(+1000000);
        //cm.gainDY(+1000);
		cm.gainItem(4001085,-1);
		cm.gainItem(4001030,-1);
		cm.gainItem(4251202,1);
		cm.gainItem(2022279,5);
		cm.gainMeso(30000000);
		//cm.gainItem(4000019,-50);
		cm.sendOk("恭喜，任务完成！");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 5){
	if (cm.haveItem(4000161,100) && cm.getBossLog('PlayQuest85') < 1) {
		cm.gainItem(4000161,-100);
		cm.setBossLog('PlayQuest85');
		cm.gainItem(4032226,10);
		cm.gainExp( + 100000);
		cm.sendOk("任务完成,获得群主江浩额外赠送的\r\n10万经验!");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 6){
	if (cm.haveItem(4000052,100) && cm.getBossLog('PlayQuest86') < 1) {
		cm.gainItem(4000052,-100);
		cm.setBossLog('PlayQuest86');
		cm.gainItem(4032226,10);
		cm.gainExp( + 100000);
		cm.sendOk("任务完成,获得群主江浩额外赠送的\r\n10万经验!");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 7){
	if (cm.haveItem(4000190,100) && cm.getBossLog('PlayQuest87') < 1) {
		cm.gainItem(4000190,-100);
		cm.setBossLog('PlayQuest87');
		cm.gainItem(4032226,10);
		cm.gainExp( + 100000);
		cm.sendOk("任务完成,获得群主江浩额外赠送的\r\n10万经验");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 8){
	if (cm.haveItem(4001085,1) && cm.getBossLog('PlayQuest88') < 1) {
		cm.gainItem(4001085,-1);
		cm.setBossLog('PlayQuest88');
		cm.gainNX(1000);
		cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 9){
	if (cm.haveItem(4001084,1) && cm.getBossLog('PlayQuest89') < 1) {
		cm.gainItem(4001084,-1);
		cm.setBossLog('PlayQuest89');
		cm.gainNX(1000);
		cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 10){
	if (cm.haveItem(4001083,1) && cm.getBossLog('PlayQuest90') < 1) {
		cm.gainItem(4001083,-1);
		cm.setBossLog('PlayQuest90');
		cm.gainNX(2000);
		cm.sendOk("任务完成,获得以下奖励:\r\n2000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();



}else if (selection == 11){
	if (cm.haveItem(4001126,1000) && cm.getBossLog('PlayQuest14') < 1) {
		cm.gainItem(4001126,-1000);
		cm.setBossLog('PlayQuest14');
		cm.gainNX(1000);
		cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();


}
}
}
}
