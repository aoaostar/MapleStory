function enter(pi) {
	var em = pi.getEventManager("HorntailBattle");

	if (em != null) {
		var prop = em.getProperty("preheadCheck");

		if (prop != null && prop.equals("2")) {
	    	pi.mapMessage(6, "The enromous creature is approaching from the deep cave.")
			em.setProperty("preheadCheck", "3");
		}
		if( prop != null && prop.equals("4") && pi.getMonsterCount(240060100) < 1 ) { //召唤怪物之后变成4
			em.setProperty("state",3);
		}
    }
}