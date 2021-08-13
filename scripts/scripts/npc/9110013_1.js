var status;


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        cm.dispose();
        return;
    }
	if(!cm.getPlayer().isGM()){
		cm.dispose();
		return;
	}
    if (status == 0) {
        var text="请选择您要兑换的装备:\r\n";
		text+="#b#L1##v4000487#x10 + #v4000017#x10兑换 #v1002419#(四维+5)#l\r\n";
		text+="#b#L2##v4000487#x10 + #v1002419#x1 + #v4000017#x20 兑换 #v1002238#(四维+10,攻魔+10)#l\r\n";
		text+="#b#L3##v4000487#x30 + #v1002419#x1 + #v1002238#x1 + #v4000017#x30 兑换 #v1002424#(四维+30,攻魔+30)#l\r\n";

	      cm.sendSimple(text);
	}
	else if(status==1){
		if(selection==1){
			if(cm.haveItem(4000487,10)&& cm.haveItem(4000017,10)){
			cm.gainItem(4000487,-10);
			cm.gainItem(4000017,-10);
			cm.gainItem(1002419,5,5,5,5,0,0,5,5,0,0,0,0,0,0,0);
			cm.sendOk("恭喜：属性装备兑换成功");
			cm.喇叭(3, "玩家：[" + cm.getName() + "]属性装备兑换成功！");
			cm.dispose();
			}else{
				cm.sendOk("兑换材料不足，继续努力吧！");
			cm.dispose();
			}
		}
		else if(selection==2){
			if(cm.haveItem(4000487,10)&& cm.haveItem(1002419,1)&& cm.haveItem(4000017,20)){
			cm.gainItem(4000487,-10);
			cm.gainItem(1002419,-1);
			cm.gainItem(4000017,-20);
			cm.gainItem(4005004,-10);
			cm.gainItem(1002238,10,10,10,10,0,0,10,10,0,0,0,0,0,0,0);
			cm.sendOk("恭喜：属性帽子升级成功");
			cm.喇叭(3, "玩家：[" + cm.getName() + "]属性帽子升级成功！");
			cm.dispose();
			}else{
				cm.sendOk("兑换材料不足，继续努力吧！");
			cm.dispose();
			}
		}
		else if(selection==3){
			if(cm.haveItem(4000487,30)&& cm.haveItem(1002419,1) && cm.haveItem(1002238,1)&& cm.haveItem(4000017,30)){
			cm.gainItem(4000487,-30);
			cm.gainItem(1002419,-1);
			cm.gainItem(1002238,-1);
			cm.gainItem(4000017,-30);
			cm.gainItem(1002424,30,30,30,30,0,0,30,30,0,0,0,0,0,0,0);
			cm.sendOk("恭喜：时装升级成功");
			cm.喇叭(3, "玩家：[" + cm.getName() + "]时装升级成功！");
			cm.dispose();
			}else{
				cm.sendOk("兑换材料不足，继续努力吧！");
			cm.dispose();
			}
		}

	}

	
	else if(status==2){
		
	}
}