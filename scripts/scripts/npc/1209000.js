var status = -1;

function action(mode, type, selection) {
    if (cm.getQuestStatus(21002) == 0) {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            cm.sendNext("哦，战神，你醒了！伤势如何？…什么？你想知道现在正在发生什么事吗？");
        } else if (status == 1) {
            cm.sendNextPrev("我们都准备好离开这个地方了。我们船上的每个人都在船上，神圣的鸟在飞行中为我们的方舟提供保护，所以你不用担心。一旦我们完成了所有的事情，我们将立即逃往金银岛。");
        } else if (status == 2) {
            cm.sendNextPrev("战神的同伴们…？...他们去和黑魔法师战斗了。他们决定趁我们逃跑的时候阻止黑魔法师。什么？你想加入他们的战斗吗？不，不要去！你受伤了！你应该马上上方舟！");
        } else if (status == 3) {
            cm.forceStartQuest(21002, "1");
            // Ahh, Oh No. The kid is missing
            cm.showWZEffect("Effect/Direction1.img/aranTutorial/Trio", 1);
            cm.dispose();
        }
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            cm.sendSimple("我们正处于紧急状态。您想了解些什么？ \r #b#L0#黑魔法师在哪?#l \r #b#L1#逃难的准备情况如何？?#l \r #b#L2#我的同伴们在哪?#l");
        } else if (status == 1) {
            switch (selection) {
                case 0:
                    cm.sendOk("我听说黑魔法师接近我们现在所在的地方。我们甚至不能通过森林，因为黑魔法师控制的龙群阻挡。这就是为什么我们把方舟作为我们的逃生路线。我们离开这个地方的唯一办法是飞往金银岛。");
                    break;
                case 1:
                    cm.sendOk("逃难几乎都做好了。可以搭载的人全部都上方舟了。现在只剩下几个人搭乘后就可以出发前往明珠港。神兽答应在逃生船飞行的其间阻挡空中的攻击...没有人会留下来守护这里...");
                    break;
                case 2:
                    cm.sendOk("你的同伴... 他们已经去找黑魔法师了。 他们说要带我们去逃难的期间阻止黑魔法师...还说因为您受伤了，所以不带您去。等孩子都救出来后，您也跟我们一起逃走吧！ 战神！");
                    break;
            }
            cm.safeDispose();
        }
    }
}