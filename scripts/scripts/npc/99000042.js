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
            text += " \t\t\t  #e#d欢迎来到#r大王冒险岛#k#n\r\n"
            //text += "#L1#爆率查询#l\t#L2#矿石合成#l\t#L3#每日跑商#l\t#L4#进入商城#l\r\n"
            text += "#L1005#重载事件#l#L1006#重载爆率#l#L1007#重载反应堆#l#L1008#重载传送点#l\r\n"
            if (cm.getPlayer().isGM()) {
                text += " \t\t#r以下功能，仅管理员可见，普通玩家看不见\r\n"
                text += "#L1000#快捷传送#l\t#L1001#快速转职#l\r\n"
                text += "#L1002#刷新当前地图#l#L1003#刷新个人状态#l\r\n"
                text += "#L1005#重载事件#l#L1006#重载爆率#l#L1007#重载反应堆#l#L1008#重载传送点#l\r\n"
                text += "#L1009#重载任务#l#L1010#重载商店#l\r\n"
            }
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1);

        } else if (selection == 2) {
            cm.openNpc(2020000, 0);

        } else if (selection == 3) {
            cm.openNpc(9010009, 0);

        } else if (selection == 4) {
            cm.openNpc(9900004, 4);

        } else if (selection == 5) {
            cm.openNpc(9900004, 5);

        } else if (selection == 6) {
            cm.openNpc(9900004, 6);

        } else if (selection == 7) {
            cm.openNpc(9900004, 7);

        } else if (selection == 8) {
            cm.openNpc(9900004, 8);

        } else if (selection == 9) {
            cm.openNpc(9900004, 9);

        } else if (selection == 10) {
            cm.openNpc(9900004, 10);

        } else if (selection == 11) {
            cm.openNpc(9900004, 11);

        } else if (selection == 12) {
            cm.openNpc(9900004, 12);

        } else if (selection == 13) {
            cm.openNpc(9900004, 13);

        } else if (selection == 14) {
            cm.openNpc(9900004, 14);

        } else if (selection == 15) {
            cm.openNpc(9900004, 15);

        } else if (selection == 16) {
            cm.openNpc(9900004, 16);

        } else if (selection == 17) {
            cm.openNpc(9900004, 17);

        } else if (selection == 18) {
            cm.openNpc(9900004, 18);

        } else if (selection == 19) {
            cm.openNpc(9900004, 19);

        } else if (selection == 20) {
            cm.openNpc(9900004, 20);

        } else if (selection == 21) {
            cm.openNpc(9900004, 21);

        } else if (selection == 22) {
            cm.openNpc(9900004, 22);

        } else if (selection == 23) {
            cm.openNpc(9900004, 23);

        } else if (selection == 24) {
            cm.openNpc(9900004, 24);

        } else if (selection == 25) {
            cm.openNpc(9900004, 25);

        } else if (selection == 26) {
            cm.openNpc(9900004, 26);

        } else if (selection == 27) {
            cm.openNpc(9900004, 27);

        } else if (selection == 28) {
            cm.openNpc(9900004, 28);

        } else if (selection == 29) {
            cm.openNpc(9900004, 29);

        } else if (selection == 30) {
            cm.openNpc(9900004, 30);

        } else if (selection == 31) {
            cm.openNpc(9900004, 31);

        } else if (selection == 32) {
            cm.openNpc(9900004, 32);

        } else if (selection == 33) {
            cm.openNpc(9900004, 33);

        } else if (selection == 34) {
            cm.openNpc(9900004, 34);

        } else if (selection == 35) {
            cm.openNpc(9900004, 35);

        } else if (selection == 36) {
            cm.openNpc(9900004, 36);

        } else if (selection == 37) {
            cm.openNpc(9900004, 37);

        } else if (selection == 38) {
            cm.openNpc(9900004, 38);

        } else if (selection == 39) {
            cm.openNpc(9900004, 39);

        } else if (selection == 40) {
            cm.openNpc(9900004, 40);

        } else if (selection == 1000) {//
            cm.openNpc(9900004, 1000);

        } else if (selection == 1001) {//
            cm.openNpc(9900004, 1001);

        } else if (selection == 1002) {//
            cm.刷新地图();
            cm.dispose();
        } else if (selection == 1003) {//
            cm.刷新状态();
            cm.dispose();
        } else if (selection == 1004) {//
            cm.gainItem(5211047, 1, 1);//高质地喇叭
            cm.dispose();
        } else if (selection == 1005) {//
            cm.重载事件();
            cm.dispose();
        } else if (selection == 1006) {//
            cm.重载爆率();
            cm.dispose();
        } else if (selection == 1007) {//
            cm.重载反应堆();
            cm.dispose();
        } else if (selection == 1008) {//
            cm.重载传送点();
            cm.dispose();
        } else if (selection == 1009) {//
            cm.重载任务();
            cm.dispose();
        } else if (selection == 1010) {//
            cm.重载商店();
            cm.dispose();
        }
    }
}
