var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 正在进行中 = "#fUI/UIWindow/Quest/Tab/enabled/1#";
var 完成 = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var 正在进行中蓝 = "#fUI/UIWindow/MonsterCarnival/icon1#";
var 完成红 = "#fUI/UIWindow/MonsterCarnival/icon0#";
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
            text += "在我这里可以兑换普通抽奖币哦！！！\r\n\r\n#b你的积分剩余：" + cm.getPlayer().getjf() + "点！\r\n"//3
            //text += "#L15##r" + 蓝色箭头 + "领取#v4031326##z4031326#\t需要：等级达到70以上\r\n\r\n"//
            //text += "#L11##b" + 红色箭头 + "制作#v1112424##z1112424#\t需要：#r#v4310015#x500#k个\r\n\r\n"//
            //text += "#L1##b" + 红色箭头 + "#v4310059#兑换必成卷\r\n\r\n"//
            //text += "#L2##b" + 红色箭头 + "#v4310061#兑换红武器\r\n\r\n"//
            //text += "#L3##b" + 红色箭头 + "#v4310063#兑换神器"//
            text += "#L3##b" + 红色箭头 + "#v4310158#兑换普通抽奖币x1个\t需要积分：10\r\n\r\n"//
            text += "#L4##b" + 红色箭头 + "#v4310158#兑换普通抽奖币x10个\t需要积分：100\r\n\r\n"//
            //text += "#L5##b" + 红色箭头 + "#v4310100#合成大王冒险岛金条\r\n\r\n"//
            //text += "#L6##b" + 红色箭头 + "#v3994731#兑换女神专属贝雷帽\r\n\r\n"//
            //text += "#L7##b" + 红色箭头 + "制作#v1052461##z1052461#\t需要：#r#v4310015#x300#k个\r\n\r\n"//
            //text += "#L8##b" + 红色箭头 + "制作#v2100902##z2100902#\t需要：#r#v4310015#x100#k个\r\n\r\n"//
			//text += "#L9##b" + 红色箭头 + "兑换#v2049100##z2049100#\t需要：#r#v4310015#x20#k个\r\n\r\n"//
			//text += "#L10##b" + 红色箭头 + "制作#v2340000##z2340000#\t需要：#r#v4310015#x20#k个\r\n\r\n"//
            //text += "#L9##b" + 红色箭头 + "制作#v1452205##z1452205#\r\n"//
            //text += "#L10##b" + 红色箭头 + "制作#v1462193##z1462193#\r\n"//
            //text += "#L11##b" + 红色箭头 + "制作#v1332225##z1332225#\r\n"//
            //text += "#L12##b" + 红色箭头 + "制作#v1472214##z1472214#\r\n"//
            //text += "#L13##b" + 红色箭头 + "制作#v1482168##z1482168#\r\n"//
            //text += "#L14##b" + 红色箭头 + "制作#v1492179##z1492179#\r\n"//
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9300000, 1);
        } else if (selection == 2) {
		cm.openNpc(9300000, 2);
        } else if (selection == 3) {
			if(cm.getPlayer().getjf() >= 10){
				cm.getPlayer().gainjf(-10);
				cm.gainItem(4310158,1);
				cm.sendOk("换购成功，获得#v4310158#x1个！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『积分兑换普通抽奖币』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功用10积分兑换【普通抽奖币x1个】,祝你一发入魂！"));
					cm.dispose();
					}else{
					cm.sendOk("积分不足，请充值获得积分！");
					cm.dispose();
				}
        } else if (selection == 4) {
			if(cm.getPlayer().getjf() >= 100){
				cm.getPlayer().gainjf(-100);
				cm.gainItem(4310158,10);
				cm.sendOk("换购成功，获得#v4310158#x10个！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『积分兑换普通抽奖币』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功用100积分兑换【普通抽奖币x10个】,祝你一发入魂！"));
					cm.dispose();
					}else{
					cm.sendOk("积分不足，请充值获得积分！");
					cm.dispose();
				}
        } else if (selection == 5) {
		cm.openNpc(9300000, 14);
        } else if (selection == 6) {
		cm.openNpc(9300000, 6);
        } else if (selection == 7) {
		cm.openNpc(9900004, 907);
        } else if (selection == 8) {
		cm.openNpc(9900004, 908);
        } else if (selection == 9) {
		cm.openNpc(9900004, 909);
        } else if (selection == 10) {
		cm.openNpc(9900004, 910);
        } else if (selection == 11) {
		cm.openNpc(9900004, 911);
        } else if (selection == 12) {
		cm.openNpc(9900004, 512);
        } else if (selection == 13) {
		cm.openNpc(9900004, 513);
        } else if (selection == 14) {
		cm.openNpc(9900004, 514);
        } else if (selection == 15) {
            if (cm.getPlayer().getLevel() < 70) {
                cm.sendOk("你的等级小于 70 级，无法领取财神的信件。");
                cm.dispose();
            } else if(cm.haveItem(4031326,1)){
                cm.sendOk("你已经拥有财神的信件，请不要重复领取，小心大姐大拿屎丢你！");
                cm.dispose();
            } else {
cm.gainItem(4031326,+1);//财神的信件
                cm.sendOk("恭喜你领取成功，快去带新人把！");
cm.喇叭(3, "恭喜[" + cm.getPlayer().getName() + "]成功领取财神的信件，快去带新人把！！");
                cm.dispose();
	}
	}
    }
}


