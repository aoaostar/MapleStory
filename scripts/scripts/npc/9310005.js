function start() {
	    if (cm.haveItem(4000194,10) && cm.getBossLog('PlayQuest168') < 200) {
        cm.setBossLog('PlayQuest168');
			cm.gainItem(4000194, -10);
			cm.warp(701010322, "sp");	
			cm.dispose();
	} else {
	   cm.sendOk("你没有黑羊毛1个,或者你已经完成了200次!");
    cm.dispose();
}
}