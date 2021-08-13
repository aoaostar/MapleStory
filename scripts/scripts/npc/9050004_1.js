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
            txt = "我是每日跑商任务NPC！第一环.\r\n\r\n";

            if (cm.getPS() == 0){// cm.getPS()  的意思是 读取跑商值如果等于0 就得出他没有开始跑商 就运行他进行第一环跑商!
                txt += "#L1##b收集50个半人马的骨头#v4000234#交给我！#l";
                cm.sendSimple(txt);
            }else{
                txt += "你已经完成过了第一轮，进行下一轮.!\r\n请第二天再来完成第一轮！";
                cm.sendOk(txt);
                cm.dispose();
            }

        } else if (selection == 1) {
            if (cm.haveItem(4000234,50) && cm.getLevel() >= 81 && cm.getLevel() <= 150){
                cm.gainPS(1);//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！
		
                cm.gainItem(4000234, -50);
                //cm.gainMeso(+5000);//读取变量
                cm.gainExp(+150000);
                cm.sendOk("跑商第一轮完成!获得经验=150000\r\n\r\n然后你去进行下一环.");
                cm.dispose();
            }else{
                cm.sendOk("收集50个半人马的骨头#v4000234#交给我！并确定您的角色等级在81-150之间~");
                cm.dispose();
            }
        }
    }
}
