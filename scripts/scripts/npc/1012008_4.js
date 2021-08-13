importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var ºÚË®¾§ = 4021008;
var ¸ĞÌ¾ºÅ = "#fUI/UIWindow/Quest/icon0#";
var Õı·½¼ıÍ· = "#fUI/Basic/BtHide3/mouseOver/0#";
var ÅÆ1 = "#fEffect/SkillName1.img/1001003/ÅÆ1#";
var ÅÆ2 = "#fEffect/SkillName1.img/1001003/ÅÆ2#";
var ÅÆ3 = "#fEffect/SkillName1.img/1001003/ÅÆ3#";
var ÅÆ4 = "#fEffect/SkillName1.img/1001003/ÅÆ4#";
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
	    var a1 = "#L1##b" + ÅÆ1 + "";
		var a2 = "#L2##b" + ÅÆ2 + "";
		var a3 = "#L3##b" + ÅÆ3 + "";
		var a4 = "#L4##b" + ÅÆ4 + "";


            cm.sendSimple("×ÔÓÉÃ°ÏÕµº·­ÅÆĞ¡ÓÎÏ·#rµÚËÄ¹Ø#k¡£\r\n\r\n"+a1+""+a2+""+a3+""+a4+"");
            
	    } else if (selection == 1) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
            cm.gainMeso(50000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,5);
			return;
			}
			else {
			cm.gainMeso(-50000);
			cm.sendOk("·­³µÁË·­³µÁË·­³µÁË¡£");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("¹ş¹ş¹ş¡¤¡¤¡¤¡¤");
			cm.dispose();
			return;
		}
		} else if (selection == 2) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
            cm.gainMeso(50000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,5);
			return;
			}
			else {
			cm.gainMeso(-50000);	
			cm.sendOk("·­³µÁË·­³µÁË·­³µÁË¡£");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("¹ş¹ş¹ş¡¤¡¤¡¤¡¤");
			cm.dispose();
			return;
		}
		} else if (selection == 3) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
            cm.gainMeso(50000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,5);
			return;
			}
			else {
		    cm.gainMeso(-50000);
			cm.sendOk("·­³µÁË·­³µÁË·­³µÁË¡£");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("¹ş¹ş¹ş¡¤¡¤¡¤¡¤");
			cm.dispose();
			return;
		}
		} else if (selection == 4) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<55){
            cm.gainMeso(50000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,5);
			return;
			}
			else {
		    cm.gainMeso(-50000);
			cm.sendOk("·­³µÁË·­³µÁË·­³µÁË¡£");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("¹ş¹ş¹ş¡¤¡¤¡¤¡¤");
			cm.dispose();
			return;
		}
        

		}
		
    }
}