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
            text += "#e#b你好！在我这里可以帮你制作你所需要的武器，以下是我可以为您制作的武器列表.\r\n\r\n"//3
		//	text += "#L10##e#d#v4031975#      消耗制造#l\r\n"//3
		//	text += "#L11##e#d#v4031975#      革命制作#l\r\n"//3
        //    text += "#L2##e#d#v4031975#Lv43  枫叶武器制造#l\r\n"//3
        //    text += "#L3##e#d#v4031975#Lv64  枫叶武器制造#l\r\n"//3
        //   text += "#L4##e#d#v4031975#Lv77  黄金枫叶制造#l\r\n"//3
        //    text += "#L5##e#d#v4031975#Lv100 扎昆泊伊兹尼#l\r\n"//3
            text += "#L7##rLv120 永恒武器制造#l\r\n"//3
            text += "#L8#Lv130 伽耶武器制造(天然金色品质武器)#l\r\n"//3
			text += "#L9#Lv135 外星人武器制造(天然金色品质武器)#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000018, 1);
        } else if (selection == 2) {
		cm.openNpc(9000018, 1);
        } else if (selection == 3) {
		cm.openNpc(9000018, 2);
        } else if (selection == 4) {
		cm.openNpc(9000018, 3);
        } else if (selection == 5) {
		cm.openNpc(9000018, 4);
        } else if (selection == 6) {
		cm.openNpc(9000018, 5);
        } else if (selection == 7) {
		cm.openNpc(9000018, 6);
        } else if (selection == 8) {
		cm.openNpc(9000018, 9);
		} else if (selection == 9) {
		cm.openNpc(9000018, 999);
		} else if (selection == 10) {
		cm.openNpc(9000018, 10);
		} else if (selection == 11) {  //革命制作
		cm.openNpc(9900004, 400);
	}
    }
}


