var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";
var 红色 = "#fEffect/CharacterEff/1114000/2/0#";
var status = 0;
var fstype = 0;


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

	    var textz = "#r在我这里强化装备!#k\r\n其中充值币不限制次数 金币强化每日10次 #k\r\n注意:装备请放第一格，#r强化成功会全区报喜哦!#k\r\n";

		textz += "------------------------------------------------------\r\n";
               
	//    textz += "#b#L9#" + 红色 + "使用1个#z4170000##v4170000#增加装备30HP、30MP、1物防、1魔防（100%成功）\r\n\r\n";
		
		textz += "#b#L4#" + 红色 + "15个充值币增加装备四维各10点+双攻5（100%成功)\r\n\r\n";
		
		//textz += "#b#L5#" + 红色 + "10000点券增加装备四维各10点+双攻7（失败不返还）\r\n\r\n";
		
		//textz += "#b#L7#" + 红色 + "10000抵用增加装备四维各7点+双攻5（失败不返还）\r\n\r\n";
		
	textz += "#b#L6#" + 红色 + "8000W金币增加装备四维各6点+双攻3（失败不返还）\r\n\r\n";
		
		//textz += "#b#L10#" + 红色 + "使用1个#z4170009##v4170009#兑换一个#z2370000##v2370000#\r\n\r\n";
		//textz += "#r#L8#" + 蓝色角点 + "用1个时间之泪增加装备攻击10（100%成功，扣除回合）\r\n";



		cm.sendSimple (textz);  
  
//----------------------------------------------------------------------------------------------------------------------------------------------	
//----------------------------------------------------------------------------------------------------------------------------------------------		
	}else if (status == 1) {

            if (selection == 0) { //力量母矿
                fstype = 0;
                cm.sendNext("你目前选择的是用力量母矿增加装备10力量（有几率失败，失败退回一半，成功了回合减1）");

            }else if (selection == 1) { //智慧母矿
                fstype = 1;
                cm.sendNext("你目前选择的是用智慧母矿增加装备10智力（有几率失败，失败退回一半，成功了回合减1）");

            }else if (selection == 2) { //敏捷母矿
                fstype = 2;
                cm.sendNext("你目前选择的是用敏捷母矿增加装备10敏捷（有几率失败，失败退回一半，成功了回合减1）");

            }else if (selection == 3) { //幸运母矿
                fstype = 3;
                cm.sendNext("你目前选择的是用幸运母矿增加装备10运气（有几率失败，失败退回一半，成功了回合减1）");
        
			}else if (selection == 4) { //象征
                fstype = 4;
                cm.sendNext("你目前选择的是用15充值币增加装备四维各10+双攻5.");

            }else if (selection == 5) { //象征
                fstype = 5;
                cm.sendNext("你目前选择的是用1万点券增加装备四维各10+双攻7，（失败不返还）");

            }else if (selection == 6) { //象征
                fstype = 6;
                cm.sendNext("你目前选择的是用8000W金币增加装备四维各6点+双攻3（失败返还一半）");

            }else if (selection == 7) { //黑龙角
                fstype = 7;
                cm.sendNext("你目前选择的是用1万点抵用增加装备四维各7+双攻5（失败不返还）");

            }else if (selection == 8) { //时间之石
                fstype = 8;
                cm.sendNext("你目前选择的是用时间之石增加装备攻击、魔法力10.加工费2000点卷（100%成功，回合减1）");
            }else if (selection == 9) { //飞天猪的蛋
                fstype = 9;
                cm.sendNext("你目前选择的是用飞天猪的蛋增加装备30HP、30MP、1物防、1魔防（100%成功，不减回合））");
            }else if (selection == 10) { //海盗副本蛋
                fstype = 10;
                cm.sendNext("你目前选择的是用海盗副本蛋兑换孙子兵法书");
            }

//----------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------

      } else if (status == 2) {

            if (fstype == 0) { //力量母矿
                fstype = 0;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
                
  
                }
            }


            if (fstype == 1) { //智慧母矿
                fstype = 1;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }

            if (fstype == 2) { //敏捷母矿
                fstype = 2;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
              
                }
            }

            if (fstype == 3) { //运气母矿
                fstype = 3;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }
             

            if (fstype == 4) { //运气母矿
                fstype = 4;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }


            if (fstype == 5) { //运气母矿
                fstype = 5;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }


            if (fstype == 6) { //运气母矿
                fstype = 6;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }


            if (fstype == 7) { //运气母矿
                fstype = 7;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
              
                }
            }

            if (fstype == 8) { //运气母矿
                fstype = 8;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
               
                }
            }
			
			if (fstype == 9) { //运气母矿
                fstype = 9;
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("对不起,你装备栏第一格没有装备!");
                    cm.dispose();
                }
            }

