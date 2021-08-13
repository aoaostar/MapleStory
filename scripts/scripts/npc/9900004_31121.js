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
            var txt1 = "#d#L1#兑换#v2043001##z2043001#\t  需要：#v4002000#X10个\r\n"//3红色之弩\r\n";
            var txt2 = "#d#L2#兑换#v2043101##z2043101#\t  需要：#v4002000#X10个\r\n\r\n"//3红色之矛
            var txt3 = "#d#L3#兑换#v2043201##z2043201#\t需要：#v4002000#X10个\r\n\r\n"//3红色切割者
            var txt4 = "#d#L4#兑换#v2043301##z2043301#\t    需要：#v4002000#X10个\r\n\r\n"//3红色双手剑
            var txt5 = "#d#L5#兑换#v2043701##z2043701#\t    需要：#v4002000#X10个\r\n\r\n"//3红色弓
            var txt6 = "#d#L6#兑换#v2043801##z2043801#\t    需要：#v4002000#X10个\r\n\r\n"//3红色拳甲
            var txt7 = "#d#L7#兑换#v2044001##z2044001#\t  需要：#v4002000#X10个\r\n\r\n"//3海盗红拳
            var txt8 = "#d#L8#兑换#v2044101##z2044101#\t  需要：#v4002000#X10个\r\n\r\n"//3红色长杖
            var txt9 = "#d#L9#兑换#v2044201##z2044201#\t需要：#v4002000#X10个\r\n\r\n"//3红色短枪	
            var txt10 = "#d#L10#兑换#v2044301##z2044301#\t      需要：#v4002000#X10个\r\n"//3红色双手剑
            var txt11 = "#d#L11#兑换#v2044401##z2044401#\t      需要：#v4002000#X10个\r\n"//3红色弓
            var txt12 = "#d#L12#兑换#v2044501##z2044501#\t      需要：#v4002000#X10个\r\n"//3红色拳甲
            var txt13 = "#d#L13#兑换#v2044601##z2044601#\t      需要：#v4002000#X10个\r\n"//3海盗红拳
            var txt14 = "#d#L14#兑换#v2044701##z2044701#\t    需要：#v4002000#X10个\r\n"//3红色长杖
            var txt15 = "#d#L15#兑换#v2044801##z2044801#\t   需要：#v4002000#X10个\r\n"//3红色短枪
            var txt16 = "#d#L16#兑换#v2044901##z2044901#\t    需要：#v4002000#X10个\r\n"//3红色长杖
            var txt17 = "#d#L17#兑换#v2040804##z2040804#\t    需要：#v4002000#X20个\r\n"//3红色短枪



            cm.sendSimple("如果你有#v4002000#，可以在我这里兑换60%卷轴哦！！！\r\n" + txt1 + "" + txt2 + "" + txt3 + "" + txt4 + "" + txt5 + "" + txt6 + "" + txt7 + "" + txt8 + "" + txt9 + "" + txt10 + "" + txt11 + "" + txt12 + "" + txt13 + "" + txt14 + "" + txt15 + "" + txt16 + "" + txt17 + "");
        } else if (status == 1) {
            if (selection == 1) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043001, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[单手剑攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 2) { 
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043101, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[单手斧攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043201, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[单手钝器攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043301, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[短剑攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043701, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[短杖魔力卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 6) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2043801, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[长杖魔力卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044001, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[双手剑攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044101, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[双手斧攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044201, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[双手钝器攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044301, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[枪攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044401, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[矛攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(20441001, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[弓攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044601, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[弩攻击卷轴60% ]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044701, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[拳套攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            } else if (selection == 110) {
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044801, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[拳甲攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 16){
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044901, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[短枪攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 17){
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2040804, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[手套攻击卷轴60%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 18){
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044703, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[拳套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 19){
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(20448110, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[指节攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 20){
                if (cm.haveItem(4002000, 10)) {
                    cm.gainItem(4002000, -10);
                    cm.gainItem(2044908, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[短枪攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4002000#");
                    cm.dispose();
                }
            }else if(selection == 21){
                if (cm.haveItem(4002000, 20)) {
                    cm.gainItem(4002000, -20);
                    cm.gainItem(2040807, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用绿蜗牛邮票换取[手套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v40314106#");
                    cm.dispose();
                }
            }
        }
    }
}
