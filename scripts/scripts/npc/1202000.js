var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (cm.getPlayer().getMapId() != 140090000) {
        if (status == 0) {
            cm.sendSimple("下面列出的信息，都是教你在10级以下如何进行游戏的操作方法 \n\r 那么, 你想从中学到什么？?  \n\r #b#L1#小地图是？#l \n\r #b#L2#任务是？#l \n\r #b#L3#现有道具是？#l \n\r #b#L4#如何攻击？#l \n\r #b#L5#如何捡取道具？#l \n\r #b#L6#如何佩戴装备？#l \n\r #b#L7#如何提高技能点数？#l \n\r #b#L8#如何使用快捷键？#l \n\r #b#L9#如何打碎箱子？#l \n\r #b#L10#如何坐凳子？#l \n\r #b#L11#如何查看大地图？#l");
        } else {
            cm.summonMsg(selection);
            cm.dispose();
        }
    } else {
        if (cm.getInfoQuest(21019).equals("")) {
            if (status == 0) {
                cm.sendNext("你…终于醒了！");
            } else if (status == 1) {
                cm.sendNextPrevS("...你是谁?", 2);
            } else if (status == 2) {
                cm.sendNextPrev("我一直在等你. 等待着与黑魔法师战斗的英雄最终苏醒过来...!");
            } else if (status == 3) {
                cm.sendNextPrevS("等等，你在说什么？你是谁？...?", 2);
            } else if (status == 4) {
                cm.sendNextPrevS("...我是谁？我不记得过去的事了。啊...我的头好痛！", 2);
            } else if (status == 5) {
                cm.updateInfoQuest(21019, "helper=clear");
                cm.showWZEffect("Effect/Direction1.img/aranTutorial/face", -1);
                cm.showWZEffect("Effect/Direction1.img/aranTutorial/ClickLirin", -1);
                //cm.playerSummonHint(true);
                cm.dispose();
            }
        } else {
            if (status == 0) {
                cm.sendNext("你好些了吗?");
            } else if (status == 1) {
                cm.sendNextPrevS("我. ..真的一点都不记得了…我在哪里？你是谁？", 2);
            } else if (status == 2) {
                cm.sendNextPrev("镇静一点。黑魔法师的诅咒让你失去了记忆……不过你用不着担心。你想知道的事情，我都会一一告诉你.");
            } else if (status == 3) {
                cm.sendNextPrev("你是我们的英雄。数百年前，你勇敢地和黑魔法师战斗，并拯救了冒险岛世界。不过，在最后时刻你中了黑魔法师的诅咒，被封冻在冰块里沉睡了好久好久。所以，记忆也渐渐消失了。.");
            } else if (status == 4) {
                cm.sendNextPrev("这个地方叫做里恩岛。黑魔法师把你封冻在了这里。在黑魔法师的诅咒下，不论四季变化，这里永远都是冰封雪飘。我们是在冰窟的最深处发现你的。");
            } else if (status == 5) {
                cm.sendNextPrev("我叫利琳，属于里恩一族。里恩一族从很久以前就遵照预言在这里等待着英雄的归来。然后……我们终于发现了你。就在这个地方……");
            } else if (status == 6) {
                cm.sendNextPrev("我是不是一次说了太多？理解起来有些困难？没关系，慢慢你就会明白……#b咱们赶紧回村子里吧#k。回村子的路上，我再慢慢给你解释。");
            } else if (status == 7) {
                cm.playerSummonHint(true);
                cm.warp(140090100, 1);
                cm.dispose();
            }
        }
    }
}