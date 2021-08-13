var 聊天 = "#fUI/StatusBar/BtChat/normal/0#";
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
			text += "#e#r下面我将为你介绍跑商送货的规则：#k!#n\r\n\r\n"
            text += "#r1、跑商活动每天进行，每完成一轮方可进行下一轮的跑商。\r\n"//3
			text += "#r2、跑商活动每一轮的奖励都不同，需求的材料也不同，坚持到底才能获得丰富奖励哦！\r\n"//3、
			text += "#r3、跑商活动会在每天清零，请各位知晓。\r\n"//3
			text += "#r4、跑商活动的奖品也在不断调整中，大家快去刷怪吧！\r\n"//3

            cm.sendOk(text);
		    cm.dispose();
		}
    }
}


