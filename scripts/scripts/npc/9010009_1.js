importPackage(net.sf.odinms.client);
var status = 0;

var ttt = "#fUI/UIWindow.img/Quest/icon9/0#";
var xxx = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;


        if (status == 0) {

            var textz = "\r\n#e欢迎来到#b冒险岛跑环#k.每人每天跑一次获取相对应的奖励，建议新人每日都做。#l\r\n\r\n";

            textz += "#L100##e#d第一轮#v4000019#100个#b(金币300万+#v2022468#X10).\r\n\r\n"//3
            textz += "#L101##e#d第二轮#v4000005#100个#b(金币600万+#v2022468#X10).\r\n\r\n"//3
            textz += "#L102##e#d第三轮#v4000007#100个#b(金币900万+#v2022468#X10).\r\n\r\n"//3
            textz += "#L103##e#d第四轮#v4000142#100个#b(金币1500万+#v2022468#X10).\r\n\r\n"//3
            textz += "#L104##e#d第五轮#v4000160#100个#b(金币2000万+#v2022468#X10).\r\n\r\n"//3
            textz += "#L105##e#d第六轮#v4000180#100个#b(金币3000万+#v2022468#X10+#v2340000#X5).\r\n\r\n"//3
            textz += "#L106##e#d第七轮#v4000274#100个#b(金币4000万+#v2022468#X10+#v4000464#X5).\r\n\r\n"//3
            textz += "#L107##e#d第八轮#v4000407#30个#b(金币5000万+#v2022468#X30+#v2614015#X5).\r\n\r\n"//3
            textz += "#L108##e#d第九轮#v4000402#30个#b(金币1E万+#v2022468#X40+#v2614015#X5).\r\n\r\n"//3
            textz += "#L109##e#d第十轮#v4000406#30个#b(金币2E#v2022468#X50#v2614015#X10#v4000464#X5.\r\n\r\n"//3

            //textz += "#d#L5#收集#v4000161##b#z4000161##r 100 #d个可兑换#r#v4032226#10只\r\n";

            //textz += "#d#L6#收集#v4000052##b#z4000052##r 100 #d个可兑换#r#v4032226#10只\r\n";

            //textz += "#d#L7#收集#v4000190##b#z4000190##r 100 #d个可兑换#r#v4032226#10只\r\n";

            //textz += "#d#L8#收集#v4001085##b#z4001085##r 1 #d个\r\n  可兑换#r1000点#d卷#l\r\n";

            //textz += "#d#L9#收集#v4001084##b#z4001084##r 1 #d个\r\n  可兑换#r1000点#d卷#l\r\n";

            //textz += "#d#L10#收集#v4001083##b#z4001083##r 1 #d个\r\n  可兑换#r2000点#d卷#l\r\n";
            cm.sendSimple(textz);


        } else if (status == 1) {

            if (selection == 100) {
                if (cm.getBossLog('跑环1') == 0 && cm.haveItem(4000019, 100)) {

                    cm.setBossLog('跑环1');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000019, -100);
					cm.gainItem(2022468, 10);
                    cm.gainMeso(+3000000);//
                    cm.gainExp(+50000);//给经验
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第一环，获取了大量奖励!");
                    cm.sendOk("跑商第一轮完成!\r\n\r\n然后你去进行下一环.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000019#交给我!\r\nPS：您已经完成过该环节，或者材料不足！");
                    cm.dispose();
                }
            } else if (selection == 101) {
                if (cm.getBossLog('跑环1') > 0 && cm.getBossLog('跑环2') == 0 && cm.haveItem(4000005, 100)) {

                    cm.setBossLog('跑环2');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000005, -100);
					cm.gainItem(2022468, 10);
                    cm.gainMeso(+6000000);//读取变量
                    cm.gainExp(+100000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第二环，获取了大量奖励!");
                    cm.sendOk("跑商第二轮完成!\r\n\r\n你已经完成过了然后了第二轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000005#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();
                }
            } else if (selection == 102) {
                if (cm.getBossLog('跑环2') > 0 && cm.getBossLog('跑环3') == 0 && cm.haveItem(4000007, 100)) {

                    cm.setBossLog('跑环3');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000007, -100);
					cm.gainItem(2022468, 10);
                    cm.gainMeso(+9000000);//读取变量
                    cm.gainExp(+200000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第三环，获取了大量奖励!");
                    cm.sendOk("跑商第三轮完成!\r\n\r\n你已经完成过了然后了第三轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000007#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();


                }
            } else if (selection == 103) {
                if (cm.getBossLog('跑环3') > 0 && cm.getBossLog('跑环4') == 0 && cm.haveItem(4000083, 100)) {

                    cm.setBossLog('跑环4');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000083, -100);
					cm.gainItem(2022468, 10);
                    cm.gainMeso(+15000000);//读取变量
                    cm.gainExp(+300000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第四环，获取了大量奖励!");
                    cm.sendOk("跑商第四轮完成!\r\n\r\n你已经完成过了然后了第四轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000083#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 104) {
                if (cm.getBossLog('跑环4') > 0 && cm.getBossLog('跑环5') == 0 && cm.haveItem(4000160, 100)) {

                    cm.setBossLog('跑环5');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000160, -100);
					cm.gainItem(2022468, 10);
                    cm.gainMeso(+20000000);//读取变量
                    cm.gainExp(+400000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第五环，获取了大量奖励!");
                    cm.sendOk("跑商第五轮完成!\r\n\r\n你已经完成过了然后了第五轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000160#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 105) {
                if (cm.getBossLog('跑环5') > 0 && cm.getBossLog('跑环6') == 0 && cm.haveItem(4000180, 100)) {

                    cm.setBossLog('跑环6');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000180, -100);
					cm.gainItem(2022468, 10);
					cm.gainItem(2340000, 5);
                    cm.gainMeso(+30000000);//读取变量
                    cm.gainExp(+500000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第六环，获取了大量奖励!");
                    cm.sendOk("跑商第六轮完成!\r\n\r\n你已经完成过了然后了第六轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000180#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 106) {
                if (cm.getBossLog('跑环6') > 0 && cm.getBossLog('跑环7') == 0 && cm.haveItem(4000274, 100)) {

                    cm.setBossLog('跑环7');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000274, -100);
					cm.gainItem(2022468, 10);
					cm.gainItem(4000464, 5);
                    cm.gainMeso(+40000000);//读取变量
                    cm.gainExp(+600000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第七环，获取了大量奖励!");
                    cm.sendOk("跑商第七轮完成!\r\n\r\n你已经完成过了然后了第七轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集100个#v4000274#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 107) {
                if (cm.getBossLog('跑环7') > 0 && cm.getBossLog('跑环8') == 0 && cm.haveItem(4000407, 30)) {

                    cm.setBossLog('跑环8');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000407, -30);
                  cm.gainItem(2022468, 30);
				  cm.gainItem(2614015, 5);
                    cm.gainMeso(+50000000);//读取变量
                    cm.gainExp(+700000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第八环，获取了大量奖励!");
                    cm.sendOk("跑商第八轮完成!\r\n\r\n你已经完成过了然后了第八轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集30个#v4000407#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 108) {
                if (cm.getBossLog('跑环8') > 0 && cm.getBossLog('跑环9') == 0 && cm.haveItem(4000402, 30)) {

                    cm.setBossLog('跑环9');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000402, -30);
                  cm.gainItem(2022468, 40);
				  cm.gainItem(2614015, 5);
                    cm.gainMeso(+100000000);//读取变量
                    cm.gainExp(+8000000);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第九环，获取了大量奖励!");
                    cm.gainvip(+1);
                    cm.sendOk("跑商第九轮完成!\r\n\r\n你已经完成过了然后了第九轮，继续进行下一环吧.");
                    cm.dispose();
                } else {
                    cm.sendOk("收集30个#v4000402#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 109) {
                if (cm.getBossLog('跑环9') > 0 && cm.getBossLog('跑环10') == 0 && cm.haveItem(4000406, 30)) {

                    cm.setBossLog('跑环10');//cm.gainPS(1);  的意思是 你完成跑商第一环的时候给予你 跑商值+1这样你就无法在重复做第一环了。只有凌晨12点刷新才行！

                    cm.gainItem(4000406, -30);
					cm.gainItem(2022468, 50);
					cm.gainItem(2614015, 10);
					cm.gainItem(4000464, 5);
                    cm.gainMeso(+200000000);//读取变量
                    cm.gainExp(+1000000);
					cm.spawnMonster(9300340,1);
                    cm.worldMessage(6, "【跑商每日任务】[" + cm.getName() + "]成功完成了第十环，获取了大量奖励!蛋糕怪物出现了！");
                    cm.sendOk("跑商第十轮完成!\r\n\r\n你已经完成过了然后了第十轮.蛋糕怪物出现了！");
                    cm.dispose();
                } else {
                    cm.sendOk("收集30个#v4000406#交给我!\r\nPS：您已经完成过该环节，或者没完成上一环，或者材料不足！");
                    cm.dispose();

                }
            } else if (selection == 10) {
                if (cm.haveItem(4001083, 1) && cm.getBossLog('PlayQuest90') < 1) {
                    cm.gainItem(4001083, -1);
                    cm.setBossLog('PlayQuest90');
                    cm.gainNX(2000);
                    cm.sendOk("任务完成,获得以下奖励:\r\n2000点卷");
                    cm.dispose();
                } else {
                    cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
                    cm.dispose();
                }

            } else if (selection == 11) {
                if (cm.haveItem(4001126, 1000) && cm.getBossLog('PlayQuest14') < 1) {
                    cm.gainItem(4001126, -1000);
                    cm.setBossLog('PlayQuest14');
                    cm.gainNX(1000);
                    cm.sendOk("任务完成,获得以下奖励:\r\n1000点卷");
                    cm.dispose();
                } else {
                    cm.sendOk("请检查是否有足够的物品。\r\n#r(注:该任务每天只能完成一次)#k");
                    cm.dispose();
                }
            }
        }
    }
}
