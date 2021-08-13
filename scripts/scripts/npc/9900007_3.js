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
            cm.sendOk("#i3994125# - 我会继续在这里等着你的。");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
if (status == 0) {
            cm.sendGetText("输入你想要充值的喇叭点数量。\r\n喇叭点与点券比例是；#r1:1");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo(" - 你确定要充值 #r" + fee + "#k 喇叭点吗？");
        } else if (status == 2) {
            if (cm.getPlayer().getCSPoints(1) < fee) {
                cm.sendOk("哦呵，不好意思你没那么多钱了。");
                cm.dispose();
      //  } else if (cm.getNX() < 50000000) {
                //cm.sendOk("请先确定包里的金币不能低于#r50000000!");
                //cm.dispose();
      //  } else if (cm.getText() < 10000000) {
                //cm.sendOk("#i3994125# 低于#r10000000#k金币？那你还是去别的赌博机玩吧。");
                //cm.dispose();
        } else {
                 if (chance <= 1) { 
	                    cm.gainNX(-fee); 
	                    cm.setBossRankCount("喇叭点",fee);	 
                         cm.serverNotice("[喇叭公告]：玩家 "+ cm.getChar().getName() +" ，成功充值 "+fee+" 喇叭点!"); 
	                    cm.dispose(); 
	                } 
            }
        }
    }
}
