var status = -1;

function start(mode, type, selection) {
	qm.forceStartQuest(21203, "0");
	qm.forceStartQuest(21202);
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.forceStartQuest(21203, "0");
	qm.forceStartQuest(21202);
	qm.forceCompleteQuest();
	qm.dispose();
}