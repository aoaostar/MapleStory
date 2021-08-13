importPackage(net.sf.cherry.client);

var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var status = 0;


	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
	if (mode == -1) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		} else {
	if (status >= 0 && mode == 0) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		return;
		}
	if (mode == 1)
		status++;
		else
		status--;





	if (status == 0) {
		
   	    var add = "真正的勇士.\r\n想要强大并不能只靠装备，要锻炼自身，强化自己，所以一切装备只是累赘，并不是真正的强大。\r\n\r\n";

		//add += "你现在的血量较少!\r\n";



		add += " #L0#"+正方箭头+"  #b200个#i4000016# 提升 #rHP 20#l#b\r\n";
		
		add += " #L1#"+正方箭头+"  #b200个#i4000016# 提升 #rHP 20#l#b\r\n";
 



		cm.sendSimple (add);    

	} else if (status == 1) {


	if (selection == 0) {
		 if(cm.haveItem(4000016,200)){
                     cm.gainItem(4000016,-200);
                     var xueliang=cm.getPlayer().getMaxHp();
                     cm.getPlayer().setMaxHp(xueliang+20);
                     cm.sendOk("体质提升成功\r\n生命值增加20点\r\n请小退游戏生效");
					 cm.worldMessage(6,"[体质强化]：玩家  "+cm.getName()+"  完成体质强化，生命值提升20点。");
                     cm.dispose();
                     }
                 else {
                     cm.sendOk("你缺少提升体质的材料");
                     cm.dispose();
                      }

	  	} else if (selection == 1) {
		 if(cm.haveItem(4000016,200)){
                     cm.gainItem(4000016,-200);
                     var xueliang=cm.getPlayer().getMaxHp();
                     cm.getPlayer().setMaxHp(xueliang+20);
                     cm.sendOk("体质提升成功\r\n生命值增加20点\r\n请小退游戏生效");
					 cm.worldMessage(6,"[体质强化]：玩家  "+cm.getName()+"  完成体质强化，生命值提升20点。");
                     cm.dispose();
                     }
                 else {
                     cm.sendOk("你缺少提升体质的材料");
                     cm.dispose();
                      }				  








		}					
		}
		}
	}
		

