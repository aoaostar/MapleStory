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
    }var MC = cm.getServerName();
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  selStr = "#e#r"+MC+" - 玩家天赋页;\r\n";
        selStr += "#L0##b"+箭头+"返回界面#l\r\n\r\n\r\n";
		if(cm.getQuestStatus(999991)== 2){
		selStr += "#L1##s4111002#克隆术[初级]#l\r\n";
		} else {}
		
		


		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 1:
		if(cm.getBossRank("克隆",2) <= 0){
            cm.克隆();
			cm.setBossRankCount("克隆");
			cm.playerMessage(5,"你克隆出了一个自己");
            cm.dispose();
       } else {
			cm.取消克隆();
			cm.setBossRankCount("克隆","-"+cm.getBossRank("克隆",2)+"");
			cm.playerMessage(5,"克隆体消失");
            cm.dispose();
		return;
			}
            break;
		case 0:
	     	cm.dispose();
            cm.openNpc(9900004,0);	
            break;
			
	 
			 
			 
			 
		}
    }
}
