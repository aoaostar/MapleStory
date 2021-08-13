importPackage(Packages.client);

var status = 0;
var 抽奖币 = 4310027;//抽奖币
var 快乐百宝券 = 4110000;//快乐百宝券
var 升级消耗物品 = 4310058;//老公老婆币
var 升级所需物品 = 1112446;//老公老婆戒指1级
var 给予戒指ID = 1112446;//老婆老公戒指1级
var 随机 = Math.floor(Math.random() * 100);//随机值0→100
var 力量 = 3;
var 敏捷 = 3;
var 智力 = 3;
var 运气 = 3;
var 物理攻击力 = 3;
var 魔法攻击力 = 3;
var 锁 = 1;
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
            var text = "";
            for (i = 0; i < 50; i++) {
                text += "";
            }
            text += "#L1#升级老公老婆戒指：#r1→2#k#l\r\n\r\n";
            text += "#L2#升级老公老婆戒指：#r2→3#k#l\r\n\r\n";
            text += "#L3#升级老公老婆戒指：#r3→4#k#l\r\n\r\n";
            text += "#L4#升级老公老婆戒指：#r4→5#k#l\r\n\r\n";
            text += "#L5#升级老公老婆戒指：#r5→6#k#l\r\n\r\n";
            text += "#L6#升级老公老婆戒指：#r6→7#k#l\r\n\r\n";
            text += "#L7#升级老公老婆戒指：#r7→8#k#l\r\n\r\n";
            text += "#L8#升级老公老婆戒指：#r8→9#k#l\r\n\r\n";
            text += "#L9#升级老公老婆戒指：#r9→10#k#l\r\n\r\n";
            text += "#L10#升级老公老婆戒指：#r10→11#k#l\r\n\r\n";
            text += "#L11#升级老公老婆戒指：#r11→12#k#l\r\n\r\n";
            text += "#L12#升级老公老婆戒指：#r12→13#k#l\r\n\r\n";
            text += "#L13#升级老公老婆戒指：#r13→14#k#l\r\n\r\n";
            text += "#L14#升级老公老婆戒指：#r14→15#k#l\r\n\r\n";
            text += "#L15#升级老公老婆戒指：#r15→16#k#l\r\n\r\n";
            text += "#L16#升级老公老婆戒指：#r16→17#k#l\r\n\r\n";
            text += "#L17#升级老公老婆戒指：#r17→18#k#l\r\n\r\n";
            text += "#L18#升级老公老婆戒指：#r18→19#k#l\r\n\r\n";
            text += "#L19#升级老公老婆戒指：#r19→20#k#l\r\n\r\n";
            text += "#L20#升级老公老婆戒指：#r20→21#k#l\r\n\r\n";
            text += "#L21#升级老公老婆戒指：#r21→22#k#l\r\n\r\n";
            text += "#L22#升级老公老婆戒指：#r22→23#k#l\r\n\r\n";
            text += "#L23#升级老公老婆戒指：#r23→24#k#l\r\n\r\n";
            text += "#L24#升级老公老婆戒指：#r24→25#k#l\r\n\r\n";
            text += "#L25#升级老公老婆戒指：#r25→26#k#l\r\n\r\n";
            text += "#L26#升级老公老婆戒指：#r26→27#k#l\r\n\r\n";
            text += "#L27#升级老公老婆戒指：#r27→28#k#l\r\n\r\n";
            text += "#L28#升级老公老婆戒指：#r28→29#k#l\r\n\r\n";
            text += "#L29#升级老公老婆戒指：#r29→30#k#l\r\n\r\n";
            text += "#L30#升级老公老婆戒指：#r30→31#k#l\r\n\r\n";
            text += "#L31#升级老公老婆戒指：#r31→32#k#l\r\n\r\n";
            text += "#L32#升级老公老婆戒指：#r32→33#k#l\r\n\r\n";
            text += "#L33#升级老公老婆戒指：#r33→34#k#l\r\n\r\n";
            text += "#L34#升级老公老婆戒指：#r34→35#k#l\r\n\r\n";
            text += "#L35#升级老公老婆戒指：#r35→36#k#l\r\n\r\n";
            text += "#L36#升级老公老婆戒指：#r36→37#k#l\r\n\r\n";
            text += "#L37#升级老公老婆戒指：#r37→38#k#l\r\n\r\n";
            text += "#L38#升级老公老婆戒指：#r38→39#k#l\r\n\r\n";
            text += "#L39#升级老公老婆戒指：#r39→40#k#l\r\n\r\n";
            text += "#L40#升级老公老婆戒指：#r40→41#k#l\r\n\r\n";
            text += "#L41#升级老公老婆戒指：#r41→42#k#l\r\n\r\n";
            text += "#L42#升级老公老婆戒指：#r42→43#k#l\r\n\r\n";
            text += "#L43#升级老公老婆戒指：#r43→44#k#l\r\n\r\n";
            text += "#L44#升级老公老婆戒指：#r44→45#k#l\r\n\r\n";
            text += "#L45#升级老公老婆戒指：#r45→46#k#l\r\n\r\n";
            text += "#L46#升级老公老婆戒指：#r46→47#k#l\r\n\r\n";
            text += "#L47#升级老公老婆戒指：#r47→48#k#l\r\n\r\n";
            text += "#L48#升级老公老婆戒指：#r48→49#k#l\r\n\r\n";
            text += "#L49#升级老公老婆戒指：#r49→50#k#l\r\n\r\n";
            cm.sendSimple(text);
        } else if (status == 1) {
            if (selection == 1) {
                if (cm.haveItem(升级所需物品, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 30) {//百分之八十成功率
                        cm.gainItem(升级所需物品, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 1, 力量 + 1, 敏捷 + 1, 智力 + 1, 运气 + 1, 物理攻击力 + 1, 魔法攻击力 + 1, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.2，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 2) {
                if (cm.haveItem(升级所需物品 + 1, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 35) {
                        cm.gainItem(升级所需物品 + 1, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 2, 力量 + 2, 敏捷 + 2, 智力 + 2, 运气 + 2, 物理攻击力 + 2, 魔法攻击力 + 2, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.3，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(升级所需物品 + 2, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 40) {
                        cm.gainItem(升级所需物品 + 2, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 3, 力量 + 3, 敏捷 + 3, 智力 + 3, 运气 + 3, 物理攻击力 + 3, 魔法攻击力 + 3, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.4，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(升级所需物品 + 3, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 45) {
                        cm.gainItem(升级所需物品 + 3, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 4, 力量 + 4, 敏捷 + 4, 智力 + 4, 运气 + 4, 物理攻击力 + 4, 魔法攻击力 + 4, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.5，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (cm.haveItem(升级所需物品 + 4, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 50) {
                        cm.gainItem(升级所需物品 + 4, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 5, 力量 + 5, 敏捷 + 5, 智力 + 5, 运气 + 5, 物理攻击力 + 5, 魔法攻击力 + 5, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.6，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 6) {
                if (cm.haveItem(升级所需物品 + 5, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 55) {
                        cm.gainItem(升级所需物品 + 5, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 6, 力量 + 6, 敏捷 + 6, 智力 + 6, 运气 + 6, 物理攻击力 + 6, 魔法攻击力 + 6, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.7，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(升级所需物品 + 6, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 6, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 7, 力量 + 7, 敏捷 + 7, 智力 + 7, 运气 + 7, 物理攻击力 + 7, 魔法攻击力 + 7, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.8，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 8) {
                if (cm.haveItem(升级所需物品 + 7, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 7, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 8, 力量 + 8, 敏捷 + 8, 智力 + 8, 运气 + 8, 物理攻击力 + 8, 魔法攻击力 + 8, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.9，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(升级所需物品 + 8, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 8, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 9, 力量 + 9, 敏捷 + 9, 智力 + 9, 运气 + 9, 物理攻击力 + 9, 魔法攻击力 + 9, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.10，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(升级所需物品 + 9, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 9, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 10, 力量 + 10, 敏捷 + 10, 智力 + 10, 运气 + 10, 物理攻击力 + 10, 魔法攻击力 + 10, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.11，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(升级所需物品 + 10, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 10, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 11, 力量 + 11, 敏捷 + 11, 智力 + 11, 运气 + 11, 物理攻击力 + 11, 魔法攻击力 + 11, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.12，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(升级所需物品 + 11, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 11, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 12, 力量 + 12, 敏捷 + 12, 智力 + 12, 运气 + 12, 物理攻击力 + 12, 魔法攻击力 + 12, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.13，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(升级所需物品 + 12, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 12, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 13, 力量 + 13, 敏捷 + 13, 智力 + 13, 运气 + 13, 物理攻击力 + 13, 魔法攻击力 + 13, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.14，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(升级所需物品 + 13, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 13, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 14, 力量 + 14, 敏捷 + 14, 智力 + 14, 运气 + 14, 物理攻击力 + 14, 魔法攻击力 + 14, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.15，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 15) {
                if (cm.haveItem(升级所需物品 + 14, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 14, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 15, 力量 + 15, 敏捷 + 15, 智力 + 15, 运气 + 15, 物理攻击力 + 15, 魔法攻击力 + 15, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.16，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 16) {
                if (cm.haveItem(升级所需物品 + 15, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 60) {
                        cm.gainItem(升级所需物品 + 15, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 16, 力量 + 16, 敏捷 + 16, 智力 + 16, 运气 + 16, 物理攻击力 + 16, 魔法攻击力 + 16, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.17，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 17) {
                if (cm.haveItem(升级所需物品 + 16, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 61) {
                        cm.gainItem(升级所需物品 + 16, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 17, 力量 + 17, 敏捷 + 17, 智力 + 17, 运气 + 17, 物理攻击力 + 17, 魔法攻击力 + 17, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.18，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 18) {
                if (cm.haveItem(升级所需物品 + 17, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 62) {
                        cm.gainItem(升级所需物品 + 17, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 18, 力量 + 18, 敏捷 + 18, 智力 + 18, 运气 + 18, 物理攻击力 + 18, 魔法攻击力 + 18, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.19，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 19) {
                if (cm.haveItem(升级所需物品 + 18, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 63) {
                        cm.gainItem(升级所需物品 + 18, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 19, 力量 + 19, 敏捷 + 19, 智力 + 19, 运气 + 19, 物理攻击力 + 19, 魔法攻击力 + 19, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.20，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 20) {
                if (cm.haveItem(升级所需物品 + 19, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 64) {
                        cm.gainItem(升级所需物品 + 19, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 20, 力量 + 20, 敏捷 + 20, 智力 + 20, 运气 + 20, 物理攻击力 + 20, 魔法攻击力 + 20, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.21，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 21) {
                if (cm.haveItem(升级所需物品 + 20, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 65) {
                        cm.gainItem(升级所需物品 + 20, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 21, 力量 + 21, 敏捷 + 21, 智力 + 21, 运气 + 21, 物理攻击力 + 21, 魔法攻击力 + 21, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.22，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 22) {
                if (cm.haveItem(升级所需物品 + 21, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 65) {
                        cm.gainItem(升级所需物品 + 21, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 22, 力量 + 22, 敏捷 + 22, 智力 + 22, 运气 + 22, 物理攻击力 + 22, 魔法攻击力 + 22, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.23，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 23) {
                if (cm.haveItem(升级所需物品 + 22, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 65) {
                        cm.gainItem(升级所需物品 + 22, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 23, 力量 + 23, 敏捷 + 23, 智力 + 23, 运气 + 23, 物理攻击力 + 23, 魔法攻击力 + 23, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.24，接下来就算失败我也会给你一些小奖励哦！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 24) {
                if (cm.haveItem(升级所需物品 + 23, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 65) {
                        cm.gainItem(升级所需物品 + 23, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 24, 力量 + 24, 敏捷 + 24, 智力 + 24, 运气 + 24, 物理攻击力 + 24, 魔法攻击力 + 24, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.25，我已经注意到你了！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(快乐百宝券, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个快乐百宝券以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 25) {
                if (cm.haveItem(升级所需物品 + 24, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 66) {
                        cm.gainItem(升级所需物品 + 24, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 25, 力量 + 25, 敏捷 + 25, 智力 + 25, 运气 + 25, 物理攻击力 + 25, 魔法攻击力 + 25, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.26，接下来，我得给你增加难度了！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(快乐百宝券, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个快乐百宝券以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 26) {
                if (cm.haveItem(升级所需物品 + 25, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 68) {
                        cm.gainItem(升级所需物品 + 25, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 26, 力量 + 26, 敏捷 + 26, 智力 + 26, 运气 + 26, 物理攻击力 + 26, 魔法攻击力 + 26, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.27，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(快乐百宝券, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个快乐百宝券以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 27) {
                if (cm.haveItem(升级所需物品 + 26, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 26, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 27, 力量 + 27, 敏捷 + 27, 智力 + 27, 运气 + 27, 物理攻击力 + 27, 魔法攻击力 + 27, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.28，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(快乐百宝券, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个快乐百宝券以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 28) {
                if (cm.haveItem(升级所需物品 + 27, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 27, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 28, 力量 + 28, 敏捷 + 28, 智力 + 28, 运气 + 28, 物理攻击力 + 28, 魔法攻击力 + 28, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.29，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 29) {
                if (cm.haveItem(升级所需物品 + 28, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 28, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 29, 力量 + 29, 敏捷 + 29, 智力 + 29, 运气 + 29, 物理攻击力 + 29, 魔法攻击力 + 29, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.30，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 30) {
                if (cm.haveItem(升级所需物品 + 29, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 29, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 30, 力量 + 30, 敏捷 + 30, 智力 + 30, 运气 + 30, 物理攻击力 + 30, 魔法攻击力 + 30, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.31，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 31) {
                if (cm.haveItem(升级所需物品 + 30, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 30, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 31, 力量 + 31, 敏捷 + 31, 智力 + 31, 运气 + 31, 物理攻击力 + 31, 魔法攻击力 + 31, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.32，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 32) {
                if (cm.haveItem(升级所需物品 + 31, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 70) {
                        cm.gainItem(升级所需物品 + 31, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 32, 力量 + 32, 敏捷 + 32, 智力 + 32, 运气 + 32, 物理攻击力 + 32, 魔法攻击力 + 32, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.33，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 33) {
                if (cm.haveItem(升级所需物品 + 32, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 72) {
                        cm.gainItem(升级所需物品 + 32, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 33, 力量 + 33, 敏捷 + 33, 智力 + 33, 运气 + 33, 物理攻击力 + 33, 魔法攻击力 + 33, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.34，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 34) {
                if (cm.haveItem(升级所需物品 + 33, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 74) {
                        cm.gainItem(升级所需物品 + 33, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 34, 力量 + 34, 敏捷 + 34, 智力 + 34, 运气 + 34, 物理攻击力 + 34, 魔法攻击力 + 34, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.35，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 35) {
                if (cm.haveItem(升级所需物品 + 34, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 76) {
                        cm.gainItem(升级所需物品 + 34, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 35, 力量 + 35, 敏捷 + 35, 智力 + 35, 运气 + 35, 物理攻击力 + 35, 魔法攻击力 + 35, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.36，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 36) {
                if (cm.haveItem(升级所需物品 + 35, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 78) {
                        cm.gainItem(升级所需物品 + 35, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 36, 力量 + 36, 敏捷 + 36, 智力 + 36, 运气 + 36, 物理攻击力 + 36, 魔法攻击力 + 36, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.37，要到38级我赌起码8个币！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 37) {
                if (cm.haveItem(升级所需物品 + 36, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 80) {
                        cm.gainItem(升级所需物品 + 36, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 37, 力量 + 37, 敏捷 + 37, 智力 + 37, 运气 + 37, 物理攻击力 + 37, 魔法攻击力 + 37, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.38，要到39级我赌起码9个币！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 38) {
                if (cm.haveItem(升级所需物品 + 37, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 82) {
                        cm.gainItem(升级所需物品 + 37, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 38, 力量 + 38, 敏捷 + 38, 智力 + 38, 运气 + 38, 物理攻击力 + 38, 魔法攻击力 + 38, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.39，要到40级我赌起码10个币！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 39) {
                if (cm.haveItem(升级所需物品 + 38, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 84) {
                        cm.gainItem(升级所需物品 + 38, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 39, 力量 + 39, 敏捷 + 39, 智力 + 39, 运气 + 39, 物理攻击力 + 39, 魔法攻击力 + 39, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.40，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 40) {
                if (cm.haveItem(升级所需物品 + 39, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 86) {
                        cm.gainItem(升级所需物品 + 39, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 40, 力量 + 40, 敏捷 + 40, 智力 + 40, 运气 + 40, 物理攻击力 + 40, 魔法攻击力 + 40, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.41，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.gainItem(抽奖币, 1);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 41) {
                if (cm.haveItem(升级所需物品 + 40, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 88) {
                        cm.gainItem(升级所需物品 + 40, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 41, 力量 + 41, 敏捷 + 41, 智力 + 41, 运气 + 41, 物理攻击力 + 41, 魔法攻击力 + 41, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.42，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 42) {
                if (cm.haveItem(升级所需物品 + 41, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 90) {
                        cm.gainItem(升级所需物品 + 41, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 42, 力量 + 42, 敏捷 + 42, 智力 + 42, 运气 + 42, 物理攻击力 + 42, 魔法攻击力 + 42, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.43，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 43) {
                if (cm.haveItem(升级所需物品 + 42, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 91) {
                        cm.gainItem(升级所需物品 + 42, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 43, 力量 + 43, 敏捷 + 43, 智力 + 43, 运气 + 43, 物理攻击力 + 43, 魔法攻击力 + 43, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.44，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 44) {
                if (cm.haveItem(升级所需物品 + 43, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 92) {
                        cm.gainItem(升级所需物品 + 43, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 44, 力量 + 44, 敏捷 + 44, 智力 + 44, 运气 + 44, 物理攻击力 + 44, 魔法攻击力 + 44, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.45，欧洲人！欧洲人！这是来自北欧的纯种欧洲人啊！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 3);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、三张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 45) {
                if (cm.haveItem(升级所需物品 + 44, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 93) {
                        cm.gainItem(升级所需物品 + 44, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 45, 力量 + 45, 敏捷 + 45, 智力 + 45, 运气 + 45, 物理攻击力 + 45, 魔法攻击力 + 45, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.46，难道你就是传说中的欧皇？");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 4);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、四张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 46) {
                if (cm.haveItem(升级所需物品 + 45, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 94) {
                        cm.gainItem(升级所需物品 + 45, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 46, 力量 + 46, 敏捷 + 46, 智力 + 46, 运气 + 46, 物理攻击力 + 46, 魔法攻击力 + 46, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.47，男默女泪、感情至深动人肺腑！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 4);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、四张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 47) {
                if (cm.haveItem(升级所需物品 + 46, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 95) {
                        cm.gainItem(升级所需物品 + 46, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 47, 力量 + 47, 敏捷 + 47, 智力 + 47, 运气 + 47, 物理攻击力 + 47, 魔法攻击力 + 47, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.48，G-M不想说话，并对你放了一个屁！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 4);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、四张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 48) {
                if (cm.haveItem(升级所需物品 + 47, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 96) {
                        cm.gainItem(升级所需物品 + 47, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 48, 力量 + 48, 敏捷 + 48, 智力 + 48, 运气 + 48, 物理攻击力 + 48, 魔法攻击力 + 48, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.49，起立！为大佬鼓掌！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 4);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、四张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 49) {
                if (cm.haveItem(升级所需物品 + 48, 1) && cm.haveItem(升级消耗物品, 1)) {
                    if (随机 >= 97) {
                        cm.gainItem(升级所需物品 + 48, -1);
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(给予戒指ID + 49, 力量 + 49, 敏捷 + 49, 智力 + 49, 运气 + 49, 物理攻击力 + 49, 魔法攻击力 + 49, 锁);
                        cm.喇叭(4, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.50，下一件该升级什么装备呢？");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                        cm.dispose();
                    } else {
                        cm.gainItem(升级消耗物品, -1);
                        cm.gainItem(抽奖币, 1);
                        cm.gainItem(快乐百宝券, 5);
                        cm.sendOk("哎呀呀，又升级失败了。太抱歉了！！看着你黑如锅底的面庞，赠送你一个抽奖币、五张快乐百宝卷以作鼓励！");
                        cm.喇叭(2, "非洲人[" + cm.getPlayer().getName() + "]在老公老婆升级中心痛苦地流下眼泪！");
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            }
        }
    }
}


