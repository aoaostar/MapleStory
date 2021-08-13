function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

     cm.sendNext("暂未开放VIP系统哦");
     cm.dispose();

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
            text += " \t\t\t#e#d欢迎来到#rVIP系统中心#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟#k#n\r\n\r\n"
                 
			//text += "\t\t\t\t#e#r1软妹币=1充值币#v4031039#   \r\n\r\n"  				   
           
            text += " #r#L1#月卡办理#k#l #L10#稀有飞镖#l #L12#人物效果#l \r\n"            
			//text += " #L12#人物效果#l  \r\n"
			
		//	text += " #L3#每日跑商#l\r\n\r\n"#L13#VIP戒指（含HP/MP）#l#r#L14#神级卷轴#l
			//text += " #L4#升级奖励#l\r\n\r\n"
			//text += " #L6#每日答题#l\r\n\r\n"

           // text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1009);
			
		} else if (selection == 10) {
            cm.openNpc(9900004, 1010);	
			
		} else if (selection == 11) {
            cm.openNpc(9900004, 1011);	

		} else if (selection == 12) {
            cm.openNpc(9900004, 1012);

		} else if (selection == 13) {
            cm.openNpc(9900004, 1013);

		} else if (selection == 14) {
            cm.openNpc(9900004, 1023);		

        } else if (selection == 3) {
            if (cm.getBossLog("vipqiandao1") == 0 && cm.haveItem(3700149, 1)) {//获取玩家签到状态
                cm.setBossLog('vipqiandao1');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				//cm.gainDY(3000)
				cm.gainMeso(+10000000);//金币
				cm.gainNX(+10000);
                cm.喇叭(1, "[VIP签到]：" + cm.getPlayer().getName() + "，今日已成功签到！金币+1000W，点券+1W.当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！");
                cm.dispose();
            }
            // cm.openNpc(9900004, 1112);
        } else if (selection == 4) {
            if (cm.getBossLog("vipqiandao2") == 0 && cm.haveItem(3700150, 1)) {//获取玩家签到状态
                cm.setBossLog('vipqiandao2');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				//cm.gainDY(3000)
				cm.gainMeso(10000000);
				cm.gainItem(4000463, 2);
				cm.gainItem(2340000,4);//祝福
                cm.喇叭(1, "[VIP签到]：" + cm.getPlayer().getName() + "，今日已成功签到！送1000W游戏币+ 国庆币2个+ 祝福卷4张.当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("您今日已签到过了！");
                cm.dispose();
            }
			
			
			
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
