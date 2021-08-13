var status;
var partyLdr;
var chatState;
var party;
var preamble = null;
var stage8question;
var stage8answer;
if(preamble == null){
var random = java.lang.Math.floor(Math.random() * 4 + 1);
}
if(random == 1){
stage8question = Array("10+5=?", "10+5=?");
stage8answer = Array(Array(1, 0, 0, 0,1), Array(1, 0, 0, 0,1));
} else if(random == 2){
stage8question = Array("20+4=?", "20+4=?");
stage8answer = Array(Array(0, 1, 0, 1,0), Array(0, 1, 0, 1,0));
} else if(random == 3){
stage8question = Array("30+5=?", "30+5=?");
stage8answer = Array(Array(0, 0, 1, 0,1), Array(0, 0, 1, 0,1));
} else if(random == 4){
stage8question = Array("10+3=?", "10+3=?");
stage8answer = Array(Array(1, 0, 1, 0,0), Array(1, 0, 1, 0,0));
}

function start() {
    status = -1;
    //preamble = null;
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

function clear(stage, eim, cm) {
    eim.setProperty("3stageclear", "true");

    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
    cm.givePartyExp(5040, eim.getPlayers());
    // stage 9 does not have a door, might be cause of DC error
}

function failstage(eim, cm) {
    cm.showEffect(true, "quest/party/wrong_kor");
    cm.playSound(true, "Party1/Failed");
}

function boxStage(cm) {
    var debug = false;
    var eim = cm.getEventInstance();
    var nthtext = "eighth";
    var nthobj = "boxes";
    var nthverb = "stand";
    var nthpos = "stand too close to the edges";
    var curcombo = stage8answer;
    var currect = cm.getMap().getAreas();
    var objset = [0, 0, 0, 0,0];

    if (cm.isLeader()) { // leader
        if (status == 0) {
            party = eim.getPlayers();
            preamble = eim.getProperty("leader" + nthtext + "preamble");
            if (preamble == null) {
                cm.sendNext("你好。欢迎来到第3阶段，在我旁边，有5个木桩。需要2个队友站在木桩上，队长在来点击我，看是否正确。祝你好运！");
                eim.setProperty("leader" + nthtext + "preamble", "done");
                var as = Math.floor(Math.random() * stage8answer.length);
                eim.setProperty("stage" + nthtext + "combo", as);
                cm.getMap().startSimpleMapEffect(stage8question[as], 5120018);
                cm.dispose();
            } else { // otherwise, check for stage completed
                var complete = eim.getProperty("3stageclear");
                if (complete != null) {
                    var mapClear = "3stageclear";
                    eim.setProperty(mapClear, "true"); // Just to be sure
		    //cm.warpParty(910340300);
                    cm.sendNext("恭喜你！ 你成功通过了第3阶段！快点，向第4阶段前进吧！");
                } else {
                    var totplayers = 0;
                    for (i = 0; i < objset.length; i++) {
                        var present = cm.getMap().getNumPlayersItemsInArea(i);
                        if (present != 0) {
                            objset[i] = objset[i] + 1;
                            totplayers = totplayers + 1;
                        }
                    }
                    if (totplayers == 2 || debug) {
                        var combo = curcombo[parseInt(eim.getProperty("stage" + nthtext + "combo"))];
                        var testcombo = true;
                        for (i = 0; i < objset.length; i++) {
                            if (combo[i] != objset[i]) {
                                testcombo = false;
                            }
                        }
                        if (testcombo || debug) {
                            clear(8, eim, cm);
                            if (cm.getEventInstance().getProperty("s8start") != null) {
                                var starts4Time = cm.getEventInstance().getProperty("s8start");
                                var nowTime = new Date().getTime();
                                if ((nowTime - starts4Time) < 90000) cm.getEventInstance().setProperty("s8achievement", "true"); // give via portal script.
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
                            cm.sendNext("需要 2名队员站在木桩上 组成不同的组合. 只允许 2名队员站在木桩上, 请记住你们已经使用过的错误组合,方便快速通关.");
                            cm.dispose();
                        }
                    }
                }
            }
        } else {
            cm.dispose();
        }
    } else { // not leader
        if (status == 0) {
                cm.sendNext("恭喜你！ 你成功通过了第3阶段！快点，向第4阶段前进吧！");
                cm.dispose();
        } else {
                cm.sendNext("恭喜你！ 你成功通过了第3阶段！快点，向第4阶段前进吧！");
                cm.dispose();
        }
    }
}
