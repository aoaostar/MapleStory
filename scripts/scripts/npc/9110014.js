 importPackage(net.sf.cherry.client);
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
		 if (cm.getLevel() < 50 ) {
			cm.sendOk("只有 #r50级#k 才可以开肝该副本。");
			cm.dispose();
			 }
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "\r\n勇士:#d#h ##k，3000W金币召唤一次。\r\n\r\n";

		textz += "#b#L0#解开封印【召唤--时间的宠儿(PB)】#l\r\n\r\n";
		
		//textz += "#b#L1#解开封印【召唤--扎昆手臂(经验)】#l\r\n\r\n";
		
	//	textz += "#b#L2#解开封印【召唤--三号BOSS】#l\r\n\r\n";
		
	//	textz += "#r#L3#解开封印【召唤--四号BOSS】#l\r\n\r\n";
		
	//	textz += "#r#L5#解开封印【召唤--五号BOSS】#l\r\n\r\n";
		
		textz += "#r#L4#我要离开#l\r\n\r\n";

		

		cm.sendSimple (textz);  

			
	}else if (status == 1) {
    
	if (selection == 0){
		var party = cm.getPlayer().getParty();	
		if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                cm.sendOk("你不是队长。请你们队长来说话吧！");
                cm.dispose();
				}else if(cm.getLevel() < 120){
	            cm.sendOk("需要120级才能召唤.");
                cm.dispose();
//	}	else    if (cm.getBossLog('PlayQuest57') > 3) {
			//cm.sendOk("你今天挑战次数超过3次!");
			//cm.dispose();	
	}	else    if(cm.getMeso() < 30000000){
            cm.sendOk("你金币不足3000W!");
			cm.dispose();
		//	cm.sendOk("你身上不足3000点券!");
			//cm.dispose();
			}else{ 
                   // cm.sendOk("当前船长并没有清理完，无法继续召唤！");
                  //  cm.dispose();
		//}else if(party.getMembers().size() < 0) {
	           // cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
                   // cm.dispose();	
		//}else if (cm.getMeso() >= 5000000) {	//&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1)// #i04001257# #i04001258# #i04001259# #i04001260#
                       // cm.gainNX(-3000);
						cm.setBossLog('PlayQuest57');
                        cm.spawnMonster(8820001,1);
						 cm.gainMeso(-30000000);	
                        cm.dispose();
						cm.喇叭(2,"[PB副本]：玩家" + cm.getPlayer().getName() + "开始挑战时间的宠儿(PB)~");
                       
}

	}else if (selection == 1){
		var party = cm.getPlayer().getParty();		
		if (cm.getBossLog('PlayQuest56') > 2) {
			cm.sendOk("你今天挑战次数超过2次!");
			cm.dispose();	
	}	else if (cm.getMeso() < 2000000) {
			cm.sendOk("你身上不足200万金币!");
			cm.dispose();
			}else{ 
	//	}else if(party.getMembers().size() < 0) {
	 //           cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
     //               cm.dispose();	
		//&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1)// #i04001257# #i04001258# #i04001259# #i04001260#
                        cm.gainMeso(-2000000);
						cm.setBossLog('PlayQuest56');
                        cm.spawnMonster(8800003,1);
                        cm.dispose();
						cm.喇叭(2,"[扎昆副本]：玩家" + cm.getPlayer().getName() + "开始挑战扎昆手臂~");
                        
			cm.dispose();			
}
    }else if (selection == 2){
		var party = cm.getPlayer().getParty();		
		if (cm.getBossLog('PlayQuest52') >= 2) {
			cm.sendOk("你今天挑战次数超过2次!");
			cm.dispose();	
	}	else if (cm.getMeso() < 5000000) {
			cm.sendOk("你身上不足500万金币!");
			cm.dispose();
			}else{ 
	//	}else if(party.getMembers().size() < 0) {
	//            cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
    //                cm.dispose();	
		//&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1)// #i04001257# #i04001258# #i04001259# #i04001260#
                       cm.gainMeso(-5000000);
					   cm.setBossLog('PlayQuest52');
                        cm.spawnMonster(9400591,1);
                        cm.dispose();
						cm.喇叭(2,"[绯红副本]：玩家" + cm.getPlayer().getName() + "开始挑战三号BOSS，开始爆肝啊啊啊啊!!!");
                       
			cm.dispose();	
}
     }else if (selection == 3){
		var party = cm.getPlayer().getParty();		
		if (cm.getBossLog('PlayQuest53') >= 2) {
			cm.sendOk("你今天挑战次数超过2次!");
			cm.dispose();	
	}	else if (cm.getMeso() < 5000000) {
			cm.sendOk("你身上不足500万金币!");
			cm.dispose();
			}else{ 
		//}else if(party.getMembers().size() < 0) {
	     //       cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
        //            cm.dispose();	
		//&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1)// #i04001257# #i04001258# #i04001259# #i04001260#
                       cm.gainMeso(-5000000);
					   cm.setBossLog('PlayQuest53');
                        cm.spawnMonster(9400592,1);
                        cm.dispose();
						cm.喇叭(2,"[绯红副本]：玩家" + cm.getPlayer().getName() + "开始挑战四号BOSS!!!希望他能活着出来");
                       
			cm.dispose();	
			}
			}else if (selection == 5){
		var party = cm.getPlayer().getParty();		
		if (cm.getBossLog('PlayQuest54') >= 2) {
			cm.sendOk("你今天挑战次数超过2次!");
			cm.dispose();	
	}	else if (cm.getMeso() < 5000000) {
			cm.sendOk("你身上不足500万金币!");
			cm.dispose();
			}else{ 
		//}else if(party.getMembers().size() < 0) {
	     //       cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
        //            cm.dispose();	
		//&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1)// #i04001257# #i04001258# #i04001259# #i04001260#
                         cm.gainMeso(-5000000);
						 cm.setBossLog('PlayQuest54');
                        cm.spawnMonster(9400593,1);
                        cm.dispose();
						cm.喇叭(2,"[绯红副本]：玩家" + cm.getPlayer().getName() + "开始挑战五号BOSS,准备接受暴虐吧");
                       
			cm.dispose();	
			}
	     }else if (selection == 4){
			cm.warp(910000000);
			cm.dispose();	
			}
			
}										
}
}

