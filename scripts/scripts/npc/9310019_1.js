importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*1+1);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
		if (hour == 0 ||hour == 1 || hour == 2 ||hour == 3 ||hour == 4 ||hour == 5 ||hour == 6 ||hour == 7 ||hour == 8 ||hour == 9 ||hour == 10 ||hour == 11 ||hour == 13 ||hour == 14 ||hour == 15 ||hour == 16 ||hour == 17 ||hour == 18 ||hour == 19 ||hour == 20 ||hour == 22 ||hour == 23 ||hour == 24  ){
			    cm.sendOk("开放时间；#r12;00 - 12;59 #k/#r 21; - 21;59#k");
			    cm.dispose();}
        if (mode == 0) {
            cm.sendOk("放弃了？");
            cm.dispose();
            return;
        }var MC = cm.getServerName();
		 var MZ = cm.getChar().getName();
		
        if (mode == 1)
            status++;
        else
            status--;
  
			if (status == 0)
				{
					
            cm.sendGetText("┏━━━━━━━━━━━━━━━━━━━━━━┓\r\n\t\t"+MC+" 擂台争霸赛 - 青龙之门\r\n┗━━━━━━━━━━━━━━━━━━━━━━┛\r\n         \r\n- 开启时间：#b#e12;00 - 12;59/21; - 21;59#n\r\n#r- 报名方式；请在填写参加擂台赛的角色名#k \r\n#r- 报名格式；七宝（角色名）#k\r\n\r\n- #d报名输入框；");
        } else if (status == 1) {
               fee = cm.getText();
            cm.sendYesNo("- 再次确认你是否填写正确？\r\n			#r" + fee + "#k ");
        } else if (status == 2) {
			
			
			
      if (cm.getText() == ""+MZ+"" ){//////////////判断口令是否正确
        cm.warp(701000201,0)
		cm.dispose();
		cm.serverNotice("[擂台争霸]："+ cm.getChar().getName() +" 参加"+MC+" - 擂台争霸赛 - 青龙之门区域 - 。"); 

            } else {
                 if (chance <= 1) { 
                         cm.sendOk("错误错误错误~~~~~~~~·");
						// cm.serverNotice("[擂台争霸]："+ cm.getChar().getName() +" 参加"+MC+" - 擂台争霸赛 - 青龙之门区域 - 。"); 
	                    cm.dispose(); 

	                } 

            }
        }
    }
}
