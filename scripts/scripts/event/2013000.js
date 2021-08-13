var status = -1;
var minLevel = 70; // 35
var maxLevel = 255; // 65

var minPartySize = 3;
var maxPartySize = 6;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
            return;
        }
        status--;
    }
    if (cm.getMapId() == 920010000) { //inside orbis pq
        cm.sendOk("We have to save Chamberlain Eak! Restore the 20 Cloud Pieces!");
        cm.dispose();
        return;
    }
    if (status == 0) {
        for (var i = 4001044; i < 4001064; i++) {
            cm.removeAll(i); //holy
        }
        if (cm.getParty() == null) { // No Party
            cm.sendSimple("你想参加女神组队副本么？\r\n\r\n#r进入条件最低: " + minPartySize + " 个人, 等级要求最低 " + minLevel + " 最高等级 " + maxLevel + ".#b\r\n#L0#我想参加女神副本.#l\r\n#L1#我想兑换女神物品.#l");
        } else if (!cm.isLeader()) { // Not Party Leader
            cm.sendSimple("If you want to try the quest, please tell the #bleader of your party#k to talk to me.#b\r\n#L0#我想参加女神副本.#l\r\n#L1#我想兑换女神物品.#l");
        } else {
            // Check if all party members are within PQ levels
            var party = cm.getParty().getMembers();
            var mapId = cm.getMapId();
            var next = true;
            var levelValid = 0;
            var inMap = 0;
            var it = party.iterator();

            while (it.hasNext()) {
                var cPlayer = it.next();
                if ((cPlayer.getLevel() >= minLevel) && (cPlayer.getLevel() <= maxLevel)) {
                    levelValid += 1;
                } else {
                    next = false;
                }
                if (cPlayer.getMapid() == mapId) {
                    inMap += (cPlayer.getJobId() == 900 ? 6 : 1);
                }
            }
            if (party.size() > maxPartySize || inMap < minPartySize) {
                next = false;
            }
            if (next) {
                var em = cm.getEventManager("OrbisPQ");
                if (em == null) {
                    cm.sendSimple("你想参加女神组队副本么？.#b\r\n#L0#我想参加女神副本.#l\r\n#L1#我想兑换女神物品.#l");
                } else {
                    var prop = em.getProperty("state");
                    if (prop.equals("0") || prop == null) {
                        em.startInstance(cm.getParty(), cm.getMap(), 120);
                        cm.dispose();
                        return;
                    } else {
            cm.sendSimple("你想参加女神组队副本么？\r\n\r\n#r进入条件最低: " + minPartySize + " 个人, 等级要求最低 " + minLevel + " 最高等级 " + maxLevel + ".#b\r\n#L0#我想参加女神副本.#l\r\n#L1#我想兑换女神物品.#l");
                    }
                }
            } else {
            cm.sendSimple("你想参加女神组队副本么？\r\n\r\n#r进入条件最低: " + minPartySize + " 个人, 等级要求最低 " + minLevel + " 最高等级 " + maxLevel + ".#b\r\n#L0#我想参加女神副本.#l\r\n#L1#我想兑换女神物品.#l");
            }
        }
    } else { //broken glass
        if (selection == 0) {
            if (!cm.isGMS()) { //TODO Jump
                cm.sendOk("你不满足进入条件.");
            } else if (cm.haveItem(1082232, 1)) {
                if (!cm.canHold(1082322, 1)) {
                    cm.sendOk("Make room for this Wristband.");
                } else if (cm.haveItem(4001158, 10)) {
                    cm.gainItem(1082322, 1);
                    cm.gainItem(4001158, -10);
                } else {
                    cm.sendOk("Come back when you have done 10 #t4001158#.");
                }
            } else if (!cm.canHold(1082232, 1)) {
                cm.sendOk("Make room for this Wristband.");
            } else if (cm.haveItem(4001158, 10)) {
                cm.gainItem(1082232, 1);
                cm.gainItem(4001158, -10);
            } else {
                cm.sendOk("Come back when you have done 10 #t4001158#.");
            }
        } else if (selection == 1) {
            if (!cm.isGMS()) { //TODO Jump
                cm.sendOk("你不满足兑换条件.");
            } else if (cm.haveItem(1072455, 1)) {
                if (!cm.canHold(1072534, 1)) {
                    cm.sendOk("Make room for this shoe.");
                } else if (cm.haveItem(4001158, 10)) {
                    cm.gainItem(1072534, 1);
                    cm.gainItem(4001158, -10);
                } else {
                    cm.sendOk("Come back when you have done 10 #t4001158#.");
                }
            } else if (!cm.canHold(1072455, 1)) {
                cm.sendOk("Make room for this shoe.");
            } else if (cm.haveItem(4001158, 10)) {
                cm.gainItem(1072455, 1);
                cm.gainItem(4001158, -10);
            } else {
                cm.sendOk("Come back when you have done 10 #t4001158#.");
            }
        }
        cm.dispose();
    }
}
