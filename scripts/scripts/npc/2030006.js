/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
var status = 0;
var qChars = new Array ("问题1: 下面中冒险岛最初遇见的NPC是谁？#微微安#库克斯#希娜# 村长塔塔曼#3",//5
    "问题1:下面中在射手村看不到的NPC是谁?#担心鬼#特奥#桃子#亚华#2",//2
    "问题1: 下面中怪物技能和技能说明不恰当的是什么?#虚弱-移动速度减少#虚弱-移动速度增加#虚弱-命中减少#虚弱-经验减少#1",//4
    "问题1: 下面中不是阿尔法队员的是谁？ #查理中士#布拉宝伍长#霍古斯曹长#比特中士#4",//3
    "问题1: 下面中在魔法密林见不到的NPC是谁?#飞天猪#露尔#浪漫#朴先生#2",//1
	"问题1: 下面中怪物和怪物所掉的战利品连接正确的是哪一个?#蓝蜗牛-蘑菇壳#蝙蝠-蝙蝠翅膀#白雪人-企鹅的嘴巴#火狗-火焰之羽#2");//6
var qItems = new Array( "问题2: 下面中下面中在勇士部落没有的NPC的谁?#大姐大#易德#酷男孩#飞天猪#2",//6
    "问题2: 下面中在天空之城看不见的NPC是谁?#情人节红线女#马丁#星缘#索非亚#4",//4
    "问题2: 其中为上升等级1到2的时候所需要的经验值是多少?#10#12#15#20#3",//5
    "问题2: 下面中不是为觉醒麦吉的旧战剑修要的道具是什么?#火焰羽毛#妖精之翼#冰块#玻璃鞋#2",//3
	"问题2: 下面中在彩虹村看不见的怪物是什么?#漂漂猪#蓝蜗牛#红蜗牛#花蘑菇#1",//2
	"问题2: 魔法密林汉斯手里的球是什么颜色的?#红色#蓝色#紫色#黑色#2",//随机
	"问题2: 海盗教官凯琳在航海室屏幕上盯着的NPC是谁?#奈落#微微安#金博士#飞天猪#3",//随机
	"问题2: 射手村弓箭手教官赫丽娜的眼睛是什么颜色的?#红色#黑色#蓝色#绿色#4",//随机
	"问题2: 色药水加多少MP?#约50#约100#约150#约200#2",//随机
	"问题2: 下面中在神秘岛看不见的怪物是什么?#蓝蘑菇#石头人#黑鄂鱼#巫婆#3");//1
var qMobs = new Array( "问题3: 下面中射手村的玛亚为只俩了自己的病要求的药是什么?#特殊的药#解毒药#止咳药水#奇怪的药#4",//2
    "问题3: 下面中废弃都市看不见的NPC是谁?#拉克里斯#鲁克#欧文#休咪#2",//3
    "问题3: 下面中在神秘岛的冰峰雪域里看不见的NPC是谁?#保姆 珥玛#飞天猪#酷男孩#杰德#1",//5
    "问题3: 神秘岛没有的村落是哪个?#天空之城#冰封雪域#勇士部落#通天塔#3",//随机
	"问题3: 2转的职业中，没有以下哪个名字?#剑客#牧师#枪战士#魔法师#4",//随机
	"问题3: 不是存在与金银岛的村子是哪一个?#射手村#魔法密林#勇士部落#天空之城#4",//随机
	"问题3: 下面中在废弃都市会见的离家出走的阿勒斯的父亲是谁?#长老斯坦#微微安#库克斯#村长塔塔曼#1",//6
    "问题3: 下面中等级最高的怪物是什么?#红蜗牛#蓝蜗牛#斧木妖#蘑菇仔#3",//1
    "问题3: 下面中要求等级最高的任务是什么?#阿尔卡斯特和黑暗水晶#好无聊#帮忙写作业#木妖好可怕#1");//4
var qQuests = new Array("问题4: 下面中药水和药水效果连接不正确的是什么?#解毒药－解除中毒#活力神水－恢复MP300#黄昏之露－恢复MP5000#清晨之露－恢复3000#4",//1
    "问题4: 下面中跟合成或冶炼工作没有关系的是谁?#赛恩#斯密斯#摩斯#辛德#1",//4
    "问题4: 下面中不是位于金银岛的村落是哪个?#天空之城#玩具城#彩虹村#神木村#3",//2
    "问题4: 下面哪个职业不是二转中出现的职业?#剑客#牧师#枪战士#斗士#4",//5
	"问题4: 在金银岛的明珠港不能看到的NPC是谁?#特奥#辛德#贝干#简#2",//随机
	"问题4: 能够进行合成和精炼的NPC是谁?#约翰#智慧爷爷#薇薇安#辛德#4",//随机
    "问题4: 下面中怪物和怪物战利品连接不正确的是什么?#小白雪人-小白雪人皮#野狼-野狼之尾#野狼-野狼之尾#食人花-食人花的叶子#4",//3
	"问题4: 下面中为2转收集30个黑珠后从转职教官收到的是什么?#通行证#英雄证书#三连胜证书#证书#2");
