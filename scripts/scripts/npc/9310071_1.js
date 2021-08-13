importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);

var status = 0;
var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";

	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
		if (mode == -1) {
		cm.dispose();
		} else {
		if (status >= 0 && mode == 0) {
		cm.dispose();
		return;
		}
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "\r\n你好，我是大姐大，请选择你需要租的装备 \r\n所有装备出租的只有一天权\r\n";
		
		textz += "#r#L6##v1122017##z1122017#   精灵吊坠 - 项 链 #l\r\n\r\n";

		cm.sendSimple (textz);  

	}else if (status == 1) {

	if (selection == 0) {
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r1亿#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else {
        var ii = MapleItemInformationProvider.getInstance();		                
		var type = ii.getInventoryType(1302208); //获得装备的类形/////////////////////////////////////////////////////////////////////
		var toDrop = ii.randomizeStats(ii.getEquipById(1302208)).copy(); // 生成一个Equip类
		var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
		toDrop.setExpiration(temptime); 
		cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
		cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
		cm.getChar().saveToDB(false, false);
		cm.gainItem(5220007,-1);
		cm.sendOk("兑换成功!")
		cm.dispose();
		}
	} else if (selection == 1) {
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else {
			var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1332025); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1332025)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}

	}else if (selection == 2){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else{
			var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1382012); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1382012)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
toDrop.setExpiration(temptime); 
cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}

	}else if (selection == 3){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else{
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1452022); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1452022)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
			cm.dispose();
			}

	}else if (selection == 4){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else{
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1462019); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1462019)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}

	}else if (selection == 5){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else{
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1472032); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1472032)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}

	}else if (selection == 6){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else{
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1122017); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1122017)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}
	}else if (selection == 7){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else {
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1492042); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1492042)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
			}
   }else if (selection == 8){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else {
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1432012); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1432012)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
	       }
	 }else if (selection == 9){
		if (cm.getMeso() < 0) {
 			cm.sendOk("请带来#r5000W#k金币#k");
      			cm.dispose();
		} else if (!cm.haveItem(5220007,1)) {
 			cm.sendOk("#v5220007##z5220007#您没有该物品 需要在商城购买后在来吧#k");
      			cm.dispose();
		} else if (cm.getInventory(1).isFull(3)){
			cm.sendOk("#b请保证装备栏位至少有3个空格,否则无法兑换.");
			cm.dispose();
		} else {
         var ii = MapleItemInformationProvider.getInstance();		                
            var type = ii.getInventoryType(1442024); //获得装备的类形/////////////////////////////////////////////////////////////////////
            var toDrop = ii.randomizeStats(ii.getEquipById(1442024)).copy(); // 生成一个Equip类
            var temptime = (System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000); //时间
			toDrop.setExpiration(temptime); 
			cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
			cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
			cm.getChar().saveToDB(false, false);
			cm.gainItem(5220007,-1);
			cm.sendOk("兑换成功!")
      			cm.dispose();
}       }
}
}
}
