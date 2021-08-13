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
			//text += "\t\t\t#e#r一元软妹币=1蛋糕\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#dNo.1 普通理财                    #v3700150##t3700150#\r\n价格:30蛋糕/周 100蛋糕/月\r\n特权:   每日领取\r\n1.金币150W.\r\n2.扫荡卡3张:\r\n3.祝福1张\r\n4.点卷2000\r\n#L1#购买月卡(100蛋糕)#l     #L2#购买周卡(30蛋糕)#l\r\n\r\n\r\n"
			text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""
			//text += "#L1##b#v2070012##z2070012#      #r10蛋糕/1个 #l\r\n"
			text += "#L2##b#v2070023##z2070023#      #r#v2002033#3个 #l\r\n"
			//text += "#L3##b#v2070011##z2070011#      #r20蛋糕/1个 #l\r\n"
			text += "#L4##b#v2070024##z2070024#      #r#v2002033#3个 #l\r\n"
			text += "#L5##b#v2070016##z2070016#      #r#v2002033#3个 #l\r\n"
			//text += "#L6##b#v2070026##z2070026#      #r50蛋糕/1个 #l\r\n"
			text += "#L7##b#v2070019##z2070019#      #r#v2002033#5个 #l\r\n"
			if(cm.haveItem(3700155,1) && cm.haveItem(3700156,1) && cm.haveItem(3700157,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.getmoneyb() >= 10){
			 cm.setmoneyb(-10);
			 cm.gainjf(+10);
			 cm.gainItem(2070012,1);	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 纸飞机一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.haveItem(2002033,3)) {
				cm.gainItem(2002033, -3);
			 cm.gainItem(2070023,1);

cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 火焰飞镖一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 3) {
				if(cm.getmoneyb() >= 20){
					cm.setmoneyb(-20);
					cm.gainjf(+20);
			 cm.gainItem(2070011,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 枫叶镖一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.haveItem(2002033,3)) {
				cm.gainItem(2002033, -3);
			 cm.gainItem(2070024,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 苦无一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.haveItem(2002033,3)) {
				cm.gainItem(2002033, -3);
					
			 cm.gainItem(2070016,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 水晶飞镖一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
			
}else if  (selection == 7) {
				if(cm.haveItem(2002033,5)) {
				cm.gainItem(2002033, -5);
			 cm.gainItem(2070019,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 蓝色死神飞镖一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}			
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 50){
					cm.setmoneyb(-50);
					cm.gainjf(+50);
			 cm.gainItem(2070026,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买稀有飞镖 白金飞镖一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的蛋糕不足~!请兑换后再来！");
            cm.dispose();
			}
				}				
			}
		}	
