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
	    cm.sendOk("你愿意出家么？做我的小跟班，我带你去尼姑庵！！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
