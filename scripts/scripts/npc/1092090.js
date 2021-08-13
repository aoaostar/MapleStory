






var chance1 = Math.floor(Math.random()*200+1);
var chance2 = Math.floor(Math.random()*50);
var chance3 = (Math.floor(Math.random()*20)+1);
var chance4 = Math.floor(Math.random()*2+1);
var itemchance = chance1 + chance2 + chance3 * chance4;
var status = 0;

function start() 
	{
	status = -1;
	action(1, 0, 0);


	}

function action(mode, type, selection)
{
	if (mode == -1)
	{
		cm.dispose();
	}
	else if (mode == 0)
	{
		cm.sendOk("好的如果要出去随时来找我.");
		cm.dispose();
	}else 
	{
		if (mode == 1)
			status++;
		else
			status--;		
	if (status == 0)
	{		
		cm.sendYesNo("当前时间是#b" + cm.getHour() + "时:" + cm.getMin() + "分:" + cm.getSec() + "秒. \r\n#k#r每天20点整#k点击随机获取点券!\r\n每次点击都会获得点卷,运气不错时可以获得更多!\r\n只有5分钟时间,加油!!\r\n\r\n" );	
	}
	else if (status == 1) {
		if (cm.getHour() < 20  ||cm.getHour() > 20 ) {
cm.sendOk("活动时间还没到.\r\n#r现在服务器时间:" + cm.getHour() + "时:" + cm.getMin() + "分:" + cm.getSec() + "秒");
cm.dispose();
 } else if (cm.getMin() > 4) {
cm.sendOk("已经过了哦.\r\n#r现在服务器时间:" + cm.getHour() + "时:" + cm.getMin() + "分:" + cm.getSec() + "秒");
cm.dispose();


 }else if (cm.getLevel() > 0 ) {
       if ((itemchance >= 30) && (itemchance <= 50)) { 
var zz =4001126;
cm.gainNX(1);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 1 个点卷.");
cm.dispose();
}else if ((itemchance >= 70) && (itemchance <= 80)) { 
var zz =4001126;
cm.gainNX(2);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 2 个点卷.");
cm.dispose();
}else if ((itemchance >= 80) && (itemchance <= 90)) { 
var zz =4001126;
cm.gainNX(3);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 3 个点卷.");
cm.dispose();
}else if ((itemchance >= 90) && (itemchance <= 110)) { 
var zz =4001126;
cm.gainNX(4);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 4 个点卷.");
cm.dispose();
}else if ((itemchance >= 110) && (itemchance <= 120)) { 
var zz =4001126;
cm.gainNX(5);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 5 个点卷.");
cm.dispose();
}else if ((itemchance >= 120) && (itemchance <= 140)) { 
var zz =4001126;
cm.gainNX(6);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 6 个点卷.");
cm.dispose();
}else if ((itemchance >= 190) && (itemchance <= 200)) { 
var zz =4001126;
cm.gainNX(7);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 7 个点卷.");
cm.dispose();
}else if ((itemchance >= 225) && (itemchance <= 230)) { 
var zz =4001126;
cm.gainNX(8);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 8 个点卷.");
cm.worldMessage(6,"玩家："+cm.getName()+" 在[20点无与伦比]抽取了8点券！");
cm.dispose();
}else{
var zz =4001126;
cm.gainNX(3);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 3 个点卷.");
cm.dispose();}

 }else if (cm.getName().getVip() == 1 ||cm.getName().getVip() == 2 ||cm.getName().getVip() == 3 ||cm.getName().getVip() == 4 ||cm.getName().getVip() == 5) {
if ((itemchance >= 30) && (itemchance <= 50)) { 
var zz =4001126;
cm.gainNX(6);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 6 个点卷.");
cm.dispose();
}else if ((itemchance >= 70) && (itemchance <= 90)) { 
var zz =4001126;
cm.gainNX(12);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 12 个点卷.");
cm.worldMessage(6,"玩家："+cm.getName()+" 在20点无与伦比抽取了12点券！");
cm.dispose();
}else if ((itemchance >= 110) && (itemchance <= 140)) { 
var zz =4001126;
cm.gainNX(10);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 10 个点卷.");
cm.dispose();
}else if ((itemchance >= 170) && (itemchance <= 200)) { 
var zz =4001126;
cm.gainNX(6);
cm.getC().getChannelServer().getWorldInterface().broadcastMessage(null, net.sf.cherry.tools.MaplePacketCreator.serverNotice(12,cm.getC().getChannel(),"[无与伦比]" + " : " + cm.getName().getName() +" 疯狂点击获得 6 个点卷.大家鼓掌.",true).getBytes());
cm.dispose();
}else if ((itemchance >= 210) && (itemchance <= 230)) { 
var zz =4001126;
cm.gainNX(8);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 8 个点卷.");
cm.dispose();
}else{
var zz =4001126;
cm.gainNX(12);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 12 个点卷.");
cm.dispose();}
 }else if (cm.getName().getVip() == 1 ||cm.getName().getVip() == 2 ||cm.getName().getVip() == 3 ||cm.getName().getVip() == 4 ||cm.getName().getVip() == 5) {
if ((itemchance >= 30) && (itemchance <= 50)) { 
var zz =4001126;
cm.gainNX(6);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 6 个点卷.");
cm.dispose();
}else if ((itemchance >= 70) && (itemchance <= 90)) { 
var zz =4001126;
cm.gainNX(12);
//cm.getC().getChannelServer().getWorldInterface().broadcastMessage(null, net.sf.cherry.tools.MaplePacketCreator.serverNotice(12,cm.getC().getChannel(),"[无与伦比]" + " : " + cm.getName().getName() +" 疯狂点击获得 #r12#k 个点卷大家鼓掌.",true).getBytes());
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 12 个点卷.");
cm.worldMessage(6,"玩家："+cm.getName()+" 在20点无与伦比抽取了12点券！");
cm.dispose();
}else if ((itemchance >= 110) && (itemchance <= 140)) { 
var zz =4001126;
cm.gainNX(6);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 6 个点卷.");
cm.dispose();
}else if ((itemchance >= 170) && (itemchance <= 200)) { 
var zz =4001126;
cm.gainNX(6);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 6 个点卷.");
cm.dispose();
}else if ((itemchance >= 210) && (itemchance <= 230)) { 
var zz =4001126;
cm.gainNX(18);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 18 个点卷.");
cm.worldMessage(6,"玩家："+cm.getName()+" 在20点无与伦比抽取了18点券！");
cm.dispose();
}else{
var zz =4001126;
cm.gainNX(4);
cm.serverNotice("[20点无与伦比]:[" + cm.getName() + "]疯狂点击获得 4 个点卷.");
cm.dispose();}
		 }else {
cm.sendOk("活动时间还没到.\r\n#r现在服务器时间:" + cm.getHour() + "时:" + cm.getMin() + "分:" + cm.getSec() + "秒");
		cm.dispose();	
	}
}
}}
