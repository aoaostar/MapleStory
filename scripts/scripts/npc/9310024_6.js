
status = -1;
var itemList = Array(
Array(2340000, 50, 1, 1), //祝福卷轴
Array(2049100, 50, 1, 1),//混沌卷轴60%
Array(2040801, 400, 1, 1), //手套敏捷卷轴60%
Array(2040802, 600, 1, 1), //手套敏捷卷轴10%
Array(2040804, 400, 1, 1), //手套攻击卷轴60%
Array(2040805, 200, 1, 1), //手套攻击卷轴10%
Array(2040808, 500, 1, 1), //手套敏捷诅咒卷轴70%
Array(2040809, 500, 1, 1), //手套敏捷诅咒卷轴30%
Array(2040810, 100, 1, 1), //手套攻击诅咒卷轴70%
Array(2040811, 100, 1, 1), //手套攻击诅咒卷轴30%
Array(2040814, 500, 1, 1), //手套魔力诅咒卷轴70%
Array(2040815, 500, 1, 1), //手套魔力诅咒卷轴30%
Array(2040816, 500, 1, 1), //手套魔力卷轴10%
Array(2040817, 500, 1, 1), //手套魔力卷轴60%
Array(2040819, 500, 1, 1), //手套敏捷卷轴65%
Array(2040820, 500, 1, 1), //手套敏捷卷轴15%
Array(2040821, 600, 1, 1), //手套攻击卷轴65%
Array(2040822, 600, 1, 1), //手套攻击卷轴15%
Array(2040906, 500, 1, 1), //盾牌运气卷轴70%
Array(2040907, 500, 1, 1), //盾牌运气卷轴30%
Array(2040914, 600, 1, 1), //盾牌攻击卷轴60%
Array(2040915, 600, 1, 1), //盾牌攻击卷轴10%
Array(2040916, 400, 1, 1), //盾牌攻击诅咒卷轴70%
Array(2040917, 200, 1, 1), //盾牌攻击诅咒卷轴30%
Array(2040919, 200, 1, 1), //盾牌魔力卷轴60%
Array(2040920, 600, 1, 1), //盾牌魔力卷轴10%
Array(2040921, 600, 1, 1), //盾牌魔力诅咒卷轴70%
Array(2040922, 400, 1, 1), //盾牌魔力诅咒卷轴30%
Array(2040924, 600, 1, 1), //盾牌运气卷轴60%
Array(2040925, 400, 1, 1), //盾牌运气卷轴10%
Array(2040930, 600, 1, 1), //盾牌力量卷轴70%
Array(2040931, 200, 1, 1), //盾牌力量卷轴60%
Array(2040932, 100, 1, 1), //盾牌力量卷轴30%
Array(2040933, 600, 1, 1), //盾牌力量卷轴10%
Array(2041201, 600, 1, 1), //项链运气卷轴10%
Array(2041202, 600, 1, 1), //项链运气卷轴60%
Array(2041204, 600, 1, 1), //项链运气诅咒卷轴30%
Array(2041205, 600, 1, 1), //项链运气诅咒卷轴70%
Array(2041206, 600, 1, 1), //项链力量卷轴10%
Array(2041207, 600, 1, 1), //项链力量卷轴60%
Array(2041209, 600, 1, 1), //项链力量诅咒卷轴30%
Array(2041210, 600, 1, 1), //项链力量诅咒卷轴70%
Array(2041301, 600, 1, 1), //腰带力量卷轴60%
Array(2041304, 600, 1, 1), //腰带智力卷轴60%
Array(2041307, 600, 1, 1), //腰带敏捷卷轴60%
Array(2041310, 600, 1, 1) //腰带运气卷轴60%

);

function start() {
    action(1, 0, 0);
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
			if(cm.haveItem(4170001,5)) {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendYesNo("消耗5个#v4170001#抽取以下物品!\r\n#k当前拥有:#c4170001# 个。 以下是全部物品:" + str1);
			} else {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendOk("消耗5个#v4170001#抽取以下物品!\r\n#k当前拥有:#c4170001# 个。 以下是全部物品:" + str1);
				cm.dispose();
			}
		} else if (status == 1){	
        var chance = Math.floor(Math.random() * 700);
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
            item = cm.gainGachaponItem(itemId, quantity, "鸣人", notice);
            if (item != -1) {
				//cm.setmoneyb(-5);
cm.gainItem(4170001, -5);//获得物品
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("怎么没接住球啊，算了还你宝石吧。");
           // cm.setmoneyb(-5);
//cm.gainItem(4170001, 5);//获得物品
			//cm.gainNX(1000);	//加减点券
            cm.safeDispose();
        }
    }
}















