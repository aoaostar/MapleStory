importPackage(Packages.server.maps);

var status = -1;
var readNotice = 0;

function start() {
    cm.sendSimple("#e< 公告 >#n\r\n凡是有勇气挑战武陵道场者，欢迎你前来武陵道场！-武公-\r\n\r\n#b#L0#挑战武陵道场！#l\r\n#L1#详细阅读公告#l");
}

function action(mode, type, selection) {
    if (mode >= 0) {
        if (selection == 1 || readNotice == 1) {
            if (status == -1) {
                readNotice = 1;
                cm.sendNext("#e< 公告：挑战吧！>#n\r\n我是武陵道场的主人名叫武公。很就以前我是在武陵山开始修炼仙术，现在我的内功已达到快超越极限的阶段。以前武陵道场的主人懦弱到不象样的程度。所以今天开始以我接管武陵道场。只有强者可以拥有武陵道场的资格。\r\n想要得到武术指点的人尽管来挑战！或者想要挑战我的人也无妨。我会让你知道你的无知！！");
                status = 1;
            } else if (status == 1) {
                cm.sendPrev("备注：欢迎你来挑战。如果你没有勇气的话，找其他伙伴一起也无妨。");
                cm.dispose();
            }
        } else {
            if (status == -1 && mode == 1) {
                cm.sendYesNo("#b(用手碰到公告书时，身体开始被神秘的气息包围着)#k \r\n\r\n 要移动到武陵道场吗?");
                status = 1;
            } else if (status == 1) {
                if (mode == 0) {
                    cm.sendNext("#b(从公告书上挪开手时，神秘的气息消失无踪)");
                } else {
                    cm.saveLocation("DOJO");
                    cm.warp(925020000, 4);
                }
                cm.dispose();
            } else
                cm.dispose();
        }
    } else
        cm.dispose();
}