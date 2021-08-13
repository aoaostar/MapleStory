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
            text += "#e#r每月首次累计充值达到300礼包，可获得：#n#b\r\n物品1：混沌卷轴15张\r\n物品2：祝福卷轴5张\r\n物品3: 大王冒险岛金条2个\r\n物品4: 精灵吊坠6小时\r\n物品5: 皇家理发卷10张\r\n\r\n为保证本服平衡,本月充值超过800将不送礼包物品\r\n次月月初可以继续充值#r\r\n领取礼包必须要有足够的空间哦，否则被系统吞了东西，管理员不负责哦\r\n"//3
            text += "#L1##r#v4310048#领取累计充值300礼包#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4310048,1)){
				cm.gainItem(4310048, -1);
				cm.gainItem(2049100, 15);//混沌
				cm.gainItem(2340000, 5);//祝福
				cm.gainItem(4310100, 2);//赞助兑换币
				cm.gainItem(1122017, 1 ,6);//3倍经验卷6小时
				cm.gainItem(5150040, 10);//皇家

            cm.sendOk("领取成功，恭喜你！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『每月充值300礼包通知』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功领取【每月首次累计充值达到300礼包】！"));
            cm.dispose();
			}else{
            cm.sendOk("你的充值达不到限度，或者你已经领取过了，请勿重复领取！");
            cm.dispose();
			}
		}
    }
}


