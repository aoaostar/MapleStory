var status = 0;
var itemList =   
Array(   
Array(1102481,100,1,1),//	暴君西亚戴斯披风      战士
Array(1082543,100,1,1),//	暴君西亚戴斯手套
Array(1072743,100,1,1),//	暴君西亚戴斯靴
Array(1132174,100,1,1),//	暴君西亚戴斯腰带

Array(1102482,110,1,1),//	暴君赫尔梅斯披风      法师
Array(1082544,110,1,1),//	暴君赫尔梅斯手套
Array(1072744,110,1,1),//	暴君赫尔梅斯靴	 
Array(1132175,110,1,1),//	暴君赫尔梅斯腰带

Array(1102483,100,1,1),//	暴君凯伦披风          弓箭手
Array(1082545,100,1,1),//	暴君凯伦手套
Array(1072745,100,1,1),//	暴君凯伦靴
Array(1132176,100,1,1),//	暴君凯伦腰带

Array(1102484,100,1,1),//	暴君利卡昂披风        飞侠
Array(1082546,100,1,1),//	暴君利卡昂手套
Array(1072746,100,1,1),//	暴君利卡昂靴
Array(1132177,100,1,1),//	暴君利卡昂腰带	 
	 
Array(1102485,100,1,1),//	暴君阿尔泰披风	      海盗
Array(1082547,100,1,1),//	暴君阿尔泰手套
Array(1072747,100,1,1),//	暴君阿尔泰靴	 
Array(1132178,100,1,1)//        暴君阿尔泰腰带
);	  
function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
           selStr = "#e#r该玩具箱可以获得以下物品，100%获得#g\r\n赶紧嗨起来吧~#n#k\r\n";
		   for (i = 0; i < itemList.length; i++) {
                selStr += "#d#v" + itemList[i][0] + "#";
            }
			
			cm.sendOk(selStr);
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
		selStr = "\r\n";
		   for (i = 0; i < itemList.length; i++) {
                selStr += "#d#v" + itemList[i][0] + "#";
            }
        if (cm.haveItem(4000463, 18)) {
            cm.sendYesNo("欢迎来到暴君抽奖，每次抽奖需要#r18个#v4000463#\r\n"+selStr);
        } else {
            cm.sendOk("欢迎来到暴君抽奖，每次抽奖需要#r18个#v4000463#\r\n"+selStr);
            cm.safeDispose();
        }
    } else if (status == 1) {
        var chance = Math.floor(Math.random() * 200);
        var finalitem = Array();
        for (var i = 0; i < itemList.length; i++) {
            if (itemList[i][1] >= chance) {
                finalitem.push(itemList[i]);
            }
        }
        if (finalitem.length != 0) {
            var item;
            var random = new java.util.Random();
            var finalchance = random.nextInt(finalitem.length);
            var itemId = finalitem[finalchance][0];
            var quantity = finalitem[finalchance][2];
            var notice = finalitem[finalchance][3];
            item = cm.gainGachaponItem(itemId, quantity, "暴君抽奖", notice);
            if (item != -1) {
                cm.gainItem(4000463, -18);
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("你确实有#b#v4000463##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        }
    }
}