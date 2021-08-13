var map;
var cost;
var location;
var mapname;
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.sendNext("嗯......想想吧。这是出租车公司价值的服务！你永远不会后悔！");
	cm.dispose();
	return;
    }

    if (status == 0) {
	cm.sendYesNo("你好！我将会把你传送到上海外滩深处，是否继续？\r\n#r需要花费1000金币哦");
    } else if (status == 1) {
	if (cm.getMeso() < 1000) {
	    cm.sendNext("你看起来没啥钱可以支付,很抱歉我们不收乞丐搭车的,滚吧!!!");
	    cm.dispose();
	} else {
	    cm.gainMeso(-1000);
	    cm.warp(701010320, 0);
	    cm.dispose();
	}
    }
}