importPackage(net.sf.odinms.client);
var status = 0;
var fee;
var chance = Math.floor(Math.random()*1);
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("咳咳。。");
            cm.dispose();
            return;
        }	
        if (mode == 1)
            status++;
        else
            status--;
		var MC = cm.getServerName();
		var 点券 = cm.getPlayer().getCSPoints(1);
		var 挖矿数量卡 = cm.getBossRank("挖矿数量卡",2);
		var 挖矿数量卡单价 = 100;

          if (status == 0) {
            cm.sendGetText("当前#b挖矿数量卡#k单价；#r"+挖矿数量卡单价+"#k\r\n你现在最多可以购买；#r"+(点券/挖矿数量卡单价).toFixed(0)+"#k\r\n\r\n请输入需要购买的数量；");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("你需要； #r "+fee*挖矿数量卡单价+" #k 点券。\r\n来购买； #r"+fee+"#k   张数量卡。");
		} else if (fee < 0) {
                cm.sendOk("输入有误!");
                cm.dispose();
        } else if (cm.getPlayer().getCSPoints(1) < fee*挖矿数量卡单价 ) {
                cm.sendOk("你的点券不够哦。");
                cm.dispose();
				} else {

                 if (chance <= 1) { 
					   cm.setBossRankCount("挖矿数量卡",fee);
					   cm.gainNX(-fee*挖矿数量卡单价);
					   cm.playerMessage(6,"成功购买了 "+fee+" 张[挖矿数量卡]");
	                   cm.dispose();
                       cm.openNpc(2000,3);	
	                } 
	                else  { 
                         cm.sendOk("未知错误，请联系大王");
	                     cm.dispose(); 
	                } 

            }
        }
    }

