/*
	NPC Name: 		Dances with Balrog
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
	    cm.sendNext("If you wish to experience what it's like to be a Warrior, come see me again.");
	    cm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	cm.sendNext("Warriors possess an enormous power with stamina to back it up, and they shine the brightest in melee combat situation. Regular attacks are powerful to begin with, and armed with complex skills, the job is perfect for explosive attacks.");
    } else if (status == 1) {
	cm.sendYesNo("Would you like to experience what it's like to be a Warrior?");
    } else if (status == 2) {
	cm.MovieClipIntroUI(true);
	cm.warp(1020100, 0); // Effect/Direction3.img/swordman/Scene00
	cm.dispose();
    }
}