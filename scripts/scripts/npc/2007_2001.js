importPackage(Packages.database);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 枫叶至尊10 = "#fEffect/SkillName1.img/1001003/枫叶至尊10#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
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
    	
		var  
		  selStr ="#i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##l\r\n";
    	  selStr +="\t #b#L0##i4001126#- [提交枫叶] -#i4001126##l\r\n ";
		 
			
		
    	selStr +="\t\t\t #L1##i4001126#- [枫叶排行] -#i4001126##l\r\n";
    	
		selStr +="\t\t\t\t\t#r #L3##i4001126#- [典藏勋章] -#i4001126##l\r\n\r\n#k";
		selStr +="#i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##i4001126##l";
    	selStr +="#e#b#L5#"+正方箭头+"详细说明#l   #b#L99#"+正方箭头+"返回界面#l   #L88##r#e"+正方箭头+"领取勋章#k#l#n\r\n";
	
		
    	cm.sendOk(selStr);
    }else if(status == 1){
    	switch(selection){
    		case 0:
    			cm.dispose();
    			cm.openNpc(2007,20011);
    			break;
			case 99:
    			cm.dispose();
    			cm.openNpc(9900004,0);
    			break;
    		case 1:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from fengye order by sidai desc limit 100;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var lts = cm.getBossRank("擂台赛",2);
				var 
				text = "\t\t\t  #e#d#i4001126#★枫叶天梯★#k#n#i4001126#\r\n\r\n";
				text += "提示；#r每周六凌晨结算，第一名获得有限期别致勋章.\r\n";
				text += "#k_____________________________________________________\r\n\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#n#b":i==3?"#k":"";
					
					text += "★第"      + i +     "名★\t -  [ #e ";
					
					// 填充名字空格
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += "\r\n";
					}

					// 填充迷路的孩子
					var zc = "#n]  - \r\n☆积分; "+(list.getInt("sidai")).toFixed(0)+"\r\n#n#k______________________________________________________\r\n";
					text += "  " + zc;
					var totalsidai = list.getInt("sidai");
					var totalsidailength = 0;
					while (totalsidai > 0) {
						totalsidai = Math.floor(totalsidai/10);
						totalsidailength += 1;
					}
					for (var j = 8 - totalsidailength; j > 0; j--) {
						text += "";
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
    			var text = "#i1142099# #r#t1142099##k\r\n";
    			text += "#i1142098# #r#t1142098##k\r\n";
				text += "#i1142097# #r#t1142097##k\r\n";
				text += "#i1142096# #r#t1142096##k\r\n";
				text += "#i1142095# #r#t1142095##k\r\n";
				text += "#i1142094# #r#t1142094##k\r\n";
				text += "#i1142093# #r#t1142093##k\r\n";
				text += "#i1142092# #r#t1142092##k\r\n";
				text += "#i1142091# #r#t1142091##k\r\n";
				text += "#i1142089# #r#t1142089##k\r\n";
				
    			cm.sendOk(text);
    			cm.dispose();
    			break;
			case 5:
                cm.sendOk("\r\n周一至周五为枫叶提交时间。\r\n周六为勋章领取时间。\r\n每周末根据枫叶提交榜来发放名次勋章。");
    			cm.dispose();
				 break;
		    case 88:
            cm.dispose();
            cm.openNpc(2007,20013);	
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