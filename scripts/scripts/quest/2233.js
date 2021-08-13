var status = -1;

function start(mode, type, selection) {
    if (qm.getPlayer().getCurrentRep() > 0) {
        qm.forceCompleteQuest();
        qm.gainExp(3000);
        qm.sendNext("Good job!");
    } else {
        qm.sendNext("Please, get some Rep!");
    }
    qm.dispose();
}
function end(mode, type, selection) {
    if (qm.getPlayer().getCurrentRep() > 0) {
        qm.forceCompleteQuest();
        qm.gainExp(3000);
        qm.sendNext("Good job!");
    } else {
        qm.sendNext("Please, get some Rep!");
    }
    qm.dispose();
}
