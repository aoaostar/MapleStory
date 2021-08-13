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
	    cm.sendOk("喂...喂..快把藏宝图给我找出来...因为把地图给弄丢了，害得现在都没办法航海了！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
