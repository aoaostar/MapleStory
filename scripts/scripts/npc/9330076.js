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
	    cm.sendOk("我师傅爹上次去了尼姑庵以后，过了5个月我就出生了！我好幸福！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
