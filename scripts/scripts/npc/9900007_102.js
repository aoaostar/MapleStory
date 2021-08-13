var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 矿 = "#fReactor/2112007.img/0/0#";
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";

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
		
		if (cm.getPlayer().getGMLevel() == 99) {
            cm.showInstruction("\r\n#e#r"+MC+"\r\n\r\n你无权使用#n#k\r\n ", 200, 3);  
            cm.dispose();
          return;
	 
	    
	    
    } else if (status <= 0) {
        var selStr = "#e#r挖矿说明#k#n\r\n\r\n";
 
		selStr +=" 1. 挖矿首先你需要一把  #v1322071# #b#t1322071##k\r\n";//
		selStr +=" 2. 然后在有 "+矿+" 的地图，\r\n    装备 #v1322071# #b#t1322071##k 就可以进行挖矿\r\n";//
		
		
		selStr +=" 3. 每次挖矿会获得#b各种矿物,金币，经验，材料#k等，根据挖\r\n    矿等级，获得的东西也会不同。\r\n";//
		
		
		selStr +=" 4. 每次挖矿会获得#r挖矿经验#k。挖矿经验会提高挖矿等级，\r\n    可以购买增幅卡，来快速累积经验。\r\n";//
		
		
		selStr +=" 5. 不同类型的卡可以叠加。\r\n";//
		
		//selStr +="\r\n\r\n\t\t\t\t #L0#"+箭头+"#b返回\r\n";//
 
 
 
 
 
 
 
 
			
        cm.sendSimple(selStr)
    } else if (status == 1) {
        switch (selection) {
			case 0:
            cm.dispose();
            cm.openNpc(1022004,0);
            break
			
			
			
			
			
			
			
			
			
			
			
			
			
			

        }
    }
}