//odinms_MS
importPackage(net.sf.odinms.tools);
importPackage(net.sf.odinms.client);

var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			
			cm.sendSimple("\t\t\t#e#r欢迎来到BOSS材料换购系统中心#n\r\n#d====================================================\r\n#d目前账户剩余金币:"+ cm.getPlayer().getMeso() + "\r\n====================================================#b#n\r\n#L2##b#e兑换1个时空碎片D 需要金币500w#v4031179##b#n\r\n#L3##b#e兑换1个梦幻主题熊 需要金币500w#v4032246##b#n\r\n#L4##b#e兑换1个火焰之眼 需要金币500w#v4001017#\r\n#L5##b#e兑换1个玩具奖牌 需要金币500w#v4031172#\r\n");
			} else if (status == 1) {
                  if (selection == 1) {
		  cm.sendOk("\t\t\t#e#b点券的获取方法（新人必看）#n#d\r\n====================================================#k\r\n本服一切装备都可以靠努力获得，加油！#k#d\r\n====================================================#k\r\n想要获得更多信息咨询，请加入我们的交流群一起讨论#r#d\r\n");
	          cm.dispose();
                  }else if(selection == 2){ 	           
			    if(cm.getMeso() >= 5000000){
                cm.sendOk("#b#e恭喜您获得#r#e【时空碎片D 1个】");
                cm.gainItem(4031179,1);
			    cm.gainMeso(-5000000);
                cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]兑换时空碎片D 1个成功！！");
                cm.dispose();		
                }else{ 
                cm.sendOk("#b您没有足够的金币进行购买,请努力吧.");
                cm.dispose();
                  }
                  }else if(selection == 3){ 	           
			   if(cm.getMeso() >= 5000000){
                cm.sendOk("#b#e恭喜您获得#r#e【梦幻主题小熊 1个】.");
                cm.gainItem(4032246,1);
				
                cm.gainMeso(-5000000);
                cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]兑换梦幻主题小熊 1个成功！！");
                cm.dispose();		
                }else{ 
                cm.sendOk("#b您没有足够的金币进行购买,请努力收集吧.");
                cm.dispose();
                  }
                  }else if(selection == 4){ 	           
			    if(cm.getMeso() >= 5000000){
                cm.sendOk("#b#e恭喜您获得#r#e【火焰之眼 1个】.");
                cm.gainItem(4001017,1);
				
                cm.gainMeso(-5000000);
                cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]兑换火焰之眼 1个成功！！");
                cm.dispose();		
                }else{ 
                cm.sendOk("#b您没有足够的金币进行购买,请努力收集吧.");
                cm.dispose();
                  }
                  }else if(selection == 5){ 	           
			  if(cm.getMeso() >= 5000000){
                cm.sendOk("#b#e恭喜您获得#r#e【玩具奖牌 1个】.");
                cm.gainItem(4031172,1);
				
                cm.gainMeso(-5000000);
                cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]兑换玩具奖牌 1个成功！！");
                cm.dispose();		
                }else{ 
                cm.sendOk("#b您没有足够的金币进行购买,请努力收集吧.");
                cm.dispose();
                  }
                  }else if(selection == 6){ 	           
			    if(cm.getPlayer().getNX() > 50000){
                cm.sendOk("#b#e恭喜您获得#r#e【海盗小暴君一套】.");
                cm.gainItem(1132173,1);
				cm.gainItem(1102480,1);
				cm.gainItem(1072741,1);
                cm.gainNX(-50000);
                cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]兑换海盗小暴君一套成功！！");
                cm.dispose();		
                }else{ 
                cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
                cm.dispose();
                  }
                }else if(selection == 7){ 	           
					if(cm.getPlayer().getNX() > 30000){
						cm.sendOk("#r#e恭喜您获得四维20+双攻20的属性点装-透明手套.");
						cm.gainItem(1082102,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
						cm.gainNX(-30000);
						cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]获得了四维20+双攻20的属性点装-透明手套！！");
						cm.dispose();		
					}else{ 
						cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
						cm.dispose();
					}
				}else if(selection == 8){ 	           
					if(cm.getPlayer().getNX() > 30000){
						cm.sendOk("#r#e恭喜您获得四维20+双攻20的属性点装-透明鞋.");
						cm.gainItem(1072153,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
						cm.gainNX(-30000);
						cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]获得了四维20+双攻20的属性点装-透明鞋！！");
						cm.dispose();		
					}else{ 
						cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
						cm.dispose();
						
					}
				}else if(selection == 11){ 	           
					if(cm.getPlayer().getNX() > 30000){
						cm.sendOk("#r#e恭喜您获得四维20+双攻20的属性点装-透明披风.");
						cm.gainItem(1102039,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
						cm.gainNX(-30000);
						cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]获得了四维20+双攻20的属性点装-透明披风！！");
						cm.dispose();		
					}else{ 
						cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
						cm.dispose();	
					}
				} else if(selection == 9){ 	           
					if(cm.getPlayer().getNX() > 30000){
						cm.sendOk("#r#e恭喜您获得四维20+双攻20的属性点装-透明眼饰.");
						cm.gainItem(1022048,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
						cm.gainNX(-30000);
						cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]获得了四维20+双攻20的属性点装-透明眼饰！！");
						cm.dispose();		
					}else{ 
						cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
						cm.dispose();
					}
				} else if(selection == 10){ 	           
					if(cm.getPlayer().getNX() > 30000){
						cm.sendOk("#r#e恭喜您获得四维20+双攻20的属性点装-透明面具.");
						cm.gainItem(1012057,20,20,20,20,0,0,20,20,0,0,0,0,0,0);
						cm.gainNX(-30000);
						cm.喇叭(3,"恭喜玩家[" + cm.getChar().getName() + "]获得了四维20+双攻10的属性点装-透明面具！！");
						cm.dispose();		
					}else{ 
						cm.sendOk("#b您没有足够的点券进行购买,请努力收集吧.");
						cm.dispose();
					}
				}
	}
    }
}
