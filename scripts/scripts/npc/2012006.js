/*
-- MrCoffee JavaScript --
        NPC脚本 
-------------------------
   MrCoffee MapleStory
----- Version Info ------
     Version - 1.0.0 
-------------------------
*/

importPackage(net.sf.MrCoffee.client);

var mapid = new Array(200000110,200000120,200000130,200000140,200000150);
var platform = new Array("魔法密林","玩具城","神木村","武陵","阿里安特");
var flight = new Array("ship","ship","ship","Hak","Geenie");
var menu;
var select;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if(mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if(mode == 0) {
			cm.sendOk("请仔细选择好你要去的站台，再跟我讲。");
			cm.dispose();
			return;
		}
		if(mode == 1)
			status++;
		else
			status--;
		if(status == 0) {
			menu = "天空之城来往航班纵横交错，请选择一个可以带你到目的地的站台。请放心，即使你选择错了，还可以回来跟我说，我将带你到正确的站台等待航班。请在下面选择你要去的站台。";
			for(var i=0; i < platform.length; i++) {
				menu += "\r\n#L"+i+"##b开往 "+platform[i]+"#k#l";
			}
			cm.sendSimple(menu);
		} else if(status == 1) {
			select = selection;
			cm.sendYesNo("即使你选择错了站台，你还可以回到这里来跟我说，现在你将要移动到开往 #b "+flight[select]+"  "+platform[select]+" #k的站台？");
		} else if(status == 2) {
			cm.warp(mapid[select]);
			cm.dispose();
		}
	}
}