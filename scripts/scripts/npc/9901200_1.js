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
            text += "#e#r你好！在我这里可以帮你制作你所需要的武器，以下是我可以为您制作的武器列表.\r\n\r\n#b黄金枫叶武器可升级次数都是#r+9#b哦\r\n\r\n"//3
            text += "#L1##e#d#v1302142##z1302142#黄金枫叶单手剑制作.\r\n"//3
            text += "#L2##e#d#v1332114##z1332114#黄金枫叶短刀制作#l\r\n"//3
            text += "#L3##e#d#v1372071##z1372071#黄金枫叶短杖制作#l\r\n"//3
            text += "#L4##e#d#v1382093##z1382093#黄金枫叶长杖制作#l\r\n"//3
            text += "#L5##e#d#v1402085##z1402085#黄金枫叶双手剑制作#l\r\n"//3
            text += "#L6##e#d#v1432075##z1432075#黄金枫叶枪制作#l\r\n"//3
            text += "#L7##e#d#v1442104##z1442104#黄金枫叶开山斧制作#l\r\n"//3
            text += "#L8##e#d#v1452100##z1452100#黄金枫叶弓制作#l\r\n"//3
            text += "#L9##e#d#v1462085##z1462085#黄金枫叶弩制作#l\r\n"//3
            text += "#L10##e#d#v1472111##z1472111#黄金枫叶拳套制作#l\r\n"//3
            text += "#L11##e#d#v1312056##z1312056#黄金枫叶单手斧制作#l\r\n"//3
            text += "#L12##e#d#v1322084##z1322084#黄金枫叶单手锤制作#l\r\n"//3
            text += "#L13##e#d#v1412055##z1412055#黄金枫叶双手斧制作#l\r\n"//3
            text += "#L14##e#d#v1422057##z1422057#黄金枫叶双手锤制作#l\r\n"//3
            //text += "#L15##e#d#v1482073#黄金枫叶指节制作#l\r\n"//3
            text += "#L16##e#d#v1492073##z1492073#黄金枫叶短枪制作#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9900004, 31);
        } else if (selection == 2) {
		cm.openNpc(9900004, 32);
        } else if (selection == 3) {
		cm.openNpc(9900004, 33);
        } else if (selection == 4) {
		cm.openNpc(9900004, 34);
        } else if (selection == 5) {
		cm.openNpc(9900004, 35);
        } else if (selection == 6) {
		cm.openNpc(9900004, 36);
        } else if (selection == 7) {
		cm.openNpc(9900004, 37);
        } else if (selection == 8) {
		cm.openNpc(9900004, 38);
        } else if (selection == 9) {
		cm.openNpc(9900004, 39);
        } else if (selection == 10) {
		cm.openNpc(9900004, 310);
        } else if (selection == 11) {
		cm.openNpc(9900004, 311);
        } else if (selection == 12) {
		cm.openNpc(9900004, 312);
        } else if (selection == 13) {
		cm.openNpc(9900004, 313);
        } else if (selection == 14) {
		cm.openNpc(9900004, 314);
        } else if (selection == 15) {
		cm.openNpc(9900004, 315);
        } else if (selection == 16) {
		cm.openNpc(9900004, 316);
	}
    }
}


