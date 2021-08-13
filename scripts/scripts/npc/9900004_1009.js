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
			//text += "\t\t\t\t#e#rVIP#e#b办理中心欢迎您\r\n\r\n"
			//text += "\t\t\t\r\n\r\n"
			//text += "\t\t\t#e#r一元软妹币=1充值币\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#dNo.1 低调白银VIP #v3700149##v1112247##v1112140#\r\n价格:40充值币/月\r\n  每日签到\r\n1.破攻数X2\r\n2.国庆纪念币X2\r\n3.祝福卷X2\r\n4.10%GM卷X2\r\n5.金币400万\r\n#L1#购买月卡(40充值币)#l\r\n\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#bNo.2 奢华黄金VIP #v3700150##v1112246##v1112139#\r\n#e#r价格:80充值币/月\r\n 每日签到\r\n1.破攻数X4\r\n2.国庆纪念币X4\r\n3.祝福卷X4\r\n4.10%GM卷X4\r\n5.金币800万\r\n#L2#购买月卡(80充值币)#l\r\n\r\n\r\n"
			text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			text += "#bNo.3 钻石VIP #v3700148#"+"您目前拥有:蛋糕:#b#c2002033#个\r\n#e#r价格:#v2002033#100个/周\r\n 每日签到\r\n1.破攻石每日次数不限\r\n2.蜂蜜X4\r\n3.祝福卷X6\r\n4.金币2000万\r\n#L3#购买周卡(#v2002033#100个)#l\r\n\r\n\r\n"
			//text += ""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+""+蓝色角点+"\r\n"
			//text += "#dNo.4典藏级养老永久VIP  #v3700150##t3700150#\r\n价格:168充值币/永久\r\n  每日领取\r\n1.破攻数X1\r\n2.国庆纪念币X1\r\n3.祝福卷X1\r\n4.10%GM卷X1\r\n5.680点券\r\n6.6888抵用券\r\n7.金币168万\r\n#L1#购买典藏级养老永久VIP(168充值币)#l \r\n\r\n\r\n"
			if(cm.haveItem(3700155,1) && cm.haveItem(3700156,1) && cm.haveItem(3700157,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
       }else if  (selection == 1) {
				 if(cm.haveItem(3700149,1)){
				   cm.sendOk("您已经是会员了！");
                   cm.dispose();
			} else	if(cm.getmoneyb() >= 40){
					cm.setmoneyb(-40);
					cm.gainjf(+40);
			// cm.gainItem(5211060,1,720);
            // cm.gainMeso(+100000000);
          //   cm.gainNX(+100000);
           //  cm.gainItem(2049116,30);
           //  cm.gainItem(2340000,30);
             cm.gainItem(3700149,1,720);		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买低调白银VIP一个月~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				 if(cm.haveItem(3700150,1)){
				   cm.sendOk("您已经是会员了！");
                   cm.dispose();
			} else	if(cm.getmoneyb() >= 80){
					cm.setmoneyb(-80);
					cm.gainjf(+80);
			// cm.gainItem(5211060,1,720);
            // cm.gainMeso(+100000000);
          //   cm.gainNX(+100000);
           //  cm.gainItem(2049116,30);
           //  cm.gainItem(2340000,30);
             cm.gainItem(3700150,1,720);		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买奢华黄金VIP一个月~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 3) {
				 if(cm.haveItem(3700148,1)){
				   cm.sendOk("您已经是会员了！");
                   cm.dispose();
			} else	if(cm.haveItem(2002033,100)) {
				cm.gainItem(2002033, -100);
			// cm.gainItem(5211060,1,720);
            // cm.gainMeso(+100000000);
          //   cm.gainNX(+100000);
           //  cm.gainItem(2049116,30);
           //  cm.gainItem(2340000,30);
             cm.gainItem(3700148,1,168);		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买钻石VIP~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.getmoneyb() >= 60){
					cm.setmoneyb(-60);
			 cm.gainItem(3700149,1,168);			
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买经济理财会员一个周~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 5) {
				 if(cm.haveItem(3700148,1)){
				   cm.sendOk("您已经是豪华型会员了！");
                   cm.dispose();
			} else	if(cm.getmoneyb() >= 188){
					cm.setmoneyb(-188);
					cm.gainjf(+188);
			// cm.gainItem(5211060,1,720);
             cm.gainMeso(+100000000);
             cm.gainNX(+100000);
             cm.gainItem(2049116,30);
             cm.gainItem(2340000,30);
             cm.gainItem(3700148,1,720);		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买豪华理财会员一个月~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 90){
					cm.setmoneyb(-90);
			 cm.gainItem(3700148,1,168);			 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]已经成功购买理财会员一个周~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}				
			}
		}	
