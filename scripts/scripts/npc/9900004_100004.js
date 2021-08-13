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
var 一 = "#fUI/GuildMark.img/Mark/Letter/00005027/15#";
var 一 = "#fUI/GuildMark.img/Mark/Letter/00005027/15#";
var 二 = "#fUI/GuildMark.img/Mark/Letter/00005028/15#";
var 三 = "#fUI/GuildMark.img/Mark/Letter/00005029/15#";
var 四 = "#fUI/GuildMark.img/Mark/Letter/00005030/15#";
var 五 = "#fUI/GuildMark.img/Mark/Letter/00005031/15#";
var 六 = "#fUI/GuildMark.img/Mark/Letter/00005032/15#";
var 七 = "#fUI/GuildMark.img/Mark/Letter/00005033/15#";
var 零 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";
var 九 = "#fUI/GuildMark.img/Mark/Letter/00005035/15#";
var 七 = "#fUI/GuildMark.img/Mark/Letter/00005033/15#";
var 零 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";


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
	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var selStr = ""+零+""+零+""+五+"号小屋信息\r\n\r\n"
 
          selStr += "小屋户主；#r暂未出租#k\r\n";
		  
		//  selStr += "  #L3##b◇清理背包#l#k #L4##b◇游山玩水#l#k #L5##b◇活跃成就#l#k  \r\n\r\n";
		   
		 // selStr += "  #L6##b◇全服弹窗#l#k #L7##b◇快速转职#l#k #L8##b◇私人公寓#l#k  \r\n\r\n";
		cm.sendSimple(selStr);
    
    }
}