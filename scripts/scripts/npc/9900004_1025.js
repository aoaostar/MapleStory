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
			//text += "#dNo.1 普通理财                    #v3700150##t3700150#\r\n价格:30充值币/周 100充值币/月\r\n特权:   每日领取\r\n1.金币150W.\r\n2.扫荡卡3张:\r\n3.祝福1张\r\n4.点卷2000\r\n#L1#购买月卡(100充值币)#l     #L2#购买周卡(30充值币)#l\r\n\r\n\r\n"
			text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""
			text += "#L1##b#v2043800##z2043800#      #r5充值币/1个 #l\r\n"
			text += "#L2##b#v2043700##z2043700#      #r5充值币/1个 #l\r\n"
			
			if(cm.haveItem(3700148,1) && cm.haveItem(3700149,1) && cm.haveItem(3700150,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.getmoneyb() >= 5){
			 cm.setmoneyb(-5);
			 cm.gainItem(2043800,1);	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M长杖魔力攻击卷一张~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2043700,1);

cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M短杖魔力攻击卷一张~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 3) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044400,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M矛攻击卷一张~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044300,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M枪攻击卷一张~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044200,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M双手钝器攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
			
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044100,1);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M双手斧攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		

}else if  (selection == 8) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2043200,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M单手钝器攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		
			
}else if  (selection == 9) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2043100,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M单手斧攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		

}else if  (selection == 10) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2043000,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M单手剑攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
			
}else if  (selection == 11) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044500,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M弓攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}		
			
}else if  (selection == 12) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044600,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M弩攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}

}else if  (selection == 13) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044800,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M拳甲攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}	

}else if  (selection == 14) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044900,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M短枪攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
			
			
}else if  (selection == 7) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(2044000,1);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功购买M双手剑攻击卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}				
			}
		}	
