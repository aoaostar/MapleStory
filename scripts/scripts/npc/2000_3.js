var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 图标1 = "#fUI/UIWindow.img/FadeYesNo/icon7#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";

var 蘑菇 = "#fUI/UIWindow.img/Minigame/Common/mark#";
function start() {
    status = -1;
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (status <= 0 && mode <= 0) {
        cm.dispose();
        return
    }
    if (mode == 1) {
        status++
    } else {
        status--
    }	 
		
		var MC = cm.getServerName();
		var 挖矿经验 =  cm.getBossRank("挖矿经验",2);
		var 挖矿数量卡 =  cm.getBossRank("挖矿数量卡",2);
		var 挖矿经验卡 =  cm.getBossRank("挖矿经验卡(1天)",2);		
		var 算法0 = 100/200;
		
		var 算法1 = 100/400;
		var 算法2 = 100/800;	
		var 算法3 = 100/1500;		
		var 算法4 = 100/2000;
		var 算法5 = 100/5000;
		var 算法6 = 100/10000;
		var 算法7 = 100/30000;
		var 算法8 = 100/50000;
		var 挖矿2倍经验卡单价 = 5000;	
		var 挖矿3倍经验卡单价 = 10000;
		if (cm.getPlayer().getGMLevel() == 99) {
            cm.showInstruction("\r\n#e#r"+MC+"\r\n\r\n你无权使用#n#k\r\n ", 200, 3);  
            cm.dispose();
          return;
	 
	    
/*[挖矿入门] 1-2；100   
[挖矿门徒] 2-3；300
[挖矿专员] 3-4；700
[挖矿小能手] 4-5；1500
[挖矿专业户] 5-6；3000
[挖矿大手] 6-7；5000
[挖矿大能手] 7-8；10000
[挖矿爆发户] 8-9；20000
[挖矿之神] 9-10；50000
[孤独的挖矿者] 10；xxxx
*/
	    
    } else if (status <= 0) {
        var selStr = "  #v1322071#  "+心+"  "+心+"  #e#r"+MC+"挖矿助手#k#n  "+心+"  "+心+"  #v1322071#\r\n\r\n";
 
		selStr +=""+蘑菇+" 角色: #r"+ cm.getChar().getName() +"#k\r\n";	
    	if(挖矿经验 > 50000){
		selStr +=""+蘑菇+" 等级: #e#b10#k#n 级 [#r"+((挖矿经验-50000)*算法8).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-50000)*算法8+"#  [ #rxxx#k / #b"+(挖矿经验-50000)+"#k ]\r\n";//8级				
}else	if(挖矿经验 > 20000){
		selStr +=""+蘑菇+" 等级: #e#b9#k#n 级 [#r"+((挖矿经验-30000)*算法7).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-30000)*算法7+"#  [ #r30000#k / #b"+(挖矿经验-30000)+"#k ]\r\n";//8级					
}else	if(挖矿经验 > 10000){
		selStr +=""+蘑菇+" 等级: #e#b8#k#n 级 [#r"+((挖矿经验-10000)*算法6).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-10000)*算法6+"#  [ #r10000#k / #b"+(挖矿经验-10000)+"#k ]\r\n";//7级			
}else	if(挖矿经验 > 5000){
		selStr +=""+蘑菇+" 等级: #e#b7#k#n 级 [#r"+((挖矿经验-5000)*算法5).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-5000)*算法5+"#  [ #r5000#k / #b"+(挖矿经验-5000)+"#k ]\r\n";//6级			
}else	if(挖矿经验 > 3000){
		selStr +=""+蘑菇+" 等级: #e#b6#k#n 级 [#r"+((挖矿经验-3000)*算法4).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-3000)*算法4+"#  [ #r2000#k / #b"+(挖矿经验-3000)+"#k ]\r\n";//5级		
}else	if(挖矿经验 > 1500){
		selStr +=""+蘑菇+" 等级: #e#b5#k#n 级 [#r"+((挖矿经验-1500)*算法3).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-1500)*算法3+"#  [ #r1500#k / #b"+(挖矿经验-1500)+"#k ]\r\n";	//4级			
}else	if(挖矿经验 > 700){
		selStr +=""+蘑菇+" 等级: #e#b4#k#n 级 [#r"+((挖矿经验-700)*算法2).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+(挖矿经验-700)*算法2+"#  [ #r800#k / #b"+(挖矿经验-700)+"#k ]\r\n";	//3级			
}else	if(挖矿经验 > 300){
		selStr +=""+蘑菇+" 等级: #e#b3#k#n 级 [#r"+((挖矿经验-300)*算法1).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 挖矿经验: #B"+(挖矿经验-300)*算法1+"#  [ #r400#k / #b"+(挖矿经验-300)+"#k ]\r\n";	//2级	
}else	if(挖矿经验 > 100){
		selStr +=""+蘑菇+" 等级: #e#b2#k#n 级 [#r"+((挖矿经验-100)*算法0).toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 挖矿经验: #B"+(挖矿经验-100)*算法0+"#  [ #r200#k / #b"+(挖矿经验-100)+"#k ]\r\n";	//			
}else{  selStr +=""+蘑菇+" 等级: #e#b1#k#n 级 [#r"+挖矿经验.toFixed(3)+"%#k]\r\n";//1级
		selStr +=""+蘑菇+" 经验: #B"+挖矿经验+"#  [ #r100#k / #b"+挖矿经验+"#k ]\r\n";//1级
	 }	
	 
	 if(cm.getBossRank("挖矿经验卡(1天)",2)>0 || cm.getBossRank("挖矿数量卡",2) > 0){
		selStr +="\r\n━━━━━━━━━━━━[增幅卡]━━━━━━━━━━━\r\n";//
		if(cm.getBossRank("挖矿经验卡(1天)",2)== 3){
		selStr +="#d[3倍经验卡]#k #g■#k ┃ ";
}
		if(cm.getBossRank("挖矿经验卡(1天)",2)== 2){
		selStr +="#d[2倍经验卡]#k #g■#k ┃ ";
}
		if(cm.getBossRank("挖矿数量卡",2) > 0){
		selStr +="#d[挖矿数量卡]: #b"+挖矿数量卡+"#k 张";
}
		
		selStr +="\r\n━━━━━━━━━━━━━━━━━━━━━━━━━━━\r\n";//
}
		selStr +="#d[双倍挖矿经验卡]#b单价：#r"+挖矿2倍经验卡单价+"#k #b点券#k|       #e#r提示：#k#n\r\n";//
		selStr +="#d[三倍挖矿经验卡]#b单价：#r"+挖矿3倍经验卡单价+"#k #b点券#k|#e#r经验卡每日23:59失效#k#n\r\n";//
		selStr +="#L1#"+箭头+"#b挖矿说明\r\n";//
		if(!cm.haveItem(1322071)){
			if( cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1322071) == null){
			selStr +="#L2#"+箭头+"#b购买挖矿工具\r\n";
			}
		}
		
		selStr +="#L3#"+箭头+"#b购买挖矿数量卡#l\r\n";//
		selStr +="#L4#"+箭头+"#b购买挖矿2倍经验卡(1天)#l\r\n";//
		selStr +="#L5#"+箭头+"#b购买挖矿3倍经验卡(1天)#l\r\n";//
 
 
 
 
 
 
 
 
			
        cm.sendSimple(selStr)
    } else if (status == 1) {
        switch (selection) {
			case 1:
            cm.dispose();
            cm.openNpc(9900007,102);
            break
			case 3:
            cm.dispose();
            cm.openNpc(9000018,864);
            break
			
			case 4:
			if(cm.getPlayer().getCSPoints(1) >= 挖矿2倍经验卡单价){
				cm.gainNX(-挖矿2倍经验卡单价);
				cm.setBossRankCount("挖矿经验卡(1天)",-挖矿经验卡);
				cm.setBossRankCount("挖矿经验卡(1天)",2);
			    cm.setBossLog("挖矿经验卡(1天)");
				cm.dispose();
		} else {
				cm.sendOk(" 双倍挖矿卡需要  #r"+挖矿2倍经验卡单价+"#k  点券购买。");
                cm.dispose();
		} 
            break
			
			
			
			
			
			
			
			
			
			
			
			
			case 2:
		if(cm.getPlayer().getMeso() >= 10000){
			 cm.gainMeso(-10000);
			 cm.gainItem(1322071,1,1);
			 cm.sendOk("成功购买 #v1322071# #b#t1322071##k #r1天#k");
             cm.dispose();
             
	} else {
			 cm.sendOk("需要 #b10000#k 金币哦！");
             cm.dispose();
	}           
            break
			
			
			
			
			
			
			
			
			
			
			
			
			

        }
    }
}