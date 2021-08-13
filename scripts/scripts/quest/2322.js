/* ===========================================================
 Resonance
 NPC Name: 		Minister of Home Affairs
 Map(s): 		Mushroom Castle: Corner of Mushroom Forest(106020000)
 Description: 	Quest -  Over the Castle Wall (2)
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
            qm.sendNext("真的？还有别的办法可以穿过城堡吗？如果你不知道办法，那就来找我吧。.");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendYesNo("就像我告诉你的，打破障碍不能成为庆祝的理由。 那是因为我们的蘑菇王国城堡完全拒绝任何人进入我们的王国，所以你很难做到这一点。恩...想办法进去，你能……先调查一下城堡的外墙吗？");
    if (status == 1)
        qm.sendNext("走过蘑菇林，当你到达#r选择分裂的道路#k.朝城堡走去。祝你好运.");
    if (status == 2) {
        //qm.forceStartQuest();
        //qm.forceStartQuest(2322, "1");
        qm.gainExp(11000);
        qm.sendOk("在这个地区航行很好.");
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
        qm.sendOk("嗯，我看到…所以他们完全关闭了入口和一切。.");
    if (status == 1) {
        qm.gainExp(11000);
        qm.sendOk("在这个地区航行很好.");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	