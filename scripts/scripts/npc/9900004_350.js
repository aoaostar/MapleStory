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
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/9#";
var 九 = "#fUI/GuildMark.img/Mark/Letter/00005035/15#";
var 七 = "#fUI/GuildMark.img/Mark/Letter/00005033/15#";
var 零 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";
var 鱼1 = "#fEffect/SkillName1.img/1001003/鱼1#";




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
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
        var 

		selStr = " #L0#金鱼#l\r\n\r\n";
		


		
	
	
	
	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {	 
		case 0://随身仓库,判定仓库等级
            if(cm.getBossRank("鱼",2) >= 0 ) {
	         cm.dispose();
			 cm.openNpc(9900004,351);
	 } else if(cm.getBossRank("鱼",2) > 50 ) {
             cm.dispose();
			 cm.openNpc(9900004,352);
	 } else if(cm.getBossRank("鱼",2) > 100 ) {
             cm.dispose();
			 cm.openNpc(9900004,353);		 
	 } else if(cm.getBossRank("鱼",2) > 220 ) {
             cm.dispose();
			 cm.openNpc(9900004,354);	
     } else if(cm.getBossRank("鱼",2) > 400 ) {
             cm.dispose();
			 cm.openNpc(9900004,355);
     } else if(cm.getBossRank("鱼",2) > 600 ) {
             cm.dispose();
			 cm.openNpc(9900004,356);
	 } else if(cm.getBossRank("鱼",2) > 900 ) {
             cm.dispose();
			 cm.openNpc(9900004,357);		 			 
		}	else {
				cm.sendOk("领取鱼苗成功~");
				cm.setBossRankCount("鱼","0");
             cm.dispose()
				return;
			}; 
			 break;
			 
			 

			 
			 
			 
			 
			 
		}
    }
}