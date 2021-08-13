/**
 * @触发条件：开拍卖功能
 * @每日签到：领取物品 npc
 * @npcName：冒险岛运营员
 * @npcID：   9900004
 **/
importPackage(net.sf.cherry.client);
var status = 0;
var 黑水晶 = 4021008;
var 确定 = "#fUI/Login.img/BtOk/normal/0#";
var 取消 = "#fUI/Login.img/BtCancel/normal/0#";
var 方块 = "#fUI/GuildMark.img/BackGround/00001007/16#";
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
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }
		if (cm.getMapId() == 209000001 ) {
            cm.sendOk("- 私人房间无法使用\r\n\r\n- 房主可使用#r@我是002房主#k打开房间功能界面。");
            cm.dispose();
        }
		if (cm.getMapId() == 209000002 ) {
            cm.sendOk("- 私人房间无法使用\r\n\r\n- 房主可使用#r@我是003房主#k打开房间功能界面。");
            cm.dispose();
        }
		if (cm.getMapId() == 209000003 ) {
            cm.sendOk("- 私人房间无法使用\r\n\r\n- 房主可使用#r@我是004房主#k打开房间功能界面。");
            cm.dispose();
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
          var selStr = "#e"+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+"\r\n#r"+方块+"  自 由 冒 险 岛 玩 家 实 名 认 证 系 统 -#k "+方块+"\r\n#b"+方块+"  自 由 冒 险 岛 玩 家 实 名 认 证 系 统 -#k "+方块+"\r\n#d"+方块+"  自 由 冒 险 岛 玩 家 实 名 认 证 系 统 -#k "+方块+"\r\n"+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+""+方块+"\r\n\r\n";
 
          selStr += "     #L1#"+确定+"#l        #L2#"+取消+"#l\r\n\r\n";
		  

		cm.sendSimple(selStr);
        } else if (status == 1) {
         	if (selection == 1) { 
			if (cm.getName() == "我不是七宝" || cm.getName() == "我是四哥" 
 			   || cm.getName() == "牧丶君竹"|| cm.getName() == "都来给我舔"
               || cm.getName() == "凉梅"	 || cm.getName() == "Finance丶科"			
               || cm.getName() == "君竹" || cm.getName() == "L带你飞"			   ){
				 			   
				
				
				
				
				
				
				
			    cm.openNpc(9900004,0);
				
			} else {
				cm.sendOk("抱歉，认证系统暂未查询到你的ID。\r\n\r\n请找#r七宝#k进行登记，才可以使用。");
				cm.dispose();
				
			}
			}
			if (selection == 2) { 
			if (cm.getName() == "我" 
 			   			   ){
				
				
				
				
				
				
				
				
			    cm.openNpc(9900004,0);
				
			} else {
				cm.sendOk("请找#r七宝#k进行登记，才可以使用。");

				cm.dispose();
				
			}
			
			
			
			
			
			
			
			
			
			
			
			
			

	    	}
			if (selection == 100) { //兑换点卷
			if(cm.getBossLog('hydlj55')<1 ){
				
			if ( cm.getBossLog('zymxd') >=20 && cm.getBossLog('hydlj5') == 2 ) {
				//cm.gainItem(3994416, -100);
				cm.setBossLog('hydlj55');
				cm.setBossLog('hydlj5');
				cm.gainNX(2000);
				cm.sendOk("恭喜你领取20点活跃度奖励#r点劵 x 2000");
				cm.worldMessage(6,"[活跃度奖励]：恭喜，玩家 "+cm.getName()+" 领取 20 点活跃度奖励 。");
				cm.dispose();
			} else {
				cm.sendOk("你的活跃度不够，或者你已经领取过了");
				cm.dispose();
				return;
			}
			}
			else{
				cm.sendOk("今天已经领取今天所有的活跃度奖励。");
				cm.dispose();
			}
	    	}
        }
    }
}
