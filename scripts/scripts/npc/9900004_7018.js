function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            text += " \t\t\t  #e#d#r欢迎来到装备售卖系统#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L1##r#e杂货商店#l           \r\n" 
			text += " #L3##b#e坐骑专卖#l           \r\n"
			text += " #L5##r#e装备买卖#l            \r\n" 
			text += " #L6##r#e冲级武器#l            \r\n"
			//text += " #L7##b#e120级寻宝武器#l      #L8##b#e79级紫金枫叶套#l \r\n" 
			  
			
			cm.sendSimple(text);
			
		} else if (selection == 1) {//杂货商店
            cm.openShop(30);
            cm.dispose();
			
		} else if (selection == 9) {//骑宠商店
            cm.openShop(86);
            cm.dispose();	
        
		} else if (selection == 2) {//43级枫叶武器
            cm.openShop(1);
            cm.dispose();

        } else if (selection == 3) {//飞镖购买
            cm.openShop(10000006);
            cm.dispose();

        } else if (selection == 4) {//79级紫金枫叶武器
            cm.openShop(4);
            cm.dispose();
			
			} else if (selection == 5) {//冲级套装
            cm.openShop(32);
            cm.dispose();
			
			} else if (selection == 6) {
				cm.sendNext("期待开启");
				cm.dispose();
            //cm.openShop(9310001);
            //cm.dispose();

        } else if (selection == 7) {
            cm.openShop(8);
            cm.dispose();
			
        } else if (selection == 8) {
           cm.openShop(9);
            cm.dispose();
        }
    }
}
