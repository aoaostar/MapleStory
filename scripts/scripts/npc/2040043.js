/* ==================
 脚本类型:  NPC	    
 脚本作者：故事丶     
 联系方式：840645183  
 =====================
 */
//load("nashorn:mozilla_compat.js");
importPackage(net.sf.cherry.tools);
importPackage(net.sf.cherry.server.life);
importPackage(java.awt);

var status;
var partyLdr;
var chatState;
var party;
var preamble;

var stage8combos = Array(Array(0, 0, 0, 0, 1, 1, 1, 1, 1),
        Array(0, 0, 0, 1, 0, 1, 1, 1, 1),
        Array(0, 0, 0, 1, 1, 0, 1, 1, 1),
        Array(0, 0, 0, 1, 1, 1, 0, 1, 1),
        Array(0, 0, 0, 1, 1, 1, 1, 0, 1),
        Array(0, 0, 0, 1, 1, 1, 1, 1, 0),
        Array(0, 0, 1, 0, 0, 1, 1, 1, 1),
        Array(0, 0, 1, 0, 1, 0, 1, 1, 1),
        Array(0, 0, 1, 0, 1, 1, 0, 1, 1),
        Array(0, 0, 1, 0, 1, 1, 1, 0, 1),
        Array(0, 0, 1, 0, 1, 1, 1, 1, 0),
        Array(0, 0, 1, 1, 0, 0, 1, 1, 1),
        Array(0, 0, 1, 1, 0, 1, 0, 1, 1),
        Array(0, 0, 1, 1, 0, 1, 1, 0, 1),
        Array(0, 0, 1, 1, 0, 1, 1, 1, 0),
        Array(0, 0, 1, 1, 1, 0, 0, 1, 1),
        Array(0, 0, 1, 1, 1, 0, 1, 0, 1),
        Array(0, 0, 1, 1, 1, 0, 1, 1, 0),
        Array(0, 0, 1, 1, 1, 1, 0, 0, 1),
        Array(0, 0, 1, 1, 1, 1, 0, 1, 0),
        Array(0, 0, 1, 1, 1, 1, 1, 0, 0),
        Array(0, 1, 0, 0, 0, 1, 1, 1, 1),
        Array(0, 1, 0, 0, 1, 0, 1, 1, 1),
        Array(0, 1, 0, 0, 1, 1, 0, 1, 1),
        Array(0, 1, 0, 0, 1, 1, 1, 0, 1),
        Array(0, 1, 0, 0, 1, 1, 1, 1, 0),
        Array(0, 1, 0, 1, 0, 0, 1, 1, 1),
        Array(0, 1, 0, 1, 0, 1, 0, 1, 1),
        Array(0, 1, 0, 1, 0, 1, 1, 0, 1),
        Array(0, 1, 0, 1, 0, 1, 1, 1, 0),
        Array(0, 1, 0, 1, 1, 0, 0, 1, 1),
        Array(0, 1, 0, 1, 1, 0, 1, 0, 1),
        Array(0, 1, 0, 1, 1, 0, 1, 1, 0),
        Array(0, 1, 0, 1, 1, 1, 0, 0, 1),
        Array(0, 1, 0, 1, 1, 1, 0, 1, 0),
        Array(0, 1, 0, 1, 1, 1, 1, 0, 0),
        Array(0, 1, 1, 0, 0, 0, 1, 1, 1),
        Array(0, 1, 1, 0, 0, 1, 0, 1, 1),
        Array(0, 1, 1, 0, 0, 1, 1, 0, 1),
        Array(0, 1, 1, 0, 0, 1, 1, 1, 0),
        Array(0, 1, 1, 0, 1, 0, 0, 1, 1),
        Array(0, 1, 1, 0, 1, 0, 1, 0, 1),
        Array(0, 1, 1, 0, 1, 0, 1, 1, 0),
        Array(0, 1, 1, 0, 1, 1, 0, 0, 1),
        Array(0, 1, 1, 0, 1, 1, 0, 1, 0),
        Array(0, 1, 1, 0, 1, 1, 1, 0, 0),
        Array(0, 1, 1, 1, 0, 0, 0, 1, 1),
        Array(0, 1, 1, 1, 0, 0, 1, 0, 1),
        Array(0, 1, 1, 1, 0, 0, 1, 1, 0),
        Array(0, 1, 1, 1, 0, 1, 0, 0, 1),
        Array(0, 1, 1, 1, 0, 1, 0, 1, 0),
        Array(0, 1, 1, 1, 0, 1, 1, 0, 0),
        Array(0, 1, 1, 1, 1, 0, 0, 0, 1),
        Array(0, 1, 1, 1, 1, 0, 0, 1, 0),
        Array(0, 1, 1, 1, 1, 0, 1, 0, 0),
        Array(0, 1, 1, 1, 1, 1, 0, 0, 0),
        Array(1, 0, 0, 0, 0, 1, 1, 1, 1),
        Array(1, 0, 0, 0, 1, 0, 1, 1, 1),
        Array(1, 0, 0, 0, 1, 1, 0, 1, 1),
        Array(1, 0, 0, 0, 1, 1, 1, 0, 1),
        Array(1, 0, 0, 0, 1, 1, 1, 1, 0),
        Array(1, 0, 0, 1, 0, 0, 1, 1, 1),
        Array(1, 0, 0, 1, 0, 1, 0, 1, 1),
        Array(1, 0, 0, 1, 0, 1, 1, 0, 1),
        Array(1, 0, 0, 1, 0, 1, 1, 1, 0),
        Array(1, 0, 0, 1, 1, 0, 0, 1, 1),
        Array(1, 0, 0, 1, 1, 0, 1, 0, 1),
        Array(1, 0, 0, 1, 1, 0, 1, 1, 0),
        Array(1, 0, 0, 1, 1, 1, 0, 0, 1),
        Array(1, 0, 0, 1, 1, 1, 0, 1, 0),
        Array(1, 0, 0, 1, 1, 1, 1, 0, 0),
        Array(1, 0, 1, 0, 0, 0, 1, 1, 1),
        Array(1, 0, 1, 0, 0, 1, 0, 1, 1),
        Array(1, 0, 1, 0, 0, 1, 1, 0, 1),
        Array(1, 0, 1, 0, 0, 1, 1, 1, 0),
        Array(1, 0, 1, 0, 1, 0, 0, 1, 1),
        Array(1, 0, 1, 0, 1, 0, 1, 0, 1),
        Array(1, 0, 1, 0, 1, 0, 1, 1, 0),
        Array(1, 0, 1, 0, 1, 1, 0, 0, 1),
        Array(1, 0, 1, 0, 1, 1, 0, 1, 0),
        Array(1, 0, 1, 0, 1, 1, 1, 0, 0),
        Array(1, 0, 1, 1, 0, 0, 0, 1, 1),
        Array(1, 0, 1, 1, 0, 0, 1, 0, 1),
        Array(1, 0, 1, 1, 0, 0, 1, 1, 0),
        Array(1, 0, 1, 1, 0, 1, 0, 0, 1),
        Array(1, 0, 1, 1, 0, 1, 0, 1, 0),
        Array(1, 0, 1, 1, 0, 1, 1, 0, 0),
        Array(1, 0, 1, 1, 1, 0, 0, 0, 1),
        Array(1, 0, 1, 1, 1, 0, 0, 1, 0),
        Array(1, 0, 1, 1, 1, 0, 1, 0, 0),
        Array(1, 0, 1, 1, 1, 1, 0, 0, 0),
        Array(1, 1, 0, 0, 0, 0, 1, 1, 1),
        Array(1, 1, 0, 0, 0, 1, 0, 1, 1),
        Array(1, 1, 0, 0, 0, 1, 1, 0, 1),
        Array(1, 1, 0, 0, 0, 1, 1, 1, 0),
        Array(1, 1, 0, 0, 1, 0, 0, 1, 1),
        Array(1, 1, 0, 0, 1, 0, 1, 0, 1),
        Array(1, 1, 0, 0, 1, 0, 1, 1, 0),
        Array(1, 1, 0, 0, 1, 1, 0, 0, 1),
        Array(1, 1, 0, 0, 1, 1, 0, 1, 0),
        Array(1, 1, 0, 0, 1, 1, 1, 0, 0),
        Array(1, 1, 0, 1, 0, 0, 0, 1, 1),
        Array(1, 1, 0, 1, 0, 0, 1, 0, 1),
        Array(1, 1, 0, 1, 0, 0, 1, 1, 0),
        Array(1, 1, 0, 1, 0, 1, 0, 0, 1),
        Array(1, 1, 0, 1, 0, 1, 0, 1, 0),
        Array(1, 1, 0, 1, 0, 1, 1, 0, 0),
        Array(1, 1, 0, 1, 1, 0, 0, 0, 1),
        Array(1, 1, 0, 1, 1, 0, 0, 1, 0),
        Array(1, 1, 0, 1, 1, 0, 1, 0, 0),
        Array(1, 1, 0, 1, 1, 1, 0, 0, 0),
        Array(1, 1, 1, 0, 0, 0, 0, 1, 1),
        Array(1, 1, 1, 0, 0, 0, 1, 0, 1),
        Array(1, 1, 1, 0, 0, 0, 1, 1, 0),
        Array(1, 1, 1, 0, 0, 1, 0, 0, 1),
        Array(1, 1, 1, 0, 0, 1, 0, 1, 0),
        Array(1, 1, 1, 0, 0, 1, 1, 0, 0),
        Array(1, 1, 1, 0, 1, 0, 0, 0, 1),
        Array(1, 1, 1, 0, 1, 0, 0, 1, 0),
        Array(1, 1, 1, 0, 1, 0, 1, 0, 0),
        Array(1, 1, 1, 0, 1, 1, 0, 0, 0),
        Array(1, 1, 1, 1, 0, 0, 0, 0, 1),
        Array(1, 1, 1, 1, 0, 0, 0, 1, 0),
        Array(1, 1, 1, 1, 0, 0, 1, 0, 0),
        Array(1, 1, 1, 1, 0, 1, 0, 0, 0),
        Array(1, 1, 1, 1, 1, 0, 0, 0, 0));

