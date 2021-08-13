var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.dispose();
	}
	status--;
    }
    if (status == 0) {
	    cm.removeAll(4001169);//怪物之珠
	    cm.removeAll(2270004);//净化之珠
	    cm.removeAll(4001162);
	    cm.removeAll(4001163);
	cm.sendOk("#k这里是：艾琳森林组队副本\r\n最少人数1人可以进入.请召集伙伴一起挑战吧！\r\n\r\n#d#L2#进入艾琳森林组队副本#l\r\n\r\n");
    } else if (status == 1) {
	if (selection == 0) {
		cm.warp(910000000);
	} else if (selection == 1){
	    if (cm.haveItem(1032060) && !cm.haveItem(1032061) && cm.haveItem(4001198, 10)) {
		cm.gainItem(1032060,-1);
		cm.gainItem(1032061, 1);
		cm.gainItem(4001198, -10);
	    } else {
		cm.sendOk("You either don't have Altair Earrings already or you do not have 10 Altair Fragments");
	    }
	} else if (selection == 1){
	    if (cm.haveItem(1032061) && !cm.haveItem(1032101) && cm.haveItem(4001198, 10)) {
		cm.gainItem(1032061,-1);
		cm.gainItem(1032101, 1);
		cm.gainItem(4001198, -10);
	    } else {
		cm.sendOk("You either don't have Glittering Altair Earrings already or you do not have 10 Altair Fragments");
	    }
	} else if (selection == 2) {
	    if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
		cm.sendOk("队长必须在这里，请让他和我说话.");
		}else if (cm.getPlayerCount(930000000) <= 0 && cm.getPlayerCount(930000010) <= 0 && cm.getPlayerCount(930000100) <= 0 && cm.getPlayerCount(930000200) <= 0 && cm.getPlayerCount(930000300) <= 0 && cm.getPlayerCount(930000400) <= 0 && cm.getPlayerCount(930000500) <= 0 && cm.getPlayerCount(930000600) <= 0 && cm.getPlayerCount(930000700) <= 0) {
		
		var party = cm.getPlayer().getParty().getMembers();
		var mapId = cm.getPlayer().getMapId();
		var next = true;
		var size = 0;
		var it = party.iterator();
		while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null || ccPlayer.getLevel() < 45 || ccPlayer.getLevel() > 255) {
				next = false;
				break;
			}
			size += (ccPlayer.isGM() ? 4 : 1);
		}	
		if (next && size >= 1) {
			var em = cm.getEventManager("Ellin");
	                cm.喇叭(1, "["+cm.getPlayer().getName()+"]带领队伍开始挑战邪恶的[毒雾副本]！");
			if (em == null) {
				cm.sendOk("Please try again later.");
			} else {
				em.startInstance(cm.getPlayer().getParty(), cm.getPlayer().getMap());
			}
		} else {
			cm.sendOk("最低人数1人。最低等级45级。");
		}
	    } else {
			cm.sendOk("已经有队伍进入。请其他频道进行副本！");
	    }
	} else if (selection == 3) {
            cm.openNpc(9310084, 26);
			//cm.sendOk("！");
	}
	cm.dispose();
    }
}