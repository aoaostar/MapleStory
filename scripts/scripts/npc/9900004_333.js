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
		
			strlen += "#e玩  家#n；#r"+ cm.getChar().getName() +"#k    【0】未完成【2】完成  \r\n\r\n";
 
			
			//strlen += "#L1#"+正方箭头+"地铁站杀敌#l#k #L2#"+正方箭头+"金字塔杀敌#l#k #L3#"+正方箭头+"武陵塔积分#l#k\r\n#L4#"+正方箭头+"通缉令成就#l#k\r\n\r";
			
	//strlen += "#今日热度#n；#r"+ cm.getChar().getBossLog("zymxd")+"#k\r\n";
   strlen +=	  "[变态地狱船长] ； #r#n "+ cm.getChar().getQuestStatus(123)+"  \r\n";
	//strlen +=	  "完成每日控血 #r#n "+ cm.getChar().getBossLog("mrkx")+" / 5#k  次           #g    [ + 5 ]#k\r\n";
	//strlen +=  "完成每日寻宝 #r#n "+ cm.getChar().getBossLog("mrxb")+" / 5#k  次           #g    [ + 5 ]#k\r\n";
	//strlen +=   "领取每日口令 #r#n "+ cm.getChar().getBossLog("mrkl")+" / 1#k #b#k 次           #g    [ + 1 ]#k \r\n";
  

        	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			for(var i = 0; i < pirzeList.length; i++){
        		strlen +="#r#L"+ i +"# #b#t"+ pirzeList[i][0] +"##k 需要"+pirzeList[i][1]+"积分#l\r\n";
        	}
			cm.sendSimple(strlen);
			
		}else if(selection  == 1){
			var record = cm.getQuestRecord(7662);
			var data = record.getCustomData();
			if (data == null) {
				record.setCustomData("0");
				data = record.getCustomData();
			}
			var mons = parseInt(data);
			
				cm.sendOk("地铁站杀敌数 : " + mons);
				cm.dispose();
				
		}else if(selection  == 2){
            var record = cm.getQuestRecord(7760);
            var data = record.getCustomData();
            if (data == null) {
                record.setCustomData("0");
                data = record.getCustomData();
            }
            var mons = parseInt(data);
            
				cm.sendOk("金字塔杀敌数 : " + mons);	
				cm.dispose();
		}else if(selection  == 3){
            
          
				cm.sendOk("武陵塔积分；#r"+cm.getDojoPoints()+"#k\r\n");	
				cm.dispose();		
				
		}else if(selection  == 4){
            
          
				cm.dispose();
            cm.openNpc(9000064,3);	
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		}else if(status == 20){
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