var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 传送图 = "#fEffect/SkillName1.img/1001003/传送图#";
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
	//if (cm.getQuestStatus(21728) == 0) {
          //  cm.sendOk("你未习得此点歌天赋。");
           // cm.dispose();
       // }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var 
		 
		 selStr = " "+传送图+"\r\n\r\n\r\n"; 
		
		
		selStr += "#L1#扎昆入口#l  "; 
		selStr += "#L2#时间本源#l  "; 
		selStr += "#L3#喷 火 龙#l  "; 
		selStr += "#L4#芬多森林#l  "; 
		selStr += "#L5#暴 力 熊#l  "; 
		selStr += "#L6#暗黑龙王#l  "; 
		selStr += "#L7#神的黄昏#l  "; 
		

	
 
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
			
			case 1:
			cm.warp(211042300,0);
			cm.dispose();
            break;
			
			case 2:
			cm.warp(220080000,0);
			cm.dispose();
            break;
			
			case 3:
			cm.warp(240020402,0);
			cm.dispose();
            break;
			
			case 4:
			cm.warp(240020101,0);
			cm.dispose();
            break;
			
			case 5:
			cm.warp(551030100,0);
			cm.dispose();
            break;
			
			case 6:
			cm.warp(240050400,0);
			cm.dispose();
            break;
			
			case 7:
			cm.warp(270050000,0);
			cm.dispose();
            break;

		
			
		}
    }
}
