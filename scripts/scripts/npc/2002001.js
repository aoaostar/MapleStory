var status = 0;
var eventname = "XfcPQ";
//随机奖励 可添加%60卷轴 请自行添加代码。 
//代码样例：var itemList = Array(2000004,2000003);
//注意事项：添加多个随机物品的时候请用,隔开
var itemList = new Array(2043001,2043201,2043101,2043301,2043701,2043801,2040801,2040804,2040817,2044001,2044101,2044201,2044301,2044401,2044501,2044601,2044701,2044801,2044901,2370000,2370001,2370002,2370003,2370004,2370005,2370006,2370007,2370008,2370009,2370010,2370011,2370012); //最少添加2个
var randNum = Math.floor(Math.random() * (itemList.length + 1));

//设置随机物品 指定物品数量 暂时不用()
var randItem = itemList[randNum];
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (!cm.isLeader()) {
        cm.sendOk("请你们的队长和我说话。");
        cm.dispose();
        return;
    }
    if (mode == -1) {
        cm.dispose();
    } else if (mode == 0) {
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var em = cm.getEventManager(eventname);
            var eim = cm.getPlayer().getEventInstance();
            party = eim.getPlayers();
            var mapid = cm.getPlayer().getMapId();
            if (mapid == 889100000) {
                cm.sendOk("欢迎来到 幸福村 - (#r组队任务#k) 第#b1#k阶段\r\n\r\n请你和你的队友试着调查这里,搜集好50个雪花 然后走出传送门…");
            } else if (mapid == 926100001) {
                cm.sendOk("1");
                cm.dispose()
            } else if (mapid == 889100100) {
                if (cm.haveItem(4031311, 50)) {
                    cm.gainItem(4031311, -50);
			cm.summonMob(9500317, 2000, 6000, 1);
                } else {
                    cm.sendOk("欢迎来到 幸福村 - (#r组队任务#k) 第#b2#k阶段\r\n\r\n准备好50个#z4031311# 召唤本关BOSS ");
                }
                cm.dispose();
                
           
    
    
            }
        }
    }
	}