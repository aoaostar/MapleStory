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
			text += "#L1##b#v1112140#四维双攻+20 HP/MP+500  #r40充值币/1个#l\r\n"
			text += "#L2##b#v1112139#四维双攻+40 HP/MP+666  #r60充值币/1个#l\r\n"
			text += "#L3##b#v1112138#四维双攻+60 HP/MP+888  #r80充值币/1个#l\r\n"
			
			
			if(cm.haveItem(3700148,1) && cm.haveItem(3700149,1) && cm.haveItem(3700150,1)){
				cm.sendOk("您已经办理了一个理财会员,请更换别的种类再来找我~!");
			} else {
				cm.sendSimple(text);
		 
			}
        } else if (selection == 1) {
			if(cm.getmoneyb() >= 40){
			 cm.setmoneyb(-40);
			 cm.gainItem(1112140,20,20,20,20,500,500,20,20,0,0,0,0,0,0);
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得白银VIP戒指一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}else if  (selection == 2) {
				if(cm.getmoneyb() >= 60){
					cm.setmoneyb(-60);
			 cm.gainItem(1112140,40,40,40,40,666,666,40,40,0,0,0,0,0,0);
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得黄金VIP戒指一个~!", 5121005);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 3) {
				if(cm.getmoneyb() >= 80){
					cm.setmoneyb(-80);
			 cm.gainItem(1112140,60,60,60,60,888,888,60,60,0,0,0,0,0,0);	 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得钻石VIP戒指一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 4) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010019,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得爱心效果一个~!", 5121007);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 5) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010027,1)	
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得上火面膜效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 8) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010016,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得警铃效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 9) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010055,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得太空船效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 10) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010024,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得玩具小鸭效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 11) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010045,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得雷劈效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 12) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010070,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得森林翅膀效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 13) {
				if(cm.getmoneyb() >= 10){
					cm.setmoneyb(-10);
			 cm.gainItem(5010043,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得眼光效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
}else if  (selection == 14) {
				if(cm.getmoneyb() >= 15){
					cm.setmoneyb(-15);
			 cm.gainItem(5010044,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得幻影残像效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}						
			
}else if  (selection == 7) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010041,1)		
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得音符效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}			
}else if  (selection == 6) {
				if(cm.getmoneyb() >= 5){
					cm.setmoneyb(-5);
			 cm.gainItem(5010038,1)		 
cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]成功获得瀑布效果一个~!", 5121006);
				cm.dispose();
			} else {
				cm.sendOk("您的充值币不足~!请充值！");
            cm.dispose();
			}
				}				
			}
		}	
