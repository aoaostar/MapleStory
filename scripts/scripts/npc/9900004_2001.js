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
	    if (cm.getQuestStatus(21728) == 0) {
            cm.sendOk("你未习得此召唤天赋。");
            cm.dispose();
        }

	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var 
		selStr = " #L1#究极召唤术 - 暗黑龙王#l \r\n\r\n";
		  

		  

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 1:
             cm.dispose();
			 cm.spawnMonster(8810026);
			 cm.mapMessage("究极召唤术 - 暗黑龙王");
			 cm.changeMusic("Bgm14/HonTale");
    
    
            break;
		
		}
    }
}