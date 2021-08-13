var status = 0;

var ttt = "#fUI/UIWindow.img/Quest/icon9/0#";
var xxx = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;


        if (status == 0) {

            var textz = "\r\n 蝙蝠魔就在那里,你是要挑战它呢还是逃跑呢?\r\n";

            // textz += "#b#L0#1个纪念币.挑战蝙蝠魔#l\r\n";

            textz += "#b#L1#逃跑#l\r\n";

            cm.sendSimple(textz);


        } else if (status == 1) {

            if (selection == 0) {
                var party = cm.getPlayer().getParty();
                if (party == null || party.getLeader().getId() != cm.getPlayer().getId()) {
                    cm.sendOk("你不是队长。请你们队长来说话吧！");
                    cm.dispose();
                } else if (party.getMembers().size() < 0) {
                    cm.sendOk("需要 2 人以上的组队才能召唤！!");
                    cm.dispose();
                } else if (cm.haveItem(4000463) < 0) {
                    cm.sendOk("挑战巨大蝙蝠魔必须要  #v4000463# \r\n#k");
                    cm.dispose();
                } else {
                    cm.gainItem(4000463, -1);
                    //怪物ID--血 --气--经验--怪物数量--X--Y
                    cm.spawnMonster(8830007, 416, 258);
                    cm.spawnMonster(8830008, 416, 258);
                    cm.spawnMonster(8830009, 416, 258);
                    cm.dispose();
                }

            }
            //  else if (selection == 1) {
            //     cm.warpParty(105100100);
            //     cm.gainItem(1472244, 1);
            //     cm.dispose();

            // }
        }
    }
}