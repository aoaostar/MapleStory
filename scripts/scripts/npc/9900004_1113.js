importPackage(net.sf.cherry.client);
cal = java.util.Calendar.getInstance();
weekday = cal.get(java.util.Calendar.DAY_OF_WEEK);


var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var status = -1;
var ysss = 20;
var jingyan = Math.ceil(Math.random() * 1000000);
var questions = Array(
Array("荆轲刺谁？\r\n\r\nA.奶奶     B.秦王    C.鲁班七号   D.马可波罗", "B"),
Array("灰太狼的老婆是谁\r\n\r\nA.范冰冰     B.白百何    C红太郎   D.喜羊羊", "C"),
Array("暴走大事件每期结束时王尼玛会说什么？\r\n\r\nA.穿山甲说     B.荆轲刺秦王    C.嘛哩嘛哩哄   D.各位小伙伴们拜拜", "B"),
Array("以下哪种怪物会爆蛋白石母矿\r\n\r\nA.三尾狐     B.老骨龙    C.铁甲猪   D.僵尸蘑菇", "A"),
Array("僵尸蘑菇王多久刷新一次\r\n\r\nA.24小时     B.4小时    C.半小时   D.10小时", "C"),
Array("输入以下哪条指令可以查看当前地图怪物爆率\r\n\r\nA.@bw     B.@bl    C.@sb   D.@hh", "A"),
Array("最低多少级可以挑战妖僧\r\n\r\nA.100级     B.80级    C.130级   D.120级", "C"),
Array("月妙副本里，绿色种子丢在那个平台会长出花\r\n\r\nA.丢进粪坑     B.丢到右中    C.丢到左上   D.丢到右上", "D"),
Array("自称“鉴湖女侠”的近代民主革命志士是？\r\n\r\nA.邱少云     B.裘千仞    C.秋瑾   D.囚犯", "C"),
Array("清朝与慈禧两宫并尊，称母后皇太后的是谁？\r\n\r\nA.慈安     B.碰瓷    C.还珠格格   D.含香", "A"),
Array("“零点乐队”的主唱并出演《奋斗》中“猪头”一角的是谁？\r\n\r\nA.周少奇     B.周杰伦    C.周黑鸭   D.周晓鸥", "D"),
Array("源于周星驰的电影，我们通常说的“旺财”是指什么动物？\r\n\r\nA.燕子     B.狗    C.猫   D.猪", "B"),
Array("源于周星驰的电影，我们通常说的“小强”是指什么动物？\r\n\r\nA.屎壳郎     B.蟑螂    C.蛤蟆   D.螳螂", "B"),
Array("俗语中我们常说“不见什么不掉泪”？\r\n\r\nA.刀子     B.老虎    C.黄河   D.棺材", "D"),
Array("俗话中我们常说“不见什么动物不撒鹰”？\r\n\r\nA.蝴蝶     B.兔子    C.黄牛   D.山羊", "B"),
Array("我们形容一个人很懒常说“衣来伸手”的下一句是什么？\r\n\r\nA.钱来张口     B.屎来张口    C.狗来张口   D.饭来张口", "D"),
Array("表示事情要长远打算我们常用的成语“放长线”的下一句是？\r\n\r\nA.钓大鱼     B.钓小鱼    C.钓王八   D.钓螃蟹", "A"),
Array("端午节是农历几月几号？\r\n\r\nA.五月五     B.五月天    C.五月十五   D.五月二十", "A"),
Array("俗语说留得青山在的下一句是什么？\r\n\r\nA.不怕没钱花     B.不怕没老婆    C.不怕没柴烧   D.不怕没柴砍", "C"),
Array("武侠剧中人物在被死亡要挟时常说要钱没有的下一句是？\r\n\r\nA.要命一条     B.要狗一只    C.要   D.棺材", "A"),
Array("儿歌里唱道的什么动物上灯台，偷油吃，下不来？\r\n\r\nA.老鹰     B.老鼠    C.老虎   D.老牛", "B"),
Array("由郭富城演唱的2001年中国甲A联赛的主题典叫什么？\r\n\r\nA.燥起来     B.动起来    C.跳起来   D.跑起来", "B"),
Array("港剧《怒火街头》中律师罗力亚的扮演者是哪位著名男演员？\r\n\r\nA.郑嘉颖     B.郑成功    C.郑燮   D.郑爽", "A"),
Array("英国空想社会主义家托马斯。莫尔创作的一部描写世外桃源般的理想王国的小说是？\r\n\r\nA.乌漆嘛黑     B.乌托邦    C.乌鸦   D.乌龟", "B"),
Array("比喻自告奋勇，自己推荐自己担任某项工作，我们通常说是什么人自荐？\r\n\r\nA.毛毛     B.毛遂    C.周杰   D.黑皮", "B"),
Array("我们常在用自己粗浅的见解引出别人高明的意见时说抛什么引玉？\r\n\r\nA.砖     B.石    C.铁   D.钢", "A"),
Array("绿蜗牛会爆出以下哪种物品？\r\n\r\nA.绿帽子     B.绿蜗牛壳    C.当然是选择原谅她啊   D.绿茶婊", "B"),
Array("（选词填空）风萧萧兮易水寒，____一去不复返\r\n\r\nA.胖子     B.力士    C.壮士   D.莽夫", "C"),
Array("一般情况下什么怪可以秒杀满血的10级冒险家？\r\n\r\nA.蝙蝠魔     B.绿蜗牛    C.花蘑菇   D.猪猪", "A"),
Array("我怀疑你这里有问题指的是哪里？\r\n\r\nA.下体     B.良心    C.脑子   D.眼睛", "C"),
Array("以下哪种怪物会爆出齿轮镖？\r\n\r\nA.蝙蝠魔     B.机械蜘蛛    C.肯德熊   D.黑飞龙", "B"),
Array("以下哪位英雄是德玛西亚阵营的？\r\n\r\nA.德莱厄斯     B.提莫    C.奎因   D.蒙多", "C"),
Array("将以下哪种物品放在背包中可以提前五级佩戴装备？\r\n\r\nA.装备特许证     B.黑龙入场券    C.装备租赁卷   D.幸运御守", "A"),
Array("“听妈妈的话”是谁唱的？\r\n\r\nA.东尼大木     B.周展翅    C.李荣浩   D.周杰伦", "D")
 

);
var qid = -1;
var time1;
var time2;
var count = 0;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status >= 0) {
            cm.dispose();
            return;
        }

        status--;
    }var MC = cm.getServerName();
    if (status == 0) {
			
        var text = "#e#r\r\n正确 #r"+ cm.getChar().getBossLog("mrdtzc")+" #k次 \r\n错误#r "+ cm.getChar().getBossLog("mrdtcw")+"#k/ 5 次\r\n\r\n";
       // text += "#L0#"+正方箭头+" 活动规则#l\r\n\r\n";
        text += "#r#L1#"+正方箭头+" 开始答题#l\r\n";
		text += "#r#L0#"+正方箭头+" 返回界面#l\r\n";
        cm.sendSimple(text);
    } else if (status == 1) {
        if (selection == 0 && qid == -1) {
            cm.dispose();
            cm.openNpc(9900004,0);	
            status = -1;
        } else {
		if (cm.getBossLog("mrdtcw") > 4) {
             cm.sendOk("你答错太多次数了，是不是没有复习啊？");
			
             cm.dispose();
             return;	
		
			} cm.setBossLog("mrdtfq");
		
			
		 if ( cm.getBossLog("mrdtfq") >5 ) {
             cm.sendOk("你已经放弃过多的题目了。");
             cm.dispose();
             return;
			   }			
			
          if (cm.getBossLog("mrdt") == 1) {
             cm.sendOk("你已经完成了今日的答题啦~~");
             cm.dispose();
             return;

	
			  }
            time1 = cm.getCurrentTime();
            count++;
            qid = Math.floor(Math.random() * questions.length);
            cm.sendGetText("#r第 " + count + " 题#k\r\n#b#e" + questions[qid][0] + "#n#k\r\n请在" + ysss + "秒内作答");
        }
    } else if (status == 2) {
        // if (cm.getPlayer().getTodayOnlineTime() < 10) {
        //     cm.sendOk("在线时间小于10分钟，无法参加该活动。");
        //     cm.dispose();
        //     return;
        // }
        if (cm.getBossLog("mrdt") == 1) {
             cm.sendOk("本活动每账号只限参加一次，您已经参加过该活动了。");
             cm.dispose();
             return;
         }
		 if (cm.getBossLog("mrdtcw") > 4 ) {
             cm.sendOk("你答错太多次数了，是不是没有复习啊？");
             cm.dispose();
             return;
         }

        time2 = cm.getCurrentTime();
        var myasked = cm.getText();
        var answer = questions[qid][1];
        if ((time2 - time1) / 1000 > ysss) {
            cm.sendOk("很遗憾，超过答题时间了。请重新作答。");
            cm.dispose();
            return;
        }
        // if (cm.getPlayer().isGM()) {
        //     count = 10;
        //     myasked = answer;
        // }
        if (myasked == answer) {
            status = 0;
            if (count < 3) {
				cm.setBossLog("mrdtzc");
                cm.sendSimple("恭喜你回答正确，继续下一题吧！");
            } else {
               // cm.gainItem(4031227, 1);
				cm.gainItem(2022468, 10);
				cm.gainItem(4251202, 5);
				cm.gainItem(2340000, 10);
				cm.gainItem(4310149, 1);
				cm.gainItem(4000464, 5);
				cm.spawnMonster(9300340,1);
				//cm.gainExp(jingyan);
              //  cm.gainMeso(5000000);
				cm.setBossLog("mrdt");
				cm.setBossRankCount("zlks");
				cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");cm.setBossLog("zymxd");
				cm.setBossLog("mrdtzc");
                cm.dispose();
                cm.worldMessage(6,"玩家 "+cm.getName()+"  完成了每日答题，领取了丰厚的奖励。每日蛋糕怪物出现了！！！");
            }
        } else {
            status = -1;
            count = 0;
			cm.setBossLog("mrdtcw");
			// cm.sendSimple("真遗憾！你答错了~~~"); 
            cm.sendSimple("真遗憾 ！你答错了 ！\r\n\r\n题目；#b" + questions[qid][0] + "\r\n\r\n#k答案；#r" + questions[qid][1] + "#k"); 
            qid = -1;
        }
    }
}
