importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
importPackage(Packages.database);

var myDate = new Date();
var year = myDate.getFullYear();
var month = myDate.getMonth() + 1;
var days = myDate.getDate();
var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
var item;
var day;
var id;
var pirzeList = Array(
);
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
        	var strlen = "";
        	id = cm.getPlayer().getId();
        	day = getqiandao(id);
        	if(day == -1){
        		day = 0;
        	}
        	strlen += "成就积分:#B " +day+ "#\r\n";
	strlen +=		   " 玩家；#r#n"+ cm.getChar().getName() +"#k\r\n今日热度；#r"+ cm.getChar().getBossLog("zymxd")+"#k\r\n\r\n";
	strlen +=	 "使用弹窗喇叭 #r#n "+ cm.getChar().getBossLog("qftc")+" / 3 #k 次\r\n";
	strlen +=	 "完成每日跳跳 #r#n "+ cm.getChar().getBossLog("mrtt")+" / 2 #k 次           #g    [ + 2 ]#k\r\n";
	strlen +=	 "完成每日签到 #r#n "+ cm.getChar().getBossLog("mrqd")+" / 1 #k 天           #g    [ + 1 ]#k\r\n";
	strlen +=	 "完成每日跑环 #r#n "+ cm.getChar().getBossLog("paoshang1")+" / 20 #k环           #g    [ + 20 ]#k\r\n";
	strlen +=	 "完成每日奔跑 #r#n "+ cm.getChar().getBossLog("mrbp")+" / X#k  环           #g    [ + X ]#k\r\n";
	strlen +=	 "领取每日好礼 #r#n "+ cm.getChar().getBossLog("mrhl07")+" / 1 #k #b7 :00-7 :59#k   #g   [ + 1 ]#k\r\n";
	strlen +=     "领取每日好礼 #r#n "+ cm.getChar().getBossLog("mrhl12")+" / 1 #k #b12:00-12:59#k   #g   [ + 1 ]#k\r\n";
	strlen +=	  "领取每日好礼 #r#n "+ cm.getChar().getBossLog("mrhl21")+" / 1 #k #b21:00-21:59#k   #g   [ + 1 ]#k \r\n";
	strlen +=	  "进入恐惧房间 #r#n "+ cm.getChar().getBossLog("kjfj")+" 次 #k #b[无活跃度加成]#k \r\n";
	strlen +=	  "使用弹窗喇叭 #r#n "+ cm.getChar().getBossLog("qftc")+" 次 #k #b[无活跃度加成]#k \r\n";
	strlen +=	  "快捷传送使用 #r#n "+ cm.getChar().getBossLog("cs")+" 次 #k #b[无活跃度加成]#k \r\n";
	strlen +=	 "蜈蚣副本通关 #r#n "+ cm.getChar().getBossLog("wugong")+" 次 #k #b[无活跃度加成]#k \r\n";
	strlen +=	  "完成游山玩水 #r#n "+ cm.getChar().getBossLog("sss10")+" / 1#k #b#k 次           #g    [ + 5 ]#k \r\n";
	strlen +=	  "完成每日控血 #r#n "+ cm.getChar().getBossLog("mrkx")+" / 5#k  次           #g    [ + 5 ]#k\r\n";
	strlen +="完成每日寻宝 #r#n "+ cm.getChar().getBossLog("mrxb")+" / 5#k  次           #g    [ + 5 ]#k\r\n";
	strlen += "领取每日口令 #r#n "+ cm.getChar().getBossLog("mrkl")+" / 1#k #b#k 次           #g    [ + 1 ]#k \r\n";
        	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			for(var i = 0; i < pirzeList.length; i++){
        		strlen +="#r#L"+ i +"# #b#t"+ pirzeList[i][0] +"##k 需要"+pirzeList[i][1]+"积分#l\r\n";
        	}
			cm.sendSimple(strlen);
		}else if(status == 1){
			item = selection;
			var strlen = "你确定要用"+pirzeList[item][1]+"积分兑换 #b#t"+ pirzeList[item][0] +"##k 么?";
			cm.sendYesNo(strlen);
		}else if(status == 2){
			if (cm.getInventory(1).isFull() || cm.getInventory(2).isFull() || cm.getInventory(3).isFull() || cm.getInventory(4).isFull() || cm.getInventory(5).isFull()) {
                status = -1;
                cm.sendSimple("您的背包空间不足，各个栏目起码留出一个空位。");
				return;
        	}
			if(day < pirzeList[item][1]){
				cm.sendOk("您的积分不足，不可以兑换该物品哦。");
				cm.dispose();
				return;
			}
			day = day - pirzeList[item][1];
			changeqiandao(id,day);
			cm.gainItem(pirzeList[item][0],1);
			cm.sendOk("");
			cm.dispose();
		}
    }
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

function getqiandao(id) {
        var con = DatabaseConnection.getConnection();
        var ps;
		ps = con.prepareStatement("SELECT day FROM qiandao WHERE id = ?");
        ps.setInt(1, id);
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

function setqiandao(id){
	var con = DatabaseConnection.getConnection();
    var ps;
	ps = con.prepareStatement("insert into qiandao (id, day) values (?,1)");
	ps.setInt(1,id);
	ps.executeUpdate();
    ps.close();
}


function changeqiandao(id,day){
	var con = DatabaseConnection.getConnection();
    var ps;
	ps = con.prepareStatement("update qiandao set day = ? where id = ?");
	ps.setInt(1,day);
	ps.setInt(2,id);
	ps.executeUpdate();
    ps.close();
}