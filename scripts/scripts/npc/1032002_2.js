importPackage(Packages.client);
var status = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        if (status == 0) {
            var txt = "";
            txt = "我是每日跑商第2环NPC哦！\r\n\r\n";

            if (cm.getPS() == 1){// cm.getPS()  的意思是 读取跑商值如果等于1 就得出他跑商已经完成了第一环 就运行他进行第二环跑商!
                txt += "#L1##b收集150个绿叶球#v4000004#交给我!#l";
                cm.sendSimple(txt);
            }else{
                txt += "你已经完成过了然后你去找.林中之城-工作人员E!\r\n请第二天再来！";
                cm.sendOk(txt);
                cm.dispose();
            }

        } else if (selection == 1) {
            if (cm.haveItem(4000004,150)){
                cm.gainPS(1);//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第二环了。只有凌晨12点刷新才行！
                cm.gainItem(4000004, -150);
                cm.gainItem(4001126, 150);
                cm.gainDY(150);
                cm.gainMeso(150000);
                cm.sendOk("跑商第2环完成!然后你去找.林中之城-工作人员E.进行下一环！");
                cm.dispose();
            }else{
                cm.sendOk("收集150个绿叶球#v4000004#交给我!您的道具不足哦！");
                cm.dispose();
            }
        }
    }
}
