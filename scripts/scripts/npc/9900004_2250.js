
importPackage(Packages.client);
importPackage(Packages.client.inventory);
importPackage(Packages.server);
importPackage(Packages.tools);


var status = 0;
var job;
var DJ = "15000"; //扣除的点卷
var 高等五彩水晶 = "4251202"; //扣除的点卷

var ttt = "#fUI/UIWindow.img/Quest/icon9/0#";
var xxx = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";



function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
     cm.sendNext("暂未开放！");
    cm.dispose();

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

            var textz = "#e#b高级装备回收系统,使用前请阅读说明: \r\n\r\n#k1.请将要回收装备放到背包第1格 \r\n#k2.根据装备属性返还#r500-5000万#k不等的冒险币!\r\n3.请看准了在回收,放错了装备后果自负!)\r\n";

            textz += "#r#L2#『回收装备栏第一格装备』\r\n";

            //     textz += "#r#L1#提高装备攻击力 #k+1需要#r1#b个#z4251200#\r\n";
            cm.sendSimple(textz);


        } else if (status == 1) {

            if (selection == 0) { //材料强化在装备上
               if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } 
				else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } 
else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();

                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+2);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力
                 item.setHp(item.getHp() + 0); //血量
                 item.setMp(item.getMp() + 0); //蓝量

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功攻击力+２，次数-１★★>");

                    //	cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }

            } else if (selection == 1) {
               if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力
                 item.setHp(item.getHp() + 0); //血量
                 item.setMp(item.getMp() + 0); //蓝量

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功魔法攻击力+２，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }


            } else if (selection == 3) {//邮票
             if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4002002, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);


                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力
                 item.setHp(item.getHp() + 0); //血量
                 item.setMp(item.getMp() + 0); //蓝量

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功Hp+２0，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }
            } else if (selection == 4) {//邮票
                if (!cm.haveItem(4002002, 1)) {
                    cm.sendOk("需要#r 1 #k个#z4002002##v4002002#");
                    cm.dispose();
} else if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4002002, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力
                 item.setHp(item.getHp() + 0); //血量
                 item.setMp(item.getMp() + 20); //蓝量

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功Mp+２0，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }

            } else if (selection == 5) {//五彩
                if (!cm.haveItem(4251201, 1)) {
                    cm.sendOk("需要#r 1 #k个#z4251201##v4251201#");
                    cm.dispose();
} else if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+3);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功力量+3，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }

            } else if (selection == 6) {//五彩
                if (!cm.haveItem(4251201, 1)) {
                    cm.sendOk("需要#r 1 #k个#z4251201##v4251201#");
                    cm.dispose();
} else if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+3);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功敏捷+3，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }

            } else if (selection == 7) {//五彩
                if (!cm.haveItem(4251201, 1)) {
                    cm.sendOk("需要#r 1 #k个#z4251201##v4251201#");
                    cm.dispose();
} else if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+0);//智力
                 item.setLuk(item.getLuk()+3);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功运气+3，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }

            } else if (selection == 8) {//五彩
                if (!cm.haveItem(4251201, 1)) {
                    cm.sendOk("需要#r 1 #k个#z4251201##v4251201#");
                    cm.dispose();
} else if (!cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() !=0) {
cm.sendOk("你的装备强化次数不够");
cm.dispose();
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("请把要强化的装备放在第一格才能进行.");
                    cm.dispose();

                } else {

                    var statup = new java.util.ArrayList();
                    cm.gainItem(4251201, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    item.setUpgradeSlots(item.getUpgradeSlots() - 1);

                 item.setStr(item.getStr()+0);//力量
		 item.setDex(item.getDex()+0);//敏捷
		 item.setInt(item.getInt()+3);//智力
                 item.setLuk(item.getLuk()+0);//运气
                 item.setWatk(item.getWatk()+0);//攻击力
                 item.setMatk(item.getMatk()+0);//魔法力

                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
                    MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
                    cm.喇叭(4, "" + cm.getPlayer().getName() + ":     ★★装备成功智力+3，次数-１★★>");
                    //cm.sendOk("#r#e强化成功,祝您游戏愉快!#k");
                    cm.dispose();
                }


            } else if (selection == 2) 
			 {
                if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) == null) {
                    cm.sendOk("第一格没有装备,无法回收.");
                } else if (cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() == 1) {
                    cm.sendOk("限时装备不能使用该功能.");
                    cm.dispose();
                    cm.dispose();
                } else if (cm.isCash(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId())) {
                    cm.sendOk("点卷装备不能回收.");
                    cm.dispose();
                }
          else {
	
                    var statup = new java.util.ArrayList();
                    //cm.gainItem(高等五彩水晶, -1)
                    var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
                    var ii = MapleItemInformationProvider.getInstance();
              if(item.getWatk()<20 && item.getMatk() <20  && item.getStr()>10  && item.getInt()>9   && item.getLuk()>9 && item.getDex()>9 )
             
					{
				    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(5000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<60 && item.getMatk() <60  && item.getStr()>20  && item.getInt()>20   && item.getLuk()>20 && item.getDex()>20)
					{    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(10000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					
					else  if(item.getWatk()<100 && item.getMatk()<100)
					{
						    cm.sendOk("#r#e装备不行哦!不想回收！请大侠换一件装备吧!#k");
							cm.dispose();
						
					}
							else  if(item.getWatk()>99 && item.getMatk()>99&& item.getStr()>99  && item.getInt()>99  && item.getLuk()>99 && item.getDex()>99 )
					{
						    cm.sendOk("#r#e勋章不可回收!#k");
							cm.dispose();
						
					}
					else  if(item.getWatk()<110 && item.getMatk()<110)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(1000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
						else  if(item.getWatk()<115&&item.getMatk()<115)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(2000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
							else  if(item.getWatk()<120&&item.getMatk()<120)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(5000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
						else  if(item.getWatk()<130&&item.getMatk()<130)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(7000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
						else  if(item.getWatk()<140&&item.getMatk()<140)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(10000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					} 	else  if(item.getWatk()<150&&item.getMatk()<150)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(15000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
						else  if(item.getWatk()<160&&item.getMatk()<160)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(20000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<170&&item.getMatk()<170)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(25000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<180&&item.getMatk()<180)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(30000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<190&&item.getMatk()<190)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(35000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<200&&item.getMatk()<200)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(40000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<210&&item.getMatk()<210)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(4500000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
						else  if(item.getWatk()<218&&item.getMatk()<218)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(50000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
					else  if(item.getWatk()<225&&item.getMatk()<225)
					{
						 
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(50000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
						
					}
				
					
					else {
				
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1, 1, true);
					cm.gainMeso(50000000);
                    cm.sendOk("#r#e装备回收成功,送你了一笔冒险币!#k");
                    cm.dispose();
					}

                }



            }
        }
    }
}
