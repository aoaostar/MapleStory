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

	
	    var textz = "\r\n您好，尊敬的 #b#h ##k,欢迎来到本服#r礼包中心#k，\r\n本服火热公测，豪华赞助礼包大放送！#l\r\n#b注：以下七种赞助礼包，只要赞助对应金额即可领取，可兼领礼包不消耗积分，每个角色每个礼包只能领取一次！\r\n祝大家游戏愉快！ \r\n\r\n";

        textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L0##r领取100元赞助礼包(需要100赞助积分--不消耗)#k#v4000424#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L1##r领取300元赞助礼包(需要300赞助积分--不消耗)#k#v4000423#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L2##r领取500元赞助礼包(需要500赞助积分--不消耗)#k#v4031353#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L3##r领取1000元赞助礼包(需要1000赞助积分--不消耗)#k#v4031777#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L4##r领取3000元赞助礼包(需要3000赞助积分--不消耗)#k#v4031983#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

		textz += "#d#L5##r领取5000元赞助礼包(需要5000赞助积分--不消耗)#k#v4031427#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L6##r领取8000元赞助礼包(需要8000赞助积分--不消耗)#k#v5680053#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L7##r领取12000元赞助礼包(需要12000赞助积分--不消耗)#k#v5680053#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
		
		textz += "#d#L8##r领取20000元赞助礼包(需要20000赞助积分--不消耗)#k#v5680053#\r\n";
		
		textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"

                cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0) {
   // if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
            //    cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
         //   } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
              //   //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
         //       cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
         //       cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();

         // }else 
           if(cm.getPlayer().getjf() < 100){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000000)== 0){		
				cm.gainMeso(10000000);//金币
				cm.gainItem(1142145,10,10,10,10,100,100,10,10,0,0,0,0,0,0);//勋章
				cm.gainItem(5211047,1);//双倍
				cm.gainItem(2002031,10);//必成币
				cm.gainItem(1112446,1);//公婆
				//cm.gainvip(+2);//破攻等级
				cm.completeQuest(1000000);
				cm.sendOk("恭喜你，你获得了100元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]100元赞助礼包领取成功！");
				cm.dispose();
           }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~.");
                cm.dispose();
	    


}else if (selection == 1) {
//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
             //   cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
           // } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
              //  cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
              //  cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
              //  cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();

         // }else 

			  if(cm.getPlayer().getjf() < 300){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000001)== 0){	
				cm.gainMeso(30000000);//金币
				cm.gainItem(1142173,20,20,20,20,200,200,20,20,0,0,0,0,0,0);//勋章
				cm.gainItem(4000313,1000);//黄金枫叶
				cm.gainItem(2340000,20);//祝福
				cm.gainItem(2460005,1);//放大镜
				cm.gainItem(2614015,1);//破攻石				
				cm.teachSkill(4111006,20);
                cm.getPlayer().changeKeybinding(30,1,4111006);
				cm.completeQuest(1000001);
				//cm.gainItem(4000423,-1);
				cm.sendOk("恭喜你，你获得了300元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]300元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();
	    

}else if (selection == 2) {
//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
               // cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
          //  } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();

         // }else 
			  if(cm.getPlayer().getjf() < 500){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000002)== 0){	
              //  cm.gainNX(50000);//点券
				cm.gainMeso(50000000);//金币
				//cm.gainItem(4001126,2000);//枫叶
				cm.gainItem(1142006,30,30,30,30,300,300,30,30,0,0,0,0,0,0);//勋章
				cm.gainItem(1112593,35,35,35,35,0,0,35,35,0,0,0,0,0,0);//戒指
				cm.gainItem(2460005,3);//放大镜
				cm.gainItem(2614015,3);//破攻石
				cm.gainItem(5520000,3);//宿命剪刀
				cm.gainItem(5570000,3);//金锤子
				//cm.gainvip(+10);//破攻等级
				cm.completeQuest(1000002);
				//cm.gainItem(4031353,-1);
				cm.sendOk("恭喜你，你获得了500元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]500元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();

}else if (selection == 3){
//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
             //   cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
         //   } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            ///    cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
                //cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
//cm.dispose();

         // }else 
			  if(cm.getPlayer().getjf() < 1000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000003)== 0){	
               // cm.gainNX(100000);//点券
				cm.gainMeso(100000000)//金币
				//cm.gainItem(4001126,3000);//枫叶
				cm.gainItem(1142068,40,40,40,40,400,400,40,40,0,0,0,0,0,0);//勋章
				cm.gainItem(1112593,35,35,35,35,0,0,35,35,0,0,0,0,0,0);//戒指
				cm.gainItem(2460005,5);//放大镜
				cm.gainItem(2614015,5);//破攻石
				cm.gainItem(5211060,1);//三倍经验
				//cm.gainvip(+16);//破攻等级
				cm.completeQuest(1000003);
			//	cm.gainItem(4031777,-1);
				cm.sendOk("恭喜你，你获得了1000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]1000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();

}else if (selection == 4){
  //if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
               // cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
           // } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
           //     cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
           //     cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
				// }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
              //  cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();

         // }else
			  if(cm.getPlayer().getjf() < 3000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000004)== 0){	
                cm.gainMeso(300000000)//金币
				//cm.gainItem(4001126,5000);//枫叶
                cm.gainItem(1142077,50,50,50,50,500,500,50,50,0,0,0,0,0,0);//勋章
				cm.gainItem(1112593,35,35,35,35,0,0,35,35,0,0,0,0,0,0);//戒指
				cm.gainItem(1092048,0,0,5,0,100,100,60,60,0,0,0,0,0,0);//冰刀盾
				cm.gainItem(2460005,8);//放大镜
				cm.gainItem(2614015,8);//破攻石
				cm.gainItem(2010006,100);//
				cm.gainItem(2002031,50);//
				//cm.gainvip(+20);//破攻等级
				cm.completeQuest(1000004);
				//cm.gainItem(4031983,-1);
				cm.sendOk("恭喜你，你获得了3000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]3000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();

				}else if (selection == 5){

				
		//	if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
               // cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();
          //  } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
           //     cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
           //     cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
              //  cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
          //      cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
              //  cm.dispose();

        //  }else 
			  if(cm.getPlayer().getjf() < 5000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();					
			}else if (cm.getQuestStatus(1000005)== 0){	
			cm.completeQuest(1000005);
			//cm.gainItem(4001126,10000);//枫叶
                cm.gainMeso(500000000)//金币
				cm.gainItem(1142189,60,60,60,60,600,600,60,60,0,0,0,0,0,0);//勋章
				cm.gainItem(2460005,12);//放大镜
				cm.gainItem(2614015,12);//破攻石
				cm.gainItem(2010006,200);//
				cm.gainItem(2002031,80);//
				cm.gainItem(1132215,100,100,100,100,0,0,100,100,0,0,0,0,0,0);//腰带
				cm.gainItem(1112593,35,35,35,35,0,0,35,35,0,0,0,0,0,0);//戒指
				//cm.gainvip(+60);//破攻等级
				cm.sendOk("恭喜你，你获得了5000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]5000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();
				
}else if (selection == 6){
	
	//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
              //  cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
          //  } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();

         // }else 
			  if(cm.getPlayer().getjf() < 8000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000006)== 0){	
			cm.completeQuest(1000006);
                cm.gainMeso(800000000)//金币
				//cm.gainItem(4001126,20000);//枫叶
				cm.gainItem(1142031,70,70,70,70,700,700,70,70,0,0,0,0,0,0);//勋章
				cm.gainItem(2460005,18);//放大镜
				cm.gainItem(2614015,18);//破攻石
				cm.gainItem(2010006,400);//
				cm.gainItem(2002031,120);//
				cm.gainItem(1142031,100,100,100,100,0,0,100,100,0,0,0,0,0,0);//维泽特帽
				cm.sendOk("恭喜你，你获得了8000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]8000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();



}else if (selection == 7){
	
	//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
              //  cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
          //  } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();

         // }else 
			  if(cm.getPlayer().getjf() < 12000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000007)== 0){	
			cm.completeQuest(1000007);
                cm.gainMeso(1200000000)//金币
				//cm.gainItem(4001126,20000);//枫叶
				cm.gainItem(1142186,80,80,80,80,800,800,80,80,0,0,0,0,0,0);//勋章
				cm.gainItem(2460005,30);//放大镜
				cm.gainItem(2614015,30);//破攻石
				cm.gainItem(2010006,700);//
				cm.gainItem(2002031,150);//
				//cm.gainItem(1142031,100,100,100,100,0,0,100,100,0,0,0,0,0,0);//维泽特帽
				cm.sendOk("恭喜你，你获得了12000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]12000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
                cm.dispose();


}else if (selection == 8){
	
	//if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
              //  cm.sendOk("装备栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
               // cm.dispose();
          //  } else if (!cm.beibao(2, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("消耗栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(3, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("设置栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(4, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
            //    cm.sendOk("其他栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
            //    cm.dispose();
                 //以此类推。。最大cm.beibao(5,1)也就是判断到现金栏
			//	 }else if (!cm.beibao(5, 3)) {//前面的2对应装备第二栏，也就是消耗 后面就是格数
             //   cm.sendOk("特殊栏空余不足3个空格。请清理背包以免影响获得物品！");//判断不等于，就提示对话
             //   cm.dispose();

         // }else 
			  if(cm.getPlayer().getjf() < 20000){//蓝色礼物盒
			cm.sendOk("抱歉，您尚未赞助，或者赞助积分不足~.");
                cm.dispose();
			}else if (cm.getQuestStatus(1000008)== 0){	
			cm.completeQuest(1000008);
                cm.gainMeso(1900000000)//金币
				//cm.gainItem(4001126,20000);//枫叶
				cm.gainItem(1142788,150,150,150,150,150,150,150,150,0,0,0,0,0,0);//勋章
				cm.gainItem(2460005,30);//放大镜
				cm.gainItem(2614015,30);//破攻石
				cm.gainItem(2010006,700);//
				cm.gainItem(2002031,150);//
				//cm.gainItem(1142031,100,100,100,100,0,0,100,100,0,0,0,0,0,0);//维泽特帽
				cm.sendOk("恭喜你，你获得了20000元大礼包! .");
			        cm.喇叭(3,"["+cm.getName()+"]20000元赞助礼包领取成功！");
				cm.dispose();
            }else
                cm.sendOk("抱歉，您尚未赞助，或者已经领取了该档礼包~");
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
