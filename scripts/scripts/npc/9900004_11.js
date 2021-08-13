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
                //如果玩家的账号表 money字段的值 = 0 提示的npc对话
                cm.sendSimple("#d                  本服无充值\r\n#k");
       
            
        } else if (selection == 1) {
            cm.openWeb("http://new.shoukabao.com/Payment/Service/695c3a9e30551e1dbbcc4297cc40f798");
            cm.dispose();
        } else if (selection == 2) {
            cm.openNpc(9900004, 2);
			       } else if (selection == 10) {
          
            //脚本开始
            //首先判断getzb,获取的是账号表：accounts 里面的字段：money 它的值，是否大于0
            if (cm.getzb() > 0) {
                //声明一个变量，名为：充值金额，赋予它一个值。 值：cm.getzb() 同上
                var 充值金额 = cm.getzb();
                //声明一个变量，名为：点卷倍率，赋予它一个值。值：cm.getzb * 100 就是在money的值基础上 *100 例如money的值是 5 *100就是500 =点卷倍率
                var 点卷倍率 = 充值金额 * 100;
                //声明一个变量，名为：积分倍率，赋予它一个值。值：cm.getzb * 1 就是在money的值基础上 * 1 例如money的值是 5 * 1就是5 =积分倍率
                var 积分倍率 = 充值金额 * 1;
                //给予玩家点卷 数量 = 点卷倍率
                cm.gainNX(点卷倍率);//给与点券
                //给与玩家积分 数量 = 积分倍率
                cm.gainjf(积分倍率);//给予积分
                //设置money字段的值为指定的数量。值为：减去的money值数量
                cm.setzb(-充值金额);//设置money数值
				cm.setmoneyb(+充值金额);
                //npc提示对话
                cm.sendOk("您已成功领取： " + 点卷倍率 + "点卷!\r\n点卷已添加到您的帐户! \r\n享受吧!\r\n同时还获得了:" + 积分倍率 + "积分! #r赶快去商城购买你喜爱的商品吧!#k"); //
                //角色存档
                cm.getChar().saveToDB(false, false);
                //刷出公告
                cm.worldMessage(12, cm.getC().getChannel(), "〖充值系统〗" + " : " + " [" + cm.getPlayer().getName() + "]充值了" + 点卷倍率 + "点卷，" + 积分倍率 + "积分，赶快去商场挑选喜欢的物品吧！", true);
                //结束对话
                cm.dispose();
			}else{
				cm.sendOk("你没有可以领取的点卷哦！");
				cm.dispose();
			}
        }
    }
}