var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";//"+箭头+"
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 粉问号 = "#fUI/Initials.img/Button/Button0/mouseOver/0#";  //disabled/0灰色
var 粉问号2 = "#fUI/Initials.img/Button/Button0/disabled/0#";  //disabled/0灰色

var 粉斜号 = "#fUI/Initials.img/Button/Button1/mouseOver/0#";  
var 粉斜号2 = "#fUI/Initials.img/Button/Button1/disabled/0#"; 

var 粉小于号 = "#fUI/Initials.img/Button/Button2/mouseOver/0#";
var 粉小于号2 = "#fUI/Initials.img/Button/Button2/disabled/0#";

var 粉大于号 = "#fUI/Initials.img/Button/Button3/mouseOver/0#";
var 粉大于号2 = "#fUI/Initials.img/Button/Button3/disabled/0#";

var 粉感叹号 = "#fUI/Initials.img/Button/Button4/mouseOver/0#";
var 粉感叹号2 = "#fUI/Initials.img/Button/Button4/disabled/0#";

var 粉艾特号 = "#fUI/Initials.img/Button/Button5/mouseOver/0#";
var 粉艾特号2 = "#fUI/Initials.img/Button/Button5/disabled/0#";

var 粉笑眼号 = "#fUI/Initials.img/Button/Button6/mouseOver/0#";
var 粉笑眼号2 = "#fUI/Initials.img/Button/Button6/disabled/0#";

var 粉点号 = "#fUI/Initials.img/Button/Button7/mouseOver/0#";
var 粉点号2 = "#fUI/Initials.img/Button/Button7/disabled/0#";

var 粉爱心号 = "#fUI/Initials.img/Button/Button8/mouseOver/0#";
var 粉爱心号2 = "#fUI/Initials.img/Button/Button8/disabled/0#";

