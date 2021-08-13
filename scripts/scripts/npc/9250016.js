importPackage(net.sf.odinms.client);
var status = 0;
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#"; //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#"; //爱心
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 礼包物品 = "#v1302000#";
var ttt = "#fUI/UIWindow.img/Quest/icon9/0#";
var xxx = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

var VIP = 1003696;
var 洗血药水 = 4310001;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    cm.sendNext("期待开启");
    cm.dispose();

    //  if (mode == -1) {
    //      cm.dispose();
    //  } else {
    //      if (status >= 0 && mode == 0) {
    //          cm.dispose();
    //          return;
    //      }
    //      if (mode == 1)
    //          status++;
    //      else
    //          status--;


    //      if (status == 0) {
    //          //textz += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
    //          var textz = "#e\r\n您好，尊敬的 #b#h ##k,欢迎来到#r终极武器商店#k#n#l\r\n\r\n";
    //            textz += "#b#v2022468#每日任务、各个BOSS、万圣节活动地图都会产出。#k#n\r\n";

    //   textz += "#L100##v1302063#(攻300,单手剑战士用,) #r需要#v2022468#300个#k\r\n"; //
    //   textz += "#L101##v1442248#(攻300,长矛  战士用,) #r需要#v2022468#300个#k\r\n"; //
    //   textz += "#L1##v1432182#(攻300,四维100,) #r需要#v2022468#300个#k\r\n"; //枫叶钻天枪
    //   //textz += "#L2##v1442051#(攻126,四维100,) #r需要#v2022468#20个#k\r\n"; //枫叶战斧
    //   textz += "#L3##v1452220#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//枫叶HAPPY弓
    //   textz += "#L4##v1462208#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//枫叶击星弩
    //   textz += "#L5##v1402214#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//枫叶枭首剑
    //   //textz += "#L6##v1302064#(攻122,四维100,) #r需要#v2022468#20个#k\r\n";//枫叶突击剑
    //   textz += "#L7##v1382226#(魔300,四维100,) #r需要#v2022468#300个#k\r\n";//枫叶丹心杖
    //   //textz += "#L8##v1372034#(魔150,四维100,) #r需要#v2022468#20个#k\r\n";//枫叶仙姬杖
    // //  textz += "#L9##v1342087#(攻300,刀飞专用,) #r需要#v2022468#300个#k\r\n";//枫叶追魂刺
    //   textz += "#L10##v1332242#(攻300,刀飞专用) #r需要#v2022468#300个#k\r\n";//枫叶锁魄铗
    //   textz += "#L11##v1472230#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//1472055枫叶定天拳
    //   textz += "#L12##v1482183#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//1482022枫叶金爪
    //   textz += "#L13##v1492194#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//1492020枫叶枪
    //   textz += "#L14##v1422156#(攻300,四维100,) #r需要#v2022468#300个#k\r\n";//1422029枫叶轰天镗
    //   //textz += "#L15##v1422014#(攻126,四维100,) #r需要#v2022468#20个#k\r\n";//1422014枫叶锤
    //   //textz += "#L16##v1412027#(攻126,四维100,) #r需要#v2022468#20个#k\r\n";//1412027枫叶乾坤轮
    //   //textz += "#L17##v1322054#(攻122,四维100,) #r需要#v2022468#20个#k\r\n";//1322054枫叶地震锤
    //   //textz += "#L18##v1312032#(攻122,四维100,) #r需要#v2022468#20个#k\r\n";//1312032枫叶破击斧

    // //textz += "#L0#100消费积分领取#i5150040:#x1, #i"+ VIP +"#VIP等级 + 1 \r\n";//5220010
    //        //  textz += "#L1#200消费积分领取#i5570000:#x1  \r\n";
    //         // textz += "#L2#500消费积分领取#i1002939:#x1, #i"+ VIP +"#VIP等级 + 1  \r\n";
    //        //  textz += "#L3#1000消费积分领取#i"+ 洗血药水 +":#x300, #i"+ VIP +"#VIP等级 + 1  \r\n";
    //        //  textz += "#L4#1500消费积分领取#i5520000:#x3, #i"+ VIP +"#VIP等级 + 1  \r\n";
    //        //  textz += "#L5#3000消费积分领取#i5220010:#x40, #i"+ VIP +"#VIP等级 + 3  \r\n";
    //        //  textz += "#L7#5000消费积分领取#i2140008#x6, #i"+ VIP +"#VIP等级 + 4  \r\n";
    //          //textz += "#L8#8000消费积分领取#i:# \r\n";
    //          cm.sendSimple(textz);
    //      } else if (status == 1) {

    //          if (selection == 1) {//50国庆纪念币
    //              if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1432182,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);                  
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);

    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }       


    //          } else if (selection == 2) {
    //              if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1442051,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);

    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }

    //  } else if (selection == 100) {
    //              if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1302063,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }

    //  } else if (selection == 101) {
    //              if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1442248,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }

    // } else if (selection == 3) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1452220,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 4) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1462208,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 5) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1402214,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 6) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1302064,20,20,20,20,0,0,122,20,20,20,20,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 7) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1382226,100,100,100,100,0,0,300,300,0,0,0,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 8) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1372034,20,20,20,20,0,0,0,150,0,0,0,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 9) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1342087,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 10) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1332242,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 11) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1472230,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 12) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1482183,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 13) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1492194,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 14) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1422156,100,100,100,100,0,0,300,0,0,0,100,100,10,10);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 15) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1422014,20,20,20,20,0,0,126,20,20,20,20,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 16) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1412027,20,20,20,20,0,0,126,20,20,20,20,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 17) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1322054,20,20,20,20,0,0,122,20,20,20,20,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //  } else if (selection == 18) {
    //               if (cm.haveItem(2022468, 300)) {
    //      cm.gainItem(1312032,20,20,20,20,0,0,122,20,20,20,20,0,0,0);
    //      cm.gainItem(2022468,-300);
    //      cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]终极武器兑换成功！可喜可贺！蛋糕怪物出现了！", 5120009); 
    //      cm.spawnMonster(9300340,2);
    //                  cm.dispose();
    //              } else {
    //                  cm.sendOk("抱歉，您的#v2022468#数量不足以兑换~.");
    //                  cm.dispose();
    //              }
    //          } 
    //      }
    //  }
}