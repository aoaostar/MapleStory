function action(mode, type, selection) {
	if(cm.isLeader()){//是队长 少了个括号
	//奇怪怎么无效。难道要有队员。
		cm.给组队物品(4002003, 1);//水灵邮票1个
		cm.给组队物品(4002000, 1);//蜗牛邮票
		cm.给组队物品(4031456, 1);//枫叶珠
		cm.给组队物品(4170007, 1);//猪蛋
		cm.给组队物品(4310149, 2);//祝福卷
		cm.给组队金币(+1000000);//读取变量
		cm.getPlayer().endPartyQuest(1202); //might be a bad implentation.. incase they dc or something
		cm.removeAll(4001022);
		cm.removeAll(4001023);
		cm.warpParty(922010000);//队长领完全队奖励，直接传送队伍出去。
		cm.喇叭(3, "[" + cm.getPlayer().getName() + "]成功通关【组队任务 - 玩具城组队】，获得飞天猪的蛋奖励！");
		cm.dispose();
   } else{ //不是队长
	    cm.sendOk("请让队长来点我领取全队奖励。");
	    cm.dispose();
    }
}