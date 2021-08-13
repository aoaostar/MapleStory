
status = -1;
var itemList = Array(
			Array(2043002,1000, 1, 1), //单手剑攻击卷轴
			Array(2044002,1000, 1, 1), //双手剑攻击卷轴
			Array(2044302,1000, 1, 1), //枪攻击攻击卷轴
			Array(2044402,1000, 1, 1), //矛攻击攻击卷轴
			Array(2044502,1000, 1, 1), //弓攻击攻击卷轴
			Array(2044602,1000, 1, 1), //弩攻击
			Array(2043702,1000, 1, 1), //短杖攻击
			Array(2043802,1000, 1, 1), //长杖攻击
			Array(2043302,1000, 1, 1), //短剑攻击
			Array(2044702,1000, 1, 1), //拳套攻击
			Array(2044902,1000, 1, 1), //短枪攻击
			Array(2044802,1000, 1, 1), //指节攻击
			Array(2040805,1000, 1, 1), //手套攻击
			Array(2043003,800, 1, 1), //单手剑攻击卷轴(必成)
			Array(2044003,800, 1, 1), //双手剑攻击卷轴(必成)
			Array(2044303,800, 1, 1), //枪攻击攻击卷轴(必成)
			Array(2044403,800, 1, 1), //矛攻击攻击卷轴(必成)
			Array(2044503,800, 1, 1), //弓攻击攻击卷轴(必成)
			Array(2044603,800, 1, 1), //弩攻击必成卷
			Array(2043703,800, 1, 1), //短杖攻击必成卷
			Array(2043803,800, 1, 1), //长杖攻击诅轴(必成)
			Array(2043303,800, 1, 1), //短剑攻击必成卷
			Array(2044703,800, 1, 1), //拳套攻击诅轴(必成)
			Array(2044908,800, 1, 1), //短枪攻击卷轴(必成)
			Array(2044815,800, 1, 1), //指节攻击必成卷
			Array(2040807,800, 1, 1), //手套攻击卷轴(必成)
			//Array(2043701,1000, 1, 1), //短杖魔力
			//Array(2043801,1000, 1, 1), //长杖魔力
			Array(2040506,800, 1, 1), //全身铠甲敏捷卷轴(必成)
			//Array(2040303 ,500, 1, 1), //耳环智力必成卷(必成)
			Array(2040710,800, 1, 1) //鞋子跳跃卷轴(必成)

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
			if(cm.haveItem(4001323,5)) {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendYesNo("消耗5个#v4001323#抽取以下物品!\r\n#k当前拥有:#c4001323# 个。 以下是全部物品:" + str1);
			} else {
var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendOk("消耗5个#v4001323#抽取以下物品!\r\n#k当前拥有:#c4001323# 个。 以下是全部物品:" + str1);
				cm.dispose();
			}
		} else if (status == 1){	
        var chance = Math.floor(Math.random() * 500);
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
            item = cm.gainGachaponItem(itemId, quantity, "鸣人红宝石", notice);
            if (item != -1) {
				//cm.setmoneyb(-5);
cm.gainItem(4001323, -5);//获得物品
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("怎么没接住球啊，算了还你宝石吧。");
           // cm.setmoneyb(-5);
//cm.gainItem(4001323, 5);//获得物品
			//cm.gainNX(1000);	//加减点券
            cm.safeDispose();
        }
    }
}















