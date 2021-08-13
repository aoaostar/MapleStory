/* ==================
 脚本类型:  NPC	    
 脚本作者：故事丶     
 联系方式：840645183  
 =====================
 */
//load("nashorn:mozilla_compat.js");
importPackage(net.sf.cherry.tools);
var status;
var exp = 30000;

function start() {
    status = -1;
    playerStatus = cm.isLeader();
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        var eim = cm.getPlayer().getEventInstance();
        var stage4status = eim.getProperty("stage4status");
        if (stage4status == null) {
            if (playerStatus) {
                var map = eim.getMapInstance(cm.getPlayer().getMapId());
                var passes = cm.haveItem(4001022, 6);
                var stage4leader = eim.getProperty("stage4leader");
                if (stage4leader == "done") {
                    if (passes) {
                        party = eim.getPlayers();
                        map = cm.getMapId();
						cm.gainItem(40001022,-6)
                        cm.removeAll(4001022);
                        clear(4, eim, cm);
                        cm.givePartyExp(exp, party);
                        cm.sendOk("恭喜你们通过了第4阶段。现在通过传送门到达下一个阶段吧……");
                        cm.dispose();
                    } else {
                        cm.sendNext("#k你确定给我带来了#r6张通行证？#k请检查一下自己的背包是否足够。");
                    }
                    cm.dispose();
                } else {
                    cm.sendOk("欢迎来到 玩具之城 - (#r组队任务#k)第#b4#k阶段\r\n\r\n到处走走看看。请你和你的队员一起带来#r6张通行证#k给我……");
                    eim.setProperty("stage4leader", "done");
                    cm.dispose();
                }
            } else {
                cm.sendNext("欢迎来到 玩具之城 - (#r组队任务#k)第#b4#k阶段\r\n\r\n到处走走看看。带来#r1张通行证#k给我,如果你成功拿到了通行证请交给你们的组长。然后再请他转交给我……");
                cm.dispose();
            }
        } else {
            cm.sendNext("恭喜你们通过了第4阶段。现在通过传送门到达下一个阶段吧……");
            cm.dispose();
        }
    }
}
function clear(stage, eim, cm) {
    eim.setProperty("stage" + stage.toString() + "status", "clear");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
    var map = eim.getMapInstance(cm.getPlayer().getMapId());
    var mf = eim.getMapFactory();
    map = mf.getMap(922010400);
    var nextStage = eim.getMapInstance(922010500);
    var portal = nextStage.getPortal("next00");
    if (portal != null) {
        portal.setScriptName("lpq5");
    }
}