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
            var txt1 = "#d#L1#兑换#v2070005##z2070005#\t\t\t\t需要：#v4031311#X70个\r\n"//3红色之弩\r\n";
            var txt2 = "#d#L2#兑换#v2070006##z2070006#\t\t\t\t需要：#v4031311#X100个\r\n"//3红色之矛
            // var txt3 = "#d#L3#兑换#v1082149##z1082149#\t需要：#v4031311#X400个\r\n"//3红色切割者
            // var txt4 = "#d#L4#兑换#v1102041##z1102041#\t需要：#v4031311#X200个\r\n"//3红色双手剑
            // var txt5 = "#d#L5#兑换#v1102042##z1102042#\t需要：#v4031311#X200个\r\n"//3红色弓
			
             var txt6 = "#d#L6#兑换#v5150040##z5150040#\t\t   需要：#v4031311#X5个\r\n"//3红色拳甲
             var txt7 = "#d#L7#兑换点卷1000点\t\t\t\t   需要：#v4031311#X15个\r\n"//3海盗红拳
         //    var txt8 = "#d#L8#兑换积分10点\t\t\t\t   需要：#v4031311#X10个\r\n"//3红色长杖
             var txt9 = "#d#L9#兑换#v5040000##z5040000#\t\t\t   需要：#v4031311#X2个\r\n"//3红色短枪	
             var txt10 = "#d#L10#兑换#v5041000##z5041000#\t\t  需要：#v4031311#X3个\r\n"//3红色双手剑
             var txt11 = "#d#L11#兑换#v5150038##z5150038#\t\t需要：#v4031311#X50个\r\n"//3红色弓
			
            // var txt12 = "#d#L12#兑换#v2044502##z2044502#\t需要：#v4031311#X30个\r\n"//3红色拳甲
            // var txt13 = "#d#L13#兑换#v2044602##z2044602#\t需要：#v4031311#X30个\r\n"//3海盗红拳
            // var txt14 = "#d#L14#兑换#v2044702##z2044702#\t需要：#v4031311#X30个\r\n"//3红色长杖
            // var txt15 = "#d#L15#兑换#v2044802##z2044802#\t需要：#v4031311#X30个\r\n"//3红色短枪
            // var txt16 = "#d#L16#兑换#v2044902##z2044902#\t需要：#v4031311#X30个\r\n"//3红色长杖
            // var txt17 = "#d#L17#兑换#v2040805##z2040805#\t需要：#v4031311#X30个\r\n"//3红色短枪



            cm.sendSimple("如果你有#v4031311#，可以在我这里兑换各种好东西哦！！！\r\n" + txt1 + "" + txt2 + "" + txt6 + "" + txt7 + "" + txt9 + "" + txt10 + "" + txt11 + "");
        } else if (status == 1) {
            if (selection == 1) {
                if (cm.haveItem(4031311, 70)) {
                    cm.gainItem(4031311, -70);
                    cm.gainItem(2070005, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[金钱镖]一个！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 2) { 
                if (cm.haveItem(4031311, 100)) {
                    cm.gainItem(4031311, -100);
                    cm.gainItem(2070006, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[齿轮镖]一个！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(4031311, 400)) {
                    cm.gainItem(4031311, -400);
		cm.gainItem(1082149,3,3,3,3,0,0,3,3,0,0,0,0,0,0);//新手勋章
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[工地手套（褐）]一个！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(4031311, 200)) {
                    cm.gainItem(4031311, -200);
		cm.gainItem(1102041,3,3,3,3,0,0,3,3,0,0,0,0,0,0);//新手勋章
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[浪人披风（粉）]一个！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (cm.haveItem(4031311, 200)) {
                    cm.gainItem(4031311, -200);
		cm.gainItem(1102042,3,3,3,3,0,0,0,7,0,0,0,0,0,0);//新手勋章
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[浪人披风（紫）]一个！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 6) {
                if (cm.haveItem(4031311, 5)) {
                    cm.gainItem(4031311, -5);
                    cm.gainItem(5150040, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[皇家理发卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(4031311, 15)) {
                    cm.gainItem(4031311, -15);
                    cm.gainNX(1000);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[点卷1000点]！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 8) {
                if (cm.haveItem(4031311, 10)) {
                    cm.gainItem(4031311, -10);
                    cm.gainjf(10);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[积分10点]！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(4031311, 2)) {
                    cm.gainItem(4031311, -2);
                    cm.gainItem(5040000, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[普通缩地石]！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4031311, 3)) {
                    cm.gainItem(4031311, -3);
                    cm.gainItem(5041000, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[高级缩地石]！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(4031311, 50)) {
                    cm.gainItem(4031311, -50);
                    cm.gainItem(5150038, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[超级明星发型卡]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044502, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[弓攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044602, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[弩攻击卷轴10% ]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044702, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[拳套攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            } else if (selection == 15) {
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044802, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[拳甲攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 16){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044902, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[短枪攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 17){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2040805, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[手套攻击卷轴10%]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 18){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044703, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[拳套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 19){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044815, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[指节攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 20){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2044908, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[短枪攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }else if(selection == 21){
                if (cm.haveItem(4031311, 30)) {
                    cm.gainItem(4031311, -30);
                    cm.gainItem(2040807, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用雪花换取[手套攻击卷轴卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取卷轴需要有#v4031311#");
                    cm.dispose();
                }
            }
        }
    }
}

