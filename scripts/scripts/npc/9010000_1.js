var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
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
                text += "#e#r\t\t  你准备用什么材料来取悦我？#n#k\r\n\r\n";
            text += "#L1##r使用 #v4000313# #z4000313# 150张  兑换#v4310058##z4310058# 1枚\r\n\r\n"//3			
            text += "#L2##r使用 #v4031891# 金币800W        兑换#v4310058##z4310058# 1枚\r\n\r\n"//3			
            text += "#L3##r使用 #v4002000# #v4002001# 各10枚     兑换#v4310058##z4310058# 1枚\r\n\r\n"//3				
            text += "#L4##r使用 #v4001266# #z4001266# 4枚     兑换#v4310058##z4310058# 1枚\r\n\r\n"//3
            text += "#L5##r使用 #v4310100# 大王冒险岛金条 1条  兑换#v4310058##z4310058# 3枚\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r购买#v5360014##z5360014#（24小时权）\r\n需要：点卷=600点 \r\n\r\n"//3
            //    text += "#b———————————————————————————\r\n\r\n";
            //text += "" + 蓝色箭头 + "#L9##r购买#v5211047##z5211047#（3小时权）\r\n需要：抵用卷=600点 \r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L10##r购买#v5360014##z5360014#（3小时权）\r\n需要：抵用卷=600点 \r\n\r\n"//3

            //text += "" + 蓝色箭头 + "#L5##r制作#v1122126##z1122126#\r\n需要：#v4001126# 5000个、#v1122147# 1个、金币=1亿、#v4005004# 100个\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L6##r#v5211047##z5211047#\t使用权：7天权\t需要点卷：3000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r#v2049100##z2049100#10个\t需要点卷：15000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L8##r#v2340000##z2340000#10个\t需要点卷：15000点\r\n\r\n"//3
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
                if (cm.haveItem(4000313, 150)) {
				cm.gainItem(4000313,-150);
				cm.gainItem(4310058,1);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功取悦901号G-M，得到了老公老婆币1枚");
            cm.dispose();
			}else{
            cm.sendOk("你竟然敢耍我！你没有给我带来 #v4000313# #z4000313# 150张 \r\n你知道惹恼我的后果么！！！");
            cm.dispose();
			}
        } else if (selection == 2) {
                if (cm.getMeso() >= 8000000) {
				cm.gainMeso(-8000000);
				cm.gainItem(4310058,1);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功取悦901号G-M，得到了老公老婆币1枚");
            cm.dispose();
			}else{
            cm.sendOk("你竟然敢耍我！你没有给我带来 800W 金币！！！ \r\n你知道惹恼我的后果么！！！");
            cm.dispose();
			}
        } else if (selection == 3) {
                if (cm.haveItem(4002000, 10) && cm.haveItem(4002001, 10)) {
				cm.gainItem(4002000,-10);
				cm.gainItem(4002001,-10);
				cm.gainItem(4310058,1);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功取悦901号G-M，得到了老公老婆币1枚");
            cm.dispose();
			}else{
            cm.sendOk("你竟然敢耍我！你没有给我带来 #v4002000# #v4002001# 各10张 \r\n你知道惹恼我的后果么！！！");
            cm.dispose();
			}
        } else if (selection == 4) {
                if (cm.haveItem(4001266, 4)) {
				cm.gainItem(4001266,-4);
				cm.gainItem(4310058,1);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功取悦901号G-M，得到了老公老婆币1枚");
            cm.dispose();
			}else{
            cm.sendOk("你竟然敢耍我！你没有给我带来 #v4001266# #z4001266# 3枚 \r\n你知道惹恼我的后果么！！！");
            cm.dispose();
			}
        } else if (selection == 5) {
                if (cm.haveItem(4310100, 1)) {
				cm.gainItem(4310100,-1);
				cm.gainItem(4310058,3);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功取悦901号G-M，得到了老公老婆币3枚");
            cm.dispose();
			}else{
            cm.sendOk("你竟然敢耍我！你没有给我带来 #v4310100# #z4310100# 1条 \r\n你知道惹恼我的后果么！！！");
            cm.dispose();
			}
        } else if (selection == 6) {
                if (cm.getPlayer().getNX() >= 300) {
				cm.gainNX(-300);
				cm.gainItem(5360014,1,10);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买[双倍爆率卡（10小时权）]，爆肝愉快~");
            cm.dispose();
			}else{
            cm.sendOk("点卷不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 7) {
                if (cm.getPlayer().getNX() >= 600) {
				cm.gainNX(-600);
				cm.gainItem(5360014,1,24);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买[双倍爆率卡（24小时权）]，爆肝愉快~");
            cm.dispose();
			}else{
            cm.sendOk("点卷不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 8) {
                if (cm.getPlayer().getNX() >= 2000) {
				cm.gainNX(-2000);
				cm.gainItem(5360014,1,168);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买[双倍爆率卡（7天权）]，爆肝愉快~");
            cm.dispose();
			}else{
            cm.sendOk("点卷不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 9) {
                if (cm.getPlayer().getDY() >= 600) {
				cm.gainDY(-600);
				cm.gainItem(5211047,1,3);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买[双倍经验卡（3小时权）]，爆肝愉快~");
            cm.dispose();
			}else{
            cm.sendOk("抵用卷不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 10) {
                if (cm.getPlayer().getDY() >= 600) {
				cm.gainDY(-600);
				cm.gainItem(5360014,1,3);
cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买[双倍爆率卡（3小时权）]，爆肝愉快~");
            cm.dispose();
			}else{
            cm.sendOk("抵用卷不足无法换购！");
            cm.dispose();
			}
		}
    }
}


