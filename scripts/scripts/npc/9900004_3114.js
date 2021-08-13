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
//var txt1 = "#d#L1#兑换#v2040303##z2040303#×1#l\r\n"//3耳环智力必成卷\r\n";
  //          var txt2 = "#d#L2#兑换#v2040506##z2040506#×1#l\r\n"//3全身盔甲敏捷必成卷
    //        var txt3 = "#d#L3#兑换#v2040709##z2040709#×1#l\r\n"//3鞋子敏捷必成卷
      //      var txt4 = "#d#L4#兑换#v2040806##z2040806#×1#l\r\n"//3手套敏捷必成卷
            var txt5 = "#d#L5#兑换#v2043003##z2043003#×1#l\r\n"//3单手剑攻击必成卷
            var txt6 = "#d#L6#兑换#v2043103##z2043103#×1#l\r\n"//3单手斧攻击必成卷
            var txt7 = "#d#L7#兑换#v2043203##z2043203#×1#l\r\n"//3单手钝器攻击必成卷
            var txt8 = "#d#L8#兑换#v2043303##z2043303#×1#l\r\n"//3短剑攻击必成卷
            var txt9 = "#d#L9#兑换#v2043703##z2043703#×1#l\r\n"//3短杖攻击必成卷
            var txt10 = "#d#L10#兑换#v2043803##z2043803#×1#l\r\n"//3长杖攻击必成卷
            var txt11 = "#d#L11#兑换#v2044003##z2044003#×1#l\r\n"//3双手剑攻击必成卷
            var txt12 = "#d#L12#兑换#v2044103##z2044103#×1#l\r\n"//3双手斧攻击必成卷
			var txt13 = "#d#L13#兑换#v2044203##z2044203#×1#l\r\n"//3双手钝器攻击必成卷
            var txt14 = "#d#L14#兑换#v2044303##z2044303#×1#l\r\n"//3枪攻击必成卷
            var txt15 = "#d#L15#兑换#v2044403##z2044403#×1#l\r\n"//3矛攻击必成卷
            var txt16 = "#d#L16#兑换#v2044503##z2044503#×1#l\r\n"//3弓攻击必成卷
            var txt17 = "#d#L17#兑换#v2044603##z2044603#×1#l\r\n"//3弩攻击必成卷
            var txt18 = "#d#L18#兑换#v2044703##z2044703#×1#l\r\n"//3拳套攻击必成卷
            var txt19 = "#d#L19#兑换#v2044815##z2044815#×1#l\r\n"//3指节攻击必成卷
            var txt20 = "#d#L20#兑换#v2044908##z2044908#×1#l\r\n"//3短枪攻击必成卷
        //    var txt21 = "#d#L21#兑换#v2040807##z2040807#×1#l\r\n"//3手套攻击必成卷



           cm.sendSimple("如果你有#v4310059#，可以在我这里兑换各种必成卷哦！！！\r\n" + "" + txt5 + "" + txt6 + "" + txt7 + "" + txt8 + "" + txt9 + "" + txt10 + "" + txt11 + "" + txt12 + "" + txt13 + "" + txt14 + "" + txt15 + "" + txt16 + "" + txt17 + "" + txt18 + "" + txt19 + "" + txt20 +  "");
        } else if (status == 1) {
            if (selection == 1) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2040303, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[耳环智力必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 2) { 
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2040506, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[全身盔甲敏捷必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2040709, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[鞋子敏捷必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2040806, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[手套敏捷必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043003, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[单手剑攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 6) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043103, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[单手斧攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043203, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[单手钝器攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 8) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043303, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[短剑攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043703, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[短杖攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2043803, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[长杖攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044003, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[双手剑攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044103, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[双手斧攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044203, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[双手钝器攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044303, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[枪攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            } else if (selection == 15) {
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044403, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[矛攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 16){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044503, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[弓攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 17){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044603, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[弩攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 18){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044703, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[拳套攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 19){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044815, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[指节攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 20){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2044908, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[短枪攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }else if(selection == 21){
                if (cm.haveItem(4310059, 1)) {
                    cm.gainItem(4310059, -1);
                    cm.gainItem(2040807, 1);
           cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成功利用必成兑换币换取[手套攻击必成卷]一张！");
                    cm.dispose();
                } else {
                    cm.sendOk("材料不足。无法合成！获取必成需要有#v4310059#");
                    cm.dispose();
                }
            }
        }
    }
}
