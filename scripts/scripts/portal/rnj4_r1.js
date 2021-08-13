function enter(pi) {
    var em = pi.getEventManager("Romeo");
    if (em != null && em.getProperty("stage6_0").equals("0")) {
	pi.warp(926100301,0);
	em.setProperty("stage6_0", "1");
    } else {
	pi.playerMessage(5, "有人已经进入了这个门");
    }
}
