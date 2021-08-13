//////////////////////////////
//ǟѦ*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////

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
var 锻造技艺 = "#fEffect/SkillName1.img/1001002/锻造技艺#";
var 枫叶天梯 = "#fEffect/SkillName1.img/1001002/枫叶天梯#";
var 个人信息 = "#fEffect/SkillName1.img/1001002/个人信息#";
var 公告专区 = "#fEffect/SkillName1.img/1001002/公告专区#";
var 活动专区 = "#fEffect/SkillName1.img/1001002/活动专区#";
var 角色装扮 = "#fEffect/SkillName1.img/1001002/角色装扮#";
var 今日签到 = "#fEffect/SkillName1.img/1001002/今日签到#";
var 快捷商店 = "#fEffect/SkillName1.img/1001002/快捷商店#";
var 快速转职 = "#fEffect/SkillName1.img/1001002/快速转职#";
var 礼包商店 = "#fEffect/SkillName1.img/1001002/礼包商店#";
var 清理背包 = "#fEffect/SkillName1.img/1001002/清理背包#";
var 任务手册 = "#fEffect/SkillName1.img/1001002/任务手册#";
var 试炼专区 = "#fEffect/SkillName1.img/1001002/试炼专区#";
var 随身仓库 = "#fEffect/SkillName1.img/1001002/随身仓库#";
var 天赋 = "#fEffect/SkillName1.img/1001002/天赋#";
var 现金商店 = "#fEffect/SkillName1.img/1001002/现金商店#";
var 音乐点播 = "#fEffect/SkillName1.img/1001002/音乐点播#";
var 至尊喇叭 = "#fEffect/SkillName1.img/1001002/至尊喇叭#";
var 自由传送 = "#fEffect/SkillName1.img/1001002/自由传送#";
var 标题 = "#fEffect/SkillName1.img/1001002/标题#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
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
	
	var MC = cm.getServerName();
    
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {

    var  
	    selStr = "\t      "+标题+"#b#k\r\n";
		
		selStr += "#L0##b"+箭头+" 返回界面#k#l\r\n";
		
		selStr += "#L1##b"+箭头+" 版权所有#k   \r\n";
		
	//	selStr += "#L2##b"+箭头+" 界面设置#k\r\n";
		
		selStr += "#L3##b"+箭头+" 枫叶天梯#k\r\n";
		
		selStr += "#L4##b"+箭头+" 任务手册#k   #L100##r"+箭头+" 游戏关闭后无法启动#k\r\n";


		


		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0://枫叶募集
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 100://
		    cm.sendOk("游戏关闭后无法启动；\r\n\r\n#b换发型，脸型，查看背包等出现客户端消失后再启动游戏无反应。\r\n#k#r这样一般是客户端文件显示不正常，闪退后无法启动需要打开任务管理器关闭Maplestory.exe程序便可以启动。");
            cm.dispose();
            break;
			
		case 1://
		    cm.sendOk("#b亲爱的#h ##k\r\n现在是；"+year+" 年 "+month+" 月 "+day+" 日 "+ hour+" 时\r\n\r\n#e所在的服；#r"+MC+"#k\r\n#e服来源于；#r自由冒险岛#k\r\n#e作者；#r七宝\r\n#k#e测试员；#r七宝");
            cm.dispose();
            break;
	    case 2://
		    cm.sendOk("关于主界面和传送界面的介绍；\r\n\r\n\t\t点击设置按键，可以关闭掉不常用的功能按键，能给玩家带来一种整洁的操作空间。目前#r主界面#k，#r自由传送#k都可以进行自定义。");
            cm.dispose();
            break;
		case 3://
		    cm.sendOk("关于枫叶天梯的介绍；\r\n\r\n\t\t在#r"+MC+"#k内，所有的怪物都会掉落枫叶，玩家提交枫叶可增加枫叶天梯榜积分，#r周一到周五，周日为可提交时间#k，而周六不可提交，周六是领取排名奖励的时间，#r奖励根据排行榜积分办法排名勋章。");
            cm.dispose();
            break;
			
		case 4://
		    cm.sendOk("关于任务手册的介绍；\r\n\r\n\t\t任务手册存在着#r日常任务，主线任务，天赋任务，支线任务#k等任务，玩家可选择性做支线任务，但是主线任务必须要做才能解锁下一个任务。一些特殊任务满足某些条件才会被触发。");
            cm.dispose();
            break;
		
	    
		
			 
			} 
		}
    }
