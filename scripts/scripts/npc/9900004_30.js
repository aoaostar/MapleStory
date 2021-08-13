/* global cm */

var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "#b#e 8月01日-30日大王冒险岛温情首测，经验爆率双倍提升！#k#n\r\n";
                //text += "  大王冒险岛筹备阶段历时整整两个月，终于进行了首次测试，本次测试还有很多游戏功能和技改还没完善，可能会存在一些BUG，欢迎大家踊跃举报，一个有效BUG第一个发现的玩家开服奖励20-50充值及积分\r\n\r\n"//3
                text += "#e#r  针对邀请好友参与游戏，大王冒险岛给出以下拉人奖励#k#n\r\n\r\n"//3
                text += "1：邀请好友，游戏公测后达到#r50级#k\r\n  #e推荐人#n：3000点卷、大王冒险岛金条*1、白医卷轴*20（10%成功几率）、普通抽奖币*5\r\n  #e受邀人#n：5000抵用、孙子兵法*20\r\n\r\n"//3
                text += "2：邀请好友，游戏公测后达到#r90级#k\r\n  #e推荐人#n：5000点卷、大王冒险岛金条*2、白医卷轴*30（10%成功几率）、普通抽奖币*7、混沌卷轴*5\r\n  #e受邀人#n：7000抵用、快乐百宝券*15、兔子骑宠交换券*5\r\n\r\n"//3
				text += "3：邀请好友，游戏公测后达到#r130级#k\r\n  #e推荐人#n：7000点卷、大王冒险岛金条*3、白医卷轴*40（10%成功几率）、祝福卷轴*5、普通抽奖币*10、混沌卷轴*15\r\n  #e受邀人#n：10000抵用、快乐百宝券*20、兔子骑宠交换券*10\r\n\r\n"//3
				text += "4：累计邀请3位好友，游戏公测后均达到#r70级#k，赠送绝版天然#r5四维、3G6M#k工地手套（褐），仅此发放一次，以后也不会有比这个属性更好的可交易手套。欢迎大家踊跃推荐好友！\r\n\r\n"
				text += "感谢大家一起帮助建设大王冒险岛，游戏内可能存在众多BUG，一个有效BUG开服奖励20~50充值#r（第一个发现、视BUG严重性进行奖励）#k\r\n\r\n#k"//3
				//text += "#r内测期间充值更有1.2倍超值优惠，快来加入我们吧\r\n#k"//3
                cm.sendSimple(text);
            }
        } else if (selection == 1) {
            if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
                cm.sendOk("装备栏空余不足1个空格！");//判断不等于，就提示对话
                cm.dispose();
            } else if (cm.getPlayer().getCSPoints(1) >= 300) {
                cm.gainNX(-300);
                cm.gainItem(1122017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
                cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买精灵坠子3小时使用权！");
                cm.dispose();
            } else {
                cm.sendOk("点卷不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 2) {
            if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
                cm.sendOk("装备栏空余不足1个空格！");//判断不等于，就提示对话
                cm.dispose();
            } else if (cm.getPlayer().getCSPoints(1) >= 600) {
                cm.gainNX(-600);
                cm.gainItem(1122017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10);
                cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买精灵坠子10小时使用权！");
                cm.dispose();
            } else {
                cm.sendOk("道具不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 3) {
            if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
                cm.sendOk("装备栏空余不足1个空格！");//判断不等于，就提示对话
                cm.dispose();
            } else if (cm.getPlayer().getCSPoints(1) >= 1200) {
                cm.gainNX(-1200);
                cm.gainItem(1122017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 24);
                cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买精灵坠子1天使用权！");
                cm.dispose();
            } else {
                cm.sendOk("道具不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 4) {
            if (!cm.beibao(1, 1)) {//前面的1对应装备第一栏，也就是装备 后面就是格数
                cm.sendOk("装备栏空余不足1个空格！");//判断不等于，就提示对话
                cm.dispose();
            } else if (cm.getPlayer().getCSPoints(1) >= 6000) {
                cm.gainNX(-6000);
                cm.gainItem(1122017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 168);
                cm.喇叭(1, "[" + cm.getPlayer().getName() + "]成功购买精灵坠子7天使用权！");
                cm.dispose();
            } else {
                cm.sendOk("点卷不足无法换购！");
                cm.dispose();
            }
        }
    }
}


