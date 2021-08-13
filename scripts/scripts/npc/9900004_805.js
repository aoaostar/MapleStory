function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#r累积充值达到800礼包，可获得：#n#b\r\n  1、 祝福卷轴15张\r\n  2、 混沌卷轴30张\r\n  3、 大王冒险岛金条4枚\r\n  4、 精灵吊坠48小时\r\n  5、 绿水灵邮票25张\r\n  6、 蜗牛邮票25张\r\n  7、 蓝蜗牛邮票25张\r\n  8、 木妖邮票25张\r\n  9、 超级抽奖币一枚\r\n 10、 孙子兵法25本\r\n 11、 老公老婆币3枚（可用于升级老公老婆戒指）\r\n\r\n#r领取礼包必须要有足够的空间哦，否则被系统吞了东西，管理员不负责哦\r\n"//3
            text += "#L1##r#v4310119#领取累计充值800礼包#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4310119,1)){
				cm.gainItem(4310119, -1);
				cm.gainItem(4310100, 4);//桃花币
				cm.gainItem(2049100, 30);//混沌
				cm.gainItem(2340000, 15);//祝福
				cm.gainItem(1122017, 1 ,48);//3倍经验卷48小时
				cm.gainItem(4002003, 25);//绿水灵邮票
				cm.gainItem(4002000, 25);//蜗牛邮票
				cm.gainItem(4002001, 25);//蓝蜗牛邮票
				cm.gainItem(4002002, 25);//木妖邮票
				cm.gainItem(4310058, 3);//老公老婆币
				cm.gainItem(2370000, 20);//孙子兵法
				cm.gainItem(4310027,1);//超级抽奖币
            cm.sendOk("领取成功！");
cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功领取累积充值800礼包！！");
            cm.dispose();
			}else{
            cm.sendOk("你的充值达不到限度，或者你已经领取过了，请勿重复领取！");
            cm.dispose();
			}
		}
    }
}


