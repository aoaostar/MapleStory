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
	    cm.sendOk("大保健300全......？？？啊啊，不好意思，欢迎您来到豆豆屋，有什么需要我帮忙的么！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
