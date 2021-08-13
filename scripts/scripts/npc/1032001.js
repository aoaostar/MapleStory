/**
 * @作者：MinaMS
 * @重构完善版本
 * @用途 魔法师转职 1~3转 触发NPC 1032001.js 地图 101000003 - 金银岛 - 魔法密林图书馆
 */

var status = 0;
var job;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 2) {
        cm.sendOk("下定决心再来找我。");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
//开始写代码
        if (cm.getJob() == 0) {//如果是新手
            if (cm.getPlayer().getLevel() >= 8) {//如果角色等级大于等于10
                cm.sendNext("你想成为一名 #r魔法师#k 吗？\r\n魔法师可以使用华丽效果的属性魔法,并可以在组队打猎中使用非常有用的辅助魔法.2转后学习的属性魔法可以给相反属性的敌人致命的伤害哦。");
            } else {
                cm.sendOk("你的等级不足8级。无法转职成为魔法师。");
                cm.dispose();
            }
        } else {
            //不是新手
            //如果角色等级大于等于30 并且职业是魔法师。即判定其准备二转
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 200) {
                //如果条件成立，则判断其是否有4031012 - 英雄证书 - 第二次转职时教官授予的英雄证书.
                if (cm.haveItem(4031012, 1)) {//如果有英雄证书 ,代表完成教官任务归来
                    if (cm.haveItem(4031012, 1)) {
                        status = 30; //开始执行二转。跳转到21
                        cm.sendNext("恭喜你完成了测试。拿到了英雄证书。想要继续转职，请点击下一页!");
                    } else {//如果没有英雄证书
                        //防止任务道具丢失,无法进行转职
                        if (!cm.haveItem(4031009)) {//如果背包没有 4031009 - 汉斯的信件 - 从魔法密林的汉斯收到的信件.应该转交给魔法师教官.
                            cm.gainItem(4031009, 1);//给予信件。
                        }

                        //101020000 - 金银岛 - 魔法密林北部
                        cm.sendNext("请去找 #r魔法师二转教官#k.他就在 #r- 金银岛 - 魔法密林北部#k.");
                        cm.dispose();
                    }
                } else {
                    status = 10;//跳转到11
                    cm.sendNext("你已经可以转职了,要转职请点下一页.");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 210 || cm.getJob() == 220 || cm.getJob() == 230) {
                //如果条件成立，则判断其是否有4031059  - 黑符 - 在异界打退分身后获取的道符。
                if (cm.haveItem(4031059, 1)) {// - 黑符 - 在异界打退分身后获取的道符。
                    //如果有4031059
                    status = 50;
                    cm.gainItem(4031057, 1);//给予 4031057 - 力气项链 - 在异界打退分身后贤者给的项链
                    cm.gainItem(4031059, -1);//删除黑符
                    cm.sendNext("恭喜你打败分身，拿到黑符，完成了测试！现在拿着这个 #r#t4031058##k 去长老公馆找 #b泰勒斯#k.");
                } else {
                    //如果没有
                    status = 40;
                    cm.sendNext("恭喜你达到70级，你现在已经可以三转了。如果需要三转请单击下一页.");
                }
            } else {
                cm.sendOk("你好。我是魔法师转职教官。需要二转、三转，就来找我吧！");
                cm.dispose();
            }
        }
        //此处开始执行魔法师一转
    } else if (status == 1) {
        cm.sendNextPrev("一旦转职了就不能反悔,如果不想转职请点上一页.");
    } else if (status == 2) {
        cm.sendYesNo("你真的想成为一名 #r魔法师#k 吗?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(200); // 改变职业为魔法师
            cm.resetStats(4, 4, 20, 4);
        }
        cm.gainItem(1372043, 1);//1372043 - 初级魔法师的杖 - (無描述)
        cm.sendOk("转职成功 ! 你现在是一名魔法师了.");
        if (cm.getPlayer().getGender() == 0) {
            cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功转职成为一名魔法师，让我们热烈的祝福他吧！！！");
        } else {
            cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功转职成为一名魔法师，让我们热烈的祝福她吧！！！");
        }
        cm.dispose();
        //魔法师一转结束


        //魔法师二转，开始，判断没有英雄证书时，执行
    } else if (status == 11) {
        cm.sendNextPrev("魔法师二转时，你可以选择如下职业：\r\n #r法师(火,毒)#k 或 #r法师(冰,雷)#k 或 #r牧师#k.");
    } else if (status == 12) {
        cm.askAcceptDecline("但是我必须先测试你，有没有资格进行二转,你准备好了吗 ?");
    } else if (status == 13) {//4031009
        cm.gainItem(4031009, 1);//- 4031009 - 汉斯的信件 - 从魔法密林的汉斯收到的信件.应该转交给魔法师教官.
        //cm.warp(102020300);//传送到二转教官的地图
        status = 20;//跳转到21
        cm.sendNext("请去找 #b魔法师转职教官#k . 他会教你怎么做！.");
        //cm.dispose();


        //此处是判断已经有了教练的信，再次点击NPC 传送过去的代码
    } else if (status == 21) {
        cm.sendNextPrev("什么？你不知道 #r- 金银岛 - 魔法密林北部#k 在哪里？");
    } else if (status == 22) {
        cm.askAcceptDecline("好吧，我可以送你过去。但是要收费哦！传送费用需要十万金币");
    } else if (status == 23) {
        if (cm.getPlayer().getMeso() >= 100000) {//判断玩家金币是否大于十万
            cm.gainMeso(-100000);//扣除十万金币
            cm.warp(101020000);//传送到二转教官的地图  //101020000 - 金银岛 - 魔法密林北部
            cm.sendOk("我已经将你传送到二转教官所在地图了。他就在最上面，去找他吧！");
            cm.dispose();
        } else {
            cm.sendOk("你的金币不足10万，我无法送你过去！\r\n所以你还是自己去百度下那个地图怎么去吧！穷鬼！\r\n你目前拥有 " + cm.getPlayer().getMeso() + " 金币.");
        }

        //此处开始执行二转
    } else if (status == 31) {
        cm.sendSimple("你想转职成为什么职业 ? #b\r\n#L0#巫师(火,毒)#l\r\n#L1#法师(雷,冰)#l\r\n#L2#牧师#l#k");
    } else if (status == 32) {
        var jobName;
        if (selection == 0) {
            jobName = "法师(火,毒)";
            job = 210; // FP
        } else if (selection == 1) {
            jobName = "法师(雷,冰)";
            job = 220; // IL
        } else {
            jobName = "牧师";
            job = 230; // CLERIC
        }
        cm.sendYesNo("你真的要成为一名 #r" + jobName + "#k?");
    } else if (status == 33) {
        cm.changeJob(job);//改变职业
        //4031012 - 英雄证书 - 第二次转职时教官授予的英雄证书.
        cm.gainItem(4031012, -1);//删除英雄证书
        cm.sendOk("转职成功！");
        if (cm.getPlayer().getGender() == 0) {
            cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功进行二转，让我们热烈的祝福他吧！！！");
        } else {
            cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功进行二转，让我们热烈的祝福她吧！！！");
        }
        cm.dispose();
    } else if (status == 41) {
        cm.sendNextPrev("想要三转，我必须先测试你的能力！请去 - 金银岛 - 异界I \r\n进去之后，里面有一个黑魔法师冒充的我的分身。请击败他，并且拿到他掉落的 #r黑符#k，回来交给我。");
    } else if (status == 42) {
        cm.sendNextPrev("什么？你不知道 - 金银岛 - 异界I 怎么去？");
    } else if (status == 43) {
        cm.askAcceptDecline("好吧，我可以送你过去。但是要收费哦！传送费用需要二十万金币!");
    } else if (status == 44) {
        if (cm.getPlayer().getMeso() >= 200000) {//判断玩家金币是否大于20万
            cm.gainMeso(-200000);//扣除20万金币
            cm.warp(108010101);//传送到二转教官的地图
            cm.spawnMobOnMap(9001001, 1, 299, 20, 108010101);//召唤出分身//9001001 - 汉斯的分身
            cm.sendOk("我已经把你送到这里了。请打败黑魔法师冒充的分身，拿到 #r黑符#k 回来交给我.");
            cm.dispose();
        } else {
            cm.sendOk("你的金币不足20万，我无法送你过去！\r\n所以你还是自己去百度下那个地图怎么去吧！穷鬼！\r\n你目前拥有 " + cm.getPlayer().getMeso() + " 金币.");

        }
        cm.dispose();
    } else if (status == 50) {
        cm.sendNextPrev("想去雪域长老公馆，必须先去魔法密林港口，做飞船前往天空之城，然后从天空之城前往雪域。");
    } else if (status == 51) {
        cm.askAcceptDecline("什么？你不想坐船？可以啊！我可以送你过去！但是收费哦！需要100万金币呢！");
    } else if (status == 52) {
        if (cm.getPlayer().getMeso() >= 1000000) {//判断玩家金币是否大于100万
            cm.gainMeso(-1000000);//扣除100万金币
            cm.warp(211000001);//传送到长老公馆
            cm.sendOk("我已经把你送到长老公馆了。请和 #b鲁碧#k 对话！");
            cm.dispose();
        } else {
            cm.sendOk("你的金币不足20万，我无法送你过去！\r\n所以你还是老老实实坐船去吧！穷鬼！\r\n你目前拥有 " + cm.getPlayer().getMeso() + " 金币.");
        }
        cm.dispose();
    }
}
