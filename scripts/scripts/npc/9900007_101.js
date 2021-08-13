importPackage(net.sf.cherry.client);
cal = java.util.Calendar.getInstance();
weekday = cal.get(java.util.Calendar.DAY_OF_WEEK);
var Q = "#fEffect/SkillName1.img/1001003/Q#";
var W = "#fEffect/SkillName1.img/1001003/W#";
var E = "#fEffect/SkillName1.img/1001003/E#";
var A = "#fEffect/SkillName1.img/1001003/A#";
var S = "#fEffect/SkillName1.img/1001003/S#";
var D = "#fEffect/SkillName1.img/1001003/D#";

var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var status = -1;
var ysss = 10;
var jingyan = Math.ceil(Math.random() * 1000000);
var questions = Array(
Array(""+A+""+W+""+A+""+W+"", "AWAW"),
Array(""+A+""+D+""+A+""+D+"", "ADAD"),
Array(""+W+""+W+""+W+""+D+"", "WWWD"),
Array(""+Q+""+E+""+W+""+S+"", "QEWS"),
Array(""+D+""+D+""+D+""+S+"", "DDDS"),
Array(""+W+""+S+""+W+""+S+"", "WSWS"),
Array(""+E+""+E+""+E+""+E+"", "EEEE"),
Array(""+Q+""+Q+""+Q+""+E+"", "QQQE"),
Array(""+Q+""+W+""+Q+""+W+"", "QWQW"),
Array(""+W+""+Q+""+W+""+Q+"", "WQWQ"),
Array(""+S+""+S+""+S+""+S+"", "SSSS"),
Array(""+S+""+S+""+S+""+D+"", "SSSD"),
Array(""+S+""+S+""+S+""+Q+"", "SSSQ"),
Array(""+S+""+Q+""+Q+""+S+"", "SQQS"),
Array(""+S+""+W+""+W+""+W+"", "SWWW"),
Array(""+S+""+W+""+W+""+S+"", "SWWS"),
Array(""+S+""+A+""+A+""+S+"", "SAAS"),
Array(""+S+""+E+""+E+""+S+"", "SEES"),
Array(""+S+""+W+""+E+""+D+"", "SWED"),
Array(""+S+""+Q+""+W+""+Q+"", "SQWQ"),
Array(""+Q+""+W+""+A+""+W+"", "QWAW"),
Array(""+Q+""+S+""+S+""+Q+"", "QSSQ"),
Array(""+Q+""+Q+""+W+""+D+"", "QQWD"),
Array(""+Q+""+E+""+Q+""+S+"", "QEQS"),
Array(""+Q+""+D+""+D+""+S+"", "QDDS"),
Array(""+Q+""+S+""+S+""+S+"", "QSSS"),
Array(""+E+""+S+""+S+""+E+"", "ESSE"),
Array(""+Q+""+A+""+Q+""+E+"", "QAQE"),
Array(""+Q+""+S+""+Q+""+W+"", "QSQW"),
Array(""+W+""+A+""+W+""+Q+"", "WAWQ"),
Array(""+A+""+W+""+A+""+W+""+W+"", "AWAWW"),
Array(""+A+""+D+""+A+""+D+""+W+"", "ADADW"),
Array(""+W+""+W+""+W+""+D+""+W+"", "WWWDW"),
Array(""+Q+""+E+""+W+""+S+""+W+"", "QEWSW"),
Array(""+D+""+D+""+D+""+S+""+W+"", "DDDSW"),
Array(""+W+""+S+""+W+""+S+""+W+"", "WSWSW"),
Array(""+E+""+E+""+E+""+E+""+W+"", "EEEEW"),
Array(""+Q+""+Q+""+Q+""+E+""+W+"", "QQQEW"),
Array(""+Q+""+W+""+Q+""+W+""+W+"", "QWQWW"),
Array(""+S+""+W+""+W+""+W+""+Q+"", "SWWWQ"),
Array(""+S+""+W+""+W+""+S+""+Q+"", "SWWSQ"),
Array(""+S+""+A+""+A+""+S+""+Q+"", "SAASQ"),
Array(""+S+""+E+""+E+""+S+""+Q+"", "SEESQ"),
Array(""+S+""+W+""+E+""+D+""+Q+"", "SWEDQ"),
Array(""+S+""+Q+""+W+""+Q+""+Q+"", "SQWQQ"),
Array(""+Q+""+W+""+A+""+W+""+Q+"", "QWAWQ"),
Array(""+Q+""+S+""+S+""+Q+""+Q+"", "QSSQQ"),
Array(""+Q+""+Q+""+W+""+D+""+Q+"", "QQWDQ"),
Array(""+Q+""+E+""+Q+""+S+""+Q+"", "QEQSQ"),
Array(""+Q+""+D+""+D+""+S+""+Q+"", "QDDSQ"),
Array(""+Q+""+S+""+S+""+S+""+Q+"", "QSSSQ"),
Array(""+E+""+S+""+S+""+E+""+Q+"", "ESSEQ"),
Array(""+Q+""+A+""+Q+""+E+""+Q+"", "QAQEQ"),
Array(""+Q+""+S+""+Q+""+W+""+Q+"", "QSQWQ"),
Array(""+W+""+A+""+W+""+Q+""+Q+"", "WAWQQ"),
Array(""+Q+""+W+""+E+""+A+""+S+"", "QWEAS"),
Array(""+W+""+S+""+W+""+S+""+Q+"", "WSWSQ"),
Array(""+W+""+S+""+W+""+S+""+W+"", "WSWSW"),
Array(""+W+""+S+""+W+""+S+""+D+"", "WSWSD"),
Array(""+W+""+S+""+W+""+S+""+A+"", "WSWSA"),
Array(""+W+""+S+""+W+""+S+""+E+"", "WSWSE"),
Array(""+Q+""+S+""+Q+""+S+""+Q+"", "QSQSQ"),
Array(""+Q+""+W+""+Q+""+W+""+Q+"", "QWQWQ"),
Array(""+W+""+S+""+W+""+S+""+D+"", "WSWSD"),
Array(""+E+""+S+""+E+""+S+""+Q+"", "ESESQ"),
Array(""+A+""+W+""+D+""+S+""+Q+"", "AWDSQ"),
Array(""+S+""+S+""+Q+""+S+""+Q+"", "SSQSQ"),
Array(""+A+""+S+""+D+""+S+""+Q+"", "ASDSQ"),
Array(""+A+""+S+""+D+""+S+""+Q+""+Q+"", "ASDSQQ"),
Array(""+W+""+W+""+W+""+S+""+Q+""+Q+"", "WWWSQQ"),
Array(""+E+""+S+""+S+""+S+""+Q+""+Q+"", "ESSSQQ"),
Array(""+Q+""+Q+""+Q+""+Q+""+Q+""+Q+"", "QQQQQQ"),
Array(""+W+""+W+""+W+""+W+""+W+""+W+"", "WWWWWW"),
Array(""+E+""+E+""+E+""+E+""+E+""+E+"", "EEEEEE"),
Array(""+A+""+A+""+A+""+A+""+A+""+A+"", "AAAAAA"),
Array(""+S+""+S+""+S+""+S+""+S+""+S+"", "SSSSSS"),
Array(""+D+""+D+""+D+""+D+""+D+""+D+"", "DDDDDD")


 

);
var qid = -1;
var time1;
var time2;
var count = 0;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status >= 0) {
            cm.dispose();
            return;
        }

        status--;
    }
    if (status == 0) {
			
        var text = "自由冒险岛节奏小游戏;\r\n\r\n达到一定连击数可获得奖励。\r\n#r提示；请开启大写\r\n";
       // text += "#L0#"+正方箭头+" 活动规则#l\r\n\r\n";
        text += "#L1#"+正方箭头+" 开始游戏#l\r\n";
		text += "#L0#"+正方箭头+" 返回界面#l\r\n";
        cm.sendSimple(text);
    } else if (status == 1) {
        if (selection == 0 && qid == -1) {
               //cm.setBossRankCount("GOMBO","-"+cm.getBossRank("GOMBO",2)+"");
			  
             cm.dispose();
			 cm.openNpc(9900004,0);	
            status = -1;
        } else {			
			
		
			
					
			
          
            time1 = cm.getCurrentTime();
            count++;
            qid = Math.floor(Math.random() * questions.length);
            cm.sendGetText("#r第 - " + count + " - 节拍。#k\r\n#b#e" + questions[qid][0] + "#n#k\r\n目前难度 ；" + ysss + "秒/节拍;#r★★★★★#k");
        }
    } else if (status == 2) {

		 
        time2 = cm.getCurrentTime();
        var myasked = cm.getText();
        var answer = questions[qid][1];
        if ((time2 - time1) / 1000 > ysss) {
            cm.sendOk("连击已超时。");
            cm.dispose();
            return;
        }
 
        if (myasked == answer) {
            status = 0;
            if (count == 10) {
				cm.setBossRankCount("GOMBO")
				//cm.worldMessage(6,"[节奏大师]：玩家 "+cm.getName()+" 已经"+ count + "连击了，获得超级药水 。");
                cm.sendSimple("#r G O M B O #kx #r"+ count + "");
	 } else if (count == 20) {
				cm.setBossRankCount("GOMBO")
				//cm.worldMessage(6,"[节奏大师]：玩家 "+cm.getName()+" 已经"+ count + "连击了，获得超级药水 。");
                cm.sendSimple("#r G O M B O #kx #r"+ count + "");
	 } else if (count < 100) {
				
                cm.sendSimple("#r G O M B O #kx #r"+ count + "");			
            	
				
				
            } else {
              //  cm.gainItem(2000005, 1);
				cm.gainItem(4110000, 10);
				cm.setBossLog("节奏大师");
			//	cm.gainExp(jingyan);
			//	cm.gainItem(4001129, 1);
              //  cm.gainMeso(5000000);
                cm.dispose();
                
            }
        } else {
            status = -1;
            count = 0;
			
			// cm.sendSimple("真遗憾！你答错了~~~"); 
            cm.sendSimple("XX连XX击XX错XX误XX"); 
            qid = -1;
        }
    }
}
