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
            text += " \t\t\t  #e#d欢迎来到#r跳跳活动中心#k#n\r\n"

           // text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

       //     text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L2#10级以上：领取沉睡跳跳奖励(领取完成后回到自由市场-每日5次)#l\r\n\r\n"
			//text += " #L1#在线奖励#l#L55##r钻石VIP专属地图(硬通货爆爆爆！)#k#l\r\n\r\n"
		//	text += " #L3#每日跑商#l\r\n\r\n"229010000
			//text += " #L4#升级奖励#l\r\n\r\n"
		//	text += " #L6#每日答题#l\r\n\r\n"
           // text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1111);

			 } else if (selection == 7) {
            cm.openNpc(9900004, 132);
			
			 } else if (selection == 55) {
				 
			if (!cm.haveItem(1142145, 1)) {
 cm.sendOk("您还不是VIP无法进入专属福利地图~\r\n(PS：会员请把VIP勋章放进背包再点我！)");
                cm.dispose();

			}else if (cm.getPlayerCount(229000100) > 0){
	            cm.sendOk("这条线已经有人在挑战了，请换线或者等会再进！");
                cm.dispose();
			}else{
				cm.warpParty(229000100);
				cm.dispose();
                return;
	      }
				 
					
				
        } else if (selection == 2) {
				if (cm.getBossLog('每日跳跳') >= 5) {//获取玩家签到状态
				cm.sendOk("你今天次数超过5次!");
			cm.dispose();
			} else {
                cm.setBossLog('每日跳跳');//设置签到次数
             
                cm.sendOk("恭喜领取成功！");
				
              //  cm.gainMeso(100000000);//1E
				cm.gainItem(2022468,20);//国庆币
				
				cm.gainNX(20000);
				cm.gainItem(2340000,10);//祝福
				cm.gainItem(2049122,10);//正向
				cm.gainItem(4310149,2);
				
				cm.warp(910000000);
				cm.喇叭(1, "[每日跳跳]：" + cm.getPlayer().getName() + "获得丰厚活动奖励");
                cm.dispose();
           
            }
            // cm.openNpc(9900004, 1112);

        } else if (selection == 3) {
            cm.openNpc(9010009, 0);

        } else if (selection == 4) {
            cm.openNpc(9900004, 1114);

       } else if (selection == 5) {
            if (cm.getBossLog("vipqiandao3") == 0 && cm.haveItem(1142145, 1)) {//获取玩家签到状态
                cm.setBossLog('vipqiandao3');//设置签到次数
                cm.getPlayer().gainqiandao(1);
                cm.sendOk("恭喜签到成功！");
				//cm.gainDY(3000)
				cm.gainMeso(100000000);
				cm.gainItem(4000463, 4);
				cm.gainItem(4000487, 2);
				cm.gainItem(2340000,6);//祝福2022468
				cm.gainItem(2049122,2);//正向
				cm.gainItem(2022468,5);
                cm.喇叭(1, "[VIP签到]：" + cm.getPlayer().getName() + "，今日已成功签到！送1E游戏币+ 国庆币4个+ 祝福卷6张+暗影币2个+正向混沌2张 当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
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
