/**
 * @触发条件：开拍卖功能
 * @每日签到：领取物品 npc
 * @npcName：冒险岛运营员
 * @npcID：   9900004
 **/
importPackage(net.sf.cherry.client);
var status = 0;
var 黑水晶 = 4021008;
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 忠告 = "#k温馨提示：任何非法程序和外挂封号处理.封杀侥幸心理.";
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
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var a1 = "#L1#" + 正方箭头 + "增加到 90 存储空间\r\n";



            cm.sendSimple("#r你需要花费 50000000金币和30000点券升级仓库吗？#k\r\n\r\n"+a1+"");

        } else if (status == 1) {
         	if (selection == 1) { //5点
			if (cm.getMeso()>= 50000000 && cm.getPlayer().getCSPoints(1)>=30000){         
				cm.gainMeso(-50000000);
				cm.gainNX(-30000);
                cm.setBossRankCount("仓库数量","10");
				cm.sendOk("恭喜你增加仓库到 90 存储空间成功。");;
				cm.dispose();
			} else {
				cm.sendOk("金钱不够。");
				cm.dispose();
				return;
			}
	    	}
        }
    }
}
