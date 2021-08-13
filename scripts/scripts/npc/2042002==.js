/* 
 * Spiegelmann - Monster Carnival
 */

var status = -1;
var rank = "D";
var exp = 0;

function start() {
    if (cm.getCarnivalParty() != null) {
        status = 99;
    }
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("你想要做什么？如果你从未参加过怪物嘉年华, 在进入前你需要了解一两件事.\r\n#b#L0# 进入怪物嘉年华战场#l");
    } else if (status == 1) {
        switch (selection) {
            case 0:
            {
                var level = cm.getPlayerStat("LVL");
                if (level < 10) {
                    cm.sendOk("对不起，只有30级以上的用户可以参加怪物嘉年华.");
                } else {
                    cm.warp(980000000, "st00");
                }
                cm.dispose();
            }
            default:
                {
                    cm.dispose();
                    break;
                }
                break;
        }
    } else if (status == 100) {
        var carnivalparty = cm.getCarnivalParty();
        if (carnivalparty.getTotalCP() >= 501) {
            rank = "A";
            exp = 7500;
        } else if (carnivalparty.getTotalCP() >= 251) {
            rank = "B";
            exp = 6000;
        } else if (carnivalparty.getTotalCP() >= 101) {
            rank = "C";
            exp = 3000;
        } else if (carnivalparty.getTotalCP() >= 0) {
            rank = "D";
            exp = 1000;
        }
        cm.getPlayer().endPartyQuest(1301);
        if (carnivalparty.isWinner()) {
            cm.sendOk("你赢了这场战役, 表现的很出色. 胜利属于你的. \r\n#b怪物嘉年华名次 : " + rank);
        } else {
            cm.sendOk("不幸的事，你输掉了这场战役, 尽管你表现的很出色. 下次的胜利一定属于你的. \r\n#b怪物嘉年华名次 : " + rank);
        }
    } else if (status == 101) {
        var carnivalparty = cm.getCarnivalParty();
        var los = parseInt(cm.getPlayer().getOneInfo(1301, "lose"));
        var vic = parseInt(cm.getPlayer().getOneInfo(1301, "vic"));
        if (carnivalparty.isWinner()) {
            vic++;
            cm.getPlayer().updateOneInfo(1301, "vic", "" + vic);
            carnivalparty.removeMember(cm.getChar());
            cm.gainExpR(exp);
        } else {
            los++;
            cm.getPlayer().updateOneInfo(1301, "lose", "" + los);
            carnivalparty.removeMember(cm.getChar());
            cm.gainExpR(exp / 2);

        }
        cm.getPlayer().updateOneInfo(1301, "VR", "" + (java.lang.Math.ceil((vic * 100) / los)));
        cm.warp(980000000);
        cm.dispose();
    }

}