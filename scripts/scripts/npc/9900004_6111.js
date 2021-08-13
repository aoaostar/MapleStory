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
                text += "在我这里可以制作各种血面装备哦哦！！\r\n\r\n";
            text += "" + 蓝色箭头 + "#L1##r制作#v1012187#（HP+500 MP+500 四维+5）\r\n需要：#v4000313# 200个、#v4002000# 30个、#v4002001# 30个、#v4002002# 30个、#v4002003# 30个、金币=500W\r\n\r\n"//3
			
            text += "" + 蓝色箭头 + "#L2##r制作#v1012188#（HP+1000 MP+1000 四维+10）\r\n需要：#v4001266# 10个、#v4002000# 50个、#v4002001# 50个、#v4002002# 50个、#v4002003# 50个 、#v4031559# 10个、#v1012187# 1个、金币=500W\r\n\r\n"//3
			
            text += "" + 蓝色箭头 + "#L3##r制作#v1012189#（HP+2000 MP+2000 四维+20）\r\n需要：#v4000313# 1000个、#v4000243# 5个、#v4000235# 5个、#v4031559# 50个、#v4031558# 20个、#v1012188# 1个、金币=1000W\r\n\r\n"//3
			
            text += "" + 蓝色箭头 + "#L4##r制作#v1012190#（HP+3000 MP+3000 四维+30）\r\n需要：#v4000313# 2000个、#v4001266# 20个、#v4000244# 5个、#v4000245# 5个、#v4000463# 5个、#v1012189# 1个、金币=2000W\r\n\r\n"//3
			
            text += "" + 蓝色箭头 + "#L5##r制作#v1012191#（HP+4000 MP+4000 四维+40）\r\n需要：#v4001084# 1个、#v1002927# 1个、#v1002926# 1个、#v4031196# 20个、#v4000463# 20个、#v1012190# 1个、金币=4000W\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L5##r制作#v1122126##z1122126#\r\n需要：#v4001126# 5000个、#v1122147# 1个、金币=1亿、#v4005004# 100个\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L6##r#v5211047##z5211047#\t使用权：7天权\t需要点卷：3000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L7##r#v2049100##z2049100#10个\t需要点卷：15000点\r\n\r\n"//3
            //text += "" + 蓝色箭头 + "#L8##r#v2340000##z2340000#10个\t需要点卷：15000点\r\n\r\n"//3
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
			if(cm.haveItem(4000313,200) && cm.haveItem(4002000,30) && cm.haveItem(4002001,30) && cm.haveItem(4002002,30) && cm.haveItem(4002003,30) && cm.getMeso() >= 5000000){
				cm.gainItem(4000313, -200);
				cm.gainItem(4002000, -30);
				cm.gainItem(4002001, -30);
				cm.gainItem(4002002, -30);
				cm.gainItem(4002003, -30);
				cm.gainMeso(-5000000);
		cm.gainItem(1012187,5,5,5,5,500,500,0,0,0,0,0,0,0,0);//血面
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[血面第一阶段]，完成了变强的第1步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 2) {
			if(cm.haveItem(4001266,10) && cm.haveItem(4002000,50) && cm.haveItem(4002001,50) && cm.haveItem(4002002,50) && cm.haveItem(4002003,50) && cm.haveItem(4031559,10) && cm.haveItem(1012187,1) && cm.getMeso() >= 5000000){
				cm.gainItem(4001266, -10);
				cm.gainItem(4002000, -50);
				cm.gainItem(4002001, -50);
				cm.gainItem(4002002, -50);
				cm.gainItem(4002003, -50);
				cm.gainItem(4031559, -10);
				cm.gainItem(1012187, -1);
				cm.gainMeso(-5000000);
		cm.gainItem(1012188,10,10,10,10,1000,1000,0,0,0,0,0,0,0,0);//萧公手套
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[血面第二阶段]，完成了变强的第2步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 3) {
			if(cm.haveItem(4000313,1000) && cm.haveItem(4000243,5) && cm.haveItem(4000235,5) && cm.haveItem(4031559,50) && cm.haveItem(4031558,20) && cm.haveItem(1012188,1) && cm.getMeso() >= 10000000){
				cm.gainItem(4000313, -1000);
				cm.gainItem(4000243, -5);
				cm.gainItem(4000235, -5);
				cm.gainItem(4031559, -50);
				cm.gainItem(4031558, -20);
				cm.gainItem(1012188, -1);
				cm.gainMeso(-10000000);
		cm.gainItem(1012189,20,20,20,20,2000,2000,0,0,0,0,0,0,0,0);//萧公手套
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[血面第三阶段]，完成了变强的第3步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 4) {
			if(cm.haveItem(4001266,20) && cm.haveItem(4000313,2000) && cm.haveItem(4000244,5) && cm.haveItem(4000245,5) && cm.haveItem(4000463,5) && cm.haveItem(1012189,1) && cm.getMeso() >= 20000000){
				cm.gainItem(4001266, -20);
				cm.gainItem(4000313, -2000);
				cm.gainItem(4000244, -5);
				cm.gainItem(4000245, -5);
				cm.gainItem(4000463, -5);
				cm.gainItem(1012189, -1);
				cm.gainMeso(-20000000);
		cm.gainItem(1012190,30,30,30,30,3000,3000,0,0,0,0,0,0,0,0);//萧公手套
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[血面第四阶段]，完成了变强的第4步！");
            cm.dispose();
			}else{
            cm.sendOk("道具不足无法换购！");
            cm.dispose();
			}
        } else if (selection == 5) {
			if(cm.haveItem(4001084,1) && cm.haveItem(1002927,1) && cm.haveItem(1002926,1) && cm.haveItem(4031196,20) && cm.haveItem(4000463,20) && cm.haveItem(1012190,1) && cm.getMeso() >= 40000000){
				cm.gainItem(4001084, -1);
				cm.gainItem(1002927, -1);
				cm.gainItem(1002926, -1);
				cm.gainItem(4031196, -20);
				cm.gainItem(4000463, -20);
				cm.gainItem(1012190, -1);
				cm.gainMeso(-40000000);
		cm.gainItem(1012191,40,40,40,40,4000,4000,6,6,0,0,0,0,0,0);//萧公手套
cm.喇叭(2, "[" + cm.getPlayer().getName() + "]成功制作[血面第五阶段]，完成了变强的第5步！");
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


