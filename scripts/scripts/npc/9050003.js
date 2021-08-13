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
	    cm.sendOk("如果你愿意带我去兜风的话，我可以给你个宝贝！");
		cm.dispose();
            break;
        case 1: //
            break;
    }
}
