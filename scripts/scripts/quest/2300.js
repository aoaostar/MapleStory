/* ===========================================================
 Resonance
 NPC Name: 		Maple Administrator
 Description: 	Quest -  Kingdom of Mushroom in Danger
 =============================================================
 Version 1.0 - Script Done.(17/7/2010)
 =============================================================
 */

var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            if (status == 0) {
                qm.sendOk("真的？这是件急事，所以如果你有时间，请来看我。");
                qm.dispose();
                return;
            } else if (status == 3) {
                qm.sendNext("可以，如果是那样的话, 我只给你通往蘑菇王国的路线。. #b射手村的西入口附近，#k你会发现一个 #b空荡荡的房子#k. 进入房子，然后左转进入#b<主题地牢：蘑菇城堡>#k. 那是蘑菇王国的入口，时间不多了");
                qm.forceStartQuest();
                return;
            }
        }
    }
    if (status == 0)
        qm.sendAcceptDecline("既然你已经取得了职业上的进步，你看起来已经准备好了。我有些事想请你帮忙。你愿意听吗？");
    if (status == 1)
        qm.sendNext("发生的事是 #b蘑菇王国#k 王国目前处于混乱状态, 蘑菇王国在射手村附近, 聪明的国王. 最近, 他开始感到不舒服, 所以他决定任命他唯一的女儿 #b公主 薇奥莱塔#k. 从那时起，王国就处于当前状态，一定发生了什么事情。.");
    if (status == 2)
        qm.sendNext("我不知道具体的细节, 但是很明显发生了可怕的事情, 所以我认为如果你亲自查看会更好. 像你这样的探险家似乎更有能力拯救蘑菇王国. 我刚才给你写了一份 #b推荐信#k, 所以我建议你马上到蘑菇王国，找#b警卫队长#k.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v4032375# #t4032375#");
    if (status == 3)
        qm.sendYesNo("顺便说一句, 你知道蘑菇王国在哪里吗？? 如果你能找到路就好了, 但是如果你不介意的话, 我可以直接带你到门口.");
    if (status == 4) {
        qm.gainItem(4032375, 1);
        qm.warp(106020000);
        qm.forceStartQuest();
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
        qm.sendNext("恩？那是一个#b职业指导教师的推荐信#k??! 这是什么，你是来拯救我们蘑菇王国的那个人吗？,");
    if (status == 1)
        qm.sendNextPrev("恩...可以, 因为这份信是职业指导老师写的. 我很抱歉没有早点向你作自我介绍. 我是 #b警卫队长任#k 负责保护王国. 你可以看到，这个临时的藏身之处是由卫兵和士兵的队伍保障。我们的处境可能是可怕的，但是，欢迎来到蘑菇王国。");
    if (status == 2) {
        qm.gainItem(4032375, -1);
        qm.forceCompleteQuest();
        qm.forceStartQuest(2312);
        qm.dispose();
    }
}
