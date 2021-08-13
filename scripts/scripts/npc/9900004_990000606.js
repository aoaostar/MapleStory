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
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";




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
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "装备展示；#v1022079#\r\n";
		//selStr += "所需熟练度；400 \r\n";
		selStr += "#b#L0#"+箭头+"返回界面#l\r\n";
		selStr += "#L2#"+箭头+"材料详细 \r\n";
		selStr += "#L3#"+箭头+"装备特性 \r\n";
		
		if(cm.haveItem(4000007,999)&&cm.haveItem(4000004,999)&&!cm.getInventory(1).isFull()) {
		selStr += "#r#e#L1#"+箭头+"开始锻造#l\r\n";
   }else {}
				
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
					
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
           if(cm.haveItem(4000007,999)&&cm.haveItem(4000004,999)&&!cm.getInventory(1).isFull()){
					cm.gainItem(4000007,-999);
					cm.gainItem(4000004,-999);
	


                   var ii = MapleItemInformationProvider.getInstance();
                   var type = ii.getInventoryType(1022079);	
                   var toDrop = ii.randomizeStats(ii.getEquipById(1022079)).copy();
				  // var mz =  cm.getChar().getName();
                       //toDrop.setExp(1);
                        toDrop.setFlag(1);//可以//1封印//2可以交+滑//3封+防滑//4可以交易+寒冷//5寒+锁//6可以交易+寒+滑//7寒滑//8不可交易//9不可以交易+锁//10不可交换+滑//不可以交易+锁+滑
						toDrop.setStr(10);//力量
						toDrop.setDex(10);//敏捷
						toDrop.setInt(10);//运气
						toDrop.setLuk(10);//智力
                        toDrop.setWatk(0);//物理攻击
						toDrop.setMatk(0);//魔法攻击
						toDrop.setWdef(0);//物理防御
						toDrop.setMdef(0);//魔法防御
						toDrop.setSpeed(0);//移动速度
						toDrop.setJump(0)//跳跃
						toDrop.setHp(1000);
						toDrop.setMp(1000);
						toDrop.setOwner("初级");
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					cm.sendOk("制作完成");
				//	cm.setBossRankCount("锻造熟练度",100);
				//	cm.setBossRankCount("高级眼镜");	
				cm.completeQuest(1200000000);
					//cm.worldMessage(6,"玩家 "+cm.getName()+" 经过不懈努力，终于锻造出初级四维眼镜。");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!");
				cm.dispose();
				}
            break;
		case 2:
          var sld = cm.getBossRank("锻造熟练度",2);
		    cm.sendOk("所需要的材料；\r\n\r\n#v4000007#   （999 / #r#c4000007##k)\r\n#v4000004#   （999 / #r#c4000004##k)\r\n");
			cm.dispose();
		case 3:
		    cm.sendOk("\t特性；\r\n\r\n\t#r力量，敏捷，智力，运气+10 HP,MP+1000\r\n\r\n");
            cm.dispose();
          
            break;	
	 
			 
			 
			 
		}
    }
}
