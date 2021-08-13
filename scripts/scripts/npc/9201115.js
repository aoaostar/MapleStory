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
    if (cm.getPlayer().getFame() < 0 ) {
            cm.sendOk("#r- 你已经被过多人投诉，无法使用此功能。 \r\n\r\n  #k根据自由冒险岛玩家守则，你已经被过多的玩家投诉，削减人气度，已经被限制行动，如果有任何异议，请与管理员取得联系。");
            cm.dispose();
    } 
    else if (status == 0) {
    	
		if (cm.getMapId() == 0){
	var  selStr ="\t\t#e#r      射 手 村 领 地 #k#n\r\n\r\n";
    	 selStr +="   \t\t\t#L1#- 查看领地领主 -#l\r\n\r\n";
		 selStr +="   \t\t\t#L105#- 查看领地排行 -#l\r\n\r\n";
		 selStr +="   \t\t\t#L2#- 增加领地经验 -#l\r\n\r\n";  }
	else {cm.sendOk("            领地系统暂未开放。");
            cm.dispose();}	 
	//else if (cm.getMapId() == 104040001){
	//var  selStr ="\t\t#e#r射 手 村 训 练 场 II 领 地#k#n\r\n\r\n";
    	// selStr +="   \t\t\t#L5#- 查看领地领主 -#l\r\n\r\n";
		// selStr +="   \t\t\t#L106#- 查看领地排行 -#l\r\n\r\n";
		// selStr +="   \t\t\t#L6#- 增加领地经验 -#l\r\n\r\n"; 
		// selStr +="   \t\t\t#L7#- 领地产物出表 -#l\r\n\r\n"; 
		// selStr +="   \t\t\t#L8#- 打开领地界面 -#l\r\n\r\n"; }
		 
	//else if (cm.getMapId() == 541010010){
	//var  selStr ="\t\t\t#e#r幽  灵  船  领  地  2#k#n\r\n\r\n";
    	// selStr +="   \t\t\t#L9#- 查看领地领主 -#l\r\n\r\n";
		// selStr +="   \t\t\t#L107#- 查看领地排行 -#l\r\n\r\n";
		// selStr +="   \t\t\t#L10#- 增加领地经验 -#l\r\n\r\n"; 
		// selStr +="   \t\t\t#L11#- 领地产物出表 -#l\r\n\r\n"; 
		// selStr +="   \t\t\t#L12#- 打开领地界面 -#l\r\n\r\n"; }	 
		 
		 
		 
		 
		 
		 
		 
		 
    	

    	cm.sendOk(selStr);
    }else if(status == 1){
    	switch(selection){
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 1:///射手村1
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi1 order by sidai desc limit 1;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "";
				text += "\t\t射手村训练场I的领主是；";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += "";
					}
					var zc = " \t\t\t "+(list.getInt("sidai")).toFixed(0)+"";
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
    		case 2:///射手村1
    			cm.dispose();
    			cm.openNpc(9201115,1);
    			break;
			case 3:///射手村1
			    cm.sendOk("暂无详细");
    			cm.dispose();	
				break;
			case 4:///射手村1  
    			cm.dispose();
    			cm.openNpc(9201115,2);
    			break;
			case 105:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi1 order by sidai desc limit 100;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "┏\t\t\t\t#e#d★射手村训练场I★#k#n\t\t\t\t┓\r\n\r\n";
				text += "\t#e名次#n\t\t#e昵称#n\t\t\t\t#e经验#n\\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += "      "      + i +     "\t\t "  ;
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += " ";
					}	
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
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   		
			case 5:///射手村2
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi2 order by sidai desc limit 1;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "";
				text += "\t\t射手村训练场II的领主是；";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += "";
					}
					var zc = " \t\t\t "+(list.getInt("sidai")).toFixed(0)+"";
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
							
		    case 6:///射手村2
    			cm.dispose();
    			cm.openNpc(9201115,4);
    			break
			case 7:///射手村2
			    cm.sendOk("暂无详细");
    			cm.dispose();	
    			break;
			case 8:///射手村2
    			cm.dispose();
    			cm.openNpc(9201115,5);
    			break;	
			case 106:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi2 order by sidai desc limit 100;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "┏\t\t\t\t#e#d★射手村训练场II★#k#n\t\t\t\t┓\r\n\r\n";
				text += "\t#e名次#n\t\t#e昵称#n\t\t\t\t#e经验#n\\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += "      "      + i +     "\t\t "  ;
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += " ";
					}	
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
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   		
    	        case 9:///幽灵船2
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi3 order by sidai desc limit 1;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "";
				text += "\t\t幽灵船2的领主是；";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += "";
					}
					var zc = " \t\t\t "+(list.getInt("sidai")).toFixed(0)+"";
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
		    case 10:///幽灵船
    			cm.dispose();
    			cm.openNpc(9201115,7);
    			break
			case 11:///幽灵船
			    cm.sendOk("暂无详细");
    			cm.dispose();	
    			break;
			case 12:///幽灵船
    			cm.dispose();
    			cm.openNpc(9201115,8);
    			break;	
			case 107:
    			var conn = DatabaseConnection.getConnection();
				var sql = "select characterid,sidai from lingdi3 order by sidai desc limit 100;";
				var pstmt = conn.prepareStatement(sql);
				var list = pstmt.executeQuery();
				var 
				text = "┏\t\t\t\t#e#d★ 幽灵船 2 ★#k#n\t\t\t\t┓\r\n\r\n";
				text += "\t#e名次#n\t\t#e昵称#n\t\t\t\t#e经验#n\\r\n";
				for (var i = 1; i <= 100; i++) {
					if (!list.next()) {
						break;
					}
					text+=i==1?"#r":i==2?"#b":i==3?"#b":"";
					text += "      "      + i +     "\t\t "  ;
					text += getname(list.getInt("characterid"));
					for (var j = 30 - list.getInt("characterid").length; j > 0; j--) {
						text += " ";
					}	
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
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		
		
		
		
		
		
		
		
		
		
		
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