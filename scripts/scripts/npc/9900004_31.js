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
            text += " \t  #e#d欢迎来到#r木木冒险岛VIP月卡签到中心#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟#k#n\r\n"

            //text += " #L1#普通理财#l\r\n\r\n"
			//text += " #L2##e#b经济理财（每日签到）#l\r\n\r\n"
		    text += " #L3#豪华理财（每日签到）#l\r\n\r\n"
			//text += " #L4#升级奖励#l\r\n\r\n"
			//text += " #L6#每日答题#l\r\n\r\n"

        //    text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {
            if (cm.getBossLog("1vipqiandao") == 0 && cm.haveItem(3700150, 1)) {//获取玩家签到状态
                cm.setBossLog('1vipqiandao');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				cm.gainItem(4031890,3);
				cm.gainItem(2340000,1);
				cm.gainMeso(1500000);
				cm.gainNX(3000);

				
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，今日已成功签到普通理财！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！或者你没有VIP");
                cm.dispose();
            }

        } else if (selection == 2) {
            if (cm.getBossLog("2vipqiandao") == 0 && cm.haveItem(3700149, 1)) {//获取玩家签到状态
                cm.setBossLog('2vipqiandao');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				cm.gainMeso(1000000);
				cm.gainNX(1000);
				//cm.gainItem(2049116,2);
				//cm.gainItem(2340000,2);
				cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，今日已成功签到经济理财！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！或者你没有VIP");
                cm.dispose();
            }
            // cm.openNpc(9900004, 1112);

        } else if (selection == 3) {
            if (cm.getBossLog("3vipqiandao") == 0 && cm.haveItem(3700148, 1)) {//获取玩家签到状态
                cm.setBossLog('3vipqiandao');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				cm.gainMeso(100000000);
				//cm.gainNX(3000);	
				cm.gainItem(2049116,5);
                cm.gainItem(2340000,5);
				cm.gainItem(5131000,5,1);
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，今日已成功签到豪华会员！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！或者你没有VIP");
                cm.dispose();
	        }

        } else if (selection == 4) {
            cm.openNpc(9900004, 1114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 1112);
			
        } else if (selection == 6) {
            cm.openNpc(9900004, 1113);
        }
    }
}
