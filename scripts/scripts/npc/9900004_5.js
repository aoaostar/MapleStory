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
            text += " \t\t     #e#d在我这里可以合成各种材料哦#n\r\n"
            
            //text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"
            
            //text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"#L2#合成水晶#l#L3#合成矿石#l#L4#合成宝石#l
            
          //  text += "    #L1#制作勋章(#v4170007##v4170001#出处：玩具副本、天空副本)#l\r\n\r\n"
			
			text += "    #L2#副本兑换#l\r\n\r\n"
			
			text += "    #L3#装备合成#l#L4#200级勋章合成#l\r\n\r\n"
			
			//text += "    #L5#血衣合成#l\r\n\r\n"
			
			//text += "    #L4#暴君升级#l\r\n\r\n"
			
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 5111);

        } else if (selection == 2) {
            cm.openNpc(9900004, 5121);

        } else if (selection == 3) {
            cm.openNpc(9900004, 5122);

        } else if (selection == 4) {
            cm.openNpc(9900004, 5222);

        } else if (selection == 5) {
            cm.openNpc(1002006, 0);
			
			} else if (selection == 9) {
            cm.openNpc(9010000, 0);
        }
    }
}