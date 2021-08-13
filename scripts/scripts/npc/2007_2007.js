var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 签到 = "#fEffect/SkillName1.img/1001003/签到#";



function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	    

	if (cm.getPlayer().getLevel() < 10000) {
        cm.sendOk("你的天赋很平庸。");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var 
		  
	
		
		selStr = ""+cm.getPlayerCount(130000000)+"\r\n\r\n";
		
		selStr += "#L2#召唤天赋#l\r\n\r\n";
		  

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 1:
            cm.dispose();
			cm.openNpc(9900004,4);
            break;
		case 2:
            cm.dispose();
            cm.openNpc(9900004,2001);	
             break;

		}
    }
}