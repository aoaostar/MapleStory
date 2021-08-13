status = -1;
//刚刚找你 你不在 所以我就给你写这套脚本出来了 我给你解说下 
var itemList = Array(//首先我们要接触到的 就是 Array函数系列的运用
			/*Array(1032027,800, 1, 1), //黑水晶耳环
			如上面一排格式大概就是这样   Array(物品ID,概率,数量,播报),//物品名字
			大概格式就是这样然后我给你解说下 概率和播报这两个
			概率这个值是怎么算高或者怎么算低呢？？ 就是全部所有的物品的暴率都加起来然后用这个数除以总暴率就是 这件物品的暴率了
			播报很简单 0就是抽中奖品后不发公告内容 1就是中奖之后要发一条喇叭说 谁谁谁中奖了*/
			Array(2043003,1000, 1, 1), //单手剑攻击卷轴(必成)
			Array(2044003,1000, 1, 1), //双手剑攻击卷轴(必成)
			Array(2044303,1000, 1, 1), //枪攻击攻击卷轴(必成)
			Array(2044403,1000, 1, 1), //矛攻击攻击卷轴(必成)
			Array(2044503,1000, 1, 1), //弓攻击攻击卷轴(必成)
			Array(2044603,1000, 1, 1), //弩攻击必成卷
			Array(2043703,1000, 1, 1), //短杖攻击必成卷
			Array(2043803,1000, 1, 1), //长杖攻击诅轴(必成)
			Array(2043303,1000, 1, 1), //短剑攻击必成卷
			Array(2044703,1000, 1, 1), //拳套攻击诅轴(必成)
			Array(2044908,1000, 1, 1), //短枪攻击卷轴(必成)
			Array(2044815,1000, 1, 1), //指节攻击必成卷
			Array(2040807,1000, 1, 1), //手套攻击卷轴(必成)

			Array(2040506,1000, 1, 1), //全身铠甲敏捷卷轴(必成)
			Array(2040303,1000, 1, 1), //耳环智力必成卷(必成)
			Array(2040710,1000, 1, 1) //鞋子跳跃卷轴(必成)

			//Array(2100079,100,1,1),//武陵道场召唤包_阿丽莎乐
			//Array(2100082,100,1,1),//武陵道场召唤包_远古精灵
		//	Array(2100084,100,1,1),//武陵道场召唤包_朱诺
		//	Array(2100085,100,1,1),//武陵道场召唤包_老海盗
		//	Array(2100090,100,1,1),//武陵道场召唤包_蝙蝠魔
		//	Array(2100097,100,1,1),//武陵道场召唤包_蝙蝠魔
		//	Array(2100098,100,1,1),//武陵道场召唤包_火焰龙
		//	Array(2100099,100,1,1),//武陵道场召唤包_天鹰
		//	Array(2100100,100,1,1),//武陵道场召唤包_大海兽
		//	Array(2100101,100,1,1)//武陵道场召唤包_帕普拉图斯			
			/*
			
        这里很重要  这里是Array的结束段落 记住最后一排的 Array函数后面是没有逗号的  这个逗号我截图在你QQ上去 
			*/

);

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("再见。加把油。");
            cm.dispose();
        }
        status--;
    }
		if (status == 0) {//这里就是脚本开始的地方
			var str1 = "\r\n";	
			
			
			if (cm.getPlayer().getNX() >= 5000) {//判断充值币是否为10个 如果有 就进行下面一段
				cm.sendYesNo("消耗#b5000#k点券随机抽取物品!\r\n :"+str1 );
			}
			else{
				cm.sendOk("你不够点卷啊。。");
				cm.dispose();
			}
		} else if (status == 1){	
        var chance = Math.floor(Math.random() * 300+ Math.random()*5);
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
            item = cm.gainGachaponItem(itemId, quantity, "木木抽奖中心", notice);
            if (item != -1) {
				cm.gainNX(-5000);
				
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
            } else {
                cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("今天的运气可真差，什么都没有拿到。\r\n");
            cm.gainNX(-5000);//没有中奖扣除的充值币函数
            //cm.gainItem(4001322, 1);
            cm.safeDispose();
        }
    }
}
