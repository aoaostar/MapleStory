//////////////////////////////
//七宝*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////

var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/1#";
var Y = "#fUI/GuildMark.img/Mark/Letter/00005024/3#";
var X = "#fUI/GuildMark.img/Mark/Letter/00005023/1#";
var D = "#fUI/GuildMark.img/Mark/Letter/00005003/1#";
var M = "#fUI/GuildMark.img/Mark/Letter/00005012/1#";
var A = "#fUI/GuildMark.img/Mark/Letter/00005000/1#";
var P = "#fUI/GuildMark.img/Mark/Letter/00005015/1#";
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/9#";
var 锻造技艺 = "#fEffect/SkillName1.img/1001002/锻造技艺#";
var 枫叶天梯 = "#fEffect/SkillName1.img/1001002/枫叶天梯#";
var 个人信息 = "#fEffect/SkillName1.img/1001002/个人信息#";
var 公告专区 = "#fEffect/SkillName1.img/1001002/公告专区#";
var 活动专区 = "#fEffect/SkillName1.img/1001002/活动专区#";
var 角色装扮 = "#fEffect/SkillName1.img/1001002/角色装扮#";
var 今日签到 = "#fEffect/SkillName1.img/1001002/今日签到#";
var 快捷商店 = "#fEffect/SkillName1.img/1001002/快捷商店#";
var 快速转职 = "#fEffect/SkillName1.img/1001002/快速转职#";
var 礼包商店 = "#fEffect/SkillName1.img/1001002/礼包商店#";
var 清理背包 = "#fEffect/SkillName1.img/1001002/清理背包#";
var 任务手册 = "#fEffect/SkillName1.img/1001002/任务手册#";
var 试炼专区 = "#fEffect/SkillName1.img/1001002/试炼专区#";
var 随身仓库 = "#fEffect/SkillName1.img/1001002/随身仓库#";
var 天赋 = "#fEffect/SkillName1.img/1001002/天赋#";
var 现金商店 = "#fEffect/SkillName1.img/1001002/现金商店#";
var 音乐点播 = "#fEffect/SkillName1.img/1001002/音乐点播#";
var 至尊喇叭 = "#fEffect/SkillName1.img/1001002/至尊喇叭#";
var 自由传送 = "#fEffect/SkillName1.img/1001002/自由传送#";
var 标题 = "#fEffect/SkillName1.img/1001002/标题#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	
	var MC = cm.getServerName();
    
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {

    var  
	    selStr = "#r"+MC+"#b 上线提醒喇叭设置；[#r#e全服推送#k#n]#k\r\n";
		
		selStr += "喇叭点；[#r "+ cm.getBossRank("喇叭点",2)+" #k]\r\n";
	    if(cm.getBossRank("上线提示语1",2) == 2 ){
		selStr += "上线提示语；#b◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇#k\r\n";} 
		if(cm.getBossRank("上线提示语2",2) == 2 ){
		selStr += "上线提示语；#b★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★#k\r\n";} 
		else {
		selStr += "上线提示语；#r暂未设置#k#n\r\n";}
		
		
		
		if(cm.getBossRank("公告喇叭",2) == 2 ){
		selStr += "提示语类型；#b[聊天栏蓝色类型/1000]#k\r\n";} 
		
   else if(cm.getBossRank("顶端喇叭",2) == 2 ){
		selStr += "提示语类型；#b[顶上端黄色类型/5000]#k\r\n";} 
		
   else if(cm.getBossRank("弹窗喇叭",2) == 2 ){
		selStr += "提示语类型；#b[屏幕中弹窗类型/5500]#k\r\n";} 
		
		
		else {
		selStr += "提示语类型；#r暂未设置#k\r\n";}
		
		
		
		
		
	
		selStr += "#L0##b"+箭头+"返回界面#k#l\r\n";
		
		selStr += "#L4##b"+箭头+"充值喇叭点#k#l\r\n";
		
	     
	     if(cm.getBossRank("上线喇叭",2) == 1 ){
		selStr += "#L2##b"+箭头+"#r未开启 - 未开启上线喇叭#k#l\r\n\r\n";
		selStr += "#e#r\r\n      你尚未开启上线提醒#k#n";} 
	else if(cm.getBossRank("上线喇叭",2) == 2 ){
	    selStr += "#L3##b"+箭头+"#g已开启 - 已开启上线喇叭#k#l\r\n\r\n\r\n";}
		else {
		selStr += "#L1##n"+箭头+"未开通#k#l\r\n\r\n";
		selStr += "#e#r你尚未开通上线提醒#k#n";}
		
		
		if(cm.getBossRank("上线喇叭",2) == 1 ){
		selStr += "\r\n\r\n";} 
	else if(cm.getBossRank("上线喇叭",2) == 2 ){
	    selStr += "\r\n\r\n---------------#r以下是提示语类型设置#k---------------\r\n\r\n";}
		
	     if(cm.getBossRank("公告喇叭",2) == 1 && cm.getBossRank("上线喇叭",2) == 2){
		selStr += "#L5##b"+箭头+"#r关闭中#k[聊天栏蓝色类型/1000]#k#l\r\n";} 
	else if(cm.getBossRank("公告喇叭",2) == 2 && cm.getBossRank("上线喇叭",2) == 2){
	    selStr += "#L6##b"+箭头+"#g开启中#k[聊天栏蓝色类型/1000]#k#l\r\n";}

		
		 if(cm.getBossRank("顶端喇叭",2) == 1 && cm.getBossRank("上线喇叭",2) == 2){
		selStr += "#L7##b"+箭头+"#r关闭中#k[顶上端黄色类型/5000]#k#l\r\n";} 
	else if(cm.getBossRank("顶端喇叭",2) == 2 && cm.getBossRank("上线喇叭",2) == 2){
	    selStr += "#L8##b"+箭头+"#g开启中#k[顶上端黄色类型/5000]#k#l\r\n";}
		
		 if(cm.getBossRank("弹窗喇叭",2) == 1 && cm.getBossRank("上线喇叭",2) == 2){
		selStr += "#L9##b"+箭头+"#r关闭中#k[屏幕中弹窗类型/5500]#k#l\r\n";} 
	else if(cm.getBossRank("弹窗喇叭",2) == 2 && cm.getBossRank("上线喇叭",2) == 2){
	    selStr += "#L10##b"+箭头+"#g开启中#k[屏幕中弹窗类型/5500]#k#l\r\n";}

		
		
		
		
			
	    if(cm.getBossRank("上线喇叭",2) == 1 ){
		selStr += "\r\n\r\n";} 
	else if(cm.getBossRank("上线喇叭",2) == 2 ){
	    selStr += "\r\n\r\n---------------#r以下是提示语语句设置#k---------------\r\n\r\n";}

		
		
		
		
		
		 if(cm.getBossRank("上线提示语1",2) == 1 ){
		selStr += "\r\n#L30##b"+箭头+"#r[未引用]#k#b◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇#k#l\r\n";} 
	else if(cm.getBossRank("上线提示语1",2) == 2 ){
	    selStr += "\r\n#L31##b"+箭头+"#g[引用]#k#b◇天空一声巨响，"+ cm.getChar().getName() +"闪亮登场◇#k#l\r\n";}
		
		 if(cm.getBossRank("上线提示语2",2) == 1 ){
		selStr += "\r\n#L32##b"+箭头+"#r[未引用]#k#b★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★#k#l\r\n";} 
	else if(cm.getBossRank("上线提示语2",2) == 2 ){
	    selStr += "\r\n#L33##b"+箭头+"#g[引用]#k#b★我，"+ cm.getChar().getName() +"，于"+year+" 年"+month+"月"+day+"日"+ hour+"时光临"+MC+"★#k#l\r\n";}
		
		





         
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
        case 0:
            cm.dispose();
            cm.openNpc(9900004,3);	
            break;
		case 1://开通喇叭 值+1 显示关闭
            cm.setBossRankCount("上线喇叭");				
			cm.setBossRankCount("公告喇叭");				
			cm.setBossRankCount("顶端喇叭");	
            cm.setBossRankCount("弹窗喇叭");	
            cm.setBossRankCount("上线提示语1",2);	
            cm.setBossRankCount("上线提示语2");				
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;
		case 2://开启上线提醒，显示喇叭种类
            cm.setBossRankCount("上线喇叭","-"+cm.getBossRank("上线喇叭",2)+"");
			cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
			cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
			cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
			cm.setBossRankCount("上线提示语1","-"+cm.getBossRank("上线提示语1",2)+"");
			cm.setBossRankCount("上线提示语2","-"+cm.getBossRank("上线提示语2",2)+"");
			cm.setBossRankCount("公告喇叭");
			cm.setBossRankCount("弹窗喇叭");
			cm.setBossRankCount("顶端喇叭");
			cm.setBossRankCount("上线提示语1",2);	
			cm.setBossRankCount("上线提示语2");
			cm.setBossRankCount("上线喇叭",2);
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;
		case 3://关闭上线提醒，变更值为1，关闭显示喇叭种类//取回各种喇叭值为0，防止再次开启出BUG
            cm.setBossRankCount("上线喇叭","-"+cm.getBossRank("上线喇叭",2)+"");
			cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
			cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
			cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
			cm.setBossRankCount("上线提示语1","-"+cm.getBossRank("上线提示语1",2)+"");
			cm.setBossRankCount("上线提示语2","-"+cm.getBossRank("上线提示语2",2)+"");
			cm.setBossRankCount("上线喇叭");
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;	
		case 4://充值
            cm.dispose();
            cm.openNpc(9900007,3);	
            break;		
		case 5://显示为关闭，需开启公告喇叭，先减去所有喇叭值，开启+2，关闭+1
		    cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
			cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
			cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
            cm.setBossRankCount("公告喇叭",2);	
			cm.setBossRankCount("顶端喇叭");	
			cm.setBossRankCount("弹窗喇叭");
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;	
		case 6://显示为开启，再点击变成关闭，先去掉单个值，增加单个值+1显示为关
		    cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
            cm.setBossRankCount("公告喇叭");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;	
		case 7://显示为关闭，需开启公告喇叭，先减去所有喇叭值，开启+2，关闭+1
		    cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
			cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
			cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
            cm.setBossRankCount("公告喇叭");
            cm.setBossRankCount("弹窗喇叭");			
			cm.setBossRankCount("顶端喇叭",2);	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;	
        case 8://显示为开启，再点击变成关闭，先去掉单个值，增加单个值+1显示为关
		    cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
            cm.setBossRankCount("顶端喇叭");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;				
		case 10://显示为开启，再点击变成关闭，先去掉单个值，增加单个值+1显示为关
		    cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
            cm.setBossRankCount("弹窗喇叭");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
			break;
		case 9://显示为关闭，需开启公告喇叭，先减去所有喇叭值，开启+2，关闭+1
		    cm.setBossRankCount("公告喇叭","-"+cm.getBossRank("公告喇叭",2)+"");
			cm.setBossRankCount("顶端喇叭","-"+cm.getBossRank("顶端喇叭",2)+"");
			cm.setBossRankCount("弹窗喇叭","-"+cm.getBossRank("弹窗喇叭",2)+"");
            cm.setBossRankCount("公告喇叭");
            cm.setBossRankCount("弹窗喇叭",2);			
			cm.setBossRankCount("顶端喇叭");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
            break;		
			
			
			
			
			
		



        case 30://提示1
		    cm.setBossRankCount("上线提示语1","-"+cm.getBossRank("上线提示语1",2)+"");
			cm.setBossRankCount("上线提示语2","-"+cm.getBossRank("上线提示语2",2)+"");
			cm.setBossRankCount("上线提示语2");	
            cm.setBossRankCount("上线提示语1",2);	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
			break;		
		case 31://提示1 关闭
		    cm.setBossRankCount("上线提示语1","-"+cm.getBossRank("上线提示语1",2)+"");
            cm.setBossRankCount("上线提示语1");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
			break;	

        case 32://提示1
		    cm.setBossRankCount("上线提示语1","-"+cm.getBossRank("上线提示语1",2)+"");
			cm.setBossRankCount("上线提示语2","-"+cm.getBossRank("上线提示语2",2)+"");
			cm.setBossRankCount("上线提示语2",2);	
            cm.setBossRankCount("上线提示语1");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
			break;		
		case 33://提示1 关闭
		    cm.setBossRankCount("上线提示语2","-"+cm.getBossRank("上线提示语2",2)+"");
            cm.setBossRankCount("上线提示语2");	
			cm.dispose();
			cm.openNpc(9900004,990000401);	
			break;				
			
			
			
			
		
		
	    
		
			 
			} 
		}
    }
