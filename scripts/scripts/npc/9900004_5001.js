var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 开 = "#fEffect/SkillName1.img/1001003/开#";
var 关 = "#fEffect/SkillName1.img/1001003/关#";







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
    }var MC = cm.getServerName();
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
        var 
		selStr = "#e#r"+MC+"#k#n主界面控制区域；\r\n";
        selStr += "#L1314##r返回界面#k#l\r\n";

		
		if (cm.getBossRank("随身仓库",2)  == 0){
		selStr += "#L2#随身仓库#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("随身仓库",2)  == 1){
		selStr += "#L2#随身仓库#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1002#开通随身仓库#l#k\r\n";}
		
		
		if (cm.getBossRank("音乐点播",2)  == 0){
		selStr += "#L3#音乐点播#g【已开启】#l#k"; }
		else if (cm.getBossRank("音乐点播",2)  == 1){
		selStr += "#L3#音乐点播#r【已关闭】#l#k"; }
		else{selStr += "#d#L1003#开通音乐点播#l#k";}
		
		
		if (cm.getBossRank("快捷商店",2)  == 0){
		selStr += "#L4#快捷商店#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("快捷商店",2)  == 1){
		selStr += "#L4#快捷商店#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1004#开通快捷商店#l#k\r\n";}
		
		
		if (cm.getBossRank("试炼专区",2)  == 0){
		selStr += "#L5#试炼专区#g【已开启】#l#k"; }
		else if (cm.getBossRank("试炼专区",2)  == 1){
		selStr += "#L5#试炼专区#r【已关闭】#l#k"; }
		else{selStr += "#d#L1005#开通试炼专区#l#k";}
		
		
		if (cm.getBossRank("清理背包",2)  == 0){
		selStr += "#L6#清理背包#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("清理背包",2)  == 1){
		selStr += "#L6#清理背包#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1006#开通清理背包#l#k\r\n";}
		
		
		if (cm.getBossRank("锻造技艺",2)  == 0){
		selStr += "#L7#锻造技艺#g【已开启】#l#k"; }
		else if (cm.getBossRank("锻造技艺",2)  == 1){
		selStr += "#L7#锻造技艺#r【已关闭】#l#k"; }
		else{selStr += "#d#L1007#开通锻造技艺#l#k";}
		
		
		if (cm.getBossRank("任务手册",2)  == 0){
		selStr += "#L8#任务手册#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("任务手册",2)  == 1){
		selStr += "#L8#任务手册#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1008#开通任务手册#l#k\r\n";}
		
		if (cm.getBossRank("礼包商店",2)  == 0){
		selStr += "#L9#礼包商店#g【已开启】#l#k"; }
		else if (cm.getBossRank("礼包商店",2)  == 1){
		selStr += "#L9#礼包商店#r【已关闭】#l#k"; }
		else{selStr += "#d#L1009#开通礼包商店#l#k";}
		
		if (cm.getBossRank("现金商店",2)  == 0){
		selStr += "#L10#现金商店#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("现金商店",2)  == 1){
		selStr += "#L10#现金商店#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1010#开通现金商店#l#k\r\n";}
		
		
		if (cm.getBossRank("至尊喇叭",2)  == 0){
		selStr += "#L11#至尊喇叭#g【已开启】#l#k"; }
		else if (cm.getBossRank("至尊喇叭",2)  == 1){
		selStr += "#L11#至尊喇叭#r【已关闭】#l#k"; }
		else{selStr += "#d#L1011#开通至尊喇叭#l#k";}
		
		if (cm.getBossRank("角色装扮",2)  == 0){
		selStr += "#L12#角色装扮#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("角色装扮",2)  == 1){
		selStr += "#L12#角色装扮#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1012#开通角色装扮#l#k\r\n";}
		
		
		if (cm.getBossRank("快速转职",2)  == 0){
		selStr += "#L13#快速转职#g【已开启】#l#k"; }
		else if (cm.getBossRank("快速转职",2)  == 1){
		selStr += "#L13#快速转职#r【已关闭】#l#k"; }
		else{selStr += "#d#L1013#开通快速转职#l#k";}
		
		
		if (cm.getBossRank("活动专区",2)  == 0){
		selStr += "#L14#活动专区#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("活动专区",2)  == 1){
		selStr += "#L14#活动专区#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1014#开通活动专区#l#k\r\n";}
		
		if (cm.getBossRank("个人信息",2)  == 0){
		selStr += "#L16#个人信息#g【已开启】#l#k"; }
		else if (cm.getBossRank("个人信息",2)  == 1){
		selStr += "#L16#个人信息#r【已关闭】#l#k"; }
		else{selStr += "#d#L1016#开通个人信息#l#k";}
		
		if (cm.getBossRank("天赋",2)  == 0){
		selStr += "#L15#天赋#g【已开启】#l#k"; }
		else if (cm.getBossRank("天赋",2)  == 1){
		selStr += "#L15#天赋#r【已关闭】#l#k"; }
		else{selStr += "#d#L1015#开通天赋#l#k";}
		

		

	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {	
		
		case 0:
		   if (cm.getBossRank("二级传送",2)  == 0){
		cm.setBossRankCount("二级传送","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("二级传送",2)  == 1){
		cm.setBossRankCount("二级传送","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1000:
		cm.setBossRankCount("二级传送","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 1:
		   if (cm.getBossRank("枫叶天梯",2)  == 0){
		cm.setBossRankCount("枫叶天梯","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("枫叶天梯",2)  == 1){
		cm.setBossRankCount("枫叶天梯","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1001:
		cm.setBossRankCount("枫叶天梯","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 2:
		   if (cm.getBossRank("随身仓库",2)  == 0){
		cm.setBossRankCount("随身仓库","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("随身仓库",2)  == 1){
		cm.setBossRankCount("随身仓库","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1002:
		cm.setBossRankCount("随身仓库","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		
		case 3:
		   if (cm.getBossRank("音乐点播",2)  == 0){
		cm.setBossRankCount("音乐点播","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("音乐点播",2)  == 1){
		cm.setBossRankCount("音乐点播","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1003:
		cm.setBossRankCount("音乐点播","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 4:
		   if (cm.getBossRank("快捷商店",2)  == 0){
		cm.setBossRankCount("快捷商店","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("快捷商店",2)  == 1){
		cm.setBossRankCount("快捷商店","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1004:
		cm.setBossRankCount("快捷商店","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 5:
		   if (cm.getBossRank("试炼专区",2)  == 0){
		cm.setBossRankCount("试炼专区","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("试炼专区",2)  == 1){
		cm.setBossRankCount("试炼专区","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1005:
		cm.setBossRankCount("试炼专区","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 6:
		   if (cm.getBossRank("清理背包",2)  == 0){
		cm.setBossRankCount("清理背包","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("清理背包",2)  == 1){
		cm.setBossRankCount("清理背包","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1006:
		cm.setBossRankCount("清理背包","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 7:
		   if (cm.getBossRank("锻造技艺",2)  == 0){
		cm.setBossRankCount("锻造技艺","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("锻造技艺",2)  == 1){
		cm.setBossRankCount("锻造技艺","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1007:
		cm.setBossRankCount("锻造技艺","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 8:
		   if (cm.getBossRank("任务手册",2)  == 0){
		cm.setBossRankCount("任务手册","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("任务手册",2)  == 1){
		cm.setBossRankCount("任务手册","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1008:
		cm.setBossRankCount("任务手册","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 9:
		   if (cm.getBossRank("礼包商店",2)  == 0){
		cm.setBossRankCount("礼包商店","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("礼包商店",2)  == 1){
		cm.setBossRankCount("礼包商店","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1009:
		cm.setBossRankCount("礼包商店","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 10:
		   if (cm.getBossRank("现金商店",2)  == 0){
		cm.setBossRankCount("现金商店","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("现金商店",2)  == 1){
		cm.setBossRankCount("现金商店","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1010:
		cm.setBossRankCount("现金商店","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 11:
		   if (cm.getBossRank("至尊喇叭",2)  == 0){
		cm.setBossRankCount("至尊喇叭","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("至尊喇叭",2)  == 1){
		cm.setBossRankCount("至尊喇叭","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1011:
		cm.setBossRankCount("至尊喇叭","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 12:
		   if (cm.getBossRank("角色装扮",2)  == 0){
		cm.setBossRankCount("角色装扮","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("角色装扮",2)  == 1){
		cm.setBossRankCount("角色装扮","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1012:
		cm.setBossRankCount("角色装扮","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
		case 13:
		   if (cm.getBossRank("快速转职",2)  == 0){
		cm.setBossRankCount("快速转职","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("快速转职",2)  == 1){
		cm.setBossRankCount("快速转职","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1013:
		cm.setBossRankCount("快速转职","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 14:
		   if (cm.getBossRank("活动专区",2)  == 0){
		cm.setBossRankCount("活动专区","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("活动专区",2)  == 1){
		cm.setBossRankCount("活动专区","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1014:
		cm.setBossRankCount("活动专区","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 15:
		   if (cm.getBossRank("天赋",2)  == 0){
		cm.setBossRankCount("天赋","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("天赋",2)  == 1){
		cm.setBossRankCount("天赋","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1015:
		cm.setBossRankCount("天赋","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		case 16:
		   if (cm.getBossRank("个人信息",2)  == 0){
		cm.setBossRankCount("个人信息","1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		else if (cm.getBossRank("个人信息",2)  == 1){
		cm.setBossRankCount("个人信息","-1");
	    cm.dispose();
		cm.openNpc(9900004,5001);}
		break;
		case 1016:
		cm.setBossRankCount("个人信息","1");
		cm.dispose();
		cm.openNpc(9900004,5001);
		break;
		
		
			
		case 1314:
         cm.dispose();
            cm.openNpc(9900004,0);	
             break;	 
			 

			 
			 
			 
			 
			 
		}
    }
}