/*
 * 
 * @枫之梦
 * 神器进阶系统 - 魔武双修
 */
importPackage(net.sf.odinms.client);
var status = 0;
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
            var txt1 = "#d#L1#兑换#v2340000##z2340000#\t     需要：#v4002000#X20个\r\n"//3红色之弩\r\n";



            cm.sendSimple("如果你有#v4002000#，可以在我这里兑换祝福哦！！\r\n" + txt1 + "");
        } else if (status == 1) {
            if (selection == 1) {
                if (cm.haveItem(4002000, 20)) {
                    cm.gainItem(4002000, -20);
                    cm.gainItem(2340000, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用邮票换取[祝福卷轴%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002002#");
                    cm.dispose();
                }
            } else if (selection == 2) { 
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2043102, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[单手斧攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2043202, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[单手钝器攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2043302, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[短剑攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2043702, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[短杖魔力卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 6) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2043802, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[长杖魔力卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044002, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[双手剑攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 8) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044102, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[双手斧攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044202, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[双手钝器攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044302, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[枪攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044402, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[矛攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044502, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[弓攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044602, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[弩攻击卷轴10% ]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044702, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[拳套攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            } else if (selection == 15) {
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044802, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[拳甲攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 16){
                if (cm.haveItem(4031456, 20)) {
                    cm.gainItem(4031456, -20);
                    cm.gainItem(2044902, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[短枪攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 17){
                if (cm.haveItem(4031456, 25)) {
                    cm.gainItem(4031456, -25);
                    cm.gainItem(2040805, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[手套攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 18){
                if (cm.haveItem(4031456, 30)) {
                    cm.gainItem(4031456, -30);
                    cm.gainItem(2044703, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[拳套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 19){
                if (cm.haveItem(4031456, 30)) {
                    cm.gainItem(4031456, -30);
                    cm.gainItem(2044815, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[指节攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 20){
                if (cm.haveItem(4031456, 30)) {
                    cm.gainItem(4031456, -30);
                    cm.gainItem(2044908, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[短枪攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }else if(selection == 21){
                if (cm.haveItem(4031456, 30)) {
                    cm.gainItem(4031456, -30);
                    cm.gainItem(2040807, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用副本珠换取[手套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031456#");
                    cm.dispose();
                }
            }
        }
    }
}
