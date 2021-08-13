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
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
    } else if(status == 0){
		//if(getsidai() == 0){
			//cm.sendOk("由于您昨天木有参加活动，不阔以领取奖品哦！！！");
			//cm.dispose();
			//return;
		//}
    	id = cm.getPlayer().getId();
    	rank = getRank(id);
    	if(rank == 200){
    		rank = "不是领主"
    	}
    	var selStr ="#l您的经验排名是：#r "+rank+" #k\r\n#r。#k\r\n是否打开领地功能界面？";
    	cm.sendYesNo(selStr);
    }else if (status == 1) {
		

 
    	if(rank == 1){
	    cm.dispose();
		cm.openNpc(9900002,1);
		}else if(rank > 2){
		cm.sendOk("\t\t\t\t你不是此地图的领主。");
	    cm.dispose();
		
		
        
		
    
		}
    	
    }
    	
}
function getRank(id){
	var conn = DatabaseConnection.getConnection();
	var sql = "select characterid,sidai from lingdi1 order by sidai desc limit 100;";
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
    ps = con.prepareStatement("SELECT totalsidai FROM lingdi1 WHERE characterid = ?");
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