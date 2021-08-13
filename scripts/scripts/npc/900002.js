/*
	Maple Administrator - Displays Battler Info
*/

var status = -1;
var sel = 0;
var sec = 0;
var defaul = new Array(1210102, 1210100, 210100);

function action(mode, type, selection) {
	var battlers = cm.getPlayer().getBattlers();
	if (mode != 1) {
    		cm.dispose();
	} else {
		status++;
		if (status == 0) {
			if (battlers[0] == null) {
				if (cm.getPlayer().getLevel() < 30) {
					cm.sendOk("Patience. Come back when you are level 30...");
					cm.dispose();
				} else if (cm.getPlayer().getBoxed().size() > 0) {
					cm.sendOk("You have a monster in the box, please take that out first.");
					cm.dispose();
				} else {
					var selStr = "What would you like?\r\n\r\n#b";
					for (var i = 0; i < defaul.length; i++) {
						if (defaul[i] != null) {
							selStr += "#L" + i + "##o" + defaul[i] + "##l\r\n";
						}
					}
					cm.sendSimple(selStr);
				}
				return;
			}
			var selStr = "Total Wins: " + cm.getPlayer().getTotalWins() + ", Total Losses: " + cm.getPlayer().getTotalLosses() + ", Current Wins: " + cm.getPlayer().getIntNoRecord(122400) + "\r\nCheck the stats of which?\r\n\r\n#b";
			for (var i = 0; i < battlers.length; i++) {
				if (battlers[i] != null) {
					try {
						selStr += "#L" + i + "#" + battlers[i].getName() + " (#o" + battlers[i].getMonsterId() + "#) Level " + battlers[i].getLevel() + " " + battlers[i].getGenderString() + ", HP #B" + battlers[i].getHPPercent() + "# - " + battlers[i].getCurrentHP() + "/" + battlers[i].calcHP() + ", Status: " + battlers[i].getStatusString() + "#l\r\n";
					} catch (e) {
						cm.sendOk("Error: " + e);
						cm.dispose();
						cm.outputFileError(e);
						return;
					}
				}
			}
			cm.sendSimple(selStr);
		} else if (status == 1) {
			if (battlers[0] == null) {	
				cm.getPlayer().makeBattler(0, defaul[selection]);
				cm.sendOk("You have obtained your first minion. You can head down to the Internet Cafe to train or get other minions. You can also use @challenge <player name> to challenge someone to a match.");
				cm.dispose();
				return;
			}
			if (selection < 0 || selection >= battlers.length || battlers[selection] == null) {
				cm.dispose();
				return;
			}
			sel = selection;
			var info = "#e" + battlers[selection].getName() + "#n (#o" + battlers[selection].getMonsterId() + "#)\r\n";
			info += "Level " + battlers[selection].getLevel() + " " + battlers[selection].getGenderString() + "\r\n";
			info += "EXP " + battlers[selection].getExp() + "/" + battlers[selection].getNextExp() + "\r\n";
			info += "HP #B" + battlers[selection].getHPPercent() + "# - " + battlers[selection].getCurrentHP() + "/" + battlers[selection].calcHP() + "\r\n";
			info += "ATK: " + battlers[selection].getATK(0) +  ", DEF: " + battlers[selection].getDEF() + "%\r\n";
			info += "Sp.ATK: " + battlers[selection].getSpATK(0) +  ", Sp.DEF: " + battlers[selection].getSpDEF() + "%\r\n";
			info += "Speed: " + battlers[selection].getSpeed() +  ", Evasion: " + battlers[selection].getEVA() + ", Accuracy: " + battlers[selection].getACC() + "\r\n";
			info += "Status: " + battlers[selection].getStatusString() + "\r\n";
			info += "Element: " + battlers[selection].getElementString() + "\r\n";
			info += "Nature: " + battlers[selection].getNatureString() + "\r\n";
			info += "Item: " + battlers[selection].getItemString() + "\r\n";
			info += "Ability: " + battlers[selection].getAbilityString() + "\r\n";
			info += "\r\n#b";
			if (cm.getPlayer().getBattle() != null) {
				info += "#L2#Switch to this monster!#l\r\n";
			} else {
				info += "#L0#How do I evolve this?#l\r\n";
				info += "#L1#Release this monster.#l\r\n";
				info += "#L3#Rename this monster.#l\r\n";
				info += "#L4#Change order of this monster.#l\r\n";
				info += "#L5#Store this monster away.#l\r\n";
				info += "#L6#Give/take item.#l\r\n";
				info += "#L7#Rate this monster.#l\r\n";
			}
			cm.sendSimple(info);
		} else if (status == 2) {
			sec = selection;
			if (selection == 0) { //how i evolve
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				var evo = battlers[sel].getEvolutionType().value;
				if (evo == 0) {
					cm.sendNext("Congratulations, for you have reached the final stage in the evolution.");
					cm.dispose();
				} else if (evo == 1) {
					cm.sendNext("You still have a a long way to go, for you must level up some more.");
					cm.dispose();
				} else if (evo == 2) {
					var selStr = "You can only evolve by using a certain item. I can evolve it for you. Let's see here...\r\n\r\n";
					if (cm.haveItem(battlers[sel].getFamily().evoItem.id)) {
						cm.sendSimple(selStr + "#L0##v" + battlers[sel].getFamily().evoItem.id + "##z" + battlers[sel].getFamily().evoItem.id + "##l");
					} else {
						cm.sendNext(selStr + "You don't have the evolution item needed. Required: #v" + battlers[sel].getFamily().evoItem.id + "##z" + battlers[sel].getFamily().evoItem.id + "#");
						cm.dispose();
					}
				}
				
			} else if (selection == 1) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				cm.sendYesNo("Are you sure you want to release the monster " + battlers[sel].getName() + " (#o" + battlers[sel].getMonsterId() + "#)?");
			} else if (selection == 2) { //switch
				if (cm.getPlayer().getBattle() != null && !cm.getPlayer().getBattle().switchBattler(cm.getPlayer(), battlers[sel])) {
					cm.sendNext("You've already selected an action.");
				}
				cm.dispose();
			} else if (selection == 3) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				cm.sendGetText("Please enter the new name for your monster. (Min: 2 characters, Max: 20 characters)");
			} else if (selection == 4) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				if (cm.getPlayer().countBattlers() <= 1) {
					cm.sendOk("You only have one monster.");
					cm.dispose();
					return;
				}
				var selStr = "Change " + battlers[sel].getName() + " with which monster?\r\n\r\n#b";
				for (var i = 0; i < battlers.length; i++) {
					if (battlers[i] != null && i != sel) {
						selStr += "#L" + i + "#" + battlers[i].getName() + " (#o" + battlers[i].getMonsterId() + "#) Level " + battlers[i].getLevel() + " " + battlers[i].getGenderString() + ", HP #B" + battlers[i].getHPPercent() + "# - " + battlers[i].getCurrentHP() + "/" + battlers[i].calcHP() + ", Status: " + battlers[i].getStatusString() + "#l\r\n";
					}
				}
				cm.sendSimple(selStr);
			} else if (selection == 5) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				if (cm.getPlayer().countBattlers() <= 1) {
					cm.sendOk("You only have one monster.");
					cm.dispose();
					return;
				}
				cm.getPlayer().getBoxed().add(battlers[sel]);
				for (var i = sel; i < battlers.length; i++) {
					cm.getPlayer().getBattlers()[i] = ((i + 1) == battlers.length ? null : cm.getPlayer().getBattlers()[i + 1]);
				}
				cm.getPlayer().changedBattler();
				cm.sendOk("The monster has been boxed away.");
			} else if (selection == 6) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				if (battlers[sel].getItem() != null) {
					if (cm.canHold(battlers[sel].getItem().id, 1)) {
						cm.gainItem(battlers[sel].getItem().id, 1);	
						cm.sendOk("You have taken the item from this monster.");
						battlers[sel].setItem(0);
					} else {
						cm.sendOk("Please make inventory space.");
					}
					cm.dispose();
					return;
				}
				var selStr = "Which item would you like to give to this monster?#b\r\n";
				var hi = cm.getAllHoldItems();
				var pass = false;
				for (var i = 0; i < hi.length; i++) {
					if (cm.haveItem(hi[i].id, 1)) {
						pass = true;
						selStr += "#L" + i + "##i" + hi[i].id + "#" + hi[i].customName + "#l\r\n";
					}
				}
				if (!pass) {
					cm.sendNext("You have no hold items.");
					cm.dispose();
				} else {
					cm.sendSimple(selStr);
				}
			} else if (selection == 7) {
				if (cm.getPlayer().getBattle() != null) {
					cm.dispose();	
					return;
				}
				cm.sendNext(battlers[sel].getIVString());
				cm.dispose();
			}
		} else if (status == 3) {
			if (sec == 0) {
				if (cm.haveItem(battlers[sel].getFamily().evoItem.id)) {
					cm.gainItem(battlers[sel].getFamily().evoItem.id, -1);
					battlers[sel].evolve(true, cm.getPlayer());
					cm.getPlayer().changedBattler();
					cm.playSound(false, "5th_Maple/gaga");
					cm.sendNext("Your monster has evolved!!!");
				}
			} else if (sec == 1) {
				if (cm.getPlayer().removeBattler(sel)) {
					cm.sendNext("It has been released!");
				} else {
					cm.sendOk("You cannot release the last monster!");
				}
			} else if (sec == 3) {
				if (cm.getText().length() < 2 || cm.getText().length() > 20) {
					cm.sendOk(cm.getText() + " cannot be accepted.");
				} else {
					cm.getPlayer().changedBattler();
					battlers[sel].setName(cm.getText());
				}
			} else if (sec == 4) {
				if (battlers[selection] != null) {
					var dummy = cm.getPlayer().getBattlers()[selection];
					cm.getPlayer().getBattlers()[selection] = cm.getPlayer().getBattlers()[sel];
					cm.getPlayer().getBattlers()[sel] = dummy;
					cm.getPlayer().changedBattler();
				}	
			} else if (sec == 6) {
				var hi = cm.getAllHoldItems()[selection];
				if (cm.haveItem(hi.id, 1)) {
					cm.gainItem(hi.id, -1);
					battlers[sel].setItem(hi.id);
					cm.sendOk("The item has been set on to the monster.");
				}
			}
			cm.dispose();
		}
	}
}