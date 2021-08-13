/* ==================
 脚本类型:  NPC	    
 脚本作者：江浩     
 联系方式：782772124  
 =====================
 */

var status = 0;
//副本名称
var fbmc = "玩具之城 - (组队任务)";
//副本配置文件
var eventname = "LudiPQ";
//判断副本任务是否开启
var qblog = "LPQOpen";
//副本次数设置
var maxenter = 10;
//副本次数记录
var Log = '玩具城任务';
//通关副本次数记录
var Log1 = '玩具城任务完成';
//等级设置
var minlvl = 35;
var maxlvl = 255;
//人数设置
var minplayers = 1;
var maxplayers = 6;
//组队最终奖励物品代码
var zzjl = 4310030
//副本开关
var open = true;//false true
//其他设置
var ca = java.util.Calendar.getInstance();
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒



function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else if (mode == 0) {
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        var em = cm.getEventManager(eventname);
        if (status == 0) {
            var yhms = "";
            yhms += "                #k" + fbmc + "\r\n";
            yhms += "副本进入要求如下：\r\n";
            yhms += "1、等级限制：" + minlvl + " - " + maxlvl + "  人数限制：" + minplayers + " - " + maxplayers + " 经验指数：#r高#k\r\n";
           // yhms += "2、限制次数:每天可进行" + maxenter + "次\r\n";
			//yhms += "2、今日已进行: #b" + cm.getBossLog(Log) + " #k次       \r\n"
            yhms += "#L0##b开始 " + fbmc + "#l\r\n";
            yhms += "#L2##b查询 - 副本状态信息#l\r\n";
            yhms += "#L3##b#r重置副本(用于卡副本使用)#l\r\n\r\n";
            //yhms += "#L4##b#r#v4170005#副本固定奖励兑换#l\r\n\r\n";
            yhms += "#fMob/0130101.img/move/0#   #e#d欢迎来到史莱克冒险岛-用心创造快乐!\r\n";
            cm.sendSimple(yhms);
        } else if (status == 1) {
            if (selection == 0) {
				 if (hasParty() == false) { //判断是否有组队
                    cm.sendOk("你还没有创建一只队伍,请按快捷“P”键进行创建。");
                    cm.dispose();
					return;
                }
				var party = cm.getParty().getMembers();
				var inMap = cm.partyMembersInMap();
				var levelValid = 0;
				for (var i = 0; i < party.size(); i++) {
					if (party.get(i).getLevel() >= minlvl && party.get(i).getLevel() <= maxlvl) {
						levelValid++;
					}
				}
                if (hasParty() == false) { //判断是否有组队
                    cm.sendOk("你还没有创建一只队伍,请按快捷“P”键进行创建。");
                    cm.dispose();
                } else if (isLeader() == false) {//判断是否是队长
                    cm.sendOk("“队长”必须在这里,请让他和我说话。");
                    cm.dispose();
                } else if (zdLog() == false) {//判断副本挑战次数
                    cm.sendOk("您队伍成员中有人挑战次数已经用完。请核对后在来找我!");
                    cm.dispose();
                } else if (inMap < minplayers || inMap > maxplayers) {
                cm.sendOk("你的队伍人数不足"+minplayers+"人.请把你的队伍人员召集到在进入副本.");
                //cm.sendOk("Your party is not a party of "+minPlayers+". Please make sure all your members are present and qualified to participate in this quest. I see #b" + inMap + "#k of your party members are in Kerning. If this seems wrong, #blog out and log back in,#k or reform the party.");
                cm.dispose();
            } else if (levelValid != inMap) {
                cm.sendOk("请确保你的队伍人员最小等级在 "+minlvl+" 和 "+maxlvl+"之间. I see #b" + levelValid + "#k members are in the right level range. If this seems wrong, #blog out and log back in,#k or reform the party.");
                cm.dispose();
                //} else if (checkPartyLevels() == false) {//判断队伍成员等级
                 //   cm.sendOk("管理员 - 提示 \r\n\r\n队伍成员等级必须在#b" + minlvl + " - " + maxlvl + "#k之间并且必须在一张地图才能进入，请核对后在来找我。")
                 //   cm.dispose();
                } else if (checkPartySize() == false) {//判断队伍成员人数
                    cm.sendOk("管理员 - 提示 \r\n\r\n队伍成员人数必须在#b" + minplayers + "~" + maxplayers + "#k之间并且必须在一张地图才能进入，请核对后在来找我。");
                    cm.dispose();
                
                } else if (em == null) {//判断配置文件是否激活
                    cm.sendOk("配置文件没有开启,请联系管理员。");
                    cm.dispose();
                } else if (open == false) {//判断NPC脚本是否开启
                    cm.sendOk("NPC脚本没有开启,请联系管理员。。");
                    cm.dispose();
                } else if (Property() == false) {//判断副本是否已经有开启
                    cm.sendOk("当前频道已有玩家在进行任务中，请稍后在试。\r\n\r\n你可以点击“查询 - 副本状态信息”查看他们的进度。");
                    cm.dispose();
                } else {
					
                    em.startInstance(cm.getParty(), cm.getPlayer().getMap());
                    //cm.serverMessage(6,"[组队任务]: 玩家 [" + cm.getPlayer().getName() + "] 带领他的队伍进入了" + fbmc + "。");
                    var eim = cm.getPlayer().getEventInstance();
                    var party = eim.getPlayers();
                    cm.dispose();
                    em.setProperty(qblog, "false");
                }
            

            } else if (selection == 2) {//副本查询状态
                var pqtry = maxenter - cm.getBossLog(Log);
                if (Property() == false) {//判断任务是否正在进行
                    var vipstr = "#b已经开启#k";
                } else {
                    var vipstr = "#r副本空闲#k";
                }
                var yhms = "";
                yhms = "#k当前位置:\r\n             副本内容 - " + fbmc + "\r\n\r\n";
                yhms += "信息如下:\r\n\r\n";
                //yhms += "今日已进行: #b" + cm.getBossLog(Log) + " #k次#k\r\n";
                yhms += "\r\n副本运行状态：" + vipstr + "\r\n";
                yhms += "\r\n服务器时间: #r" + hour + " 时 " + minute + " 分 " + second + " 秒\r\n";
                cm.sendOk(yhms);
                cm.dispose();
			} else if (selection == 3){
				if (cm.getPlayerCount(922010100) <= 0 && cm.getPlayerCount(922010200) <= 0 && cm.getPlayerCount(922010201) <= 0 && cm.getPlayerCount(922010300) <= 0 && cm.getPlayerCount(922010400) <= 0 && cm.getPlayerCount(922010401) <= 0 && cm.getPlayerCount(922010402) <= 0 && cm.getPlayerCount(922010403) <= 0 && cm.getPlayerCount(922010404) <= 0 && cm.getPlayerCount(922010405) <= 0 && cm.getPlayerCount(922010500) <= 0 && cm.getPlayerCount(922010501) <= 0 && cm.getPlayerCount(922010502) <= 0 && cm.getPlayerCount(922010503) <= 0 && cm.getPlayerCount(922010504) <= 0 && cm.getPlayerCount(922010505) <= 0 && cm.getPlayerCount(922010506) <= 0 && cm.getPlayerCount(922010600) <= 0 && cm.getPlayerCount(922010700) <= 0 && cm.getPlayerCount(922010800) <= 0 && cm.getPlayerCount(922010900) <= 0 && cm.getPlayerCount(922011000) <= 0 && cm.getPlayerCount(922011100) <= 0) {
					cm.getEventManager("LudiPQ").setProperty("LPQOpen", "true");
					cm.sendOk("重置成功！");
					cm.dispose();
				}else {
					cm.sendOk("该副本有队伍正在进行中无法重置！");
					cm.dispose();
				}
			} else if (selection == 4){
            cm.openNpc(9310084, 23);
					//cm.sendOk("！");
                //cm.dispose();
				
            }
        }
    }
}



