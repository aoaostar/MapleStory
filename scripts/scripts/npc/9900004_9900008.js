/*
自由专职脚本v2.0/调试方法cm.getPlayer().gainSP(1, 1); 第一个1代表转数，第二个1代表给予的技能点
*/
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
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
         cm.dispose();
        }

    else if (status == 0) {
        var 
        selStr = "#e#r"+MC+"  -  全能者 -  技艺切换#k#n \r\n\r\n";
		selStr += "#b#L0#返回界面#l#k  \r\n\r\n";
		//if(cm.getQuestStatus(88881)== 2 ){
		//selStr += "#L2##r救赎者（2）技能升阶#k#l\r\n\r\n";} 
        if(cm.getPlayer().getLevel() >=120){

		selStr += "#L112#英雄#l  #L122#圣骑士#l  #L132#黑骑士#l\r\n"; 	
		selStr += "#L212#火毒魔导师#l  #L222#冰雷魔导师#l  #L232#主教#l\r\n"; 
        selStr += "#L312#神射手#l#L322#箭神#l\r\n"; 
        selStr += "#L412#隐士#l  #L422#侠盗#l\r\n"; 	 	
        selStr += "#L522#船长#l  #L512#冲锋队长#l\r\n"; 		
 }
		else if (cm.getPlayer().getLevel() >=70){
		selStr += "#L2111#战神三转#l\r\n"; 
		selStr += "#L111#勇士#l  #L121#骑士#l  #L131#龙骑士#l\r\n";  
		selStr += "#L211#火毒巫师#l  #L221#冰雷巫师#l  #L231#祭师#l\r\n";  
		selStr += "#L311#射手#l  #L321#游侠#l\r\n"; 
		selStr += "#L411#无影人#l  #L421#独行客#l\r\n"; 
		selStr += "#L511#斗士#l  #L521#大副#l\r\n"; 
 }
		else if (cm.getPlayer().getLevel() >=30){

        selStr += "#L110#剑客#l   #L120#准骑士#l   #L130#枪战士#l\r\n"; 
		selStr += "#L210#火毒法师#l#L220#冰雷法师#l#L230#牧师#l\r\n"; 
		selStr += "#L310#猎人#l    #L320#弩弓手#l #L410#刺客#l   #L420#侠客#l\r\n"; 
		selStr += "#L510#拳手#l    #L520#火枪手#l\r\n"; 
 
		 }
		else if (cm.getPlayer().getLevel() >=10){

		selStr += "#L100#战士#l#k    #L200#魔法师#l#k  #L300#弓箭手#l#k    #L400#飞侠#l#k   #L500#海盗#l#k \r\n\r\n"; 
		 }		
		else{ cm.sendOk("错误代码；0x1236658");
        cm.dispose();
		}
		cm.sendSimple(selStr);
   } else if (status == 1) {
        switch (selection) {
        case 0://枫叶募集
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		 case 2://枫叶募集
            cm.dispose();
            cm.openNpc(9900007,88881);	
            break;



		
		case 2100:
		cm.gainItem(1442077,1);
        cm.teachSkill(21000000,0,10);
		cm.teachSkill(21001003,0,20)
		cm.changeJob(2100);
        
	    cm.sendOk("成功专职为战神。");
        cm.dispose();
		break;
		case 2110:
        cm.teachSkill(21100000,0,20)
		cm.teachSkill(21100002,0,30)
		cm.teachSkill(21100004,0,20)
		cm.teachSkill(21100005,0,20)
		cm.changeJob(2110);;

		cm.sendOk("成功战神神二转。");
        cm.dispose();
		break;
		case 2111:
        cm.teachSkill(21100000,0,20)
		cm.teachSkill(21100002,0,30)
		cm.teachSkill(21100004,0,20)
		cm.teachSkill(21100005,0,20)
		cm.changeJob(2111);

		cm.sendOk("成功战神神三转。");
        cm.dispose();
		break;
		case 2112:
        cm.teachSkill(21120002,0,10);
		cm.teachSkill(21120004,0,10);
		cm.teachSkill(21120005,0,10);
		cm.teachSkill(21120006,0,10);
		cm.teachSkill(21120007,0,10);
		cm.teachSkill(21121000,0,10);
		cm.teachSkill(21121008,0,5)
		cm.changeJob(2112);

		cm.sendOk("成功战神神四转。");
        cm.dispose();
		break;
		case 100:
		cm.changeJob(100);
        cm.getPlayer().setRemainingSp(-1000);
		cm.sendOk("成功专职为战士。");
        cm.dispose();
		break;
		case 110:
		cm.changeJob(110);;

		cm.sendOk("成功专职为剑客。");
        cm.dispose();
		break;
		case 120:
		cm.changeJob(120);

		cm.sendOk("成功专职为准骑士。");
        cm.dispose();
		break;
		case 130:
		cm.changeJob(130);

		cm.sendOk("成功专职为枪战士。");
        cm.dispose();
		break;
		case 111:
		cm.changeJob(111);

		cm.sendOk("成功专职为勇士。");
        cm.dispose();
		break;
		case 121:
		cm.changeJob(121);

		cm.sendOk("成功专职为骑士。");
        cm.dispose();
		break;
		case 131:
		cm.changeJob(131);

		cm.sendOk("成功专职为龙骑士。");
        cm.dispose();
		break;
		case 112:
		cm.teachSkill(1120003,0,10);
		cm.teachSkill(1120004,0,10);
		cm.teachSkill(1120005,0,10);
		cm.teachSkill(1121000,0,10);
		cm.teachSkill(1121001,0,10);
		cm.teachSkill(1121002,0,10);
		cm.teachSkill(1121006,0,10);
		cm.teachSkill(1121008,0,10);
	    cm.teachSkill(1121010,0,10);
		cm.teachSkill(1121011,0,10);
		cm.changeJob(112);

		cm.sendOk("成功专职为英雄。");
        cm.dispose();
		break;
		case 122:
		cm.teachSkill(1220005,0,10);
		cm.teachSkill(1220006,0,10);
		cm.teachSkill(1220010,0,10);
		cm.teachSkill(1221000,0,10);
		cm.teachSkill(1221001,0,10);
		cm.teachSkill(1221002,0,10);
		cm.teachSkill(1221003,0,10);
		cm.teachSkill(1221004,0,10);
	    cm.teachSkill(1221007,0,10);
		cm.teachSkill(1221009,0,10);
		cm.teachSkill(1221011,0,10);
		cm.teachSkill(1221012,0,10);
		cm.changeJob(122);

		cm.sendOk("成功专职为圣骑士。");
        cm.dispose();
		break;
		case 132:
		cm.teachSkill(1320005,0,10);
		cm.teachSkill(1320006,0,10);
		cm.teachSkill(1320008,0,10);
		cm.teachSkill(1320009,0,10);
		cm.teachSkill(1321000,0,10);
		cm.teachSkill(1321001,0,10);
		cm.teachSkill(1321002,0,10);
		cm.teachSkill(1321003,0,10);
	    cm.teachSkill(1321007,0,10);
		cm.teachSkill(1321010,0,10);
		cm.changeJob(132);

		cm.sendOk("成功专职为黑骑士。");
        cm.dispose();
		break;
		case 200:
		cm.changeJob(200);

		cm.sendOk("成功专职为魔法师。");
        cm.dispose();
		break;
		case 210:
		cm.changeJob(210);;

		cm.sendOk("成功专职为火毒法师。");
        cm.dispose();
		break;
		case 220:
		cm.changeJob(220);

		cm.sendOk("成功专职为冰雷法师。");
        cm.dispose();
		break;
		case 230:
		cm.changeJob(230);

		cm.sendOk("成功专职为牧师。");
        cm.dispose();
		break;
		case 211:
		cm.changeJob(211);

		cm.sendOk("成功专职为火毒巫师。");
        cm.dispose();
		break;
		case 221:
		cm.changeJob(221);

		cm.sendOk("成功专职为冰雷巫师。");
        cm.dispose();
		break;
		case 231:
		cm.changeJob(231);

		cm.sendOk("成功专职为祭师。");
        cm.dispose();
		break;	
		case 212:
		cm.teachSkill(2121000,0,10);
		cm.teachSkill(2121001,0,10);
		cm.teachSkill(2121002,0,10);
		cm.teachSkill(2121003,0,10);
		cm.teachSkill(2121004,0,10);
		cm.teachSkill(2121005,0,10);
		cm.teachSkill(2121006,0,10);
		cm.teachSkill(2121007,0,10);
	    cm.teachSkill(2121008,0,10);
		cm.changeJob(212);

		cm.sendOk("成功专职为火毒魔导师。");
        cm.dispose();
		break;	
		case 222:
		cm.teachSkill(2221000,0,10);
		cm.teachSkill(2221001,0,10);
		cm.teachSkill(2221002,0,10);
		cm.teachSkill(2221003,0,10);
		cm.teachSkill(2221004,0,10);
		cm.teachSkill(2221005,0,10);
		cm.teachSkill(2221006,0,10);
		cm.teachSkill(2221007,0,10);
	    cm.teachSkill(1321008,0,10);
		cm.changeJob(222);

		cm.sendOk("成功专职为冰雷魔导师。");
        cm.dispose();
		break;	
		case 232:
		cm.teachSkill(2321000,0,10);
		cm.teachSkill(2321001,0,10);
		cm.teachSkill(2321002,0,10);
		cm.teachSkill(2321003,0,10);
		cm.teachSkill(2321004,0,10);
		cm.teachSkill(2321005,0,10);
		cm.teachSkill(2321006,0,10);
		cm.teachSkill(2321007,0,10);
	    cm.teachSkill(1321008,0,10);
		cm.teachSkill(1321019,0,10);
		cm.changeJob(232);

		cm.sendOk("成功专职为主教。");
        cm.dispose();
		break;		 
        case 300:
		cm.changeJob(300);

		cm.sendOk("成功专职为弓箭手。");
        cm.dispose();
		break;
		case 310:
		cm.changeJob(310);;

		cm.sendOk("成功专职为猎人。");
        cm.dispose();
		break;
		case 320:
		cm.changeJob(320);

		cm.sendOk("成功专职为弩弓手。");
        cm.dispose();
		break;
		case 311:
		cm.changeJob(311);

		cm.sendOk("成功专职为射手。");
        cm.dispose();
		break;
		case 321:
		cm.changeJob(321);

		cm.sendOk("成功专职为游侠。");
        cm.dispose();
		break;	 
		case 312:
		cm.teachSkill(3120005,0,10);
		cm.teachSkill(3121000,0,10);
		cm.teachSkill(3121002,0,10);
		cm.teachSkill(3121003,0,10);
		cm.teachSkill(3121004,0,10);
		cm.teachSkill(3121005,0,10);
		cm.teachSkill(3121006,0,10);
		cm.teachSkill(3121007,0,10);
	    cm.teachSkill(3121008,0,10);
		cm.teachSkill(3121009,0,10);
		cm.changeJob(312);

		cm.sendOk("成功专职为神射手。");
        cm.dispose();
		break;		 
		case 322:
		cm.teachSkill(3220004,0,10);
		cm.teachSkill(3221001,0,10);
		cm.teachSkill(3221002,0,10);
		cm.teachSkill(3221003,0,10);
		cm.teachSkill(3221004,0,10);
		cm.teachSkill(3221005,0,10);
		cm.teachSkill(3221006,0,10);
		cm.teachSkill(3221007,0,10);
	    cm.teachSkill(3221008,0,10);
		cm.teachSkill(3221009,0,10);
		cm.changeJob(322);

		cm.sendOk("成功专职为箭神。");
        cm.dispose();
		break;
		case 400:
		cm.changeJob(400);

		cm.sendOk("成功专职为飞侠。");
        cm.dispose();
        break;
        case 410:
		cm.changeJob(410);;

		cm.sendOk("成功专职为刺客。");
        cm.dispose();
		break;
        case 420:
		cm.changeJob(420);

		cm.sendOk("成功专职为侠客。");
        cm.dispose();		   
		break;
        case 411:
		cm.changeJob(411);

		cm.sendOk("成功专职为无影人。");
        cm.dispose();
        break;
        case 421:
		cm.changeJob(421);

		cm.sendOk("成功专职为独行客。");
        cm.dispose();		   
		break;
		case 412:
		cm.teachSkill(4120002,0,10);
		cm.teachSkill(4120005,0,10);
		cm.teachSkill(4121000,0,10);
		cm.teachSkill(4121003,0,10);
		cm.teachSkill(4121004,0,10);
		cm.teachSkill(4121006,0,10);
		cm.teachSkill(4121007,0,10);
		cm.teachSkill(4121008,0,10);
	    cm.teachSkill(4121009,0,10);
		cm.changeJob(412);

		cm.sendOk("成功专职为隐士。");
        cm.dispose();
		break;
		case 422:
		cm.teachSkill(4220002,0,10);
		cm.teachSkill(4220005,0,10);
		cm.teachSkill(4221000,0,10);
		cm.teachSkill(4221001,0,10);
		cm.teachSkill(4221003,0,10);
		cm.teachSkill(4221004,0,10);
		cm.teachSkill(4221007,0,10);
		cm.teachSkill(4221008,0,10);
	    cm.teachSkill(4221006,0,10);
		cm.changeJob(422);

		cm.sendOk("成功专职为侠盗。");
        cm.dispose();
		break;
		case 500:
		cm.changeJob(500);

		cm.sendOk("成功专职为海盗。");
        cm.dispose();
        break;
		case 510:
		cm.changeJob(510);;

		cm.sendOk("成功专职为拳手。");
        cm.dispose();
        break;
		case 520:
		cm.changeJob(520);

		cm.sendOk("成功专职为火枪手。");
        cm.dispose();
        break;
		case 511:
		cm.changeJob(511);

		cm.sendOk("成功专职为斗士。");
        cm.dispose();
        break;
		case 521:
		cm.changeJob(521);

		cm.sendOk("成功专职为大副。");
        cm.dispose();
        break;
		case 512:
		cm.teachSkill(5121000,0,10);
		cm.teachSkill(5121001,0,10);
		cm.teachSkill(5121002,0,10);
		cm.teachSkill(5121003,0,10);
		cm.teachSkill(5121004,0,10);
		cm.teachSkill(5121005,0,10);
		cm.teachSkill(5121010,0,10);
		cm.teachSkill(5121007,0,10);
	    cm.teachSkill(5121008,0,10);
		cm.teachSkill(5121009,0,10);
		cm.changeJob(512);

		cm.sendOk("成功专职为冲锋队长。");
        cm.dispose();
		break;
		case 522:
		cm.teachSkill(5220001,0,10);
		cm.teachSkill(5220001,0,10);
		cm.teachSkill(5220011,0,10);
		cm.teachSkill(5221000,0,10);
		cm.teachSkill(5221003,0,10);
		cm.teachSkill(5221004,0,10);
		cm.teachSkill(5221006,0,10);
		cm.teachSkill(5221007,0,10);
	    cm.teachSkill(5221008,0,10);
		cm.teachSkill(5221009,0,10);
		cm.teachSkill(5221010,0,10);
		cm.changeJob(522);

		cm.sendOk("成功专职为船长。");
        cm.dispose();
		break;
		case 1100:
		cm.changeJob(1100);

		cm.sendOk("成功专职为魂骑士。");
        cm.dispose();
        break;
		case 1110:
		cm.changeJob(1110);

		cm.sendOk("成功专职为魂骑士二转。");
        cm.dispose();
        break;
		case 1111:
		cm.changeJob(1111);

		cm.sendOk("成功专职为魂骑士三转。");
        cm.dispose();
        break;
		case 1200:
		cm.changeJob(1200);

		cm.sendOk("成功专职为炎术士。");
        cm.dispose();
        break;
		case 1210:
		cm.changeJob(1210);

		cm.sendOk("成功专职为炎术士二转。");
        cm.dispose();
        break;
		case 1211:
		cm.changeJob(1211);

		cm.sendOk("成功专职为炎术士三转。");
        cm.dispose();
        break;	
		case 1300:
		cm.changeJob(1300);

		cm.sendOk("成功专职为风灵使者。");
        cm.dispose();
        break;
		case 1310:
		cm.changeJob(1310);

		cm.sendOk("成功专职为风灵使者二转。");
        cm.dispose();
        break;
		case 1311:
		cm.changeJob(1311);

		cm.sendOk("成功专职为风灵使者三转。");
        cm.dispose();
        break;
    	case 1400:
		cm.changeJob(1400);

		cm.sendOk("成功专职为夜行者。");
        cm.dispose();
        break;
		case 1410:
		cm.changeJob(1410);

		cm.sendOk("成功专职为夜行者二转。");
        cm.dispose();
        break;
		case 1411:
		cm.changeJob(1411);

		cm.sendOk("成功专职为夜行者三转。");
        cm.dispose();
        break;	
		case 1500:
		cm.changeJob(1500);

		cm.sendOk("成功专职为奇袭者。");
        cm.dispose();
        break;
		case 1510:
		cm.changeJob(1510);

		cm.sendOk("成功专职为奇袭者二转。");
        cm.dispose();
        break;
		case 1511:
		cm.changeJob(1511);

		cm.sendOk("成功专职为奇袭者三转。");
        cm.dispose();
        break;
		case 999999999:
		    
            if(cm.getQuestStatus(1002)== 0 ){
			cm.completeQuest(1002);
			cm.gainItem(1472000, 1);
			cm.gainItem(1332000, 1);
	        cm.gainItem(1492000, 1);
			cm.gainItem(1482000, 1);
			cm.gainItem(1372005, 1);
			cm.gainItem(1452002, 1);
			
		    cm.gainItem(1442000, 1);
			cm.gainItem(1432000, 1);
	        cm.gainItem(1402001, 1);
			cm.gainItem(2330000, 1000);
			cm.gainItem(2070000, 1000);
			cm.gainItem(2060000, 1000);
            cm.dispose();
			} else {
				cm.sendOk("不能再次领取。");
				cm.dispose();
				return;
			}
		}
    }
}