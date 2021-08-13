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
            text += " \t\t#e#d欢迎来到#r怀旧冒险岛VIP系统中心#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟#k#n\r\n\r\n"
                 
			text += "\t\t\t\t#e#r1软妹币=1充值币#v4031039#   \r\n\r\n"  				   
            text += " #r#L1#神级 #bM武器物理攻击系列#l\r\n\ #r#L2#神级 #bM武器魔力攻击系列#l\r\n\ #r#L3#神级 #bM防具系列#l#r\r\n #L4#神级 #bM首饰系列#l\r\n"            
			
			
		//	text += " #L3#每日跑商#l\r\n\r\n"
			//text += " #L4#升级奖励#l\r\n\r\n"
			//text += " #L6#每日答题#l\r\n\r\n"

        //    text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1024);
			
		} else if (selection == 2) {
            cm.openNpc(9900004, 1025);	
			
		} else if (selection == 3) {
            cm.openNpc(9900004, 1026);	

		} else if (selection == 4) {
            cm.openNpc(9900004, 1027);

		} else if (selection == 5) {
            cm.openNpc(9900004, 1013);

		} else if (selection == 6) {
            cm.openNpc(9900004, 1023);		

        } else if (selection == 2) {
            if (cm.getBossLog("vipqiandao") == 0 && cm.haveItem(3700148, 1)) {//获取玩家签到状态
                cm.setBossLog('vipqiandao');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				cm.gainDY(300)
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，今日已成功签到！抵用+300.当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！");
                cm.dispose();
            }
            // cm.openNpc(9900004, 1112);

        } else if (selection == 3) {
            cm.openNpc(9900004, 31);

        } else if (selection == 4) {
            cm.openNpc(9900004, 1114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 1112);
			
        } else if (selection == 6) {
            cm.openNpc(9900004, 1113);
        }
    }
}
