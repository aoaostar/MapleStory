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

	    var textz = "#r欢迎来到执念冒险岛洗血中心!#k\r\n #k\r\n注意:装备请放第一格，#r洗血成功会全区报喜哦!#k\r\n";

		textz += "------------------------------------------------------\r\n";
               
	    textz += "#b#L9#" + 红色 + "使用1000点券增加装备100HP  （100%成功）\r\n\r\n";
		
		textz += "#b#L8#" + 红色 + "使用10000点券增加装备1000HP  （100%成功）\r\n\r\n";
		

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
                cm.sendNext("你目前选择的是用30充值币增加装备四维各15+双攻10.（失败返还15充值币）");

            }else if (selection == 5) { //象征
                fstype = 5;
                cm.sendNext("你目前选择的是用1万点券增加装备四维各10+双攻7，（失败不返还）");

            }else if (selection == 6) { //象征
                fstype = 6;
                cm.sendNext("你目前选择的是用2000W金币增加装备四维各5点+双攻3（失败不返还）");

            }else if (selection == 7) { //黑龙角
                fstype = 7;
                cm.sendNext("你目前选择的是用1万点抵用增加装备四维各7+双攻5（失败不返还）");

            }else if (selection == 8) { //时间之石
                fstype = 8;
                cm.sendNext("你目前选择的是用1000点券增加装备100HP（100%成功，不减回合））");
            }else if (selection == 9) { //飞天猪的蛋
                fstype = 9;
                cm.sendNext("你目前选择的是用1000点券增加装备100HP（100%成功，不减回合））");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("暂不支持点券装备升星，请使用普通装备！");
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

	}	else if (fstype == 8) {
		if (cm.getNX(1) < 10000){
                    cm.sendOk("身上至少带1000点券");
                    cm.dispose();

			
		} else {


                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
				item.setHp(item.getHp()+1000);

				cm.gainNX(-10000);

				cm.sendOk("#r#e洗血成功,祝您游戏愉快!#k");
		 
                // cm.serverNotice("『洗血中心』：恭喜"+ cm.getChar().getName() +"成功增加100HP"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);

		 cm.dispose();
		}

	


	}	else if (fstype == 9) {
		if (cm.getNX(1) < 1000){
                    cm.sendOk("身上至少带1000点券");
                    cm.dispose();

			
		} else {


                 var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                 var statup = new java.util.ArrayList();
				item.setHp(item.getHp()+100);

				cm.gainNX(-1000);

				cm.sendOk("#r#e洗血成功,祝您游戏愉快!#k");
		 
                // cm.serverNotice("『洗血中心』：恭喜"+ cm.getChar().getName() +"成功增加100HP"); 
                 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                 Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);

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
}
