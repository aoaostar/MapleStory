var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        var mapId = cm.getMapId();
        if (cm.isLeader()) {//是队长
            if (mapId == 103000890) {
                cm.warpParty(103000000, "mid00");
                //cm.removeAll(4001007);
                //cm.removeAll(4001008);
                cm.dispose();
            } else {
                var outText;
                if (mapId == 103000805) {
                    outText = "你确定要离开地图？？";
                } else {
                    outText = "一旦你离开地图，就必须重新开始任务了。你确定要离开？";
                }
                if (status == 0) {
                    cm.sendYesNo(outText);
                } else if (mode == 1) {
                    cm.warpParty(103000890, "st00"); // Warp player
                    cm.dispose();
                }
            }
        } else {
            cm.sendOk("请让你的队长来跟我说话，队员无法决定结束副本！");
			cm.warp(103000000);
            cm.dispose();
        }
    }
}