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

	
	    var textz = "\r\n您好，尊敬的 #b#h ##k,欢迎来到回忆冒险岛#r礼包中心#k，\r\n回忆冒险岛火热公测，豪华充值礼包大放送！#l\r\n#b注：以下六种充值礼包，只要充值对应金额即可领取，可兼领礼包不消耗积分，每个角色每个礼包只能领取一次！\r\n祝大家游戏愉快！ \r\n\r\n";

        textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L0##r领取100元充值礼包(需要100消费积分)#k#v4000424#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L1##r领取300元充值礼包(需要300消费积分)#k#v4000423#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L2##r领取500元充值礼包(需要500消费积分)#k#v4031353#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L3##r领取800元充值礼包(需要800消费积分)#k#v4031777#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L4##r领取1000元充值礼包(需要1000消费积分)#k#v4031983#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L5##r领取2000元充值礼包(需要2000消费积分)#k#v4031427#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L6##r领取3000元充值礼包(需要3000消费积分)#k#v5680053#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

                cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0) {

             if(cm.getPlayer().getjf() < 100){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000000)== 0){
              //  cm.gainNX(10000);//点券
				cm.gainMeso(200000000);//金币
				//cm.gainItem(4001126,1000);//枫叶
				//cm.gainItem(4310030,10);//抽奖币
				//cm.gainItem(4000487,10);//暗影币
				cm.gainItem(1142296,10,10,10,10,100,100,10,10,10,10,10,10,10,10);//领悟混沌的人
				//cm.gainItem(1082149,0,0,0,0,0,0,21,21,0,0,0,0,0,0);//工地手套
				cm.gainDY(20000);//抵用卷
				cm.gainItem(4251200,2,1);//五彩水晶
				cm.gainItem(5390006,10,1);//喇叭
				cm.completeQuest(1000000);
				//cm.gainvip(+1);//破攻等级
				//cm.gainItem(4000424,-1);
				cm.sendOk("恭喜你，你获得了100元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]100元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者已经领取了该档礼包~.");
                cm.dispose();
				
			
	    


}else if (selection == 1) {

             if(cm.getPlayer().getjf() < 300){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000001)== 0){
				cm.gainMeso(500000000);//金币

				cm.gainItem(1142559,20,20,20,20,200,200,20,20,20,20,20,20,10,10);//荣誉夏季
				//cm.gainItem(1122017,10,10,10,10,0,0,10,10,0,0,0,0,0,0);//精灵吊坠
				cm.gainItem(1112404,30,30,30,30,0,0,0,0,0,0,0,0,0,0);//极光戒指
				//cm.gainItem(4310030,20);//抽奖币
				//cm.gainItem(4000487,20);//暗影币
			
				cm.gainDY(50000);//抵用卷
				cm.gainItem(4251200,5,1);//五彩水晶
				cm.gainItem(5390006,20,1);//喇叭
				cm.completeQuest(1000001);
				//cm.gainvip(+2);//破攻等级
				//cm.gainItem(4000423,-1);
				cm.sendOk("恭喜你，你获得了300元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]300元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者已经领取了该档礼包~.");
                cm.dispose();
	    

}else if (selection == 2) {

           
             if(cm.getPlayer().getjf() < 500){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000002)== 0){
                cm.gainNX(100000);//点券
				cm.gainMeso(1000000000);//金币
				//cm.gainItem(4310030,50);//抽奖币
				//cm.gainItem(4001126,3000);//枫叶
				//cm.gainItem(4000313,600);//黄金枫叶
				//cm.gainItem(4000463,200);//国庆币
				cm.gainItem(1112915,50,50,50,50,0,0,0,0,0,0,0,0,0,0);//蓝调戒指
				cm.gainItem(1142265,30,30,30,30,200,200,30,30,30,30,30,30,10,10);//豪华玩家勋章
				//cm.gainItem(1082149,0,0,0,0,0,0,21,21,0,0,0,0,0,0);//工地手套
				//cm.gainItem(4000487,50);//暗影币
		
				cm.gainDY(100000);//抵用卷
				cm.gainItem(4251201,5,1);//五彩水晶
				cm.gainItem(5390006,50,1);//喇叭
				cm.completeQuest(1000002);
				//cm.gainvip(+3);//破攻等级
				//cm.gainItem(4031353,-1);
				cm.sendOk("恭喜你，你获得了500元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]500元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者已经领取了该档礼包~.");
                cm.dispose();

}else if (selection == 3){

            if(cm.getPlayer().getjf() < 800){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000003)== 0){
                cm.gainNX(150000);//点券
				cm.gainMeso(150000000)//金币
				cm.gainDY(150000);//抵用卷
				//cm.gainItem(4001126,5000);//枫叶
				//cm.gainItem(4000313,200);//黄金枫叶
				//cm.gainItem(4000463,500);//国庆币
				cm.gainItem(1112915,50,50,50,50,0,0,0,0,0,0,0,0,0,0);//蓝调戒指
				cm.gainItem(1142515,40,40,40,40,200,200,40,40,40,40,40,40,20,20);//引领暴风之人
				//cm.gainItem(1122017,20,20,20,20,0,0,20,20,0,0,0,0,0,0);//精灵吊坠
			   // cm.gainItem(4000487,80);//暗影币
				//cm.gainItem(2049116,8);//强混
				cm.gainItem(4251200,8);//五彩水晶
				cm.gainItem(5390006,80,1);//喇叭
			  //cm.gainvip(+5);//破攻等级
				cm.completeQuest(1000003);
				cm.sendOk("恭喜你，你获得了800元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]800元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者已经领取了该档礼包~.");
                cm.dispose();

}else if (selection == 4){

            if(cm.getPlayer().getjf() < 1000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000004)== 0){
                cm.gainMeso(2000000000)//金币
				cm.gainNX(200000);//点卷
                cm.gainItem(1112915,50,50,50,50,0,0,0,0,0,0,0,0,0,0);
				cm.gainItem(1112915,50,50,50,50,0,0,0,0,0,0,0,0,0,0);//蓝调戒指
				//cm.gainItem(4000313,400);//黄金枫叶
				cm.gainItem(1142515,40,40,40,40,1200,1200,40,40,40,40,40,40,20,20);//引领风暴之人
				cm.gainItem(1122017,10,10,10,10,0,0,10,10,0,0,0,0,0,0);//精灵吊坠永久
				//cm.gainItem(4000487,110);//暗影币
				cm.gainDY(200000);//抵用卷
				cm.gainItem(4251201,8,1);//五彩水晶
				cm.gainItem(5072000,80,1);//喇叭
				//cm.gainvip(+30);//破攻等级
				cm.completeQuest(1000004);
				//cm.gainItem(4031983,-1);
				cm.sendOk("恭喜你，你获得了1000元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]1000元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者已经领取了该档礼包~.");
                cm.dispose();

				}else if (selection == 5){

            if(cm.getPlayer().getjf() < 2000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000005)== 0){
                cm.gainMeso(50000000)//金币
				cm.gainItem(4310030,300);//抽奖币
				cm.gainItem(4000313,2000);//黄金枫叶
				cm.gainItem(1112404,100,100,100,100,0,0,0,0,0,0,0,0,0,0);//极光戒指
				cm.gainItem(1142594,150,150,150,150,2500,2500,150,150,150,150,150,150,30,30);//引领风暴之人
				cm.gainItem(1122017,50,50,50,50,0,0,50,50,0,0,0,0,0,0);//精灵吊坠永久
				cm.gainItem(4000487,200);//暗影币
				cm.gainItem(2049116,100);//强混
				cm.gainItem(2340000,100);//祝福
				cm.gainItem(5072000,200,1);//喇叭
				cm.completeQuest(1000005);
				cm.sendOk("恭喜你，你获得了2000元充值大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]2000元充值礼包领取成功！");
				cm.dispose();
           }else
                cm.sendOk("抱歉，您尚未充值，或者充值的不是该档礼包~.");
                cm.dispose();
			}else if (selection == 6){

          if(cm.getPlayer().getjf() < 3000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未充值，或者消费积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000006)== 0){
                cm.gainMeso(100000000)//金币
				cm.gainItem(4310030,2000);//抽奖币
				cm.gainItem(4000313,3000);//黄金枫叶
				cm.gainItem(1112404,200,200,200,200,0,0,0,0,0,0,0,0,0,0);//极光戒指
				cm.gainItem(1142569,400,400,400,400,7500,7500,400,400,400,400,400,400,40,40);//引领风暴之人
				cm.gainItem(1122017,400,400,400,400,0,0,400,400,0,0,0,0,0,0);//精灵吊坠永久
				cm.gainItem(4000487,400);//暗影币
				cm.gainItem(2049116,500);//强混
				cm.gainItem(2340000,500);//祝福
				cm.gainItem(5072000,400,1);//喇叭
				cm.completeQuest(1000006);
				cm.sendOk("恭喜你，你获得了3000元大礼包! .");
			        cm.喇叭(3,"【回忆礼包】["+cm.getName()+"]3000元充值礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未充值，或者充值的不是该档礼包~.");
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
