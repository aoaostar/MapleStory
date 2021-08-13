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
            cm.sendSimple ("您好，尊敬的 #b#h ##k, 我是#r兑换系统#k负责人\r\n\r\n      您当前的蛋糕数量:#b#c2002033#个\r\n#n#b#L4#1个#v2002033##d===#r1W点卷#k#n#l\r\n#n#b#L5#10个#v2002033##d===#r10W点卷#k#n#l\r\n#n#b#L6#1个#v2002033##d===#r5个#v2010006##k#n#l\r\n#n#b#L7#10个#v2002033##d===#r50个#v2010006##k#n#l\r\n#n#b#L8#2个#v2002033##d===#r1个#v4000464##k#n#l\r\n#n#b#L9#20个#v2002033##d===#r10个#v4000464##k#n#l\r\n\r\n");
        } else if (status == 1) {
            switch(selection) {
        case 0:
            if(cm.getmoneyb() >= 5){
                 cm.setmoneyb(-5);				
				 cm.gainItem(2002033, 1);
                 cm.gainjf(+5);				 
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]兑换1个巧克力蛋糕成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的吃货余额 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
        case 1: 
            if(cm.getmoneyb() >= 100){
                cm.setmoneyb(-100);				
				 cm.gainItem(2002033, 20);
				  cm.gainjf(+100);
				cm.sendOk("恭喜你，兑换成功! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]兑换20个巧克力蛋糕成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的吃货余额，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 2: 
            if(cm.getmoneyb() >= 1){
                cm.setmoneyb(-1);
 cm.gainjf(+1);				
				 cm.gainNX(+1000);
				cm.sendOk("恭喜你，兑换成功! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1000点兑换成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的充值币，我不能给你换购~.");
                cm.dispose();
            }
            break;
         case 4: 
            if(cm.haveItem(2002033,1)){
                cm.gainNX(10000);
				cm.gainItem(2002033,-1);
				cm.sendOk("恭喜你，你获得了 1W 点券! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1个巧克力蛋糕兑换1W点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 5: 
            if(cm.haveItem(2002033,10)){
                cm.gainNX(100000);
				cm.gainItem(2002033,-10);
				cm.sendOk("恭喜你，你获得了 10W 点券! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]10个巧克力蛋糕兑换10W点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 6: 
            if(cm.haveItem(2002033,1)){
                cm.gainItem(2010006, 5);
				cm.gainItem(2002033,-1);
				cm.sendOk("恭喜你，你获得了 5个蜂蜜! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1个巧克力蛋糕兑换5个蜂蜜成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 7: 
            if(cm.haveItem(2002033,10)){
                cm.gainItem(2010006, 50);
				cm.gainItem(2002033,-10);
				cm.sendOk("恭喜你，你获得了 50个蜂蜜! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]10个巧克力蛋糕兑换50个蜂蜜成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 8: 
            if(cm.haveItem(2002033,2)){
                cm.gainItem(4000464, 1);
				cm.gainItem(2002033,-2);
				cm.sendOk("恭喜你，你获得了 1个中国心! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]2个巧克力蛋糕兑换1个中国心成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 9: 
            if(cm.haveItem(2002033,20)){
                cm.gainItem(4000464, 10);
				cm.gainItem(2002033,-20);
				cm.sendOk("恭喜你，你获得了 10个中国心! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]20个巧克力蛋糕兑换10个中国心成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 巧克力蛋糕，我不能给你换购~.");
                cm.dispose();
            }
            break;
		case 3: 
            if(cm.getmoneyb() >= 1000){
                cm.setmoneyb(-1000);
 cm.gainjf(+1000);				
				 cm.gainItem(2002033, 200);
				cm.sendOk("恭喜你，兑换成功! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]兑换200个巧克力蛋糕成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的吃货余额，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 8: 
            if((cm.getPlayer().getCSPoints(1) >= 300)){          
				 cm.gainNX(-300);
				cm.gainItem(4001126,1);
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]300点兑换国庆成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 9: 
            if(cm.haveItem(4001126,10)){              
				cm.gainItem(4001126,-10);
				cm.gainNX(2600);
				cm.sendOk("恭喜你，你获得了 2600点卷! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]国庆兑换2600点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 10: 
            if (cm.getPlayer().getNX() >= 20000) {
                cm.gainNX(-20000);
				cm.gainMeso(20000000);
				cm.sendOk("恭喜你，你获得了2000W游戏币! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]兑换游戏币成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 15: 
            if (cm.getPlayer().getNX() >= 100000) {
                cm.gainNX(-100000);
				cm.gainMeso(100000000);
				cm.sendOk("恭喜你，你获得了1E游戏币! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]兑换游戏币成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 11: 
            if(cm.haveItem(4032226,10)){
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
           case 12: 
            if(cm.haveItem(4001126,1)){              
				cm.gainItem(4001126,-1);
				cm.gainNX(260);
				cm.sendOk("恭喜你，你获得了 260点卷! .");
			        cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]国庆兑换260点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 13: 
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
            case 14: 
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
			case 16: 
                cm.openNpc(9000041, 0);
			
            case 9: 
                cm.openNpc(9270052, 0);
            }
        }
    }
}