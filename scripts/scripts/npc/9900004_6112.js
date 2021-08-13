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
            text += "#k这里是冒险之心制作列表，请选择你要制作的物品！！\r\n\r\n"//3
            text += "#L1##b" + 红色箭头 + "制作战士觉醒之心"//
            text += "#L2##b" + 红色箭头 + "制作法师觉醒之心\r\n\r\n"//
            text += "#L3##b" + 红色箭头 + "制作弓手觉醒之心"//
            text += "#L4##b" + 红色箭头 + "制作飞侠觉醒之心\r\n\r\n"//
            text += "#L5##b" + 红色箭头 + "制作海盗觉醒之心"//
            //text += "#L6##b" + 红色箭头 + "制作血衣\r\n\r\n"//
            //text += "#L7##b" + 红色箭头 + "制作冒险之心"//
			//text += "#L8##b" + 红色箭头 + "兑换#v2049100##z2049100#\t需要：#r#v4310015#x20#k个\r\n\r\n"//
			//text += "#L9##b" + 红色箭头 + "制作#v2340000##z2340000#\t需要：#r#v4310015#x20#k个\r\n\r\n"//
            //text += "#L9##b" + 红色箭头 + "制作#v1452205##z1452205#\r\n"//
            //text += "#L10##b" + 红色箭头 + "制作#v1462193##z1462193#\r\n"//
            //text += "#L11##b" + 红色箭头 + "制作#v1332225##z1332225#\r\n"//
            //text += "#L12##b" + 红色箭头 + "制作#v1472214##z1472214#\r\n"//
            //text += "#L13##b" + 红色箭头 + "制作#v1482168##z1482168#\r\n"//
            //text += "#L14##b" + 红色箭头 + "制作#v1492179##z1492179#\r\n"//
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9900004, 312);
        } else if (selection == 2) {
		cm.openNpc(9900004, 313);
        } else if (selection == 3) {
		cm.openNpc(9900004, 314);
        } else if (selection == 4) {
		cm.openNpc(9900004, 315);
        } else if (selection == 5) {
		cm.openNpc(9900004, 316);
        } else if (selection == 6) {
            cm.openNpc(1002006, 0);
        } else if (selection == 7) {
		cm.openNpc(9900004, 311);
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


