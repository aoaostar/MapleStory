//////////////////////////////
//�߱�*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/1#";
var Y = "#fUI/GuildMark.img/Mark/Letter/00005024/3#";
var X = "#fUI/GuildMark.img/Mark/Letter/00005023/1#";
var D = "#fUI/GuildMark.img/Mark/Letter/00005003/1#";
var M = "#fUI/GuildMark.img/Mark/Letter/00005012/1#";
var A = "#fUI/GuildMark.img/Mark/Letter/00005000/1#";
var P = "#fUI/GuildMark.img/Mark/Letter/00005015/1#";
var 红色 = "#fUI/NameTag.img/126/w#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";


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
	var MC = cm.getServerName();
	var jy = cm.getPlayer().getExp();
	 var jb = cm.getMeso();
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "#e#r"+MC+"#k锻造之兵法手册制作工艺；\r\n";
		
		selStr += "[你当前拥有经验值； #d"+jy+"#k ]\r\n";
		selStr += "[你当前拥有兵法卷； #d#c 4001028##k ]\r\n";
	//	selStr += "[你当前拥有金  币； #d"+jb+"#k ]\r\n";
		
		
		selStr += "#e#L1#"+箭头+"制造兵法 #i2370012# #t2370012#/#rEXP100#k#l\r\n";
		if(cm.getBossRank("锻造熟练度",2) > 500) {
		selStr += "#e#L2#"+箭头+"制造兵法 #i2370009# #t2370009#/#rEXP500#k#l\r\n";
		 }else {}
		 
		if(cm.getBossRank("锻造熟练度",2) > 2000) {
		selStr += "#e#L3#"+箭头+"制造兵法 #i2370005# #t2370005#/#rEXP5000#k#l\r\n";
		 }else {}
		
		if(cm.getBossRank("锻造熟练度",2) > 3500) {
		selStr += "#e#L4#"+箭头+"制造兵法 #i2370003# #t2370003#/#rEXP20000#k#l\r\n";
		 }else {}
		
		if(cm.getBossRank("锻造熟练度",2) > 7500) {
		selStr += "#e#L5#"+箭头+"制造兵法 #i2370000# #t2370000#/#rEXP100000#k#l\r\n";
		 }else {}
		
	//	selStr += "#e#L0#"+箭头+"返回界面#l\r\n";
		

		
		

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
		if ( cm.getPlayer().getExp()>=200 & cm.haveItem(4001028,1)){ 
		    cm.setBossRankCount("锻造熟练度");
		    cm.gainExp(-100);
		    cm.gainItem(2370012,1);
			cm.gainItem(4001028,-1);
			cm.sendOk("制作成功，熟练度+1");
            cm.dispose();
 	
			} else {
		  cm.sendOk("经验值/200,#i4001028# *1");
		  cm.dispose();
		  }
            break;
		case 2:
		if ( cm.getPlayer().getExp()>=1000 & cm.haveItem(4001028,1)){ 
		    cm.setBossRankCount("锻造熟练度",2);
		    cm.gainExp(-1000);
		    cm.gainItem(2370009,1);
			cm.gainItem(4001028,-1);
			cm.sendOk("制作成功，熟练度+2");
            cm.dispose();
 	
			} else {
		  cm.sendOk("经验值/1000,#i4001028# *1");
		  cm.dispose();
		  }
            break;
		case 3:
		if ( cm.getPlayer().getExp()>=10000 & cm.haveItem(4001028,1)){ 
		    cm.setBossRankCount("锻造熟练度",4);
		    cm.gainExp(-10000);
		    cm.gainItem(2370005,1);
			cm.gainItem(4001028,-1);
			cm.sendOk("制作成功，熟练度+4");
            cm.dispose();
 	
			} else {
		  cm.sendOk("经验值/10000,#i4001028# *1");
		  cm.dispose();
		  }
            break;
		case 4:
		if ( cm.getPlayer().getExp()>=40000 & cm.haveItem(4001028,1)){ 
		    cm.setBossRankCount("锻造熟练度",7);
		    cm.gainExp(-40000);
		    cm.gainItem(2370003,1);
			cm.gainItem(4001028,-1);
			cm.sendOk("制作成功，熟练度+7");
            cm.dispose();
 	
			} else {
		  cm.sendOk("经验值/40000,#i4001028# *1");
		  cm.dispose();
		  }
            break;
		case 5:
		if ( cm.getPlayer().getExp()>=200000 & cm.haveItem(4001028,1)){ 
		    cm.setBossRankCount("锻造熟练度",15);
		    cm.gainExp(-200000);
		    cm.gainItem(2370000,1);
			cm.gainItem(4001028,-1);
			cm.sendOk("制作成功，熟练度+15");
            cm.dispose();
 	
			} else {
		  cm.sendOk("经验值/200000,#i4001028# *1");
		  cm.dispose();
		  }
            break;
		 	
	 
			 
			 
			 
		}
    }
}
