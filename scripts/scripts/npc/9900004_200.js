var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
//var tz = "#fEffect/CharacterEff/1082565/4/0#";  //兔子粉

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
	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var selStr = "#e┏━━━━━━━━━━━━━━━━━━━━━━━┓\r\n┃#b    自  由  冒  险  岛  快  速  转  职    #k  ┃\r\n┗━━━━━━━━━━━━━━━━━━━━━━━┛#n\r\n\r\n";
 
          selStr += "#L0##b战士#l#k  #L1##b魔法师#l#k #L2##b飞侠#l#k #L3##b弓箭手#l#k #L4##b海盗#l#k \r\n\r\n";
		  
		 selStr += "#L5##b魂骑士#l#k#L6##b炎术士#l#k #L7##b风灵使者#l#k #L8##b夜行者#l#k #L9##b奇袭者#l#k \r\n\r\n";
		   
		    selStr += "#L10##b战神#l#k  \r\n\r\n";
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
			cm.openNpc(9900004,201);
            break;
		case 1:
            cm.dispose();
            cm.openNpc(9900004,202);	
             break;
		case 2:
            cm.dispose();
            cm.openNpc(9900004,203);	
             break;
		case 3:
            cm.dispose();
            cm.openNpc(9900004,204);	
             break;
		case 4:
            cm.dispose();
            cm.openNpc(9900004,205);	
             break;
			 		case 5:
            cm.dispose();
            cm.openNpc(9900004,206);	
             break;
			 break;
			 		case 6:
            cm.dispose();
            cm.openNpc(9900004,207);	
             break;
			 break;
			 		case 7:
            cm.dispose();
            cm.openNpc(9900004,208);	
             break;
			 	case 8:
            cm.dispose();
            cm.openNpc(9900004,209);	
             break;
			 case 9:
            cm.dispose();
            cm.openNpc(9900004,210);	
             break;
			  case 10:
            cm.dispose();
            cm.openNpc(9900004,211);	
             break;
		}
    }
}