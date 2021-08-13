/*
 * 
 * @type豆豆装备兑换
 * @npcID9310101
 * @换购为：帽子
 */
/* 
 case 1002695://幽灵帽
 case 1002609://兔耳魔法帽
 case 1002665://西红柿帽
 case 1002985://豆箱帽子
 case 1002986://蝙蝠怪面具
 case 1002761://枫叶面具
 case 1002760://地球帽
 case 1002583://蝙蝠客头套
 case 1002543://板栗帽
 case 1002448://紫色头巾
 */
importPackage(net.sf.odinms.client);
var 时间之石 = 4021010;
var status = 0;
var zones = 0;
var ItemId = Array(
       // Array(5390006, 2000, "咆哮老虎喇叭"),
        Array(5390006, 500, "咆哮老虎喇叭")
        //如需其它道具兑换，请按照此格式自行添置。
        );

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            if (cm.getLevel < 10) {
                cm.sendOK("你的等级不足10级。。打开干嘛？", 2);
                cm.dispose();
            } else {
                selStr = "\t\t#e#r你想要用豆豆兑换什么物品呢？？？\r\n目前拥有"+cm.getBeans()+"豆豆！#n#k\r\n";
                for (var i = 0; i < ItemId.length; i++) {
                    selStr += "\r\n#L" + i + "##b#v" + ItemId[i][0] + "##k (需要#r " + ItemId[i][1] + " #k个 豆豆！)";
                }
                cm.sendSimple(selStr);
                zones == 0;
            }
        } else if (status == 1) {
            if (zones == 1) {
                cm.sendNext("你让我帮你做什么呢？", 2);
                zones = 2;
            } else if (zones == 0) {
if (cm.getBeans() >= ItemId[selection][1]) {
cm.gainBeans(-(ItemId[selection][1]));
                        cm.gainItem(cm.gainGachaponItem(ItemId[selection][0], 1, "豆豆兑换"), 1);
                        // cm.喇叭(3, "我成功用豆豆兑换了奖励！！");
                        cm.sendOk("兑换成功，请检背包!");
                        cm.dispose();
                    } else {
                        cm.sendOk("豆豆不足！");
                        cm.dispose();
                    }
                }
            } else if (status == 2) {
                if (zones == 2) {
                    cm.sendNext("我用于提升我魔法的时间之石被一群蘑菇妖偷走了,你能帮去抢回来吗？");
                    zones = 3;
                }
            } else if (status == 3) {
                if (zones == 3) {
                    cm.sendNext("行,这个没问题？你告诉我那些偷了你时间之石的蘑菇妖现在在什么地方呢?", 2);
                    zones = 4;
                }
            } else if (status == 4) {
                if (zones == 4) {
                    cm.sendNext("这个物品是#b全世界掉落#k的。你可以在世界上的#b任何一个怪物#k上获取！");
                    zones = 5;
                }
            } else if (status == 5) {
                if (zones == 5) {
                    cm.sendYesNo("如果你能帮我这个大忙的话,我会给你一些丰厚的奖励的，您是否愿意帮我？");
                }
            } else if (status == 6) {
                cm.setBossLog('MogoQuest');
                cm.gainItem(5390006, 1);
                cm.sendOk("非常感谢！获得后和我说话，就能换购物品了！");
                cm.dispose();
            }
        }
}	