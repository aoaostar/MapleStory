
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
			    if (cm.getMapId() > 780000007 || cm.getMapId() < 779999999) {//cm.getMapId() > 209000001 || cm.getMapId() <= 209000000
            cm.sendOk(" 此命令只在怪物擂台可用 。");
            cm.dispose();
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status === 0) {
            cm.sendOk("这是怪物大擂台的领奖处，你已经打到了指定的通行证了吗？你们当中只有一个人可以领取奖励哦\r\n\r\n，我要#i4001008# x 100");
        }
         if (status === 1) {
           //  if (cm.getMap(780000000).getCharactersSize() > 0) { //判断地图是否有人
                  //  cm.sendOk("地图已经有人咯~");
                   // cm.dispose();
              //  }
            if (cm.getParty()==null) {//判断组队
                cm.sendOk("请组队~");
                 cm.dispose();
            } else{
            var players = cm.getMap().getCharactersThreadsafe();
             for (var i = 0; i < players.size(); i++) {
             var cPlayer = players.get(i);
             if (!cPlayer.haveItem(4001008,100)){//判断道具并传送
                 cm.sendOk("你没有要给我的道具");
                 cm.dispose();
              }else{
                 cm.dispose();
                var map = cm.getMap(910000000);
                cm.gainItem(4001008, -10000);
				cm.worldMessage(6,"【怪物擂台】；玩家 "+cm.getName()+" 率先打到了指定的通行证，成功领取奖励。");
                cPlayer.changeMap(map, map.getPortal(1));
                
                 }
                    }
            
         }
        }
    }
}
                        
                  
