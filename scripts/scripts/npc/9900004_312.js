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
                text += "在我这里可以制作各个职业冒险之心哦！！\r\n\r\n";
            text += "" + 蓝色箭头 + "#L1##r制作#v1122129##z1122129#\r\n需要：#v4001126# 300个、金币=100W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L2##r制作#v1122133##z1122133#\r\n需要：#v4001126# 800个、#v1122129# 1个、金币=500W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L3##r制作#v1122138##z1122138#\r\n需要：#v4001126# 2000个、#v1122133# 1个、金币=1000W、#v4005000# 15个\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L4##r制作#v1122143##z1122143#\r\n需要：#v4001126# 3000个、#v1122138# 1个、金币=2500W、#v4005000# 25个\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L5##r制作#v1122122##z1122122#\r\n需要：#v4001126# 5000个、#v1122143# 1个、金币=5000W、#v4005000# 50个\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L6##r#v5211047##z5211047#\t使用权：7天权\t需要点卷：3000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r#v2049100##z2049100#10个\t需要点卷：15000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L8##r#v2340000##z2340000#10个\t需要点卷：15000点\r\n\r\n"//3
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
			if(cm.haveItem(4001126,300) && cm.getMeso() >= 1000000){
				cm.gainItem(4001126, -300);
				cm.gainMeso(-1000000);
				cm.gainItem(1122129, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[冒险之心]，完成了变强的第一步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 2) {
			if(cm.haveItem(4001126,800) && cm.haveItem(1122129,1) && cm.getMeso() >= 5000000){
				cm.gainItem(4001126, -800);
				cm.gainItem(1122129, -1);
				cm.gainMeso(-5000000);
				cm.gainItem(1122133, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[封印冒险之心-战士专属]，完成了变强的第二步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 3) {
			if(cm.haveItem(4001126,2000) && cm.haveItem(1122133,1) && cm.haveItem(4005000,15) && cm.getMeso() >= 10000000){
				cm.gainItem(4001126, -2000);
				cm.gainItem(1122133, -1);
				cm.gainItem(4005000, -15);
				cm.gainMeso(-10000000);
				cm.gainItem(1122138, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[苏醒冒险之心-战士专属]，完成了变强的第三步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 4) {
			if(cm.haveItem(4001126,3000) && cm.haveItem(1122138,1) && cm.haveItem(4005000,25) && cm.getMeso() >= 25000000){
				cm.gainItem(4001126, -3000);
				cm.gainItem(1122138, -1);
				cm.gainItem(4005000, -25);
				cm.gainMeso(-25000000);
				cm.gainItem(1122143, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[觉醒冒险之心-战士专属]，完成了变强的第四步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 5) {
			if(cm.haveItem(4001126,5000) && cm.haveItem(1122143,1) && cm.haveItem(4005000,50) && cm.getMeso() >= 50000000){
				cm.gainItem(4001126, -5000);
				cm.gainItem(1122143, -1);
				cm.gainItem(4005000, -50);
				cm.gainMeso(-50000000);
				cm.gainItem(1122122, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[真觉醒冒险之心-战士专属]，完成了变强的最后一步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 6) {
if (cm.getPlayer().getCSPoints(1) >= 3000) {
cm.gainNX(-3000);
cm.gainItem(5211047,1,7);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功购买(双倍经验卡--7天权)1个，快去升级！");
cm.dispose();
}else{
cm.sendOk("点卷不足，无法购买！");
cm.dispose();
}
        } else if (selection == 7) {
if (cm.getPlayer().getCSPoints(1) >= 15000) {
cm.gainNX(-15000);
cm.gainItem(2049100,10);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功购买混沌卷轴10个，恭喜！");
cm.dispose();
}else{
cm.sendOk("点卷不足，无法购买！");
cm.dispose();
}
        } else if (selection == 8) {
if (cm.getPlayer().getCSPoints(1) >= 15000) {
cm.gainNX(-15000);
cm.gainItem(2340000,10);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功购买祝福卷轴10个，恭喜！");
cm.dispose();
}else{
cm.sendOk("点卷不足，无法购买！");
cm.dispose();
}
		}
    }
}


