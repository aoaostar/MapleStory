importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var 黑水晶 = 4021008;
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 牌1 = "#fEffect/SkillName1.img/1001003/牌10#";
var 牌2 = "#fEffect/SkillName1.img/1001003/牌11#";
var 牌3 = "#fEffect/SkillName1.img/1001003/牌12#";
var 牌4 = "#fEffect/SkillName1.img/1001003/牌13#";
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
	    var a1 = "#L1##b" + 牌1 + "";
		var a2 = "#L2##b" + 牌2 + "";
		var a3 = "#L3##b" + 牌3 + "";
		var a4 = "#L4##b" + 牌4 + "";


            cm.sendSimple("自由冒险岛翻牌小游戏#r第八关#k。\r\n\r\n"+a1+""+a2+""+a3+""+a4+"");
            
	    } else if (selection == 1) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
            cm.gainMeso(450000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,9);
			return;
			}
			else {
			cm.gainMeso(-450000);
			cm.sendOk("成功也需要运气，当然也需要人品！");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("哈哈哈····");
			cm.dispose();
			return;
		}
		} else if (selection == 2) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
            cm.gainMeso(450000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,9);
			return;
			}
			else {
			cm.gainMeso(-450000);	
			cm.sendOk("成功也需要运气，当然也需要人品！");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("哈哈哈····");
			cm.dispose();
			return;
		}
		} else if (selection == 3) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
            cm.gainMeso(450000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,9);
			return;
			}
			else {
		    cm.gainMeso(-450000);
			cm.sendOk("成功也需要运气，当然也需要人品！");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("哈哈哈····");
			cm.dispose();
			return;
		}
		} else if (selection == 4) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<30){
            cm.gainMeso(450000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,9);
			return;
			}
			else {
		    cm.gainMeso(-450000);
			cm.sendOk("成功也需要运气，当然也需要人品！");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("哈哈哈····");
			cm.dispose();
			return;
		}
        

		}
		
    }
}