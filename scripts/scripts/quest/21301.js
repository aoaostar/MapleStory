var status = -1;

function start(mode, type, selection) {
    qm.sendNext("≤‚ ‘start");
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    qm.forceStartQuest();
    qm.dispose();

}