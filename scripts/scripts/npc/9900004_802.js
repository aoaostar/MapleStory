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
            text += "#e#r每月首次累计充值达到100礼包，可获得：#n#b\r\n物品1：混沌卷轴5张\r\n物品2：祝福卷轴1张\r\n物品3：皇家美发卡5张\r\n物品4: 大王冒险岛金条1个\r\n物品5: 精灵吊坠2小时\r\n\r\n为保证本服平衡,本月充值超过800将不送礼包物品\r\n次月月初可以继续充值#r\r\n领取礼包必须要有足够的空间哦，否则被系统吞了东西，管理员不负责哦\r\n"//3
            text += "#L1##r#v4310025#领取累计充值100礼包#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4310025,1)){
				cm.gainItem(4310025, -1);
				cm.gainItem(2049100, 5);//混沌
				cm.gainItem(2340000, 1);//祝福
				cm.gainItem(4310100, 1);//桃花币
				cm.gainItem(1122017, 1 ,1);//3倍经验卷2小时
				cm.gainItem(5150040, 5);//皇家理发卷

            cm.sendOk("领取成功，恭喜你！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『每月充值100礼包通知』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功领取【每月首次累计充值达到100礼包】！"));
            cm.dispose();
			}else{
            cm.sendOk("你的充值达不到限度，或者你已经领取过了，请勿重复领取！");
            cm.dispose();
			}
		}
    }
}


