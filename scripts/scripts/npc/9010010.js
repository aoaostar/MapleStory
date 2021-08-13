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
            cm.sendSimple ("#e您好，尊敬的 #b#h ##k, 我是本服的#r兑换系统#k负责人\r\n#r正向混沌卷用途：50%成功，可加护在任何普通装备上，随机增加装备现有全属性1-5点#k\r\n#k#n \r\n#L6##e合成正向混沌卷#v2049124#X50#r需要#v4005000##v4005001##v4005002##v4005003##v4005004#各1个#k#n#l#k\r\n#L7##e合成正向混沌卷#v2049124#X50#r需要#v4011000##v4011001##v4011002##v4011003##v4011004##v4011005##v4011006#各1个#k#n#l#k\r\n#L8##e合成正向混沌卷#v2049124#X50#r需要#v4021000##v4021001##v4021002##v4021003##v4021004##v4021005##v4021006##v4021007##v4021008#各1个#k#n#l#k");
        } else if (status == 1) {
            switch(selection) {
				case 6: 
 if (cm.haveItem(4005000,1) && cm.haveItem(4005001,1) && cm.haveItem(4005002,1) && cm.haveItem(4005003,1) && cm.haveItem(4005004,1)){
                cm.sendOk("恭喜你，合成正向混沌卷#v2049124#X50成功! .");
                cm.gainItem(4004000, -1);
                cm.gainItem(4004001, -1);
                cm.gainItem(4004002, -1);
				cm.gainItem(4004003, -1);
                cm.gainItem(4004004, -1);				
				cm.gainItem(2049124,50);
				cm.setBossLog('正向兑换');
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]正向混沌卷X50!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
        case 7: 
  if (cm.haveItem(4011000,1) && cm.haveItem(4011001,1) && cm.haveItem(4011002,1) && cm.haveItem(4011003,3) && cm.haveItem(4011004,1) && cm.haveItem(4011005,1) && cm.haveItem(4011006,1)){
                cm.sendOk("恭喜你，合成正向混沌卷#v2049124#X50成功! .");
                cm.gainItem(4011000, -1);
                cm.gainItem(4011001, -1);
                cm.gainItem(4011002, -1);
				cm.gainItem(4011003, -1);
                cm.gainItem(4011004, -1);
                cm.gainItem(4011005, -1);
                cm.gainItem(4011006, -1);
				cm.gainItem(2049124,50);
				cm.setBossLog('正向兑换');
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]正向混沌卷X50!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 8: 
 if (cm.haveItem(4021000,1) && cm.haveItem(4021001,1) && cm.haveItem(4021002,1) && cm.haveItem(4021003,3) && cm.haveItem(4021004,1) && cm.haveItem(4021005,1) && cm.haveItem(4021006,1) && cm.haveItem(4021007,1) && cm.haveItem(4021008,1)){
                cm.sendOk("恭喜你，合成正向混沌卷#v2049124#X50成功! .");
                cm.gainItem(4021000, -1);
                cm.gainItem(4021001, -1);
                cm.gainItem(4021002, -1);
				cm.gainItem(4021003, -1);
                cm.gainItem(4021004, -1);
                cm.gainItem(4021005, -1);
                cm.gainItem(4021006, -1);
				cm.gainItem(4021007, -1);
				cm.gainItem(4021008, -1);
				cm.gainItem(2049124,50);
				cm.setBossLog('正向兑换');
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]正向混沌卷X50!");
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