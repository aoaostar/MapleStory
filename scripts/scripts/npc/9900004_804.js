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
            text += "#e#r累积充值达到500礼包，可获得：#n#b\r\n1、祝福卷轴10张\r\n2、混沌卷轴25张\r\n3、皇家理发卷20张\r\n4、大王冒险岛金条3个\r\n5、精灵吊坠12小时\r\n\r\n#r领取礼包必须要有足够的空间哦，否则被系统吞了东西，管理员不负责哦\r\n"//3
            text += "#L1##r#v4310079#领取累计充值500礼包#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4310079,1)){
				cm.gainItem(4310079, -1);
				cm.gainItem(5150040, 20);//皇家
				cm.gainItem(2049100, 25);//混沌
				cm.gainItem(2340000, 10);//祝福
				cm.gainItem(4310100, 3);//桃花币
				cm.gainItem(1122017, 1 ,12);//3倍经验卷12小时
            cm.sendOk("领取成功！");
cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功领取累积充值500礼包！！");
            cm.dispose();
			}else{
            cm.sendOk("你的充值达不到限度，或者你已经领取过了，请勿重复领取！");
            cm.dispose();
			}
		}
    }
}


