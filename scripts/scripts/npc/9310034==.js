var 星星 = "#fEffect/CharacterEff/1114000/2/0#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
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
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
                        text += "\t\t\t  #e自助赚钱系统#b（建议购买双倍爆率卡） #k!#n\r\n"
            text += "       "+ 蓝色箭头 +"#L21##rLv30.大王蜈蚣副本#l\r\n\r\n"//3
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
            text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
			text += "\t\t\t  #e初级副本系统#b（建议购买双倍爆率卡） #k!#n\r\n"
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
            text += "#L1##e#d" + 蓝色箭头 + "红蜗牛王副本#l#L2##d " + 蓝色箭头 + "木妖王副本#l\r\n\r\n"//3
            text += "#L3##d" + 蓝色箭头 + "蘑菇王副本  #l#L4##d " + 蓝色箭头 + "寄居蟹副本#l\r\n\r\n"//3
            text += "#L5##d" + 蓝色箭头 + "僵尸菇王副本#l#L6##d " + 蓝色箭头 + "肯德熊副本#l\r\n\r\n"//3
            text += "#L7##d" + 蓝色箭头 + "妖怪禅师副本#l#L8##d " + 蓝色箭头 + "鳄鱼王副本#l\r\n\r\n"//3
            text += "#L9##d" + 蓝色箭头 + "艾利杰副本  #l#L10##d " + 蓝色箭头 + "歇尔夫副本#l\r\n\r\n"//3
            text += "#L11##d" + 蓝色箭头 + "九尾狐副本  #l#L12##d " + 蓝色箭头 + "蝙蝠怪副本#l\r\n\r\n"//3
            text += "#L13##d" + 蓝色箭头 + "格瑞分多副本#l#L14##d " + 蓝色箭头 + "喷火龙副本#l\r\n\r\n"//3
            text += "#L15##d" + 蓝色箭头 + "多多副本#l    #L16##d " + 蓝色箭头 + "玄冰独角兽副本#l\r\n\r\n"//3
			text += "#L102##d" + 蓝色箭头 + "雷卡副本#l\r\n\r\n"//3
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
                      text += "\t\t\t#e高极副本系统#b（新手勿进） #k!#n\r\n"
            text += "" + 蓝色箭头 +"#L22##rLv70.马来西亚副本#l\r\n\r\n"//3
            text += "" + 蓝色箭头 +"#L17##rLv80.鱼王皮亚奴斯洞穴#l\r\n\r\n"//3
            text += "" + 蓝色箭头 +"#L18##rLv80.时间塔的本源闹钟#l\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L19##rLv100.扎昆的祭坛挑战#l\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L20##rLv120.暗黑龙王的巢穴挑战#l\r\n\r\n"//3
            text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {//红蜗牛王
            if (cm.getLevel() < 10 ) {  
            cm.sendOk("本地图限制等级10级。您的能力没有资格挑战红蜗牛王副本");
                cm.dispose();
              }else{
			cm.warp(104000400);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战红蜗牛王副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
         } else if (selection == 2) {//木妖王
            if (cm.getLevel() < 20 ) {  
            cm.sendOk("本地图限制等级20级。您的能力没有资格挑战木妖王副本");
                cm.dispose();
              }else{
			cm.warp(101030404);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战树妖王副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
        } else if (selection == 3) {//蘑菇王
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格挑战蘑菇王副本");
                cm.dispose();
              }else{
			cm.warp(100000005);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战蘑菇王副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
        } else if (selection == 4) {//寄居蟹
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格挑战寄居蟹副本");
                cm.dispose();
              }else{
			cm.warp(110040000);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战寄居蟹副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 5) {//僵尸蘑菇王
            if (cm.getLevel() < 40 ) {  
            cm.sendOk("本地图限制等级40级。您的能力没有资格挑战僵尸蘑菇王副本");
                cm.dispose();
              }else{
			cm.warp(105070002); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战僵尸蘑菇王副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 6) {//肯德熊
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战肯德熊副本");
                cm.dispose();
              }else{
			cm.warp(250010304); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战肯德熊副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 7) {//妖怪禅师
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战妖怪禅师副本");
                cm.dispose();
              }else{
			cm.warp(250010503); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战妖怪禅师副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 8) {//鳄鱼王
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战鳄鱼王副本");
                cm.dispose();
              }else{
			cm.warp(107000300); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战鳄鱼王副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 9) {//艾利杰
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战艾利杰副本");
                cm.dispose();
              }else{
			cm.warp(200010300);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战艾利杰副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 10) {//歇尔夫
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战歇尔夫副本");
                cm.dispose();
              }else{
			cm.warp(230020100);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战歇尔夫副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
        } else if (selection == 11) {//九尾狐
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战九尾狐副本");
                cm.dispose();
              }else{
			cm.warp(222010310);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战九尾狐副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 12) {//蝙蝠怪
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战蝙蝠怪副本");
                cm.dispose();
              }else{
			cm.warp(105090900); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战蝙蝠怪副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
         } else if (selection == 13) {//格瑞分多
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战格瑞分多副本");
                cm.dispose();
              }else{
			cm.warp(240020101); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战格瑞芬多副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 14) {//喷火龙
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战喷火龙副本");
                cm.dispose();
              }else{
			cm.warp(240020402); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战喷火龙副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      }
        } else if (selection == 15) {//多多
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战多多副本");
                cm.dispose();
              }else{
			cm.warp(270010500);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战多多副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }
		} else if (selection == 102) {//雷卡
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战多多副本");
                cm.dispose();
              }else{
			cm.warp(270030500);  
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战雷卡副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }  
        } else if (selection == 16) {//玄冰独角兽
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战玄冰独角兽副本");
                cm.dispose();
              }else{
			cm.warp(270020500); //270030500雷卡// 270020500独角兽
						cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战玄冰独角兽副本，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }  
		//} else if (selection == 102 {//雷卡
           // if (cm.getLevel() < 70 ) {  
            //cm.sendOk("本地图限制等级70级。您的能力没有资格挑战雷卡副本");
              //  cm.dispose();
              //}else{
			//cm.warp(270030500);  
				//		cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战雷卡副本，大家都来战个痛快吧~");
				//cm.dispose();
                //return;
	      //}  
        } else if (selection == 17) {//鱼王
            if (cm.getLevel() < 80 ) {  
            cm.sendOk("本地图限制等级80级。您的能力没有资格挑战鱼王副本");
                cm.dispose();
              }else{
			cm.warp(230040420); 
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战鱼王副本，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      }
        } else if (selection == 18) {//闹钟
            if (cm.getLevel() < 80 ) {  
            cm.sendOk("本地图限制等级80级。您的能力没有资格闹钟副本");
                cm.dispose();
              }else{
			cm.warp(220080000);  
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战闹钟副本，大家都来战个痛快吧~");	
				cm.dispose();
                return;
	      }  
        } else if (selection == 19) {//扎昆
            if (cm.getLevel() < 100 ) {  
            cm.sendOk("本地图限制等级100级。您的能力没有资格扎昆副本");
                cm.dispose();
              }else{
			cm.warp(280030000);
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战扎昆副本，大家都来战个痛快吧~");				
				cm.dispose();
                return;
	      }
        } else if (selection == 20) {//暗黑龙王
            if (cm.getLevel() < 120 ) {  
            cm.sendOk("本地图限制等级120级。您的能力没有资格暗黑龙王副本");
                cm.dispose();
              }else{
			cm.warp(240050400);//外面 240050400//240060200
cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战暗黑龙王，大家祝福他能活着出来吧~");			
				cm.dispose();
                return;
	      }  
        } else if (selection == 21) {//大王蜈蚣
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格大王蜈蚣副本");
                cm.dispose();
              }else{
			cm.warp(701010322);
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战大王蜈蚣副本，大家都来战个痛快吧~");				
				cm.dispose();
                return;
	      }
              } else if (selection == 22) {//马来西亚
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格马来西亚副本");
                cm.dispose();
              }else{
			cm.warp(551030100);  
			cm.喇叭(1, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战马来副本，大家都来战个痛快吧~");	
				cm.dispose();
                return;
	      }  
        }
    }
}
