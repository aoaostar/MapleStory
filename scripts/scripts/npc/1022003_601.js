//////////////////////////////
//??*自由冒险岛*最具创意////
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
var 红色 = "#fUI/NameTag.img/126/w#";
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
	var jy = cm.getPlayer().getExp();
	 var jb = cm.getMeso();
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "#e#r"+MC+"#k你是想升级你的不速之客吗？；\r\n";
		
		selStr += "[你当前拥有经验值； #d"+jy+"#k ]\r\n";
		selStr += "[你当前武器熟练度； #d"+ cm.getBossRank("不速之客",2)+"#k ]\r\n";
		
		selStr += "#e#L1#"+箭头+"喂养武器（100经验=1熟练度）#k#l\r\n";
		

		
		

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
		cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
		if ( cm.getPlayer().getExp()>=200){ 
			cm.dispose();
		    cm.setBossRankCount("不速之客",1);
		    cm.gainExp(-100);
			cm.sendOk("制作成功，熟练度+1");
			
			} else {
						  cm.dispose();
		  cm.sendOk("经验值/200,#i4001028# *1");
		  }
            break;	 	
	 
			 
			 
			 
		}
    }
}
