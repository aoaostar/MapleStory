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
        	var strlen = "亲爱的#b#e#h ##n#k，这里是等级每日奖励中心\r\n";
        	strlen += 美化new + "#b等级奖励内容：\r\n			0-9 无\r\n";
			strlen += "			10-29 冒险币10w\r\n";
			strlen += "			30-69 冒险币20w\r\n";
			strlen += "			70-119 点券200 抽奖箱子x2 冒险币30w\r\n";
			strlen += "			120以上 点券500 抽奖箱子x4 冒险币50w\r\n";
			strlen +=" "+感叹号+"#L1##r注意好等级区间，确定要领取么？每天只能领取一次哦#l\r\n";
			cm.sendSimple(strlen);
		}else if(status == 1){
			var id = cm.getPlayer().getId();
        	var time = getBossLog("meirijiangli",id);
        	if(time >= 1){
        		cm.sendOk("每天只可以领取一次哦");
        		cm.dispose();
        		return;
        	}
		if (cm.getInventory(1).isFull() || cm.getInventory(2).isFull() || cm.getInventory(3).isFull() || cm.getInventory(4).isFull() || cm.getInventory(5).isFull()) {
                cm.sendSimple("您的背包空间不足，各个栏目起码留出一个空位。");
		cm.dispose();
        		return;
        	}
        	var level = cm.getLevel();
        	if(level < 10){
        		cm.sendOk("没到十级不能领取奖励哦");
        		cm.dispose();
        	}else if(level < 30){
        		cm.getPlayer().gainMeso(100000, true);
			cm.setBossLog("meirijiangli");
        		cm.sendOk("恭喜你获得10-29级奖励");
        		cm.dispose();
        	}else if(level < 70){
        		cm.getPlayer().gainMeso(200000, true);
			cm.setBossLog("meirijiangli");
        		cm.sendOk("恭喜你获得30-69级奖励");
        		cm.dispose();
        	}else if(level < 120){
        		cm.gainItem(2022336,2);
        		cm.gainNX(200);
        		cm.getPlayer().gainMeso(300000, true);
			cm.setBossLog("meirijiangli");
        		cm.sendOk("恭喜你获得70-119级奖励");
        		cm.dispose();
        	}else{
        		cm.gainItem(2022336,4);
        		cm.gainNX(500);
        		cm.getPlayer().gainMeso(500000, true);
			cm.setBossLog("meirijiangli");
        		cm.sendOk("恭喜你获得120级奖励");
        		cm.dispose();
        	}
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
