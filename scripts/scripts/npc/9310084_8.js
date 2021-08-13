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
            var txt1 = "#L1#" + 蓝色箭头 + "#d#v4002000# 100张绿蜗牛邮票兑换 #v1032058#" + 美化new + "\r\n\r\n";
            var txt2 = "#L2#" + 蓝色箭头 + "#d#v4002000# 10张绿蜗牛邮票兑换 #v1112446#" + 美化new + "\r\n\r\n";
            var txt3 = "#L3#" + 蓝色箭头 + "#d#v4002000# 1张绿蜗牛邮票兑换 #v4001322#x2" + 美化new + "#l";
         //   var txt4 = "#L4#" + 蓝色箭头 + "#d绿水灵邮票兑换" + 美化new + "\r\n\r\n";
           // var txt5 = "#L5##d" + 蓝色箭头 + "◎金杯兑换◎" + 感叹号 + "#l"
            //var txt6 = "#L4##d" + 蓝色箭头 + "温情新手须知" + 美化new + "\r\n\r\n"
            //var txt7 = "#L7##d" + 蓝色箭头 + "快捷转职系统"
            //var txt8 = "  #L8##d" + 蓝色箭头 + "精灵吊坠兑换\r\n\r\n"
            //var txt9 = "#L9##d" + 蓝色箭头 + "万能道具商店  "
            //var txt10 = "#L3##d"+红色箭头+"常驻玩家礼包.请找GM索取道具#l"
            cm.sendSimple("欢迎来到#b豆豆冒险岛#k。\r\n\r\n" + txt1 + "" + txt2 + "" + txt3 + "");

        } else if (status == 1) {
            if (selection == 1) { //更多功能
			if(cm.haveItem(4002000, 100)){
                        cm.gainItem(4002000,-100);
			var ii = net.sf.cherry.server.MapleItemInformationProvider.getInstance();
			var type = ii.getInventoryType(1032058);	
			var toDrop = ii.randomizeStats(ii.getEquipById(1032058)).copy();	
			toDrop.setLuk(5);
			toDrop.setInt(5);
			toDrop.setDex(5);
			toDrop.setStr(5);
			toDrop.setHp(150);
			toDrop.setMp(150);
			toDrop.setWdef(35);
			toDrop.setMdef(35);
			cm.getPlayer().getInventory(type).addItem(toDrop);
			cm.getC().getSession().write(net.sf.cherry.tools.MaplePacketCreator.addInventorySlot(type, toDrop));
                     cm.getC().getChannelServer().getWorldInterface().broadcastMessage(null, net.sf.cherry.tools.MaplePacketCreator.serverNotice(12,cm.getC().getChannel(),"[" + cm.getPlayer().getName() + "]" + " : " + " 用100个[绿蜗牛邮票]兑换了一个[工作人员C钛质收信机]！",true).getBytes()); 		
                   cm.sendOk("兑换成功！");
                   cm.dispose();
			}else{
			cm.sendOk("你没有100张#v4002000#无法兑换！");
			cm.dispose();
			}
            } else if (selection == 2) { //更多功能
			if(cm.haveItem(4002000, 10)){
                        cm.gainItem(4002000,-10);
			cm.gainItem(1112446,1);
                   cm.sendOk("兑换成功！");
                   cm.dispose();
			}else{
			cm.sendOk("你没有1张#v4002000#无法兑换！");
			cm.dispose();
			}
            } else if (selection == 3) {//每日赏金任务
			if(cm.haveItem(4002000, 1)){ 
                        cm.gainItem(4002000,-1);
			cm.gainItem(4001322,2);
                   cm.sendOk("兑换成功！");
                   cm.dispose();
			}else{
			cm.sendOk("你没有5张#v4002000#无法兑换！");
			cm.dispose();
			}
            } else if (selection == 4) { //更多功能
                cm.openNpc(9310057, 0);
            } else if (selection == 5) { //更多功能
                cm.openNpc(9310057, 0);
            } else if (selection == 6) { //装备分解系统
                cm.openNpc(9900004, 5);
            } else if (selection == 7) { //快速专职
                cm.openNpc(9900002, 0);
            } else if (selection == 8) { //学习锻造技能
              // cm.teachSkill(11110005,0,20);
              // cm.teachSkill(15111004,0,20);
		/*if(cm.haveItem(4001038,1)&&cm.getMeso() >= 100000){
                    cm.teachSkill(1007,1,1);
                    cm.teachSkill(10001007,1,1);
                    cm.teachSkill(20001007,1,1);
                    cm.gainMeso(-100000);
                    cm.gainItem(4001038,-1);
                     cm.sendOk("学习成功!");
                     cm.getC().getChannelServer().getWorldInterface().broadcastMessage(null, net.sf.cherry.tools.MaplePacketCreator.serverNotice(12,cm.getC().getChannel(),"[" + cm.getPlayer().getName() + "]" + " : " + " 学习了锻造技能！",true).getBytes()); 
                    cm.dispose();
                }else{
                    cm.sendOk("学习锻造技能需要消耗一个#v4001038#.和10万冒险币。");
                    cm.dispose();
                }*/
                //cm.sendOk("近期开放，敬请期待。");
                //cm.dispose();
		
                cm.openNpc(9900004, 1); //大姐大
            } else if (selection == 9) { //快捷商店
                if (cm.getMeso() >= 2000) {
                    cm.openShop(603);
                    cm.gainMeso(-2000);
                    cm.dispose();
                } else {
                    cm.sendOk("冒险币2000才可以打开远程商店。");
                    cm.dispose();
                }
            } else if (selection == 10) { //元神修炼npc
                cm.openNpc(9900004, 88); //大姐大
            } else if (selection == 11) { //积分换点卷
                cm.openNpc(9900004, 1);//
            } else if (selection == 12) { //活跃度系统
                cm.openNpc(9100106, 0); //日本高级快乐百宝箱
            } else if (selection == 13) { //待添加
                cm.openNpc(9000018, 0); //待添加
            }
        }
    }
}
