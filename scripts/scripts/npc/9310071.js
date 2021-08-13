
var status = 0;


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.sendOk("#b好的,下次再见.");
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.sendOk("#b好的,下次再见.");
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (status == 0) {
			 cm.sendGetNumber("请输入查询物品的ID：\r\n", 1, 1, 9999999);
        } else if (status == 1) {
			cm.sendNext(cm.checkDropper(selection));
        }
    }
}