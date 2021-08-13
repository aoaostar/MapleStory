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
            text += "#e#r感谢你为大王冒险岛做出的贡献#n#b\r\n\r\n"
            text += "#L1##r#v1112768#你是否确定购买？#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.getPlayer().getNX() >= 6666){
				cm.gainNX(-6666);
				cm.gainItem(1112768, 1);//

            cm.sendOk("购买成功，恭喜你！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『高端商店购买通知』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功购买【A级运气宝石戒指】！"));
            cm.dispose();
			}else{
            cm.sendOk("你的点卷是否足够，若点卷不足请充值或在游戏中获得！");
            cm.dispose();
			}
		}
    }
}


