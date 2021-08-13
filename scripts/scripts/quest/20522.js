var status = -1;

function start(mode, type, selection) {
    qm.sendNext("лл�㡣");
    qm.forceStartQuest();
    qm.forceCompleteQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    qm.forceStartQuest();
    qm.forceCompleteQuest();
    qm.dispose();
}