status = -1;
var itemList = Array(
//-------耳环-------


			

//Array(2340000, 200, 1, 1), //祝福卷轴
///Array(2340000, 200, 2, 1), //祝福卷轴
//Array(2340000, 150, 3, 1),//祝福卷轴
//Array(2340000, 200, 1, 1), //祝福卷轴
//Array(2340000, 200, 2, 1), //祝福卷轴
//Array(2340000, 150, 3, 1),//祝福卷轴
//Array(2049124, 200, 1, 1), //正向卷轴
//Array(2049124, 200, 2, 1), //正向卷轴
//Array(2049124, 150, 3, 1), //正向卷轴
//Array(2049124, 200, 1, 1), //正向卷轴
//Array(2049124, 200, 2, 1), //正向卷轴
//Array(2049124, 150, 3, 1), //正向卷轴
//Array(2614015, 200, 1, 1), //破功石50%
//Array(2614015, 200, 2, 1), //破功石50%
//Array(2614015, 150, 3, 1), //破功石50%
//Array(2614015, 200, 1, 1), //破功石50%
//Array(2614015, 200, 2, 1), //破功石50%
//Array(2614015, 150, 3, 1), //破功石50%
//Array(4000464, 200, 1, 1), //中国心
//Array(4000464, 200, 2, 1), //中国心
//Array(4000464, 150, 3, 1), //中国心
//Array(4000464, 200, 1, 1), //中国心
//Array(4000464, 200, 2, 1), //中国心
//Array(4000464, 150, 3, 1), //中国心
//Array(2002033, 200, 1, 1), //巧克力蛋糕
//Array(2002033, 200, 2, 1), //巧克力蛋糕
//Array(2002033, 150, 3, 1), //巧克力蛋糕
//Array(2022035, 200, 1, 1), //
//Array(2022035, 200, 2, 1), //
//Array(2022035, 150, 3, 1)，//
Array(2020031, 200, 1, 1), //可乐
//Array(2020031, 200, 2, 1), //可乐
//Array(2020031, 150, 3, 1), //可乐
//Array(2022002, 200, 1, 1), //雪碧
//Array(2022002, 200, 2, 1), //雪碧
//Array(2022002, 150, 3, 1), //雪碧
//Array(2002034, 200, 1, 1), //水果鲜奶蛋糕
//Array(2002034, 200, 2, 1), //水果鲜奶蛋糕
//Array(2002034, 150, 3, 1), //水果鲜奶蛋糕
//Array(2010006, 200, 1, 1), //蜂蜜罐
//Array(2010006, 200, 2, 1), //蜂蜜罐
//Array(2010006, 150, 3, 1), //蜂蜜罐
//Array(2002031, 200, 1, 1), //草莓蛋糕
//Array(2002031, 200, 2, 1), //草莓蛋糕
//Array(2002031, 150, 3, 1), //草莓蛋糕
//Array(2022279, 200, 5, 1), //鲜奶蛋糕
//Array(2022279, 200, 10, 1), //鲜奶蛋糕
//Array(2022279, 150, 20, 1), //鲜奶蛋糕
//Array(2002023, 200, 1, 1), //正向混沌卷轴
//Array(2002023, 200, 2, 1), //
//Array(2002023, 150, 3, 1) //
//Array(2020007, 200, 1, 1), //鱼干
//Array(2020007, 200, 2, 1), //鱼干
//Array(2020007, 150, 3, 1), //鱼干
Array(2022057, 200, 1, 1), //豆腐
//Array(2022057, 200, 1, 1), //豆腐
//Array(2022057, 150, 1, 1), //豆腐
//Array(2460005, 200, 1, 1), //超级放大镜
//Array(2460005, 200, 2, 1), //超级放大镜
//Array(2460005, 200, 3, 1), //超级放大镜
//Array(2460005, 200, 4, 1),	//超级放大镜
//Array(2460005, 200, 5, 1), //超级放大镜
//Array(2460005, 200, 6, 1),//超级放大镜
//Array(2460005, 200, 7, 1), //超级放大镜
//Array(2460005, 200, 8, 1),//超级放大镜
//Array(2460005, 200, 9, 1), //超级放大镜
//Array(2460005, 150, 10, 1)//超级放大镜

//Array(2022468, 200, 2, 1), //神秘箱子
//Array(2022468, 200, 3, 1), //神秘箱子
//Array(2022468, 150, 4, 1),//神秘箱子

Array(4001126, 10, 1, 1)

//Array(2022070, 200, 1, 1),
//Array(2022070, 200, 2, 1),
//Array(2022070, 200, 3, 1),

//Array(4310149, 100, 1, 1),
//Array(4310149, 50, 2, 1),
//Array(4310149, 10, 3, 1)






);

function start() {
    action(1, 1, 1);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("再见得把油。");
            cm.dispose();
        }
        status--;
    }
		if (status == 0) {
			if(cm.haveItem(2022468,1)) {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendYesNo("打开1个#v2022468#随机获得各种礼品!\r\n#k当前拥有:#c2022468# 个。 ");
			} else {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendOk("打开1个#v2022468#随机获得各种礼品!\r\n#k当前拥有:#c2022468# 个。 " );
				cm.dispose();
			}
		} else if (status == 1){	
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
            item = cm.gainGachaponItem(itemId, quantity, "吃货礼盒", notice);
			
            if (item != -1) {
				//cm.setmoneyb(-5);
cm.gainItem(2022468, -1);//获得物品
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
				//cm.全服漂浮喇叭("玩家["+cm.getName()+"]小心翼翼的打开了神秘宝箱", 5121018);
            } else {
                cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("打开一看，箱子里什么都没有~");
			cm.gainItem(2022468, -1);//获得物品
           // cm.setmoneyb(-5);
//cm.gainItem(4310030, 5);//获得物品
			//cm.gainNX(1000);	//加减点券
            cm.safeDispose();
        }
    }
}