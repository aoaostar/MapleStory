function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 1; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#d欢迎来到本服装备制作中心.#l\r\n\r\n"//3
			text += "#L0##r制作#v1122000#(全属性+40)需要：#v1122000#X1 #v4310149#X1 #v4001094#X10.#l\r\n"//3
            text += "#L1##r制作#v1003267#(全属性+60)需要：#v4310028#X60.#l\r\n"//3
            text += "#L3##r制作#v1132246##z1132246# 需要：#v1132243#X1 #v1132244#X1 #v1132245#X1 #l\r\n"//3
			text += "#L4##r制作#v1113075##z1113075# 需要：#v1113072#X1 #v1113073#X1 #v1113074#X1 #l\r\n"//3
			text += "#L5##r制作#v1032223##z1032223# 需要：#v1032220#X1 #v1032221#X1 #v1032222#X1 #l\r\n"//3
			text += "#L6##r制作#v1112593#(全属性+35) 需要：#v1112444#X1 #v1112915#X1 #l\r\n"//3
			text += "#L7##r制作#v1113048#(全属性+70) 需要：#v1112593#X4 #v1113020#X1 5亿游戏币#l\r\n"//3
			text += "#L8##r制作#v1113012#(全属性+55) 需要：#v1112951#X1 #v1112952#X1 #l\r\n"//3
			text += "#L9##r制作#v1113089#(全属性+88) 需要：#v1112763#X1 #v1112775#X1 #v1112771#X1 #v1112767#X1#l\r\n"//3
			text += "#L10##r制作#v1032219##z1032219# 需要：#v1032206#X1 #v1032207#X1 #v1032208#X1 #v1032209#X1#l\r\n"//3
			text += "#L11##r制作#v1032205#(全属性+100) 需要：#v1032219#X1 #v4000464#X1 5亿游戏币 5W点券#l\r\n"//3
			text += "#L12##r制作#v1012174##z1012174# 需要：#v1012170#X1 #v1012171#X1 #v1012172#X1 #v1012172#X1(90级+100级+130级+150级=终极)#l\r\n"//3
			text += "#L13##r制作#v1132215##z1132215# 需要：#v1132211#X1 #v1132212#X1 #v1132213#X1 #v1132214#X1#l\r\n"//3
            cm.sendSimple(text);
			} else if (selection == 0) {
			if (cm.itemQuantity(1122000) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1122000#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(4310149) < 1 ) { 
				cm.sendOk("#b装备合成需要#v4310149#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(400109) < 10 ) { 
				cm.sendOk("#b装备合成需要#v400109#X10，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1122000, -1);
				cm.gainItem(4310149, -1);
				cm.gainItem(4001094, -10);
	

				
				cm.gainItem(1122000, 40,40,40,40,0,0,40,40,200,200,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成黑龙项环升级版成功！");
            cm.dispose();
			}
			
			
			
        } else if (selection == 1) {
	
		     if (cm.itemQuantity(4310028) < 60 ) { 
				cm.sendOk("#b装备合成需要#v4310028#X60，您的物品不足#k");
				cm.dispose();
					
			}else{
				
				cm.gainItem(4310028, -60);				
				cm.gainItem(1003267, 60,60,60,60,0,0,60,60,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成帅气海盗帽成功！");
            cm.dispose();
			}
			
			 } else if (selection == 2) {
			if (cm.itemQuantity(1022224) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1022224#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1022225) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1022225#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1022226) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1022226#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1022197) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1022197#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(4000463) < 200 ) { 
				cm.sendOk("#b装备合成需要#v4000463#200个，您的物品不足#k");
				cm.dispose();
			
			}else{
				
				cm.gainItem(1022224, -1);
				cm.gainItem(1022225, -1);
				cm.gainItem(1022226, -1);
				cm.gainItem(1022197, -1);
				cm.gainItem(4000463, -200);
				
				cm.gainItem(1022197, 50,50,50,50,0,0,50,50,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成进化太阳镜成功！");
            cm.dispose();
			}
			
			 } else if (selection == 3) {
			if (cm.itemQuantity(1132243) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132243#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1132244) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132244#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1132245) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132245#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1132243, -1);
				cm.gainItem(1132244, -1);
				cm.gainItem(1132245, -1);				
				cm.gainItem(1132246, 1);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成最高级贝勒德腰带成功！");
            cm.dispose();
			}
			
			} else if (selection == 4) {
			if (cm.itemQuantity(1113072) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1113072#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1113073) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1113073#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1113074) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1113074#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1113072, -1);
				cm.gainItem(1113073, -1);
				cm.gainItem(1113074, -1);

				
				cm.gainItem(1113075, 1);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成最高级贝勒德戒指成功！");
            cm.dispose();
			}
			
			} else if (selection == 5) {
			if (cm.itemQuantity(1122264) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1122264#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1122265) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1122265#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1122266) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1122266#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1122264, -1);
				cm.gainItem(1122265, -1);
				cm.gainItem(1122266, -1);

				
				cm.gainItem(1122267, 1);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成最高级贝勒德吊坠成功！");
            cm.dispose();
			}
			
			} else if (selection == 6) {
			if (cm.itemQuantity(1112444) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112444#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1112915) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112915#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1112444, -1);
				cm.gainItem(1112915, -1);
	

				
				cm.gainItem(1112593, 35,35,35,35,0,0,35,35,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成全能戒指成功！");
            cm.dispose();
			}
			
			} else if (selection == 7) {
				if(cm.getMeso() < 500000000){
			    cm.sendOk("#b装备合成需要5亿游戏币，您的金币不足#k");
				cm.dispose();				
			} else if (cm.itemQuantity(1112593) < 4 ) { 
				cm.sendOk("#b装备合成需要#v1112593#X4，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1113020) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1113020#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1112593, -4);
				cm.gainItem(1113020, -1);
	            cm.gainMeso(-500000000);

				
				cm.gainItem(1113048, 70,70,70,70,0,0,70,70,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成斗士戒指成功！");
            cm.dispose();
			}
			
			} else if (selection == 8) {
			if (cm.itemQuantity(1112951) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112951#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1112952) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112952#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1112951, -1);
				cm.gainItem(1112952, -1);
	

				
				cm.gainItem(1113012, 55,55,55,55,100,100,55,55,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成恶魔复仇者戒指成功！");
            cm.dispose();
			}
			
			} else if (selection == 9) {
			if (cm.itemQuantity(1112763) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112763#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1112775) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112775#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1112771) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112771#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1112767) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1112767#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1112763, -1);
				cm.gainItem(1112775, -1);
				cm.gainItem(1112771, -1);
				cm.gainItem(1112767, -1);

				
				cm.gainItem(1113089, 88,88,88,88,150,150,88,88,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成伊帕娅之戒指成功！");
            cm.dispose();
			}
			
			} else if (selection == 10) {
			if (cm.itemQuantity(1032206) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1032206#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1032207) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1032207#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1032208) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1032208#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1032209) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1032209#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1032206, -1);
				cm.gainItem(1032207, -1);
				cm.gainItem(1032208, -1);
				cm.gainItem(1032209, -1);

				
				cm.gainItem(1032219, 50,50,50,50,50,50,50,50,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成遗忘之神话耳环（全属性50 HPMP50）成功！");
            cm.dispose();
			}
			
			} else if (selection == 11) {
				if(cm.getMeso() < 500000000){
			    cm.sendOk("#b装备合成需要5亿游戏币，您的金币不足#k");
				cm.dispose();
           } else  if (cm.getPlayer().getCSPoints(1) < 50000) {
                    cm.sendOk("你的点卷不足!");
                    cm.dispose();
				
			} else if (cm.itemQuantity(1032219) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1032219#X1，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(4000464) < 1 ) { 
				cm.sendOk("#b装备合成需要#v4000464#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1032219, -1);
				cm.gainItem(4000464, -1);
	            cm.gainMeso(-500000000);
                cm.gainNX(-50000);
				
				cm.gainItem(1032205, 100,100,100,100,100,100,100,100,0,0,0,0,0,0);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成神话耳环（全属性100 HPMP100）成功！");
            cm.dispose();
			}
			
			} else if (selection == 12) {
			if (cm.itemQuantity(1012170) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1012170#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1012171) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1012171#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1012172) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1012172#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1012173) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1012173#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1012170, -1);
				cm.gainItem(1012171, -1);
				cm.gainItem(1012172, -1);
				cm.gainItem(1012173, -1);

				
				cm.gainItem(1012174, 1);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成恐怖鬼娃的伤口成功！");
            cm.dispose();
			}
			
			} else if (selection == 13) {
			if (cm.itemQuantity(1132211) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132211#，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1132212) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132212#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1132213) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132213#，您的物品不足#k");
				cm.dispose();
				} else if (cm.itemQuantity(1132214) < 1 ) { 
				cm.sendOk("#b装备合成需要#v1132214#，您的物品不足#k");
				cm.dispose();

			
			}else{
				
				cm.gainItem(1132211, -1);
				cm.gainItem(1132212, -1);
				cm.gainItem(1132213, -1);
				cm.gainItem(1132214, -1);

				
				cm.gainItem(1132215, 1);
            cm.sendOk("制作成功！");
			cm.喇叭(3,"【合成中心】["+cm.getName()+"]合成冒险岛强韧意志黑色腰带成功！");
            cm.dispose();
			}
			
		}
    }
}


