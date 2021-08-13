var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
//var tz = "#fEffect/CharacterEff/1082565/4/0#";  //兔子粉

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	//if (cm.getQuestStatus(21728) == 0) {
          //  cm.sendOk("你未习得此点歌天赋。");
           // cm.dispose();
       // }
    if (cm.getMapId() == 180000001) {
            cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
            cm.dispose();
        }  
    else if (status == 0) {
        var selStr = "               #n#r 点    #d歌    #g助    #b手#k#n\r\n#r注意#k；仅当前地图有效。目前收录歌曲；#r39#k\r\n使用此功能请下载歌曲补丁包，否则会出现游戏崩溃。\r\n";

 
		 selStr += "┏━━━━━━━━━━━━━━━━━━━━━━━┓\r\n"; 
		 selStr += "#r#L1#下完这场雨#l\r\n";
		 selStr += "#L2#再见只是陌生人#l\r\n";
		 selStr += "#L3#摆脱#l\r\n";
		 selStr += "#L4#伴虎#l\r\n";
		 selStr += "#L5#拆东墙#l\r\n";
		 selStr += "#L6#弹指一挥间#l\r\n";
		 selStr += "#L7#等到烟火清凉#l\r\n";
		 selStr += "#L8#对话老师#l\r\n";
		 selStr += "#L9#闺蜜#l\r\n";
		 selStr += "#L10#河山大好#l\r\n";
		 selStr += "#L11#胡萝卜须#l\r\n";
		 selStr += "#L12#幻胖#l\r\n";
		 selStr += "#L13#幻听#l\r\n";
		 selStr += "#L14#毁人不倦#l\r\n";
		 selStr += "#L15#降温#l\r\n";
		 selStr += "#L16#敬酒不吃#l\r\n";
		 selStr += "#L17#平行宇宙#l\r\n";
		 selStr += "#L18#七夕#l\r\n";
		 selStr += "#L19#奇谈#l\r\n";
		 selStr += "#L20#千百度#l\r\n";
		 selStr += "#L21#亲情式爱情#l\r\n";
		 selStr += "#L22#全球变冷#l\r\n";
		 selStr += "#L23#山水之间#l\r\n";
		 selStr += "#L24#摄影艺术#l\r\n";
		 selStr += "#L25#双人旁#l\r\n";
		 selStr += "#L26#微博控#l\r\n";
		 selStr += "#L27#梧桐灯#l\r\n";
		 selStr += "#L28#想象之中#l\r\n";
		 selStr += "#L29#心疼你的过去#l\r\n";
		 selStr += "#L30#雅俗共赏#l\r\n";
		 selStr += "#L31#燕归巢#l\r\n";
		 selStr += "#L32#医生#l\r\n";
		 selStr += "#L33#隐隐约约#l\r\n";
		 selStr += "#L34#有桃花#l\r\n";
		 selStr += "#L35#宇宙之大#l\r\n";
		 selStr += "#L36#早睡身体好#l\r\n";
		 selStr += "#L37#装糊涂#l\r\n";
		 selStr += "#L38#最佳歌手#l\r\n";
		 selStr += "#L39#惊鸿一面#l\r\n";

         selStr +="#k\r\n┗━━━━━━━━━━━━━━━━━━━━━━━┛\r\n"		
 
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 1:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/下完这场雨");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 下完这场雨。");
			cm.dispose();
            break; 
			case 2:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/再见只是陌生人");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 再见只是陌生人。");
			cm.dispose();
            break; 
			case 3:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/摆脱");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 摆脱");
			cm.dispose();
            break;
case 4:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/伴虎");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 伴虎");
			cm.dispose();
            break;
case 5:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/拆东墙");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 拆东墙");
			cm.dispose();
            break;
case 6:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/弹指一挥间");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 弹指一挥间");
			cm.dispose();
            break;
case 7:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/等到烟火清凉");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 等到烟火清凉");
			cm.dispose();
            break;
case 8:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/对话老师");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 对话老师");
			cm.dispose();
            break;
case 9:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/闺蜜");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 闺蜜");
			cm.dispose();
            break;
case 10:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/河山大好");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 河山大好");
			cm.dispose();
            break;
case 11:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/胡萝卜须");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 胡萝卜须");
			cm.dispose();
            break;
case 12:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/幻胖");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 幻胖");
			cm.dispose();
            break;
case 13:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/幻听");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 幻听");
			cm.dispose();
            break;
			case 14:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/毁人不倦");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 毁人不倦");
			cm.dispose();
            break;
			case 15:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/降温");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 降温");
			cm.dispose();
            break;
			case 16:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/敬酒不吃");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 敬酒不吃");
			cm.dispose();
            break;
			case 17:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/平行宇宙");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 平行宇宙");
			cm.dispose();
            break;
			case 18:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/七夕");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 七夕");
			cm.dispose();
            break;
			case 19:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/奇谈");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 奇谈");
			cm.dispose();
            break;
			case 20:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/千百度");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 千百度");
			cm.dispose();
            break;
			case 21:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/亲情式的爱情");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 亲情式的爱情");
			cm.dispose();
            break;
			case 22:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/全球变冷");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 全球变冷");
			cm.dispose();
            break;
			case 23:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/山水之间");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 山水之间");
			cm.dispose();
            break;
			case 24:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/摄影艺术");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 摄影艺术");
			cm.dispose();
            break;
			case 25:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/双人旁");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 双人旁");
			cm.dispose();
            break;
			case 26:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/微博控");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 微博控");
			cm.dispose();
            break;
			case 27:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/梧桐灯");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 梧桐灯");
			cm.dispose();
            break;
			case 28:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/想象之中");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 想象之中");
			cm.dispose();
            break;
			case 29:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/心疼你的过去");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 心疼你的过去");
			cm.dispose();
            break;
			case 30:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/ 雅俗共赏");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 -  雅俗共赏");
			cm.dispose();
            break;
			case 31:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/燕归巢");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 燕归巢");
			cm.dispose();
            break;
			case 32:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/医生");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 医生");
			cm.dispose();
            break;
			case 33:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/惊鸿一面");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 惊鸿一面");
			cm.dispose();
            break;
			case 34:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/有桃花");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 有桃花");
			cm.dispose();
            break;
			case 35:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/宇宙之大");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 宇宙之大");
			cm.dispose();
            break;
			case 36:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/早睡身体好");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 早睡身体好");
			cm.dispose();
            break;
			case 37:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/装糊涂");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 装糊涂");
			cm.dispose();
            break;
			case 38:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/最佳歌手");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 最佳歌手");
			cm.dispose();
            break;
			case 39:
			cm.setBossLog("dj");
            cm.changeMusic("Bgm00/惊鸿一面");;
			cm.worldMessage(5,""+ cm.getChar().getName() +"  点播歌曲 - 惊鸿一面");
			cm.dispose();
            break;
		
			

		}
    }
}