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
                text += "在我这里可以制作贝勒德耳环哦！！\r\n\r\n";
            text += "" + 蓝色箭头 + "#L1##r制作#v1032220#低级贝勒德耳环（5四维、5G5M）\r\n需要：#v4001126# 300个、#v4031559# 40个、#v4031558# 40个、#v4002003# 40个、#v4021010# 1个、#v4000463# 3个、金币=500W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L2##r制作#v1032221#中级贝勒德耳环（10四维、10G10M）\r\n需要：#v4001126# 500个、#v1032220# 1个、#v4031559# 60个、#v4031558# 60个、#v4002003# 60个、#v4021010# 1个、#v4000463# 5个、金币=1000W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L3##r制作#v1032222#高级级贝勒德耳环（15四维、15G15M）\r\n需要：#v4001126# 1000个、#v1032221# 1个、#v4031559# 80个、#v4031558# 80个、#v4002003# 80个、#v4021010# 2个、#v4000463# 10个、金币=2000W\r\n\r\n"//3
            text += "" + 蓝色箭头 + "#L4##r制作#v1032223#最高级贝勒德耳环（20四维、20G20M）\r\n需要：#v4001126# 3000个、#v1032222# 1个、#v4031559# 100个、#v4031558# 100个、#v4002003# 100个、#v4021010# 3个、#v4000463# 20个、金币=5000W\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L5##r制作#v1122126##z1122126#\r\n需要：#v4001126# 5000个、#v1122147# 1个、金币=1亿、#v4005004# 100个\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L6##r#v5211047##z5211047#\t使用权：7天权\t需要点卷：3000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r#v2049100##z2049100#10个\t需要点卷：15000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L8##r#v2340000##z2340000#10个\t需要点卷：15000点\r\n\r\n"//3
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
			if(cm.haveItem(4001126,300) && cm.haveItem(4031559,40) && cm.haveItem(4031558,40) && cm.haveItem(4002003,40) && cm.haveItem(4021010,1) && cm.haveItem(4000463,3) && cm.getMeso() >= 5000000){
				cm.gainItem(4001126, -300);
				cm.gainItem(4031559, -40);
				cm.gainItem(4031558, -40);
				cm.gainItem(4002003, -40);
				cm.gainItem(4021010, -1);
				cm.gainItem(4000463, -3);
				cm.gainMeso(-5000000);
		cm.gainItem(1032220,5,5,5,5,0,0,5,5,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[低级贝勒德耳环]，完成了变强的第一步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 2) {
			if(cm.haveItem(4001126,500) && cm.haveItem(1032220,1) && cm.haveItem(4031559,60) && cm.haveItem(4031558,60) && cm.haveItem(4002003,60) && cm.haveItem(4021010,1) && cm.haveItem(4000463,5) && cm.getMeso() >= 10000000){
				cm.gainItem(4001126, -500);
				cm.gainItem(1032220, -1);
				cm.gainItem(4031559, -60);
				cm.gainItem(4031558, -60);
				cm.gainItem(4002003, -60);
				cm.gainItem(4021010, -1);
				cm.gainItem(4000463, -5);
				cm.gainMeso(-10000000);
		cm.gainItem(1032221,10,10,10,10,0,0,10,10,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[中级贝勒德耳环]，完成了变强的第二步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 3) {
			if(cm.haveItem(4001126,1000) && cm.haveItem(1032221,1) && cm.haveItem(4031559,80) && cm.haveItem(4031558,80) && cm.haveItem(4002003,80) && cm.haveItem(4021010,2) && cm.haveItem(4000463,10) && cm.getMeso() >= 20000000){
				cm.gainItem(4001126, -1000);
				cm.gainItem(1032221, -1);
				cm.gainItem(4031559, -80);
				cm.gainItem(4031558, -80);
				cm.gainItem(4002003, -80);
				cm.gainItem(4021010, -2);
				cm.gainItem(4000463, -10);
				cm.gainMeso(-20000000);
		cm.gainItem(1032222,15,15,15,15,0,0,15,15,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[高级贝勒德耳环]，完成了变强的第三步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 4) {
			if(cm.haveItem(4001126,3000) && cm.haveItem(1032222,1) && cm.haveItem(4031559,100) && cm.haveItem(4031558,100) && cm.haveItem(4002003,100) && cm.haveItem(4021010,3) && cm.haveItem(4000463,20) && cm.getMeso() >= 50000000){
				cm.gainItem(4001126, -3000);
				cm.gainItem(1032222, -1);
				cm.gainItem(4031559, -100);
				cm.gainItem(4031558, -100);
				cm.gainItem(4002003, -100);
				cm.gainItem(4021010, -3);
				cm.gainItem(4000463, -20);
				cm.gainMeso(-20000000);
		cm.gainItem(1032223,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[最高级贝勒德耳环]，完成了变强的最后一步！");
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


