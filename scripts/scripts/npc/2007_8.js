importPackage(Packages.database);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var item = 0;

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
    	var selStr = "#r此每日收集任务为公开测试阶段，有任何BUG请提交管理员#k\r\n\r\t#i4001271#亲爱的#b#e#h ##n#k,你找到了迷路的孩子了吗？#i4001271#\r\n";
    	selStr += "            #b  【当前时间"+hour+":"+minute+":"+second+"】\r\n\r\n";
    	selStr +="#r#L0# 我上缴迷路的孩子#l";
    	selStr +="     #r#L1# 我要查看数量排行#l\r\n";
    	selStr +="#r#L2# 我要领取活动奖励#l";
    	selStr +="     #r#L3# 我要查看详细说明#l\r\n";
    	cm.sendOk(selStr);
    }else if(status == 1){
    	switch(selection){
    		case 0:
    			cm.dispose();
    			cm.openNpc(2007,801);
    			break;
    		case 1:
    			cm.dispose();
    			cm.openNpc(2007,802);
    			break;
    		case 2:
    			cm.dispose();
    			cm.openNpc(2007,803);
    			break;
    		case 3:
    			var text = "暖手宝宝将迷路的孩子遗失在了世界各个角落，需要冒险岛的各位勇士来帮忙找回丢失的迷路的孩子，勇士们上缴迷路的孩子的数量越多，排名越高，得到GM大大的奖励就越丰厚\r\n";
    			text += "特别提示：\r\n#r 1：遗失的迷路的孩子通过击杀各种怪物获得，所以杀死的怪物越多，得到的迷路的孩子就越多哦。\r\n 2: 由于GM大大需要睡美容觉，所以22:00以后就不再收取迷路的孩子了哟\r\n 3：当天的迷路的孩子排名奖励将会在第二天0点开始领取，勇士们记得领取，过时不候哦！";
    			cm.sendOk(text);
    			cm.dispose();
    			break;
    	}
    }
}