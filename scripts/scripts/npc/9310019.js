var 星星 = "#fEffect/CharacterEff/1112903/0/0#";
var 爱心 = "#fEffect/CharacterEff/1032063/0/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
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
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            

			
            text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
			
			text += "#L1##b#e" + 红色箭头 + "月妙组队副本(10-255) 额外奖励：#v2022468#X2#l\r\n"//3

            text += "#L2##b#e" + 蓝色箭头 + "废弃组队副本(20级-255级) 额外奖励：#v2460005#X2#l\r\n"
            text += "#L3##b#e" + 蓝色箭头 + "玩具组队副本(35级-255级) 额外奖励：#v4310149#X2#l\r\n"

						
            text += "#L4##b" + 红色箭头 + "天空组队副本(35-255) 额外奖励：#v4000464#X2#l\r\n"
       //     text += "#L8##b" + 蓝色箭头 + "男女组队副本(71-255) \r\n"

          text += "#L5##b#e" + 红色箭头 + "毒雾组队副本(45级-255级) 额外奖励：#v2614015#X2#l\r\n"
          text += "#L6##b#e" + 蓝色箭头 + "海盗组队副本(80级-255级) 额外奖励：#v2340000#X2#l\r\n\r\n\r\n"
		  

          //  text += "#L1##b" + 红色箭头 + "月妙组队副本(10-200)#l #L9##b" + 红色箭头 + "英语学院副本\r\n"//3
             
        
           
           // text += "#L10##b"+xxx+"怪物嘉年华(组队对抗副本.最低2V2)等级(30-100)#l\r\n"//3
			//   text += "#L8#"+ttt+""+xxx+ "遗址公会对抗战(家族副本)#l\r\n"//3
           // text += "#b#L27#"+红色箭头+ "二十七宫#l      #L38##b" +红色箭头+"武陵道场\r\n"//3  
         //  text += "#L28##b" + 红色箭头 + "演练副本#l\r\n\r\n"//3
         
            text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"  
	  //  text += " #L11#"+ttt+""+xxx+"远征闹钟(100级)#l#b#L12#"+ttt+""+xxx+"远征扎昆(120级)#l#b\r\n";
            
        //    text += " #L23#"+ttt+""+xxx+"大王蜈蚣(70级)#l#b#L22#"+ttt+""+xxx+"巨大蝙蝠(90)#l#b\r\n";
	   // text += " #L15#"+ttt+""+xxx+"绯红副本(120级)#l#b#L16#"+ttt+""+xxx+"鱼王(120级)#l#b\r\n";

            //text += " #L13#"+ttt+""+xxx+"千年树精(130级)#l#b#L14#"+ttt+""+xxx+"远征妖僧(140级)#l#b\r\n\r\n";
			
			//text += " #L109#"+ttt+""+xxx+"狮子王接见室入口(130级)#l#b\r\n\r\n";
			//text += "#L109##dLv150.挑战品克缤#l\r\n\r\n"//3，//Array(211061001,0,"狮子王接见室入口（建议1线）")

            //text += "#L11##dLv120.千年树精王遗迹Ⅱ#l\r\n\r\n"//3
            //text += "#L12##dLv130.人偶师BOSS挑战#l\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L13##rLv120级以上.绯红副本挑战#l\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L14##rLv140级以上.御姐副本挑战#l\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L60##rLv160级以上.挑战高级boss#l\r\n\r\n"//3
           // text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
            cm.sendSimple(text);
			} else if (selection == 30) {//PK 1V1
			var party = cm.getPlayer().getParty();	
			if (cm.getPlayer().getNX() < 20000) {
	            cm.sendOk("需要2W点卷才能入场.");
                cm.dispose();

			}else{
				cm.warpParty(910000021);
				cm.gainNX(-20000);
				cm.喇叭(2, "[PK传送]：玩家" + cm.getPlayer().getName() + "进入了单人PVP地图~");
                cm.dispose();
                return;
	      }
		  
		  } else if (selection == 40) {//PK 组队
	
			 if(!cm.haveItem(2002033,10)){
	            cm.sendOk("需要10个巧克力蛋糕才能入场.");
                cm.dispose();

			}else{
				cm.gainItem(2002033,-10);
				cm.warpParty(910000022);
				cm.喇叭(2, "[PK传送]：玩家" + cm.getPlayer().getName() + "进入了组队PVP地图~");
                cm.dispose();
                return;
	      }
			
			
			
        } else if (selection == 1) { //月妙组队副本 //100000020
            cm.warpParty(100000200);
		  // cm.openNpc(1012112, 0);
			//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 2) {  //废弃组队副本
            cm.warpParty(103000000);
			//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 3) { //玩具组队副本
            cm.warpParty(221024500);
			//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 4) {//天空组队副本
            cm.warpParty(200080101);
			//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 5) {//毒物组队副本
	    cm.warpParty(300030100);
		//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 6) {//海盗组队副本
            cm.warpParty(251010404);
			//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        
        } else if (selection == 8) {//罗密欧与朱丽叶组队副本
	    cm.warpParty(261000011);
		//cm.gainDY(-500);
			//cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 9) {//英语学院副本
            cm.openNpc(9900004, 17);
         } else if (selection == 21) {//怪物公园
            cm.warpParty(951000000);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
        } else if (selection == 28) {//演练副本
            cm.warpParty(130020000);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
       } else if (selection == 29) {//金字塔副本
            cm.warpParty(926010000);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
       } else if (selection == 23) {//大王蜈蚣
            cm.warpParty(701010320);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
            
        } else if (selection == 22) {//巨大蝙蝠
            cm.warpParty(105100100);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();

        } else if (selection == 11) {//闹钟
            cm.warpParty(220080000);
            cm.dispose();
            //cm.openNpc(9310057, 0);
        } else if (selection == 12) {//扎
            cm.warpParty(211042400);
			cm.gainDY(-500);
			cm.sendOk("扣除传送费用500抵用券");
            cm.dispose();
            //cm.openNpc(9310057, 0);
        } else if (selection == 13) {//大树//Array(270050000,0,"神的黄昏:品克缤场所"),
            if (cm.getLevel() < 130 && cm.party.size() < 2) {  
            cm.sendOk("本地图限制等级130级。您的能力没有资格挑战副本");
                cm.dispose();
              }else{
			cm.warpParty(541020800);  
				cm.dispose();
                return;
	      } 
        } else if (selection == 109) {//大树//Array(270050000,0,"神的黄昏:品克缤场所"),
            if (cm.getLevel() < 150 && cm.party.size() < 2) {  
            cm.sendOk("没有资格挑战副本");
                cm.dispose();
              }else{
			cm.warpParty(211061001);//"狮子王接见室入口),//cm.warpParty(270050100);//"挑战品克缤"),  211061001
				cm.dispose();
                return;
	      } 
        } else if (selection == 14) {//妖僧
            if (cm.getLevel() < 140 ) {  
            cm.sendOk("本地图限制等级140级。您的能力没有资格挑战副本");
                cm.dispose();
              }else{
			cm.warpParty(702070400);  
                cm.dispose();
                return;
	      }
        } else if (selection == 15) {//绯红
            if (cm.getLevel() < 120 ) {  
            cm.sendOk("本地图限制等级120级。您的能力没有资格挑战副本");
                cm.dispose();
              }else{
			cm.warpParty(803001200);  
                cm.dispose();
                return;
	      } 
        } else if (selection == 16) {//鱼王
            if (cm.getLevel() < 120 ) {  
            cm.sendOk("本地图限制等级120级。您的能力没有资格挑战副本");
                cm.dispose();
              }else{
			cm.warpParty(230040420);  
                cm.dispose();
                return;
	      }
        } else if (selection == 10) {//.怪物嘉年华
            cm.warpParty(980000000);
            cm.dispose();
            //cm.openNpc(9310057, 0);
          } else if (selection == 60) {//.怪物嘉年华
            cm.warpParty(970030001);
            cm.dispose();
            //cm.openNpc(9310057, 0);
        } else if (selection == 15) {//.阿里安特
            cm.openNpc(2101018, 0); 
        } else if (selection == 27) {//.二十七宫
            cm.warpParty(970030000);
            cm.showInstruction("#r[二十七宫材料说明]#k\r\n\r\n", 240, 60);
            cm.dispose();
       } else if (selection == 38) {//.武陵道场
            cm.warpParty(925020000);
            cm.showInstruction("#r[武陵道场材料说明]#k获取腰带\r\n\r\n", 240, 60);
            cm.dispose();
           
        } else if (selection == 31) {//.废弃扫荡
           if (cm.haveItem(4031890) > 0 && cm.getPlayer().getLevel() >= 21){
           cm.gainItem(4031456,2);
           cm.gainItem(4002000,2);
		   cm.gainItem(4002001,1);//绿蜗牛邮票
           cm.gainExp(50000);
		   cm.gainMeso(200000);
           cm.gainItem(4031890,-1);
		   cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用扫荡卡通过废弃副本！");
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，或等级不足21级，不能扫荡副本");
              cm.dispose();
             }
        }
      else if (selection == 32) {//.玩具扫荡
           if (cm.haveItem(4031890) > 0 && cm.getPlayer().getLevel() >= 35){
           cm.gainItem(4001126,50);
           cm.gainItem(4002000,3);
		   cm.gainItem(4002001,1);//绿蜗牛邮票
             cm.gainExp(200000);
			 cm.gainMeso(500000);
		   cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用扫荡卡通过玩具副本！");
           cm.gainItem(4031890,-2);
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，或等级不足35，不能扫荡副本");
              cm.dispose();
             }
        }
