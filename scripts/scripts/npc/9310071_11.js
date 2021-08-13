//load("nashorn:mozilla_compat.js");
var 礼包物品 = "#v1302000#";
var x1 = "1302000,+1";// 物品ID,数量
var x2;
var x3;
var x4;
var add = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";//红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";//蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";//选择道具
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
//var 红色箭头 = "#fEffect/CharacterEff/1114000/2/0#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
//var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";

importPackage(Packages.java.util);
importPackage(Packages.client);
importPackage(Packages.server);


var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";
var item;


var jmsz = Array(
    Array(1052921, 1500, "未定义", "永久"), //蓝色金鱼全身服
	Array(1042198, 980, "未定义", "永久"), //蓝色金鱼全身服
    Array(1052919, 1260, "未定义", "永久"), //西红柿外套
    Array(1052911, 1500, "未定义", "永久"), //水晶喵喵连衣裙
    Array(1052912, 1500, "未定义", "永久"), //夹棉时尚王
    Array(1052916, 1500, "未定义", "永久"), //弓凛的花衣裳
    Array(1052899, 1500, "未定义", "永久"), //红色啵啵鼠连帽套服（黑色）
    Array(1052841, 1500, "未定义", "永久"), //甜柿服
    Array(1052842, 1500, "未定义", "永久"), //萌犬套装(裙)
    Array(1052843, 1500, "未定义", "永久"), //萌犬套装(裤)
    Array(1052844, 1500, "未定义", "永久"), //微笑玉米背带裤
    Array(1052845, 1500, "未定义", "永久"), //宽松运动服
    Array(1052782, 1500, "未定义", "永久"), //蓝色小马憨憨背带裤
    Array(1052762, 1500, "未定义", "永久"), //香蕉背带套装
    Array(1052728, 1500, "未定义", "永久"), //快乐连衣裙
    Array(1052731, 1500, "未定义", "永久"), //黑色可爱游泳服
    Array(1052697, 1500, "未定义", "永久"), //雪之女神连身裙
    Array(1052671, 1500, "未定义", "永久"), //特大号白衬衫
    Array(1052661, 1500, "未定义", "永久"), //小鸡玩偶服
    Array(1052536, 1500, "未定义", "永久"), //水手装
    Array(1052474, 1500, "未定义", "永久"), //阴阳师的礼服
    Array(1052410, 1500, "未定义", "永久"), //粉嫩花仙裙
    Array(1052370, 1500, "未定义", "永久"), //格莱特服
    Array(1052290, 1500, "未定义", "永久"), //唤灵斗师套装
    Array(1052248, 1500, "未定义", "永久"), //暗影双刀套服
    Array(1052253, 1500, "未定义", "永久"), //青草背带裤套装
    Array(1052135, 1500, "未定义", "永久"), //半人马装
    Array(1051407, 1500, "未定义", "永久"), //冒险岛学院女泳衣
    Array(1050353, 1500, "未定义", "永久"), //法式糕点服
    Array(1050338, 1500, "未定义", "永久"), //冒险岛学院男泳裤
    Array(1050319, 1500, "未定义", "永久") //秋游套装
    );


var jmwq = Array(
    Array(1702105, 1280, "未定义", "永久"), //通心钥匙
    Array(1702051, 1280, "未定义", "永久"), //灯笼
    Array(1702104, 1280, "未定义", "永久"), //巨无霸冰淇淋
    Array(1702091, 1280, "未定义", "永久"), //网球拍
    Array(1702115, 1280, "未定义", "永久"), //玫瑰花
    Array(1702217, 1280, "未定义", "永久"), //黄鸭子游泳圈
    Array(1702256, 1280, "未定义", "永久"), //风灵使者娃娃
    Array(1702257, 1280, "未定义", "永久"), //魂骑士娃娃
    Array(1702258, 1280, "未定义", "永久"), //炎术士娃娃
    Array(1702259, 1280, "未定义", "永久"), //奇袭者娃娃
    Array(1702260, 1280, "未定义", "永久"), //夜行者娃娃
    Array(1702194, 1280, "未定义", "永久"), //圣火火炬

    Array(1702186, 1280, "未定义", "永久"), //迪波
    Array(1702209, 1280, "未定义", "永久"), //驯鹿拐杖
    Array(1702204, 1280, "未定义", "永久"), //日本铁扇
    Array(1702203, 1280, "未定义", "永久"), //万圣节泰迪熊
    Array(1702150, 1280, "未定义", "永久"), //冰凌刀
    Array(1702149, 1280, "未定义", "永久"), //熔岩刀
    Array(1702157, 1280, "未定义", "永久"), //火烤棉花糖
    Array(1702161, 1280, "未定义", "永久"), //凶恶的狗狗
    Array(1702120, 1280, "未定义", "永久"), //比耶莫特的剑
    Array(1702118, 1280, "未定义", "永久"), //亚努斯的剑
    Array(1702119, 1280, "未定义", "永久"), //夏奇尔的剑
    Array(1702285, 1280, "未定义", "永久"), //蓝色蝴蝶结手提包
    Array(1702263, 1280, "未定义", "永久"), //小灰灰猫
    Array(1702298, 1280, "未定义", "永久"), //暗影刀子
    Array(1702299, 1280, "未定义", "永久"), //可爱巧克力棒
    Array(1702300, 1280, "未定义", "永久"), //电蚊拍
    Array(1702233, 1280, "未定义", "永久"), //彩虹毛笔
    Array(1702187, 1280, "未定义", "永久"), //蓝色极光
    Array(1702188, 1280, "未定义", "永久"), //粉色极光
    Array(1702193, 1280, "未定义", "永久"), //湿抹布
    Array(1702248, 1280, "未定义", "永久"), //圣诞武器
    Array(1702252, 1280, "未定义", "永久"), //鹰的飞舞
    Array(1702276, 1280, "未定义", "永久"), //天际之弓

    Array(1702362, 1280, "未定义", "永久") //我的好友品克缤

    );


