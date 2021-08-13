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
                text += "在我这里可以制作贝勒德之戒哦！！\r\n";
            text += "" + 蓝色箭头 + "#L1##r制作#v1113072#低级贝勒德戒指（5四维、5G5M）\r\n需要：#v4031559# 40个、#v4031558# 40个、#v4002002# 40个、#v4000244# 5个、#v4000245# 5个、金币=1000W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L2##r制作#v1113073#中级贝勒德戒指（10四维、10G10M）\r\n需要：#v1113072# 1个、#v4031559# 60个、#v4031558# 60个、#v4002002# 60个、#v4000244# 10个、#v4000245# 10个、金币=2000W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L3##r制作#v1113074#高级贝勒德戒指（15四维、15G15M）\r\n需要：#v1113073# 1个、#v4031559# 80个、#v4031558# 80个、#v4002002# 80个、#v4000244# 15个、#v4000245# 15个、金币=3000W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L4##r制作#v1113075#最高级贝勒德戒指（25四维、25G25M）\r\n需要：#v1113074# 1个、#v4031559# 100个、#v4031558# 100个、#v4002002# 100个、#v4000244# 20个、#v4000245# 20个、金币=5000W\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L5##r制作#v1122126##z1122126#\r\n需要：#v4001126# 5000个、#v1122147# 1个、金币=1亿、#v4005004# 100个\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L6##r#v5211047##z5211047#\t使用权：7天权\t需要点卷：3000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r#v2049100##z2049100#10个\t需要点卷：15000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L8##r#v2340000##z2340000#10个\t需要点卷：15000点\r\n\r\n"//3
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
			if(cm.haveItem(4031559,40) && cm.haveItem(4031558,40) && cm.haveItem(4002002,40) && cm.haveItem(4000244,5) && cm.haveItem(4000245,5) && cm.getMeso() >= 10000000){
				cm.gainItem(4031559, -40);
				cm.gainItem(4031558, -40);
				cm.gainItem(4002002, -40);
				cm.gainItem(4000244, -5);
				cm.gainItem(4000245, -5);
				cm.gainMeso(-10000000);
		cm.gainItem(1113072,5,5,5,5,0,0,5,5,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[低级贝勒德戒指]，完成了变强的第一步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 2) {
			if(cm.haveItem(4031559,60) && cm.haveItem(1113072,1) && cm.haveItem(4031558,60) && cm.haveItem(4002002,60) && cm.haveItem(4000244,10) && cm.haveItem(4000245,10) && cm.getMeso() >= 20000000){
				cm.gainItem(4031559, -60);
				cm.gainItem(1113072, -1);
				cm.gainItem(4031558, -60);
				cm.gainItem(4002002, -60);
				cm.gainItem(4000244, -10);
				cm.gainItem(4000245, -10);
				cm.gainMeso(-20000000);
		cm.gainItem(1113073,10,10,10,10,0,0,10,10,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[中级贝勒德戒指]，完成了变强的第二步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 3) {
			if(cm.haveItem(4031559,80) && cm.haveItem(1113073,1) && cm.haveItem(4031558,80) && cm.haveItem(4002002,80) && cm.haveItem(4000244,15) && cm.haveItem(4000245,15) && cm.getMeso() >= 30000000){
				cm.gainItem(4031559, -80);
				cm.gainItem(1113073, -1);
				cm.gainItem(4031558, -80);
				cm.gainItem(4002002, -80);
				cm.gainItem(4000244, -15);
				cm.gainItem(4000245, -15);
				cm.gainMeso(-30000000);
		cm.gainItem(1113074,15,15,15,15,0,0,15,15,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[高级贝勒德戒指]，完成了变强的第三步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 4) {
			if(cm.haveItem(4031559,100) && cm.haveItem(1113074,1) && cm.haveItem(4031558,100) && cm.haveItem(4002002,100) && cm.haveItem(4000244,20) && cm.haveItem(4000245,20) && cm.getMeso() >= 50000000){
				cm.gainItem(4031559, -100);
				cm.gainItem(1113074, -1);
				cm.gainItem(4031558, -100);
				cm.gainItem(4002002, -100);
				cm.gainItem(4000244, -20);
				cm.gainItem(4000245, -20);
				cm.gainMeso(-50000000);
		cm.gainItem(1113075,25,25,25,25,0,0,25,25,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[最高级贝勒德戒指]，完成了变强的最后一步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 5) {
			if(cm.haveItem(4001126,5000) && cm.haveItem(1122147,1) && cm.haveItem(4005004,100) && cm.getMeso() >= 100000000){
				cm.gainItem(4001126, -5000);
				cm.gainItem(1122147, -1);
				cm.gainItem(4005004, -100);
				cm.gainMeso(-100000000);
				cm.gainItem(1122126, 1);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[真觉醒冒险之心-海盗专属]，完成了变强的最后一步！");
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


