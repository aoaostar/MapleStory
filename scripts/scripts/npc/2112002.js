var status = -1;
function action(mode, type, selection) {
    if (cm.getMapId() == 926100600) {
	    cm.removeAll(4001130);
	    cm.removeAll(4001131);
	    cm.removeAll(4001132);
	    cm.removeAll(4001133);
	    cm.removeAll(4001134);
	    cm.removeAll(4001135);
	var em = cm.getEventManager("Romeo");
    if (em != null) {
	var itemid1 = 4001160;
	var itemid2 = 4170017;
	var itemid3 = 4251200;
	var itemid4 = 2022530;
	if (!cm.canHold(itemid1, 1)) {
	    cm.sendOk("请清理出一个格子");
	    cm.dispose();
	    return;
	}
		if (!cm.canHold(itemid2, 1)) {
	    cm.sendOk("请清理出一个格子");
	    cm.dispose();
	    return;
	}
		if (!cm.canHold(itemid3, 1)) {
	    cm.sendOk("请清理出一个格子");
	    cm.dispose();
	    return;
	}
		if (!cm.canHold(itemid4, 1)) {
	    cm.sendOk("请清理出一个格子");
	    cm.dispose();
	    return;
	}
	
	
	
	cm.gainItem(itemid1, 1);
	cm.gainItem(itemid2, 1);
	cm.gainItem(itemid3, 1);
	cm.gainItem(itemid4, 1);
	
	
	if (em.getProperty("stage").equals("2")) {
//    		cm.gainNX(5000);
    		cm.gainExpR(140000);
	} else {
//		cm.gainNX(3500);
		cm.gainExpR(105000);
	}
    }
    cm.getPlayer().endPartyQuest(1205);
    cm.warp(926100700,0);
    cm.dispose();
    return;
    }
    if (mode > 0) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	    cm.removeAll(4001130);
	    cm.removeAll(4001131);
	    cm.removeAll(4001132);
	    cm.removeAll(4001133);
	    cm.removeAll(4001134);
	    cm.removeAll(4001135);
	cm.sendSimple("#b#L0#让我离开这里#l\r\n#L1#给我 Horus' Eye.#l\r\n#L2#给我 Rock of Wisdom#l#k");
    } else {
	if (selection == 0) {
    	    cm.warp(926100600,0);
	} else if (selection == 1) {
	    if (cm.canHold(1122010,1) && cm.haveItem(4001160,25) && cm.haveItem(4001159,25)) {
		cm.gainItem(1122010,1);
		cm.gainItem(4001160,-25);
		cm.gainItem(4001159,-25);
	    } else {
		cm.sendOk("你要拿二十五个珠子来和我换");
	    }
	} else {
	    if (cm.canHold(2041212,1) && (cm.haveItem(4001160,10) || cm.haveItem(4001159,10))) {
		cm.gainItem(2041212,1);
		if (cm.haveItem(4001160,10)) {
			cm.gainItem(4001160,-10);
		} else {
			cm.gainItem(4001159,-10);
		}
	    } else {
		cm.sendOk("你要拿十个珠子来和我换");
	    }
	}
    	cm.dispose();
    }
}