var jzmp = Array(
    Array(1115010, 1500, "未定义", "永久"), //钻石聊天戒指
    Array(1115011, 1500, "未定义", "永久"), //小熊猫聊天戒指
    Array(1115015, 1500, "未定义", "永久"), //蝙蝠聊天戒指
    Array(1112174, 1500, "未定义", "永久"), //足球聊天戒指
    Array(1115003, 1500, "未定义", "永久"), //AC聊天戒指
    Array(1115004, 1500, "未定义", "永久"), //MF聊天戒指
    Array(1115005, 1500, "未定义", "永久"), //BL聊天戒指
	Array(1112100, 1500, "未定义", "永久") //BL聊天戒指
    );

var tjId = Array(
    Array(5520000, 1980, "未定义", "永久"), //暗夜娃娃皇冠
    Array(5570000, 2980, "未定义", "永久"), //魔法裙装
	Array(5590000, 1980, "未定义", "永久"), //魔法裙装
	Array(5190003, 1500, "未定义", "永久"), //范围自动捡起功能
	Array(5190004, 1500, "未定义", "永久"),//捡起无所有权道具&amp;金币技能
	Array(5170000, 1280, "未定义", "永久"),
	
	
    Array(3010163, 2000, "未定义", "10天", 60000 * 60 * 24 * 10, 1)//满月椅子
    );

var syxh = Array(//带攻击力的飞镖
    Array(2070012, 1000, "未定义", 1), //纸飞机
    Array(2070023, 2000, "未定义", 1), //火焰飞镖
	Array(2070011, 2000, "未定义", 1), //枫叶飞镖
	Array(2070024, 3000, "未定义", 1), //苦无飞镖
	Array(2070016, 4000, "未定义", 1), //水晶飞镖
	Array(2070026, 5000, "未定义", 1), //白金飞镖
	Array(2070019, 6000, "未定义", 1) //蓝色飞镖
	
    );

var tscl = Array(//特殊材料
    Array(4000151, 1000, 100), //时间门神的轴标
    Array(4001109, 1000, 5)//强化玻璃瓶
    );

var xswp = Array(
    Array(5000024, 4000, "蝙蝠怪", 60000 * 60 * 90 * 1, 1),
    Array(5000020, 4000, "小白雪人", 60000 * 60 * 90 * 1, 1),
    Array(5000035, 5000, "没有特效", 60000 * 60 * 90 * 1, 1),
    Array(5000033, 5000, "没有特效", 60000 * 60 * 90 * 1, 1),
    Array(5000032, 5000, "没有特效", 60000 * 60 * 90 * 1, 1),
    Array(5000030, 5000, "没有特效", 60000 * 60 * 90 * 1, 1),
    Array(5000029, 5000, "没有特效", 60000 * 60 * 90 * 1, 1),
    Array(5000036, 5000, "鬼灵精怪", 60000 * 60 * 90 * 1, 1),
	Array(5000046, 4980, "花蘑菇仔", 60000 * 60 * 90 * 1, 1),
    Array(5000000, 1980, "褐色小猫", 60000 * 60 * 90 * 1, 1),
    Array(5000004, 1980, "黑色小猫", 60000 * 60 * 90 * 1, 1),
    Array(5000010, 2980, "恐龙公主", 60000 * 60 * 90 * 1, 1),
    Array(5000009, 2980, "恐龙王子", 60000 * 60 * 90 * 1, 1),
    Array(5000027, 4980, "猪八戒", 60000 * 60 * 90 * 1, 1),
    Array(5000008, 4980, "熊猫", 60000 * 60 * 90 * 1, 1),
    Array(5000007, 1980, "黑小猪", 60000 * 60 * 90 * 1, 1),
    Array(5000003, 1980, "小怪猫", 60000 * 60 * 90 * 1, 1),
    Array(5000001, 1980, "褐色小狗", 60000 * 60 * 90 * 1, 1),
    Array(5000005, 1980, "白色兔子", 60000 * 60 * 90 * 1, 1),
    Array(5000023, 1980, "小企企", 60000 * 60 * 90 * 1, 1),
    Array(5000006, 1980, "雪犬", 60000 * 60 * 90 * 1, 1),
    Array(5000041, 2980, "雪娃娃", 60000 * 60 * 90 * 1, 1),
    Array(5000014, 4980, "圣诞鹿", 60000 * 60 * 90 * 1, 1),
    Array(5000016, 8888, "云豹", 60000 * 60 * 90 * 1, 1),
    Array(5000012, 2980, "小白虎", 60000 * 60 * 90 * 1, 1),
    Array(5000011, 1980, "猴子", 60000 * 60 * 90 * 1, 1),
    Array(5000002, 2980, "粉红兔子", 60000 * 60 * 90 * 1, 1)

	
   );


