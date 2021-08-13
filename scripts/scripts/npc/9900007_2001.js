importPackage(net.sf.odinms.client);
var status = 0;
var fee;
var chance = Math.floor(Math.random()*42+1);

function start() {
    status = -30;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -30) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
			cm.warp(910000000);
            return;
        }var MC = cm.getServerName();
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
			   
       cm.sendNext("┏\r\n    #e#b"+MC+" - #k#n擂台争霸\r\n\t\t\t\t\t\t\t┛\r\n\r\n    #v4031980# #e#t4031980##k#n [#r 1#k /#b #c4031980##k ]");
          

    	  
 
        } else if (cm.haveItem(4031980,1)) {
			var jy = cm.getPlayer().getExp();
			var jy10 = cm.getPlayer().getExp()/10;
			var jy20 = cm.getPlayer().getExp()/20;
			var jy30 = cm.getPlayer().getExp()/30;
			 var wj =  cm.获取指定地图玩家数量(701000201)/2;
			  var jb = cm.getMeso();
               var dq = cm.getPlayer().getCSPoints(1);
			   var dq10 = cm.getPlayer().getCSPoints(1)/10;
			    var xq = cm.获取当前星期()/2;
                  var jb10 = cm.getMeso()/10;
				  var jb20 = cm.getMeso()/20;
				  var jb30 = cm.getMeso()/30;
				  var fjb = cm.获取指定地图玩家数量(701000201)*2000/cm.获取指定地图玩家数量(701000201);;
                //  var rq = cm.getfame();
				   
				   
			   
                  if (chance <= 1) { //获得金币
				       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jb10*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jb10*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 2) { //失去金币
			           cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb10*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb10*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
					   }
	         else if (chance == 3) { //获得经验
			           cm.spawnMonster(9302000,10);
			           cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jy10*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jy10*wj).toFixed(0)+" 经验。还杀了个人。"); 
	                   cm.dispose();
	                } 
			 else if  (chance == 4) { //杀人
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy10*wj);
					   cm.杀人(701000201,2);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy10*wj).toFixed(0)+" 经验。还杀了个人。"); 
	                   cm.dispose();
	                } 
			else if (chance == 5) { //获得金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jb20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 6) { //失去金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-3);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
					   }
	         else if (chance == 7) { //获得经验
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jy20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jy20*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
			 else if  (chance == 8) { //获得经验
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy20*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
            else if  (chance == 9) { //失去点券
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*dq10*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*dq10*wj).toFixed(0)+" 点券。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 10) { //得到点券
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*dq10*wj);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上得到了 "+(xq*dq10*wj).toFixed(0)+" 点券。"); 
	                   cm.dispose();
	                } 
			else if (chance == 11) { //获得金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jb20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 12) { //失去金币
			           cm.spawnMonster(9302000,10);
                       cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。还杀了个人。"); 
	                   cm.dispose();
					   }
			else if (chance == 13) { //失去金币
                       cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。还杀了个人。"); 
	                   cm.dispose();
					   }
			else if (chance == 14) { //全服发金币
			           
                       cm.给全服发点卷(1000000,3);
	                   cm.gainItem(4031980,-30);
					   cm.setBossRankCount("擂台赛",5);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上在所有玩家发放1000000金币。"); 
	                   cm.dispose();
					   }
			else if (chance == 15) { //杀市场
			           cm.spawnMonster(9302000,10);
                       cm.杀人(910000000,2);
	                   cm.gainItem(4031980,-30);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上向市场的玩家发了攻击，杀害了2个玩家。"); 
	                   cm.dispose();
					   }
			else if  (chance == 16) { //获得经验
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy30*wj);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy30*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 17) { //杀所有人
                       cm.杀人(701000201,90);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上杀了所有人。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 18) { //给超级
                       cm.给指定地图发物品(701000201,2000005,100);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上给了所有人100瓶超级药水。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 19) { //给金币
                       cm.给指定地图发点卷(701000201,1,3);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上把自己的 1 金币分给了大家。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 20) { //飞鱼
                       cm.spawnMonster(2230107,50);
					   cm.setBossRankCount("擂台赛",5);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上射出了50只飞鱼。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 21) { //飞鱼
			           cm.spawnMonster(2230107,50);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上射出了50只飞鱼。"); 
	                   cm.dispose();
	                }
            else if  (chance == 21) { //蜈蚣
			           cm.spawnMonster(5220004,10);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上召唤出了10只巨型蜈蚣。"); 
	                   cm.dispose();
	                }	

