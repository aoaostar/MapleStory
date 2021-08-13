//Gachaphon

importPackage(net.sf.odinms.client);



var status = 0;
var chance1 = Math.floor(Math.random()*200+1);
var chance2 = Math.floor(Math.random()*50);
var chance3 = (Math.floor(Math.random()*20)+1);
var chance4 = Math.floor(Math.random()*2+1);
var itemchance = chance1 + chance2 + chance3 * chance4;
var itemamount = Math.floor(Math.random()*50+1);


function start() {
	status = -1;
	action(1, 0, 0);
}


function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (status >= 2 && mode == 0) {
			cm.sendOk("下次再见!");
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
				cm.sendNext("我是豆豆交换机.\r\n我是一个特殊的抽奖机器，我无法告诉你用什么可以在我这里抽奖，如果你猜到的话，\r\n我可以给你特殊的奖励哦");
			}
		else if (status == 1) {
			if (cm.haveItem(5220000)) {
			cm.sendYesNo("你确定你拿到正确的抽奖凭证了么？是否尝试一下开始抽奖？");
			}
			else if (!cm.haveItem(5220000)) {
			cm.sendOk("你并没有带来我想要的东西！");
			cm.dispose();
			}
		}
		else if (status == 2) {
			cm.gainItem(5220000, -1);
			if ((itemchance >= 1) && (itemchance <= 20)) {
			cm.gainItem(2000004, itemamount);
			}
			else if ((itemchance >= 21) && (itemchance <= 40)) {
			cm.gainItem(2020012, itemamount);
			}
			else if ((itemchance >= 41) && (itemchance <= 50)) {
			cm.gainItem(2000005, itemamount);
			}
			else if ((itemchance >= 51) && (itemchance <= 60)) {
			cm.gainItem(2030007, itemamount);
			}
			else if ((itemchance >= 61) && (itemchance <= 70)) {
			cm.gainItem(2022027, itemamount);
			}
			else if (itemchance == 71) {
			cm.gainItem(2040001, 1);
			}
			else if (itemchance == 72) {
			cm.gainItem(2041002, 1);
			}
			else if (itemchance == 73) {
			cm.gainItem(2040805, 1);
			}
			else if (itemchance == 74) {
			cm.gainItem(2040702, 1);
			}
			else if (itemchance == 75) {
			cm.gainItem(2043802, 1);
			}
			else if (itemchance == 76) {
			cm.gainItem(2040402, 1);
			}
			else if (itemchance == 77) {
			cm.gainItem(2043702, 1);
			}
			else if (itemchance == 78) {
			cm.gainItem(1302022, 1);
			}
			else if (itemchance == 79) {
			cm.gainItem(1322021, 1);
			}	
			else if (itemchance == 80) {
			cm.gainItem(1322026, 1);
			}	
			else if (itemchance == 81) {
			cm.gainItem(1302026, 1);
			}
			else if (itemchance == 82) {
			cm.gainItem(1442017, 1);
			}
			else if (itemchance == 83) {
			cm.gainItem(1082147, 1);
			}	
			else if (itemchance == 84) {
			cm.gainItem(1102043, 1);
			}
			else if (itemchance == 85) {
			cm.gainItem(1442016, 1);
			}
			else if (itemchance == 86) {
			cm.gainItem(1402012, 1);
			}
			else if (itemchance == 87) {
			cm.gainItem(1302027, 1);
			}	
			else if (itemchance == 88) {
			cm.gainItem(1322027, 1);
			}
			else if (itemchance == 89) {
			cm.gainItem(1322025, 1);
			}
			else if (itemchance == 90) {
			cm.gainItem(1312012, 1);
			}
			else if (itemchance == 91) {
			cm.gainItem(1062000, 1);
			}
			else if (itemchance == 92) {
			cm.gainItem(1332020, 1);
			}
			else if (itemchance == 93) {
			cm.gainItem(1302028, 1);
			}
			else if (itemchance == 94) {
			cm.gainItem(1372002, 1);
			}
			else if (itemchance == 95) {
			cm.gainItem(1002033, 1);
			}
			else if (itemchance == 96) {
			cm.gainItem(1092022, 1);
			}
			else if (itemchance == 97) {
			cm.gainItem(1302021, 1);
			}
			else if (itemchance == 98) {
			cm.gainItem(1102041, 1);
			}
			else if (itemchance == 99) {
			cm.gainItem(1102042, 1);
			}
			else if (itemchance == 100) {
			cm.gainItem(1322024, 1);
			}
			else if (itemchance == 101) {
			cm.gainItem(1082148, 1);
			}
			else if (itemchance == 102) {
			cm.gainItem(1002012, 1);
			}
			else if (itemchance == 103) {
			cm.gainItem(1322012, 1);
			}
			else if (itemchance == 104) {
			cm.gainItem(1322022, 1);
			}
			else if (itemchance == 105) {
			cm.gainItem(1002020, 1);
			}
			else if (itemchance == 106) {
			cm.gainItem(1302013, 1);
			}
			else if (itemchance == 107) {
			cm.gainItem(1082146, 1);
			}
			else if (itemchance == 108) {
			cm.gainItem(1442014, 1);
			}
			else if (itemchance == 109) {
			cm.gainItem(1002096, 1);
			}
			else if (itemchance == 110) {
			cm.gainItem(1302017, 1);
			}
			else if (itemchance == 111) {
			cm.gainItem(1442012, 1);
			}
			else if (itemchance == 112) {
			cm.gainItem(1322010, 1);
			}
			else if (itemchance == 113) {
			cm.gainItem(1442011, 1);
			}
			else if (itemchance == 114) {
			cm.gainItem(1442018, 1);
			}
			else if (itemchance == 115) {
			cm.gainItem(1092011, 1);
			}
			else if (itemchance == 116) {
			cm.gainItem(1092014, 1);
			}
			else if (itemchance == 117) {
			cm.gainItem(1302003, 1);
			}
			else if (itemchance == 118) {
			cm.gainItem(1432001, 1);
			}
			else if (itemchance == 119) {
			cm.gainItem(1312011, 1);
			}
			else if (itemchance == 120) {
			cm.gainItem(1002088, 1);
			}
			else if (itemchance == 121) {
			cm.gainItem(1041020, 1);
			}
			else if (itemchance == 122) {
			cm.gainItem(1322015, 1);
			}
			else if (itemchance == 123) {
			cm.gainItem(1442004, 1);
			}
			else if (itemchance == 124) {
			cm.gainItem(1422008, 1);
			}
			else if (itemchance == 125) {
			cm.gainItem(1302056, 1);
			}
			else if (itemchance == 126) {
			cm.gainItem(1432000, 1);
			}
			else if (itemchance == 127) {
			cm.gainItem(1382001, 1);
			}
			else if (itemchance == 128) {
			cm.gainItem(1041053, 1);
			}
			else if (itemchance == 129) {
			cm.gainItem(1060014, 1);
			}
			else if (itemchance == 130) {
			cm.gainItem(1050053, 1);
			}
			else if (itemchance == 131) {
			cm.gainItem(1051032, 1);
			}
			else if (itemchance == 132) {
			cm.gainItem(1050073, 1);
			}
			else if (itemchance == 133) {
			cm.gainItem(1061036, 1);
			}
			else if (itemchance == 134) {
			cm.gainItem(1002253, 1);
			}
			else if (itemchance == 135) {
			cm.gainItem(1002034, 1);
			}
			else if (itemchance == 136) {
			cm.gainItem(1051025, 1);
			}
			else if (itemchance == 137) {
			cm.gainItem(1050067, 1);
			}
			else if (itemchance == 138) {
			cm.gainItem(1051052, 1);
			}
			else if (itemchance == 139) {
			cm.gainItem(1002072, 1);
			}
			else if (itemchance == 140) {
			cm.gainItem(1002144, 1);
			}
			else if (itemchance == 141) { 
			cm.gainItem(1051054, 1);
			}
			else if (itemchance == 142) { 
			cm.gainItem(1050069, 1);
			}
			else if (itemchance == 143) { 
			cm.gainItem(1372007, 1);
			}
			else if (itemchance == 144) { 
			cm.gainItem(1050056, 1);
			}
			else if (itemchance == 145) { 
			cm.gainItem(1050074, 1);
			}
			else if (itemchance == 146) { 
			cm.gainItem(1002254, 1);
			}
			else if (itemchance == 147) {
			cm.gainItem(1002274, 1);
			}
			else if (itemchance == 148) { 
			cm.gainItem(1002218, 1);
			}
			else if (itemchance == 149) { 
			cm.gainItem(1051055, 1);
			}
			else if (itemchance == 150) { 
			cm.gainItem(1382010, 1);
			}
			else if (itemchance == 151) { 
			cm.gainItem(1002246, 1);
			}
			else if (itemchance == 152) { 
			cm.gainItem(1050039, 1);
			}
			else if (itemchance == 153) { 
			cm.gainItem(1382007, 1);
			}
			else if (itemchance == 154) { 
			cm.gainItem(1372000, 1);
			}
			else if (itemchance == 155) { 
			cm.gainItem(1002013, 1);
			}
			else if (itemchance == 156) { 
			cm.gainItem(1050072, 1);
			}
			else if (itemchance == 157) { 
			cm.gainItem(1002036, 1);
			}
			else if (itemchance == 158) { 
			cm.gainItem(1002243, 1);
			}
			else if (itemchance == 159) { 
			cm.gainItem(1372008, 1);
			}
			else if (itemchance == 160) { 
			cm.gainItem(1382008, 1);
			}
			else if (itemchance == 161) { 
			cm.gainItem(1382011, 1);
			}
			else if (itemchance == 162) { 
			cm.gainItem(1092021, 1);
			}
			else if (itemchance == 163) { 
			cm.gainItem(1051034, 1);
			}
			else if (itemchance == 164) { 
			cm.gainItem(1050047, 1);
			}

			else if (itemchance == 165) { 
			cm.gainItem(1040019, 1);
			}
			else if (itemchance == 166) { 
			cm.gainItem(1041031, 1);
			}
			else if (itemchance == 167) { 
			cm.gainItem(1051033, 1);
			}
			else if (itemchance == 168) { 
			cm.gainItem(1002153, 1);
			}
			else if (itemchance == 169) { 
			cm.gainItem(1002252, 1);
			}
			else if (itemchance == 170) { 
			cm.gainItem(1051024, 1);
			}
			else if (itemchance == 171) { 
			cm.gainItem(1002153, 1);
			}
			else if (itemchance == 172) { 
			cm.gainItem(1050068, 1);
			}
			else if (itemchance == 173) { 
			cm.gainItem(1382003, 1);
			}
			else if (itemchance == 174) { 
			cm.gainItem(1382006, 1);
			}
			else if (itemchance == 175) { 
			cm.gainItem(1050055, 1);
			}
			else if (itemchance == 176) { 
			cm.gainItem(1051031, 1);
			}
			else if (itemchance == 177) { 
			cm.gainItem(1050025, 1);
			}
			else if (itemchance == 178) { 
			cm.gainItem(1002155, 1);
			}
			else if (itemchance == 179) { 
			cm.gainItem(1002245, 1);
			}
			else if (itemchance == 180) { 
			cm.gainItem(13720013, 1);
			}
			else if (itemchance == 181) { 
			cm.gainItem(1452004, 1);
			}
			else if (itemchance == 182) { 
			cm.gainItem(1452023, 1);
			}
			else if (itemchance == 183) { 
			cm.gainItem(1060057, 1);
			}
			else if (itemchance == 184) { 
			cm.gainItem(1040071, 1);
			}
			else if (itemchance == 185) { 
			cm.gainItem(1002137, 1);
			}
			else if (itemchance == 186) { 
			cm.gainItem(1462009, 1);
			}
			else if (itemchance == 187) { 
			cm.gainItem(1452017, 1);
			}
			else if (itemchance == 188) { 
			cm.gainItem(1040025, 1);
			}
			else if (itemchance == 189) { 
			cm.gainItem(1041027, 1);
			}
			else if (itemchance == 190) { 
			cm.gainItem(1452005, 1);
			}
			else if (itemchance == 191) { 
			cm.gainItem(1452007, 1);
			}
			else if (itemchance == 192) { 
			cm.gainItem(1061057, 1);
			}
			else if (itemchance == 193) { 
			cm.gainItem(1472006, 1);
			}
			else if (itemchance == 194) { 
			cm.gainItem(1472019, 1);
			}
			else if (itemchance == 195) { 
			cm.gainItem(1060084, 1);
			}
			else if (itemchance == 196) { 
			cm.gainItem(1472028, 1);
			}
			else if (itemchance == 197) { 
			cm.gainItem(1002179, 1);
			}
			else if (itemchance == 198) { 
			cm.gainItem(1082074, 1);
			}
			else if (itemchance == 199) { 
			cm.gainItem(1332015, 1);
			}
			else if (itemchance == 200) { 
			cm.gainItem(1432001, 1);
			}
			else if (itemchance == 201) { 
			cm.gainItem(1060071, 1);
			}
			else if (itemchance == 202) { 
			cm.gainItem(1472007, 1);
			}
			else if (itemchance == 203) { 
			cm.gainItem(1472002, 1);
			}
			else if (itemchance == 204) { 
			cm.gainItem(1051009, 1);
			}
			else if (itemchance == 205) { 
			cm.gainItem(1061037, 1);
			}
			else if (itemchance == 206) { 
			cm.gainItem(1332016, 1);
			}
			else if (itemchance == 207) { 
			cm.gainItem(1332034, 1);
			}
			else if (itemchance == 208) { 
			cm.gainItem(1472020, 1);
			}
			else if ((itemchance >= 209) && (itemchance <= 215)) { 
			cm.gainItem(1102084, 1);
			}
			else if ((itemchance >= 216) && (itemchance <= 221)) { 
			cm.gainItem(1102086, 1);
			}
			else if ((itemchance >= 222) && (itemchance <= 228)) { 
			cm.gainItem(1102042, 1);
			}
			else if ((itemchance >= 228) && (itemchance <= 240)) { 
			cm.gainItem(1032026, 1);
			}
			else if (itemchance >= 228) { 
			cm.gainItem(1082149, 1);
			}

			cm.dispose();
		}
	}
}
