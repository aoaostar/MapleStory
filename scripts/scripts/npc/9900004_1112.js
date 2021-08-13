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
            text += " \t\t\t  #e#d欢迎来到#r木木冒险岛#k#n\r\n"

            text += "\t\t\t#e#d当前在线时间：" + cm.getGamePoints() + "分钟！#k#n\r\n"

            text += "\t#e#d账户余额：剩余点卷" + cm.getNX(1) + "#k\t剩余抵用卷" + cm.getNX(2) + "#k#n\r\n"

            text += " #L1#领取每日奖励(每天都可以免费领取哦)#l\r\n\r\n"
            //text += "暂时只可以领取每日奖励。签到天数奖励领取，请等待开放\r\n\r\n"
            //下面的奖励写好后，解除屏蔽就行了
             text += " #b#L2#领取签到3天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L3#领取签到5天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L4#领取签到7天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L5#领取签到10天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L6#领取签到15天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L7#领取签到20天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L8#领取签到25天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
             text += " #b#L9#领取签到30天奖励 #r(警告：超过签到天数将无法领取)#l#k\r\n\r\n"
            cm.sendSimple(text);
        } else if (selection == 1) {//每日免费领取福利
            if (cm.getBossLog("mrjl") < 1) {//获取玩家，每日奖励是否领取过了
                cm.setBossLog('mrjl');//先设置他的领取状态，bosslog记录
                cm.gainItem(4001126, 100);//每天给一百个枫叶 - 快乐百宝券 - 在快乐百宝箱会使用的快乐百宝券
                cm.gainMeso(1000000);//每天领100W金币
                cm.gainDY(1000);//每天领100抵用卷
                cm.即时存档();
                cm.dispose();
            } else {
                cm.sendOk("您今天已经领取过奖励了。");
                cm.dispose();
            }
        } else if (selection == 2) {//第3天
            if (cm.getPlayer().getqiandao() >= 3 && cm.获取签到奖励领取状态() == 0) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 150);//枫叶*150
				cm.gainMeso(10000000);//1000W金币
				cm.gainDY(3000);//抵用卷3000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到3天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足3天 \r\n 2、你的签到天数超过了3天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 3) {//第5天
            if (cm.getPlayer().getqiandao() >= 5 && cm.获取签到奖励领取状态() == 1) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 300);//枫叶*300
				cm.gainMeso(20000000);//2000W金币
				cm.gainDY(5000);//抵用卷5000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到5天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足5天 \r\n 2、你的签到天数超过了5天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 4) {//第7天
            if (cm.getPlayer().getqiandao() >= 7 && cm.获取签到奖励领取状态() == 2) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 300);//枫叶*300
				cm.gainMeso(25000000);//2500W金币
				cm.gainDY(8000);//抵用卷8000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到7天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足7天 \r\n 2、你的签到天数超过了7天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 5) {//第15天
            if (cm.getPlayer().getqiandao() >= 10 && cm.获取签到奖励领取状态() == 3) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 500);//枫叶*500
				cm.gainMeso(40000000);//4000W金币
				cm.gainDY(15000);//抵用卷15000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到15天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足15天 \r\n 2、你的签到天数超过了15天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 6) {//第20天
            if (cm.getPlayer().getqiandao() >= 15 && cm.获取签到奖励领取状态() == 4) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 700);//枫叶*700
				cm.gainMeso(60000000);//6000W金币
				cm.gainDY(20000);//抵用卷20000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到20天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足20天 \r\n 2、你的签到天数超过了20天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 7) {//第25天
            if (cm.getPlayer().getqiandao() >= 20 && cm.获取签到奖励领取状态() == 5) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 1000);//枫叶*1000
				cm.gainMeso(80000000);//8000W金币
				cm.gainDY(25000);//抵用卷25000
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到25天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足25天 \r\n 2、你的签到天数超过了25天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 8) {//第30天
		
            if (cm.getPlayer().getqiandao() >= 25 && cm.获取签到奖励领取状态() == 6) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                cm.gainItem(4001126, 1000);//枫叶*1000
				cm.gainItem(4000463, 5);//5个抽奖币
				cm.gainDY(30000);//抵用卷30000
				cm.gainMeso(100000000);//给予1E游戏币
                cm.sendOk("恭喜领取签到奖励成功！");
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到30天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：\r\n 1、你签到天数，不足30天 \r\n 2、你的签到天数超过了30天 \r\n 3、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }
        } else if (selection == 9) {//第30天
            if (cm.getPlayer().getqiandao() >= 30 && cm.获取签到奖励领取状态() == 7) {//获取玩家签到状态
				cm.设置签到奖励领取状态(+1);
                //奖励写在这里
				cm.gainItem(4001126, 1000);//枫叶*1000
				cm.gainItem(4000463, 5);//5个抽奖币
				cm.gainDY(30000);//抵用卷1500
				cm.gainMeso(100000000);//给予1E游戏币
                cm.sendOk("恭喜领取签到奖励成功！");
                //cm.setqiandao(0);//这一句是让玩家，签到三十天后，重置他的签到状态=0天，这样下个月，就可以从第一天开始累计了！！但是要是一个月31天的话，我就呵呵了
                //这句不要也行，源码里面我写了三十天自动清0
                cm.即时存档();
                cm.喇叭(1, "[每日签到]：" + cm.getPlayer().getName() + "，已成功领取连续签到30天的奖励！当前总签到天数为：" + cm.getPlayer().getqiandao() + "天.");
                cm.dispose();
            } else {
                cm.sendOk("领取失败了！\r\n 可能的原因：1、你签到天数，不足30天 \r\n 2、你的帐号下其他角色已经领取过了。");
                cm.dispose();
            }

        }
    }
}