var jmpf = Array(
    Array(1102273, 1000, "未定义", "永久") //路西法之翼
    );


var jmmz = Array(
    Array(1004635, 1500, "未定义", "永久"), //毛线编织帽
    Array(1002960, 1500, "未定义", "永久"), //暗夜娃娃皇冠
    Array(1004639, 1500, "未定义", "永久"), //北极罩帽
    Array(1004665, 1500, "未定义", "永久"), //暖绒兔兔帽
    Array(1004638, 1500, "未定义", "永久"), //茅山道士帽
    Array(1004592, 1500, "未定义", "永久"), //紫色时间
    Array(1004592, 1500, "未定义", "永久"), //粉色时间
    Array(1004589, 1500, "未定义", "永久"), //侠盗猫眼罩
    Array(1004570, 1500, "未定义", "永久"), //黑色海魂帽
    Array(1004571, 1500, "未定义", "永久"), //海贼团贝雷帽
    Array(1004541, 1500, "未定义", "永久"), //茶会大蝴蝶结
    Array(1004540, 1500, "未定义", "永久"), //奥尔卡的睡帽
    Array(1004506, 1500, "未定义", "永久"), //白色兔耳发带
    Array(1004504, 1500, "未定义", "永久"), //贵族花纹帽子
    Array(1004499, 1500, "未定义", "永久"), //蓝色金鱼帽子
    Array(1004500, 1500, "未定义", "永久"), //蓝色金鱼帽子
    Array(1004491, 1500, "未定义", "永久"), //西红柿帽子
    Array(1004481, 1500, "未定义", "永久"), //反抗者护目镜
    Array(1004480, 1500, "未定义", "永久"), //小淘气的飞行帽
    Array(1004470, 1500, "未定义", "永久"), //毛绒雷锋帽
    Array(1004469, 1500, "未定义", "永久"), //爱情宣言
    Array(1004463, 1500, "未定义", "永久"), //方块兔帽子
    Array(1004448, 1500, "未定义", "永久"), //黑色啵啵鼠帽
    Array(1004409, 1500, "未定义", "永久"), //松鼠的休闲帽
    Array(1004403, 1500, "未定义", "永久"), //嘻哈兔子
    Array(1004405, 1500, "未定义", "永久"), //睡虎休闲帽
    Array(1004397, 1500, "未定义", "永久"), //清扫头巾
    Array(1004336, 1500, "未定义", "永久"), //暴走斯乌假发
    Array(1004330, 1500, "未定义", "永久"), //奢华羊绒小礼帽
    Array(1004329, 1500, "未定义", "永久"), //蓝色棒球帽发夹
    Array(1004324, 1500, "未定义", "永久"), //防毒面罩
    Array(1004294, 1500, "未定义", "永久"), //甜柿帽
    Array(1004295, 1500, "未定义", "永久"), //音乐飞天小鸡帽
    Array(1004296, 1500, "未定义", "永久"), //萌动飞天小鸡帽
    Array(1004298, 1500, "未定义", "永久"), //泰迪萌犬帽(白)
    Array(1004299, 1500, "未定义", "永久"), //泰迪萌犬帽(白)
    Array(1004275, 1500, "未定义", "永久"), //幸运帽
    Array(1004269, 1500, "未定义", "永久"), //苹果蒂风情帽
    Array(1004239, 1500, "未定义", "永久"), //桃太郎帽子
    Array(1004212, 1500, "未定义", "永久"), //晶莹精致丝带
    Array(1004211, 1500, "未定义", "永久"), //哈尼绒绒耳
    Array(1004202, 1500, "未定义", "永久"), //隐武士战盔
    Array(1004200, 1500, "未定义", "永久"), //夏日兔草帽
    Array(1004198, 1500, "未定义", "永久"), //太极发箍
    Array(1004196, 1500, "未定义", "永久"), //风车头箍
    Array(1004194, 1500, "未定义", "永久"), //蝴蝶结贝雷帽
    Array(1004169, 1500, "未定义", "永久"), //美味荷包蛋帽
    Array(1004171, 1500, "未定义", "永久"), //旋转木马帽
    Array(1004163, 1500, "未定义", "永久"), //爱丽丝表丝带
    Array(1004158, 1500, "未定义", "永久"), //鼠鼠派对发箍
    Array(1004025, 1500, "未定义", "永久"), //绿猫猫帽子
    Array(1004033, 1500, "未定义", "永久"), //红猫猫帽子
    Array(1004003, 1500, "未定义", "永久"), //粉红猫耳套头帽
    Array(1003968, 1500, "未定义", "永久"), //巧克力绵羊玩偶帽
    Array(1003951, 1500, "未定义", "永久"), //欧黛特头箍
    Array(1003952, 1500, "未定义", "永久"), //欧黛特头箍
    Array(1003937, 1500, "未定义", "永久"), //浪漫斗笠
    Array(1003917, 1500, "未定义", "永久"), //粉红太阳镜帽
    Array(1003900, 1500, "未定义", "永久"), //蓝色桃心透明帽子
    Array(1003859, 1500, "未定义", "永久"), //满天星普赛克
    Array(1003847, 1500, "未定义", "永久"), //鬼剑士假发
    Array(1003831, 1500, "未定义", "永久"), //卷卷绵羊发卡
    Array(1003777, 1500, "未定义", "永久"), //伶俐猫咪斗篷
    Array(1003778, 1500, "未定义", "永久"), //可爱猫咪斗篷
    Array(1003779, 1500, "未定义", "永久"), //爱丽丝兔兔帽
    Array(1003763, 1500, "未定义", "永久"), //黑色之翼首领帽
    Array(1003710, 1500, "未定义", "永久"), //[MS折扣]怪医黑杰克帽
    Array(1003417, 1500, "未定义", "永久"), //恐龙帽子
    Array(1003203, 1500, "未定义", "永久"), //小红帽
    Array(1003149, 1500, "未定义", "永久"), //洛比尔兔子斗篷
    Array(1003146, 1500, "未定义", "永久"), //蕾丝蝴蝶结发箍
    Array(1003147, 1500, "未定义", "永久"), //天蓝女仆头饰
    Array(1003131, 1500, "未定义", "永久"), //黑色精致丝带
    Array(1003122, 1500, "未定义", "永久"), //黄色兔兔巾
    Array(1003123, 1500, "未定义", "永久"), //黄色兔兔巾
    Array(1003109, 1500, "未定义", "永久"), //皇家彩虹斗篷
    Array(1002976, 1500, "未定义", "永久"), //女仆头饰
    Array(1002721, 1500, "未定义", "永久"), //狸毛护耳
    Array(1002704, 1500, "未定义", "永久"), //黄独眼怪婴
    Array(1002691, 1500, "未定义", "永久"), //半人马假发(棕色)
    Array(1002695, 1500, "未定义", "永久"), //幽灵帽
    Array(1004636, 1500, "未定义", "永久"), //香蕉郊游帽

    Array(1004602, 1500, "未定义", "永久"), //农夫的瑰宝
    Array(1004601, 1500, "未定义", "永久"), //小企鹅帽子
    Array(1004589, 1500, "未定义", "永久"), //侠盗猫眼罩
    Array(1004570, 1500, "未定义", "永久"), //黑色海魂帽
    Array(1004571, 1500, "未定义", "永久"), //海贼团贝雷帽
    Array(1004568, 1500, "未定义", "永久"), //呆萌鼠鼠帽
    Array(1004543, 1500, "未定义", "永久"), //复古头巾
 
    
    Array(1004491, 1500, "未定义", "永久"), //西红柿

    Array(1004506, 1500, "未定义", "永久"), //黑猫
    Array(1004203, 1500, "未定义", "永久"), //喵星人的秘密
    Array(1000051, 1500, "未定义", "永久") //精灵精神发带
    );



