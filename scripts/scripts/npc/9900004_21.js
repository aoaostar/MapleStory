var status = -1;

function start(){
	action(1, 0, 0);
}

function action(mode, type ,selection) {
	if(mode == 0 && status == 0) {
		status --;
	} else if(mode == 1) {
		status ++;
	} else {
		cm.dispose();
		return;
	}
	
	if (status == 0) {
		cm.sendOk("我是本服的道具回收NPC\r\n我可以帮你清理掉你不想要的物品！\r\n\r\n#L1#我要选择性删除道具#l\r\n");
	} else if (selection == 1){
		cm.openNpc(9900004,1911);
	} else if (selection == 2){
		cm.openNpc(9900004,1922);
	}
}