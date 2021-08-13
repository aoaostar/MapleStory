
function start() {
	if(cm.getzb() > 0){
			var 充值金额 = cm.getzb();
			var 点卷倍率 = 充值金额 * 150;
		//	var 积分倍率 = 充值金额 * 1;
			cm.gainNX(点卷倍率);
		//	cm.gainjf(积分倍率);
			cm.setzb(-充值金额);
			cm.sendOk("您已成功领取： "+点卷倍率+"点卷!\r\n点卷已添加到您的帐户! \r\n享受吧! #r赶快去商城购买你喜爱的商品吧!#k");//\r\n同时还获得了:"+积分倍率+"积分!
			cm.getChar().saveToDB(false, false);
			cm.worldMessage(12, cm.getC().getChannel(),"〖充值系统〗" + " : " + " [" + cm.getPlayer().getName() + "]充值了"+ 点卷倍率 +"点卷,赶快去商场挑选喜欢的物品吧！", true);  
			cm.dispose();
		} else {
			cm.sendOk("请确认你是否充值点卷了!");
			cm.dispose();
		}
}

