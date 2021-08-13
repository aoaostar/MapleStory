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
            cm.sendSimple ("您好，尊敬的 #b#h ##k, 我是本服的#r兑换系统#k负责人\r\n\r\n您目前有金币： #e#r" + cm.getMeso() + "\r\n#n#L2#100张#d#v4001126##d=====#r10张#d#v4000313##d#l\r\n");
        } else if (status == 1) {
            switch(selection) {
        case 0:
            if((cm.getPlayer().getCSPoints(1) >= 3000)){
                //cm.gainDY(100);             
				 cm.gainNX(-3000);
				cm.gainItem(4000463,10);
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]3000点兑换国庆成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
        case 1: 
            if(cm.haveItem(4001126,1000)){
                //cm.gainDY(100);
                cm.gainNX(3000);
				cm.gainItem(4001126,-1000);
				cm.sendOk("恭喜你，你获得了 3000 点券! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1000张枫叶兑换3000点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
         case 2: 
            if(cm.haveItem(4001126,100)){
                //cm.gainDY(100);
                //cm.gainNX(200);
				cm.gainItem(4001126,-100);
				cm.gainItem(4000313,10);
				cm.sendOk("恭喜你，你获得了 10张黄金枫叶! .");
			    cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]100张枫叶兑换10张黄金枫叶成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 3: 
            if((cm.getPlayer().getCSPoints(1) >= 300)){
                //cm.gainDY(100);             
				 cm.gainNX(-300);
				cm.gainItem(4000463,1);
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]300点兑换国庆成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 4: 
            if(cm.haveItem(4000463,10)){              
				cm.gainItem(4000463,-10);
				cm.gainNX(2600);
				cm.sendOk("恭喜你，你获得了 2600点卷! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]国庆兑换2600点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 5: 
            if(cm.haveItem(4032226,10)){
                //cm.gainDY(100);
                cm.gainMeso(2000000);
				cm.gainItem(4032226,-10);
				cm.sendOk("恭喜你，你获得了 2000000 金币! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]10只黄金猪猪兑换2000000金币成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 黄金猪猪，我不能给你换购~.");
                cm.dispose();
            }
            break;
           case 6: 
            if(cm.haveItem(4000463,1)){              
				cm.gainItem(4000463,-1);
				cm.gainNX(260);
				cm.sendOk("恭喜你，你获得了 260点卷! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]国庆兑换260点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
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
            case 9: 
                cm.openNpc(9270052, 0);
            }
        }
    }
}