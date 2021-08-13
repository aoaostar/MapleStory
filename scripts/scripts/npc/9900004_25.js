//////////////////////////////
//ZEV*自由冒险岛*最具创意////

///////////////////////////
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
    if (status   <= 0 && mode   <= 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	
    if ( cm.getMapId() == 1) {
            cm.sendOk(" 有什么我可以帮到你的呢？");
            cm.dispose();
        }


  
    else if (status   <= 0) {

    var  
	    selStr = "\r\n";
		selStr += "\t\t\t#r您的当前在线时间："+cm.查询今日在线时间()+" 分钟#k#n\r\n";
		
		 selStr += "#L1#"+箭头+"#b#e60分钟在线奖励 （2000抵用券）#l#k#n\r\n";
		 selStr += "#L2#"+箭头+"#b#e120分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L3#"+箭头+"#b#e180分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L4#"+箭头+"#b#e240分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L5#"+箭头+"#b#e300分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L6#"+箭头+"#b#e360分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L7#"+箭头+"#b#e420分钟在线奖励（2000抵用券）#l#k#n\r\n";
		 selStr += "#L8#"+箭头+"#b#e480分钟在线奖励（2000抵用券）#l#k#n\r\n";



		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {

			case 1:	
		  if(cm.查询今日在线时间()>=60 && cm.getBossLog("在线奖励60分钟")== 0){ 
			cm.setBossLog("在线奖励60分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 2:	
		  if(cm.查询今日在线时间()>=120 && cm.getBossLog("在线奖励120分钟")== 0){ 
			cm.setBossLog("在线奖励120分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 3:	
		  if(cm.查询今日在线时间()>=180 && cm.getBossLog("在线奖励180分钟")== 0){ 
			cm.setBossLog("在线奖励180分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 4:	
		  if(cm.查询今日在线时间()>=240 && cm.getBossLog("在线奖励240分钟")== 0){ 
			cm.setBossLog("在线奖励240分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 5:	
		  if(cm.查询今日在线时间()>=300 && cm.getBossLog("在线奖励300分钟")== 0){ 
			cm.setBossLog("在线奖励300分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 6:	
		  if(cm.查询今日在线时间()>=360 && cm.getBossLog("在线奖励360分钟")== 0){ 
			cm.setBossLog("在线奖励360分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 7:	
		  if(cm.查询今日在线时间()>=420 && cm.getBossLog("在线奖励420分钟")== 0){ 
			cm.setBossLog("在线奖励420分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			case 8:	
		  if(cm.查询今日在线时间()>=480 && cm.getBossLog("在线奖励480分钟")== 0){ 
			cm.setBossLog("在线奖励480分钟");
			  cm.sendOk("领取在线奖励成功");
			  cm.gainDY(2000);
			  cm.dispose();
			} else{
			  cm.sendOk("你想作死？还没到时间或者你已经领取过了吧！");
              cm.dispose();
			}
			break;
			
			
			
			
			
			
			
			
			
			
			
			
		}
    }
 }