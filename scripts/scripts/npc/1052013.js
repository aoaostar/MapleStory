/*
	Computer - Premium road : Kerning City Internet Cafe
*/

var maps = Array(190000000, 191000000, 192000000, 195000000, 196000000, 197000000
);

function start() {
    var selStr = "这里是电脑世界，你确定要前往电脑网吧世界么？#b";
    for (var i = 0; i < maps.length; i++) {
	selStr += "\r\n#L" + i + "##m" + maps[i] + "# #l";
    }
    cm.sendSimple(selStr);
}

function action(mode, type, selection) {
    if (mode == 1) {
	cm.warp(maps[selection], 0);
    }
    cm.dispose();
}