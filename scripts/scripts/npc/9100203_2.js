importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*9+1);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("摇一摇，中大奖。");
            cm.dispose();
            return;
        }
		if(cm.getBossLog("1") == 0 ){
    		cm.sendOk("你第一个号码还没摇呢。");
    		cm.dispose();
    		return;
			 }
		
    	if(cm.getBossLog("2") > 0 ){
    		cm.sendOk("第二个号码已经摇到了。");
    		cm.dispose();
    		return;
			 }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendAcceptDecline("这里是第二个号码摇号。");
        } else if (status == 1) {
            cm.sendAcceptDecline("你确定要摇吗？");

        } else if (status == 2) {
            if (cm.getMeso() < fee) {
                cm.sendOk("哦呵，不好意思你没那么多钱了，去赚点钱再来吧，这可不是免费的,快去当掉一些东西再来吧!");
                cm.dispose();
            //} else if (cm.getMeso() < 100) {
               // cm.sendOk("请先确定包里的金币不能低于#r50000000!");
              //  cm.dispose();
           // } else if (cm.getText() < 100) {
               // cm.sendOk("#i3994125# 低于#r10000000#k金币？那你还是去别的赌博机玩吧。");
               // cm.dispose();
            } else {
                 if (chance <= 1) { 
	                     
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 1 #k"); 
                        cm.dispose(); 
	                } 
	                else if (chance == 2) { 
	                     
	                     cm.setBossLog("2");
						 cm.setBossLog("2");
	                     cm.sendNext("你抽取到了号码#r 2 #k"); 
	                     cm.dispose(); 
	                } 
					else if (chance == 3) { 
	                     
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 3 #k"); 
	                    cm.dispose(); 
	                } 
					else if (chance == 4) { 
	                     
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 4 #k"); 
	                    cm.dispose(); 
				   }else if (chance == 5) { 
	                     
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 5 #k"); 
	                    cm.dispose(); 
					}else if (chance == 6) { 
	                    
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 6 #k"); 
	                    cm.dispose(); 
					}else if (chance == 7) { 
	                   
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 7 #k"); 
	                    cm.dispose(); 
					}else if (chance == 8) { 
	                    
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
	                    cm.sendNext("你抽取到了号码#r 8 #k"); 
	                    cm.dispose(); 
					}else if (chance == 9) { 
	                    
	                    cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						cm.setBossLog("2");
						
	                    cm.sendNext("你抽取到了号码#r 9 #k"); 
	                    cm.dispose(); 



                }
            }
        }
    }
			
}
