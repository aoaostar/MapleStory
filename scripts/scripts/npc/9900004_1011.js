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
			text += "\t\t\t"+"您目前拥有:"+cm.getmoneyb()+"充值币\r\n\r\n"
			text += "\t\t\t#e#r一元软妹币=1充值币\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#dNo.1 普通理财                    #v3700150##t3700150#\r\n价格:50充值币/周 100充值币/月\r\n特权:   每日领取\r\n1.金币150W.\r\n2.扫荡卡3张:\r\n3.祝福1张\r\n4.点卷2000\r\n#L1#购买月卡(100充值币)#l     #L2#购买周卡(50充值币)#l\r\n\r\n\r\n"
			text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""
			text += "#L1##b#v1002186#四维双攻+10      #r30充值币/1个 #l\r\n"
			text += "#L2##b#v1012057#四维双攻+10     #r30充值币/1个 #l\r\n"
			text += "#L3##b#v1022048#四维双攻+10    #r30充值币/1个 #l\r\n"
			text += "#L4##b#v1082102#四维双攻+10     #r30充值币/1个 #l\r\n"
			text += "#L5##b#v1072153#四维双攻+10     #r30充值币/1个 #l\r\n"
			text += "#L6##b#v1032024#四维双攻+10     #r30充值币/1个 #l\r\n"
			text += "#L7##b#v1102039#四维双攻+10     #r30充值币/1个 #l\r\n"
			text += "#L8##b#v1102039#加10000血量     #r100充值币/1个 #l\r\n"
			text += "#L9##b#v1102039#加20000血量     #r200充值币/1个 #l\r\n"
			//text += "#L10##b#v1102039#加30000血量    #r300充值币/1个 #l\r\n"
			if(cm.haveItem(3700155,1) && cm.haveItem(3700156,1) && cm.haveItem(3700157,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.getmoneyb() >= 30){
			 cm.setmoneyb(-30);
			 cm.gainjf(+30);
			 cm.gainItem(1002186,10,10,10,10,0,0,10,15,0,0,0,0,0,0);	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明帽一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.getmoneyb() >= 30){
					cm.setmoneyb(-30);
					cm.gainjf(+30);
			 cm.gainItem(1012057,10,10,10,10,0,0,10,15,0,0,0,0,0,0)
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明面具一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 3) {
				if(cm.getmoneyb() >= 30){
					cm.setmoneyb(-30);
					cm.gainjf(+30);
			 cm.gainItem(1022048,10,10,10,10,0,0,10,15,0,0,0,0,0,0)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明眼饰一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.getmoneyb() >= 30){
					cm.setmoneyb(-30);
					cm.gainjf(+30);
			 cm.gainItem(1082102,10,10,10,10,0,0,10,15,0,0,0,0,0,0)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明手套一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.getmoneyb() >= 30){
					cm.setmoneyb(-30);
					cm.gainjf(+30);
			 cm.gainItem(1072153,10,10,10,10,0,0,10,15,0,0,0,0,0,0)	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明鞋一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
			
}else if  (selection == 7) {
				if(cm.getmoneyb() >= 30){
					cm.setmoneyb(-30);
					cm.gainjf(+30);
			 cm.gainItem(1102039,10,10,10,10,0,0,10,15,0,0,0,0,0,0)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明披风一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}			
			
			}else if  (selection == 8) {
				if(cm.getmoneyb() >= 100){
					cm.setmoneyb(-100);
					cm.gainjf(+100);
			 cm.gainItem(1102039,20,20,20,35,10000,10,35,50,0,0,0,0,0,0)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得血量+10000 透明披风一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		
			
			
			}else if  (selection == 9) {
				if(cm.getmoneyb() >= 200){
					cm.setmoneyb(-200);
					cm.gainjf(+200);
			 cm.gainItem(1102039,20,20,20,35,20000,20,35,50,0,0,0,0,0,0)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得血量+20000 透明披风一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		
			
			}else if  (selection == 10) {
				if(cm.getmoneyb() >= 300){
					cm.setmoneyb(-300);
					cm.gainjf(+300);
			 cm.gainItem(1102039,20,20,20,35,30000,20,35,50,0,0,0,0,0,0)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得血量+30000 透明披风一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		
			
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 50){
					cm.setmoneyb(-50);
					cm.gainjf(50);
			 cm.gainItem(1032024,20,20,20,35,0,0,20,35,0,0,0,0,0,0)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得四维双攻+10 透明耳环一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}				
			}
		}	
