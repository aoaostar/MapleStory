
importPackage(net.sf.cherry.client);
var status = 0;
var 确定 = "#fUI/Login.img/BtOk/normal/0#";
var 取消 = "#fUI/Login.img/BtCancel/normal/0#";
var 方块 = "#fUI/GuildMark.img/BackGround/00001007/16#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";

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
		if (cm.getMapId() == 209000015) {
            cm.sendOk("擂台中，无法使用。");
            cm.dispose();
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
		  selStr  = "自由冒险岛\r\n\r\n";
		  selStr += "\t请勿使用第三方插件，修改游戏数据等。\r\n";
		  selStr += "\t有任何建议，游戏BUG，好的想法，创意等，请提交给群主，我会第一时间给予回应。\r\n";
		  //selStr += "\t#r实行一号一角色制，把角色名提交给群主，就可以通过验证进入游戏。\r\n";
		 // selStr += "\t#r需要点券请找客户，请勿在游戏中私底下交易，造成的损失概不负责。\r\n";
		  
		  
          selStr  += "\r\n\t\t\t#b#L1#开启冒险岛的世界#l\r\n\r\n";
		  
		  
		cm.sendSimple(selStr);
        } else if (status == 1) {
         	if (selection == 1) { 
			if ( cm.getMapId()  >  0 ) {  
			   cm.warp(104000000,0);
			   
			   cm.serverNotice("[公告]：玩家 "+ cm.getChar().getName() +" 加入自由冒险岛大家庭，开启新的旅程，欢迎你!!"); 
			   cm.dispose();
			   
			} else {
				cm.sendOk("#r#e公测时间为18:00，暂时无法进入，请在公测开启后再通过。");
				cm.dispose();	
			}
			}
	    	}
        }
    }

