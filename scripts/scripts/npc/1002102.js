var status = 0;
var itemList =   
Array(   




Array(4032366,500,1,1),//1002102//
Array(4032367,500,1,1),// 
Array(4032368,500,1,1),// 
Array(4032369,500,1,1),// 
Array(4032370,500,1,1),// 
Array(4032371,500,5,1)// 

			//-------第一个是物品ID，第二个是抽奖概率，第三个是抽奖数量,第四个是是否提示抽奖广播(大于1就会广播)
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
            cm.sendOk("箱子抽奖：#v2049100#混沌卷轴/#v2340000#祝福卷轴/#v2000005#超级药水x50.随机抽取其一.");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        if (cm.haveItem(4032366, 1)) {
				var str1 = "";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendAcceptDecline("用#i4032366#即可进行今日奔跑活动。\r\n\r\n#r任务物品展示:#k\r\n" + str1);
			} else {
            var str1 = "\r\n";	
           for (var i = 0; i < itemList.length; i++){
                   str1 += "#v"+itemList[i][0]+"#";
            }
				cm.sendOk("你身上没有#r朦胧的痕迹#k#i4032366#,无法进行奔跑活动。");
				cm.dispose();
			}
    } else if (status == 1) {
        var chance = Math.floor(Math.random() * +1000);
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
            item = cm.gainGachaponItem(itemId, quantity, "每日奔跑", notice);
            if (item != -1) {
                cm.gainItem(4032366, -1);
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("你确实有#b#4110000##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
           cm.sendOk("- 任 务 终 结 -");
		   cm.gainItem(4032366, -1);
            cm.safeDispose();
        }
    }
}