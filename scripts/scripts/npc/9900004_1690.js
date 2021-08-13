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
            text += " \t\t#e#d欢迎来到#r零花钱系统中心#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟#k#n\r\n\r\n"
                 
							   
         //   text += " #b#L3#兑换强混#l #L1#兑换祝福#l #L15#兑换红色枫叶#l\r\n"            
			//text += "#r#L18#兑换BOSS召唤材料#l\r\n"
			text += " #b#L30##v2022002#X5兑换5000W金币#l \r\n" 
			//text += " #b#L31#【金币兑换】1个游戏币#v4310149#兑换1E金币#l \r\n"
		//	text += "#r#L22#兑换邮票#l\r\n"
			
		//	text += " #L3#每日跑商#l\r\n\r\n"
			//text += " #L4#升级奖励#l\r\n\r\n"
			//text += " #L6#每日答题#l\r\n\r\n"

        //    text += " #L5#领取签到奖励#l \r\n\r\n"
            cm.sendSimple(text);
			} else if (selection == 30) {
			 if(cm.haveItem(2022002,5)){
                cm.gainItem(2022002,-5);				
				 cm.gainMeso(+50000000);				 
				cm.sendOk("恭喜你，兑换成功! .");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的雪碧，我不能给你换购~.");
                cm.dispose();
            }
			
			 } else if (selection == 31) {
			  if(cm.haveItem(4310149,1)){
                cm.gainMeso(+100000000);
				cm.gainItem(4310149,-1);
				cm.sendOk("恭喜你，你获得了 1E金币! .");
			      
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的游戏币，我不能给你换购~.");
                cm.dispose();
            }
			
        } else if (selection == 1) {
            cm.openNpc(9900004, 7002);
			
		} else if (selection == 10) {
            cm.openNpc(9900004, 1010);	
			
		} else if (selection == 11) {
            cm.openNpc(9900004, 1011);	

		} else if (selection == 12) {
            cm.openNpc(9900004, 1012);

		} else if (selection == 13) {
            cm.openNpc(9900004, 1013);	
			
		} else if (selection == 14) {
            cm.openNpc(9900004, 1014);	

		} else if (selection == 15) {
            cm.openNpc(9900004, 1015);	

		} else if (selection == 16) {
            cm.openNpc(9900004, 1016);

		} else if (selection == 17) {
            cm.openNpc(9900004, 1017);

		} else if (selection == 18) {
            cm.openNpc(9900004, 1018);

		} else if (selection == 19) {
            cm.openNpc(9900004, 1019);	

		} else if (selection == 20) {
            cm.openNpc(9900004, 1020);	

		} else if (selection == 22) {
            cm.openNpc(9900004, 1022);		

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
            cm.openNpc(9900004, 7002);

        } else if (selection == 4) {
            cm.openNpc(9900004, 1114);

        } else if (selection == 5) {
            cm.openNpc(9900004, 1112);
			
        } else if (selection == 6) {
            cm.openNpc(9900004, 1113);
        }
    }
}
