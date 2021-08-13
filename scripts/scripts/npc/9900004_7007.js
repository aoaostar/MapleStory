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
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            //显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#d您好！这里是杀怪数量兑换物品处\r\n\r\n兑换后扣除对应杀怪次数。.#l\r\n\r\n"//3
            text += "#e#d您当前的杀怪数量为：" + cm.getPlayer().getSG() + ".#l\r\n\r\n"//3
			text += "#L10##r我要用1千点杀怪数量兑换#k10W#v5200002##l\r\n\r\n"//3
			text += "#L9##r我要用2千点杀怪数量兑换#k100瓶#v2000004##l\r\n\r\n"//3
			text += "#L7##r我要用1万点杀怪数量兑换#k100瓶#v2000005##l\r\n\r\n"//3
            text += "#L3##r我要用100万杀怪次数兑换#k杀怪王勋章#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
            if (cm.getPlayer().getSG() >= 100000) {
                cm.getPlayer().gainSG(-100000);
               // cm.gainItem();//物品自己加
                cm.gainMeso(5000000);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用10万点[杀怪数量]兑换了10W冒险币，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的杀怪数量不足5千");
                cm.dispose();
            }
        } else if (selection == 2) {
            if (cm.getPlayer().getSG() >= 10000) {
                cm.getPlayer().gainSG(-10000);
               cm.gainItem(4032169,1);//物品自己加
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用1万点[杀怪数量]兑换了七星之一(玉衡)，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的杀怪数量不足1万");
                cm.dispose();
            }
        } else if (selection == 3) {
            if (cm.getPlayer().getSG() >= 1000000) {
                cm.getPlayer().gainSG(-1000000);
                //cm.getPlayer().modifyCSPoints(1, 1000, true);
                cm.gainItem(1142726, 20, 20, 20, 20, 1000, 1000, 10, 10, 0, 0, 0, 0, 0, 0);
                //cm.gainMeso(100000);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用100万[杀怪数量]兑换了杀怪王勋章，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的杀怪数量不足100万");
                cm.dispose();
            }
        } else if (selection == 4) {
            if (cm.getPlayer().getSG() >= 100000) {
                cm.getPlayer().gainSG(-100000);
               // cm.gainItem();//物品自己加
                cm.gainItem(4000313,100);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用10万点[杀怪数量]兑换了100张金叶，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           } 
		           } else if (selection == 5) {
            if (cm.getPlayer().getSG() >= 100000) {
                cm.getPlayer().gainSG(-100000);
               // cm.gainItem();//物品自己加
                cm.gainItem(4032226,100);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用10万点[杀怪数量]兑换了100只金猪，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           } 
		   } else if (selection == 6) {
            if (cm.getPlayer().getSG() >= 5000) {
                cm.getPlayer().gainSG(-5000);
               // cm.gainItem();//物品自己加
                cm.gainItem(4001126,50);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用5000点[杀怪数量]兑换了50张枫叶，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 7) {
            if (cm.getPlayer().getSG() >= 10000) {
                cm.getPlayer().gainSG(-10000);
               // cm.gainItem();//物品自己加
                cm.gainItem(2000005,100);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用10000点[杀怪数量]兑换了100瓶超级药水，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 8) {
            if (cm.getPlayer().getSG() >= 2000) {
                cm.getPlayer().gainSG(-2000);
               // cm.gainItem();//物品自己加
                cm.gainItem(4001126,100);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用2000点[杀怪数量]兑换了100张枫叶，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 9) {
            if (cm.getPlayer().getSG() >= 2000) {
                cm.getPlayer().gainSG(-2000);
               // cm.gainItem();//物品自己加
                cm.gainItem(2000004,100);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用2000点[杀怪数量]兑换了100瓶特殊药水，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 10) {
            if (cm.getPlayer().getSG() >= 1000) {
                cm.getPlayer().gainSG(-1000);
               // cm.gainItem();//物品自己加
                cm.gainMeso(100000);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用1000点[杀怪数量]兑换了10W金币，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 11) {
            if (cm.getPlayer().getSG() >= 1000) {
                cm.getPlayer().gainSG(-1000);
               // cm.gainItem();//物品自己加
                cm.gainNX(30000);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用1000点[杀怪数量]兑换了3W点券，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 12) {
            if (cm.getPlayer().getSG() >= 300) {
                cm.getPlayer().gainSG(-300);
               // cm.gainItem();//物品自己加
                cm.gainItem(2022118,1);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用300点[杀怪数量]兑换了1个管理员的祝福，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
		   } else if (selection == 13) {
            if (cm.getPlayer().getSG() >= 100) {
                cm.getPlayer().gainSG(-100);
               // cm.gainItem();//物品自己加
                cm.gainItem(2022117,1);
                cm.sendOk("兑换成功！");
                cm.worldMessage(6, "玩家：[" + cm.getName() + "]用100点[杀怪数量]兑换了1瓶枫叶糖浆，再接再厉哦！");
                cm.dispose();
            } else {
                cm.sendOk("您的材料不足！");
                cm.dispose();
           }
        }
    }
}


