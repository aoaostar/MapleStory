//////////////////////////////
//七宝*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////
importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);






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
	    if ( cm.getMapId() == 20000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "装备展示；#v1402037#\r\n";
		selStr += "所需熟练度；2000 \r\n";
		selStr += "#b#L0#返回界面#l\r\n";
		selStr += "#L2#材料详细 \r\n";
		
		if(cm.haveItem(4001078,200)&&cm.haveItem(4000235,50)&&cm.haveItem(4000262,200)&&cm.haveItem(4000263,200)&&cm.haveItem(4000268,200)&&cm.haveItem(4000030,200)) {
		selStr += "#e#L1#开始锻造#l\r\n";
}	else {}
				
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
					
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
           if(cm.haveItem(4001078,200)&&cm.haveItem(4000235,50)&&cm.haveItem(4000262,200)&&cm.haveItem(4000263,200)&&cm.haveItem(4000268,200)&&cm.haveItem(4000030,200)){
					cm.gainItem(4001078,-400);
					cm.gainItem(4000235,-400);
					cm.gainItem(4000262,-400);
					cm.gainItem(4001078,-400);
					cm.gainItem(4000235,-400);
					cm.gainItem(4000262,-400);

                   var ii = MapleItemInformationProvider.getInstance();
                   var type = ii.getInventoryType(402037);	
                   var toDrop = ii.randomizeStats(ii.getEquipById(402037)).copy();
                       //toDrop.setExp(1);
                        toDrop.setFlag(0);//可以//1封印//2可以交+滑//3封+防滑//4可以交易+寒冷//5寒+锁//6可以交易+寒+滑//7寒滑//8不可交易//9不可以交易+锁//10不可交换+滑//不可以交易+锁+滑
						toDrop.setStr(0);//力量
						toDrop.setDex(0);//敏捷
						toDrop.setInt(0);//运气
						toDrop.setLuk(0);//智力
                        toDrop.setWatk(0);//物理攻击
						toDrop.setMatk(0);//魔法攻击
						toDrop.setWdef(0);//物理防御
						toDrop.setMdef(0);//魔法防御
						toDrop.setSpeed(0);//移动速度
						toDrop.setHp(500);
						toDrop.setMp(500);
						toDrop.setOwner("初级");
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("制作完成!");
					cm.worldMessage(6,"玩家 "+cm.getName()+" 经过不懈努力，终于锻造出龙背刃。");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            break;
		case 2:
          var sld = cm.getBossRank("锻造熟练度",2);
		    cm.sendOk("#e#bfalse = 未达到 true = 已达到#k\r\n\r\n#v4001078#  x 200 = #r"+cm.haveItem(4001078,200)+"#k\r\n#v4000235##  x 50 = #r"+cm.haveItem(4000235,50)+"#k\r\n#v4000262#  x 200 = #r"+cm.haveItem(4000262,200)+"#k\r\n#v4000263#  x 200 = #r"+cm.haveItem(4000263,200)+"#k\r\n#v4000030#  x 200 = #r"+cm.haveItem(4000030,200)+"#k\r\n#v4000268#  x 200 = #r"+cm.haveItem(4000268,200)+"#k\r\n#v4000269#  x 200 = #r"+cm.haveItem(4000269,200)+"#k\r\n熟练度相差;"+(1999-sld)+"");
            cm.dispose();
          
            break;	
	 
			 
			 
			 
		}
    }
}
