/*
	Thief Job Instructor - Thief's Construction Site (108000400)
*/

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }

    if (status == 0) {
	if (cm.haveItem(4031013, 30)) {
	    cm.removeAll(4031013);
	    cm.completeQuest(100010);
	    cm.startQuest(100011);
	    cm.sendOk("你是一个英雄，拿着这个#i4031012# 达鲁克会认可你的。");
	} else {
	    cm.sendOk("开始考验。你要交给我 #b30个 #t4031013##k. 祝你好运.")
	    cm.safeDispose();
	}
    } else if (status == 1) {
	cm.warp(103000000, 0);
	cm.gainItem(4031012, 1);
	cm.dispose();
    }
}	