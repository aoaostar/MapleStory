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
var 音乐点播 = "#fEffect/SkillName1.img/1001003/音乐点播#";
var 随身仓库 = "#fEffect/SkillName1.img/1001003/随身仓库#";
var 附属职业 = "#fEffect/SkillName1.img/1001003/附属职业#";
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
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }

	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var     selStr = " #e#r初级#k召唤术；#n \r\n";
	   selStr += " #L1##o100101# x 10#l  #L2##o130101# x 10#l   \r\n";


		
		  

		
	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 1:
		 if (cm.haveItem(4032225,20)&& cm.haveItem(4000000,50)) {	
             cm.gainItem(4032225,-20);
			 cm.gainItem(4000000,-50);
             cm.spawnMonster(100101,10);
             cm.setBossRankCount("召唤熟练度","10");    
			 cm.sendOk("召唤成功,熟练度 + 10");
             cm.dispose();
						
               }else{
            cm.sendOk("材料不够，无法召唤.\r\n需要；#i4032225# x 20,#i4000000# x 50");
			cm.dispose();	
			 break;}
             
		case 2:
		 if (cm.haveItem(4032225,20)&& cm.haveItem(4000016,50)) {	
             cm.gainItem(4032225,-20);
			 cm.gainItem(4000016,-50);
             cm.spawnMonster(130101,10);
             cm.setBossRankCount("召唤熟练度","10");    
			 cm.sendOk("召唤成功,熟练度 + 10");
             cm.dispose();
						
               }else{
            cm.sendOk("材料不够，无法召唤.\r\n需要；#i4032225# x 20,#i4000016# x 50");
			cm.dispose();	
			 break;}
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
		}
    }
}