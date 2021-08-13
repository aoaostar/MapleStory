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
	    selStr = "#e#r"+MC+"#k你是想升级你的不速之客吗？二代不速之客需要100点武器经验值！；\r\n";
		
		selStr += "[你当前拥有经验值； #d"+jy+"#k ]\r\n";
		selStr += "[你当前武器熟练度； #d"+ cm.getBossRank("不速之客",2)+"#k ]\r\n";
	//	selStr += "[你当前拥有金  币； #d"+jb+"#k ]\r\n";
		
		
		selStr += "#e#L1#"+箭头+"喂养武器（100W经验=100武器经验）#k#l\r\n";
		
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1302143,1)) {
		selStr += "#r#e#L2#"+箭头+"提炼二代不速之客-单手剑#l\r\n";
		   }else {}
		
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1312058,1)) {
		selStr += "#r#e#L3#"+箭头+"提炼二代不速之客-单手斧#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1322086,1)) {
		selStr += "#r#e#L4#"+箭头+"提炼二代不速之客-单手钝器#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1332116,1)) {
		selStr += "#r#e#L5#"+箭头+"提炼二代不速之客-单手短刀#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1372074,1)) {
		selStr += "#r#e#L6#"+箭头+"提炼二代不速之客-短杖#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1382095,1)) {
		selStr += "#r#e#L7#"+箭头+"提炼二代不速之客-长杖#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1402086,1)) {
		selStr += "#r#e#L8#"+箭头+"提炼二代不速之客-双手剑#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1412058,1)) {
		selStr += "#r#e#L9#"+箭头+"提炼二代不速之客-双手斧#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1422059,1)) {
		selStr += "#r#e#L10#"+箭头+"提炼二代不速之客-双手钝器#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1432077,1)) {
		selStr += "#r#e#L11#"+箭头+"提炼二代不速之客-枪#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1442107,1)) {
		selStr += "#r#e#L12#"+箭头+"提炼二代不速之客-矛#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1452102,1)) {
		selStr += "#r#e#L13#"+箭头+"提炼二代不速之客-弓#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1462087,1)) {
		selStr += "#r#e#L14#"+箭头+"提炼二代不速之客-弩#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1472113,1)) {
		selStr += "#r#e#L15#"+箭头+"提炼二代不速之客-拳套#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1482075,1)) {
		selStr += "#r#e#L16#"+箭头+"提炼二代不速之客-指节#l\r\n";
		   }else {}
		 
		if(cm.getBossRank("不速之客",2) >= 100 && !cm.getInventory(1).isFull() && cm.haveItem(1492075,1)) {
		selStr += "#r#e#L17#"+箭头+"提炼二代不速之客-火枪#l\r\n";
		   }else {}
		cm.sendSimple(selStr);
		 
		
		
		
		

		
		

		 
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.openNpc(9900004,0);	
			cm.dispose();
            break;
		case 1:
		if ( cm.getPlayer().getExp()>=1000000){ 
			cm.dispose();
		    cm.setBossRankCount("不速之客",100);
		    cm.gainExp(-1000000);
			cm.sendOk("感受到了一股神秘的力量！");
			
			} else {
		  cm.sendOk("需要100W经验值给武器增加100点武器经验哦！");
		  cm.dispose();
		  }
            break;
		case 2:
		cm.gainItem(1302143, -1);
		cm.gainItem(1302144, 1);
		cm.completeQuest(88888);
        break;
		case 3:
		cm.gainItem(1312058, -1);
		cm.gainItem(1312059, 1);
		cm.completeQuest(88888);
        break;
		case 4:
		cm.gainItem(1322086, -1);
		cm.gainItem(1322087, 1);
		cm.completeQuest(88888);
        break;
		case 5:
		cm.gainItem(1332116, -1);
		cm.gainItem(1332117, 1);
		cm.completeQuest(88888);
        break;
		case 6:
		cm.gainItem(1372074, -1);
		cm.gainItem(1372075, 1);
		cm.completeQuest(88888);
        break;
		case 7:
		cm.gainItem(1382095, -1);
		cm.gainItem(1382096, 1);
		cm.completeQuest(88888);
        break;
		case 8:
		cm.gainItem(1402086, -1);
		cm.gainItem(1402087, 1);
		cm.completeQuest(88888);
        break;
		case 9:
		cm.gainItem(1412058, -1);
		cm.gainItem(1412059, 1);
		cm.completeQuest(88888);
        break;
		case 10:
		cm.gainItem(1422059, -1);
		cm.gainItem(1422060, 1);
		cm.completeQuest(88888);
        break;
		case 11:
		cm.gainItem(1432077, -1);
		cm.gainItem(1432078, 1);
		cm.completeQuest(88888);
        break;
		case 12:
		cm.gainItem(1442107, -1);
		cm.gainItem(1442108, 1);
		cm.completeQuest(88888);
        break;
		case 13:
		cm.gainItem(1452102, -1);
		cm.gainItem(1452103, 1);
		cm.completeQuest(88888);
        break;
		case 14:
		cm.gainItem(1462087, -1);
		cm.gainItem(1462088, 1);
		cm.completeQuest(88888);
        break;
		case 15:
		cm.gainItem(1472113, -1);
		cm.gainItem(1472114, 1);
		cm.completeQuest(88888);
        break;
		case 16:
		cm.gainItem(1482075, -1);
		cm.gainItem(1482076, 1);
		cm.completeQuest(88888);
        break;
		case 17:
		cm.gainItem(1492075, -1);
		cm.gainItem(1492076, 1);
		cm.completeQuest(88888);
        break;
		
			
		 	
	 
			 
			 
			 
		}
    }
}