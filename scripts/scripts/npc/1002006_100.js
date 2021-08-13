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
	text += "#d合成-- #r★3000HP血衣★需要以下物品：\r\n#v4000016##d#z4000016# 100个\r\n#v4000001##d#z4000001# 100个\r\n#v4000015##d#z4000015# 100个 金币2000W\r\n\r\n~\r\n"
	text += "\r\n#L1##d我收集了以上物品。确定制作3000HP血衣";//七天
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1012058,1)){
			cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4000016,100) && cm.haveItem(4000001,100) && cm.haveItem(4000015,100) && cm.getMeso() >= 20000000){
			cm.gainItem(4000016, -100);
			cm.gainItem(4000001, -100);
			cm.gainItem(4000015, -100);
			cm.gainMeso(-20000000);
cm.gainItem(1113035,10,10,10,10,3000,3000,0,0,0,0,0,0,0,0);
            cm.sendOk("换购成功！");
            cm.dispose();
cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]成功制作3000HP血衣，恭喜！！");
			}else{
             cm.sendOk("无法制作，或许你材料不足");
            cm.dispose();
			}
		}
    }
}




