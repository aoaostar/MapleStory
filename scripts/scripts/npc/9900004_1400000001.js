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
			var a5 = "#L5#" + 红色箭头 + " #r四维+2攻击力/魔法力+1的#v1032205##l\r\n\r\n"+圆形+"100个#v4000000# 100个#v4000016# 100个#v4000019#\r\n";//0
            var a1 = "#L1#" + 红色箭头 + " #r四维+4攻击力/魔法力+2的#v1032206##l\r\n\r\n"+圆形+"1个#v1032205#200个#v4000039#200个#v4000020#200个#v4000178#200W#v2140002#\r\n";//0
            var a2 = "#L2#" + 红色箭头 + " #r四维+6攻击力/魔法力+3的#v1032207##l\r\n\r\n"+圆形+"1个#v1032206# 300个#v4000051# 300个#v4000048# 300个#v4000052# \r\n 3个#v4002003# 3个#v4002001# 3个#v4002002# 3个#v4000463# 6个#v4031196#300W#v2140002#\r\n";//0
            var a3 = "#L3#" + 红色箭头 + " #r四维+8攻击力/魔法力+4的#v1032208##l\r\n\r\n"+圆形+"1个#v1032207# 400个#v4000233# 400个#v4000232# 400个#v4000234# \r\n 4个#v4002003# 4个#v4002001# 4个#v4002002# 4个#v4000463# 8个#v4031196#400W#v2140002#\r\n";//0
            var a4 = "#L4#" + 红色箭头 + " #r四维+10攻击力/魔法力+5的#v1032209##l\r\n\r\n"+圆形+"1个#v1032208# 500个#v4000453# 500个#v4000448# 500个#v4000458# \r\n 5个#v4002003# 5个#v4002001#5个#v4002002#5个#v4000463# 10个#v4031196#500W#v2140002#\r\n";//0
			var a7 = "#L7#" + 红色箭头 + " #r四维+30攻击力/魔法力+30的#v1032219##l\r\n\r\n"+圆形+"1个#v1032209# 10个#v4030002# 10个#v4030003# 10个#v4030004# \r\n 10个#v4030005# 10个#v4030006#10个#v4030007#10个#v4030008#1500W#v2140002#\r\n";//0
            var a6 = "#L6#" + 红色箭头 + " #r装备强化系统" + 感叹号 + "\r\n\r\n";
            cm.sendSimple("这里是装备区，有升级装备和制作：\r\n"+a1+"");
        } else if (selection == 5) {//帽子
			if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100)) {
				cm.gainItem(4000000,-100);
				cm.gainItem(4000016,-100);
				cm.gainItem(4000019,-100);
				 var ii = MapleItemInformationProvider.getInstance();		                
				var type = ii.getInventoryType(1032205); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032205)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(2);
				toDrop.setDex(2);
				toDrop.setInt(2);
				toDrop.setLuk(2);
				toDrop.setWatk(1);
				toDrop.setMatk(1);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中r
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			    cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1032205#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
	    } else if (selection == 1) {//帽子
			if (cm.haveItem(4000039, 200)&&cm.haveItem(4000020, 200)&&cm.haveItem(4000178, 200)&&cm.haveItem(1032205, 1)&& cm.getMeso() > 2000000&&!cm.getInventory(1).isFull()) {//
				cm.gainItem(4000039, -200);
				cm.gainItem(4000020, -200);
				cm.gainItem(4000178, -200);
				cm.gainItem(1032205, -1);
				cm.getPlayer().gainMeso(-2000000, true);
				var ii = MapleItemInformationProvider.getInstance();              
				var type = ii.getInventoryType(1032206); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032206)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(4);
				toDrop.setDex(4);
				toDrop.setInt(4);
				toDrop.setLuk(4);
				toDrop.setWatk(2);
				toDrop.setMatk(2);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
				cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1032206#已经制作好了，去试一下吧");
				cm.completeQuest(1400000001);
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
	    } else if (selection == 2) {//套装
			if (cm.haveItem(1032206, 1)&&cm.haveItem(4000051, 300)&&cm.haveItem(4000048, 300)&&cm.haveItem(4000052, 300)&&cm.haveItem(4002003, 3)&&cm.haveItem(4002001, 3)&&cm.haveItem(4002002, 3)&&cm.haveItem(4000463, 3)&&cm.haveItem(4031196, 6)&& cm.getMeso() > 3000000&&!cm.getInventory(1).isFull()) {
				cm.gainItem(1032206, -1);
				cm.gainItem(4000051, -300);
				cm.gainItem(4000048, -300);
				cm.gainItem(4000052, -300);
				cm.gainItem(4002003, -3);
				cm.gainItem(4002001, -3);
				cm.gainItem(4002002, -3);
				cm.gainItem(4000463, -3);
				cm.gainItem(4031196, -6);
				cm.getPlayer().gainMeso(-3000000,true);
				 var ii = MapleItemInformationProvider.getInstance();	                
				var type = ii.getInventoryType(1032207); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032207)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(6);
				toDrop.setDex(6);
				toDrop.setInt(6);
				toDrop.setLuk(6);
				toDrop.setWatk(3);
				toDrop.setMatk(3);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
				cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1012189#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
	    } else if (selection == 3) {//手套
			if (cm.haveItem(1032207, 1)&&cm.haveItem(4000233, 400)&&cm.haveItem(4000232, 400)&&cm.haveItem(4000234, 400)&&cm.haveItem(4002003, 4)&&cm.haveItem(4002001, 4)&&cm.haveItem(4002002, 4)&&cm.haveItem(4000463, 4)&&cm.haveItem(4031196, 8)&& cm.getMeso() > 4000000&&!cm.getInventory(1).isFull()) {
				cm.gainItem(1032207, -1);
				cm.gainItem(4000233, -400);
				cm.gainItem(4000232, -400);
				cm.gainItem(4000234, -400);
				cm.gainItem(4002003, -4);
				cm.gainItem(4002001, -4);
				cm.gainItem(4002002, -4);
				cm.gainItem(4000463, -4);
				cm.gainItem(4031196, -8);
				cm.getPlayer().gainMeso(-4000000,true);
				 var ii = MapleItemInformationProvider.getInstance();		                
				var type = ii.getInventoryType(1032208); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032208)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(8);
				toDrop.setDex(8);
				toDrop.setInt(8);
				toDrop.setLuk(8);
				toDrop.setWatk(4);
				toDrop.setMatk(4);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
				cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1032208#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
	    } else if (selection == 4) {//鞋子
			if (cm.haveItem(1032208, 1)&&cm.haveItem(4000453, 500)&&cm.haveItem(4000448, 500)&&cm.haveItem(4000458, 500)&&cm.haveItem(4002003, 5)&&cm.haveItem(4002001, 5)&&cm.haveItem(4002002, 5)&&cm.haveItem(4000463, 5)&&cm.haveItem(4031196, 10)&& cm.getMeso() > 5000000&&!cm.getInventory(1).isFull()) {
				cm.gainItem(1032208, -1);
				cm.gainItem(4000453, -500);
				cm.gainItem(4000448, -500);
				cm.gainItem(4000458, -500);
				cm.gainItem(4002003, -5);
				cm.gainItem(4002001, -5);
				cm.gainItem(4002002, -5);
				cm.gainItem(4000463, -5);
				cm.gainItem(4031196, -10);
				cm.getPlayer().gainMeso(-5000000,true);
				 var ii = MapleItemInformationProvider.getInstance();		                
				var type = ii.getInventoryType(1032209); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032209)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(10);
				toDrop.setDex(10);
				toDrop.setInt(10);
				toDrop.setLuk(10);
				toDrop.setWatk(5);
				toDrop.setMatk(5);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
				cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1032209#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
			} else if (selection == 7) {//鞋子
			if (cm.haveItem(1032209, 1)&&cm.haveItem(4032202, 10)&&cm.haveItem(4032203, 10)&&cm.haveItem(4032204, 10)&&cm.haveItem(4032205, 10)&&cm.haveItem(4032206, 10)&&cm.haveItem(4032207, 10)&&cm.haveItem(4032208, 10)&& cm.getMeso() > 15000000&&!cm.getInventory(1).isFull()) {
				cm.gainItem(1032209, -1);
				cm.gainItem(4032202, -10);
				cm.gainItem(4032203, -10);
				cm.gainItem(4032204, -10);
				cm.gainItem(4032205, -10);
				cm.gainItem(4032206, -10);
				cm.gainItem(4032207, -10);
				cm.gainItem(4032208, -10);
				cm.getPlayer().gainMeso(-15000000,true);
				 var ii = MapleItemInformationProvider.getInstance();		                
				var type = ii.getInventoryType(1032219); //获得装备的类形
				var toDrop = ii.randomizeStats(ii.getEquipById(1032219)).copy(); // 生成一个Equip类 
				toDrop.setFlag(1);	
				toDrop.setStr(30);
				toDrop.setDex(30);
				toDrop.setInt(30);
				toDrop.setLuk(30);
				toDrop.setWatk(30);
				toDrop.setMatk(30);
				cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
				cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
				cm.getChar().saveToDB(false, false);
				cm.sendOk("#z1032219#已经制作好了，去试一下吧");
				cm.dispose();
				return;
			} else {
				cm.sendOk("你的材料不足!!!");
				cm.dispose();
				return;
			}
        }
    }
}
