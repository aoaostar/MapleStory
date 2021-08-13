var 礼包物品 = "#v1302000#";
var x1 = "1302000,+1";// 物品ID,数量
var x2;
var x3;
var x4;
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";

function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
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
			if(cm.getJob() >= 0 && cm.getJob()<= 522 && cm.hasSkill(1017) == false){
			cm.teachSkill(1017,1,1);
			}else if(cm.getJob() >=1000 || cm.getJob() <= 2112 && cm.hasSkill(20001019) == false){
			cm.teachSkill(20001019,1,1);
			}
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
			
            text += "\t#b#L0#抽椅子#v3015343##l#b#L3#技能抽奖#v3015343##b#L100#皇家坐骑#v3015343#\r\n\r\n"
            text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n"
			//text+="#L100##v4000463#2个国庆币-皇家坐骑随机抽奖#l\r\n";
			text+="#L101##v2002033#1个巧克力蛋糕-武器装备随机抽奖#l\r\n";
			text+="#L102##v4001126#30个枫叶抽3张-10%卷轴#l\r\n";
			//text+="#L33##v2280006#500个枫叶-技能书随机抽奖#l\r\n";
			//text+="#L22##v1302021#5000点券-玩具装备随机抽奖#l\r\n";
		    cm.sendSimple(text);
        } else if (selection == 0) {//蜈蚣
             cm.dispose();
             cm.openNpc(9900004, 666);
       } else if (selection == 1) {//未来之城
             cm.dispose();
             cm.openNpc(9900004, 777);
        
        } else if (selection == 2) {//130
            cm.openNpc(9310100, 0);
        
        } else if (selection == 3) {//140
            cm.openNpc(2041024, 0);
			
		}	else if(selection==101){
				cm.openNpc(9310024,101);
				
				}	else if(selection==100){
				cm.openNpc(9310085,0);
				
				}	else if(selection==102){
				cm.openNpc(9310024,102);
			
		}	else if(selection==199){
				cm.openNpc(9310024,199);
			

			
						}else if (selection == 22){
				cm.openNpc(9310024, 123);
			}else if (selection == 33){
				cm.openNpc(9310024, 4);
       
        } else if (selection == 4) {//血衣
            cm.openNpc(9310071, 53);
        
        } else if (selection == 5) {//血衣
            cm.openNpc(9310071, 54);
        
        } else if (selection == 6) {//血衣
            cm.openNpc(9310071, 55);
        
        } else if (selection == 7) {//血衣
            cm.openNpc(9310071, 56);

        } else if (selection == 8) {//血衣
            cm.openNpc(9310071, 57);

        } else if (selection == 10) {//血衣
            cm.openNpc(9330078, 98);

        } else if (selection == 11) {//血衣
            cm.openNpc(9330078, 99);
        } else if (selection == 20) {//血衣
            cm.openNpc(9310071, 66);
        
        } 

    }
}