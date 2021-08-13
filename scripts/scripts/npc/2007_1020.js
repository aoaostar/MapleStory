var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
//var tz = "#fEffect/CharacterEff/1082565/4/0#";  //兔子粉

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
        var 

 
	  
		  selStr = "每日跑环详细说明；\r\n\r\n"; 
		 
         selStr += "#r跑环环数;#k20\r\n";
		 
		 selStr += "#r每环奖励;#k20W经验值，10瓶超级药水。20W金币。 \r\n";
		 
		 selStr += "#r第10环奖励;#k5张百宝券，100点券。 \r\n";
		 
		 selStr += "#r第20环奖励;#k5张百宝券，500点券，100W金币，100瓶超级药水 \r\n";
		 
		 selStr += "#r注意；每日凌晨将会重置跑环环数。 #k\r\n";
		 
		 
		 
		 
	   
		
		  

 
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
            cm.dispose();
			cm.openNpc(2007,1020);
            break; 
			
			


		}
    }
}