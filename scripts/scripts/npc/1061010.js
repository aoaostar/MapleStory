function start() {
    cm.sendYesNo("请问你是否要离开呢??");
}

function action(mode, type, selection) {
    if (mode == 1) {
        var map = cm.getMapId();
        var kill = cm.getMap().killAllMonsters(true);

        var tomap;

        if (map == 108010101) {//法师的
            kill;
            tomap = 101000003;//图书馆

        } else if (map == 108010201) {//战士的
            kill;
            tomap = 102000003;//- 金银岛 - 战士圣殿

        } else if (map == 108010301) {//弓箭手的
            kill;
            tomap = 100000201;//弓箭手培训中心

        } else if (map == 108010401) {//飞侠的
            kill;
            tomap = 103000003;//废都酒吧

        } else if (map == 108010501) {//海盗的
            kill;
            tomap = 120000101;//- 诺特勒斯号 - 航海室　

        }
        cm.warp(tomap);
    }
    cm.dispose();
}