var 粉五角星号 = "#fUI/Initials.img/Button/Button9/mouseOver/0#";
var 粉五角星号2 = "#fUI/Initials.img/Button/Button9/disabled/0#";
var 金币 = "#fUI/UIWindow.img/QuestIcon/7/0#"; 
var 空白 = "#fUI/UIWindow.img/Skill/CoolTime/15#";  
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
    } var MC = cm.getServerName();
	  var r = Math.ceil(Math.random() * 10);  //随机取一个值
	  var 粉问号赔率 = 1;
	  var 粉斜号赔率 = 1;
	  var 粉小于号赔率 = 1;
	  var 粉大于号赔率 = 1;
	  var 粉感叹号赔率 = 1;
	  var 粉艾特号赔率 = 1;
	  var 粉点号赔率 =1;
	  var 粉笑眼号赔率 =1;
	  var 粉爱心号赔率 = 1;
	  var 粉五角星号赔率 = 1;
	  
	  var 粉色问号下注 = cm.getBossRank("粉色问号下注",2);
	  var 粉斜号下注 = cm.getBossRank("粉斜号下注",2);
	  var 粉小于号下注 = cm.getBossRank("粉小于号下注",2);
	  var 粉大于号下注 = cm.getBossRank("粉大于号下注",2);
	  var 粉感叹号下注 = cm.getBossRank("粉感叹号下注",2);
	  var 粉艾特号下注 = cm.getBossRank("粉艾特号下注",2);
	  var 粉点号下注 = cm.getBossRank("粉点号下注",2);
	  var 粉笑眼号下注 = cm.getBossRank("粉笑眼号下注",2);
	  var 粉爱心号下注 = cm.getBossRank("粉爱心号下注",2);
	  var 粉五角星号下注 = cm.getBossRank("粉五角星号下注",2);
	  
	  
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var selStr = "  "+心+"  "+心+" "+心+" #e#r《"+MC+"娱乐赌博》#n#k  "+心+"  "+心+"  "+心+"\r\n\r\n";

		  if(r==1){
		  selStr += "\t\t\t "+粉问号+"";
		  cm.gainMeso(粉色问号下注*粉问号赔率);
		  cm.setBossRankCount("粉色问号下注",-粉色问号下注);
	}else{
		  selStr += "\t\t\t "+粉问号2+"";
		  cm.setBossRankCount("粉色问号下注",-粉色问号下注);
		  }
		  if(r==2){
		  selStr += " "+粉斜号+"";	
		  cm.gainMeso(粉斜号下注*粉斜号赔率);
		  cm.setBossRankCount("粉斜号下注",-粉斜号下注);		  
	}else{
		  selStr += " "+粉斜号2+"";
		   cm.setBossRankCount("粉斜号下注",-粉斜号下注);
		  }	  
		  if(r==3){
		  selStr += " "+粉小于号+"";	
		  cm.gainMeso(粉小于号下注*粉小于号赔率);
		  cm.setBossRankCount("粉小于号下注",-粉小于号下注);			  
	}else{
		  selStr += " "+粉小于号2+"";
		  cm.setBossRankCount("粉小于号下注",-粉小于号下注);	
		  }		  	  
		  if(r==4){
		  selStr += " "+粉大于号+"\r\n";
		  cm.gainMeso(粉大于号下注*粉大于号赔率);
		  cm.setBossRankCount("粉大于号下注",-粉大于号下注);			  
	}else{
		  selStr += " "+粉大于号2+"\r\n";
		  cm.setBossRankCount("粉大于号下注",-粉大于号下注);
		  }  
		  if(r==5){
		  selStr += "\t\t\t "+粉感叹号+"";
		  cm.gainMeso(粉感叹号下注*粉感叹号赔率);
		  cm.setBossRankCount("粉感叹号下注",-粉感叹号下注);
	}else{
		  selStr += "\t\t\t "+粉感叹号2+"";
		   cm.setBossRankCount("粉感叹号下注",-粉感叹号下注);
		   }
		  selStr += " "+空白+" "+空白+" ";
		  if(r==6){
		  selStr += ""+粉艾特号+"\r\n";	
		  cm.gainMeso(粉艾特号下注*粉艾特号赔率);
		  cm.setBossRankCount("粉艾特号下注",-粉艾特号下注);		  
	}else{
		  selStr += ""+粉艾特号2+"\r\n";
		  cm.setBossRankCount("粉艾特号下注",-粉艾特号下注);
		  }  
		  if(r==7){
		  selStr += "\t\t\t "+粉点号+"";
		  cm.gainMeso(粉点号下注*粉点号赔率);
		  cm.setBossRankCount("粉点号下注",-粉点号下注);
	}else{
		  selStr += "\t\t\t "+粉点号2+"";
		  cm.setBossRankCount("粉点号下注",-粉点号下注);
		  }	
	      if(r==8){
		  selStr += " "+粉笑眼号+"";
		  cm.gainMeso(粉笑眼号下注*粉笑眼号赔率);
		  cm.setBossRankCount("粉笑眼号下注",-粉笑眼号下注);		  
	}else{
		  selStr += " "+粉笑眼号2+"";
		   cm.setBossRankCount("粉笑眼号下注",-粉笑眼号下注);
		   }  
		  if(r==9){
		  selStr += " "+粉爱心号+"";
		  cm.gainMeso(粉爱心号下注*粉爱心号赔率);
		  cm.setBossRankCount("粉爱心号下注",-粉爱心号下注);			  
	}else{
		  selStr += " "+粉爱心号2+"";
		  cm.setBossRankCount("粉爱心号下注",-粉爱心号下注);
		   }		  
		  if(r==10){
		  selStr += " "+粉五角星号+"\r\n\r\n";
		  cm.gainMeso(粉五角星号下注*粉五角星号赔率);
		  cm.setBossRankCount("粉五角星号下注",-粉五角星号下注);		  
	}else{
		  selStr += " "+粉五角星号2+"\r\n\r\n";
		  cm.setBossRankCount("粉五角星号下注",-粉五角星号下注);	}
		
		
		
		
		
		
		
		/*selStr += "\t\t\t "+粉问号2+" "+粉斜号2+" "+粉小于号2+" "+粉大于号2+"\r\n";
		
		selStr += "\t\t\t "+粉感叹号2+" "+空白+" "+空白+" "+粉艾特号2+"\r\n";
		
		selStr += "\t\t\t "+粉艾特号2+" "+粉笑眼号2+" "+粉爱心号2+" "+粉五角星号2+"\r\n";*/
		
		
		if(r==1 && 粉色问号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉色问号下注*粉问号赔率+"#k\r\n";	
}else   if(r==2 && 粉斜号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉斜号下注*粉斜号赔率+"#k\r\n";	
}else   if(r==3 && 粉小于号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉小于号下注*粉小于号赔率+"#k\r\n";			
}else   if(r==4 && 粉大于号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉大于号下注*粉大于号赔率+"#k\r\n";			
}else   if(r==5 && 粉感叹号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉感叹号下注*粉感叹号赔率+"#k\r\n";			
}else   if(r==6 && 粉艾特号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉艾特号下注*粉艾特号赔率+"#k\r\n";		
}else   if(r==7 && 粉点号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉点号下注*粉点号赔率+"#k\r\n";			
}else   if(r==8 && 粉笑眼号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉笑眼号下注*粉笑眼号赔率+"#k\r\n";			
}else   if(r==9 && 粉爱心号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉爱心号下注*粉爱心号赔率+"#k\r\n";			
}else   if(r==9 && 粉五角星号下注>0){
		selStr += "[赌博信息]:#b恭喜你，您中奖了#k,获得金币 #r"+粉五角星号下注*粉五角星号赔率+"#k\r\n";			
}else{
		selStr += "[赌博信息]:#r好遗憾好遗憾哈哈#k\r\n";	
}		
		
		
		
		
		
		
			selStr += "\t\t\t\t #L1#"+箭头+"#e#b再来一次#k#n#l\r\n";
		
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 1:
            cm.dispose();
            cm.openNpc(9100200,0);	
            break;

		}
    }
}