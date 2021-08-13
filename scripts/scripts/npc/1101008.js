/* 
 *  NPC  : Guide Summoner
 *  Maps : Erev Map of the Start // 20021
 */

var status = -1;

function start() {
    cm.sendSimple("这些事情你必须都得晓得的\r\n好了，你想要知道想一项事情？？  \n\r #b#L0#告诉我更多关于你的事情。#l \n\r #b#L1#小地图介绍。#l \n\r #b#L2#如何打开任务视窗。#l \n\r #b#L3#如何打开道具栏。#l \n\r #b#L4#如何攻击。#l \n\r #b#L5#如何捡道具。#l \n\r #b#L6#如何穿装备。#l \n\r #b#L7#技能视窗。#l \n\r #b#L8#如何把技能放到快捷键上。#l \n\r #b#L9#如何打破箱子。#l \n\r #b#L10#如何坐椅子。#l \n\r #b#L11#如何查看世界地图。#l \n\r #b#L12#什么是皇家骑士团。#l");
}

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.dispose();
    }
    if (status == 0) {
	if (selection == 0) {
	    cm.sendNext("你好我是提酷！");
	} else if (selection == 12) {
	    cm.sendOk("皇家骑士团就是皇家骑士团。");
	    cm.dispose();
	} else {
	    cm.summonMsg(selection);
	    cm.dispose();
	}
    } else if (status == 1) {
	cm.sendNext("很高兴认识你。");
	cm.dispose();
    }
}