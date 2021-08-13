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
                text += "#e#k#r             欢迎来到礼包领取中心#k#n\r\n1:为避免过度充值影响玩家心态，每月充值超过800将不再赠送礼包哟~\r\n\r\n2：感谢您每一分的赞助给大王冒险岛建设事业带来的帮助，我们会不断更新以提升游戏体验！\r\n\r\n";
            // text += "#L1##d新服开区大礼包#l\r\n\r\n"//
            text += "#L2##d#v4310025##z4310025#\t领取累计充值100礼包#l\r\n\r\n"//
            text += "#L3##d#v4310048##z4310048#\t领取累计充值300礼包#l\r\n\r\n"
            text += "#L4##d#v4310079##z4310079#\t领取累计充值500礼包#l\r\n\r\n"
            text += "#L5##d#v4310119##z4310119#\t领取累计充值800礼包#l\r\n\r\n"
            text += "#L6##d#v4310022##z4310022#\t     领取女侠认证礼包#l\r\n\r\n"
            //text += "#L7##d#v4310025##z4310025#\t领取累计充值5000礼包#l\r\n\r\n"
            //text += "#L8##d#v4310010##z4310010#\t领取官方认证老玩家礼包#l\r\n\r\n"
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
			 if(cm.getPlayer().getOneTimeLog("kaifulibao") > 0){
				cm.sendOk("你已经领取过新服开区大礼包，无法继续领取");
				cm.dispose();
        } else {
				cm.gainItem(5040000, 20);
				cm.gainItem(5041000, 10);
				cm.gainNX(900);
				cm.gainDY(3000);
				cm.sendOk("恭喜你领取成功。获得20个普通缩地，10个高级缩地，900点券，3000抵用");
			cm.getPlayer().setOneTimeLog("kaifulibao");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『新服开区大礼包』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功领取【新服开区大礼包】,大王冒险岛欢迎你的到来！！"));
	    }
        } else if (selection == 2) {
		cm.openNpc(9900004, 802);
        } else if (selection == 3) {
		cm.openNpc(9900004, 803);
        } else if (selection == 4) {
		cm.openNpc(9900004, 804);
        } else if (selection == 5) {
		cm.openNpc(9900004, 805);
        } else if (selection == 6) {
		cm.openNpc(9900004, 806);
        } else if (selection == 7) {
		cm.openNpc(9900004, 807);
        } else if (selection == 8) {
		cm.openNpc(9900004, 808);
	}
    }
}


