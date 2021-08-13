/*
 *  Scarf Snowman - Happy Ville NPC
 */


function start() {
    cm.sendYesNo("怎么样，要跟着我去幸福村吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
	cm.warp(209080100);
    } else {
	cm.sendNext("想好在找我！");
    }
    cm.dispose();
}