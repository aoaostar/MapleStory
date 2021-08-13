var 礼包物品 = "#v1302000#";
var x1 = "1302000,+1";// 物品ID,数量
var x2;
var x3;
var x4;
var add = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";//红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";//蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";//选择道具
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
//var 红色箭头 = "#fEffect/CharacterEff/1114000/2/0#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
//var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";


function start() {

if (cm.getChar().getMapId() != 209000015){
    cm.sendSimple (""+add+add+add+add+add+add+add+add+add+add+add+"本服排行榜"+add+add+add+add+add+add+add+add+add+add+add+"\r\n#b查看哪种排名？\r\n#L1#破攻排行榜#l#r#L2#家族排行榜#l\r\n\r\n#L12#等级排行榜#L11#人气排行榜\r\n\r\n#L10#金币排行榜#L112#杀怪排行榜");//#L10#金币排行榜
    } else {
    cm.sendOk("不要再这个地图使用我")
    }
}
function action(mode, type, selection) {
cm.dispose();
if (selection == 0) { //人气排行
       cm.openNpc(2101017,0);
} else if (selection == 1) {
	//Level
        cm.破攻排行榜();
} else if (selection == 2) {
        //MapGui
        cm.家族排行榜();
	cm.dispose(); 
}  else if (selection == 10) {
        //MapGui
        cm.金币排行榜();
	cm.dispose(); 
}   else if (selection == 11) {
        cm.人气排行榜();
	cm.dispose(); 
}   else if (selection == 12) {
        cm.等级排行榜();
}   else if (selection == 112) {
        cm.杀怪排行榜();
	cm.dispose(); 
}  
}