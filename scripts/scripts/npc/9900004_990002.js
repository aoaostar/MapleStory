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
var 九 = "#fUI/GuildMark.img/Mark/Letter/00005035/15#";
var 七 = "#fUI/GuildMark.img/Mark/Letter/00005033/15#";
var 零 = "#fUI/GuildMark.img/Mark/Letter/00005026/15#";


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


	if (cm.getMapId() > 209000001 || cm.getMapId() <= 209000000) {
        cm.sendOk("此命令只有002号房主可使用。");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var selStr = "#e#r┏#k━━━━━━━━ "+零+""+七+""+九+"━━━━━━━━━#r┓#k\r\n             -★☆★☆★☆★☆-\r\n#b        "+Z+"    "+Y+"    "+M+"    "+X+"    "+D+" #k\r\n  #n                                        \r\n#r┗#k━━━━━━━━━━━━━━━━━━━━━━#r┛#k#n\r\r\n";
  selStr += "   现在时间：#r"+year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分"+second+"秒#k\r\n   点券余额：#r"+cm.getPlayer().getCSPoints(1)+"\r\n";
          selStr += "  #L0##b返回市场#l#k #L1##b携带钥匙#l#k #L2#◇进入活动#l#k \r\n\r\n";
		  
	
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
		cm.warp(910000000,0);
            cm.dispose();
            break;
		case 1:
		
		cm.gainItem(4031332,1)
            cm.dispose();
             break;
		case 2:
            cm.dispose();
            cm.openNpc(9900004,99);	
             break;
		case 3:
            cm.dispose();
            cm.openNpc(9900004,98);	
             break;
		case 4:
            cm.dispose();
            cm.openNpc(9900004,900);	
             break;
			 		case 5:
            cm.dispose();
            cm.openNpc(9900004,102);	
             break;
			 break;
			 		case 6:
            cm.dispose();
            cm.openNpc(9900004,101);	
             break;
			 break;
			 		case 7:
            cm.dispose();
            cm.openNpc(9900004,200);	
             break;
			 		case 8:
            cm.dispose();
            cm.openNpc(9900004,666);	
             break;
		}
    }
}