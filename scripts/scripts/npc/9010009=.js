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
            text += "#e#b你好！这里是跑环系统中心:.\r\n#e#r总共10环，10环总共奖励为：6000万金币，1个高等黑暗水晶 请大家多多加油哦.\r\n"//3
			text += "#L2#点我了解#b跑商攻略#k.\r\n\r\n"//3
		    text += "#L1#第一环 #e#d需要#v4000016#100个 \r\n\r\n"//3
	       // text += "#L3#第二环 #e#d需要#v4000009#100个 \r\n\r\n"//3
			//text += "#L4#第三环 #e#d需要#v4000020#100个 \r\n\r\n"//3
			//text += "#L5#第四环 #e#d需要#v4000015#100个 \r\n\r\n"//3
			//text += "#L6#第五环 #e#d需要#v4000004#100个 \r\n\r\n"//3
			//text += "#L7#第六环 #e#d需要#v4000188#100个 \r\n\r\n"//3
			//text += "#L8#第七环 #e#d需要#v4000008#100个 \r\n\r\n"//3
			//text += "#L9#第八环 #e#d需要#v4003004#100个 \r\n\r\n"//3
			//text += "#L10#第九环 #e#d需要#v4000007#100个 \r\n\r\n"//3
			//text += "#L11#第十环 #e#d需要#v4000007#200个 \r\n\r\n"//3
            //text += "#L2##e#d#v4031017# 副本蛋兑换固定奖励.#l\r\n\r\n"//3
            //text += "#L3##e#d#v04032226# 每日活动奖励兑换（1小时/1次.胜利即可）#l\r\n"//3
            //text += "#L4##e#d#v1382057#永恒冰轮杖制作#l\r\n"//3
            //text += "#L5##e#d#v1402046#永恒玄冥剑制作#l\r\n"//3
            //text += "#L6##e#d#v1432047#永恒显圣枪制作#l\r\n"//3
            //text += "#L7##e#d#v1442063#永恒神光戟制作#l\r\n"//3
            //text += "#L8##e#d#v1452057#永恒惊电弓制作#l\r\n"//3
            //text += "#L9##e#d#v1462050#永恒冥雷弩制作#l\r\n"//3
            //text += "#L10##e#d#v1472068#永恒大悲赋制作#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9010009, 1);
        } else if (selection == 2) {
		cm.openNpc(9010009, 2);
        } else if (selection == 3) {
		cm.openNpc(9010009, 5);
        } else if (selection == 4) {
		cm.openNpc(9010009, 6);
        } else if (selection == 5) {
		cm.openNpc(9010009, 7);
        } else if (selection == 6) {
		cm.openNpc(9010009, 8);
        } else if (selection == 7) {
		cm.openNpc(9010009, 9);
        } else if (selection == 8) {
		cm.openNpc(9010009, 10);
        } else if (selection == 9) {
		cm.openNpc(9010009, 11);
        } else if (selection == 10) {
		cm.openNpc(9010009, 12);
		} else if (selection == 11) {
		cm.openNpc(9010009, 13);
	}
    }
}


