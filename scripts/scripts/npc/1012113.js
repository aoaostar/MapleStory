function action(mode, type, selection) {
    if (cm.getMapId() == 910010100) {
        for (var i = 4001095; i < 4001099; i++) {
            cm.给予组队物品队长双倍(i, 0, true);
        }
        cm.给予组队物品队长双倍(4001100, 0, true);
        cm.给予组队物品队长双倍(4001101, 0, true);
        cm.warp(910010200);
        cm.dispose();
    } else {
        for (var i = 4001095; i < 4001099; i++) {
            cm.给予组队物品队长双倍(i, 0, true);
        }
        for (var i = 4001100; i < 4001101; i++) {
            cm.给予组队物品队长双倍(i, 0, true);
        }
        cm.warp(100000200);
        cm.dispose();
    }
}