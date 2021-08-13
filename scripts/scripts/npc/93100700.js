/*
	Mong from Kong - Victoria Road : Kerning City (103000000)
*/


function start() {
    cm.sendYesNo("你要进入国庆活动地图么?除非你给我一个#i4000464#?");
}


function action(mode, type, selection) {
	
	if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4) {
			cm.sendOk("只有在 #r2 ，3，4#k 频道才可以进入。");
			cm.dispose();
			return;
	    }
    if (mode == 0) {
	cm.sendNext("你没有我需要的物品，我不打算让你进去。");
    } else {
	if (cm.haveItem(4000464,1)) {
		cm.gainItem(4000464,-1);
	    cm.warp(749020900, 0);
	} else {
	    cm.sendNext("哎，你都没有#i4000464#，怎么能让你进去呢？。");
	}
    }
    cm.dispose();
}