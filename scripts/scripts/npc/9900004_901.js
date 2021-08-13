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
        }var MC = cm.getServerName();
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 红色箭头 + " #b制作#v1082145# 需要#v1082002#*3+#v4002000#*20+#金币200W#l\r\n\r\n";
            var a2 = "#L2#" + 红色箭头 + " #b制作#v1142287# 需要#v1142441#一个+#v4000013#+#v4000023#+#v4000007#各100个#l\r\n\r\n";
            var a3 = "#L3#" + 红色箭头 + " #b制作#v1142075# 需要#v1142287#一个+#v4000046#+#v4000028#各200个#l\r\n\r\n";
            var a4 = "#L4#" + 红色箭头 + " #b制作#v1142746# 需要+#v4000463#10个+#v1142075#一个+#v4000273#+#v4000267#+#v4000180#各200个#l\r\n\r\n";
            var a5 = "#L5#" + 红色箭头 + " #b制作#v1142286# 需要#v4000463#30个+#v1142746#一个+#v4001085#+#v4001084#+#v4001083#各一个#l\r\n\r\n";
            var a6 = "#L6#" + 红色箭头 + " #b制作#v1142745# 需要#v4000463#50个+#v1142286#一个+#v1122000#+#v1002926#+#v1002357#各一个#l\r\n\r\n";



            cm.sendSimple("#e#r#k#n勋章制作：\r\n\r\n请选择：\r\n" + a1 );
        } else if (status == 1) {
		
	    if (cm.getInventory(1).isFull()){
                        cm.sendOk("#b请保证装备栏位至少有2个空格,否则无法合成.");
                        cm.dispose();
			return;
            }
            if (selection == 1) {//红
				if(cm.haveItem(1082002,3) && cm.haveItem(4002000,20) && cm.getMeso() >= 2000000){
					cm.gainItem(1082002,-3);
					cm.gainItem(4002000,-20);
					cm.gainItem(4000012,-50);
					cm.gainMeso(-2000000);

                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1082145);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1082145)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(20);
						toDrop.setDex(20);
						toDrop.setInt(20);
						toDrop.setLuk(20);
                        toDrop.setWatk(10);
						toDrop.setMatk(10);					
						toDrop.setHp(100);
						toDrop.setMp(100);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("手套已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 2) {//红
				if( cm.haveItem(1142441, 1) && cm.haveItem(4000013,100) && cm.haveItem(4000023,100) && cm.haveItem(4000007,100) ){
					cm.gainItem(1142441,-1);
					cm.gainItem(4000013,-100);
					cm.gainItem(4000023,-100);
					cm.gainItem(4000007,-100);
                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1142287);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1142287)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(10);
						toDrop.setDex(10);
						toDrop.setInt(10);
						toDrop.setLuk(10);
                        			toDrop.setWatk(5);
						toDrop.setMatk(5);
						toDrop.setSpeed(5);
						toDrop.setHp(100);
						toDrop.setMp(100);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("勋章已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 3) {//红
				if(cm.haveItem(1142287, 1) && cm.haveItem(4000046,200) && cm.haveItem(4000028,200) ){
					cm.gainItem(1142287,-1);
					cm.gainItem(4000046,-200);
					cm.gainItem(4000028,-200);
                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1142075);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1142075)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(20);
						toDrop.setDex(20);
						toDrop.setInt(20);
						toDrop.setLuk(20);
                        			toDrop.setWatk(10);
						toDrop.setMatk(10);
						toDrop.setSpeed(10);
						toDrop.setHp(300);
						toDrop.setMp(300);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("勋章已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 4) {//红




				if(cm.haveItem(1142075, 1) && cm.haveItem(4000463, 10) && cm.haveItem(4000273,200) && cm.haveItem(4000267,200) && cm.haveItem(4000180,200) ){
					cm.gainItem(1142075,-1);
					cm.gainItem(4000463,-10);
					cm.gainItem(4000273,-200);
					cm.gainItem(4000267,-200);
					cm.gainItem(4000180,-200);
                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1142746);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1142746)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(30);
						toDrop.setDex(30);
						toDrop.setInt(30);
						toDrop.setLuk(30);
                        			toDrop.setWatk(20);
						toDrop.setMatk(20);
						toDrop.setSpeed(20);
						toDrop.setJump(20);
						toDrop.setHp(500);
						toDrop.setMp(500);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("勋章已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 5) {//红
				if(cm.haveItem(1142746, 1) && cm.haveItem(4000463, 30)  && cm.haveItem(4001085,1) && cm.haveItem(4001084,1) && cm.haveItem(4001083,1)){
					cm.gainItem(1142746,-1);
					cm.gainItem(4000463,-30);
					cm.gainItem(4001085,-1);
					cm.gainItem(4001084,-1);
					cm.gainItem(4001083,-1);
                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1142286);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1142286)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(40);
						toDrop.setDex(40);
						toDrop.setInt(40);
						toDrop.setLuk(40);
                        			toDrop.setWatk(40);
						toDrop.setMatk(40);
						toDrop.setSpeed(25);
						toDrop.setJump(25);
						toDrop.setHp(800);
						toDrop.setMp(800);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("勋章已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 6) {//红
				if(cm.haveItem(1142286, 1) && cm.haveItem(4000463, 50)  && cm.haveItem(1122000,1) && cm.haveItem(1002926,1) && cm.haveItem(1002357,1) ){
					cm.gainItem(1142286,-1);
					cm.gainItem(4000463,-50);
					cm.gainItem(1122000,-1);
					cm.gainItem(1002926,-1);
					cm.gainItem(1002357,-1);
                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1142745);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1142745)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(1);
						toDrop.setStr(50);
						toDrop.setDex(50);
						toDrop.setInt(50);
						toDrop.setLuk(50);
                        			toDrop.setWatk(50);
						toDrop.setMatk(50);
						toDrop.setSpeed(30);
						toDrop.setJump(30);
						toDrop.setHp(1000);
						toDrop.setMp(1000);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("勋章已经放入你的背包了!");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }
        }
    }
}
