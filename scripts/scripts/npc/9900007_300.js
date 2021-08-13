//////////////////////////////
//�߱�*自由冒险岛*最具创意////
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
		
		
   var  
	    selStr = "#e#r"+MC+" - 角色装扮:\r\n#k点券余额：#r"+cm.getPlayer().getCSPoints(1)+"#n#k\r\n\r\n";
		selStr += "#e#L0#"+箭头+"返回界面#l  #L5##b"+箭头+"#r时装专卖#l  #L6##b"+箭头+"#r坐骑更换#l\r\n";
		
		selStr += "#L1##b"+箭头+"挑选发型#l  #L2##b"+箭头+"皇家发型#l  #L7##b"+箭头+"眼睛换色#l\r\n";
		
		
		selStr += "#L3##b"+箭头+"整容手术#l  #L4##b"+箭头+"改变肤色#l  #L8##b"+箭头+"劵类购买#l\r\n";
		

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
            cm.dispose();
            cm.openNpc(1012103,0);	
			
            break;
		 case 2:
            cm.dispose();
            cm.openNpc(1012117,0);	
            break;
        case 3:
            cm.dispose();
            cm.openNpc(1052004,0);	
            break;
        case 4:
            cm.dispose();
            cm.openNpc(1012105,0);	
            break;	
        case 5:
            cm.dispose();
            cm.openNpc(9900007,310);	
            break;
        case 6 :
            cm.dispose();
            cm.openNpc(9900007,311);	
            break;		
        case 7 :
            cm.dispose();
            cm.openNpc(1052005);	
            break;		
        case 8 :
            cm.dispose();
            cm.openNpc(9900004,2000);	
            break;				
	 
			 
			 
			 
		}
    }
}
