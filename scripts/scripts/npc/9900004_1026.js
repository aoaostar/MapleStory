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
			text += "#L1##b#v2041012#M四维披风卷      #r3充值币/1张 #l\r\n"
			text += "#L2##b#v2040024#M四维头盔卷      #r3充值币/1张 #l\r\n"
			text += "#L3##b#v2040412#M四维上衣卷      #r3充值币/1张 #l\r\n"
			text += "#L4##b#v2040612#M四维裤裙卷      #r3充值币/1张 #l\r\n"
			text += "#L5##b#v2040500#M四维全身铠甲卷    #r3充值币/1张 #l\r\n"
			text += "#L6##b#v2041300#M四维腰带卷      #r3充值币/1张 #l\r\n"
			text += "#L7##b#v2040700#M四维鞋子卷      #r3充值币/1张 #l\r\n"
			
			if(cm.haveItem(3700148,1) && cm.haveItem(3700149,1) && cm.haveItem(3700150,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.getmoneyb() >= 3){
			 cm.setmoneyb(-3);
			 cm.gainItem(2041012,1);	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维披风卷轴一张~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2040024,1)
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维头盔卷一张~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 3) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2040412,1)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维上衣卷一张~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2040612,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维裤裙卷一张~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2040500,1)	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维全身铠甲卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
			
}else if  (selection == 7) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2040700,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维鞋子卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}			
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 3){
					cm.setmoneyb(-3);
			 cm.gainItem(2041300,1)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得M四维腰带卷一张~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}				
			}
		}	