var jmsp = Array(
    Array(1012501, 1500, "未定义", "永久"), //烈焰之瞳(蓝)
    Array(1022184, 1500, "未定义", "永久"), //烈焰之瞳(蓝)
    Array(1022183, 1500, "未定义", "永久"), //烈焰之瞳(红) 
    Array(1012450, 1500, "未定义", "永久"), //巧克力彩豆曲奇
    Array(1012412, 1500, "未定义", "永久"), //血泪 
    Array(1012099, 1500, "未定义", "永久"), //蓝色腮红 
    Array(1012253, 1500, "未定义", "永久"), //心跳唇彩
    Array(1012160, 1500, "未定义", "永久"), //烈焰红唇
    Array(1012208, 1500, "未定义", "永久"), //害羞了 
    Array(1012131, 1500, "未定义", "永久"), //好白的牙
    Array(1012165, 1500, "未定义", "永久"), //小丑鼻子
    Array(1012176, 1500, "未定义", "永久"), //娃娃脸
    Array(1012179, 1500, "未定义", "永久"), //鹿鼻子
    Array(1022057, 1500, "未定义", "永久"), //POP眼镜
    Array(1022064, 1500, "未定义", "永久"), //时尚大太阳镜
    Array(1022063, 1500, "未定义", "永久"), //无框小圆眼镜
    Array(1022104, 1500, "未定义", "永久"), //3D炫彩眼镜
    Array(1022102, 1500, "未定义", "永久"), //LED炫光眼镜
    Array(1022108, 1500, "未定义", "永久"), //柠檬黄潮流眼镜
    Array(1022109, 1500, "未定义", "永久"), //玫瑰红潮流眼镜
    Array(1022110, 1500, "未定义", "永久"), //非主流墨镜
    Array(1022122, 1500, "未定义", "永久"), //DJ眼镜
    Array(1032029, 1500, "未定义", "永久"), //925银耳环
    Array(1032181, 1500, "未定义", "永久"), //杜鹃花耳环
    Array(1032034, 1500, "未定义", "永久"), //可乐耳环
    Array(1032094, 1500, "未定义", "永久"), //胡萝卜耳环
    Array(1032088, 1500, "未定义", "永久"), //体力猎犬耳环
    Array(1032145, 1500, "未定义", "永久")//螃蟹耳环
    );


