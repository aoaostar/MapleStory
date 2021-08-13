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
var item = 4000000;

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
    } else if(status == 0){
    	var selStr ="\r\n\r\n#r#L0# 查看蜗牛壳排行#l\r\n\r\n";
    	//selStr +="#r#L1# 我要查看昨日上缴排行#l\r\n";
	cm.sendOk(selStr);
    }else if (status == 1) {
    	switch(selection){
    		case 0:
	    		var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from sidai_slots order by sidai desc limit 20;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var text = "\t\t\t\t#e#d★ #i4000000#排行 ★#k#n\r\n\r\n";
				text += "\t#e名次#n\t\t\t#e昵称#n\t\t\t  #e数量#n\\r\n";
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
					text += "\t "    + i +       "\t\t\t ";
					
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
			case 1:
			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,totalsidai from sidai_slots order by totalsidai desc limit 20;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var text = "\t\t\t\t#e#d★ 迷路的孩子上缴排行 ★#k#n\r\n\r\n";
				text += "\t#e名次#n\t#e玩家昵称#n\t\t  #e迷路的孩子数量#n\\r\n";
				for (var i = 1; i <= 10; i++) {
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
					text += "\t " + i + "\t\t ";
					
					// 填充名字空格
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += " ";
					}

					// 填充迷路的孩子
					var zc = " "+(list.getInt("totalsidai")).toFixed(0)+"   个";
					text += "  " + zc;
					var totalsidai = list.getInt("totalsidai");
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
		}
    }
}

function getname(id){
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