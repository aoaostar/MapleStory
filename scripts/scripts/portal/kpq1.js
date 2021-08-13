importPackage(Packages.server.maps);

function enter(pi) {
    var eim = pi.getEventManager("KerningPQ").getInstance("KerningPQ");
    
    // only let people through if the eim is ready
    if (eim.getProperty("1stageclear") == null) { // do nothing; send message to player
	pi.playerMessage(5, "該洞口目前無法進入。");
    } else {
	pi.warp(103000801, "st00");
    }
}