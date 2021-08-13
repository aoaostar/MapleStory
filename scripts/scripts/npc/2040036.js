/* ==================
 脚本类型:  NPC	    
 脚本作者：故事丶     
 联系方式：840645183  
 =====================
 */
//load("nashorn:mozilla_compat.js");
importPackage(net.sf.cherry.tools);
var status;
var exp = 23000;

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
        var stage1status = eim.getProperty("stage1status");
        if (stage1status == null) {
            if (playerStatus) {
                var map = eim.getMapInstance(cm.getPlayer().getMapId());
                var passes = cm.haveItem(4001022, 5);
                var stage1leader = eim.getProperty("stage1leader");
                if (stage1leader == "done") {
                    if (passes) {
                        party = eim.getPlayers();
                        map = cm.getMapId();
                        cm.removeAll(4001022);
                        clear(1, eim, cm);
                        cm.givePartyExp(exp, party);
                        cm.sendOk("恭喜你们通过了第1阶段。现在通过传送门到达下一个阶段吧……");
                        cm.dispose();
                    } else {
                        cm.sendNext("你确定给我带来了25张#i4001022#？请检查一下自己的背包是否足够。");
                    }
                    cm.dispose();
                } else {
                    cm.sendOk("欢迎来到 玩具之城 - (#r组队任务#k)第#b1#k阶段\r\n\r\n看看周围是不是有很多老鼠？请你和你的队员一起消灭这些老鼠带来#r15张通行证#k给我……");
                    eim.setProperty("stage1leader", "done");
                    cm.dispose();
                }
            } else {
                cm.sendNext("欢迎来到 玩具之城 - (#r组队任务#k)第#b1#k阶段\r\n\r\n看看周围是不是有很多老鼠？请打败这些老鼠带来#r15张通行证#k给我,如果你成功拿到了通行证请交给你们的组长。然后再请他转交给我……");
                cm.dispose();
            }
        } else {
            cm.sendNext("恭喜你们通过了第1阶段。现在通过传送门到达下一个阶段吧……");
            cm.dispose();
        }
    }
}
function clear(stage, eim, cm) {
	eim.setProperty("stage" + stage.toString() + "status","clear");
    cm.showEffect(true, "quest/party/clear");
    cm.playSound(true, "Party1/Clear");
    cm.environmentChange(true, "gate");
	var map = eim.getMapInstance(cm.getChar().getMapId());
	var mf = eim.getMapFactory();
	map = mf.getMap(922010100);
	var nextStage = eim.getMapInstance(922010200);
	var portal = nextStage.getPortal("next00");
	if (portal != null) {
		portal.setScriptName("lpq2");
	}
}