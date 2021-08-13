importPackage(Packages.client);
importPackage(Packages.client.inventory);

var status = -1;



function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    
    if (mode == 1) {
        status++;
    } else if (mode == 0 && status != 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
		    var text = "\t\t\t#e欢迎来到#r师徒系统#k!#n\r\n";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			text += "你需要建立师徒关系吗？\r\n"
			text += "#L1#我要建立师徒关系#l			#L6##b师徒系统说明#k\r\n"
			text += "#L2#带徒入门(PS：师傅一定不能是队长)#l	\r\n"
            text += "#L3#带徒出师(PS：师傅一定不能是队长)#l\r\n"
            text += "#L4#逐徒出师#l   #L5#退出师门#l     #L7##r★徒弟拜师后点我#l\r\n\r\n"
            cm.sendSimple(text);
    } else if (status == 1){
		if (selection == 1){
			var id = cm.getPlayer().getId();
			if (cm.getPlayer().getLevel() < 180){
				cm.sendOk("你的等级不够180级");
				cm.dispose();
				return;
			}else if(cm.getOneTimeLog("师傅") < 1){
				cm.setOneTimeLog("师傅");
				//cm.师门出师();
				cm.sendOk("你成功建立了师门，赶紧去收徒吧！");
				cm.worldMessage(6,"【师徒系统】[" + cm.getChar().getName() + "]成功建立了师门，要找师傅的赶紧了！");
				cm.dispose();
			} else {
				cm.sendOk("你已经建立过师门了！去收徒吧！");
				cm.dispose();
			}
			
        } else if (selection == 2){
			if (cm.getParty() == null) {
                cm.sendNext("请组队后在来找我！");
                cm.dispose();
                return;
            } else if (cm.allMembersHere() == false){
				cm.sendOk("徒弟或者师傅不在这个地图啊？");
				cm.dispose();
				return;
			} else if (cm.getOneTimeLog("师傅") < 1) {
                cm.sendNext("请让师傅找我对话(PS：师傅一定要是队员)");
                cm.dispose();
                return;
			} else if (cm.isLeader()) {
                cm.sendNext("请让师傅找我对话(PS：师傅一定要是队员)");
                cm.dispose();
                return;
			}
var next = true;
var mapId = cm.getPlayer().getMapId();
var party = cm.getPlayer().getParty().getMembers();
var it = party.iterator();
var cPlayer = it.next();
var victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
var TDid = victim.getId();
var a1_1 = cm.getOneTimeLog(TDid);//自己的收徒记录ID
var shoutu = victim.getOneTimeLog(TDid);
var chushi = victim.getOneTimeLog("出师");
var id = cm.getPlayer().getId();
var tcsm = victim.getOneTimeLog("退出师门");
var jrsm = victim.getOneTimeLog(TDid);
			if (victim.getLevel() > 50){
				cm.sendOk("准徒弟等级是否已经大于50级了？！");
				cm.dispose();
				return;
			} else if (a1_1 - jrsm - tcsm > 0){
				cm.sendOk("你已经收过这个徒弟了");
				cm.dispose();
				return;
			} else if (shoutu - tcsm != 0 ){
				cm.sendOk("你徒弟已经有师门了");
				cm.dispose();
			} else if (cm.getOneTimeLog("师傅") < 1){
				cm.sendOk("你还没建立师门呢");
				cm.dispose();				
			} else if (cm.getPlayer().getParty().getMembers().size() > 2){
				cm.sendOk("每次只能带一个徒弟入门（请2人组队）");
				cm.dispose();
				return;
			} else if (cm.getOneTimeLog("收徒") - cm.getOneTimeLog("出师") - tcsm >= 6){
				cm.sendOk("你的师门已经收过6个徒弟了");
				cm.dispose();
				return;
			} else {
					cm.setOneTimeLog("收徒");
					cm.givePartyBossLog("收徒");
					cm.setOneTimeLog(TDid);
					cm.sendOk("你成功收了"+victim.getName()+"为徒弟，请提醒徒弟点我确认关系，之后才能生效！");
					Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(11,cm.getC().getChannel(),"师徒系统" + " : ["+ cm.getPlayer().getName() +"]收了["+victim.getName()+"]为徒弟，请徒弟进行确认！",true).getBytes());
					cm.dispose();
					return;
					}
			
		} else if (selection == 3){
			if (cm.getParty() == null) {
                		cm.sendNext("组队后在来找我");
                		cm.dispose();
                		return;
            }else if (cm.getOneTimeLog("师傅") < 1){
				cm.sendOk("你还没建立师门呢");
				cm.dispose();
				return;
			}
			var next = true;
			var gender = cm.getPlayer().getGender();
			var mapId = cm.getPlayer().getMapId();
			var party = cm.getPlayer().getParty().getMembers();
			var it = party.iterator();
			var cPlayer = it.next();
			var victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			var TDid = victim.getId();
			if (cm.isLeader()) {
                		cm.sendNext("请让师傅找我对话(PS：师傅一定要是队员)");
                		cm.dispose();
                		return;
			} else if (victim.getLevel() < 140){
				cm.sendOk("你的徒弟等级不够140级，不能出师！");
				cm.dispose();
			} else if (cm.getOneTimeLog("师傅") < 1){
				cm.sendOk("你还没建立师门呢");
				cm.dispose();
				return;
           	} else if (cm.getOneTimeLog(TDid) == 0){
				cm.sendOk("你确定这是你徒弟吗？？？");
				cm.dispose();
				return;
			} else if (cm.getOneTimeLog(-TDid) == 1){
				cm.sendOk("这个徒弟已经出师了！");
				cm.dispose();
				return;
			} else {
				cm.setOneTimeLog("出师");
				cm.setOneTimeLog(-TDid);
			
				//cm.gainItem(4310098,10);//10低贝
				//cm.gainItem(4310097,10);//10贝勒
				//cm.gainItem(3992025,2);//圣诞大星
				//cm.gainItem(4310059,5);//必成币
				cm.gainMeso(200000000);//；金币
				cm.gainNX(20000);//点卷
				cm.gainItem(2340000,10);//祝福
				cm.gainItem(2049116,10);//混沌 	
                cm.gainItem(4251202,1);//五彩
                cm.gainItem(2002031,2);//草莓蛋糕				
				//victim.modifyCSPoints(1,20000);//队友获得
				cm.sendOk("你带徒"+victim.getName()+"出师成功!\r\n师傅获得：2W点卷、#v2340000#*10 #v2049116#*10 #v4251202#*1 #v2002031#*2。");
				Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(11,cm.getC().getChannel(),"师徒系统" + " : ["+ cm.getPlayer().getName() +"]玩家成功带徒出师["+victim.getName()+"]获得重磅礼包！",true).getBytes());
				cm.dispose();
			}
		} else if (selection == 4){
			cm.sendOk("暂不支持此功能");
			cm.dispose();
			//cm.openNpc(9900004,153);
		} else if (selection == 5){
			var id = cm.getPlayer().getId();
			var tcsm = cm.getOneTimeLog("退出师门");
			var jrsm = cm.getOneTimeLog(id);
			if (jrsm == 0){;
				cm.sendOk("退出师门失败！\r\n你没并没有加入过师门!（注：师傅不可以取消师门）");
				cm.dispose();
			} else if (jrsm - tcsm == 0){
				cm.sendOk("你已经没有师门可以退出了 ，不必再次退出！");
				cm.dispose();
			} else {
				cm.setOneTimeLog("退出师门");
				cm.sendOk("退出师门成功!");
				cm.dispose();
			}
		} else if (selection == 6){
			cm.sendOk("师门系统介绍：\r\n徒弟等级：必须小于50级\r\n师父等于：必须180级以上\r\n\r\n徒弟达到140级即可出师！\r\n\r\n师父出师获得：2W点卷、#v2340000#*10 #v2049116#*10 #v4251202#*1 #v2002031#*2\r\n\r\n\r\n");
			cm.dispose();
		} else if (selection == 7){
			var id = cm.getPlayer().getId();
			if (cm.getPlayer().getLevel() >= 200){
				cm.sendOk("师傅不需要确认！");
				cm.dispose();
				return;
			} else if (cm.getBossLog("收徒") >= 1 && cm.getOneTimeLog(id) == 0){
				cm.setOneTimeLog(id);
				cm.sendOk("师徒关系确认成功！");
				Packages.handling.world.World.Broadcast.broadcastMessage(Packages.tools.MaplePacketCreator.serverNotice(11,cm.getC().getChannel(),"师徒系统" + " : ["+ cm.getPlayer().getName() +"]师徒关系确认成功。",true).getBytes());
				cm.dispose();
			} else {
				cm.sendOk("你已经确认过了。或者\r\n");
				cm.dispose();
			}
		}
		
    }
	
}
