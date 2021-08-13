var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);

var 鱼1 = "#fEffect/SkillName1.img/1001003/鱼1#";
var 鱼2 = "#fEffect/SkillName1.img/1001003/鱼2#";
var 鱼3 = "#fEffect/SkillName1.img/1001003/鱼3#";
var 鱼4 = "#fEffect/SkillName1.img/1001003/鱼4#";
var 鱼5 = "#fEffect/SkillName1.img/1001003/鱼5#";
var 鱼6 = "#fEffect/SkillName1.img/1001003/鱼6#";
var 鱼7 = "#fEffect/SkillName1.img/1001003/鱼7#";




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
        selStr = " #b#h ##k的金鱼养殖场。\r\n";
		selStr += "\r\n              "+鱼7+"\r\n\r\n";
		
		selStr += " 健康值；#B"+cm.getBossRank("鱼",2) +"# ["+cm.getBossRank("鱼",2) +"/100]\r\n\r\n";
		
		selStr += " #L1##r喂食#k#l \r\n\r\n";
		


		
	
	
	
	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {	 
		case 1://随身仓库,判定仓库等级
            if(cm.getHour()  == 5 && cm.getBossLog("鱼5") == 0) {
 		     cm.setBossLog("鱼5");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
	         cm.dispose();
			 
			 
		
	 } else if(cm.getHour()  == 6 && cm.getBossLog("鱼6") == 0 ) {
 		     cm.setBossLog("鱼6");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	
			 
			 
      } else if(cm.getHour()  ==7 && cm.getBossLog("鱼7") == 0 ) {
		   	 cm.setBossLog("鱼7");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
	 } else if(cm.getHour()  == 8 && cm.getBossLog("鱼8") == 0 ) {
 		     cm.setBossLog("鱼8");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	

			 
	 } else if(cm.getHour()  == 9 && cm.getBossLog("鱼9") == 0 ) {
 		     cm.setBossLog("鱼9");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	
			 
			 
     } else if(cm.getHour()  == 10 && cm.getBossLog("鱼10") == 0 ) {
 		     cm.setBossLog("鱼10");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
     } else if(cm.getHour()  == 11&& cm.getBossLog("鱼11") == 0 ) {
 		     cm.setBossLog("鱼11");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
	 } else if(cm.getHour()  == 12 && cm.getBossLog("鱼12") == 0) {
 		     cm.setBossLog("鱼12");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
     } else if(cm.getHour()  == 13&& cm.getBossLog("鱼13") == 0 ) {
 		     cm.setBossLog("鱼13");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
	 } else if(cm.getHour()  == 14 && cm.getBossLog("鱼14") == 0) {
 		     cm.setBossLog("鱼14");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	

			 
	 } else if(cm.getHour()  == 15&& cm.getBossLog("鱼15") == 0 ) {
 		     cm.setBossLog("鱼15");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	
			 
			 
     } else if(cm.getHour()  == 16&& cm.getBossLog("鱼16") == 0 ) {
 		     cm.setBossLog("鱼16");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
     } else if(cm.getHour()  == 17 && cm.getBossLog("鱼17") == 0) {
 		     cm.setBossLog("鱼17");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
	 } else if(cm.getHour()  == 18&& cm.getBossLog("鱼18") == 0 ) {
 		     cm.setBossLog("鱼18");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
     } else if(cm.getHour()  == 19 && cm.getBossLog("鱼19") == 0) {
 		     cm.setBossLog("鱼19");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
	 } else if(cm.getHour()  == 20 && cm.getBossLog("鱼20") == 0) {
 		     cm.setBossLog("鱼20");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
 		     cm.setBossLog("鱼20");cm.setBossRankCount("鱼");
             cm.dispose();
			 	

			 
	 } else if(cm.getHour()  == 21  && cm.getBossLog("鱼21") == 0) {
 		     cm.setBossLog("鱼21");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 	
			 
			 
     } else if(cm.getHour()  == 22  && cm.getBossLog("鱼22") == 0) {
 		     cm.setBossLog("鱼22");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 
			 
     } else if(cm.getHour()  == 23 && cm.getBossLog("鱼23") == 0) {
 		     cm.setBossLog("鱼23");cm.setBossRankCount("鱼");
			 cm.sendOk("金鱼吃到了美味的事物，很开心的游动着。");
             cm.dispose();
			 
			 	

			 
		}	else {
				cm.sendOk("鱼吃太多会撑死的哦，一个小时只能喂一次。");
				
             cm.dispose()
				return;
			}; 
			 break;
			 
			 

			 
			 
			 
			 
			 
		}
    }
}