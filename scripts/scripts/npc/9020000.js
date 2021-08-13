var status;
var 最小等级 = 20;
var 最大小等级 = 255;
var 最小人数 = 1;
var 最大小人数 = 6;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        var text = "";
        for (i = 0; i < 10; i++) {
            text += "";
        }
        text += "#d这里是废弃都市组队任务，最低要求" + 最小人数 + "人，等级在" + 最小等级 + "级以上即可开始废弃组队任务。\r\n\r\n"
        text += "#L1##r开始组队副本#l\r\n"
        cm.sendSimple(text);
    } else if(status==1){
		if (selection == 2) {
			cm.openNpc(9020000,5);
		}else if (selection == 1) {
        if (cm.getParty() == null) { // 没有组队
            cm.sendOk("你没有队伍无法进入！");
            cm.dispose();
        } else if (!cm.isLeader()) { // 不是队长
            cm.sendOk("请让你的队长和我说话~");
            cm.dispose();
        } else {
            var party = cm.getParty().getMembers();//声明变量 队伍 = party 赋值 party = 获取队伍所有人
            var inMap = cm.partyMembersInMap(); //声明变量 初始地图 = inMap 赋值 inMap = 获取队伍所有人所在地图
            var levelValid = 0;
            for (var i = 0; i < party.size(); i++) {//循环获取队伍人数
                if (party.get(i).getLevel() >= 最小等级 && party.get(i).getLevel() <= 最大小等级)//判断队伍成员等级是否超过限定等级
                    levelValid++;
            }
            if (inMap < 最小人数 || inMap > 最大小人数) {//判断初始地图 队伍的人数，是否匹配限定人数
                cm.sendOk("你的队伍人数不足" + 最小人数 + "人.请把你的队伍人员召集到废气都市在进入副本.");
                cm.dispose();
            } else if (levelValid != inMap) {
                cm.sendOk("请确保你的队伍人员最小等级在 " + 最小等级 + " 和 " + 最大小等级 + "之间. 并且 #b" + levelValid + "#k 队伍成员的等级处于正确的范围内.");
                cm.dispose();
            } else {
                var em = cm.getEventManager("KerningPQ");//调用事件
                if (em == null) {//如果事件脚本不存在
                    cm.sendOk("事件发生错误，请联系管理员.");
                    cm.dispose();
                } else {
                    //判断副本地图人数，是否为0
                    if (cm.getPlayerCount(103000800) <= 0 && cm.getPlayerCount(103000801) <= 0 && cm.getPlayerCount(103000802) <= 0 && cm.getPlayerCount(103000803) <= 0 && cm.getPlayerCount(103000804) <= 0) {
                       //屏蔽后，可以带卡入内
					    cm.givePartyItems(4001008, -1, true);//通行证 全部删除 卡可以带出来，但是进去会消失
                        cm.givePartyItems(4001007, -1, true);//证书 全部删除 这个数量也不正确//这里为真true 代表直接删除背包所有的这个道具
                        em.startInstance(cm.getParty(), cm.getPlayer().getMap());//传送队伍进入副本
                    } else {
                        cm.sendOk("请稍等...任务正在进行中.");
                    }
                }
                cm.dispose();
			
            }
        }
    }
}
}