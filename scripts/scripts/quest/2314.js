/* ===========================================================
 Resonance
 NPC Name: 		Minister of Home Affairs
 Map(s): 		Mushroom Castle: Corner of Mushroom Forest(106020000)
 Description: 	Quest -  Exploring Mushroom Forest(1)
 =============================================================
 Version 1.0 - Script Done.(18/7/2010)
 =============================================================
 */

importPackage(Packages.client);

var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.sendNext("请不要对我们的蘑菇王国失去信心.");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendYesNo("为了救公主，你必须先去蘑菇林. 国王设置一道强大的屏障，禁止任何人进入城堡。. 请为我们调查此事.");
    if (status == 1)
        qm.sendNext("你会跑到蘑菇森林的屏障，从你现在所在的地方向东走去。请小心. 我听说这个地区到处都是疯狂的、恐惧的怪物。.");
    if (status == 2) {
        //qm.forceStartQuest();
        //qm.forceStartQuest(2314,"1");
        qm.gainExp(8300);
        qm.sendOk("我明白了，所以它确实不是任何一种常规的屏障。伟大的工作. 如果没有你的帮助，我们就不会知道那是怎么回事了.");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}

function end(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendOk("我看,你已经彻底调查了蘑菇林的屏障。它是什么样子的？");
    if (status == 1) {
        qm.gainExp(8300);
        qm.sendOk("我明白了，所以它确实不是任何一种常规的屏障。那里的伟大工作。如果没有你的帮助，我们就不会知道那是怎么回事了。");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	