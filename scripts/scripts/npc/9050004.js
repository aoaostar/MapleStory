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
            cm.sendSimple("#r请选择你制作的血衣\r\n<血衣分别为以下几种类型>\r\n\r\n#d#L0#3000HP血衣#l\r\n\r\n#L1##b5000HP血衣#n#l\r\n\r\n#b#L2##r8000HP血衣\r\n\r\n");
        } else if (status == 1) {
            if (selection == 0) {//副本传送
             cm.openNpc(1002006,100);
            } else if (selection == 1) {//副本兑换奖励
              cm.openNpc(1002006,110);
            }else if(selection == 2){
                cm.openNpc(1002006,120);
        }
        }
    }
}


