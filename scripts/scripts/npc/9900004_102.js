//////////////////////////////
//七宝*自由冒险岛*最具创意////
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
		selStr = "#e区域；#r"+MC+"#k\r\n";
		
		selStr += "#e玩家#n；#r"+ cm.getChar().getName() +"#k   \r\n";
	     if(cm.getQuestStatus(88882)== 2 ){
		selStr += "#e职业#n；#r救赎者（2）#k\r\n";} 
	else if(cm.getQuestStatus(88880)== 2 ){
		selStr += "#e职业#n；#r救赎者（1）#k\r\n";}
	else if(cm.getPlayer().getJob() == 0 ){
		selStr += "#e职业#n；新手\r\n";}
    else if (cm.getPlayer().getJob() == 100 ){
		selStr += "#e职业#n；战士\r\n";}
	else if (cm.getPlayer().getJob() == 110 ){
		selStr += "#e职业#n；剑客\r\n";}
	else if (cm.getPlayer().getJob() == 111 ){
		selStr += "#e职业#n；勇士\r\n";}
	else if (cm.getPlayer().getJob() == 112 ){
		selStr += "#e职业#n；英雄\r\n";}	
	else if (cm.getPlayer().getJob() == 120 ){
		selStr += "#e职业#n；准骑士\r\n";}
	else if (cm.getPlayer().getJob() == 121 ){
		selStr += "#e职业#n；骑士\r\n";}
	else if (cm.getPlayer().getJob() == 122 ){
		selStr += "#e职业#n；圣骑士\r\n";}
	else if (cm.getPlayer().getJob() == 130 ){
		selStr += "#e职业#n；枪战士\r\n";}
	else if (cm.getPlayer().getJob() == 131 ){
		selStr += "#e职业#n；龙骑士\r\n";}
	else if (cm.getPlayer().getJob() == 132 ){
		selStr += "#e职业#n黑骑士\r\n";}	
	else if (cm.getPlayer().getJob() == 200 ){
		selStr += "#e职业#n；魔法师\r\n";}	
	else if (cm.getPlayer().getJob() == 210 ){
		selStr += "#e职业#n；火毒法师\r\n";}
	else if (cm.getPlayer().getJob() == 211 ){
		selStr += "#e职业#n；火毒巫师\r\n";}		
 	else if (cm.getPlayer().getJob() == 212 ){
		selStr += "#e职业#n；火毒魔导师\r\n";}       
	else if (cm.getPlayer().getJob() == 220 ){
		selStr += "#e职业#n；冰雷法师\r\n";}
	else if (cm.getPlayer().getJob() == 221 ){
		selStr += "#e职业#n；冰雷巫师\r\n";}		
 	else if (cm.getPlayer().getJob() == 222 ){
		selStr += "#e职业#n；冰雷魔导师\r\n";}
	else if (cm.getPlayer().getJob() == 230 ){
		selStr += "#e职业#n；牧师\r\n";}
	else if (cm.getPlayer().getJob() == 231 ){
		selStr += "#e职业#n；祭师\r\n";}		
 	else if (cm.getPlayer().getJob() == 232 ){
		selStr += "#e职业#n；主教\r\n";}
 	else if (cm.getPlayer().getJob() == 300 ){
		selStr += "#e职业#n；弓箭手\r\n";}		
 	else if (cm.getPlayer().getJob() == 310 ){
		selStr += "#e职业#n；猎人\r\n";}		
 	else if (cm.getPlayer().getJob() == 311 ){
		selStr += "#e职业#n；射手\r\n";}		
 	else if (cm.getPlayer().getJob() == 312 ){
		selStr += "#e职业#n；神射手\r\n";}		
 	else if (cm.getPlayer().getJob() == 320 ){
		selStr += "#e职业#n；弩弓手\r\n";}		
 	else if (cm.getPlayer().getJob() == 321 ){
		selStr += "#e职业#n；游侠\r\n";}			
 	else if (cm.getPlayer().getJob() == 322 ){
		selStr += "#e职业#n；箭神\r\n";}			
 	else if (cm.getPlayer().getJob() == 400 ){
		selStr += "#e职业#n；飞侠\r\n";}			
 	else if (cm.getPlayer().getJob() == 410 ){
		selStr += "#e职业#n；刺客\r\n";}		
 	else if (cm.getPlayer().getJob() == 411 ){
		selStr += "#e职业#n；无影人\r\n";}		
 	else if (cm.getPlayer().getJob() == 412){
		selStr += "#e职业#n；隐士\r\n";}		
 	else if (cm.getPlayer().getJob() == 420 ){
		selStr += "#e职业#n；侠客\r\n";}		
 	else if (cm.getPlayer().getJob() == 421 ){
		selStr += "#e职业#n；独行客\r\n";}		
 	else if (cm.getPlayer().getJob() == 422 ){
		selStr += "#e职业#n；侠盗\r\n";}
 	else if (cm.getPlayer().getJob() == 1100 ){
		selStr += "#e职业#n；魂骑士1转\r\n";}		
 	else if (cm.getPlayer().getJob() == 1110 ){
		selStr += "#e职业#n；魂骑士2转\r\n";}
 	else if (cm.getPlayer().getJob() == 1111 ){
		selStr += "#e职业#n；魂骑士3转\r\n";}
	else if (cm.getPlayer().getJob() == 1200 ){
		selStr += "#e职业#n；炎术师1转\r\n";}
	else if (cm.getPlayer().getJob() == 1210 ){
		selStr += "#e职业#n；炎术师2转\r\n";}
	else if (cm.getPlayer().getJob() == 1211 ){
		selStr += "#e职业#n；炎术师3转\r\n";}
	else if (cm.getPlayer().getJob() == 1300 ){
		selStr += "#e职业#n；风灵使者1转\r\n";}
	else if (cm.getPlayer().getJob() == 1310 ){
		selStr += "#e职业#n；风灵使者2转\r\n";}
	else if (cm.getPlayer().getJob() == 1311 ){
		selStr += "#e职业#n；风灵使者3转\r\n";}
	else if (cm.getPlayer().getJob() == 1400 ){
		selStr += "#e职业#n；夜行者1转\r\n";}
	else if (cm.getPlayer().getJob() == 1410 ){
		selStr += "#e职业#n；夜行者2转\r\n";}		
	else if (cm.getPlayer().getJob() == 1411 ){
		selStr += "#e职业#n；夜行者3转\r\n";}		
	else if (cm.getPlayer().getJob() == 1500 ){
		selStr += "#e职业#n；奇袭者1转\r\n";}		
	else if (cm.getPlayer().getJob() == 1510 ){
		selStr += "#e职业#n；奇袭者2转\r\n";}
	else if (cm.getPlayer().getJob() == 1511 ){
		selStr += "#e职业#n；奇袭者3转\r\n";}
	else if (cm.getPlayer().getJob() == 2100 ){
		selStr += "#e职业#n；战神1转\r\n";}
	else if (cm.getPlayer().getJob() == 2110 ){
		selStr += "#e职业#n；战神2转\r\n";}
	else if (cm.getPlayer().getJob() == 2111 ){
		selStr += "#e职业#n；战神3转\r\n";}
	else if (cm.getPlayer().getJob() == 2112 ){
		selStr += "#e职业#n；战神4转\r\n";}
	else{
		selStr += "#e职业#n；显示错误\r\n";
		cm.dispose();}
	//	selStr +="#e强化熟练度；#r"+ cm.getBossRank("强化",2)+"#l\r\n#k";
		selStr +="#e锻造熟练度；#r"+ cm.getBossRank("锻造熟练度",2)+"#l\r\n#k";
	//	selStr +="#e地铁站积分；#r"+ cm.getBossRank("地铁积分",2)+"#l\r\n#k";
		selStr +="#e武陵塔积分；#r"+cm.getDojoPoints()+"#l\r\n#k";
		selStr += "#k#e现有点券：#r"+cm.getPlayer().getCSPoints(1)+"\r\n#k";
		selStr += "#k#e现有抵用：#r"+cm.getPlayer().getCSPoints(2)+"\r\n#k";
		selStr += "#k#e现有金币：#r"+cm.getPlayer().getMeso()+"\r\n#k";
        selStr += "#b\r\n#L0#"+箭头+"返回界面#l";
		
		
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
		case 10000://
		 
		cm.getPlayer().dropMessage();
		
            cm.dispose();
            
            break;
		
		
		
		
		
		
		
		
		
        case 0://枫叶募集
            cm.dispose();
            cm.openNpc(9900004,0);	
            break;
	    case 1:
            var text = "\t\t\t\t#e#r★ 锻造熟练度 ★#k#n\r\n\r\n";
			text += "\t#e名次#n\t#e 玩家昵称#n\t\t\t#e 熟练度#n\t\t #e#n\r\n#k";
			var rankinfo_list=cm.getBossRankCountTop("锻造熟练度");
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
             case 2:
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
			 case 3:
		    cm.sendOk("\t亲爱的；#r#h ##k\r\n\t现在你的积分点数有 #b"+cm.getDojoPoints()+"#k 点");
			cm.dispose(); 
			 
			 
		}
    }
}
