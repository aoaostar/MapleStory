var 星星 = "#fEffect/CharacterEff/1114000/2/0#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
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
           // text += """本服每周双休日举行#rPK排名赛""\r\n"
           //             text += "\t\t\t  #e自助赚钱系统#b（建议购买双倍爆率卡） #k!#n\r\n"
           // text += "       "+ 蓝色角点 +"#L21##rLv30.大王蜈蚣#l\r\n\r\n"//3
           // text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
           // text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
			//text += "\t\t\t  #e初级系统#b（建议购买双倍爆率卡） #k!#n\r\n"
           // text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
           // text += "#L1##e#d" + 蓝色角点 + "红蜗牛王#l#L2##d " + 蓝色角点 + "木妖王#l\r\n\r\n"//3
		   text+="\t\t本服所有BOSS均出#v2022468#快去杀BOSS吧！#l\r\n";
		   text += "#L30#" + 蓝色角点 +"#r1V1 PK场(2W点卷/人)#L40#" + 蓝色角点 +"#r3V3 PK场(10个#v2002033#/人)\r\n\r\n"//3		   
          //  text += "#L3##d" + 蓝色角点 + "蘑菇王  #L5##d" + 蓝色角点 + "僵尸菇王#l\r\n\r\n"//3
         //   text += "#L5##d" + 蓝色角点 + "僵尸菇王#l\r\n\r\n"//3
          // text += "#L7##d" + 蓝色角点 + "妖怪禅师#l#L8##d " + 蓝色角点 + "鳄鱼王#l\r\n\r\n"//3
          //  text += "#L9##d" + 蓝色角点 + "艾利杰  #l#L10##d " + 蓝色角点 + "歇尔夫#l\r\n\r\n"//3
           // text += "#L12##d" + 蓝色角点 + "蝙蝠怪#l\r\n\r\n"//3
           // text += "#L13##d" + 蓝色角点 + "格瑞分多#l#L14##d" + 蓝色角点 + "喷火龙#l#L12##d" + 蓝色角点 + "蝙蝠怪#l\r\n\r\n"//3
           // text += "#L15##d" + 蓝色角点 + "多多#l    #L16##d " + 蓝色角点 + "玄冰独角兽#l\r\n\r\n"//3
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
                     // text += "\t\t\t#e欢迎来到本服特色传送系统#b#k#n\r\n"
			//text += "" + 红色箭头 +"#L0##b本服BOSS体系说明#l\r\n\r\n"//3 
			text += "#L1#" + 蓝色角点 + "红蜗牛王#l#L2##d " + 蓝色角点 + "木妖王#l\r\n"//
			text += "#L7#" + 蓝色角点 + "妖怪禅师#l#L8##d " + 蓝色角点 + "鳄鱼王#l\r\n"//3
            text += "#L9#" + 蓝色角点 + "艾利杰  #l#L10##d " + 蓝色角点 + "歇尔夫#l\r\n\r\n"//3
            text += "#d#L3#" + 蓝色角点 + "蘑菇王    #d#L5#" + 蓝色角点 + "僵尸菇王#l    #d#L13#" + 蓝色角点 + "格瑞分多#l\r\n"//3	
            text += "#d#L14#" + 蓝色角点 + "喷火龙#l    #d#L12#" + 蓝色角点 + "蝙蝠魔#l      #L21#" + 蓝色角点 +"#r大王蜈蚣\r\n\r\n"//3			
		//	text += "              \r\n\r\n"//3
			//text += "" + 蓝色角点 +"#L12##r蝙蝠魔\r\n\r\n"//3
            text += "#L17#" + 蓝色角点 +"#r鱼王皮亚奴斯洞穴      #L18#" + 蓝色角点 +"#r时间塔的本源闹钟\r\n"//3			
            //text += "" + 蓝色角点 +"#L26##r巨大蝙蝠怪\r\n\r\n"//3
            text += "#L50#" + 蓝色角点 + "#r蝙蝠怪    #L70#" + 蓝色角点 + "#r妖僧   #L27#" + 蓝色角点 + "#r大树   #L20#" + 蓝色角点 +"#rPB\r\n"//3	" + 蓝色角点 + "#L23##r绯红" + 蓝色角点 + "#L26##r巨大蝙蝠怪
			//text += "" + 蓝色角点 +"#L23##r绯红\r\n\r\n"//3
			text += "#L22#" + 蓝色角点 +"#r狮子      #L24#" + 蓝色角点 +"#r黑龙王 #L19#" + 蓝色角点 +"#r扎昆   #L25#" + 蓝色角点 +"#r终极BOSS     \r\n\r\n"//3
			//text += "" + 蓝色角点 +"#L30##r妖僧\r\n\r\n"//3
			//text += "    \r\n\r\n"//3
			//text += "" + 蓝色角点 +"#L27##r大树        " + 蓝色角点 +"#L19##r扎昆\r\n\r\n"//3
		//	text += "        \r\n\r\n"//3
			//text += "" + 蓝色角点 +"#L20##rPB\r\n\r\n"//3
			
			//text += "" + 蓝色角点 +"#L40##r组队PK场\r\n\r\n"//3
            // text += "" + 蓝色角点 + "#L19##rLv70.扎昆的祭坛挑战#l\r\n\r\n"//3
            // text += "" + 蓝色角点 + "#L20##rLv70.暗黑龙王的巢穴挑战#l\r\n\r\n"//3
          //  text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
			//text += ""+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+星星+"\r\n"
            cm.sendSimple(text);
			} else if (selection == 0) {//体系说明
			cm.sendOk("#e独家BOSS体系,还有许多待开放boss（"+星星+"=5*"+爱心+"）!\r\n\r\n1.大王蜈蚣（难度系数为零）\r\
n2.蝙蝠魔（难度系数"+爱心+"）\r\
n3.鱼王（难度系数"+爱心+"）\r\
n4.巨大蝙蝠怪（难度系数"+爱心+"）\r\
n5.闹钟（难度系数"+爱心+"）\r\
n6.绯红1阶（难度系数"+爱心+爱心+"）\r\
n7.狮子2阶（难度系数"+爱心+爱心+爱心+"）\r\
n8.黑龙3阶（难度系数"+爱心+爱心+爱心+爱心+"）\r\
n9.大树4阶（难度系数"+爱心+爱心+爱心+爱心+爱心+"）\r\
n10.扎昆5阶（难度系数"+星星+星星+星星+星星+星星+"）");
        } else if (selection == 1) {//红蜗牛王
            if (cm.getLevel() < 10 ) {  
            cm.sendOk("本地图限制等级10级。您的能力没有资格挑战红蜗牛王");
                cm.dispose();
              }else{
			cm.warp(104000400);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战红蜗牛王，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
		  
		  } else if (selection == 20) {//PB
			var party = cm.getPlayer().getParty();	
			if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
			}else if(party.getMembers().size() < 1) {
	            cm.sendOk("需要 1 人以上的组队才能进入！!");
                cm.dispose();
			//}else 
			}else if(cm.getLevel() < 120){
	            cm.sendOk("需要120级才能带队入场.");
                cm.dispose();
			}else if(cm.getPlayer().getMeso() < 5000000){
	            cm.sendOk("需要500W才能入场.");
                cm.dispose();
			}else if (cm.getPlayerCount(910000022) > 0){
	            cm.sendOk("已经有人在挑战PB了.");
                cm.dispose();
			}else{
				cm.warpParty(270050100);
				cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "带领着队伍开始挑战PB，大家都来战个痛快吧~");
                cm.dispose();
                return;
	      }
		  
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
		  
         } else if (selection == 2) {//木妖王
            if (cm.getLevel() < 20 ) {  
            cm.sendOk("本地图限制等级20级。您的能力没有资格挑战木妖王");
                cm.dispose();
              }else{
			cm.warp(101030404);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战树妖王，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
		  
		} else if (selection == 30) {//妖僧
            if (cm.getLevel() < 140 ) {  
            cm.sendOk("本地图限制等级140级。您的能力没有资格挑战妖僧");
                cm.dispose();
              }else{
			cm.warp(702070400);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战妖僧，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }   
		  
        } else if (selection == 3) {//蘑菇王
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格挑战蘑菇王");
                cm.dispose();
              }else{
			cm.warp(100000005);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战蘑菇王，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
        } else if (selection == 4) {//寄居蟹
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格挑战寄居蟹");
                cm.dispose();
              }else{
			cm.warp(110040000);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战寄居蟹，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 5) {//僵尸蘑菇王
            if (cm.getLevel() < 40 ) {  
            cm.sendOk("本地图限制等级40级。您的能力没有资格挑战僵尸蘑菇王");
                cm.dispose();
              }else{
			cm.warp(105070002); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战僵尸蘑菇王，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
		  
		  } else if (selection == 50) {//蝙蝠魔
            if (cm.getLevel() < 10 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战蝙蝠魔");
                cm.dispose();
              }else{
			cm.warp(105090900); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战蝙蝠怪，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      }
        } else if (selection == 6) {//肯德熊
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战肯德熊");
                cm.dispose();
              }else{
			cm.warp(250010304); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战肯德熊，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 7) {//妖怪禅师
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战妖怪禅师");
                cm.dispose();
              }else{
			cm.warp(250010503); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战妖怪禅师，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 8) {//鳄鱼王
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战鳄鱼王");
                cm.dispose();
              }else{
			cm.warp(107000300); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战鳄鱼王，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 9) {//艾利杰
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战艾利杰");
                cm.dispose();
              }else{
			cm.warp(200010300);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战艾利杰，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 10) {//歇尔夫
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战歇尔夫");
                cm.dispose();
              }else{
			cm.warp(230020100);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战歇尔夫，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
        } else if (selection == 11) {//九尾狐
            if (cm.getLevel() < 50 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战九尾狐");
                cm.dispose();
              }else{
			cm.warp(222010310);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战九尾狐，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      } 
       } else if (selection == 12) {//蝙蝠魔
            if (cm.getLevel() < 10 ) {  
            cm.sendOk("本地图限制等级50级。您的能力没有资格挑战蝙蝠魔");
                cm.dispose();
              }else{
			cm.warp(101000300); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战蝙蝠怪，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
         } else if (selection == 13) {//格瑞分多
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战格瑞分多");
                cm.dispose();
              }else{
			cm.warp(240020101); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战格瑞芬多，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      } 
        } else if (selection == 14) {//喷火龙
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战喷火龙");
                cm.dispose();
              }else{
			cm.warp(240020402); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战喷火龙，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      }
        } else if (selection == 15) {//多多
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战多多");
                cm.dispose();
              }else{
			cm.warp(270010500);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战多多，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }
        } else if (selection == 16) {//玄冰独角兽
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战玄冰独角兽");
                cm.dispose();
              }else{
			cm.warp(270020500);  
						cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战玄冰独角兽，大家都来战个痛快吧~");
				cm.dispose();
                return;
	      }  
        } else if (selection == 17) {//鱼王
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战鱼王");
                cm.dispose();
              }else{
			cm.warp(230040420); 
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战鱼王，大家都来战个痛快吧~");			
				cm.dispose();
                return;
	      }
        } else if (selection == 18) {//闹钟
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格闹钟");
                cm.dispose();
              }else{
			cm.warp(220080000);  
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战闹钟，大家都来战个痛快吧~");	
				cm.dispose();
                return;
	      }
      
            } else if (selection == 27) {//大树
			var party = cm.getPlayer().getParty();	
			if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
			}else if(party.getMembers().size() < 1) {
	            cm.sendOk("需要 1 人以上的组队才能进入！!");
                cm.dispose();
			//}else 
			}else if(cm.getLevel() < 70){
	            cm.sendOk("需要70级才能入场.");
                cm.dispose();
				} else if (cm.getBossLog('dashu') >10) {
                    cm.sendOk("每天只能打10次大树！");
                    cm.dispose();
			}else if(cm.getPlayer().getMeso() < 5000000){
	            cm.sendOk("需要500W才能入场.");
                cm.dispose();
			}else if (cm.getPlayerCount(541020800) > 0){
	            cm.sendOk("已经有人在挑战大树了.");
                cm.dispose();
			}else{
				cm.warpParty(541020800);
				cm.setBossLog("dashu");
				//cm.givePartyBossLog("树精Boss");
				cm.gainMeso(-5000000);
				//cm.resetMap(541020800);
				cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战大树，大家都来战个痛快吧~");
                cm.dispose();
                return;
}
		  
        } else if (selection == 19) {//扎昆
            var party = cm.getPlayer().getParty();	
			if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
			}else if(party.getMembers().size() < 1) {
	            cm.sendOk("需要 1 人以上的组队才能进入！!");
                cm.dispose(); 
		}else	if(cm.getLevel() < 70){
	            cm.sendOk("需要70级才能入场.");
                cm.dispose();
			}else if(cm.getPlayer().getMeso() < 15000000){
	            cm.sendOk("你大金币不足，需要1500W才能入场.");
                cm.dispose();
			}else if (cm.getPlayerCount(910000018) > 0){
	            cm.sendOk("已经有人在挑战扎昆了.");
                cm.dispose();
			}else{
				cm.warpParty(910000018);
				cm.dispose();
                return;
	      }
        } else if (selection == 21) {//大王蜈蚣
            if (cm.getLevel() < 30 ) {  
            cm.sendOk("本地图限制等级30级。您的能力没有资格大王蜈蚣");
                cm.dispose();
              }else{
			cm.warp(701010321);
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战大王蜈蚣，大家都来战个痛快吧~");				
				cm.dispose();
                return;
	      }
              } else if (selection == 22) {//狮子或熊
            if (cm.getLevel() < 70 ) {  
            cm.sendOk("本地图限制等级70级。您的能力没有资格挑战狮子或熊");
                cm.dispose();
              }else{
			cm.warp(551030100);  
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战狮子或熊，大家都来战个痛快吧~");	
				cm.dispose();
                return;
				 }
				} else if (selection == 23) {//绯红
            if (cm.getLevel() < 100 ) {  
            cm.sendOk("本地图限制等级100级。您的能力没有资格挑战绯红");
                cm.dispose();
              }else{
			cm.warp(803001200);
			cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战绯红，大家都来战个痛快吧~");
                cm.dispose();
                return;
	      }  
            } else if (selection == 24) {//黑龙
            var party = cm.getPlayer().getParty();	
			if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
			}else if(party.getMembers().size() < 1) {
	            cm.sendOk("需要 1 人以上的组队才能进入！!");
                cm.dispose(); 
		}else	if(cm.getLevel() < 70){
	            cm.sendOk("需要70级才能入场.");
                cm.dispose();
			}else if(cm.getPlayer().getMeso() < 20000000){
	            cm.sendOk("你大金币不足，需要2000W才能入场.");
                cm.dispose();
			}else if (cm.getPlayerCount(910000019) > 0){
	            cm.sendOk("已经有人在挑战黑龙了.");
                cm.dispose();
			}else{
				cm.warpParty(910000019);
				cm.dispose();
                return;
	      }
		} else if (selection == 26) {//蝙蝠
            if (cm.getLevel() < 70 ) {  
				cm.sendOk("本地图限制等级70级。您的能力没有资格挑战巨大蝙蝠怪");
                cm.dispose();
            }else{
				cm.warp(105100100,0);  //每次传送到地图中间
				cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战巨魔蝙蝠怪~");
				cm.dispose();
                return;
	        } 	

} else if (selection == 70) {//妖僧
            if (cm.getLevel() < 130 ) {  
				cm.sendOk("本地图限制等级130级。您的能力没有资格挑战妖僧");
                cm.dispose();
            }else{
				cm.warp(702070400,0);  //每次传送到地图中间
				cm.喇叭(2, "[BOSS传送]：玩家" + cm.getPlayer().getName() + "开始挑战妖僧~");
				cm.dispose();
                return;
	        } 	
			
		} else if (selection == 25) {//终极
            var party = cm.getPlayer().getParty();	
			if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
			}else if(party.getMembers().size() < 1) {
	            cm.sendOk("需要 1 人以上的组队才能进入！!");
                cm.dispose(); 
		}else	if(cm.getLevel() < 200){
	            cm.sendOk("需要200级才能入场.");
                cm.dispose();
			}else if(cm.getPlayer().getMeso() < 20000000){
	            cm.sendOk("你大金币不足，需要2000W才能入场.");
                cm.dispose();
			}else if (cm.getPlayerCount(910000020) > 0){
	            cm.sendOk("已经有人在挑战终极BOSS了.");
                cm.dispose();
			}else{
				cm.warpParty(910000020);
				cm.dispose();
                return;
	      }
        }
    }
}
