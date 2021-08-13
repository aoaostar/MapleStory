/* Author: Xterminator
	NPC Name: 		Robin
	Map(s): 		Maple Road : Snail Hunting Ground I (40000)
	Description: 		Beginner Helper
*/
var status = -1;
var mainmenu = "现在...问我关于旅行的问题!!\r\n#L0##b我怎么移动?#l\r\n#L1#我该如何杀死这些怪物?#l\r\n#L2#我该怎样捡取道具?#l\r\n#L3#我死后会发生什么?#l\r\n#L4#我什么时候可以选择职业？#l\r\n#L5#告诉我更多关于怀旧冒险岛的事情！#l\r\n#L6#我要怎么做才能成为一名战士？#l\r\n#L7#我要怎么做才能成为一名弓箭手?#l\r\n#L8#我要怎么做才能成为一名魔法师?#l\r\n#L9#我要怎么做才能成为一名飞侠?#l\r\n#L10#如何提高角色的能力值? (S)#l\r\n#L11#我如何查看刚刚捡取的物品?#l\r\n#L12#我怎样使用一件物品?#l\r\n#L13#我怎样查看我穿的衣服?#l\r\n#L14#什么是技能? (K)#l\r\n#L15#我怎样才能去明珠港?#l\r\n#L16#金币是什么？#l#k";

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    cm.sendNext("以下是如何杀死一个怪物。每一个怪物都拥有自己的HP，你将用武器或法术攻击他们。当然，他们越强，就越难把他们杀死。");
	}
	status--;
    }
    if (status == 0) {
	cm.sendSimple(mainmenu);
    } else if (status == 1) {
	if (selection == 0) { // How do I move?
	    status = -1;
	    cm.sendNext("好的，这就是你移动的方式. 使用键盘上的 #b←左箭头, 和 →右箭头#k 就可以在平地和倾斜的道路上移动, 并按 键盘左下角的#bAlt#k键跳跃. 精选的鞋子可以提高你的移动速度和弹跳能力。看到地图里的发光处，那是传送门，走过去按键盘上的 #b↑上箭头#k .就可以进入传送门去往别的地图，按 ↓下箭头是趴下来。");
	} else if (selection == 1) { // How do I take down the monsters?
	    cm.sendNext("以下是如何杀死一个怪物。每一个怪物都拥有自己的HP，你将用武器或法术攻击他们。当然，他们越强，就越难把他们杀死。");
	} else if (selection == 2) { // How can I pick up an item?
	    status = 5;
	    cm.sendNext("这就是你如何捡取道具的方法。一旦你杀死一个怪物，从怪物身上就会有物品掉落到地图上。当捡取时，角色站在该物品的地方，并按键盘上的“Z”键，就可以捡取道具了。");
	} else if (selection == 3) { // What happens when I die?
	    status = 8;
	    cm.sendNext("想知道你死后会发生什么吗？当你的HP达到0时，你会变成鬼魂.将有一个墓碑在你的死亡的地方出现，你将无法移动，虽然你仍然可以聊天.");
	} else if (selection == 4) { // When can I choose a job?
	    status = 11;
	    cm.sendNext("你什么时候可以选择职业？哈哈哈，放心吧，我的朋友。每个职业都要有一套要求，需要你达到需求！通常情况下8到10级之间的水平是可以的，所以要努力打怪升级。");
	} else if (selection == 5) { // Tell me more about this island!
	    status = 14;
	    cm.sendNext("想知道这个岛吗？他叫怀旧冒险岛，漂浮在空中，他一直漂浮在天空中，所以那些讨厌的怪物，并不是真的存在。这是一个非常安静的岛，适合初学者!");
	} else if (selection == 6) { // What should I do to become a Warrior?
	    status = -1;
	    cm.sendNext("你想成为一名#b战士#k? 恩, 那么我建议你去金银岛.勇士部落前往一个叫战士圣殿的地方。找NPC#r武术教练#k and see #bDances with Balrog#k. 他会教你成为真正的战士.额，一个很重要的事情；你必须至少10级才能成为一名战士！！");
	} else if (selection == 7) { // What should I do to become a Bowman?
	    status = -1;
	    cm.sendNext("你想成为一名#b弓箭手#k? 恩, 那么我建议你去金银岛.射手村前往一个叫弓箭手培训中心地方。找NPC#r赫丽娜#k. 他会教你成为真正的弓箭手.额，一个很重要的事情；你必须至少10级才能成为一名弓箭手！！");
	} else if (selection == 8) { // What should I do to become a Magician?
	    status = 19;
	    cm.sendNext("你想成为一名#b魔法师#k? 恩, 那么我建议你去金银岛.魔法密林前往一个叫图书馆的地方。找NPC#r汉斯#k. 他会教你成为真正的魔法师.额，一个很重要的事情；你必须至少8级才能成为一名魔法师！！");
	} else if (selection == 9) { // What should I do to become a Thief?
	    status = -1;
	    cm.sendNext("你想成为一名#b飞侠#k? 恩, 那么我建议你去金银岛.废弃都市前往一个叫酒吧的地方。找NPC#r达克鲁#k. 他会教你成为真正的飞侠.额，一个很重要的事情；你必须至少8级才能成为一名飞侠！！");
	} else if (selection == 10) { // How do I raise the character stats? (S)
	    status = 22;
	    cm.sendNext("你想知道如何提高你的角色能力值？先按键盘上的“S”键，打开能力值窗口，选择与你职业匹配的能力值，进行加点就可以了。当你升级时，你将获得5个能力点，即AP。就是这么简单。");
	} else if (selection == 11) { // How do I check the items that I just picked up?
	    status = -1;
	    cm.sendNext("你想知道如何查看你捡到的东西？当你杀死一个怪物时候，他会掉落一个物品，你可以按Z键捡取道具，然后该道具将存储在你的背包里，你可以按“I”键打开背包，查看你刚捡取到的物品。#k.");
	} else if (selection == 12) { // How do I put on an item?
	    status = -1;
	    cm.sendNext("你想知道如何穿戴衣服？按I键打开背包，鼠标移动到你想穿戴的装备上面，双击它，就可以装备道具了。当然如果你发现自己穿不上这件装备，那么就是你的职业属性等不符合这件装备的设定，那么你就无法穿戴了。你也可以通过打开仓库对该装备进行存储，或打开商店对该装备进行卖出处理。也可以鼠标单击装备，然后把图标拖出装备窗口，再按一下鼠标左键，就可以把物品丢弃掉了。怎么样？会了吧！");
	} else if (selection == 13) { // How do I check out the items that I'm wearing?
	    status = -1;
	    cm.sendNext("你要查看穿在身上的装备？按键盘上的“E”键打开装备窗口，里面就是你身上佩戴的装备了，鼠标移动到装备上会显示装备的数据信息。双击穿在身上的装备，就可以取下装备，放入背包哦~！");
	} else if (selection == 14) { // What are skills? (K)
	    status = -1;
	    cm.sendNext("获得一份工作后，获得的特殊能力叫做技能。你将获得专门的技能，按键盘上的“K”键，可以查看你的技能列表，也可以给技能增加点数，强化技能的属性值。四转后，需要技能书，才能帮你加满技能哦~！");
	} else if (selection == 15) { // How do I get to Victoria Island?
	    status = -1;
	    cm.sendNext("你可以到金银岛，从码头坐船到明珠港。按键盘上的“W”键可以查看世界地图，你会看到你在金银岛上的位置。");
	} else if (selection == 16) { // What are mesos?
	    status = -1;
	    cm.sendNext("金币是在大王冒险岛使用的货币。你可以通过它们来购买物品。为了获取它们，你必须杀死大量的怪物，从它们身上获取金币。也可以在商店出售一些垃圾的装备物品等，或完成任务，来获取金币");
	}
    } else if (status == 2) { // How do I take down the monsters?
	cm.sendNextPrev("为了攻击这些怪物，你需要装备一个武器，在使用武器的时候。配合技能。就可以轻松的干掉怪物了。");
    } else if (status == 3) { // How do I take down the monsters?
	cm.sendNextPrev("一旦你的职业提升。你会获得更多的不同技能，你可以将他们分配给快捷键，更容易使用它们。如果是攻击技能，你不需要按Ctrl键来攻击，需要按快捷热键分配的按钮就可以了。");
    } else if (status == 4) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 5) { // How can I pick up an item?
	cm.sendNext("这就是你如何捡取道具的方法。一旦你杀死一个怪物，从怪物身上就会有物品掉落到地图上。当捡取时，角色站在该物品的地方，并按键盘上的“Z”键，就可以捡取道具了。");
    } else if (status == 6) { // How can I pick up an item?
	cm.sendNextPrev("但是要记住，如果你的背包满了，就无法捡取了。所以如果你有个道具不需要了，卖掉它，这样你就可以从中有所收获，一旦你的等级提高，了解的更多的时候，就会发现可以扩大背包，这样你就能获得并存放更多的道具了");
    } else if (status == 7) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 8) { // What happens when I die?
	cm.sendNext("想知道你死后会发生什么吗？当你的HP达到0时，你会变成鬼魂.将有一个墓碑在你的死亡的地方出现，你将无法移动，虽然你仍然可以聊天.");
    } else if (status == 9) { // What happens when I die?
	cm.sendNextPrev("如果你是一名新手，那么当你死亡的时候，不会有什么损失，然而一旦你选择了一份职业。情况就不一样了。当你死亡的时候，你会失去一部分的经验值。所以确保不惜一切代价，避免危险和死亡！");
    } else if (status == 10) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 11) { // When can I choose a job?
	cm.sendNext("你什么时候可以选择职业？哈哈哈，放心吧，我的朋友。每个职业都有一套要求来让你达成，通常8到10级之间的水平就可以了。所以要努力升级哦！");
    } else if (status == 12) { // When can I choose a job?
	cm.sendNextPrev("等级不是唯一决定进步的因素，你还需要提高到一定的级别，增加以职业为基础的能力值。例如要成为一个战士，你的力量值必须超过35点，等等，你知道我在说什么吗？确保你知道你对自己的职业有个清晰的了解。");
    } else if (status == 13) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 14) { // Tell me more about this island!
	cm.sendNext("想知道这个岛吗？他叫大王冒险岛，漂浮在空中，他一直漂浮在天空中，所以那些讨厌的怪物，并不是真的存在。这是一个非常安静的岛，适合初学者!");
    } else if (status == 15) { // Tell me more about this island!
	cm.sendNextPrev("但是，如果你想成为一个强大的玩家，最好不要考虑在这里久待！你应该去往一个叫做金银岛的巨大岛屿，那个地方比这里大得多，一点也不好笑。那里很危险的。");
    } else if (status == 16) { // Tell me more about this island!
	cm.sendNextPrev("你怎么去金银岛？在该岛的东部有一个港口。在那里你可以找到一个在空中飞翔的船，老船长站在船的前面，问题这个事情。");
    } else if (status == 17) { // Tell me more about this island!
	cm.sendNextPrev("哦是的！在我走之前最后一条信息。如果你不知道你在哪里，就按“W”键查看大地图，会弹出定位器显示你的位置，你不必担心会迷失方向。");
    } else if (status == 18) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 19) { // What should I do to become a Magician?
	cm.sendNext("你想成为一名#b魔法师#k? 恩, 那么我建议你去金银岛.魔法密林前往一个叫图书馆的地方。找NPC#r汉斯#k. 他会教你成为真正的魔法师.");
    } else if (status == 20) { // What should I do to become a Magician?
	cm.sendNextPrev("哦，顺便说一下，不像别的职业，成为一个魔法师，你只需要在8级就可以转职了。为什么法师的入门标准低于其他职业呢？因为法师升级慢啊，前期没有法术配合，就是个小辣鸡，所以低于别的职业两级就能转职啦。哈哈。");
    } else if (status == 21) {
	status = 0;
	cm.sendSimple(mainmenu);
    } else if (status == 22) { // How do I raise the character stats? (S)
	cm.sendNext("你想知道如何提高你的角色能力值？先按键盘上的“S”键，打开能力值窗口，选择与你职业匹配的能力值，进行加点就可以了。当你升级时，你将获得5个能力点，即AP。就是这么简单。");
    } else if (status == 23) { // How do I raise the character stats? (S)
	cm.sendNextPrev("将鼠标光标放在所有的能力值上面，就会出现简要说明。例如战士的STR弓箭手的DEX魔法师的INT飞侠的LUK这些都是各职业的基础能力。你需要仔细考虑如何自身的不足之处，从而强大你自己。");
    } else if (status == 24) {
	status = 0;
	cm.sendSimple(mainmenu);
    }
}
