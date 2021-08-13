var setupTask;

function init() {
	scheduleNew();
}

function scheduleNew() {
	var nextTime = 15*1000;//cal.getTimeInMillis();
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
	//var 星期 = cm.获取当前星期();
	
	
	if (hour == 12 && minute == 00  ) {

		em.broadcastServerMsg(5120020, " 现在时间是中午12点整。", true);
	}

	

	
	if (hour == 0 && minute == 00  ) {

		em.broadcastServerMsg(5120020, " 新的一天又开始了。", true);
	}


	
	
	
	
	
	
	
	
	}
function monsterDrop(eim, player, mob) {}