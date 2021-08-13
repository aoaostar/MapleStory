var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var eim = cm.getEventInstance();
    var stage1status = eim.getProperty("stage1status");

    if (stage1status == null) {
	if (cm.isLeader()) { // Leader
	    var stage1leader = eim.getProperty("stage1leader");
	    if (stage1leader == "done") {

		if (cm.haveItem(4001007, 30)) { // Clear stage
        	    eim.setProperty("1stageclear",1);
		    cm.sendNext("恭喜你！ 你成功通过了第1阶段！快点，向第2阶段前进吧！");
		    cm.gainItem(4001007, -30);
		    clear(1, eim, cm);
		    cm.givePartyExp(2100, eim.getPlayers());
		    cm.dispose();
		} else { // Not done yet
		    cm.sendNext("确定你带来了 #r30 张证书卡#k 了吗？ 请检查一下你的背包~");
		}
		cm.dispose();
	    } else {
		cm.sendOk("你好，欢迎来到第一个阶段，在这里你可能会考到很多凶狠的鳄鱼，打倒凶狠的鳄鱼获取相应数目的证书卡交给我，就行了。你们把证书卡全部交给组队长，组队长再和我讲话，就可以顺利通关了，那么祝你一切顺利！");
		eim.setProperty("stage1leader","done");
		cm.dispose();
	    }
	} else { // Members
	    cm.sendNext("你好，欢迎来到第一个阶段，在这里你可能会考到很多凶狠的鳄鱼，打倒凶狠的鳄鱼获取相应数目的证书卡交给我，就行了。你们把证书卡全部交给组队长，组队长再和我讲话，就可以顺利通关了，那么祝你一切顺利！");
	    cm.dispose();
	}
    } else {
	cm.sendNext("恭喜你！ 你成功通过了第1阶段！快点，向第2阶段前进吧！");
	cm.dispose();
    }
}

function clear(stage, eim, cm) {
    eim.setProperty("stage" + stage.toString() + "status","clear");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
}
