var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	    
	if (cm.getPlayer().getLevel() < 10) {
        cm.sendOk("你的等级未达到10级，因此未能解锁本功能");
        cm.dispose();
		 }
  
    else if (status == 0) {
        var selStr = "#r━━━━━━━━━━天球雪屋━━━━━━━━━━━#k\r\n\r\n";
        if (cm.getMapId() == 209000002){ 
          selStr += "\t\t\t#L1#召唤天球#l #L90#离开这里#l\r\n\r\n";}
   else if (cm.getMapId() == 209000003){ 
          selStr += "\t\t\t#L2#召唤天球#l #L90#离开这里#l\r\n\r\n";}	
   else if (cm.getMapId() == 209000004){ 
          selStr += "\t\t\t#L3#召唤天球#l #L90#离开这里#l\r\n\r\n";}	
   else if (cm.getMapId() == 209000005){ 
          selStr += "\t\t\t#L4#召唤天球#l #L90#离开这里#l\r\n\r\n";}	
   else if (cm.getMapId() == 209000006){ 
          selStr += "\t\t\t#L5#召唤天球#l #L90#离开这里#l\r\n\r\n";}			  
		  


		   
		  
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 90:
            cm.dispose();
			cm.warp(209080100,0);
        break;	
			
        case 1:
        if (cm.haveItem(4031497,1)&& cm.getMap().getAllMonstersThreadsafe().size() == 0 ) {	
            cm.gainItem(4031497,-1);
            cm.spawnMonster(9400014,-286,154);
            cm.dispose();
         }else{
            cm.sendOk("缺少一个召唤物骨架或未杀死天球都无法继续召唤。");
			cm.dispose();	
         }
         break;
		 
		 case 2:
        if (cm.haveItem(4031497,1)&& cm.getMap().getAllMonstersThreadsafe().size() == 0 ) {	
            cm.gainItem(4031497,-1);
            cm.spawnMonster(9400014,-286,154);
            cm.dispose();
         }else{
            cm.sendOk("缺少一个召唤物骨架或未杀死天球都无法继续召唤。");
			cm.dispose();	
         }
         break;
		 
		 case 3:
        if (cm.haveItem(4031497,1)&& cm.getMap().getAllMonstersThreadsafe().size() == 0 ) {	
            cm.gainItem(4031497,-1);
            cm.spawnMonster(9400014,-286,154);
            cm.dispose();
         }else{
            cm.sendOk("缺少一个召唤物骨架或未杀死天球都无法继续召唤。");
			cm.dispose();	
         }
         break;
		 
		 case 4:
        if (cm.haveItem(4031497,1)&& cm.getMap().getAllMonstersThreadsafe().size() == 0 ) {	
            cm.gainItem(4031497,-1);
            cm.spawnMonster(9400014,-286,154);
            cm.dispose();
         }else{
            cm.sendOk("缺少一个召唤物骨架或未杀死天球都无法继续召唤。");
			cm.dispose();	
         }
         break;
		 
		 case 5:
        if (cm.haveItem(4031497,1)&& cm.getMap().getAllMonstersThreadsafe().size() == 0 ) {	
            cm.gainItem(4031497,-1);
            cm.spawnMonster(9400014,-286,154);
            cm.dispose();
         }else{
            cm.sendOk("缺少一个召唤物骨架或未杀死天球都无法继续召唤。");
			cm.dispose();	
         }
         break;
		 

			

		}
    }
}