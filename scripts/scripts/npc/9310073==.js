importPackage(net.sf.odinms.client);
var status = 0;
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 礼包物品 = "#v1302000#";
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

	
	    var textz = "\r\n您好，尊敬的 #b#h ##k,欢迎来到悠悠冒险岛#r礼包中心#k，\r\n悠悠冒险岛火热公测，豪华赞助礼包大放送！#l\r\n#b注：以下六种赞助礼包，只要充值对应金额即可领取\r\n祝大家游戏愉快！ 更有#rVIP等级、抽奖积分#k赠送#l\r\n\r\n您目前有积分： #e#r" + cm.getjf() + "\r\n";

        textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L0##r领取100元赞助礼包#k#v4000424#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L1##r领取300元赞助礼包#k#v4000423#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L2##r领取500元赞助礼包#k#v4031353#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L3##r领取1000元赞助礼包#k#v4031777#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L4##r领取2000元赞助礼包#k#v4031983#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L5##r领取3000元赞助礼包#k#v5680053#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

                cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0) {

            if(cm.haveItem(4000463,10)){//国庆币
				cm.dispose();
                cm.gainNX(20000);//点券
				cm.gainjf(7);//积分
				cm.gainItem(2340000,10);//祝福卷
				cm.gainItem(2049117,10);//混沌卷
				cm.gainItem(4310030,10);//运动会币
				cm.gainItem(1142000,10,10,10,10,500,500,10,10,1,1,1,1,10,10);//诚实冒险家勋章
				cm.gainItem(1112908,10,10,10,10,100,100,10,10,1,1,1,1,0,0);//极光戒指
				cm.gainItem(1012057,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明面具
				cm.gainItem(1122017,1);//精灵吊坠
				cm.gainvip(+1);
				cm.gainItem(4000487,-100);
				cm.sendOk("恭喜你，你获得了100元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]100元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();
	    


}else if (selection == 1) {

            if(cm.haveItem(4310002,100)){//国庆纪念银币
				cm.dispose();
                cm.gainNX(60000);
				cm.gainjf(30);//积分
				cm.gainItem(2340000,5);//祝福卷
				cm.gainItem(2049117,5);//混沌卷
				cm.gainItem(4310030,30);//运动会币
				cm.gainItem(1142003,30,30,30,30,1000,1000,30,30,5,5,5,5,15,15);//超人气王勋章
				cm.gainItem(1112908,20,20,20,20,200,200,20,20,10,10,1,1,0,0);//极光戒指
				cm.gainItem(1122017,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//精灵吊坠
				cm.gainItem(1102039,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明披风
				cm.gainvip(+3);
				cm.gainItem(4310002,-100);
				cm.sendOk("恭喜你，你获得了300元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]300元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();
	    

}else if (selection == 2) {

            if(cm.haveItem(4310020,100)){//怪物公园纪念币
				cm.dispose();
                cm.gainNX(120000);
				cm.gainjf(90);//积分
				cm.gainItem(2340000,100);//祝福卷
				cm.gainItem(2049117,100);//混沌卷
				cm.gainItem(2049116,20);//强化混沌卷
				cm.gainItem(4310030,500);//运动会币
				cm.gainItem(1142005,60,60,60,60,1500,1500,30,30,60,60,10,10,20,20);//传说冒险家勋章
				cm.gainItem(1112908,30,30,30,30,300,300,30,30,20,20,1,1,0,0);//极光戒指
				cm.gainItem(1122017,30,30,30,30,30,30,30,30,1,1,1,1,0,0);//精灵吊坠
				cm.gainItem(1032024,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明耳环
				cm.gainvip(+5);
				cm.gainItem(4310020,-100);
				cm.sendOk("恭喜你，你获得了500元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]500元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();

}else if (selection == 3){

            if(cm.haveItem(4310021,100)){//海豚岛纪念币
				cm.dispose();
                cm.gainNX(250000);
				cm.gainjf(9999);//积分
				cm.gainItem(2340000,200);//祝福卷
				cm.gainItem(2049117,200);//混沌卷
				cm.gainItem(2049116,50);//强化混沌卷
				cm.gainItem(4310030,1000);//运动会币
				cm.gainItem(1142006,100,100,100,100,2000,2000,100,100,30,30,30,30,30,20);//冒险岛偶像明星勋章
				cm.gainItem(1112908,50,50,50,50,500,500,50,50,30,30,1,1,0,0);//极光戒指
				cm.gainItem(1122017,50,50,50,50,50,50,50,50,1,1,1,1,0,0);//精灵吊坠
				cm.gainItem(1002186,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明帽
				cm.gainvip(+10);
				cm.gainItem(4310021,-100);
				cm.sendOk("恭喜你，你获得了1000元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]1000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();

}else if (selection == 4){

            if(cm.haveItem(4310022,100)){//樱花节纪念币
				cm.dispose();
                cm.gainNX(600000);
				cm.gainjf(9999);//积分
				cm.gainItem(2340000,300);//祝福卷
				cm.gainItem(2049117,300);//混沌卷
				cm.gainItem(2049116,100);//强化混沌卷
				cm.gainItem(4310030,2000);//运动会币
				cm.gainItem(1142384,200,200,200,200,2500,2500,150,150,100,100,50,50,40,20);//冒险巨星勋章
				cm.gainItem(1112908,80,80,80,80,800,800,80,80,80,80,1,1,0,0);//极光戒指
				cm.gainItem(1122017,80,80,80,80,80,80,80,80,1,1,1,1,0,0);//精灵吊坠
				cm.gainItem(1012289,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明脸饰
				cm.gainvip(+20);
				cm.gainItem(4310022,-100);
				cm.sendOk("恭喜你，你获得了2000元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]2000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();

}else if (selection == 5){

            if(cm.haveItem(4310027,100)){//传说币
				cm.dispose();
                cm.gainNX(1000000);
				cm.gainjf(9999);//积分
				cm.gainItem(2340000,500);//祝福卷
				cm.gainItem(2049117,500);//混沌卷
				cm.gainItem(2049116,200);//强化混沌卷
				cm.gainItem(4310030,3000);//运动会币
				cm.gainItem(1142142,300,300,300,300,3000,3000,200,200,200,200,80,80,40,20);//法老守护者勋章
				cm.gainItem(1112908,100,100,100,100,1000,1000,100,100,100,100,1,1,0,0);//极光戒指
				cm.gainItem(1122017,100,100,100,100,100,100,100,100,10,10,10,10,0,0);//精灵吊坠
				cm.gainItem(1022079,10,10,10,10,10,10,10,10,1,1,1,1,0,0);//透明眼镜
				cm.gainvip(+30);
				cm.gainItem(4310027,-100);
				cm.sendOk("恭喜你，你获得了3000元大礼包! .");
			        cm.喇叭(3,"【礼包系统】["+cm.getName()+"]3000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，该档礼包请联系群主~.");
                cm.dispose();

}else if (selection == 6){
	if (cm.haveItem(4001085,1) && cm.getBossLog('PlayQuest7') < 1) {
		cm.gainItem(4001085,-1);
		cm.setBossLog('PlayQuest7');
		cm.gainItem(4001126,2500);
		cm.sendOk("任务完成,获得以下奖励:\r\n#2500张枫叶!");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 7){
	if (cm.haveItem(4001083,1) && cm.getBossLog('PlayQuest8') < 1) {
		cm.gainItem(4001083,-1);
		cm.setBossLog('PlayQuest8');
		cm.gainItem(4001126,5000);
		cm.sendOk("任务完成,获得以下奖励:\r\n5000张枫叶");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 8){
	if (cm.haveItem(4001085,1) && cm.getBossLog('PlayQuest9') < 1) {
		cm.gainItem(4001085,-1);
		cm.setBossLog('PlayQuest9');
		cm.gainNX(1000);
		cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 9){
	if (cm.haveItem(4001084,1) && cm.getBossLog('PlayQuest10') < 1) {
		cm.gainItem(4001084,-1);
		cm.setBossLog('PlayQuest10');
		cm.gainNX(1000);
		cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
		cm.dispose();
	} else 
		cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
		cm.dispose();

}else if (selection == 10){
	if (cm.haveItem(4001083,1) && cm.getBossLog('PlayQuest11') < 1) {
		cm.gainItem(4001083,-1);
		cm.setBossLog('PlayQuest11');
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
