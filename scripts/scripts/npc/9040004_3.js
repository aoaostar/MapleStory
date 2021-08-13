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
            text += " \t\t     #e#d你可以在这里兑换各种物品哦#n\r\n"
            
          text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"
            
          text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"
			
			//text += "#r兑换前，务必检查背包是否还有空位，否则系统误吞概不补偿#d\r\n"
			
			text += "#r兑换前，务必检查背包是否还有空位，否则系统误吞概不补偿#d\r\n\r\n\r\n"
			
			//text += "#r兑换前，务必检查背包是否还有空位，否则系统误吞概不补偿#d\r\n"
            
            text += "#L1##b副本装备兑换#l\r\n\r\n\r\n#L2#副本装备升级#l\r\n\r\n"
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9040004, 1);

        } else if (selection == 2) {
            cm.openNpc(9040004, 2);

        } else if (selection == 3) {
            cm.openNpc(9900004, 3113);

        } else if (selection == 4) {
            cm.openNpc(9900004, 3114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 82);
        }
    }
}