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
            text += "#r这里是进行各种装备升级的快速通道，请问你要升级什么装备？\r\n\r\n"//3
            //text += "#L1##r制作并升级老公老婆戒指#k>>>>>>>>>#b通道1#l\r\n\r\n"//3
            //text += "#L2##r制作并升级阿尔泰耳环#k>>>>>>>>>>>#b毒物副本获得#l\r\n\r\n"//3
            text += "#L3##r升级粉红披风#k>>>>>>>>>>>#b副本获得#l\r\n\r\n"//3
			text += "#L4##r升级黄色钉鞋#k>>>>>>>>>>>#b副本获得#l\r\n\r\n"//3
			text += "#L5##r升级龙眼镜#k>>>>>>>>>>>#b副本获得#l\r\n\r\n"//3
			text += "#L6##r升级紫金枫叶#k>>>>>>>>>>>#b副本获得#l\r\n\r\n"//3
			//text += "#L7##r兑换精灵吊坠#k>>>>>>>>>>>#b通道4#l\r\n\r\n"//3			
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000017, 0);
        } else if (selection == 6) {
		cm.openNpc(9310059, 5);
        } else if (selection == 2) {
		cm.openNpc(9000017, 30);
        } else if (selection == 3) {
		cm.openNpc(9040004, 23);
        } else if (selection == 4) {
		cm.openNpc(9040004, 24);
		} else if (selection == 5) {
		cm.openNpc(9040004, 25);
        } else if (selection == 6) {
		cm.openNpc(9040004, 26);
        } else if (selection == 7) {
		cm.openNpc(9000017, 70);
        } else if (selection == 8) {
		cm.openNpc(9000017, 80);		
	}
    }
}


