/*
 * 
 * @wnms
 * @大擂台传送副本npc
 */
function start() {
    status = -1;
    action(1, 0, 0);
}
var 冒险币 = 5000;
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
            cm.sendSimple("#r我是小游戏系统负责人\r\n<以下几种类型游戏>\r\n\r\n#d#L0#21点#l\r\n\r\n#L1#开锁#n#l\r\n\r\n#L2#五子棋+记忆大考验合成#n#l\r\n\r\n");
        } else if (status == 1) {
            if (selection == 0) {//副本传送
             cm.openNpc(9310100,100);
            } else if (selection == 1) {//副本兑换奖励
              cm.openNpc(9310100,110);
            }else if(selection == 2){
                cm.openNpc(9310100,120);
        }
        }
    }
}


