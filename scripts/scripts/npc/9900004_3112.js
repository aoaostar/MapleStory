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
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "这里是邮票兑换卷轴的快速通道，请问你要兑换什么卷轴？\r\n\r\n"//3
            text += "#L1#兑换#v2043001#60%卷轴\t        需要：#v4002000#X10个\r\n\r\n"//3
            text += "#L2#兑换#v2043002#10%卷轴\t        需要：#v4002000#X5个\r\n\r\n"//3
            text += "#L3#兑换#v2340000#祝福卷轴\t    需要：#v4002000#X20个\r\n\r\n"//3		
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9900004, 31121);
        } else if (selection == 2) {
		cm.openNpc(9900004, 31122);
        } else if (selection == 3) {
		cm.openNpc(9900004, 31123);		
	}
    }
}


