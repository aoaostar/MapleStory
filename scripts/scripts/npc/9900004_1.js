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
            text += " \t\t\t  #e#d欢迎来到#r冒险岛世界#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L2#每日签到#l #L5##r钻石VIP签到#k#l\r\n\r\n"
		//	text += " #L1#在线奖励#l\r\n\r\n"
		//	text += " #L3#每日跑商#l\r\n\r\n"
			//text += " #L4#升级奖励#l\r\n\r\n"
			text += " #L6#每日答题#l\r\n\r\n"
        //    text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1111);

        } else if (selection == 2) {
            if (cm.getBossLog("qiandao") == 0) {//获取玩家签到状态
                cm.setBossLog('qiandao');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				//cm.gainDY(1000)
                cm.gainMeso(50000000);
				cm.gainItem(2010006,1);
				cm.gainItem(2340000,2);//祝福
				cm.gainItem(2022468,10);
				cm.spawnMonster(9300340,1);
				//cm.gainvip(+2);//破攻等级
				cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，今日已成功签到.每日蛋糕怪物出现了！！！");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！");
                cm.dispose();
            }
            // cm.openNpc(9900004, 1112);

        } else if (selection == 3) {
            cm.openNpc(9010009, 0);

        } else if (selection == 4) {
            cm.openNpc(9900004, 1114);

       } else if (selection == 5) {
            if (cm.getBossLog("vipqiandao3") == 0 && cm.haveItem(3700148, 1)) {//获取玩家签到状态
                cm.setBossLog('vipqiandao3');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				//cm.gainDY(3000)
				cm.gainMeso(100000000);
				cm.gainItem(2010006,4);//蜂蜜
				cm.gainItem(2340000,6);//祝福
				cm.gainItem(2022468,10);
				cm.spawnMonster(9300340,1);
                cm.喇叭(1, "[VIP签到]：" + cm.getPlayer().getName() + "，今日已成功签到！送1E金币+ 蜂蜜4个+ 祝福卷6张+神秘箱子10个，每日蛋糕怪物出现了！！！");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！");
                cm.dispose();
            }
			
        } else if (selection == 6) {
            cm.openNpc(9900004, 1113);
        }
    }
}
