importPackage(net.sf.cherry.tools);
importPackage(net.sf.cherry.client);

var status = 0;

	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
		if (mode == -1) {
		cm.dispose();
		} else {
		if (status >= 0 && mode == 0) {
		cm.dispose();
		return;
		}
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "#d我这里可以兑换每日签到（出席勋章）奖励哦！\r\n";

  textz += "#L1#1.#v2450000##z2450000# 需要:#v4032398#1个 \r\n";
   textz += "#L2#2.#v1122017##z1122017#（1日）+#v2450000##z2450000#（X5） \r\n         需要:#v4032398#1个 （120级以下可以兑换）\r\n";
   textz += "#L3#3.#v3010070##z3010070# 需要:#v4032398#99个 \r\n";
 // textz += "#L4#4.#v2044401##z2044401# 需要:#v4031227#8个 \r\n";
  // textz += "#L5#5.#v2043701##z2043701# 需要:#v4031227#8个 \r\n";
  // textz += "#L6#6.#v2043801##z2043801# 需要:#v4031227#8个 \r\n";
  // textz += "#L7#7.#v2044501##z2044501# 需要:#v4031227#8个 \r\n";
  // textz += "#L8#8.#v2044601##z2044601# 需要:#v4031227#8个 \r\n";
 //  textz += "#L11#9.#v2044701##z2044701# 需要:#v4031227#8个 \r\n";
 // textz += "#L10#2.#v1112748##z1112748# 需要:#v4251202#4个 \r\n";
 // textz += "#L9#9.#v4031227##z4031227# 需要:#v4251200#1个 \r\n";
  //textz += "#L12#10.#v2340000##z2340000# 需要:#v4031227#8个 \r\n";

 // textz += "#L13#1.#v1052647##z1052647# 需要:#v4251202#5个#v4001129#50个  \r\n";
 // textz += "#L14#2.#v1082540##z1082540# 需要:#v4251202#5个#v4001129#50个  \r\n";
 // textz += "#L15#3.#v1102612##z1102612# 需要:#v4251202#5个#v4001129#50个  \r\n";
//  textz += "#L16#4.#v1003946##z1003946# 需要:#v4251202#5个#v4001129#50个  \r\n";
 // textz += "#L17#5.#v1072853##z1072853# 需要:#v4251202#5个#v4001129#50个 \r\n";




		cm.sendSimple (textz);  

	}else if (status == 1) {

	       if (selection == 1){
                   if (!cm.haveItem(4032398,1)) {
 			cm.sendOk("请带来#v4032398##z4032398#*1");    
			cm.dispose();
		} else{
			cm.gainItem(4032398,-1);
			cm.gainItem(2450000,1,1);
			cm.sendOk("#b兑换成功");
      			cm.dispose();
			}

       } else if (selection == 2){
if (cm.getPlayer().getLevel() < 120 && cm.getBossLog("新人保送计划")==0) {
cm.setBossLog("新人保送计划");
cm.gainItem(1122017,1,1);
cm.gainItem(2450000,5,1);
cm.dispose();
}else{
cm.sendOk("你已超过120级或者你今天已经领取过了！");
cm.dispose();
}

       } else if (selection == 3){
                  if (!cm.haveItem(4032398,30)) {
    cm.sendOk("请带来#v4032398##z4032398#*30");
         cm.dispose();
 
  } else{
   cm.gainItem(4032398,-99);
   cm.gainItem(3010070,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 4){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8	);
   cm.gainItem(2044401,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 5){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2043701,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 6){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2043801,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 7){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2044501,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 8){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2044601,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 9){
                  if (!cm.haveItem(4251200,1)) {
    cm.sendOk("请带来#v4251200##z4251200#*1");
         cm.dispose();
 
  } else{
   cm.gainItem(4251200,-1);
   cm.gainItem(4031127,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 10){
                  if (!cm.haveItem(4251202,8)) {
    cm.sendOk("请带来#v4251202##z4251202#*8");
         cm.dispose();

  } else{
   cm.gainItem(4251202,-4);
    cm.gainItem(1112748,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 11){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2044701,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 12){
                  if (!cm.haveItem(4031227,8)) {
    cm.sendOk("请带来#v4031227##z4031227#*8");
         cm.dispose();
  } else{
   cm.gainItem(4031227,-8);
   cm.gainItem(2340000,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 13){
                  if (!cm.haveItem(4251202,5)) {
    cm.sendOk("请带来#v4251202##z4251202#*5");
         cm.dispose();
                  } else if (!cm.haveItem(4001129,50)) {
    cm.sendOk("请带来#v4001129##z4001129#*50");
         cm.dispose();
  } else{
   cm.gainItem(4251202,-5);
  cm.gainItem(4001129,-50);
   
   cm.gainItem(1052647,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


       } else if (selection == 14){
                  if (!cm.haveItem(4251202,5)) {
    cm.sendOk("请带来#v4251202##z4251202#*5");
         cm.dispose();
                  } else if (!cm.haveItem(4001129,50)) {
    cm.sendOk("请带来#v4001129##z4001129#*50");
         cm.dispose();
  } else{
   cm.gainItem(4251202,-5);
cm.gainItem(4001129,-50);
   cm.gainItem(1082540,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


       } else if (selection == 15){
                  if (!cm.haveItem(4251202,5)) {
    cm.sendOk("请带来#v4251202##z4251202#*5");
         cm.dispose();
                  } else if (!cm.haveItem(4001129,50)) {
    cm.sendOk("请带来#v4001129##z4001129#*50");
         cm.dispose();
  } else{
   cm.gainItem(4251202,-5);
   cm.gainItem(4001129,-50);
   cm.gainItem(1102612,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 16){
                  if (!cm.haveItem(4251202,5)) {
    cm.sendOk("请带来#v4251202##z4251202#*5");
         cm.dispose();
                  } else if (!cm.haveItem(4001129,50)) {
    cm.sendOk("请带来#v4001129##z4001129#*50");
         cm.dispose();
  } else{
   cm.gainItem(4251202,-5);
   cm.gainItem(4001129,-50);
   cm.gainItem(1003946,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


       } else if (selection == 17){
         
                  if (!cm.haveItem(4251202,5)){
    cm.sendOk("请带来#v4251202##z4251202#*5#v4001129##z4001129#");
         cm.dispose();
                  } else if (!cm.haveItem(4001129,50)) {
    cm.sendOk("请带来#v4001129##z4001129#*50");
         cm.dispose();
  } else{
   cm.gainItem(4251202,-5);
   cm.gainItem(4001129,-50);
   cm.gainItem(1072853,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 18){
                  if (!cm.haveItem(4031227,30)) {
    cm.sendOk("请带来#v4031227##z4031227#*30");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-30);
   cm.gainItem(1072853,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 19){
                 if (!cm.haveItem(4031227,30)) {
    cm.sendOk("请带来#v4031227##z4031227#*30");
         cm.dispose();
                  } else if (!cm.haveItem(4001126,1000)) {
    cm.sendOk("请带来#v4001126##z4001126#*1000");
         cm.dispose();
		  } else  if (cm.getMeso() < 20000000) {
 			cm.sendOk("请带来#r20000000W#k金币#k");
      			cm.dispose();
                  } else if (!cm.haveItem(1122031,1)) {
    cm.sendOk("请带来#v1122031##z1122031#*1");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-30);
   cm.gainItem(4001126,-1000);
   cm.gainMeso(-20000000);
   cm.gainItem(1122031,-1);
   cm.gainItem(1122036,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 20){
                 if (!cm.haveItem(4031227,30)) {
    cm.sendOk("请带来#v4031227##z4031227#*30");
         cm.dispose();
                  } else if (!cm.haveItem(4001126,1000)) {
    cm.sendOk("请带来#v4001126##z4001126#*1000");
         cm.dispose();
		  } else  if (cm.getMeso() < 20000000) {
 			cm.sendOk("请带来#r20000000W#k金币#k");
      			cm.dispose();
                  } else if (!cm.haveItem(1122032,1)) {
    cm.sendOk("请带来#v1122032##z1122032#*1");
         cm.dispose();
 
  } else{
   cm.gainItem(4031227,-30);
   cm.gainItem(4001126,-1000);
   cm.gainMeso(-20000000);
   cm.gainItem(1122032,-1);
   cm.gainItem(1122037,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}

       } else if (selection == 210){
                  if (!cm.haveItem(4021000,2)) {
    cm.sendOk("请带来#v4021000##z4021000#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021001,2)) {
    cm.sendOk("请带来#v4021001##z4021001#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021002,2)) {
    cm.sendOk("请带来#v4021002##z4021002#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021003,2)) {
    cm.sendOk("请带来#v4021003##z4021003#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021004,2)) {
    cm.sendOk("请带来#v4021004##z4021004#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021005,2)) {
    cm.sendOk("请带来#v4021005##z4021005#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021006,2)) {
    cm.sendOk("请带来#v4021006##z4021006#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021007,2)) {
    cm.sendOk("请带来#v4021007##z4021007#*2");
         cm.dispose();
                  } else if (!cm.haveItem(4021008,2)) {
    cm.sendOk("请带来#v4021008##z4021008#*2");
         cm.dispose();
                  } else if (!cm.haveItem(1132205,1)) {
    cm.sendOk("请带来#v1132205##z1132205#*1");
         cm.dispose();
         cm.dispose();
 
  } else{
   cm.gainItem(4021000,-2);
   cm.gainItem(4021001,-2);
   cm.gainItem(4021002,-2);
   cm.gainItem(4021003,-2);
   cm.gainItem(4021004,-2);
   cm.gainItem(4021005,-2);
   cm.gainItem(4021006,-2);
   cm.gainItem(4021007,-2);
   cm.gainItem(4021008,-2);
   cm.gainItem(1132205,-1);
   cm.gainItem(1132204,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


       } else if (selection == 21){
                  if (!cm.haveItem(4001126,200)) {
    cm.sendOk("请带来#v4001126##z4001126#*200");
         cm.dispose();
 
  } else{
   cm.gainItem(4001126,-200);
   cm.gainItem(1092110,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


       } else if (selection == 22){
                  if (!cm.haveItem(4001126,200)) {
    cm.sendOk("请带来#v4001126##z4001126#*200");
         cm.dispose();
 
  } else{
   cm.gainItem(4001126,-200);
   cm.gainItem(1092111,1);
   cm.sendOk("#b兑换成功")
   cm.dispose();
}


}
}
}
}
