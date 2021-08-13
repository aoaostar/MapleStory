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
		var r = Math.ceil(Math.random() * 50);
		var r1 = Math.ceil(Math.random() * 21);
        if (mode == 1) {
            status++;
        } else {
            status--;
        } 
        if (status === 0) {
			
        cm.gainNX(r);
		cm.worldMessage(6,"玩家 "+cm.getName()+" 在市场"+r1+"找到了神秘人物并领取了"+r+"点券。");
		cm.warp(910000000);
		//cm.setBossLog("qiyu");
        cm.sendOk("恭喜恭喜~");
                    cm.dispose();
                }
       
    }
}