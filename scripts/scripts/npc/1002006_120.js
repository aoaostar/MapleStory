function start() {
status = -1;

action(1, 0, 0);
}
function action(mode, type, selection) {
            if (mode == -1) {
                cm.dispose();
            }
            else {
                if (status >= 0 && mode == 0) {
                
   cm.sendOk("感谢使用.");
   cm.dispose();
   return;                    
                }
                if (mode == 1) {
   status++;
  }
  else {
   status--;
  }
          if (status == 0) {
	var tex2 = "";	
	var text = "";
	for(i = 0; i < 10; i++){
		text += "";
	}				
	text += "#d合成-- #r★8000HP血衣★需要以下物品：\r\n#v1113036##d5000HP血衣 1个\r\n#v4000151##d#z4000151# 50个\r\n#v4000152##d#z4000152# 50个\r\n#v4000406##d#z4000406# 100个\r\n#v4000402##d#z4000402# 100个\r\n#v4000407##d#z4000407# 100个 金币5000W\r\n\r\n~\r\n"
	text += "\r\n#L1##d我收集了以上物品。确定制作8000HP血衣";//七天
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1012060,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4000151,50) && cm.haveItem(4000152,50) && cm.haveItem(1113036,1) && cm.haveItem(4000406,100) && cm.haveItem(4000402,100) && cm.haveItem(4000407,100) && cm.getMeso() >= 50000000){
			cm.gainItem(4000151, -50);
			cm.gainItem(4000152, -50);
			cm.gainItem(4000406, -100);
			cm.gainItem(4000402, -100);
			cm.gainItem(4000407, -100);
			cm.gainItem(1113036, -1);
			cm.gainMeso(-50000000);
cm.gainItem(1113034,30,30,30,30,8000,8000,5,5,0,0,0,0,0,0);
            cm.sendOk("换购成功！");
            cm.dispose();
cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]成功制作8000HP血衣，恭喜！！");
			}else{
            cm.sendOk("无法制作，或许你材料不足\r\n");
            cm.dispose();
			}
		}
    }
}




