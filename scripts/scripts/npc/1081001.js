/**
	Pison - Florina Beach(110000000)
**/
var status = -1;
var returnmap = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.sendNext("看来你在这一定还有一些事要处理. ");
	cm.safeDispose();
	return;
    }
    if (status == 0) {
	returnmap = cm.getSavedLocation("FLORINA");
	cm.sendNext("你想离开 #b#m110000000##k? 如果你愿意, 我可以送你回到 #b#m"+returnmap+"##k.");
    } else if (status == 1) {
	cm.sendYesNo("你确定要返回到 #b#m"+returnmap+"##k? 好吧, 我们立即出发.")
    } else if (status == 2) {
	if (returnmap < 0) {
		returnmap = 104000000;
	}
	cm.warp(returnmap);
	cm.clearSavedLocation("FLORINA");
	cm.dispose();
    }
}
