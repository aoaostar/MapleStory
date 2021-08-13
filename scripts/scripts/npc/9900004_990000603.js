importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);

var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
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
			if (cm.getInventory(1).isFull()){
            cm.sendOk("#b请保证装备栏位至少有1个空格,否则无法合成.");
            cm.dispose();
			return;
			}
            var a1 = "#L1#" + 红色箭头 + " #r1000血量的#v1012188##l\r\n\r\n"+圆形+"\r\n";//0
            var a6 = "#L6#" + 红色箭头 + " #r装备强化系统" + 感叹号 + "\r\n\r\n";
            cm.sendSimple("这里是装备区，有升级装备和制作：\r\n"+a5+""+a1+""+a2+""+a3+""+a4+"");
        } else if (selection == 5) {//帽子
			if (cm.haveItem(4000313, 999) && cm.haveItem(4002001, 20) && cm.haveItem(4002002, 15) && cm.haveItem(4002003, 8)) {
				cm.gainItem(4000313, -999);
				cm.gainItem(4002001,-20);
				cm.gainItem(4002002,-15);
				cm.gainItem(4002003,-8);
				 var ii = MapleItemInformationProvider.getInstance();		                
				var type = ii.getInventoryType(1012187); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1012187)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setHp(500);
				toDrop.setMp(500);
				
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中r
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			    cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1012187#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}

			}
        }
			//		toDrop.setFlag(1);	
			//toDrop.setMatk(10);
			//toDrop.setWatk(10);
		//	toDrop.setLuk(10);
			//toDrop.setInt(10);
			//toDrop.setDex(10);
		//	toDrop.setHp(150);
		//	toDrop.setMp(150);
		//	toDrop.setWdef(25);
		//	toDrop.setMdef(25);
    }
}
