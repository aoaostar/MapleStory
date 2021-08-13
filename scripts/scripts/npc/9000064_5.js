importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*1+1);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("#i3994125# - 我会继续在这里等着你归来的。");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
       // if (status == 0) {
        //    cm.sendAcceptDecline("#v5073000##v5073000##v5073000##v5073000##v5073000##v5073000##v5073000##v5073000##v5073000#\r\n\r\n\r\n ");
       // } else

			if (status == 0)
				{
            cm.sendGetText("#b500#k点卷/次,现有#d "+cm.getPlayer().getCSPoints(1)+"#k 点卷\r\n今日使用了全服弹窗 #r"+ cm.getChar().getBossLog("qftc")+"#k 次\r\n#b警告；#r禁止辱骂带有攻击性的言语#k\r\n输入你想要说的话吧...");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("- 你确定要推送此信息； #r" + fee + "#k ？");
        } else if (status == 2) {
            if ( cm.getPlayer().getNX() < 500 ){

		cm.sendOk("你点卷不够，无法使用 ");
		cm.dispose();
          //  } else if (cm.getNX() < 500000) {
               // cm.sendOk("请先确定包里的金币不能低于#r50000000!");
               // cm.dispose();
           // } else if (cm.getPlayer().getNX() < 10000) {
               // cm.sendOk("对不起,你的金币不足100000,所以不能参于此次下注!");
               // cm.dispose();
           // } else if (cm.getText() > 10000) {
           //     cm.sendOk("哦不好意思哦!这里最大赌注不能超过1亿!");
           //     cm.dispose();
           // } else if (cm.getText() < 10000000) {
             //   cm.sendOk("#i3994125# 低于#r10000000#k金币？那你还是去别的赌博机玩吧。");
            //    cm.dispose();
            } else {
                 if (chance <= 1) { 
				         cm.gainNX(-500);
				         cm.setBossLog("qftc");
                         cm.worldMessage(1,"["+ cm.getChar().getName() +"]说；\r\n"+ fee +" "); 
						 cm.serverNotice("[全服弹窗]：玩家 "+ cm.getChar().getName() +" 今日使用了全服弹窗 "+ cm.getChar().getBossLog("qftc")+" 次，"); 
	                    cm.dispose(); 
	                } 
	                else if (chance == 2) { 
	                     //cm.gainMeso(fee); 
	                     cm.sendNext("#i3994125# - #r这把不算，重新再来，你手不要抖动。#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机手抖了一下，赌局作废。"); 
	                     cm.dispose(); 
	                } 
					else if (chance == 3) { 
	                    cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 4) { 
	                    cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 5) { 
	                     //cm.gainMeso(fee); 
	                     cm.sendNext("#i3994125# - #r这把不算，重新再来，你手不要抖动。#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机手抖了一下，赌局作废。"); 
	                     cm.dispose(); 
	                } 
					else if (chance == 6) { 
	                   cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 7) { 
	                     //cm.gainMeso(fee); 
	                     cm.sendNext("#i3994125# - #r这把不算，重新再来，你手不要抖动。#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机手抖了一下，赌局作废。"); 
	                     cm.dispose(); 
	                } 
					else if (chance == 8) { 
	                    cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 9) { 
	                   cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 10) { 
	                   cm.gainMeso(-fee); 
	                    cm.sendNext("#i3994125# - #r你输了#k"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                    cm.dispose(); 
	                } 
	                else if (chance == 11) { 
	                     cm.gainMeso(-fee); 
	                     cm.sendNext("哦··你的运气不怎么好哦··哈哈 再来一把嘛!"); 
                         cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机输了，看来今天并不被女神并眷顾!"); 
	                     cm.dispose(); 
	                } 
                    else if (chance >= 12) {
                    cm.gainMeso(fee * 2);
                    //cm.gainItem(2022282,1);                   
                    cm.sendNext("#i3994125# -#r不错哦，恭喜你赢了，我愿赌服输!");
                    cm.serverNotice("[赌博公告]：玩家 "+ cm.getChar().getName() +" ，在赌场⑹号机赢了，将筹码翻了【二倍】!");
                    cm.dispose();
                }
            }
        }
    }
}