//----------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------
	if (fstype == 0) {
		if (!cm.haveItem(4005000,10))  {
                    cm.sendOk("请带来#r 10 #k个#z4005000##v4005000#");
                    cm.dispose();
		}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                    cm.sendOk("升级次数没了，无法强化!");
                    cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 4);
                 if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
		 item.setStr(item.getStr()+10);
                 item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 cm.gainItem(4005000,-10);
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                 cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        成功为装备增加10力量"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.gainItem(4005000,-2);
		 cm.sendOk("强化失败，退回你8个力量水晶");	
		}
		 cm.dispose();
		}



	} else if (fstype == 1) {
		if (!cm.haveItem(4005001,10))  {
                    cm.sendOk("请带来#r 10 #k个#z4005001##v4005001#");
                    cm.dispose();
		}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                    cm.sendOk("升级次数没了，无法强化!");
                    cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 4);
                 if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
		 item.setInt(item.getInt()+10);
                 item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 cm.gainItem(4005001,-10);
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                 cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        成功为装备增加10智力"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.gainItem(4005001,-2);
		 cm.sendOk("强化失败，退回你8个智慧水晶");	
		}
		 cm.dispose();
		}

	} else if (fstype == 2) {
		if (!cm.haveItem(4005002,10))  {
                    cm.sendOk("请带来#r 10 #k个#z4005002##v4005002#");
                    cm.dispose();
		}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                    cm.sendOk("升级次数没了，无法强化!");
                    cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 4);
                 if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
		 item.setDex(item.getDex()+20);
                 item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 cm.gainItem(4005002,-10);
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                 cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        成功为装备增加10敏捷"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.gainItem(4005002,-2);
		 cm.sendOk("强化失败，退回你8个敏捷水晶");	
		}
		 cm.dispose();
		}

	} else if (fstype == 3) {
		if (!cm.haveItem(4005003,10))  {
                    cm.sendOk("请带来#r 10 #k个#z4005003##v4005003#");
                    cm.dispose();
		}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                    cm.sendOk("升级次数没了，无法强化!");
                    cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 4);
                 if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
		 item.setLuk(item.getLuk()+20);
                 item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 cm.gainItem(4005003,-10);
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                 cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        成功为装备增加10运气"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.gainItem(4005003,-2);
		 cm.sendOk("强化失败，退回你8个运气水晶");	
		}
		 cm.dispose();
		}

	}  else if (fstype == 4) {
              if (cm.getmoneyb() < 15){
                    cm.sendOk("请带来15充值币加工费");
                    cm.dispose();
					
	//	}	else   			if (cm.getBossLog('PlayQuest40') >= 5) {
		//	cm.sendOk("你今天强化次数超过5次!");
			//cm.dispose();	
			
		} else {

                // var chance = Math.floor(Math.random() * 2);
                // if (chance <= 1) {
					var equip = cm.getInventory(1).getItem(1);
                  if( equip != null ) { 
					 
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
                // item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 item.setStr(equip.getStr()+10); //给予装备力量
		 item.setDex(equip.getDex() + 10);//给予装备敏捷
		 item.setInt(equip.getInt() + 10);//给予装备智力
		 item.setLuk(equip.getLuk() + 10);//给予装备运气
		 item.setWatk(equip.getWatk() + 5);
		 item.setMatk(equip.getMatk() + 5);
		// cm.refreshEquip(equip);
		 //cm.gainItem(4001084,-1);
		 cm.setmoneyb(-15);
		 cm.gainjf(+15);
		// cm.setBossLog('PlayQuest40');
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
              cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        使用20个充值币为装备增加四维各10点+5G");
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		//} else {
		// cm.setmoneyb(-15);
		// cm.gainjf(+15);
		// cm.setBossLog('PlayQuest40');
		// cm.sendOk("强化失败，退回你15个充值币");	
	//	}
		 cm.dispose();
		}
}
	} else if (fstype == 5) {
		if (cm.getNX(1) < 10000){
                    cm.sendOk("请带来1万点券加工费");
                    cm.dispose();
					
		}	else    if (cm.getBossLog('PlayQuest41') > 2) {
			cm.sendOk("你今天强化次数超过3次!");
			cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 2);
                 if (chance <= 1) {
					var equip = cm.getInventory(1).getItem(1);
                  if( equip != null ) { 
					 
                // var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                // var statup = new java.util.ArrayList();
                // item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 item.setStr(equip.getStr()+15); //给予装备力量
		 item.setDex(equip.getDex() + 15);//给予装备敏捷
		 item.setInt(equip.getInt() + 15);//给予装备智力
		 item.setLuk(equip.getLuk() + 15);//给予装备运气
		 item.setWatk(equip.getWatk() + 10);
		 item.setMatk(equip.getMatk() + 10);
		 cm.refreshEquip(equip);
		 //cm.gainItem(4001084,-1);
		 cm.setmoneyb(-30);
		 cm.gainjf(+30);
		 cm.setBossLog('PlayQuest40');
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
              cm.serverNotice("『浩浩强化系统』：恭喜"+ cm.getChar().getName() +"        使用30个充值币为装备增加四维各15点+10G");
          //       Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                // Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.setmoneyb(-15);
		 cm.gainjf(+15);
		 cm.setBossLog('PlayQuest40');
		 cm.sendOk("强化失败，退回你15个充值币");	
		}
		 cm.dispose();
		}
}

	} else if (fstype == 6) {
		 if (cm.getMeso() < 80000000){
                    cm.sendOk("请带来8000W金币加工费");
                    cm.dispose();
		}	else    if (cm.getBossLog('PlayQuest43') >= 10) {
			cm.sendOk("你今天强化次数超过10次!");
			cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 7);
                 if (chance <= 1) {
				//	var equip = cm.getInventory(1).getItem(1);
                 // if( equip != null ) { 
					 
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
                // item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 item.setStr(item.getStr()+6); //给予装备力量
		 item.setDex(item.getDex() + 6);//给予装备敏捷
		 item.setInt(item.getInt() + 6);//给予装备智力
		 item.setLuk(item.getLuk() + 6);//给予装备运气
		 item.setWatk(item.getWatk() + 3);
		 item.setMatk(item.getMatk() + 3);
		// cm.refreshEquip(equip);
		 //cm.gainItem(4001084,-1);
		 cm.gainMeso(-80000000);
		 cm.setBossLog('PlayQuest43');
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
              cm.serverNotice("『强化系统』：恭喜"+ cm.getChar().getName() +"        使用1E金币为装备增加四维各10点+5双攻");
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.gainMeso(-8000000);
		 cm.setBossLog('PlayQuest43');
		 cm.sendOk("强化失败，扣除一半手续费。");	
		}
		 cm.dispose();
		}
}
	} else if (fstype == 7) {
                if (cm.getPlayer().getDY() <10000){
                    cm.sendOk("请带来10000抵用");
                    cm.dispose();
		}	else    if (cm.getBossLog('PlayQuest42') > 2) {
			cm.sendOk("你今天强化次数超过3次!");
			cm.dispose();
			
		} else {

                 var chance = Math.floor(Math.random() * 2);
                 if (chance <= 1) {
					var equip = cm.getInventory(1).getItem(1);
                  if( equip != null ) { 
					 
                // var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                // var statup = new java.util.ArrayList();
                // item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 item.setStr(equip.getStr()+15); //给予装备力量
		 item.setDex(equip.getDex() + 15);//给予装备敏捷
		 item.setInt(equip.getInt() + 15);//给予装备智力
		 item.setLuk(equip.getLuk() + 15);//给予装备运气
		 item.setWatk(equip.getWatk() + 10);
		 item.setMatk(equip.getMatk() + 10);
		 cm.refreshEquip(equip);
		 //cm.gainItem(4001084,-1);
		 cm.setmoneyb(-30);
		 cm.gainjf(+30);
		 cm.setBossLog('PlayQuest40');
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
              cm.serverNotice("『浩浩强化系统』：恭喜"+ cm.getChar().getName() +"        使用30个充值币为装备增加四维各15点+10G");
          //       Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                // Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		} else {
		 cm.setmoneyb(-15);
		 cm.gainjf(+15);
		 cm.setBossLog('PlayQuest40');
		 cm.sendOk("强化失败，退回你15个充值币");	
		}
		 cm.dispose();
		}
}

	} else if (fstype == 8) {
		if (!cm.haveItem(4021010,1))  {
                    cm.sendOk("请带来#r 1 #k个#z4021010##v4021010#");
                    cm.dispose();
		}else if (cm.getPlayer().getNX() <=2000){
                    cm.sendOk("请带来2000点卷");
                    cm.dispose();
		//}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                   // cm.sendOk("升级次数没了，无法强化!");
                    //cm.dispose();
			
		} else {

                 //var chance = Math.floor(Math.random() * 3);
                // if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
                 item.setUpgradeSlots((item.getUpgradeSlots() - 1));
		 item.setWatk(item.getWatk() + 10);
		 item.setMatk(item.getMatk() + 10);
		 cm.gainItem(4021010,-1);
		 cm.gainNX(-2000);
		 cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                 cm.serverNotice("『黑龙之角强化装备』：恭喜"+ cm.getChar().getName() +"        使用1个时间之石为装备增加攻击10"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		//} else {
		 //cm.gainItem(4005003,-5);
		 //cm.sendOk("强化失败，退回你5个运气母矿");	
		//}
		 cm.dispose();
		}


	}	else if (fstype == 9) {
		if (!cm.haveItem(4170000,1)){
                    cm.sendOk("请带来#r 1 #k个#z4170000##v4170000#");
                    cm.dispose();
		}else if (cm.getMeso() <= 0){
                    cm.sendOk("身上至少带1金币");
                    cm.dispose();
		//}else if (cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() <=0) {
                   // cm.sendOk("升级次数没了，无法强化!");
                    //cm.dispose();
			
		} else {

                 //var chance = Math.floor(Math.random() * 3);
                // if (chance <= 1) {
                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
				item.setHp(item.getHp()+30);
				item.setMp(item.getMp()+30);
				item.setWdef(item.getWdef()+1);
				item.setMdef(item.getMdef()+1);
		//cm.gainItem(4005003,-1);
                 //item.setUpgradeSlots((item.getUpgradeSlots() - 1));
				cm.gainItem(4170000,-1);

				cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
		 
                // cm.serverNotice("『强化装备』：恭喜"+ cm.getChar().getName() +"成功为装备增加30HP、30MP、1物防、1魔防"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
		//} else {
		 //cm.gainItem(4005003,-1);
		 //cm.sendOk("强化失败，退回你9个运气母矿");	
		//}
		 cm.dispose();
		}

	} else if (fstype == 10) {
		if (!cm.haveItem(4170009,1)){
            cm.sendOk("请带来#r 1 #k个#z4170009##v4170009#");
            cm.dispose();
		} else {
            cm.gainItem(4170009,-1);
			cm.gainItem(2370000,1);
			cm.sendOk("#r#e兑换成功,祝您游戏愉快!#k");
            cm.dispose();
		} 

	}
									
}
}

