var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.getPlayer().getLevel() < 40 && cm.haveItem(4032495)) {
            cm.sendYesNo("你想进去进行职业头任务?");
        } else {
            cm.sendOk("你需要小于40级，进入需要#v4032495#.");
            cm.dispose();
        }
    } else if (status == 1) {
        if (cm.getPlayerCount(677000001) == 0) {
            cm.warp(677000001, 0);
            cm.killMob(9400612);
            cm.spawnMob_map(9400612, 677000001, 330, 60);
            cm.dispose();
        } else {
            cm.sendOk("里面已经有人在进行职业头任务了，你稍后再试");
            cm.dispose();
        }
    }
}