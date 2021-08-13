/* Author: Xterminator
	NPC Name: 		Syras
	Map(s): 		Ariant : Ariant Station Platform (260000100)
	Description: 		Orbis Ticketing Usher
*/
var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			cm.sendNext("Hi. I'm Syras. Do you want to leave Ariant and go somewhere else? This station is where you'll find the ship that heads to #bOrbis Station#k of Ossyria leaving #bat the top of the hour, and every 10 minutes afterwards#k.");
		} else if (status == 1) {
			cm.sendNextPrev("If you're going to Orbis, talk to that old man on the right, #bAsesson#k. He has a hard time hearing, so you may want to yell at him to get his attention.");
		} else if (status == 2) {
			cm.sendNextPrev("Oh, and in case you're not aware of this, somewhere in the desert, a mysterious-looking man called Karcasa sends people to Victoria Island for a fee. I hope you understand that it's against the law to fly these innocent people to other towns without permit!!");
		} else if (status == 3) {
			cm.sendPrev("The Camel Cab, however, is permitted by the king so you can use that. Well, that cab will only take you up to Magatia, but it's still leagal.");
		} else if (status == 4) {
			cm.dispose();
		}
	}
}