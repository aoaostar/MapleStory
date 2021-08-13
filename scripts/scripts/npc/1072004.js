/**
 Warrior Job Instructor - Warrior's Rocky Mountain (108000300)
 **/

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
            cm.completeQuest(100004);
            cm.startQuest(100005);
            cm.sendOk("你是个真正的英雄！把这个#i4031012#交给武术教练他会承认你.");
        } else {
            cm.sendOk("你需要收集 #b30个 #t4031013##k交给我. 祝你好运.")
            cm.dispose();
        }
    } else if (status == 1) {
        cm.gainItem(4031012, 1);
        cm.warp(102000000, 0);
        cm.dispose();
    }
}	