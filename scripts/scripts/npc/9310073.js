var 星星 = "#fEffect/CharacterEff/1114000/2/0#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 礼包物品 = "#v1302000#";
var x1 = "1302000,+1"; // 物品ID,数量
var x2;
var x3;
var x4;
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 礼包物品 = "#v1302000#";
var add = "#fEffect/CharacterEff/1112903/0/0#"; //红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#"; //红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#"; //蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#"; //选择道具
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#"; //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#"; //爱心
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE); //获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE); //获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";

var 粉爱心 = "#fItem/Etc/0427/04270005/Icon8/1#"; //
var 菊花 = "#fUI/PredictHarmony/card/19#"; //卡片效果菊花
var 笑 = "#fUI/GuildBBS/GuildBBS/Emoticon/Basic/0#"; //笑脸
var 金枫叶 = "#fMap/MapHelper/weather/maple/2#";
var 红枫叶 = "#fMap/MapHelper/weather/maple/1#";
var 巫女 = "#fMap/MapHelper/weather/witch/0#"; //巫女
var 气球 = "#fMap/MapHelper/weather/balloon/4#"; //气球
var 射箭 = "#fMap/MapHelper/weather/LoveEffect2/4/0#"; //射箭
var 玫瑰 = "#fMap/MapHelper/weather/rose/0#"; //玫瑰花
var 烟花 = "#fMap/MapHelper/weather/squib/squib1/3#"; //烟花

var 大粉红爱心 = "#fItem/Etc/0427/04270001/Icon8/4#"; //
var 小粉红爱心 = "#fItem/Etc/0427/04270001/Icon8/5#"; //
var 小黄星 = "#fItem/Etc/0427/04270001/Icon9/0#"; //
var 大黄星 = "#fItem/Etc/0427/04270001/Icon9/1#"; //
var 小水滴 = "#fItem/Etc/0427/04270001/Icon10/5#"; //
var 大水滴 = "#fItem/Etc/0427/04270001/Icon10/4#"; //
var tz = "#fEffect/CharacterEff/1082565/4/0#"; //粉兔子
var tz1 = "#fEffect/CharacterEff/1082565/0/0#"; //橙兔子
var tz2 = "#fEffect/CharacterEff/1082565/2/0#"; //蓝兔子
var 邪恶小兔 = "#fEffect/CharacterEff/1112960/3/0#"; //邪恶小兔 【小】
var 邪恶小兔2 = "#fEffect/CharacterEff/1112960/3/1#"; //邪恶小兔 【大】
var 花草 = "#fEffect/SetEff/208/effect/walk2/4#";
var 花草1 = "#fEffect/SetEff/208/effect/walk2/3#";
var 小花 = "#fMap/MapHelper/weather/birthday/2#";
var 桃花 = "#fMap/MapHelper/weather/rose/4#";
var 银杏叶 = "#fMap/MapHelper/weather/maple/3#";
var 小烟花 = "#fMap/MapHelper/weather/squib/squib4/1#";
var 星星 = "#fMap/MapHelper/weather/witch/3#";

function start() {
    status = -1;

    action(1, 0, 0);
}

