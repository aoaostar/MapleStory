importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var 黑水晶 = 4021008;
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 牌4 = "#fEffect/SkillName1.img/1001003/牌4#";
var 牌5 = "#fEffect/SkillName1.img/1001003/牌5#";
var 牌6 = "#fEffect/SkillName1.img/1001003/牌6#";
var 牌7 = "#fEffect/SkillName1.img/1001003/牌7#";
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
	    var a1 = "      #L1##b" + 牌4 + "";
		var a2 = "#L2##b" + 牌5 + "";
		var a3 = "#L3##b" + 牌6 + "";
		


            cm.sendSimple("自由冒险岛翻牌小游戏#r第一关#k。\r\n\r\n"+a1+""+a2+""+a3+"");
            
	    } else if (selection == 1) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<50){
            cm.gainMeso(10000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,2);
			return;
			}
			else {
			cm.gainMeso(-10000);
			cm.sendOk("犯错了牌，没办法进入下一关了。");
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
			if(rand<50){
            cm.gainMeso(10000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,2);
			return;
			}
			else {
			cm.gainMeso(-10000);	
			cm.sendOk("犯错了牌，没办法进入下一关了。");
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
			if(rand<50){
            cm.gainMeso(10000);
			cm.setBossRankCount("翻牌");
			cm.dispose();
			cm.openNpc(1012008,2);
			return;
			}
			else {
		    cm.gainMeso(-10000);
			cm.sendOk("犯错了牌，没办法进入下一关了。");
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