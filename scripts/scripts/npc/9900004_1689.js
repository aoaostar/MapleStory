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
            cm.sendSimple ("#e您好，尊敬的 #b#h ##k, 我是本服的#r吃货系统#k负责人\r\n#k#n \r\n#L12##e合成#v1092048#(60G)#r需要#v2002034#X500个#k#l\r\n#L13##e合成#v1032205#(全属性100)#r需要#v2002034#X300个#k#l\r\n#L14##e合成#v1402310#(300G)#r需要#v2002034#X300个#k#l\r\n#L7##e合成200级战士神装四件#r需要#v2020007#X20条+#v2002023#X80+暴君战士套四件+#v4310149#X20#k#l\r\n#L8##e合成200级法师神装四件#r需要#v2020007#X20条+#v2002023#X80+暴君法师套四件+#v4310149#X20#k#l\r\n#L9##e合成200级弓手神装四件#r需要#v2020007#X20条+#v2002023#X80+暴君弓手套四件+#v4310149#X20#k#l\r\n#L10##e合成200级飞侠神装四件#r需要#v2020007#X20条+#v2002023#X80+暴君飞侠套四件+#v4310149#X20#k#l\r\n#L11##e合成200级海盗神装四件#r需要#v2020007#X20条+#v2002023#X80+暴君海盗套四件+#v4310149#X20#k#l\r\n");
        } else if (status == 1) {
            switch(selection) {
				case 6: 

          if (cm.haveItem(4031640,100)){
                cm.sendOk("恭喜你，合成#v2020007#X1成功! .");
                cm.gainItem(4031640, -100);
                		
				cm.gainItem(2020007,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成鱿鱼干X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 12: 

          if (cm.haveItem(2002034,500)){
                cm.sendOk("恭喜你，合成#v1092048#X1成功! .");
                cm.gainItem(2002034, -500);               		
				cm.gainItem(1092048,5,5,5,5,0,0,60,60,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成★冰刀盾X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 13: 

          if (cm.haveItem(2002034,300)){
                cm.sendOk("恭喜你，合成#v1032205#X1成功! .");
                cm.gainItem(2002034, -300);               		
				cm.gainItem(1032205,100,100,100,100,100,100,50,50,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成神话耳环X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 14: 

          if (cm.haveItem(2002034,300)){
                cm.sendOk("恭喜你，合成#v1402310#X1成功! .");
                cm.gainItem(2002034, -300);               		
				cm.gainItem(1402310,10,10,0,0,0,0,300,0,0,0,0,0,0,0);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成★狼牙X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 15: 

          if (cm.haveItem(4000004,100) && cm.haveItem(4000007,100) && cm.haveItem(4000036,100)){
                cm.sendOk("恭喜你，合成#v2022002#X1成功! .");
                cm.gainItem(4000004, -100);
				cm.gainItem(4000007, -100);
				cm.gainItem(4000036, -100);
                		
				cm.gainItem(2022002,1);
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成雪碧X1!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			 case 7: 

          if (cm.haveItem(2020007,20) && cm.haveItem(2002023,80) && cm.haveItem(4310149,20) && cm.haveItem(1102481,1) && cm.haveItem(1082543,1) && cm.haveItem(1072743,1) && cm.haveItem(1132174,1)){
                cm.sendOk("恭喜你，合成200级神装四件成功! .");
                cm.gainItem(2020007, -20);
                cm.gainItem(2002023, -80);
                cm.gainItem(4310149, -20);
				cm.gainItem(1102481, -1);
				cm.gainItem(1082543, -1);
				cm.gainItem(1072743, -1);
				cm.gainItem(1132174, -1);
				cm.gainItem(1052929,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1073057,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1082647,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1102828,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成200级神装四件!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 8: 

          if (cm.haveItem(2020007,20) && cm.haveItem(2002023,80) && cm.haveItem(4310149,20) && cm.haveItem(1102482,1) && cm.haveItem(1082544,1) && cm.haveItem(1072744,1) && cm.haveItem(1132175,1)){
                cm.sendOk("恭喜你，合成200级神装四件成功! .");
                cm.gainItem(2020007, -20);
                cm.gainItem(2002023, -80);
                cm.gainItem(4310149, -20);
				cm.gainItem(1102482, -1);
				cm.gainItem(1082544, -1);
				cm.gainItem(1072744, -1);
				cm.gainItem(1132175, -1);
				cm.gainItem(1052929,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1073057,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1082647,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1102828,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成200级神装四件!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 9: 

          if (cm.haveItem(2020007,20) && cm.haveItem(2002023,80) && cm.haveItem(4310149,20) && cm.haveItem(1102483,1) && cm.haveItem(1082545,1) && cm.haveItem(1072745,1) && cm.haveItem(1132176,1)){
                cm.sendOk("恭喜你，合成200级神装四件成功! .");
                cm.gainItem(2020007, -20);
                cm.gainItem(2002023, -80);
                cm.gainItem(4310149, -20);
				cm.gainItem(1102483, -1);
				cm.gainItem(1082545, -1);
				cm.gainItem(1072745, -1);
				cm.gainItem(1132176, -1);
				cm.gainItem(1052929,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1073057,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1082647,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1102828,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成200级神装四件!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 10: 

          if (cm.haveItem(2020007,20) && cm.haveItem(2002023,80) && cm.haveItem(4310149,20) && cm.haveItem(1102484,1) && cm.haveItem(1082546,1) && cm.haveItem(1072746,1) && cm.haveItem(1132177,1)){
                cm.sendOk("恭喜你，合成200级神装四件成功! .");
                cm.gainItem(2020007, -20);
                cm.gainItem(2002023, -80);
                cm.gainItem(4310149, -20);
				cm.gainItem(1102484, -1);
				cm.gainItem(1082546, -1);
				cm.gainItem(1072746, -1);
				cm.gainItem(1132177, -1);
				cm.gainItem(1052929,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1073057,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1082647,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1102828,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成200级神装四件!");
                cm.dispose();
            }else{
                cm.sendOk("你没有 足够的材料，我不能给你合成~.");
                cm.dispose();
            }
            break;
			
			case 11: 

          if (cm.haveItem(2020007,20) && cm.haveItem(2002023,80) && cm.haveItem(4310149,20) && cm.haveItem(1102485,1) && cm.haveItem(1082547,1) && cm.haveItem(1072747,1) && cm.haveItem(1132178,1)){
                cm.sendOk("恭喜你，合成200级神装四件成功! .");
                cm.gainItem(2020007, -20);
                cm.gainItem(2002023, -80);
                cm.gainItem(4310149, -20);
				cm.gainItem(1102485, -1);
				cm.gainItem(1082547, -1);
				cm.gainItem(1072747, -1);
				cm.gainItem(1132178, -1);
				cm.gainItem(1052929,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1073057,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1082647,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.gainItem(1102828,50,50,50,50,0,0,30,30,300,300,0,0,0,0); //
				cm.worldMessage(6,"【合成系统】["+cm.getName()+"]合成200级神装四件!");
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
          
            
            }
        }
    }
}