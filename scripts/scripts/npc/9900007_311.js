var status = -1;
var msg;
var sel;
var item = [
1932029,1932034,1932035,1932038,1932044,1932047,1932048,1932049,1932050,1932052,1932053,1932057,
1932071,1932072,1932078,1932080,1932081,1932084,1932086,1932087,1932089,1932091,1932092,1932098,
1932100,1932105,1932106,1932107,1932108,1932109,1932110,1932112,1932113,1932114,1932115,1932116,
1932117,1932119,1932121,1932122,1932123,1932124,1932127,1932128,1932140,1932142,1932143,1932144,
1932148,1932150,1932151,1932152,1932153,1932154,1932156,1932157,1932158,1932159,1932161,1932162,
1932163,1932164,1932165,1932167,1932170,1932171,1932173,1932178,1932191,1932192,1932193,1932187,
1932188,1932189,1932190,1932195,1932198,1932199,1932200,1932202,1932204,1932205,1932207,1932212,
1932213,1932214,1932215,1932216,1932217,1932223,1932224,1932230,1932232,1932234,1932235,1932236,
1932237,1932238,1932239,1932240,1932241,1932242,1932243,1932246,1932247,1932248,1932251,1932252,
1932254,1932255,1932256,1932258,1932259,1932260,1932261,1932263,1932264,1932265,1932272,1932273,
1932274,1932277,1932279,1932280,1932281,1932282,1932286,1932287,1932288,1932290,1932293,1932292,
1932297,1932302,1932303,1932304,1932308,1932313,1932314,1932315,1932316,1932317,1932318,1932319,
1932322,1932323,1932327,1932329,1932330,1932334,1932335,1932336,1932337,1932338,1932339,1932341,
1932342,1932350,1932351,1932352,1932353,1932355,1932366,1932011,1932063,1932064,1932310,1932326




];
var random = true;
var need = 5000;

function start(){
	action(1, 0, 0);
}

function action(mode, type, selection){
	if(mode == 1){
		status++;
	} else if(mode == 0){
		status--;
	} else {
		cm.dispose();
		return;
	}var MC = cm.getServerName();
	var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
	//if(status == 0) {
		//msg = "你好,我是皇家骑宠代理人,你将愿意花费点卷在我这里随机获取皇家骑宠技能吗 ?";
		//if(cm.getPlayer().getMountId() != 0 && Packages.server.MapleItemInformationProvider.getInstance().itemExists(cm.getPlayer().getMountId())){
			//msg += "\r\n#r 目前你的骑宠是 : #i" + cm.getPlayer().getMountId() + "##t" + cm.getPlayer().getMountId() + "#";
		//}
		//cm.sendYesNo(msg);
	 if(status == 0) {
		cm.sendSimple("#i1932355##i1932355##i1932355##i1932355##i1932355##i1932355##i1932355##i1932355##i1932355##i1932355#\r\n#r#e"+MC+"#n#k骑宠更换区；\r\n\r\n更换价格/ #r"+need+"#k 点券     当前余额:#r" + cm.getChar().getCSPoints(1) + "\r\n\r\n\t\t\t\t#L0#"+箭头+" 更换骑宠#l");//#L1#查看骑宠种类
	} else if(status == 1) {
		msg = "\r\n";
		if(selection == 1){
			for(var i = 0; i < item.length; i++){
				if(Packages.server.MapleItemInformationProvider.getInstance().itemExists(item[i])){
					msg += "#i"+item[i]+":##t\t\t\t"+item[i]+"#\r\n ";
				}
			}
			cm.sendNext(msg);
			status = 0;
		}
		
		if(selection == 0 && random){
			if(cm.getPlayer().getNX() < need){
				cm.sendNext("点卷不足" + need);
				cm.dispose();
				return;
			} else if(!cm.canHold(1932081)){
				//cm.sendNext("装备栏空间不足");
				//cm.dispose();
				//return;
			}
			
			var exist = true;
			do{
				sel = Math.floor(Math.random() * item.length);
				exist = Packages.server.MapleItemInformationProvider.getInstance().itemExists(item[sel]);
			}while(!exist);
			cm.getPlayer().setMountId(item[sel]);
			cm.getPlayer().modifyCSPoints(1, -need, true);
			cm.sendNext("你获得了 #i"+item[sel]+":##t"+item[sel]+"#");
			//cm.serverNotice("[骑宠公告]：玩家 "+ cm.getChar().getName() +" 获得了#t"+item[sel]+"#。"); 
			cm.dispose();
		} else {
			if(selection == 0){
				for(var i = 0; i < item.length; i++){
					msg += "#L"+i+"##i"+item[i]+":##t"+item[i]+"\r\n";
				}
				cm.sendSimple(msg);
			}
		}
	} else if(status == 3){
		if(!random){
			sel = selection;
			cm.sendYesNo("你确定是否选择 #i"+item[sel]+":##t"+item[sel]+"#");
		}
	} else if(status == 4){
		if(!random){
			cm.getPlayer().setMountId(item[sel]);
			cm.sendNext("你获得了 #i"+item[sel]+":##t"+item[sel]+"#");
			cm.dispose();
		}
	} else {
		cm.dispose();
	}
}