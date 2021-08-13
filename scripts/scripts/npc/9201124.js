importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    var a1 = "#L1##b" + 正方箭头 + "装备敏捷提升#i4000038# x 1     金币 x 100.000\r\n";
		//var a2 = "#L2##b强 化 装 备（属性强化）金杯/1  【30% 】\r\n";
		//var a3 = "#L3##r强 化 装 备（属性强化）金猪/1  【40% 】\r\n";
		//var a4 = "#L4##r金 猪 必 成（属性强化）金猪/20 【100%】\r\n";
		//var a5 = "#L5##r金 猪 提 升（次数提升）金猪/5  【 5% 】";
		//var a6 = "#L6##r强 化 说 明\r\n";
		//var a7 = "#L7##r强 化 装 备（属性强化）金杯/20 【40% 】\r\n";

            cm.sendSimple("#r#i3994115##i3994115##i3994115##i3994115##i3994115#\r\n#d- 我可以帮你提升#r装备【敏捷】【命中】#k\r\n\r\n"+a1+"");
	    } else if (selection == 1) {
		if (cm.haveItem(4000038, 1)&&cm.getMeso()>=100000 ) {
			cm.gainItem(4000038, -1);
			cm.gainMeso(-100000);
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
			cm.gainEquiPproperty(0,0,0,0,3,0,0,0,0,2,0);//次数/w攻击/m攻击/力量/敏捷/智力/运气/HP/MP/命中/回避/
			cm.sendOk("#fEffect/BasicEff.img/Fishing/6#\r\n\r\n#r增加属性#k;#b   敏 捷 + 3  命 中 + 2");
			cm.dispose();
			cm.worldMessage(6,"[公告]：恭喜，玩家  "+cm.getName()+"  准备提升敏捷成功。");
			return;
			}
			else {
			cm.gainEquiPproperty(0,0,0,0,-3,0,0,0,0,-2,0);
			//cm.sendOk("真遗憾，提升失败，还损失了一些属性。");
			cm.sendOk("很遗憾失败了:#r\r\n敏捷-3 \r\n命中-2");
			cm.worldMessage(6,"[公告]：遗憾，玩家  "+cm.getName()+"  装备提升敏捷失败。");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("你没有#i4000038#，或者#i4031039#金币 x 100000!!!");
			cm.dispose();
			return;
		}
		} else if (selection == 2) {
		if (cm.haveItem(4000038, 1) ) {
			cm.gainItem(4000038, -1);
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
			cm.gainEquiPproperty(0,1,2,3,3,3,3,30,30,3,3);
			cm.sendOk("恭喜你啊，强化好了，看看是不是比之前更漂亮了。");
			cm.dispose();
			return;
			}
			else {
			cm.gainEquiPproperty(0,-2,-4,-5,-5,-5,-5,-50,-50,-5,-5);
			cm.sendOk("真遗憾，强化失败，损失了一些属性~");
			cm.dispose();
			return;
			}
		} else {
			cm.sendOk("你没有金杯，所以不能强化!!!");
			cm.dispose();
			return;
		}
		} else if (selection == 3) {
		if (cm.haveItem(4032226, 1) ) {
			cm.gainItem(4032226, -1);
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
			cm.gainEquiPproperty(0,2,4,4,4,4,4,40,40,4,4);
			cm.sendOk("恭喜你啊，强化好了，看看是不是比之前更漂亮了。");
			cm.dispose();
			return;
			}
			else {
			cm.gainEquiPproperty(0,-2,-4,-2,-2,-2,-2,-20,-20,-2,-2);
			cm.sendOk("真遗憾，强化失败，损失了一些属性~");
			cm.dispose();
			return;
			}
		} else {
			cm.sendOk("你没有金猪，所以不能强化!!!");
			cm.dispose();
			return;
		}
		} else if (selection == 4) {
		if (cm.haveItem(4032226, 20) ) {
			cm.gainItem(4032226, -20);
			var rand=Math.floor(Math.random()*100);
			if(rand<50){
			cm.gainEquiPproperty(0,2,4,6,6,6,6,60,60,6,6);
			cm.sendOk("恭喜你强化出第一种属性");
			cm.dispose();
			return;
			}
			else {
			cm.gainEquiPproperty(0,1,2,3,3,3,3,30,30,3,3);
			cm.sendOk("恭喜你强化出第二种属性");
			cm.dispose();
			return;
			}
		} else {
			cm.sendOk("你没有金猪，所以不能强化!!!");
			cm.dispose();
			return;
		}
		} else if (selection == 5) {
		if (cm.haveItem(4032226, 5) ) {
			cm.gainItem(4032226, -5);
			var rand=Math.floor(Math.random()*100);
			if(rand<5){
			cm.gainEquiPproperty(1,0,0,0,0,0,0,0,0,0,0);
			cm.sendOk("恭喜你提升次数成功了");
			cm.dispose();
			return;
			}
			else {
			cm.gainEquiPproperty(0,0,0,-1,-1,-1,-1,-1,-1,-1,-1);
			cm.sendOk("提升失败，装备损失一些属性");
			cm.dispose();
			return;
			}
		} else {
			cm.sendOk("你没有金猪，所以不能提升!!!");
			cm.dispose();
			return;
		}
		} else if (selection == 7) {
		if (cm.haveItem(4000038, 20) ) {
			cm.gainItem(4000038, -20);
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
			cm.gainEquiPproperty(0,1,2,3,3,3,3,30,30,3,3);
			cm.sendOk("恭喜你强化成功，成功提升装备属性。");
			cm.dispose();
			return;
			}
			else {
			cm.gainEquiPproperty(0,-2,-4,-5,-5,-5,-5,-50,-50,-5,-5);
			cm.sendOk("真遗憾，强化失败，还损失了一些属性。");
			cm.dispose();
			return;
			}
		} else {
			cm.sendOk("你没有金杯，所以不能强化!!!");
			cm.dispose();
			return;
		}
		}else if(selection == 6){
			var strlen = "#r看群文件的强化攻略有详细说明。#k";
			cm.sendOk(strlen);
			cm.dispose();
		}
		
    }
}