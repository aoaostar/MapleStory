//////////////////////////////
//七宝*自由冒险岛*最具创意////
//1346464664/992916233//////
///////////////////////////

var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/1#";
var Y = "#fUI/GuildMark.img/Mark/Letter/00005024/3#";
var X = "#fUI/GuildMark.img/Mark/Letter/00005023/1#";
var D = "#fUI/GuildMark.img/Mark/Letter/00005003/1#";
var M = "#fUI/GuildMark.img/Mark/Letter/00005012/1#";
var A = "#fUI/GuildMark.img/Mark/Letter/00005000/1#";
var P = "#fUI/GuildMark.img/Mark/Letter/00005015/1#";
var Z = "#fUI/GuildMark.img/Mark/Letter/00005025/9#";

var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
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
	
	var MC = cm.getServerName();
    
	    if ( cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {

    var  
	    selStr = "#r"+MC+"#b 场景特效开关设置；#k\r\n\r\n#r#e提示；若当前地图已有特效，再次开启则为取消。\r\n消费；2000点券/次#n#k\r\n\r\n";
		

		
		
	
		selStr += "#L0##b"+箭头+"返回界面#k#l\r\n";
		
		selStr += "#L18##b"+箭头+"鞭炮#k#r[庆祝专用]#k#l\r\n";
		
		selStr += "#L10##b"+箭头+"烟花#k#r[庆祝专用]#k#l\r\n";
		
		selStr += "#L1##b"+箭头+"雪球飘飘#k#l\r\n";
		
		selStr += "#L2##b"+箭头+"花瓣飘飘#k#l\r\n";
		
		selStr += "#L3##b"+箭头+"气泡飘飘#k#l\r\n";
		
		selStr += "#L4##b"+箭头+"雪花飘飘#k#l\r\n";
		
		selStr += "#L5##b"+箭头+"袜子飘飘#k#l\r\n";
		
		selStr += "#L6##b"+箭头+"巧克力飘飘#k#l\r\n";
		
	    selStr += "#L7##b"+箭头+"彩叶飘飘#k#l\r\n";
		
		selStr += "#L8##b"+箭头+"糖果飘飘#k#l\r\n";
		
		selStr += "#L9##b"+箭头+"枯叶飘飘#k#l\r\n";
		
		
		
		selStr += "#L11##b"+箭头+"可乐飘飘#k#l\r\n";
		
		selStr += "#L12##b"+箭头+"豆豆飘飘#k#l\r\n";
		
		selStr += "#L13##b"+箭头+"可乐带飘飘#k#l\r\n";
		
		selStr += "#L14##b"+箭头+"足球飘飘#k#l\r\n";
		
		selStr += "#L15##b"+箭头+"人参飘飘#k#l\r\n";
		
		selStr += "#L16##b"+箭头+"散花散花#k#l\r\n";
		
		selStr += "#L17##b"+箭头+"巧克力面包飘飘#k#l\r\n";



         
		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) { 
        case 0:
            cm.dispose();
            cm.openNpc(9900004,3);	
            break;
		 case 1:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120000);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 2:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120001);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 3:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120002);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 4:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120003);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 5:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120004);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 6:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120005);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 7:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120006);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 8:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120007);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 9:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120008);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 10:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120009);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 11:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120010);
			cm.dispose();
			
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 12:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121000);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 13:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121001);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;	
		case 14:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121002);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;	
		case 15:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121003);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;	
		case 16:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121004);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
		case 17:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5121005);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;
       	case 18:
		  if(cm.getPlayer().getCSPoints(1) >= 2000){
			cm.gainNX(-2000);
			cm.设置天气(5120015);
			cm.dispose();
		
			} else {
				cm.sendOk("点券不够。");
				cm.dispose();
				return;
			}
            break;		
			
		
		
	
			
		
		
	    
		
			 
			} 
		}
    }
