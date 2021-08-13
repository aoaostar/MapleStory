var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.getPlayer().getLevel() < 40 && cm.haveItem(4032491)) {
            cm.sendYesNo("你想进去进行职业头任务?");
        } else {
            cm.sendOk("你需要小于40级，进入需要印第安老斑鸠勋章.");
            cm.dispose();
        }
    } else if (status == 1) {
        if (cm.getPlayerCount(677000005) == 0) {
            cm.warp(677000005, 0);
            cm.killMob(9400609);
            cm.spawnMob_map(9400609, 677000005, 79, 85);
            cm.dispose();
        } else {
            cm.sendOk("里面已经有人在进行职业头任务了，你稍后再试");
            cm.dispose();
        }
    }
}