var map;
var cost;
var location;
var mapname;
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.sendNext("嗯……再考虑一下吧。但你一定不会后悔的！");
        cm.dispose();
        return;
    }
    if (status == 0) {
        switch (cm.getMapId()) {
        case 540000000:
            // CBD
            map = 541020000;
            cost = 30000;
            mapname = "Ulu City";
            break;
        case 240000000:
            // Leafre
            map = 240030000;
            cost = 55000;
            mapname = "Ossyria Continent";
            break;
        case 220000000:
            // Ludi
            map = 220050300;
            cost = 45000;
            mapname = "Ossyria Continent";
            break;
        case 211000000:
            // El Nath
            map = 211040200;
            cost = 45000;
            mapname = "Ossyria Continent";
            break;
        case 105000000:
            map = 105030000;
            cost = 30000;
            mapname = "Victoria Continent";
            break;
        case 105030000:
            map = 105000000;
            cost = 30000;
            mapname = "Victoria Continent";
            break;
        default:
            map = 211040200;
            cost = 45000;
            mapname = "Ossyria Continent";
            break;
        }
        cm.sendNext("你好！我是随时可以去危险地区的危险地区快速出租车！现运营线路为#m" + cm.getMapId() + "#到#b#m" + map + "##k之间！价格为 #b" + cost + " 金币#k。");
    } else if (status == 1) {
        cm.sendYesNo("#b" + cost + "金币#k支付后，要移动到#b#m" + map + "##k吗？");
    } else if (status == 2) {
        if (cm.getMeso() < cost) {
            cm.sendNext("你的金币好像不够。非常抱歉，不支付金币的话，是不能使用出租车的。继续努力打猎，获取金币后再来吧。");
            cm.dispose();
        } else {
            cm.gainMeso( - cost);
            cm.warp(map, 0);
            cm.dispose();
        }
    }
}
