importPackage(net.sf.cherry.tools);
importPackage(net.sf.cherry.client);

var status = 0;

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

	    var textz = "#d我这里有一些成长型不速之客武器。.\r\n";

  textz += "#L17#购买单手剑#l\r\n";
  textz += "#L18#购买单手斧#l\r\n";
  textz += "#L19#购买单手钝器#l\r\n";
  textz += "#L20#购买单手短刀#l\r\n";
  textz += "#L21#购买短杖#l\r\n";
  textz += "#L22#购买长杖#l\r\n";
  textz += "#L23#购买双手剑#l\r\n";
  textz += "#L24#购买双手斧#l\r\n";
  textz += "#L25#购买双手钝器#l\r\n";
  textz += "#L26#购买枪#l\r\n";
  textz += "#L27#购买矛#l\r\n";
  textz += "#L28#购买弓#l\r\n";
  textz += "#L29#购买弩#l\r\n";
  textz += "#L30#购买拳套#l\r\n";
  textz += "#L31#购买指节#l\r\n";
  textz += "#L32#购买火枪#l\r\n";





		cm.sendSimple (textz);  

	}else if (status == 1) {

	       if (selection == 1){
                   if (cm.getMeso()<=300000) {
					   cm.dispose();
 			cm.sendOk("想要不速之客最少你也要有三十万金币吧？");			
		} else{
			cm.dispose();
		 cm.openNpc(1022003,601);//单手剑
			}

       } else if (selection == 2){
                 if (cm.getMeso()<=300000) {
					 cm.dispose();
 			cm.sendOk("想要不速之客最少你也要有三十万金币吧？");
		} else{
			cm.dispose();
		cm.openNpc(1022003,602);//单手斧
      			
			}
       } else if (selection == 3){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
cm.openNpc(1022003,603);//单手钝器
}

       } else if (selection == 4){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,604);//短刀

}

       } else if (selection == 5){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,605);//短杖

}

       } else if (selection == 6){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,606);//长杖

}

       } else if (selection == 7){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,607);//双手剑

}

       } else if (selection == 8){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,608);//双手斧

}

       } else if (selection == 9){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,609);//双手钝器

}

       } else if (selection == 10){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,610);//枪

}

       } else if (selection == 11){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,611);//矛

}

       } else if (selection == 12){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,612);//弓

}

        } else if (selection == 13){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,613);//弩

}


       } else if (selection == 14){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,614);//拳套

}


       } else if (selection == 15){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,615);//指节

}

       } else if (selection == 16){
       if (cm.getMeso()<=300000) {
		   cm.dispose();
    cm.sendOk("想要不速之客最少你也要有三十万金币吧？");

 
  } else{
	  cm.dispose();
		cm.openNpc(1022003,616);//短刀

}


       } else if (selection == 17){//单手剑
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1302143,1);
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
		    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 18){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1312058,1);//单手斧
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 19){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1322086,1);//单手钝器
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 20){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1332116,1);//单手短刀
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 21){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1372074,1);//短杖
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 22){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1382095,1);//长杖
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 23){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1402086,1);//双手剑
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 24){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1412058,1);//双手斧
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 25){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1422059,1);//双手钝器
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 26){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1432077,1);//枪
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 27){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1442107,1);//矛
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 28){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1452102,1);//弓
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 29){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1462087,1);//弩
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 30){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1472113,1);//拳套
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 31){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1482075,1);//指节
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}
       } else if (selection == 32){
       if (cm.haveItem(4000000, 100) && cm.haveItem(4000016, 100) && cm.haveItem(4000019, 100) && cm.getMeso() > 300000&&!cm.getInventory(1).isFull()) {
		   	  cm.gainItem(1492075,1);//火枪
			  cm.gainItem(4000000,-100);
			  cm.gainItem(4000016,-100);
			  cm.gainItem(4000019,-100);
	          cm.gainMeso(-300000);
			   
    cm.completeQuest(88880);
         cm.dispose();
  } else{
cm.sendOk("就算是初级的不速之客也不是免费的，你需要带给我100个#v4000000# 100个#v4000016# 100个#v4000019#材料我才可以帮你打造，哦对了，再给我30W金币，这个不是材料哦，而是我帮你打造不速之客的辛苦费。");
   cm.dispose();
}






}
}
}
}
