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
            cm.sendSimple ("您好，尊敬的 #b#h ##k, 我是吃货冒险岛#r游戏道具兑换#k负责人\r\n\r\n\r\n#L0#1个#d#v4030009##d#d兑换=====#v4080000#棋盘1个#k#n#l\r\n#L1#1个#d#v4030009##d#d兑换=====#v4080006#棋盘1个#k#n#l\r\n#L2#1个#d#v4030009##d#d兑换=====#v4080007#棋盘1个#k#n#l\r\n#L3#1个#d#v4030009##d#d兑换=====#v4080008#棋盘1个#k#n#l\r\n#L4#1个#d#v4030009##d#d兑换=====#v4080009#棋盘1个#k#n#l\r\n#L5#1个#d#v4030009##d#d兑换=====#v4080010#棋盘1个#k#n#l\r\n#L6#1个#d#v4030009##d#d兑换=====#v4080011#棋盘1个#k#n#l\r\n#L7#100个#d#v4030012##d#d兑换=====#v4080100#记忆大考验1个#k#n#l\r\n");
        } else if (status == 1) {
            switch(selection) {
        case 0:
		 if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080000,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
        case 1: 
            if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080006,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
         case 2: 
             if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080007,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 3: 
            if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080008,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 4: 
            if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080009,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 5: 
            if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080010,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
           case 6: 
            if(!cm.haveItem(4030009,1)){
			 cm.sendOk("你没有 1个#d#v4030009#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换棋盘成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030009,-1);
                cm.gainItem(4080011,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 7: 
            if(!cm.haveItem(4030012,100)){
			 cm.sendOk("你没有 100个#d#v4030012#! .");
			 cm.dispose();
			
		  }else if (cm.getLevel() >= 10 && cm.getLevel() <=255 ) {         
                cm.sendOk("恭喜你，兑换记忆大考验游戏成功! 去和小伙伴一起娱乐吧!.");
                cm.gainItem(4030012,-100);
                cm.gainItem(4080100,1);
                cm.dispose();
            }else{
                cm.sendOk("你等级不够10级，我不能给你换购~.");
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
			case 10: 
            if(cm.getMeso() >= 1000000){
                cm.sendOk("恭喜你，你获得了 #v2614000#突破一万之石! .");
                cm.gainMeso(-100000000);
                cm.gainItem(2614000,1);
                cm.dispose();
            }else{
                cm.sendOk("你没有 100W 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 11: 
            if(cm.getMeso() >= 10000000){
                cm.sendOk("恭喜你，你获得了 #v2614001#突破十万之石! .");
                cm.gainMeso(-100000000);
                cm.gainItem(2614001,1);
                cm.dispose();
            }else{
                cm.sendOk("你没有 1000W 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 9: 
                cm.openNpc(9270052, 0);
            break;
            }
        }
    }
}