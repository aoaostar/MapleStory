
var status = 0;
var beauty = 0;
var price = 3000000;
var facenew = Array();

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("你好,我是差不多医生!如你想要更换眼睛颜色吗,我可以帮你更换哦那么你要做什么？\r\n\#L2##b改变眼睛颜色#l");
        } else if (status == 1) {
            if (selection == 1) {
                cm.dispose();
            } else if (selection == 2) {
                facenew = Array();
                if (cm.getChar().getGender() == 0) {
                    var current = cm.getChar().getFace() % 100 + 20000;
                } else {
                    var current = cm.getChar().getFace() % 100 + 21000;
                }
                colors = Array();
                colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 500, current + 600, current + 700);
                cm.sendStyle("请选择你喜欢的颜色.", 0, colors);
            }
        } else if (status == 2) {
            cm.setFace(colors[selection]);
            cm.sendOk("好了,你的朋友们一定认不出来是你了!");
            cm.dispose();

        }
    }
}
