/*
	内容：个人排行榜
*/

var status = -1;

var tz5 = "#fUI/UIWindow2.img/QuestAlarm/BtQ/normal/0#";
var iconEvent = "#fUI/UIToolTip.img/Item/Equip/Star/Star#";


function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 0) {
		cm.dispose();
		return;
	}
	status++;
	if (status == 0) {
		var text = "#e#b         ┏   《金榜》   ┓ #r\r\n";

		text += "\t\t   #L21#bossrankpoints积分排行(zha)#l\r\n";
		text += "\t\t   #L22#bossrankcount次数排行(zha)#l\r\n";
		text += "\r\n#e#b         ┗                      ┛\r\n";
		cm.sendSimple(text);
	} else if (status == 1) {
		if(selection==21){
			var text = "\t\t\t\t#e#d★ bossrankpoints积分排行 ★#k#n\r\n\r\n";
			text += "\t#e名次#n\t#e玩家昵称#n\t\t\t#e积分#n\t\t #e#n\r\n";
			var rankinfo_list=cm.getBossRankPointsTop("zha");
			if(rankinfo_list!=null){
				for(var i=0;i<rankinfo_list.size();i++){
					if(i==10){
						break;
					}
					var info=rankinfo_list.get(i);

					text+=i==1?"#r":i==2?"#g":i==3?"#b":"";
					text += "\t " + i + "\t\t ";
					// 填充名字空格
					text += info.getCname();
					for (var j = 16 - info.getCname().getBytes().length; j > 0 ; j--) {
						text += " ";
					}
					text += "\t " + info.getPoints();
					
					text += "\t\t\t #k";

					text += "\r\n";
				}
			}
			cm.sendOkS(text, 3);
			cm.dispose();
		}else if(selection==22){

			var text = "\t\t\t\t#e#d★ bossrankcount次数排行 ★#k#n\r\n\r\n";
			text += "\t#e名次#n\t#e玩家昵称#n\t\t\t#e次数#n\t\t #e#n\r\n";
			var rankinfo_list=cm.getBossRankCountTop("zha");
			if(rankinfo_list!=null){
				for(var i=0;i<rankinfo_list.size();i++){
					if(i==10){
						break;
					}
					var info=rankinfo_list.get(i);

					text+=i==1?"#r":i==2?"#g":i==3?"#b":"";
					text += "\t " + i + "\t\t ";
					// 填充名字空格
					text += info.getCname();
					for (var j = 16 - info.getCname().getBytes().length; j > 0 ; j--) {
						text += " ";
					}
					text += "\t " + info.getCount();
					
					text += "\t\t\t #k";

					text += "\r\n";
				}
			}
			cm.sendOkS(text, 3);
			cm.dispose();

		
		
		}
	}
}
