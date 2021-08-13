/*
 少林妖僧 -- 入口NPC
 */

var shaoling = 2;
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
	//	if (cm.getPlayer().getClient().getChannel() != 1) {
		//	cm.sendOk("妖僧只有在频道 #r1#k 可以挑战");
		//	cm.dispose();
		//	return;
	//    }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("#b亲爱的 #k#h  ##e\r\n#b是否要挑战武陵妖僧副本？100级可进#k \r\n\r\n\r\n  #L0##r我要挑战武陵妖僧#k#l\r\n\r\n\r\n ");//#L0##r我要挑战武陵妖僧#k#l
        } else if (status == 1) {
            if (selection == 0) {
                var pt = cm.getPlayer().getParty();
                if ( cm.getQuestStatus(8534) != 0 ) {
                    cm.sendOk("妖僧暂时关闭维修！");
                    cm.dispose();
             //   } else if (cm.getBossLog('shaoling') >2) {
                   // cm.sendOk("每天只能打3次妖僧！");
                  //  cm.dispose();
				} else if (cm.getLevel() < 100 ) {  
				cm.sendOk("本地图限制等级100级。您的能力没有资格挑战妖僧");
                cm.dispose();
                } else if (cm.getParty() == null) {
                    cm.sendOk("请组队再来找我....");
                    cm.dispose();
                } else if (!cm.isLeader()) {
                    cm.sendOk("请叫你的队长来找我!");
                    cm.dispose();
                } else if (pt.getMembers().size() < 1) {
                    cm.sendOk("需要 1 人以上的组队才能进入！!");
                    cm.dispose();
                } else {
                    var party = cm.getParty().getMembers();
                    var mapId = cm.getMapId();
                    var next = true;
                    var levelValid = 0;
                    var inMap = 0;

                    var it = party.iterator();
                    while (it.hasNext()) {
                        var cPlayer = it.next();
                        if ((cPlayer.getLevel() >= 135 && cPlayer.getLevel() <=255 ) || cPlayer.getJobId() == 000) {
                            levelValid += 1;
                        } else {
                            next = false;
                        }
                        if (cPlayer.getMapid() == mapId) {
                            inMap += (cPlayer.getJobId() == 900 ? 3 : 1);
                        }
                    }
                    if ( inMap < 1) {
                        next = false;
                    }
                    if (next) {
                        var em = cm.getEventManager("shaoling");
                        if (em == null) {
                            cm.sendOk("当前副本有问题，请联络管理员....");
                        } else {
                            var prop = em.getProperty("state");
                            if (prop.equals("0") || prop == null) {
                                em.startInstance(cm.getParty(), cm.getMap());
                                cm.setBossLog("shaoling");
                                cm.dispose();
                                return;
                            } else {
                                cm.sendOk("里面已经有人在挑战了，你可以排队等候一下。...");
                            }
                        }
                    } else {
                        cm.sendOk("等级尚未达到 #r135#k ");
                    }
                }
                cm.dispose();
            }
        }
    }
}
