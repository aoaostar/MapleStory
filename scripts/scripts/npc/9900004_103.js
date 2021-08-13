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
var itemList = Array(
Array(2022336,1),
Array(2450000,3),
Array(2022530,3),
Array(4000463,4),
Array(2100900,5),
Array(2100902,5),
Array(1142070,10),
Array(1142178,30),
Array(1142000,35),
Array(1142004,40)
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
        	var strlen = "亲爱的#b#e#h ##n#k，这里是签到积分兑换中心\r\n";
        	id = cm.getPlayer().getId();
        	day = getqiandao(id);
        	if(day == -1){
        		day = 0;
        	}
        	strlen = "#e玩家昵称#n；#r"+ cm.getChar().getName() +"#k\r\n";
			strlen += "#e自由积分 #r#n #B"+day+" # "+ day+" #k\r\n";
        	for(var i = 0; i < itemList.length; i++){
        		strlen +="#r#L"+ i +"# #b#t"+ itemList[i][0] +"##k 需要"+itemList[i][1]+"积分#l\r\n";
        	}
			cm.sendSimple(strlen);
		}else if(status == 1){
			item = selection;
			var strlen = "你确定要用"+itemList[item][1]+"积分兑换 #b#t"+ itemList[item][0] +"##k 么?";
			cm.sendYesNo(strlen);
		}else if(status == 2){
			if (cm.getInventory(1).isFull() || cm.getInventory(2).isFull() || cm.getInventory(3).isFull() || cm.getInventory(4).isFull() || cm.getInventory(5).isFull()) {
                status = -1;
                cm.sendSimple("您的背包空间不足，各个栏目起码留出一个空位。");
				return;
        	}
			if(day < itemList[item][1]){
				cm.sendOk("您的积分不足，不可以兑换该物品哦。");
				cm.dispose();
				return;
			}
			day = day - itemList[item][1];
			changeqiandao(id,day);
			cm.gainItem(itemList[item][0],1);
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