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
var 返回市场 = "#fEffect/SkillName1.img/1001003/返回市场#";
var 远程传送 = "#fEffect/SkillName1.img/1001003/远程传送#";
var 活跃成就 = "#fEffect/SkillName1.img/1001003/活跃成就#";
var 清理背包 = "#fEffect/SkillName1.img/1001003/清理背包#";
var 快捷商店 = "#fEffect/SkillName1.img/1001003/快捷商店#";
var 快速转职 = "#fEffect/SkillName1.img/1001003/快速转职#";
var 签到 = "#fEffect/SkillName1.img/1001003/签到#";

var rank;

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
  
    else if (status == 0)
		{
        var selStr = "相信枫叶的传承，相信枫叶的美。\r\n\r\n";

		selStr += " #L1##i4001126##i4001126##i4001126##i4001126##l  \r\n\r\n";
		

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 1:
            cm.dispose();
            cm.openNpc(2007,2001);	
             break;
		case 2:

            cm.dispose();
            cm.openNpc(2007,2002);	
             break;
		case 3:
            cm.dispose();
            cm.openNpc(1052014,0);	
             break;
		case 4:
            cm.dispose();
            cm.openNpc(9900004,98);	
             break;
			 		case 5:
            cm.dispose();
            cm.openNpc(9900004,200);	
             break;
			 		case 6:
            cm.dispose();
            cm.openNpc(9900004,4);	
             break;
			 		case 7:
            cm.dispose();
            cm.openNpc(2007,2000);	
             break;
			 		case 8:
            cm.dispose();
            cm.openNpc(9900004,666);	
             break;
		}
    }
}