function action(mode, type, selection) {
    
        cm.sendOk("期待开启！");
        cm.dispose();
 


    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        // if (mode == 1) {
        //     status++;
        // } else {
        //     status--;
        // }
        // if (status == 0) {
        //     var tex2 = "";
        //     var text = "";
        //     for (i = 0; i < 10; i++) {
        //         text += "";
        //     }
        //     //  text += ""+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+爱心+"\r\n\r\n"
        //     text += "\t  " + 星星 + 星星 + "#e转生系统#b（255级可转生）" + 星星 + 星星 + " #k#n\r\n#r\t\t\t\t\r\n\t\t\t\t" + 星星 + "角色转生等级:#b" + cm.getClient().getPlayer().getBeans() + "级" + 星星 + "\r\n\r\n"
        //     text += "\t" + 蓝色箭头 + "#L0##b转生体系说明：转生后换频道生效#k#l\r\n"
        //     text += "\t" + 蓝色箭头 + "#L1#开始转生：#r需要#v4000464##v4310149#各10个#k#l\r\n\r\n" //3
        //     text += "\t" + 蓝色箭头 + "#L2#转生奖励：#r#v1082102#X1(+100全属性HP+5000)转生1次#k#l\r\n\r\n" //3
        //     text += "\t" + 蓝色箭头 + "#L3#转生奖励：#r#v1022048#X1(+200全属性HP+5000)转生3次#k#l\r\n\r\n" //3
        //     text += "\t" + 蓝色箭头 + "#L4#转生奖励：#r#v1072153#X1(+300全属性HP+5000)转生5次#k#l\r\n\r\n" //3
        //     text += "" + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + 爱心 + "\r\n"

        //     cm.sendSimple(text);

        // } else if (selection == 0) { //体系说明
        //     cm.sendOk("#e独家转生系统,让你越肝越强！\r\n#b转生说明：变成10级新手保留所有属性和键盘技能\r\n（#rPS：转生等级+1级）#k");

        // } else if (selection == 1) { //转生
        //     if (cm.getClient().getPlayer().getBeans() > 5) {
        //         cm.sendOk("转生等级最高5级，您已经上天入地无所不能！");
        //         cm.dispose();
        //     } else if (cm.getLevel() < 255) {
        //         cm.sendOk("转生要求等级达到255级。您的能力没有资格转生");
        //         cm.dispose();
        //     } else if (cm.getPlayer().getMeso() < 200000000) {
        //         cm.sendOk("需要2E金币才能转生.");
        //         cm.dispose();
        //     } else if (!cm.haveItem(4000464, 2)) {
        //         cm.sendOk("需要中国心X2！");
        //         cm.dispose();
        //     } else {
        //         cm.setPlayerStat("LVL", 11);
        //         cm.changeJob(000)
        //         cm.spawnMonster(9300340, 2);
        //         cm.gainItem(4000464, -10);
        //         cm.gainItem(4310149, -10);
        //         cm.getPlayer().gainBeans(1);
        //         // cm.gainAp(+5);//转生奖励
        //         cm.sendOk("恭喜！转生成功！请换线生效！.");
        //         cm.喇叭(1, "[转生俱乐部]：玩家" + cm.getPlayer().getName() + "转生成功！可喜可贺！蛋糕怪物出现了！");
        //         cm.全服漂浮喇叭("恭喜玩家[" + cm.getName() + "]转生成功！可喜可贺！蛋糕怪物出现了！", 5120009);
        //         cm.dispose();
        //         return;
        //     }

        // } else if (selection == 2) { //转生
        //     if (cm.getClient().getPlayer().getBeans() < 1) {
        //         cm.sendOk("您的转生等级不足以领取奖励！");
        //         cm.dispose();
        //     } else if (cm.getQuestStatus(1000010) == 0) {
        //         cm.completeQuest(1000010);
        //         cm.gainItem(1082102, 100, 100, 100, 100, 5000, 5000, 100, 100, 0, 0, 0, 0, 0, 0);
        //         // cm.gainAp(+5);//转生奖励
        //         cm.sendOk("恭喜！转生奖励领取成功！.");
        //         cm.喇叭(1, "[转生奖励]：玩家" + cm.getPlayer().getName() + "转生奖励领取成功！可喜可贺！");
        //         cm.全服漂浮喇叭("恭喜玩家[" + cm.getName() + "]转生奖励领取成功！可喜可贺！", 5120008);
        //         cm.dispose();
        //     } else
        //         cm.sendOk("抱歉，已经领取过该奖励~");
        //     cm.dispose();

        // } else if (selection == 3) { //转生
        //     if (cm.getClient().getPlayer().getBeans() < 3) {
        //         cm.sendOk("您的转生等级不足以领取奖励！");
        //         cm.dispose();
        //     } else if (cm.getQuestStatus(1000011) == 0) {
        //         cm.completeQuest(1000011);
        //         cm.gainItem(1022048, 200, 200, 200, 200, 5000, 5000, 200, 200, 0, 0, 0, 0, 0, 0);
        //         // cm.gainAp(+5);//转生奖励
        //         cm.sendOk("恭喜！转生奖励领取成功！.");
        //         cm.喇叭(1, "[转生奖励]：玩家" + cm.getPlayer().getName() + "转生奖励领取成功！可喜可贺！");
        //         cm.全服漂浮喇叭("恭喜玩家[" + cm.getName() + "]转生奖励领取成功！可喜可贺！", 5120006);
        //         cm.dispose();
        //     } else
        //         cm.sendOk("抱歉，已经领取过该奖励~");
        //     cm.dispose();

        // } else if (selection == 4) { //转生
        //     if (cm.getClient().getPlayer().getBeans() < 5) {
        //         cm.sendOk("您的转生等级不足以领取奖励！");
        //         cm.dispose();
        //     } else if (cm.getQuestStatus(1000012) == 0) {
        //         cm.completeQuest(1000012);
        //         cm.gainItem(1072153, 300, 300, 300, 300, 5000, 5000, 300, 300, 0, 0, 0, 0, 0, 0);
        //         // cm.gainAp(+5);//转生奖励
        //         cm.sendOk("恭喜！转生奖励领取成功！.");
        //         cm.喇叭(1, "[转生奖励]：玩家" + cm.getPlayer().getName() + "转生奖励领取成功！可喜可贺！");
        //         cm.全服漂浮喇叭("恭喜玩家[" + cm.getName() + "]转生奖励领取成功！可喜可贺！", 5120007);
        //         cm.dispose();
        //     } else
        //         cm.sendOk("抱歉，已经领取过该奖励~");



    }
}
}