function start() {
    status = -1;
    playerStatus = cm.isLeader();
    preamble = null;
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
            status--;
        }
        boxStage(cm);
    }
}

/*function clear(stage, eim, cm) {
 eim.setProperty("8stageclear","true");
 var packetef = MaplePacketCreator.showEffect("quest/party/clear");
 var packetsnd = MaplePacketCreator.playSound("Party1/Clear");
 var packetglow = MaplePacketCreator.environmentChange("gate",2);
 var map = eim.getMapInstance(cm.getChar().getMapId());
 map.broadcastMessage(packetef);
 map.broadcastMessage(packetsnd);
 map.broadcastMessage(packetglow);
 var mf = eim.getMapFactory();
 map = mf.getMap(922010100 + stage * 100);
 cm.givePartyExp(30000, party);
 }*/
function clear(stage, eim, cm) {
    eim.setProperty("8stageclear", "true");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
    var map = eim.getMapInstance(cm.getPlayer().getMapId());
    cm.givePartyExp(5000, party);
}

function failstage(eim, cm) {
    cm.showEffect(true, "quest/party/wrong_kor");
    cm.playSound(true, "Party1/Failed");
    var map = eim.getMapInstance(cm.getPlayer().getMapId());
}

function boxStage(cm) {
    var debug = false;
    var eim = cm.getPlayer().getEventInstance();
    var nthtext = "eighth";
    var nthobj = "boxes";
    var nthverb = "stand";
    var nthpos = "stand too close to the edges";
    var curcombo = stage8combos;
    var currect = cm.getPlayer().getMap().getAreas();
    var objset = [0, 0, 0, 0, 0, 0, 0, 0, 0];
    if (playerStatus) { // leader
        if (status == 0) {
            party = eim.getPlayers();
            preamble = eim.getProperty("leader" + nthtext + "preamble");
            if (preamble == null) {
                cm.sendNext("欢迎来到 玩具之城 - (#r组队任务#k)第#b8#k阶段\r\n\r\n看到我旁边的#b数字箱子#k了嘛？我需要你们#r5个组队成员#k站在箱子上面帮我找到正确的答案……祝你们好运。");
                eim.setProperty("leader" + nthtext + "preamble", "done");
                var sequenceNum = Math.floor(Math.random() * curcombo.length);
                eim.setProperty("stage" + nthtext + "combo", sequenceNum.toString());
                cm.dispose();
            } else { // otherwise, check for stage completed
                var complete = eim.getProperty("8stageclear");
                if (complete != null) {
                    var mapClear = "8stageclear";
                    eim.setProperty(mapClear, "true"); // Just to be sure
                    cm.sendNext("恭喜你们通过了第8阶段。现在通过传送门到达下一个阶段吧……");
                } else {
                    var totplayers = 0;
                    for (i = 0; i < objset.length; i++) {
                        for (j = 0; j < party.size(); j++) {
                            var present = currect.get(i).contains(party.get(j).getPosition());
                            if (present) {
                                objset[i] = objset[i] + 1;
                                totplayers = totplayers + 1;
                            }
                        }
                    }
                    var numSpawn = 5;
                    if (totplayers == 5 || debug) {
                        var combo = curcombo[parseInt(eim.getProperty("stage" + nthtext + "combo"))];
                        var testcombo = true;
                        for (i = 0; i < objset.length; i++) {
                            if (combo[i] != objset[i]) {
                                testcombo = false;
                            }
                        }
                        if (testcombo || debug) {
                            clear(8, eim, cm);
                            if (cm.getPlayer().getEventInstance().getProperty("s8start") != null)
                            {
                                var starts4Time = cm.getPlayer().getEventInstance().getProperty("s8start");
                                var nowTime = new java.util.Date().getTime();
                                if ((nowTime - starts4Time) < 90000)
                                    cm.getPlayer().getEventInstance().setProperty("s8achievement", "true"); 
                            }
                            cm.dispose();
                        } else {
                            failstage(eim, cm);
                            cm.dispose();
                        }
                    } else {
                        if (debug) {
                            var outstring = "Objects contain:"
                            for (i = 0; i < objset.length; i++) {
                                outstring += "\r\n" + (i + 1).toString() + ". " + objset[i].toString();
                            }
                            cm.sendNext(outstring);
                            var combo = curcombo[parseInt(eim.getProperty("stage" + nthtext + "combo"))];
                        } else {
                            cm.sendNext("答案错误。\r\n\r\n提示 - 看到我旁边的数字箱子了嘛？我需要你们#b5个队伍成员#k站在箱子上面帮我找到正确的坐标答案。");
                            clear(8, eim, cm);
                            cm.dispose();
                        }
                    }
                }
            } 
        } else {
            cm.dispose();
        }
    } else { 
        if (status == 0) {
            var complete = eim.getProperty("8stageclear");
            if (complete != null) {
                cm.sendNext("恭喜你们通过了第8阶段。现在通过传送门到达下一个阶段吧……");
                cm.dispose();
            } else {
                cm.sendNext("恭喜你们通过了第8阶段。现在通过传送门到达下一个阶段吧……");
                cm.dispose();
            }
        } else {
            var complete = eim.getProperty("8stageclear");
            if (complete != null) {
                cm.sendNext("恭喜你们通过了第8阶段。现在通过传送门到达下一个阶段吧……");
                cm.dispose();
            }
            cm.dispose();
        }
    }
}