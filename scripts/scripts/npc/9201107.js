 importPackage(net.sf.cherry.client);
var status = 0;

var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";


	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
		if (mode == -1) {
		cm.dispose();
		} else {
		if (status >= 0 && mode == 0) {
		cm.dispose();
		return;
		}
		 if (cm.getPlayer().getClient().getChannel() != 1) {
			cm.sendOk("只有 #r1#k 线可以启用贤者雕像。");
			cm.dispose();
			 }
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "";

		textz += "#b#L0#解开封印#l #L1#离开这里#l\r\n\r\n\r\n";

		textz += "   所需物品；  #i04001256# #i04001257# #i04001258# #i04001259# #i04001260#";

		cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0){
		var party = cm.getPlayer().getParty();		
		if (party == null || party.getLeader().getId() != cm.getPlayer().getId ()) {
                    cm.sendOk("你不是队长。请你们队长来说话吧！");
                    cm.dispose();
					
		}else if(party.getMembers().size() < 0) {
	            cm.sendOk("需要 6 人以上的组队才可以释放强大的魔法！!");
                    cm.dispose();
					
		}else if (cm.haveItem(4001256,1)&&cm.haveItem(4001257,1)&&cm.haveItem(4001258,1)&&cm.haveItem(4001259,1)&&cm.haveItem(4001260,1) ) {	
                        cm.gainItem(4001256,-1);
						 cm.gainItem(4001257,-1);
						  cm.gainItem(4001258,-1);
						   cm.gainItem(4001259,-1);
						    cm.gainItem(4001260,-1);
                        cm.spawnMonster(9400432,1445,-22);
                    
					
					
					
                        cm.dispose();
						//cm.worldMessage(6,"灾难啊，灾难啊，太可怕了，"+cm.getName()+"在勇士部落召唤出了暗黑龙王。");
						//cm.worldMessage(6,"灾难啊，灾难啊，太可怕了，"+cm.getName()+"在勇士部落召唤出了暗黑龙王。");
						//cm.worldMessage(6,"灾难啊，灾难啊，太可怕了，"+cm.getName()+"在勇士部落召唤出了暗黑龙王。");
                        }else{
                        cm.sendOk("需要特定的材料");
			cm.dispose();	
}

	}else if (selection == 1){
		cm.warp(910000000);			
		cm.dispose();

}										
}
}
}
