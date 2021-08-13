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
            cm.sendSimple ("#e您好，尊敬的 #b#h ##k, 我是本服的#r破攻系统#k负责人\r\n每增加一级破功等级，破功上限增加10000\r\n\r\n#b您目前破攻等级为： #e#r" + cm.getvip() + "#k#n \r\n#L5##e合成放大镜#v2460005##r需要#v2010006#X1#v4251202#X1#v4000464#X1+金币1E#k#n#l#k\r\n#L3##e合成破攻石#v2614015##r需要#v2010006#X1#v4251202#X1#v4000464#X1+金币1E#k#n#l#k\r\n#L4##e【普通破攻】使用破攻石#v2614015##r提升1万点破攻上限(10次/天)#k#n#l#k\r\n#L6##e【钻石VIP】使用破攻石#v2614015##r提升2万点破攻上限(不限次数)#k#n#l#k\r\n\r\n");
        } else if (status == 1) {
            switch(selection) {
        case 2:
		
		     if(cm.getMeso() < 30000000){
                cm.sendOk("抱歉你的金币不足3000W不可以合成! .");
				cm.dispose();
          } else   if(cm.haveItem(4251201,5)){
                cm.sendOk("恭喜你，合成高等五彩#v4251202#成功! .");
                cm.gainItem(4251201,-5);
				cm.gainMeso(-30000000);
				cm.gainItem(4251202,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成高等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
        case 1:
             		
             if(cm.getMeso() < 30000000){
                cm.sendOk("抱歉你的金币不足3000W不可以合成! .");
				cm.dispose();
          } else   if(cm.haveItem(4251200,5)){
                cm.sendOk("恭喜你，合成中等五彩#v4251202#成功! .");
                cm.gainItem(4251200,-5);
				cm.gainMeso(-30000000);
				cm.gainItem(4251201,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成中等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
         case 0: 
             if(cm.getMeso() < 30000000){
                cm.sendOk("抱歉你的金币不足3000W不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4001038,1) && cm.haveItem(4001042,1) && cm.haveItem(4001041,1) && cm.haveItem(4001040,1) && cm.haveItem(4001043,1) && cm.haveItem(4001039,1)){
                cm.sendOk("恭喜你，合成高等五彩#v4251202#成功! .");
                cm.gainItem(4001038, -1);
                cm.gainItem(4001042, -1);
                cm.gainItem(4001041, -1);
				cm.gainItem(4001040, -1);
				cm.gainItem(4001043, -1);
				cm.gainItem(4001039, -1);
				cm.gainMeso(-30000000);
				cm.gainItem(4251202,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成高等五彩水晶X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
            case 3: 
              if(cm.getMeso() < 100000000){
                cm.sendOk("抱歉你的金币不足1E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4251202,1) && cm.haveItem(2010006,1) && cm.haveItem(4000464,1)){
                cm.sendOk("恭喜你，合成破攻石#v2614015#成功! .");
                cm.gainItem(4251202, -1);
                cm.gainItem(2010006, -1);
                cm.gainItem(4000464, -1);
				//cm.gainItem(4001040, -1);
				//cm.gainItem(4001043, -1);
				//cm.gainItem(4001039, -1);
				cm.gainMeso(-100000000);
				cm.gainItem(2614015,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成破攻石X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			case 7: 
              if(cm.getMeso() < 80000000){
                cm.sendOk("抱歉你的金币不足8000W不可以合成! .");
				cm.dispose();
          } else if (cm.haveItem(4251202,2) && cm.haveItem(2010006,4) && cm.haveItem(4000464,1) && cm.haveItem(3700148,1)){
                cm.sendOk("恭喜你，合成破攻石#v2614015#成功! .");
                cm.gainItem(4251202, -2);
                cm.gainItem(2010006, -4);
                cm.gainItem(4000464, -1);
				//cm.gainItem(4001040, -1);
				//cm.gainItem(4001043, -1);
				//cm.gainItem(4001039, -1);
				cm.gainMeso(-80000000);
				cm.gainItem(2614015,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成破攻石X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，或者您不是钻石会员，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
            case 4: 
			  if (cm.getBossLog('PlayQuest80') >= 10) {
			cm.sendOk("你今天破攻次数超过10次!");
			cm.dispose();
           }	else  if (cm.haveItem(2614015,1)){
                //cm.gainDY(100);
				cm.gainItem(2614015,-1);
				cm.gainvip(+1);
				cm.setBossLog('PlayQuest80');
				cm.sendOk("恭喜你，提升普通破攻成功! .");
			        cm.worldMessage(6,"【破攻系统】["+cm.getName()+"]提升普通破攻1万点上限成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的破攻石#v2614015#，我不能给你破攻~.");
                cm.dispose();
            }
            break;
			
			case 6: 
		if (cm.haveItem(2614015,1) && cm.haveItem(3700148,1)){
                //cm.gainDY(100);
				cm.gainItem(2614015,-1);
				cm.gainvip(+2);
				//cm.setBossLog('PlayQuest80');
				cm.sendOk("恭喜你，提升VIP破攻成功! .");
			        cm.worldMessage(6,"【破攻系统】["+cm.getName()+"]提升VIP破攻2万点上限成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的破攻石#v2614015#，我不能给你破攻~.");
                cm.dispose();
            }
            break;
			
            case 5: 
             if(cm.getMeso() < 100000000){
                cm.sendOk("抱歉你的金币不足1E不可以合成! .");
				cm.dispose();
          } else   if (cm.haveItem(4251202,1) && cm.haveItem(2010006,1) && cm.haveItem(4000464,1)){
                cm.sendOk("恭喜你，合成放大镜#v2460005#成功! .");
                cm.gainItem(4251202, -1);
                cm.gainItem(2010006, -1);
                cm.gainItem(4000464, -1);
				//cm.gainItem(4001040, -1);
				//cm.gainItem(4001043, -1);
				//cm.gainItem(4001039, -1);
				cm.gainMeso(-100000000);
				cm.gainItem(2460005,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成放大镜X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
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