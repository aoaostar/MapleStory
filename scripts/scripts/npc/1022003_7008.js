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

	    selStr = "装备展示；#v1012289#\r\n";
		//selStr += "所需熟练度；1700 \r\n";
		selStr += "#b#L0#"+箭头+"返回界面#l\r\n";
		selStr += "#L2#"+箭头+"材料详细 \r\n";
		selStr += "#L3#"+箭头+"装备特性 \r\n";
		selStr += "#L4#"+箭头+"剧情介绍 \r\n";
		
		if(cm.haveItem(4021000,70) && cm.haveItem(4021001,70) && cm.haveItem(4021002,70) && cm.haveItem(4021003,70) && cm.haveItem(4021004,70) && cm.haveItem(4021005,70) && cm.haveItem(4021006,70)&& cm.haveItem(4021007,70)&& cm.haveItem(4021008,70)&& cm.haveItem(4011007,15)&& cm.haveItem(4021009,15)&&!cm.getInventory(1).isFull()) {
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
           if(cm.haveItem(4021000,70) && cm.haveItem(4021001,70) && cm.haveItem(4021002,70) && cm.haveItem(4021003,70) && cm.haveItem(4021004,70) && cm.haveItem(4021005,70) && cm.haveItem(4021006,70)&& cm.haveItem(4021007,70)&& cm.haveItem(4021008,70)&& cm.haveItem(4011007,15)&& cm.haveItem(4021009,15)&&!cm.getInventory(1).isFull()){
				cm.gainItem(4021000, -70);
				cm.gainItem(4021001, -70);
				cm.gainItem(4021002, -70);
				cm.gainItem(4021003, -70);
				cm.gainItem(4021004, -70);
				cm.gainItem(4021005, -70);
				cm.gainItem(4021006, -70);
				cm.gainItem(4021007, -70);
				cm.gainItem(4021008, -70);
			//	cm.gainItem(4021009, -70);
				cm.gainItem(4011007, -15);
				cm.gainItem(4021009, -15);
                   var ii = MapleItemInformationProvider.getInstance();
                   var type = ii.getInventoryType(1012289);	
                   var toDrop = ii.randomizeStats(ii.getEquipById(1012289)).copy();
				  // var mz =  cm.getChar().getName();
                       //toDrop.setExp(1);
                        toDrop.setFlag(1);//可以//1封印//2可以交+滑//3封+防滑//4可以交易+寒冷//5寒+锁//6可以交易+寒+滑//7寒滑//8不可交易//9不可以交易+锁//10不可交换+滑//不可以交易+锁+滑
						toDrop.setStr(70);//力量
						toDrop.setDex(70);//敏捷
						toDrop.setInt(70);//运气
						toDrop.setLuk(70);//智力
                        toDrop.setWatk(0);//物理攻击
						toDrop.setMatk(0);//魔法攻击
						toDrop.setWdef(0);//物理防御
						toDrop.setMdef(0);//魔法防御
						toDrop.setSpeed(0);//移动速度
						toDrop.setJump(0)//跳跃
						toDrop.setHp(0);
						toDrop.setMp(0);
						toDrop.setOwner("高级");
                        cm.getPlayer().getInventory(type).addItem(toDrop);
                        cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop));
					//cm.sendOk("制作完成。熟练度；+700");
					//cm.setBossRankCount("锻造熟练度",700);
					//cm.setBossRankCount("超级级眼镜");	
					cm.completeQuest(1100000000);
					//cm.worldMessage(6,"玩家 "+cm.getName()+" 经过不懈努力，终于锻造出眼镜。");
					cm.dispose();
				}else{
				cm.sendOk("你确定你的材料足够或者钱带够了吗!还有啊，一定要给我留个空位我才可以给你制作！");
				cm.dispose();
				}
            break;
		case 2:
          var sld = cm.getBossRank("锻造熟练度",2);
		   cm.sendOk("所需要的材料；\r\n\r\n#v4021006#   （70 / #r#c4021006##k)\r\n#v4021007#   （70 / #r#c4021007##k)\r\n#v4021008#   （70 / #r#c4021008##k)\r\n#v4021000#   （70 / #r#c4021000##k)\r\n#v4021001#   （70 / #r#c4021001##k)\r\n#v4021002#   （70 / #r#c4021002##k)\r\n#v4021003#   （70 / #r#c4021003##k)\r\n#v4021004#   （70 / #r#c4021004##k)\r\n#v4021005#   （70 / #r#c4021005##k)\r\n#v4011007#   （15 / #r#c4011007##k)\r\n#v4021009#   （15 / #r#c4021009##k)\r\n");
            cm.dispose();
		case 3:
		    cm.sendOk("\t特性；\r\n\r\n\t#r力量，敏捷，智力，运气+70\r\n\r\n");
            cm.dispose();
          
            break;	
		case 4:
		    cm.sendOk("无面人一直想知道自己是什么样子的，它问过天使，天使说：只要你经历过，你最后会找到您的自己真正的面孔。天使作为神特殊的孩子享受着和神之间的亲密关系，在天堂凝望着、爱着、赞美着神。\r\n天使经常从天上给人类带来神的旨意。天使的数量是数之不尽的，他们存在在宇宙中的各个角落。\r\n天使专注于为所有的具有自由意识的实体的需求服务，因此你可以体验到天使的那种无差别的爱。\r\n天使毫不犹豫地执行着他们所分配的任务，带着极大的快乐提供给人们爱、智慧和指导。每一个人身边都一直有天使围绕，天使热切的寻找着机会和你交流。\r\n天使遇到世界上各种各样的事情，洞察到人的内心，洞察到事件的实在。\r\n无面人最后在十个、一百个、一千个惊险旅程中领悟到：只要能克服恐惧就可以得到勇气，在伤痕中可以找到爱，而只要战胜了邪恶和苦难，就可以得到善良。它懂得了世界上，人与人之间的感情，人与事物之间的关联，世界万物的变化。\r\n然后无面人问天使，你长什么样子？漂亮吗？\r\n此时天使说：“其实我长得和你一样。”\r\n原来无面人在寻找的过程中，让他变成有天使一样的脸孔。");
            cm.dispose();
          
            break;
	 
			 
			 
			 
		}
    }
}