else if (selection == 33) {//.天空扫荡
           if (cm.haveItem(4031890) > 0){
           cm.gainItem(4001322,10);
           cm.gainItem(4002000,1);//绿蜗牛邮票
             cm.gainExp(50000);
           cm.gainItem(4031890,-1);
		   cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用扫荡卡通过玩具副本！");
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，不能扫荡副本");
              cm.dispose();
             }
        }
else if (selection == 34) {//.男女扫荡
           if (cm.haveItem(4031890) > 0){
           cm.gainItem(4001322,10);
           cm.gainItem(4002000,1);//绿蜗牛邮票
             cm.gainExp(50000);
           cm.gainItem(4031890,-1);
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，不能扫荡副本");
              cm.dispose();
             }
        }
else if (selection == 35) {//.毒物扫荡
           if (cm.haveItem(4031890) > 0 && cm.getPlayer().getLevel() >= 60){
           cm.gainItem(4031456,5);
           cm.gainItem(4002000,3);
		   cm.gainItem(4002001,2);//绿蜗牛邮票
             cm.gainExp(700000);
			 cm.gainMeso(900000);
			 cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用扫荡卡通过毒物副本！");
           cm.gainItem(4031890,-3);
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，或等级不足60.不能扫荡副本");
              cm.dispose();
             }
        }
else if (selection == 36) {//.海盗扫荡
            if (cm.haveItem(4031890) > 0 && cm.getPlayer().getLevel() >= 90){
           cm.gainItem(4031456,3);
           cm.gainItem(4002000,3);
		   cm.gainItem(4002001,2);//绿蜗牛邮票
             cm.gainExp(3000000);
			 cm.gainMeso(1000000);
			 cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用扫荡卡通过海盗副本！");
           cm.gainItem(4031890,-5);
            cm.dispose();
           }
         else{
              cm.sendOk("你没有扫荡卡，不能扫荡副本");
              cm.dispose();
             }
        }
    }
}


