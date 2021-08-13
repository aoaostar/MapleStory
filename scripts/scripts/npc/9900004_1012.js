var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
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
                text += "";
            }
			text += "\t\t\t\t#e#rVIP#e#b系统中心欢迎您\r\n\r\n"
			//text += "\t\t\t"+"您目前拥有:"+cm.getmoneyb()+"蛋糕\r\n\r\n"
			text += "\t\t\t#e#r人物特效：一周权\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#dNo.1 普通理财                    #v3700150##t3700150#\r\n价格:30蛋糕/周 100蛋糕/月\r\n特权:   每日领取\r\n1.金币150W.\r\n2.扫荡卡3张:\r\n3.祝福1张\r\n4.点卷2000\r\n#L1#购买月卡(100蛋糕)#l     #L2#购买周卡(30蛋糕)#l\r\n\r\n\r\n"
			text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""
			//text += "#L15##b#v5150038#  #r10蛋糕/1个#l\r\n"
			text += "#L1##b#v5010066#  #r#v2002033#1个#l   #L2##b#v5010023#  #r#v2002033#1个#l\r\n"
			text += "#L3##b#v5010013#  #r#v2002033#1个#l   #L4##b#v5010019#  #r5#v2002033#1个\r\n"
			text += "#L5##b#v5010027#  #r#v2002033#1个#l   #L6##b#v5010038#  #r5#v2002033#1个#l\r\n"
			text += "#L7##b#v5010041#  #r#v2002033#1个#l   #L8##b#v5010016#  #r5#v2002033#1个#l\r\n"
			text += "#L9##b#v5010055#  #r#v2002033#1个#l   #L10##b#v5010024#  #r#v2002033#1个#l\r\n"
			text += "#L11##b#v5010045#  #r#v2002033#1个#l   #L12##b#v5010070#  #r#v2002033#1个#l\r\n"
			text += "#L13##b#v5010043#  #r#v2002033#1个#l  #L14##b#v5010044#  #r#v2002033#1个#l\r\n"
			
			if(cm.haveItem(3700155,1) && cm.haveItem(3700156,1) && cm.haveItem(3700157,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010066,1,168);	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得DISCO效果一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010023,1,168)
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得怦怦小红心效果一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
			
}else if  (selection == 15) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5150038,1,168)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得超级明星美发卡一张~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}			
}else if  (selection == 3) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010013,1,168)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得烟火效果一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010019,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得爱心效果一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010027,1,168)	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得上火面膜效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 8) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010016,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得警铃效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 9) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010055,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得太空船效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 10) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010024,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得玩具小鸭效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 11) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010045,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得雷劈效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 12) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010070,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得森林翅膀效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 13) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010043,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得眼光效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 14) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010044,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得幻影残像效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}						
			
}else if  (selection == 7) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010041,1,168)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得音符效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}			
}else if  (selection == 6) {
				if(cm.haveItem(2002033,1)) {
				cm.gainItem(2002033, -1);
			 cm.gainItem(5010038,1,168)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得瀑布效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
				}				
			}
		}	
