function enter(pi) {
	if (pi.getQuestStatus(2073) == 1) {
		pi.warp(900000000,0);
		return true;
	} else {
		pi.playerMessage(5,"You can't seem to be able to go inside...");
		return false;
	}
}