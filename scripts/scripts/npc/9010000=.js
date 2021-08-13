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
            text += "#e#r这里是特殊戒指锻造所：\r\n如果您有可升级锻造的道具，可以查看下面列表，进行锻造。\r\n\r\n"//3
           // text += "#L2##e#d#v1112446# 制作老公老婆戒指LV1.#l\r\n"//3
            text += "#L3##e#d#v1112446# 升级锻造老公老婆戒指.#l\r\n"//3
            //text += "#L4##e#d#v1032060# 锻造阿尔泰耳环.#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000017, 70);
        } else if (selection == 6) {
		cm.openNpc(9310059, 5);
        } else if (selection == 5) {
		cm.openNpc(9310059, 4);
        } else if (selection == 2) {
		cm.openNpc(9000017, 1);
        } else if (selection == 3) {
		cm.openNpc(9000017, 2);
        } else if (selection == 4) {
		cm.openNpc(9000017, 3);
	}
    }
}


