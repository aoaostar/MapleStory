var status = 0;
var minLevel = 10;
var maxLevel = 255;
var minPartySize = 1;
var maxPartySize = 7;

function start() {
    status = -1;
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
        if (mode == 1) status++;
        else status--;
        if (status == 0) {
            cm.sendSimple("#e<组队任务：月妙组队任务>#n\r\n你想和队员们一起努力，完成任务吗，2人即可进入？副本通关需求20个年糕，其余的年糕要给组员拿出副本分享哦~！给队长会都被小虎收走哦！如果想挑战的话，请让#b所属组队的队长#k来和我说话。\r\n#b#L0#我想执行组队任务。#l\r\n#L1#我想听一下说明。#l\r\n#L2#50个年糕兑换一个#v1002798#4维属性+8.HP+100#l")
        } else if (status == 1) {
            if (selection == 0) {
                if (cm.getParty() == null) { // 没有组队
                    cm.sendOk("请组队后和我谈话。");
                    cm.dispose();
                } else if (!cm.isLeader()) { // 不是队长
                    cm.sendOk("请叫队长和我谈话。");
                    cm.dispose();
                    /*} else if (cm.getBossLog('LUDI') >= 6){
				cm.sendOk("对不起，一天只能进入6次。")
				cm.dispose();*/
                } else {
					cm.givePartyItems(4001095,-1,true);
					cm.givePartyItems(4001096,-1,true);
					cm.givePartyItems(4001097,-1,true);
					cm.givePartyItems(4001098,-1,true);
					cm.givePartyItems(4001099,-1,true);
					cm.givePartyItems(4001100,-1,true);
                    var party = cm.getParty().getMembers();
                    var mapId = cm.getPlayer().getMapId();
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
                            inMap += 1;
                        }
                    }
                    if (party.size() < minPartySize || party.size() > maxPartySize || inMap < minPartySize) {
                        next = false;
                    }
                    if (next) {
                        var em = cm.getEventManager("HenesysPQ");
                        if (em == null) {
                            cm.sendOk("此任务正在建设当中。");
                        } else {
                            var prop = em.getProperty("state");
                            if (prop.equals("0") || prop == null) {
                                em.startInstance(cm.getParty(), cm.getMap());
			cm.喇叭(2, "[" + cm.getPlayer().getName() + "]开始带领他的队伍挑战【月妙副本】，让我们祝福他们！！");	
                                cm.removeAll(4001022);
                                cm.removeAll(4001023);
                                cm.dispose();
                                return;
                            } else {
                                cm.sendOk("任务正在进行中...请稍等!");
                            }
                        }
                        cm.dispose();
                    } else {
                        cm.sendOk("请确认你的组队员：\r\n\r\n#b1、组队员必须要" + minPartySize + "人以上，" + maxPartySize + "人以下。\r\n2、组队员等级必须要在" + minLevel + "级以上" + maxLevel + "级以下。\r\n\r\n（#r如果仍然错误, 重新下线,再登陆 或者请重新组队。#k#b）");
                        cm.dispose();
                    }
                } //判断组队
            } else if (selection == 1) {
                cm.sendOk("请确认你的组队员：\r\n\r\n#b1、组队员必须要" + minPartySize + "人以上，" + maxPartySize + "人以下。\r\n2、组队员等级必须要在" + minLevel + "级以上" + maxLevel + "级以下。\r\n\r\n（#r如果仍然错误, 重新下线,再登陆 或者请重新组队。#k#b）");
                cm.dispose();
            } else if (selection == 2) {
				if(cm.haveItem(4001101,50)){
					cm.gainItem(4001101,-50);
					cm.gainItem(1002798,8,8,8,8,100,100,0,0,20,20,0,0,0,0);
					cm.worldMessage(6,"["+cm.getName()+"]使用50个年糕在月妙NPC兑换了【头顶年糕】帽子！");
cm.喇叭(3, "[" + cm.getPlayer().getName() + "]使用50个年糕在月妙NPC兑换了【头顶年糕】帽子！");
                cm.dispose();
				}else{
                cm.sendOk("年糕不足50个无法兑换！");
                cm.dispose();
				}
            }
        }
}
}