var qTowns = new Array( "问题5: 下面中药水和药水效果连接不恰当的是什么?#鸡蛋-HP50#烤肉-HP100#蓝色药水-MP100#披萨-HP400恢复#4",//4
    "问题5: 下面中跟宠物没有关系的NPC是谁?#威巴#帕特里沙#比休斯#奈勒#3",//1
    "问题5: 下面中可以反复解决的任务是什么?#艾温的玻璃鞋#杰德的苹果#斯卡德的头盔#奈洛的药水1#1",//5
    "问题5: 勇士部落的武术教练戴的帽子上的羽毛有几根?#8根#10根#13根#16根#3",//6
    "问题5: 下面中在金银岛中央迷宫里看不见的怪物是什么?#牛魔王#独眼龙#红蜗牛#石球#4",//2
	"问题5: 下面中能飞行的怪物是什么?#狼人#蘑菇王#企鹅#@巫婆#4");//3
var correctAnswer = 0;

function start() {
    if (cm.haveItem(4031058)) {
        if (cm.haveItem(4031058)) cm.sendOk("#h #你有#t4031058#已经。请不要浪费我的时间.");
        cm.dispose();
    } else {
        cm.sendNext("欢迎 #r#h ##k, 我是负责#r三转问答#k的#p2030006#.\r\n你已经走了很远到达这个阶段。");
    }
}

function action(mode, type, selection) {
    if (mode == -1)
        cm.dispose();
    else {
        if (mode == 0) {
            cm.sendOk("下次再见。");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 1)
            cm.sendNextPrev("#h #,如果你给我一个#v4005004#我会让你试试回答5个问题，让他们都正确，你将获得#v4031058# #b智慧项链#k.");
        else if (status == 2) {
            if (!cm.haveItem(4005004)) {
                cm.sendOk("#h #, 你没有#v4005004#");
                cm.dispose();
            } else {
                cm.gainItem(4005004, -1);
                cm.sendSimple("让我来测试你的知识 #r人物#k.\r\n\r\n" + getQuestion(qChars[Math.floor(Math.random() * qChars.length)]));
                status = 2;
            }
        } else if (status == 3) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你是正确的.\n准备好进入下一个问题了吗 ?");
            else {
                cm.sendOk("你得到一个问题答案是错误的!\r\n请你认真检查自己的知识。");
                cm.dispose();
            }
        } else if (status == 4)
            cm.sendSimple("现在让我们来进入下一个环节.\r\n\r\n" + getQuestion(qItems[Math.floor(Math.random() * qItems.length)]));
        else if (status == 5) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你是正确的.\n准备好进入下一个问题了吗 ?");
            else {
                cm.sendOk("有一题做错了!\r\n(这些都是很容易的)\r\n请认真思考答案");
                cm.dispose();
            }
        } else if (status == 6) {
            cm.sendSimple("现在你的问题 #b关于怪物#k.\r\n\r\n" + getQuestion(qMobs[Math.floor(Math.random() * qMobs.length)]));
            status = 6;
        } else if (status == 7) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你是正确的。让我们进入下一个关卡 ?");
            else {
                cm.sendOk("看来你没有回答正确啊");
                cm.dispose();
            }
        } else if (status == 8)
            cm.sendSimple("下面让我们进入这一道选择.\r\n\r\n" + getQuestion(qQuests[Math.floor(Math.random() * qQuests.length)]));
        else if (status == 9) {
            if (selection == correctAnswer) {
                cm.sendOk("#h # 你是正确的。让我们进入下一个关卡 ?");
                status = 9;
            } else {
                cm.sendOk("失败了");
                cm.dispose();
            }
        } else if (status == 10) {
            cm.sendSimple("最后一个问题.\r\n让我看看你是不是真的骨灰.\r\n\r\n" + getQuestion(qTowns[Math.floor(Math.random() * qTowns.length)]));
            status = 10;
        } else if (status == 11) {
            if (selection == correctAnswer) {
                cm.gainItem(4031058, 1);
                cm.sendOk("祝贺 #h #, 完成了三转的答题测试.\r\n把这个 #v4031058# 带给需要的长老把.");
                cm.dispose();
            } else {
                cm.sendOk("真遗憾");
                cm.dispose();
            }
        }
    }
}
function getQuestion(qSet){
    var q = qSet.split("#");
    var qLine = q[0] + "\r\n\r\n#L0#" + q[1] + "#l\r\n#L1#" + q[2] + "#l\r\n#L2#" + q[3] + "#l\r\n#L3#" + q[4] + "#l";
    correctAnswer = parseInt(q[5],10);
    correctAnswer--;
    return qLine;
}