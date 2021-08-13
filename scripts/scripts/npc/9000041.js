importPackage(Packages.client);
importPackage(Packages.client.inventory);


var status = -1;
var beauty = 0;
var tosend = 0;
var sl;
var mats;
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            if (status == 0) {
                cm.sendNext("如果需要点卷中介服务在来找我吧。");
                cm.dispose();
            }
            status--;
        }
        if (status == 0) {
            var gsjb = "";
            gsjb ="  #e#r可乐获得方式: 在吃货系统用#v4001126##v4000313#兑换\r\n  \r\n";
            gsjb +="  当前点卷:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";
           // gsjb +="#L3##b#z4001126#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r1 = 1#b)#l\r\n\r\n";
			//gsjb +="#L1##b#z4000313#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r1 = 10#b)#l\r\n\r\n";
			gsjb +="#L2##b#v2022035#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r1 = 3000#b)#l\r\n\r\n";
			gsjb +="#L6##b#v2022035#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r10 = 30000#b)#l\r\n\r\n";
			gsjb +="#L4##b#v2020031#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r1 = 3000#b)#l\r\n\r\n";
			gsjb +="#L8##b#v2020031#兑换点卷 #fUI/Basic/BtHide3/mouseOver/0# #b比例 - (#r10 = 30000#b)#l\r\n\r\n";
            cm.sendSimple(gsjb);
        } else if (status == 1) {
            if (cm.getPlayer() >= 1 && cm.getPlayer() <= 5) {
                cm.sendOk("GM不能参与兑换。");
                cm.dispose();
            }
            if (selection == 0) {
                if (cm.getPlayer().getCSPoints(1) / 30 == 0) {
                    cm.sendNext("您的帐户点卷不足无法兑换国庆纪念币。");
                    status = -1;
                } else {
                    beauty = 1;
                    cm.sendGetNumber("请输入#r点卷#k兑换#b#z4000463##k的数量:\r\n#b比例 - (#r1000 = 1#b)\r\n你的账户信息 -  点卷数量: #r" +
                            cm.getPlayer().getCSPoints(1) + " \r\n", 1, 1, cm.getPlayer().getCSPoints(1) / 1000);

                }

            
            } else if (selection == 1) {
                var iter = cm.getChar().getInventory(MapleInventoryType.ETC).listById(4000313).iterator();
                if (cm.haveItem(4000313) == 0) {
                    cm.sendNext("您的帐户#z4000313#数量不足兑换点卷。");
                    status = -1;
                } else {
                    beauty = 2;
                    cm.sendGetNumber("请输入#b#z4000313##k兑换#r点卷#k的数量:\r\n#b比例 - (#r1 = 10#b)\r\n你的账户信息 - \r\n    点卷数量: #r" +
                            cm.getPlayer().getCSPoints(1) + "    \r\n", 1, 1, iter.next().getQuantity());

                }
            } else if (selection == 3) {
                var iter = cm.getChar().getInventory(MapleInventoryType.ETC).listById(4001126).iterator();
                if (cm.haveItem(4001126) == 0) {
                    cm.sendNext("您的帐户#v4001126#数量不足兑换点卷。");
                    status = -1;
                } else {
                    beauty = 3;
                    cm.sendGetNumber("请输入#b#z4001126##k兑换#r点卷#k的数量:\r\n#b比例 - (#r1 = 1#b)\r\n你的账户信息 - \r\n    点卷数量: #r" +
                            cm.getPlayer().getCSPoints(1) + "   \r\n", 1, 1, iter.next().getQuantity());

                }
            } else if (selection == 2) {
                if (cm.haveItem(2022035,1)){
                cm.sendOk("兑换成功!");
                cm.gainItem(2022035, -1);
                		
				cm.gainNX(3000);
				//cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成可乐X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你点券~.");
                cm.dispose();
            }
        } else if (selection == 4) {
                if (cm.haveItem(2020031,1)){
                cm.sendOk("兑换成功!");
                cm.gainItem(2020031, -1);
                		
				cm.gainNX(3000);
				//cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成可乐X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你点券~.");
                cm.dispose();
            }
			
			
			} else if (selection == 6) {
                if (cm.haveItem(2022035,10)){
                cm.sendOk("兑换成功!");
                cm.gainItem(2022035, -10);
                		
				cm.gainNX(30000);
				//cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成可乐X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你点券~.");
                cm.dispose();
            }
        } else if (selection == 8) {
                if (cm.haveItem(2020031,10)){
                cm.sendOk("兑换成功!");
                cm.gainItem(2020031, -10);
                		
				cm.gainNX(30000);
				//cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成可乐X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你点券~.");
                cm.dispose();
            }


        } else if (status == 2) {
            if (beauty == 1) {
                if (selection <= 0) {
                    cm.sendOk("输入的兑换数字错误。");
                    cm.dispose();
                } else if (cm.getPlayer().getCSPoints(1) >= selection * 1000) {
                    cm.gainNX(-selection * 1000);
                    cm.gainItem(4000463, selection);
                    cm.sendOk("您成功将 #r " + (selection * 1000) + " #k点卷 兑换成 国庆纪念币#v4000463# x #r" + selection + " #k")
                } else {
                    cm.sendNext("兑换" + selection + "个#z4000463##v4000463# 需要#r " + (selection * 1000) + "#k点卷。您没有足够的点卷。");
                    cm.dispose();
                }
            } else if (beauty == 2) {
                if (cm.haveItem(4000313, selection)) {
                    cm.gainItem(4000313, -selection);
                    cm.gainNX(+10 * selection);
                    cm.sendOk("您成功将#z4000313##v4000313# x #r" + selection + " #k换为#r " + (10 * selection) + " #k点卷。");
                } else {
                    cm.sendNext("您的输入的数量错误，无法兑换点卷。");
                    cm.dispose();
                }

            } else if (beauty == 3) {
                if (cm.haveItem(4001126, selection)) {
                    cm.gainItem(4001126, -selection);
                    cm.gainNX(+Math.floor(1 * selection));
                    cm.sendOk("您成功将#z4001126##v4001126# x #r" + selection + " #k换为#r " + Math.floor(1 * selection) + " #k点卷。");
                } else {
                    cm.sendNext("您的输入的数量错误，无法兑换点卷。");
                    cm.dispose();
                }
	        } else if (beauty == 4) {
                if (cm.haveItem(4251401, selection)) {
                    cm.gainItem(4251401, -selection);
                    cm.gainNX(+Math.floor(5000 * selection));
                    cm.sendOk("您成功将#z4251401##v4251401# x #r" + selection + " #k换为#r " + Math.floor(5000 * selection) + " #k点卷。");
                } else {
                    cm.sendNext("您的输入的数量错误，无法兑换点卷。");
                    cm.dispose();
                }
            }
            status = -1;
        } else {
            cm.dispose();
        }
    }
}
}