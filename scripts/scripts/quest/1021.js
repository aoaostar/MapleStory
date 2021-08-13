/*
 * MinaMS服务端
 * 罗杰的苹果任务
 * 任务ID：1021
 * NPC ID:2000
 */
/* global qm */
importPackage(Packages.tools);
importPackage(Packages.client);
var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            qm.sendNext("欢迎来到#r" + qm.ms() + "#k\r\n我是#b罗杰#k，我可以教你许多东西，你想不想知道？");
        } else if (status == 1) {
            qm.sendNextPrev("看来你很有冒险的用心，哈哈~在受到怪物袭击时，HP会下降.\r\n我可以教你如何使用消耗类型的物品。");
        } else if (status == 2) {
            qm.sendAcceptDecline("准备好了吗？哈哈~我现在就做示范给你看。");
        } else if (status == 3) {
            if (qm.getPlayer().getHp() >= 50) {
                qm.getPlayer().stat.setHp(25);
                qm.getPlayer().updateSingleStat(MapleStat.HP, 25);
            }
            if (!qm.haveItem(2010007)) {
                qm.gainItem(2010007, 1);
            }
            qm.forceStartQuest();
            qm.sendNext("怎么样？是不是发现HP发生变化了?如果HP到了0,你就会死亡,现在我给你一个 #r苹果#k ,吃了它,你就可以补充HP,其他类型的道具也是这样使用,按 #bI#k 键就可以打开窗口,找到苹果之后就双击它,就可以使用啦,是不是很简单呢?快点去试试吧!");
        } else if (status == 4) {
            qm.sendPrev("按 #bI#k 键，打开你的背包。在消耗栏内，你就可以看见#b苹果#k，吃掉苹果。你就可以恢复#b100%的HP#k。");
        } else if (status == 5) {
            qm.showWZEffect("UI/tutorial.img/28", 1);
            qm.dispose();
        }
    }
}


function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            if (qm.getPlayerStat("HP") < 50) {
                qm.sendNext("嗨，你的HP还没有完全恢复，使用我给你的苹果来补充吧！快去试试!");
                qm.dispose();
            } else {
                qm.sendNext("很好~你已经学会了如何使用消耗品来补充HP。");
            }
        } else if (status == 1) {
            qm.sendNextPrev("做为奖励，我送你一份礼物,这个是旅行的必需品,在紧急情况下可以帮助你。");
        } else if (status == 2) {
            qm.sendNextPrev("好了，这里是我所有可以教你的，虽然这只是基本的知识，但我也只能教你这些。祝你好运~！\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v2010000# 3 #t2010000#\r\n#v2010009# 3 #t2010009#\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 10 exp");
        } else if (status == 3) {
            qm.gainExp(10);
            qm.gainItem(2010000, 3);
            qm.gainItem(2010009, 3);
            qm.forceCompleteQuest();
            qm.dispose();
        }
    }
}