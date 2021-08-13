var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var eim = cm.getEventInstance();
    var stage3status = eim.getProperty("stage3status");

    if (stage3status == null) {
	if (cm.isLeader()) { // Leader
	    var stage3leader = eim.getProperty("stage3leader");
	    if (stage3leader == "done") {

		if (!cm.haveMonster(9500007)) { // Clear stage
		    clear(1, eim, cm);
                    cm.warpParty(910340600, 0);
		    cm.givePartyExp(2100, eim.getPlayers());
		    cm.dispose();
		} else { // Not done yet
		    cm.sendNext("请检查地图上是否还存在怪物，否则无法通过！");
		}
		cm.dispose();
	    } else {
		cm.sendOk("你好，欢迎来到第5个阶段，消灭BOSS，然后组队长再和我讲话，就可以顺利通关了，那么祝你一切顺利！");
		cm.spawnMonster(9300003,1);
		eim.setProperty("stage3leader","done");
		cm.dispose();
	    }
	} else { // Members
	    cm.sendNext("你好，欢迎来到第5个阶段，消灭BOSS，然后组队长再和我讲话，就可以顺利通关了，那么祝你一切顺利！");
	    cm.dispose();
	}
    } else {
	cm.sendNext("恭喜你！ 你成功通过了第5阶段！快点，向最后阶段前进吧！");
	cm.dispose();
    }
}

function clear(stage, eim, cm) {
    eim.setProperty("stage" + stage.toString() + "status","clear");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
}
