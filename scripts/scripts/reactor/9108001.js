
function act() {
	rm.mapMessage(6, "One of the seeds has been placed.");
	var em = rm.getEventManager("HenesysPQ");
	if (em != null) {
		var react = rm.getMap().getReactorByName("fullmoon");
		em.setProperty("stage", parseInt(em.getProperty("stage")) + 1);
		react.forceHitReactor(react.getState() + 1);
		if (em.getProperty("stage").equals("6")) {
			rm.mapMessage(6, "Protect the Moon Bunny!!!");
			rm.getMap().setSpawns(true);
			rm.getMap().respawn(true);
			rm.getMap().spawnMonsterOnGroundBelow(em.getMonster(9300061), new java.awt.Point(-183, -433));
			rm.achievement(50);
		}
	}
}