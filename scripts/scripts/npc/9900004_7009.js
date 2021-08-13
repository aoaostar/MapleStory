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
            text += " \t\t\t  #e#d#r欢迎来到小宇冒险岛制作装备系统#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L1##r#e制作革命套装#l       #L2#制作革命武器#l\r\n" 
			text += " #L3##b#e制作寻宝套#l         #L4##b#e制作狮子套#l\r\n"
			text += " #L5##r#e制作红色武器#l       #L6##r#e制作布莱克武器#l \r\n" 
			text += " #L7##b#e制作首饰装备#l       #L8##b#e制作巨匠武器#l \r\n"
			
			cm.sendSimple(text);
			
		} else if (selection == 1) {
            cm.openNpc(9900004, 7010);
        
		} else if (selection == 2) {
            cm.openNpc(9900004, 7011);

        } else if (selection == 3) {
            cm.openNpc(9900004, 7012);

        } else if (selection == 4) {
            cm.openNpc(9900004, 7013);
			
			} else if (selection == 5) {
            cm.openNpc(9900004, 7014);
			
			} else if (selection == 6) {
            cm.openNpc(9900004, 7015);

        } else if (selection == 7) {
            cm.openNpc(9900004, 7016);
			
        } else if (selection == 8) {
            cm.openNpc(9900004, 7017);
        }
    }
}
