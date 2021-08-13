/**
 * @作者：MinaMS
 * @重构完善版本
 * @用途 海盗转职 1~3转 触发NPC 1090000.js 地图 120000101 - 诺特勒斯号 - 航海室
 */

var status = 0;
var jobId;
var jobName;
var mapId


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 2) {
        cm.sendOk("下定决心再来找我.");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        //912010200 - 隐藏地图 - 巴特的房间
        //4031059 - 黑符 - 在异界打退分身后获取的道符。
        if (cm.getMapId() == 912010200 || cm.haveItem(4031059, 1)) {
            if (cm.getQuestStatus(6370) == 1) {
                cm.removeAll(4031059);
                //5221006 - 武装 - 登上海盗船。受到伤害时耐久度减少，耐久度为0时，一定时间内将不能乘坐海盗船。\n可使用技能 : 武装, 投弹攻击, 速射, 烈焰喷射, 寒冰喷射, 海鸥空袭, 章鱼炮台, 冒险岛勇士, 勇士的意志 \n#c耐久度耗尽后冷却时间 : 90秒#
                cm.teachSkill(5221006, 0, 10);
                cm.forceCompleteQuest(6370);
            } else if (cm.getQuestStatus(6330) == 1) {
                cm.removeAll(4031059);
                //5121003 - 超级变身 - 在120秒内保持超人变形状态。\n限制使用技能 : 双弹射击和橡木伪装都无法使用。\n必要技能 : #c变身 20级以上#
                cm.teachSkill(5121003, 0, 10);
                cm.forceCompleteQuest(6330);
            }
            cm.warp(120000101, 0);//120000101 - 诺特勒斯号 - 航海室　
            cm.sendOk("恭喜完成任务！！");
            cm.dispose();
        }
        if (cm.getJob() == 0) {
            if (cm.getPlayer().getLevel() >= 10) {
                cm.sendNext("你想成为一名 #r海盗#k 吗？\r\n海盗凭借高敏捷及力量给与敌人发射百发百中的短枪或者可以使用瞬间制约敌人的格斗术.使用短枪的海盗可以选择属性子弹更有效地攻击敌人或者坐在船上攻击敌人,使用格斗拳甲的海盗可以变身后发挥更强的力量。");
            } else {
                cm.sendOk("你的等级不足10级。无法转职成为海盗。");
                cm.dispose();
            }
        } else {
                        //不是新手
            //如果角色等级大于等于30 并且职业是海盗。即判定其准备二转
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 500) { // 海盗
                 //如果条件成立，则判断其是否有4031012 - 英雄证书 - 第二次转职时教官授予的英雄证书.
                if (cm.haveItem(4031012, 1)) {//如果有英雄证书 ,代表完成教官任务归来
                    if (cm.haveItem(4031012, 1)) {
                        status = 20;
                        cm.sendNext("恭喜你完成了测试。拿到了英雄证书。想要继续转职，请点击下一页!");
                    } else {//如果没有英雄证书
                        //防止任务道具丢失,无法进行转职
                        cm.sendOk("请去找 #r海盗转职教官#k.")
                        cm.dispose();
                    }
                } else {
                    status = 10;
                    cm.sendNext("你已经可以转职了,要转职请点下一页.");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 510 || cm.getJob() == 520) {
                if (cm.haveItem(4031059, 1)) {
                    cm.gainItem(4031057, 1);
                    cm.gainItem(4031059, -1);
                    cm.warp(211000001);
                    cm.sendOk("你完成了一个考验，现在去找 #b费德#k.");
                } else {
                    cm.warp(108010501, 0);//108010501 - 隐藏地图 - 异次元的世界
                    //  public void spawnMobOnMap(int mobId, int quantity, int x, int y, int map) {
                    //cm.warpMapWithClock(108010101, 800);//设置地图时间，108010501是异次元地图 103000003是废都酒吧 时间到了自动回到酒吧
                    cm.spawnMobOnMap(9001004, 1, 299, 20, 108010501);//召唤出分身//9001004 - 凯琳的分身
                    cm.sendOk("嗨, #b#h0##k! 我需要一个 #b黑符#k. 快去找异次元空间拿给我");
                    cm.startQuest(100101);
                }
                cm.dispose();
            } else if (cm.isQuestActive(6370)) {
                var dd = cm.getEventManager("KyrinTrainingGroundC");
                if (dd != null) {
                    dd.startInstance(cm.getPlayer());
                } else {
                    cm.sendOk("未知的错误请稍后在尝试。");
                }
            } else if (cm.isQuestActive(6330)) {
                var dd = cm.getEventManager("KyrinTrainingGroundV");
                if (dd != null) {
                    dd.startInstance(cm.getPlayer());
                } else {
                    cm.sendOk("未知的错误请稍后在尝试。");
                }
            } else {
                cm.sendOk("你好,我是卡伊琳-海盗转职官.");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        cm.sendNextPrev("一旦转职了就不能反悔,如果不想转职请点上一页.");
    } else if (status == 2) {
        cm.sendYesNo("你真的要成为一位 #r海盗#k ?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(500); // 海盗
            cm.resetStats(4, 25, 4, 4);
        }
        cm.gainItem(1482014, 1);
        cm.gainItem(1492014, 1);
        cm.gainItem(2330000, 600);
        cm.gainItem(2330000, 600);
        cm.gainItem(2330000, 600);
        cm.sendOk("转职成功 ! 请去开创天下吧.");
        cm.dispose();
    } else if (status == 11) {
        cm.sendNextPrev("你可以选择你要转职成为一位 #r拳手#k, #r火枪手#k.")
    } else if (status == 12) {
        cm.askAcceptDecline("但是我必须先测试你,你准备好了吗 ?");
    } else if (status == 13) {
        cm.sendSimple("你想要成为什么 ? #b\r\n#L0#拳手#l\r\n#L1#火枪手#l#k");
    } else if (status == 14) {
        var jobName;
        if (selection == 0) {
            jobName = "拳手";
            MapId = "108000502";
        } else if (selection == 1) {
            jobName = "火枪手";
            MapId = "108000500";
        }
        cm.sendYesNo("你真的要成为一位 #r" + jobName + "#k?");
    } else if (status == 15) {
        cm.warp(MapId);
        cm.sendOk("请去找 #b海盗转职教官#k . 他会帮助你的.");
        cm.dispose();
    } else if (status == 21) {
        cm.sendSimple("你想要成为什么 ? #b\r\n#L0#拳手#l\r\n#L1#火枪手#l#k");
    } else if (status == 22) {
        var jobName;
        if (selection == 0) {
            jobName = "拳手";
            job = 510;
        } else if (selection == 1) {
            jobName = "火枪手";
            job = 520;
        }
        cm.sendYesNo("你真的要成为一位 #r" + jobName + "#k?");
    } else if (status == 23) {
        cm.changeJob(job);
        if (cm.haveItem(4031857) && cm.haveItem(4031012, 1)) {
            cm.gainItem(4031857, -15);
            cm.gainItem(4031012, -1);
            cm.sendOk("转职成功.");
            cm.dispose();
        } else if (cm.haveItem(4031856) && cm.haveItem(4031012, 1)) {
            cm.gainItem(4031856, -15);
            cm.gainItem(4031012, -1);
            cm.sendOk("转职成功.");
            cm.dispose();
        } else {
            cm.removeAll(4031857);
            cm.removeAll(4031856);
            cm.removeAll(4031012);
            cm.sendOk("转职成功.");
            cm.dispose();
        }
    }
}