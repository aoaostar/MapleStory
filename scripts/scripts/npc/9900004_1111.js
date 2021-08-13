var 正在进行中 = "#fUI/UIWindow/Quest/Tab/enabled/1#";
var 完成 = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var 正在进行中蓝 = "#fUI/UIWindow/MonsterCarnival/icon1#";
var 完成红 = "#fUI/UIWindow/MonsterCarnival/icon0#";
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
			text +="\t\t#e#d欢迎领取#r在线奖励  #d在线时间：#r" + cm.getGamePoints() + "分钟#k#n\r\n "
			// text +="#b在线奖励依次为.特殊50个、枫叶50个-超级50个、管理者的祝福1个-强化3个、祝福3个-金色枫叶20个、皇家理发1个-蓝蜗牛一张、抵用1000-树妖1张、抵用1500-绿水灵一张、国庆币2个\r\n"
			text +="#L1##r领取永久雇佣商人！#v5030001# x1 市场摆摊#l\r\n\r\n\r\n"//3
            if(cm.getPlayer().getGamePoints() >= 60 && cm.getPlayer().getGamePointsPD() == 0){
					text += "#L2##r"+完成红+"在线时间超过60分钟！"+完成+"金币*60W#v2022035#*1个#v2020031#*1个#v2000019#*60个\r\n\r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 60 && cm.getPlayer().getGamePointsPD() > 0){
					text += ""+完成红+"#r在线时间超过60分钟！#l"+完成+"金币*60W#v2022035#*1个#v2020031#*1个#v2000019#*60个\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过60分钟！#l"+正在进行中+"金币*60W#v2022035#*1个#v2020031#*1个#v2000019#*60个\r\n\r\n"//3
			}		
		    if(cm.getPlayer().getGamePoints() >= 120 && cm.getPlayer().getGamePointsPD() == 1){
					text += "#L3##r"+完成红+"在线时间超过120分钟！"+完成+"金币*120W 点卷*1000 #v5440000#*1200 \r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 120 && cm.getPlayer().getGamePointsPD() > 1){
					text += ""+完成红+"#r在线时间超过120分钟！#l"+完成+"金币*120W 点卷*1000 #v5440000#*1200\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过120分钟！#l"+正在进行中+"金币*120W 点卷*1000 #v5440000#*1200\r\n\r\n"//3
			}
			if(cm.getPlayer().getGamePoints() >= 180 && cm.getPlayer().getGamePointsPD() == 2){
					text += "#L4##r"+完成红+"在线时间超过180分钟！"+完成+"金币*180W #v5440000#*1800 #v2049116#*20个 \r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 180 && cm.getPlayer().getGamePointsPD() > 2){
					text += ""+完成红+"#r在线时间超过180分钟！#l"+完成+"金币*180W #v5440000#*1800 #v2049116#*20个\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过180分钟！#l"+正在进行中+"金币*180W #v5440000#*1800 #v2049116#*20个\r\n\r\n"//3
			}
			if(cm.getPlayer().getGamePoints() >= 240 && cm.getPlayer().getGamePointsPD() == 3){
					text += "#L5##r"+完成红+"在线时间超过240分钟！"+完成+"金币*240W #v5440000#*2400 #v2340000#*20个\r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 240 && cm.getPlayer().getGamePointsPD() > 3){
					text += ""+完成红+"#r在线时间超过240分钟！#l"+完成+"金币*240W #v5440000#*2400 #v2340000#*20个\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过240分钟！#l"+正在进行中+"金币*240W #v5440000#*2400 #v2340000#*20个\r\n\r\n"//3
			}
			if(cm.getPlayer().getGamePoints() >= 300 && cm.getPlayer().getGamePointsPD() == 4){
					text += "#L8##r"+完成红+"在线时间超过420分钟！"+完成+"#v5440000#*4200 #v2000004#*50个 金币*1500W\r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 300 && cm.getPlayer().getGamePointsPD() > 4){
					text += ""+完成红+"#r在线时间超过420分钟！#l"+完成+"#v5440000#*4200 #v2000004#*50个 金币*1500W\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过300分钟！#l"+正在进行中+"#v5440000#*4200 #v2000004#*50个 金币*1500W\r\n\r\n"//3
			}
			if(cm.getPlayer().getGamePoints() >= 360 && cm.getPlayer().getGamePointsPD() == 5){
					text += "#L7##r"+完成红+"在线时间超过360分钟！"+完成+"金币*1000W #v5440000#*3600 #v2010006#*1个#k\r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 360 && cm.getPlayer().getGamePointsPD() > 5){
					text += ""+完成红+"#r在线时间超过360分钟！#l"+完成+"金币*1000W #v5440000#*3600 #v2010006#*1个#k\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过360分钟！#l"+正在进行中+"金币*1000W #v5440000#*3600 #v2010006#*1个#k\r\n\r\n"//3
			}
			if(cm.getPlayer().getGamePoints() >= 420 && cm.getPlayer().getGamePointsPD() == 6){
					text += "#L8##r"+完成红+"在线时间超过420分钟！"+完成+"金币2000W+点卷5000+#v2000019#80个+#v4251202#1个+#v2002031#2个\r\n\r\n\r\n"//3
				} else if(cm.getPlayer().getGamePoints() >= 420 && cm.getPlayer().getGamePointsPD() > 6){
					text += ""+完成红+"#r在线时间超过420分钟！#l"+完成+"金币2000W+点卷5000+#v2000019#80个+#v4251202#1个+#v2002031#2个\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过420分钟！#l"+正在进行中+"金币2000W+点卷5000+#v2000019#80个+#v4251202#1个+#v2002031#2个\r\n\r\n"//3
			}
            cm.sendSimple(text);
        } else if (selection == 1) {
			if(cm.haveItem(5030001, 1)){
            cm.sendOk("你已经领取过了。无法重新领取！");
            cm.dispose();
			}else if (cm.haveItem(5030000, 1)){
            cm.sendOk("你已经领取过了。无法重新领取！");
            cm.dispose();
			}else{
			cm.gainItem(5030001, 1);//
			//cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取永久雇佣商人！");
            cm.dispose();
			}
        } else if (selection == 2) {
			cm.gainItem(2020031, 1);//特殊药水50个
			cm.gainItem(2022035, 1);//枫叶60个
			cm.gainMeso(+600000);
			cm.gainDY(600)//抵用券
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了60分钟在线奖励！金币60W+抵押卷600+百事可乐60瓶+可口可乐6瓶+超级药水60个");
            cm.dispose();
        } else if (selection == 3) {
			cm.gainMeso(+1200000);
			cm.gainDY(1200)//抵用券
			cm.gainNX(1000)//
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了120分钟在线奖励！金币120W + 抵押卷1200 + 1000点券.");
            cm.dispose();
        } else if (selection == 4) {
			cm.gainItem(2049116, 20);//漂漂猪的雕像
			cm.gainDY(1800)//抵用券
			//cm.gainNX(1000)//
			cm.gainMeso(+1800000);
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了180分钟在线奖励！金币180W+抵押卷1800+混沌卷20张");
            cm.dispose();
        } else if (selection == 5) {
			cm.gainItem(2340000, 20);//漂漂猪的雕像
			cm.gainDY(2400)//抵用券
			cm.gainMeso(+2400000);
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了240分钟在线奖励！金币240W+抵押卷2400+祝福卷20张");
            cm.dispose();
        } else if (selection == 6) {
			cm.gainItem(2000004,50)//树妖邮票一张
			cm.gainItem(4001017,1)//
			cm.gainDY(4200)//抵用券500
			//cm.gainNX(5000)//
			cm.gainMeso(+15000000);
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了300分钟在线奖励！抵押卷4200+特殊药水50个+金币1500W");
            cm.dispose();
        } else if (selection == 7) {
            cm.gainItem(2010006,1)//树妖邮票一张
			cm.gainDY(3600)//抵用券500
			//cm.gainNX(5000)//
			cm.gainMeso(+10000000);
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了360分钟在线奖励！金币1000W+抵押卷3600+蜂蜜1个");
            cm.dispose();
        } else if (selection == 8) {
            cm.gainItem(4251202,1);//五彩
            cm.gainItem(2002031,2);//
            cm.gainItem(2000019,80);			
			cm.gainNX(5000);//抵用券500
			cm.gainMeso(+20000000);
			//cm.gainNX(5000)//
		//	cm.gainMeso(+3600000);
			cm.gainGamePointsPD(1);
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了420分钟在线奖励！金币2000W+点卷5000+超级药水80个+高等五彩水晶1个+草莓蛋糕2个");
            cm.dispose();
		}
    }
}


