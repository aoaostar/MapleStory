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

    cm.sendNext("期待开启");
    cm.dispose();


    // if (mode == -1) {
    //     cm.dispose();
    // } else {
    //     if (mode == 0) {
    //         cm.dispose();
    //         return;
    //     }
    //     if (mode == 1)
    //         status++;
    //     else
    //         status--;
    //     if (status == 0) {
    //         cm.sendSimple ("您好，尊敬的 #b#h ##k, 我是吃货冒险岛的#r置换系统#k负责人\r\n\r\n#L5##e点装置换（第一格属性转移第二格）=====#r10000点券一次#k#n#l#k");
    //     } else if (status == 1) {
    //         switch(selection) {
    //     case 2:
    //         if(cm.getPlayer().getMeso() >= 100000000){
    //             cm.sendOk("恭喜你，你成功兑换了1枚游戏币! ");
    // cm.gainItem(4310149,1);
    //             cm.gainMeso(-100000000);
    // cm.worldMessage(6,"【兑换系统】土豪玩家["+cm.getName()+"]成功兑换了一枚游戏币!");
    //             cm.dispose();
    //         }else{
    //             cm.sendOk("你没有足够的金币，我不能给你兑换~.");
    //             cm.dispose();
    //         }
    //         break;
    //     case 1: 
    //         if(cm.getMeso() >= 1000000){
    //             cm.sendOk("恭喜你，你获得了 10000 点券! .");
    //             cm.gainMeso(-1000000);
    //             cm.gainNX(10000);
    //             cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 1000000 金币，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //      case 0: 
    //         if(cm.haveItem(4001126,100)){
    //             //cm.gainDY(100);
    //             cm.gainvip(+1);
    // cm.gainItem(4001126,-100);
    // cm.sendOk("恭喜你，你提升了1级VIP等级! .");
    //        cm.worldMessage(6,"【破攻系统】["+cm.getName()+"]提升了1级VIP等级!");
    // cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //        case 3: 
    //         if(cm.haveItem(4001126,1000)){
    //             //cm.gainDY(100);
    //             cm.gainMeso(5000000);
    // cm.gainItem(4001126,-1000);
    // cm.sendOk("恭喜你，你获得了 5000000 金币! .");
    //    cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1000张枫叶兑换5000000金币成功！");
    // cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //         case 4: 
    //         if(cm.haveItem(4000313,100)){
    //             //cm.gainDY(100);
    //             cm.gainMeso(15000000);
    // cm.gainItem(4000313,-100);
    // cm.sendOk("恭喜你，你获得了 15000000 金币! .");
    //    cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]100张黄金枫叶兑换15000000金币成功！");
    // cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 足够的 黄金枫叶，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //         case 5: 
    //         if (cm.getPlayer().getNX() >= 10000) {
    // if( cm.transferCashEquipStat(1,2) ) {
    //  cm.gainNX(-10000);
    //  cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]点装置换成功！");
    //  cm.sendOk("点装置换成功！");
    // } else {
    //  cm.sendOk("点装置换失败！装备栏第一格和第二个必须都是点装才行！");
    //             }
    //         }

    //         break;
    //        case 6: 
    //         if(cm.getMeso() >= 10000000){
    // cm.gainMeso(-10000000);
    //             cm.gainD(100000);
    // cm.sendOk("恭喜你，你获得了100000抵用券! .");
    //    cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]1000w金币兑换10w抵用成功！");
    // cm.dispose();
    //         }else{
    //             cm.sendOk("你没有足够的金币，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //         case 7: 
    //         if(cm.getNX(2) >= 100000){
    //             cm.sendOk("恭喜你，你获得了10个劳动奖章! .");
    //             cm.gainD(-100000);
    //             cm.gainItem(4001266,10);
    // cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]10w抵用兑换10劳动奖章成功！");
    //             cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 100000 抵用，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //         case 8: 
    //         if(cm.getNX(2) >= 130000){
    //             cm.sendOk("恭喜你，你获得了10个淡蓝色矿石! .");
    //             cm.gainD(-130000);
    //             cm.gainItem(4001197,10);
    // cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]13w抵用兑换10淡蓝色矿石成功！");
    //             cm.dispose();
    //         }else{
    //             cm.sendOk("你没有 130000 抵用，我不能给你换购~.");
    //             cm.dispose();
    //         }
    //         break;
    //         }
    //     }
    // }
}