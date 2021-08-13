var status = -1;


function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    switch(cm.getPlayer().getMapId()) {
	case 930000000:
	    cm.sendNext("欢迎，请通过光圈进入！.");
	    break;
	case 930000010:
	    cm.warpParty(100000000);
	    break;
	case 930000100:
	    cm.sendNext("我们必须消灭怪物");
	    break;
	case 930000200:
	    cm.sendNext("我们必须消灭怪物");
	    break;
	case 930000300:
		cm.给组队经验(150000);
	    cm.warpParty(930000400);
	    break;
	case 930000400:
	    if (cm.haveItem(4001169,50)) {
		cm.给组队经验(150000);
		cm.warpParty(930000500);
		cm.gainItem(4001169,-50);
	    } else {
		cm.sendOk("我们必须消灭怪物，获得50个怪物之珠");
	    }
	    break;
	case 930000600:
	    cm.sendNext("This is it! Place the Magic Stone on the Altar!");
	    break;
	case 930000700:
	    cm.removeAll(4001169);//怪物之珠
	    cm.removeAll(2270004);//净化之珠
	    cm.removeAll(4001162);
	    cm.removeAll(4001163);
		cm.给组队经验(300000);
	//	cm.给组队物品(4031456, 1);//给枫叶珠1个
		cm.给组队物品(2614015, 2);//
		cm.给组队物品(4170001, 1);//
	    cm.warpParty(930000800,0);
	    break;
    }
    cm.dispose();
}