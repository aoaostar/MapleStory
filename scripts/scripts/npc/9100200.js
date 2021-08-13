importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    var a1 = "#L1##b" + 正方箭头 + "金币10W 【大】【小】\r\n";
		var a2 = "#L2##b" + 正方箭头 + "金币100W【大】【小】\r\n";
		var a3 = "#L3##b" + 正方箭头 + "金币500W【大】【小】\r\n";


            cm.sendSimple("\r\n#d-来来来，买大买小，买好离手#i3994122# #k#fEffect/BasicEff.img/NoCri0/1##r【号赌博机】\r\n#r  【赔率】1:2【概率】5:5\r\n"+a3+"");
            
	    } else if (selection == 1) {
			
		if (cm.getMeso()>=100000  ) {
			cm.gainMeso(-100000);
			var rand=Math.floor(Math.random()*100);
			if(rand<50){
            cm.gainMeso(200000);
			cm.sendOk("恭喜你，赢了");
			cm.dispose();
			cm.worldMessage(6,"[赌博公告]：恭喜，玩家 "+cm.getName()+" 在赌博机①号获胜，获得20W金币 。");
			return;
			}
			else {

			cm.sendOk("哎，输了");
			cm.worldMessage(6,"[赌博公告]：遗憾，玩家 "+cm.getName()+" 在赌博机①号输了，丢失10W金币 。");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("你金币不够咯~或者你没有许可证~");
			cm.dispose();
			return;
		}
        } else if (selection == 2) {
			
		if (cm.getMeso()>=1000000  ) {
			cm.gainMeso(-1000000);
			var rand=Math.floor(Math.random()*100);
			if(rand<50){
            cm.gainMeso(2000000);
			cm.sendOk("恭喜你，赢了");
			cm.dispose();
			cm.worldMessage(6,"[赌博公告]：恭喜，玩家 "+cm.getName()+" 在赌博机①获胜，获得200W金币 。");
			return;
			}
			else {

			cm.sendOk("哎，输了");
			cm.worldMessage(6,"[赌博公告]：遗憾，玩家 "+cm.getName()+" 在赌博机①输了，丢失100W金币 。");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("你金币不够咯~~或者你没有许可证~");
			cm.dispose();
			return;
		}
		} else if (selection == 3) {
			
		if (cm.getMeso()>=5000000 ) {
			cm.gainMeso(-5000000);
			var rand=Math.floor(Math.random()*100);
			if(rand<50){
            cm.gainMeso(10000000);
			cm.sendOk("恭喜你，赢了");
			cm.dispose();
			cm.worldMessage(6,"[赌博公告]：恭喜，玩家 "+cm.getName()+" 在赌博机①获胜，获得1000W金币 。");
			return;
			}
			else {

			cm.sendOk("哎，输了");
			cm.worldMessage(6,"[赌博公告]：遗憾，玩家 "+cm.getName()+" 在赌博机①输了，丢失500W金币 。");
			cm.dispose();
			return;
				}
		} else {
			cm.sendOk("你金币不够咯~~或者你没有许可证~");
			cm.dispose();
			return;
		}

		}
		
    }
}