
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
        }var MC = cm.getServerName();
		var jb = cm.getBossRank("银行金币",2)/90/10;
		var dq = cm.getBossRank("银行点券",2)/95/5;
        if(cm.getBossLog("银行利息") == 0){
			cm.setBossRankCount("银行金币",jb);
			cm.setBossRankCount("银行点券",dq);
		    cm.setBossLog("银行利息");
			
			
		}
		
		//if (cm.getBossLog("gengxin")==0  ) {
        //    cm.showInstruction("\r\n#e#r"+MC+"#n#k\r\n\r\n\r\n当前版本;#bV 4.9\r\n\r\n\r\n签到完成\r\n\r\n\r\n", 220, 15); 
        //    cm.dispose();
        //  return;
	 
	  //  }

        if (mode == 1)
            status++;
        else
            status--;
cm.dispose();
			}
	    	}

