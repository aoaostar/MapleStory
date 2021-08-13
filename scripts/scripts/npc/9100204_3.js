importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*30+1);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("#i3994125# - 我会继续在这里等着你的。");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
         if (status == 0) {
            cm.sendGetText("请输入你要下注的金币数量。");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("你确定要下注 #r" + fee + "#k 金币吗 ？");
        } else if (status == 2) {
            if (cm.getMeso() < fee) {
                cm.sendOk("哦呵，不好意思你没那么多钱了。");
                cm.dispose();

        } else {
                 if (chance <= 1) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果1","1");
						  
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
	                else if (chance == 2) { 
	                    cm.gainMeso(-fee);
						cm.setBossRankCount("水果3","1");
						cm.gainMeso(fee * 3);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 3) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果2","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 4) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果1","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 5) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果2","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 6) { 
	                    cm.gainMeso(-fee); 
					    cm.setBossRankCount("水果2","1");
						
	                    cm.dispose(); 
					    cm.openNpc(9100204,0);
	                } 
					else if (chance == 7) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果3","1");
						cm.gainMeso(fee * 3);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 8) { 
	                    cm.gainMeso(-fee); 
					    cm.setBossRankCount("水果1","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 9) { 
	                    cm.gainMeso(-fee); 
					    cm.setBossRankCount("水果3","1");
						cm.gainMeso(fee * 3);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
					else if (chance == 10) { 
	                    cm.gainMeso(-fee); 
					    cm.setBossRankCount("水果4","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
	                else if (chance == 11) { 
	                    cm.gainMeso(-fee); 
						cm.setBossRankCount("水果5","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
	                } 
                    else if (chance == 12) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果6","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 13) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果6","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
				    }	
					else if (chance == 14) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果1","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 15) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果6","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 16) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果6","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 17) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果2","1");
						cm.gainMeso(fee * 2);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 18) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果1","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 19) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果3","1");
						cm.gainMeso(fee * 3);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 20) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果9","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}
					else if (chance == 21) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果1","1");
						
	                    cm.dispose(); 
						cm.openNpc(9100204,0);	
					}
					else if (chance == 22) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果12","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);	
					}
					else if (chance == 23) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果9","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);	
                    }
					else if (chance == 24) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果6","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
                    }
					else if (chance == 25) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果7","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
                    }
					else if (chance == 26) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果3","1");
						cm.gainMeso(fee * 3);
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}	
                    else if (chance == 27) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果10","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}	
                    else if (chance == 28) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果12","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);
					}	
                    else if (chance == 29) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果11","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);	
					}	
                    else if (chance == 30) {
                        cm.gainMeso(-fee); 
				        cm.setBossRankCount("水果1","1");
	                    cm.dispose(); 
						cm.openNpc(9100204,0);						
                    }						
                }
				
            }
        }
    }

