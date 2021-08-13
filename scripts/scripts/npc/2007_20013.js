importPackage(Packages.database);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var days = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var item = 4001126;
var rank;
var id;
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
    if (cm.获取当前星期() == 1 || cm.获取当前星期() == 2 ||cm.获取当前星期() == 3 ||cm.获取当前星期() == 4 ||cm.获取当前星期() == 5||cm.获取当前星期() == 6 ) {
            cm.sendOk("请在周六进行领取。");
            cm.dispose();
    } else if(status == 0){
		//if(getsidai() == 0){
			//cm.sendOk("由于您昨天木有参加活动，不阔以领取奖品哦！！！");
			//cm.dispose();
			//return;
		//}
    	id = cm.getPlayer().getId();
    	rank = getRank(id);
    	if(rank == 11){
    		rank = "未上榜"
    	}
    	var selStr ="#l您的排名是：第#r "+rank+" #k名\r\n#r领取前请将未到期的勋章销毁，否则无法领取。#k\r\n是否要领取奖励？";
    	cm.sendYesNo(selStr);
    }else if (status == 1) {
		

    	if(getBossLog("枫叶勋章",id) > 0){
    		cm.sendOk("每周只能领取一次哦！");
    		cm.dispose();
    		return;
    	}
		if (cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142099)||cm.haveItem(1142099)||
			cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142098)||cm.haveItem(1142098)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142097)||cm.haveItem(1142097)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142096)||cm.haveItem(1142096)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142095)||cm.haveItem(1142095)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142094)||cm.haveItem(1142094)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142093)||cm.haveItem(1142093)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142092)||cm.haveItem(1142092)||
		    cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142091)||cm.haveItem(1142091)||
			cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142089)||cm.haveItem(1142089)){
		    cm.sendOk("你身上有已有枫叶勋章，无法领取。");
    		cm.dispose();
    		return;
    	}
		
		if(cm.getInventory(1).isFull()){
    		cm.sendOk("#b请保证装备栏位至少有2个空格,否则无法领取.");
    		cm.dispose();
    		return;
    	}
    	if (cm.getInventory(2).isFull()){
            cm.sendOk("#b请保证消耗栏位至少有2个空格,否则无法抽取.");
            cm.dispose();
            return;
        }
    	if(rank == 1){
        cm.gainItem(1142099,1,5);
		cm.broadcastServerMsg(5121000, "恭喜 "+ cm.getChar().getName() +" 成为"+year+"年"+month+"月"+days+"日的枫叶天梯第一名。",true);
    	//cm.dispose();
		}else if(rank == 2){
        cm.gainItem(1142098,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第二名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
    	}else if(rank == 3){
        cm.gainItem(1142097,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第三名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 4){
        cm.gainItem(1142096,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第四名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 5){
        cm.gainItem(1142095,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第五名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 6){
        cm.gainItem(1142094,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第六名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 7){
        cm.gainItem(1142093,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第七名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 8){
        cm.gainItem(1142092,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第八名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
		}else if(rank == 9){
        cm.gainItem(1142091,1,5);
		cm.worldMessage(6,"恭喜玩家 "+ cm.getChar().getName() +" 领取"+year+"年"+month+"月"+days+"日的枫叶勋章第九名。 ");
		cm.sendOk("您已经成功领取奖励，要继续加油哦");
    	}else if(rank <= 10){
    	}else{
    	 cm.gainItem(1142089,1,5);
		 cm.sendOk("您已经成功领取奖励，要继续加油哦");
    	}
    	cm.setBossLog("枫叶勋章");
    	//cm.sendOk("您已经成功领取奖励，要继续加油哦");
    	cm.dispose();
    }
    	
}
function getRank(id){
	var conn = DatabaseConnection.getConnection();
	var sql = "select characterid,sidai from fengye order by sidai desc limit 100;";
	var pstmt = conn.prepareStatement(sql);
	var list = pstmt.executeQuery();
	var text = "\t\t\t\t#e#d★ 迷路的孩子上缴排行 ★#k#n\r\n\r\n";
	text += "\t#e名次#n\t#e玩家昵称#n\t\t  #e迷路的孩子数量#n\\r\n";
	var rank = 0;
	for (var i = 1; i <= 11; i++) {
		rank++;
		if (!list.next()) {
			break;
		}
		cid = list.getInt("characterid");
		if(id == cid){
			break;
		}
	}
	list.close();
	pstmt.close();
	return rank;
}

function getsidai(){
	var id = cm.getPlayer().getId();
    var con = DatabaseConnection.getConnection();
    ps = con.prepareStatement("SELECT totalsidai FROM fengye WHERE characterid = ?");
    ps.setInt(1, id);
    var rs = ps.executeQuery();
    if (rs.next()) {
        count = rs.getInt(1);
    } else {
        count = 0;
    }
    rs.close();
    ps.close();
    return count;
}

function getBossLog(boss,id) {
        var con = DatabaseConnection.getConnection();
        var count = 0;
        var ps;
        //ps = con.prepareStatement("SELECT COUNT(*) FROM bosslog WHERE characterid = ? AND bossid = ? AND lastattempt >= subtime(CURRENT_TIMESTAMP, '23:0:0.0')");
		var day = ""+year+"-"+month+"-"+days+"";
		ps = con.prepareStatement("SELECT COUNT(*) FROM bosslog WHERE characterid = ? AND bossid = ? AND lastattempt >= ?");
        ps.setInt(1, id);
        ps.setString(2, boss);
		ps.setString(3,day);
        var rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        } else {
            count = -1;
        }
        rs.close();
        ps.close();
        return count;
}