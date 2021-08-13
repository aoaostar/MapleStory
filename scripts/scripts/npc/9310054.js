importPackage(Packages.client);

var status = 0;
var jobName;
var job;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.sendOk("天气很好哦~~如果你改变想法记的随时来找我。祝你好运！");
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendYesNo("嗨，我是 #b骑士团引导者#k 我可以帮助你转换成初心者哦~~！\r\n#r特别须知：骑士团一转去林中找神秘NPC\r\n二转通过转职任务！\r\n");
        }else if(status == 1){
			if(cm.getPlayer().getJob() == 0){
            cm.changeJob(1000);
			cm.getPlayer().setRemainingSp(1);cm.sendOk("转职成功！加油锻炼哦！");
            cm.dispose();
			}else{
				cm.sendOk("对不起，您不是新手，我不能为你服务！")
			}
        }
    }
}