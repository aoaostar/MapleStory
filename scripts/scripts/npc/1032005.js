/**
	VIP Cab - Victoria Road : Ellinia (101000000)
**/

var status = -1;
var cost;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status >= 1) {
	    cm.sendNext("这个城市也有很多你没探索到的地方哦。如果你觉得有必要去蚂蚁广场，你可以找我。");
	    cm.safeDispose();
	    return;
	}
	status--;
    }

    if (status == 0) {
	cm.sendNext("如果你给我10000金币，我就送你去#b蚂蚁广场#k.");
    } else if (status == 1) {
	var job = cm.getJob();
	if (job == 0 || job == 1000 || job == 2000) {
	    cm.sendYesNo("我们对新手有百分90的优惠哦！如果你想要安全到达#b蚂蚁广场#k,那就快点付钱吧！");
	    cost = 1000;
	} else {
	    cm.sendYesNo("那边有一个24小时排挡可以补充你需要的补给品，快点付钱吧，才#b10,000金币#k你都出不起么?");
	    cost = 10000;
	}
    } else if (status == 2) {
	if (cm.getMeso() < cost) {
	    cm.sendNext("你省省吧，这么一点小钱都出不起，坐不起出租车就去坐公交！");
	    cm.safeDispose();
	} else {
	    cm.gainMeso(-cost);
	    cm.warp(105070001, 0);
	    cm.dispose();
	}
    }
}