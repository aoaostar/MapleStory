function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#d您好！可以通过海盗副本蛋得到进一步的提升哦！\r\n\r\n#v1012474#Lv100级佩戴.全属性+8,HP/MP+100,防御/魔防+50,命中/回避+15,攻击/魔法+6\r\n所需材料:#v4170009#x50个.搜集完毕就可以找我进行进化了.#l\r\n\r\n"//3
            text += "#L1##r我要合成#v1012474##l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
			//1
			//2
			//3
			//4
			//5
			/*if(!cm.beibao(1,3)){
            cm.sendOk("装备栏空余不足3个空格！");
            cm.dispose();
			}else if(!cm.beibao(2,2)){
            cm.sendOk("消耗栏空余不足2个空格！");
            cm.dispose();
			}else if(!cm.beibao(3,1)){
            cm.sendOk("设置栏空余不足1个空格！");
            cm.dispose();
			}else if(!cm.beibao(4,1)){
            cm.sendOk("其他栏空余不足1个空格！");
            cm.dispose();
			}else if(!cm.beibao(5,1)){
            cm.sendOk("现金栏空余不足1个空格！");
            cm.dispose();
			}else */if(cm.haveItem(4170009,50)){
				cm.gainItem(4170009, -50);
				cm.gainItem(1012474,8,8,8,8,100,100,6,6,50,50,15,15,0,0);
				cm.gainMeso(100000);
            cm.sendOk("兑换成功！");
//cm.喇叭(2, "恭喜[" + cm.getPlayer().getName() + "]用50个[海盗副本蛋]成功合成10周年大赏枫叶脸饰，恭喜！！！");
            cm.dispose();
			}else{
            cm.sendOk("您的材料不足！");
            cm.dispose();
			}
		}
    }
}