function getPartySize() {
    if (cm.getPlayer().getParty() == null)
        return 0;
    return (cm.getPlayer().getParty().getMembers().size());

}

function isLeader() {
    if (cm.getParty() == null)
        return false;
    return cm.isLeader();
}

function checkPartySize() {
    var size = 0;
    if (cm.getPlayer().getParty() == null)
        size = 0;
    else
        size = (cm.getPlayer().getParty().getMembers().size());
    if (size < minplayers || size > maxplayers)
        return false;
    return true;
}

function checkPartyLevels() {
    var pass = true;
    var party = cm.getPlayer().getParty().getMembers();
    if (cm.getPlayer().getParty() == null)
        pass = false;
    else {
        for (var i = 0; i < party.size() && pass; i++) {
            if ((party.get(i).getLevel() < minlvl) || (party.get(i).getLevel() > maxlvl) || (party.get(i).getMapId() != cm.getMapId()))
                pass = false;

        }
    }
    return pass;
}

function hasParty() {
    if (cm.getPlayer().getParty() == null)
        return false;
    return true;
}

function Property() {
    var em = cm.getEventManager(eventname);
    if (em.getProperty(qblog) == "false") {
    return false;
    return true;
}
}
function zdLog() {
    var party = cm.getPlayer().getParty().getMembers();
    var it = party.iterator();
    var cPlayer = it.next();
    var zd = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
    if (zd.getBossLog(Log) >= maxenter)
        return false;
    return true;
}

