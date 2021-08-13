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
            text += "#e#r这里是固定副本奖励兑换处.通关副本，获得的副本蛋，累计可兑换当前副本固定奖励.请点击查看.\r\n\r\n"//3			
            text += "#L1##e#d#v4170013#月妙  副本蛋固定奖励#l\r\n"//3
            text += "#L2##e#d#v4170002#废气  副本蛋固定奖励#l\r\n"//3
            text += "#L3##e#d#v4170005#玩具  副本蛋固定奖励#l\r\n"//3
          //  text += "#L5##e#d#v4170009#海盗  副本蛋固定奖励#l\r\n"//3
          //  text += "#L7##e#d#v4170006#天空  副本蛋固定奖励#l\r\n"//3
            text += "#L4##e#d#v4170017#罗密欧副本蛋固定奖励#l\r\n"//3
            text += "#L6##e#d#v4170001#毒雾  副本蛋固定奖励#l\r\n"//3
		//	text += "#L8##e#d#v4002000#绿蜗牛邮票兑换（废气副本产出）#l\r\n"//3
		//	text += "#L9##e#d#v4002001#蓝蜗牛邮票兑换（玩具副本产出）#l\r\n"//3
		//	text += "#L10##e#d#v4002003#绿水灵邮票兑换（毒雾副本产出）#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9310084, 21);
        } else if (selection == 2) {
		cm.openNpc(9310084, 22);
        } else if (selection == 3) {
		cm.openNpc(9310084, 23);
        } else if (selection == 4) {
		cm.openNpc(9310084, 24);
        } else if (selection == 5) {
		cm.openNpc(9310084, 25);
        } else if (selection == 6) {
		cm.openNpc(9310084, 26);
        } else if (selection == 7) {
		cm.openNpc(9310084, 7);
		} else if (selection == 8) {
		cm.openNpc(9310084, 8);
		} else if (selection == 9) {
		cm.openNpc(9310084, 9);
		} else if (selection == 10) {
		cm.openNpc(9310084, 10);
        } else if (selection == 26) {
		cm.openNpc(9310084, 13);
	}
    }
}


