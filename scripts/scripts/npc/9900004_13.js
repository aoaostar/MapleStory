/*
 *
 *  此脚本由冒险岛制作完成
 * i
 *
 */


importPackage(net.sf.odinms.client);

var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

//------------------------------------------------------------------------

var chosenMap = -1;
var monsters = 0;
var towns = 0;
var bosses = 0;
var fuben = 0;

//------------------------------------------------------------------------

var bossmaps = Array(
		Array(100000005,0,"铁甲猪公园III"),
		Array(105070002,0,"蘑菇王之墓"), 
		Array(105090900,0,"被诅咒的寺院"), 				
		Array(230040420,0,"皮亚奴斯洞穴"), 
		Array(211042300,0,"扎昆入口"), 
		Array(220080000,0,"时间塔的本源"), 
		Array(240020402,0,"喷火龙栖息地"), 
		Array(240020101,0,"格瑞芬多森林"),					
		Array(551030100,0,"挑战暴力熊"),					
		Array(240040700,0,"暗黑龙王洞穴"), 				
		Array(270050000,0,"神的黄昏:品克缤场所"),	
		Array(541020800,0,"千年树精")									
		);

//------------------------------------------------------------------------

var monstermaps = Array(
);

//------------------------------------------------------------------------

var townmaps = Array(
		//Array(270050000,0,"PB") 
		//Array(240060200,0,"黑龙"),
                //Array(280030000,0,"扎昆")
		);

//------------------------------------------------------------------------

var fubenmaps = Array(
		Array(800020400,0,"家族漫步地图"),
		Array(193000000,0,"网吧地图")						
		);

//------------------------------------------------------------------------

	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
	if (mode == -1) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		} else {
	if (status >= 0 && mode == 0) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		return;
		}
	if (mode == 1) {
		status++;
		} else {
		status--;
		}

//------------------------------------------------------------------------

	if (status == 0) {

   	    var add = "\r\n您好，我是BOSS掉线传送员。请问您要回到？\r\n\r\n#e#r";

//		add += "冒险到世界地图我都可以为你送到,";

//		add += "为方便玩家,管理设置了练级地图,BOSS地图直接传送,";

		//add += "如果你有什么更好的地图,不防联系管理员添加,感谢你对本服的支持.#b\r\n\r\n";

	   add += "#L0#黑龙--扎昆--PB#l ";    

		//add += "#L1#练级传送#l ";

//		add += "#L2#BOSS传送#l ";
 
		cm.sendSimple (add);    

//------------------------------------------------------------------------
				
	} else if (status == 1) {

	if (selection == 0){
		var selStr = "选择你的目的地吧.#b";
		for (var i = 0; i < townmaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + townmaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		towns = 1;
		}

	if (selection == 1) {
		var selStr = "选择你的目的地吧.#b";
		for (var i = 0; i < monstermaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + monstermaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		monsters = 1;
		}

	if (selection == 2) {
		var selStr = "选择你的目的地吧.#b";
		for (var i = 0; i < bossmaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + bossmaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		bosses = 1;
		}

	if (selection == 3) {
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(1).getMapFactory().getMap(280030000);
		var zha1 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(2).getMapFactory().getMap(280030000);
		var zha2 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(3).getMapFactory().getMap(280030000);
		var zha3 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(4).getMapFactory().getMap(280030000);
		var zha4 = map.getCharacters().toArray().length;

		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(1).getMapFactory().getMap(240060200);
		var hei1 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(2).getMapFactory().getMap(240060200);
		var hei2 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(3).getMapFactory().getMap(240060200);
		var hei3 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(4).getMapFactory().getMap(240060200);
		var hei4 = map.getCharacters().toArray().length

		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(1).getMapFactory().getMap(270050100);
		var pb1 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(2).getMapFactory().getMap(270050100);
		var pb2 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(3).getMapFactory().getMap(270050100);
		var pb3 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(4).getMapFactory().getMap(270050100);
		var pb4 = map.getCharacters().toArray().length

		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(1).getMapFactory().getMap(220080000);
		var nao1 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(2).getMapFactory().getMap(220080000);
		var nao2 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(3).getMapFactory().getMap(220080000);
		var nao3 = map.getCharacters().toArray().length;
		var map = net.sf.odinms.net.channel.ChannelServer.getInstance(4).getMapFactory().getMap(220080000);
		var nao4 = map.getCharacters().toArray().length

   	    var add = "以下所示为各线的BOSS战况#b\r\n";

		add += ""+aaa+"[#r频道一#b]\r\n";

		add += ""+zzz+"[#d扎昆#b]：#r"+zha1+"#b人  [#d黑龙#b]：#r"+hei1+"#b人  [#dPB#b]：#r"+pb1+"#b人  [#d闹钟#b]：#r"+nao1+"#b人\r\n\r\n";

		add += ""+aaa+"[#r频道二#b]\r\n";

		add += ""+zzz+"[#d扎昆#b]：#r"+zha2+"#b人  [#d黑龙#b]：#r"+hei2+"#b人  [#dPB#b]：#r"+pb2+"#b人  [#d闹钟#b]：#r"+nao2+"#b人\r\n\r\n";

		add += ""+aaa+"[#r频道三#b]\r\n";

		add += ""+zzz+"[#d扎昆#b]：#r"+zha3+"#b人  [#d黑龙#b]：#r"+hei3+"#b人  [#dPB#b]：#r"+pb3+"#b人  [#d闹钟#b]：#r"+nao3+"#b人\r\n\r\n";

		add += ""+aaa+"[#r频道四#b]\r\n";

		add += ""+zzz+"[#d扎昆#b]：#r"+zha4+"#b人  [#d黑龙#b]：#r"+hei4+"#b人  [#dPB#b]：#r"+pb4+"#b人  [#d闹钟#b]：#r"+nao4+"#b人\r\n\r\n";
 
		cm.sendOk (add); 

		cm.dispose();

                   }

//------------------------------------------------------------------------

	} else if (status == 2) {

	if (towns == 1) {
		cm.sendYesNo("你确定要去 " + townmaps[selection][2] + "?");
		chosenMap = selection;
		towns = 2;

	} else if (monsters == 1) {
		cm.sendYesNo("你确定要去 " + monstermaps[selection][2] + "?");
		chosenMap = selection;
		monsters = 2;

	} else if (bosses == 1) {
		cm.sendYesNo("你确定要去 " + bossmaps[selection][2] + "?");
		chosenMap = selection;
		bosses = 2;

	} else if (fuben == 1) {
		cm.sendYesNo("你确定要去 " + fubenmaps[selection][2] + "?");
		chosenMap = selection;
		fuben = 2;

		}

//----------------------------------------------------------------------

	} else if (status == 3) {

	if (towns == 2) {
		if(cm.getMeso()>=townmaps[chosenMap][1]){
		cm.warp(townmaps[chosenMap][0], 0);
		cm.gainMeso(-townmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (monsters == 2) {
		if(cm.getMeso()>=monstermaps[chosenMap][1]){
		cm.warp(monstermaps[chosenMap][0], 0);
		cm.gainMeso(-monstermaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (bosses == 2) {
		if(cm.getMeso()>=bossmaps[chosenMap][1]){
		cm.warp(bossmaps[chosenMap][0], 0);
		cm.gainMeso(-bossmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (fuben == 2) {
		if(cm.getMeso()>=fubenmaps[chosenMap][1]){
		cm.warp(fubenmaps[chosenMap][0], 0);
		cm.gainMeso(-fubenmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

                }

//------------------------------------------------------------------------

		}
		}
		}