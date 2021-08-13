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
            text += " \t\t     #e#d在我这里可以制作各种装备哦#n\r\n"
            
            //text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"
            
            //text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"#L4#合成水晶#l
            
            text += "    #L1#老公老婆#l#L2#家族创建#l#L3#本服介绍#l\r\n\r\n"
			
			text += "    #L5#黄金枫叶制作#l#L6#手套制作#l#L7#120永恒武器#l#L8#等待添加#l\r\n\r\n"
			
			
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9010000, 0);

        } else if (selection == 2) {
            cm.openNpc(2010007, 0);

        } else if (selection == 3) {
            cm.openNpc(2007, 0);

        } else if (selection == 4) {
            cm.openNpc(9900004, 5114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 1001);
			
		} else if (selection == 6) {
            cm.openNpc(9900004, 901);

        } else if (selection == 7) {
             cm.openNpc(9900004, 401);

        } else if (selection == 8) {
           

        } else if (selection == 9) {
            

        } else if (selection == 10) {
            
	
	    
        }
    }
}