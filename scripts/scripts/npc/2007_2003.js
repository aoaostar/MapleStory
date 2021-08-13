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
var item = 4001126;

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
    	var selStr ="┏-#i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126#-┓#l\r\n\r\n\r\n";

    	selStr +="\t\t\t\t#L1#- #r枫叶排行#k -#l\r\n\r\n\r\n";


		selStr +="┗-#i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126#-┛#l";
    	//selStr +="     #r#L3# 我要查看详细说明#l\r\n";
    	cm.sendOk(selStr);
    }else if(status == 1){
    	switch(selection){
    		case 0:
    			cm.dispose();
    			cm.openNpc(2007,20011);
    			break;
    		case 1:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from fengye order by sidai desc limit 100;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "┏------------------#e#d★ #i4001126# ★#k#n------------------┓\r\n\r\n";
				text += "\t#e名次#n\t\t#e昵称#n\t\t\t\t#e数量#n\\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					
					text += "      "      + i +     "\t\t "  ;
					
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
    			cm.openNpc(2007,20013);
    			break;
    		case 3:
    			var text = "#i1142085# #r#t1142085##k\r\n";
    			text += "#i1142086# #r#t1142086##k\r\n";
				text += "#i1142087# #r#t1142087##k\r\n";
				text += "#i1142088# #r#t1142088##k\r\n";
				text += "#i1142089# #r#t1142089##k\r\n";
				text += "#i1142090# #r#t1142090##k\r\n";
				text += "#i1142091# #r#t1142091##k\r\n";
				text += "#i1142092# #r#t1142092##k\r\n";
				text += "#i1142093# #r#t1142093##k\r\n";
				text += "#i1142095# #r#t1142095##k\r\n";
				
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