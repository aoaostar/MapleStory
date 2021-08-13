[]vr mapList = Array(//array（地图id，物品id）（每一个小格子都要是不同的地图以及不同的物品代码！！！！！！地图的多少关系到该活动的难度）
    Array(100000000,4031817),
	Array(103000000,4031818)
);

var prizeList = Array(4000000);//奖品抽取，输入奖品代码就好，奖品越多，抽到的几率就越低
var firstmap = Array(749030000,4032218);//活动开始的地图id以及发放物品的id
var index = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
    } else if(status == 0){
        if(cm.getMapId() == firstmap[0]){
            var strlen = "您好，我是捉迷藏活动npc，我将发放给你一个活动物品，请将该物品给有需要的npc，他有可能会给你丰厚的奖品哦！";
            index = 1;
        }else{
            var strlen = "您好，我是捉迷藏活动npc，非常开心您找到了我，请问你有我需要的物品么？";
            index = 0;
        }
        cm.sendYesNo(strlen);
    }else if(status == 1){
        if(cm.getInventory(4).isFull()){
            cm.sendOk("#b请保证其他栏位至少有2个空格,否则无法领取活动物品.");
            cm.dispose();
            return;
        }
        if (cm.getInventory(2).isFull()){
            cm.sendOk("#b请保证消耗栏位至少有2个空格,否则无法抽取活动物品.");
            cm.dispose();
            return;
        }
        if(index == 1){
            if(cm.getBossLog("zhongqiuhuodong") >= 2){
                cm.sendOk("该活动只可以参加两次哦！");
                cm.dispose();
                return;
            }
            for(var i = 0; i < mapList.length;i++){
                if(cm.haveItem(mapList[i][1]) || cm.haveItem(firstmap[1]) ){
                    cm.sendOk("你已经有活动物品了哦~不要重复领取哦！");
                    cm.dispose();
                    return;	
                }
            }
            cm.gainItem(firstmap[1]);
            cm.setBossLog("zhongqiuhuodong");
            cm.sendOk("恭喜你成功获得了本次活动的活动物品");
            cm.dispose();
            return;
        }else{
			if(selectMod == -1){
				cm.sendOk("对不起，我不是该活动的npc");
				cm.dispose();
				return;
			}
			var item = mapList[selectMod][1];
			if(cm.haveItem(item)){
				if(item == finalItem){
					var ran = Math.floor(Math.random() * priceList.length);
					cm.gainItem(prizeList[ran],1);
					cm.gainItem(item,-1);
					cm.sendOk("恭喜你成功获得了本次活动的奖品");
					cm.dispose();
					return;
				}else{
					var ran = Math.floor(Math.random() * priceList.length);
					cm.gainItem(mapList[ram][1]);
					cm.gainItem(item,-1);
					cm.sendOk("恭喜你成功获得了活动物品!");
					cm.dispose();
					return;
				}
			}else{
				cm.sendOk("不好意思哦，您没有我需要的物品哦");
				cm.dispose();
				return;
			}
		}
    }
}

function selectMod(){
    var map = cm.getMapId();
    for(var i = 0; i < mapList.length;i++){
        if(map == mapList[i][0])
            return i;
    }
    return -1;
}