var status = -1;

function start(mode, type, selection) {
    qm.sendNext("You want a pole arm? Hah! You don't look strong at all. Way outta your league. If you want a pole arm, prove me wrong by hunting #r#o9001012#s#k to the west of here, and find 30 #b#t4032311##k!");
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.sendNext("哈！你已经证明了自己的价值。你会得到你想要的，是否转职？");
    } else if (status == 1) {
        if (qm.getPlayerStat("RSP") > (qm.getPlayerStat("LVL") - 30) * 3) {
            qm.sendNext("你有太多未用完的 #bSP#k. 我强烈建议你在你的一转和二转技能上使用更多的SP。.");
            qm.dispose();
            return;
        }
        qm.sendNextS("我的回忆又回来了…", 2);
        qm.changeJob(2110);
        qm.gainItem(1142130, 1);
        qm.gainItem(4032311, -30);
		qm.teachSkill(21100000,0,20);//给与精准矛
        qm.forceCompleteQuest(21201);
        qm.forceCompleteQuest();
    } else if (status == 2) {
        qm.sendOk("哈哈!你已经得到你想要的了，二转成功。现在离开！");
        qm.dispose();
    }
}