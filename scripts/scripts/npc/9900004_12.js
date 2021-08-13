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
            text += " #e#r欢迎来到亲子鉴定中心~你到底是欧洲人还是非洲人呢？#k#n\r\n"
            
            //text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"
            
            //text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"
            
            text += "\t#L1#陈玄风普通抽奖#l\t\t#L2#梅超风超级抽奖#l\r\n\r\n"//\t#L3#暂无功能#l
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 121);

        } else if (selection == 2) {
            cm.openNpc(9900004, 122);

        } else if (selection == 3) {
            cm.openNpc(9010009, 123);
        }
    }
}