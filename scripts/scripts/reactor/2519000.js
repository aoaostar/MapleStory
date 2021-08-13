function act() {
try {
	var em = rm.getEventManager("Pirate");
	if (em != null) {
		rm.mapMessage(6, "One of the doors have been activated.");
		em.setProperty("stage4", parseInt(em.getProperty("stage4")) + 1);
		if (em.getProperty("stage4").equals("4")) { //all 5 done
			rm.mapMessage(6, "Proceed!");
		}
	}
}catch (e) {
	rm.playerMessage(5, "Error: " + e);
}
}