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
            var a1 = "#L1#" + 红色箭头 + " #b制作#v1102040# 需要#v1102041#*1+#v4002000#*50+#v4002001#*30+#v4000463#*50+#v4031456#*50+#v4170007#*10#l\r\n\r\n";
            var a2 = "#L2#" + 红色箭头 + " #b制作#v1102042# 需要#v1102040#*1+#v4002000#*100+#v4002001#*50+#v4000463#*66+#v4031456#*100+#v4170007#*20#l\r\n\r\n";
            var a3 = "#L3#" + 红色箭头 + " #b制作#v1102043# 需要#v1102042#*1+#v4002000#*200+#v4002001#*80+#v4000463#*100+#v4031456#*200+#v4170007#*30#l\r\n\r\n";
           



            cm.sendSimple("#e#r#k#n披风升级：\r\n\r\n请选择：\r\n" + a1 + ""+a2+""+a3+"");
        } else if (status == 1) {
		
	    if (cm.getInventory(1).isFull()){
                        cm.sendOk("#b请保证装备栏位至少有2个空格,否则无法合成.");
                        cm.dispose();
			return;
            }
            if (selection == 1) {//红
				if(cm.haveItem(1102041,1) && cm.haveItem(4002000,50) && cm.haveItem(4002001,30) && cm.haveItem(4000463,50) && cm.haveItem(4031456,50) && cm.haveItem(4170007,10)){
					cm.gainItem(1102041,-1);
					cm.gainItem(4002000,-50);
					cm.gainItem(4002001,-30);
					cm.gainItem(4000463,-50);
					cm.gainItem(4031456,-50);
					cm.gainItem(4170007,-10);
                    cm.gainItem(1102040, 30,30,30,30,0,0,20,20,30,30,10,10,0,0);
					cm.sendOk("兑换成功!");
					cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作了浪人披风（黄）！");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            }else if (selection == 2) {//红
					if(cm.haveItem(1102040,1) && cm.haveItem(4002000,100) && cm.haveItem(4002001,50) && cm.haveItem(4000463,66) && cm.haveItem(4031456,100) && cm.haveItem(4170007,20)){
					cm.gainItem(1102040,-1);
					cm.gainItem(4002000,-100);
					cm.gainItem(4002001,-50);
					cm.gainItem(4000463,-66);
					cm.gainItem(4031456,-100);
					cm.gainItem(4170007,-20);
                    cm.gainItem(1102042, 40,40,40,40,0,0,25,25,35,35,15,15,0,0);
					cm.sendOk("兑换成功!");
					cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作了浪人披风（紫）！");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				
				}
            }else if (selection == 3) {//红
	              if(cm.haveItem(1102042,1) && cm.haveItem(4002000,200) && cm.haveItem(4002001,80) && cm.haveItem(4000463,80) && cm.haveItem(4031456,200) && cm.haveItem(4170007,30)){
					cm.gainItem(1102042,-1);
					cm.gainItem(4002000,-200);
					cm.gainItem(4002001,-80);
					cm.gainItem(4000463,-80);
					cm.gainItem(4031456,-200);
					cm.gainItem(4170007,-30);
                    cm.gainItem(1102043, 50,50,50,50,0,0,30,30,40,40,20,20,0,0);
					cm.sendOk("兑换成功!");
					cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作了顶级浪人披风（褐）！");
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
