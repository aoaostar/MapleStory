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
var item = 4000016;

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
    	var selStr ="┏------------------------------------------------┓#l\r\n";
    	  selStr +="\t\t\t\t #r#L0#提交蜗牛壳#l\r\n\r\n ";
    	selStr +="\t\t\t\t #L1#蜗牛壳排行#l\r\n\r\n";
    	selStr +="\t\t\t\t #L2#领排行奖励#l\r\n\r\n\r\n#k";
		selStr +="\t\t\t\t 提交次数；"+ cm.getChar().getBossLog("tijiao21")+" / 5\r\n";
		selStr +="┗------------------------------------------------┛#l";
    	//selStr +="     #r#L3# 我要查看详细说明#l\r\n";
    	cm.sendOk(selStr);
    }else if(status == 1){
    	switch(selection){
    		case 0:
    			cm.dispose();
    			cm.openNpc(2007,20021);
    			break;
    		case 1:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from sidai_slots1 order by sidai desc limit 20;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "┏------------------#e#d★ #i4000016# ★#k#n------------------┓\r\n\r\n";
				text += "\t#e名次#n\t\t#e昵称#n\t\t\t    #e数量#n\\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					if (i == 1) {
						text += "#r";
					} else if (i == 2) {
						text += "#g";
					} else if (i == 3) {
						text += "#b";
					}
					text += "\t "    + i +       "\t\t ";
					
					// 填充名字空格
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += " ";
					}

					// 填充迷路的孩子
					var zc = " \t\t\t "+(list.getInt("sidai")).toFixed(0)+"";
					text += "  " + zc;
					var totalsidai = list.getInt("sidai");
					var totalsidailength = 0;
					while (totalsidai > 0) {
						totalsidai = Math.floor(totalsidai/10);
						totalsidailength += 1;
					}
					for (var j = 8 - totalsidailength; j > 0; j--) {
						text += " ";
					}

					
					text += "#k\r\n";
				}
				list.close();
				pstmt.close();
				cm.sendOk(text);
				cm.dispose();
				break;
    			
    		case 2:
    			cm.dispose();
    			cm.openNpc(2007,20023);
    			break;
    		case 3:
    			var text = "暖手宝宝将迷路的孩子遗失在了世界各个角落，需要冒险岛的各位勇士来帮忙找回丢失的迷路的孩子，勇士们上缴迷路的孩子的数量越多，排名越高，得到GM大大的奖励就越丰厚\r\n";
    			text += "特别提示：\r\n#r 1：遗失的迷路的孩子通过击杀各种怪物获得，所以杀死的怪物越多，得到的迷路的孩子就越多哦。\r\n 2: 由于GM大大需要睡美容觉，所以22:00以后就不再收取迷路的孩子了哟\r\n 3：当天的迷路的孩子排名奖励将会在第二天0点开始领取，勇士们记得领取，过时不候哦！";
    			cm.sendOk(text);
    			cm.dispose();
    			break;
    	}
    }
}function getname(id){
    var con1 = DatabaseConnection.getConnection();
    ps1 = con1.prepareStatement("SELECT name FROM characters WHERE id = ?");
    ps1.setInt(1, id);
    var rs1 = ps1.executeQuery();
    var name;
    if (rs1.next()) {
        name = rs1.getString("name");
    } else {
        name = "匿名人士";
    }
    rs1.close();
    ps1.close();
    return name;
}