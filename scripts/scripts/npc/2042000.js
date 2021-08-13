var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}


function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        cm.dispose();
    if (status == 0 && mode == 1) {
        var selStr = "报名参加怪物嘉年华!\r\n#L100#兑换休彼德曼项链#l";
        var found = false;
        for (var i = 0; i < 6; i++) {
            if (getCPQField(i + 1) != "") {
                selStr += "\r\n#b#L" + i + "# " + getCPQField(i + 1) + "#l#k";
                found = true;
            }
        }
        if (cm.getParty() == null) {
            cm.sendSimple("你还没有一个队伍。\r\n#L100#兑换休彼德曼项链.#l");
            //cm.dispose();
        } else {
            if (cm.isLeader()) {
                if (found) {
                    cm.sendSimple(selStr);
                } else {
                    cm.sendSimple("目前没有空余房间.\r\n#L100#兑换休彼德曼项链.#l");
                    cm.dispose();
                }
            } else {
                cm.sendSimple("请让你的组队长跟我说话.\r\n#L100#兑换休彼德曼项链.#l");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        if (selection == 100) {
            cm.sendSimple("#b#L0#50 嘉年华币 = 兑换休彼德曼项链#l\r\n#L1#30 嘉年华币 = 兑换休彼德曼珠子#l\r\n#L2#150 嘉年华币 = 兑换休彼德曼混沌项链#l#k");
        } else if (selection >= 0 && selection < 6) {
            var mapid = 980000000 + ((selection + 1) * 100);
            if (cm.getEventManager("cpq").getInstance("cpq" + mapid) == null) {
                if ((cm.getParty() != null && 1 < cm.getParty().getMembers().size() && cm.getParty().getMembers().size() < (selection == 4 || selection == 5 || selection == 8 ? 4 : 3)) || cm.getPlayer().isGM()) {
                    if (checkLevelsAndMap(30, 255) == 1) {
                        cm.sendOk("队伍里有人等级不符合.");
                        cm.dispose();
                    } else if (checkLevelsAndMap(30, 255) == 2) {
                        cm.sendOk("队伍上的所有其他成员与你不在同一地图");
                        cm.dispose();
                    } else {
                        cm.getEventManager("cpq").startInstance("" + mapid, cm.getPlayer());
                        cm.dispose();
                    }
                } else {
                    cm.sendOk("你的队伍人数不符.");
                    cm.dispose();
                }
            } else if (cm.getParty() != null && cm.getEventManager("cpq").getInstance("cpq" + mapid).getPlayerCount() == cm.getParty().getMembers().size()) {
                if (checkLevelsAndMap(30, 255) == 1) {
                    cm.sendOk("队伍中有人的等级不符合.");
                    cm.dispose();
                } else if (checkLevelsAndMap(30, 255) == 2) {
                    cm.sendOk("你的队伍成员，并没有全在这个地图");
                    cm.dispose();
                } else {
                    //Send challenge packet here
                    var owner = cm.getChannelServer().getPlayerStorage().getCharacterByName(cm.getEventManager("cpq").getInstance("cpq" + mapid).getPlayers().get(0).getParty().getLeader().getName());
                    owner.addCarnivalRequest(cm.getCarnivalChallenge(cm.getChar()));
                    //if (owner.getConversation() != 1) {
                    cm.openNpc(owner.getClient(), 2042001);
                    //}
                    cm.sendOk("你的挑战邀请已经发出.");
                    cm.dispose();
                }
            } else {
                cm.sendOk("参加怪物嘉年华PQ的双方队伍，必须保证队伍人数一致！");
                cm.dispose();
            }
        } else {
            cm.dispose();
        }
    } else if (status == 2) {
        if (selection == 0) {
            if (!cm.haveItem(4001129, 50)) {
                cm.sendOk("你没有 50个 #i4001129# 无法兑换 #i1122007#");
            } else if (!cm.canHold(1122007, 1)) {
                cm.sendOk("请腾出背包空间");
            } else {
                cm.gainItem(1122007, 1);
                cm.gainItem(4001129, -50);
            }
            cm.dispose();
        } else if (selection == 1) {
            if (!cm.haveItem(4001129, 30)) {
                cm.sendOk("你没有 30个 #i4001129# 无法兑换 #i2041211#");
            } else if (!cm.canHold(2041211, 1)) {
                cm.sendOk("请腾出背包空间");
            } else {
                cm.gainItem(2041211, 1);
                cm.gainItem(4001129, -30);
            }
            cm.dispose();
        } else if (selection == 2) {
            if (!cm.haveItem(4001254, 150)) {
                cm.sendOk("你没有 150个 #i4001129# 无法兑换 #i1122058#");
            } else if (!cm.canHold(1122058, 1)) {
                cm.sendOk("请腾出背包空间");
            } else {
                cm.gainItem(1122058, 1);
                cm.gainItem(4001254, -150);
            }
            cm.dispose();
        }
    }
}

function checkLevelsAndMap(lowestlevel, highestlevel) {
    var party = cm.getParty().getMembers();
    var mapId = cm.getMapId();
    var valid = 0;
    var inMap = 0;

    var it = party.iterator();
    while (it.hasNext()) {
        var cPlayer = it.next();
        if (!(cPlayer.getLevel() >= lowestlevel && cPlayer.getLevel() <= highestlevel) && cPlayer.getJobId() != 900) {
            valid = 1;
        }
        if (cPlayer.getMapid() != mapId) {
            valid = 2;
        }
    }
    return valid;
}

function getCPQField(fieldnumber) {
    var status = "";
    var event1 = cm.getEventManager("cpq");
    if (event1 != null) {
        var event = event1.getInstance("cpq" + (980000000 + (fieldnumber * 100)));
        if (event == null && fieldnumber != 5 && fieldnumber != 6 && fieldnumber != 9) {
            status = "怪物嘉年华战场 " + fieldnumber + "(2v2)";
        } else if (event == null) {
            status = "怪物嘉年华战场 " + fieldnumber + "(3v3)";
        } else if (event != null && (event.getProperty("started").equals("false"))) {
            var averagelevel = 0;
            for (i = 0; i < event.getPlayerCount(); i++) {
                averagelevel += event.getPlayers().get(i).getLevel();
            }
            averagelevel /= event.getPlayerCount();
            status = event.getPlayers().get(0).getParty().getLeader().getName() + "/" + event.getPlayerCount() + "队伍/平均等级 " + averagelevel;
        }
    }
    return status;
}
