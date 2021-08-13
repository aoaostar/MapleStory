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
	text += "#d合成-- #r★5000HP血衣★需要以下物品：\r\n#v1113035##d3000HP血衣 1个\r\n#v4000072##d#z4000072# 150个 \r\n#v4000071##d#z4000071# 150个\r\n#v4000070##d#z4000070# 150个 金币5000W\r\n\r\n~\r\n"
	text += "\r\n#L1##d我收集了以上物品。确定制作5000HP血衣";//七天
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1012059,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4000070,150) && cm.haveItem(4000072,150) && cm.haveItem(4000071,150) && cm.haveItem(1113035,1) && cm.getMeso() >= 50000000){
			cm.gainItem(4000070, -150);
			cm.gainItem(4000071, -150);
			cm.gainItem(4000072, -150);
			cm.gainMeso(-20000000);
			cm.gainItem(1113035, -1);
cm.gainItem(1113036,20,20,20,20,5000,5000,0,0,0,0,0,0,0,0);
            cm.sendOk("换购成功！");
            cm.dispose();
cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]成功制作5000HP血衣，恭喜！！");
			}else{
            cm.sendOk("无法制作，或许你材料不足\r\n");
            cm.dispose();
			}
		}
    }
}




