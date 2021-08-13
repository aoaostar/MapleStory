var status = 0;
var fstype = 0;
var price = 50000000; //没用
var types = new Array("装备栏", "消耗栏", "任务栏", "杂物栏", "现金栏");
var chance3 = (Math.floor(Math.random() * 3) + 1);
var chance1 = (Math.floor(Math.random() * 3) + 1);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        if (status == 0) {//#L2#使用点卷升级-1次(10000)(127最高)
            cm.sendSimple("#r注意:请将需要升级的装备放在装备栏第一格!\r\n\r\n#b#L2#使用点券砸卷+1次(3000点券)(30次最高)\r\n");


        } else if (status == 1) {

            if (selection == 2) { //使用点卷升级//#L2#使用点卷升级-3次(5000)(127最高)
                fstype = 2;
                cm.sendNext("你要使用3000点卷为装备增加1个升级次数吗?");


            } else if (selection == 3) { //使用五彩升级
                fstype = 3;
                cm.sendNext("你要使用1000点券为装备增加1个升级次数吗?");

            }

        } else if (status == 2) {

            if (fstype == 2) { //使用点卷升级
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("你装备栏第一格没有装备!");
                    cm.dispose();
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("非常抱歉,点装不支持升级!");
                    cm.dispose();
                 } else if (item.getItemId() == 1122000 || item.getItemId() ==1122076) {
                    cm.sendOk("对不起,黑龙项链不能提升次数!");
                    cm.dispose();
                    return;
                }
                   else fstype = 13;		
			}

            if (fstype == 3) { //使用金币升级
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("你装备栏第一格没有装备!");
                    cm.dispose();
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("非常抱歉,点装不支持升级!");
                    cm.dispose();
                 } else if (item.getItemId() == 1122000 || item.getItemId() ==1122076) {
                    cm.sendOk("对不起,黑龙项链不能提升次数!");
                    cm.dispose();
                    return;
                }
				   else fstype = 14;	
            }


             if (fstype == 13) {
var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();

var id = item.getItemId();
var 已升级次数= item.getLevel();
var 剩余回合 = item.getUpgradeSlots();
var 强化上限 = 20;
var pd = 强化上限 - (已升级次数 + 剩余回合);
if (pd != -1 & pd > 0){



                if (cm.getPlayer().getCSPoints(1) < 3000) {
                    cm.sendOk("你的点卷不足!");
                    cm.dispose();
                    return;
                }
				else {
                    var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                    var statup = new java.util.ArrayList();
                    item.setUpgradeSlots((item.getUpgradeSlots() + 1));
                    Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                    Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                    cm.gainNX(-3000);
                    cm.sendOk("升级成功！");
	                cm.worldMessage(6,"玩家["+cm.getName()+"]使用3000点卷给装备升级了1个次数,大家一起恭喜他(她)!");
                    cm.dispose();
                    }			
			   
            }
		else if (pd == 0){ cm.sendOk("已到达上限，无法强化！");cm.dispose();}

}


             if (fstype == 14) {
var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();

var id = item.getItemId();
var 已升级次数= item.getLevel();
var 剩余回合 = item.getUpgradeSlots();
var 强化上限 = 120;
var pd = 强化上限 - (已升级次数 + 剩余回合);
if (pd != -1 & pd > 0){


		
                if (chance == 1) {
                    var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                    var statup = new java.util.ArrayList();
                    item.setUpgradeSlots((item.getUpgradeSlots() + 1));
                  // item.setLocked(1);
 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                    Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);           
	                cm.gainNX(-1000);
                    cm.sendOk("升级成功！");
	                cm.worldMessage(6,"玩家["+cm.getName()+"]使用点券给装备增加了1次砸卷次数,大家一起恭喜他(她)!");
                    cm.dispose();
			}
			else {
                    cm.gainMeso(-500000);
                    cm.sendOk("真遗憾，升级失败，扣除手续费500000金币");
					cm.dispose();
                }
			}
		else if (pd <= 0){ cm.sendOk("已到达上限，无法强化！");cm.dispose();}
}
			}
			}
			}