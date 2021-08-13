/*
 Bowman Job Instructor - Ant Tunnel For Bowman (108000100)
 */

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.haveItem(4031013, 30)) {
            cm.removeAll(4031013);
            cm.completeQuest(100001);
            cm.startQuest(100002);
            cm.sendOk("你是个真正的英雄！拿着这个，#i4031012# 赫丽娜会承认你的！。");
        } else {
            cm.sendOk("你需要在这里收集 #b30个 #t4031013##k交给我. 祝你好运.")
            cm.dispose();
        }
    } else if (status == 1) {
        cm.warp(100000000, 1);
        cm.gainItem(4031012, 1);
        cm.dispose();
    }
}	