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
        cm.sendOk("");
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendYesNo("切换到冰雷模式？");
        }else if(status == 1){
			if(cm.getPlayer().getJob() == 212 || cm.getPlayer().getJob() == 220 || cm.getPlayer().getJob() == 211 ){
            cm.changeJob(222);
			cm.getPlayer().setRemainingSp(0);cm.sendOk("切换成功~");
            cm.dispose();
			}else{
				cm.sendOk("你已经是冰雷模式。")
				cm.dispose();
			}
        }
    }
}