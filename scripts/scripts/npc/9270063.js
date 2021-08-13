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
		var Editing = true //true=显示;false=开始活动
          if(Editing){
          cm.sendOk("暂停运作");
          cm.dispose();
          return;
        } 
			cm.sendSimple("#b欢迎玩家 #r#h ##k ,\r\n祝你中秋节快乐哦，收集月亮的照片#r#i2210017##k.\r\n#r#e-确保背包空间足够的情况下进行兑换-#n" +
            "#k\r\n#L101##r#i2210017##bx100#r换#b 神秘箱子 #i2022336#x2\r\n#L102##r#i2210017##bx100#r换#b双倍经验狩猎#i2450000#x3\r\n#L103##r#i2210017##bx100#r换#b孙子兵法#i2370000#x1\r\n#L104##r#i2210017##bx100#r换#b枫叶镖#i2070011#x1\r\n#L105##r#i2210017##bx100#r换#b黄金蛋召唤包#i2101022#x1\r\n#L107##r#i2210017##bx100#r换#b满月椅#i3010164#x1\r\n#L108##r#i2210017##bx100#r换#b豆豆票#i4110010#x120\r\n#L106##r#i2210017##bx400#r换#b国庆纪念币#i4000463#x1\r\n#L109##r#i2210017##bx500#r换#b红玫瑰戒指#i1112252#x1#i1112141#x1");
        } else if (status == 1) {
            
            if (selection == 101) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(2022336, 2);
                    cm.sendOk("获得#i2022336# x2");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有足够的#i2210017#,请在次确认");
                    cm.dispose();
                }
            } else if (selection == 102) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(2450000, 3);
                    cm.sendOk("获得#i2450000#x3");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 103) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(2370000, 1);
                    cm.sendOk("获得#i2370000#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 104) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(2070011, 1);
                    cm.sendOk("获得#i2070011#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 105) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(2101022, 1);
                    cm.sendOk("获得#i2101022#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 106) {
                if (cm.haveItem(2210017, 400) ) {
                    cm.gainItem(2210017, -400);
                    cm.gainItem(4000463, 1);
                    cm.sendOk("获得#i4000463#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 107) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(3010164, 1);
                    cm.sendOk("获得#i3010164#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 108) {
                if (cm.haveItem(2210017, 100) ) {
                    cm.gainItem(2210017, -100);
                    cm.gainItem(4110010, 120);
                    cm.sendOk("获得#i4110010#x120");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }else if (selection == 109) {
                if (cm.haveItem(2210017, 500) ) {
                    cm.gainItem(2210017, -500);
                    cm.gainItem(1112252, 1);
					cm.gainItem(1112141, 1);
                    cm.sendOk("获得#i1112252#x1#i1112141#x1");
                    cm.dispose();
                } else {
                    cm.sendOk("您身上没有#i2210017#,请在次确认");
                    cm.dispose();
				}
			 }
        }
    }
}

	