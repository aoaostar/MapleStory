
var minPartySize = 3;//3
var maxPartySize = 6;
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
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
       // if (status === 0) {
            //cm.sendOk("这是怪物大擂台。你要和你的朋友们组队进去哦，但是最终的胜者只有一个，所以你要加油以最快的速度拿到道具并且领取奖励~\r\n\r\n领取奖励的方法#r @gwlt @怪物擂台~");
      //  }
         if (status === 0) {
             if (cm.getMap(221024500).getCharactersSize() > 0) { //判断地图是否有人
                    cm.sendOk("该擂台已经有人了哦~");
                    cm.dispose();
                }

            if (cm.getParty()==null) {//判断组队
                cm.sendOk("请组队~并保证所有队友在这里~");
                 cm.dispose();
            }else{
            //     var party = cm.getParty().getMembers();
            //    if (party.size() < minPartySize || party.size() > maxPartySize || inMap < minPartySize) {
            //                   cm.sendOk("请确认你的组队员：\r\n\r\n#b1、组队员必须要" + minPartySize + "人以上，" + maxPartySize + "人以下");
            //                   cm.dispose();
            //                 }else{
            var players = cm.getMap().getCharactersThreadsafe();
             for (var i = 0; i < players.size(); i++) {
             var cPlayer = players.get(i);
             if (!cPlayer.haveItem(2000005,0)){//判断道具并传送
                 
                 cm.sendOk("带上超级药水~");
                //  cm.dispose();
              }else{
                var map = cm.getMap(221024500);
              //   cPlayer.removeItem(4001008, 1);
                cPlayer.changeMap(map, map.getPortal(1));
                // cm.dispose();
                 }
          
                    }
                      cm.dispose();
            
         }
                        }
            
        
        }
    }
// }
                        
                  
