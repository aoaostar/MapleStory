importPackage(Packages.tools);
importPackage(Packages.constants);


var 道具喇叭 = 2;
var 高效能喇叭 = 1;
var Sp = 10;
var 自付币 = 1;
var 金币 = 10000;



var picked = 0;
var status = -1;
var itemid = -1;
var state = -1;


function start() {
    status = -1;
    action(1, 0, 0);
}


function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        cm.sendOk("谢谢光临，欢迎下次再来.");
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("┏━━━━━━━━━━━━━━━━━━━┓\r\n#k\t- 自付币余额；#r[" + cm.getBossRank("自由宝",2) + "]#k\r\n┗#k━━━━━━━━━━━━━━━━━━━┛#k\r\n#l\r\n#L800##b汇率#l\r\n#L900##b充值余额#l#L200#货币购买#l#L101##b游戏喇叭#l#L102##b技能重置卷#l");
    } else if (status == 1) {
        state = selection;
        if (state == 101) {
            cm.sendSimple("你目前有 #r[" + cm.getBossRank("自由宝",2) + "]#k 自由币#k \r #L0##i5072000#高效能喇叭 - #r"+高效能喇叭+"#k \r\n#L1##i5076000#道具喇叭 - #r"+道具喇叭+"#k");
        } else if (state == 102) {
            cm.sendSimple("你目前有 #r[" + cm.getBossRank("自由宝",2) + "]#k 自由币#k \r #L6##i5050001#1转技能重置卷轴 - #r"+Sp+"#k\r\n#L7##i5050002#2转技能重置卷轴 - #r"+Sp+"#k\r\n#L8##i5050003#3转技能重置卷轴 - #r"+Sp+"#k\r\n#L9##i5050004#4转技能重置卷轴 - #r"+Sp+"#k");
        } else if (state == 900) {
            cm.sendSimple(" #L901#国庆币充值#l ");
        } else if (state == 200) {
            cm.sendSimple(" #L201#金币购买#l ");
        } else if (state == 800) {
            cm.sendSimple(" 金币汇率 #r 1："+金币+" #k");
			cm.dispose();
			
			
			
			
			
	
        }
    } else if (status == 2) {
        if (state == 101) {
            picked = selection;
            cm.sendGetNumber("请问您要买多少个呢??", 1, 1, 100);
        } else if (state == 102) {
            picked = selection;
            cm.sendGetNumber("请问您要买多少个呢??", 1, 1, 100);
        } else if (state == 900) {
            picked = selection;
            cm.sendGetNumber("请问您要买多少个呢??", 1, 1, 100);
        } else if (state == 200) {
            picked = selection;
            cm.sendGetNumber("请问您要买多少个呢??", 1, 10, 100);
        }
    } else if (status == 3) {
        tw = selection;
        if (state == 101) {
            if (tw * 0 != -0) {
                cm.sendOk("我只接受0以上的数字!");
                cm.dispose();
                return;
            }
            if (picked == 0) {
                cm.sendYesNo("这些#i5072000# 花您 " + tw * 高效能喇叭 + " 自由币, 请问您确定要购买吗??");
            }
            if (picked == 1) {
                cm.sendYesNo("这些#i5076000# 花您 " + tw * 道具喇叭 + " 自由币, 请问您确定要购买吗??");
            }

        }
        if (state == 102) {
            if (tw * 0 != 0) {
                cm.sendOk("我只接受0以上的数字!");
                cm.dispose();
                return;
            
            }
            if (picked == 6) {
                cm.sendYesNo("这些#i5050001# 花您 " + tw * Sp + " 自由币, 请问您确定要购买吗??");
            }
            if (picked == 7) {
                cm.sendYesNo("这些#i5050002# 花您 " + tw * Sp + " 自由币, 请问您确定要购买吗??");
            }
            if (picked == 8) {
                cm.sendYesNo("这些#i5050003# 花您 " + tw * Sp + " 自由币, 请问您确定要购买吗??");
            }
            if (picked == 9) {
                cm.sendYesNo("这些#i5050004# 花您 " + tw * Sp + " 自由币, 请问您确定要购买吗??");
            }
        }
		if (state == 900) {
            if (tw * 0 != 0) {
                cm.sendOk("我只接受0以上的数字!");
                cm.dispose();
                return;
            }
            if (picked == 901) {
                cm.sendYesNo("充值" + tw * 自付币 + "自由币余额, 请问您确定要充值吗??");
            }

        }
		if (state == 200) {
            if (tw * 0 != 0) {
                cm.sendOk("我只接受0以上的数字!");
                cm.dispose();
                return;
            }
            if (picked == 201) {
                cm.sendYesNo("购买" + tw * 金币 + " 金币, 请问您确定要购买吗??");
            }

        }
    } else if (status == 4) {
        if (state == 101) {
            if (picked == 0) {
                if (cm.getBossRank("自由宝",2) >= tw * 高效能喇叭) {
                    cm.gainItem(5072000, tw);
                    cm.setBossRankCount("自由宝",-tw * 高效能喇叭);
                    cm.sendOk("感谢你购买了 #i5072000# 花您 " + tw * 高效能喇叭 + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }
            }
            if (picked == 1) {
                if (cm.getBossRank("自由宝",2) >= tw * 道具喇叭) {
                    cm.gainItem(5076000, tw);
                    cm.setBossRankCount("自由宝",-tw * 道具喇叭);
                    cm.sendOk("感谢你购买了 #i5076000# 花您 " + tw * 道具喇叭 + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }
            }
            
        }
        if (state == 102) {
            if (picked == 6) {
                if (cm.getBossRank("自由宝",2) >= tw * Sp) {
                    cm.gainItem(5050001, tw);
                    cm.gainMeso(-(tw * Sp));
                    cm.sendOk("感谢你购买了 #i5050001# 花您 " + tw * Sp + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }
            }
            if (picked == 7) {
                if (cm.getBossRank("自由宝",2) >= tw * Sp) {
                    cm.gainItem(5050002, tw);
                    cm.gainMeso(-(tw * Sp));
                    cm.sendOk("感谢你购买了 #i5050002# 花您 " + tw * Sp + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }
            }
            if (picked == 8) {
                if (cm.getBossRank("自由宝",2) >= tw * Sp) {
                    cm.gainItem(5050003, tw);
                    cm.gainMeso(-(tw * Sp));
                    cm.sendOk("感谢你购买了 #i5050003# 花您 " + tw * Sp + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }
            }
            if (picked == 9) {
                if (cm.getBossRank("自由宝",2) >= tw * Sp) {
                    cm.gainItem(5050004, tw);
                    cm.gainMeso(-(tw * Sp));
                    cm.sendOk("感谢你购买了 #i5050004# 花您 " + tw * Sp + " 自由币，谢谢惠顾欢迎下次再来~~");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的自由币!");
                    cm.dispose();
                }

            }
        }
		if (state == 900) {
            if (picked == 901) {
                if (cm.haveItem(4000463,tw)) {
                    cm.gainItem(4000463, -tw);
                    
					cm.setBossRankCount("自由宝",tw * 自付币);
                    cm.sendOk(" 充值自付币余额 #r" + tw * 自付币 + " #k成功。");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的#i4000463# !");
                    cm.dispose();
                }
            

            }
        }
		if (state == 200) {
            if (picked == 201) {
                if (cm.getBossRank("自由宝",2) >= tw ) {
                    cm.gainMeso(tw * 金币);
					cm.setBossRankCount("自由宝",-tw );
                    cm.sendOk(" 购买 #r" + tw * 金币 + " #k成功。");
                    cm.dispose();
                } else {
                    cm.sendOk("您没有足够的余额!");
                    cm.dispose();
                }
            

            }
        }
    }
	
	
}