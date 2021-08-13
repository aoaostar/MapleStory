importPackage(Packages.client);
importPackage(Packages.database);

var status = 0;
var ringnum = 0;
var id = 0;
var itemIndex;
var itemList = Array(//Array(id,Num)
Array(4000000,33),Array(4000001,33),Array(4000005,33),
Array(4000003,33),Array(4000006,33),Array(4000007,33),
Array(4000008,33),Array(4000009,33),Array(4000011,33),
Array(4000014,33),Array(4000026,33),Array(4000025,33),
Array(4000029,33),Array(4000032,33),Array(4000036,33),
Array(4000052,33),Array(4000051,33),Array(4000037,33),
Array(4000053,20),Array(4000054,20),Array(4000055,20),
Array(4000097,33),Array(4000102,33),Array(4000103,33),
Array(4000109,33),Array(4000113,33),Array(4000117,33),
Array(4000120,33),Array(4000115,33),Array(4000111,33),
Array(4000121,33),Array(4000154,33),Array(4000157,33),
Array(4000158,33),Array(4000153,33),Array(4000159,33),
Array(4000164,33),Array(4000162,33),Array(4000161,33),
Array(4000165,33),Array(4000133,33),Array(4000169,33),
Array(4000190,33),Array(4000191,33),Array(4000193,33),
Array(4000196,33),Array(4000188,33),Array(4000192,33),
Array(4000197,33),Array(4000195,33),Array(4000236,33),
Array(4000004,33),Array(4000002,33),Array(4000012,33),
Array(4000015,33),Array(4000016,33),Array(4000018,33),
Array(4000035,33),Array(4000033,33),Array(4000034,33),
Array(4000031,33),Array(4000023,33),Array(4000027,33),
Array(4000165,33),Array(4000133,33),Array(4000169,33),
Array(4000043,33),Array(4000041,33),Array(4000039,33),
Array(4000059,33),Array(4000060,33),Array(4000061,33),
Array(4000132,33),Array(4000134,33),Array(4000069,33),
Array(4000147,33),Array(4000155,33),Array(4000156,33),
Array(4000170,33),Array(4000171,33),Array(4000172,33),
Array(4000205,33),Array(4000204,33),Array(4000187,33),
Array(4000206,33),Array(4000207,33),Array(4000018,33),
Array(4000110,33),Array(4000107,33),Array(4000106,33),
Array(4000021,33),Array(4000018,33),Array(4000019,33),
Array(4000024,33),Array(4000027,33),Array(4000034,33),
Array(4000118,33),Array(4000119,30),Array(4000119,33),
Array(4000351,33),Array(4000350,33),Array(4000332,33),
Array(4000333,33),Array(4000329,33),Array(4000334,33),
Array(4000335,33),Array(4000379,33),Array(4000380,33),
Array(4000160,33),Array(4000167,33),Array(4000168,33),
Array(4000177,33),Array(4000178,33),Array(4000185,33),
Array(4000276,33),Array(4000277,30),Array(4000278,33),
Array(4000279,33),Array(4000280,33),Array(4000281,33),
Array(4000282,33),Array(4000298,33),Array(4000299,33),
Array(4000289,33),Array(4000288,33),Array(4000287,33),
Array(4000292,33),Array(4000291,33),Array(4000286,33),
Array(4000293,33),Array(4000295,33),Array(4000168,33),
Array(4000353,33),Array(4000354,33),Array(4000356,33),
Array(4000361,33),Array(4000363,10),Array(4000364,33),
Array(4000365,33),Array(4000359,33),Array(4000360,33),
Array(4000433,33),Array(4000467,33),Array(4000468,33),
Array(4000471,33),Array(4000472,33),Array(4000469,33),
Array(4000470,33),Array(4000474,33),Array(4000473,33),
Array(4000476,33),Array(4000475,33),Array(4000477,33),
Array(4000090,33),Array(4000089,33),Array(4000078,33),
Array(4000091,33),Array(4000092,30),Array(4000093,30),
Array(4000033,33),Array(2210006,2),
Array(2210006,2),Array(4000436,33),Array(4000438,30),
Array(4000439,33),Array(4000437,20),Array(4000126,9),
Array(4000383,33),Array(4000382,33),Array(4000000,33)
);
var myDate = new Date();
var year = myDate.getFullYear();
var month = myDate.getMonth() + 1;
var days = myDate.getDate();
var giftList = Array(//Array(id,Num)
	Array(2000005,10)
);
var finalGiftList = Array(//Array(id,Num)
	Array(2000005,20)
);
function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
		if (cm.getPlayer().getLevel() < 70) {
        cm.sendOk("#d尼哈沙漠#k 收集任务，到达#r70#k级解锁。");
        cm.dispose();
		 }
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
        	id = cm.getPlayer().getId();
        	ringnum = getBossLog("paoshang",id);
			
        	var strlen = "你会帮我收集我需要的物品吗？\r\n\r\n";
        	if(ringnum == 0)
            	strlen += "#r玩法详情： #b每次您都会接收到一个任务，您需要将我需要的材料拿来，满足我的条件后，我会给你一个巨大的奖品哦，每天我都会提供给你40次任务，做完就没有咯！";
            else if (ringnum <= 20){
            	strlen += "您已经完成我这次的委托了么？";
            }else{
				strlen = "真厉害！您已经完成了当日所有的收集！";
				cm.sendOk(strlen);
				cm.dispose();
				return;
			}
            cm.sendNext(strlen);
        }else if(status == 1){
			if(cm.getInventory(4).isFull()){
    		cm.sendOk("#b请保证其他栏位至少有2个空格,否则无法继续.");
    		cm.dispose();
    		return;
			}
			if (cm.getInventory(2).isFull()){
            cm.sendOk("#b请保证消耗栏位至少有2个空格,否则无法继续.");
            cm.dispose();
            return;
			}
        	itemIndex = getOneTimeLog(id);
        	if(ringnum > 0 && ringnum < 40){//每轮奖励
        		if(cm.haveItem(itemList[itemIndex][0],itemList[itemIndex][1])){
        			cm.gainItem(itemList[itemIndex][0],-itemList[itemIndex][1]);
        			cm.getPlayer().gainMeso(200000, true);
        			if(ringnum%5 == 0){
        				cm.gainItem(2000005,20);
        				//cm.gainItem(4001126,50);
        			}
        			cm.sendNext("恭喜您完成了这次收集，请继续！");
        		}else{
        		    cm.sendOk(""+ringnum+"对不起，您还没有拿来我需要的材料，加油哦！\r\n\r\n这次您需要帮我搜集"+itemList[itemIndex][1]+"个#v"+itemList[itemIndex][0]+"#\r\n期待您的好消息");
					cm.dispose();
					return;
        		}
        	}else if(ringnum == 40){//最终奖励
        		if(cm.haveItem(itemList[itemIndex][0],itemList[itemIndex][1])){
        			cm.gainItem(itemList[itemIndex][0],-itemList[itemIndex][1]);
        			cm.getPlayer().gainMeso(1000000, true);
        			cm.gainItem(2000005,200);

					cm.setBossLog("paoshang");
        			cm.sendNext("恭喜您完成了今日收集，请继续！");
        		}else{
        			cm.sendOk(""+ringnum+"对不起，您还没有拿来我需要的材料，加油哦！\r\n\r\n这次您需要帮我搜集"+itemList[itemIndex][1]+"个#v"+itemList[itemIndex][0]+"#\r\n期待您的好消息");
					cm.dispose();
					return;
        		}
        	}else{
        		var ran = Math.floor(Math.random() * itemList.length);
        		var itmeid = itemList[ran][0];
        		var itemnum = itemList[ran][1];
        		if(itemIndex == -1){
        			setOneTimeLog(ran,id);
        		}else{
        			changeOneTimeLog(ran,id);
        		}
        		var strlen1 = "您当前跑环环数为： "+(ringnum+1)+"\r\n\r\n";
				strlen1 += "您已经成功的领取了本次跑环！";
	        	//strlen1 += " 这次您需要帮我搜集"+itemnum +"个#v"+itemid +"#\r\n期待您的好消息";
        		cm.setBossLog("paoshang");
        		cm.sendOk(strlen1);
        		cm.dispose();
        	}
        }else if(status == 2){
        	if(ringnum < 40){
	        	var ran = Math.floor(Math.random() * itemList.length);
	        	var itemid = itemList[ran][0];
	        	var itemnum = itemList[ran][1];
	        	if(itemIndex == -1){
	        		setOneTimeLog(ran,id);
	        	}else{
	        		changeOneTimeLog(ran,id);
	        	}
	        	var strlen = "您当前跑环环数为： "+ (ringnum+1) +"\r\n\r\n";
	        	strlen += " 这次您需要帮我搜集"+itemnum +"个#v"+itemid +"#\r\n期待您的好消息";
	        	cm.setBossLog("paoshang");
	        	cm.sendOk(strlen);
	        	cm.dispose();
	        }else{
	        	cm.sendOk("您已经完成了跑商！");
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

function setOneTimeLog(bossid,id) {
    var con1 = DatabaseConnection.getConnection();
    var ps = con1.prepareStatement("insert into onetimelog (characterid, log) values (?,?)");
    ps.setInt(1, id);
    ps.setString(2, bossid);
    ps.executeUpdate();
    ps.close();
}
function changeOneTimeLog(bossid,id) {
    var con1 = DatabaseConnection.getConnection();
    var ps = con1.prepareStatement("update onetimelog set log = ? where characterid = ?");
    ps.setString(1, bossid);
    ps.setInt(2, id);
    ps.executeUpdate();
    ps.close();
}

function getOneTimeLog(id) {
        var con = DatabaseConnection.getConnection();
        var count = 0;
        var ps;
        ps = con.prepareStatement("SELECT log FROM onetimelog WHERE characterid = ?");
        ps.setInt(1, id);
        var rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getString("log");
        } else {
            count = -1;
        }
        rs.close();
        ps.close();
        return count;
}

