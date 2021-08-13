
importPackage(net.sf.cherry.client);
var status = 0;
var 确定 = "#fUI/Login.img/BtOk/normal/0#";
var 取消 = "#fUI/Login.img/BtCancel/normal/0#";
var 方块 = "#fUI/GuildMark.img/BackGround/00001007/16#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 玩家对抗 = "#fEffect/SkillName1.img/1001003/玩家对抗#";
var 功能菜单 = "#fEffect/SkillName1.img/1001003/功能菜单#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var Message = new Array(
"每日活跃度到达一定活跃可领取奖励哦。",
"如果不喜欢游戏菜单风格，可以在个人天赋里更换。",
"打开活跃成就界面，可看到个人详细资料。",
"枫叶至尊有专属的枫叶界面风格，和专属的权限。",
"完成某些专属的任务，打野怪会爆出特别的东西哦。",
"随身仓库无论是存入物品或者取出物品，都会收取费用。",
"人气度至关重要，当你的人气度负了的话，将被会系统限制。",
"玩家对抗中可以获得丰富的奖励。",
"节奏大师，连击数越高，获得的奖励越丰厚。",
"如果觉得传送界面太多太麻烦，可以在个人天赋页面关闭不常用的传送点哦。",
"做一个合法的岛民，不乱减少别人的人气，这是一个衡量玩家的标准。",
"强大的个人天赋，你哪项天赋比较强大呢？",
"枫叶至尊每周都会更换，准备好去争夺了吗？",
"枫叶募集收取枫叶时间是周一至周五和周末，周六为枫叶至尊更替时间。",
"冒险岛翻牌小游戏，是一种超级考验手气的小游戏。",
"如果我是你，我会时不时打开菜单界面，说不定有好东西等着你呢？",
"奇遇奖励各式各样，你也许可以奇遇获得特殊的能力。",
"有些副本需要消耗疲劳值，有些副本不需要。",
"有些副本需要疲劳值，当疲劳值消耗完了，可以打野怪去。",
"天赋分阶为；初级，中级，高级，宗师级，奥义。",
"奇遇是一种很特殊的奇遇，遇到了是奇遇，遇不到就等机遇了。",
"有些特殊的能力来源于奇遇，有些特殊的能力来源于天赋修炼。",
"奇遇能力简介，隐形术 - 可以消于空间，隐于遁形，不被他人所察觉。",
"奇遇能力简介，千里追踪 - 任何人都无法逃脱，只要我知道你。",
"奇遇能力简介，探囊取物 - 你的东西是我的，我的东西还是我的。",
"奇遇能力简介，影分身 - 人数才能决定胜负。",
"奇遇能力简介，全能法师 - 掌控了所有法师的能力。",
"翻牌小游戏，当玩家通关一定关数，可一定几率触发大奖励。",
"制药天赋可以制作出各种稀有的药品。",
"收集更多的枫叶，就可以夺得每周的枫叶至尊。",
"召唤天赋可以各种强大的怪物。",
"占领自己的领地，领主在领地可以有更多的收获。",
"附魔天赋可以将装备附魔，来增强你的战斗力。",
"随身仓库的最大存储量可以在市场的仓库管理员扩充。",
"自由娱乐赌博场的赌博机不单单只有一种。",
"赌博就去射手村的赌博娱乐场，小赌怡情，大赌伤心。"
	
	
	);

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
        var MC = cm.getServerName();		
		var wj1 =  cm.获取指定地图玩家数量(1);
		var wj2 =  cm.获取指定地图玩家数量(2);
		var wj3 =  cm.获取指定地图玩家数量(3);
		var wj4 =  cm.获取指定地图玩家数量(4);
		var wj5 =  cm.获取指定地图玩家数量(5);
		var wj0 =  cm.获取指定地图玩家数量(910000023);
		var lt1 =  cm.获取指定地图玩家数量(701000201);
		var jy = cm.getPlayer().getExp(); 
		var jb = cm.getMeso();
		var dq = cm.getPlayer().getCSPoints(1)
		var fy = cm.haveItem(4032217,20);
		//if (cm.getMapId() == 104040000 ) {
        // cm.dispose();
		// cm.openNpc(9900004,2);
         // return;
		// }
		if (cm.getMapId() == 910000007 && cm.getPlayer().getClient().getChannel() != 2) {
           cm.showInstruction("\r\n抢楼啊？傻逼？~~~2线2线\r\n", 200, 3); 
		   cm.dispose();
		   cm.openNpc(9900007,102);
          return;
		 }
		if (cm.getMapId() == 1 ) {
           cm.showInstruction("\r\n这里的气息，阻挡了我的视线\r\n\r\n我得出去，不过这里好像没有出路。\r\n", 200, 3);  
            cm.dispose();
          return;
		 }
		if (cm.getMapId() == 2  ) {
           cm.showInstruction("\r\n这里气息更浓烈，\r\n\r\n我会不会死在这里！\r\n ", 200, 3);  
            cm.dispose();
          return;
	    }
		if (cm.getMapId() == 3  ) {
           cm.showInstruction("\r\r\n\r\能感觉到这里有出路\r\n\r\n在哪里在哪里\r\n ", 200, 3);  
            cm.dispose();
          return;
	 
	    }
		if (cm.getMapId() == 4  ) {
           cm.showInstruction("\r\n一股死人的气息。\r\n ", 200, 3);  
            cm.dispose();
          return;
	 
	    }
		if (cm.getMapId() == 5  ) {
           cm.showInstruction("\r\n这不是我该来的地方。怕怕\r\n ", 200, 3);  
            cm.dispose();
          return;
	 
	    }
		if (cm.getMapId() == 910000023  ) {
           cm.showInstruction("\r\n#e#r异界中心#k#n\r\n\r\n异界存在人数；"+(wj1+wj2+wj3+wj4+wj5+wj0)+"\r\n\r\n", 200, 3);  
            cm.dispose();
          return;
	 
	    }
		
		if (cm.getMapId() == 701000201  ) {
			//cm.给指定地图发物品(701000201,2000005,1)
           cm.showInstruction("\r\n#d《"+MC+"》#k\r\n#e#r擂台争霸 - 青龙之门#k#n\r\n\r\n当前积分:"+ cm.getBossRank("擂台赛",2)+"\r\n擂台人数；#r"+lt1+"#k\r\n", 300, 3);  
           cm.dispose();
		    cm.openNpc(9900007,2001);
          return;
	 
	    }
		if (cm.getPlayer().getFame() < 0 ) {
            cm.sendOk("#r- 你已经被过多人投诉，无法使用此功能。 \r\n\r\n  #k根据自由冒险岛玩家守则，你已经被过多的玩家投诉，削减人气度，已经被限制行动，如果有任何异议，请与管理员取得联系。");
            cm.dispose();

        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {

          var 
		   

           selStr  = "\t#L1#"+功能菜单+"#l #L2#"+玩家对抗+"#l\r\n\r\n";
		   
		   
		   if (cm.getMapId() == 910000000  ) {
			   selStr += "\r\n\t\t\t\t   #e#L3##r进入活动#l#k ";} else {}
		    if (cm.getMapId() == 910000000  ) {  } else {
		   selStr +=("\t\t\t\t\t#e#r[小提示]#k\r\n\r\n#b\t#n"+ Message[Math.floor(Math.random() * Message.length)]);}
		  
		cm.sendSimple(selStr);
        } else if (status == 1) {
         	if (selection == 1) { 
			if ( cm.getMapId() > 0  ) {  
			   cm.openNpc(9900004,0);
			} else {
				cm.sendOk("#r#e抱歉，你尚未在群主登记角色名称，无法通过验证。");
				cm.dispose();	
			}
			}
			if (selection == 2) { 
			if (  cm.getMapId() > 0  ) { 	
		        cm.openNpc(9900004,700);				
			} else {
				cm.sendOk("#r#e抱歉，你尚未在群主登记角色名称，无法通过验证。");
				cm.dispose();			
			}		
			}
			if (selection == 3) { 
			if (  cm.getMapId() > 0  ) { 	
		        cm.openNpc(9900007,105);				
			} else {
				cm.sendOk("#r#e抱歉，你尚未在群主登记角色名称，无法通过验证。");
				cm.dispose();			
			}		
			}
	    	}
        }
    }
