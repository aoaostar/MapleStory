var status = -1;
var need = 50000;

function start() {
 action(1, 0, 0);
}
 
function action(mode, type, selection){
	 if(mode == 1){
		 status++;
	 } else {
		 cm.dispose();
		 returm;
	 }
	 if(status == 0){
		 cm.sendYesNo("你想坐上我的双峰么？坐一次很昂贵哦~\r\n\r\n金币需要#r" + need);
	 } else if(status == 1){
		 if(cm.getPlayer().getMeso() < need){
			 cm.sendNext("你的金币不足 ");
			 cm.dispose();
			 return;
		 }
		 cm.gainMeso(-need);
		if (cm.getMapId() == 260020000) {
			cm.warp(260020700, 0);
		} else { // 260020700
			cm.warp(260020000, 1);
        }
		cm.dispose();
		}
 }

