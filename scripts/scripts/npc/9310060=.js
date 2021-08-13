var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 0) {
		cm.dispose();
	return;
    } else if (mode == 1){
	status++;
    } else {
	status--;
    }

    switch (status) {
        case 0: 
	    cm.sendOk("听说史莱克冒险岛来了一个超级大富豪，只要完成他的任务，就可以得到一种特别的赚钱法门哟！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
