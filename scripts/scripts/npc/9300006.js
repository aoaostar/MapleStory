
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
            text += " \t\t\t  #e#d欢迎来到#r怀旧冒险岛#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L1#领取结婚福利#l\r\n\r\n"

            cm.sendSimple(text);
        } else if (selection == 1) {//每日免费领取福利
            if (cm.getPlayer().getOneTimeLog("LoveChair") > 0) {
                cm.sendOk("您已经领取过了");
				cm.dispose();
            } else {
                cm.getPlayer().setOneTimeLog("LoveChair");
                cm.gainItem(3012003, 1);
                cm.gainItem(1112320, 5, 5, 5, 5, 200, 200, 5, 5, 10, 10, 10, 10, 10, 10);
                cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]为他/她的侠侣戴上了结婚戒指，有请宾客入场！");
                cm.sendOk("#v" + 3012003 + "#、#v1112804#已经放到了你的背包、重新登陆后，就有结婚经验了！");
                cm.dispose();
            }
        }
    }
}
