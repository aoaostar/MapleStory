function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#r私聊岛主认证女侠，可获得：#n#b\r\n1、大王冒险岛女侠勋章（四维15 HP/MP1500 双攻15 双防200 命中回避50 移速跳跃20 ）\r\n2、超级明星美发卡1张\r\n3、1W点卷\r\n4、兔子骑宠特殊交换券×5\r\n#r领取礼包必须要有足够的空间哦，否则被系统吞了东西，管理员不负责哦\r\n"//3
            text += "#L1##r#v4310022#领取女侠认证礼包#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("请清理你的背包，至少空出4个位置！");
            cm.dispose();
        } else if(cm.haveItem(4310022,1)){
				cm.gainItem(4310022, -1);
				cm.gainItem(5150038, 1);//超级明星美发卡
				cm.gainItem(4001215, 5);//兔子骑宠特殊交换券
				cm.gainNX(10000);//点卷
				cm.gainItem(1142304,15,15,15,15,1500,1500,15,15,200,200,50,50,20,10);//女神勋章
            cm.sendOk("领取成功，很高兴为你服务！");
cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]成为大王冒险岛一代侠女！快去和她结为侠侣吧！");
            cm.dispose();
			}else{
            cm.sendOk("你确定你认证成功了么？");
            cm.dispose();
			}
		}
    }
}


