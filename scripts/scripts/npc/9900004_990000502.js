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
	    if ( cm.getMapId() == 20000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "装备展示；#v1402037#\r\n";
		selStr += "所需熟练度；2000 \r\n";
		selStr += "#b#L0#返回界面#l\r\n";
		selStr += "#L2#材料详细 \r\n";
		
		if(cm.haveItem(4001078,200)&&cm.haveItem(400235,50)&&cm.haveItem(400262,200)&&cm.haveItem(400263,200)&&cm.haveItem(400268,200)&&cm.haveItem(4000030,200)) {
		selStr += "#e#L1#开始锻造#l\r\n";
}	else {}
				
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
					
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
            cm.dispose();
            cm.openNpc(9900004,990000501);	
            break;
		case 2:
          var sld = cm.getBossRank("锻造熟练度",2);
		    cm.sendOk("#e#bfalse = 未达到 true = 已达到#k\r\n\r\n#v4001078#  x 200 = #r"+cm.haveItem(4001078,200)+"#k\r\n#v4000235##  x 50 = #r"+cm.haveItem(4000235,50)+"#k\r\n#v4000262#  x 200 = #r"+cm.haveItem(4000262,200)+"#k\r\n#v4000263#  x 200 = #r"+cm.haveItem(4000263,200)+"#k\r\n#v4000030#  x 200 = #r"+cm.haveItem(4000030,200)+"#k\r\n#v4000268#  x 200 = #r"+cm.haveItem(4000268,200)+"#k\r\n#v4000269#  x 200 = #r"+cm.haveItem(4000269,200)+"#k\r\n熟练度相差;"+(1999-sld)+"");
            cm.dispose();
          
            break;	
	 
			 
			 
			 
		}
    }
}
