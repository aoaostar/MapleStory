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
            text += " \t  #e#d在这里可以制作和升级各种强大的装备哦！#n\r\n"
            
            //text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"
            
            //text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"
            
            text += "          \t#L1#血色面巾#l\t#L2#冒险之心#l\r\n\r\n\t#L3#贝勒腰带#l\t#L4#贝勒戒指#l\t#L5#贝勒耳环#l\r\n\r\n"
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 6111);

        } else if (selection == 2) {
            cm.openNpc(9900004, 6112);

        } else if (selection == 3) {
            cm.openNpc(9900004, 6113);

        } else if (selection == 4) {
            cm.openNpc(9900004, 6114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 6115);
        }
    }
}