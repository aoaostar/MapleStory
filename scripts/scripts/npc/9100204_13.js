var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 水果按键1 = "#fEffect/SkillName1.img/1001003/水果按键1#";
var 水果按键2 = "#fEffect/SkillName1.img/1001003/水果按键2#";
var 水果按键3 = "#fEffect/SkillName1.img/1001003/水果按键3#";
var 水果按键4 = "#fEffect/SkillName1.img/1001003/水果按键4#";
var 水果按键5 = "#fEffect/SkillName1.img/1001003/水果按键5#";
var 水果按键6 = "#fEffect/SkillName1.img/1001003/水果按键6#";
var 水果按键7 = "#fEffect/SkillName1.img/1001003/水果按键7#";
var 水果按键8 = "#fEffect/SkillName1.img/1001003/水果按键8#";
var 水果按键9 = "#fEffect/SkillName1.img/1001003/水果按键9#";
var 水果按键10 = "#fEffect/SkillName1.img/1001003/水果按键10#";
var 水果按键11 = "#fEffect/SkillName1.img/1001003/水果按键11#";
var 水果按键12 = "#fEffect/SkillName1.img/1001003/水果按键12#";

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
        var selStr = "自由冒险岛娱乐场水果机赔率介绍；\r\n";

        selStr += " "+水果按键1+"  x 2  \r\n";
		selStr += " "+水果按键2+"  x 2  \r\n";
		selStr += " "+水果按键3+"  x 3  \r\n";
		selStr += " "+水果按键4+"  x 3  \r\n";
		selStr += " "+水果按键5+"  x 4  \r\n";
		selStr += " "+水果按键6+"  x 4  \r\n";
        selStr += " "+水果按键11+"  x 5  \r\n";
		selStr += " "+水果按键12+"  x 8  \r\n";
		
		selStr += " "+水果按键8+"  x 10  \r\n";
		selStr += " "+水果按键7+"  x 15  \r\n";

		selStr += " "+水果按键9+"  x 25  \r\n";
		selStr += " "+水果按键10+"  x 50 \r\n";

		


			   
			  
		 
		 
	   
		
		   	
 
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
  			
			

		}
    }
}