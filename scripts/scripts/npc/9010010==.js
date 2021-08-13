function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            text += "#e#r欢迎来到亲子鉴定中心，你到底是欧洲人还是非洲人呢？#k#n\r\n"
            
            text += "\t\t\t#e#b   你的积分剩余： " + cm.getPlayer().getjf() + "点！\r\n"
            
			text += "#L3##r#v4310158#兑换普通抽奖币x1个\t  需要积分：10\r\n\r\n"//
			
            text += "#L4##r#v4310158#兑换普通抽奖币x10个\t  需要积分：100#k\r\n\r\n"//
			
			text += "-------------------抽奖区---------------------\r\n\r\n"//
            
            text += "#L1##v4310158#陈玄风普通抽奖#l\t#L2##v4310027#梅超风超级抽奖#l\r\n\r\n"
            
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 121);

        } else if (selection == 2) {
            cm.openNpc(9900004, 122);

        } else if (selection == 3) {
			if(cm.getPlayer().getjf() >= 10){
				cm.getPlayer().gainjf(-10);
				cm.gainItem(4310158,1);
				cm.sendOk("换购成功，获得#v4310158#x1个！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『积分兑换普通抽奖币』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功用10积分兑换【普通抽奖币x1个】,祝你一发入魂！"));
					cm.dispose();
					}else{
					cm.sendOk("积分不足，请充值获得积分！");
					cm.dispose();
				}
        } else if (selection == 4) {
			if(cm.getPlayer().getjf() >= 100){
				cm.getPlayer().gainjf(-100);
				cm.gainItem(4310158,10);
				cm.sendOk("换购成功，获得#v4310158#x10个！");
			Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(12, cm.getClient().getChannel(), "『积分兑换普通抽奖币』" + " : " + "恭喜[" + cm.getPlayer().getName() + "]成功用100积分兑换【普通抽奖币x10个】,祝你一发入魂！"));
					cm.dispose();
					}else{
					cm.sendOk("积分不足，请充值获得积分！");
					cm.dispose();
				}
		}
    }
}