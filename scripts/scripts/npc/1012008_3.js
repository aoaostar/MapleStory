importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var ºÚË®¾§ = 4021008;
var ¸ĞÌ¾ºÅ = "#fUI/UIWindow/Quest/icon0#";
var Õı·½¼ıÍ· = "#fUI/Basic/BtHide3/mouseOver/0#";
var ÅÆ9 = "#fEffect/SkillName1.img/1001003/ÅÆ9#";
var ÅÆ8 = "#fEffect/SkillName1.img/1001003/ÅÆ8#";
var ÅÆ6 = "#fEffect/SkillName1.img/1001003/ÅÆ6#";
var ÅÆ7 = "#fEffect/SkillName1.img/1001003/ÅÆ7#";
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
	    var a1 = "#L1##b" + ÅÆ8 + "";
		var a2 = "#L2##b" + ÅÆ9 + "";
		var a3 = "#L3##b" + ÅÆ6 + "";
		var a4 = "#L4##b" + ÅÆ7 + "";


            cm.sendSimple("×ÔÓÉÃ°ÏÕµº·­ÅÆĞ¡ÓÎÏ·#rµÚÈı¹Ø#k¡£\r\n\r\n"+a1+""+a2+""+a3+""+a4+"");
            
	    } else if (selection == 1) {
			
		if (cm.getMeso()>=0  ) {
			var rand=Math.floor(Math.random()*100);
			if(rand<40){
            cm.gainMeso(25000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,4);
			return;
			}
			else {
			cm.gainMeso(-25000);
			cm.sendOk("Ë²¼ä±¬Õ¨¡£Íê³Éµ¥É±¡£");
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
			if(rand<45){
            cm.gainMeso(25000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,4);
			return;
			}
			else {
			cm.gainMeso(-25000);	
			cm.sendOk("Ë²¼ä±¬Õ¨¡£Íê³Éµ¥É±¡£");
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
			if(rand<45){
            cm.gainMeso(25000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,4);
			return;
			}
			else {
		    cm.gainMeso(-25000);
			cm.sendOk("Ë²¼ä±¬Õ¨¡£Íê³Éµ¥É±¡£");
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
			if(rand<45){
            cm.gainMeso(25000);
			cm.setBossRankCount("·­ÅÆ");
			cm.dispose();
			cm.openNpc(1012008,4);
			return;
			}
			else {
		    cm.gainMeso(-25000);
			cm.sendOk("Ë²¼ä±¬Õ¨¡£Íê³Éµ¥É±¡£");
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