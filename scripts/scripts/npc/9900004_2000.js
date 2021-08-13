
var ca = java.util.Calendar.getInstance();
var 百宝券1 = "5000";
var 百宝券11 = "10000";
var 百宝券115 = "100000";
var 皇家1="1000";
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
    }
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }
				if ( cm.getInventory(4).isFull(3)) {
                cm.sendSimple("您的背包空间不足。\r\n\r\n请#r其他#k栏留出 #r4#k 个空位。");
				cm.dispose();
        		return;
}

    else if (status == 0) {		
   var  
	    selStr = "#r#e增值服务 - 现金道具：\r\n#k点券余额：#r"+cm.getPlayer().getCSPoints(1)+"#n#k\r\n";
		selStr += "#L0#"+箭头+"#b返回界面#l#k\r\n";
		selStr += "#L4##v5150040# #b#z5150040# x 1 / #r"+皇家1+" 点券#l\r\n";
		selStr += "#L1##v4110000# #b#z4110000# x 1 / #r"+百宝券1+" 点券#l\r\n";
		//selStr += "#L2##v4110000# #b#z4110000# x 11 / #r"+百宝券11+" 点券#l\r\n";
		//selStr += "#L3##v4110000# #b#z4110000# x 115 / #r"+百宝券115+" 点券#l\r\n";
      
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
			
		case 1:
            if(cm.getPlayer().getCSPoints(1) >= 1000  ){
            cm.gainNX(-1000);
			cm.gainItem(4110000, 1);
            cm.sendOk("购买成功。");			
            cm.dispose();
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		
		case 2:
            if(cm.getPlayer().getCSPoints(1) >= 10000  ){
            cm.gainNX(-10000);
			cm.gainItem(4110000, 11);
            cm.sendOk("购买成功。");
           // cm.serverNotice("[公告]："+ cm.getChar().getName() +"/购买了百宝券大礼包。"); 			
            cm.dispose();
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
			
		case 3:
            if(cm.getPlayer().getCSPoints(1) >= 100000  ){
            cm.gainNX(-100000);
			cm.gainItem(4110000, 115);
            cm.sendOk("购买成功。");
         //   cm.serverNotice("[公告]："+ cm.getChar().getName() +"/购买了百宝券豪华大礼包，简直是豪气冲天。");  			
            cm.dispose();
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
	    case 4:
            if(cm.getPlayer().getCSPoints(1) >= 1000  ){
            cm.gainNX(-1000);
			cm.gainItem(5150040,1);
            cm.sendOk("购买成功。");
           // cm.serverNotice("[公告]："+ cm.getChar().getName() +"/购买了皇家理发卷。");  			
            cm.dispose();
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
			 
			 
			 
		}
    }
}