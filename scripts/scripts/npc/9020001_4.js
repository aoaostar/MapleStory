var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var eim = cm.getEventInstance();
    var stage2status = eim.getProperty("stage2status");

    if (stage2status == null) {
	if (cm.isLeader()) { // Leader
	    var stage2leader = eim.getProperty("stage2leader");
	    if (stage2leader == "done") {

		if (cm.haveItem(4001008, 13)) { // Clear stage
        	    eim.setProperty("4stageclear",1);
		    cm.sendNext("恭喜你！ 你成功通过了第4阶段！快点，向第5阶段前进吧！");
		    cm.gainItem(4001008, -13);
		    clear(1, eim, cm);
		    cm.givePartyExp(2100, eim.getPlayers());
		    cm.dispose();
		} else { // Not done yet
		    cm.sendNext("确定你带来了 #r13 张通行证#k 了吗？ 请检查一下你的背包~");
		}
		cm.dispose();
	    } else {
		cm.sendOk("你好，欢迎来到第4阶段，到处走走，可能会发现很多凶猛的怪物，打败它们，获取通行证#r13#k张，再把他们交给我。记住，怪物可能比你强大很多，请小心一点，祝你通过这一关。");
		eim.setProperty("stage2leader","done");
		cm.dispose();
	    }
	} else { // Members
	    cm.sendNext("欢迎来到第4阶段，在地图上走走，你就会看见许多凶猛的怪物，打败他们获取他们身上的通行证#r13#k张，交给你们的组队长。");
	    cm.dispose();
	}
    } else {
	cm.sendNext("恭喜你！ 你成功通过了第4阶段！快点，向第5阶段前进吧！");
	cm.dispose();
    }
}

function clear(stage, eim, cm) {
    eim.setProperty("stage" + stage.toString() + "status","clear");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
}
