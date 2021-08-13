//////////////////////////////
//�߱�*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/1#";
var Y = "#fUI/GuildMark.img/Mark/Letter/00005024/3#";
var X = "#fUI/GuildMark.img/Mark/Letter/00005023/1#";
var D = "#fUI/GuildMark.img/Mark/Letter/00005003/1#";
var M = "#fUI/GuildMark.img/Mark/Letter/00005012/1#";
var A = "#fUI/GuildMark.img/Mark/Letter/00005000/1#";
var P = "#fUI/GuildMark.img/Mark/Letter/00005015/1#";
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/9#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";


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
    }var MC = cm.getServerName();
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
		
		
   var  
	    selStr = "#e#r"+MC+"#k试炼区；\r\n";
		selStr += "#b#e#L0#"+箭头+"返回界面#l\r\n";
		
		selStr += "#L1##b"+箭头+"[怪物训练场]强化之地#l  #L2##b"+箭头+"排行榜#l\r\n";
		selStr += "#L3##b"+箭头+"[挑战武陵塔]武陵塔林#l  #L4##b"+箭头+"查积分#l\r\n";
		selStr += "#L5##b"+箭头+"[挑战地铁站]废弃车站#l  #L6##b"+箭头+"积分榜#l\r\n";
		selStr += "#L7##b"+箭头+"[挑战金字塔]金色的塔#l  \r\n";
		//selStr += "#L2##b"+箭头+"欢乐抢楼#l\r\n";

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
		case 1:
		 if ( cm.getMapId() == 910000000||cm.getMapId() == 970030000){ 
            cm.dispose();
            cm.openNpc(9900007,201);	
			} else {
		  cm.sendOk("#r提示；只有从市场才可以进入。");
		  cm.dispose();
		  }
            break;	
		case 3:
            cm.dispose();
			cm.warp(925020000,0);
            break;	
		case 4:
		cm.sendOk("\t亲爱的；#r#h ##k\r\n\t现在你的积分点数有 #b"+cm.getDojoPoints()+"#k 点");
		
            cm.dispose();
            break;	
		case 5:
            cm.dispose();
			cm.warp(910320000,0);
			cm.sendOk("已经到达地铁挑战区域。");
            break;
        case 7:
            cm.dispose();
			cm.warp(926010000,0);
			cm.sendOk("已经到达金字塔挑战区域。");
            break;			
			
			
			
			
			
			
			
			
			
			
			
			
		 case 2:
            var text = "\t\t\t\t#e#r★ BOSS通关榜 ★#k#n\r\n\r\n";
			text += "\t#e名次#n\t#e 玩家昵称#n\t\t\t#e 关卡#n\t\t #e#n\r\n#k";
			var rankinfo_list=cm.getBossRankCountTop("BOSS训练场");
			if(rankinfo_list!=null){
				for(var i=0;i<rankinfo_list.size();i++){
					if(i==10){
						break;
					}
					var info=rankinfo_list.get(i);

					text+=i==0?"#r":i==1?"#r":i==2?"#g":i==3?"#b":"";
					text += "\t " + (i+1) + "\t\t ";
					// 填充名字空格
					text += info.getCname();
					for (var j = 16 - info.getCname().getBytes().length; j > 0 ; j--) {
						text += " ";
					}
					text += "\t\t" + info.getCount();
					
					text += "\t\t\t #k";

					text += "\r\n";
				}
			}
			cm.sendOkS(text, 3);
			cm.dispose();
			 break;
	 case 6:
            var text = "\t\t\t\t#e#r★ 地铁站积分 ★#k#n\r\n\r\n";
			text += "\t#e名次#n\t#e 玩家昵称#n\t\t\t#e 积分#n\t\t #e#n\r\n#k";
			var rankinfo_list=cm.getBossRankCountTop("地铁积分");
			if(rankinfo_list!=null){
				for(var i=0;i<rankinfo_list.size();i++){
					if(i==10){
						break;
					}
					var info=rankinfo_list.get(i);

					text+=i==0?"#r":i==1?"#r":i==2?"#g":i==3?"#b":"";
					text += "\t " + (i+1) + "\t\t ";
					// 填充名字空格
					text += info.getCname();
					for (var j = 16 - info.getCname().getBytes().length; j > 0 ; j--) {
						text += " ";
					}
					text += "\t\t" + info.getCount();
					
					text += "\t\t\t #k";

					text += "\r\n";
				}
			}
			cm.sendOkS(text, 3);
			cm.dispose();
			
            break;
			 
			 
			 
			 
		}
			
    }
}
