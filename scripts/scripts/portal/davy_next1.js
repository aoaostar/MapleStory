function enter(pi) {
    try {
        var em = pi.getEventManager("Pirate");
		var playerS = pi.isLeader();
        if (playerS == true && em != null && pi.haveItem(4001120,20)&& pi.haveItem(4001121,20)&& pi.haveItem(4001122,20)) {
        //if (playerS == true && em != null && em.getProperty("stage2").equals("3")) {
		//pi.givePartyExp(30000);
            pi.warpParty(925100200); //next
        } else {
            pi.playerMessage(5, "The portal is not opened yet.");
        }
    } catch(e) {
        pi.playerMessage(5, "Error: " + e);
    }
}