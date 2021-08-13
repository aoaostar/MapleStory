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

var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";

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
	var MC = cm.getServerName();
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  selStr = "┏\t\t\t\t\t\t\t\t\t\t\t\t\t┓\r\n";
	    selStr += "\t\t\t\t#d#e"+MC+"锻造技艺\r\n";

		selStr += "#n#k┗\t\t\t\t\t\t\t\t\t\t\t\t\t┛\r\n";
		
		selStr += "#b#L0#返回界面#l";
		selStr += "#b#L9999#- 熟练度越高，会解锁更高级的锻造物品 -\r\n";
		selStr += "\r\n#e#L1##r锻造跑鞋[初级]#l ";
		if(cm.getBossRank("高级跑鞋",2) > 0) {
		selStr += "#e#L2##r锻造跑鞋[高级]#l\r\n";
   }else {}
    
	    selStr += "\r\n#e#L6##r锻造眼镜[初级]#l ";
		if(cm.getBossRank("高级眼镜",2) > 0) {
		selStr += "#e#L7##r锻造眼镜[高级]#l\r\n";
   }else {}
   
		
		

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 9999:
		　　
            cm.dispose();
		    cm.openNpc(9900004,102);
			
            break;
		case 1:
            cm.dispose();
            cm.openNpc(9900004,990000601);	
            break;
	    case 2:
            cm.dispose();
            cm.openNpc(9900004,990000602);	
            break
			
			
			
		case 6:
            cm.dispose();
            cm.openNpc(9900004,990000606);	
            break
		case 7:
            cm.dispose();
            cm.openNpc(9900004,990000607);	
            break	
	 
			 
			 
			 
		}
    }
}
