/* Author: aaroncsn (MapleSea Like)
	NPC Name: 		Mike
	Map(s): 		Warning Street: Perion Dungeon Entrance(106000300)
	Description: 		Unknown
*/

function start() {
    if (cm.getQuestStatus(28177) == 1 && !cm.haveItem(4032479)) { //too lazy
        cm.gainItem(4032479, 1);
    }
    cm.sendNext("发生了什么事？");
    cm.dispose();
}