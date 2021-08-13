function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#r你好！只要你有装备租赁卷，即可在我这里租赁装备.\r\n\r\n#b使用时间:#r3小时使用权\r\n"//3
            text += "#L1##e#d我要购买#v5220007#\t需要：点卷=200  时间:3小时使用权#l\r\n\r\n"//3
            text += "#L2##e#d#v1302071#粉色花边游泳圈（单手剑）  3小时使用权#l\r\n"//3
            text += "#L3##e#d#v1312034#粉色花边游泳圈（单手斧）  3小时使用权#l\r\n"//3
            text += "#L4##e#d#v1402041#粉色花边游泳圈（双手剑）  3小时使用权#l\r\n"//3
            text += "#L5##e#d#v1432042#粉色花边游泳圈（枪）      3小时使用权#l\r\n"//3
            text += "#L6##e#d#v1442053#粉色花边游泳圈（矛）      3小时使用权#l\r\n"//3
            text += "#L7##e#d#v1382042#粉色花边游泳圈（长杖）    3小时使用权#l\r\n"//3
            text += "#L8##e#d#v1452048#粉色花边游泳圈（弓）      3小时使用权#l\r\n"//3
            text += "#L9##e#d#v1462043#粉色花边游泳圈（弩）      3小时使用权#l\r\n"//3
            text += "#L10##e#d#v1332059#粉色花边游泳圈（短刀）    3小时使用权#l\r\n"//3
            text += "#L11##e#d#v1472058#粉色花边游泳圈（拳套）    3小时使用权#l\r\n"//3
            text += "#L12##e#d#v1492026#粉色花边游泳圈（短枪）    3小时使用权#l\r\n"//3
            text += "#L13##e#d#v1482025#粉色花边游泳圈（指节）    3小时使用权#l\r\n"//3

            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000018, 1);
        } else if (selection == 2) {
		cm.openNpc(9000018, 2);
        } else if (selection == 3) {
		cm.openNpc(9000018, 3);
        } else if (selection == 4) {
		cm.openNpc(9000018, 4);
        } else if (selection == 5) {
		cm.openNpc(9000018, 5);
        } else if (selection == 6) {
		cm.openNpc(9000018, 6);
        } else if (selection == 7) {
		cm.openNpc(9000018, 7);
        } else if (selection == 8) {
		cm.openNpc(9000018, 8);
        } else if (selection == 9) {
		cm.openNpc(9000018, 9);
        } else if (selection == 10) {
		cm.openNpc(9000018, 10);
        } else if (selection == 11) {
		cm.openNpc(9000018, 11);
        } else if (selection == 12) {
		cm.openNpc(9000018, 12);
        } else if (selection == 13) {
		cm.openNpc(9000018, 13);
	}
    }
}


