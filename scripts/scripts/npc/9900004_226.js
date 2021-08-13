
importPackage(net.sf.cherry.client);
var ca = java.util.Calendar.getInstance();
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
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
		var jinbi = Math.ceil(Math.random() * 1000000);
		
		var jingyan = Math.ceil(Math.random() * 2000000);
		var bb = Math.ceil(Math.random() * 5);
		var dq = Math.ceil(Math.random() * 2000);
		
		
		 if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("公告喇叭",2) == 2 && cm.getBossRank("上线提示语1",2) == 2&& cm.getBossRank("喇叭点",2) >=1000){
			cm.worldMessage(6,"◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇");
			cm.setBossRankCount("喇叭点",-1000);
		    cm.dispose();
} else  if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("顶端喇叭",2) == 2  && cm.getBossRank("上线提示语1",2) == 2&& cm.getBossRank("喇叭点",2) >=5000){
		    cm.worldMessage(4,"◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇");
			cm.setBossRankCount("喇叭点",-5000);
		    cm.dispose();
} else  if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("弹窗喇叭",2) == 2  && cm.getBossRank("上线提示语1",2) == 2&& cm.getBossRank("喇叭点",2) >=5500){
		    cm.worldMessage(1,"◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇");
			cm.setBossRankCount("喇叭点",-5500);
		    cm.dispose();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
} else  if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("公告喇叭",2) == 2 && cm.getBossRank("上线提示语2",2) == 2&& cm.getBossRank("喇叭点",2) >=1000){
			cm.worldMessage(6,"★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★");
			cm.setBossRankCount("喇叭点",-1000);
		    cm.dispose();
} else  if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("顶端喇叭",2) == 2  && cm.getBossRank("上线提示语2",2) == 2&& cm.getBossRank("喇叭点",2) >=5000){
		    cm.worldMessage(4,"★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★");
			cm.setBossRankCount("喇叭点",-5000);
		    cm.dispose();
} else  if(cm.getBossRank("上线喇叭",2) == 2 && cm.getBossRank("弹窗喇叭",2) == 2  && cm.getBossRank("上线提示语2",2) == 2&& cm.getBossRank("喇叭点",2) >=5500){
		    cm.worldMessage(1,"★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★");
			cm.setBossRankCount("喇叭点",-5500);
		    cm.dispose();				












			
			}
		
		
		if(cm.getBossLog("mrqdts")==0 && cm.getPlayer().getLevel() >= 45){
			cm.setBossRankCount("签到天数");
			//cm.setBossRankCount("活跃度");
			//cm.setBossRankCount("活跃度","-"+cm.getBossRank("活跃度",2)+"");
		//	cm.gainNX(dq);
		//	cm.gainMeso(jinbi);
		//	cm.gainExp(jingyan);
			cm.gainItem(4110000,bb)
			cm.gainItem(4032398, +1)
			cm.setBossLog("mrqdts");
			cm.showInstruction("\r\n#e#r"+MC+"#n#k\r\n\r\n你第 #e#r"+ cm.getBossRank("签到天数",2)+" #k#n天签到\r\n\r\n获得百宝券；#b"+bb+"#k\r\n\r\n获得出席图章；1", 300, 20);
			cm.取消克隆();			
			cm.dispose();
		return;
		} else if (cm.getBossLog("mrqdts")==0 && cm.getPlayer().getLevel() < 45) {
			cm.setBossRankCount("签到天数");
			cm.gainItem(4032398, +1)
			cm.setBossLog("mrqdts");
			cm.showInstruction("\r\n#e#r"+MC+"#n#k\r\n\r\n你第 #e#r"+ cm.getBossRank("签到天数",2)+" #k#n天签到\r\n#k\r\n获得出席图章；1", 300, 20);
			cm.取消克隆();
            cm.dispose();
          return;
	 
	    }
		 else if (cm.getPlayer().getLevel() > 0) {		
			cm.取消克隆();
            cm.dispose();
          return;
	 
	    }

        if (mode == 1)
            status++;
        else
            status--;
		
		cm.dispose();
	}
}

