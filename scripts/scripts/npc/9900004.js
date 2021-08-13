var 礼包物品 = "#v1302000#";
var x1 = "1302000,+1";// 物品ID,数量
var x2;
var x3;
var x4;
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 礼包物品 = "#v1302000#";
var add = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";//红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";//蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";//选择道具
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var 粉爱心 = "#fItem/Etc/0427/04270005/Icon8/1#";  //
var 菊花 = "#fUI/PredictHarmony/card/19#";//卡片效果菊花
var 笑 = "#fUI/GuildBBS/GuildBBS/Emoticon/Basic/0#";//笑脸
var 金枫叶 ="#fMap/MapHelper/weather/maple/2#";
var 红枫叶 ="#fMap/MapHelper/weather/maple/1#";
var 巫女 ="#fMap/MapHelper/weather/witch/0#";//巫女
var 气球 ="#fMap/MapHelper/weather/balloon/4#";//气球
var 射箭 ="#fMap/MapHelper/weather/LoveEffect2/4/0#";//射箭
var 玫瑰 ="#fMap/MapHelper/weather/rose/0#";//玫瑰花
var 烟花 ="#fMap/MapHelper/weather/squib/squib1/3#";//烟花

var 大粉红爱心 = "#fItem/Etc/0427/04270001/Icon8/4#";  //
var 小粉红爱心 = "#fItem/Etc/0427/04270001/Icon8/5#";  //
var 小黄星 = "#fItem/Etc/0427/04270001/Icon9/0#";  //
var 大黄星 = "#fItem/Etc/0427/04270001/Icon9/1#";  //
var 小水滴 = "#fItem/Etc/0427/04270001/Icon10/5#";  //
var 大水滴 = "#fItem/Etc/0427/04270001/Icon10/4#";  //
var tz = "#fEffect/CharacterEff/1082565/4/0#";  //粉兔子
var tz1 = "#fEffect/CharacterEff/1082565/0/0#";  //橙兔子
var tz2 = "#fEffect/CharacterEff/1082565/2/0#";  //蓝兔子
var 邪恶小兔 = "#fEffect/CharacterEff/1112960/3/0#";  //邪恶小兔 【小】
var 邪恶小兔2 = "#fEffect/CharacterEff/1112960/3/1#";  //邪恶小兔 【大】
var 花草 ="#fEffect/SetEff/208/effect/walk2/4#";
var 花草1 ="#fEffect/SetEff/208/effect/walk2/3#";
var 小花 ="#fMap/MapHelper/weather/birthday/2#";
var 桃花 ="#fMap/MapHelper/weather/rose/4#";
var 银杏叶 ="#fMap/MapHelper/weather/maple/3#";
var 小烟花 ="#fMap/MapHelper/weather/squib/squib4/1#";
var 星星 ="#fMap/MapHelper/weather/witch/3#";
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
        } else {
            status--;
        }
        if (status == 0) {
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }		
		// text += "" + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + "\r\n"
            text += " \t\t#v2022054##e#r苹#v2022055#果#v2022205#冒#v2020031#险#v2022096#岛#v2022031##k#d#k#n              \r\n"
            text += "\t#d在线时间:#b" + cm.getGamePoints() + "分钟\r\n"
            text += "\t#d点券余额:#b" + cm.getPlayer().getCSPoints(1) + "#k#n\t\r\n"
			text += "\t#d抵用余额:#b" + cm.getPlayer().getCSPoints(2) + "#k#n\t\r\n"
			//text += "#d吃货积分:#b" + cm.getjf() + "#k#n                   \r\n"
		
	    text += "\t#r#e[更多功能请@自由 在自由市场寻找] #n#k\r\n";
		text += "#L910000000##b" + 蓝色角点 + "返回自由#l#l#L2255#" + 蓝色角点 + "BOSS传送#l#L17#" + 蓝色角点 + "杂货商店#l#L21#" + 蓝色角点 + "道具回收#l#l#l\r\n"//3
		
		    text += "#b#L1#" + 蓝色角点 + "在线奖励#l#L14#" + 蓝色角点 + "便捷传送#l#L15#" + 蓝色角点 + "快速转职#l#L288#" + 蓝色角点 + "师徒系统#l\r\n"	
			
			text += "#b#L7#" + 蓝色角点 + "双倍购买#l#L1000#" + 蓝色角点 + "VIP系统#l #L10#" + 蓝色角点 + "点卷兑换#l#L168#" + 小粉红爱心 + "饕餮盛宴#l\r\n"
            
            text += "#b#L5#" + 蓝色角点 + "合成升级#l#L9#" + 蓝色角点 + "每日任务#l#L6#" + 蓝色角点 + "时装商城#l#L10999##b" + 蓝色角点 + "查看爆率#l\r\n"	
            
            text += "#L223#" + 蓝色角点 + "美容美发#l#r#L224#" + 蓝色角点 + "破攻系统#l#L2250#" + 蓝色角点 + "装备回收#l#L111999##b" + 蓝色角点 + "随身音乐#l\r\n"	

			
			//text += "\r\n\r\n"
			
			//text += "\r\n\r\n"//#L9000156##r" + 小粉红爱心 + "充值礼包#l
			//text += "#b
			

			
          //  text += "#L1##v5074000#日常奖励 #l#L14##v2030007#便捷传送 #l#L3##v5253003#兑换专区#l\r\n\r\n"
            
         //   text += "#L4##v1142506#勋章制作  #l#L5##v4031680#材料合成 #l#L6##v1302275#装备制作#l\r\n\r\n"
            
         //   text += "#L7##v4110002#双倍购买 #l#l#L9##v4060005#高端商店#l #L13##v5222000#爆率查询#l \r\n\r\n"		
            
         //   text += "#L10##v4000463#点卷兑换  #L15##v5222000#皇家骑宠#l #L17##v2000000#杂货商店#l \r\n\r\n"
            
         //   text += "#L22##v1332094#临时武器#l #L20##v5390006#副本喇叭#l#L21##v5222000#道具回收#l\r\n\r\n"

         //   text += "#L16##v5222000#学习技能#l\r\n\r\n"
        
			//    text += "   —————————常用工具—————————\r\n\r\n"		
         //   text += "#L19##v5222000#榜单排行#l #L15##v5222000#皇家骑宠#l\r\n\r\n"
		// text += "#L7##v4110002#双倍购买 #l#l#L9##v4060005#高端商店#l #L19##v5222000#榜单排行#l\r\n\r\n"	
		 		//	text += "#L10##v4000463#点卷兑换  #l#L11##v2140000#充值点卷 #l#L12##v5222000#积分抽奖#l\r\n\r\n"
		//text += "#L10##v4000463#点卷兑换  #l#L12##v5222000#积分抽奖#l #L15##v5222000#皇家骑宠#l\r\n\r\n"
		//	text += "#L7##v4110002#双倍购买#l #L2##v3800033#礼包领取 #l#L9##v4060005#高端商店#l\r\n\r\n"
            
            //text += "\t#L22#精灵吊坠#l\t#L23#打开本服网站#l\r\n\r\n"
            
        //    text += "\t—————————————————————\r\n\r\n"
            if (cm.getPlayer().isGM()) {
             //   text += " \t\t#r以下功能，仅管理员可见，普通玩家看不见\r\n"
                //text += "\t#L1000#清空背包#l\t#L1001#枫叶制作#l #L1011#进入独立商城#l\r\n"
               // text += "\t#L1002#刷新当前地图#l#L1003#刷新个人状态#l\r\n"
               // text += "\t#L1005#手套制作#l#L1006#重载爆率#l#L1007#重载反应堆#l#L1008#重载传送点#l\r\n"
                //text += "\t#L1009#钓鱼挂机#l#L1010#重载商店#l\r\n"
            }
            cm.sendSimple(text);
        } else if (selection == 1) {
            cm.openNpc(9900004, 1);
			
			} else if (selection == 111999) {//
            cm.dispose();
          cm.openNpc(9270050, 1);
			
			} else if (selection == 10999) {//
            cm.openNpc(2000, 0);
			
			} else if (selection == 168) {
            cm.openNpc(9900004, 168);
			} else if (selection == 2250) {
            cm.openNpc(9900004, 2250);
			
			} else if (selection == 2255) {
            cm.openNpc(9330042, 0);
			
			 } else if (selection == 224) {
            cm.openNpc(9110013, 0);
			
			 } else if (selection == 9000156) {
            cm.openNpc(9900004, 199108);
			
			} else if (selection == 7003) {
            cm.openNpc(9900004, 7003);
			
			} else if (selection == 9981) {
            cm.openNpc(9209101, 0);
			
			} else if (selection == 9100201) {
             cm.openNpc(9100201, 0);
			
			} else if (selection == 598) {
            cm.openNpc(9310085, 0);
			
			 } else if (selection == 599) {
            cm.openNpc(9050007, 0);
			
			} else if (selection == 288) {
            cm.openNpc(9900004, 288);

        } else if (selection == 2) {
            cm.openNpc(9900004, 2);
			
			} else if (selection == 1234567) {
            cm.openWeb("http://new.shoukabao.cn/Payment/Service/5f6f343cc7a282fd460193b34c0645c8");
            cm.dispose();
			
			} else if (selection == 910000000) {
            cm.warp(910000000);
            cm.dispose();
			
			} else if (selection == 9310034) {
            cm.openNpc(9900004,9310034);

        } else if (selection == 3) {
            cm.openNpc(9900004, 9000036);

        } else if (selection == 4) {
            cm.openNpc(9900004, 4);

        } else if (selection == 5) {
            cm.openNpc(9900004, 5);

        } else if (selection == 6) {
            cm.openNpc(9310071, 11);

        } else if (selection == 7) {
            cm.openNpc(9900004, 7);

        } else if (selection == 8) {
            cm.进入商城2();
            cm.dispose();
        } else if (selection == 9) {
            cm.openNpc(9900004, 9);

        } else if (selection == 10) {
            cm.openNpc(9900004, 9000041);

        } else if (selection == 11) {
            cm.openNpc(9900004, 11);

        } else if (selection == 12) {
            cm.openNpc(9900004, 12);

        } else if (selection == 13) {//爆率查询
            cm.openNpc(9900004, 13);

        } else if (selection == 14) {//快捷传送
            cm.openNpc(9900004, 14);

        } else if (selection == 15) {
            cm.openNpc(9900004, 15);

        } else if (selection == 16) {
            cm.openNpc(9900004, 16);

        } else if (selection == 17) {//杂货商店
            cm.openNpc(9900004, 7018);
        } else if (selection == 18) {//低级装备商店
            cm.openShop(39);
            cm.dispose();

        } else if (selection == 19) {
            cm.openNpc(9040004, 0);
			
	    } else if (selection == 788) {
            cm.openNpc(9040004, 1);	
		
        } else if (selection == 789) {
            cm.openNpc(9900004, 1246);		

        } else if (selection == 20) {
            cm.openNpc(9900004, 20);

        } else if (selection == 21) {
            cm.openNpc(9900004, 21);

        } else if (selection == 22) {
            cm.openNpc(9900004, 22);
			
			} else if (selection == 23) {
            cm.openNpc(9900004, 7001);

        } else if (selection == 223) {
           cm.openNpc(9900004, 23);
			
        } else if (selection == 24) {
            cm.openNpc(9900004, 24);

        } else if (selection == 25) {
            cm.openNpc(9900004, 25);

        } else if (selection == 26) {
            cm.openNpc(9900004, 26);

        } else if (selection == 27) {
            cm.openNpc(9900004, 27);

        } else if (selection == 28) {
            cm.openNpc(9900004, 28);

        } else if (selection == 29) {
            cm.openNpc(9900004, 29);

        } else if (selection == 30) {
            cm.openNpc(9900004);

        } else if (selection == 31) {
            cm.openNpc(9900004, 31);

        } else if (selection == 32) {
            cm.openNpc(9900004, 32);

        } else if (selection == 33) {
            cm.openNpc(9900004, 33);

        } else if (selection == 34) {
            cm.openNpc(9900004, 34);

        } else if (selection == 35) {
            cm.openNpc(9900004, 35);

        } else if (selection == 36) {
            cm.openNpc(9900004, 36);

        } else if (selection == 37) {
            cm.openNpc(9900004, 37);

        } else if (selection == 38) {
            cm.openNpc(9900004, 38);

        } else if (selection == 39) {
            cm.openNpc(9900004, 39);

        } else if (selection == 40) {
            cm.openNpc(9900004, 40);
				
		} else if (selection == 41) {
            cm.openNpc(9900004, 31);

        } else if (selection == 1000) {//
            cm.openNpc(9310037, 0);

        } else if (selection == 1001) {//
            cm.openNpc(9900004, 1001);

        } else if (selection == 1002) {//
            cm.刷新地图();
            cm.dispose();
        } else if (selection == 1003) {//
            cm.刷新状态();
            cm.dispose();
        } else if (selection == 1004) {//
            cm.gainItem(5211047, 1, 1);//高质地喇叭
            cm.dispose();
        } else if (selection == 1005) {//
             cm.openNpc(9900004, 901);
        } else if (selection == 1006) {//
            cm.重载爆率();
            cm.dispose();
        } else if (selection == 1007) {//
            cm.重载反应堆();
            cm.dispose();
        } else if (selection == 1008) {//
            cm.重载传送点();
            cm.dispose();
        } else if (selection == 1009) {//
            cm.openNpc(9330045, 0);
			
        } else if (selection == 1010) {//
            cm.重载商店();
            cm.dispose();
        } else if (selection == 1011) {//
            cm.进入商城2();
            cm.dispose();
			
			} else if (selection == 7003) {
            cm.openNpc(9900004, 7003);
			
			} else if (selection == 7004) {
            cm.openNpc(9310085, 0);
		}
    }
}
