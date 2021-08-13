var status = 0;
var itemList =   
Array(   
Array(1432182,1,1, 1), //红色之矛
Array(1402214,10,1, 1), //红色双手剑
Array(1452220,10,1, 1), //红色弓
Array(1462208,10,1, 1), //红色之弩
Array(1472230,10,1, 1), //红色拳甲
Array(1332242,10,1, 1), //红色切割者
Array(2070024,10,1, 1), //无限飞镖
//Array(2070026,1,1, 1), //黄金闪镖
//Array(2070016,1,1, 1), //水晶镖
Array(2070023,0,1, 1), //火焰飞镖
Array(2070019,0,1, 1), //高科技电光镖

//Array(2049100,10,10, 1), //混沌卷轴60%*5
//Array(2340000,10,5, 1), //祝福卷轴*5		
Array(1113039,10,1, 1), //妖精女皇戒指
Array(1402037,10,1, 1), //龙背刃
Array(1002850,1,1, 1), //圣诞鹿变身帽
Array(1072761,10,1, 1), //斯泰拉鞋
Array(1082518,10,1, 1), //情人节手镯(最高级)	
Array(1032216,10,1, 1), //真红耳环	 
Array(1032193,10,1, 1), //瑞贝卡的酸甜耳环
Array(1032217,10,1, 1), //逆转宝石耳环
Array(1012535,1,1, 1), //黑门面具

//Array(2040560,10,3, 1), //品克缤全身铠甲力量卷轴	
//Array(2040558,10,3, 1), //品克缤全身铠甲运气卷轴
//Array(2040556,10,3, 1), //品克缤全身铠甲智力卷轴
//Array(2040552,10,3, 1), //品克缤全身铠甲敏捷卷轴

Array(1132135,10,1, 1), //希望之树之传说腰带	  
//Array(1132192,10,1, 1), //S级力量宝石腰带	 
//Array(1132196,10,1, 1), //S级运气宝石腰带	 
//Array(1132200,10,1, 1), //S级智慧宝石腰带	  
//Array(1132204,10,1, 1), //S级敏捷宝石腰带
Array(1102777,10,1, 1), //宇宙冲撞披风
Array(1102471,10,1, 1), //赫里希安精锐战士披风
Array(1102472,10,1, 1), //赫里希安精锐法师披风	 
Array(1102473,10,1, 1), //赫里希安精锐弓箭手披风
Array(1102474,10,1, 1), //赫里希安精锐飞侠披风	
Array(1102475,10,1, 1), //赫里希安精锐海盗披风
Array(1072732,0,1, 1), //赫里希安精锐战士靴
Array(1072733,1,1, 1), //赫里希安精锐法师靴
Array(1072734,0,1, 1), //赫里希安精锐弓箭手靴
Array(1072735,0,1, 1), //赫里希安精锐飞侠靴
Array(1072736,1,1, 1), //赫里希安精锐海盗靴

Array(1092049,1,1, 1),//热情剑盾
Array(1112763,10,1, 1), //S级力量戒指
Array(1112775,10,1, 1), //S级敏捷戒指
Array(1112767,10,1, 1), //S级运气戒指
Array(1112771,10,1, 1), //S级智慧戒指
Array(1012187,10,1, 1), //破旧的面巾
Array(1032220,10,1, 1), //低级贝勒德耳环
Array(1113072,10,1, 1), //低级贝勒德戒指
Array(1132243,10,1, 1), //低级贝勒德腰带
Array(1012174,0,1, 1), //恐怖鬼娃的伤口

Array(1902002,0,1, 1), //赤羚龙
Array(4310059,0,1, 1), //必成币
Array(1052647,0,1, 1), //革命套服
Array(1003946,1,1, 1), //革命帽子
Array(1003982,10,1, 1), //宇宙海盗半包式头盔
Array(1002927,10,1, 1), //心疤狮王
Array(1002926,10,1, 1), //暴力熊帽
Array(1004427,10,1, 1) //宇宙冲撞头盔

);

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
           selStr = "#e#r该玩具箱可以获得以下物品，100%获得\r\n\#b是否消耗 #g1#r个#b#v4310027#\r\n来一发？#b[#r100%获得，绝无欺骗#b]#n#k\r\n";
		   for (i = 0; i < itemList.length; i++) {
                selStr += "#d#v" + itemList[i][0] + "#";
            }
			
			cm.sendOk(selStr);
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
		selStr = "\r\n";
		   for (i = 0; i < itemList.length; i++) {
                selStr += "#d#v" + itemList[i][0] + "#";
            }
        if (cm.haveItem(4310027, 1)) {
            cm.sendYesNo("我是曾经名震江湖的黑风母煞梅超风，我老公陈玄风因为卖假货被岛主变为一块石头，\r\n看来你已经得到超级抽奖币了，我可以给你真货~但是我不想跟我老公一样也变成石头，所以你赶紧抽，抽完了我要跑路了\r\n\#b是否消耗 #g1#r个#b#v4310027#\r\n来一发？#b[#r100%获得，所有物品获得概率均等#b]"+selStr);
        } else {
            cm.sendOk("只要你有#v4310027#，就可以随机获得以下曾经名震江湖的神器！\r\n\#b是否消耗 #g1#r个#b#v4310027#\r\n来一发？#b[#r100%获得，所有物品概率均等#b]"+selStr);
            cm.safeDispose();
        }
    } else if (status == 1) {
        var chance = Math.floor(Math.random() * 8);
        var finalitem = Array();
        for (var i = 0; i < itemList.length; i++) {
            if (itemList[i][1] >= chance) {
                finalitem.push(itemList[i]);
            }
        }
        if (finalitem.length != 0) {
            var item;
            var random = new java.util.Random();
            var finalchance = random.nextInt(finalitem.length);
            var itemId = finalitem[finalchance][0];
            var quantity = finalitem[finalchance][2];
            var notice = finalitem[finalchance][3];
            item = cm.gainGachaponItem(itemId, quantity, "梅超风超级抽奖", notice);//  int  int  sting  int
            if (item != -1) {
                cm.gainItem(4310027, -1);
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("你确实有#b#v4310027##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。\r\n\r\n#b#v4310012# 超级抽奖币私聊岛主购买，100人民币一枚，每月限购两枚，！！！");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("今天的运气可真差，什么都没有拿到。");
            cm.gainItem(4310027, -1);
            cm.safeDispose();
        }
    }
}