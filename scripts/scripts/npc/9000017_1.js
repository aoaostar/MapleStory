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
            text += "#e#d制作[老公老婆戒指]需要#v4002000#x10、MXB=20W搜集好道具我就可以为您制作了.\r\n#r最高可提升至LV5#k.#l\r\n\r\n"//3
            text += "#L1##r制作老公老婆戒指#l\r\n\r\n"//3
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
			}else */if(cm.haveItem(4002000,10) && cm.getMeso() >=200000){
				cm.gainItem(4002000, -10);				
				cm.gainItem(1112446, 1,1,1,1,50,50,0,0,0,0,0,0,2,2);//老公戒指V1
				cm.gainMeso(-200000);
            cm.sendOk("换购成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]制作了[老公老婆戒指LV1]，继续加油将它打造到极致吧！");
            cm.dispose();
cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]成功制作了[老公老婆戒指LV1]，继续加油将它打造到极致吧！");
			}else{
            cm.sendOk("您的材料不足！制作[老公老婆戒指LV1]，需要\r\n#v4011001#x1、\r\n#v4011000#x1、\r\n#v4011003#x1、\r\n#v4011002#x1");
            cm.dispose();
			}
		}
    }
}


