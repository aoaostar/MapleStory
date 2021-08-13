var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.getPlayer().getLevel() < 40 && cm.haveItem(4032492)) {
            cm.sendYesNo("你想进去进行职业头任务?");
        } else {
            cm.sendOk("你需要小于40级，进入需要#v4032492#.");
            cm.dispose();
        }
    } else if (status == 1) {
        if (cm.getPlayerCount(677000003) == 0) {
            cm.warp(677000003, 0);
            cm.killMob(9400610);
            cm.spawnMob_map(9400610, 677000003, 186, 35);
            cm.dispose();
        } else {
            cm.sendOk("里面已经有人在进行职业头任务了，你稍后再试");
            cm.dispose();
        }
    }
}