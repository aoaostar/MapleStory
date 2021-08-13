importPackage(net.sf.odinms.client);
var 枫叶至尊10 = "#fEffect/SkillName1.img/1001003/枫叶至尊10#";

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
            cm.sendOk(" 至尊霸气。");
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
            cm.sendGetText("足球赛特效喇叭;\r\n#r向全服玩家发送足球赛特效喇叭\r\n#b2000点券/次#k\r\n#k禁止传播色情，辱骂等敏感词汇，喇叭不可覆盖，如果当前有喇叭播放，请稍后在推送");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("- 确定播出； #r" + fee + "#k ？");
        } else if (status == 2) {
            if(cm.getPlayer().getCSPoints(1) < 2000){
    		cm.sendOk("你的点券不够使用。");
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
				        
						 cm.gainNX(-2000);
						 cm.broadcastServerMsg(5121002, ""+ cm.getChar().getName() +"说；"+ fee +"",true);
                         cm.sendOk("已经成功发送；"+fee+"");
	
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
