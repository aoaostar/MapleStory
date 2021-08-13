/* Spiruna
	Orbis : Old Man's House (200050001)
	
	Refining NPC: 
	* Dark Crystal - Half Price compared to Vogen, but must complete quest 
*/

var status = 0;
var selectedItem = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
    }
    if (status == 0 && mode == 1) {
        if (cm.getQuestStatus(3034) == 2) {
            var selStr = "你帮了我这么多的忙，如果你有很多黑色水晶母矿，你给我500金币我就帮你进行合成."
            cm.sendYesNo(selStr);
        } else {
            cm.sendOk("滚开！我在冥想呢~");
            cm.dispose();
        }
    } else if (status == 1 && mode == 1) {
        cm.sendGetNumber("好，你想让我做多少？", 1, 1, 100);
    } else if (status == 2 && mode == 1) {
        var complete = false;
        var itemID = 4005004;
        var matID = 4004004;
        var matQty = 10;
        var cost = 500;
        if (cm.getMeso() < cost * selection) {
            cm.sendOk("滚开！你个穷鬼！")
            cm.dispose();
            return;
        } else {
            complete = cm.haveItem(matID, matQty * selection);
        }

        if (!complete) {
            cm.sendOk("我需要那块矿石来提炼晶体。没有例外的。");
        } else {
            cm.gainItem(matID, -matQty * selection);
            cm.gainMeso( - cost * selection);
            cm.gainItem(itemID, selection);
            cm.sendOk("拿去享受吧");
        }
        cm.dispose();
    }
}
