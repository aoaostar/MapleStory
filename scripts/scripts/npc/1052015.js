/*
 By 梓条
 */

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		var Editing = false//true=显示;false=开始活动
          if(Editing){
          cm.sendOk("国庆节还没开始呢.");
          cm.dispose();
          return;
        } 
			cm.sendSimple("#b欢迎玩家 #r#h ##k ,\r\n国庆节快乐，这里是活动兑换NPC\r\n\r\n#n"+
            "#k\r\n#L99##r#i4030002##bx10#r换#b 超级药水 #i2000005#x3#k\r\n\r\n#L100##r#i4030002##r#i4030003##bx50#r换#b 神秘箱子 #i2022336#x2#k\r\n\r\n#L101##r#i4030002##r#i4030003##r#i4030004##bx100#r换#b 神秘箱子 #i2022336#x6\r\n\r\n#L102##r#i4030002##r#i4030003##r#i4030004##i4030005##bx50#r换#b 高级白衣卷 #i2049002#x1\r\n\r\n#L103##r#i4030002##r#i4030003##r#i4030004##i4030005##i4030006##bx100#r换#b 祥龙披风 #i1102248#x1\r\n#L104##r#i4030002##r#i4030003##r#i4030004##i4030005##i4030006##bx200#r换#b 贵族披风 #i1102163#x1\r\n#L105##r#i4030007##bx10#r换#b 金杯 #i4000038#x20\r\n#L106##r#i4030007##bx5#r换#b 高级鱼饵 #i2300001#x100\r\n#L107##r#i4030008##bx5#r换#b 黄金猪猪 #i4032226#x2#k");
        } else if (status == 1) {
            
			if (selection == 99) {
                if(cm.haveItem(4030002, 10)) {
                    cm.gainItem(4030002, -10);
                    cm.gainItem(2000005, 3);
                    cm.sendOk("获得超级药水 x3");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
					 }
                }if (selection == 100) {
                if(cm.haveItem(4030002, 50) && cm.haveItem(4030003,50)) {
                    cm.gainItem(4030002, -50);
					cm.gainItem(4030003, -50);
                    cm.gainItem(2022336, 2);
                    cm.sendOk("获得神秘箱子 x2");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 101) {
                if(cm.haveItem(4030002, 100) && cm.haveItem(4030003,100) && cm.haveItem(4030004,100)) {
                    cm.gainItem(4030002, -100);
					cm.gainItem(4030003, -100);
					cm.gainItem(4030004, -100);
                    cm.gainItem(2022336, 6);
                    cm.sendOk("获得神秘箱子 x6");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 102) {
                if(cm.haveItem(4030002, 50) && cm.haveItem(4030003,50) && cm.haveItem(4030004,50)&& cm.haveItem(4030005,50)) {
                    cm.gainItem(4030002, -50);
					cm.gainItem(4030003, -50);
					cm.gainItem(4030004, -50);
					cm.gainItem(4030005, -50);
                    cm.gainItem(2049002, 1);
                    cm.sendOk("获得高级白医卷 x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 103) {
                if(cm.haveItem(4030002, 100) && cm.haveItem(4030003,100) && cm.haveItem(4030004,100)&& cm.haveItem(4030005,100)&& cm.haveItem(4030006,100)) {
                    cm.gainItem(4030002, -100);
					cm.gainItem(4030003, -100);
					cm.gainItem(4030004, -100);
					cm.gainItem(4030005, -100);
					cm.gainItem(4030006, -100);
                    cm.gainItem(1102248, 1);
                    cm.sendOk("获得祥龙披风 x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 104) {
                if(cm.haveItem(4030002, 200) && cm.haveItem(4030003,200) && cm.haveItem(4030004,200)&& cm.haveItem(4030005,200)&& cm.haveItem(4030006,200)) {
                    cm.gainItem(4030002, -200);
					cm.gainItem(4030003, -200);
					cm.gainItem(4030004, -200);
					cm.gainItem(4030005, -200);
					cm.gainItem(4030006, -200);
                    cm.gainItem(1102163, 1);
                    cm.sendOk("获得贵族披风 x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 105) {
                if(cm.haveItem(4030007, 10)) {
                    cm.gainItem(4030007, -10);
                    cm.gainItem(4000038, 20);
                    cm.sendOk("获得金杯 x20");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 106) {
                if(cm.haveItem(4030007, 5)) {
                    cm.gainItem(4030007, -5);
                    cm.gainItem(2300001, 100);
                    cm.sendOk("获得高级鱼饵 x100");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
				} if (selection == 107) {
                if(cm.haveItem(4030008, 5)) {
                    cm.gainItem(4030008, -5);
                    cm.gainItem(4032226, 2);
                    cm.sendOk("获得黄金猪 x2");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的物品,请在次确认");
                    cm.dispose();
                }
			 }
        }
    }
}

	