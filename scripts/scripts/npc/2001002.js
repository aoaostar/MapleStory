var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
//var tz = "#fEffect/CharacterEff/1082565/4/0#";  //兔子粉
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
	//if (cm.getQuestStatus(21728) == 0) {
          //  cm.sendOk("你未习得此点歌天赋。");
           // cm.dispose();
       // }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
		var rsb = cm.获取指定地图玩家数量(209000014);
		var rsa = cm.获取指定地图玩家数量(209000013);
        var  selStr = ""; 
        if(cm.getMapId() == 209000013 ){
		 selStr += "你所在的队伍；#dA队伍#k\r\n"; 
		  selStr += "当前对抗积分；#d"+cm.getBossRank("对抗积分",2)+"#k\r\n"; 
		 selStr += "敌对队伍人数；#d"+rsb+"#k\r\n"; 
		 selStr += "#r#L1#召唤兽1 积分+2#l\r\n";
		 selStr += "#r#L999#结束游戏#l\r\n";
		 
         	
 } else  if(cm.getMapId() == 209000014 ){
		 selStr += "你所在的队伍；#dB队伍#k\r\n"; 
		 selStr += "当前对抗积分#d；"+cm.getBossRank("对抗积分",2)+"#k\r\n"; 
		 selStr += "敌对队伍人数；#d"+rsa+"#k\r\n"; 
		 selStr += "#r#L10#召唤兽1 积分+2#l\r\n";
		 selStr += "#r#L999#结束游戏#l\r\n";
         	 }		 
        selStr +=""
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
			case 999:
			    cm.dispose();
				cm.warp(910000000);
		    break;
            case 1:
			var rsb = cm.获取指定地图玩家数量(209000014);
		    var rsa = cm.获取指定地图玩家数量(209000013);
			 
			if ( rsb ==0 ){
				cm.sendOk("等待对面入场之后，再开启游戏。");
				 cm.dispose();
	 }else {if (cm.getMap().getAllMonstersThreadsafe().size() == 0){
				cm.setBossRankCount("对抗积分","2");
				cm.给指定地图发公告(209000014,""+ cm.getChar().getName() +"向你发起来干扰，你无法召唤怪物赚取积分。",5121009);
				cm.在指定地图召唤指定怪物(209000013,9500335);
				cm.在指定地图召唤指定怪物(209000014,9501006);
				cm.在指定地图召唤指定怪物(209000014,9501007);
                cm.dispose();
	        }else {
		        cm.sendOk("有怪物干扰，无法召唤。");
                cm.dispose();
			}
		   }
			break; 
			
			
			
			
			
			
			
			case 10:
			var rsb = cm.获取指定地图玩家数量(209000014);
		    var rsa = cm.获取指定地图玩家数量(209000013);
			 
			if ( rsa ==0 ){
				cm.sendOk("等待对面入场之后，再开启游戏。");
				 cm.dispose();
	 }else {if (cm.getMap().getAllMonstersThreadsafe().size() == 0){
				cm.setBossRankCount("对抗积分","2");
				cm.给指定地图发公告(209000013,""+ cm.getChar().getName() +"向你发起来干扰，你无法召唤怪物赚取积分。",5121009);
				cm.在指定地图召唤指定怪物(209000014,9500335);
				cm.在指定地图召唤指定怪物(209000013,9501006);
				cm.在指定地图召唤指定怪物(209000013,9501007);
                cm.dispose();
	        }else {
		        cm.sendOk("有怪物干扰，无法召唤。");
                cm.dispose();
			}
		   }


			
			

			

		}
    }
}
