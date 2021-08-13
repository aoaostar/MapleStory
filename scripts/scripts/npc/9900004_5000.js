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
    }
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
        var 
         selStr = "#L1314##r返回传送#k#l\r\n";
		if (cm.getBossRank("废弃都市",2)  == 0){
		selStr += "#L1#废弃都市#g【已开启】#l#k"; }
		else if (cm.getBossRank("废弃都市",2)  == 1){
		selStr += "#L1#废弃都市#r【已关闭】#l#k"; }
		else{selStr += "#d#L1001#开通废弃都市#l#k";}
			
		
		if (cm.getBossRank("勇士部落",2)  == 0){
		selStr += "#L2#勇士部落#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("勇士部落",2)  == 1){
		selStr += "#L2#勇士部落#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1002#开通勇士部落#l#k\r\n";}
		
		
		if (cm.getBossRank("魔法密林",2)  == 0){
		selStr += "#L3#魔法密林#g【已开启】#l#k"; }
		else if (cm.getBossRank("魔法密林",2)  == 1){
		selStr += "#L3#魔法密林#r【已关闭】#l#k"; }
		else{selStr += "#d#L1003#开通魔法密林#l#k";}
		
		
		if (cm.getBossRank("林中之城",2)  == 0){
		selStr += "#L4#林中之城#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("林中之城",2)  == 1){
		selStr += "#L4#林中之城#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1004#开通林中之城#l#k\r\n";}
		
		
		if (cm.getBossRank("射手村",2)  == 0){
		selStr += "#L5#射手之村#g【已开启】#l#k"; }
		else if (cm.getBossRank("射手村",2)  == 1){
		selStr += "#L5#射手之村#r【已关闭】#l#k"; }
		else{selStr += "#d#L1005#开通射手之村#l#k";}
		
		
		if (cm.getBossRank("明珠港",2)  == 0){
		selStr += "#L6#明珠之港#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("明珠港",2)  == 1){
		selStr += "#L6#明珠之港#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1006#开通明珠之港#l#k\r\n";}
		
		
		if (cm.getBossRank("玩具城",2)  == 0){
		selStr += "#L7#玩具之城#g【已开启】#l#k"; }
		else if (cm.getBossRank("玩具城",2)  == 1){
		selStr += "#L7#玩具之城#r【已关闭】#l#k"; }
		else{selStr += "#d#L1007#开通玩具之城#l#k";}
		
		
		if (cm.getBossRank("海底世界",2)  == 0){
		selStr += "#L8#海底世界#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("海底世界",2)  == 1){
		selStr += "#L8#海底世界#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1008#开通海底世界#l#k\r\n";}
		
		if (cm.getBossRank("神木之村",2)  == 0){
		selStr += "#L9#神木之村#g【已开启】#l#k"; }
		else if (cm.getBossRank("神木之村",2)  == 1){
		selStr += "#L9#神木之村#r【已关闭】#l#k"; }
		else{selStr += "#d#L1009#开通神木之村#l#k";}
		
		if (cm.getBossRank("乌鲁之城",2)  == 0){
		selStr += "#L10#乌鲁之城#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("乌鲁之城",2)  == 1){
		selStr += "#L10#乌鲁之城#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1010#开通乌鲁之城#l#k\r\n";}
		
		
		if (cm.getBossRank("少林之寺",2)  == 0){
		selStr += "#L11#少林之寺#g【已开启】#l#k"; }
		else if (cm.getBossRank("少林之寺",2)  == 1){
		selStr += "#L11#少林之寺#r【已关闭】#l#k"; }
		else{selStr += "#d#L1011#开通少林之寺#l#k";}
		
		if (cm.getBossRank("新叶之城",2)  == 0){
		selStr += "#L12#新叶之城#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("新叶之城",2)  == 1){
		selStr += "#L12#新叶之城#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1012#开通新叶之城#l#k\r\n";}
		
		
		if (cm.getBossRank("百草之堂",2)  == 0){
		selStr += "#L13#百草之堂#g【已开启】#l#k"; }
		else if (cm.getBossRank("百草之堂",2)  == 1){
		selStr += "#L13#百草之堂#r【已关闭】#l#k"; }
		else{selStr += "#d#L1013#开通百草之堂#l#k";}
		
		
		if (cm.getBossRank("地球本部",2)  == 0){
		selStr += "#L14#地球本部#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("地球本部",2)  == 1){
		selStr += "#L14#地球本部#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1014#开通地球本部#l#k\r\n";}
		
		
		if (cm.getBossRank("吉隆都市",2)  == 0){
		selStr += "#L15#吉隆都市#g【已开启】#l#k"; }
		else if (cm.getBossRank("吉隆都市",2)  == 1){
		selStr += "#L15#吉隆都市#r【已关闭】#l#k"; }
		else{selStr += "#d#L1015#开通吉隆都市#l#k";}
		
		if (cm.getBossRank("天空之城",2)  == 0){
		selStr += "#L16#天空之城#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("天空之城",2)  == 1){
		selStr += "#L16#天空之城#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1016#开通天空之城#l#k\r\n";}
		
		if (cm.getBossRank("冰峰雪域",2)  == 0){
		selStr += "#L17#冰峰雪域#g【已开启】#l#k"; }
		else if (cm.getBossRank("冰峰雪域",2)  == 1){
		selStr += "#L17#冰峰雪域#r【已关闭】#l#k"; }
		else{selStr += "#d#L1017#开通冰峰雪域#l#k";}
		
		if (cm.getBossRank("艾林森林",2)  == 0){
		selStr += "#L18#艾林森林#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("艾林森林",2)  == 1){
		selStr += "#L18#艾林森林#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1018#开通艾林森林#l#k\r\n";}

		 
		if (cm.getBossRank("阿里安特",2)  == 0){
		selStr += "#L19#阿里安特#g【已开启】#l#k"; }
		else if (cm.getBossRank("阿里安特",2)  == 1){
		selStr += "#L19#阿里安特#r【已关闭】#l#k"; }
		else{selStr += "#d#L1019#开通阿里安特#l#k";}
		 
		 
	    if (cm.getBossRank("马加提亚",2)  == 0){
		selStr += "#L20#马加提亚#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("马加提亚",2)  == 1){
		selStr += "#L20#马加提亚#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1020#开通马加提亚#l#k\r\n";}

 
		if (cm.getBossRank("武陵桃园",2)  == 0){
		selStr += "#L21#武陵桃园#g【已开启】#l#k"; }
		else if (cm.getBossRank("武陵桃园",2)  == 1){
		selStr += "#L21#武陵桃园#r【已关闭】#l#k"; }
		else{selStr += "#d#L1021#开通武陵桃园#l#k";}
		
	
	    if (cm.getBossRank("甘榜之村",2)  == 0){
		selStr += "#L22#甘榜之村#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("甘榜之村",2)  == 1){
		selStr += "#L22#甘榜之村#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1022#开通甘榜之村#l#k\r\n";}
	
	
	    if (cm.getBossRank("昭和之村",2)  == 0){
		selStr += "#L23#昭和之村#g【已开启】#l#k"; }
		else if (cm.getBossRank("昭和之村",2)  == 1){
		selStr += "#L23#昭和之村#r【已关闭】#l#k"; }
		else{selStr += "#d#L1023#开通昭和之村#l#k";}
	
	     
		if (cm.getBossRank("新新加坡",2)  == 0){
		selStr += "#L24#新新加坡#g【已开启】#l#k\r\n"; }
		else if (cm.getBossRank("新新加坡",2)  == 1){
		selStr += "#L24#新新加坡#r【已关闭】#l#k\r\n"; }
		else{selStr += "#d#L1024#开通新新加坡#l#k\r\n";}
		
		
	    if (cm.getBossRank("二级传送",2)  == 0){
		selStr += "#L0#二级传送#g【已开启】#l#k"; }
		else if (cm.getBossRank("二级传送",2)  == 1){
		selStr += "#L0#二级传送#r【已关闭】#l#k"; }
		else{selStr += "#d#L1000#开通二级传送#l#k";}
	

	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {	
		
		case 0:
		   if (cm.getBossRank("二级传送",2)  == 0){
		cm.setBossRankCount("二级传送","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("二级传送",2)  == 1){
		cm.setBossRankCount("二级传送","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1000:
		cm.setBossRankCount("二级传送","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 1:
		   if (cm.getBossRank("废弃都市",2)  == 0){
		cm.setBossRankCount("废弃都市","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("废弃都市",2)  == 1){
		cm.setBossRankCount("废弃都市","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1001:
		cm.setBossRankCount("废弃都市","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 2:
		   if (cm.getBossRank("勇士部落",2)  == 0){
		cm.setBossRankCount("勇士部落","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("勇士部落",2)  == 1){
		cm.setBossRankCount("勇士部落","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1002:
		cm.setBossRankCount("勇士部落","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		
		case 3:
		   if (cm.getBossRank("魔法密林",2)  == 0){
		cm.setBossRankCount("魔法密林","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("魔法密林",2)  == 1){
		cm.setBossRankCount("魔法密林","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1003:
		cm.setBossRankCount("魔法密林","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 4:
		   if (cm.getBossRank("林中之城",2)  == 0){
		cm.setBossRankCount("林中之城","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("林中之城",2)  == 1){
		cm.setBossRankCount("林中之城","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1004:
		cm.setBossRankCount("林中之城","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 5:
		   if (cm.getBossRank("射手村",2)  == 0){
		cm.setBossRankCount("射手村","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("射手村",2)  == 1){
		cm.setBossRankCount("射手村","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1005:
		cm.setBossRankCount("射手村","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 6:
		   if (cm.getBossRank("明珠港",2)  == 0){
		cm.setBossRankCount("明珠港","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("明珠港",2)  == 1){
		cm.setBossRankCount("明珠港","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1006:
		cm.setBossRankCount("明珠港","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 7:
		   if (cm.getBossRank("玩具城",2)  == 0){
		cm.setBossRankCount("玩具城","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("玩具城",2)  == 1){
		cm.setBossRankCount("玩具城","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1007:
		cm.setBossRankCount("玩具城","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 8:
		   if (cm.getBossRank("海底世界",2)  == 0){
		cm.setBossRankCount("海底世界","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("海底世界",2)  == 1){
		cm.setBossRankCount("海底世界","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1008:
		cm.setBossRankCount("海底世界","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 9:
		   if (cm.getBossRank("神木之村",2)  == 0){
		cm.setBossRankCount("神木之村","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("神木之村",2)  == 1){
		cm.setBossRankCount("神木之村","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1009:
		cm.setBossRankCount("神木之村","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 10:
		   if (cm.getBossRank("乌鲁之城",2)  == 0){
		cm.setBossRankCount("乌鲁之城","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("乌鲁之城",2)  == 1){
		cm.setBossRankCount("乌鲁之城","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1010:
		cm.setBossRankCount("乌鲁之城","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 11:
		   if (cm.getBossRank("少林之寺",2)  == 0){
		cm.setBossRankCount("少林之寺","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("少林之寺",2)  == 1){
		cm.setBossRankCount("少林之寺","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1011:
		cm.setBossRankCount("少林之寺","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 12:
		   if (cm.getBossRank("新叶之城",2)  == 0){
		cm.setBossRankCount("新叶之城","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("新叶之城",2)  == 1){
		cm.setBossRankCount("新叶之城","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1012:
		cm.setBossRankCount("新叶之城","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		
		case 13:
		   if (cm.getBossRank("百草之堂",2)  == 0){
		cm.setBossRankCount("百草之堂","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("百草之堂",2)  == 1){
		cm.setBossRankCount("百草之堂","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1013:
		cm.setBossRankCount("百草之堂","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 14:
		   if (cm.getBossRank("地球本部",2)  == 0){
		cm.setBossRankCount("地球本部","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("地球本部",2)  == 1){
		cm.setBossRankCount("地球本部","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1014:
		cm.setBossRankCount("地球本部","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 15:
		   if (cm.getBossRank("吉隆都市",2)  == 0){
		cm.setBossRankCount("吉隆都市","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("吉隆都市",2)  == 1){
		cm.setBossRankCount("吉隆都市","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1015:
		cm.setBossRankCount("吉隆都市","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 16:
		   if (cm.getBossRank("天空之城",2)  == 0){
		cm.setBossRankCount("天空之城","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("天空之城",2)  == 1){
		cm.setBossRankCount("天空之城","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1016:
		cm.setBossRankCount("天空之城","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 17:
		   if (cm.getBossRank("冰峰雪域",2)  == 0){
		cm.setBossRankCount("冰峰雪域","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("冰峰雪域",2)  == 1){
		cm.setBossRankCount("冰峰雪域","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1017:
		cm.setBossRankCount("冰峰雪域","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 18:
		   if (cm.getBossRank("艾林森林",2)  == 0){
		cm.setBossRankCount("艾林森林","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("艾林森林",2)  == 1){
		cm.setBossRankCount("艾林森林","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1018:
		cm.setBossRankCount("艾林森林","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 19:
		   if (cm.getBossRank("阿里安特",2)  == 0){
		cm.setBossRankCount("阿里安特","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("阿里安特",2)  == 1){
		cm.setBossRankCount("阿里安特","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1019:
		cm.setBossRankCount("阿里安特","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 20:
		   if (cm.getBossRank("马加提亚",2)  == 0){
		cm.setBossRankCount("马加提亚","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("马加提亚",2)  == 1){
		cm.setBossRankCount("马加提亚","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1020:
		cm.setBossRankCount("马加提亚","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
		
		case 21:
		   if (cm.getBossRank("武陵桃园",2)  == 0){
		cm.setBossRankCount("武陵桃园","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("武陵桃园",2)  == 1){
		cm.setBossRankCount("武陵桃园","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1021:
		cm.setBossRankCount("武陵桃园","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;
			
			
		case 22:
		   if (cm.getBossRank("甘榜之村",2)  == 0){
		cm.setBossRankCount("甘榜之村","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("甘榜之村",2)  == 1){
		cm.setBossRankCount("甘榜之村","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1022:
		cm.setBossRankCount("甘榜之村","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;	
			
	
		case 23:
		   if (cm.getBossRank("昭和之村",2)  == 0){
		cm.setBossRankCount("昭和之村","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("昭和之村",2)  == 1){
		cm.setBossRankCount("昭和之村","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1023:
		cm.setBossRankCount("昭和之村","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;	


        case 24:
		   if (cm.getBossRank("新新加坡",2)  == 0){
		cm.setBossRankCount("新新加坡","1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		else if (cm.getBossRank("新新加坡",2)  == 1){
		cm.setBossRankCount("新新加坡","-1");
	    cm.dispose();
		cm.openNpc(9900004,5000);}
		break;
		case 1024:
		cm.setBossRankCount("新新加坡","1");
		cm.dispose();
		cm.openNpc(9900004,5000);
		break;			
			
		case 1314:
         cm.dispose();
            cm.openNpc(9900004,9900005);	
             break;	 
			 

			 
			 
			 
			 
			 
		}
    }
}