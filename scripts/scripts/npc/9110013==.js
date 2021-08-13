function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            text += " \t\t\t  #e#d#r欢迎来到童趣冒险岛装备合成系统#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L1##r#e制作帽子#l       #L2#制作鞋子#l\r\n" 
			//text += " #L3##b#e制作项链#l       #L4##b#e制作戒指#l\r\n"
			//text += " #L5##r#e制作手套#l       #L6##r#e制作披风#l \r\n" 
			//text += " #L7##b#e制作脸饰#l       #L8##b#e制作耳环#l \r\n"
			
			cm.sendSimple(text);
			
		} else if (selection == 1) {
            cm.openNpc(9110013, 1);
        
		} else if (selection == 2) {
            cm.openNpc(9110013, 2);

        } else if (selection == 3) {
            cm.openNpc(9110013, 3);

        } else if (selection == 4) {
            cm.openNpc(9110013, 4);
			
			} else if (selection == 5) {
            cm.openNpc(9110013, 5);
			
			} else if (selection == 6) {
            cm.openNpc(9110013, 6);

        } else if (selection == 7) {
            cm.openNpc(9110013, 7);
			
        } else if (selection == 8) {
            cm.openNpc(9110013, 8);
        }
    }
}
