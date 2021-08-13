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
            text += "这里是进行各种副本兑换造的快速通道，请问你要兑换什么？\r\n\r\n"//3
            //text += "#L1##r制作并升级老公老婆戒指#k>>>>>>>>>#b通道1#l\r\n\r\n"//3
            text += "#L2##r#v4031456#兑换60%卷轴\r\n\r\n"//3
            text += "#L3##r#v4031456#兑换10%卷轴\r\n\r\n"//3		
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000017, 0);
        } else if (selection == 2) {
		cm.openNpc(9900004, 31111);
        } else if (selection == 3) {
		cm.openNpc(9900004, 31112);		
	}
    }
}


