/* global cm */

var 正在进行中 = "#fUI/UIWindow/Quest/Tab/enabled/1#";
var 完成 = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var 正在进行中蓝 = "#fUI/UIWindow/MonsterCarnival/icon1#";
var 完成红 = "#fUI/UIWindow/MonsterCarnival/icon0#";
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
                text += "";
            }
            text += "\t\t\t  #e#d   欢迎来到#r冒险岛世界#b"  + "#e#d\r\n\t注意事项：系统记录外挂请不要报着侥幸心理开。\r\n\r\n"

            if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getdjjl() == 0) {
                text += "#L1##r" + 完成红 + "等级达到10级！" + 完成 + "#v2000004##v5440000#+20W点卷 #l\r\n\r\n"
            } else if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getdjjl() > 0) {
                text += "" + 完成红 + "#r等级达到10级！#l" + 完成 + "#v2000004##v5440000# +20W点卷#l\r\n\r\n"
            } else {
                text += "" + 正在进行中蓝 + "#r等级达到10级！#l" + 正在进行中 + "#v2000004##v5440000# +20W点卷#l\r\n\r\n"
            }

            if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getdjjl() == 1) {
                text += "#L2##r" + 完成红 + "等级达到30级！" + 完成 + "#v2000005##v2022070##v5440000#+80W点卷 #l\r\n\r\n"
            } else if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getdjjl() > 1) {
                text += "" + 完成红 + "#r等级达到30级！#l" + 完成 + "#v2000005##v2022070##v5440000#+80W点卷#l\r\n\r\n"
            } else {
                text += "" + 正在进行中蓝 + "#r等级达到30级！#l" + 正在进行中 + "#v2000005##v2022070##v5440000#+80W点卷 #l\r\n\r\n"
            }

            if (cm.getPlayer().getLevel() >= 70 && cm.getPlayer().getdjjl() == 2) {
                text += "#L3##r" + 完成红 + "等级达到70级！" + 完成 + "#v2000005##v2022035#金币100W #l\r\n\r\n"
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getPlayer().getdjjl() > 2) {
                text += "" + 完成红 + "#r等级达到70级！#l" + 完成 + "#v2000005##v2022035#金币100W#l\r\n\r\n"
            } else {
                text += "" + 正在进行中蓝 + "#r等级达到70级！#l" + 正在进行中 + "#v2000005##v2022035#金币100W #l\r\n\r\n"
            }

            if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getdjjl() == 3) {
                text += "#L4##r" + 完成红 + "等级达到100级！" + 完成 + "#v4002001##v5150040##v5152001##v5440000 #\r\n\r\n"
            } else if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getdjjl() > 3) {
                text += "" + 完成红 + "#r等级达到100级！#l" + 完成 + "#v4002001##v5150040##v5152001##v5440000#\r\n\r\n"
            } else {
                text += "" + 正在进行中蓝 + "#r等级达到100级！#l" + 正在进行中 + "#v4002001##v5150040##v5152001##v5440000 #\r\n\r\n"
            }

            if (cm.getPlayer().getLevel() >= 150 && cm.getPlayer().getdjjl() == 4) {
                text += "#L5##r" + 完成红 + "等级达到150级！" + 完成 + "#v2010006##v2002031##v5440000#金币200W #l\r\n\r\n"
            } else if (cm.getPlayer().getLevel() >= 150 && cm.getPlayer().getdjjl() > 4) {
                text += "" + 完成红 + "#r等级达到150级！#l" + 完成 + "#v2010006##v2002031##v5440000#金币200W #l\r\n\r\n"
            } else {
                text += "" + 正在进行中蓝 + "#r等级达到150级！#l" + 正在进行中 + "#v2010006##v2002031##v5440000#金币200W #l\r\n\r\n"
            }

        //    if (cm.getPlayer().getLevel() >= 190 && cm.getPlayer().getdjjl() == 5) {
              //  text += "#L6##r" + 完成红 + "等级达到190级！" + 完成 + "#v4000463##v4002003##v5440000#金币200W #l\r\n\r\n\r\n"
           // } else if (cm.getPlayer().getLevel() >= 190 && cm.getPlayer().getdjjl() > 5) {
            //    text += "" + 完成红 + "#r等级达到190级！#l" + 完成 + "#v4000463##v4002003##v5440000#金币200W #l\r\n\r\n\r\n"
          //  } else {
            //    text += "" + 正在进行中蓝 + "#r等级达到190级！#l" + 正在进行中 + "#v4000463##v4002003##v5440000#金币200W #l\r\n\r\n\r\n"
          //  }

            //if (cm.getPlayer().getLevel() >= 150 && cm.getPlayer().getdjjl() == 6) {
            //    text += "#L7##r" + 完成红 + "等级达到150级！" + 完成 + "点卷x3000 #l\r\n\r\n\r\n"
            //} else if (cm.getPlayer().getLevel() >= 150 && cm.getPlayer().getdjjl() > 6) {
            //    text += "" + 完成红 + "#r等级达到150级！#l" + 完成 + "点卷x3000 #l\r\n\r\n\r\n"
            //} else {
            //    text += "" + 正在进行中蓝 + "#r等级达到150级！#l" + 正在进行中 + "点卷x3000 #l\r\n\r\n\r\n"
            //}

            //if (cm.getPlayer().getLevel() >= 170 && cm.getPlayer().getdjjl() == 7) {
            //    text += "#L8##r" + 完成红 + "等级达到170级！" + 完成 + "点卷x5000 #l\r\n\r\n"
            //} else if (cm.getPlayer().getLevel() >= 170 && cm.getPlayer().getdjjl() > 7) {
            //    text += "" + 完成红 + "#r等级达到170级！#l" + 完成 + "点卷x5000 #l\r\n\r\n"
            //} else {
            //    text += "" + 正在进行中蓝 + "#r等级达到170级！#l" + 正在进行中 + "点卷x5000 #l\r\n\r\n"
            //}

            //if (cm.getPlayer().getLevel() >= 180 && cm.getPlayer().getdjjl() == 8) {
            //    text += "#L9##r" + 完成红 + "等级达到180级！" + 完成 + "#v4251200#x3 #l\r\n\r\n"
            //} else if (cm.getPlayer().getLevel() >= 180 && cm.getPlayer().getdjjl() > 8) {
            //    text += "" + 完成红 + "#r等级达到180级！#l" + 完成 + "#v4251200#x3 #l\r\n\r\n"
            //} else {
            //    text += "" + 正在进行中蓝 + "#r等级达到180级！#l" + 正在进行中 + "#v4251200#x3 #l\r\n\r\n"
            //}

            //if (cm.getPlayer().getLevel() >= 190 && cm.getPlayer().getdjjl() == 9) {
            //    text += "#L10##r" + 完成红 + "等级达到190级！" + 完成 + "点卷*8000#l\r\n\r\n"
            //} else if (cm.getPlayer().getLevel() >= 190 && cm.getPlayer().getdjjl() > 9) {
            //    text += "" + 完成红 + "#r等级达到190级！#l" + 完成 + "点卷*8000 #l\r\n\r\n"
            //} else {
            //    text += "" + 正在进行中蓝 + "#r等级达到190级！#l" + 正在进行中 + "点卷*8000 #l\r\n\r\n"
            //}

            //if (cm.getPlayer().getLevel() >= 199 && cm.getPlayer().getdjjl() == 10) {
            // text += "#L11##r" + 完成红 + "等级达到199级！" + 完成 + "#v1142111#x1 限时：1天#l\r\n\r\n\r\n"
            // } else if (cm.getPlayer().getLevel() >= 199 && cm.getPlayer().getdjjl() > 10) {
            //  text += "" + 完成红 + "#r等级达到199级！#l" + 完成 + "\r\n\r\n"
            // } else {
            //  text += "" + 正在进行中蓝 + "#r等级达到199级！#l" + 正在进行中 + "\r\n"
            //}

          //  if (cm.getPlayer().getLevel() >= 200 && cm.getPlayer().getdjjl() == 11) {
           //  text += "#L12##r" + 完成红 + "等级达到200级！" + 完成 + "点卷x30000点 #l\r\n\r\n\r\n"
          //  } else if (cm.getPlayer().getLevel() >= 200 && cm.getPlayer().getdjjl() > 11) {
          //    text += "" + 完成红 + "#r等级达到200级！#l" + 完成 + "\r\n\r\n"
         //   } else {
        //     text += "" + 正在进行中蓝 + "#r等级达到200级！#l" + 正在进行中 + "\r\n"
         //   }

            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.gainItem(2000004,100);//100个特殊药水
			cm.gainDY(300);//抵用300点
			cm.gainNX(200000);//
            cm.setdjjl(1);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到10级，领取了100个特殊药水，抵用300点，点卷20W");
            cm.dispose();
        } else if (selection == 2) {
            cm.gainItem(2000005, 50);//100个超级药水
			cm.gainItem(2022070, 1);//管理者的祝福1个
			cm.gainDY(500)//抵用500点
			cm.gainNX(800000);//
            cm.setdjjl(2);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到30级，领取了100个超级药水，祝福增益1个，抵用500点，点卷80W");
            cm.dispose();
        } else if (selection == 3) {
            cm.gainItem(2000005, 10);//50个超级药水
			cm.gainItem(2022035, 5);//500个枫叶
            cm.gainMeso(+1000000);//金币
            cm.setdjjl(3);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到70级，领取了10个超级药水，5个百事可乐");
            cm.dispose();
        } else if (selection == 4) {
            cm.gainItem(4002001, 1);//蓝蜗牛邮票1张
			cm.gainItem(5150040, 1);//皇家理发1张
			cm.gainItem(5152001, 1);//射手整容1张
			cm.gainDY(+1000);
            cm.setdjjl(4);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到100级，领取了蓝蜗牛邮票1张，皇家理发1张，射手村高级整容1张");
            cm.dispose();
        } else if (selection == 5) {
          
			cm.gainItem(2010006, 1);//木妖邮票1张
			cm.gainItem(2002031, 1);//黄金枫叶100个
			cm.gainDY(+5000);
			cm.gainMeso(+2000000);//金币#v2010006##v2002031#
            cm.setdjjl(5);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到150级，领取了蜂蜜X1、草莓蛋糕X1、5000抵用券、200W金币");
            cm.dispose();
        } else if (selection == 6) {
            cm.gainItem(4000463, 10);//国庆纪念币10个
			cm.gainItem(4000463, 10);
			cm.gainItem(4002003, 5);//绿水灵邮票5张
		    //cm.gainDY(+2000);
			cm.gainMeso(+2000000);//金币
            cm.setdjjl(6);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到190级，领取了国庆纪念币10个，绿水灵邮票5张");
            cm.dispose();
        } else if (selection == 7) {
            cm.gainNX(3000);
            cm.setdjjl(7);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到150级，领取了点卷x3000点哦~！");
            cm.dispose();
        } else if (selection == 8) {
            cm.gainNX(5000);
            cm.setdjjl(8);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到170级，领取了点卷x5000点哦~！");
            cm.dispose();
        } else if (selection == 9) {
            cm.gainItem(4251200, 3);
            cm.setdjjl(9);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到180级，领取了五彩水晶x3个！");
            cm.dispose();
        } else if (selection == 10) {
            cm.gainNX(8000);
            cm.setdjjl(10);
            cm.即时存档();
            cm.sendOk("领取奖励成功！");
            cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到190级，领取了点卷x8000点！");
            cm.dispose();
            //} else if (selection == 11) {
            // cm.gainItem(1142111,100,100,100,100,5000,5000,100,100,10,10,10,10,10,10,24);
            //cm.setdjjl(11);
            //cm.即时存档();
            //cm.sendOk("领取奖励成功！");
            //cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到199级，领取了站在巅峰的人勋章！");
            // cm.dispose();
            //} else if (selection == 12) {
            //cm.gainNX(30000);
            //cm.setdjjl(12);
            //cm.即时存档();
            // cm.sendOk("领取奖励成功！");
            // cm.喇叭(1, "玩家：" + cm.getPlayer().getName() + "，等级达到200级，领取了点卷3万点！");
            //cm.dispose();

        }
    }
}