var txwq = Array(
    Array(1702377, 2280, "未定义", "永久"), //喜欢草莓
    Array(1702388, 2280, "未定义", "永久"), //熊宝宝是个能巧匠
	Array(1702534, 2860, "未定义", "永久"), //嘟嘟奶嘴
	Array(1702505, 2860, "未定义", "永久"), //熊猫
    Array(1702389, 2860, "未定义", "永久"), //飞飞蓝鸟
    Array(1702400, 2860, "未定义", "永久"), //斯乌的兔子玩偶
    Array(1702401, 2860, "未定义", "永久"), //兔子萝卜武器
    Array(1702402, 2860, "未定义", "永久"), //高尔夫球棒
    Array(1702403, 2260, "未定义", "永久"), //侦探的巨型放大镜
    Array(1702405, 2260, "未定义", "永久"), //星光之心魔棒
    Array(1702408, 2260, "未定义", "永久"), //纪念日专用武器
    Array(1702415, 2260, "未定义", "永久"), //睡梦绵羊枕头
    Array(1702426, 2260, "未定义", "永久"), //暴风领主
    Array(1702446, 2260, "未定义", "永久"), //和海懒一起
    Array(1702473, 2260, "未定义", "永久"), //岩炎执行者
    Array(1702521, 2680, "未定义", "永久"), //青炎
    Array(1702492, 2680, "未定义", "永久"), //春节灯笼
    Array(1702501, 2680, "未定义", "永久"), //玫瑰花舞
    Array(1702510, 2680, "未定义", "永久"), //兔兔熊手电筒
    Array(1702524, 2680, "未定义", "永久"), //红红番茄
    Array(1702535, 2680, "未定义", "永久"), //呼啦呼啦小企鹅
    Array(1702538, 2240, "未定义", "永久"), //露珠武器
    Array(1702565, 2980, "未定义", "永久"), //死亡之刃
    Array(1702567, 2268, "未定义", "永久"), //漂浮小虎
    Array(1702595, 2980, "未定义", "永久"), //薄荷喵下午茶
    Array(1702597, 2980, "未定义", "永久"), //彩虹贝壳武器
    Array(1702397, 2980, "未定义", "永久")//溢彩银河
    );

var gfs = Array(
    Array(1003393, 3000, "未定义", "永久"), //帝国伯爵冠
    Array(1102381, 3000, "未定义", "永久"), //皇家公爵翅膀
    Array(1032233, 3000, "未定义", "永久"), //粉色桃心耳环
    Array(1112928, 3000, "未定义", "永久"), //粉色流星戒指
    Array(1112924, 3000, "未定义", "永久"), //黄色流星戒指
    Array(1112930, 3000, "未定义", "永久"), //西红柿效果
    Array(1112937, 3000, "未定义", "永久"), //催眠曲戒指
    Array(1112916, 3000, "未定义", "永久"), //寂寞单身戒指
    Array(5010069, 3000, "未定义", "永久"), //死神之翼
    Array(1052731, 3000, "未定义", "永久"), //黑色可爱游泳服
    Array(1702278, 3000, "未定义", "永久"), //枫之武器
    Array(1702289, 3000, "未定义", "永久"), //皇家海军旗帜
    Array(1702155, 3000, "未定义", "永久") //绚丽彩虹

    );



var ns = Array(
    
    Array(1052410, 3000, "未定义", "永久"), //粉嫩花仙裙
    Array(1051296, 3000, "未定义", "永久"), //玩世不恭裙装
    Array(1051295, 3000, "未定义", "永久"), //魔法裙装
    Array(1051228, 3000, "未定义", "永久"), //粉红色雪花休闲装
    Array(1041189, 3000, "未定义", "永久"), //女牛仔衬衫
    Array(1001076, 3000, "未定义", "永久"), //樱桃雪水晶
    Array(1001064, 3000, "未定义", "永久"), //闪耀Girl帽子
    Array(1001083, 3000, "未定义", "永久"), //爆莉萌天使发卡
    Array(1001044, 3000, "未定义", "永久"), //绿色婚纱头纱
    Array(1001061, 3000, "未定义", "永久"), //伊丽莎白假发(女)
    Array(1001068, 3000, "未定义", "永久"), //军团长假发-女
    Array(1001069, 3000, "未定义", "永久"), //精灵王假发-女
    Array(1001094, 3000, "未定义", "永久"), //蓝色蕾丝头巾
    Array(1001089, 3000, "未定义", "永久"), //兔子胡萝卜绒线帽
    Array(1001085, 3000, "未定义", "永久"), //粉蝴蝶发卡
    Array(1061148, 3000, "未定义", "永久"), //巨星粉色短裙
    Array(1061001, 3000, "未定义", "永久"), //蓝超短裙
    Array(1061007, 3000, "未定义", "永久"), //红超短裙 
    Array(1061067, 3000, "未定义", "永久") //热裤
    );

