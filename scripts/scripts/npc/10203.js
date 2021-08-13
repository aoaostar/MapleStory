/*
	NPC Name: 		Dark Lord
	Map(s): 		Maple Road : Spilt road of choice
	Description: 		Job tutorial, movie clip
*/

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 1) {
	    cm.sendNext("If you wish to experience what it's like to be a Thief, come see me again.");
	    cm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	cm.sendNext("Thieves are a perfect blend of luck, dexterity, and power that are adept at surprise attacks against helpless enemies. A high level of avoidability and speed allows the thieves to attack enemies with various angles.");
    } else if (status == 1) {
	cm.sendYesNo("Would you like to experience what it's like to be a Thief?");
    } else if (status == 2) {
	cm.MovieClipIntroUI(true);
	cm.warp(1020400, 0); // Effect/Direction3.img/rouge/Scene00
	cm.dispose();
    }
}