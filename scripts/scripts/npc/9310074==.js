/*
自由市场牌子
*/


var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		cm.sendOk("选手编号：62\r\n角色名：女王佳佳\r\n服务器：蓝蜗牛");
		cm.dispose();
		}
	}
}
