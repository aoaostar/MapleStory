importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*1+1);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("你不知道指令？");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
  
			if (status == 0)
				{
					
            cm.sendGetText("\r\n-#r "+year+" 年 "+month+" 月 "+day+" 日 #b的口令请留意群内。#k\r\n\r\n 输入口令;\r\n");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("- 你输入的口令是； #r" + fee + "#k ？");
        } else if (status == 2) {
            if (cm.getBossLog("mrkl") == 0 && cm.getText() == "撸啊撸，不撸不舒服斯基，啊撸啊撸" ){//////////////口令
        cm.setBossLog("mrkl");
		cm.setBossLog("zymxd");
		cm.sendOk("领取成功 ");
		cm.dispose();
		
		cm.serverNotice("[每日口令]："+ cm.getChar().getName() +"输入了正确的口令，成功领取"+year+"年"+month+"月"+day+"日的奖励。"); 

            } else {
                 if (chance <= 1) { 
				         
                         cm.sendOk("请输入正确的口令，或者你已经领取了。")
	                    cm.dispose(); 

	                } 

            }
        }
    }
}
