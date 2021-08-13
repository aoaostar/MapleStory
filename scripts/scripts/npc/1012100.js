/**
 * @作者：MinaMS
 * @重构完善版本
 * @用途 弓箭手转职 1~3转 触发NPC 1012100.js 地图 100000201 - 金银岛 - 弓箭手培训中心
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
            if (cm.getPlayer().getLevel() >= 10) {//如果角色等级大于等于10
                cm.sendNext("你想成为一名 #r弓箭手#k 吗？\r\n弓手拥有高敏捷及力量,在战斗中负责远距离攻击,假如弓手职业能巧妙地运用地势的话,打猎可是非常轻松厉害。");
            } else {
                cm.sendOk("你的等级不足10级。无法转职成为弓箭手。");
                cm.dispose();
            }
        } else {
            //不是新手
            //如果角色等级大于等于30 并且职业是弓箭手。即判定其准备二转
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 300) {
                //如果条件成立，则判断其是否有4031012 - 英雄证书 - 第二次转职时教官授予的英雄证书.
                if (cm.haveItem(4031012, 1)) {//如果有英雄证书 ,代表完成教官任务归来
                    if (cm.haveItem(4031012, 1)) {
                        status = 30; //开始执行二转。跳转到21
                        cm.sendNext("恭喜你完成了测试。拿到了英雄证书。想要继续转职，请点击下一页!");
                    } else {//如果没有英雄证书
                        //防止任务道具丢失,无法进行转职
                        if (!cm.haveItem(4031010)) {//如果背包没有4031010 - 赫丽娜的信件 - 从射手村的赫丽娜收到的信件.应该转交给弓箭手教官.
                            cm.gainItem(4031010, 1);//给予信件。
                        }

                        //106010000 - 金银岛 - 迷宫通道
                        cm.sendNext("请去找 #r弓箭手二转教官#k.他就在 #r- 金银岛 - 迷宫通道#k.");
                        cm.dispose();
                    }
                } else {
                    status = 10;//跳转到11
                    cm.sendNext("你已经可以转职了,要转职请点下一页.");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 310 || cm.getJob() == 320) {
                //如果条件成立，则判断其是否有4031059  - 黑符 - 在异界打退分身后获取的道符。
                if (cm.haveItem(4031059, 1)) {// - 黑符 - 在异界打退分身后获取的道符。
                    //如果有4031059
                    status = 50;
                    cm.gainItem(4031057, 1);//给予 4031057 - 力气项链 - 在异界打退分身后贤者给的项链
                    cm.gainItem(4031059, -1);//删除黑符
                    cm.sendNext("恭喜你打败分身，拿到黑符，完成了测试！现在拿着这个 #r#t4031057##k 去长老公馆找 #b泰勒斯#k.");
                } else {
                    //如果没有
                    status = 40;
                    cm.sendNext("恭喜你达到70级，你现在已经可以三转了。如果需要三转请单击下一页.");
                }
            } else {
                cm.sendOk("你好。我是弓箭手转职教官。需要二转、三转，就来找我吧！");
                cm.dispose();
            }
        }
        //此处开始执行弓箭手一转
    } else if (status == 1) {
        cm.sendNextPrev("一旦转职了就不能反悔,如果不想转职请点上一页.");
    } else if (status == 2) {
        cm.sendYesNo("你真的想成为一名 #r弓箭手#k 吗?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(300); // 改变职业为弓箭手
            cm.resetStats(4, 25, 4, 4);
        }
        cm.gainItem(1452051, 1); //1452051 - 初级弓手的弓 - (無描述)
        cm.gainItem(2060000, 1000);//弓矢
        cm.gainItem(2060000, 1000);//弓矢
        cm.sendOk("转职成功 ! 你现在是一名弓箭手了.");
        if (cm.getPlayer().getGender() == 0) {
            cm.worldMessage("[转职快报]：恭喜帅哥." + cm.getChar().getName() + "  成功转职成为一名弓箭手，让我们热烈的祝福他吧！！！");
        } else {
            cm.worldMessage("[转职快报]：恭喜美女." + cm.getChar().getName() + "  成功转职成为一名弓箭手，让我们热烈的祝福她吧！！！");
        }
        cm.dispose();
        //弓箭手一转结束


        //弓箭手二转，开始，判断没有英雄证书时，执行
    } else if (status == 11) {
        cm.sendNextPrev("弓箭手二转时，你可以选择如下职业：\r\n #r猎手#k 或 #r弩手#k.");
    } else if (status == 12) {
        cm.askAcceptDecline("但是我必须先测试你，有没有资格进行二转,你准备好了吗 ?");
    } else if (status == 13) {//4031010
        cm.gainItem(4031010, 1);//给予赫丽娜的信件 -
        //cm.warp(102020300);//传送到二转教官的地图
        status = 20;//跳转到21
        cm.sendNext("请去找 #b弓箭手转职教官#k . 他会教你怎么做！.");
        //cm.dispose();


        //此处是判断已经有了教练的信，再次点击NPC 传送过去的代码
    } else if (status == 21) {
        cm.sendNextPrev("什么？你不知道 #r- 金银岛 - 迷宫通道#k 在哪里？");
    } else if (status == 22) {
        cm.askAcceptDecline("好吧，我可以送你过去。但是要收费哦！传送费用需要十万金币");
    } else if (status == 23) {
        if (cm.getPlayer().getMeso() >= 100000) {//判断玩家金币是否大于十万
            cm.gainMeso(-100000);//扣除十万金币
            cm.warp(106010000);//传送到二转教官的地图
            cm.sendOk("我已经将你传送到二转教官所在地图了。他就在这，去找他吧！");
            cm.dispose();
        } else {
            cm.sendOk("你的金币不足10万，我无法送你过去！\r\n所以你还是自己去百度下那个地图怎么去吧！穷鬼！\r\n你目前拥有 " + cm.getPlayer().getMeso() + " 金币.");
        }

        //此处开始执行二转
    } else if (status == 31) {
        cm.sendSimple("你想转职成为什么职业 ? #b\r\n#L0#猎手#l\r\n#L1#弩弓手#l#k");
    } else if (status == 32) {
        var jobName;
        if (selection == 0) {
            jobName = "猎手";
            job = 310; // 猎手
        } else {
            jobName = "弩弓手";
            job = 320; // 弩弓手
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
        cm.sendNextPrev("想要三转，我必须先测试你的能力！请去 - 金银岛 - 异界III \r\n进去之后，里面有一个黑魔法师冒充的我的分身。请击败他，并且拿到他掉落的 #r黑符#k，回来交给我。");
    } else if (status == 42) {
        cm.sendNextPrev("什么？你不知道 - 金银岛 - 异界III 怎么去？");
    } else if (status == 43) {
        cm.askAcceptDecline("好吧，我可以送你过去。但是要收费哦！传送费用需要二十万金币!");
    } else if (status == 44) {
        if (cm.getPlayer().getMeso() >= 200000) {//判断玩家金币是否大于20万
            cm.gainMeso(-200000);//扣除20万金币
            cm.warp(108010301);//传送到二转教官的地图
            cm.spawnMobOnMap(9001002, 1, 299, 20, 108010301);//召唤出分身//9001002 - 赫丽娜的分身
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
            cm.sendOk("我已经把你送到长老公馆了。请和 #b蕾妮#k 对话！");
            cm.dispose();
        } else {
            cm.sendOk("你的金币不足20万，我无法送你过去！\r\n所以你还是老老实实坐船去吧！穷鬼！\r\n你目前拥有 " + cm.getPlayer().getMeso() + " 金币.");
        }
        cm.dispose();
    }
}
