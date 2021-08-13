var setupTask;
var map;
function init() {
	scheduleNew();
}

function scheduleNew() {
	
	var nextTime = 60*1000;//cal.getTimeInMillis();
	nextTime += java.lang.System.currentTimeMillis() ;
	setupTask =  em.scheduleAtTimestamp("start", nextTime);
	
}

function cancelSchedule() { 
	if (setupTask != null)
		setupTask.cancel(true);
}

function start() {
	
	scheduleNew()
	var hour=new Date(java.lang.System.currentTimeMillis()).getHours();
	var minute=new Date(java.lang.System.currentTimeMillis()).getMinutes();
	if (hour == 21&& minute == 30 &&(em.获取当前星期() == 6 ||em.获取当前星期() == 7 || em.获取当前星期() == 1)) 
	{
	var eim = em.newInstance("Laba");
	var redm1 = getredm(6, 999);
        for (var i = 1; i < 7; i++) {
        if (i == redm1) {
         map = eim.setInstanceMap(910000006+i);
         map.spawnNpc(9001106, new java.awt.Point(-947, -108));
	   em.broadcastServerMsg(5120025, " 神秘人物出现在市场的 7 - 12 某个洞穴中，等待你的奇遇。", true);
                       }
                    }
    }
  if (hour == 21 && minute == 31 &&(em.获取当前星期() == 6 ||em.获取当前星期() == 7 || em.获取当前星期() == 1)) 
	{
         map.removeNpc(9001106);
        em.broadcastServerMsg(5120025, " 神秘人物已经离开了。带着他的小宠物~", true);
                       }
	
}
function monsterDrop(eim, player, mob) {}
function getredm(max, count) {
    var redm = Math.floor(Math.random() * max);
    if (redm == count) {
        redm = getredm(max, count)
    }
    return redm;
}

rand = (function () {
    var today = new Date();
    var seed = today.getTime();
    function rnd() {
        seed = (seed * 9301 + 49297) % 233280;
        return seed / (233280.0);
    };
    return function rand(number) {
        return Math.ceil(rnd() * number);
    };
})();