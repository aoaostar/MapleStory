importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*14+1);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
			cm.warp(910000000);
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
			var rs = cm.获取指定地图玩家数量(209000015);
			var jy = cm.getPlayer().getExp();
			 var wj =  cm.获取指定地图玩家数量(209000015);
			  var jb = cm.getMeso();
			  var hulu = cm.haveItem(4032217,20);

            cm.sendYesNo("#b#v4032217##k x 20  \r\n#r注意；一次会消耗所有糖葫芦，建议刚好20个的时候交给我。#k\r\n#r点击不是回到市场#k\r\n当前擂台人数;#r"+rs+"#k\r\n金币平均收益；#d"+((wj*10000)+(jb/10))+" #k\r\n金币平均损失；#d"+jb/10*wj+" #k\r\n经验平均收益；#d"+jy/50*wj+" #k\r\n经验平均损失；#d"+jy/10*wj+" #k\r\n");
          

    	  
 
        } else if (cm.haveItem(4032217,1) && cm.getPlayer().getJob() < 2100) {
			var jy = cm.getPlayer().getExp()
			 var wj =  cm.获取指定地图玩家数量(209000015)
			  var jb = cm.getMeso()
			   
                 if (chance <= 1) { //获得金币
	                   cm.gainItem(4032217,-2000);
					   cm.gainMeso(wj*10000+jb/10);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+((wj*10000)+(jb/10))+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 2) { //丢失金币
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(-jb/10*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/10*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
			else if (chance == 3) { //经验收益
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(jy/50*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/50*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 4) { //经验损失
	                   cm.gainItem(4032217,-2000);  
	                   cm.gainMeso(-jy/10*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/10*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }		
            else if (chance == 5) { //杀一人
	                   cm.gainItem(4032217,-2000); 
	                   cm.杀人(209000015,1);

                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上一怒杀一人，千里不流行。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }							
			else if (chance == 6) { //经验损失
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jy/8*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/8*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }			
            else if (chance == 7) { //杀两人
	                   cm.gainItem(4032217,-2000);
	                   cm.杀人(209000015,2);

                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上一怒杀两人，千里不流行。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 8) { //经验损失
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jy/11*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/11*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 9) { //金币收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(wj*12000+jb/9);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+((wj*12000)+(jb/9))+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 10) { //经验收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(jy/30*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/30*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 11) { //经验收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(jy/60*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/60*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 12) { //丢失金币
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(-jb/20*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/20*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 13) { //丢失金币
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jb/15*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/15*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }cm.dispose();
			} else if (cm.haveItem(4032217,40) && cm.getPlayer().getJob() > 2100) {
			var jy = cm.getPlayer().getExp()
			 var wj =  cm.获取指定地图玩家数量(209000015)
			  var jb = cm.getMeso()
			   
                 if (chance <= 1) { //获得金币
	                   cm.gainItem(4032217,-2000);
					   cm.gainMeso(wj*10000+jb/10);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+((wj*10000)+(jb/10))+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 2) { //丢失金币
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(-jb/10*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/10*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
			else if (chance == 3) { //经验收益
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(jy/50*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/50*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 4) { //经验损失
	                   cm.gainItem(4032217,-2000);  
	                   cm.gainMeso(-jy/10*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/10*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }		
            else if (chance == 5) { //杀一人
	                   cm.gainItem(4032217,-2000); 
	                   cm.杀人(209000015,1);

                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上一怒杀一人，千里不流行。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }							
			else if (chance == 6) { //经验损失
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jy/8*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/8*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }			
            else if (chance == 7) { //杀两人
	                   cm.gainItem(4032217,-2000);
	                   cm.杀人(209000015,2);

                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上一怒杀两人，千里不流行。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 8) { //经验损失
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jy/11*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/11*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 9) { //金币收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(wj*12000+jb/9);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+((wj*12000)+(jb/9))+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 10) { //经验收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(jy/30*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/30*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }	
            else if (chance == 11) { //经验收益
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(jy/60*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+jy/60*wj+" 经验。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 12) { //丢失金币
	                   cm.gainItem(4032217,-2000);
	                   cm.gainMeso(-jb/20*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/20*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }
            else if (chance == 13) { //丢失金币
	                   cm.gainItem(4032217,-2000); 
	                   cm.gainMeso(-jb/15*wj);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上丢失了 "+jb/15*wj+" 金币。"); 
	                   cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.spawnMonster(9300340,-69,-221);cm.dispose(); 
	                }cm.dispose();
					} else   {
                cm.sendOk("给我20根 #i4032217# 我要吃我要吃。战神需要 40根 #i4032217#");
                cm.dispose();
					
                }
            }
        }
    

