//////////////////////////////
//七宝*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/1#";
var Y = "#fUI/GuildMark.img/Mark/Letter/00005024/3#";
var X = "#fUI/GuildMark.img/Mark/Letter/00005023/1#";
var D = "#fUI/GuildMark.img/Mark/Letter/00005003/1#";
var M = "#fUI/GuildMark.img/Mark/Letter/00005012/1#";
var A = "#fUI/GuildMark.img/Mark/Letter/00005000/1#";
var P = "#fUI/GuildMark.img/Mark/Letter/00005015/1#";
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/9#";

var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";//"+箭头+"

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
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }
		if (cm.getInventory(1).isFull(4) || cm.getInventory(2).isFull(1)) {
                cm.sendSimple("您的背包空间不足。\r\n\r\n请#r装备#k栏留出 #r5#k 个空位。\r\n请#r消耗#k栏留出 #r2#k 个空位。");
				cm.dispose();
        		return;
}

  
    else if (status == 0) {
		
		
   var  
	    selStr = "#r#e富裕礼包：\r\n#k礼包价格：#r60000\r\n#k点券余额：#r"+cm.getPlayer().getCSPoints(1)+"#n#k\r\n";
		selStr += "#i4110000# #t4110000# x20\r\n#i2049100# #t2049100# x 20\r\n#i1102671# #t1102671# x 1 \r\n#i2000005# #t2000005# x 200\r\n#i2022503# #t2022503# x 20\r\n#i2022514# #t2022514# x 20";
		selStr += "\r\n#L1##b"+箭头+"购买礼包#l  #L0##b"+箭头+"返回#l\r\n";

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,1000);	
            break;
			
		case 1:
		    
            if(cm.getPlayer().getCSPoints(1) >= 60000){
			cm.gainNX(-60000);
			
			cm.gainItem(4110000, 20);
			cm.gainItem(2022514, 20);
	        cm.gainItem(2022503, 20);
			cm.gainItem(2000005, 200);
			cm.gainItem(1102671, 1);
			cm.gainItem(2049100, 20);
			cm.sendOk("购买成功~~哦也~~");	
            cm.serverNotice("[公告]："+ cm.getChar().getName() +"/购买了富裕大礼包，小伙伴们都惊呆了。"); 
            cm.dispose();
			} else {
				cm.sendOk("点券不够，或者已经购买。");
				cm.dispose();
				return;
			}
            break;
	 
			 
			 
			 
		}
    }
}
