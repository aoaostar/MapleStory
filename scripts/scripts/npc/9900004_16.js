var status = -1;
var job = 0;
var type = -1;
//1007 - 全职业通用-锻造 - [最高等级 : 3]\n可以使用炼金术制作物品。根据自身角色等级的不同，可制作的物品不同。

//8 - 冒险家群宠 - [最高等级：1]可携带多只宠物，最多携带3只。
//1004 - 冒险家骑兽技能 - [最高等级 : 1]\n能够坐骑怪物并移动
//1017 - 冒险家皇家骑宠

//10000018 - 骑士团群宠 - [最高等级：1]可携带多只宠物，最多携带3只。
//10001004 - 骑士团骑兽技能 - [最高等级：1]\n能够坐骑怪物并移动
//10001019 - 骑士团皇家骑宠

//20000024 - 战神群宠 - [最高等级：1]可携带多只宠物，最多携带3只。
//20001004 - 战神骑兽技能 - [最高等级：1]\n能够坐骑怪物并移动
//20001019 - 战神皇家骑宠
var skill = [[8, 1004, 1007, 1017], [10000018, 10001004, 10001019], [20000024, 20001004, 20001019]];

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        status--;
    } else if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (status == 0) {
        cm.sendYesNo("到达等级10，在我这里可以帮你一键学习 骑兽技能#s8#-群宠#s1004#-锻造#s1007#-皇家骑宠技能#s1017#");
    } else if (status == 1) {
        if (cm.getPlayer().getLevel() < 10) {
            cm.sendNext("你的等级没有达到10级");
            cm.dispose();
            return;
        }
        job = cm.getPlayer().getJob();
        if (job < 1000) {// Adv(0 ~ 522)
            type = 0;
        } else if (job < 2000) {// Cy(1000 ~ 1512)
            type = 1;
        } else if (job < 3000) {// Aran(2000 ~ 2112)
            type = 2;
        } else {
            cm.dispose();
            return;
        }
        for (var i = 0; i < skill[type].length; i++) {
            var level = 1;
            if (i == 2) {
                level = 3;
            }
            cm.teachSkill(skill[type][i], level);
        }
		cm.completeQuest(8001);
        cm.sendNext("技能已经学习成功");
        cm.dispose();
    } else {
        cm.dispose();
    }
}