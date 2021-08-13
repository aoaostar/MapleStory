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
            for (i = 0; i < 1; i++) {
                text += "";
            }
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#d制作#v1492073#需要#v1302064#1.#v1332056#1.#v1372034#1.#v1382039#1.#v1432040#1.#v1442051#1.#v1452045#1.#v1462040#1.#v1402039#1.#v1472055#1.#v1312032#1.#v1322054#1.#v1482022#1.#v1492022#1.#v4002000#50.冒险币500万搜集好道具我就可以为您制作了.#l\r\n\r\n"//3
            text += "#L1##r制作枫叶武器#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
			if (cm.getMeso() < 5000000) { 
				cm.sendOk("#b装备强化需要 500W金币，您的金币不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1302064) < 1 ) { 
				cm.sendOk("#b装备强化需要#v1302064#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1332056) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000048#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1372034) < 1  ) { 
 				cm.sendOk("#b装备强化需要#v4000051#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1382039) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000052#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1432040) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000049#1张，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1442051) < 1  ) { 
 				cm.sendOk("#b装备强化需要#v4000050#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1452045) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000056#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1462040) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000057#1张，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1402039) < 1  ) { 
 				cm.sendOk("#b装备强化需要#v4000053#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1472055) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000054#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1312032) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000069#1张，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1322054) < 1  ) { 
 				cm.sendOk("#b装备强化需要#v4000082#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1482022) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000472#1个，您的物品不足#k");
				cm.dispose();
			} else if (cm.itemQuantity(1492022) < 1 ) { 
				cm.sendOk("#b装备强化需要#v4000470#1张，您的物品不足#k");
				cm.dispose();
			
			 } else if (cm.itemQuantity(4002000) < 50 ) { 
				cm.sendOk("#b装备强化需要#v4000470#1张，您的物品不足#k");
				cm.dispose();
			
			
			}else{
				cm.gainItem(1302064, -1);
				cm.gainItem(1332056, -1);
				cm.gainItem(1372034, -1);
				cm.gainItem(1382039, -1);
				cm.gainItem(1432040, -1);
				cm.gainItem(1442051, -1);
				cm.gainItem(1452045, -1);
				cm.gainItem(1462040, -1);
				cm.gainItem(1402039, -1);
				cm.gainItem(1472055, -1);
				cm.gainItem(1312032, -1);
				cm.gainItem(1322054, -1);
				cm.gainItem(1482022, -1);
				cm.gainItem(1492022, -1);
				cm.gainItem(4002000, -50);
				cm.gainItem(1492073, 20,20,20,20,0,0,120,120,1,1,1,1,0,0);
				cm.gainMeso(-5000000);
            cm.sendOk("制作成功！");
            cm.dispose();
			}
		}
    }
}


