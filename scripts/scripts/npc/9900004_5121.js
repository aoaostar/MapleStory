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
            cm.sendSimple ("#e您好，尊敬的 #b#h ##k, 我是本服的#r兑换系统#k负责人\r\n#k#n \r\n#L9##e合成X1#v2049124#正向卷(加全属性1-5点)#r需要#v2022279#10个#k#n#l#k\r\n#L99##e合成X10#v2049124#正向卷(加全属性1-5点)#r需要#v2022279#100个#k#n#l#k\r\n#L4##e合成传说枫叶币#v4310028##r需要#v4001083##v4001084##v4001085#各一个#k#n#l#k\r\n");
        } else if (status == 1) {
            switch(selection) {
				case 6: 

          if (cm.haveItem(4170000,1) && cm.haveItem(4170002,1) && cm.haveItem(4170004,1) && cm.haveItem(4170007,1)){
                cm.sendOk("恭喜你，合成幸运币#v2022279#X2成功! .");
                cm.gainItem(4170000, -1);
                cm.gainItem(4170002, -1);
                cm.gainItem(4170004, -1);
				cm.gainItem(4170007, -1);			
				cm.gainItem(2022279,2);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成幸运币X2!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 9:
		
		     if(cm.haveItem(2022279,10)){
                cm.sendOk("恭喜你，合成正向混沌卷#v2049124#成功! .");
                cm.gainItem(2022279,-10);
				cm.gainItem(2049124,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成正向混沌卷X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 99:
		
		     if(cm.haveItem(2022279,100)){
                cm.sendOk("恭喜你，合成正向混沌卷#v2049124#成功! .");
                cm.gainItem(2022279,-100);
				cm.gainItem(2049124,10);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成正向混沌卷X10!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
        case 2:
		
		     if(cm.haveItem(4251201,5)){
                cm.sendOk("恭喜你，合成高等五彩#v4251202#成功! .");
                cm.gainItem(4251201,-5);
				cm.gainItem(4251202,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成高等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
        case 1:
             		
           if(cm.haveItem(4251200,5)){
                cm.sendOk("恭喜你，合成中等五彩#v4251202#成功! .");
                cm.gainItem(4251200,-5);
				cm.gainItem(4251201,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成中等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
         case 0: 

          if (cm.haveItem(4001038,1) && cm.haveItem(4001042,1) && cm.haveItem(4001041,1) && cm.haveItem(4001040,1) && cm.haveItem(4001043,1) && cm.haveItem(4001039,1)){
                cm.sendOk("恭喜你，合成高等五彩#v4251202#成功! .");
                cm.gainItem(4001038, -1);
                cm.gainItem(4001042, -1);
                cm.gainItem(4001041, -1);
				cm.gainItem(4001040, -1);
				cm.gainItem(4001043, -1);
				cm.gainItem(4001039, -1);
				cm.gainItem(4251202,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成高等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            case 3: 
              if (cm.haveItem(4002000,100)){
                cm.sendOk("恭喜你，合成中国心#v4000464#成功! .");
                cm.gainItem(4002000, -100);
				cm.gainItem(4000464,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成中国心X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            case 4: 

          if (cm.haveItem(4001083,1) && cm.haveItem(4001084,1) && cm.haveItem(4001085,1)){
                cm.sendOk("恭喜你，合成传说枫叶币#v4310028#成功! .");
                cm.gainItem(4001083, -1);
                cm.gainItem(4001084, -1);
                cm.gainItem(4001085, -1);
				cm.gainItem(4310028,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成传说枫叶币X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            case 5: 
            if (cm.haveItem(4001226,1) && cm.haveItem(4001227,1) && cm.haveItem(4001228,1) && cm.haveItem(4001229,1) && cm.haveItem(4001230,1)){
                cm.sendOk("恭喜你，合成中国心#v4000464#成功! .");
                cm.gainItem(4001226, -1);
                cm.gainItem(4001227, -1);
                cm.gainItem(4001228, -1);
				cm.gainItem(4001229, -1);
				cm.gainItem(4001230, -1);
				cm.gainItem(4000464,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成中国心X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
           case 6: 
            if(cm.haveItem(4001126,100)){
                cm.gainItem(4000313,10);
				cm.gainItem(4001126,-100);
				cm.sendOk("恭喜你，你获得了 10张黄金枫叶! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]100张枫叶兑换10张黄金枫叶成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 7: 
            if(cm.getMeso() >= 10000000){
                cm.sendOk("恭喜你，你获得了 2000000 经验值! .");
                cm.gainMeso(-10000000);
                cm.gainExp(2000000);
                cm.dispose();
            }else{
                cm.sendOk("你没有 10000000 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 8: 
            if(cm.getMeso() >= 100000000){
                cm.sendOk("恭喜你，你获得了 50000000 经验值! .");
                cm.gainMeso(-100000000);
                cm.gainExp(50000000);
                cm.dispose();
            }else{
                cm.sendOk("你没有 1亿 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            }
        }
    }
}