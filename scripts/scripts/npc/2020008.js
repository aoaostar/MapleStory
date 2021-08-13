var status = 0;
var job;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 1) {
            cm.sendOk("等您下定决心再次找我吧.");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            if (cm.getQuestStatus(6192) == 1) {
                if (cm.getParty() != null) {
                    var ddz = cm.getEventManager("ProtectTylus");
                    if (ddz == null) {
                        cm.sendOk("未知的错误");
                    } else {
                        var prop = ddz.getProperty("state");
                        if (prop == null || prop.equals("0")) {
                            ddz.startInstance(cm.getParty(), cm.getMap());
                        } else {
                            cm.sendOk("别人已经在努力保护Tylus，请稍后重试了一下.");
                        }
                    }
                } else {
                    cm.sendOk("请队长来找我谈话！");
                }
            } else if (cm.getQuestStatus(6192) == 2) {
                cm.sendOk("你保护了我。谢谢。我会教你的立场技巧.");
                if (cm.getJob() == 112) {
                    if (cm.getPlayer().getMasterLevel(1121002) <= 0) {
                        cm.teachSkill(1121002, 0, 10);
                    }
                } else if (cm.getJob() == 122) {
                    if (cm.getPlayer().getMasterLevel(1221002) <= 0) {
                        cm.teachSkill(1221002, 0, 10);
                    }
                } else if (cm.getJob() == 132) {
                    if (cm.getPlayer().getMasterLevel(1321002) <= 0) {
                        cm.teachSkill(1321002, 0, 10);
                    }
                }
            }
            if (cm.getJob() == 111 || cm.getJob() == 121 || cm.getJob() == 131 || cm.getJob() == 112 || cm.getJob() == 122 || cm.getJob() == 132 || cm.getJob() == 2111) {
                cm.sendOk("您属于战士职业群,但是您已经成功三转了,已经超越了教官的强度了!\r\n如果需要四转，请去神木村的祭司之林，找四转教官吧！");
                cm.dispose();
                return;
            }
            if (!(cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110)) {
                cm.sendOk("请找您的转职教官,您不属于战士职业群!");
                cm.dispose();
                return;
            } else if (cm.getPlayer().getLevel() < 70) {
                cm.sendOk("你的等级尚未满级");
                cm.dispose();
                return;
            }
            if (cm.haveItem(4031057, 1)) {
                cm.sendNext("恭喜你到达这里,最后我将给你一个考验!");
            } else if (!(cm.haveItem(4031057, 1))) {
                cm.warp(102000003);
                cm.sendOk("去找 #r战士转职官#k 他会帮助你的!");
                cm.dispose();
            } else if (cm.getPlayer().getRemainingSp() <= (cm.getLevel() - 70) * 3) {
                cm.sendNext("你的技能点数还没点完..");
            } else {
                cm.sendOk("你还不能转职...");
                cm.dispose();
            }
        } else if (status == 1) {
            if (cm.haveItem(4031058, 1)) {
                if (cm.getJob() == 110) {
                    cm.changeJob(111);
                    //cm.getPlayer().gainAp(5);
                    cm.gainItem(4031057, -1);
                    cm.gainItem(4031058, -1);
                    cm.sendOk("恭喜转职了!");
                    if (cm.getPlayer().getGender() == 0) {
                        cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功转职成为一名 勇士，让我们热烈的祝福他吧！！！");
                    } else {
                        cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功转职成为一名 勇士，让我们热烈的祝福她吧！！！");
                    }
                    cm.dispose();
                } else if (cm.getJob() == 120) {
                    cm.changeJob(121);
                    //cm.getPlayer().gainAp(5);
                    cm.gainItem(4031057, -1);
                    cm.gainItem(4031058, -1);
                    cm.sendOk("恭喜转职了!");
                    if (cm.getPlayer().getGender() == 0) {
                        cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功转职成为一名 骑士，让我们热烈的祝福他吧！！！");
                    } else {
                        cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功转职成为一名 骑士，让我们热烈的祝福她吧！！！");
                    }
                    cm.dispose();
                } else if (cm.getJob() == 130) {
                    cm.changeJob(131);
                    //cm.getPlayer().gainAp(5);
                    cm.gainItem(4031057, -1);
                    cm.gainItem(4031058, -1);
                    cm.sendOk("恭喜转职了!");
                    if (cm.getPlayer().getGender() == 0) {
                        cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功转职成为一名 龙骑士，让我们热烈的祝福他吧！！！");
                    } else {
                        cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功转职成为一名 龙骑士，让我们热烈的祝福她吧！！！");
                    }
                    cm.dispose();
                } else if (cm.getJob() == 2110) {
                    cm.changeJob(2111);
                    //cm.getPlayer().gainAp(5);
                    cm.gainItem(1142131, 1);//试炼中的战神勋章
                    cm.gainItem(4031057, -1);
                    cm.gainItem(4031058, -1);
                    cm.sendOk("恭喜转职了!");
                    if (cm.getPlayer().getGender() == 0) {
                        cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功进行战神三转，让我们热烈的祝福他吧！！！");
                    } else {
                        cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功进行战神三转，让我们热烈的祝福她吧！！！");
                    }
                    cm.dispose();
                }
            } else if (cm.haveItem(4031057, 1))
                cm.sendAcceptDecline("你准备承担最终测试??");
            else
                cm.sendAcceptDecline("是的，我可以让你更加强大。虽然你必须证明不仅是你的实力，还包括你的知识。你准备好挑战了吗？");
        } else if (status == 2) {
            if (cm.haveItem(4031057, 1)) {
                cm.warp(211040401);
                cm.sendOk("去找神圣的石头测验吧!!.");
                cm.dispose();
            }
        }
    }
}
