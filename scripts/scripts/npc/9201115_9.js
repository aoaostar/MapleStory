var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 雪屋天球 = "#fEffect/SkillName1.img/1001003/雪屋天球#";
var 每日收集 = "#fEffect/SkillName1.img/1001003/每日收集#";
var 每日跑商 = "#fEffect/SkillName1.img/1001003/每日跑商#";
var 武陵塔 = "#fEffect/SkillName1.img/1001003/武陵塔#";
var 跳跳活动 = "#fEffect/SkillName1.img/1001003/跳跳活动#";
var 枫叶排行 = "#fEffect/SkillName1.img/1001003/枫叶排行#";


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
	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var  selStr = "┏━━━━━━━━━━━━━━━━━━━━━━━━┓\r\n\r\n";
		
	    
		selStr += "#L1#收取领地奖励#l\r\r\n";
		  

selStr += "#k\r\n\r\n┗━━━━━━━━━━━━━━━━━━━━━━━━┛#k#n\r\r\n";
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
			
			 
			case 1:
			cm.sendOk("暂未设置");
            cm.dispose();
			
            break;

		}
    }
}