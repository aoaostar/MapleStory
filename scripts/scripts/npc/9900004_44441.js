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

	    var textz = "\r\n音乐点歌台~~\r\n\r\n";

		textz += "#b#L0#【下完这场雨】#l\r\n\r\n";
		


		

		cm.sendSimple (textz);  

			
	}else if (status == 1) {
    
	if (selection == 0){
				
				 if (cm.haveItem(4001256,1)) {	
                        cm.gainItem(4001256,-1);
						cm.changeMusic("Bgm00/下完这场雨");
                        
                        cm.dispose();
						cm.worldMessage(5,"成功召唤出地狱船长[普通]。");
                        }else{
                         cm.sendOk("需要特定的材料#i4001256# X 1");
			cm.dispose();	
}

			
}										
}
}
}
