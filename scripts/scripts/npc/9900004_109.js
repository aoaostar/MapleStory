var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
function start() {
    status = -1;
	 boat = cm.getEventManager("Boats");
	 train = cm.getEventManager("Trains");
	 flight = cm.getEventManager("Flight");
	 geenie = cm.getEventManager("Geenie");
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
    }var MC = cm.getServerName();
	
	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
   
    else if (status == 0) {
        var selStr = "#r#e航线状态#k#n \r\n";
		
		selStr += "\t\t#L1##b返回#l\r\n\r\n";
		if(boat.getProperty("entry").equals("true")) {
	   selStr += "#d\t\t[魔法密林] → [天空之城]: #g船来了#k\r\n";
	} else if(boat.getProperty("entry").equals("false") && boat.getProperty("docked").equals("true")) {
	   selStr += "#d\t\t[魔法密林] → [天空之城]: #g船来了,检票中#k\r\n";
	} else {
	   selStr += "#d\t\t[魔法密林] → [天空之城]: #r船未来#k\r\n";
	}
	    if(boat.getProperty("entry").equals("true")) {
	   selStr += "#d\t\t[天空之城] → [魔法密林]: #g船来了#k\r\n\r\n";
	} else if(boat.getProperty("entry").equals("false") && boat.getProperty("docked").equals("true")) {
	   selStr += "#d\t\t[天空之城] → [魔法密林]: #g船来了,检票中#k\r\n\r\n";
	} else {
	   selStr += "#d\t\t[天空之城] → [魔法密林]: #r船未来#k\r\n\r\n";
	}
		
		if (train.getProperty("entry").equals("true")) {
	     selStr += "#d\t\t[天空之城] → [玩 具 城]: #g船来了#k\r\n";
	} else if (train.getProperty("entry").equals("false") && train.getProperty("docked").equals("true")) {
	     selStr += "#d\t\t[天空之城] → [玩 具 城]: #g船来了,检票中#k\r\n";
	} else {
	     selStr += "#d\t\t[天空之城] → [玩 具 城]: #r船未来#k\r\n";
	}
	
	
	    if(train.getProperty("entry").equals("true")) {
	    selStr += "#d\t\t[玩 具 城] → [天空之城]: #g船来了#k\r\n\r\n";
	} else if(train.getProperty("entry").equals("false") && train.getProperty("docked").equals("true")) {
	    selStr += "#d\t\t[玩 具 城] → [天空之城]: #g船来了,检票中#k\r\n\r\n";
	} else {
	    selStr += "#d\t\t[玩 具 城] → [天空之城]: #r船未来#k\r\n\r\n";
	}
        
		if(flight.getProperty("entry").equals("true")) {
	    selStr += "#d\t\t[天空之城] → [神 木 村]: #g船来了#k\r\n";
	} else if(flight.getProperty("entry").equals("false") && flight.getProperty("docked").equals("true")) {
	    selStr += "#d\t\t[天空之城] → [神 木 村]: #g船来了,检票中#k\r\n";
	} else {
	    selStr += "#d\t\t[天空之城] → [神 木 村]: #r船未来#k\r\n";
	}
		if(flight.getProperty("entry").equals("true")) {
        selStr += "#d\t\t[神 木 村] → [天空之城]: #g船来了#k\r\n\r\n";
	} else if(flight.getProperty("entry").equals("false") && flight.getProperty("docked").equals("true")) {
	    selStr += "#d\t\t[神 木 村] → [天空之城]: #g船来了,检票中#k\r\n\r\n";
	} else {
	    selStr += "#d\t\t[神 木 村] → [天空之城]: #r船未来#k\r\n\r\n";
	}
	
	
	
	
	
		 if (geenie.getProperty("entry").equals("true")) {
	    selStr += "#d\t\t[天空之城] → [阿里安特]: #g船来了#k\r\n";
	} else if(geenie.getProperty("entry").equals("false") && geenie.getProperty("docked").equals("true")) {
	    selStr += "#d\t\t[天空之城] → [阿里安特]: #g船来了,检票中#k\r\n";
	} else {
	    selStr += "#d\t\t[天空之城] → [阿里安特]: #r船未来#k\r\n";
	}    
		if (geenie.getProperty("entry").equals("true")) {
	   selStr += "#d\t\t[阿里安特] → [天空之城]: #g船来了#k\r\n";
	} else if(geenie.getProperty("entry").equals("false") && geenie.getProperty("docked").equals("true")) {
	   selStr += "#d\t\t[阿里安特] → [天空之城]: #g船来了,检票中#k\r\n";
	} else {
	   selStr += "#d\t\t[阿里安特] → [天空之城]: #r船未来#k\r\n";
	}
		 
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 1:
            cm.dispose();
            cm.openNpc(9900002,9900004);	
             break;

		}
    }
}