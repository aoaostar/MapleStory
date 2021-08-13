
function act() {
    rm.mapMessage(6, "The music played through the air.");
	var em = rm.getEventManager("OrbisPQ");
	if (em != null) {
		em.setProperty("stage3", "1");
	}
}