//////////////////////////////////
            else if (chance == 22) { //失去金币
			           cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb10*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb10*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
					   }
	         else if (chance == 23) { //获得经验
			           cm.spawnMonster(9302000,10);
			           cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jy10*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jy10*wj).toFixed(0)+" 经验。还杀了个人。"); 
	                   cm.dispose();
	                } 
			 else if  (chance == 24) { //杀人
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy10*wj);
					   cm.杀人(701000201,2);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy10*wj).toFixed(0)+" 经验。还杀了个人。"); 
	                   cm.dispose();
	                } 
			else if (chance == 25) { //获得金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jb20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 26) { //失去金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
					   }
	         else if (chance == 27) { //获得经验
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jy20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jy20*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
			 else if  (chance == 28) { //获得经验
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy20*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
            else if  (chance == 29) { //失去点券
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*dq10*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*dq10*wj).toFixed(0)+" 点券。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 30) { //得到点券
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*dq10*wj);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上得到了 "+(xq*dq10*wj).toFixed(0)+" 点券。"); 
	                   cm.dispose();
	                } 
			else if (chance == 31) { //获得金币
                       cm.spawnMonster(9302000,10);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(xq*jb20*wj);
					   cm.setBossRankCount("擂台赛");
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上获得了 "+(xq*jb20*wj).toFixed(0)+" 金币。"); 
	                   cm.dispose();
	                } 
	         else if (chance == 32) { //失去金币
			           cm.spawnMonster(9302000,10);
                       cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-3);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。还杀了个人。"); 
	                   cm.dispose();
					   }
			else if (chance == 33) { //失去金币
			           cm.spawnMonster(8220007,10);
                       cm.杀人(701000201,2);
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jb20*wj);
					   cm.setBossRankCount("擂台赛",-2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jb20*wj).toFixed(0)+" 金币。还杀了个人。"); 
	                   cm.dispose();
					   }
			else if (chance == 34) { //全服发金币
			           
                       cm.给全服发点卷(1000000,3);
	                   cm.gainItem(4031980,-30);
					   cm.setBossRankCount("擂台赛",5);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上在所有玩家发放1000000金币。"); 
	                   cm.dispose();
					   }
			else if (chance == 35) { //杀市场
			           cm.spawnMonster(9302000,10);
                       cm.杀人(910000000,2);
	                   cm.gainItem(4031980,-30);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上向市场的玩家发了攻击，杀害了2个玩家。"); 
	                   cm.dispose();
					   }
			else if  (chance == 36) { //获得经验
	                   cm.gainItem(4031980,-30);
					   cm.gainMeso(-xq*jy30*wj);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上失去了 "+(xq*jy30*wj).toFixed(0)+" 经验。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 37) { //杀所有人
			           cm.spawnMonster(7220001,10);         
                       cm.杀人(701000201,90);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上杀了所有人。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 38) { //给超级
                       cm.给指定地图发物品(701000201,2000005,100);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上给了所有人100瓶超级药水。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 39) { //给金币
                       cm.给指定地图发点卷(701000201,1,3);
					   cm.setBossRankCount("擂台赛",2);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上把自己的 1 金币分给了大家。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 40) { //飞鱼
			           cm.spawnMonster(8220007,10);
                       cm.spawnMonster(2230107,50);
					   cm.setBossRankCount("擂台赛",5);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上射出了50只飞鱼。"); 
	                   cm.dispose();
	                } 
			else if  (chance == 41) { //飞鱼
			           cm.spawnMonster(2230107,50);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上射出了50只飞鱼。"); 
	                   cm.dispose();
	                }
            else if  (chance == 42) { //蜈蚣
			           cm.spawnMonster(5220004,10);
					   cm.setBossRankCount("擂台赛",1);
                       cm.serverNotice("[擂台争霸]：玩家 "+ cm.getChar().getName() +" 在擂台争霸上召唤出了10只巨型蜈蚣。"); 
	                   cm.dispose();
	                }				
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					cm.dispose();
					} else   {
                cm.sendOk("需要 #v4031980# #e#t4031980##k#n x 30 ");
                cm.dispose();
					
                }
            }
        }
