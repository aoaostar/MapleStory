//////////////////////////////
//??*自由冒险岛*最具创意////
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
	    selStr = "\t      "+标题+"#r#e#k\r\n";

		selStr += "#b#L0#"+箭头+"上一页#l \r\n\r\n";
		selStr += "#b#L1#"+箭头+"民生银行#l  #b#L3#"+箭头+"点卷兑换#l  #b#L6#"+箭头+"航行状态#l\r\n";
		selStr += "#b#L100#"+箭头+"房屋租赁#l  #b#L4#"+箭头+"养殖中心#l  #b#L5#"+箭头+"个人信息#l\r\n";
		selStr += "#b#L7#"+箭头+"在线奖励#l\r\n";
		//selStr += "#b#L7#"+箭头+"在线奖励#l  #b#L8#"+箭头+"拍卖助手#l  #b#L9#"+箭头+"分身处理#l  #L10#"+箭头+"测试功能请别点击#l\r\n";

		
		
		
		
		
		
		//selStr += " #b#e#L0#"+枫叶天梯+"#l #L1#"+随身仓库+"#l #L2#"+音乐点播+"#l #L3#"+快捷商店+"#l\r\n";
		///selStr += " #L99995#"+试炼专区+"#l #L5#"+清理背包+"#l #L99997#"+锻造技艺+"#l #L7#"+任务手册+"#l \r\n";
		//selStr += " #L20#"+礼包商店+"#l #L21#"+现金商店+"#l #r#e#L23#"+至尊喇叭+"#l #L24#"+角色装扮+"#l\r\n";
		//selStr += " #L100000#"+快速转职+"#l #L99996#"+活动专区+"#l #L99994#"+天赋+"#l #L6#"+个人信息+"#l\r\n";

		
        //selStr += "\r\n\t\t\t\t\t\t\t\t\t\t\t  #L9999##r设置#l#k ";

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {

        case 0://
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
			
			
		case 1:
            cm.dispose();
            cm.openNpc(9900002,100);	
            break;
			
		case 3:
            cm.dispose();
            cm.openNpc(9310071);	
            break;
			
	    case 2:
            cm.dispose();
			//cm.sendOk("未开放，正在研发。");
            cm.openNpc(9900002,300);	
            break;
			
		case 4:
            cm.dispose();
            cm.openNpc(9900004,350);	
            break;
			
		case 5:
            cm.dispose();
            cm.openNpc(9900004,102);	
            break;
		case 6:
            cm.dispose();
            cm.openNpc(9900004,109);	
            break;
		case 7:
            cm.dispose();
            cm.openNpc(9900004,25);	
            break;
		case 8:
            cm.dispose();
            cm.openNpc(9900004,28);	
            break;
		case 9:
			//cm.setBossRankCount("克隆","-"+cm.getBossRank("克隆",2)+"");
			cm.playerMessage(5,"克隆体消失");
            cm.取消克隆();	
			cm.dispose();
            break;
		case 10:
            cm.dispose();
            cm.openNpc(9900004,6004);	
            break;
			
		case 100:
		//cm.sendOk("未开放，正在研发。");
          //  cm.dispose();
            cm.openNpc(9900004,666);	
            break;
				 
			 
			 
			} 
		}
    }