var qltz = Array(

    Array(1061204, 1000, "未定义", "永久"), //可爱棒球服（女）
    Array(1052728, 3000, "未定义", "永久"), //快乐连衣裙
    Array(1000072, 3000, "未定义", "永久"), //蓝色钱包君（男）
    Array(1001095, 3000, "未定义", "永久"), //粉色钱包君（女）
    Array(1042213, 3000, "未定义", "永久"), //粉色雪花卫衣
    Array(1042212, 3000, "未定义", "永久"), //蓝色点点外套

    Array(1000070, 3000, "未定义", "永久"), //贵族骑手帽(男)
    Array(1001093, 3000, "未定义", "永久") //贵族骑手帽（女）

    );

var stxz = Array(

    Array(1082503, 1500, "未定义", "永久"), //圣洁天使手镯
    Array(1072662, 1500, "未定义", "永久"), //天使闪耀
    Array(1072663, 1500, "未定义", "永久") //蓝色天使鞋

    );


var status = 0;
var xx = -1;
var jiage = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}


function action(mode, type, selection) {
    if (mode == -1) {
        cm.sendOk("#b好的,下次再见.");
        cm.dispose();
    } else {

        if (mode == 0) {
            cm.sendOk("#b好的,下次再见.");
            cm.dispose();
            return;
        }

        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        //---------------------------------------------------------------------------------

        if (status == 0) {

            var add = "#r吃货冒险岛GM人员#k,粉嘟嘟,\r\n\r\n";

            add += "   您当前位置:#b商城首页#k#r\r\n\r\n\r\n";

            add += "" + sss + "\r\n   ";

            add += "#L0##e#r点卷商城#l ";

            cm.sendSimple(add);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        } else if (status == 1) {

            if (selection == 0) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#b";
				
				add += "#L99#点装转移(点装转移)#l \r\n";

                add += "#L1#精美时装#l ";

                add += "#L2#戒指名片#l ";
				
				

                //add += "#L4##r稀有飞镖#l ";

                add += "#L3##b本周推荐#l #L5#宠物系列(永久)#l \r\n";

              //  add += "";

                add += "#L6#精美武器#l ";

                add += "#L7#精美披风#l ";

                add += "#L10##r特效武器#l#k#b \r\n";

                add += "#L8#精美帽子#l ";

                add += "#L9#精美饰品#l ";

                add += "#L13#情侣套装#l \r\n";

                add += "#L12#我是女神#l ";

                add += "#L11#我是土豪#l ";

                add += "#L14#手套鞋子#l \r\n";

                cm.sendSimple(add);

            }

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        } else if (status == 2) {
			
			if (selection == 99) {
				
				cm.进入商城2();
				cm.dispose();
       } 

            if (selection == 1) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美时装#k\r\n\r\n";

                add += "   当前点券余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jmsz.length; i++) {

                    add += "\r\n#L" + i + "##v" + jmsz[i][0] + "##z" + jmsz[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点券:#r " + jmsz[i][1] + " #d    使用期限:#r " + jmsz[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 1

            } else if (selection == 2) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美名片#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jzmp.length; i++) {

                    add += "\r\n#L" + i + "##v" + jzmp[i][0] + "##z" + jzmp[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + jzmp[i][1] + " #d    使用期限:#r " + jzmp[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 2


            } else if (selection == 3) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>本周推荐#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "\r\n#b";

                for (var i = 0; i < tjId.length; i++) {

                    add += "\r\n#L" + i + "##v" + tjId[i][0] + "##z" + tjId[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + tjId[i][1] + " #d    使用期限:#r " + tjId[i][3] + "#k#b";


                }


                cm.sendSimple(add);

                xx = 3;

            } else if (selection == 4) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>使用消耗#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "\r\n#b";

                for (var i = 0; i < syxh.length; i++) {

                    add += "\r\n#L" + i + "##v" + syxh[i][0] + "##z" + syxh[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + syxh[i][1] + " #d    购买数量:#r " + syxh[i][3] + "#k#b";


                }


                cm.sendSimple(add);

                xx = 4;

            } else if (selection == 5) {
                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>宠物系列#k\r\n\r\n";
                add += "   宠物内容如果有加BUFF.增益效果.加攻击力的一律是无效技能！\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "\r\n#b";

                for (var i = 0; i < xswp.length; i++) {

                    add += "\r\n#L" + i + "##v" + xswp[i][0] + "##z" + xswp[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + xswp[i][1] + " #d    购买数量:#r " + xswp[i][2] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 5


            } else if (selection == 6) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美武器#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jmwq.length; i++) {

                    add += "\r\n#L" + i + "##v" + jmwq[i][0] + "##z" + jmwq[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + jmwq[i][1] + " #d    使用期限:#r " + jmwq[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 6


            } else if (selection == 7) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美披风#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jmpf.length; i++) {

                    add += "\r\n#L" + i + "##v" + jmpf[i][0] + "##z" + jmpf[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + jmpf[i][1] + " #d    使用期限:#r " + jmpf[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 7


            } else if (selection == 8) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美帽子#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jmmz.length; i++) {

                    add += "\r\n#L" + i + "##v" + jmmz[i][0] + "##z" + jmmz[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + jmmz[i][1] + " #d    使用期限:#r " + jmmz[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 8

            } else if (selection == 9) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美饰品#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < jmsp.length; i++) {

                    add += "\r\n#L" + i + "##v" + jmsp[i][0] + "##z" + jmsp[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + jmsp[i][1] + " #d    使用期限:#r " + jmsp[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 9


            } else if (selection == 10) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>特效武器#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < txwq.length; i++) {

                    add += "\r\n#L" + i + "##v" + txwq[i][0] + "##z" + txwq[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + txwq[i][1] + " #d    使用期限:#r " + txwq[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 10

            } else if (selection == 11) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>我是高富帅#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < gfs.length; i++) {

                    add += "\r\n#L" + i + "##v" + gfs[i][0] + "##z" + gfs[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + gfs[i][1] + " #d    使用期限:#r " + gfs[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 11


            } else if (selection == 12) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>我是女神#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < ns.length; i++) {

                    add += "\r\n#L" + i + "##v" + ns[i][0] + "##z" + ns[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + ns[i][1] + " #d    使用期限:#r " + ns[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 12


            } else if (selection == 13) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>情侣套装#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < qltz.length; i++) {

                    add += "\r\n#L" + i + "##v" + qltz[i][0] + "##z" + qltz[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + qltz[i][1] + " #d    使用期限:#r " + qltz[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 13


            } else if (selection == 14) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>手套鞋子#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n#b";

                for (var i = 0; i < stxz.length; i++) {

                    add += "\r\n#L" + i + "##v" + stxz[i][0] + "##z" + stxz[i][0] + "##l#d\r\n\r\n		";

                    add += "需要点卷:#r " + stxz[i][1] + " #d    使用期限:#r " + stxz[i][3] + "#k#b";

                }

                cm.sendSimple(add);

                xx = 14

            }

        ////////////////////////////////////////////////////////////////////////////////////

        } else if (status == 3) {

            if (xx == 1) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美时装#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jmsz[selection][0] + "# #z" + jmsz[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jmsz[selection][1] + " #d    使用期限:#r " + jmsz[selection][3] + "\r\n                              ";

                add += "   #L1#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 2) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美名片#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jzmp[selection][0] + "# #z" + jzmp[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jzmp[selection][1] + " #d    使用期限:#r " + jzmp[selection][3] + "\r\n                              ";

                add += "   #L2#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 3) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>商城更新#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "\r\n\r\n#d";

                add += "   物品:#v" + tjId[selection][0] + "# #z" + tjId[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r" + tjId[selection][1] + " #d    使用期限:#r " + tjId[selection][3] + "\r\n                              ";

                add += "   #L3#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;


            }

            if (xx == 4) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>使用消耗#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "\r\n\r\n#d";

                add += "   物品:#v" + syxh[selection][0] + "# #z" + syxh[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r" + syxh[selection][1] + " #d    购买数量:#r " + syxh[selection][3] + "\r\n                              ";

                add += "   #L4#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;


            }

            if (xx == 5) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>限时物品#k\r\n\r\n";

                add += "   物品:#v" + xswp[selection][0] + "# #z" + xswp[selection][0] + "##k\r\n\r\n";

                add += "   需要点卷:#r" + xswp[selection][1] + " #d        使用期限:#r " + xswp[selection][2] + "\r\n";

                add += "   #L5#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;


            }


            if (xx == 6) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美武器#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jmwq[selection][0] + "# #z" + jmwq[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jmwq[selection][1] + " #d    使用期限:#r " + jmwq[selection][3] + "\r\n                              ";

                add += "   #L6#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 7) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美披风#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jmpf[selection][0] + "# #z" + jmpf[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jmpf[selection][1] + " #d    使用期限:#r " + jmpf[selection][3] + "\r\n                              ";

                add += "   #L7#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }


            if (xx == 8) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美帽子#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jmmz[selection][0] + "# #z" + jmmz[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jmmz[selection][1] + " #d    使用期限:#r " + jmmz[selection][3] + "\r\n                              ";

                add += "   #L8#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 9) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>精美饰品#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + jmsp[selection][0] + "# #z" + jmsp[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + jmsp[selection][1] + " #d    使用期限:#r " + jmsp[selection][3] + "\r\n                              ";

                add += "   #L9#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 10) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>特效武器#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + txwq[selection][0] + "# #z" + txwq[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + txwq[selection][1] + " #d    使用期限:#r " + txwq[selection][3] + "\r\n                              ";

                add += "   #L10#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 11) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>我要当高富帅#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + gfs[selection][0] + "# #z" + gfs[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + gfs[selection][1] + " #d    使用期限:#r " + gfs[selection][3] + "\r\n                              ";

                add += "   #L11#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 12) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>我是女神#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + ns[selection][0] + "# #z" + ns[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + ns[selection][1] + " #d    使用期限:#r " + ns[selection][3] + "\r\n                              ";

                add += "   #L12#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 13) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>情侣套装#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + qltz[selection][0] + "# #z" + qltz[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + qltz[selection][1] + " #d    使用期限:#r " + qltz[selection][3] + "\r\n                              ";

                add += "   #L13#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

            if (xx == 14) {

                var add = "欢迎来到#r#r吃货冒险岛#b下面是本服的游戏商城区.\r\n\r\n";

                add += "   您当前位置:#b商城首页>>点卷商城>>手套鞋子#k\r\n\r\n";

                add += "   当前点卷余额:#r" + cm.getPlayer().getCSPoints(1) + "#k\r\n\r\n#d";

                add += "   物品:#v" + stxz[selection][0] + "# #z" + stxz[selection][0] + "#\r\n\r\n";

                add += "   需要点卷:#r " + stxz[selection][1] + " #d    使用期限:#r " + stxz[selection][3] + "\r\n                              ";

                add += "   #L14#立即购买#l";

                cm.sendSimple(add);

                jiage = selection;

            }

        ///////////////////////////////////////////////////////////////////////////////////////////

        } else if (status == 4) {


            if (selection == 1) {
                if (cm.getPlayer().getCSPoints(1) >= jmsz[jiage][1]) {
                    //cm.gainItem(jmsz[jiage][0], 1);
					item = cm.gainGachaponItem(jmsz[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jmsz[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 2) {
                if (cm.getPlayer().getCSPoints(1) >= jzmp[jiage][1]) {
					item = cm.gainGachaponItem(jzmp[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jzmp[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 3) {
                if (cm.getPlayer().getCSPoints(1) >= tjId[jiage][1]) {
                    //cm.gainItem(tjId[jiage][0], 1);
					item = cm.gainGachaponItem(tjId[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -tjId[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.getPlayer().getCSPoints(1) >= syxh[jiage][1]) {
					item = cm.gainGachaponItem(syxh[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -syxh[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请登陆网站冲值.");
                    cm.dispose();
                }


            } else if (selection == 5) {
                if (cm.getPlayer().getCSPoints(1) >= xswp[jiage][1]) {
                    cm.sendOk("#b购买成功,请查看背包.");
                    cm.getPlayer().modifyCSPoints(1, -xswp[jiage][1]);
					cm.gainPet(xswp[jiage][0],xswp[jiage][2],1,100,100,90*24);
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请登陆网站冲值.");
                    cm.dispose();
                }

            } else if (selection == 6) {
                if (cm.getPlayer().getCSPoints(1) >= jmwq[jiage][1]) {
					item = cm.gainGachaponItem(jmwq[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jmwq[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 7) {
                if (cm.getPlayer().getCSPoints(1) >= jmpf[jiage][1]) {
					item = cm.gainGachaponItem(jmpf[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jmpf[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }

            } else if (selection == 8) {
                if (cm.getPlayer().getCSPoints(1) >= jmmz[jiage][1]) {
					item = cm.gainGachaponItem(jmmz[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jmmz[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }

            } else if (selection == 9) {
                if (cm.getPlayer().getCSPoints(1) >= jmsp[jiage][1]) {
					item = cm.gainGachaponItem(jmsp[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -jmsp[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 10) {
                if (cm.getPlayer().getCSPoints(1) >= txwq[jiage][1]) {
					item = cm.gainGachaponItem(txwq[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -txwq[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 11) {
                if (cm.getPlayer().getCSPoints(1) >= gfs[jiage][1]) {
					item = cm.gainGachaponItem(gfs[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -gfs[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }


            } else if (selection == 12) {
                if (cm.getPlayer().getCSPoints(1) >= ns[jiage][1]) {
					item = cm.gainGachaponItem(ns[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -ns[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }

            } else if (selection == 13) {
                if (cm.getPlayer().getCSPoints(1) >= qltz[jiage][1]) {
					item = cm.gainGachaponItem(qltz[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -qltz[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }

            } else if (selection == 14) {
                if (cm.getPlayer().getCSPoints(1) >= stxz[jiage][1]) {
					item = cm.gainGachaponItem(stxz[jiage][0], 1, "现金商城", 0);
					if (item != -1) {
						cm.sendOk("你获得了 #b#t" + item + "##k " + 1 + "个。");
						cm.getPlayer().modifyCSPoints(1, -stxz[jiage][1]);
					} else {
						cm.sendOk("请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
					}
                    cm.dispose();
                    
                } else {
                    cm.sendOk("#b您没有足够的点卷进行购买,请充值.");
                    cm.dispose();
                }
            }
        }
    }

}
//load("nashorn:mozilla_compat.js");



