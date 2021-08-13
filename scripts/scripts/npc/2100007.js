/* Author: aaroncsn (MapleSea Like)(Incomplete- Needs skin id)
	NPC Name: 		Laila
	Map(s): 		The Burning Road: Ariant(2600000000)
	Description: 	Skin Care Specialist
*/

var status = 0;
var skin = Array(0, 1, 2, 3, 4);

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status >= 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			cm.sendNext("嗨，我是#p2100007# 今天你想要来点不一样的？？");
		} else if (status == 1) {
			cm.sendStyle("选择一个你想要的。", skin);
		} else if (status == 2){
			cm.dispose();
			if (cm.haveItem(5153007) == true){
				cm.gainItem(5153007, -1);
				cm.setSkin(skin[selection]);
				cm.sendOk("享受！");
			} else {
				cm.sendNext("痾.... 貌似没有#t5153007#。");
			}
		}
	}
}