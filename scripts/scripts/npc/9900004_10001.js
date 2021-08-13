importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
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
            var a1 = "  #L1#" + 正方箭头 + " 传承工地手套#v1082147##l";
			var a2 = "#L2#" + 正方箭头 + " 制造材料明细\r\n\r\n";



            cm.sendSimple("#r【 我 可 以 锻 造 出 你 想 象 不 到 的 东 西 】#k\r\n\r\n\r\n" + a1 + "" + a2 + "");
        } else if (status == 1) {
		
	    if (cm.getInventory(1).isFull()){
                        cm.sendOk("#b请保证装备栏位至少有2个空格,否则无法合成.");
                        cm.dispose();
			return;
            }
            if (selection == 1) {//红
				if(cm.haveItem(4000001,400) && cm.haveItem(4000015,400) && cm.haveItem(4000012,400)){
					cm.gainItem(4000001,-400);
					cm.gainItem(4000015,-400);
					cm.gainItem(4000012,-400);

                        var ii = MapleItemInformationProvider.getInstance();
                        var type = ii.getInventoryType(1082147);	
                        var toDrop = ii.randomizeStats(ii.getEquipById(1082147)).copy();
                        //var temptime = new java.sql.Timestamp(java.lang.System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);//最前面的1 代表1天。自己改
                        //toDrop.setExpiration(temptime);
                        toDrop.setFlag(20);//1封印//2可以交换//3封+防滑//4可以交易+寒冷//5寒+锁//6可以交易+寒+滑//7寒滑//8不可交易//9不可以交易+锁//10不可交换+滑//不可以交易+锁+滑
						toDrop.setStr(10);
						toDrop.setDex(10);
						toDrop.setInt(10);
						toDrop.setLuk(10);
                        toDrop.setWatk(10);
						toDrop.setMatk(10);
						toDrop.setSpeed(10);
						toDrop.setHp(500);
						toDrop.setMp(500);
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.worldMessage(6,"玩家 "+cm.getName()+" 经过不懈努力，终于锻造出传承工地手套。");
					cm.worldMessage(1,"锻造完成\r\n已放入背包");

					cm.dispose();
				}else{
				cm.worldMessage(1,"材料不够啊，你是在逗我吗？");
				cm.dispose();
				}
				   
        }else if (selection == 2) {//红
			    cm.sendOk("#b 需要以下材料；\r\n\r\n\r\n#v4000001#+#v4000015#+#v4000012#各400个");
				cm.dispose();
            }
        }
    }
}
