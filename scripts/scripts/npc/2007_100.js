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
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var selStr = "#n选择收集的区域；\r\n#r注意；一天只能选择一个收集任务，无法重复领取。#k\r\n";

          selStr += "#L1##b每日收集【金银岛】            #g★★☆☆☆#l#k  \r\n\r\n";
		  selStr += "#L2##b每日收集【水下世界】          #g★★★☆☆#l#k  \r\n\r\n";
		  selStr += "#L3##b每日收集【冰封雪域】          #g★★★☆☆#l#k  \r\n\r\n";
		  selStr += "#L4##b每日收集【武陵桃园】          #g★★☆☆☆#l#k  \r\n\r\n";
		  selStr += "#L5##b每日收集【尼哈沙漠】          #g★★☆☆☆#l#k  \r\n\r\n";
		  selStr += "#L6##b每日收集【米纳尔森林】        #g★★★★☆#l#k  \r\n\r\n";
		  selStr += "#L7##b每日收集【时间停止之湖】      #g★★★★★#l#k  \r\n\r\n";
		  
		
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		    case 1:
            cm.dispose();
			cm.openNpc(2007,101);
            break; 
			case 2:
            cm.dispose();
			cm.openNpc(2007,102);
            break;
            case 3:
            cm.dispose();
			cm.openNpc(2007,103);
            break; 
			case 4:
            cm.dispose();
			cm.openNpc(2007,104);
			break; 
			case 5:
            cm.dispose();
			cm.openNpc(2007,105);
            break;
            case 6:
            cm.dispose();
			cm.openNpc(2007,106);
            break; 
			case 7:
            cm.dispose();
			cm.openNpc(2007,107);


	  }
    }
}