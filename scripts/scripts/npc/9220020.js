var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (!cm.isLeader()) {
	cm.sendNext("请找队长来和我说话。");
	cm.dispose();
	return;
    }
    if (cm.haveItem(4032119,17)) {
	cm.warpParty(674030200);
	cm.gainItem(4032119,-17);
    } else {
	cm.sendOk("欢迎来到V怪客副本 我需要地图上岩块堆 中的\r\n#r17个 #b#t4032119##k。");
    }
    cm.dispose();
}