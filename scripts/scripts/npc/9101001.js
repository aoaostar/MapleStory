/* Author: Xterminator
	NPC Name: 		Peter
	Map(s): 		Maple Road: Entrance - Mushroom Town Training Camp (3)
	Description: 	Takes you out of Entrace of Mushroom Town Training Camp
*/
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	cm.sendNext(" - #r#n玩家指令列表；#k#b\r\n@bw   或者  @爆物     <查询爆物>\r\n#b@jk   或者  @解卡     <解除卡死>\r\n@cd   或者  @存档     <保存数据>\r\n@mrtt                 <每日跳跳>\r\n\r\n\r\n#d - 版本；0161204A\r\n - 自由冒险岛");
    } else if (status == 1){
 
    }
}