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
        var selStr = "#e#r┏#k━━━━━━━━━━━━━━━━━━━━#r┓#k\r\n             -★☆★☆★☆★☆-\r\n#b    自     #r 由   #g   冒  #d    险      #k岛   #k\r\n  #n                                \r\n#r┗#k━━━━━━━━━━━━━━━━━━━━━━#r┛#k#n\r\r\n";
  selStr += "   现在时间：#r"+year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分"+second+"秒#k\r\n   点券余额：#r"+cm.getPlayer().getCSPoints(1)+"\r\n";
          selStr += "  #L0##b◇瞬间传送#l#k #L1##b◇每日奖励#l#k #L2#◇进入活动#l#k \r\n\r\n";
		  
		  selStr += "  #L3##b◇清理背包#l#k #L4##b◇游山玩水#l#k #L5##b◇活跃热度#l#k  \r\n\r\n";
		   
		  selStr += "  #L6##b◇全服弹窗#l#k #L7##b◇快速转职#l#k  \r\n\r\n";
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
			cm.openNpc(9900004,1);
            break;
		case 1:
            cm.dispose();
            cm.openNpc(9900004,4);	
             break;
		case 2:
            cm.dispose();
            cm.openNpc(9900004,99);	
             break;
		case 3:
            cm.dispose();
            cm.openNpc(9900004,98);	
             break;
		case 4:
            cm.dispose();
            cm.openNpc(9900004,900);	
             break;
			 		case 5:
            cm.dispose();
            cm.openNpc(9900004,102);	
             break;
			 break;
			 		case 6:
            cm.dispose();
            cm.openNpc(9900004,101);	
             break;
			 break;
			 		case 7:
            cm.dispose();
            cm.openNpc(9900004,200);	
             break;
		}
    }
}