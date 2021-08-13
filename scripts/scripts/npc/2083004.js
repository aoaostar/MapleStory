/*
	NPC Name: 		Mark of the Squad
	Map(s): 		Entrance to Horned Tail's Cave
	Description: 		Horntail Battle starter
*/
var status = -1;

function start() {
		if (cm.getPlayer().getLevel() < 80) {
			cm.sendOk("挑战这里最低等级需要80级,历练到80级再来吧");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3) {
			cm.sendOk("黑龙只能在2或3频道挑战！");
			cm.dispose();
			return;
		}
    var em = cm.getEventManager("HorntailBattle");

    if (em == null) {
	cm.sendOk("事件尚未启动，请联系GM处理该事情。");
	cm.dispose();
	return;
    }
    var prop = em.getProperty("state");

	    var marr = cm.getQuestRecord(160100);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    var time = parseInt(data);
    if (prop == null || prop.equals("0")) {
	var squadAvailability = cm.getSquadAvailability("Horntail");
	if (squadAvailability == -1) {
	    status = 0;
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("你已经去了黑龙在过去12小时。剩下的时间： " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	    cm.sendYesNo("你有兴趣成为探险队的领队吗？");

	} else if (squadAvailability == 1) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("你已经去了黑龙在过去12小时。剩下的时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	    // -1 = Cancelled, 0 = not, 1 = true
	    var type = cm.isSquadLeader("Horntail");
	    if (type == -1) {
		cm.sendOk("组队已经结束了，请重新开始任务.");
		cm.dispose();
	    } else if (type == 0) {
		var memberType = cm.isSquadMember("Horntail");
		if (memberType == 2) {
		    cm.sendOk("你被禁止入队.");
		    cm.dispose();
		} else if (memberType == 1) {
		    status = 5;
		    cm.sendSimple("你想做什么？ \r\n#b#L0#检查成员#l \r\n#b#L1#加入队伍#l \r\n#b#L2#退出队伍#l");
		} else if (memberType == -1) {
		    cm.sendOk("The squad has ended, please re-register.");
		    cm.dispose();
		} else {
		    status = 5;
		    cm.sendSimple("你想做什么？? \r\n#b#L0#检查成员#l \r\n#b#L1#加入队伍#l \r\n#b#L2#退出队伍#l");
		}
	    } else { // Is leader
		status = 10;
		cm.sendSimple("你想做什么？? \r\n#b#L0#检查成员#l \r\n#b#L1#删除成员#l \r\n#b#L2#编辑限制列表#l \r\n#r#L3#进入地图#l");
	    // TODO viewing!
	    }
	} else {
			var eim = cm.getDisconnected("HorntailBattle");
			if (eim == null) {
				var squd = cm.getSquad("Horntail");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("你已经去了黑龙在过去12小时。剩下的时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("小队与黑龙的战斗已经开始了。\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("小队与黑龙的战斗已经开始了.");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("啊，你回来了。你愿意再加入你的队伍吗？");
				status = 1;
			}
	}
    } else {
			var eim = cm.getDisconnected("HorntailBattle");
			if (eim == null) {
				var squd = cm.getSquad("Horntail");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("你已经去了黑龙在过去12小时。剩下的时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
					cm.sendYesNo("小队与黑龙的战斗已经开始了.\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("小队与黑龙的战斗已经开始了.");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("啊，你回来了。你愿意再加入你的队伍吗？?");
				status = 1;
			}
    }
}

function action(mode, type, selection) {
    switch (status) {
	case 0:
	    	if (mode == 1) {
			if (cm.registerSquad("Horntail", 5, " 被任命为小组队长（常规）。如果您愿意参加，请在时间段内为远征队登记。")) {
				cm.sendOk("你被任命为小组队长。在接下来的5分钟里，你可以加入探险队的成员。");
			} else {
				cm.sendOk("增加小组时出现错误.");
			}
	    	}
	    cm.dispose();
	    break;
	case 1:
		if (!cm.reAdd("HorntailBattle", "Horntail")) {
			cm.sendOk("错误…请再试一次。");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("Horntail");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经预订好位置了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("Horntail", 0)) {
		    cm.sendOk("由于一个未知的错误，对小组的要求被拒绝了.");
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("Horntail", true);
		if (ba == 2) {
		    cm.sendOk("队伍已满，请稍后再试。");
		} else if (ba == 1) {
		    cm.sendOk("你已经成功地加入了小组。");
		} else {
		    cm.sendOk("You are already part of the squad.");
		}
	    } else {// withdraw
		var baa = cm.addMember("Horntail", false);
		if (baa == 1) {
		    cm.sendOk("你已经退出了小组的成功");
		} else {
		    cm.sendOk("You are not part of the squad.");
		}
	    }
	    cm.dispose();
	    break;
	case 10:
	    if (mode == 1) {
		if (selection == 0) {
		    if (!cm.getSquadList("Horntail", 0)) {
			cm.sendOk("由于一个未知的错误，对小组的要求被拒绝了。");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("Horntail", 1)) {
			cm.sendOk("由于一个未知的错误，对小组的要求被拒绝了。");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("Horntail", 2)) {
			cm.sendOk("由于一个未知的错误，对小组的要求被拒绝了。");
			cm.dispose();
		    }
		} else if (selection == 3) { // get insode
		    if (cm.getSquad("Horntail") != null) {
			var dd = cm.getEventManager("HorntailBattle");
			dd.startInstance(cm.getSquad("Horntail"), cm.getMap(), 160100);
		    } else {
			cm.sendOk("由于一个未知的错误，对小组的要求被拒绝了。");
		    }
		    cm.dispose();
		}
	    } else {
		cm.dispose();
	    }
	    break;
	case 11:
	    cm.banMember("Horntail", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("Horntail", selection);
	    }
	    cm.dispose();
	    break;
	default:
	    cm.dispose();
	    break;
    }
}