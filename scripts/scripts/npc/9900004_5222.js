/*
*   交换商店，增强版  NPC
*   By: ZreHy_MS               如果需要改动，请自行修改，本人开服用的~
*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple ("#e您好，尊敬的 #b#h ##k, 我是本服的#r勋章升级#k负责人\r\n#k#n \r\n#L5##e合成魂士勋章#v1142109##r需要等级200级+#v4031648#X10+金币2E+5W点卷#k#n#l#k\r\n#L6##e合成魂师勋章#v1142110##r需要等级205级+#v4031648#X30+金币3E+7W点卷#k#n#l#k\r\n#L7##e合成大魂师勋章#v1142509##r需要等级210级+#v4031648#X45+金币4E+8W点卷#k#n#l#k\r\n#L8##e合成魂尊勋章#v1142408##r需要等级215级+#v4031648#X60+金币5E+9W点卷#k#n#l#k\r\n#L9##e合成魂宗勋章#v1142100##r需要等级220级+#v4031648#X75+金币6E+10W点卷#k#n#l#k\r\n#L10##e合成魂王勋章#v1142304##r需要等级225级+#v4031648#X90+金币7E+11W点卷#k#n#l#k\r\n#L11##e合成魂帝勋章#v1142610##r需要等级230级+#v4031648#X110+金币8E+12W点卷#k#n#l#k\r\n#L12##e合成魂圣勋章#v1142587##r需要等级235级+#v4031648#X130+金币9E+13W点卷#k#n#l#k\r\n#L13##e合成魂斗罗勋章#v1142683##r需要等级240级+#v4031648#X150+金币10E+14W点卷#k#n#l#k\r\n#L14##e合成封号斗罗勋章#v1142788##r需要等级245级+#v4031648#X170+金币11E+15W点卷#k#n#l#k\r\n#L15##e合成教皇勋章#v1142789##r需要等级250级+#v4031648#X200+金币15E+20W点卷#k#n#l#k\r\n\r\n\r\n");
        } else if (status == 1) {
            switch(selection) {
        
            case 5: 
			if (cm.getLevel() < 200 ) {  
                cm.sendOk("您的等级不足200级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 50000) {
			    cm.sendOk("抱歉你的点卷不足5W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 200000000){
                cm.sendOk("抱歉你的金币不足2E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,10)){
                cm.sendOk("恭喜你，合成魂士勋章#v1142109#成功! .");
                cm.gainItem(4031648, -10);
				cm.gainMeso(-200000000);
				cm.gainNX(-50000);
				cm.gainItem(1142109, 10,10,10,10,0,0,10,10,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂士勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            case 6: 
			if (cm.getLevel() < 205 ) {  
                cm.sendOk("您的等级不足205级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 70000) {
			    cm.sendOk("抱歉你的点卷不足7W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 300000000){
                cm.sendOk("抱歉你的金币不足3E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,30) && cm.haveItem(1142109,1)){
                cm.sendOk("恭喜你，合成魂师勋章#v1142110#成功! .");
				cm.gainItem(1142109, -1);
                cm.gainItem(4031648, -30);
				cm.gainMeso(-300000000);
				cm.gainNX(-70000);
				cm.gainItem(1142110, 25,25,25,25,0,0,25,25,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂师勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 7: 
			if (cm.getLevel() < 210 ) {  
                cm.sendOk("您的等级不足210级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 80000) {
			    cm.sendOk("抱歉你的点卷不足8W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 400000000){
                cm.sendOk("抱歉你的金币不足4E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,45) && cm.haveItem(1142110,1)){
                cm.sendOk("恭喜你，合成大魂师勋章#v1142509#成功! .");
				cm.gainItem(1142110, -1);
                cm.gainItem(4031648, -45);
				cm.gainMeso(-400000000);
				cm.gainNX(-80000);
				cm.gainItem(1142509, 40,40,40,40,0,0,40,40,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成大魂师勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 8: 
			if (cm.getLevel() < 215 ) {  
                cm.sendOk("您的等级不足215级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 90000) {
			    cm.sendOk("抱歉你的点卷不足9W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 500000000){
                cm.sendOk("抱歉你的金币不足5E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,60) && cm.haveItem(1142509,1)){
                cm.sendOk("恭喜你，合成魂尊勋章#v1142509#成功! .");
				cm.gainItem(1142509, -1);
                cm.gainItem(4031648, -60);
				cm.gainMeso(-500000000);
				cm.gainNX(-90000);
				cm.gainItem(1142408, 55,55,55,55,0,0,55,55,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂尊勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 9: 
			if (cm.getLevel() < 220 ) {  
                cm.sendOk("您的等级不足220级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 100000) {
			    cm.sendOk("抱歉你的点卷不足10W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 600000000){
                cm.sendOk("抱歉你的金币不足6E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,75) && cm.haveItem(1142408,1)){
                cm.sendOk("恭喜你，合成魂宗勋章#v1142100#成功! .");
				cm.gainItem(1142408, -1);
                cm.gainItem(4031648, -75);
				cm.gainMeso(-600000000);
				cm.gainNX(-100000);
				cm.gainItem(1142100, 70,70,70,70,0,0,70,70,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂宗勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 10: 
			if (cm.getLevel() < 225 ) {  
                cm.sendOk("您的等级不足225级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 110000) {
			    cm.sendOk("抱歉你的点卷不足11W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 700000000){
                cm.sendOk("抱歉你的金币不足7E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,90) && cm.haveItem(1142100,1)){
                cm.sendOk("恭喜你，合成魂王勋章#v1142304#成功! .");
				cm.gainItem(1142100, -1);
                cm.gainItem(4031648, -90);
				cm.gainMeso(-700000000);
				cm.gainNX(-110000);
				cm.gainItem(1142304, 85,85,85,85,0,0,85,85,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂王勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 11: 
			if (cm.getLevel() < 230 ) {  
                cm.sendOk("您的等级不足230级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 120000) {
			    cm.sendOk("抱歉你的点卷不足12W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 800000000){
                cm.sendOk("抱歉你的金币不足8E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,110) && cm.haveItem(1142304,1)){
                cm.sendOk("恭喜你，合成魂帝勋章#v1142610#成功! .");
				cm.gainItem(1142304, -1);
                cm.gainItem(4031648, -110);
				cm.gainMeso(-800000000);
				cm.gainNX(-120000);
				cm.gainItem(1142610, 100,100,100,100,0,0,100,100,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂帝勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 12: 
			if (cm.getLevel() < 235 ) {  
                cm.sendOk("您的等级不足235级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 130000) {
			    cm.sendOk("抱歉你的点卷不足13W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 900000000){
                cm.sendOk("抱歉你的金币不足9E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,130) && cm.haveItem(1142610,1)){
                cm.sendOk("恭喜你，合成魂圣勋章#v1142587#成功! .");
				cm.gainItem(1142610, -1);
                cm.gainItem(4031648, -130);
				cm.gainMeso(-900000000);
				cm.gainNX(-130000);
				cm.gainItem(1142587, 115,115,115,115,0,0,115,115,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂圣勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 13: 
			if (cm.getLevel() < 240 ) {  
                cm.sendOk("您的等级不足240级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 140000) {
			    cm.sendOk("抱歉你的点卷不足14W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 1000000000){
                cm.sendOk("抱歉你的金币不足10E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,150) && cm.haveItem(1142587,1)){
                cm.sendOk("恭喜你，合成魂斗罗勋章#v1142683#成功! .");
				cm.gainItem(1142587, -1);
                cm.gainItem(4031648, -150);
				cm.gainMeso(-1000000000);
				cm.gainNX(-140000);
				cm.gainItem(1142683, 130,130,130,130,0,0,130,130,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成魂斗罗勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 14: 
			if (cm.getLevel() < 245 ) {  
                cm.sendOk("您的等级不足245级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 150000) {
			    cm.sendOk("抱歉你的点卷不足15W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 1100000000){
                cm.sendOk("抱歉你的金币不足11E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,170) && cm.haveItem(1142683,1)){
                cm.sendOk("恭喜你，合成封号斗罗勋章#v1142788#成功! .");
				cm.gainItem(1142683, -1);
                cm.gainItem(4031648, -170);
				cm.gainMeso(-1100000000);
				cm.gainNX(-150000);
				cm.gainItem(1142788, 145,145,145,145,0,0,145,145,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成封号斗罗勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 15: 
			if (cm.getLevel() < 250 ) {  
                cm.sendOk("您的等级不足250级，无法合成勋章");
                cm.dispose();
		  } else   if (cm.getPlayer().getNX() < 200000) {
			    cm.sendOk("抱歉你的点卷不足20W不可以合成! .");
				cm.dispose();
          } else   if(cm.getMeso() < 1500000000){
                cm.sendOk("抱歉你的金币不足15E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4031648,200) && cm.haveItem(1142788,1)){
                cm.sendOk("恭喜你，合成教皇勋章#v1142789#成功! .");
				cm.gainItem(1142788, -1);
                cm.gainItem(4031648, -200);
				cm.gainMeso(-1500000000);
				cm.gainNX(-200000);
				cm.gainItem(1142789, 200,200,200,200,0,0,200,200,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成教皇勋章!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            }
        }
    }
}